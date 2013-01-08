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
package quests.Q00044_HelpTheSon;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Help The Son! (44)<br>
 * Original Jython script by zerghase.
 * @author malyelfik
 */
public class Q00044_HelpTheSon extends Quest
{
	// NPCs
	private static final int LUNDY = 30827;
	private static final int DRIKUS = 30505;
	// Monsters
	private static final int MAILLE_GUARD = 20921;
	private static final int MAILLE_SCOUT = 20920;
	private static final int MAILLE_LIZARDMAN = 20919;
	// Items
	private static final int WORK_HAMMER = 168;
	private static final int GEMSTONE_FRAGMENT = 7552;
	private static final int GEMSTONE = 7553;
	private static final int PET_TICKET = 7585;
	
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
			case "30827-01.htm":
				st.startQuest();
				break;
			case "30827-03.html":
				if (st.hasQuestItems(WORK_HAMMER))
				{
					st.takeItems(WORK_HAMMER, 1);
					st.setCond(2, true);
				}
				else
				{
					htmltext = "30827-03a.html";
				}
				break;
			case "30827-06.html":
				if (st.getQuestItemsCount(GEMSTONE_FRAGMENT) == 30)
				{
					st.takeItems(GEMSTONE_FRAGMENT, -1);
					st.giveItems(GEMSTONE, 1);
					st.setCond(4, true);
				}
				else
				{
					htmltext = "30827-06a.html";
				}
				break;
			case "30505-02.html":
				if (st.hasQuestItems(GEMSTONE))
				{
					st.takeItems(GEMSTONE, -1);
					st.setCond(5, true);
				}
				else
				{
					htmltext = "30505-02a.html";
				}
				break;
			case "30827-09.html":
				st.giveItems(PET_TICKET, 1);
				st.exitQuest(false, true);
				break;
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
		
		switch (npc.getNpcId())
		{
			case LUNDY:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= 24) ? "30827-00.htm" : "30827-00a.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = (st.hasQuestItems(WORK_HAMMER)) ? "30827-02.html" : "30827-02a.html";
								break;
							case 2:
								htmltext = "30827-04.html";
								break;
							case 3:
								htmltext = "30827-05.html";
								break;
							case 4:
								htmltext = "30827-07.html";
								break;
							case 5:
								htmltext = "30827-08.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case DRIKUS:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 4:
							htmltext = "30505-01.html";
							break;
						case 5:
							htmltext = "30505-03.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(2))
		{
			st.giveItems(GEMSTONE_FRAGMENT, 1);
			if (st.getQuestItemsCount(GEMSTONE_FRAGMENT) == 30)
			{
				st.setCond(3, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public Q00044_HelpTheSon(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(LUNDY);
		addTalkId(LUNDY, DRIKUS);
		addKillId(MAILLE_GUARD, MAILLE_LIZARDMAN, MAILLE_SCOUT);
		registerQuestItems(GEMSTONE, GEMSTONE_FRAGMENT);
	}
	
	public static void main(String[] args)
	{
		new Q00044_HelpTheSon(44, Q00044_HelpTheSon.class.getSimpleName(), "Help The Son!");
	}
}
