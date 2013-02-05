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
package quests.Q00020_BringUpWithLove;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Bring Up With Love (20)
 * @author Gnacik, jurchiks
 */
public class Q00020_BringUpWithLove extends Quest
{
	// NPC
	private static final int TUNATUN = 31537;
	// Items
	private static final int BEAST_HANDLERS_WHIP = 15473;
	private static final int WATER_CRYSTAL = 9553;
	private static final int JEWEL_OF_INNOCENCE = 7185;
	
	public Q00020_BringUpWithLove(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(TUNATUN);
		addTalkId(TUNATUN);
		addFirstTalkId(TUNATUN);
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
			case "31537-12.htm":
				st.startQuest();
				break;
			case "31537-03.htm":
				if (hasQuestItems(player, BEAST_HANDLERS_WHIP))
				{
					return "31537-03a.htm";
				}
				giveItems(player, BEAST_HANDLERS_WHIP, 1);
				break;
			
			case "31537-15.htm":
				takeItems(player, JEWEL_OF_INNOCENCE, -1);
				giveItems(player, WATER_CRYSTAL, 1);
				st.exitQuest(false, true);
				break;
			case "31537-21.html":
				if (player.getLevel() < 82)
				{
					return "31537-23.html";
				}
				if (hasQuestItems(player, BEAST_HANDLERS_WHIP))
				{
					return "31537-22.html";
				}
				giveItems(player, BEAST_HANDLERS_WHIP, 1);
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			newQuestState(player);
		}
		return "31537-20.html";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = ((player.getLevel() < 82) ? "31537-00.htm" : "31537-01.htm");
				break;
			case State.STARTED:
				switch (st.getCond())
				{
					case 1:
						htmltext = "31537-13.htm";
						break;
					case 2:
						htmltext = "31537-14.htm";
						break;
				}
				break;
		}
		return htmltext;
	}
	
	public static void checkJewelOfInnocence(L2PcInstance player)
	{
		final QuestState st = player.getQuestState(Q00020_BringUpWithLove.class.getSimpleName());
		if ((st != null) && st.isCond(1) && !st.hasQuestItems(JEWEL_OF_INNOCENCE) && (getRandom(20) == 0))
		{
			st.giveItems(JEWEL_OF_INNOCENCE, 1);
			st.setCond(2, true);
		}
	}
	
	public static void main(String[] args)
	{
		new Q00020_BringUpWithLove(20, Q00020_BringUpWithLove.class.getSimpleName(), "Bring Up With Love");
	}
}
