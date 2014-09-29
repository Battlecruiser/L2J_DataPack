/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package quests.Q00042_HelpTheUncle;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Help The Uncle! (42)<br>
 * Original Jython script by zerghase.
 * @author malyelfik
 */
public class Q00042_HelpTheUncle extends Quest
{
	// NPCs
	private static final int WATERS = 30828;
	private static final int SOPHYA = 30735;
	// Monsters
	private static final int MONSTER_EYE_DESTROYER = 20068;
	private static final int MONSTER_EYE_GAZER = 20266;
	// Items
	private static final int TRIDENT = 291;
	private static final int MAP_PIECE = 7548;
	private static final int MAP = 7549;
	private static final int PET_TICKET = 7583;
	
	public Q00042_HelpTheUncle()
	{
		super(42, Q00042_HelpTheUncle.class.getSimpleName(), "Help The Uncle!");
		addStartNpc(WATERS);
		addTalkId(WATERS, SOPHYA);
		addKillId(MONSTER_EYE_DESTROYER, MONSTER_EYE_GAZER);
		registerQuestItems(MAP, MAP_PIECE);
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
			case "30828-01.htm":
				st.startQuest();
				break;
			case "30828-03.html":
				if (st.hasQuestItems(TRIDENT))
				{
					st.takeItems(TRIDENT, 1);
					st.setCond(2, true);
				}
				else
				{
					htmltext = "30828-03a.html";
				}
				break;
			case "30828-06.html":
				if (st.getQuestItemsCount(MAP_PIECE) == 30)
				{
					st.takeItems(MAP_PIECE, -1);
					st.giveItems(MAP, 1);
					st.setCond(4, true);
				}
				else
				{
					htmltext = "30828-06a.html";
				}
				break;
			case "30735-02.html":
				if (st.hasQuestItems(MAP))
				{
					st.takeItems(MAP, -1);
					st.setCond(5, true);
				}
				else
				{
					htmltext = "30735-02a.html";
				}
				break;
			case "30828-09.html":
				st.giveItems(PET_TICKET, 1);
				st.exitQuest(false, true);
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		QuestState st = player.getQuestState(getName());
		
		if ((st != null) && st.isCond(2))
		{
			st.giveItems(MAP_PIECE, 1);
			if (st.getQuestItemsCount(MAP_PIECE) == 30)
			{
				st.setCond(3, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
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
		
		switch (npc.getId())
		{
			case WATERS:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= 25) ? "30828-00.htm" : "30828-00a.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = (st.hasQuestItems(TRIDENT)) ? "30828-02.html" : "30828-02a.html";
								break;
							case 2:
								htmltext = "30828-04.html";
								break;
							case 3:
								htmltext = "30828-05.html";
								break;
							case 4:
								htmltext = "30828-07.html";
								break;
							case 5:
								htmltext = "30828-08.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case SOPHYA:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 4:
							htmltext = "30735-01.html";
							break;
						case 5:
							htmltext = "30735-03.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
}
