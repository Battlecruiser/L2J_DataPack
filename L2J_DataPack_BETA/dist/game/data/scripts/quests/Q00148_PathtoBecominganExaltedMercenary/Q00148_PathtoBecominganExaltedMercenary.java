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
package quests.Q00148_PathtoBecominganExaltedMercenary;

import quests.Q00147_PathtoBecominganEliteMercenary.Q00147_PathtoBecominganEliteMercenary;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Path to Becoming an Exalted Mercenary (148)
 * @author Gnacik
 * @version 2010-09-30 Based on official server Franz
 */
public class Q00148_PathtoBecominganExaltedMercenary extends Quest
{
	// NPCs
	private static final int[] MERC =
	{
		36481,
		36482,
		36483,
		36484,
		36485,
		36486,
		36487,
		36488,
		36489
	};
	// Items
	private static final int ELITE_CERTIFICATE = 13767;
	private static final int TOP_ELITE_CERTIFICATE = 13768;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("exalted-00b.htm"))
		{
			st.giveItems(ELITE_CERTIFICATE, 1);
		}
		else if (event.equalsIgnoreCase("exalted-03.htm"))
		{
			st.startQuest();
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
		
		switch (st.getState())
		{
			case State.CREATED:
				QuestState _prev = player.getQuestState(Q00147_PathtoBecominganEliteMercenary.class.getSimpleName());
				if ((player.getClan() != null) && (player.getClan().getCastleId() > 0))
				{
					htmltext = "castle.htm";
				}
				else if (st.hasQuestItems(ELITE_CERTIFICATE))
				{
					htmltext = "exalted-01.htm";
				}
				else
				{
					if ((_prev != null) && _prev.isCompleted())
					{
						htmltext = "exalted-00a.htm";
					}
					else
					{
						htmltext = "exalted-00.htm";
					}
				}
				break;
			case State.STARTED:
				if (st.getCond() < 4)
				{
					htmltext = "exalted-04.htm";
				}
				else if (st.isCond(4))
				{
					st.takeItems(ELITE_CERTIFICATE, -1);
					st.giveItems(TOP_ELITE_CERTIFICATE, 1);
					st.exitQuest(false);
					htmltext = "exalted-05.htm";
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	public Q00148_PathtoBecominganExaltedMercenary(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(MERC);
		addTalkId(MERC);
	}
	
	public static void main(String[] args)
	{
		new Q00148_PathtoBecominganExaltedMercenary(148, Q00148_PathtoBecominganExaltedMercenary.class.getSimpleName(), "Path to Becoming an Exalted Mercenary");
	}
}
