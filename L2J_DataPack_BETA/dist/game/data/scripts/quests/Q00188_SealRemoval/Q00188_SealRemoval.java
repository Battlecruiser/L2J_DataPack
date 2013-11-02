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
package quests.Q00188_SealRemoval;

import quests.Q00186_ContractExecution.Q00186_ContractExecution;
import quests.Q00187_NikolasHeart.Q00187_NikolasHeart;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Seal Removal (188)
 * @author ivantotov
 */
public final class Q00188_SealRemoval extends Quest
{
	// NPCs
	private static final int MAESTRO_NIKOLA = 30621;
	private static final int RESEARCHER_LORAIN = 30673;
	private static final int DOROTHY_LOCKSMITH = 30970;
	// Items
	private static final int LORAINES_CERTIFICATE = 10362;
	private static final int BROKEN_METAL_PIECES = 10369;
	// Misc
	private static final int MIN_LEVEL = 41;
	private static final int MAX_LEVEL_FOR_EXP_SP = 47;
	
	private Q00188_SealRemoval(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(RESEARCHER_LORAIN);
		addTalkId(RESEARCHER_LORAIN, MAESTRO_NIKOLA, DOROTHY_LOCKSMITH);
		registerQuestItems(BROKEN_METAL_PIECES);
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
			case "30970-02.html":
			case "30621-02.html":
			case "30621-04.html":
			{
				htmltext = event;
				break;
			}
			case "30673-03.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					giveItems(player, BROKEN_METAL_PIECES, 1);
					htmltext = event;
				}
				break;
			}
			case "30621-03.html":
			{
				if (st.isCond(1))
				{
					st.setCond(2, true);
					htmltext = event;
				}
				break;
			}
			case "30970-03.html":
			{
				if (st.isCond(2))
				{
					st.giveAdena(98583, true);
					if (player.getLevel() < MAX_LEVEL_FOR_EXP_SP)
					{
						st.addExpAndSp(285935, 18711);
					}
					st.exitQuest(false, true);
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
			case RESEARCHER_LORAIN:
			{
				switch (st.getState())
				{
					case State.CREATED:
					{
						if (st.hasQuestItems(LORAINES_CERTIFICATE))
						{
							final QuestState q184 = player.getQuestState("184_Nikolas_Cooperation_Contract"); // TODO: Update.
							if ((q184 != null) && q184.isCompleted())
							{
								htmltext = (player.getLevel() >= MIN_LEVEL) ? "30673-01.htm" : "30673-02.htm";
							}
						}
						else
						{
							final QuestState q185 = player.getQuestState("185_Nikolas_Cooperation_Consideration"); // TODO: Update.
							final QuestState q186 = player.getQuestState(Q00186_ContractExecution.class.getSimpleName());
							final QuestState q187 = player.getQuestState(Q00187_NikolasHeart.class.getSimpleName());
							if ((q185 != null) && q185.isCompleted() && (q186 != null) && q186.isCompleted() && (q187 != null) && q187.isCompleted())
							{
								htmltext = (player.getLevel() >= MIN_LEVEL) ? "30673-01.htm" : "30673-02.htm";
							}
						}
						break;
					}
					case State.STARTED:
					{
						if (st.getCond() >= 1)
						{
							htmltext = "30673-04.html";
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
			case MAESTRO_NIKOLA:
			{
				if (st.isStarted())
				{
					htmltext = st.isCond(1) ? "30621-01.html" : "30621-05.html";
				}
				break;
			}
			case DOROTHY_LOCKSMITH:
			{
				if (st.isCond(2))
				{
					htmltext = "30970-01.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00188_SealRemoval(188, Q00188_SealRemoval.class.getSimpleName(), "Seal Removal");
	}
}
