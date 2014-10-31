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
 * Ragna Orc Commander AI.
 * @author Zealar
 */
public final class RagnaOrcCommander extends AbstractNpcAI
{
	private static final int RAGNA_ORC_COMMANDER = 22694;
	
	private RagnaOrcCommander()
	{
		super(RagnaOrcCommander.class.getSimpleName(), "ai/individual");
		addSpawnId(RAGNA_ORC_COMMANDER);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		spawnMinions(npc, "Privates1");
		if (getRandom(100) < 50)
		{
			spawnMinions(npc, "Privates2");
		}
		else
		{
			spawnMinions(npc, "Privates3");
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
		new RagnaOrcCommander();
	}
}
