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
package quests.Q00189_ContractCompletion;

import quests.Q00186_ContractExecution.Q00186_ContractExecution;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Contract Completion (189)
 * @author ivantotov
 */
public final class Q00189_ContractCompletion extends Quest
{
	// NPCs
	private static final int BLUEPRINT_SELLER_LUKA = 31437;
	private static final int HEAD_BLACKSMITH_KUSTO = 30512;
	private static final int RESEARCHER_LORAIN = 30673;
	private static final int SHEGFIELD = 30068;
	// Items
	private static final int SCROLL_OF_DECODING = 10370;
	// Misc
	private static final int MIN_LEVEL = 42;
	private static final int MAX_LEVEL_FOR_EXP_SP = 48;
	
	public Q00189_ContractCompletion()
	{
		super(189, Q00189_ContractCompletion.class.getSimpleName(), "Contract Completion");
		addStartNpc(BLUEPRINT_SELLER_LUKA);
		addTalkId(BLUEPRINT_SELLER_LUKA, HEAD_BLACKSMITH_KUSTO, RESEARCHER_LORAIN, SHEGFIELD);
		registerQuestItems(SCROLL_OF_DECODING);
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
			case "30068-02.html":
			{
				htmltext = event;
				break;
			}
			case "31437-03.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					st.giveItems(SCROLL_OF_DECODING, 1);
					htmltext = event;
				}
				break;
			}
			case "30512-02.html":
			{
				if (st.isCond(4))
				{
					st.giveAdena(121527, true);
					if (player.getLevel() < MAX_LEVEL_FOR_EXP_SP)
					{
						st.addExpAndSp(309467, 20614);
					}
					st.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
			case "30673-02.html":
			{
				if (st.isCond(1))
				{
					st.setCond(2, true);
					st.takeItems(SCROLL_OF_DECODING, -1);
					htmltext = event;
				}
				break;
			}
			case "30068-03.html":
			{
				if (st.isCond(2))
				{
					st.setCond(3, true);
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
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case BLUEPRINT_SELLER_LUKA:
			{
				switch (st.getState())
				{
					case State.CREATED:
					{
						final QuestState qs = player.getQuestState(Q00186_ContractExecution.class.getSimpleName());
						if ((qs != null) && qs.isCompleted())
						{
							htmltext = (player.getLevel() >= MIN_LEVEL) ? "31437-01.htm" : "31437-02.htm";
						}
						break;
					}
					case State.STARTED:
					{
						if (st.getCond() >= 1)
						{
							htmltext = "31437-04.html";
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
			case HEAD_BLACKSMITH_KUSTO:
			{
				if (st.isCond(4))
				{
					htmltext = "30512-01.html";
				}
				break;
			}
			case RESEARCHER_LORAIN:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "30673-01.html";
						break;
					}
					case 2:
					{
						htmltext = "30673-03.html";
						break;
					}
					case 3:
					{
						st.setCond(4, true);
						htmltext = "30673-04.html";
						break;
					}
					case 4:
					{
						htmltext = "30673-05.html";
						break;
					}
				}
				break;
			}
			case SHEGFIELD:
			{
				switch (st.getCond())
				{
					case 2:
					{
						htmltext = "30068-01.html";
						break;
					}
					case 3:
					{
						htmltext = "30068-04.html";
						break;
					}
				}
				break;
			}
		}
		return htmltext;
	}
}