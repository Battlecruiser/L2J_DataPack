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
package ai.individual;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.holders.MinionHolder;

/**
 * Ragna Orc Seer AI.
 * @author Zealar
 */
public final class RagnaOrcSeer extends AbstractNpcAI
{
	private static final int RAGNA_ORC_SEER = 22697;
	
	private RagnaOrcSeer()
	{
		super(RagnaOrcSeer.class.getSimpleName(), "ai/individual");
		addSpawnId(RAGNA_ORC_SEER);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		
		if (getRandom(100) < 50)
		{
			spawnMinions(npc, "Privates1");
		}
		else
		{
			spawnMinions(npc, "Privates2");
		}
		return super.onSpawn(npc);
	}
	
	private void spawnMinions(final L2Npc npc, final String spawnName)
	{
		for (MinionHolder is : npc.getTemplate().getParameters().getMinionList(spawnName))
		{
			addMinion((L2MonsterInstance) npc, is.getId());
		}
	}
	
	public static void main(String[] args)
	{
		new RagnaOrcSeer();
	}
}
