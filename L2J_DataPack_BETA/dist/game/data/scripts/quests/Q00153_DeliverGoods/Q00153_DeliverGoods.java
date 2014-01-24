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
package quests.Q00153_DeliverGoods;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Deliver Goods (153)
 * @author Zoey76
 * @version 1.0 Freya (11/16/2010), Based on Naia (EURO)
 */
public class Q00153_DeliverGoods extends Quest
{
	// NPCs
	private static final int JACKSON_ID = 30002;
	private static final int SILVIA_ID = 30003;
	private static final int ARNOLD_ID = 30041;
	private static final int RANT_ID = 30054;
	// Items
	private static final int DELIVERY_LIST_ID = 1012;
	private static final int HEAVY_WOOD_BOX_ID = 1013;
	private static final int CLOTH_BUNDLE_ID = 1014;
	private static final int CLAY_POT_ID = 1015;
	private static final int JACKSONS_RECEIPT_ID = 1016;
	private static final int SILVIAS_RECEIPT_ID = 1017;
	private static final int RANTS_RECEIPT_ID = 1018;
	// Rewards
	private static final int SOULSHOT_NO_GRADE_ID = 1835; // You get 3 Soulshots no grade.
	private static final int RING_OF_KNOWLEDGE_ID = 875;
	private static final int XP_REWARD_AMOUNT = 600;
	
	public Q00153_DeliverGoods(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ARNOLD_ID);
		addTalkId(JACKSON_ID, SILVIA_ID, ARNOLD_ID, RANT_ID);
		registerQuestItems(DELIVERY_LIST_ID, HEAVY_WOOD_BOX_ID, CLOTH_BUNDLE_ID, CLAY_POT_ID, JACKSONS_RECEIPT_ID, SILVIAS_RECEIPT_ID, RANTS_RECEIPT_ID);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && (npc.getId() == ARNOLD_ID))
		{
			if (event.equalsIgnoreCase("30041-02.html"))
			{
				st.startQuest();
				st.giveItems(DELIVERY_LIST_ID, 1);
				st.giveItems(HEAVY_WOOD_BOX_ID, 1);
				st.giveItems(CLOTH_BUNDLE_ID, 1);
				st.giveItems(CLAY_POT_ID, 1);
			}
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st != null)
		{
			if (npc.getId() == ARNOLD_ID)
			{
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= 2) ? "30041-01.htm" : "30041-00.htm";
						break;
					case State.STARTED:
						if (st.isCond(1))
						{
							htmltext = "30041-03.html";
						}
						else if (st.isCond(2))
						{
							st.takeItems(DELIVERY_LIST_ID, -1);
							st.takeItems(JACKSONS_RECEIPT_ID, -1);
							st.takeItems(SILVIAS_RECEIPT_ID, -1);
							st.takeItems(RANTS_RECEIPT_ID, -1);
							// On retail it gives 2 rings but one at the time.
							st.giveItems(RING_OF_KNOWLEDGE_ID, 1);
							st.giveItems(RING_OF_KNOWLEDGE_ID, 1);
							st.addExpAndSp(XP_REWARD_AMOUNT, 0);
							st.exitQuest(false);
							htmltext = "30041-04.html";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
			}
			else
			{
				if (npc.getId() == JACKSON_ID)
				{
					if (st.hasQuestItems(HEAVY_WOOD_BOX_ID))
					{
						st.takeItems(HEAVY_WOOD_BOX_ID, -1);
						st.giveItems(JACKSONS_RECEIPT_ID, 1);
						htmltext = "30002-01.html";
					}
					else
					{
						htmltext = "30002-02.html";
					}
				}
				else if (npc.getId() == SILVIA_ID)
				{
					if (st.hasQuestItems(CLOTH_BUNDLE_ID))
					{
						st.takeItems(CLOTH_BUNDLE_ID, -1);
						st.giveItems(SILVIAS_RECEIPT_ID, 1);
						st.giveItems(SOULSHOT_NO_GRADE_ID, 3);
						htmltext = "30003-01.html";
					}
					else
					{
						htmltext = "30003-02.html";
					}
				}
				else if (npc.getId() == RANT_ID)
				{
					if (st.hasQuestItems(CLAY_POT_ID))
					{
						st.takeItems(CLAY_POT_ID, -1);
						st.giveItems(RANTS_RECEIPT_ID, 1);
						htmltext = "30054-01.html";
					}
					else
					{
						htmltext = "30054-02.html";
					}
				}
				
				if (st.isCond(1) && st.hasQuestItems(JACKSONS_RECEIPT_ID) && st.hasQuestItems(SILVIAS_RECEIPT_ID) && st.hasQuestItems(RANTS_RECEIPT_ID))
				{
					st.setCond(2, true);
				}
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00153_DeliverGoods(153, Q00153_DeliverGoods.class.getSimpleName(), "Deliver Goods");
	}
}
