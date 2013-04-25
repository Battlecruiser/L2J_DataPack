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
package ai.individual;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Necromancer of the Valley AI.
 * @author Micr0, improved by Adry_85
 */
public class NecromancerValley extends AbstractNpcAI
{
	private static final int NECROMANCER = 22858;
	private static final int EXPLODING_ORC_GHOST = 22818;
	private static final int WRATHFUL_ORC_GHOST = 22819;
	
	private NecromancerValley(String name, String descr)
	{
		super(name, descr);
		addAttackId(NECROMANCER);
		addKillId(NECROMANCER);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (getRandom(100) < 20)
		{
			L2Character attacker = isSummon ? killer.getSummon() : killer;
			L2Attackable Orc = (L2Attackable) addSpawn(EXPLODING_ORC_GHOST, npc.getX(), npc.getY(), npc.getZ() + 10, npc.getHeading(), false, 0, true);
			Orc.setRunning();
			Orc.addDamageHate(attacker, 0, 600);
			Orc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
			L2Attackable Ork2 = (L2Attackable) addSpawn(WRATHFUL_ORC_GHOST, npc.getX(), npc.getY(), npc.getZ() + 20, npc.getHeading(), false, 0, false);
			Ork2.setRunning();
			Ork2.addDamageHate(attacker, 0, 600);
			Ork2.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
			
		}
		
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (getRandom(100) < 1)
		{
			L2Character player = isSummon ? attacker.getSummon() : attacker;
			L2Attackable Orc = (L2Attackable) addSpawn(EXPLODING_ORC_GHOST, npc.getX(), npc.getY(), npc.getZ() + 10, npc.getHeading(), false, 0, true);
			Orc.setRunning();
			Orc.addDamageHate(player, 0, 600);
			Orc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
			L2Attackable Ork2 = (L2Attackable) addSpawn(WRATHFUL_ORC_GHOST, npc.getX(), npc.getY(), npc.getZ() + 20, npc.getHeading(), false, 0, false);
			Ork2.setRunning();
			Ork2.addDamageHate(player, 0, 600);
			Ork2.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
			
		}
		
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	public static void main(String[] args)
	{
		new NecromancerValley(NecromancerValley.class.getSimpleName(), "ai");
	}
}
