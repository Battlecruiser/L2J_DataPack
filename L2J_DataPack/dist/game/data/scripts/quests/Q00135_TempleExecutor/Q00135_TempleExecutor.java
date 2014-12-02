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
package quests.Q00135_TempleExecutor;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Temple Executor (135)
 * @author malyelfik
 */
public class Q00135_TempleExecutor extends Quest
{
	// NPCs
	private static final int SHEGFIELD = 30068;
	private static final int PANO = 30078;
	private static final int ALEX = 30291;
	private static final int SONIN = 31773;
	// Items
	private static final int STOLEN_CARGO = 10328;
	private static final int HATE_CRYSTAL = 10329;
	private static final int OLD_TREASURE_MAP = 10330;
	private static final int SONINS_CREDENTIALS = 10331;
	private static final int PANOS_CREDENTIALS = 10332;
	private static final int ALEXS_CREDENTIALS = 10333;
	private static final int BADGE_TEMPLE_EXECUTOR = 10334;
	// Monsters
	private static final Map<Integer, Integer> MOBS = new HashMap<>();
	static
	{
		MOBS.put(20781, 439); // Delu Lizardman Shaman
		MOBS.put(21104, 439); // Delu Lizardman Supplier
		MOBS.put(21105, 504); // Delu Lizardman Special Agent
		MOBS.put(21106, 423); // Cursed Seer
		MOBS.put(21107, 902); // Delu Lizardman Commander
	}
	// Misc
	private static final int MIN_LEVEL = 35;
	private static final int ITEM_COUNT = 10;
	private static final int MAX_REWARD_LEVEL = 41;
	
	public Q00135_TempleExecutor()
	{
		super(135, Q00135_TempleExecutor.class.getSimpleName(), "Temple Executor");
		addStartNpc(SHEGFIELD);
		addTalkId(SHEGFIELD, ALEX, SONIN, PANO);
		addKillId(MOBS.keySet());
		registerQuestItems(STOLEN_CARGO, HATE_CRYSTAL, OLD_TREASURE_MAP, SONINS_CREDENTIALS, PANOS_CREDENTIALS, ALEXS_CREDENTIALS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30291-02a.html":
			case "30291-04.html":
			case "30291-05.html":
			case "30291-06.html":
			case "30068-08.html":
			case "30068-09.html":
			case "30068-10.html":
				break;
			case "30068-03.htm":
				st.startQuest();
				break;
			case "30068-04.html":
				st.setCond(2, true);
				break;
			case "30291-07.html":
				st.unset("talk");
				st.setCond(3, true);
				break;
			case "30068-11.html":
				st.giveItems(BADGE_TEMPLE_EXECUTOR, 1);
				st.giveAdena(16924, true);
				if (player.getLevel() < MAX_REWARD_LEVEL)
				{
					st.addExpAndSp(30000, 2000);
				}
				st.exitQuest(false, true);
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
		final L2PcInstance member = getRandomPartyMember(player, 3);
		if (member == null)
		{
			return super.onKill(npc, player, isSummon);
		}
		final QuestState st = getQuestState(member, false);
		if ((getRandom(1000) < MOBS.get(npc.getId())))
		{
			if (st.getQuestItemsCount(STOLEN_CARGO) < ITEM_COUNT)
			{
				st.giveItems(STOLEN_CARGO, 1);
			}
			else if (st.getQuestItemsCount(HATE_CRYSTAL) < ITEM_COUNT)
			{
				st.giveItems(HATE_CRYSTAL, 1);
			}
			else
			{
				st.giveItems(OLD_TREASURE_MAP, 1);
			}
			
			if ((st.getQuestItemsCount(STOLEN_CARGO) >= ITEM_COUNT) && (st.getQuestItemsCount(HATE_CRYSTAL) >= ITEM_COUNT) && (st.getQuestItemsCount(OLD_TREASURE_MAP) >= ITEM_COUNT))
			{
				st.setCond(4, true);
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
		
		switch (npc.getId())
		{
			case SHEGFIELD:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= MIN_LEVEL) ? "30068-01.htm" : "30068-02.htm";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1: // 1
								st.setCond(2, true);
								htmltext = "30068-04.html";
								break;
							case 2: // 2, 3
							case 3: // 4
								htmltext = "30068-05.html";
								break;
							case 4: // 5
								htmltext = "30068-06.html";
								break;
							case 5:
								if (st.isSet("talk"))
								{
									htmltext = "30068-08.html";
								}
								else if (st.hasQuestItems(PANOS_CREDENTIALS, SONINS_CREDENTIALS, ALEXS_CREDENTIALS))
								{
									st.takeItems(SONINS_CREDENTIALS, -1);
									st.takeItems(PANOS_CREDENTIALS, -1);
									st.takeItems(ALEXS_CREDENTIALS, -1);
									st.set("talk", "1");
									htmltext = "30068-07.html";
								}
								else
								{
									htmltext = "30068-06.html";
								}
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case ALEX:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "30291-01.html";
							break;
						case 2:
							if (st.isSet("talk"))
							{
								htmltext = "30291-03.html";
							}
							else
							{
								st.set("talk", "1");
								htmltext = "30291-02.html";
							}
							break;
						case 3:
							htmltext = "30291-08.html"; // 4
							break;
						case 4:
							if (st.hasQuestItems(PANOS_CREDENTIALS, SONINS_CREDENTIALS))
							{
								if (st.getQuestItemsCount(OLD_TREASURE_MAP) < ITEM_COUNT)
								{
									return htmltext;
								}
								st.setCond(5, true);
								st.takeItems(OLD_TREASURE_MAP, -1);
								st.giveItems(ALEXS_CREDENTIALS, 1);
								htmltext = "30291-10.html";
							}
							else
							{
								htmltext = "30291-09.html";
							}
							break;
						case 5:
							htmltext = "30291-11.html";
							break;
					}
				}
				break;
			case PANO:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "30078-01.html";
							break;
						case 2:
							htmltext = "30078-02.html";
							break;
						case 3:
							htmltext = "30078-03.html";
							break;
						case 4:
							if (!st.isSet("Pano"))
							{
								if (st.getQuestItemsCount(HATE_CRYSTAL) < ITEM_COUNT)
								{
									return htmltext;
								}
								st.takeItems(HATE_CRYSTAL, -1);
								st.giveItems(PANOS_CREDENTIALS, 1);
								st.set("Pano", "1");
								htmltext = "30078-04.html";
								break;
							}
						case 5:
							htmltext = "30078-05.html";
							break;
					}
				}
				break;
			case SONIN:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "31773-01.html";
							break;
						case 2:
							htmltext = "31773-02.html";
							break;
						case 3:
							htmltext = "31773-03.html";
							break;
						case 4:
							if (!st.isSet("Sonin"))
							{
								if (st.getQuestItemsCount(STOLEN_CARGO) < ITEM_COUNT)
								{
									return htmltext;
								}
								st.takeItems(STOLEN_CARGO, -1);
								st.giveItems(SONINS_CREDENTIALS, 1);
								st.set("Sonin", "1");
								htmltext = "31773-04.html";
								break;
							}
						case 5:
							htmltext = "31773-05.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
}