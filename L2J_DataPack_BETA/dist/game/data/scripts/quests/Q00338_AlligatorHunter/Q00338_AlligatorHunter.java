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
package quests.Q00338_AlligatorHunter;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Alligator Hunter (338)
 * @author malyelfik
 */
public class Q00338_AlligatorHunter extends Quest
{
	// NPC
	private static final int ENVERUN = 30892;
	
	// Monster
	private static final int ALLIGATOR = 20135;
	
	// Items
	private static final int ALLIGATOR_LEATHER = 4337;
	
	// Misc
	private static final int MIN_LEVEL = 40;
	private static final int SECOND_CHANCE = 19;
	
	public Q00338_AlligatorHunter(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ENVERUN);
		addTalkId(ENVERUN);
		addKillId(ALLIGATOR);
		
		registerQuestItems(ALLIGATOR_LEATHER);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30892-03.htm":
				st.startQuest();
				break;
			case "30892-06.html":
				if (!st.hasQuestItems(ALLIGATOR_LEATHER))
				{
					return "30892-05.html";
				}
				int amount = (st.getQuestItemsCount(ALLIGATOR_LEATHER) >= 10) ? 3430 : 0;
				amount += 60 * st.getQuestItemsCount(ALLIGATOR_LEATHER);
				st.giveAdena(amount, true);
				st.takeItems(ALLIGATOR_LEATHER, -1);
				break;
			case "30892-10.html":
				st.exitQuest(true, true);
				break;
			case "30892-07.html":
			case "30892-08.html":
			case "30892-09.html":
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if (st != null)
		{
			st.giveItems(ALLIGATOR_LEATHER, 1);
			if (getRandom(100) < SECOND_CHANCE)
			{
				st.giveItems(ALLIGATOR_LEATHER, 1);
			}
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
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
			case State.CREATED:
				htmltext = (player.getLevel() >= MIN_LEVEL) ? "30892-02.htm" : "30892-01.htm";
				break;
			case State.STARTED:
				htmltext = "30892-04.html";
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00338_AlligatorHunter(338, Q00338_AlligatorHunter.class.getSimpleName(), "Alligator Hunter");
	}
}