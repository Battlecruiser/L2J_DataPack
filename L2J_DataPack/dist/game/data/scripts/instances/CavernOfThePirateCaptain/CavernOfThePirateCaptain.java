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
package instances.CavernOfThePirateCaptain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.PcCondOverride;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Cavern Of The Pirate Captain (Day Dream) instance Zone.
 * @author St3eT
 */
public class CavernOfThePirateCaptain extends AbstractNpcAI
{
	protected class CavernOfThePirateCaptainWorld extends InstanceWorld
	{
		List<L2PcInstance> playersInside = new ArrayList<>();
		L2Attackable _zaken;
		long storeTime = 0;
		boolean _is83;
		int _zakenRoom;
		int _blueFounded;
	}
	
	// NPCs
	private static final int PATHFINDER = 32713; // Pathfinder Worker
	private static final int ZAKEN_60 = 29176; // Zaken
	private static final int ZAKEN_83 = 29181; // Zaken
	private static final int CANDLE = 32705; // Zaken's Candle
	private static final int DOLL_BLADER_60 = 29023; // Doll Blader
	private static final int DOLL_BLADER_83 = 29182; // Doll Blader
	private static final int VALE_MASTER_60 = 29024; // Veil Master
	private static final int VALE_MASTER_83 = 29183; // Veil Master
	private static final int PIRATES_ZOMBIE_60 = 29027; // Pirate Zombie
	private static final int PIRATES_ZOMBIE_83 = 29185; // Pirate Zombie
	private static final int PIRATES_ZOMBIE_CAPTAIN_60 = 29026; // Pirate Zombie Captain
	private static final int PIRATES_ZOMBIE_CAPTAIN_83 = 29184; // Pirate Zombie Captain
	// Items
	private static final int VORPAL_RING = 15763; // Sealed Vorpal Ring
	private static final int VORPAL_EARRING = 15764; // Sealed Vorpal Earring
	private static final int FIRE = 15280; // Transparent 1HS (for NPC)
	private static final int RED = 15281; // Transparent 1HS (for NPC)
	private static final int BLUE = 15302; // Transparent Bow (for NPC)
	// Locations
	private static final Location[] ENTER_LOC =
	{
		new Location(52684, 219989, -3496),
		new Location(52669, 219120, -3224),
		new Location(52672, 219439, -3312),
	};
	private static final Location[] CANDLE_LOC =
	{
		// Floor 1
		new Location(53313, 220133, -3498),
		new Location(53313, 218079, -3498),
		new Location(54240, 221045, -3498),
		new Location(54325, 219095, -3498),
		new Location(54240, 217155, -3498),
		new Location(55257, 220028, -3498),
		new Location(55257, 218172, -3498),
		new Location(56280, 221045, -3498),
		new Location(56195, 219095, -3498),
		new Location(56280, 217155, -3498),
		new Location(57215, 220133, -3498),
		new Location(57215, 218079, -3498),
		// Floor 2
		new Location(53313, 220133, -3226),
		new Location(53313, 218079, -3226),
		new Location(54240, 221045, -3226),
		new Location(54325, 219095, -3226),
		new Location(54240, 217155, -3226),
		new Location(55257, 220028, -3226),
		new Location(55257, 218172, -3226),
		new Location(56280, 221045, -3226),
		new Location(56195, 219095, -3226),
		new Location(56280, 217155, -3226),
		new Location(57215, 220133, -3226),
		new Location(57215, 218079, -3226),
		// Floor 3
		new Location(53313, 220133, -2954),
		new Location(53313, 218079, -2954),
		new Location(54240, 221045, -2954),
		new Location(54325, 219095, -2954),
		new Location(54240, 217155, -2954),
		new Location(55257, 220028, -2954),
		new Location(55257, 218172, -2954),
		new Location(56280, 221045, -2954),
		new Location(56195, 219095, -2954),
		new Location(56280, 217155, -2954),
		new Location(57215, 220133, -2954),
		new Location(57215, 218079, -2954),
	};
	// Misc
	private static final int MIN_LV_60 = 55;
	private static final int MIN_LV_83 = 78;
	private static final int PLAYERS_60_MIN = 9;
	private static final int PLAYERS_60_MAX = 27;
	private static final int PLAYERS_83_MIN = 9;
	private static final int PLAYERS_83_MAX = 27;
	private static final int TEMPLATE_ID_60 = 133;
	private static final int TEMPLATE_ID_83 = 135;
	private static final int HOURS = 6;
	private static final int MINUTES = 30;
	private static final int DAY_A = Calendar.MONDAY;
	private static final int DAY_B = Calendar.WEDNESDAY;
	private static final int DAY_C = Calendar.FRIDAY;
	//@formatter:off
	private static final int[][] ROOM_DATA =
	{
		// Floor 1
		{54240, 220133, -3498, 1, 3, 4, 6},
		{54240, 218073, -3498, 2, 5, 4, 7},
		{55265, 219095, -3498, 4, 9, 6, 7},
		{56289, 220133, -3498, 8, 11, 6, 9},
		{56289, 218073, -3498, 10, 12, 7, 9},
		// Floor 2
		{54240, 220133, -3226, 13, 15, 16, 18},
		{54240, 218073, -3226, 14, 17, 16, 19},
		{55265, 219095, -3226, 21, 16, 19, 18},
		{56289, 220133, -3226, 20, 23, 21, 18},
		{56289, 218073, -3226, 22, 24, 19, 21},
		// Floor 3
		{54240, 220133, -2954, 25, 27, 28, 30},
		{54240, 218073, -2954, 26, 29, 28, 31},
		{55265, 219095, -2954, 33, 28, 31, 30},
		{56289, 220133, -2954, 32, 35, 30, 33},
		{56289, 218073, -2954, 34, 36, 31, 33}
	};
	//@formatter:on
	
	private CavernOfThePirateCaptain()
	{
		super(CavernOfThePirateCaptain.class.getSimpleName(), "instances");
		addStartNpc(PATHFINDER);
		addTalkId(PATHFINDER);
		addKillId(ZAKEN_60, ZAKEN_83);
		addFirstTalkId(CANDLE);
	}
	
	private void enterInstance(L2PcInstance player, String template, boolean is83)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof CavernOfThePirateCaptainWorld)
			{
				teleportPlayer(player, ENTER_LOC[getRandom(ENTER_LOC.length)], world.getInstanceId(), false);
				return;
			}
			player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
			return;
		}
		
		if (checkConditions(player, is83))
		{
			world = new CavernOfThePirateCaptainWorld();
			world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
			world.setTemplateId(is83 ? TEMPLATE_ID_83 : TEMPLATE_ID_60);
			world.setStatus(0);
			InstanceManager.getInstance().addWorld(world);
			final CavernOfThePirateCaptainWorld curworld = (CavernOfThePirateCaptainWorld) world;
			curworld._is83 = is83;
			curworld.storeTime = System.currentTimeMillis();
			
			if (!player.isInParty())
			{
				managePlayerEnter(player, curworld);
			}
			else if (player.getParty().isInCommandChannel())
			{
				for (L2PcInstance players : player.getParty().getCommandChannel().getMembers())
				{
					managePlayerEnter(players, curworld);
				}
			}
			else
			{
				for (L2PcInstance players : player.getParty().getMembers())
				{
					managePlayerEnter(players, curworld);
				}
			}
			manageNpcSpawn(curworld);
		}
	}
	
	private void managePlayerEnter(L2PcInstance player, CavernOfThePirateCaptainWorld world)
	{
		if (!world._is83)
		{
			player.stopAllEffectsExceptThoseThatLastThroughDeath();
			if (player.hasSummon())
			{
				player.getSummon().stopAllEffectsExceptThoseThatLastThroughDeath();
			}
		}
		world.playersInside.add(player);
		world.addAllowed(player.getObjectId());
		teleportPlayer(player, ENTER_LOC[getRandom(ENTER_LOC.length)], world.getInstanceId(), false);
	}
	
	private boolean checkConditions(L2PcInstance player, boolean is83)
	{
		if (player.canOverrideCond(PcCondOverride.INSTANCE_CONDITIONS))
		{
			return true;
		}
		
		if (!player.isInParty())
		{
			broadcastSystemMessage(player, null, SystemMessageId.NOT_IN_PARTY_CANT_ENTER, false);
			return false;
		}
		
		final L2Party party = player.getParty();
		final boolean isInCC = party.isInCommandChannel();
		final List<L2PcInstance> members = (isInCC) ? party.getCommandChannel().getMembers() : party.getMembers();
		final boolean isPartyLeader = (isInCC) ? party.getCommandChannel().isLeader(player) : party.isLeader(player);
		
		if (!isPartyLeader)
		{
			broadcastSystemMessage(player, null, SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER, false);
			return false;
		}
		
		if ((members.size() < (is83 ? PLAYERS_83_MIN : PLAYERS_60_MIN)) || (members.size() > (is83 ? PLAYERS_83_MAX : PLAYERS_60_MAX)))
		{
			broadcastSystemMessage(player, null, SystemMessageId.PARTY_EXCEEDED_THE_LIMIT_CANT_ENTER, false);
			return false;
		}
		
		for (L2PcInstance groupMembers : members)
		{
			if (groupMembers.getLevel() < (is83 ? MIN_LV_83 : MIN_LV_60))
			{
				broadcastSystemMessage(player, groupMembers, SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT, true);
				return false;
			}
			
			if (!player.isInsideRadius(groupMembers, 1000, true, true))
			{
				broadcastSystemMessage(player, groupMembers, SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED, true);
				return false;
			}
			
			final Long reentertime = InstanceManager.getInstance().getInstanceTime(groupMembers.getObjectId(), (is83 ? TEMPLATE_ID_83 : TEMPLATE_ID_60));
			if (System.currentTimeMillis() < reentertime)
			{
				broadcastSystemMessage(player, groupMembers, SystemMessageId.C1_MAY_NOT_REENTER_YET, true);
				return false;
			}
		}
		return true;
	}
	
	private void broadcastSystemMessage(L2PcInstance player, L2PcInstance member, SystemMessageId msgId, boolean toGroup)
	{
		final SystemMessage sm = SystemMessage.getSystemMessage(msgId);
		
		if (toGroup)
		{
			sm.addPcName(member);
			
			if (player.getParty().isInCommandChannel())
			{
				player.getParty().getCommandChannel().broadcastPacket(sm);
			}
			else
			{
				player.getParty().broadcastPacket(sm);
			}
		}
		else
		{
			player.broadcastPacket(sm);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("enter60"))
		{
			enterInstance(player, "CavernOfThePirateCaptainWorldDay60.xml", false);
		}
		else if (event.equals("enter83"))
		{
			enterInstance(player, "CavernOfThePirateCaptainWorldDay83.xml", true);
		}
		else
		{
			final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
			
			if ((tmpworld != null) && (tmpworld instanceof CavernOfThePirateCaptainWorld))
			{
				final CavernOfThePirateCaptainWorld world = (CavernOfThePirateCaptainWorld) tmpworld;
				
				switch (event)
				{
					case "BURN_BLUE":
					{
						if (npc.getRightHandItem() == 0)
						{
							npc.setRHandId(FIRE);
							startQuestTimer("BURN_BLUE2", 3000, npc, player);
							if (world._blueFounded == 4)
							{
								startQuestTimer("SHOW_ZAKEN", 5000, npc, player);
							}
						}
						break;
					}
					case "BURN_BLUE2":
					{
						if (npc.getRightHandItem() == FIRE)
						{
							npc.setRHandId(BLUE);
						}
						break;
					}
					case "BURN_RED":
					{
						if (npc.getRightHandItem() == 0)
						{
							npc.setRHandId(FIRE);
							startQuestTimer("BURN_RED2", 3000, npc, player);
						}
						break;
					}
					case "BURN_RED2":
					{
						if (npc.getRightHandItem() == FIRE)
						{
							final int room = getRoomByCandle(npc);
							npc.setRHandId(RED);
							manageScreenMsg(world, NpcStringId.THE_CANDLES_CAN_LEAD_YOU_TO_ZAKEN_DESTROY_HIM);
							spawnNpc(world._is83 ? DOLL_BLADER_83 : DOLL_BLADER_60, room, player, world);
							spawnNpc(world._is83 ? VALE_MASTER_83 : VALE_MASTER_60, room, player, world);
							spawnNpc(world._is83 ? PIRATES_ZOMBIE_83 : PIRATES_ZOMBIE_60, room, player, world);
							spawnNpc(world._is83 ? PIRATES_ZOMBIE_CAPTAIN_83 : PIRATES_ZOMBIE_CAPTAIN_60, room, player, world);
						}
						break;
					}
					case "SHOW_ZAKEN":
					{
						if (world._is83)
						{
							manageScreenMsg(world, NpcStringId.WHO_DARES_AWKAWEN_THE_MIGHTY_ZAKEN);
						}
						world._zaken.setInvisible(false);
						world._zaken.setIsParalyzed(false);
						spawnNpc(world._is83 ? DOLL_BLADER_83 : DOLL_BLADER_60, world._zakenRoom, player, world);
						spawnNpc(world._is83 ? PIRATES_ZOMBIE_83 : PIRATES_ZOMBIE_60, world._zakenRoom, player, world);
						spawnNpc(world._is83 ? PIRATES_ZOMBIE_CAPTAIN_83 : PIRATES_ZOMBIE_CAPTAIN_60, world._zakenRoom, player, world);
						break;
					}
				}
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if ((tmpworld != null) && (tmpworld instanceof CavernOfThePirateCaptainWorld))
		{
			final CavernOfThePirateCaptainWorld world = (CavernOfThePirateCaptainWorld) tmpworld;
			final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
			
			if (npc.getId() == ZAKEN_83)
			{
				for (L2PcInstance playersInside : world.playersInside)
				{
					if ((playersInside != null) && ((playersInside.getInstanceId() == world.getInstanceId()) && playersInside.isInsideRadius(npc, 1500, true, true)))
					{
						final long time = System.currentTimeMillis() - world.storeTime;
						if (time <= 300000) // 5 minutes
						{
							if (getRandomBoolean())
							{
								giveItems(playersInside, VORPAL_RING, 1);
							}
						}
						else if (time <= 600000) // 10 minutes
						{
							if (getRandom(100) < 30)
							{
								giveItems(playersInside, VORPAL_EARRING, 1);
							}
						}
						else if (time <= 900000) // 15 minutes
						{
							if (getRandom(100) < 25)
							{
								giveItems(playersInside, VORPAL_RING, 1);
							}
						}
					}
				}
			}
			saveReenterForPlayers(world);
			inst.setDuration(300000);
			inst.setEmptyDestroyTime(0);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if ((tmpworld != null) && (tmpworld instanceof CavernOfThePirateCaptainWorld))
		{
			final CavernOfThePirateCaptainWorld world = (CavernOfThePirateCaptainWorld) tmpworld;
			final boolean isBlue = npc.getVariables().getInt("isBlue", 0) == 1;
			
			if (npc.isScriptValue(0))
			{
				if (isBlue)
				{
					world._blueFounded++;
					startQuestTimer("BURN_BLUE", 500, npc, player);
				}
				else
				{
					startQuestTimer("BURN_RED", 500, npc, player);
				}
				npc.setScriptValue(1);
			}
		}
		return null;
	}
	
	private int getRoomByCandle(L2Npc npc)
	{
		final int candleId = npc.getVariables().getInt("candleId", 0);
		
		for (int i = 0; i < 15; i++)
		{
			if ((ROOM_DATA[i][3] == candleId) || (ROOM_DATA[i][4] == candleId))
			{
				return i + 1;
			}
		}
		
		if ((candleId == 6) || (candleId == 7))
		{
			return 3;
		}
		else if ((candleId == 18) || (candleId == 19))
		{
			return 8;
		}
		else if ((candleId == 30) || (candleId == 31))
		{
			return 13;
		}
		return 0;
	}
	
	private void manageScreenMsg(CavernOfThePirateCaptainWorld world, NpcStringId stringId)
	{
		for (L2PcInstance players : world.playersInside)
		{
			if ((players != null) && (players.getInstanceId() == world.getInstanceId()))
			{
				showOnScreenMsg(players, stringId, 2, 6000);
			}
		}
	}
	
	private L2Attackable spawnNpc(int npcId, int roomId, L2PcInstance player, CavernOfThePirateCaptainWorld world)
	{
		if ((player != null) && (npcId != ZAKEN_60) && (npcId != ZAKEN_83))
		{
			final L2Attackable mob = (L2Attackable) addSpawn(npcId, ROOM_DATA[roomId - 1][0] + getRandom(350), ROOM_DATA[roomId - 1][1] + getRandom(350), ROOM_DATA[roomId - 1][2], 0, false, 0, false, world.getInstanceId());
			addAttackPlayerDesire(mob, player);
			return mob;
		}
		return (L2Attackable) addSpawn(npcId, ROOM_DATA[roomId - 1][0], ROOM_DATA[roomId - 1][1], ROOM_DATA[roomId - 1][2], 0, false, 0, false, world.getInstanceId());
	}
	
	private void manageNpcSpawn(CavernOfThePirateCaptainWorld world)
	{
		final List<L2Npc> candles = new ArrayList<>();
		world._zakenRoom = getRandom(1, 15);
		
		for (int i = 0; i < 36; i++)
		{
			final L2Npc candle = addSpawn(CANDLE, CANDLE_LOC[i], false, 0, false, world.getInstanceId());
			candle.getVariables().set("candleId", i + 1);
			candles.add(candle);
		}
		
		for (int i = 3; i < 7; i++)
		{
			candles.get(ROOM_DATA[world._zakenRoom - 1][i] - 1).getVariables().set("isBlue", 1);
		}
		world._zaken = spawnNpc(world._is83 ? ZAKEN_83 : ZAKEN_60, world._zakenRoom, null, world);
		world._zaken.setInvisible(true);
		world._zaken.setIsParalyzed(true);
	}
	
	private void saveReenterForPlayers(InstanceWorld world)
	{
		final Calendar reenter = Calendar.getInstance();
		reenter.set(Calendar.MINUTE, MINUTES);
		reenter.set(Calendar.HOUR_OF_DAY, HOURS);
		
		if (reenter.getTimeInMillis() <= System.currentTimeMillis())
		{
			reenter.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		if ((reenter.get(Calendar.DAY_OF_WEEK) <= DAY_A) || (reenter.get(Calendar.DAY_OF_WEEK) > DAY_C))
		{
			while (reenter.get(Calendar.DAY_OF_WEEK) != DAY_A)
			{
				reenter.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		else if (reenter.get(Calendar.DAY_OF_WEEK) <= DAY_B)
		{
			while (reenter.get(Calendar.DAY_OF_WEEK) != DAY_B)
			{
				reenter.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		else
		{
			while (reenter.get(Calendar.DAY_OF_WEEK) != DAY_C)
			{
				reenter.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		
		final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.INSTANT_ZONE_S1_RESTRICTED);
		sm.addString(InstanceManager.getInstance().getInstanceIdName(world.getTemplateId()));
		
		for (int objectId : world.getAllowed())
		{
			final L2PcInstance player = L2World.getInstance().getPlayer(objectId);
			InstanceManager.getInstance().setInstanceTime(objectId, world.getTemplateId(), reenter.getTimeInMillis());
			if ((player != null) && player.isOnline())
			{
				player.sendPacket(sm);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new CavernOfThePirateCaptain();
	}
}
