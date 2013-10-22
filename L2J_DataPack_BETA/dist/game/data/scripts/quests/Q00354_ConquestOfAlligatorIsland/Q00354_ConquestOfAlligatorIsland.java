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
package quests.Q00354_ConquestOfAlligatorIsland;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.Config;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Conquest of Alligator Island (354)
 * @author Adry_85
 */
public class Q00354_ConquestOfAlligatorIsland extends Quest
{
	// NPC
	private static final int KLUCK = 30895;
	// Items
	private static final int ALLIGATOR_TOOTH = 5863;
	private static final int MYSTERIOUS_MAP_PIECE = 5864;
	private static final int PIRATES_TREASURE_MAP = 5915;
	// Misc
	private static final int MIN_LEVEL = 38;
	// Mobs
	private static final Map<Integer, Integer> MOB1 = new HashMap<>();
	private static final Map<Integer, Integer> MOB2 = new HashMap<>();
	static
	{
		MOB1.put(20804, 84); // crokian_lad
		MOB1.put(20805, 91); // dailaon_lad
		MOB1.put(20806, 88); // crokian_lad_warrior
		MOB1.put(20807, 92); // farhite_lad
		MOB2.put(22208, 14); // nos_lad
		MOB2.put(20991, 69); // tribe_of_swamp
	}
	
	private Q00354_ConquestOfAlligatorIsland(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addKillId(MOB1.keySet());
		addKillId(MOB2.keySet());
		addStartNpc(KLUCK);
		addTalkId(KLUCK);
		registerQuestItems(ALLIGATOR_TOOTH, MYSTERIOUS_MAP_PIECE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "30895-04.html":
			case "30895-05.html":
			case "30895-09.html":
			{
				htmltext = event;
				break;
			}
			case "30895-02.html":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "ADENA":
			{
				final int count = (int) st.getQuestItemsCount(ALLIGATOR_TOOTH);
				if (count >= 100)
				{
					st.giveAdena((count * 220) + 10700, true);
					st.takeItems(ALLIGATOR_TOOTH, -1);
					htmltext = "30895-06.html";
				}
				else if ((count > 0) && (count < 100))
				{
					st.giveAdena((count * 220) + 10700, true);
					st.takeItems(ALLIGATOR_TOOTH, -1);
					htmltext = "30895-07.html";
				}
				else if (count == 0)
				{
					htmltext = "30895-08.html";
				}
				break;
			}
			case "30895-10.html":
			{
				st.exitQuest(true, true);
				htmltext = event;
				break;
			}
			case "REWARD":
			{
				final int count = (int) st.getQuestItemsCount(MYSTERIOUS_MAP_PIECE);
				if ((count > 0) && (count < 10))
				{
					htmltext = "30895-12.html";
				}
				else if (count >= 10)
				{
					st.giveItems(PIRATES_TREASURE_MAP, 1);
					st.takeItems(MYSTERIOUS_MAP_PIECE, 10);
					htmltext = "30895-13.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = getRandomPartyMemberState(player, -1, 3, npc);
		if ((st != null) && st.isStarted() && Util.checkIfInRange(1500, npc, player, false))
		{
			int npcId = npc.getId();
			if (MOB1.containsKey(npcId))
			{
				float chance = MOB1.get(npcId) * Config.RATE_QUEST_DROP;
				if (getRandom(100) < chance)
				{
					st.giveItems(ALLIGATOR_TOOTH, 1);
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
			else if (MOB2.containsKey(npcId))
			{
				float chance = MOB2.get(npcId) * Config.RATE_QUEST_DROP;
				st.giveItems(ALLIGATOR_TOOTH, getRandom(100) < chance ? 2 : 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
			
			if (getRandom(10) == 5)
			{
				st.giveItems(MYSTERIOUS_MAP_PIECE, 1);
			}
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (player.getLevel() >= MIN_LEVEL) ? "30895-01.htm" : "30895-03.html";
				break;
			}
			case State.STARTED:
			{
				if (st.isCond(1))
				{
					htmltext = (!st.hasQuestItems(MYSTERIOUS_MAP_PIECE)) ? "30895-04.html" : "30895-11.html";
				}
			}
		}
		return htmltext;
	}
	
	public static void main(String args[])
	{
		new Q00354_ConquestOfAlligatorIsland(354, Q00354_ConquestOfAlligatorIsland.class.getSimpleName(), "Conquest of Alligator Island");
	}
}
