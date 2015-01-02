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
package quests.Q00121_PavelTheGiant;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Pavel the Giants (121)<br>
 * Original Jython script by Ethernaly.
 * @author malyelfik
 */
public class Q00121_PavelTheGiant extends Quest
{
	// NPCs
	private static final int NEWYEAR = 31961;
	private static final int YUMI = 32041;
	
	public Q00121_PavelTheGiant()
	{
		super(121, Q00121_PavelTheGiant.class.getSimpleName(), "Pavel the Giant");
		addStartNpc(NEWYEAR);
		addTalkId(NEWYEAR, YUMI);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "31961-02.htm":
				st.startQuest();
				break;
			case "32041-02.html":
				st.addExpAndSp(346320, 26069);
				st.exitQuest(false, true);
				break;
		}
		return event;
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
			case NEWYEAR:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= 70) ? "31961-01.htm" : "31961-00.htm";
						break;
					case State.STARTED:
						htmltext = "31961-03.html";
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case YUMI:
				if (st.isStarted())
				{
					htmltext = "32041-01.html";
				}
				break;
		}
		return htmltext;
	}
}