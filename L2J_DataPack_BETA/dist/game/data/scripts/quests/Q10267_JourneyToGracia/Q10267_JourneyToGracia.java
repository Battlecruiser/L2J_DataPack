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
package quests.Q10267_JourneyToGracia;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Journey To Gracia (10267).<br>
 * Original jython script by Kerberos v1.0 on 2009/05/2
 * @author nonom
 */
public class Q10267_JourneyToGracia extends Quest
{
	private static final String qn = "10267_JourneyToGracia";
	
	// NPCs
	private static final int ORVEN = 30857;
	private static final int KEUCEREUS = 32548;
	private static final int PAPIKU = 32564;
	
	// Items
	private static final int LETTER = 13810;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		final int npcId = npc.getNpcId();
		switch (st.getState())
		{
			case State.COMPLETED:
				if (npcId == KEUCEREUS)
				{
					htmltext = "32548-03.htm";
				}
				else if (npcId == ORVEN)
				{
					htmltext = "30857-0a.htm";
				}
				break;
			case State.CREATED:
				if (npcId == ORVEN)
				{
					htmltext = (player.getLevel() < 75) ? "30857-00.htm" : "30857-01.htm";
				}
				break;
			case State.STARTED:
				final int cond = st.getInt("cond");
				if (npcId == ORVEN)
				{
					htmltext = "30857-07.htm";
				}
				else if (npcId == PAPIKU)
				{
					htmltext = (cond == 1) ? "32564-01.htm" : "32564-03.htm";
				}
				else if ((npcId == KEUCEREUS) && (cond == 2))
				{
					htmltext = "32548-01.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "30857-06.htm":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				st.giveItems(LETTER, 1);
				break;
			case "32564-02.htm":
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
				break;
			case "32548-02.htm":
				st.giveAdena(92500, false);
				st.addExpAndSp(75480, 7570);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
				break;
		}
		return htmltext;
	}
	
	public Q10267_JourneyToGracia(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ORVEN);
		
		addTalkId(ORVEN, KEUCEREUS, PAPIKU);
		
		questItemIds = new int[]
		{
			LETTER
		};
	}
	
	public static void main(String[] args)
	{
		new Q10267_JourneyToGracia(10267, qn, "Journey to Gracia");
	}
}
