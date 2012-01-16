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
 * Journey To Gracia (10267). Original jython script by Kerberos v1.0 on 2009/05/2
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
		QuestState st = player.getQuestState(qn);
		
		int npcId = npc.getNpcId();
		
		if (st == null)
		{
			return htmltext;
		}
		
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
					if (player.getLevel() < 75)
					{
						htmltext = "30857-00.htm";
					}
					else
					{
						htmltext = "30857-01.htm";
					}
				}
				break;
			case State.STARTED:
				if (npcId == ORVEN)
				{
					htmltext = "30857-07.htm";
				}
				else if (npcId == PAPIKU)
				{
					if (st.getInt("cond") == 1)
					{
						htmltext = "32564-01.htm";
					}
					else
					{
						htmltext = "32564-03.htm";
					}
				}
				else if (npcId == KEUCEREUS && st.getInt("cond") == 2)
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
		QuestState st = player.getQuestState(qn);
		
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
				st.giveItems(57, 92500);
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
		
		addTalkId(ORVEN);
		addTalkId(KEUCEREUS);
		addTalkId(PAPIKU);
		
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
