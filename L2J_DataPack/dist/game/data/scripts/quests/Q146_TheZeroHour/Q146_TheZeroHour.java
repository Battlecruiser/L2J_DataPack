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
package quests.Q146_TheZeroHour;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * The Zero Hour (146)
 * @author Gnacik, malyelfik
 */
public class Q146_TheZeroHour extends Quest
{
	private static final String qn = "146_TheZeroHour";
	// Npc
	private static final int Kahman = 31554;
	private static final int QueenShyeed = 25671;
	// Item
	private static final int Fang = 14859;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equalsIgnoreCase("31554-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getLevel() < 81)
				{
					htmltext = "31554-02.htm";
				}
				else
				{
					final QuestState prev = player.getQuestState("109_InSearchOfTheNest");
					if ((prev != null) && prev.isCompleted())
					{
						htmltext = "31554-01a.htm";
					}
					else
					{
						htmltext = "31554-04.html";
					}
				}
				break;
			case State.STARTED:
				if (st.getInt("cond") == 1)
				{
					htmltext = "31554-06.html";
				}
				else
				{
					st.giveItems(14849, 1);
					st.addExpAndSp(154616, 12500);
					st.takeItems(Fang, 1);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(false);
					htmltext = "31554-05.html";
				}
				break;
			case State.COMPLETED:
				htmltext = "31554-01b.htm";
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, "1");
		if (partyMember == null)
		{
			return null;
		}
		QuestState st = partyMember.getQuestState(qn);
		if (!st.hasQuestItems(Fang))
		{
			st.giveItems(Fang, 1);
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		return null;
	}
	
	public Q146_TheZeroHour(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(Kahman);
		addTalkId(Kahman);
		addKillId(QueenShyeed);
		
		questItemIds = new int[]
		{
			Fang
		};
	}
	
	public static void main(String[] args)
	{
		new Q146_TheZeroHour(146, qn, "The Zero Hour");
	}
}
