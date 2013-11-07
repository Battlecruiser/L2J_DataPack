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
package quests.Q00017_LightAndDarkness;

import quests.Q00015_SweetWhispers.Q00015_SweetWhispers;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Light And Darkness (17)<br>
 * Original jython script by disKret, Skeleton & DrLecter.
 * @author nonom
 */
public class Q00017_LightAndDarkness extends Quest
{
	// NPCs
	private static final int HIERARCH = 31517;
	private static final int SAINT_ALTAR_1 = 31508;
	private static final int SAINT_ALTAR_2 = 31509;
	private static final int SAINT_ALTAR_3 = 31510;
	private static final int SAINT_ALTAR_4 = 31511;
	// Item
	private static final int BLOOD_OF_SAINT = 7168;
	
	private Q00017_LightAndDarkness(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(HIERARCH);
		addTalkId(HIERARCH, SAINT_ALTAR_1, SAINT_ALTAR_2, SAINT_ALTAR_3, SAINT_ALTAR_4);
		registerQuestItems(BLOOD_OF_SAINT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31517-02.html":
				if (player.getLevel() >= 61)
				{
					st.startQuest();
					st.giveItems(BLOOD_OF_SAINT, 4);
				}
				else
				{
					htmltext = "31517-02a.html";
				}
				break;
			case "31508-02.html":
			case "31509-02.html":
			case "31510-02.html":
			case "31511-02.html":
				final int cond = st.getCond();
				final int npcId = Integer.parseInt(event.replace("-02.html", ""));
				if ((cond == (npcId - 31507)) && st.hasQuestItems(BLOOD_OF_SAINT))
				{
					htmltext = npcId + "-01.html";
					st.takeItems(BLOOD_OF_SAINT, 1);
					st.setCond(cond + 1, true);
				}
				break;
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
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				final QuestState st2 = player.getQuestState(Q00015_SweetWhispers.class.getSimpleName());
				htmltext = ((st2 != null) && (st2.isCompleted())) ? "31517-00.htm" : "31517-06.html";
				break;
			case State.STARTED:
				final long blood = st.getQuestItemsCount(BLOOD_OF_SAINT);
				final int npcId = npc.getId();
				switch (npcId)
				{
					case HIERARCH:
						if (st.getCond() < 5)
						{
							htmltext = (blood >= 5) ? "31517-05.html" : "31517-04.html";
						}
						else
						{
							st.addExpAndSp(697040, 54887);
							st.exitQuest(false, true);
							htmltext = "31517-03.html";
						}
						break;
					case SAINT_ALTAR_1:
					case SAINT_ALTAR_2:
					case SAINT_ALTAR_3:
					case SAINT_ALTAR_4:
						if ((npcId - 31507) == st.getCond())
						{
							htmltext = npcId + ((blood > 0) ? "-00.html" : "-02.html");
						}
						else if (st.getCond() > (npcId - 31507))
						{
							htmltext = npcId + "-03.html";
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00017_LightAndDarkness(17, Q00017_LightAndDarkness.class.getSimpleName(), "Light and Darkness");
	}
}
