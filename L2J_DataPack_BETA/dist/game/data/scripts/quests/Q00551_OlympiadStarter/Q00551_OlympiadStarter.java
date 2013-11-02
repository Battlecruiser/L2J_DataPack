/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00551_OlympiadStarter;

import com.l2jserver.gameserver.enums.QuestType;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.olympiad.CompetitionType;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Olympiad Starter (551)<br>
 * @author Gnacik
 * @since Nov. 5, 2011, improved by jurchiks
 * @version 2011-02-04 Based on official H5 PTS server
 */
public class Q00551_OlympiadStarter extends Quest
{
	private static final int MANAGER = 31688;
	
	private static final int CERT_3 = 17238;
	private static final int CERT_5 = 17239;
	private static final int CERT_10 = 17240;
	
	private static final int OLY_CHEST = 17169;
	private static final int MEDAL_OF_GLORY = 21874;
	
	public Q00551_OlympiadStarter(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(MANAGER);
		addTalkId(MANAGER);
		registerQuestItems(CERT_3, CERT_5, CERT_10);
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
			st.startQuest();
		}
		else if (event.equalsIgnoreCase("31688-04.html"))
		{
			final long count = getQuestItemsCount(player, CERT_3) + getQuestItemsCount(player, CERT_5);
			if (count > 0)
			{
				giveItems(player, OLY_CHEST, count); // max 2
				if (count == 2)
				{
					giveItems(player, MEDAL_OF_GLORY, 3);
				}
				st.exitQuest(QuestType.DAILY, true);
			}
			else
			{
				htmltext = super.getNoQuestMsg(player); // missing items
			}
		}
		return htmltext;
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
							giveItems(loser, CERT_3, 1);
						}
						break;
					case 5:
						if (!st.hasQuestItems(CERT_5))
						{
							giveItems(loser, CERT_5, 1);
						}
						break;
					case 10:
						if (!st.hasQuestItems(CERT_10))
						{
							giveItems(loser, CERT_10, 1);
						}
						break;
				}
				st.set("matches", String.valueOf(matches));
			}
		}
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
							giveItems(winner, CERT_3, 1);
						}
						break;
					case 5:
						if (!st.hasQuestItems(CERT_5))
						{
							giveItems(winner, CERT_5, 1);
						}
						break;
					case 10:
						if (!st.hasQuestItems(CERT_10))
						{
							giveItems(winner, CERT_10, 1);
						}
						break;
				}
				st.set("matches", String.valueOf(matches));
			}
		}
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
			if (st.isNowAvailable())
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
			final long count = getQuestItemsCount(player, CERT_3) + getQuestItemsCount(player, CERT_5) + getQuestItemsCount(player, CERT_10);
			if (count == 3)
			{
				htmltext = "31688-04.html";
				giveItems(player, OLY_CHEST, 4);
				giveItems(player, MEDAL_OF_GLORY, 5);
				st.exitQuest(QuestType.DAILY, true);
			}
			else
			{
				htmltext = "31688-s" + count + ".html";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00551_OlympiadStarter(551, Q00551_OlympiadStarter.class.getSimpleName(), "Olympiad Starter");
	}
}
