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
package instances.HarnakUndergroundRuins;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.CategoryType;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.ExSendUIEvent;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * @author Sdw
 */
public class HarnakUndergroundRuins extends AbstractNpcAI
{
	private static final int TEMPLATE_ID = 195;
	// Locations
	private static final Location START_LOC = new Location(-107910, 205828, -10872);
	private static final Location NPC_ROOM1_LOC = new Location(-107930, 206328, -10872);
	private static final Location EXIT_LOC = new Location(-114962, 226564, -2864);
	// Doors
	private static final int DOOR_ONE = 16240100;
	private static final int DOOR_TWO = 16240102;
	// NPCs
	private static final int HADEL = 33344;
	private static final int KRAKIA_BATHUS = 27437;
	private static final int KRAKIA_CARCASS = 27438;
	private static final int KRAKIA_LOTUS = 27439;
	private static final int RAKZAN = 27440;
	private static final int WEISS_KHAN = 27441;
	private static final int BAMONTI = 27442;
	private static final int SEKNUS = 27443;
	private static final int WEISS_ELE = 27454;
	private static final int HARNAKS_WRAITH = 27445;
	private static final int SEAL_CONTROL_DEVICE = 33548;
	private static final int POWER_SOURCE = 33501;
	private static final int[] POWER_SOURCES =
	{
		33501,
		33556,
		33557
	};
	// Skills
	private static final SkillHolder RELEASE_OF_POWER = new SkillHolder(14625, 1);
	private static final SkillHolder MAXIMUM_DEFENSE = new SkillHolder(14700, 1);
	private static final SkillHolder LIGHT_HEAL = new SkillHolder(14736, 1);
	private static final SkillHolder ULTIMATE_BUFF = new SkillHolder(4318, 1);
	// Zones
	private static final int ZONE_ROOM_2 = 200032;
	private static final int ZONE_ROOM_3 = 200033;
	// Movies
	private static final int LAST_ROOM_OPENING = 46;
	private static final int SUCCES_ENDING = 47;
	private static final int FAILED_ENDING = 48;
	
	protected class HuRWorld extends InstanceWorld
	{
		protected int wave = 0;
		protected int currentNpc = 0;
		protected int waveNpcId = 0;
		protected int maximalDefenseCounter = 0;
		protected int timerCount = 0;
		protected int enabledSeal = 0;
		protected Set<L2Npc> spawnedNpc = Collections.newSetFromMap(new ConcurrentHashMap<L2Npc, Boolean>());
		protected boolean openingPlayed = false;
		protected boolean harnakMessage1 = false;
		protected boolean harnakMessage2 = false;
		protected boolean harnakMessage3 = false;
	}
	
	private HarnakUndergroundRuins()
	{
		super(HarnakUndergroundRuins.class.getSimpleName(), "instances");
		registerMobs(KRAKIA_BATHUS, KRAKIA_CARCASS, KRAKIA_LOTUS, RAKZAN, WEISS_KHAN, BAMONTI, SEKNUS, WEISS_ELE, HARNAKS_WRAITH);
		addSeeCreatureId(POWER_SOURCES);
		addEnterZoneId(ZONE_ROOM_2, ZONE_ROOM_3);
		addFirstTalkId(SEAL_CONTROL_DEVICE);
		addTalkId(HADEL);
		addStartNpc(HADEL);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		switch (event)
		{
			case "enter_instance":
			{
				enterInstance(player, "HarnakUndergroundRuins.xml");
				break;
			}
			case "message1":
			{
				showOnScreenMsg(player, NpcStringId.AN_INTRUDER_INTERESTING, ExShowScreenMessage.TOP_CENTER, 5000);
				break;
			}
			case "message2":
			{
				showOnScreenMsg(player, NpcStringId.PROVE_YOUR_WORTH, ExShowScreenMessage.TOP_CENTER, 5000);
				break;
			}
			case "message3":
			{
				showOnScreenMsg(player, NpcStringId.ONLY_THOSE_STRONG_ENOUGH_SHALL_PROCEED, ExShowScreenMessage.TOP_CENTER, 5000);
				break;
			}
			case "message4":
			{
				showOnScreenMsg(player, NpcStringId.THOUGH_SMALL_THIS_POWER_WILL_HELP_YOU_GREATLY, ExShowScreenMessage.TOP_CENTER, 5000);
				break;
			}
			case "message5":
			{
				showOnScreenMsg(player, NpcStringId.ARE_YOU_STRONG_OR_WEAK_OF_THE_LIGHT_OR_DARKNESS, ExShowScreenMessage.TOP_CENTER, 5000);
				break;
			}
			case "message6":
			{
				showOnScreenMsg(player, NpcStringId.ONLY_THOSE_OF_LIGHT_MAY_PASS_OTHERS_MUST_PROVE_THEIR_STRENGTH, ExShowScreenMessage.TOP_CENTER, 5000);
				break;
			}
			case "razkan_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.COME_ATTACK_ME_IF_YOU_DARE);
				break;
			}
			case "bathus_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.IT_S_THE_END_FOR_YOU_TRAITOR);
				break;
			}
			case "bamonti_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.I_WANT_TO_HEAR_YOU_CRY);
				break;
			}
			case "carcass_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.I_WANT_TO_HEAR_YOU_CRY);
				break;
			}
			case "khan_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.YOU_LL_HAVE_TO_KILL_US_FIRST);
				break;
			}
			case "seknus_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.LETS_SEE_WHAT_YOU_ARE_MADE_OF);
				break;
			}
			case "lotus_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.REPENT_AND_YOUR_DEATH_WILL_BE_QUICK);
				break;
			}
			case "ele_say":
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.DIE_TRAITOR);
				break;
			}
			case "spawn_npc1":
			{
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					HuRWorld world = (HuRWorld) tmpworld;
					Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
					List<L2Npc> spawnedNpcs = inst.spawnGroup("first_room");
					world.spawnedNpc.addAll(spawnedNpcs);
					final L2Npc razkan = spawnedNpcs.stream().filter(n -> n.getId() == RAKZAN).findFirst().orElse(null);
					if (razkan != null)
					{
						world.currentNpc = RAKZAN;
						razkan.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
						broadcastNpcSay(razkan, Say2.NPC_ALL, NpcStringId.ARE_YOU_AGAINST_THE_WILL_OF_LIGHT);
						startQuestTimer("razkan_say", 1600, razkan, player);
					}
					world.setStatus(1);
				}
				break;
			}
			case "spawn_npc2":
			{
				openDoor(DOOR_ONE, player.getInstanceId());
				final Instance inst = InstanceManager.getInstance().getInstance(player.getInstanceId());
				inst.spawnGroup("power_sources");
				break;
			}
			case "spawn_npc3":
			{
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					HuRWorld world = (HuRWorld) tmpworld;
					world.incStatus();
					Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
					List<L2Npc> spawnedNpcs = inst.spawnGroup("third_room");
					final L2Npc powerSource = spawnedNpcs.stream().filter(n -> n.getId() == POWER_SOURCE).findFirst().orElse(null);
					if (powerSource != null)
					{
						powerSource.setTarget(player);
						startQuestTimer("cast_light_heal", 3000, powerSource, player);
					}
				}
				break;
			}
			case "show_movie_opening":
			{
				player.showQuestMovie(LAST_ROOM_OPENING);
				startQuestTimer("spawn_npc3", 29950, npc, player);
				break;
			}
			case "spawn_wave1":
			{
				int npcId = 0;
				if (player.isInCategory(CategoryType.SIGEL_CANDIDATE))
				{
					npcId = RAKZAN;
				}
				else if (player.isInCategory(CategoryType.TYRR_CANDIDATE))
				{
					npcId = KRAKIA_BATHUS;
				}
				else if (player.isInCategory(CategoryType.OTHELL_CANDIDATE))
				{
					npcId = BAMONTI;
				}
				else if (player.isInCategory(CategoryType.YUL_CANDIDATE))
				{
					npcId = KRAKIA_CARCASS;
				}
				else if (player.isInCategory(CategoryType.FEOH_CANDIDATE))
				{
					npcId = WEISS_KHAN;
				}
				else if (player.isInCategory(CategoryType.ISS_CANDIDATE))
				{
					npcId = SEKNUS;
				}
				else if (player.isInCategory(CategoryType.WYNN_CANDIDATE))
				{
					npcId = KRAKIA_LOTUS;
				}
				else if (player.isInCategory(CategoryType.AEORE_CANDIDATE))
				{
					npcId = WEISS_ELE;
				}
				
				if (npcId > 0)
				{
					final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
					if (tmpworld instanceof HuRWorld)
					{
						final HuRWorld world = (HuRWorld) tmpworld;
						final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
						final List<L2Npc> spawnedNpcs = inst.spawnGroup("second_room_wave_1_" + npcId);
						world.spawnedNpc.addAll(spawnedNpcs);
						world.waveNpcId = npcId;
						for (L2Npc spawnedNpc : spawnedNpcs)
						{
							addAttackPlayerDesire(spawnedNpc, player);
						}
						world.wave++;
					}
				}
				break;
			}
			case "spawn_wave2":
			{
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					final HuRWorld world = (HuRWorld) tmpworld;
					final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
					final List<L2Npc> spawnedNpcs = inst.spawnGroup("second_room_wave_2_" + world.waveNpcId);
					world.spawnedNpc.addAll(spawnedNpcs);
					for (L2Npc spawnedNpc : spawnedNpcs)
					{
						addAttackPlayerDesire(spawnedNpc, player);
					}
					world.wave++;
				}
				break;
			}
			case "spawn_wave3":
			{
				showOnScreenMsg(player, NpcStringId.I_MUST_GO_HELP_SOME_MORE, ExShowScreenMessage.TOP_CENTER, 5000);
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					HuRWorld world = (HuRWorld) tmpworld;
					final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
					List<L2Npc> spawnedNpcs = inst.spawnGroup("second_room_wave_3_" + world.waveNpcId);
					world.spawnedNpc.addAll(spawnedNpcs);
					for (L2Npc spawnedNpc : spawnedNpcs)
					{
						addAttackPlayerDesire(spawnedNpc, player);
					}
					final List<L2Npc> powersources = inst.spawnGroup("power_source");
					for (L2Npc powersource : powersources)
					{
						powersource.setTarget(player);
						startQuestTimer("cast_defense_maximum", 1, powersource, player);
					}
					world.wave++;
				}
				break;
			}
			case "cast_defense_maximum":
			{
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					HuRWorld world = (HuRWorld) tmpworld;
					if (npc.calculateDistance(player, true, false) < MAXIMUM_DEFENSE.getSkill().getCastRange())
					{
						npc.doCast(MAXIMUM_DEFENSE.getSkill());
						world.maximalDefenseCounter++;
						if (world.maximalDefenseCounter < 3)
						{
							startQuestTimer("cast_defense_maximum", 60000, npc, player);
						}
						else
						{
							npc.deleteMe();
						}
					}
					else
					{
						startQuestTimer("cast_defense_maximum", 1, npc, player);
					}
				}
				break;
			}
			case "cast_light_heal":
			{
				final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
				
				if ((npc != null) && (world != null) && (world.isStatus(3) || world.isStatus(4)))
				{
					if (npc.calculateDistance(player, true, false) < LIGHT_HEAL.getSkill().getCastRange())
					{
						npc.doCast(LIGHT_HEAL.getSkill());
					}
					startQuestTimer("cast_light_heal", 3000, npc, player);
				}
				break;
			}
			case "fail_instance":
			{
				InstanceManager.getInstance().getInstance(player.getInstanceId()).removeSpawnedNpcs();
				player.showQuestMovie(FAILED_ENDING);
				startQuestTimer("exit", 13500, npc, player);
				break;
			}
			case "exit":
			{
				final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
				world.removeAllowed(player.getObjectId());
				teleportPlayer(player, EXIT_LOC, 0);
				break;
			}
			case "spawn_npc4":
			{
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					final HuRWorld world = (HuRWorld) tmpworld;
					final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
					List<L2Npc> spawnedNpcs = inst.spawnGroup("third_room_" + world.waveNpcId);
					for (L2Npc spawnedNpc : spawnedNpcs)
					{
						addAttackPlayerDesire(spawnedNpc, player);
					}
					spawnedNpcs = inst.spawnGroup("seal");
					for (L2Npc spawnedNpc : spawnedNpcs)
					{
						broadcastNpcSay(spawnedNpc, Say2.NPC_ALL, NpcStringId.DISABLE_DEVICE_WILL_GO_OUT_OF_CONTROL_IN_1_MINUTE);
						startQuestTimer("seal_say", 10000, spawnedNpc, player);
					}
				}
				break;
			}
			case "activate_seal":
			{
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					HuRWorld world = (HuRWorld) tmpworld;
					if (npc.getScriptValue() == 0)
					{
						npc.setScriptValue(1);
						world.enabledSeal++;
						if (world.enabledSeal == 2)
						{
							cancelQuestTimer("fail_instance", null, player);
							InstanceManager.getInstance().getInstance(world.getInstanceId()).removeSpawnedNpcs();
							player.showQuestMovie(SUCCES_ENDING);
							startQuestTimer("spawn_hermuncus", 25050, npc, player);
						}
					}
				}
				break;
			}
			case "seal_say":
			{
				final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HuRWorld)
				{
					HuRWorld world = (HuRWorld) tmpworld;
					switch (world.timerCount)
					{
						case 0:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS_ARE_REMAINING41);
							break;
						}
						case 1:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS_ARE_REMAINING42);
							break;
						}
						case 2:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS_ARE_REMAINING43);
							break;
						}
						case 3:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS_ARE_REMAINING44);
							break;
						}
						case 4:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS_ARE_REMAINING45);
							break;
						}
						case 5:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS);
							break;
						}
						case 6:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS2);
							break;
						}
						case 7:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS3);
							break;
						}
						case 8:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECONDS4);
							break;
						}
						case 9:
						{
							broadcastNpcSay(npc, Say2.NPC_SHOUT, NpcStringId.SECOND);
							break;
						}
					}
					if (world.timerCount <= 4)
					{
						startQuestTimer("seal_say", 10000, npc, player);
					}
					else if ((world.timerCount > 4) && (world.timerCount <= 9))
					{
						startQuestTimer("seal_say", 1000, npc, player);
					}
					world.timerCount++;
				}
				break;
			}
			case "spawn_hermuncus":
			{
				final Instance inst = InstanceManager.getInstance().getInstance(player.getInstanceId());
				inst.spawnGroup("hermuncus");
				break;
			}
			case "cast_release_power":
			{
				npc.setTarget(player);
				npc.doCast(RELEASE_OF_POWER.getSkill());
				break;
			}
			case "whisper_to_player":
			{
				showOnScreenMsg(player, NpcStringId.I_HERMUNCUS_GIVE_MY_POWER_TO_THOSE_WHO_FIGHT_FOR_ME, ExShowScreenMessage.TOP_CENTER, 5000);
				
				broadcastNpcSay(npc, Say2.TELL, NpcStringId.RECEIVE_THIS_POWER_FORM_THE_ANCIENT_GIANT);
				broadcastNpcSay(npc, Say2.TELL, NpcStringId.USE_THIS_NEW_POWER_WHEN_THE_TIME_IS_RIGHT);
				
				startQuestTimer("message4", 3000, npc, player);
			}
		}
		
		return htmltext;
	}
	
	private void enterInstance(L2PcInstance player, String template)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof HuRWorld)
			{
				teleportPlayer(player, START_LOC, world.getInstanceId());
				return;
			}
			player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			return;
		}
		world = new HuRWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(TEMPLATE_ID);
		world.addAllowed(player.getObjectId());
		world.setStatus(0);
		InstanceManager.getInstance().addWorld(world);
		
		teleportPlayer(player, START_LOC, world.getInstanceId());
		
		startQuestTimer("fail_instance", 1260000, null, player);
		startQuestTimer("message1", 2500, null, player);
		startQuestTimer("message2", 5000, null, player);
		startQuestTimer("message3", 8500, null, player);
		startQuestTimer("spawn_npc1", 10000, null, player);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(killer);
		if ((tmpworld instanceof HuRWorld))
		{
			HuRWorld world = (HuRWorld) tmpworld;
			if (world.isStatus(0))
			{
				world.spawnedNpc.remove(npc);
				if (world.spawnedNpc.isEmpty())
				{
					startQuestTimer("spawn_npc2", 100, npc, killer);
					world.setStatus(2);
				}
			}
			else if (world.isStatus(1))
			{
				world.spawnedNpc.remove(npc);
				switch (npc.getId())
				{
					case RAKZAN:
					{
						final L2Npc bathius = world.spawnedNpc.stream().filter(n -> n.getId() == KRAKIA_BATHUS).findFirst().orElse(null);
						if (bathius != null)
						{
							bathius.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
							broadcastNpcSay(bathius, Say2.NPC_ALL, NpcStringId.ARE_YOU_PLANNING_TO_BETRAY_THE_GODS_AND_FOLLOW_A_GIANT);
							startQuestTimer("bathus_say", 2600, bathius, killer);
							world.currentNpc = KRAKIA_BATHUS;
						}
						break;
					}
					case KRAKIA_BATHUS:
					{
						final L2Npc bamonti = world.spawnedNpc.stream().filter(n -> n.getId() == BAMONTI).findFirst().orElse(null);
						if (bamonti != null)
						{
							bamonti.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
							broadcastNpcSay(bamonti, Say2.NPC_ALL, NpcStringId.HAHA);
							startQuestTimer("bamonti_say", 2600, bamonti, killer);
							world.currentNpc = BAMONTI;
						}
						break;
					}
					case BAMONTI:
					{
						final L2Npc carcass = world.spawnedNpc.stream().filter(n -> n.getId() == KRAKIA_CARCASS).findFirst().orElse(null);
						if (carcass != null)
						{
							carcass.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
							broadcastNpcSay(carcass, Say2.NPC_ALL, NpcStringId.HAHA);
							startQuestTimer("carcass_say", 2600, carcass, killer);
							world.currentNpc = KRAKIA_CARCASS;
						}
						break;
					}
					case KRAKIA_CARCASS:
					{
						final L2Npc khan = world.spawnedNpc.stream().filter(n -> n.getId() == WEISS_KHAN).findFirst().orElse(null);
						if (khan != null)
						{
							khan.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
							broadcastNpcSay(khan, Say2.NPC_ALL, NpcStringId.YOU_WILL_NOT_FREE_HERMUNCUS);
							startQuestTimer("khan_say", 2600, khan, killer);
							world.currentNpc = WEISS_KHAN;
						}
						break;
					}
					case WEISS_KHAN:
					{
						final L2Npc seknus = world.spawnedNpc.stream().filter(n -> n.getId() == SEKNUS).findFirst().orElse(null);
						if (seknus != null)
						{
							seknus.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
							broadcastNpcSay(seknus, Say2.NPC_ALL, NpcStringId.MORTAL);
							startQuestTimer("seknus_say", 2600, seknus, killer);
							world.currentNpc = SEKNUS;
						}
						break;
					}
					case SEKNUS:
					{
						final L2Npc lotus = world.spawnedNpc.stream().filter(n -> n.getId() == KRAKIA_LOTUS).findFirst().orElse(null);
						if (lotus != null)
						{
							lotus.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
							broadcastNpcSay(lotus, Say2.NPC_ALL, NpcStringId.TRYING_TO_FREE_HERMUNCUS);
							startQuestTimer("lotus_say", 2600, lotus, killer);
							world.currentNpc = KRAKIA_LOTUS;
						}
						break;
					}
					case KRAKIA_LOTUS:
					{
						final L2Npc ele = world.spawnedNpc.stream().filter(n -> n.getId() == WEISS_ELE).findFirst().orElse(null);
						if (ele != null)
						{
							ele.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, NPC_ROOM1_LOC);
							broadcastNpcSay(ele, Say2.NPC_ALL, NpcStringId.YOU_WILL_NEVER_BREAK_THE_SEAL);
							startQuestTimer("ele_say", 2600, ele, killer);
							world.currentNpc = WEISS_ELE;
						}
						break;
					}
					case WEISS_ELE:
					{
						startQuestTimer("spawn_npc2", 100, npc, killer);
						break;
					}
				}
			}
			else if (world.isStatus(2))
			{
				world.spawnedNpc.remove(npc);
				if (world.spawnedNpc.isEmpty())
				{
					switch (world.wave)
					{
						case 1:
						{
							startQuestTimer("spawn_wave2", 100, npc, killer);
							break;
						}
						case 2:
						{
							startQuestTimer("spawn_wave3", 100, npc, killer);
							break;
						}
						case 3:
						{
							openDoor(DOOR_TWO, world.getInstanceId());
							break;
						}
					}
				}
			}
			else if (npc.getId() == HARNAKS_WRAITH)
			{
				cancelQuestTimer("fail_instance", null, killer);
				InstanceManager.getInstance().getInstance(world.getInstanceId()).removeSpawnedNpcs();
				killer.showQuestMovie(SUCCES_ENDING);
				startQuestTimer("spawn_hermuncus", 25050, npc, killer);
			}
		}
		
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isSummon)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
		if (tmpworld instanceof HuRWorld)
		{
			HuRWorld world = (HuRWorld) tmpworld;
			
			if (world.isStatus(1))
			{
				switch (npc.getId())
				{
					case RAKZAN:
					case KRAKIA_BATHUS:
					case BAMONTI:
					case KRAKIA_CARCASS:
					case WEISS_KHAN:
					case SEKNUS:
					case KRAKIA_LOTUS:
					case WEISS_ELE:
					{
						if ((npc.getId() != world.currentNpc))
						{
							for (L2Npc livingNpc : world.spawnedNpc)
							{
								addAttackPlayerDesire(livingNpc, player);
							}
							
							world.setStatus(0);
						}
						break;
					}
				}
			}
			else if (world.isStatus(2))
			{
				switch (npc.getId())
				{
					case RAKZAN:
					case KRAKIA_BATHUS:
					case BAMONTI:
					case KRAKIA_CARCASS:
					case WEISS_KHAN:
					case SEKNUS:
					case KRAKIA_LOTUS:
					{
						if (((npc.getCurrentHp() / npc.getMaxHp()) * 100) < 80)
						{
							npc.doCast(ULTIMATE_BUFF.getSkill());
						}
						break;
					}
				}
			}
			else if (world.isStatus(3))
			{
				if (npc.getId() == HARNAKS_WRAITH)
				{
					if (!world.harnakMessage1 && (((npc.getCurrentHp() / npc.getMaxHp()) * 100) > 80))
					{
						showOnScreenMsg(player, NpcStringId.FREE_ME_FROM_THIS_BINDING_OF_LIGHT, ExShowScreenMessage.TOP_CENTER, 5000);
						world.harnakMessage1 = true;
					}
					else if (!world.harnakMessage2 && (((npc.getCurrentHp() / npc.getMaxHp()) * 100) <= 80))
					{
						showOnScreenMsg(player, NpcStringId.DESTROY_THE_GHOST_OF_HARNAK_THIS_CORRUPTED_CREATURE, ExShowScreenMessage.TOP_CENTER, 5000);
						world.harnakMessage2 = true;
					}
					else if (!world.harnakMessage3 && (((npc.getCurrentHp() / npc.getMaxHp()) * 100) <= 60))
					{
						showOnScreenMsg(player, NpcStringId.FREE_ME_AND_I_PROMISE_YOU_THE_POWER_OF_GIANTS, ExShowScreenMessage.TOP_CENTER, 5000);
						world.harnakMessage3 = true;
					}
					else if (((npc.getCurrentHp() / npc.getMaxHp()) * 100) <= 50)
					{
						world.incStatus();
						player.sendPacket(new ExSendUIEvent(player, false, false, 60, 0, NpcStringId.REMAINING_TIME));
						showOnScreenMsg(player, NpcStringId.NO_THE_SEAL_CONTROLS_HAVE_BEEN_EXPOSED_GUARDS_PROTECT_THE_SEAL_CONTROLS, ExShowScreenMessage.TOP_CENTER, 10000);
						startQuestTimer("spawn_npc4", 1, npc, player);
						cancelQuestTimer("fail_instance", null, player);
						startQuestTimer("fail_instance", 60000, null, player);
					}
				}
			}
		}
		
		return super.onAttack(npc, player, damage, isSummon);
	}
	
	@Override
	public String onSeeCreature(L2Npc npc, L2Character creature, boolean isSummon)
	{
		if (Util.contains(POWER_SOURCES, npc.getId()) && creature.isPlayer())
		{
			startQuestTimer("cast_release_power", 2000, npc, creature.getActingPlayer());
			if (npc.getId() == POWER_SOURCE)
			{
				startQuestTimer("whisper_to_player", 2000, npc, creature.getActingPlayer());
			}
		}
		return super.onSeeCreature(npc, creature, isSummon);
	}
	
	@Override
	public String onEnterZone(L2Character character, L2ZoneType zone)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(character.getActingPlayer());
		if (tmpworld instanceof HuRWorld)
		{
			final HuRWorld world = (HuRWorld) tmpworld;
			
			switch (zone.getId())
			{
				case ZONE_ROOM_2:
				{
					if (character.isPlayer() && world.isStatus(1))
					{
						world.incStatus();
						
						startQuestTimer("message2", 100, null, character.getActingPlayer());
						startQuestTimer("message5", 2600, null, character.getActingPlayer());
						startQuestTimer("message6", 5100, null, character.getActingPlayer());
						
						startQuestTimer("spawn_wave1", 5100, null, character.getActingPlayer());
					}
					break;
				}
				case ZONE_ROOM_3:
				{
					if (character.isPlayer() && !world.openingPlayed)
					{
						startQuestTimer("show_movie_opening", 100, null, character.getActingPlayer());
						world.openingPlayed = true;
					}
					break;
				}
			}
		}
		return super.onEnterZone(character, zone);
	}
	
	public static void main(String[] args)
	{
		new HarnakUndergroundRuins();
	}
}