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
package quests.Q00647_InfluxOfMachines;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Influx of Machines (647)
 * @author malyelfik
 */
public class Q00647_InfluxOfMachines extends Quest
{
	// NPC
	private static final int GUTENHAGEN = 32069;
	// Monsters
	private static final Map<Integer, Integer> MOBS = new HashMap<>();
	static
	{
		MOBS.put(22801, 280); // Cruel Pincer Golem
		MOBS.put(22802, 227); // Cruel Pincer Golem
		MOBS.put(22803, 286); // Cruel Pincer Golem
		MOBS.put(22804, 288); // Horrifying Jackhammer Golem
		MOBS.put(22805, 235); // Horrifying Jackhammer Golem
		MOBS.put(22806, 295); // Horrifying Jackhammer Golem
		MOBS.put(22807, 273); // Scout-type Golem No. 28
		MOBS.put(22808, 143); // Scout-type Golem No. 2
		MOBS.put(22809, 629); // Guard Golem
		MOBS.put(22810, 465); // Micro Scout Golem
		MOBS.put(22811, 849); // Great Chaos Golem
		MOBS.put(22812, 463); // Boom Golem
	}
	// Item
	private static final int BROKEN_GOLEM_FRAGMENT = 15521;
	private static final int[] RECIPES =
	{
		6881, // Recipe: Forgotten Blade (60%)
		6883, // Recipe: Basalt Battlehammer (60%)
		6885, // Recipe: Imperial Staff (60%)
		6887, // Recipe: Angel Slayer (60%)
		6891, // Recipe: Dragon Hunter Axe (60%)
		6893, // Recipe: Saint Spear (60%)
		6895, // Recipe: Demon Splinter (60%)
		6897, // Recipe: Heavens Divider (60%)
		6899, // Recipe: Arcana Mace (60%)
		7580, // Recipe: Draconic Bow (60%)
	};
	// Misc
	private static final int MIN_LEVEL = 70;
	private static final int FRAGMENT_COUNT = 500;
	
	public Q00647_InfluxOfMachines()
	{
		super(647, Q00647_InfluxOfMachines.class.getSimpleName(), "Influx of Machines");
		addStartNpc(GUTENHAGEN);
		addTalkId(GUTENHAGEN);
		addKillId(MOBS.keySet());
		registerQuestItems(BROKEN_GOLEM_FRAGMENT);
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
			case "32069-03.htm":
				st.startQuest();
				htmltext = event;
				break;
			case "32069-06.html":
				if (st.isCond(2) && (st.getQuestItemsCount(BROKEN_GOLEM_FRAGMENT) >= FRAGMENT_COUNT))
				{
					st.giveItems(RECIPES[getRandom(RECIPES.length)], 1);
					st.exitQuest(true, true);
					htmltext = event;
				}
				else
				{
					htmltext = "32069-07.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final L2PcInstance member = getRandomPartyMember(player, 1);
		if (member != null)
		{
			final QuestState st = member.getQuestState(getName());
			if (st.isCond(1) && (getRandom(1000) < MOBS.get(npc.getId())))
			{
				st.giveItems(BROKEN_GOLEM_FRAGMENT, 1);
				if (st.getQuestItemsCount(BROKEN_GOLEM_FRAGMENT) >= FRAGMENT_COUNT)
				{
					st.setCond(2, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (player.getLevel() >= MIN_LEVEL) ? "32069-01.htm" : "32069-02.htm";
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					htmltext = "32069-04.html";
				}
				else if (st.isCond(2) && (st.getQuestItemsCount(BROKEN_GOLEM_FRAGMENT) >= FRAGMENT_COUNT))
				{
					htmltext = "32069-05.html";
				}
				break;
		}
		return htmltext;
	}
}