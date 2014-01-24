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
package quests.Q00190_LostDream;

import quests.Q00187_NikolasHeart.Q00187_NikolasHeart;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Lost Dream (190)
 * @author ivantotov
 */
public final class Q00190_LostDream extends Quest
{
	// NPCs
	private static final int HEAD_BLACKSMITH_KUSTO = 30512;
	private static final int RESEARCHER_LORAIN = 30673;
	private static final int MAESTRO_NIKOLA = 30621;
	private static final int JURIS = 30113;
	// Misc
	private static final int MIN_LEVEL = 42;
	private static final int MAX_LEVEL_FOR_EXP_SP = 48;
	
	private Q00190_LostDream(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(HEAD_BLACKSMITH_KUSTO);
		addTalkId(HEAD_BLACKSMITH_KUSTO, RESEARCHER_LORAIN, MAESTRO_NIKOLA, JURIS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return null;
		}
		String htmltext = null;
		switch (event)
		{
			case "30113-02.html":
			{
				htmltext = event;
				break;
			}
			case "30512-03.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "30512-06.html":
			{
				if (st.isCond(2))
				{
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "30113-03.html":
			{
				if (st.isCond(1))
				{
					st.setCond(2, true);
					htmltext = event;
				}
				break;
			}
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
		
		switch (npc.getId())
		{
			case HEAD_BLACKSMITH_KUSTO:
			{
				switch (st.getState())
				{
					case State.CREATED:
					{
						final QuestState qs = player.getQuestState(Q00187_NikolasHeart.class.getSimpleName());
						if ((qs != null) && qs.isCompleted())
						{
							htmltext = (player.getLevel() >= MIN_LEVEL) ? "30512-01.htm" : "30512-02.htm";
						}
						break;
					}
					case State.STARTED:
					{
						switch (st.getCond())
						{
							case 1:
							{
								htmltext = "30512-04.html";
								break;
							}
							case 2:
							{
								htmltext = "30512-05.html";
								break;
							}
							case 3:
							case 4:
							{
								htmltext = "30512-07.html";
								break;
							}
							case 5:
							{
								htmltext = "30512-08.html";
								st.giveAdena(109427, true);
								if (player.getLevel() < MAX_LEVEL_FOR_EXP_SP)
								{
									st.addExpAndSp(309467, 20614);
								}
								st.exitQuest(false, true);
								break;
							}
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
			case RESEARCHER_LORAIN:
			{
				switch (st.getCond())
				{
					case 3:
					{
						st.setCond(4, true);
						htmltext = "30673-01.html";
						break;
					}
					case 4:
					{
						htmltext = "30673-02.html";
						break;
					}
				}
				break;
			}
			case MAESTRO_NIKOLA:
			{
				switch (st.getCond())
				{
					case 4:
					{
						st.setCond(5, true);
						htmltext = "30621-01.html";
						break;
					}
					case 5:
					{
						htmltext = "30621-02.html";
						break;
					}
				}
				break;
			}
			case JURIS:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "30113-01.html";
						break;
					}
					case 2:
					{
						htmltext = "30113-04.html";
						break;
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00190_LostDream(190, Q00190_LostDream.class.getSimpleName(), "Lost Dream");
	}
}