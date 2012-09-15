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

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class NonLethalableNpcs extends AbstractNpcAI
{
	private static final int[] NPCS = new int[]
	{
		35062, // Headquarters
	};
	
	/**
	 * @param name
	 * @param descr
	 */
	public NonLethalableNpcs(String name, String descr)
	{
		super(name, descr);
		addSpawnId(NPCS);
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawnTable())
		{
			if (Util.contains(NPCS, spawn.getNpcid()))
			{
				onSpawn(spawn.getLastSpawn());
			}
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setLethalable(false);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new NonLethalableNpcs(NonLethalableNpcs.class.getSimpleName(), "ai/group_template");
	}
}
