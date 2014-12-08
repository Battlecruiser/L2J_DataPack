/*
 * Copyright (C) 2004-2014 L2J DataPack
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
import com.l2jserver.gameserver.model.olympiad.Participant;
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
	
	public Q00551_OlympiadStarter()
	{
		super(551, Q00551_OlympiadStarter.class.getSimpleName(), "Olympiad Starter");
		addStartNpc(MANAGER);
		addTalkId(MANAGER);
		registerQuestItems(CERT_3, CERT_5, CERT_10);
		addOlympiadMatchFinishId();
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
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
			final long count = st.getQuestItemsCount(CERT_3) + st.getQuestItemsCount(CERT_5);
			if (count > 0)
			{
				st.giveItems(OLY_CHEST, count); // max 2
				if (count == 2)
				{
					st.giveItems(MEDAL_OF_GLORY, 3);
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
			final QuestState st = getQuestState(loser, false);
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
	public void onOlympiadMatchFinish(Participant winner, Participant looser, CompetitionType type)
	{
		if (winner != null)
		{
			final L2PcInstance player = winner.getPlayer();
			if (player == null)
			{
				return;
			}
			final QuestState st = getQuestState(player, false);
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
		
		if (looser != null)
		{
			final L2PcInstance player = looser.getPlayer();
			if (player == null)
			{
				return;
			}
			final QuestState st = getQuestState(player, false);
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
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
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
			final long count = st.getQuestItemsCount(CERT_3) + st.getQuestItemsCount(CERT_5) + st.getQuestItemsCount(CERT_10);
			if (count == 3)
			{
				htmltext = "31688-04.html";
				st.giveItems(OLY_CHEST, 4);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.exitQuest(QuestType.DAILY, true);
			}
			else
			{
				htmltext = "31688-s" + count + ".html";
			}
		}
		return htmltext;
	}
}
