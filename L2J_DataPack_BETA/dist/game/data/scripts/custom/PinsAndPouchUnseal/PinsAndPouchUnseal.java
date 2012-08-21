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
package custom.PinsAndPouchUnseal;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Pins And Pouch Unseal AI.<br>
 * Updated by Bloodshed.
 * @author Gigiikun
 */
public class PinsAndPouchUnseal extends Quest
{
	private final static int[] NPCs =
	{
		32610,32612
	};
	
	private final static int[] UNSEALPRICE = {3200,11800,26500,136600};
	// failed, low, mid, high, top
	private final static int[] CHANCES = {49,78,95,99,100};
	
	// sealdId, lowId, midId, highId, topId
	private final static int[][] PINS = {{13898,13902,13903,13904,13905},
		{13899,13906,13907,13908,13909},
		{13900,13910,13911,13912,13913},
		{13901,13914,13915,13916,13917}
	};
	
	// sealdId, lowId, midId, highId, topId
	private final static int[][] POUCHS = {{13918,13922,13923,13924,13925},
		{13919,13926,13927,13928,13929},
		{13920,13930,13931,13932,13933},
		{13921,13934,13935,13936,13937}
	};
	
	// "B,C grade" is the Magic Clip
	// "A,S grade" is the Magic Ornament
	// sealdId, lowId, midId, highId, topId
	private final static int[][] CLIPSORNAMENTS = {{14902,14906,14907,14908,14909},
		{14903,14910,14911,14912,14913},
		{14904,14914,14915,14916,14917},
		{14905,14918,14919,14920,14921}
	};
	
	public PinsAndPouchUnseal(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for (int id : NPCs)
		{
			addStartNpc(id);
			addTalkId(id);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		htmltext = event;
		if (event.contains("_grade_"))
		{
			int grade = Integer.parseInt(event.substring(0, 1));
			int price;
			int[] itemIds;
			if (event.endsWith("_pin"))
			{
				price = UNSEALPRICE[grade];
				itemIds = PINS[grade];
			}
			else if (event.endsWith("_pouch"))
			{
				price = UNSEALPRICE[grade];
				itemIds = POUCHS[grade];
			}
			else if (event.endsWith("_clip"))
			{
				price = UNSEALPRICE[grade];
				itemIds = CLIPSORNAMENTS[grade - 2];
			}
			else if (event.endsWith("_ornament"))
			{
				price = UNSEALPRICE[grade];
				itemIds = CLIPSORNAMENTS[grade];
			}
			else
				// this should not happen!
				return "";
			if (st.hasQuestItems(itemIds[0]))
			{
				if (st.getQuestItemsCount(57) > price)
				{
					htmltext = "";
					st.takeItems(57, price);
					st.takeItems(itemIds[0], 1);
					int rand = getRandom(100);
					if (rand < CHANCES[0])
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), NpcStringId.WHAT_A_PREDICAMENT_MY_ATTEMPTS_WERE_UNSUCCESSFUL));
					else if (rand < CHANCES[1])
						st.giveItems(itemIds[1], 1);
					else if (rand < CHANCES[2])
						st.giveItems(itemIds[2], 1);
					else if (rand < CHANCES[3])
						st.giveItems(itemIds[3], 1);
					else
						st.giveItems(itemIds[4], 1);
				}
				else
					htmltext = npc.getNpcId() + "-low.htm";
			}
			else
				htmltext = npc.getNpcId() + "-no.htm";
			st.exitQuest(true);
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		htmltext = npc.getNpcId() + "-1.htm";
		
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new PinsAndPouchUnseal(-1, "PinsAndPouchUnseal", "custom");
	}
}
