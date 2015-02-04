/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package instances.IceQueensCastleNormalBattle;

import instances.AbstractInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import quests.Q10286_ReunionWithSirra.Q10286_ReunionWithSirra;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.MountType;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2CommandChannel;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.PcCondOverride;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2GrandBossInstance;
import com.l2jserver.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2QuestGuardInstance;
import com.l2jserver.gameserver.model.actor.instance.L2RaidBossInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.variables.NpcVariables;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.ExChangeClientEffectInfo;
import com.l2jserver.gameserver.network.serverpackets.ExSendUIEvent;
import com.l2jserver.gameserver.network.serverpackets.OnEventTrigger;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.taskmanager.DecayTaskManager;
import com.l2jserver.gameserver.util.Util;

/**
 * Ice Queen's Castle (Normal Battle) instance zone.
 * @author St3eT
 */
public final class IceQueensCastleNormalBattle extends AbstractInstance
{
	protected class IQCNBWorld extends InstanceWorld
	{
		protected List<L2PcInstance> playersInside = new ArrayList<>();
		protected List<L2Npc> knightStatues = new ArrayList<>();
		protected List<L2Attackable> spawnedMobs = new CopyOnWriteArrayList<>();
		protected L2NpcInstance controller = null;
		protected L2GrandBossInstance freya = null;
		protected L2QuestGuardInstance supp_Jinia = null;
		protected L2QuestGuardInstance supp_Kegor = null;
		protected boolean isSupportActive = false;
		protected boolean canSpawnMobs = true;
		protected boolean isHardCore = false;
	}
	
	// Npcs
	private static final int FREYA_THRONE = 29177; // First freya
	private static final int FREYA_SPELLING = 29178; // Second freya
	private static final int FREYA_STAND_EASY = 29179; // Last freya - Easy mode
	private static final int FREYA_STAND_HARD = 29180; // Last freya - Hardcore mode
	private static final int INVISIBLE_NPC = 18919;
	private static final int KNIGHT_EASY = 18855; // Archery Knight - Easy mode
	private static final int KNIGHT_HARD = 18856; // Archery Knight - Hardcore mode
	private static final int GLACIER = 18853; // Glacier
	private static final int BREATH = 18854; // Archer's Breath
	private static final int GLAKIAS_EASY = 25699; // Glakias (Archery Knight Captain) - Easy mode
	private static final int GLAKIAS_HARD = 25700; // Glakias (Archery Knight Captain) - Hardcore mode
	private static final int SIRRA = 32762; // Sirra
	private static final int JINIA = 32781; // Jinia
	private static final int SUPP_JINIA = 18850; // Jinia
	private static final int SUPP_KEGOR = 18851; // Kegor
	// Skills
	private static final SkillHolder BLIZZARD_EASY = new SkillHolder(6274, 1); // Eternal Blizzard
	private static final SkillHolder BLIZZARD_HARD = new SkillHolder(6275, 1); // Eternal Blizzard
	private static final SkillHolder BLIZZARD_FORCE = new SkillHolder(6697, 1); // Eternal Blizzard
	private static final SkillHolder BLIZZARD_BREATH = new SkillHolder(6299, 1); // Breath of Ice Palace - Ice Storm
	private static final SkillHolder SUICIDE_BREATH = new SkillHolder(6300, 1); // Self-Destruction
	private static final SkillHolder JINIA_SUPPORT = new SkillHolder(6288, 1); // Jinia's Prayer
	private static final SkillHolder KEGOR_SUPPORT = new SkillHolder(6289, 1); // Kegor's Courage
	private static final SkillHolder ICE_STONE = new SkillHolder(6301, 1); // Cold Mana's Fragment
	private static final SkillHolder CANCEL = new SkillHolder(4618, 1); // NPC Cancel PC Target
	private static final SkillHolder POWER_STRIKE = new SkillHolder(6293, 1); // Power Strike
	private static final SkillHolder POINT_TARGET = new SkillHolder(6295, 1); // Point Target
	private static final SkillHolder CYLINDER_THROW = new SkillHolder(6297, 1); // Cylinder Throw
	private static final SkillHolder SELF_RANGE_BUFF = new SkillHolder(6294, 1); // Leader's Roar
	private static final SkillHolder LEADER_RUSH = new SkillHolder(6296, 1); // Rush
	private static final SkillHolder ANTI_STRIDER = new SkillHolder(4258, 1); // Hinder Strider
	private static final SkillHolder ICE_BALL = new SkillHolder(6278, 1); // Ice Ball
	private static final SkillHolder SUMMON_ELEMENTAL = new SkillHolder(6277, 1); // Summon Spirits
	private static final SkillHolder SELF_NOVA = new SkillHolder(6279, 1); // Attack Nearby Range
	private static final SkillHolder REFLECT_MAGIC = new SkillHolder(6282, 1); // Reflect Magic
	private static final SkillHolder FREYA_ANGER = new SkillHolder(6285, 1); // Rage of Ice
	private static final SkillHolder FREYA_BUFF = new SkillHolder(6284, 1); // Freya's Bless
	// Locations
	private static final Location FREYA_SPAWN = new Location(114720, -117085, -11088, 15956);
	private static final Location FREYA_SPELLING_SPAWN = new Location(114723, -117502, -10672, 15956);
	private static final Location FREYA_CORPSE = new Location(114767, -114795, -11200, 0);
	private static final Location MIDDLE_POINT = new Location(114730, -114805, -11200);
	private static final Location KEGOR_FINISH = new Location(114659, -114796, -11205);
	private static final Location GLAKIAS_SPAWN = new Location(114707, -114799, -11199, 15956);
	private static final Location SUPP_JINIA_SPAWN = new Location(114751, -114781, -11205);
	private static final Location SUPP_KEGOR_SPAWN = new Location(114659, -114796, -11205);
	private static final Location BATTLE_PORT = new Location(114694, -113700, -11200);
	private static final Location CONTROLLER_LOC = new Location(114394, -112383, -11200);
	private static final Location[] ENTER_LOC =
	{
		new Location(114185, -112435, -11210),
		new Location(114183, -112280, -11210),
		new Location(114024, -112435, -11210),
		new Location(114024, -112278, -11210),
		new Location(113865, -112435, -11210),
		new Location(113865, -112276, -11210),
	
	};
	private static final Location[] STATUES_LOC =
	{
		new Location(113845, -116091, -11168, 8264),
		new Location(113381, -115622, -11168, 8264),
		new Location(113380, -113978, -11168, -8224),
		new Location(113845, -113518, -11168, -8224),
		new Location(115591, -113516, -11168, -24504),
		new Location(116053, -113981, -11168, -24504),
		new Location(116061, -115611, -11168, 24804),
		new Location(115597, -116080, -11168, 24804),
		new Location(112942, -115480, -10960, 52),
		new Location(112940, -115146, -10960, 52),
		new Location(112945, -114453, -10960, 52),
		new Location(112945, -114123, -10960, 52),
		new Location(116497, -114117, -10960, 32724),
		new Location(116499, -114454, -10960, 32724),
		new Location(116501, -115145, -10960, 32724),
		new Location(116502, -115473, -10960, 32724),
	};
	private static Location[] KNIGHTS_LOC =
	{
		new Location(114502, -115315, -11205, 15451),
		new Location(114937, -115323, -11205, 18106),
		new Location(114722, -115185, -11205, 16437),
	};
	// Misc
	private static final int MAX_PLAYERS = 27;
	private static final int MIN_PLAYERS = 10;
	private static final int MIN_LEVEL = 82;
	private static final int TEMPLATE_ID_EASY = 139; // Ice Queen's Castle
	private static final int TEMPLATE_ID_HARD = 144; // Ice Queen's Castle (Epic)
	private static final int DOOR_ID = 23140101;
	private static int[] EMMITERS =
	{
		23140202,
		23140204,
		23140206,
		23140208,
		23140212,
		23140214,
		23140216,
	};
	
	public IceQueensCastleNormalBattle()
	{
		super(IceQueensCastleNormalBattle.class.getSimpleName());
		addStartNpc(SIRRA, SUPP_KEGOR, SUPP_JINIA);
		addFirstTalkId(SUPP_KEGOR, SUPP_JINIA);
		addTalkId(SIRRA, JINIA, SUPP_KEGOR);
		addAttackId(FREYA_THRONE, FREYA_STAND_EASY, FREYA_STAND_HARD, GLAKIAS_EASY, GLAKIAS_HARD, GLACIER, BREATH, KNIGHT_EASY, KNIGHT_HARD);
		addKillId(GLAKIAS_EASY, GLAKIAS_HARD, FREYA_STAND_EASY, FREYA_STAND_HARD, KNIGHT_EASY, KNIGHT_HARD, GLACIER, BREATH);
		addSpellFinishedId(GLACIER, BREATH);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("enterEasy"))
		{
			enterInstance(player, new IQCNBWorld(), "IceQueensCastleNormalBattleEasy.xml", TEMPLATE_ID_EASY);
		}
		else if (event.equals("enterHardcore"))
		{
			enterInstance(player, new IQCNBWorld(), "IceQueensCastleNormalBattleHardcore.xml", TEMPLATE_ID_HARD);
		}
		else
		{
			final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
			
			if ((tmpworld != null) && (tmpworld instanceof IQCNBWorld))
			{
				final IQCNBWorld world = (IQCNBWorld) tmpworld;
				switch (event)
				{
					case "openDoor":
					{
						if (npc.isScriptValue(0))
						{
							npc.setScriptValue(1);
							openDoor(DOOR_ID, world.getInstanceId());
							world.controller = (L2NpcInstance) addSpawn(INVISIBLE_NPC, CONTROLLER_LOC, false, 0, true, world.getInstanceId());
							for (Location loc : STATUES_LOC)
							{
								if (loc.getZ() == -11168)
								{
									final L2Npc statue = addSpawn(INVISIBLE_NPC, loc, false, 0, false, world.getInstanceId());
									world.knightStatues.add(statue);
								}
							}
							
							if (!world.isHardCore)
							{
								for (L2PcInstance players : world.playersInside)
								{
									if ((players != null) && !players.isDead() && (players.getInstanceId() == world.getInstanceId()))
									{
										final QuestState qs = player.getQuestState(Q10286_ReunionWithSirra.class.getSimpleName());
										if ((qs != null) && (qs.getState() == State.STARTED) && qs.isCond(5))
										{
											qs.setCond(6, true);
										}
									}
								}
							}
							startQuestTimer("STAGE_1_MOVIE", 60000, world.controller, null);
						}
						break;
					}
					case "portInside":
					{
						teleportPlayer(player, BATTLE_PORT, world.getInstanceId());
						break;
					}
					case "killFreya":
					{
						final QuestState qs = player.getQuestState(Q10286_ReunionWithSirra.class.getSimpleName());
						if ((qs != null) && (qs.getState() == State.STARTED) && qs.isCond(6))
						{
							qs.setMemoState(10);
							qs.setCond(7, true);
						}
						world.supp_Kegor.deleteMe();
						world.freya.decayMe();
						manageMovie(world, 20);
						cancelQuestTimer("FINISH_WORLD", world.controller, null);
						startQuestTimer("FINISH_WORLD", 58500, world.controller, null);
						break;
					}
					case "18851-01.html":
					{
						return event;
					}
					case "STAGE_1_MOVIE":
					{
						closeDoor(DOOR_ID, world.getInstanceId());
						world.setStatus(1);
						manageMovie(world, 15);
						startQuestTimer("STAGE_1_START", 53500, world.controller, null);
						break;
					}
					case "STAGE_1_START":
					{
						world.freya = (L2GrandBossInstance) addSpawn(FREYA_THRONE, FREYA_SPAWN, false, 0, true, world.getInstanceId());
						world.freya.setIsMortal(false);
						manageScreenMsg(world, NpcStringId.BEGIN_STAGE_1);
						startQuestTimer("CAST_BLIZZARD", 50000, world.controller, null);
						startQuestTimer("STAGE_1_SPAWN", 2000, world.freya, null);
						break;
					}
					case "STAGE_1_SPAWN":
					{
						notifyEvent("START_SPAWN", world.controller, null);
						break;
					}
					case "STAGE_1_FINISH":
					{
						world.freya.deleteMe();
						world.freya = null;
						manageDespawnMinions(world);
						manageMovie(world, 16);
						startQuestTimer("STAGE_1_PAUSE", 24100 - 1000, world.controller, null);
						break;
					}
					case "STAGE_1_PAUSE":
					{
						world.freya = (L2GrandBossInstance) addSpawn(FREYA_SPELLING, FREYA_SPELLING_SPAWN, false, 0, true, world.getInstanceId());
						world.freya.setIsInvul(true);
						world.freya.disableCoreAI(true);
						manageTimer(world, 60, NpcStringId.TIME_REMAINING_UNTIL_NEXT_BATTLE);
						world.setStatus(2);
						startQuestTimer("STAGE_2_START", 60000, world.controller, null);
						break;
					}
					case "STAGE_2_START":
					{
						world.canSpawnMobs = true;
						notifyEvent("START_SPAWN", world.controller, null);
						manageScreenMsg(world, NpcStringId.BEGIN_STAGE_2);
						
						if (world.isHardCore)
						{
							startQuestTimer("STAGE_2_FAILED", 360000, world.controller, null);
							manageTimer(world, 360, NpcStringId.BATTLE_END_LIMIT_TIME);
							world.controller.getVariables().set("TIMER_END", System.currentTimeMillis() + 360000);
						}
						break;
					}
					case "STAGE_2_MOVIE":
					{
						manageMovie(world, 23);
						startQuestTimer("STAGE_2_GLAKIAS", 7000, world.controller, null);
						break;
					}
					case "STAGE_2_GLAKIAS":
					{
						for (Location loc : STATUES_LOC)
						{
							if (loc.getZ() == -10960)
							{
								final L2Npc statue = addSpawn(INVISIBLE_NPC, loc, false, 0, false, world.getInstanceId());
								world.knightStatues.add(statue);
								startQuestTimer("SPAWN_KNIGHT", 5000, statue, null);
							}
						}
						
						final L2RaidBossInstance glakias = (L2RaidBossInstance) addSpawn((world.isHardCore ? GLAKIAS_HARD : GLAKIAS_EASY), GLAKIAS_SPAWN, false, 0, true, world.getInstanceId());
						startQuestTimer("LEADER_DELAY", 5000, glakias, null);
						
						if (world.isHardCore)
						{
							startQuestTimer("SHOW_GLAKIAS_TIMER", 3000, world.controller, null);
						}
						break;
					}
					case "STAGE_2_FAILED":
					{
						manageMovie(world, 22);
						startQuestTimer("STAGE_2_FAILED2", 22000, npc, null);
						break;
					}
					case "STAGE_2_FAILED2":
					{
						InstanceManager.getInstance().destroyInstance(world.getInstanceId());
						break;
					}
					case "STAGE_3_MOVIE":
					{
						manageMovie(world, 17);
						startQuestTimer("STAGE_3_START", 21500, world.controller, null);
						break;
					}
					case "STAGE_3_START":
					{
						for (L2PcInstance players : world.playersInside)
						{
							if (players != null)
							{
								players.broadcastPacket(ExChangeClientEffectInfo.STATIC_FREYA_DESTROYED);
								
								for (int emmiterId : EMMITERS)
								{
									players.sendPacket(new OnEventTrigger(emmiterId, true));
								}
							}
						}
						world.setStatus(4);
						world.freya.deleteMe();
						world.canSpawnMobs = true;
						world.freya = (L2GrandBossInstance) addSpawn((world.isHardCore ? FREYA_STAND_HARD : FREYA_STAND_EASY), FREYA_SPAWN, false, 0, true, world.getInstanceId());
						world.controller.getVariables().set("FREYA_MOVE", 0);
						notifyEvent("START_SPAWN", world.controller, null);
						startQuestTimer("START_MOVE", 10000, world.controller, null);
						startQuestTimer("CAST_BLIZZARD", 50000, world.controller, null);
						manageScreenMsg(world, NpcStringId.BEGIN_STAGE_3);
						
						if (world.isHardCore)
						{
							world.freya.doCast(FREYA_ANGER.getSkill());
							startQuestTimer("FREYA_BUFF", 15000, world.controller, null);
						}
						break;
					}
					case "FREYA_BUFF":
					{
						world.freya.doCast(FREYA_BUFF.getSkill());
						startQuestTimer("FREYA_BUFF", 15000, world.controller, null);
						break;
					}
					case "START_MOVE":
					{
						if (npc.getVariables().getInt("FREYA_MOVE") == 0)
						{
							world.controller.getVariables().set("FREYA_MOVE", 1);
							world.freya.setIsRunning(true);
							world.freya.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, MIDDLE_POINT);
						}
						break;
					}
					case "CAST_BLIZZARD":
					{
						if (!world.freya.isInvul())
						{
							final int manaBurnUse = world.controller.getVariables().getInt("MANA_BURN_USE", 0);
							if (world.isHardCore && (manaBurnUse < 4) && (world.freya.getCurrentHp() < (world.freya.getMaxHp() * (0.8 - (0.2 * manaBurnUse)))))
							{
								world.controller.getVariables().set("MANA_BURN_USE", manaBurnUse + 1);
								world.freya.doCast(BLIZZARD_FORCE.getSkill());
								startQuestTimer("MANA_BURN", 7000, world.controller, null);
								manageScreenMsg(world, NpcStringId.MAGIC_POWER_SO_STRONG_THAT_IT_COULD_MAKE_YOU_LOSE_YOUR_MIND_CAN_BE_FELT_FROM_SOMEWHERE);
							}
							else
							{
								final Skill skill = (world.isHardCore ? BLIZZARD_HARD.getSkill() : BLIZZARD_EASY.getSkill());
								world.freya.doCast(skill);
								manageScreenMsg(world, NpcStringId.STRONG_MAGIC_POWER_CAN_BE_FELT_FROM_SOMEWHERE);
							}
						}
						
						final int time = (world.isHardCore ? getRandom(35, 40) : getRandom(55, 60)) * 1000;
						startQuestTimer("CAST_BLIZZARD", time, world.controller, null);
						
						for (L2Attackable minion : world.spawnedMobs)
						{
							if ((minion != null) && !minion.isDead() && !minion.isInCombat())
							{
								manageRandomAttack(world, minion);
							}
						}
						break;
					}
					case "SPAWN_SUPPORT":
					{
						for (L2PcInstance players : world.playersInside)
						{
							if ((players != null) && (players.getInstanceId() == world.getInstanceId()))
							{
								players.setIsInvul(false);
							}
						}
						world.freya.setIsInvul(false);
						world.freya.disableCoreAI(false);
						manageScreenMsg(world, NpcStringId.BEGIN_STAGE_4);
						world.supp_Jinia = (L2QuestGuardInstance) addSpawn(SUPP_JINIA, SUPP_JINIA_SPAWN, false, 0, true, world.getInstanceId());
						world.supp_Jinia.setIsRunning(true);
						world.supp_Jinia.setIsInvul(true);
						world.supp_Jinia.setCanReturnToSpawnPoint(false);
						world.supp_Jinia.reduceCurrentHp(1, world.freya, null); // TODO: Find better way for attack
						world.freya.reduceCurrentHp(1, world.supp_Jinia, null);
						world.supp_Kegor = (L2QuestGuardInstance) addSpawn(SUPP_KEGOR, SUPP_KEGOR_SPAWN, false, 0, true, world.getInstanceId());
						world.supp_Kegor.setIsRunning(true);
						world.supp_Kegor.setIsInvul(true);
						world.supp_Kegor.setCanReturnToSpawnPoint(false);
						world.supp_Kegor.reduceCurrentHp(1, world.freya, null); // TODO: Find better way for attack
						world.freya.reduceCurrentHp(1, world.supp_Kegor, null);
						startQuestTimer("GIVE_SUPPORT", 1000, world.controller, null);
						break;
					}
					case "GIVE_SUPPORT":
					{
						if (world.isSupportActive)
						{
							world.supp_Jinia.doCast(JINIA_SUPPORT.getSkill());
							world.supp_Kegor.doCast(KEGOR_SUPPORT.getSkill());
							startQuestTimer("GIVE_SUPPORT", 25000, world.controller, null);
						}
						break;
					}
					case "FINISH_STAGE":
					{
						world.supp_Jinia.deleteMe();
						world.supp_Jinia = null;
						world.freya.teleToLocation(FREYA_CORPSE);
						world.supp_Kegor.teleToLocation(KEGOR_FINISH);
						break;
					}
					case "START_SPAWN":
					{
						for (L2Npc statues : world.knightStatues)
						{
							notifyEvent("SPAWN_KNIGHT", statues, null);
						}
						
						for (Location loc : KNIGHTS_LOC)
						{
							final L2Attackable knight = (L2Attackable) addSpawn((world.isHardCore ? KNIGHT_HARD : KNIGHT_EASY), loc, false, 0, false, world.getInstanceId());
							knight.disableCoreAI(true);
							knight.setState(1);
							knight.getSpawn().setLocation(loc);
							world.spawnedMobs.add(knight);
							startQuestTimer("ICE_RUPTURE", getRandom(2, 5) * 1000, knight, null);
						}
						
						for (int i = 0; i < world.getStatus(); i++)
						{
							notifyEvent("SPAWN_GLACIER", world.controller, null);
						}
						break;
					}
					case "SPAWN_KNIGHT":
					{
						if (world.canSpawnMobs)
						{
							final Location loc = new Location(MIDDLE_POINT.getX() + getRandom(-1000, 1000), MIDDLE_POINT.getY() + getRandom(-1000, 1000), MIDDLE_POINT.getZ());
							final L2Attackable knight = (L2Attackable) addSpawn(world.isHardCore ? KNIGHT_HARD : KNIGHT_EASY, npc.getLocation(), false, 0, false, world.getInstanceId());
							knight.getVariables().set("SPAWNED_NPC", npc);
							knight.disableCoreAI(true);
							knight.setIsImmobilized(true);
							knight.setState(1);
							knight.getSpawn().setLocation(loc);
							world.spawnedMobs.add(knight);
							
							final int time = (world.isHardCore ? getRandom(5, 10) : getRandom(15, 20)) * 1000;
							startQuestTimer("ICE_RUPTURE", time, knight, null);
						}
						break;
					}
					case "SPAWN_GLACIER":
					{
						if (world.canSpawnMobs)
						{
							final Location loc = new Location(MIDDLE_POINT.getX() + getRandom(-1000, 1000), MIDDLE_POINT.getY() + getRandom(-1000, 1000), MIDDLE_POINT.getZ());
							final L2Attackable glacier = (L2Attackable) addSpawn(GLACIER, loc, false, 0, false, world.getInstanceId());
							glacier.setState(1);
							glacier.disableCoreAI(true);
							glacier.setIsImmobilized(true);
							world.spawnedMobs.add(glacier);
							startQuestTimer("CHANGE_STATE", 1400, glacier, null);
						}
						break;
					}
					case "ICE_RUPTURE":
					{
						if (npc.isCoreAIDisabled())
						{
							npc.disableCoreAI(false);
							npc.setIsImmobilized(false);
							npc.setState(2);
							manageRandomAttack(world, (L2Attackable) npc);
						}
						break;
					}
					case "FIND_TARGET":
					{
						manageRandomAttack(world, (L2Attackable) npc);
						break;
					}
					case "CHANGE_STATE":
					{
						npc.setState(2);
						startQuestTimer("CAST_SKILL", 20000, npc, null);
						break;
					}
					case "CAST_SKILL":
					{
						if (npc.isScriptValue(0) && !npc.isDead())
						{
							npc.setTarget(npc);
							npc.doCast(ICE_STONE.getSkill());
							npc.setScriptValue(1);
						}
						break;
					}
					case "SUICIDE":
					{
						npc.setState(3);
						npc.setIsMortal(true);
						npc.doDie(null);
						break;
					}
					case "BLIZZARD":
					{
						npc.getVariables().set("SUICIDE_COUNT", npc.getVariables().getInt("SUICIDE_COUNT") + 1);
						
						if (npc.getVariables().getInt("SUICIDE_ON") == 0)
						{
							if (npc.getVariables().getInt("SUICIDE_COUNT") == 2)
							{
								startQuestTimer("ELEMENTAL_SUICIDE", 20000, npc, null);
							}
							else
							{
								if (npc.checkDoCastConditions(BLIZZARD_BREATH.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(npc);
									npc.doCast(BLIZZARD_BREATH.getSkill());
								}
								startQuestTimer("BLIZZARD", 20000, npc, null);
							}
						}
						break;
					}
					case "ELEMENTAL_SUICIDE":
					{
						npc.setTarget(npc);
						npc.doCast(SUICIDE_BREATH.getSkill());
						break;
					}
					case "ELEMENTAL_KILLED":
					{
						if (npc.getVariables().getInt("SUICIDE_ON") == 1)
						{
							npc.setTarget(npc);
							npc.doCast(SUICIDE_BREATH.getSkill());
						}
						break;
					}
					case "FINISH_WORLD":
					{
						if (world.freya != null)
						{
							world.freya.decayMe();
						}
						
						for (L2PcInstance players : world.playersInside)
						{
							if ((players != null))
							{
								players.broadcastPacket(ExChangeClientEffectInfo.STATIC_FREYA_DEFAULT);
							}
						}
						InstanceManager.getInstance().destroyInstance(world.getInstanceId());
						break;
					}
					case "LEADER_RANGEBUFF":
					{
						if (npc.checkDoCastConditions(SELF_RANGE_BUFF.getSkill()) && !npc.isCastingNow())
						{
							npc.setTarget(npc);
							npc.doCast(SELF_RANGE_BUFF.getSkill());
						}
						else
						{
							startQuestTimer("LEADER_RANGEBUFF", 30000, npc, null);
						}
						break;
					}
					case "LEADER_RANDOMIZE":
					{
						final L2Attackable mob = (L2Attackable) npc;
						mob.clearAggroList();
						
						for (L2Character characters : npc.getKnownList().getKnownPlayersInRadius(1000))
						{
							if ((characters != null))
							{
								mob.addDamageHate(characters, 0, getRandom(10000, 20000));
							}
						}
						startQuestTimer("LEADER_RANDOMIZE", 25000, npc, null);
						break;
					}
					case "LEADER_DASH":
					{
						final L2Character mostHated = ((L2Attackable) npc).getMostHated();
						if (getRandomBoolean() && !npc.isCastingNow() && (mostHated != null) && !mostHated.isDead() && (npc.calculateDistance(mostHated, true, false) < 1000))
						{
							npc.setTarget(mostHated);
							npc.doCast(LEADER_RUSH.getSkill());
						}
						startQuestTimer("LEADER_DASH", 10000, npc, null);
						break;
					}
					case "LEADER_DESTROY":
					{
						final L2Attackable mob = (L2Attackable) npc;
						if (npc.getVariables().getInt("OFF_SHOUT") == 0)
						{
							manageScreenMsg(world, NpcStringId.THE_SPACE_FEELS_LIKE_ITS_GRADUALLY_STARTING_TO_SHAKE);
							
							switch (getRandom(4))
							{
								case 0:
								{
									broadcastNpcSay(npc, Say2.SHOUT, NpcStringId.ARCHER_GIVE_YOUR_BREATH_FOR_THE_INTRUDER);
									break;
								}
								case 1:
								{
									broadcastNpcSay(npc, Say2.SHOUT, NpcStringId.MY_KNIGHTS_SHOW_YOUR_LOYALTY);
									break;
								}
								case 2:
								{
									broadcastNpcSay(npc, Say2.SHOUT, NpcStringId.I_CAN_TAKE_IT_NO_LONGER);
									break;
								}
								case 3:
								{
									broadcastNpcSay(npc, Say2.SHOUT, NpcStringId.ARCHER_HEED_MY_CALL);
									for (int i = 0; i < 3; i++)
									{
										final L2Attackable breath = (L2Attackable) addSpawn(BREATH, npc.getLocation(), true, 0, false, world.getInstanceId());
										breath.setIsRunning(true);
										breath.addDamageHate(mob.getMostHated(), 0, 999);
										breath.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, mob.getMostHated());
										startQuestTimer("BLIZZARD", 20000, breath, null);
										world.spawnedMobs.add(breath);
									}
									break;
								}
							}
						}
						break;
					}
					case "LEADER_DELAY":
					{
						if (npc.getVariables().getInt("DELAY_VAL") == 0)
						{
							npc.getVariables().set("DELAY_VAL", 1);
						}
						break;
					}
					case "SHOW_GLAKIAS_TIMER":
					{
						final int time = (int) ((world.controller.getVariables().getLong("TIMER_END", 0) - System.currentTimeMillis()) / 1000);
						manageTimer(world, time, NpcStringId.BATTLE_END_LIMIT_TIME);
						break;
					}
					case "MANA_BURN":
					{
						for (L2PcInstance temp : world.playersInside)
						{
							if ((temp != null) && (temp.getInstanceId() == world.getInstanceId()))
							{
								temp.setCurrentMp(0);
								temp.broadcastStatusUpdate();
							}
						}
						break;
					}
				}
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if (tmpworld instanceof IQCNBWorld)
		{
			final IQCNBWorld world = (IQCNBWorld) tmpworld;
			
			if (npc.getId() == SUPP_JINIA)
			{
				player.sendPacket(ActionFailed.STATIC_PACKET);
				return null;
			}
			else if (npc.getId() == SUPP_KEGOR)
			{
				if (world.isSupportActive)
				{
					player.sendPacket(ActionFailed.STATIC_PACKET);
					return null;
				}
				return "18851.html";
			}
		}
		player.sendPacket(ActionFailed.STATIC_PACKET);
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon, Skill skill)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if (tmpworld instanceof IQCNBWorld)
		{
			final IQCNBWorld world = (IQCNBWorld) tmpworld;
			switch (npc.getId())
			{
				case FREYA_THRONE:
				{
					if ((world.controller.getVariables().getInt("FREYA_MOVE") == 0) && world.isStatus(1))
					{
						world.controller.getVariables().set("FREYA_MOVE", 1);
						manageScreenMsg(world, NpcStringId.FREYA_HAS_STARTED_TO_MOVE);
						world.freya.setIsRunning(true);
						world.freya.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, MIDDLE_POINT);
					}
					
					if (npc.getCurrentHp() < (npc.getMaxHp() * 0.02))
					{
						notifyEvent("STAGE_1_FINISH", world.controller, null);
						cancelQuestTimer("CAST_BLIZZARD", world.controller, null);
					}
					else
					{
						if ((attacker.getMountType() == MountType.STRIDER) && !attacker.isAffectedBySkill(ANTI_STRIDER.getSkillId()) && !npc.isCastingNow())
						{
							if (!npc.isSkillDisabled(ANTI_STRIDER.getSkill()))
							{
								npc.setTarget(attacker);
								npc.doCast(ANTI_STRIDER.getSkill());
							}
						}
						
						final L2Character mostHated = ((L2Attackable) npc).getMostHated();
						final boolean canReachMostHated = (mostHated != null) && !mostHated.isDead() && (npc.calculateDistance(mostHated, true, false) <= 800);
						
						if (getRandom(10000) < 3333)
						{
							if (getRandomBoolean())
							{
								if ((npc.calculateDistance(attacker, true, false) <= 800) && npc.checkDoCastConditions(ICE_BALL.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(attacker);
									npc.doCast(ICE_BALL.getSkill());
								}
							}
							else
							{
								if (canReachMostHated && npc.checkDoCastConditions(ICE_BALL.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(mostHated);
									npc.doCast(ICE_BALL.getSkill());
								}
							}
						}
						else if (getRandom(10000) < 800)
						{
							if (getRandomBoolean())
							{
								if ((npc.calculateDistance(attacker, true, false) <= 800) && npc.checkDoCastConditions(SUMMON_ELEMENTAL.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(attacker);
									npc.doCast(SUMMON_ELEMENTAL.getSkill());
								}
							}
							else
							{
								if (canReachMostHated && npc.checkDoCastConditions(SUMMON_ELEMENTAL.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(mostHated);
									npc.doCast(SUMMON_ELEMENTAL.getSkill());
								}
							}
						}
						else if (getRandom(10000) < 1500)
						{
							if (!npc.isAffectedBySkill(SELF_NOVA.getSkillId()) && npc.checkDoCastConditions(SELF_NOVA.getSkill()) && !npc.isCastingNow())
							{
								npc.setTarget(npc);
								npc.doCast(SELF_NOVA.getSkill());
							}
						}
					}
					break;
				}
				case FREYA_STAND_EASY:
				case FREYA_STAND_HARD:
				{
					if (world.controller.getVariables().getInt("FREYA_MOVE") == 0)
					{
						world.controller.getVariables().set("FREYA_MOVE", 1);
						world.freya.setIsRunning(true);
						world.freya.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, MIDDLE_POINT);
					}
					
					if ((npc.getCurrentHp() < (npc.getMaxHp() * 0.2)) && !world.isSupportActive)
					{
						world.isSupportActive = true;
						world.freya.setIsInvul(true);
						world.freya.disableCoreAI(true);
						for (L2PcInstance players : world.playersInside)
						{
							players.setIsInvul(true);
							players.abortAttack();
						}
						manageMovie(world, 18);
						startQuestTimer("SPAWN_SUPPORT", 27000, world.controller, null);
					}
					
					if ((attacker.getMountType() == MountType.STRIDER) && !attacker.isAffectedBySkill(ANTI_STRIDER.getSkillId()) && !npc.isCastingNow())
					{
						if (!npc.isSkillDisabled(ANTI_STRIDER.getSkill()))
						{
							npc.setTarget(attacker);
							npc.doCast(ANTI_STRIDER.getSkill());
						}
					}
					
					final L2Character mostHated = ((L2Attackable) npc).getMostHated();
					final boolean canReachMostHated = (mostHated != null) && !mostHated.isDead() && (npc.calculateDistance(mostHated, true, false) <= 800);
					
					if (getRandom(10000) < 3333)
					{
						if (getRandomBoolean())
						{
							if ((npc.calculateDistance(attacker, true, false) <= 800) && npc.checkDoCastConditions(ICE_BALL.getSkill()) && !npc.isCastingNow())
							{
								npc.setTarget(attacker);
								npc.doCast(ICE_BALL.getSkill());
							}
						}
						else
						{
							if (canReachMostHated && npc.checkDoCastConditions(ICE_BALL.getSkill()) && !npc.isCastingNow())
							{
								npc.setTarget(mostHated);
								npc.doCast(ICE_BALL.getSkill());
							}
						}
					}
					else if (getRandom(10000) < 1333)
					{
						if (getRandomBoolean())
						{
							if ((npc.calculateDistance(attacker, true, false) <= 800) && npc.checkDoCastConditions(SUMMON_ELEMENTAL.getSkill()) && !npc.isCastingNow())
							{
								npc.setTarget(attacker);
								npc.doCast(SUMMON_ELEMENTAL.getSkill());
							}
						}
						else
						{
							if (canReachMostHated && npc.checkDoCastConditions(SUMMON_ELEMENTAL.getSkill()) && !npc.isCastingNow())
							{
								npc.setTarget(mostHated);
								npc.doCast(SUMMON_ELEMENTAL.getSkill());
							}
						}
					}
					else if (getRandom(10000) < 1500)
					{
						if (!npc.isAffectedBySkill(SELF_NOVA.getSkillId()) && npc.checkDoCastConditions(SELF_NOVA.getSkill()) && !npc.isCastingNow())
						{
							npc.setTarget(npc);
							npc.doCast(SELF_NOVA.getSkill());
						}
					}
					else if (getRandom(10000) < 1333)
					{
						if (!npc.isAffectedBySkill(REFLECT_MAGIC.getSkillId()) && npc.checkDoCastConditions(REFLECT_MAGIC.getSkill()) && !npc.isCastingNow())
						{
							npc.setTarget(npc);
							npc.doCast(REFLECT_MAGIC.getSkill());
						}
					}
					break;
				}
				case GLACIER:
				{
					if (npc.isScriptValue(0) && (npc.getCurrentHp() < (npc.getMaxHp() * 0.5)))
					{
						npc.setTarget(attacker);
						npc.doCast(ICE_STONE.getSkill());
						npc.setScriptValue(1);
					}
					break;
				}
				case BREATH:
				{
					if ((npc.getCurrentHp() < (npc.getMaxHp() / 20)) && (npc.getVariables().getInt("SUICIDE_ON", 0) == 0))
					{
						npc.getVariables().set("SUICIDE_ON", 1);
						startQuestTimer("ELEMENTAL_KILLED", 1000, npc, null);
					}
					break;
				}
				case KNIGHT_EASY:
				case KNIGHT_HARD:
				{
					if (npc.isCoreAIDisabled())
					{
						manageRandomAttack(world, (L2Attackable) npc);
						npc.disableCoreAI(false);
						npc.setIsImmobilized(false);
						npc.setState(2);
						cancelQuestTimer("ICE_RUPTURE", npc, null);
					}
					break;
				}
				case GLAKIAS_EASY:
				case GLAKIAS_HARD:
				{
					if (npc.getCurrentHp() < (npc.getMaxHp() * 0.02))
					{
						if (npc.getVariables().getInt("OFF_SHOUT") == 0)
						{
							npc.getVariables().set("OFF_SHOUT", 1);
							npc.getVariables().set("DELAY_VAL", 2);
							npc.setTarget(attacker);
							npc.doCast(CANCEL.getSkill());
						}
						else if (npc.getVariables().getInt("OFF_SHOUT") == 1)
						{
							npc.setTarget(attacker);
							npc.doCast(CANCEL.getSkill());
						}
					}
					else if ((npc.getVariables().getInt("OFF_SHOUT") == 0) && (npc.getVariables().getInt("DELAY_VAL") == 1))
					{
						final L2Character mostHated = ((L2Attackable) npc).getMostHated();
						final boolean canReachMostHated = (mostHated != null) && !mostHated.isDead() && (npc.calculateDistance(mostHated, true, false) < 1000);
						
						if (npc.getVariables().getInt("TIMER_ON") == 0)
						{
							npc.getVariables().set("TIMER_ON", 1);
							startQuestTimer("LEADER_RANGEBUFF", getRandom(5, 30) * 1000, npc, null);
							startQuestTimer("LEADER_RANDOMIZE", 25000, npc, null);
							startQuestTimer("LEADER_DASH", 5000, npc, null);
							startQuestTimer("LEADER_DESTROY", 60000, npc, null);
						}
						
						if (getRandom(10000) < 2500)
						{
							if (getRandom(10000) < 2500)
							{
								if (npc.checkDoCastConditions(POWER_STRIKE.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(attacker);
									npc.doCast(POWER_STRIKE.getSkill());
								}
							}
							else if (npc.checkDoCastConditions(POWER_STRIKE.getSkill()) && !npc.isCastingNow() && canReachMostHated)
							{
								npc.setTarget(((L2Attackable) npc).getMostHated());
								npc.doCast(POWER_STRIKE.getSkill());
							}
						}
						else if (getRandom(10000) < 1500)
						{
							if (getRandomBoolean())
							{
								if (npc.checkDoCastConditions(POINT_TARGET.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(attacker);
									npc.doCast(POINT_TARGET.getSkill());
								}
							}
							else if (npc.checkDoCastConditions(POINT_TARGET.getSkill()) && !npc.isCastingNow() && canReachMostHated)
							{
								npc.setTarget(((L2Attackable) npc).getMostHated());
								npc.doCast(POINT_TARGET.getSkill());
							}
						}
						else if (getRandom(10000) < 1500)
						{
							if (getRandomBoolean())
							{
								if (npc.checkDoCastConditions(CYLINDER_THROW.getSkill()) && !npc.isCastingNow())
								{
									npc.setTarget(attacker);
									npc.doCast(CYLINDER_THROW.getSkill());
								}
							}
							else if (npc.checkDoCastConditions(CYLINDER_THROW.getSkill()) && !npc.isCastingNow() && canReachMostHated)
							{
								npc.setTarget(((L2Attackable) npc).getMostHated());
								npc.doCast(CYLINDER_THROW.getSkill());
							}
						}
					}
					break;
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon, skill);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, Skill skill)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if (tmpworld instanceof IQCNBWorld)
		{
			final IQCNBWorld world = (IQCNBWorld) tmpworld;
			
			switch (npc.getId())
			{
				case GLACIER:
				{
					if (skill == ICE_STONE.getSkill())
					{
						if (getRandom(100) < 75)
						{
							final L2Attackable breath = (L2Attackable) addSpawn(BREATH, npc.getLocation(), false, 0, false, world.getInstanceId());
							if (player != null)
							{
								breath.setIsRunning(true);
								breath.addDamageHate(player, 0, 999);
								breath.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
							}
							else
							{
								manageRandomAttack(world, breath);
							}
							world.spawnedMobs.add(breath);
							startQuestTimer("BLIZZARD", 20000, breath, null);
						}
						notifyEvent("SUICIDE", npc, null);
					}
					break;
				}
				case BREATH:
				{
					if (skill == SUICIDE_BREATH.getSkill())
					{
						npc.doDie(null);
					}
					break;
				}
			}
		}
		return super.onSpellFinished(npc, player, skill);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if (tmpworld instanceof IQCNBWorld)
		{
			final IQCNBWorld world = (IQCNBWorld) tmpworld;
			switch (npc.getId())
			{
				case GLAKIAS_EASY:
				case GLAKIAS_HARD:
				{
					manageDespawnMinions(world);
					manageTimer(world, 60, NpcStringId.TIME_REMAINING_UNTIL_NEXT_BATTLE);
					cancelQuestTimer("STAGE_2_FAILED", world.controller, null);
					startQuestTimer("STAGE_3_MOVIE", 60000, world.controller, null);
					break;
				}
				case FREYA_STAND_EASY:
				case FREYA_STAND_HARD:
				{
					world.isSupportActive = false;
					manageMovie(world, 19);
					manageDespawnMinions(world);
					finishInstance(world);
					DecayTaskManager.getInstance().cancel(world.freya);
					cancelQuestTimer("GIVE_SUPPORT", world.controller, null);
					cancelQuestTimer("CAST_BLIZZARD", world.controller, null);
					cancelQuestTimer("FREYA_BUFF", world.controller, null);
					startQuestTimer("FINISH_STAGE", 16000, world.controller, null);
					startQuestTimer("FINISH_WORLD", 300000, world.controller, null);
					break;
				}
				case KNIGHT_EASY:
				case KNIGHT_HARD:
				{
					final L2Npc spawnedBy = npc.getVariables().getObject("SPAWNED_NPC", L2Npc.class);
					final NpcVariables var = world.controller.getVariables();
					int knightCount = var.getInt("KNIGHT_COUNT");
					
					if ((var.getInt("FREYA_MOVE") == 0) && world.isStatus(1))
					{
						var.set("FREYA_MOVE", 1);
						manageScreenMsg(world, NpcStringId.FREYA_HAS_STARTED_TO_MOVE);
						world.freya.setIsRunning(true);
						world.freya.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, MIDDLE_POINT);
					}
					
					if ((knightCount < 10) && (world.isStatus(2)))
					{
						knightCount++;
						var.set("KNIGHT_COUNT", knightCount);
						
						if (knightCount == 10)
						{
							notifyEvent("STAGE_2_MOVIE", world.controller, null);
							world.setStatus(3);
						}
					}
					
					if (spawnedBy != null)
					{
						final int time = (world.isHardCore ? getRandom(30, 60) : getRandom(50, 60)) * 1000;
						startQuestTimer("SPAWN_KNIGHT", time, spawnedBy, null);
					}
					world.spawnedMobs.remove(npc);
					break;
				}
				case GLACIER:
				{
					startQuestTimer("SPAWN_GLACIER", getRandom(30, 60) * 1000, world.controller, null);
					world.spawnedMobs.remove(npc);
					break;
				}
				case BREATH:
				{
					world.spawnedMobs.remove(npc);
					break;
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public void onEnterInstance(L2PcInstance player, InstanceWorld world, boolean firstEntrance)
	{
		if (firstEntrance)
		{
			final IQCNBWorld curworld = (IQCNBWorld) world;
			curworld.isHardCore = curworld.getTemplateId() == TEMPLATE_ID_HARD;
			
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
			
			for (L2PcInstance players : curworld.playersInside)
			{
				if (players != null)
				{
					players.broadcastPacket(ExChangeClientEffectInfo.STATIC_FREYA_DEFAULT);
					
					for (int emmiterId : EMMITERS)
					{
						players.sendPacket(new OnEventTrigger(emmiterId, false));
					}
				}
			}
		}
		else
		{
			teleportPlayer(player, world.isStatus(4) ? BATTLE_PORT : ENTER_LOC[getRandom(ENTER_LOC.length)], world.getInstanceId());
		}
	}
	
	private void managePlayerEnter(L2PcInstance player, IQCNBWorld world)
	{
		world.playersInside.add(player);
		world.addAllowed(player.getObjectId());
		teleportPlayer(player, ENTER_LOC[getRandom(ENTER_LOC.length)], world.getInstanceId(), false);
	}
	
	@Override
	protected boolean checkConditions(L2PcInstance player)
	{
		final L2Party party = player.getParty();
		final L2CommandChannel channel = party != null ? party.getCommandChannel() : null;
		
		if (player.canOverrideCond(PcCondOverride.INSTANCE_CONDITIONS))
		{
			return true;
		}
		
		if (party == null)
		{
			player.sendPacket(SystemMessageId.YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER);
			return false;
		}
		else if (channel == null)
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_ENTER_BECAUSE_YOU_ARE_NOT_ASSOCIATED_WITH_THE_CURRENT_COMMAND_CHANNEL);
			return false;
		}
		else if (player != channel.getLeader())
		{
			player.sendPacket(SystemMessageId.ONLY_A_PARTY_LEADER_CAN_MAKE_THE_REQUEST_TO_ENTER);
			return false;
		}
		else if ((channel.getMemberCount() < MIN_PLAYERS) || (channel.getMemberCount() > MAX_PLAYERS))
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_ENTER_DUE_TO_THE_PARTY_HAVING_EXCEEDED_THE_LIMIT);
			return false;
		}
		for (L2PcInstance channelMember : channel.getMembers())
		{
			if (channelMember.getLevel() < MIN_LEVEL)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_S_LEVEL_DOES_NOT_CORRESPOND_TO_THE_REQUIREMENTS_FOR_ENTRY);
				sm.addPcName(channelMember);
				party.broadcastPacket(sm);
				return false;
			}
			else if (!Util.checkIfInRange(1000, player, channelMember, true))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED);
				sm.addPcName(channelMember);
				party.broadcastPacket(sm);
				return false;
			}
			else if (System.currentTimeMillis() < InstanceManager.getInstance().getInstanceTime(channelMember.getObjectId(), TEMPLATE_ID_EASY))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_MAY_NOT_RE_ENTER_YET);
				sm.addPcName(channelMember);
				party.broadcastPacket(sm);
				return false;
			}
			else if (System.currentTimeMillis() < InstanceManager.getInstance().getInstanceTime(channelMember.getObjectId(), TEMPLATE_ID_HARD))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_MAY_NOT_RE_ENTER_YET);
				sm.addPcName(channelMember);
				party.broadcastPacket(sm);
				return false;
			}
		}
		return true;
	}
	
	private void manageRandomAttack(IQCNBWorld world, L2Attackable mob)
	{
		final List<L2PcInstance> players = new ArrayList<>();
		for (L2PcInstance player : world.playersInside)
		{
			if ((player != null) && !player.isDead() && (player.getInstanceId() == world.getInstanceId()) && !player.isInvisible())
			{
				players.add(player);
			}
		}
		
		Collections.shuffle(players);
		final L2PcInstance target = (!players.isEmpty()) ? players.get(0) : null;
		if (target != null)
		{
			mob.addDamageHate(target, 0, 999);
			mob.setIsRunning(true);
			mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
		}
		else
		{
			startQuestTimer("FIND_TARGET", 10000, mob, null);
		}
	}
	
	private void manageDespawnMinions(IQCNBWorld world)
	{
		world.canSpawnMobs = false;
		for (L2Attackable mobs : world.spawnedMobs)
		{
			if ((mobs != null) && !mobs.isDead())
			{
				mobs.doDie(null);
			}
		}
	}
	
	private void manageTimer(IQCNBWorld world, int time, NpcStringId npcStringId)
	{
		for (L2PcInstance players : world.playersInside)
		{
			if ((players != null) && (players.getInstanceId() == world.getInstanceId()))
			{
				players.sendPacket(new ExSendUIEvent(players, false, false, time, 0, npcStringId));
			}
		}
	}
	
	private void manageScreenMsg(IQCNBWorld world, NpcStringId stringId)
	{
		for (L2PcInstance players : world.playersInside)
		{
			if ((players != null) && (players.getInstanceId() == world.getInstanceId()))
			{
				showOnScreenMsg(players, stringId, 2, 6000);
			}
		}
	}
	
	private void manageMovie(IQCNBWorld world, int movie)
	{
		for (L2PcInstance players : world.playersInside)
		{
			if ((players != null) && (players.getInstanceId() == world.getInstanceId()))
			{
				players.showQuestMovie(movie);
			}
		}
	}
}