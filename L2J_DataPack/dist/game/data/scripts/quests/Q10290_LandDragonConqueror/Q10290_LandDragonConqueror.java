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
package quests.Q10290_LandDragonConqueror;

import java.util.function.Function;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Land Dragon Conqueror (10290)
 * @author malyelfik
 */
public final class Q10290_LandDragonConqueror extends Quest
{
	// NPC
	private static final int THEODRIC = 30755;
	// Monster
	private static final int ANTHARAS = 29068;
	// Items
	private static final int PORTAL_STONE = 3865;
	private static final int SHABBY_NECKLACE = 15522;
	private static final int MIRACLE_NECKLACE = 15523;
	// Reward
	private static final int ANTHARAS_SLAYER_CIRCLET = 8568;
	// Misc
	private static final int MIN_LEVEL = 83;
	
	public Q10290_LandDragonConqueror()
	{
		super(10290, Q10290_LandDragonConqueror.class.getSimpleName(), "Land Dragon Conqueror");
		addStartNpc(THEODRIC);
		addTalkId(THEODRIC);
		addKillId(ANTHARAS);
		registerQuestItems(MIRACLE_NECKLACE, SHABBY_NECKLACE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equals("30755-05.htm"))
		{
			st.startQuest();
			st.giveItems(SHABBY_NECKLACE, 1);
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (!player.isInParty())
		{
			return super.onKill(npc, player, isSummon);
		}
		
		Function<L2PcInstance, Boolean> rewardCheck = p ->
		{
			if (Util.checkIfInRange(8000, npc, p, false))
			{
				QuestState st = getQuestState(p, false);
				
				if ((st != null) && st.isCond(1) && st.hasQuestItems(SHABBY_NECKLACE))
				{
					st.takeItems(SHABBY_NECKLACE, -1);
					st.giveItems(MIRACLE_NECKLACE, 1);
					st.setCond(2, true);
				}
			}
			return true;
		};
		
		// rewards go only to command channel, not to a single party or player (retail Freya AI)
		if (player.getParty().isInCommandChannel())
		{
			player.getParty().getCommandChannel().forEachMember(rewardCheck);
		}
		else
		{
			player.getParty().forEachMember(rewardCheck);
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		switch (st.getState())
		{
			case State.CREATED:
			{
				if (player.getLevel() < MIN_LEVEL)
				{
					htmltext = "30755-00.htm";
				}
				else
				{
					htmltext = st.hasQuestItems(PORTAL_STONE) ? "30755-02.htm" : "30755-01.htm";
				}
				break;
			}
			case State.STARTED:
			{
				if (st.isCond(1))
				{
					if (st.hasQuestItems(SHABBY_NECKLACE))
					{
						htmltext = "30755-06.html";
					}
					else
					{
						st.giveItems(SHABBY_NECKLACE, 1);
						htmltext = "30755-07.html";
					}
				}
				else if ((st.isCond(2)) && st.hasQuestItems(MIRACLE_NECKLACE))
				{
					htmltext = "30755-08.html";
					st.giveAdena(131236, true);
					st.addExpAndSp(702557, 76334);
					st.giveItems(ANTHARAS_SLAYER_CIRCLET, 1);
					st.exitQuest(false, true);
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = "30755-09.html";
				break;
			}
		}
		return htmltext;
	}
}
