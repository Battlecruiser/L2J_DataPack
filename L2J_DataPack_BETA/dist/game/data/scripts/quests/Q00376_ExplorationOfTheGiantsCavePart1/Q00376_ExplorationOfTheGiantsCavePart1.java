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
package quests.Q00376_ExplorationOfTheGiantsCavePart1;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.serverpackets.RadarControl;

/**
 * Exploration of the Giants' Cave Part 1 (376)<br>
 * Original Jython script by Gnacik.
 * @author nonom
 */
public class Q00376_ExplorationOfTheGiantsCavePart1 extends Quest
{
	// NPC
	private static final int SOBLING = 31147;
	// Items
	private static final int ANCIENT_PARCHMENT = 14841;
	private static final int BOOK1 = 14836;
	private static final int BOOK2 = 14837;
	private static final int BOOK3 = 14838;
	private static final int BOOK4 = 14839;
	private static final int BOOK5 = 14840;
	// Mobs
	private static final int[] MOBS =
	{
		22670,
		22671,
		22672,
		22673,
		22674,
		22675,
		22676,
		22677
	};
	
	private Q00376_ExplorationOfTheGiantsCavePart1()
	{
		super(376, Q00376_ExplorationOfTheGiantsCavePart1.class.getSimpleName(), "Exploration of the Giants' Cave - Part 1");
		addStartNpc(SOBLING);
		addTalkId(SOBLING);
		addKillId(MOBS);
		registerQuestItems(ANCIENT_PARCHMENT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31147-02.htm":
			{
				st.startQuest();
				player.sendPacket(new RadarControl(0, 2, 185712, 47414, -4350));
				break;
			}
			case "31147-quit.html":
			{
				st.exitQuest(true, true);
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = getRandomPartyMemberState(player, -1, 3, npc);
		if (st != null)
		{
			giveItemRandomly(player, npc, ANCIENT_PARCHMENT, 1, 0, 0.2, true);
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
		
		if (st.isCreated())
		{
			htmltext = (player.getLevel() >= 79 ? "31147-01.htm" : "31147-00.html");
		}
		else if (st.isStarted())
		{
			htmltext = (st.hasQuestItems(BOOK1, BOOK2, BOOK3, BOOK4, BOOK5) ? "31147-03.html" : "31147-02a.html");
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00376_ExplorationOfTheGiantsCavePart1();
	}
}
