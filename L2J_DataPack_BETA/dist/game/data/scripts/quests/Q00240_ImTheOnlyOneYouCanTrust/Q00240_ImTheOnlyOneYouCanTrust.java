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
package quests.Q00240_ImTheOnlyOneYouCanTrust;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * I'm the Only One You Can Trust (240)
 * @author malyelfik
 */
public class Q00240_ImTheOnlyOneYouCanTrust extends Quest
{
	// NPC
	private static final int KINTAIJIN = 32640;
	// Monster
	private static final int[] MOBS =
	{
		22617,
		22618,
		22619,
		22620,
		22621,
		22622,
		22623,
		22624,
		22625,
		22626,
		22627,
		22628,
		22629,
		22630,
		22631,
		22632,
		22633
	};
	// Item
	private static final int STAKATO_FANG = 14879;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equalsIgnoreCase("32640-3.htm"))
		{
			st.startQuest();
		}
		return event;
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
				htmltext = (player.getLevel() >= 81) ? "32640-1.htm" : "32640-0.htm";
				break;
			case State.STARTED:
				switch (st.getCond())
				{
					case 1:
						htmltext = (!st.hasQuestItems(STAKATO_FANG)) ? "32640-8.html" : "32640-9.html";
						break;
					case 2:
						if (st.getQuestItemsCount(STAKATO_FANG) >= 25)
						{
							st.giveAdena(147200, true);
							st.takeItems(STAKATO_FANG, -1);
							st.addExpAndSp(589542, 36800);
							st.exitQuest(false, true);
							htmltext = "32640-10.html";
						}
						break;
				}
				break;
			case State.COMPLETED:
				htmltext = "32640-11.html";
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, "1");
		if (partyMember == null)
		{
			return super.onKill(npc, player, isPet);
		}
		
		final QuestState st = partyMember.getQuestState(getName());
		st.giveItems(STAKATO_FANG, 1);
		if (st.getQuestItemsCount(STAKATO_FANG) >= 25)
		{
			st.setCond(2, true);
		}
		else
		{
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		return super.onKill(npc, player, isPet);
	}
	
	public Q00240_ImTheOnlyOneYouCanTrust(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KINTAIJIN);
		addTalkId(KINTAIJIN);
		addKillId(MOBS);
		registerQuestItems(STAKATO_FANG);
	}
	
	public static void main(String[] args)
	{
		new Q00240_ImTheOnlyOneYouCanTrust(240, Q00240_ImTheOnlyOneYouCanTrust.class.getSimpleName(), "I'm the Only One You Can Trust");
	}
}