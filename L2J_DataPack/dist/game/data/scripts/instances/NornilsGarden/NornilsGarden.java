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
package instances.NornilsGarden;

import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * Nornil's Garden instance zone.
 * @author Gnacik
 * @version 2010-10-15 Based on official server Naia
 */
public final class NornilsGarden extends Quest
{
	protected class NornilsWorld extends InstanceWorld
	{
		public L2Npc first_npc = null;
		public boolean spawned_1 = false;
		public boolean spawned_2 = false;
		public boolean spawned_3 = false;
		public boolean spawned_4 = false;
	}
	
	private static final int TEMPLATE_ID = 11;
	
	private static final int DURATION_TIME = 70;
	private static final int EMPTY_DESTROY_TIME = 5;
	
	private static final int INSTANCE_LVL_MIN = 18;
	private static final int INSTANCE_LVL_MAX = 22;
	
	private static final int _garden_guard = 32330;
	
	private static final int[] _final_gates =
	{
		32260,
		32261,
		32262
	};
	
	private static final Location SPAWN_PPL = new Location(-111184, 74540, -12430);
	private static final Location EXIT_PPL = new Location(-74058, 52040, -3680);
	
	private static final int[][] _auto_gates =
	{
		// Warriors gate
		{
			20110,
			16200001
		},
		// Midway gate
		{
			20111,
			16200004
		},
		// Gate
		{
			20112,
			16200013
		}
	};
	
	private static final Skill skill1 = SkillData.getInstance().getSkill(4322, 1);
	private static final Skill skill2 = SkillData.getInstance().getSkill(4327, 1);
	private static final Skill skill3 = SkillData.getInstance().getSkill(4329, 1);
	private static final Skill skill4 = SkillData.getInstance().getSkill(4324, 1);
	
	private static final int _herb_jar = 18478;
	
	// @formatter:off
	private static final int[][] _gatekeepers =
	{
		{ 18352, 9703, 0 }, // Kamael Guard
		{ 18353, 9704, 0 }, // Guardian of Records
		{ 18354, 9705, 0 }, // Guardian of Observation
		{ 18355, 9706, 0 }, // Spicula's Guard
		{ 18356, 9707, 16200024 }, // Harkilgamed's Gatekeeper
		{ 18357, 9708, 16200025 }, // Rodenpicula's Gatekeeper
		{ 18358, 9713, 0 }, // Guardian of Secrets
		{ 18359, 9709, 16200023 }, // Arviterre's Guardian
		{ 18360, 9710, 0 }, // Katenar's Gatekeeper
		{ 18361, 9711, 0 }, // Guardian of Prediction
		{ 25528, 9712, 0 }  // Tiberias
	};
	
	private static final int[][] HP_HERBS_DROPLIST =
	{
		// itemId, count, chance
		{ 8602, 1, 10 },
		{ 8601, 2, 40 },
		{ 8600, 3, 70 }
	};
	
	private static final int[][] _group_1 = 
	{
		{ 18363, -109899, 74431, -12528, 16488 },
		{ 18483, -109701, 74501, -12528, 24576 },
		{ 18483, -109892, 74886, -12528,     0 },
		{ 18363, -109703, 74879, -12528, 49336 }

	};
	
	private static final int[][] _group_2 = 
	{
		{ 18363, -110393, 78276, -12848, 49152 },
		{ 18363, -110561, 78276, -12848, 49152 },
		{ 18362, -110414, 78495, -12905, 48112 },
		{ 18362, -110545, 78489, -12903, 48939 },
		{ 18483, -110474, 78601, -12915, 49488 },
		{ 18362, -110474, 78884, -12915, 49338 },
		{ 18483, -110389, 79131, -12915, 48539 },
		{ 18483, -110551, 79134, -12915, 49151 }
	};
	
	private static final int[][] _group_3 =
	{
		{ 18483, -107798, 80721, -12912, 0 },
		{ 18483, -107798, 80546, -12912, 0 },
		{ 18347, -108033, 80644, -12912, 0 },
		{ 18363, -108520, 80647, -12912, 0 },
		{ 18483, -108740, 80752, -12912, 0 },
		{ 18363, -109016, 80642, -12912, 0 },
		{ 18483, -108740, 80546, -12912, 0 }
	};
	
	private static final int[][] _group_4 =
	{
		{ 18362, -110082, 83998, -12928, 0 },
		{ 18362, -110082, 84210, -12928, 0 },
		{ 18363, -109963, 84102, -12896, 0 },
		{ 18347, -109322, 84102, -12880, 0 },
		{ 18362, -109131, 84097, -12880, 0 },
		{ 18483, -108932, 84101, -12880, 0 },
		{ 18483, -109313, 84488, -12880, 0 },
		{ 18362, -109122, 84490, -12880, 0 },
		{ 18347, -108939, 84489, -12880, 0 }
	};
	
	private static final int[][] MP_HERBS_DROPLIST =
	{
		// itemId, count, chance
		{ 8605, 1, 10 },
		{ 8604, 2, 40 },
		{ 8603, 3, 70 }
	};
	// @formatter:on
	
	private static final void dropHerb(L2Npc mob, L2PcInstance player, int[][] drop)
	{
		final int chance = getRandom(100);
		for (int[] element : drop)
		{
			if (chance < element[2])
			{
				mob.dropItem(player, element[0], element[1]);
			}
		}
	}
	
	private static final void removeBuffs(L2Character ch)
	{
		ch.stopAllEffectsExceptThoseThatLastThroughDeath();
	}
	
	private static final void giveBuffs(L2Character ch)
	{
		if (skill1 != null)
		{
			skill1.applyEffects(ch, ch);
		}
		if (skill2 != null)
		{
			skill2.applyEffects(ch, ch);
		}
		if (skill3 != null)
		{
			skill3.applyEffects(ch, ch);
		}
		if (skill4 != null)
		{
			skill4.applyEffects(ch, ch);
		}
	}
	
	private NornilsGarden()
	{
		super(-1, NornilsGarden.class.getSimpleName(), "instances");
		addStartNpc(_garden_guard);
		addFirstTalkId(_garden_guard);
		addTalkId(_garden_guard);
		for (int i[] : _gatekeepers)
		{
			addKillId(i[0]);
		}
		for (int i[] : _auto_gates)
		{
			addEnterZoneId(i[0]);
		}
		addTalkId(_final_gates);
		addAttackId(_herb_jar);
		addAttackId(18362); // first garden guard
	}
	
	@Override
	public final void teleportPlayer(L2PcInstance player, Location loc, int instanceId)
	{
		removeBuffs(player);
		giveBuffs(player);
		if (player.hasSummon())
		{
			removeBuffs(player.getSummon());
			giveBuffs(player.getSummon());
		}
		super.teleportPlayer(player, loc, instanceId);
	}
	
	private void exitInstance(L2PcInstance player)
	{
		InstanceWorld inst = InstanceManager.getInstance().getWorld(player.getInstanceId());
		if (inst instanceof NornilsWorld)
		{
			NornilsWorld world = ((NornilsWorld) inst);
			world.removeAllowed(player.getObjectId());
			teleportPlayer(player, EXIT_PPL, 0);
		}
	}
	
	private final synchronized String enterInstance(L2Npc npc, L2PcInstance player)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof NornilsWorld) || (world.getTemplateId() != TEMPLATE_ID))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return null;
			}
			// check for level difference again on reenter
			if ((player.getLevel() > INSTANCE_LVL_MAX) || (player.getLevel() < INSTANCE_LVL_MIN))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(player);
				player.sendPacket(sm);
				return null;
			}
			// check what instance still exist
			Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
			if (inst != null)
			{
				teleportPlayer(player, SPAWN_PPL, world.getInstanceId());
			}
			return null;
		}
		// Creating new instance
		String result = checkConditions(npc, player);
		if (!(result.equalsIgnoreCase("ok")))
		{
			return result;
		}
		
		final int instanceId = InstanceManager.getInstance().createDynamicInstance("NornilsGarden.xml");
		final Instance inst = InstanceManager.getInstance().getInstance(instanceId);
		
		inst.setName(InstanceManager.getInstance().getInstanceIdName(TEMPLATE_ID));
		inst.setSpawnLoc(new Location(player));
		inst.setAllowSummon(false);
		inst.setDuration(DURATION_TIME * 60000);
		inst.setEmptyDestroyTime(EMPTY_DESTROY_TIME * 60000);
		world = new NornilsWorld();
		world.setInstanceId(instanceId);
		world.setTemplateId(TEMPLATE_ID);
		InstanceManager.getInstance().addWorld(world);
		_log.info("Nornils Garden: started, Instance: " + instanceId + " created by player: " + player.getName());
		
		prepareInstance((NornilsWorld) world);
		
		// and finally teleport party into instance
		final L2Party party = player.getParty();
		if (party != null)
		{
			for (L2PcInstance partyMember : party.getMembers())
			{
				world.addAllowed(partyMember.getObjectId());
				teleportPlayer(partyMember, SPAWN_PPL, instanceId);
			}
		}
		return null;
	}
	
	private void prepareInstance(NornilsWorld world)
	{
		world.first_npc = addSpawn(18362, -109702, 74696, -12528, 49568, false, 0, false, world.getInstanceId());
		
		final L2DoorInstance door = getDoor(16200010, world.getInstanceId());
		if (door != null)
		{
			door.setTargetable(false);
			door.setMeshIndex(2);
		}
	}
	
	private void spawn1(L2Npc npc)
	{
		InstanceWorld inst = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (inst instanceof NornilsWorld)
		{
			NornilsWorld world = ((NornilsWorld) inst);
			if (npc.equals(world.first_npc) && !world.spawned_1)
			{
				world.spawned_1 = true;
				
				for (int mob[] : _group_1)
				{
					addSpawn(mob[0], mob[1], mob[2], mob[3], mob[4], false, 0, false, world.getInstanceId());
				}
			}
		}
	}
	
	private void spawn2(L2Npc npc)
	{
		InstanceWorld inst = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (inst instanceof NornilsWorld)
		{
			NornilsWorld world = ((NornilsWorld) inst);
			if (!world.spawned_2)
			{
				world.spawned_2 = true;
				
				for (int mob[] : _group_2)
				{
					addSpawn(mob[0], mob[1], mob[2], mob[3], mob[4], false, 0, false, world.getInstanceId());
				}
			}
		}
	}
	
	private void spawn3(L2Character cha)
	{
		InstanceWorld inst = InstanceManager.getInstance().getWorld(cha.getInstanceId());
		if (inst instanceof NornilsWorld)
		{
			NornilsWorld world = ((NornilsWorld) inst);
			if (!world.spawned_3)
			{
				world.spawned_3 = true;
				
				for (int mob[] : _group_3)
				{
					addSpawn(mob[0], mob[1], mob[2], mob[3], mob[4], false, 0, false, world.getInstanceId());
				}
			}
		}
	}
	
	private void spawn4(L2Character cha)
	{
		InstanceWorld inst = InstanceManager.getInstance().getWorld(cha.getInstanceId());
		if (inst instanceof NornilsWorld)
		{
			NornilsWorld world = ((NornilsWorld) inst);
			if (!world.spawned_4)
			{
				world.spawned_4 = true;
				
				for (int mob[] : _group_4)
				{
					addSpawn(mob[0], mob[1], mob[2], mob[3], mob[4], false, 0, false, world.getInstanceId());
				}
			}
		}
	}
	
	public void openDoor(QuestState st, L2PcInstance player, int doorId)
	{
		st.unset("correct");
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(player.getInstanceId());
		if (tmpworld instanceof NornilsWorld)
		{
			openDoor(doorId, tmpworld.getInstanceId());
		}
	}
	
	private static final String checkConditions(L2Npc npc, L2PcInstance player)
	{
		final L2Party party = player.getParty();
		// player must be in party
		if (party == null)
		{
			player.sendPacket(SystemMessageId.NOT_IN_PARTY_CANT_ENTER);
			return "32330-05.html";
		}
		// ...and be party leader
		if (party.getLeader() != player)
		{
			player.sendPacket(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER);
			return "32330-08.html";
		}
		boolean _kamael = false;
		// for each party member
		for (L2PcInstance partyMember : party.getMembers())
		{
			// player level must be in range
			if (partyMember.getLevel() > INSTANCE_LVL_MAX)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partyMember);
				player.sendPacket(sm);
				return "32330-06.html";
			}
			if (partyMember.getLevel() < INSTANCE_LVL_MIN)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partyMember);
				player.sendPacket(sm);
				return "32330-07.html";
			}
			if (partyMember.getClassId().level() != 0)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partyMember);
				player.sendPacket(sm);
				return "32330-06.html";
			}
			// player must be near party leader
			if (!partyMember.isInsideRadius(player, 500, true, true))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED);
				sm.addPcName(partyMember);
				player.sendPacket(sm);
				return "32330-08.html";
			}
			if (partyMember.getRace().ordinal() == 5)
			{
				_kamael = true;
			}
		}
		if (!_kamael)
		{
			return "32330-08.html";
		}
		return "ok";
	}
	
	@Override
	public String onEnterZone(L2Character character, L2ZoneType zone)
	{
		if ((character instanceof L2PcInstance) && !character.isDead() && !character.isTeleporting() && ((L2PcInstance) character).isOnline())
		{
			InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(character.getInstanceId());
			if (tmpworld instanceof NornilsWorld)
			{
				for (int _auto[] : _auto_gates)
				{
					if (zone.getId() == _auto[0])
					{
						openDoor(_auto[1], tmpworld.getInstanceId());
					}
					if (zone.getId() == 20111)
					{
						spawn3(character);
					}
					else if (zone.getId() == 20112)
					{
						spawn4(character);
					}
					
				}
			}
		}
		return super.onEnterZone(character, zone);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if ((npc.getId() == _garden_guard) && event.equalsIgnoreCase("enter_instance"))
		{
			try
			{
				htmltext = enterInstance(npc, player);
			}
			catch (Exception e)
			{
			}
		}
		else if ((npc.getId() == 32258) && event.equalsIgnoreCase("exit"))
		{
			try
			{
				exitInstance(player);
			}
			catch (Exception e)
			{
			}
		}
		else if (Util.contains(_final_gates, npc.getId()))
		{
			if (event.equalsIgnoreCase("32260-02.html") || event.equalsIgnoreCase("32261-02.html") || event.equalsIgnoreCase("32262-02.html"))
			{
				st.unset("correct");
			}
			else if (Util.isDigit(event))
			{
				int correct = st.getInt("correct");
				correct++;
				st.set("correct", String.valueOf(correct));
				htmltext = npc.getId() + "-0" + String.valueOf(correct + 2) + ".html";
			}
			else if (event.equalsIgnoreCase("check"))
			{
				int correct = st.getInt("correct");
				if ((npc.getId() == 32260) && (correct == 3))
				{
					openDoor(st, player, 16200014);
				}
				else if ((npc.getId() == 32261) && (correct == 3))
				{
					openDoor(st, player, 16200015);
				}
				else if ((npc.getId() == 32262) && (correct == 4))
				{
					openDoor(st, player, 16200016);
				}
				else
				{
					return npc.getId() + "-00.html";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		if (Util.contains(_final_gates, npc.getId()))
		{
			return npc.getId() + "-01.html";
		}
		
		return null;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		getQuestState(player, true);
		return npc.getId() + ".html";
	}
	
	@Override
	public final String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if ((npc.getId() == _herb_jar) && !npc.isDead())
		{
			dropHerb(npc, attacker, HP_HERBS_DROPLIST);
			dropHerb(npc, attacker, MP_HERBS_DROPLIST);
			npc.doDie(attacker);
		}
		else if ((npc.getId() == 18362) && (npc.getInstanceId() > 0))
		{
			spawn1(npc);
		}
		return null;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		for (int _gk[] : _gatekeepers)
		{
			if (npc.getId() == _gk[0])
			{
				// Drop key
				npc.dropItem(player, _gk[1], 1);
				
				// Check if gatekeeper should open bridge, and open it
				if (_gk[2] > 0)
				{
					InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(player.getInstanceId());
					if (tmpworld instanceof NornilsWorld)
					{
						openDoor(_gk[2], tmpworld.getInstanceId());
					}
				}
			}
			if (npc.getId() == 18355)
			{
				spawn2(npc);
			}
		}
		return super.onKill(npc, player, isSummon);
	}
	
	public static void main(String[] args)
	{
		new NornilsGarden();
	}
}
