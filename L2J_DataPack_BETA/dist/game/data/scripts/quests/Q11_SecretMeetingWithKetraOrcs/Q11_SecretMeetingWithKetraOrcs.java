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
package quests.Q11_SecretMeetingWithKetraOrcs;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Secret Meeting With Ketra Orcs (11).<br>
 * Original Jython script by Emperorc.
 * @author nonom
 */
public class Q11_SecretMeetingWithKetraOrcs extends Quest
{
	private static final String qn = "11_SecretMeetingWithKetraOrcs";
	
	// NPCs
	private static final int CADMON = 31296;
	private static final int LEON = 31256;
	private static final int WAHKAN = 31371;
	
	// Items
	private static final int BOX = 7231;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		final int cond = st.getInt("cond");
		switch (event)
		{
			case "31296-03.html":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				break;
			case "31256-02.html":
				if (cond == 1)
				{
					st.set("cond", "2");
					st.giveItems(BOX, 1);
					st.playSound("ItemSound.quest_middle");
				}
				break;
			case "31371-02.html":
				if ((cond == 2) && (st.hasQuestItems(BOX)))
				{
					st.takeItems(BOX, -1);
					st.addExpAndSp(233125, 18142);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(false);
				}
				else
				{
					htmltext = "31371-03.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				if (npcId == CADMON)
				{
					htmltext = (player.getLevel() >= 74) ? "31296-01.htm" : "31296-02.html";
				}
				break;
			case State.STARTED:
				if ((npcId == CADMON) && (cond == 1))
				{
					htmltext = "31296-04.html";
				}
				else if (npcId == LEON)
				{
					if (cond == 1)
					{
						htmltext = "31256-01.html";
						
					}
					else if (cond == 2)
					{
						htmltext = "31256-03.html";
					}
				}
				else if ((npcId == WAHKAN) && (cond == 2))
				{
					htmltext = "31371-01.html";
				}
				break;
		}
		return htmltext;
	}
	
	public Q11_SecretMeetingWithKetraOrcs(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(CADMON);
		
		addTalkId(CADMON, LEON, WAHKAN);
	}
	
	public static void main(String[] args)
	{
		new Q11_SecretMeetingWithKetraOrcs(11, qn, "Secret Meeting With Ketra Orcs");
	}
}
