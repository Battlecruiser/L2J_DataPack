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
package quests.Q00613_ProveYourCourageVarka;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Prove Your Courage! (Varka) (613)
 * @author malyelfik
 */
public class Q00613_ProveYourCourageVarka extends Quest
{
	// NPC
	private static final int ASHAS = 31377;
	// Monster
	private static final int HEKATON = 25299;
	// Items
	private static final int HEKATON_HEAD = 7240;
	private static final int VALOR_FEATHER = 7229;
	private static final int VARKA_ALLIANCE_THREE = 7223;
	// Misc
	private static final int MIN_LEVEL = 75;
	
	private Q00613_ProveYourCourageVarka(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ASHAS);
		addTalkId(ASHAS);
		addKillId(HEKATON);
		registerQuestItems(HEKATON_HEAD);
	}
	
	@Override
	public void actionForEachPlayer(L2PcInstance player, L2Npc npc, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(1) && Util.checkIfInRange(1500, npc, player, false))
		{
			giveItems(player, HEKATON_HEAD, 1);
			st.setCond(2, true);
		}
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
			case "31377-04.htm":
				st.startQuest();
				break;
			case "31377-07.html":
				if (st.hasQuestItems(HEKATON_HEAD) && st.isCond(2))
				{
					giveItems(player, VALOR_FEATHER, 1);
					st.addExpAndSp(10000, 0);
					st.exitQuest(true, true);
				}
				else
				{
					htmltext = getNoQuestMsg(player);
				}
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		executeForEachPlayer(killer, npc, isSummon, true, false);
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
				htmltext = (player.getLevel() >= MIN_LEVEL) ? (st.hasQuestItems(VARKA_ALLIANCE_THREE)) ? "31377-01.htm" : "31377-02.htm" : "31377-03.htm";
				break;
			case State.STARTED:
				htmltext = (st.isCond(2) && st.hasQuestItems(HEKATON_HEAD)) ? "31377-05.html" : "31377-06.html";
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00613_ProveYourCourageVarka(613, Q00613_ProveYourCourageVarka.class.getSimpleName(), "Prove Your Courage! (Varka)");
	}
}
