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
package ai.npc.Teleports.Survivor;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;

/**
 * Gracia Survivor teleport AI.<br>
 * Original Jython script by Kerberos.
 * @author Plim
 */
public class Survivor extends AbstractNpcAI
{
	private static final int SURVIVOR = 32632;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ("32632-2.htm".equals(event))
		{
			if (player.getLevel() < 75)
			{
				event = "32632-3.htm";
			}
			else if (getQuestItemsCount(player, PcInventory.ADENA_ID) < 150000)
			{
				return event;
			}
			else
			{
				takeItems(player, PcInventory.ADENA_ID, 150000);
				player.teleToLocation(-149406, 255247, -80);
				return null;
			}
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		return "32632-1.htm";
	}
	
	private Survivor(String name, String descr)
	{
		super(name, descr);
		
		addStartNpc(SURVIVOR);
		addTalkId(SURVIVOR);
	}
	
	public static void main(String[] args)
	{
		new Survivor(Survivor.class.getSimpleName(), "ai/npc/Teleports/");
	}
}
