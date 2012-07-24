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
package quests.Q10268_ToTheSeedOfInfinity;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * To the Seed of Infinity (10268).<br>
 * Original Jython script by Kerberos.
 * @author nonom
 */
public class Q10268_ToTheSeedOfInfinity extends Quest
{
	private static final String qn = "10268_ToTheSeedOfInfinity";
	
	// NPCs
	private static final int KEUCEREUS = 32548;
	private static final int TEPIOS = 32603;
	
	// Items
	private static final int INTRODUCTION = 13811;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case KEUCEREUS:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() < 75) ? "32548-00.html" : "32548-01.htm";
						break;
					case State.STARTED:
						htmltext = "32548-06.html";
						break;
					case State.COMPLETED:
						htmltext = "32548-0a.html";
						break;
				}
				break;
			case TEPIOS:
				switch (st.getState())
				{
					case State.STARTED:
						htmltext = "32530-01.html";
						st.giveAdena(16671, true);
						st.addExpAndSp(100640, 10098);
						st.exitQuest(false, true);
						break;
					case State.COMPLETED:
						htmltext = "32530-02.html";
						break;
					default:
						break;
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equals("32548-05.html"))
		{
			st.startQuest();
			st.giveItems(INTRODUCTION, 1);
		}
		return event;
	}
	
	public Q10268_ToTheSeedOfInfinity(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KEUCEREUS);
		addTalkId(KEUCEREUS, TEPIOS);
		questItemIds = new int[]
		{
			INTRODUCTION
		};
	}
	
	public static void main(String[] args)
	{
		new Q10268_ToTheSeedOfInfinity(10268, qn, "To the Seed of Infinity");
	}
}
