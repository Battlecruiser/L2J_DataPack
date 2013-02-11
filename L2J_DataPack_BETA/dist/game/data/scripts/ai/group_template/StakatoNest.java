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
package ai.group_template;

import java.util.List;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.util.Broadcast;
import com.l2jserver.gameserver.util.Util;

/**
 * Stakato Nest AI.
 * @author Gnacik
 */
public class StakatoNest extends AbstractNpcAI
{
	// List of all mobs just for register
	private static final int[] STAKATO_MOBS =
	{
		18793,
		18794,
		18795,
		18796,
		18797,
		18798,
		22617,
		22618,
		22619,
		22620,
		22621,
		22622,
		22623,
		22624,
		22625,
		22626,
		22627,
		22628,
		22629,
		22630,
		22631,
		22632,
		22633,
		25667
	};
	
	// Coocons
	private static final int[] COCOONS =
	{
		18793,
		18794,
		18795,
		18796,
		18797,
		18798
	};
	
	// Cannibalistic Stakato Leader
	private static final int STAKATO_LEADER = 22625;
	
	// Spike Stakato Nurse
	private static final int STAKATO_NURSE = 22630;
	// Spike Stakato Nurse (Changed)
	private static final int STAKATO_NURSE_2 = 22631;
	// Spiked Stakato Baby
	private static final int STAKATO_BABY = 22632;
	// Spiked Stakato Captain
	private static final int STAKATO_CAPTAIN = 22629;
	
	// Female Spiked Stakato
	private static final int STAKATO_FEMALE = 22620;
	// Male Spiked Stakato
	private static final int STAKATO_MALE = 22621;
	// Male Spiked Stakato (Changed)
	private static final int STAKATO_MALE_2 = 22622;
	// Spiked Stakato Guard
	private static final int STAKATO_GUARD = 22619;
	
	// Cannibalistic Stakato Chief
	private static final int STAKATO_CHIEF = 25667;
	// Growth Accelerator
	private static final int GROWTH_ACCELERATOR = 2905;
	// Small Stakato Cocoon
	private static final int SMALL_COCOON = 14833;
	// Large Stakato Cocoon
	private static final int LARGE_COCOON = 14834;
	
	private StakatoNest(String name, String descr)
	{
		super(name, descr);
		registerMobs(STAKATO_MOBS);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		L2MonsterInstance mob = (L2MonsterInstance) npc;
		
		if ((mob.getNpcId() == STAKATO_LEADER) && (getRandom(1000) < 100) && (mob.getCurrentHp() < (mob.getMaxHp() * 0.3)))
		{
			L2MonsterInstance _follower = checkMinion(npc);
			
			if (_follower != null)
			{
				double _hp = _follower.getCurrentHp();
				
				if (_hp > (_follower.getMaxHp() * 0.3))
				{
					mob.abortAttack();
					mob.abortCast();
					mob.setHeading(Util.calculateHeadingFrom(mob, _follower));
					mob.doCast(SkillTable.getInstance().getInfo(4484, 1));
					mob.setCurrentHp(mob.getCurrentHp() + _hp);
					_follower.doDie(_follower);
					_follower.deleteMe();
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		L2MonsterInstance _minion = checkMinion(npc);
		
		if ((npc.getNpcId() == STAKATO_NURSE) && (_minion != null))
		{
			Broadcast.toSelfAndKnownPlayers(npc, new MagicSkillUse(npc, 2046, 1, 1000, 0));
			for (int i = 0; i < 3; i++)
			{
				L2Npc _spawned = addSpawn(STAKATO_CAPTAIN, _minion, true);
				attackPlayer(killer, _spawned);
			}
		}
		else if (npc.getNpcId() == STAKATO_BABY)
		{
			L2MonsterInstance leader = ((L2MonsterInstance) npc).getLeader();
			if ((leader != null) && !leader.isDead())
			{
				startQuestTimer("nurse_change", 5000, leader, killer);
			}
		}
		else if ((npc.getNpcId() == STAKATO_MALE) && (_minion != null))
		{
			Broadcast.toSelfAndKnownPlayers(npc, new MagicSkillUse(npc, 2046, 1, 1000, 0));
			for (int i = 0; i < 3; i++)
			{
				L2Npc _spawned = addSpawn(STAKATO_GUARD, _minion, true);
				attackPlayer(killer, _spawned);
			}
		}
		else if (npc.getNpcId() == STAKATO_FEMALE)
		{
			L2MonsterInstance leader = ((L2MonsterInstance) npc).getLeader();
			if ((leader != null) && !leader.isDead())
			{
				startQuestTimer("male_change", 5000, leader, killer);
			}
		}
		else if (npc.getNpcId() == STAKATO_CHIEF)
		{
			if (killer.isInParty())
			{
				List<L2PcInstance> party = killer.getParty().getMembers();
				for (L2PcInstance member : party)
				{
					giveCocoon(member, npc);
				}
			}
			else
			{
				giveCocoon(killer, npc);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isSummon)
	{
		if (Util.contains(COCOONS, npc.getNpcId()) && Util.contains(targets, npc) && (skill.getId() == GROWTH_ACCELERATOR))
		{
			npc.doDie(caster);
			L2Npc spawned = addSpawn(STAKATO_CHIEF, npc.getX(), npc.getY(), npc.getZ(), Util.calculateHeadingFrom(npc, caster), false, 0, true);
			attackPlayer(caster, spawned);
		}
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((npc == null) || (player == null))
		{
			return null;
		}
		if (npc.isDead())
		{
			return null;
		}
		
		if (event.equalsIgnoreCase("nurse_change"))
		{
			npc.getSpawn().decreaseCount(npc);
			npc.deleteMe();
			L2Npc _spawned = addSpawn(STAKATO_NURSE_2, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), false, 0, true);
			attackPlayer(player, _spawned);
		}
		else if (event.equalsIgnoreCase("male_change"))
		{
			npc.getSpawn().decreaseCount(npc);
			npc.deleteMe();
			L2Npc _spawned = addSpawn(STAKATO_MALE_2, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), false, 0, true);
			attackPlayer(player, _spawned);
		}
		return null;
	}
	
	private static L2MonsterInstance checkMinion(L2Npc npc)
	{
		L2MonsterInstance mob = (L2MonsterInstance) npc;
		if (mob.hasMinions())
		{
			List<L2MonsterInstance> minion = mob.getMinionList().getSpawnedMinions();
			if ((minion != null) && !minion.isEmpty() && (minion.get(0) != null) && !minion.get(0).isDead())
			{
				return minion.get(0);
			}
		}
		
		return null;
	}
	
	private static void attackPlayer(L2PcInstance player, L2Npc npc)
	{
		if ((npc != null) && (player != null))
		{
			((L2Attackable) npc).setIsRunning(true);
			((L2Attackable) npc).addDamageHate(player, 0, 999);
			((L2Attackable) npc).getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
		}
	}
	
	private static void giveCocoon(L2PcInstance player, L2Npc npc)
	{
		if (getRandom(100) > 80)
		{
			player.addItem("StakatoCocoon", LARGE_COCOON, 1, npc, true);
		}
		else
		{
			player.addItem("StakatoCocoon", SMALL_COCOON, 1, npc, true);
		}
	}
	
	public static void main(String[] args)
	{
		new StakatoNest(StakatoNest.class.getSimpleName(), "ai");
	}
}
