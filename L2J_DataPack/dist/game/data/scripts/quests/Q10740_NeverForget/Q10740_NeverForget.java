/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package quests.Q10740_NeverForget;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * @author Sdw
 */
public class Q10740_NeverForget extends Quest
{
	// NPC's
	private static final int SIVANTHE = 33951;
	private static final int REMEMBERANCE_TOWER = 33989;
	// Items
	private static final int UNNAMED_RELICS = 39526;
	private static final ItemHolder RING_OF_KNOWLEDGE = new ItemHolder(875, 2);
	private static final ItemHolder HEALING_POTION = new ItemHolder(1060, 100);
	// Mobs
	private static final int[] MOBS =
	{
		23449, // Keen Floato
		23450, // Ratel
		23451, // Robust Ratel
	};
	// Misc
	private static final int MIN_LEVEL = 8;
	private static final int MAX_LEVEL = 20;
	
	public Q10740_NeverForget()
	{
		super(10740, Q10740_NeverForget.class.getSimpleName(), "Never Forget");
		addStartNpc(SIVANTHE);
		addTalkId(SIVANTHE, REMEMBERANCE_TOWER);
		registerQuestItems(UNNAMED_RELICS);
		addKillId(MOBS);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "33951-07.htm");
		addCondRace(Race.ERTHEIA, "33951-07.htm");
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "33951-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33989-02.htm":
			{
				if (qs.isCond(2) && (qs.getQuestItemsCount(UNNAMED_RELICS) >= 20))
				{
					qs.takeItems(UNNAMED_RELICS, 20);
					qs.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "33951-02.htm":
			{
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		switch (npc.getId())
		{
			case SIVANTHE:
			{
				if (qs.isCreated())
				{
					htmltext = "33951-01.htm";
				}
				else if (qs.isStarted())
				{
					switch (qs.getCond())
					{
						case 1:
						{
							htmltext = "33951-04.htm";
							break;
						}
						case 2:
						{
							htmltext = "33951-05.htm";
							break;
						}
						case 3:
						{
							giveItems(player, RING_OF_KNOWLEDGE);
							giveItems(player, HEALING_POTION);
							giveAdena(player, 1600, true);
							addExpAndSp(player, 16851, 0);
							qs.exitQuest(false, true);
							showOnScreenMsg(player, NpcStringId.CHECK_YOUR_EQUIPMENT_IN_YOUR_INVENTORY, ExShowScreenMessage.TOP_CENTER, 4500);
							htmltext = "33951-06.htm";
							break;
						}
					}
				}
				break;
			}
			case REMEMBERANCE_TOWER:
			{
				switch (qs.getCond())
				{
					case 2:
					{
						htmltext = "33989-01.htm";
						break;
					}
					case 3:
					{
						htmltext = "33989-03.htm";
						break;
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		
		if ((qs != null) && qs.isCond(1))
		{
			giveItemRandomly(killer, npc, UNNAMED_RELICS, 1, 20, 1.0, true);
			if (qs.getQuestItemsCount(UNNAMED_RELICS) == 20)
			{
				qs.setCond(2, true);
			}
			else
			{
				playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
}
