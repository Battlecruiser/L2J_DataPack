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
package ai.group_template;

import java.util.Map;

import javolution.util.FastMap;
import quests.Q00692_HowtoOpposeEvil.Q00692_HowtoOpposeEvil;
import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.datatables.NpcData;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.idfactory.IdFactory;
import com.l2jserver.gameserver.instancemanager.GraciaSeedsManager;
import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.util.Util;

/**
 * Energy Seeds AI.
 * @author Gigiikun
 */
public class EnergySeeds extends AbstractNpcAI
{
	private static final int HOWTOOPPOSEEVIL_CHANCE = 60;
	private static final int RATE = 1;
	private static final int RESPAWN = 480000;
	private static final int RANDOM_RESPAWN_OFFSET = 180000;
	private static Map<Integer, ESSpawn> _spawns = new FastMap<>();
	protected static Map<L2Npc, Integer> _spawnedNpcs = new FastMap<L2Npc, Integer>().shared();
	
	private static final int TEMPORARY_TELEPORTER = 32602;
	// @formatter:off
	private static final int[] SEED_IDS =
	{
		18678, 18679, 18680, 18681, 18682, 18683
	};
	
	private static final int[][] ANNIHILATION_SUPRISE_MOB_IDS =
	{
		{ 22746, 22747, 22748, 22749 },
		{ 22754, 22755, 22756 },
		{ 22760, 22761, 22762 }
	};
	
	private static int[] SEED_OF_DESTRUCTION_DOORS =
	{
		12240003, 12240004, 12240005, 12240006, 12240007, 12240008, 12240009,
		12240010, 12240011, 12240012, 12240013, 12240014, 12240015, 12240016,
		12240017, 12240018, 12240019, 12240020, 12240021, 12240022, 12240023,
		12240024, 12240025, 12240026, 12240027, 12240028, 12240029, 12240030,
		12240031
	};
	private static final Location SOD_EXIT_POINT = new Location(-248717, 250260, 4337);
	// @formatter:off
	private static final int SOD_ZONE = 60009;
	
	private enum GraciaSeeds
	{
		INFINITY,
		DESTRUCTION,
		ANNIHILATION_BISTAKON,
		ANNIHILATION_REPTILIKON,
		ANNIHILATION_COKRAKON
	}
	
	private EnergySeeds()
	{
		super(EnergySeeds.class.getSimpleName(), "instances"); // ai/group_template?
		registerMobs(SEED_IDS);
		addFirstTalkId(SEED_IDS);
		addFirstTalkId(TEMPORARY_TELEPORTER);
		addEnterZoneId(SOD_ZONE);
		addSpawnsToList();
		startAI();
	}
	
	protected boolean isSeedActive(GraciaSeeds seed)
	{
		switch (seed)
		{
			case INFINITY:
				return false;
			case DESTRUCTION:
				return GraciaSeedsManager.getInstance().getSoDState() == 2;
			case ANNIHILATION_BISTAKON:
			case ANNIHILATION_REPTILIKON:
			case ANNIHILATION_COKRAKON:
				return true;
		}
		return true;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isSummon)
	{
		if (!Util.contains(targets, npc) || (skill.getId() != 5780))
		{
			return super.onSkillSee(npc, caster, skill, targets, isSummon);
		}
		
		npc.deleteMe();
		
		if (_spawnedNpcs.containsKey(npc) && _spawns.containsKey(_spawnedNpcs.get(npc)))
		{
			ESSpawn spawn = _spawns.get(_spawnedNpcs.get(npc));
			spawn.scheduleRespawn(RESPAWN + getRandom(RANDOM_RESPAWN_OFFSET));
			_spawnedNpcs.remove(npc);
			if (isSeedActive(spawn._seedId))
			{
				int itemId = 0;
				
				switch (npc.getId())
				{
					case 18678: // Water
						itemId = 14016;
						break;
					case 18679: // Fire
						itemId = 14015;
						break;
					case 18680: // Wind
						itemId = 14017;
						break;
					case 18681: // Earth
						itemId = 14018;
						break;
					case 18682: // Divinity
						itemId = 14020;
						break;
					case 18683: // Darkness
						itemId = 14019;
						break;
					default:
						return super.onSkillSee(npc, caster, skill, targets, isSummon);
				}
				if (getRandom(100) < 33)
				{
					caster.sendPacket(SystemMessageId.THE_COLLECTION_HAS_SUCCEEDED);
					caster.addItem("EnergySeed", itemId, getRandom(RATE + 1, 2 * RATE), null, true);
				}
				else
				{
					caster.sendPacket(SystemMessageId.THE_COLLECTION_HAS_SUCCEEDED);
					caster.addItem("EnergySeed", itemId, getRandom(1, RATE), null, true);
				}
				seedCollectEvent(caster, npc, spawn._seedId);
			}
		}
		
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("StartSoDAi"))
		{
			for (int doorId : SEED_OF_DESTRUCTION_DOORS)
			{
				L2DoorInstance doorInstance = DoorTable.getInstance().getDoor(doorId);
				if (doorInstance != null)
				{
					doorInstance.openMe();
				}
			}
			startAI(GraciaSeeds.DESTRUCTION);
		}
		else if (event.equalsIgnoreCase("StopSoDAi"))
		{
			for (int doorId : SEED_OF_DESTRUCTION_DOORS)
			{
				L2DoorInstance doorInstance = DoorTable.getInstance().getDoor(doorId);
				if (doorInstance != null)
				{
					doorInstance.closeMe();
				}
			}
			for (L2PcInstance ch : ZoneManager.getInstance().getZoneById(SOD_ZONE).getPlayersInside())
			{
				if (ch != null)
				{
					ch.teleToLocation(SOD_EXIT_POINT);
				}
			}
			stopAI(GraciaSeeds.DESTRUCTION);
		}
		else if (event.equalsIgnoreCase("DeSpawnTask"))
		{
			if (npc.isInCombat())
			{
				startQuestTimer("DeSpawnTask", 30000, npc, null);
			}
			else
			{
				npc.deleteMe();
			}
		}
		return null;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.getId() == TEMPORARY_TELEPORTER)
		{
			player.teleToLocation(SOD_EXIT_POINT);
		}
		player.sendPacket(ActionFailed.STATIC_PACKET);
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (_spawnedNpcs.containsKey(npc) && _spawns.containsKey(_spawnedNpcs.get(npc)))
		{
			_spawns.get(_spawnedNpcs.get(npc)).scheduleRespawn(RESPAWN + getRandom(RANDOM_RESPAWN_OFFSET));
			_spawnedNpcs.remove(npc);
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onEnterZone(L2Character character, L2ZoneType zone)
	{
		if (character.getInstanceId() != 0)
		{
			return super.onEnterZone(character, zone);
		}
		
		if (character instanceof L2PcInstance)
		{
			switch (zone.getId())
			{
				case SOD_ZONE:
					if (!isSeedActive(GraciaSeeds.DESTRUCTION) && !character.isGM())
					{
						character.teleToLocation(SOD_EXIT_POINT);
					}
					break;
			}
		}
		return super.onEnterZone(character, zone);
	}
	
	public void startAI()
	{
		// spawn all NPCs
		for (ESSpawn spawn : _spawns.values())
		{
			if (isSeedActive(spawn._seedId))
			{
				spawn.scheduleRespawn(0);
			}
		}
	}
	
	public void startAI(GraciaSeeds type)
	{
		// spawn all NPCs
		for (ESSpawn spawn : _spawns.values())
		{
			if (spawn._seedId == type)
			{
				spawn.scheduleRespawn(0);
			}
		}
	}
	
	public void stopAI(GraciaSeeds type)
	{
		for (L2Npc seed : _spawnedNpcs.keySet())
		{
			if (type == _spawns.get(_spawnedNpcs.get(seed))._seedId)
			{
				seed.deleteMe();
			}
		}
	}
	
	public void seedCollectEvent(L2PcInstance player, L2Npc seedEnergy, GraciaSeeds seedType)
	{
		if (player == null)
		{
			return;
		}
		QuestState st = player.getQuestState(Q00692_HowtoOpposeEvil.class.getSimpleName());
		switch (seedType)
		{
			case INFINITY:
				if ((st != null) && st.isCond(3))
				{
					handleQuestDrop(st, 13798);
				}
				break;
			case DESTRUCTION:
				if ((st != null) && st.isCond(3))
				{
					handleQuestDrop(st, 13867);
				}
				break;
			case ANNIHILATION_BISTAKON:
				if ((st != null) && st.isCond(3))
				{
					handleQuestDrop(st, 15535);
				}
				if (getRandom(100) < 50)
				{
					L2MonsterInstance mob = spawnSupriseMob(seedEnergy, ANNIHILATION_SUPRISE_MOB_IDS[0][getRandom(ANNIHILATION_SUPRISE_MOB_IDS[0].length)]);
					mob.setRunning();
					mob.addDamageHate(player, 0, 999);
					mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
				}
				break;
			case ANNIHILATION_REPTILIKON:
				if ((st != null) && st.isCond(3))
				{
					handleQuestDrop(st, 15535);
				}
				if (getRandom(100) < 50)
				{
					L2MonsterInstance mob = spawnSupriseMob(seedEnergy, ANNIHILATION_SUPRISE_MOB_IDS[1][getRandom(ANNIHILATION_SUPRISE_MOB_IDS[1].length)]);
					mob.setRunning();
					mob.addDamageHate(player, 0, 999);
					mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
				}
				break;
			case ANNIHILATION_COKRAKON:
				if ((st != null) && st.isCond(3))
				{
					handleQuestDrop(st, 15535);
				}
				if (getRandom(100) < 50)
				{
					L2MonsterInstance mob = spawnSupriseMob(seedEnergy, ANNIHILATION_SUPRISE_MOB_IDS[2][getRandom(ANNIHILATION_SUPRISE_MOB_IDS[2].length)]);
					mob.setRunning();
					mob.addDamageHate(player, 0, 999);
					mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
				}
				break;
		}
	}
	
	private L2MonsterInstance spawnSupriseMob(L2Npc energy, int npcId)
	{
		L2NpcTemplate supriseMobTemplate = NpcData.getInstance().getTemplate(npcId);
		L2MonsterInstance monster = new L2MonsterInstance(IdFactory.getInstance().getNextId(), supriseMobTemplate);
		monster.setCurrentHpMp(monster.getMaxHp(), monster.getMaxMp());
		monster.setHeading(energy.getHeading());
		monster.setInstanceId(energy.getInstanceId());
		monster.setShowSummonAnimation(true);
		monster.spawnMe(energy.getX(), energy.getY(), energy.getZ());
		startQuestTimer("DeSpawnTask", 30000, monster, null);
		return monster;
	}
	
	private void handleQuestDrop(QuestState st, int itemId)
	{
		double chance = HOWTOOPPOSEEVIL_CHANCE * Config.RATE_QUEST_DROP;
		int numItems = (int) (chance / 100);
		chance = chance % 100;
		if (getRandom(100) < chance)
		{
			numItems++;
		}
		if (numItems > 0)
		{
			st.giveItems(itemId, numItems);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
	}
	
	private void addSpawnsToList()
	{
		// Seed of Destruction
		// Temporary Teleporters
		//@formatter:off
		_spawns.put(1, new ESSpawn(1, GraciaSeeds.DESTRUCTION, new Location(-245790,220320,-12104), new int[]{TEMPORARY_TELEPORTER}));
		_spawns.put(2, new ESSpawn(2, GraciaSeeds.DESTRUCTION, new Location(-249770,207300,-11952), new int[]{TEMPORARY_TELEPORTER}));
		//Energy Seeds
		_spawns.put(3, new ESSpawn(3, GraciaSeeds.DESTRUCTION, new Location(-248360,219272,-12448), new int[]{18678,18679,18680}));
		_spawns.put(4, new ESSpawn(4, GraciaSeeds.DESTRUCTION, new Location(-249448,219256,-12448), new int[]{18678,18679,18680}));
		_spawns.put(5, new ESSpawn(5, GraciaSeeds.DESTRUCTION, new Location(-249432,220872,-12448), new int[]{18678,18679,18680}));
		_spawns.put(6, new ESSpawn(6, GraciaSeeds.DESTRUCTION, new Location(-248360,220888,-12448), new int[]{18678,18679,18680}));
		
		_spawns.put(7, new ESSpawn(7, GraciaSeeds.DESTRUCTION, new Location(-250088,219256,-12448), new int[]{18681,18682}));
		_spawns.put(8, new ESSpawn(8, GraciaSeeds.DESTRUCTION, new Location(-250600,219272,-12448), new int[]{18681,18682}));
		_spawns.put(9, new ESSpawn(9, GraciaSeeds.DESTRUCTION, new Location(-250584,220904,-12448), new int[]{18681,18682}));
		_spawns.put(10, new ESSpawn(10, GraciaSeeds.DESTRUCTION, new Location(-250072,220888,-12448), new int[]{18681,18682}));
		
		_spawns.put(11, new ESSpawn(11, GraciaSeeds.DESTRUCTION, new Location(-253096,217704,-12296), new int[]{18683,18678}));
		_spawns.put(12, new ESSpawn(12, GraciaSeeds.DESTRUCTION, new Location(-253112,217048,-12288), new int[]{18683,18678}));
		_spawns.put(13, new ESSpawn(13, GraciaSeeds.DESTRUCTION, new Location(-251448,217032,-12288), new int[]{18683,18678}));
		_spawns.put(14, new ESSpawn(14, GraciaSeeds.DESTRUCTION, new Location(-251416,217672,-12296), new int[]{18683,18678}));
		
		_spawns.put(15, new ESSpawn(15, GraciaSeeds.DESTRUCTION, new Location(-251416,217672,-12296), new int[]{18679,18680}));
		_spawns.put(16, new ESSpawn(16, GraciaSeeds.DESTRUCTION, new Location(-251416,217016,-12280), new int[]{18679,18680}));
		_spawns.put(17, new ESSpawn(17, GraciaSeeds.DESTRUCTION, new Location(-249752,217016,-12280), new int[]{18679,18680}));
		_spawns.put(18, new ESSpawn(18, GraciaSeeds.DESTRUCTION, new Location(-249736,217688,-12296), new int[]{18679,18680}));
		
		_spawns.put(19, new ESSpawn(19, GraciaSeeds.DESTRUCTION, new Location(-252472,215208,-12120), new int[]{18681,18682}));
		_spawns.put(20, new ESSpawn(20, GraciaSeeds.DESTRUCTION, new Location(-252552,216760,-12248), new int[]{18681,18682}));
		_spawns.put(21, new ESSpawn(21, GraciaSeeds.DESTRUCTION, new Location(-253160,216744,-12248), new int[]{18681,18682}));
		_spawns.put(22, new ESSpawn(22, GraciaSeeds.DESTRUCTION, new Location(-253128,215160,-12096), new int[]{18681,18682}));
		
		_spawns.put(23, new ESSpawn(23, GraciaSeeds.DESTRUCTION, new Location(-250392,215208,-12120), new int[]{18683,18678}));
		_spawns.put(24, new ESSpawn(24, GraciaSeeds.DESTRUCTION, new Location(-250264,216744,-12248), new int[]{18683,18678}));
		_spawns.put(25, new ESSpawn(25, GraciaSeeds.DESTRUCTION, new Location(-249720,216744,-12248), new int[]{18683,18678}));
		_spawns.put(26, new ESSpawn(26, GraciaSeeds.DESTRUCTION, new Location(-249752,215128,-12096), new int[]{18683,18678}));
		
		_spawns.put(27, new ESSpawn(27, GraciaSeeds.DESTRUCTION, new Location(-250280,216760,-12248), new int[]{18679,18680,18681}));
		_spawns.put(28, new ESSpawn(28, GraciaSeeds.DESTRUCTION, new Location(-250344,216152,-12248), new int[]{18679,18680,18681}));
		_spawns.put(29, new ESSpawn(29, GraciaSeeds.DESTRUCTION, new Location(-252504,216152,-12248), new int[]{18679,18680,18681}));
		_spawns.put(30, new ESSpawn(30, GraciaSeeds.DESTRUCTION, new Location(-252520,216792,-12248), new int[]{18679,18680,18681}));
		
		_spawns.put(31, new ESSpawn(31, GraciaSeeds.DESTRUCTION, new Location(-242520,217272,-12384), new int[]{18681,18682,18683}));
		_spawns.put(32, new ESSpawn(32, GraciaSeeds.DESTRUCTION, new Location(-241432,217288,-12384), new int[]{18681,18682,18683}));
		_spawns.put(33, new ESSpawn(33, GraciaSeeds.DESTRUCTION, new Location(-241432,218936,-12384), new int[]{18681,18682,18683}));
		_spawns.put(34, new ESSpawn(34, GraciaSeeds.DESTRUCTION, new Location(-242536,218936,-12384), new int[]{18681,18682,18683}));
		
		_spawns.put(35, new ESSpawn(35, GraciaSeeds.DESTRUCTION, new Location(-240808,217272,-12384), new int[]{18678,18679}));
		_spawns.put(36, new ESSpawn(36, GraciaSeeds.DESTRUCTION, new Location(-240280,217272,-12384), new int[]{18678,18679}));
		_spawns.put(37, new ESSpawn(37, GraciaSeeds.DESTRUCTION, new Location(-240280,218952,-12384), new int[]{18678,18679}));
		_spawns.put(38, new ESSpawn(38, GraciaSeeds.DESTRUCTION, new Location(-240792,218936,-12384), new int[]{18678,18679}));
		
		_spawns.put(39, new ESSpawn(39, GraciaSeeds.DESTRUCTION, new Location(-239576,217240,-12640), new int[]{18680,18681,18682}));
		_spawns.put(40, new ESSpawn(40, GraciaSeeds.DESTRUCTION, new Location(-239560,216168,-12640), new int[]{18680,18681,18682}));
		_spawns.put(41, new ESSpawn(41, GraciaSeeds.DESTRUCTION, new Location(-237896,216152,-12640), new int[]{18680,18681,18682}));
		_spawns.put(42, new ESSpawn(42, GraciaSeeds.DESTRUCTION, new Location(-237912,217256,-12640), new int[]{18680,18681,18682}));
		
		_spawns.put(43, new ESSpawn(43, GraciaSeeds.DESTRUCTION, new Location(-237896,215528,-12640), new int[]{18683,18678}));
		_spawns.put(44, new ESSpawn(44, GraciaSeeds.DESTRUCTION, new Location(-239560,215528,-12640), new int[]{18683,18678}));
		_spawns.put(45, new ESSpawn(45, GraciaSeeds.DESTRUCTION, new Location(-239560,214984,-12640), new int[]{18683,18678}));
		_spawns.put(46, new ESSpawn(46, GraciaSeeds.DESTRUCTION, new Location(-237896,215000,-12640), new int[]{18683,18678}));
		
		_spawns.put(47, new ESSpawn(47, GraciaSeeds.DESTRUCTION, new Location(-237896,213640,-12768), new int[]{18678,18679,18680}));
		_spawns.put(48, new ESSpawn(48, GraciaSeeds.DESTRUCTION, new Location(-239560,213640,-12768), new int[]{18678,18679,18680}));
		_spawns.put(49, new ESSpawn(49, GraciaSeeds.DESTRUCTION, new Location(-239544,212552,-12768), new int[]{18678,18679,18680}));
		_spawns.put(50, new ESSpawn(50, GraciaSeeds.DESTRUCTION, new Location(-237912,212552,-12768), new int[]{18678,18679,18680}));
		
		_spawns.put(51, new ESSpawn(51, GraciaSeeds.DESTRUCTION, new Location(-237912,211912,-12768), new int[]{18681,18682}));
		_spawns.put(52, new ESSpawn(52, GraciaSeeds.DESTRUCTION, new Location(-237912,211400,-12768), new int[]{18681,18682}));
		_spawns.put(53, new ESSpawn(53, GraciaSeeds.DESTRUCTION, new Location(-239560,211400,-12768), new int[]{18681,18682}));
		_spawns.put(54, new ESSpawn(54, GraciaSeeds.DESTRUCTION, new Location(-239560,211912,-12768), new int[]{18681,18682}));
		
		_spawns.put(55, new ESSpawn(55, GraciaSeeds.DESTRUCTION, new Location(-241960,214536,-12512), new int[]{18683,18678,18679}));
		_spawns.put(56, new ESSpawn(56, GraciaSeeds.DESTRUCTION, new Location(-241976,213448,-12512), new int[]{18683,18678,18679}));
		_spawns.put(57, new ESSpawn(57, GraciaSeeds.DESTRUCTION, new Location(-243624,213448,-12512), new int[]{18683,18678,18679}));
		_spawns.put(58, new ESSpawn(58, GraciaSeeds.DESTRUCTION, new Location(-243624,214520,-12512), new int[]{18683,18678,18679}));
		
		_spawns.put(59, new ESSpawn(59, GraciaSeeds.DESTRUCTION, new Location(-241976,212808,-12504), new int[]{18680,18681}));
		_spawns.put(60, new ESSpawn(60, GraciaSeeds.DESTRUCTION, new Location(-241960,212280,-12504), new int[]{18680,18681}));
		_spawns.put(61, new ESSpawn(61, GraciaSeeds.DESTRUCTION, new Location(-243624,212264,-12504), new int[]{18680,18681}));
		_spawns.put(62, new ESSpawn(62, GraciaSeeds.DESTRUCTION, new Location(-243624,212792,-12504), new int[]{18680,18681}));
		
		_spawns.put(63, new ESSpawn(63, GraciaSeeds.DESTRUCTION, new Location(-243640,210920,-12640), new int[]{18682,18683,18678}));
		_spawns.put(64, new ESSpawn(64, GraciaSeeds.DESTRUCTION, new Location(-243624,209832,-12640), new int[]{18682,18683,18678}));
		_spawns.put(65, new ESSpawn(65, GraciaSeeds.DESTRUCTION, new Location(-241976,209832,-12640), new int[]{18682,18683,18678}));
		_spawns.put(66, new ESSpawn(66, GraciaSeeds.DESTRUCTION, new Location(-241976,210920,-12640), new int[]{18682,18683,18678}));
		
		_spawns.put(67, new ESSpawn(67, GraciaSeeds.DESTRUCTION, new Location(-241976,209192,-12640), new int[]{18679,18680}));
		_spawns.put(68, new ESSpawn(68, GraciaSeeds.DESTRUCTION, new Location(-241976,208664,-12640), new int[]{18679,18680}));
		_spawns.put(69, new ESSpawn(69, GraciaSeeds.DESTRUCTION, new Location(-243624,208664,-12640), new int[]{18679,18680}));
		_spawns.put(70, new ESSpawn(70, GraciaSeeds.DESTRUCTION, new Location(-243624,209192,-12640), new int[]{18679,18680}));
		
		_spawns.put(71, new ESSpawn(71, GraciaSeeds.DESTRUCTION, new Location(-241256,208664,-12896), new int[]{18681,18682,18683}));
		_spawns.put(72, new ESSpawn(72, GraciaSeeds.DESTRUCTION, new Location(-240168,208648,-12896), new int[]{18681,18682,18683}));
		_spawns.put(73, new ESSpawn(73, GraciaSeeds.DESTRUCTION, new Location(-240168,207000,-12896), new int[]{18681,18682,18683}));
		_spawns.put(74, new ESSpawn(74, GraciaSeeds.DESTRUCTION, new Location(-241256,207000,-12896), new int[]{18681,18682,18683}));
		
		_spawns.put(75, new ESSpawn(75, GraciaSeeds.DESTRUCTION, new Location(-239528,208648,-12896), new int[]{18678,18679}));
		_spawns.put(76, new ESSpawn(76, GraciaSeeds.DESTRUCTION, new Location(-238984,208664,-12896), new int[]{18678,18679}));
		_spawns.put(77, new ESSpawn(77, GraciaSeeds.DESTRUCTION, new Location(-239000,207000,-12896), new int[]{18678,18679}));
		_spawns.put(78, new ESSpawn(78, GraciaSeeds.DESTRUCTION, new Location(-239512,207000,-12896), new int[]{18678,18679}));
		
		_spawns.put(79, new ESSpawn(79, GraciaSeeds.DESTRUCTION, new Location(-245064,213144,-12384), new int[]{18680,18681,18682}));
		_spawns.put(80, new ESSpawn(80, GraciaSeeds.DESTRUCTION, new Location(-245064,212072,-12384), new int[]{18680,18681,18682}));
		_spawns.put(81, new ESSpawn(81, GraciaSeeds.DESTRUCTION, new Location(-246696,212072,-12384), new int[]{18680,18681,18682}));
		_spawns.put(82, new ESSpawn(82, GraciaSeeds.DESTRUCTION, new Location(-246696,213160,-12384), new int[]{18680,18681,18682}));
		
		_spawns.put(83, new ESSpawn(83, GraciaSeeds.DESTRUCTION, new Location(-245064,211416,-12384), new int[]{18683,18678}));
		_spawns.put(84, new ESSpawn(84, GraciaSeeds.DESTRUCTION, new Location(-245048,210904,-12384), new int[]{18683,18678}));
		_spawns.put(85, new ESSpawn(85, GraciaSeeds.DESTRUCTION, new Location(-246712,210888,-12384), new int[]{18683,18678}));
		_spawns.put(86, new ESSpawn(86, GraciaSeeds.DESTRUCTION, new Location(-246712,211416,-12384), new int[]{18683,18678}));
		
		_spawns.put(87, new ESSpawn(87, GraciaSeeds.DESTRUCTION, new Location(-245048,209544,-12512), new int[]{18679,18680,18681}));
		_spawns.put(88, new ESSpawn(88, GraciaSeeds.DESTRUCTION, new Location(-245064,208456,-12512), new int[]{18679,18680,18681}));
		_spawns.put(89, new ESSpawn(89, GraciaSeeds.DESTRUCTION, new Location(-246696,208456,-12512), new int[]{18679,18680,18681}));
		_spawns.put(90, new ESSpawn(90, GraciaSeeds.DESTRUCTION, new Location(-246712,209544,-12512), new int[]{18679,18680,18681}));
		
		_spawns.put(91, new ESSpawn(91, GraciaSeeds.DESTRUCTION, new Location(-245048,207816,-12512), new int[]{18682,18683}));
		_spawns.put(92, new ESSpawn(92, GraciaSeeds.DESTRUCTION, new Location(-245048,207288,-12512), new int[]{18682,18683}));
		_spawns.put(93, new ESSpawn(93, GraciaSeeds.DESTRUCTION, new Location(-246696,207304,-12512), new int[]{18682,18683}));
		_spawns.put(94, new ESSpawn(94, GraciaSeeds.DESTRUCTION, new Location(-246712,207816,-12512), new int[]{18682,18683}));
		
		_spawns.put(95, new ESSpawn(95, GraciaSeeds.DESTRUCTION, new Location(-244328,207272,-12768), new int[]{18678,18679,18680}));
		_spawns.put(96, new ESSpawn(96, GraciaSeeds.DESTRUCTION, new Location(-243256,207256,-12768), new int[]{18678,18679,18680}));
		_spawns.put(97, new ESSpawn(97, GraciaSeeds.DESTRUCTION, new Location(-243256,205624,-12768), new int[]{18678,18679,18680}));
		_spawns.put(98, new ESSpawn(98, GraciaSeeds.DESTRUCTION, new Location(-244328,205608,-12768), new int[]{18678,18679,18680}));
		
		_spawns.put(99, new ESSpawn(99, GraciaSeeds.DESTRUCTION, new Location(-242616,207272,-12768), new int[]{18681,18682}));
		_spawns.put(100, new ESSpawn(100, GraciaSeeds.DESTRUCTION, new Location(-242104,207272,-12768), new int[]{18681,18682}));
		_spawns.put(101, new ESSpawn(101, GraciaSeeds.DESTRUCTION, new Location(-242088,205624,-12768), new int[]{18681,18682}));
		_spawns.put(102, new ESSpawn(102, GraciaSeeds.DESTRUCTION, new Location(-242600,205608,-12768), new int[]{18681,18682}));
		
		// Seed of Annihilation
		_spawns.put(103, new ESSpawn(103, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184519,183007,-10456), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(104, new ESSpawn(104, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184873,181445,-10488), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(105, new ESSpawn(105, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184009,180962,-10488), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(106, new ESSpawn(106, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-185321,181641,-10448), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(107, new ESSpawn(107, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184035,182775,-10512), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(108, new ESSpawn(108, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-185433,181935,-10424), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(109, new ESSpawn(109, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183309,183007,-10560), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(110, new ESSpawn(110, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184929,181886,-10488), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(111, new ESSpawn(111, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184009,180392,-10424), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(112, new ESSpawn(112, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183793,183239,-10488), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(113, new ESSpawn(113, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184245,180848,-10464), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(114, new ESSpawn(114, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-182704,183761,-10528), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(115, new ESSpawn(115, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184705,181886,-10504), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(116, new ESSpawn(116, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184304,181076,-10488), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(117, new ESSpawn(117, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183596,180430,-10424), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(118, new ESSpawn(118, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184422,181038,-10480), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(119, new ESSpawn(119, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184929,181543,-10496), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(120, new ESSpawn(120, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184398,182891,-10472), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(121, new ESSpawn(121, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-177606,182848,-10584), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(122, new ESSpawn(122, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-178104,183224,-10560), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(123, new ESSpawn(123, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-177274,182284,-10600), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(124, new ESSpawn(124, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-177772,183224,-10560), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(125, new ESSpawn(125, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-181532,180364,-10504), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(126, new ESSpawn(126, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-181802,180276,-10496), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(127, new ESSpawn(127, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-178429,180444,-10512), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(128, new ESSpawn(128, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-177606,182190,-10600), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(129, new ESSpawn(129, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-177357,181908,-10576), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(130, new ESSpawn(130, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-178747,179534,-10408), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(131, new ESSpawn(131, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-178429,179534,-10392), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(132, new ESSpawn(132, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-178853,180094,-10472), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(133, new ESSpawn(133, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-181937,179660,-10416), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(134, new ESSpawn(134, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-180992,179572,-10416), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(135, new ESSpawn(135, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-185552,179252,-10368), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(136, new ESSpawn(136, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184572,178913,-10400), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(137, new ESSpawn(137, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184768,178348,-10312), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(138, new ESSpawn(138, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-184572,178574,-10352), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(139, new ESSpawn(139, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-185062,178913,-10384), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(140, new ESSpawn(140, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-181397,179484,-10416), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(141, new ESSpawn(141, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-181667,179044,-10408), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(142, new ESSpawn(142, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-185258,177896,-10240), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(143, new ESSpawn(143, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183506,176570,-10280), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(144, new ESSpawn(144, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183719,176804,-10240), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(145, new ESSpawn(145, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183648,177116,-10240), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(146, new ESSpawn(146, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183932,176492,-10240), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(147, new ESSpawn(147, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183861,176570,-10240), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(148, new ESSpawn(148, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-183790,175946,-10240), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(149, new ESSpawn(149, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-178641,179604,-10416), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(150, new ESSpawn(150, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-178959,179814,-10432), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(151, new ESSpawn(151, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-176367,178456,-10376), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(152, new ESSpawn(152, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-175845,177172,-10264), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(153, new ESSpawn(153, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-175323,177600,-10248), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(154, new ESSpawn(154, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-174975,177172,-10216), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(155, new ESSpawn(155, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-176019,178242,-10352), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(156, new ESSpawn(156, GraciaSeeds.ANNIHILATION_BISTAKON, new Location(-174801,178456,-10264), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		
		_spawns.put(157, new ESSpawn(157, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185648,183384,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(158, new ESSpawn(158, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-186740,180908,-15528), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(159, new ESSpawn(159, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185297,184658,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(160, new ESSpawn(160, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185697,181601,-15488), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(161, new ESSpawn(161, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-186684,182744,-15536), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(162, new ESSpawn(162, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184908,183384,-15616), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(163, new ESSpawn(163, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184994,185572,-15784), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(164, new ESSpawn(164, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185796,182616,-15608), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(165, new ESSpawn(165, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184970,184385,-15648), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(166, new ESSpawn(166, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185995,180809,-15512), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(167, new ESSpawn(167, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185352,182872,-15632), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(168, new ESSpawn(168, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185624,184294,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(169, new ESSpawn(169, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184486,185774,-15816), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(170, new ESSpawn(170, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-186496,184112,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(171, new ESSpawn(171, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184232,185976,-15816), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(172, new ESSpawn(172, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184994,185673,-15792), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(173, new ESSpawn(173, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185733,184203,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(174, new ESSpawn(174, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185079,184294,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(175, new ESSpawn(175, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184803,180710,-15528), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(176, new ESSpawn(176, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-186293,180413,-15528), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(177, new ESSpawn(177, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185352,182936,-15632), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(178, new ESSpawn(178, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184356,180611,-15496), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(179, new ESSpawn(179, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185375,186784,-15816), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(180, new ESSpawn(180, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184867,186784,-15816), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(181, new ESSpawn(181, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180553,180454,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(182, new ESSpawn(182, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180422,180454,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(183, new ESSpawn(183, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-181863,181138,-15120), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(184, new ESSpawn(184, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-181732,180454,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(185, new ESSpawn(185, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180684,180397,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(186, new ESSpawn(186, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-182256,180682,-15112), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(187, new ESSpawn(187, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185492,179492,-15392), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(188, new ESSpawn(188, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185894,178538,-15336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(189, new ESSpawn(189, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-186028,178856,-15336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(190, new ESSpawn(190, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185224,179068,-15336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(191, new ESSpawn(191, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185492,178538,-15336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(192, new ESSpawn(192, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185894,178538,-15336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(193, new ESSpawn(193, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180619,178855,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(194, new ESSpawn(194, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180255,177892,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(195, new ESSpawn(195, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-185804,176472,-15336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(196, new ESSpawn(196, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184580,176370,-15320), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(197, new ESSpawn(197, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184308,176166,-15320), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(198, new ESSpawn(198, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-183764,177186,-15304), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(199, new ESSpawn(199, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180801,177571,-15144), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(200, new ESSpawn(200, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184716,176064,-15320), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(201, new ESSpawn(201, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-184444,175452,-15296), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(202, new ESSpawn(202, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180164,177464,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(203, new ESSpawn(203, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-180164,178213,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(204, new ESSpawn(204, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-179982,178320,-15152), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(205, new ESSpawn(205, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-176925,177757,-15824), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(206, new ESSpawn(206, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-176164,179282,-15720), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(207, new ESSpawn(207, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175692,177613,-15800), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(208, new ESSpawn(208, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175418,178117,-15824), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(209, new ESSpawn(209, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-176103,177829,-15824), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(210, new ESSpawn(210, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175966,177325,-15792), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(211, new ESSpawn(211, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-174778,179732,-15664), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(212, new ESSpawn(212, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175692,178261,-15824), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(213, new ESSpawn(213, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-176038,179192,-15736), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(214, new ESSpawn(214, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175660,179462,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(215, new ESSpawn(215, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175912,179732,-15664), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(216, new ESSpawn(216, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175156,180182,-15680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(217, new ESSpawn(217, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-174240,182059,-15664), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(218, new ESSpawn(218, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-175590,181478,-15640), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(219, new ESSpawn(219, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-174510,181561,-15616), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(220, new ESSpawn(220, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-174240,182391,-15688), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(221, new ESSpawn(221, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-174105,182806,-15672), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(222, new ESSpawn(222, GraciaSeeds.ANNIHILATION_REPTILIKON, new Location(-174645,182806,-15712), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		
		_spawns.put(223, new ESSpawn(223, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-214962,182403,-10992), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(224, new ESSpawn(224, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-215019,182493,-11000), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(225, new ESSpawn(225, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-211374,180793,-11672), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(226, new ESSpawn(226, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-211198,180661,-11680), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(227, new ESSpawn(227, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-213097,178936,-12720), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(228, new ESSpawn(228, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-213517,178936,-12712), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(229, new ESSpawn(229, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-214105,179191,-12720), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(230, new ESSpawn(230, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-213769,179446,-12720), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(231, new ESSpawn(231, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-214021,179344,-12720), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(232, new ESSpawn(232, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-210582,180595,-11672), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(233, new ESSpawn(233, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-210934,180661,-11696), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(234, new ESSpawn(234, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207058,178460,-12656), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(235, new ESSpawn(235, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207454,179151,-11368), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(236, new ESSpawn(236, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207422,181365,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(237, new ESSpawn(237, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207358,180627,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(238, new ESSpawn(238, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207230,180996,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(239, new ESSpawn(239, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-208515,184160,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(240, new ESSpawn(240, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207613,184000,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(241, new ESSpawn(241, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-208597,183760,-11352), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(242, new ESSpawn(242, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206710,176142,-12656), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(243, new ESSpawn(243, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206361,178136,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(244, new ESSpawn(244, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206178,178630,-12672), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(245, new ESSpawn(245, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-205738,178715,-12656), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(246, new ESSpawn(246, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206442,178205,-12648), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(247, new ESSpawn(247, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206585,178874,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(248, new ESSpawn(248, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206073,179366,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(249, new ESSpawn(249, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206009,178628,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(250, new ESSpawn(250, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206155,181301,-12656), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(251, new ESSpawn(251, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206595,181641,-12656), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(252, new ESSpawn(252, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206507,181641,-12656), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(253, new ESSpawn(253, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206507,181471,-12640), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(254, new ESSpawn(254, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206974,175972,-12672), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(255, new ESSpawn(255, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206304,175130,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(256, new ESSpawn(256, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206886,175802,-12672), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(257, new ESSpawn(257, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207238,175972,-12672), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(258, new ESSpawn(258, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206386,174857,-11328), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(259, new ESSpawn(259, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-206386,175039,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(260, new ESSpawn(260, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-205976,174584,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(261, new ESSpawn(261, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-207367,184320,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(262, new ESSpawn(262, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219002,180419,-12608), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(263, new ESSpawn(263, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218853,182790,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(264, new ESSpawn(264, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218853,183343,-12600), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(265, new ESSpawn(265, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218358,186247,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(266, new ESSpawn(266, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218358,186083,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(267, new ESSpawn(267, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-217574,185796,-11352), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(268, new ESSpawn(268, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219178,181051,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(269, new ESSpawn(269, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-220171,180313,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(270, new ESSpawn(270, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219293,183738,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(271, new ESSpawn(271, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219381,182553,-12584), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(272, new ESSpawn(272, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219600,183024,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(273, new ESSpawn(273, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219940,182680,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(274, new ESSpawn(274, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219260,183884,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(275, new ESSpawn(275, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219855,183540,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(276, new ESSpawn(276, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218946,186575,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(277, new ESSpawn(277, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219882,180103,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(278, new ESSpawn(278, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219266,179787,-12584), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(279, new ESSpawn(279, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219201,178337,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(280, new ESSpawn(280, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219716,179875,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(281, new ESSpawn(281, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219716,180021,-11328), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(282, new ESSpawn(282, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219989,179437,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(283, new ESSpawn(283, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219078,178298,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(284, new ESSpawn(284, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218684,178954,-11328), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(285, new ESSpawn(285, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219089,178456,-11328), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(286, new ESSpawn(286, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-220266,177623,-12608), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(287, new ESSpawn(287, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219201,178025,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(288, new ESSpawn(288, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219142,177044,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(289, new ESSpawn(289, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219690,177895,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(290, new ESSpawn(290, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219754,177623,-12584), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(291, new ESSpawn(291, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218791,177830,-12584), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(292, new ESSpawn(292, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218904,176219,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(293, new ESSpawn(293, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218768,176384,-12584), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(294, new ESSpawn(294, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218774,177626,-11320), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(295, new ESSpawn(295, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218774,177792,-11328), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(296, new ESSpawn(296, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219880,175901,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(297, new ESSpawn(297, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219210,176054,-12592), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(298, new ESSpawn(298, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219850,175991,-12608), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(299, new ESSpawn(299, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-219079,175021,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(300, new ESSpawn(300, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218812,174229,-11344), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		_spawns.put(301, new ESSpawn(301, GraciaSeeds.ANNIHILATION_COKRAKON, new Location(-218723,174669,-11336), new int[]{ 18678, 18679, 18680, 18681, 18682, 18683 }));
		//@formatter:on
	}
	
	private class ESSpawn
	{
		protected final int _spawnId;
		protected final GraciaSeeds _seedId;
		protected final int[] _npcIds;
		protected final Location _loc;
		
		public ESSpawn(int spawnId, GraciaSeeds seedId, Location loc, int[] npcIds)
		{
			_spawnId = spawnId;
			_seedId = seedId;
			_loc = loc;
			_npcIds = npcIds;
		}
		
		public void scheduleRespawn(long waitTime)
		{
			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				@Override
				public void run()
				{
					// if the AI is inactive, do not spawn the NPC
					if (isSeedActive(_seedId))
					{
						// get a random NPC that should spawn at this location
						Integer spawnId = _spawnId; // the map uses "Integer", not "int"
						_spawnedNpcs.put(addSpawn(_npcIds[getRandom(_npcIds.length)], _loc, false, 0), spawnId);
					}
				}
			}, waitTime);
		}
	}
	
	public static void main(String[] args)
	{
		new EnergySeeds();
	}
}