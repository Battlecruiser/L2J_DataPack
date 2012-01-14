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
package quests.Q10269_ToTheSeedOfDestruction;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * To the Seed of Destruction (10269). Original jython script by Kerberos v1.0 on 2009/05/1
 * @author nonom
 */
public class Q10269_ToTheSeedOfDestruction extends Quest
{
	private static final String qn = "10269_ToTheSeedOfDestruction";
	
	// NPCs
	private static final int KEUCEREUS = 32548;
	private static final int ALLENOS = 32526;
	
	// Items
	private static final int INTRODUCTION = 13812;
	
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
				if (npcId == ALLENOS)
				{
					htmltext = "32526-02.htm";
				}
				else
				{
					htmltext = "32548-0a.htm";
				}
				break;
			case State.CREATED:
				if (npcId == KEUCEREUS)
				{
					if (player.getLevel() < 75)
					{
						htmltext = "32548-00.htm";
					}
					else
					{
						htmltext = "32548-01.htm";
					}
				}
				break;
			case State.STARTED:
				if (npcId == KEUCEREUS)
				{
					htmltext = "32548-06.htm";
				}
				else if (npcId == ALLENOS)
				{
					htmltext = "32526-01.htm";
					st.giveItems(57, 29174);
					st.addExpAndSp(176121, 7671);
					st.exitQuest(false);
					st.playSound("ItemSound.quest_finish");
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
		
		if (event.equalsIgnoreCase("32548-05.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			st.giveItems(INTRODUCTION, 1);
		}
		return htmltext;
	}
	
	public Q10269_ToTheSeedOfDestruction(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(KEUCEREUS);
		addTalkId(KEUCEREUS);
		addTalkId(ALLENOS);
		
		questItemIds = new int[]
		{
			INTRODUCTION
		};
	}
	
	public static void main(String[] args)
	{
		new Q10269_ToTheSeedOfDestruction(10269, qn, "To the Seed of Destruction");
	}
}
