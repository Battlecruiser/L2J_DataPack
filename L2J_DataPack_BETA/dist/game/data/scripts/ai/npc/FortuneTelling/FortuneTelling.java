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
package ai.npc.FortuneTelling;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;

/**
 * Fortune Telling AI.<br>
 * Original Jython script by Kerberos.
 * @authors Nyaran
 */
public class FortuneTelling extends AbstractNpcAI
{
	private static final int NPC_ID = 32616;
	private static final int COST = 1000;
	
	public FortuneTelling(String name, String descr)
	{
		super(name, descr);
		addStartNpc(NPC_ID);
		addTalkId(NPC_ID);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		if (player.getAdena() < COST)
		{
			htmltext = "lowadena.htm";
		}
		else
		{
			takeItems(player, PcInventory.ADENA_ID, COST);
			htmltext = getHtm(player.getHtmlPrefix(), "fortune.htm").replace("%fortune%", "<fstring>" + (1800309 + getRandom(386)) + "</fstring>");
		}
		return htmltext;
	}
	
	public static void main(String args[])
	{
		new FortuneTelling(FortuneTelling.class.getSimpleName(), "ai/npc");
	}
}