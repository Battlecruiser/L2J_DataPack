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
package instances.MonasteryOfSilence1;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Monastery of Silence instance zone.
 * @author Adry_85
 */
public final class MonasteryOfSilence1 extends AbstractNpcAI
{
	protected static final class MoSWorld extends InstanceWorld
	{
		protected L2Npc elcadia = null;
	}
	
	private static final int TEMPLATE_ID = 151;
	// NPCs
	private static final int ELCADIA_INSTANCE = 32787;
	private static final int ERIS_EVIL_THOUGHTS = 32792;
	private static final int RELIC_GUARDIAN = 32803;
	private static final int RELIC_WATCHER1 = 32804;
	private static final int RELIC_WATCHER2 = 32805;
	private static final int RELIC_WATCHER3 = 32806;
	private static final int RELIC_WATCHER4 = 32807;
	private static final int ODD_GLOBE = 32815;
	private static final int TELEPORT_CONTROL_DEVICE1 = 32817;
	private static final int TELEPORT_CONTROL_DEVICE2 = 32818;
	private static final int TELEPORT_CONTROL_DEVICE3 = 32819;
	private static final int TELEPORT_CONTROL_DEVICE4 = 32820;
	// Locations
	private static final Location START_LOC = new Location(120710, -86971, -3392);
	private static final Location EXIT_LOC = new Location(115983, -87351, -3397, 0, 0);
	private static final Location CENTRAL_ROOM_LOC = new Location(85794, -249788, -8320);
	private static final Location SOUTH_WATCHERS_ROOM_LOC = new Location(85798, -246566, -8320);
	private static final Location WEST_WATCHERS_ROOM_LOC = new Location(82531, -249405, -8320);
	private static final Location EAST_WATCHERS_ROOM_LOC = new Location(88665, -249784, -8320);
	private static final Location NORTH_WATCHERS_ROOM_LOC = new Location(85792, -252336, -8320);
	private static final Location BACK_LOC = new Location(120710, -86971, -3392);
	// NpcString
	private static final NpcStringId[] ELCADIA_DIALOGS =
	{
		NpcStringId.IT_SEEMS_THAT_YOU_CANNOT_REMEMBER_TO_THE_ROOM_OF_THE_WATCHER_WHO_FOUND_THE_BOOK,
		NpcStringId.WE_MUST_SEARCH_HIGH_AND_LOW_IN_EVERY_ROOM_FOR_THE_READING_DESK_THAT_CONTAINS_THE_BOOK_WE_SEEK,
		NpcStringId.REMEMBER_THE_CONTENT_OF_THE_BOOKS_THAT_YOU_FOUND_YOU_CAN_T_TAKE_THEM_OUT_WITH_YOU
	};
	// Buffs
	private static final SkillHolder[] BUFFS =
	{
		new SkillHolder(6725, 1), // Bless the Blood of Elcadia
		new SkillHolder(6728, 1), // Recharge of Elcadia
		new SkillHolder(6730, 1), // Greater Battle Heal of Elcadia
	};
	
	private MonasteryOfSilence1()
	{
		super(MonasteryOfSilence1.class.getSimpleName(), "instances");
		addFirstTalkId(TELEPORT_CONTROL_DEVICE1, TELEPORT_CONTROL_DEVICE2, TELEPORT_CONTROL_DEVICE3, TELEPORT_CONTROL_DEVICE4, ERIS_EVIL_THOUGHTS);
		addStartNpc(ODD_GLOBE, TELEPORT_CONTROL_DEVICE1, TELEPORT_CONTROL_DEVICE2, TELEPORT_CONTROL_DEVICE3, TELEPORT_CONTROL_DEVICE4, ERIS_EVIL_THOUGHTS);
		addTalkId(ODD_GLOBE, ERIS_EVIL_THOUGHTS, RELIC_GUARDIAN, RELIC_WATCHER1, RELIC_WATCHER2, RELIC_WATCHER3, RELIC_WATCHER4, TELEPORT_CONTROL_DEVICE1, TELEPORT_CONTROL_DEVICE2, TELEPORT_CONTROL_DEVICE3, TELEPORT_CONTROL_DEVICE4, ERIS_EVIL_THOUGHTS);
	}
	
	private void enterInstance(L2PcInstance player, String template)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof MoSWorld))
			{
				player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			}
			else
			{
				// Teleport player.
				teleportPlayer(player, START_LOC, world.getInstanceId(), false);
				spawnNPC(player, (MoSWorld) world);
				removeBuffs(player);
			}
		}
		else
		{
			// New instance.
			world = new MoSWorld();
			world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
			world.setTemplateId(TEMPLATE_ID);
			world.setStatus(0);
			InstanceManager.getInstance().addWorld(world);
			_log.info("Monastery of Silence started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
			// Teleport players.
			teleportPlayer(player, START_LOC, world.getInstanceId(), false);
			spawnNPC(player, (MoSWorld) world);
			removeBuffs(player);
			world.addAllowed(player.getObjectId());
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
		if (!(tmpworld instanceof MoSWorld))
		{
			return null;
		}
		
		MoSWorld world = (MoSWorld) tmpworld;
		switch (event)
		{
			case "TELE2":
			{
				teleportPlayer(player, CENTRAL_ROOM_LOC, world.getInstanceId());
				world.elcadia.teleToLocation(CENTRAL_ROOM_LOC.getX(), CENTRAL_ROOM_LOC.getY(), CENTRAL_ROOM_LOC.getZ(), 0, world.getInstanceId());
				startQuestTimer("START_MOVIE", 2000, npc, player);
				break;
			}
			case "EXIT":
			{
				cancelQuestTimer("FOLLOW", npc, player);
				teleportPlayer(player, EXIT_LOC, 0);
				world.elcadia.deleteMe();
				break;
			}
			case "START_MOVIE":
			{
				player.showQuestMovie(24);
				break;
			}
			case "BACK":
			{
				teleportPlayer(player, BACK_LOC, world.getInstanceId());
				world.elcadia.teleToLocation(BACK_LOC.getX(), BACK_LOC.getY(), BACK_LOC.getZ(), 0, world.getInstanceId());
				break;
			}
			case "EAST":
			{
				teleportPlayer(player, EAST_WATCHERS_ROOM_LOC, world.getInstanceId());
				world.elcadia.teleToLocation(EAST_WATCHERS_ROOM_LOC.getX(), EAST_WATCHERS_ROOM_LOC.getY(), EAST_WATCHERS_ROOM_LOC.getZ(), 0, world.getInstanceId());
				break;
			}
			case "WEST":
			{
				teleportPlayer(player, WEST_WATCHERS_ROOM_LOC, world.getInstanceId());
				world.elcadia.teleToLocation(WEST_WATCHERS_ROOM_LOC.getX(), WEST_WATCHERS_ROOM_LOC.getY(), WEST_WATCHERS_ROOM_LOC.getZ(), 0, world.getInstanceId());
				break;
			}
			case "NORTH":
			{
				teleportPlayer(player, NORTH_WATCHERS_ROOM_LOC, world.getInstanceId());
				world.elcadia.teleToLocation(NORTH_WATCHERS_ROOM_LOC.getX(), NORTH_WATCHERS_ROOM_LOC.getY(), NORTH_WATCHERS_ROOM_LOC.getZ(), 0, world.getInstanceId());
				break;
			}
			case "SOUTH":
			{
				teleportPlayer(player, SOUTH_WATCHERS_ROOM_LOC, world.getInstanceId());
				world.elcadia.teleToLocation(SOUTH_WATCHERS_ROOM_LOC.getX(), SOUTH_WATCHERS_ROOM_LOC.getY(), SOUTH_WATCHERS_ROOM_LOC.getZ(), 0, world.getInstanceId());
				break;
			}
			case "CENTER":
			{
				teleportPlayer(player, CENTRAL_ROOM_LOC, world.getInstanceId());
				world.elcadia.teleToLocation(CENTRAL_ROOM_LOC.getX(), CENTRAL_ROOM_LOC.getY(), CENTRAL_ROOM_LOC.getZ(), 0, world.getInstanceId());
				break;
			}
			case "FOLLOW":
			{
				npc.setIsRunning(true);
				npc.getAI().startFollow(player);
				if (player.isInCombat())
				{
					broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.YOUR_WORK_HERE_IS_DONE_SO_RETURN_TO_THE_CENTRAL_GUARDIAN);
					npc.setTarget(player);
					npc.doCast(BUFFS[getRandom(BUFFS.length)].getSkill());
				}
				else
				{
					broadcastNpcSay(npc, Say2.NPC_ALL, ELCADIA_DIALOGS[getRandom(ELCADIA_DIALOGS.length)]);
				}
				startQuestTimer("FOLLOW", 10000, npc, player);
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		if (npc.getId() == ODD_GLOBE)
		{
			enterInstance(talker, "MonasteryOfSilence.xml");
		}
		return super.onTalk(npc, talker);
	}
	
	private static final void removeBuffs(L2Character ch)
	{
		ch.stopAllEffectsExceptThoseThatLastThroughDeath();
		if (ch.hasSummon())
		{
			ch.getSummon().stopAllEffectsExceptThoseThatLastThroughDeath();
		}
	}
	
	protected void spawnNPC(L2PcInstance player, MoSWorld world)
	{
		final L2Npc npc = addSpawn(ELCADIA_INSTANCE, player.getX(), player.getY(), player.getZ(), 0, false, 0, false, player.getInstanceId());
		world.elcadia = npc;
		startQuestTimer("FOLLOW", 3000, npc, player);
	}
	
	public static void main(String[] args)
	{
		new MonasteryOfSilence1();
	}
}
