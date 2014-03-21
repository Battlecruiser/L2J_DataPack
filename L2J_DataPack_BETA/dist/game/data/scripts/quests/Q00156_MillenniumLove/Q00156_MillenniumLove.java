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
package quests.Q00156_MillenniumLove;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Millennium Love (156)
 * @author xban1x
 */
public class Q00156_MillenniumLove extends Quest
{
	// NPCs
	private static final int LILITH = 30368;
	private static final int BAENEDES = 30369;
	// Items
	private static final int LILITHS_LETTER = 1022;
	private static final int THEONS_DIARY = 1023;
	private static final int GREATER_COMP_SOULSHOUT_PACKAGE_NO_GRADE = 5250;
	// Misc
	private static final int MIN_LVL = 15;
	
	public Q00156_MillenniumLove()
	{
		super(156, Q00156_MillenniumLove.class.getSimpleName(), "Millennium Love");
		addStartNpc(LILITH);
		addTalkId(LILITH, BAENEDES);
		registerQuestItems(LILITHS_LETTER, THEONS_DIARY);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st != null)
		{
			switch (event)
			{
				case "30368-02.html":
				case "30368-03.html":
				{
					htmltext = event;
					break;
				}
				case "30368-05.htm":
				{
					if (player.getLevel() >= MIN_LVL)
					{
						st.startQuest();
						st.giveItems(LILITHS_LETTER, 1);
						htmltext = event;
					}
					else
					{
						htmltext = "30368-04.htm";
					}
					break;
				}
				case "30369-02.html":
				{
					if (st.isCond(1) && st.hasQuestItems(LILITHS_LETTER))
					{
						st.takeItems(LILITHS_LETTER, 1);
						st.giveItems(THEONS_DIARY, 1);
						st.setCond(2, true);
						htmltext = event;
					}
					break;
				}
				case "30369-03.html":
				{
					if (st.isCond(1) && st.hasQuestItems(LILITHS_LETTER))
					{
						st.addExpAndSp(3000, 0);
						st.exitQuest(false, true);
						htmltext = event;
					}
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st != null)
		{
			switch (npc.getId())
			{
				case LILITH:
				{
					switch (st.getState())
					{
						case State.CREATED:
						{
							htmltext = "30368-01.htm";
							break;
						}
						case State.STARTED:
						{
							if (st.isCond(1) && st.hasQuestItems(LILITHS_LETTER))
							{
								htmltext = "30368-06.html";
							}
							else if (st.isCond(2) && st.hasQuestItems(THEONS_DIARY))
							{
								st.giveItems(GREATER_COMP_SOULSHOUT_PACKAGE_NO_GRADE, 1);
								st.addExpAndSp(3000, 0);
								st.exitQuest(false, true);
								htmltext = "30368-07.html";
							}
							break;
						}
						case State.COMPLETED:
						{
							htmltext = getAlreadyCompletedMsg(player);
							break;
						}
					}
					break;
				}
				case BAENEDES:
				{
					switch (st.getCond())
					{
						case 1:
						{
							if (st.hasQuestItems(LILITHS_LETTER))
							{
								htmltext = "30369-01.html";
							}
							break;
						}
						case 2:
						{
							if (st.hasQuestItems(THEONS_DIARY))
							{
								htmltext = "30369-04.html";
							}
							break;
						}
					}
					break;
				}
			}
		}
		return htmltext;
	}
}
