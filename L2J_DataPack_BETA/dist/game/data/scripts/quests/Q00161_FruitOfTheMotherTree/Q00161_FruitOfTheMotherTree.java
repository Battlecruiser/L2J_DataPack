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
package quests.Q00161_FruitOfTheMotherTree;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Fruit of the Mother Tree (161)
 * @author malyelfik
 */
public class Q00161_FruitOfTheMotherTree extends Quest
{
	// NPCs
	private static final int ANDELLIA = 30362;
	private static final int THALIA = 30371;
	// Items
	private static final int ANDELLRIAS_LETTER = 1036;
	private static final int MOTHERTREE_FRUIT = 1037;
	// Misc
	private static final int MIN_LEVEL = 3;
	
	public Q00161_FruitOfTheMotherTree()
	{
		super(161, Q00161_FruitOfTheMotherTree.class.getSimpleName(), "Fruit of the Mother Tree");
		addStartNpc(ANDELLIA);
		addTalkId(ANDELLIA, THALIA);
		registerQuestItems(ANDELLRIAS_LETTER, MOTHERTREE_FRUIT);
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
			case "30362-04.htm":
				st.startQuest();
				st.giveItems(ANDELLRIAS_LETTER, 1);
				break;
			case "30371-03.html":
				break;
			default:
				htmltext = null;
				break;
		
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
		
		switch (npc.getId())
		{
			case ANDELLIA:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getRace() == Race.ELF) ? (player.getLevel() >= MIN_LEVEL) ? "30362-03.htm" : "30362-02.htm" : "30362-01.htm";
						break;
					case State.STARTED:
						if (st.isCond(1))
						{
							htmltext = "30362-05.html";
						}
						else if (st.isCond(2) && st.hasQuestItems(MOTHERTREE_FRUIT))
						{
							st.giveAdena(1000, true);
							st.addExpAndSp(1000, 0);
							st.exitQuest(false, true);
							htmltext = "30362-06.html";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case THALIA:
				if (st.isStarted())
				{
					if (st.isCond(1) && st.hasQuestItems(ANDELLRIAS_LETTER))
					{
						st.takeItems(ANDELLRIAS_LETTER, -1);
						st.giveItems(MOTHERTREE_FRUIT, 1);
						st.setCond(2, true);
						htmltext = "30371-01.html";
					}
					else if (st.isCond(2) && st.hasQuestItems(MOTHERTREE_FRUIT))
					{
						htmltext = "30371-02.html";
					}
				}
				break;
		}
		return htmltext;
	}
}