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

import java.util.Arrays;

import javolution.util.FastSet;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.instancemanager.WalkingManager;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.util.Rnd;

/**
 * Drake Walkers AI.
 * @author UnAfraid
 */
public class DrakeWalkers extends AbstractNpcAI
{
	private static final int[] DRAKES =
	{
		22848,
		22849,
		22850,
		22851,
		22857
	};
	
	private static final int[] ROUTE_ID =
	{
		11,
		12,
		13
	};
	
	private DrakeWalkers(String name, String descr)
	{
		super(name, descr);
		addSpawnId(DRAKES);
		
		FastSet<L2Spawn> spawns = SpawnTable.getInstance().getSpawnTable();
		for (L2Spawn spawn : spawns)
		{
			if (Arrays.binarySearch(DRAKES, spawn.getNpcid()) >= 0)
			{
				onSpawn(spawn.getLastSpawn());
			}
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		WalkingManager.getInstance().startMoving(npc, ROUTE_ID[Rnd.get(ROUTE_ID.length)]);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new DrakeWalkers(DrakeWalkers.class.getSimpleName(), "ai");
	}
}
