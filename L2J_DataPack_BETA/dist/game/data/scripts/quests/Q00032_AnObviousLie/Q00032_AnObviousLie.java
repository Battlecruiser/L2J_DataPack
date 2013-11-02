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
package quests.Q00032_AnObviousLie;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * An Obvious Lie (32).
 * @author janiko
 */
public final class Q00032_AnObviousLie extends Quest
{
	// NPCs
	private static final int MAXIMILIAN = 30120;
	private static final int GENTLER = 30094;
	private static final int MIKI_THE_CAT = 31706;
	// Monster
	private static final int ALLIGATOR = 20135;
	// Items
	private static final int MAP_OF_GENTLER = 7165;
	private static final int MEDICINAL_HERB = 7166;
	private static final int SPIRIT_ORE = 3031;
	private static final int THREAD = 1868;
	private static final int SUEDE = 1866;
	// Misc
	private static final int MIN_LVL = 45;
	private static final int REQUIRED_HERB_COUNT = 20;
	// Reward
	private static final Map<String, Integer> EARS = new HashMap<>();
	{
		EARS.put("cat", 6843); // Cat Ears
		EARS.put("raccoon", 7680); // Raccoon ears
		EARS.put("rabbit", 7683); // Rabbit ears
	}
	
	private Q00032_AnObviousLie()
	{
		super(32, Q00032_AnObviousLie.class.getSimpleName(), "An Obvious Lie");
		addStartNpc(MAXIMILIAN);
		addTalkId(MAXIMILIAN, GENTLER, MIKI_THE_CAT);
		addKillId(ALLIGATOR);
		registerQuestItems(MEDICINAL_HERB, MAP_OF_GENTLER);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st == null)
		{
			return htmltext;
		}
		switch (event)
		{
			case "30120-02.html":
			{
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "30094-02.html":
			{
				if (st.isCond(1))
				{
					st.setCond(2, true);
					giveItems(player, MAP_OF_GENTLER, 1);
					htmltext = event;
				}
				break;
			}
			case "31706-02.html":
			{
				if (st.isCond(2) && hasQuestItems(player, MAP_OF_GENTLER))
				{
					st.setCond(3, true);
					takeItems(player, MAP_OF_GENTLER, 1);
					htmltext = event;
				}
				break;
			}
			case "30094-06.html":
			{
				if (st.isCond(4) && (getQuestItemsCount(player, MEDICINAL_HERB) >= 20))
				{
					takeItems(player, MEDICINAL_HERB, 20);
					st.setCond(5, true);
					htmltext = event;
				}
				break;
			}
			case "30094-09.html":
			{
				if (st.isCond(5) && (getQuestItemsCount(player, SPIRIT_ORE) >= 500))
				{
					takeItems(player, SPIRIT_ORE, 500);
					st.setCond(6, true);
					htmltext = event;
				}
				break;
			}
			case "30094-12.html":
			{
				if (st.isCond(7))
				{
					st.setCond(8, true);
					htmltext = event;
				}
				break;
			}
			case "30094-15.html":
			{
				htmltext = event;
				break;
			}
			case "31706-05.html":
			{
				if (st.isCond(6))
				{
					st.setCond(7, true);
					htmltext = event;
				}
				break;
			}
			case "cat":
			case "raccoon":
			case "rabbit":
			{
				if (st.isCond(8) && (getQuestItemsCount(player, THREAD) >= 1000) && (getQuestItemsCount(player, SUEDE) >= 500))
				{
					takeItems(player, THREAD, 1000);
					takeItems(player, SUEDE, 500);
					giveItems(player, EARS.get(event), 1);
					st.exitQuest(false, true);
					htmltext = "30094-16.html";
				}
				else
				{
					htmltext = "30094-17.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getRandomPartyMemberState(killer, 3, 3, npc);
		if ((qs != null) && giveItemRandomly(killer, npc, MEDICINAL_HERB, 1, REQUIRED_HERB_COUNT, 1.0, true))
		{
			qs.setCond(4);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		switch (npc.getId())
		{
			case MAXIMILIAN:
			{
				if (st.isCreated())
				{
					htmltext = ((player.getLevel() >= MIN_LVL) ? "30120-01.htm" : "30120-03.htm");
				}
				else if (st.isStarted())
				{
					if (st.isCond(1))
					{
						htmltext = "30120-04.html";
					}
				}
				else
				{
					htmltext = getAlreadyCompletedMsg(player);
				}
				break;
			}
			case GENTLER:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "30094-01.html";
						break;
					}
					case 2:
					{
						htmltext = "30094-03.html";
						break;
					}
					case 4:
					{
						htmltext = ((getQuestItemsCount(player, MEDICINAL_HERB) >= 20) ? "30094-04.html" : "30094-05.html");
						break;
					}
					case 5:
					{
						htmltext = ((getQuestItemsCount(player, SPIRIT_ORE) >= 500) ? "30094-07.html" : "30094-08.html");
						break;
					}
					case 6:
					{
						htmltext = "30094-10.html";
						break;
					}
					case 7:
					{
						htmltext = "30094-11.html";
						break;
					}
					case 8:
					{
						if ((getQuestItemsCount(player, THREAD) >= 1000) && (getQuestItemsCount(player, SUEDE) >= 500))
						{
							htmltext = "30094-13.html";
						}
						else
						{
							htmltext = "30094-14.html";
						}
						break;
					}
				}
				break;
			}
			case MIKI_THE_CAT:
			{
				switch (st.getCond())
				{
					case 2:
					{
						if (hasQuestItems(player, MAP_OF_GENTLER))
						{
							htmltext = "31706-01.html";
						}
						break;
					}
					case 3:
					case 4:
					case 5:
					{
						htmltext = "31706-03.html";
						break;
					}
					case 6:
					{
						htmltext = "31706-04.html";
						break;
					}
					case 7:
					{
						htmltext = "31706-06.html";
						break;
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00032_AnObviousLie();
	}
}
