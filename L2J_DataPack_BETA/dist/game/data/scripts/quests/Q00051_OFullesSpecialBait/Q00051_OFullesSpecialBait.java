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
package quests.Q00051_OFullesSpecialBait;

import com.l2jserver.Config;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * O'Fulle's Special Bait (51)<br>
 * Original Jython script by Kilkenny.
 * @author nonom
 */
public class Q00051_OFullesSpecialBait extends Quest
{
	// NPCs
	private static final int OFULLE = 31572;
	private static final int FETTERED_SOUL = 20552;
	// Items
	private static final int LOST_BAIT = 7622;
	private static final int ICY_AIR_LURE = 7611;
	
	public Q00051_OFullesSpecialBait(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(OFULLE);
		addTalkId(OFULLE);
		addKillId(FETTERED_SOUL);
		registerQuestItems(LOST_BAIT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = event;
		switch (event)
		{
			case "31572-03.htm":
				st.startQuest();
				break;
			case "31572-07.html":
				if ((st.isCond(2)) && (getQuestItemsCount(player, LOST_BAIT) >= 100))
				{
					htmltext = "31572-06.htm";
					giveItems(player, ICY_AIR_LURE, 4);
					st.exitQuest(false, true);
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, 1);
		if (partyMember == null)
		{
			return null;
		}
		
		final QuestState st = partyMember.getQuestState(getName());
		if (getQuestItemsCount(player, LOST_BAIT) < 100)
		{
			float chance = 33 * Config.RATE_QUEST_DROP;
			if (getRandom(100) < chance)
			{
				st.rewardItems(LOST_BAIT, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		
		if (getQuestItemsCount(player, LOST_BAIT) >= 100)
		{
			st.setCond(2, true);
		}
		return super.onKill(npc, player, isSummon);
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
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				htmltext = (player.getLevel() >= 36) ? "31572-01.htm" : "31572-02.html";
				break;
			case State.STARTED:
				htmltext = (st.isCond(1)) ? "31572-05.html" : "31572-04.html";
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00051_OFullesSpecialBait(51, Q00051_OFullesSpecialBait.class.getSimpleName(), "O'Fulle's Special Bait");
	}
}
