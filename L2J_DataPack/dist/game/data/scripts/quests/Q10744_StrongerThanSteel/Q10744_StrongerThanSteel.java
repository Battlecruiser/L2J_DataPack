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
package quests.Q10744_StrongerThanSteel;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Sdw
 */
public class Q10744_StrongerThanSteel extends Quest
{
	// NPC's
	private static final int MILONE = 33953;
	private static final int DOLKIN = 33954;
	private static final int TREANT = 23457;
	private static final int LEAFIE = 23458;
	// Misc
	private static final int MIN_LEVEL = 15;
	private static final int MAX_LEVEL = 20;
	// Item
	private static final int TREANT_LEAF = 39532;
	private static final int LEAFIE_LEAF = 39531;
	
	public Q10744_StrongerThanSteel()
	{
		super(10744, Q10744_StrongerThanSteel.class.getSimpleName(), "Stronger Than Steel");
		addStartNpc(MILONE);
		addTalkId(MILONE, DOLKIN);
		addKillId(TREANT, LEAFIE);
		registerQuestItems(TREANT_LEAF, LEAFIE_LEAF);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "fixme.htm");
		addCondRace(Race.ERTHEIA, "fixme.htm");
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
			case "33953-02.htm":
			case "33954-02.htm":
			{
				htmltext = event;
				break;
			}
			case "33953-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33954-03.htm":
			{
				if (qs.isCond(1))
				{
					qs.setCond(2, true);
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
			case MILONE:
			{
				if (qs.isCreated())
				{
					htmltext = "33953-01.htm";
				}
				else if (qs.isStarted())
				{
					htmltext = "33953-03.htm";
				}
				break;
			}
			case DOLKIN:
			{
				if (qs.isCond(1))
				{
					htmltext = "33954-01.htm";
				}
				else if (qs.isCond(3))
				{
					htmltext = "33954-04.htm";
					giveAdena(player, 34000, true);
					addExpAndSp(player, 112001, 2);
					qs.exitQuest(false, true);
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
		
		if ((qs != null) && qs.isCond(2))
		{
			if (npc.getId() == TREANT)
			{
				giveItemRandomly(killer, npc, TREANT_LEAF, 1, 20, 1.0, true);
			}
			else if (npc.getId() == LEAFIE)
			{
				giveItemRandomly(killer, npc, LEAFIE_LEAF, 1, 15, 1.0, true);
			}
			
			if ((getQuestItemsCount(killer, TREANT_LEAF) >= 20) && (getQuestItemsCount(killer, LEAFIE_LEAF) >= 15))
			{
				qs.setCond(3, true);
			}
		}
		
		return super.onKill(npc, killer, isSummon);
	}
}
