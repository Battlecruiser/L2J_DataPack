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
package quests.Q00033_MakeAPairOfDressShoes;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Make a Pair of Dress Shoes (33)
 * @author malyelfik
 */
public class Q00033_MakeAPairOfDressShoes extends Quest
{
	// NPCs
	private static final int IAN = 30164;
	private static final int WOODLEY = 30838;
	private static final int LEIKAR = 31520;
	// Items
	private static final int LEATHER = 1882;
	private static final int THREAD = 1868;
	private static final int DRESS_SHOES_BOX = 7113;
	// Misc
	private static final int MIN_LEVEL = 60;
	private static final int LEATHER_COUNT = 200;
	private static final int THREAD_COUNT = 600;
	private static final int ADENA_COUNT = 500000;
	private static final int ADENA_COUNT2 = 200000;
	private static final int ADENA_COUNT3 = 300000;
	
	private Q00033_MakeAPairOfDressShoes(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(WOODLEY);
		addTalkId(WOODLEY, IAN, LEIKAR);
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
			case "30838-03.htm":
				st.startQuest();
				break;
			case "30838-06.html":
				st.setCond(3, true);
				break;
			case "30838-09.html":
				if ((st.getQuestItemsCount(LEATHER) >= LEATHER_COUNT) && (st.getQuestItemsCount(THREAD) >= THREAD_COUNT) && (player.getAdena() >= ADENA_COUNT2))
				{
					st.takeItems(LEATHER, LEATHER_COUNT);
					st.takeItems(THREAD, LEATHER_COUNT);
					st.takeItems(PcInventory.ADENA_ID, ADENA_COUNT2);
					st.setCond(4, true);
				}
				else
				{
					htmltext = "30838-10.html";
				}
				break;
			case "30838-13.html":
				st.giveItems(DRESS_SHOES_BOX, 1);
				st.exitQuest(false, true);
				break;
			case "31520-02.html":
				st.setCond(2, true);
				break;
			case "30164-02.html":
				if (player.getAdena() < ADENA_COUNT3)
				{
					return "30164-03.html";
				}
				st.takeItems(PcInventory.ADENA_ID, ADENA_COUNT3);
				st.setCond(5, true);
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
		
		switch (npc.getNpcId())
		{
			case WOODLEY:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= MIN_LEVEL) ? "30838-01.htm" : "30838-02.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "30838-04.html";
								break;
							case 2:
								htmltext = "30838-05.html";
								break;
							case 3:
								htmltext = ((st.getQuestItemsCount(LEATHER) >= LEATHER_COUNT) && (st.getQuestItemsCount(THREAD) >= THREAD_COUNT) && (player.getAdena() >= ADENA_COUNT)) ? "30838-07.html" : "30838-08.html";
								break;
							case 4:
								htmltext = "30838-11.html";
								break;
							case 5:
								htmltext = "30838-12.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case LEIKAR:
				if (st.isStarted())
				{
					if (st.isCond(1))
					{
						htmltext = "31520-01.html";
					}
					else if (st.isCond(2))
					{
						htmltext = "31520-03.html";
					}
				}
				break;
			case IAN:
				if (st.isStarted())
				{
					if (st.isCond(4))
					{
						htmltext = "30164-01.html";
					}
					else if (st.isCond(5))
					{
						htmltext = "30164-04.html";
					}
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00033_MakeAPairOfDressShoes(33, Q00033_MakeAPairOfDressShoes.class.getSimpleName(), "Make a Pair of Dress Shoes");
	}
}