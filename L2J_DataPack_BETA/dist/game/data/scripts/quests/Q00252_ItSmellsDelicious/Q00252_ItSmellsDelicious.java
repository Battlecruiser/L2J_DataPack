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
package quests.Q00252_ItSmellsDelicious;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * It Smells Delicious! (252)<br>
 * Updated by corbin12, thanks VlLight for help.
 * @author Dumpster, jurchiks
 */
public class Q00252_ItSmellsDelicious extends Quest
{
	// NPC
	public static final int STAN = 30200;
	// Items
	public static final int DIARY = 15500;
	public static final int COOKBOOK_PAGE = 15501;
	// Monsters
	private static final int[] MOBS =
	{
		22786,
		22787,
		22788
	};
	private static final int CHEF = 18908;
	// Misc
	private static final double DIARY_CHANCE = 0.599;
	private static final int DIARY_MAX_COUNT = 10;
	private static final double COOKBOOK_PAGE_CHANCE = 0.36;
	private static final int COOKBOOK_PAGE_MAX_COUNT = 5;
	
	public Q00252_ItSmellsDelicious(int id, String name, String descr)
	{
		super(id, name, descr);
		addStartNpc(STAN);
		addTalkId(STAN);
		addKillId(CHEF);
		addKillId(MOBS);
		registerQuestItems(DIARY, COOKBOOK_PAGE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		
		switch (event)
		{
			case "30200-05.htm":
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			case "30200-08.html":
				if (st.isCond(2))
				{
					giveAdena(player, 147656, true);
					st.addExpAndSp(716238, 78324);
					st.exitQuest(false, true);
					htmltext = event;
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs;
		if (npc.getId() == CHEF) // only the killer gets quest items from the chef
		{
			qs = killer.getQuestState(getName());
			if ((qs != null) && qs.isCond(1))
			{
				if (giveItemRandomly(killer, npc, COOKBOOK_PAGE, 1, COOKBOOK_PAGE_MAX_COUNT, COOKBOOK_PAGE_CHANCE, true))
				{
					if (hasMaxDiaries(qs))
					{
						qs.setCond(2, true);
					}
				}
			}
		}
		else
		{
			qs = getRandomPartyMemberState(killer, 1, 3, npc);
			if ((qs != null) && qs.isCond(1))
			{
				if (giveItemRandomly(killer, npc, DIARY, 1, DIARY_MAX_COUNT, DIARY_CHANCE, true))
				{
					if (hasMaxCookbookPages(qs))
					{
						qs.setCond(2, true);
					}
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public boolean checkPartyMember(QuestState qs, L2Npc npc)
	{
		return !hasMaxDiaries(qs);
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
		
		if (npc.getId() == STAN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					htmltext = ((player.getLevel() >= 82) ? "30200-01.htm" : "30200-02.htm");
					break;
				case State.STARTED:
					switch (st.getCond())
					{
						case 1:
							htmltext = "30200-06.html";
							break;
						case 2:
							if (hasMaxDiaries(st) && hasMaxCookbookPages(st))
							{
								htmltext = "30200-07.html";
							}
							break;
					}
					break;
				case State.COMPLETED:
					htmltext = "30200-03.html";
					break;
			}
		}
		return htmltext;
	}
	
	private static boolean hasMaxDiaries(QuestState qs)
	{
		return (qs.getQuestItemsCount(DIARY) >= DIARY_MAX_COUNT);
	}
	
	private static boolean hasMaxCookbookPages(QuestState qs)
	{
		return (qs.getQuestItemsCount(COOKBOOK_PAGE) >= COOKBOOK_PAGE_MAX_COUNT);
	}
	
	public static void main(String[] args)
	{
		new Q00252_ItSmellsDelicious(252, Q00252_ItSmellsDelicious.class.getSimpleName(), "It Smells Delicious!");
	}
}
