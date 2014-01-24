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
package quests.Q00277_GatekeepersOffering;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Gatekeeper's Offering (277)
 * @author malyelfik
 */
public class Q00277_GatekeepersOffering extends Quest
{
	// NPC
	private static final int TAMIL = 30576;
	// Monster
	private static final int GREYSTONE_GOLEM = 20333;
	// Items
	private static final int STARSTONE = 1572;
	private static final int GATEKEEPER_CHARM = 1658;
	// Misc
	private static final int MIN_LEVEL = 15;
	private static final int STARSTONE_COUT = 20;
	
	private Q00277_GatekeepersOffering(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(TAMIL);
		addTalkId(TAMIL);
		addKillId(GREYSTONE_GOLEM);
		registerQuestItems(STARSTONE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && event.equalsIgnoreCase("30576-03.htm"))
		{
			if (player.getLevel() < MIN_LEVEL)
			{
				return "30576-01.htm";
			}
			st.startQuest();
			return event;
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = killer.getQuestState(getName());
		if ((st != null) && st.isStarted() && (st.getQuestItemsCount(STARSTONE) < STARSTONE_COUT))
		{
			st.giveItems(STARSTONE, 1);
			if (st.getQuestItemsCount(STARSTONE) >= STARSTONE_COUT)
			{
				st.setCond(2, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, killer, isSummon);
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
				htmltext = "30576-02.htm";
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					htmltext = "30576-04.html";
				}
				else if (st.isCond(2) && (st.getQuestItemsCount(STARSTONE) >= STARSTONE_COUT))
				{
					st.giveItems(GATEKEEPER_CHARM, 2);
					st.exitQuest(true, true);
					htmltext = "30576-05.html";
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00277_GatekeepersOffering(277, Q00277_GatekeepersOffering.class.getSimpleName(), "Gatekeeper's Offering");
	}
}