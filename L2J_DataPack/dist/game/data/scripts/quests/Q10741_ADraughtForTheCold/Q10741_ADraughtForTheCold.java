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
package quests.Q10741_ADraughtForTheCold;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Sdw
 */
public class Q10741_ADraughtForTheCold extends Quest
{
	// NPC's
	private static final int SIVANTHE = 33951;
	private static final int LEIRA = 33952;
	// Items
	private static final int EMPTY_HONEY_JAR = 39527;
	private static final int SWEET_HONEY = 39528;
	private static final int NUTRITIOUS_MEAT = 39529;
	// Mobs
	private static final int HONEY_BEE = 23452;
	private static final int KIKU = 23453;
	private static final int ROBUST_HONEY_BEE = 23484;
	// Misc
	private static final int MIN_LEVEL = 10;
	private static final int MAX_LEVEL = 20;
	
	public Q10741_ADraughtForTheCold()
	{
		super(10741, Q10741_ADraughtForTheCold.class.getSimpleName(), "A Draught For The Cold");
		addStartNpc(SIVANTHE);
		addTalkId(SIVANTHE, LEIRA);
		addKillId(HONEY_BEE, KIKU, ROBUST_HONEY_BEE);
		registerQuestItems(EMPTY_HONEY_JAR, SWEET_HONEY, NUTRITIOUS_MEAT);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "33951-07.htm");
		addCondRace(Race.ERTHEIA, "33951-07.htm");
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
			case "33951-03.htm":
			{
				qs.startQuest();
				qs.giveItems(EMPTY_HONEY_JAR, 10);
				htmltext = event;
				break;
			}
			case "33951-02.htm":
			{
				htmltext = event;
				break;
			}
			case "33952-02.htm":
			{
				if (qs.isCond(2))
				{
					giveAdena(player, 2000, true);
					addExpAndSp(player, 22973, 2);
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
		
		switch (npc.getId())
		{
			case SIVANTHE:
			{
				if (qs.isCreated())
				{
					htmltext = "33951-01.htm";
				}
				else if (qs.isStarted())
				{
					htmltext = "33951-04.htm";
				}
				break;
			}
			case LEIRA:
			{
				if (qs.isCond(2))
				{
					htmltext = "33952-01.htm";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		if ((qs != null) && qs.isCond(1))
		{
			switch (npc.getId())
			{
				case HONEY_BEE:
				case ROBUST_HONEY_BEE:
				{
					if (qs.getQuestItemsCount(EMPTY_HONEY_JAR) > 0)
					{
						takeItems(killer, EMPTY_HONEY_JAR, 1);
						giveItems(killer, SWEET_HONEY, 1);
						playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
					}
					break;
				}
				case KIKU:
				{
					giveItemRandomly(killer, npc, NUTRITIOUS_MEAT, 1, 10, 1.0, true);
					break;
				}
			}
			
			if ((qs.getQuestItemsCount(SWEET_HONEY) >= 10) && (qs.getQuestItemsCount(NUTRITIOUS_MEAT) >= 10))
			{
				qs.setCond(2, true);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
}
