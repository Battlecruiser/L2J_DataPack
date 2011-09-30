/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.group_template;

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.util.Rnd;

/**
 * This class manages the spawn of NPC's, having several random spawn points.
 * @author GKR
 */
public class RandomSpawn extends L2AttackableAIScript
{
	private static final Map<Integer, int[][]> SPAWN_POINTS = new FastMap<Integer, int[][]>();
	
	static
	{
		// Keltas
		SPAWN_POINTS.put(22341, new int[][]
		{
			{
				-27136, 250938, -3523
			},
			{
				-29658, 252897, -3523
			},
			{
				-27237, 251943, -3527
			},
			{
				-28868, 250113, -3479
			}
		});
		// Keymaster
		SPAWN_POINTS.put(22361, new int[][]
		{
			{
				14091, 250533, -1940
			},
			{
				15762, 252440, -2015
			},
			{
				19836, 256212, -2090
			},
			{
				21940, 254107, -2010
			},
			{
				17299, 252943, -2015
			}
		});
		// Typhoon
		SPAWN_POINTS.put(25539, new int[][]
		{
			{
				-20641, 255370, -3235
			},
			{
				-16157, 250993, -3058
			},
			{
				-18269, 250721, -3151
			},
			{
				-16532, 254864, -3223
			},
			{
				-19055, 253489, -3440
			},
			{
				-9684, 254256, -3148
			},
			{
				-6209, 251924, -3189
			},
			{
				-10547, 251359, -2929
			},
			{
				-7254, 254997, -3261
			},
			{
				-4883, 253171, -3322
			}
		});
		// Mutated Elpy
		SPAWN_POINTS.put(25604, new int[][]
		{
			{
				-46080, 246368, -14183
			},
			{
				-44816, 246368, -14183
			},
			{
				-44224, 247440, -14184
			},
			{
				-44896, 248464, -14183
			},
			{
				-46064, 248544, -14183
			},
			{
				-46720, 247424, -14183
			}
		});
	}
	
	public RandomSpawn(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		for (int npcId : SPAWN_POINTS.keySet())
		{
			addSpawnId(npcId);
		}
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		if (!npc.isTeleporting())
		{
			final int[][] spawnlist = SPAWN_POINTS.get(npc.getNpcId());
			final int[] spawn = spawnlist[Rnd.get(spawnlist.length)];
			if (!npc.isInsideRadius(spawn[0], spawn[1], spawn[2], 200, false, false))
			{
				npc.getSpawn().setLocx(spawn[0]);
				npc.getSpawn().setLocy(spawn[1]);
				npc.getSpawn().setLocz(spawn[2]);
				ThreadPoolManager.getInstance().scheduleGeneral(new Teleport(npc, spawn), 100);
			}
		}
		
		return super.onSpawn(npc);
	}
	
	private static class Teleport implements Runnable
	{
		private final L2Npc _npc;
		private final int[] _coords;
		
		public Teleport(L2Npc npc, int[] coords)
		{
			_npc = npc;
			_coords = coords;
		}
		
		@Override
		public void run()
		{
			_npc.teleToLocation(_coords[0], _coords[1], _coords[2]);
		}
	}
	
	public static void main(String[] args)
	{
		new RandomSpawn(-1, "RandomSpawn", "ai");
	}
}
