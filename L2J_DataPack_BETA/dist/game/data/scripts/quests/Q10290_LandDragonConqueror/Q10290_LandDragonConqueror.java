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
package quests.Q10290_LandDragonConqueror;

import com.l2jserver.gameserver.model.IL2Procedure;
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
public class Q10290_LandDragonConqueror extends Quest
{
	// NPC
	private static final int THEODRIC = 30755;
	
	private static final int[] ANTHARAS =
	{
		29019, // Old
		29066, // Weak
		29067, // Normal
		29068
	// Strong
	};
	
	// Items
	private static final int PORTAL_STONE = 3865;
	private static final int SHABBY_NECKLACE = 15522;
	private static final int MIRACLE_NECKLACE = 15523;
	private static final int ANTHARAS_SLAYER_CIRCLET = 8568;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
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
			{
				if (player.getLevel() < 83)
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
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (!player.isInParty())
		{
			return super.onKill(npc, player, isPet);
		}
		
		// rewards go only to command channel, not to a single party or player (retail Freya AI)
		if (player.getParty().isInCommandChannel())
		{
			player.getParty().getCommandChannel().forEachMember(new RewardCheck(npc));
		}
		else
		{
			player.getParty().forEachMember(new RewardCheck(npc));
		}
		
		return super.onKill(npc, player, isPet);
	}
	
	public class RewardCheck implements IL2Procedure<L2PcInstance>
	{
		private final L2Npc _npc;
		
		public RewardCheck(L2Npc npc)
		{
			_npc = npc;
		}
		
		@Override
		public boolean execute(L2PcInstance member)
		{
			if (Util.checkIfInRange(8000, _npc, member, false))
			{
				QuestState st = member.getQuestState(getName());
				
				if ((st != null) && st.isCond(1) && st.hasQuestItems(SHABBY_NECKLACE))
				{
					st.takeItems(SHABBY_NECKLACE, -1);
					st.giveItems(MIRACLE_NECKLACE, 1);
					st.setCond(2, true);
				}
			}
			return true;
		}
	}
	
	public Q10290_LandDragonConqueror(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(THEODRIC);
		addTalkId(THEODRIC);
		addKillId(ANTHARAS);
		registerQuestItems(MIRACLE_NECKLACE, SHABBY_NECKLACE);
	}
	
	public static void main(String[] args)
	{
		new Q10290_LandDragonConqueror(10290, Q10290_LandDragonConqueror.class.getSimpleName(), "Land Dragon Conqueror");
	}
}
