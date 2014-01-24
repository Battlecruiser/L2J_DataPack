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
package quests.Q00019_GoToThePastureland;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Go to the Pastureland (19)<br>
 * Original Jython script by disKret.
 * @author malyelfik
 */
public class Q00019_GoToThePastureland extends Quest
{
	// NPCs
	private static final int VLADIMIR = 31302;
	private static final int TUNATUN = 31537;
	// Items
	private static final int VEAL = 15532;
	private static final int YOUNG_WILD_BEAST_MEAT = 7547;
	
	public Q00019_GoToThePastureland(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(VLADIMIR);
		addTalkId(VLADIMIR, TUNATUN);
		registerQuestItems(VEAL, YOUNG_WILD_BEAST_MEAT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equalsIgnoreCase("31302-02.htm"))
		{
			st.startQuest();
			st.giveItems(VEAL, 1);
		}
		else if (event.equalsIgnoreCase("31537-02.html"))
		{
			if (st.hasQuestItems(YOUNG_WILD_BEAST_MEAT))
			{
				st.giveAdena(50000, true);
				st.addExpAndSp(136766, 12688);
				st.exitQuest(false, true);
				htmltext = "31537-02.html";
			}
			else if (st.hasQuestItems(VEAL))
			{
				st.giveAdena(147200, true);
				st.addExpAndSp(385040, 75250);
				st.exitQuest(false, true);
				htmltext = "31537-02.html";
			}
			else
			{
				htmltext = "31537-03.html";
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
		
		if (npc.getId() == VLADIMIR)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82)
					{
						htmltext = "31302-01.htm";
					}
					else
					{
						htmltext = "31302-03.html";
					}
					break;
				case State.STARTED:
					htmltext = "31302-04.html";
					break;
				case State.COMPLETED:
					htmltext = getAlreadyCompletedMsg(player);
					break;
			}
		}
		else if ((npc.getId() == TUNATUN) && (st.isCond(1)))
		{
			htmltext = "31537-01.html";
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00019_GoToThePastureland(19, Q00019_GoToThePastureland.class.getSimpleName(), "Go to the Pastureland");
	}
}
