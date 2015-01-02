/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package quests.Q00012_SecretMeetingWithVarkaSilenos;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Secret Meeting With Varka Silenos (12)<br>
 * Original Jython script by Emperorc.
 * @author nonom
 */
public class Q00012_SecretMeetingWithVarkaSilenos extends Quest
{
	// NPCs
	private static final int CADMON = 31296;
	private static final int HELMUT = 31258;
	private static final int NARAN = 31378;
	// Item
	private static final int BOX = 7232;
	
	public Q00012_SecretMeetingWithVarkaSilenos()
	{
		super(12, Q00012_SecretMeetingWithVarkaSilenos.class.getSimpleName(), "Secret Meeting With Varka Silenos");
		addStartNpc(CADMON);
		addTalkId(CADMON, HELMUT, NARAN);
		registerQuestItems(BOX);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31296-03.html":
				st.startQuest();
				break;
			case "31258-02.html":
				if (st.isCond(1))
				{
					st.setCond(2, true);
					giveItems(player, BOX, 1);
				}
				break;
			case "31378-02.html":
				if (st.isCond(2) && st.hasQuestItems(BOX))
				{
					st.addExpAndSp(233125, 18142);
					st.exitQuest(false, true);
				}
				else
				{
					htmltext = "31378-03.html";
				}
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
		
		final int npcId = npc.getId();
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				if (npcId == CADMON)
				{
					htmltext = (player.getLevel() >= 74) ? "31296-01.htm" : "31296-02.html";
				}
				break;
			case State.STARTED:
				final int cond = st.getInt("cond");
				if ((npcId == CADMON) && (cond == 1))
				{
					htmltext = "31296-04.html";
				}
				else if (npcId == HELMUT)
				{
					if (cond == 1)
					{
						htmltext = "31258-01.html";
					}
					else if (cond == 2)
					{
						htmltext = "31258-03.html";
					}
				}
				else if ((npcId == NARAN) && (cond == 2))
				{
					htmltext = "31378-01.html";
				}
				break;
		}
		return htmltext;
	}
}
