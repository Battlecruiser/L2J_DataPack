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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * It Smells Delicious! (252) Updated by corbin12, thanks VLight for help.
 * @author Dumpster
 */
public class Q00252_ItSmellsDelicious extends Quest
{
	public static final int STAN = 30200;
	public static final int MAHUM_DIARY = 15500;
	public static final int MAHUM_COOKBOOK = 15501;
	
	private static final int[] MOBS =
	{
		22786,
		22787,
		22788
	};
	
	private static final int CHEF = 18908;
	
	public Q00252_ItSmellsDelicious(int id, String name, String descr)
	{
		super(id, name, descr);
		addStartNpc(STAN);
		addTalkId(STAN);
		addKillId(CHEF);
		addKillId(MOBS);
		registerQuestItems(MAHUM_DIARY, MAHUM_COOKBOOK);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == STAN)
		{
			if (event.equalsIgnoreCase("30200-05.htm"))
			{
				st.startQuest();
			}
			else if (event.equalsIgnoreCase("30200-08.htm"))
			{
				st.giveAdena(147656, true);
				st.addExpAndSp(716238, 78324);
				st.exitQuest(false, true);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final int npcId = npc.getNpcId();
		QuestState st;
		if (Util.contains(MOBS, npcId) && (getRandom(1000) < 599))
		{
			st = getRandomPartyMemberQuestState(player, getName());
			if (st != null)
			{
				st.giveItems(MAHUM_DIARY, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				
				if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOK) >= 5))
				{
					st.setCond(2, true);
				}
			}
		}
		else if (npcId == CHEF)
		{
			st = player.getQuestState(getName());
			if ((st != null) && st.isStarted() && (st.isCond(1)) && (st.getQuestItemsCount(MAHUM_COOKBOOK) < 5) && (getRandom(1000) < 360))
			{
				st.giveItems(MAHUM_COOKBOOK, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				
				if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOK) >= 5))
				{
					st.setCond(2, true);
				}
			}
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
		
		if (npc.getNpcId() == STAN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					htmltext = (player.getLevel() >= 82) ? "30200-01.htm" : "30200-02.htm";
					break;
				case State.STARTED:
					if (st.isCond(1))
					{
						htmltext = "30200-06.htm";
					}
					else if (st.isCond(2))
					{
						if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOK) >= 5))
						{
							htmltext = "30200-07.htm";
						}
					}
					break;
				case State.COMPLETED:
					htmltext = "30200-03.htm";
			}
		}
		return htmltext;
	}
	
	private static QuestState getRandomPartyMemberQuestState(L2PcInstance player, String questName)
	{
		if (player == null)
		{
			return null;
		}
		
		final L2Party party = player.getParty();
		QuestState st;
		
		if ((party == null) || party.getMembers().isEmpty())
		{
			st = player.getQuestState(questName);
			if ((st == null) || st.isStarted() || (!st.isCond(1)) || (st.getQuestItemsCount(MAHUM_DIARY) >= 10))
			{
				return null;
			}
			return st;
		}
		
		final List<QuestState> candidates = new ArrayList<>();
		// get the target for enforcing distance limitations.
		L2Object target = player.getTarget();
		
		if (target == null)
		{
			target = player;
		}
		
		for (final L2PcInstance partyMember : party.getMembers())
		{
			if (partyMember.isDead() || !partyMember.isInsideRadius(target, 1500, true, false))
			{
				continue;
			}
			
			st = partyMember.getQuestState(questName);
			if ((st == null) || (st.getState() != State.STARTED) || (!st.isCond(1)) || (st.getQuestItemsCount(MAHUM_DIARY) >= 10))
			{
				continue;
			}
			candidates.add(st);
		}
		return candidates.isEmpty() ? null : candidates.get(getRandom(candidates.size()));
	}
	
	public static void main(String[] args)
	{
		new Q00252_ItSmellsDelicious(252, Q00252_ItSmellsDelicious.class.getSimpleName(), "It Smells Delicious!");
	}
}
