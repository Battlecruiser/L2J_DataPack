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
package quests.Q00168_DeliverSupplies;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Deliver Supplies (168)
 * @author xban1x
 */
public class Q00168_DeliverSupplies extends Quest
{
	// NPCs
	private static final int JENNA = 30349;
	private static final int ROSELYN = 30355;
	private static final int KRISTIN = 30357;
	private static final int HARANT = 30360;
	// Items
	private static final int JENNAS_LETTER = 1153;
	private static final int SENTRY_BLADE1 = 1154;
	private static final int SENTRY_BLADE2 = 1155;
	private static final int SENTRY_BLADE3 = 1156;
	private static final int OLD_BRONZE_SWORD = 1157;
	// Misc
	private static final int MIN_LVL = 3;
	private static final Map<Integer, Integer> SENTRIES = new HashMap<>();
	static
	{
		SENTRIES.put(KRISTIN, SENTRY_BLADE3);
		SENTRIES.put(ROSELYN, SENTRY_BLADE2);
	}
	
	public Q00168_DeliverSupplies()
	{
		super(168, Q00168_DeliverSupplies.class.getSimpleName(), "Deliver Supplies");
		addStartNpc(JENNA);
		addTalkId(JENNA, ROSELYN, KRISTIN, HARANT);
		registerQuestItems(JENNAS_LETTER, SENTRY_BLADE1, SENTRY_BLADE2, SENTRY_BLADE3, OLD_BRONZE_SWORD);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if ((st != null) && event.equals("30349-03.htm"))
		{
			st.startQuest();
			st.giveItems(JENNAS_LETTER, 1);
			return event;
		}
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (st != null)
		{
			switch (npc.getId())
			{
				case JENNA:
				{
					switch (st.getState())
					{
						case State.CREATED:
						{
							htmltext = (player.getRace() == Race.DARK_ELF) ? (player.getLevel() >= MIN_LVL) ? "30349-02.htm" : "30349-01.htm" : "30349-00.htm";
							break;
						}
						case State.STARTED:
						{
							switch (st.getCond())
							{
								case 1:
								{
									if (st.hasQuestItems(JENNAS_LETTER))
									{
										htmltext = "30349-04.html";
									}
									break;
								}
								case 2:
								{
									if (st.hasQuestItems(SENTRY_BLADE1, SENTRY_BLADE2, SENTRY_BLADE3))
									{
										st.takeItems(SENTRY_BLADE1, -1);
										st.setCond(3, true);
										htmltext = "30349-05.html";
									}
									break;
								}
								case 3:
								{
									if (hasAtLeastOneQuestItem(player, SENTRY_BLADE2, SENTRY_BLADE3))
									{
										htmltext = "30349-07.html";
									}
									break;
								}
								case 4:
								{
									if (st.getQuestItemsCount(OLD_BRONZE_SWORD) >= 2)
									{
										st.giveAdena(820, true);
										st.exitQuest(false, true);
										htmltext = "30349-07.html";
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
				case HARANT:
				{
					if (st.isCond(1) && st.hasQuestItems(JENNAS_LETTER))
					{
						st.takeItems(JENNAS_LETTER, -1);
						st.giveItems(SENTRY_BLADE1, 1);
						st.giveItems(SENTRY_BLADE2, 1);
						st.giveItems(SENTRY_BLADE3, 1);
						st.setCond(2, true);
						htmltext = "30360-01.html";
					}
					else if (st.isCond(2))
					{
						htmltext = "30360-02.html";
					}
					break;
				}
				case ROSELYN:
				case KRISTIN:
				{
					if (st.isCond(3) && st.hasQuestItems(SENTRIES.get(npc.getId())))
					{
						st.takeItems(SENTRIES.get(npc.getId()), -1);
						st.giveItems(OLD_BRONZE_SWORD, 1);
						if (st.getQuestItemsCount(OLD_BRONZE_SWORD) >= 2)
						{
							st.setCond(4, true);
						}
						htmltext = npc.getId() + "-01.html";
					}
					else if (!st.hasQuestItems(SENTRIES.get(npc.getId())) && st.hasQuestItems(OLD_BRONZE_SWORD))
					{
						htmltext = npc.getId() + "-02.html";
					}
					break;
				}
			}
		}
		return htmltext;
	}
}