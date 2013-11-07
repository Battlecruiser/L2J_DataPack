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
package quests.Q00040_ASpecialOrder;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * A Special Order (40)
 * @author janiko
 */
public final class Q00040_ASpecialOrder extends Quest
{
	// NPCs
	private static final int HELVETIA = 30081;
	private static final int OFULLE = 31572;
	private static final int GESTO = 30511;
	// Items
	private static final int ORANGE_SWIFT_FISH = 6450;
	private static final int ORANGE_UGLY_FISH = 6451;
	private static final int ORANGE_WIDE_FISH = 6452;
	private static final int GOLDEN_COBOL = 5079;
	private static final int BUR_COBOL = 5082;
	private static final int GREAT_COBOL = 5084;
	private static final int WONDROUS_CUBIC = 10632;
	private static final int BOX_OF_FISH = 12764;
	private static final int BOX_OF_SEED = 12765;
	// Misc
	private static final int MIN_LVL = 40;
	
	private Q00040_ASpecialOrder(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(HELVETIA);
		addTalkId(HELVETIA, OFULLE, GESTO);
		registerQuestItems(BOX_OF_FISH, BOX_OF_SEED);
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
			case "accept":
			{
				st.setState(State.STARTED);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ACCEPT);
				if (getRandomBoolean())
				{
					st.setCond(2);
					htmltext = "30081-03.html";
				}
				else
				{
					st.setCond(5);
					htmltext = "30081-04.html";
				}
				break;
			}
			case "30081-07.html":
			{
				if (st.isCond(4) && st.hasQuestItems(BOX_OF_FISH))
				{
					st.rewardItems(WONDROUS_CUBIC, 1);
					st.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
			case "30081-10.html":
			{
				if (st.isCond(7) && st.hasQuestItems(BOX_OF_SEED))
				{
					st.rewardItems(WONDROUS_CUBIC, 1);
					st.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
			case "31572-02.html":
			case "30511-02.html":
			{
				htmltext = event;
				break;
			}
			case "31572-03.html":
			{
				if (st.isCond(2))
				{
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "30511-03.html":
			{
				if (st.isCond(5))
				{
					st.setCond(6, true);
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
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
			case HELVETIA:
			{
				switch (st.getState())
				{
					case State.CREATED:
					{
						htmltext = (player.getLevel() >= MIN_LVL) ? "30081-01.htm" : "30081-02.htm";
						break;
					}
					case State.STARTED:
					{
						switch (st.getCond())
						{
							case 2:
							case 3:
							{
								htmltext = "30081-05.html";
								break;
							}
							case 4:
							{
								if (st.hasQuestItems(BOX_OF_FISH))
								{
									htmltext = "30081-06.html";
								}
								break;
							}
							case 5:
							case 6:
							{
								htmltext = "30081-08.html";
								break;
							}
							case 7:
							{
								if (st.hasQuestItems(BOX_OF_SEED))
								{
									htmltext = "30081-09.html";
								}
								break;
							}
						}
						break;
					}
					case State.COMPLETED:
					{
						htmltext = getAlreadyCompletedMsg(player);
						break;
					}
				}
				break;
			}
			case OFULLE:
			{
				switch (st.getCond())
				{
					case 2:
					{
						htmltext = "31572-01.html";
						break;
					}
					case 3:
					{
						if ((st.getQuestItemsCount(ORANGE_SWIFT_FISH) >= 10) && (st.getQuestItemsCount(ORANGE_UGLY_FISH) >= 10) && (st.getQuestItemsCount(ORANGE_WIDE_FISH) >= 10))
						{
							st.setCond(4, true);
							st.giveItems(BOX_OF_FISH, 1);
							takeItems(player, 10, ORANGE_SWIFT_FISH, ORANGE_UGLY_FISH, ORANGE_WIDE_FISH);
							htmltext = "31572-05.html";
						}
						else
						{
							htmltext = "31572-04.html";
						}
						break;
					}
					case 4:
					{
						htmltext = "31572-06.html";
						break;
					}
				}
				break;
			}
			case GESTO:
			{
				switch (st.getCond())
				{
					case 5:
					{
						htmltext = "30511-01.html";
						break;
					}
					case 6:
					{
						if ((st.getQuestItemsCount(GOLDEN_COBOL) >= 40) && (st.getQuestItemsCount(BUR_COBOL) >= 40) && (st.getQuestItemsCount(GREAT_COBOL) >= 40))
						{
							st.setCond(7, true);
							st.giveItems(BOX_OF_SEED, 1);
							takeItems(player, 40, GOLDEN_COBOL, BUR_COBOL, GREAT_COBOL);
							htmltext = "30511-05.html";
						}
						else
						{
							htmltext = "30511-04.html";
						}
						break;
					}
					case 7:
					{
						htmltext = "30511-06.html";
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
		new Q00040_ASpecialOrder(40, Q00040_ASpecialOrder.class.getSimpleName(), "A Special Order");
	}
}