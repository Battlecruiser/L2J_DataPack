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
package quests.Q00611_AllianceWithVarkaSilenos;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Alliance with Varka Silenos (611)
 * @author malyelfik
 */
public class Q00611_AllianceWithVarkaSilenos extends Quest
{
	private static class DropInfo
	{
		private final int _chance;
		private final int _minCond;
		private final int _itemId;
		
		public DropInfo(int chance, int minCond)
		{
			_chance = chance;
			_minCond = minCond;
			switch (_minCond)
			{
				case 1:
					_itemId = KETRA_BADGE_SOLDIER;
					break;
				case 2:
					_itemId = KETRA_BADGE_OFFICER;
					break;
				default:
					_itemId = KETRA_BADGE_CAPTAIN;
					break;
			}
		}
		
		public int getMinCond()
		{
			return _minCond;
		}
		
		public int getChance()
		{
			return _chance;
		}
		
		public int getItemId()
		{
			return _itemId;
		}
	}
	
	// NPC
	private static final int NARAN_ASHANUK = 31378;
	// Monsters
	private static final Map<Integer, DropInfo> MOBS = new HashMap<>();
	static
	{
		MOBS.put(21324, new DropInfo(500, 1)); // Ketra Orc Footman
		MOBS.put(21325, new DropInfo(500, 1)); // Ketra's War Hound
		MOBS.put(21327, new DropInfo(509, 1)); // Ketra Orc Raider
		MOBS.put(21328, new DropInfo(521, 1)); // Ketra Orc Scout
		MOBS.put(21329, new DropInfo(519, 1)); // Ketra Orc Shaman
		MOBS.put(21331, new DropInfo(500, 2)); // Ketra Orc Warrior
		MOBS.put(21332, new DropInfo(500, 2)); // Ketra Orc Lieutenant
		MOBS.put(21334, new DropInfo(509, 2)); // Ketra Orc Medium
		MOBS.put(21335, new DropInfo(518, 2)); // Ketra Orc Elite Soldier
		MOBS.put(21336, new DropInfo(518, 2)); // Ketra Orc White Captain
		MOBS.put(21338, new DropInfo(527, 2)); // Ketra Orc Seer
		MOBS.put(21339, new DropInfo(500, 3)); // Ketra Orc General
		MOBS.put(21340, new DropInfo(500, 3)); // Ketra Orc Battalion Commander
		MOBS.put(21342, new DropInfo(508, 3)); // Ketra Orc Grand Seer
		MOBS.put(21343, new DropInfo(628, 2)); // Ketra Commander
		MOBS.put(21344, new DropInfo(604, 2)); // Ketra Elite Guard
		MOBS.put(21345, new DropInfo(627, 3)); // Ketra's Head Shaman
		MOBS.put(21346, new DropInfo(604, 3)); // Ketra's Head Guard
		MOBS.put(21347, new DropInfo(649, 3)); // Ketra Prophet
		MOBS.put(21348, new DropInfo(626, 3)); // Prophet's Guard
		MOBS.put(21349, new DropInfo(626, 3)); // Prophet's Aide
	}
	// Items
	private static final int KETRA_BADGE_SOLDIER = 7226;
	private static final int KETRA_BADGE_OFFICER = 7227;
	private static final int KETRA_BADGE_CAPTAIN = 7228;
	private static final int VALOR_FEATHER = 7229;
	private static final int WISDOM_FEATHER = 7230;
	private static final int[] KETRA_MARKS =
	{
		7211, // Mark of Ketra's Alliance - Level 1
		7212, // Mark of Ketra's Alliance - Level 2
		7213, // Mark of Ketra's Alliance - Level 3
		7214, // Mark of Ketra's Alliance - Level 4
		7215, // Mark of Ketra's Alliance - Level 5
	};
	private static final int[] VARKA_MARKS =
	{
		7221, // Mark of Varka's Alliance - Level 1
		7222, // Mark of Varka's Alliance - Level 2
		7223, // Mark of Varka's Alliance - Level 3
		7224, // Mark of Varka's Alliance - Level 4
		7225, // Mark of Varka's Alliance - Level 5
	};
	// Misc
	private static final int MIN_LEVEL = 74;
	private static final int[] SOLDIER_BADGE_COUNT =
	{
		100, // cond 1
		200, // cond 2
		300, // cond 3
		300, // cond 4
		400, // cond 5
	};
	private static final int[] OFFICER_BADGE_COUNT =
	{
		0, // cond 1
		100, // cond 2
		200, // cond 3
		300, // cond 4
		400, // cond 5
	};
	private static final int[] CAPTAIN_BADGE_COUNT =
	{
		0, // cond 1
		0, // cond 2
		100, // cond 3
		200, // cond 4
		200, // cond 5
	};
	
	private Q00611_AllianceWithVarkaSilenos(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(NARAN_ASHANUK);
		addTalkId(NARAN_ASHANUK);
		addKillId(MOBS.keySet());
		registerQuestItems(KETRA_BADGE_CAPTAIN, KETRA_BADGE_OFFICER, KETRA_BADGE_SOLDIER);
	}
	
	private boolean canGetItem(QuestState st, int itemId)
	{
		int count = 0;
		switch (itemId)
		{
			case KETRA_BADGE_SOLDIER:
				count = SOLDIER_BADGE_COUNT[st.getCond() - 1];
				break;
			case KETRA_BADGE_OFFICER:
				count = OFFICER_BADGE_COUNT[st.getCond() - 1];
				break;
			case KETRA_BADGE_CAPTAIN:
				count = CAPTAIN_BADGE_COUNT[st.getCond() - 1];
				break;
		}
		if (st.getQuestItemsCount(itemId) < count)
		{
			return true;
		}
		return false;
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
			case "31378-12a.html":
			case "31378-12b.html":
			case "31378-25.html":
				break;
			case "31378-04.htm":
				if (hasAtLeastOneQuestItem(player, KETRA_MARKS))
				{
					return "31378-03.htm";
				}
				st.setState(State.STARTED);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ACCEPT);
				for (int i = 0; i < VARKA_MARKS.length; i++)
				{
					if (st.hasQuestItems(VARKA_MARKS[i]))
					{
						st.setCond(i + 2);
						return "31378-0" + (i + 5) + ".htm";
					}
				}
				st.setCond(1);
				break;
			case "31378-12.html":
				if (st.getQuestItemsCount(KETRA_BADGE_SOLDIER) < SOLDIER_BADGE_COUNT[0])
				{
					return getNoQuestMsg(player);
				}
				st.takeItems(KETRA_BADGE_SOLDIER, -1);
				st.giveItems(VARKA_MARKS[0], 1);
				st.setCond(2, true);
				break;
			case "31378-15.html":
				if ((st.getQuestItemsCount(KETRA_BADGE_SOLDIER) < SOLDIER_BADGE_COUNT[1]) || (st.getQuestItemsCount(KETRA_BADGE_OFFICER) < OFFICER_BADGE_COUNT[1]))
				{
					return getNoQuestMsg(player);
				}
				takeItems(player, -1, KETRA_BADGE_SOLDIER, KETRA_BADGE_OFFICER, VARKA_MARKS[0]);
				st.giveItems(VARKA_MARKS[1], 1);
				st.setCond(3, true);
				break;
			case "31378-18.html":
				if ((st.getQuestItemsCount(KETRA_BADGE_SOLDIER) < SOLDIER_BADGE_COUNT[2]) || (st.getQuestItemsCount(KETRA_BADGE_OFFICER) < OFFICER_BADGE_COUNT[2]) || (st.getQuestItemsCount(KETRA_BADGE_CAPTAIN) < CAPTAIN_BADGE_COUNT[2]))
				{
					return getNoQuestMsg(player);
				}
				takeItems(player, -1, KETRA_BADGE_SOLDIER, KETRA_BADGE_OFFICER, KETRA_BADGE_CAPTAIN, VARKA_MARKS[1]);
				st.giveItems(VARKA_MARKS[2], 1);
				st.setCond(4, true);
				break;
			case "31378-21.html":
				if (!st.hasQuestItems(VALOR_FEATHER) || (st.getQuestItemsCount(KETRA_BADGE_SOLDIER) < SOLDIER_BADGE_COUNT[3]) || (st.getQuestItemsCount(KETRA_BADGE_OFFICER) < OFFICER_BADGE_COUNT[3]) || (st.getQuestItemsCount(KETRA_BADGE_CAPTAIN) < CAPTAIN_BADGE_COUNT[3]))
				{
					return getNoQuestMsg(player);
				}
				takeItems(player, -1, KETRA_BADGE_SOLDIER, KETRA_BADGE_OFFICER, KETRA_BADGE_CAPTAIN, VALOR_FEATHER, VARKA_MARKS[2]);
				st.giveItems(VARKA_MARKS[3], 1);
				st.setCond(5, true);
				break;
			case "31378-26.html":
				takeItems(player, -1, VARKA_MARKS);
				takeItems(player, -1, VALOR_FEATHER, WISDOM_FEATHER);
				st.exitQuest(true, true);
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final L2PcInstance member = getRandomPartyMemberState(killer, State.STARTED);
		if (member != null)
		{
			final QuestState st = member.getQuestState(getName());
			final DropInfo info = MOBS.get(npc.getId());
			if ((st.getCond() >= info.getMinCond()) && (st.getCond() < 6) && canGetItem(st, info.getItemId()) && (getRandom(1000) < info.getChance()))
			{
				st.giveItems(info.getItemId(), 1);
			}
		}
		return super.onKill(npc, killer, isSummon);
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
				htmltext = (player.getLevel() >= MIN_LEVEL) ? "31378-01.htm" : "31378-02.htm";
				break;
			case State.STARTED:
				switch (st.getCond())
				{
					case 1:
						htmltext = (st.getQuestItemsCount(KETRA_BADGE_SOLDIER) >= SOLDIER_BADGE_COUNT[0]) ? "31378-11.html" : "31378-10.html";
						break;
					case 2:
						htmltext = (st.hasQuestItems(VARKA_MARKS[0]) && (st.getQuestItemsCount(KETRA_BADGE_SOLDIER) >= SOLDIER_BADGE_COUNT[1]) && (st.getQuestItemsCount(KETRA_BADGE_OFFICER) >= OFFICER_BADGE_COUNT[1])) ? "31378-14.html" : "31378-13.html";
						break;
					case 3:
						htmltext = (st.hasQuestItems(VARKA_MARKS[1]) && (st.getQuestItemsCount(KETRA_BADGE_SOLDIER) >= SOLDIER_BADGE_COUNT[2]) && (st.getQuestItemsCount(KETRA_BADGE_OFFICER) >= OFFICER_BADGE_COUNT[2]) && (st.getQuestItemsCount(KETRA_BADGE_CAPTAIN) >= CAPTAIN_BADGE_COUNT[2])) ? "31378-17.html" : "31378-16.html";
						break;
					case 4:
						htmltext = (hasQuestItems(player, VARKA_MARKS[2], VALOR_FEATHER) && (st.getQuestItemsCount(KETRA_BADGE_SOLDIER) >= SOLDIER_BADGE_COUNT[3]) && (st.getQuestItemsCount(KETRA_BADGE_OFFICER) >= OFFICER_BADGE_COUNT[3]) && (st.getQuestItemsCount(KETRA_BADGE_CAPTAIN) >= CAPTAIN_BADGE_COUNT[3])) ? "31378-20.html" : "31378-19.html";
						break;
					case 5:
						if (!st.hasQuestItems(VARKA_MARKS[3]) || !st.hasQuestItems(WISDOM_FEATHER) || (st.getQuestItemsCount(KETRA_BADGE_SOLDIER) < SOLDIER_BADGE_COUNT[4]) || (st.getQuestItemsCount(KETRA_BADGE_OFFICER) < OFFICER_BADGE_COUNT[4]) || (st.getQuestItemsCount(KETRA_BADGE_CAPTAIN) < CAPTAIN_BADGE_COUNT[4]))
						{
							return "31378-22.html";
						}
						st.setCond(6, true);
						takeItems(player, -1, KETRA_BADGE_SOLDIER, KETRA_BADGE_OFFICER, KETRA_BADGE_CAPTAIN, WISDOM_FEATHER, VARKA_MARKS[3]);
						st.giveItems(VARKA_MARKS[4], 1);
						htmltext = "31378-23.html";
						break;
					case 6:
						if (st.hasQuestItems(VARKA_MARKS[4]))
						{
							htmltext = "31378-24.html";
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00611_AllianceWithVarkaSilenos(611, Q00611_AllianceWithVarkaSilenos.class.getSimpleName(), "Alliance with Varka Silenos");
	}
}