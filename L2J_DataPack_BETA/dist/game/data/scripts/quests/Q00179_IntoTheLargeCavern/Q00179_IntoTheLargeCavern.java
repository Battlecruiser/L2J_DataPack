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
package quests.Q00179_IntoTheLargeCavern;

import com.l2jserver.gameserver.enums.PcRace;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Into the Large Cavern (179)
 * @author Gnacik
 * @version 2010-10-15 Based on official server Naia
 */
public class Q00179_IntoTheLargeCavern extends Quest
{
	// NPCs
	private static final int KEKROPUS = 32138;
	private static final int MENACING_MACHINE = 32258;
	
	public Q00179_IntoTheLargeCavern(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KEKROPUS);
		addTalkId(KEKROPUS, MENACING_MACHINE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getId() == KEKROPUS)
		{
			if (event.equalsIgnoreCase("32138-03.html"))
			{
				st.startQuest();
			}
		}
		else if (npc.getId() == MENACING_MACHINE)
		{
			if (event.equalsIgnoreCase("32258-08.html"))
			{
				st.giveItems(391, 1);
				st.giveItems(413, 1);
				st.exitQuest(false, true);
			}
			else if (event.equalsIgnoreCase("32258-09.html"))
			{
				st.giveItems(847, 2);
				st.giveItems(890, 2);
				st.giveItems(910, 1);
				st.exitQuest(false, true);
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
		
		if (npc.getId() == KEKROPUS)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getRace() != PcRace.Kamael)
					{
						htmltext = "32138-00b.html";
					}
					else
					{
						final QuestState prev = player.getQuestState("178_IconicTrinity");
						final int level = player.getLevel();
						if ((prev != null) && prev.isCompleted() && (level >= 17) && (level <= 21) && (player.getClassId().level() == 0))
						{
							htmltext = "32138-01.htm";
						}
						else if (level < 17)
						{
							htmltext = "32138-00.html";
						}
						else
						{
							htmltext = "32138-00c.html";
						}
					}
					break;
				case State.STARTED:
					if (st.isCond(1))
					{
						htmltext = "32138-03.htm";
					}
					break;
				case State.COMPLETED:
					htmltext = getAlreadyCompletedMsg(player);
					break;
			}
		}
		else if ((npc.getId() == MENACING_MACHINE) && (st.getState() == State.STARTED))
		{
			htmltext = "32258-01.html";
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00179_IntoTheLargeCavern(179, Q00179_IntoTheLargeCavern.class.getSimpleName(), "Into The Large Cavern");
	}
}
