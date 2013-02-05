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
package quests.Q00146_TheZeroHour;

import quests.Q00109_InSearchOfTheNest.Q00109_InSearchOfTheNest;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * The Zero Hour (146)
 * @author Gnacik, malyelfik
 */
public class Q00146_TheZeroHour extends Quest
{
	// NPCs
	private static final int KAHMAN = 31554;
	private static final int QUEEN_SHYEED = 25671;
	// Item
	private static final int KAHMANS_SUPPLY_BOX = 14849;
	private static final int FANG = 14859;
	
	public Q00146_TheZeroHour(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KAHMAN);
		addTalkId(KAHMAN);
		addKillId(QUEEN_SHYEED);
		registerQuestItems(FANG);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equalsIgnoreCase("31554-03.htm"))
		{
			st.startQuest();
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		final L2PcInstance partyMember = getRandomPartyMember(killer, 1);
		if (partyMember != null)
		{
			final QuestState st = partyMember.getQuestState(getName());
			if (!st.hasQuestItems(FANG))
			{
				st.giveItems(FANG, 1);
				st.setCond(2, true);
			}
		}
		return super.onKill(npc, killer, isPet);
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
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getLevel() < 81)
				{
					htmltext = "31554-02.htm";
				}
				else
				{
					final QuestState prev = player.getQuestState(Q00109_InSearchOfTheNest.class.getSimpleName());
					if ((prev != null) && prev.isCompleted())
					{
						htmltext = "31554-01a.htm";
					}
					else
					{
						htmltext = "31554-04.html";
					}
				}
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					htmltext = "31554-06.html";
				}
				else
				{
					st.giveItems(KAHMANS_SUPPLY_BOX, 1);
					st.addExpAndSp(154616, 12500);
					st.exitQuest(false, true);
					htmltext = "31554-05.html";
				}
				break;
			case State.COMPLETED:
				htmltext = "31554-01b.htm";
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00146_TheZeroHour(146, Q00146_TheZeroHour.class.getSimpleName(), "The Zero Hour");
	}
}
