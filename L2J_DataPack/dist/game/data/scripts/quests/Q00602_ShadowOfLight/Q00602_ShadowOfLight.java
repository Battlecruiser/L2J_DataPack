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
package quests.Q00602_ShadowOfLight;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Shadow of Light (602)<br>
 * Original Jython script by disKret.
 * @author malyelfik
 */
public class Q00602_ShadowOfLight extends Quest
{
	// NPC
	private static final int EYE_OF_ARGOS = 31683;
	// Item
	private static final int EYE_OF_DARKNESS = 7189;
	// Monsters
	private static final int[] MOBS =
	{
		21299,
		21304
	};
	
	// Reward
	private static final int[][] REWARD =
	{
		{
			6699,
			40000,
			120000,
			20000
		},
		{
			6698,
			60000,
			110000,
			15000
		},
		{
			6700,
			40000,
			150000,
			10000
		},
		{
			0,
			100000,
			140000,
			11250
		}
	};
	
	public Q00602_ShadowOfLight()
	{
		super(602, Q00602_ShadowOfLight.class.getSimpleName(), "Shadow of Light");
		addStartNpc(EYE_OF_ARGOS);
		addTalkId(EYE_OF_ARGOS);
		addKillId(MOBS);
		registerQuestItems(EYE_OF_DARKNESS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "31683-02.htm":
				st.startQuest();
				break;
			case "31683-05.html":
				if (st.getQuestItemsCount(EYE_OF_DARKNESS) < 100)
				{
					return "31683-06.html";
				}
				
				int i = getRandom(4);
				if (i < 3)
				{
					st.giveItems(REWARD[i][0], 3);
				}
				st.giveAdena(REWARD[i][1], true);
				st.addExpAndSp(REWARD[i][2], REWARD[i][3]);
				st.exitQuest(true, true);
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return super.onKill(npc, player, isSummon);
		}
		
		int chance = (npc.getId() == MOBS[0]) ? 560 : 800;
		
		if (st.isCond(1) && (getRandom(1000) < chance))
		{
			st.giveItems(EYE_OF_DARKNESS, 1);
			if (st.getQuestItemsCount(EYE_OF_DARKNESS) == 100)
			{
				st.setCond(2, true);
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
		final QuestState st = getQuestState(player, true);
		
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (player.getLevel() >= 68) ? "31683-01.htm" : "31683-00.htm";
				break;
			case State.STARTED:
				htmltext = (st.isCond(1)) ? "31683-03.html" : "31683-04.html";
				break;
		}
		return htmltext;
	}
}