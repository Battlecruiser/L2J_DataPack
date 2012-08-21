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

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Sandstorms AI.
 * @author Ectis
 */
public class Sandstorms extends L2AttackableAIScript
{
	private static final int SANDSTORM = 32350;
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (npc.getNpcId() == SANDSTORM)
		{
			npc.setTarget(player);
			npc.doCast(SkillTable.getInstance().getInfo(5435, 1));
		}
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	public Sandstorms(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addAttackId(SANDSTORM);
	}
	
	public static void main(String[] args)
	{
		new Sandstorms(-1, Sandstorms.class.getSimpleName(), "ai");
	}
}
