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
package quests.Q551_OlympiadStarter;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.olympiad.CompetitionType;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Olympiad Starter (551).<br>
 * Based on official H5 PTS server.
 * @since Nov. 5, 2011, improved by jurchiks.
 * @version 2011-02-04
 * @author Gnacik
 */
public class Q551_OlympiadStarter extends Quest
{
	private static final String qn = "551_OlympiadStarter";
	
	private static final int MANAGER = 31688;
	
	private static final int CERT_3 = 17238;
	private static final int CERT_5 = 17239;
	private static final int CERT_10 = 17240;
	
	private static final int OLY_CHEST = 17169;
	private static final int MEDAL_OF_GLORY = 21874;
	
	public Q551_OlympiadStarter(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MANAGER);
		addTalkId(MANAGER);
		questItemIds = new int[]
		{
			CERT_3,
			CERT_5,
			CERT_10
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
			final long count = st.getQuestItemsCount(CERT_3) + st.getQuestItemsCount(CERT_5);
			if (count > 0)
			{
				st.giveItems(OLY_CHEST, count); // max 2
				if (count == 2)
				{
					st.giveItems(MEDAL_OF_GLORY, 3);
				}
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(QuestType.DAILY);
			}
			else
			{
				htmltext = super.getNoQuestMsg(player); // missing items
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
			if(st.isNowAvailable())
			{
				st.setState(State.CREATED);
				if ((player.getLevel() < 75) || !player.isNoble())
				{
					htmltext = "31688-00.htm";
				}
			}
			else
			{
				htmltext = "31688-05.html";
			}
		}
		else if (st.isStarted())
		{
			final long count = st.getQuestItemsCount(CERT_3) + st.getQuestItemsCount(CERT_5) + st.getQuestItemsCount(CERT_10);
			if (count == 3)
			{
				htmltext = "31688-04.html"; // reusing the same html
				st.giveItems(OLY_CHEST, 4);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(QuestType.DAILY);
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
				final int matches = st.getInt("matches") + 1;
				switch (matches)
				{
					case 3:
						if (!st.hasQuestItems(CERT_3))
						{
							st.giveItems(CERT_3, 1);
						}
						break;
					case 5:
						if (!st.hasQuestItems(CERT_5))
						{
							st.giveItems(CERT_5, 1);
						}
						break;
					case 10:
						if (!st.hasQuestItems(CERT_10))
						{
							st.giveItems(CERT_10, 1);
						}
						break;
				}
				st.set("matches", String.valueOf(matches));
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
				final int matches = st.getInt("matches") + 1;
				switch (matches)
				{
					case 3:
						if (!st.hasQuestItems(CERT_3))
						{
							st.giveItems(CERT_3, 1);
						}
						break;
					case 5:
						if (!st.hasQuestItems(CERT_5))
						{
							st.giveItems(CERT_5, 1);
						}
						break;
					case 10:
						if (!st.hasQuestItems(CERT_10))
						{
							st.giveItems(CERT_10, 1);
						}
						break;
				}
				st.set("matches", String.valueOf(matches));
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Q551_OlympiadStarter(551, qn, "Olympiad Starter");
	}
}
