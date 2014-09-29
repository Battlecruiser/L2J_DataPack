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
package quests.Q00124_MeetingTheElroki;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Meeting the Elroki (124)
 * @author Adry_85
 */
public class Q00124_MeetingTheElroki extends Quest
{
	// NPCs
	private static final int MARQUEZ = 32113;
	private static final int MUSHIKA = 32114;
	private static final int ASAMAH = 32115;
	private static final int KARAKAWEI = 32117;
	private static final int MANTARASA = 32118;
	// Item
	private static final int MANTARASA_EGG = 8778;
	
	public Q00124_MeetingTheElroki()
	{
		super(124, Q00124_MeetingTheElroki.class.getSimpleName(), "Meeting the Elroki");
		addStartNpc(MARQUEZ);
		addTalkId(MARQUEZ, MUSHIKA, ASAMAH, KARAKAWEI, MANTARASA);
		registerQuestItems(MANTARASA_EGG);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "32113-03.html":
				st.startQuest();
				break;
			case "32113-04.html":
				if (st.isCond(1))
				{
					st.setCond(2, true);
				}
				break;
			case "32114-04.html":
				if (st.isCond(2))
				{
					st.setCond(3, true);
				}
				break;
			case "32115-06.html":
				if (st.isCond(3))
				{
					st.setCond(4, true);
				}
				break;
			case "32117-05.html":
				if (st.isCond(4))
				{
					st.setCond(5, true);
				}
				break;
			case "32118-04.html":
				if (st.isCond(5))
				{
					st.giveItems(MANTARASA_EGG, 1);
					st.setCond(6, true);
				}
				break;
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case MARQUEZ:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() < 75) ? "32113-01a.htm" : "32113-01.htm";
						break;
					case State.STARTED:
						switch (st.getInt("cond"))
						{
							case 1:
								htmltext = "32113-05.html";
								break;
							case 2:
								htmltext = "32113-06.html";
								break;
							case 3:
							case 4:
							case 5:
								htmltext = "32113-07.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case MUSHIKA:
				if (st.isStarted())
				{
					switch (st.getInt("cond"))
					{
						case 1:
							htmltext = "32114-01.html";
							break;
						case 2:
							htmltext = "32114-02.html";
							break;
						default:
							htmltext = "32114-03.html";
							break;
					}
					break;
				}
				break;
			case ASAMAH:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
						case 2:
							htmltext = "32115-01.html";
							break;
						case 3:
							htmltext = "32115-02.html";
							break;
						case 4:
							htmltext = "32115-07.html";
							break;
						case 5:
							htmltext = "32115-08.html";
							break;
						case 6:
							if (st.hasQuestItems(MANTARASA_EGG))
							{
								htmltext = "32115-09.html";
								st.giveAdena(100013, true);
								st.addExpAndSp(301922, 30294);
								st.exitQuest(false, true);
							}
							break;
					}
				}
				break;
			case KARAKAWEI:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
						case 2:
						case 3:
							htmltext = "32117-01.html";
							break;
						case 4:
							htmltext = "32117-02.html";
							break;
						case 5:
							htmltext = "32117-07.html";
							break;
						case 6:
							htmltext = "32117-06.html";
							break;
					}
				}
				break;
			case MANTARASA:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
						case 2:
						case 3:
						case 4:
							htmltext = "32118-01.html";
							break;
						case 5:
							htmltext = "32118-03.html";
							break;
						case 6:
							htmltext = "32118-02.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
}
