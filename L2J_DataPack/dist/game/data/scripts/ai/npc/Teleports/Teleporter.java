/*
 * Copyright (C) 2004-2014 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.npc.Teleports;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import ai.npc.AbstractDPScript;

import com.l2jserver.Config;
import com.l2jserver.gameserver.instancemanager.SiegeManager;
import com.l2jserver.gameserver.instancemanager.TownManager;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.zone.ZoneId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * Basic teleporter AI, used for all normal teleporters, the special ones should extend this one.
 * @author UnAfraid
 */
public class Teleporter extends AbstractDPScript
{
	private final Map<Integer, TeleportHolder> _teleporters = new HashMap<>();
	
	protected Teleporter()
	{
		super(Teleporter.class.getSimpleName(), "ai/npc/teleporters");
		load();
	}
	
	@Override
	protected void load()
	{
		// Load all teleporters data
		parseDatapackFile(getAbsolutePath() + "/teleporterData.xml");
		log("Loaded: " + _teleporters.size() + " npc teleporters.");
		
		// Register npcs
		_teleporters.keySet().forEach(this::addTalkId);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		final NpcHtmlMessage msg = new NpcHtmlMessage(npc.getObjectId());
		msg.setFile(talker.getHtmlPrefix(), getAbsolutePath() + "/teleports.htm");
		final StringBuilder sb = new StringBuilder();
		for (TeleportLocation loc : _teleporters.get(npc.getId()).getLocations())
		{
			final int id = loc.getId();
			
			String finalName = loc.getName();
			String confirmDesc = loc.getName();
			if (loc.getNpcStringId() != null)
			{
				finalName = "<fstring>" + loc.getNpcStringId().getId() + "</fstring>";
				confirmDesc = "F;" + loc.getNpcStringId().getId();
			}
			if (shouldPayFee(talker, loc))
			{
				finalName += " - " + loc.getFeeCount() + " " + getItemName(loc.getFeeId());
			}
			sb.append("<button align=left icon=teleport action=\"bypass -h Quest " + getName() + " teleport " + id + "\" msg=\"811;" + confirmDesc + "\">" + finalName + "</button>");
		}
		msg.replace("%locations%", sb.toString());
		talker.sendPacket(msg);
		return null;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final StringTokenizer st = new StringTokenizer(event);
		final String cmd = st.nextToken();
		switch (cmd)
		{
			case "teleport":
			{
				if (!st.hasMoreTokens())
				{
					return null;
				}
				
				final String idToken = st.nextToken();
				if (!Util.isDigit(idToken))
				{
					return null;
				}
				
				final int locId = Integer.valueOf(idToken);
				final TeleportHolder holder = _teleporters.get(npc.getId());
				final TeleportLocation loc = holder.getLocation(locId);
				if (loc == null)
				{
					return null;
				}
				
				// you cannot teleport to village that is in siege
				if (SiegeManager.getInstance().getSiege(loc.getX(), loc.getY(), loc.getZ()) != null)
				{
					player.sendPacket(SystemMessageId.YOU_CANNOT_TELEPORT_TO_A_VILLAGE_THAT_IS_IN_A_SIEGE);
					return null;
				}
				else if (TownManager.townHasCastleInSiege(loc.getX(), loc.getY()) && npc.isInsideZone(ZoneId.TOWN))
				{
					player.sendPacket(SystemMessageId.YOU_CANNOT_TELEPORT_TO_A_VILLAGE_THAT_IS_IN_A_SIEGE);
					return null;
				}
				else if (npc.getCastle().getSiege().isInProgress())
				{
					final NpcHtmlMessage msg = new NpcHtmlMessage(npc.getObjectId());
					msg.setFile(player.getHtmlPrefix(), "data/html/teleporter/castleteleporter-busy.htm");
					player.sendPacket(msg);
					return null;
				}
				else if (!Config.ALT_GAME_KARMA_PLAYER_CAN_USE_GK && (player.getKarma() != 0)) // karma
				{
					player.sendMessage("Go away, you're not welcome here.");
					return null;
				}
				else if (player.isCombatFlagEquipped())
				{
					player.sendPacket(SystemMessageId.YOU_CANNOT_TELEPORT_WHILE_IN_POSSESSION_OF_A_WARD);
					return null;
				}
				else if (player.isAlikeDead())
				{
					return null;
				}
				
				if (shouldPayFee(player, loc) && !takeItems(player, loc.getFeeId(), loc.getFeeCount()))
				{
					if (loc.getFeeId() == Inventory.ADENA_ID)
					{
						player.sendPacket(SystemMessageId.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
					}
					else
					{
						player.sendMessage("You do not have enough " + super.getItemName(loc.getFeeId()));
					}
				}
				else
				{
					player.teleToLocation(loc);
				}
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	protected boolean shouldPayFee(L2PcInstance player, TeleportLocation loc)
	{
		return Config.ALT_GAME_FREE_TELEPORT || ((player.getLevel() >= 76) && ((loc.getFeeId() != 0) && (loc.getFeeCount() > 0)));
	}
	
	@Override
	protected String getItemName(int itemId)
	{
		if (itemId == Inventory.ADENA_ID)
		{
			return "<fstring>1000308</fstring>";
		}
		else if (itemId == Inventory.ANCIENT_ADENA_ID)
		{
			return "<fstring>1000309</fstring>";
		}
		return super.getItemName(itemId);
	}
	
	@Override
	protected void parseDocument()
	{
		for (Node listNode = getCurrentDocument().getFirstChild(); listNode != null; listNode = listNode.getNextSibling())
		{
			if ("list".equals(listNode.getNodeName()))
			{
				for (Node npcNode = listNode.getFirstChild(); npcNode != null; npcNode = npcNode.getNextSibling())
				{
					if ("npc".equals(npcNode.getNodeName()))
					{
						parseNpc(npcNode);
					}
				}
			}
		}
	}
	
	protected void parseNpc(Node npcNode)
	{
		final int id = parseInt(npcNode.getAttributes(), "id");
		final TeleportHolder holder = new TeleportHolder(id);
		for (Node locNode = npcNode.getFirstChild(); locNode != null; locNode = locNode.getNextSibling())
		{
			if ("location".equals(locNode.getNodeName()))
			{
				parseLocation(id, holder, locNode);
			}
		}
		_teleporters.put(id, holder);
	}
	
	protected void parseLocation(int id, TeleportHolder holder, Node locNode)
	{
		final NamedNodeMap attrs = locNode.getAttributes();
		final int nextId = holder.getLocations().size() + 1;
		final StatsSet set = new StatsSet();
		for (int i = 0; i < attrs.getLength(); i++)
		{
			final Node locationNode = attrs.item(i);
			set.set(locationNode.getNodeName(), locationNode.getNodeValue());
		}
		holder.addLocation(new TeleportLocation(nextId, set));
	}
	
	public static void main(String[] args)
	{
		new Teleporter();
	}
}
