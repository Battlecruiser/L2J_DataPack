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
package quests.Q10321_QualificationsOfTheSeeker;

import quests.Q10320_LetsGoToTheCentralSquare.Q10320_LetsGoToTheCentralSquare;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.serverpackets.TutorialShowHtml;

/**
 * Qualifications Of The Seeker (10321)
 * @author ivantotov
 */
public final class Q10321_QualificationsOfTheSeeker extends Quest
{
	// NPCs
	private static final int SHANNON = 32974;
	private static final int THEODORE = 32975;
	// Misc
	private static final int MAX_LEVEL = 20;
	
	public Q10321_QualificationsOfTheSeeker()
	{
		super(10321, Q10321_QualificationsOfTheSeeker.class.getSimpleName(), "Qualifications Of The Seeker");
		addStartNpc(THEODORE);
		addTalkId(THEODORE, SHANNON);
		addCondMaxLevel(MAX_LEVEL, "32975-01a.htm");
		addCondNotRace(Race.ERTHEIA, "32975-01b.htm");
		addCondCompletedQuest(Q10320_LetsGoToTheCentralSquare.class.getSimpleName(), "32975-01a.htm");
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
			case "32975-03.htm":
			{
				qs.startQuest();
				player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_027_Quest_01.htm", TutorialShowHtml.LARGE_WINDOW));
				htmltext = event;
				break;
			}
			case "32975-02.htm":
			{
				htmltext = event;
				break;
			}
			case "32974-02.html":
			{
				giveAdena(player, 50, true);
				addExpAndSp(player, 40, 5);
				qs.exitQuest(false, true);
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
		if (qs.isCreated())
		{
			if (npc.getId() == THEODORE)
			{
				htmltext = "32975-01.htm";
			}
		}
		else if (qs.isStarted())
		{
			if (npc.getId() == THEODORE)
			{
				htmltext = "32975-04.html";
			}
			else if (npc.getId() == SHANNON)
			{
				htmltext = "32974-01.html";
			}
		}
		else if (qs.isCompleted())
		{
			if (npc.getId() == THEODORE)
			{
				htmltext = "32975-05.html";
			}
			else if (npc.getId() == SHANNON)
			{
				htmltext = "32974-03.html";
			}
		}
		return htmltext;
	}
}