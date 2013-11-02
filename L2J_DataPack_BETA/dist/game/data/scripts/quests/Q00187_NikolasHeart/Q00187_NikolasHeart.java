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
package quests.Q00187_NikolasHeart;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Nikola's Heart (187)
 * @author ivantotov
 */
public final class Q00187_NikolasHeart extends Quest
{
	// NPCs
	private static final int HEAD_BLACKSMITH_KUSTO = 30512;
	private static final int MAESTRO_NIKOLA = 30621;
	private static final int RESEARCHER_LORAIN = 30673;
	// Items
	private static final int LORAINES_CERTIFICATE = 10362;
	private static final int METALLOGRAPH = 10368;
	// Misc
	private static final int MIN_LEVEL = 41;
	private static final int MIN_LEVEL_FOR_EXP_SP = 47;
	
	private Q00187_NikolasHeart(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(RESEARCHER_LORAIN);
		addTalkId(HEAD_BLACKSMITH_KUSTO, RESEARCHER_LORAIN, MAESTRO_NIKOLA);
		registerQuestItems(METALLOGRAPH);
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
			case "30621-02.html":
			case "30512-02.html":
			{
				htmltext = event;
				break;
			}
			case "30673-03.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					takeItems(player, LORAINES_CERTIFICATE, -1);
					giveItems(player, METALLOGRAPH, 1);
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
			case "30512-03.html":
			{
				if (st.isCond(2))
				{
					st.giveAdena(93383, true);
					if (player.getLevel() < MIN_LEVEL_FOR_EXP_SP)
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
						final QuestState qs = player.getQuestState("185_Nikolas_Cooperation_Consideration");
						if ((qs != null) && qs.isCompleted() && st.hasQuestItems(LORAINES_CERTIFICATE))
						{
							htmltext = player.getLevel() < MIN_LEVEL ? "30673-02.htm" : "30673-01.htm";
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
					htmltext = st.isCond(1) ? "30621-01.html" : "30621-04.html";
				}
				break;
			}
			case HEAD_BLACKSMITH_KUSTO:
			{
				if (st.isCond(2) && st.hasQuestItems(METALLOGRAPH))
				{
					htmltext = "30512-01.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00187_NikolasHeart(187, Q00187_NikolasHeart.class.getSimpleName(), "Nikola's Heart");
	}
}
