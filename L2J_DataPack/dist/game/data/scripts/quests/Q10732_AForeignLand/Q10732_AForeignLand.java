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
package quests.Q10732_AForeignLand;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.serverpackets.ExShowUsm;
import com.l2jserver.gameserver.network.serverpackets.TutorialShowHtml;

/**
 * @author Sdw
 */
public class Q10732_AForeignLand extends Quest
{
	// NPC's
	private static final int NAVARI = 33931;
	private static final int GERETH = 33932;
	// Misc
	private static final int MAX_LEVEL = 20;
	
	public Q10732_AForeignLand()
	{
		super(10732, Q10732_AForeignLand.class.getSimpleName(), "A Foreign Land");
		addStartNpc(NAVARI);
		addTalkId(NAVARI, GERETH);
		addCondMaxLevel(MAX_LEVEL, "findme.html"); // TODO: Find proper HTML
		addCondRace(Race.ERTHEIA, "findme.html"); // TODO: Find proper HTML
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		String htmltext = null;
		
		if (qs == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "33931-03.htm":
			{
				qs.startQuest();
				player.sendPacket(ExShowUsm.ERTHEIA_FIRST_QUEST);
				htmltext = event;
				break;
			}
			case "33932-02.htm":
			{
				player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_001_Radar_01.htm", TutorialShowHtml.LARGE_WINDOW));
				giveAdena(player, 3000, true);
				addExpAndSp(player, 75, 2);
				qs.exitQuest(false, true);
				break;
			}
			case "33931-02.htm":
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
		final QuestState st = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		switch (npc.getId())
		{
			case NAVARI:
			{
				if (st.isCreated())
				{
					htmltext = "33931-01.htm";
				}
				else if (st.isStarted())
				{
					htmltext = "33931-04.htm";
				}
				else if (st.isCompleted())
				{
					htmltext = getAlreadyCompletedMsg(player);
				}
				break;
			}
			case GERETH:
			{
				if (st.isStarted())
				{
					htmltext = "33932-01.htm";
				}
				else if (st.isCompleted())
				{
					htmltext = getAlreadyCompletedMsg(player);
				}
				break;
			}
		}
		return htmltext;
	}
}