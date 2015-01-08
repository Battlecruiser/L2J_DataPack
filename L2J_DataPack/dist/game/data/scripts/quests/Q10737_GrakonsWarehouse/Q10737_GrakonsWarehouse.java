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
package quests.Q10737_GrakonsWarehouse;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Sdw
 */
public class Q10737_GrakonsWarehouse extends Quest
{
	// NPC's
	private static final int KATALIN = 33943;
	private static final int AYANTHE = 33942;
	private static final int GRAKON = 33947;
	// Misc
	private static final int MIN_LEVEL = 5;
	private static final int MAX_LEVEL = 20;
	// Items
	private static final ItemHolder APPRENTICE_SUPPORT_BOX = new ItemHolder(39520, 1);
	private static final ItemHolder APPRENTICE_ADVENTURER_STAFF = new ItemHolder(7816, 1);
	private static final ItemHolder APPRENTICE_ADVENTURER_FISTS = new ItemHolder(7819, 1);
	
	public Q10737_GrakonsWarehouse()
	{
		super(10737, Q10737_GrakonsWarehouse.class.getSimpleName(), "Grakon's Warehouse");
		addStartNpc(KATALIN, AYANTHE);
		addTalkId(KATALIN, AYANTHE, GRAKON);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "fixme.htm");
		addCondRace(Race.ERTHEIA, "fixme.htm");
		registerQuestItems(APPRENTICE_SUPPORT_BOX.getId());
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
			case "33942-03.htm":
			case "33943-03.htm":
			{
				qs.startQuest();
				giveItems(player, APPRENTICE_SUPPORT_BOX);
				htmltext = event;
				break;
			}
			case "33947-04.htm":
			case "33947-08.htm":
			{
				if (qs.isStarted())
				{
					giveAdena(player, 11000, true);
					addExpAndSp(player, 2625, 0);
					if (player.getClassId() == ClassId.ERTHEIA_FIGHTER)
					{
						giveItems(player, APPRENTICE_ADVENTURER_FISTS);
					}
					else if (player.getClassId() == ClassId.ERTHEIA_WIZARD)
					{
						giveItems(player, APPRENTICE_ADVENTURER_STAFF);
					}
					qs.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
			case "33942-02.htm":
			case "33943-02.htm":
			case "33947-02.htm":
			case "33947-03.htm":
			case "33947-06.htm":
			case "33947-07.htm":
			{
				htmltext = event;
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
		
		switch (npc.getId())
		{
			case KATALIN:
			case AYANTHE:
			{
				if (qs.isCreated())
				{
					htmltext = npc.getId() + "-01.htm";
				}
				else if (qs.isStarted())
				{
					htmltext = npc.getId() + "-03.htm";
				}
				break;
			}
			case GRAKON:
			{
				if (qs.isStarted())
				{
					if (player.getClassId() == ClassId.ERTHEIA_FIGHTER)
					{
						htmltext = "33947-01.htm";
					}
					else if (player.getClassId() == ClassId.ERTHEIA_WIZARD)
					{
						htmltext = "33947-05.htm";
					}
				}
				break;
			}
		}
		
		return htmltext;
	}
}
