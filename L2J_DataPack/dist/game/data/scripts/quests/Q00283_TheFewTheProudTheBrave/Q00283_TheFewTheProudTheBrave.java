/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package quests.Q00283_TheFewTheProudTheBrave;

import quests.Q00261_CollectorsDream.Q00261_CollectorsDream;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * The Few, The Proud, The Brave (283)
 * @author xban1x
 */
public final class Q00283_TheFewTheProudTheBrave extends Quest
{
	// NPC
	private static final int PERWAN = 32133;
	// Item
	private static final int CRIMSON_SPIDER_CLAW = 9747;
	// Monster
	private static final int CRIMSON_SPIDER = 22244;
	// Misc
	private static final int CLAW_PRICE = 45;
	private static final int BONUS = 2187;
	private static final int MIN_LVL = 15;
	
	public Q00283_TheFewTheProudTheBrave()
	{
		super(283, Q00283_TheFewTheProudTheBrave.class.getSimpleName(), "The Few, The Proud, The Brave");
		addKillId(CRIMSON_SPIDER);
		addStartNpc(PERWAN);
		addTalkId(PERWAN);
		registerQuestItems(CRIMSON_SPIDER_CLAW);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		String htmltext = null;
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "32133-03.htm":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "32133-06.html":
			{
				htmltext = event;
				break;
			}
			case "32133-08.html":
			{
				if (st.hasQuestItems(CRIMSON_SPIDER_CLAW))
				{
					final long claws = st.getQuestItemsCount(CRIMSON_SPIDER_CLAW);
					st.giveAdena((claws * CLAW_PRICE) + ((claws >= 10) ? BONUS : 0), true);
					st.takeItems(CRIMSON_SPIDER_CLAW, -1);
					Q00261_CollectorsDream.giveNewbieReward(player);
					htmltext = event;
				}
				else
				{
					htmltext = "32133-07.html";
				}
				break;
			}
			case "32133-09.html":
			{
				st.exitQuest(true, true);
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = getRandomPartyMemberState(killer, -1, 3, npc);
		if (st != null)
		{
			st.giveItemRandomly(npc, CRIMSON_SPIDER_CLAW, 1, 0, 0.6, true);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		final QuestState st = getQuestState(talker, true);
		String htmltext = getNoQuestMsg(talker);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (talker.getLevel() >= MIN_LVL) ? "32133-01.htm" : "32133-02.htm";
				break;
			}
			case State.STARTED:
			{
				htmltext = st.hasQuestItems(CRIMSON_SPIDER_CLAW) ? "32133-04.html" : "32133-05.html";
				break;
			}
		}
		return htmltext;
	}
}
