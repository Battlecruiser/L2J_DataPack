/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package quests.Q10738_AnInnerBeauty;

import quests.Q10737_GrakonsWarehouse.Q10737_GrakonsWarehouse;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Sdw
 */
public class Q10738_AnInnerBeauty extends Quest
{
	// NPC's
	private static final int GRAKON = 33947;
	private static final int EVNA = 33935;
	// Misc
	private static final int MIN_LEVEL = 5;
	private static final int MAX_LEVEL = 20;
	// Items
	private static final ItemHolder GRAKON_S_NOTE = new ItemHolder(39521, 1);
	
	public Q10738_AnInnerBeauty()
	{
		super(10738, Q10738_AnInnerBeauty.class.getSimpleName(), "An Inner Beauty");
		addStartNpc(GRAKON);
		addTalkId(GRAKON, EVNA);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "33947-05.htm");
		addCondRace(Race.ERTHEIA, "33947-05.htm");
		addCondCompletedQuest(Q10737_GrakonsWarehouse.class.getSimpleName(), "33947-05.htm");
		registerQuestItems(GRAKON_S_NOTE.getId());
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "33947-02.htm":
			case "33947-03.htm":
			case "33935-02.htm":
			{
				htmltext = event;
				break;
			}
			case "33947-04.htm":
			{
				qs.startQuest();
				giveItems(player, GRAKON_S_NOTE);
				htmltext = event;
				break;
			}
			case "33935-03.htm":
			{
				if (qs.isStarted())
				{
					giveAdena(player, 12000, true);
					addExpAndSp(player, 2625, 1);
					qs.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		if (npc.getId() == GRAKON)
		{
			if (qs.isCreated())
			{
				htmltext = "33947-01.htm";
			}
			else if (qs.isStarted())
			{
				htmltext = "33947-04.htm";
			}
		}
		else if (npc.getId() == EVNA)
		{
			if (qs.isStarted())
			{
				htmltext = "33935-01.htm";
			}
		}
		return htmltext;
	}
}
