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
package quests.Q10283_RequestOfIceMerchant;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Request of Ice Merchant (10283)
 * @author Gnacik
 * @version 2010-08-07 Based on Freya PTS
 */
public class Q10283_RequestOfIceMerchant extends Quest
{
	// NPCs
	private static final int RAFFORTY = 32020;
	private static final int KIER = 32022;
	private static final int JINIA = 32760;
	
	public Q10283_RequestOfIceMerchant(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(RAFFORTY);
		addTalkId(RAFFORTY, KIER, JINIA);
		addFirstTalkId(JINIA);
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
		
		if (npc.getNpcId() == RAFFORTY)
		{
			if (event.equalsIgnoreCase("32020-03.htm"))
			{
				st.startQuest();
			}
			else if (event.equalsIgnoreCase("32020-07.htm"))
			{
				st.setCond(2, true);
			}
		}
		else if ((npc.getNpcId() == KIER) && event.equalsIgnoreCase("spawn"))
		{
			addSpawn(JINIA, 104322, -107669, -3680, 44954, false, 60000);
			return null;
		}
		else if ((npc.getNpcId() == JINIA) && event.equalsIgnoreCase("32760-04.html"))
		{
			st.giveAdena(190000, true);
			st.addExpAndSp(627000, 50300);
			st.exitQuest(false, true);
			npc.deleteMe();
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
		
		switch (npc.getNpcId())
		{
			case RAFFORTY:
				switch (st.getState())
				{
					case State.CREATED:
						QuestState _prev = player.getQuestState("115_TheOtherSideOfTruth");
						htmltext = ((_prev != null) && _prev.isCompleted() && (player.getLevel() >= 82)) ? "32020-01.htm" : "32020-00.htm";
						break;
					case State.STARTED:
						if (st.isCond(1))
						{
							htmltext = "32020-04.htm";
						}
						else if (st.isCond(2))
						{
							htmltext = "32020-08.htm";
						}
						break;
					case State.COMPLETED:
						htmltext = "32020-09.htm";
						break;
				}
				break;
			case KIER:
				if (st.isCond(2))
				{
					htmltext = "32022-01.html";
				}
				break;
			case JINIA:
				if (st.isCond(2))
				{
					htmltext = "32760-02.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.getInstanceId() > 0)
		{
			return "32760-10.html";
		}
		
		final QuestState st = player.getQuestState(getName());
		if ((npc.getNpcId() == JINIA) && (st != null) && (st.isCond(2)))
		{
			return "32760-01.html";
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q10283_RequestOfIceMerchant(10283, Q10283_RequestOfIceMerchant.class.getSimpleName(), "Request of Ice Merchant");
	}
}
