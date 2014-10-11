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

import com.l2jserver.gameserver.datatables.NpcData;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.holders.MinionHolder;
import com.l2jserver.util.Rnd;

/**
 * Ragna Orc Hero AI.
 * @author Zealar
 */
public final class RagnaOrcHero extends AbstractNpcAI
{
	private static final int RAGNA_ORC_HERO = 22693;
	
	private RagnaOrcHero()
	{
		super(RagnaOrcHero.class.getSimpleName(), "ai/individual");
		addSpawnId(RAGNA_ORC_HERO);
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(RAGNA_ORC_HERO))
		{
			onSpawn(spawn.getLastSpawn());
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc instanceof L2MonsterInstance)
		{
			L2MonsterInstance monster = (L2MonsterInstance) npc;
			
			if (!monster.hasMinions())
			{
				L2NpcTemplate template = NpcData.getInstance().getTemplate(RAGNA_ORC_HERO);
				
				if (Rnd.get(100) < 70)
				{
					if (template.getParameters().getMinionList("Privates1") != null)
					{
						for (MinionHolder is : template.getParameters().getMinionList("Privates1"))
						{
							addMinion((L2MonsterInstance) npc, is.getId());
						}
					}
				}
				else
				{
					if (template.getParameters().getMinionList("Privates2") != null)
					{
						for (MinionHolder is : template.getParameters().getMinionList("Privates2"))
						{
							addMinion((L2MonsterInstance) npc, is.getId());
						}
					}
				}
			}
		}
		
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new RagnaOrcHero();
	}
}
