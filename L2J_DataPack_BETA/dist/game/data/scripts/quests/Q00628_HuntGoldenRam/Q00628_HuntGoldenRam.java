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
package quests.Q00628_HuntGoldenRam;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemChanceHolder;
import com.l2jserver.gameserver.model.holders.QuestItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Hunt of the Golden Ram Mercenary Force (628)
 * @author netvirus
 */
public final class Q00628_HuntGoldenRam extends Quest
{
	// NPCs
	private static final int KAHMAN = 31554;
	private static final int ABERCROMBIE = 31555;
	private static final int SELINA = 31556;
	// Items
	private static final int GOLDEN_RAM_COIN = 7251;
	private static final int BADGE_RECRUIT = 7246;
	private static final int BADGE_SOLDIER = 7247;
	private static final int SPLINTER_STAKATO_CHITIN = 7248;
	private static final int NEEDLE_STAKATO_CHITIN = 7249;
	// Misc
	private static final int REQUIRED_ITEM_COUNT = 100;
	private static final int MIN_LVL = 66;
	// Mobs
	private static final Map<Integer, ItemChanceHolder> MOBS_DROP_CHANCES = new HashMap<>();
	// Buffs
	private static final Map<String, QuestItemHolder> BUFFS = new HashMap<>();
	
	static
	{
		MOBS_DROP_CHANCES.put(21508, new ItemChanceHolder(SPLINTER_STAKATO_CHITIN, 0.500, 1)); // splinter_stakato
		MOBS_DROP_CHANCES.put(21509, new ItemChanceHolder(SPLINTER_STAKATO_CHITIN, 0.430, 1)); // splinter_stakato_worker
		MOBS_DROP_CHANCES.put(21510, new ItemChanceHolder(SPLINTER_STAKATO_CHITIN, 0.521, 1)); // splinter_stakato_soldier
		MOBS_DROP_CHANCES.put(21511, new ItemChanceHolder(SPLINTER_STAKATO_CHITIN, 0.575, 1)); // splinter_stakato_drone
		MOBS_DROP_CHANCES.put(21512, new ItemChanceHolder(SPLINTER_STAKATO_CHITIN, 0.746, 1)); // splinter_stakato_drone_a
		MOBS_DROP_CHANCES.put(21513, new ItemChanceHolder(NEEDLE_STAKATO_CHITIN, 0.500, 2)); // needle_stakato
		MOBS_DROP_CHANCES.put(21514, new ItemChanceHolder(NEEDLE_STAKATO_CHITIN, 0.430, 2)); // needle_stakato_worker
		MOBS_DROP_CHANCES.put(21515, new ItemChanceHolder(NEEDLE_STAKATO_CHITIN, 0.520, 2)); // needle_stakato_soldier
		MOBS_DROP_CHANCES.put(21516, new ItemChanceHolder(NEEDLE_STAKATO_CHITIN, 0.531, 2)); // needle_stakato_drone
		MOBS_DROP_CHANCES.put(21517, new ItemChanceHolder(NEEDLE_STAKATO_CHITIN, 0.744, 2)); // needle_stakato_drone_a
		
		BUFFS.put("Focus", new QuestItemHolder(4404, 2, 2)); // (buff_id, buff_level, buff_cost)
		BUFFS.put("Death", new QuestItemHolder(4405, 2, 2));
		BUFFS.put("Might", new QuestItemHolder(4393, 3, 3));
		BUFFS.put("Acumen", new QuestItemHolder(4400, 2, 3));
		BUFFS.put("Berserker", new QuestItemHolder(4397, 1, 3));
		BUFFS.put("Vampiric", new QuestItemHolder(4399, 2, 3));
		BUFFS.put("Empower", new QuestItemHolder(4401, 1, 6));
		BUFFS.put("Haste", new QuestItemHolder(4402, 2, 6));
	}
	
	private Q00628_HuntGoldenRam()
	{
		super(628, Q00628_HuntGoldenRam.class.getSimpleName(), "Hunt of the Golden Ram Mercenary Force");
		addStartNpc(KAHMAN);
		addTalkId(KAHMAN, SELINA);
		addFirstTalkId(ABERCROMBIE, SELINA);
		addKillId(MOBS_DROP_CHANCES.keySet());
		registerQuestItems(SPLINTER_STAKATO_CHITIN, NEEDLE_STAKATO_CHITIN, BADGE_RECRUIT, BADGE_SOLDIER);
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
			case "accept":
			{
				if (qs.isCreated())
				{
					final boolean itemRecruit = hasQuestItems(player, BADGE_RECRUIT);
					final boolean itemSoldier = hasQuestItems(player, BADGE_SOLDIER);
					qs.startQuest();
					if (itemSoldier)
					{
						htmltext = "31554-05.htm";
						qs.setCond(3);
					}
					else if (itemRecruit)
					{
						htmltext = "31554-04.htm";
						qs.setCond(2);
					}
					else
					{
						htmltext = "31554-03.htm";
					}
				}
				break;
			}
			case "31554-08.html":
			{
				if (qs.isStarted())
				{
					giveItems(player, BADGE_RECRUIT, 1);
					takeItems(player, SPLINTER_STAKATO_CHITIN, -1);
					qs.setCond(2, true);
					htmltext = event;
				}
				break;
			}
			case "31554-12.html":
			case "31554-13.html":
			{
				if (qs.isStarted())
				{
					htmltext = event;
				}
				break;
			}
			case "31554-14.html":
			{
				if (qs.isStarted())
				{
					qs.exitQuest(true, true);
					htmltext = event;
				}
				break;
			}
			case "Focus":
			case "Death":
			case "Might":
			case "Acumen":
			case "Berserker":
			case "Vampiric":
			case "Empower":
			case "Haste":
			{
				if (qs.isCond(3))
				{
					final QuestItemHolder buffs = BUFFS.get(event);
					if (getQuestItemsCount(player, GOLDEN_RAM_COIN) >= buffs.getCount())
					{
						takeItems(player, GOLDEN_RAM_COIN, buffs.getCount());
						npc.setTarget(player);
						npc.doCast(SkillData.getInstance().getSkill(buffs.getId(), buffs.getChance()));
						htmltext = "31556-03.htm";
					}
					else
					{
						htmltext = "31556-04.htm";
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = null;
		if (qs == null)
		{
			switch (npc.getId())
			{
				case ABERCROMBIE:
				{
					htmltext = "31555-00.htm";
					break;
				}
				case SELINA:
				{
					htmltext = "31556-00.htm";
					break;
				}
			}
			return htmltext;
		}
		
		final boolean itemRecruit = hasQuestItems(player, BADGE_RECRUIT);
		final boolean itemSolder = hasQuestItems(player, BADGE_SOLDIER);
		switch (npc.getId())
		{
			case ABERCROMBIE:
			{
				if (qs.isStarted())
				{
					if (itemRecruit)
					{
						htmltext = "31555-01.htm";
					}
					else if (itemSolder)
					{
						htmltext = "31555-02.htm";
					}
					else
					{
						htmltext = "31555-00.htm";
					}
				}
				break;
			}
			case SELINA:
			{
				if (qs.isStarted())
				{
					if (itemRecruit)
					{
						htmltext = "31556-01.htm";
					}
					else if (itemSolder)
					{
						htmltext = "31556-02.htm";
					}
					else
					{
						htmltext = "31556-00.htm";
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		QuestState qs = getRandomPartyMemberState(killer, -1, 1, npc);
		if (qs != null)
		{
			final ItemChanceHolder item = MOBS_DROP_CHANCES.get(npc.getId());
			if ((item.getCount() <= qs.getCond()) && !qs.isCond(3))
			{
				giveItemRandomly(qs.getPlayer(), npc, item.getId(), 1, REQUIRED_ITEM_COUNT, item.getChance(), true);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (qs == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case KAHMAN:
			{
				switch (qs.getState())
				{
					case State.CREATED:
					{
						htmltext = ((player.getLevel() >= MIN_LVL) ? "31554-01.htm" : "31554-02.htm");
						break;
					}
					case State.STARTED:
					{
						final long itemCountSplinter = getQuestItemsCount(player, SPLINTER_STAKATO_CHITIN);
						final long itemCountNeedle = getQuestItemsCount(player, NEEDLE_STAKATO_CHITIN);
						switch (qs.getCond())
						{
							case 1:
							{
								htmltext = ((itemCountSplinter >= REQUIRED_ITEM_COUNT) ? "31554-07.html" : "31554-06.html");
								break;
							}
							case 2:
							{
								if (hasQuestItems(player, BADGE_RECRUIT))
								{
									if ((itemCountSplinter >= REQUIRED_ITEM_COUNT) && (itemCountNeedle >= REQUIRED_ITEM_COUNT))
									{
										takeItems(player, BADGE_RECRUIT, -1);
										takeItems(player, SPLINTER_STAKATO_CHITIN, -1);
										takeItems(player, NEEDLE_STAKATO_CHITIN, -1);
										giveItems(player, BADGE_SOLDIER, 1);
										qs.setCond(3, true);
										htmltext = "31554-10.html";
									}
									else
									{
										htmltext = "31554-09.html";
									}
								}
								else
								{
									qs.setCond(1);
									htmltext = ((itemCountSplinter >= REQUIRED_ITEM_COUNT) ? "31554-07.html" : "31554-06.html");
								}
								break;
							}
							case 3:
							{
								if (hasQuestItems(player, BADGE_SOLDIER))
								{
									htmltext = "31554-11.html";
								}
								else
								{
									qs.setCond(1);
									htmltext = ((itemCountSplinter >= REQUIRED_ITEM_COUNT) ? "31554-07.html" : "31554-06.html");
								}
								break;
							}
						}
						break;
					}
				}
				break;
			}
			case SELINA:
			{
				if (qs.isCond(3))
				{
					htmltext = "31556-03.htm";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00628_HuntGoldenRam();
	}
}
