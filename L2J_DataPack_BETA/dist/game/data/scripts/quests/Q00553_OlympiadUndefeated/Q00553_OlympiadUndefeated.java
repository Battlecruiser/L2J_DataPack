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
package quests.Q00553_OlympiadUndefeated;

import com.l2jserver.gameserver.enums.QuestType;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.olympiad.CompetitionType;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Olympiad Undefeated (553)<br>
 * @author lion
 * @since Nov. 5, 2011, improved by jurchiks
 * @version 2011-02-05 Based on official H5 PTS server and 551 quest ;)
 */
public class Q00553_OlympiadUndefeated extends Quest
{
	// NPC
	private static final int MANAGER = 31688;
	// Items
	private static final int WIN_CONF_2 = 17244;
	private static final int WIN_CONF_5 = 17245;
	private static final int WIN_CONF_10 = 17246;
	private static final int OLY_CHEST = 17169;
	private static final int MEDAL_OF_GLORY = 21874;
	
	public Q00553_OlympiadUndefeated()
	{
		super(553, Q00553_OlympiadUndefeated.class.getSimpleName(), "Olympiad Undefeated");
		addStartNpc(MANAGER);
		addTalkId(MANAGER);
		registerQuestItems(WIN_CONF_2, WIN_CONF_5, WIN_CONF_10);
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
			final long count = st.getQuestItemsCount(WIN_CONF_2) + st.getQuestItemsCount(WIN_CONF_5);
			
			if (count > 0)
			{
				st.giveItems(OLY_CHEST, count);
				if (count == 2)
				{
					st.giveItems(MEDAL_OF_GLORY, 3);
				}
				st.exitQuest(QuestType.DAILY, true);
			}
			else
			{
				htmltext = getNoQuestMsg(player); // missing items
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
			if ((st != null) && st.isStarted() && (st.isCond(1)))
			{
				st.unset("undefeatable");
				st.takeItems(WIN_CONF_2, -1);
				st.takeItems(WIN_CONF_5, -1);
				st.takeItems(WIN_CONF_10, -1);
			}
		}
	}
	
	@Override
	public void onOlympiadWin(L2PcInstance winner, CompetitionType type)
	{
		if (winner != null)
		{
			final QuestState st = winner.getQuestState(getName());
			if ((st != null) && st.isStarted() && (st.isCond(1)))
			{
				final int matches = st.getInt("undefeatable") + 1;
				st.set("undefeatable", String.valueOf(matches));
				switch (matches)
				{
					case 2:
						if (!st.hasQuestItems(WIN_CONF_2))
						{
							st.giveItems(WIN_CONF_2, 1);
						}
						break;
					case 5:
						if (!st.hasQuestItems(WIN_CONF_5))
						{
							st.giveItems(WIN_CONF_5, 1);
						}
						break;
					case 10:
						if (!st.hasQuestItems(WIN_CONF_10))
						{
							st.giveItems(WIN_CONF_10, 1);
							st.setCond(2);
						}
						break;
				}
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
		else
		{
			final long count = st.getQuestItemsCount(WIN_CONF_2) + st.getQuestItemsCount(WIN_CONF_5) + st.getQuestItemsCount(WIN_CONF_10);
			if ((count == 3) && st.isCond(2))
			{
				st.giveItems(OLY_CHEST, 4);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.exitQuest(QuestType.DAILY, true);
				htmltext = "31688-04.html";
			}
			else
			{
				htmltext = "31688-w" + count + ".html";
			}
		}
		return htmltext;
	}
}
