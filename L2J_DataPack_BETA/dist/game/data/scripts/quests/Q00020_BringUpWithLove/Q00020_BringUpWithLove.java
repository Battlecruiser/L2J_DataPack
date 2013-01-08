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
package quests.Q00020_BringUpWithLove;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Bring Up With Love (20)
 * @author Gnacik
 * @version 2010-09-29 Based on official server Franz
 */
public class Q00020_BringUpWithLove extends Quest
{
	// NPC
	private static final int TUNATUN = 31537;
	// Items
	private static final int BEAST_WHIP = 15473;
	private static final int CRYSTAL = 9553;
	private static final int JEWEL = 7185;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == TUNATUN)
		{
			if (event.equalsIgnoreCase("31537-12.htm"))
			{
				st.startQuest();
			}
			else if (event.equalsIgnoreCase("31537-03.htm"))
			{
				if (st.hasQuestItems(BEAST_WHIP))
				{
					return "31537-03a.htm";
				}
				st.giveItems(BEAST_WHIP, 1);
			}
			else if (event.equalsIgnoreCase("31537-15.htm"))
			{
				st.takeItems(JEWEL, -1);
				st.giveItems(CRYSTAL, 1);
				st.exitQuest(false, true);
			}
			else if (event.equalsIgnoreCase("31537-21.html"))
			{
				if (player.getLevel() < 82)
				{
					return "31537-23.html";
				}
				if (st.hasQuestItems(BEAST_WHIP))
				{
					return "31537-22.html";
				}
				st.giveItems(BEAST_WHIP, 1);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == TUNATUN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82)
					{
						htmltext = "31537-01.htm";
					}
					else
					{
						htmltext = "31537-00.htm";
					}
					break;
				case State.STARTED:
					if (st.isCond(1))
					{
						htmltext = "31537-13.htm";
					}
					else if (st.isCond(2))
					{
						htmltext = "31537-14.htm";
					}
					break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			newQuestState(player);
		}
		return "31537-20.html";
	}
	
	public Q00020_BringUpWithLove(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(TUNATUN);
		addTalkId(TUNATUN);
		addFirstTalkId(TUNATUN);
	}
	
	public static void main(String[] args)
	{
		new Q00020_BringUpWithLove(20, Q00020_BringUpWithLove.class.getSimpleName(), "Bring Up With Love");
	}
}
