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

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.holders.MinionHolder;
import com.l2jserver.util.Rnd;

/**
 * Grove Robber's AI.<br>
 * <ul>
 * <li>Grove Robber Summoner</li>
 * <li>Grove Robber Megician</li>
 * </ul>
 * @author Zealar
 */
public final class GraveRobbers extends AbstractNpcAI
{
	private static final int GRAVE_ROBBER_SUMMONER = 22678;
	private static final int GRAVE_ROBBER_MEGICIAN = 22679;
	
	private GraveRobbers()
	{
		super(GraveRobbers.class.getSimpleName(), "ai/individual");
		addSpawnId(GRAVE_ROBBER_SUMMONER, GRAVE_ROBBER_MEGICIAN);
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(GRAVE_ROBBER_MEGICIAN))
		{
			onSpawn(spawn.getLastSpawn());
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc instanceof L2MonsterInstance)
		{
			if (!((L2MonsterInstance) npc).hasMinions())
			{
				L2NpcTemplate template = npc.getTemplate();
				
				if (Rnd.get(2) == 0)
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
		new GraveRobbers();
	}
}
