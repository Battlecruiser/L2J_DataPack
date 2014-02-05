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
package instances.LibraryOfSages;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Library of Sages instance zone.
 * @author Adry_85
 */
public final class LibraryOfSages extends AbstractNpcAI
{
	protected class LoSWorld extends InstanceWorld
	{
		protected L2Npc elcadia = null;
		protected long storeTime = 0;
	}
	
	private static final int INSTANCEID = 156;
	// NPCs
	private static final int SOPHIA1 = 32596;
	private static final int PILE_OF_BOOKS1 = 32809;
	private static final int PILE_OF_BOOKS2 = 32810;
	private static final int PILE_OF_BOOKS3 = 32811;
	private static final int PILE_OF_BOOKS4 = 32812;
	private static final int PILE_OF_BOOKS5 = 32813;
	private static final int SOPHIA2 = 32861;
	private static final int SOPHIA3 = 32863;
	private static final int ELCADIA_INSTANCE = 32785;
	// Locations
	private static final Location START_LOC = new Location(37063, -49813, -1128, 0, INSTANCEID);
	private static final Location EXIT_LOC = new Location(37063, -49813, -1128, 0, 0);
	private static final Location LIBRARY_LOC = new Location(37355, -50065, -1127, 0, INSTANCEID);
	// NpcString
	private static final NpcStringId[] ELCADIA_DIALOGS =
	{
		NpcStringId.I_MUST_ASK_LIBRARIAN_SOPHIA_ABOUT_THE_BOOK,
		NpcStringId.THIS_LIBRARY_ITS_HUGE_BUT_THERE_ARENT_MANY_USEFUL_BOOKS_RIGHT,
		NpcStringId.AN_UNDERGROUND_LIBRARY_I_HATE_DAMP_AND_SMELLY_PLACES,
		NpcStringId.THE_BOOK_THAT_WE_SEEK_IS_CERTAINLY_HERE_SEARCH_INCH_BY_INCH
	};
	
	private LibraryOfSages()
	{
		super(LibraryOfSages.class.getSimpleName(), "instances");
		addFirstTalkId(SOPHIA2, ELCADIA_INSTANCE, PILE_OF_BOOKS1, PILE_OF_BOOKS2, PILE_OF_BOOKS3, PILE_OF_BOOKS4, PILE_OF_BOOKS5);
		addStartNpc(SOPHIA1, SOPHIA2, SOPHIA3);
		addTalkId(SOPHIA1, SOPHIA2, SOPHIA3);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
		if (!(tmpworld instanceof LoSWorld))
		{
			return null;
		}
		
		LoSWorld world = (LoSWorld) tmpworld;
		switch (event)
		{
			case "TELEPORT2":
			{
				player.teleToLocation(LIBRARY_LOC);
				world.elcadia.teleToLocation(LIBRARY_LOC);
				break;
			}
			case "EXIT":
			{
				cancelQuestTimer("FOLLOW", npc, player);
				player.teleToLocation(EXIT_LOC);
				world.elcadia.deleteMe();
				break;
			}
			case "FOLLOW":
			{
				npc.setIsRunning(true);
				npc.getAI().startFollow(player);
				broadcastNpcSay(npc, Say2.NPC_ALL, ELCADIA_DIALOGS[getRandom(ELCADIA_DIALOGS.length)]);
				startQuestTimer("FOLLOW", 10000, npc, player);
				break;
			}
			case "ENTER":
			{
				cancelQuestTimer("FOLLOW", npc, player);
				player.teleToLocation(START_LOC);
				world.elcadia.teleToLocation(START_LOC);
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		enterInstance(talker, "LibraryOfSages.xml", START_LOC);
		return super.onTalk(npc, talker);
	}
	
	protected int enterInstance(L2PcInstance player, String template, Location loc)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof LoSWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, loc, world.getInstanceId(), false);
			spawnNPC(player, (LoSWorld) world);
			return 0;
		}
		// New instance
		world = new LoSWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((LoSWorld) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("Library of Sages started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, loc, world.getInstanceId(), false);
		world.addAllowed(player.getObjectId());
		spawnNPC(player, (LoSWorld) world);
		return world.getInstanceId();
	}
	
	protected void spawnNPC(L2PcInstance player, LoSWorld world)
	{
		final L2Npc npc = addSpawn(ELCADIA_INSTANCE, player.getX(), player.getY(), player.getZ(), 0, false, 0, false, player.getInstanceId());
		world.elcadia = npc;
		startQuestTimer("FOLLOW", 3000, npc, player);
	}
	
	public static void main(String[] args)
	{
		new LibraryOfSages();
	}
}
