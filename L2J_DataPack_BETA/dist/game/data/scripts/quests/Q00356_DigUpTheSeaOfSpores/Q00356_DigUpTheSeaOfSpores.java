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
package quests.Q00356_DigUpTheSeaOfSpores;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemChanceHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.util.Rnd;

/**
 * Dig Up the Sea of Spores! (356)
 * @author Adry_85
 */
public final class Q00356_DigUpTheSeaOfSpores extends Quest
{
	// NPC
	private static final int GAUEN = 30717;
	// Items
	private static final int CARNIVORE_SPORE = 5865;
	private static final int HERBIVOROUS_SPORE = 5866;
	// Misc
	private static final int MIN_LEVEL = 43;
	// Monsters
	private static final Map<Integer, ItemChanceHolder> MONSTERS = new HashMap<>();
	static
	{
		MONSTERS.put(20558, new ItemChanceHolder(HERBIVOROUS_SPORE, 0.73, 1));
		MONSTERS.put(20562, new ItemChanceHolder(CARNIVORE_SPORE, 0.94, 1));
	}
	
	public Q00356_DigUpTheSeaOfSpores()
	{
		super(356, Q00356_DigUpTheSeaOfSpores.class.getSimpleName(), "Dig Up the Sea of Spores!");
		addStartNpc(GAUEN);
		addTalkId(GAUEN);
		addKillId(MONSTERS.keySet());
		registerQuestItems(HERBIVOROUS_SPORE, CARNIVORE_SPORE);
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
			case "30717-02.html":
			case "30717-03.html":
			case "30717-04.html":
			case "30717-10.html":
			case "30717-18.html":
			{
				htmltext = event;
				break;
			}
			case "30717-05.html":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "30717-09.html":
			{
				addExpAndSp(player, 31850, 0);
				takeItems(player, CARNIVORE_SPORE, -1);
				takeItems(player, HERBIVOROUS_SPORE, -1);
				htmltext = event;
				break;
			}
			case "30717-11.html":
			{
				qs.exitQuest(true, true);
				htmltext = event;
				break;
			}
			case "30717-14.html":
			{
				addExpAndSp(player, 45500, 2600);
				qs.exitQuest(true, true);
				htmltext = event;
				break;
			}
			case "FINISH":
			{
				final int value = Rnd.get(100);
				int adena = 0;
				if (value < 20)
				{
					adena = 44000;
					htmltext = "30717-15.html";
				}
				else if (value < 70)
				{
					adena = 20950;
					htmltext = "30717-16.html";
				}
				else
				{
					adena = 10400;
					htmltext = "30717-17.html";
				}
				giveAdena(player, adena, true);
				qs.exitQuest(true, true);
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getRandomPartyMemberState(killer, -1, 3, npc);
		if (qs != null)
		{
			final ItemChanceHolder item = MONSTERS.get(npc.getId());
			if (giveItemRandomly(qs.getPlayer(), npc, item.getId(), item.getCount(), 50, item.getChance(), true) //
				&& (getQuestItemsCount(qs.getPlayer(), CARNIVORE_SPORE) >= 50) //
				&& (getQuestItemsCount(qs.getPlayer(), CARNIVORE_SPORE) >= 50))
			{
				qs.setCond(3, true);
			}
			else
			{
				qs.setCond(2, true);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (qs.isCreated())
		{
			htmltext = (player.getLevel() >= MIN_LEVEL) ? "30717-01.html" : "30717-06.htm";
		}
		else if (qs.isStarted())
		{
			if ((getQuestItemsCount(player, HERBIVOROUS_SPORE) < 50) && (getQuestItemsCount(player, CARNIVORE_SPORE) < 50))
			{
				htmltext = "30717-07.html";
			}
			else if ((getQuestItemsCount(player, HERBIVOROUS_SPORE) >= 50) && (getQuestItemsCount(player, CARNIVORE_SPORE) < 50))
			{
				htmltext = "30717-08.html";
			}
			else if ((getQuestItemsCount(player, HERBIVOROUS_SPORE) < 50) && (getQuestItemsCount(player, CARNIVORE_SPORE) >= 50))
			{
				htmltext = "30717-12.html";
			}
			else
			{
				htmltext = "30717-13.html";
			}
		}
		return htmltext;
	}
}
