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
package ai.individual;

import javolution.util.FastSet;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.instancemanager.WalkingManager;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;

/**
 * Mesmer Drake AI.
 * @author Adry_85
 */
public class MesmerDrake extends AbstractNpcAI
{
	private static final int MESMER_DRAKE_ID = 22820;
	
	private static final int[] ROUTE_ID =
	{
		17,
		18,
		19
	};
	
	private MesmerDrake(String name, String descr)
	{
		super(name, descr);
		addSpawnId(MESMER_DRAKE_ID);
		
		FastSet<L2Spawn> spawns = SpawnTable.getInstance().getSpawnTable();
		for (L2Spawn spawn : spawns)
		{
			if (spawn.getNpcid() == MESMER_DRAKE_ID)
			{
				onSpawn(spawn.getLastSpawn());
			}
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		for (int route : ROUTE_ID)
		{
			WalkingManager.getInstance().startMoving(npc, route);
		}
		
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new MesmerDrake(MesmerDrake.class.getSimpleName(), "ai");
	}
}
