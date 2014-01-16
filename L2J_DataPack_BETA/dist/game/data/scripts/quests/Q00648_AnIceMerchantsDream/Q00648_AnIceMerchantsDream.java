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
package quests.Q00648_AnIceMerchantsDream;

import java.util.HashMap;
import java.util.Map;

import quests.Q00115_TheOtherSideOfTruth.Q00115_TheOtherSideOfTruth;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * An Ice Merchant's Dream (648)
 * @author netvirus, Adry_85
 */
public final class Q00648_AnIceMerchantsDream extends Quest
{
	private static class DropInfo
	{
		private final double _firstChance;
		private final double _secondChance;
		
		public DropInfo(double firstChance, double secondChance)
		{
			_firstChance = firstChance;
			_secondChance = secondChance;
		}
		
		public double getFirstChance()
		{
			return _firstChance;
		}
		
		public double getSecondChance()
		{
			return _secondChance;
		}
	}
	
	// NPCs
	private static final int RAFFORTY = 32020;
	private static final int ICE_SHELF = 32023;
	// Items
	private static final int SILVER_HEMOCYTE = 8057;
	private static final int SILVER_ICE_CRYSTAL = 8077;
	private static final int BLACK_ICE_CRYSTAL = 8078;
	// Misc
	private static final int MIN_LVL = 53;
	// Monsters
	private static final Map<Integer, DropInfo> MONSTERS = new HashMap<>();
	static
	{
		MONSTERS.put(22080, new DropInfo(0.285, 0.048)); // Massive Maze Bandersnatch
		MONSTERS.put(22081, new DropInfo(0.443, 0.0)); // Lost Watcher
		MONSTERS.put(22082, new DropInfo(0.510, 0.0)); // Elder Lost Watcher
		MONSTERS.put(22083, new DropInfo(0.477, 0.049)); // Baby Panthera
		MONSTERS.put(22084, new DropInfo(0.477, 0.049)); // Panthera
		MONSTERS.put(22085, new DropInfo(0.420, 0.043)); // Lost Gargoyle
		MONSTERS.put(22086, new DropInfo(0.490, 0.050)); // Lost Gargoyle Youngling
		MONSTERS.put(22087, new DropInfo(0.787, 0.081)); // Pronghorn Spirit
		MONSTERS.put(22088, new DropInfo(0.480, 0.049)); // Pronghorn
		MONSTERS.put(22089, new DropInfo(0.550, 0.056)); // Ice Tarantula
		MONSTERS.put(22090, new DropInfo(0.570, 0.058)); // Frost Tarantula
		MONSTERS.put(22091, new DropInfo(0.623, 0.0)); // Lost Iron Golem
		MONSTERS.put(22092, new DropInfo(0.623, 0.0)); // Frost Iron Golem
		MONSTERS.put(22093, new DropInfo(0.910, 0.093)); // Lost Buffalo
		MONSTERS.put(22094, new DropInfo(0.553, 0.057)); // Frost Buffalo
		MONSTERS.put(22095, new DropInfo(0.593, 0.061)); // Ursus Cub
		MONSTERS.put(22096, new DropInfo(0.593, 0.061)); // Ursus
		MONSTERS.put(22097, new DropInfo(0.693, 0.071)); // Lost Yeti
		MONSTERS.put(22098, new DropInfo(0.717, 0.074)); // Frost Yeti
	}
	
	private Q00648_AnIceMerchantsDream()
	{
		super(648, Q00648_AnIceMerchantsDream.class.getSimpleName(), "An Ice Merchants Dream");
		addStartNpc(RAFFORTY);
		addTalkId(RAFFORTY, ICE_SHELF);
		addKillId(MONSTERS.keySet());
		registerQuestItems(SILVER_HEMOCYTE, SILVER_ICE_CRYSTAL, BLACK_ICE_CRYSTAL);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		final QuestState q115 = player.getQuestState(Q00115_TheOtherSideOfTruth.class.getSimpleName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "ACCEPT":
			{
				st.startQuest();
				if ((q115 != null) && (q115.isCompleted()))
				{
					htmltext = "32020-04.htm";
				}
				else
				{
					st.setCond(2);
					htmltext = "32020-05.htm";
				}
				break;
			}
			case "ASK":
			{
				if (st.getCond() >= 1)
				{
					htmltext = ((q115 != null) && !q115.isCompleted()) ? "32020-14.html" : "32020-15.html";
				}
				break;
			}
			case "LATER":
			{
				if (st.getCond() >= 1)
				{
					htmltext = ((q115 != null) && !q115.isCompleted()) ? "32020-19.html" : "32020-20.html";
				}
				break;
			}
			case "REWARD":
			{
				if (st.getCond() >= 1)
				{
					final long silverCryCount = getQuestItemsCount(player, SILVER_ICE_CRYSTAL);
					final long blackCryCount = getQuestItemsCount(player, BLACK_ICE_CRYSTAL);
					if ((silverCryCount + blackCryCount) > 0)
					{
						giveAdena(player, (silverCryCount * 300) + (blackCryCount * 1200), true);
						takeItems(player, -1, SILVER_ICE_CRYSTAL, BLACK_ICE_CRYSTAL);
						htmltext = ((q115 != null) && !q115.isCompleted()) ? "32020-16.html" : "32020-17.html";
					}
					else
					{
						htmltext = "32020-18.html";
					}
				}
				break;
			}
			case "QUIT":
			{
				if (st.getCond() >= 1)
				{
					if ((q115 != null) && !q115.isCompleted())
					{
						htmltext = "32020-21.html";
						st.exitQuest(true, true);
					}
					else
					{
						htmltext = "32020-22.html";
					}
				}
				break;
			}
			case "32020-06.html":
			case "32020-07.html":
			case "32020-08.html":
			case "32020-09.html":
			{
				if (st.getCond() >= 1)
				{
					htmltext = event;
				}
				break;
			}
			case "32020-23.html":
			{
				if (st.getCond() >= 1)
				{
					st.exitQuest(true, true);
					htmltext = event;
				}
				break;
			}
			case "32023-04.html":
			{
				if ((st.getCond() >= 1) && hasQuestItems(player, SILVER_ICE_CRYSTAL) && (st.getInt("ex") == 0))
				{
					st.set("ex", ((getRandom(4) + 1) * 10));
					htmltext = event;
				}
				break;
			}
			case "32023-05.html":
			{
				if ((st.getCond() >= 1) && hasQuestItems(player, SILVER_ICE_CRYSTAL) && (st.getInt("ex") > 0))
				{
					takeItems(player, SILVER_ICE_CRYSTAL, 1);
					int val = (st.getInt("ex") + 1);
					st.set("ex", val);
					playSound(player, QuestSound.ITEMSOUND_BROKEN_KEY);
					htmltext = event;
				}
				break;
			}
			case "32023-06.html":
			{
				if ((st.getCond() >= 1) && hasQuestItems(player, SILVER_ICE_CRYSTAL) && (st.getInt("ex") > 0))
				{
					takeItems(player, SILVER_ICE_CRYSTAL, 1);
					int val = (st.getInt("ex") + 2);
					st.set("ex", val);
					playSound(player, QuestSound.ITEMSOUND_BROKEN_KEY);
					htmltext = event;
				}
				break;
			}
			case "REPLY4":
			{
				if ((st.getCond() >= 1) && (st.getInt("ex") > 0))
				{
					int ex = st.getInt("ex");
					int val1 = ex / 10;
					int val2 = ex - (val1 * 10);
					if (val1 == val2)
					{
						htmltext = "32023-07.html";
						giveItems(player, BLACK_ICE_CRYSTAL, 1);
						playSound(player, QuestSound.ITEMSOUND_ENCHANT_SUCCESS);
					}
					else
					{
						htmltext = "32023-08.html";
						playSound(player, QuestSound.ITEMSOUND_ENCHANT_FAILED);
					}
					st.set("ex", 0);
				}
				break;
			}
			case "REPLY5":
			{
				if ((st.getCond() >= 1) && (st.getInt("ex") > 0))
				{
					int ex = st.getInt("ex");
					int val1 = ex / 10;
					int val2 = ((ex - (val1 * 10)) + 2);
					if (val1 == val2)
					{
						htmltext = "32023-07.html";
						giveItems(player, BLACK_ICE_CRYSTAL, 1);
						playSound(player, QuestSound.ITEMSOUND_ENCHANT_SUCCESS);
					}
					else
					{
						htmltext = "32023-08.html";
						playSound(player, QuestSound.ITEMSOUND_ENCHANT_FAILED);
					}
					st.set("ex", 0);
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = getRandomPartyMemberState(killer, -1, 3, npc);
		if (st != null)
		{
			final DropInfo info = MONSTERS.get(npc.getId());
			if (st.getCond() >= 1)
			{
				giveItemRandomly(st.getPlayer(), npc, SILVER_ICE_CRYSTAL, 1, 0, info.getFirstChance(), true);
			}
			
			if (info.getSecondChance() > 0)
			{
				final QuestState st2 = st.getPlayer().getQuestState(Q00115_TheOtherSideOfTruth.class.getSimpleName());
				if ((st.getCond() >= 2) && (st2 != null) && st2.isCompleted())
				{
					giveItemRandomly(st.getPlayer(), npc, SILVER_HEMOCYTE, 1, 0, info.getSecondChance(), true);
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, true);
		final QuestState st2 = player.getQuestState(Q00115_TheOtherSideOfTruth.class.getSimpleName());
		String htmltext = getNoQuestMsg(player);
		switch (npc.getId())
		{
			case RAFFORTY:
			{
				if (st.isCreated())
				{
					if (player.getLevel() < MIN_LVL)
					{
						htmltext = "32020-01.htm";
					}
					else
					{
						htmltext = ((st2 != null) && (st2.isCompleted())) ? "32020-02.htm" : "32020-03.htm";
					}
				}
				else if (st.isStarted())
				{
					final long hasQuestItems = getQuestItemsCount(player, SILVER_ICE_CRYSTAL, BLACK_ICE_CRYSTAL);
					if ((st2 != null) && st2.isCompleted())
					{
						htmltext = (hasQuestItems > 0) ? "32020-13.html" : "32020-11.html";
						if (st.isCond(1))
						{
							st.setCond(2, true);
						}
					}
					else
					{
						htmltext = (hasQuestItems > 0) ? "32020-12.html" : "32020-10.html";
					}
				}
				break;
			}
			case ICE_SHELF:
			{
				// TODO: In High Five this quest have an updated reward system.
				if (st.isStarted())
				{
					if (hasQuestItems(player, SILVER_ICE_CRYSTAL))
					{
						final int val = st.getInt("ex") % 10;
						if (val == 0)
						{
							htmltext = "32023-03.html";
							st.set("ex", 0);
						}
						else
						{
							htmltext = "32023-09.html";
						}
					}
					else
					{
						htmltext = "32023-02.html";
					}
				}
				else
				{
					htmltext = "32023-01.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00648_AnIceMerchantsDream();
	}
}
