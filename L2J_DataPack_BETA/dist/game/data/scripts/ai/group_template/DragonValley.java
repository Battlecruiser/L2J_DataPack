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

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.util.Util;

/**
 * Dragon Valley AI.
 * @author St3eT
 */
public final class DragonValley extends AbstractNpcAI
{
	// NPC
	private static final int NECROMANCER_OF_THE_VALLEY = 22858;
	private static final int EXPLODING_ORC_GHOST = 22818;
	private static final int WRATHFUL_ORC_GHOST = 22819;
	private static final int[] HERB_DROP =
	{
		22822, // Drakos Warrior
		22823, // Drakos Assassin
		22824, // Drakos Guardian
		22825, // Giant Scorpion Bones
		22826, // Scorpion Bones
		22827, // Batwing Drake
		22828, // Parasitic Leech
		22829, // Emerald Drake
		22830, // Gem Dragon
		22831, // Dragon Tracker of the Valley
		22832, // Dragon Scout of the Valley
		22833, // Sand Drake Tracker
		22834, // Dust Dragon Tracker
		22860, // Hungry Parasitic Leech
		22861, // Hard Scorpion Bones
		22862, // Drakos Hunter
	};
	
	// Skill
	private static SkillHolder SELF_DESTRUCTION = new SkillHolder(6850, 1);
	
	private DragonValley()
	{
		super(DragonValley.class.getSimpleName(), "ai/group_template");
		addAttackId(NECROMANCER_OF_THE_VALLEY);
		addKillId(NECROMANCER_OF_THE_VALLEY);
		addKillId(HERB_DROP);
		addSpawnId(EXPLODING_ORC_GHOST);
		addSpawnId(HERB_DROP);
		
		for (int npcId : HERB_DROP)
		{
			for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(npcId))
			{
				onSpawn(spawn.getLastSpawn());
			}
		}
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(NECROMANCER_OF_THE_VALLEY))
		{
			onSpawn(spawn.getLastSpawn());
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("SelfDestruction") && !npc.isDead())
		{
			npc.abortAttack();
			npc.disableCoreAI(true);
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			npc.doCast(SELF_DESTRUCTION.getSkill());
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		L2Attackable mob = (L2Attackable) npc;
		if (Util.contains(HERB_DROP, npc.getId()) && mob.isSweepActive())
		{
			((L2Attackable) npc).dropItem(killer, getRandom(8604, 8605), 1);
		}
		else if (npc.getId() == NECROMANCER_OF_THE_VALLEY)
		{
			spawnGhost(npc, killer, isSummon, 20);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		spawnGhost(npc, attacker, isSummon, 1);
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		((L2Attackable) npc).setOnKillDelay(0);
		if (npc.getId() == EXPLODING_ORC_GHOST)
		{
			startQuestTimer("SelfDestruction", 3000, npc, null);
		}
		return super.onSpawn(npc);
	}
	
	private void spawnGhost(L2Npc npc, L2PcInstance player, boolean isSummon, int chance)
	{
		if ((npc.getScriptValue() < 2) && (getRandom(100) < chance))
		{
			int val = npc.getScriptValue();
			final L2Playable attacker = isSummon ? player.getSummon() : player;
			final L2Attackable Ghost1 = (L2Attackable) addSpawn(getRandom(EXPLODING_ORC_GHOST, WRATHFUL_ORC_GHOST), npc.getX(), npc.getY(), npc.getZ() + 10, npc.getHeading(), false, 0, true);
			attackPlayer(Ghost1, attacker);
			val++;
			if ((val < 2) && (getRandom(100) < 10))
			{
				final L2Attackable Ghost2 = (L2Attackable) addSpawn(getRandom(EXPLODING_ORC_GHOST, WRATHFUL_ORC_GHOST), npc.getX(), npc.getY(), npc.getZ() + 20, npc.getHeading(), false, 0, false);
				attackPlayer(Ghost2, attacker);
				val++;
			}
			npc.setScriptValue(val);
		}
	}
	
	public static void main(String[] args)
	{
		new DragonValley();
	}
}