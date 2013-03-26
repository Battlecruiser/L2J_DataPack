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

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;

/**
 * See Through Silent Move AI.
 * @author Gigiikun
 */
public class SeeThroughSilentMove extends AbstractNpcAI
{
	//@formatter:off
	private static final int[] MONSTERS =
	{
		18001, 18002, 22199, 22215, 22216, 22217, 22327, 22746, 22747, 22748,
		22749, 22750, 22751, 22752, 22753, 22754, 22755, 22756, 22757, 22758,
		22759, 22760, 22761, 22762, 22763, 22764, 22765, 22794, 22795, 22796,
		22797, 22798, 22799, 22800, 22843, 22857, 25725, 25726, 25727, 29009,
		29010, 29011, 29012, 29013
	};
	//@formatter:on
	
	private SeeThroughSilentMove()
	{
		super(SeeThroughSilentMove.class.getSimpleName(), "ai/group_template");
		for (int npcId : MONSTERS)
		{
			for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(npcId))
			{
				final L2Npc npc = spawn.getLastSpawn();
				if ((npc != null) && npc.isL2Attackable())
				{
					((L2Attackable) npc).setSeeThroughSilentMove(true);
				}
			}
		}
		addSpawnId(MONSTERS);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc.isL2Attackable())
		{
			((L2Attackable) npc).setSeeThroughSilentMove(true);
		}
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new SeeThroughSilentMove();
	}
}
