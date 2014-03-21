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
package quests.Q00008_AnAdventureBegins;

import com.l2jserver.gameserver.enums.PcRace;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * An Adventure Begins (8)
 * @author malyelfik
 */
public class Q00008_AnAdventureBegins extends Quest
{
	// NPCs
	private static final int JASMINE = 30134;
	private static final int ROSELYN = 30355;
	private static final int HARNE = 30144;
	// Items
	private static final int ROSELYNS_NOTE = 7573;
	private static final int SCROLL_OF_ESCAPE_GIRAN = 7559;
	private static final int MARK_OF_TRAVELER = 7570;
	// Misc
	private static final int MIN_LEVEL = 3;
	
	public Q00008_AnAdventureBegins()
	{
		super(8, Q00008_AnAdventureBegins.class.getSimpleName(), "An Adventure Begins");
		addStartNpc(JASMINE);
		addTalkId(JASMINE, ROSELYN, HARNE);
		registerQuestItems(ROSELYNS_NOTE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30134-03.htm":
				st.startQuest();
				break;
			case "30134-06.html":
				st.giveItems(SCROLL_OF_ESCAPE_GIRAN, 1);
				st.giveItems(MARK_OF_TRAVELER, 1);
				st.exitQuest(false, true);
				break;
			case "30355-02.html":
				st.setCond(2, true);
				st.giveItems(ROSELYNS_NOTE, 1);
				break;
			case "30144-02.html":
				if (!st.hasQuestItems(ROSELYNS_NOTE))
				{
					return "30144-03.html";
				}
				st.takeItems(ROSELYNS_NOTE, -1);
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
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case JASMINE:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = ((player.getRace() == PcRace.DarkElf) && (player.getLevel() >= MIN_LEVEL)) ? "30134-02.htm" : "30134-01.html";
						break;
					case State.STARTED:
						if (st.isCond(1))
						{
							htmltext = "30134-04.html";
						}
						else if (st.isCond(3))
						{
							htmltext = "30134-05.html";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case ROSELYN:
				if (st.isStarted())
				{
					if (st.isCond(1))
					{
						htmltext = "30355-01.html";
					}
					else if (st.isCond(2))
					{
						htmltext = "30355-03.html";
					}
				}
				break;
			case HARNE:
				if (st.isStarted())
				{
					if (st.isCond(2))
					{
						htmltext = "30144-01.html";
					}
					else if (st.isCond(3))
					{
						htmltext = "30144-04.html";
					}
				}
				break;
		}
		return htmltext;
	}
}