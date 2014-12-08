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
package quests.Q00006_StepIntoTheFuture;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Step Into the Future (6)
 * @author malyelfik
 */
public class Q00006_StepIntoTheFuture extends Quest
{
	// NPCs
	private static final int ROXXY = 30006;
	private static final int BAULRO = 30033;
	private static final int SIR_COLLIN = 30311;
	// Items
	private static final int BAULRO_LETTER = 7571;
	private static final int SCROLL_OF_ESCAPE_GIRAN = 7559;
	private static final int MARK_OF_TRAVELER = 7570;
	// Misc
	private static final int MIN_LEVEL = 3;
	
	public Q00006_StepIntoTheFuture()
	{
		super(6, Q00006_StepIntoTheFuture.class.getSimpleName(), "Step Into the Future");
		addStartNpc(ROXXY);
		addTalkId(ROXXY, BAULRO, SIR_COLLIN);
		registerQuestItems(BAULRO_LETTER);
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
			case "30006-03.htm":
				st.startQuest();
				break;
			case "30006-06.html":
				st.giveItems(SCROLL_OF_ESCAPE_GIRAN, 1);
				st.giveItems(MARK_OF_TRAVELER, 1);
				st.exitQuest(false, true);
				break;
			case "30033-02.html":
				st.setCond(2, true);
				st.giveItems(BAULRO_LETTER, 1);
				break;
			case "30311-02.html":
				if (!st.hasQuestItems(BAULRO_LETTER))
				{
					return "30311-03.html";
				}
				st.takeItems(BAULRO_LETTER, -1);
				st.setCond(3, true);
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
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
			case ROXXY:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = ((player.getRace() == Race.HUMAN) && (player.getLevel() >= MIN_LEVEL)) ? "30006-02.htm" : "30006-01.html";
						break;
					case State.STARTED:
						if (st.isCond(1))
						{
							htmltext = "30006-04.html";
						}
						else if (st.isCond(3))
						{
							htmltext = "30006-05.html";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case BAULRO:
				if (st.isStarted())
				{
					if (st.isCond(1))
					{
						htmltext = "30033-01.html";
					}
					else if (st.isCond(2))
					{
						htmltext = "30033-03.html";
					}
				}
				break;
			case SIR_COLLIN:
				if (st.isStarted())
				{
					if (st.isCond(2))
					{
						htmltext = "30311-01.html";
					}
					else if (st.isCond(3))
					{
						htmltext = "30311-04.html";
					}
				}
				break;
		}
		return htmltext;
	}
}