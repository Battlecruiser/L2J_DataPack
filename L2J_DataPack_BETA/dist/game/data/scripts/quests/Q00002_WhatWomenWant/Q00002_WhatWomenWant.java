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
package quests.Q00002_WhatWomenWant;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;

/**
 * What Women Want (2)
 * @author malyelfik
 */
public class Q00002_WhatWomenWant extends Quest
{
	// NPCs
	private static final int ARUJIEN = 30223;
	private static final int MIRABEL = 30146;
	private static final int HERBIEL = 30150;
	private static final int GREENIS = 30157;
	
	// Items
	private static final int ARUJIENS_LETTER1 = 1092;
	private static final int ARUJIENS_LETTER2 = 1093;
	private static final int ARUJIENS_LETTER3 = 1094;
	private static final int POETRY_BOOK = 689;
	private static final int GREENIS_LETTER = 693;
	private static final int EARRING = 113;
	
	// Misc
	private static final int MIN_LEVEL = 2;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30223-04.htm":
				st.startQuest();
				st.giveItems(ARUJIENS_LETTER1, 1);
				break;
			case "30223-08.html":
				st.takeItems(ARUJIENS_LETTER3, -1);
				st.giveItems(POETRY_BOOK, 1);
				st.setCond(4, true);
				break;
			case "30223-09.html":
				st.giveAdena(450, true);
				st.exitQuest(false, true);
				// Newbie Guide
				showOnScreenMsg(player, NpcStringId.DELIVERY_DUTY_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000);
				st.addExpAndSp(4254, 335);
				st.giveAdena(1850, true);
				break;
			case "30223-03.html":
				break;
			default:
				htmltext = null;
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
		
		switch (npc.getNpcId())
		{
			case ARUJIEN:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = ((player.getRace() != Race.Elf) && (player.getRace() != Race.Human)) ? "30223-00.htm" : (player.getLevel() >= MIN_LEVEL) ? "30223-02.htm" : "30223-01.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "30223-05.html";
								break;
							case 2:
								htmltext = "30223-06.html";
								break;
							case 3:
								htmltext = "30223-07.html";
								break;
							case 4:
								htmltext = "30223-10.html";
								break;
							case 5:
								st.giveItems(EARRING, 1);
								st.exitQuest(false, true);
								htmltext = "30223-11.html";
								// Newbie Guide
								showOnScreenMsg(player, NpcStringId.DELIVERY_DUTY_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000);
								st.addExpAndSp(4254, 335);
								st.giveAdena(1850, true);
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case MIRABEL:
				if (st.isStarted())
				{
					if (st.isCond(1))
					{
						st.setCond(2, true);
						st.takeItems(ARUJIENS_LETTER1, -1);
						st.giveItems(ARUJIENS_LETTER2, 1);
						htmltext = "30146-01.html";
					}
					else
					{
						htmltext = "30146-02.html";
					}
				}
				break;
			case HERBIEL:
				if (st.isStarted() && (st.getCond() > 1))
				{
					if (st.isCond(2))
					{
						st.setCond(3, true);
						st.takeItems(ARUJIENS_LETTER2, -1);
						st.giveItems(ARUJIENS_LETTER3, 1);
						htmltext = "30150-01.html";
					}
					else
					{
						htmltext = "30150-02.html";
					}
				}
				break;
			case GREENIS:
				if (st.isStarted())
				{
					if (st.isCond(4))
					{
						st.setCond(5, true);
						st.takeItems(POETRY_BOOK, -1);
						st.giveItems(GREENIS_LETTER, 1);
						htmltext = "30157-02.html";
					}
					else if (st.isCond(5))
					{
						htmltext = "30157-03.html";
					}
					else
					{
						htmltext = "30157-01.html";
					}
				}
				break;
		}
		return htmltext;
	}
	
	public Q00002_WhatWomenWant(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ARUJIEN);
		addTalkId(ARUJIEN, MIRABEL, HERBIEL, GREENIS);
		
		registerQuestItems(ARUJIENS_LETTER1, ARUJIENS_LETTER2, ARUJIENS_LETTER3, POETRY_BOOK, GREENIS_LETTER);
	}
	
	public static void main(String[] args)
	{
		new Q00002_WhatWomenWant(2, Q00002_WhatWomenWant.class.getSimpleName(), "What Women Want");
	}
}