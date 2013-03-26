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

import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Pavel Archaic AI.
 * @author Gnacik
 */
public class PavelArchaic extends AbstractNpcAI
{
	private PavelArchaic()
	{
		super(PavelArchaic.class.getSimpleName(), "ai/group_template");
		addKillId(22801, 22804);
		addAttackId(18917);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (!npc.isDead())
		{
			npc.doDie(attacker);
			
			if (getRandom(100) < 40)
			{
				L2Attackable golem1 = (L2Attackable) addSpawn(22801, npc.getLocation(), false, 0);
				attackPlayer(golem1, attacker);
				
				L2Attackable golem2 = (L2Attackable) addSpawn(22804, npc.getLocation(), false, 0);
				attackPlayer(golem2, attacker);
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		L2Attackable golem = (L2Attackable) addSpawn(npc.getNpcId() + 1, npc.getLocation(), false, 0);
		attackPlayer(golem, killer);
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new PavelArchaic();
	}
}
