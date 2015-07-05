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
package hellbound.AI;

import hellbound.HellboundEngine;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;

/**
 * Manages Naia's cast on the Hellbound Core
 * @author GKR
 */
public final class HellboundCore extends AbstractNpcAI
{
	// NPCs
	private static final int NAIA = 18484;
	private static final int HELLBOUND_CORE = 32331;
	// Skills
	private static SkillHolder BEAM = new SkillHolder(5493, 1);
	
	public HellboundCore()
	{
		super(HellboundCore.class.getSimpleName(), "hellbound/AI");
		addSpawnId(HELLBOUND_CORE, NAIA);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("cast") && (HellboundEngine.getInstance().getLevel() <= 6))
		{
			for (L2Character naia : npc.getKnownList().getKnownCharactersInRadius(900))
			{
				if ((naia != null) && naia.isMonster() && (naia.getId() == NAIA) && !naia.isDead() && !naia.isChanneling())
				{
					naia.setTarget(npc);
					naia.doSimultaneousCast(BEAM.getSkill());
				}
			}
			startQuestTimer("cast", 10000, npc, null);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		if (npc.getId() == NAIA)
		{
			npc.setIsNoRndWalk(true);
		}
		else
		{
			startQuestTimer("cast", 10000, npc, null);
		}
		
		return super.onSpawn(npc);
	}
}