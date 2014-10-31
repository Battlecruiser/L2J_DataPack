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
package quests.Q00601_WatchingEyes;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Watching Eyes (601)<br>
 * Original Jython script by disKret.
 * @author malyelfik
 */
public class Q00601_WatchingEyes extends Quest
{
	// NPC
	private static final int EYE_OF_ARGOS = 31683;
	// Item
	private static final int PROOF_OF_AVENGER = 7188;
	// Monsters
	private static final Map<Integer, Integer> MOBS = new HashMap<>();
	
	static
	{
		MOBS.put(21308, 790);
		MOBS.put(21309, 820);
		MOBS.put(21306, 850);
		MOBS.put(21310, 680);
		MOBS.put(21311, 630);
	}
	
	// Reward
	private static final int[][] REWARD =
	{
		{
			6699,
			90000
		},
		{
			6698,
			80000
		},
		{
			6700,
			40000
		},
		{
			0,
			230000
		}
	};
	
	public Q00601_WatchingEyes()
	{
		super(601, Q00601_WatchingEyes.class.getSimpleName(), "Watching Eyes");
		addStartNpc(EYE_OF_ARGOS);
		addTalkId(EYE_OF_ARGOS);
		addKillId(MOBS.keySet());
		registerQuestItems(PROOF_OF_AVENGER);
	}
	
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
			case "31683-02.htm":
				st.startQuest();
				break;
			case "31683-05.html":
				if (st.getQuestItemsCount(PROOF_OF_AVENGER) < 100)
				{
					return "31683-06.html";
				}
				
				int i = getRandom(4);
				if (i < 3)
				{
					st.giveItems(REWARD[i][0], 5);
					st.addExpAndSp(120000, 10000);
				}
				st.giveAdena(REWARD[i][1], true);
				st.exitQuest(true, true);
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		
		if ((st != null) && st.isCond(1) && (getRandom(1000) < MOBS.get(npc.getId())))
		{
			st.giveItems(PROOF_OF_AVENGER, 1);
			if (st.getQuestItemsCount(PROOF_OF_AVENGER) == 100)
			{
				st.setCond(2, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, player, isSummon);
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
			case State.CREATED:
				htmltext = (player.getLevel() >= 71) ? "31683-01.htm" : "31683-00.htm";
				break;
			case State.STARTED:
				htmltext = (st.isCond(1)) ? "31683-03.html" : "31683-04.html";
				break;
		}
		return htmltext;
	}
}