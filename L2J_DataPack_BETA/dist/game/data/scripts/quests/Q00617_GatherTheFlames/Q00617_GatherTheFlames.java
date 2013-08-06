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
package quests.Q00617_GatherTheFlames;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Gather the Flames (617)
 * @author malyelfik
 */
public class Q00617_GatherTheFlames extends Quest
{
	// NPCs
	private static final int HILDA = 31271;
	private static final int VULCAN = 31539;
	private static final int ROONEY = 32049;
	// Item
	private static final int TORCH = 7264;
	// Reward
	private static final int[] REWARD =
	{
		6881,
		6883,
		6885,
		6887,
		6891,
		6893,
		6895,
		6897,
		6899,
		7580
	};
	
	// Monsters
	private static final Map<Integer, Integer> MOBS = new HashMap<>();
	
	static
	{
		MOBS.put(22634, 639);
		MOBS.put(22635, 611);
		MOBS.put(22636, 649);
		MOBS.put(22637, 639);
		MOBS.put(22638, 639);
		MOBS.put(22639, 645);
		MOBS.put(22640, 559);
		MOBS.put(22641, 588);
		MOBS.put(22642, 537);
		MOBS.put(22643, 618);
		MOBS.put(22644, 633);
		MOBS.put(22645, 550);
		MOBS.put(22646, 593);
		MOBS.put(22647, 688);
		MOBS.put(22648, 632);
		MOBS.put(22649, 685);
	}
	
	public Q00617_GatherTheFlames(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(HILDA, VULCAN);
		addTalkId(ROONEY, HILDA, VULCAN);
		addKillId(MOBS.keySet());
		registerQuestItems(TORCH);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = event;
		switch (event)
		{
			case "31539-03.htm":
			case "31271-03.htm":
				st.startQuest();
				break;
			case "32049-02.html":
			case "31539-04.html":
			case "31539-06.html":
				break;
			case "31539-07.html":
				if ((st.getQuestItemsCount(TORCH) < 1000) || !st.isStarted())
				{
					return getNoQuestMsg(player);
				}
				st.giveItems(REWARD[getRandom(REWARD.length)], 1);
				st.takeItems(TORCH, 1000);
				break;
			case "31539-08.html":
				st.exitQuest(true, true);
				break;
			case "6883":
			case "6885":
			case "7580":
			case "6891":
			case "6893":
			case "6895":
			case "6897":
			case "6899":
				if ((st.getQuestItemsCount(TORCH) < 1200) || !st.isStarted())
				{
					return getNoQuestMsg(player);
				}
				st.giveItems(Integer.valueOf(event), 1);
				st.takeItems(TORCH, 1200);
				htmltext = "32049-04.html";
				break;
			case "6887":
			case "6881":
				if ((st.getQuestItemsCount(TORCH) < 1200) || !st.isStarted())
				{
					return getNoQuestMsg(player);
				}
				st.giveItems(Integer.valueOf(event), 1);
				st.takeItems(TORCH, 1200);
				htmltext = "32049-03.html";
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
		final L2PcInstance partyMember = getRandomPartyMember(player, 1);
		if (partyMember == null)
		{
			return super.onKill(npc, player, isSummon);
		}
		
		final QuestState st = partyMember.getQuestState(getName());
		
		if (getRandom(1000) < MOBS.get(npc.getId()))
		{
			st.giveItems(TORCH, 2);
		}
		else
		{
			st.giveItems(TORCH, 1);
		}
		st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
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
		
		switch (npc.getId())
		{
			case ROONEY:
				if (st.isStarted())
				{
					htmltext = (st.getQuestItemsCount(TORCH) >= 1200) ? "32049-02.html" : "32049-01.html";
				}
				break;
			case VULCAN:
				if (st.isCreated())
				{
					htmltext = (player.getLevel() >= 74) ? "31539-01.htm" : "31539-02.htm";
				}
				else
				{
					htmltext = (st.getQuestItemsCount(TORCH) >= 1000) ? "31539-04.html" : "31539-05.html";
				}
				break;
			case HILDA:
				if (st.isCreated())
				{
					htmltext = (player.getLevel() >= 74) ? "31271-01.htm" : "31271-02.htm";
				}
				else
				{
					htmltext = "31271-04.html";
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00617_GatherTheFlames(617, Q00617_GatherTheFlames.class.getSimpleName(), "Gather the Flames");
	}
}