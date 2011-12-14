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
package quests.Q10271_TheEnvelopingDarkness;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * The Enveloping Darkness (10271)
 * @author Gladicek
 */
public class Q10271_TheEnvelopingDarkness extends Quest
{
	private static final String qn = "10271_TheEnvelopingDarkness";
	
	private static final int ORBYU = 32560;
	private static final int EL = 32556;
	private static final int MEDIBAL_CORPSE = 32528;
	private static final int MEDIBAL_DOCUMENT = 13852;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == ORBYU)
		{
			switch (st.getState())
			{
				case State.CREATED:
					QuestState _prev = player.getQuestState("10269_ToTheSeedOfDestruction");
					if ((_prev != null) && _prev.isCompleted() && (player.getLevel() >= 75))
						htmltext = "32560-01.htm";
					else
						htmltext = "32560-02.htm";
					break;
				case State.STARTED:
					htmltext = "32560-05.htm";
					break;
				case State.COMPLETED:
					htmltext = "32560-03.htm";
					break;
			}
			if (st.getInt("cond") == 2)
			{
				htmltext = "32560-06.htm";
			}
			else if (st.getInt("cond") == 3)
			{
				htmltext = "32560-07.htm";
			}
			else if (st.getInt("cond") == 4)
			{
				htmltext = "32560-08.htm";
				st.unset("cond");
				st.setState(State.COMPLETED);
				st.giveItems(57, 62516);
				st.addExpAndSp(377403, 37867);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
			}
			
		}
		
		else if (npc.getNpcId() == EL)
		{
			if (st.isCompleted())
			{
				htmltext = "32556-02.htm";
			}
			if (st.getInt("cond") == 1)
			{
				htmltext = "32556-01.htm";
			}
			else if (st.getInt("cond") == 2)
			{
				htmltext = "32556-07.htm";
			}
			else if (st.getInt("cond") == 3)
			{
				htmltext = "32556-08.htm";
			}
			else if (st.getInt("cond") == 4)
			{
				htmltext = "32556-09.htm";
			}
		}
		
		else if (npc.getNpcId() == MEDIBAL_CORPSE)
		{
			switch (st.getState())
			{
				case State.COMPLETED:
					htmltext = "32528-02.htm";
					break;
			}
			if (st.getInt("cond") == 2)
			{
				htmltext = "32528-01.htm";
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "3");
				st.giveItems(MEDIBAL_DOCUMENT, 1);
			}
			else if (st.getInt("cond") == 3)
			{
				htmltext = "32528-03.htm";
			}
			else if (st.getInt("cond") == 4)
			{
				htmltext = "32528-03.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("32560-05.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32556-06.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32556-09.htm"))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
			st.takeItems(MEDIBAL_DOCUMENT, 1);
			
		}
		return htmltext;
	}
	
	public Q10271_TheEnvelopingDarkness(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ORBYU);
		addTalkId(ORBYU);
		addTalkId(EL);
		addTalkId(MEDIBAL_CORPSE);
		questItemIds = new int[]
		{
			MEDIBAL_DOCUMENT
		};
	}
	
	public static void main(String[] args)
	{
		new Q10271_TheEnvelopingDarkness(10271, qn, "The Enveloping Darkness");
	}
}
