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
package quests.Q00133_ThatsBloodyHot;

import quests.Q00131_BirdInACage.Q00131_BirdInACage;

import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * That's Bloody Hot! (133)
 * @author Zoey76
 */
public class Q00133_ThatsBloodyHot extends Quest
{
	// NPCs
	private static final int KANIS = 32264;
	private static final int GALATE = 32292;
	// Item
	private static final int REFINED_CRYSTAL_SAMPLE = 9785;
	// Misc
	private static final int MIN_LEVEL = 78;
	
	public Q00133_ThatsBloodyHot(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KANIS);
		addTalkId(KANIS, GALATE);
		registerQuestItems(REFINED_CRYSTAL_SAMPLE);
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
			case "32264-04.html":
			{
				if (player.getLevel() >= MIN_LEVEL)
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "32264-06.html":
			case "32264-07.html":
			{
				if (st.isCond(1))
				{
					htmltext = event;
				}
				break;
			}
			case "32264-08.html":
			{
				st.setCond(2);
				htmltext = event;
				break;
			}
			case "32264-10.html":
			case "32264-11.html":
			{
				if (st.isCond(2))
				{
					htmltext = event;
				}
				break;
			}
			case "32264-12.html":
			{
				if (st.isCond(2))
				{
					giveItems(player, REFINED_CRYSTAL_SAMPLE, 1);
					st.setCond(3);
					htmltext = event;
				}
				break;
			}
			case "32292-03.html":
			{
				if (st.isCond(3))
				{
					htmltext = event;
				}
				break;
			}
			case "32292-05.html":
			{
				if (st.isCond(3) && st.hasQuestItems(REFINED_CRYSTAL_SAMPLE))
				{
					takeItems(player, REFINED_CRYSTAL_SAMPLE, -1);
					htmltext = event;
					st.setCond(4);
				}
				break;
			}
			case "32292-06.html":
			{
				if (st.isCond(4))
				{
					if (!HellboundManager.getInstance().isLocked())
					{
						st.giveAdena(254247, true);
						st.addExpAndSp(331457, 32524);
						st.exitQuest(false, true);
						htmltext = event;
					}
					else
					{
						st.giveAdena(254247, true);
						st.addExpAndSp(325881, 32524);
						st.exitQuest(false, true);
						htmltext = "32292-07.html";
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
			case State.CREATED:
			{
				if (npc.getId() == KANIS)
				{
					final QuestState qs = player.getQuestState(Q00131_BirdInACage.class.getSimpleName());
					if ((qs != null) && qs.isCompleted())
					{
						htmltext = (player.getLevel() >= MIN_LEVEL) ? "32264-01.htm" : "32264-02.html";
					}
					else
					{
						htmltext = "32264-03.html";
					}
				}
				break;
			}
			case State.STARTED:
			{
				if (npc.getId() == KANIS)
				{
					if (st.isCond(1))
					{
						htmltext = "32264-05.html";
					}
					else if (st.isCond(2))
					{
						htmltext = "32264-09.html";
					}
					else if (st.getCond() >= 3)
					{
						htmltext = "32264-13.html";
					}
				}
				else if (npc.getId() == GALATE)
				{
					if (st.getCond() < 3)
					{
						htmltext = "32292-01.html";
					}
					else if (st.isCond(3))
					{
						htmltext = "32292-02.html";
					}
					else if (st.isCond(4))
					{
						htmltext = "32292-04.html";
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00133_ThatsBloodyHot(133, Q00133_ThatsBloodyHot.class.getSimpleName(), "That's Bloody Hot!");
	}
}
