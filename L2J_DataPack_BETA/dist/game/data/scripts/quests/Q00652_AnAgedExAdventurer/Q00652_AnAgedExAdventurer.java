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
package quests.Q00652_AnAgedExAdventurer;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * An Aged Ex-Adventurer (652)
 * @author malyelfik
 */
public class Q00652_AnAgedExAdventurer extends Quest
{
	// NPCs
	private static final int TANTAN = 32012;
	private static final int SARA = 30180;
	// Items
	private static final int SOULSHOT_C = 1464;
	private static final int ENCHANT_ARMOR_D = 956;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		if (event.equals("32012-04.htm"))
		{
			if (st.getQuestItemsCount(SOULSHOT_C) < 100)
			{
				return "32012-05.htm";
			}
			
			st.startQuest();
			st.takeItems(SOULSHOT_C, 100);
			npc.deleteMe();
			htmltext = event;
		}
		else if (event.equals("32012-03.html"))
		{
			htmltext = event;
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case TANTAN:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= 46) ? "32012-01.htm" : "32012-01a.htm";
						break;
					case State.STARTED:
						htmltext = "32012-02.html";
						break;
				}
				break;
			case SARA:
				if (st.isStarted())
				{
					if (getRandom(10) <= 4)
					{
						st.giveItems(ENCHANT_ARMOR_D, 1);
						st.giveAdena(5026, true);
						htmltext = "30180-01.html";
					}
					else
					{
						st.giveAdena(10000, true);
						htmltext = "30180-02.html";
					}
					st.exitQuest(true, true);
				}
				break;
		}
		return htmltext;
	}
	
	public Q00652_AnAgedExAdventurer(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(TANTAN);
		addTalkId(TANTAN, SARA);
	}
	
	public static void main(String[] args)
	{
		new Q00652_AnAgedExAdventurer(652, Q00652_AnAgedExAdventurer.class.getSimpleName(), "An Aged Ex-Adventurer");
	}
}