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
package quests.Q552_OlympiadVeteran;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.olympiad.CompetitionType;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Based on official H5 PTS server and 551 quest ;)
 * @since Nov. 5, 2011, improved by jurchiks.
 * @version 2011-02-05
 * @author lion
 */
public class Q552_OlympiadVeteran extends Quest
{
	private static final String qn = "552_OlympiadVeteran";
	
	private static final int MANAGER = 31688;
	
	private static final int Team_Event_Certificate = 17241;
	private static final int Class_Free_Battle_Certificate = 17242;
	private static final int Class_Battle_Certificate = 17243;
	
	private static final int OLY_CHEST = 17169;
	
	public Q552_OlympiadVeteran(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MANAGER);
		addTalkId(MANAGER);
		questItemIds = new int[]
		{
			Team_Event_Certificate,
			Class_Free_Battle_Certificate,
			Class_Battle_Certificate
		};
		setOlympiadUse(true);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		String htmltext = event;
		
		if (event.equalsIgnoreCase("31688-03.html"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("31688-04.html"))
		{
			final long count = st.getQuestItemsCount(Team_Event_Certificate) + st.getQuestItemsCount(Class_Free_Battle_Certificate) + st.getQuestItemsCount(Class_Battle_Certificate);
			
			if (count > 0)
			{
				st.giveItems(OLY_CHEST, count);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
			}
			else
			{
				htmltext = getNoQuestMsg(player); // missing items
			}
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
		
		if ((player.getLevel() < 75) || !player.isNoble())
		{
			htmltext = "31688-00.htm";
		}
		else if (st.isCreated())
		{
			htmltext = "31688-01.htm";
		}
		else if (st.isCompleted())
		{
			htmltext = "31688-05.html";
		}
		else if (st.isStarted())
		{
			final long count = st.getQuestItemsCount(Team_Event_Certificate) + st.getQuestItemsCount(Class_Free_Battle_Certificate) + st.getQuestItemsCount(Class_Battle_Certificate);
			
			if (count == 3)
			{
				htmltext = "31688-04.html"; // reusing the same html
				st.giveItems(OLY_CHEST, 4);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
			}
			else
			{
				htmltext = "31688-s" + count + ".html";
			}
		}
		return htmltext;
	}
	
	@Override
	public void onOlympiadWin(L2PcInstance winner, CompetitionType type)
	{
		if (winner != null)
		{
			final QuestState st = winner.getQuestState(getName());
			if ((st != null) && st.isStarted())
			{
				int matches;
				switch (type)
				{
					case CLASSED:
					{
						matches = st.getInt("classed") + 1;
						st.set("classed", String.valueOf(matches));
						if ((matches == 5) && !st.hasQuestItems(Class_Battle_Certificate))
						{
							st.giveItems(Class_Battle_Certificate, 1);
						}
						break;
					}
					case NON_CLASSED:
					{
						matches = st.getInt("nonclassed") + 1;
						st.set("nonclassed", String.valueOf(matches));
						if ((matches == 5) && !st.hasQuestItems(Class_Free_Battle_Certificate))
						{
							st.giveItems(Class_Free_Battle_Certificate, 1);
						}
						break;
					}
					case TEAMS:
					{
						matches = st.getInt("teams") + 1;
						st.set("teams", String.valueOf(matches));
						if ((matches == 5) && !st.hasQuestItems(Team_Event_Certificate))
						{
							st.giveItems(Team_Event_Certificate, 1);
						}
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void onOlympiadLose(L2PcInstance loser, CompetitionType type)
	{
		if (loser != null)
		{
			final QuestState st = loser.getQuestState(getName());
			if ((st != null) && st.isStarted())
			{
				int matches;
				switch (type)
				{
					case CLASSED:
					{
						matches = st.getInt("classed") + 1;
						st.set("classed", String.valueOf(matches));
						if (matches == 5)
						{
							st.giveItems(Class_Battle_Certificate, 1);
						}
						break;
					}
					case NON_CLASSED:
					{
						matches = st.getInt("nonclassed") + 1;
						st.set("nonclassed", String.valueOf(matches));
						if (matches == 5)
						{
							st.giveItems(Class_Free_Battle_Certificate, 1);
						}
						break;
					}
					case TEAMS:
					{
						matches = st.getInt("teams") + 1;
						st.set("teams", String.valueOf(matches));
						if (matches == 5)
						{
							st.giveItems(Team_Event_Certificate, 1);
						}
						break;
					}
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Q552_OlympiadVeteran(552, qn, "Olympiad Veteran");
	}
}
