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
package ai.npc.Teleports.CrumaTower;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Cruma Tower teleport AI.
 * @author Plim
 */
public class CrumaTower extends AbstractNpcAI
{
	private static final int MOZELLA = 30483;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getLevel() <= 55)
		{
			player.teleToLocation(17724, 114004, -11672);
			return null;
		}
		
		return "30483-1.htm";
	}
	
	private CrumaTower(String name, String descr)
	{
		super(name, descr);
		
		addStartNpc(MOZELLA);
		addTalkId(MOZELLA);
	}
	
	public static void main(String[] args)
	{
		new CrumaTower(CrumaTower.class.getSimpleName(), "ai/npc/Teleports/");
	}
}
