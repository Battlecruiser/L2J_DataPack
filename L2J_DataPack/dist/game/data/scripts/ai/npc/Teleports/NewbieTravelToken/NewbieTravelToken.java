/*
 * Copyright (C) 2004-2013 L2J DataPack
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
package ai.npc.Teleports.NewbieTravelToken;

import java.util.Map;

import javolution.util.FastMap;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.util.Util;

/**
 * Newbie Travel Token AI.<br>
 * Original Jython script by DrLecter.
 * @author Plim
 */
public class NewbieTravelToken extends AbstractNpcAI
{
	// Item
	private static final int NEWBIE_TRAVEL_TOKEN = 8542;
	// NPC Id - Teleport Location
	private static final Map<Integer, Location> DATA = new FastMap<>();
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (Util.isDigit(event))
		{
			final int npcId = Integer.parseInt(event);
			if (DATA.keySet().contains(npcId))
			{
				if (hasQuestItems(player, NEWBIE_TRAVEL_TOKEN))
				{
					takeItems(player, NEWBIE_TRAVEL_TOKEN, 1);
					player.teleToLocation(DATA.get(npcId), false);
				}
				else
				{
					player.sendPacket(SystemMessageId.INCORRECT_ITEM_COUNT);
				}
				return super.onAdvEvent(event, npc, player);
			}
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		return player.getLevel() >= 20 ? "cant-travel.htm" : npc.getNpcId() + ".htm";
	}
	
	private NewbieTravelToken(String name, String descr)
	{
		super(name, descr);
		// Initialize Map
		DATA.put(30600, new Location(12160, 16554, -4583)); // DE
		DATA.put(30601, new Location(115594, -177993, -912)); // DW
		DATA.put(30599, new Location(45470, 48328, -3059)); // EV
		DATA.put(30602, new Location(-45067, -113563, -199)); // OV
		DATA.put(30598, new Location(-84053, 243343, -3729)); // TI
		DATA.put(32135, new Location(-119712, 44519, 368)); // SI
		
		for (int npcId : DATA.keySet())
		{
			addStartNpc(npcId);
			addTalkId(npcId);
		}
	}
	
	public static void main(String[] args)
	{
		new NewbieTravelToken(NewbieTravelToken.class.getSimpleName(), "ai/npc/Teleports");
	}
}
