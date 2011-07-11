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
package teleports.NewbieTravelToken;

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Plim
 * Original python script by DrLecter
 */
public class NewbieTravelToken extends Quest
{
	private static final int TOKEN = 8542;
	
	private static final Map<Integer, int[]> DATA = new FastMap<Integer, int[]>();
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
			st = newQuestState(player);
		
		try
		{
			if (DATA.keySet().contains(new Integer(event)))
			{
				if (st.getQuestItemsCount(TOKEN) > 0)
				{
		             st.takeItems(TOKEN,1);
		             st.getPlayer().teleToLocation(DATA.get(event)[0], DATA.get(event)[1], DATA.get(event)[2]);
				}
				
				else
				{
					 st.exitQuest(true);
		             player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.INCORRECT_ITEM_COUNT));
		             return "";
				}
			}
		}
		catch(NumberFormatException e)
		{
			//not a number
		}
		
		return event;
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return null;
		
		if (player.getLevel() >= 20)
		{
		     htmltext="cant-travel.htm";
		     st.exitQuest(true);
		}
		
		else
			htmltext = String.valueOf(npc.getNpcId())+".htm";
		
		return htmltext;
	}

	public NewbieTravelToken(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		// Initialize Map
		DATA.put(30600, new int[]{12160, 16554,-4583}); //DE
		DATA.put(30601, new int[]{115594,-177993, -912}); //DW
		DATA.put(30599, new int[]{45470,  48328,-3059}); //EV
		DATA.put(30602, new int[]{-45067,-113563, -199}); //OV
		DATA.put(30598, new int[]{-84053, 243343,-3729}); //TI
		DATA.put(32135, new int[]{-119712, 44519,368}); //SI
		
		for (int npcId : DATA.keySet())
		{
			addStartNpc(npcId);
			addTalkId(npcId);
		}
	}
	
	public static void main(String[] args)
	{
		new NewbieTravelToken(-1, NewbieTravelToken.class.getSimpleName(), "teleports");
	}
}
