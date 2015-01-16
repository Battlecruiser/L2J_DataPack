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
package quests.Q10743_StrangeFungus;

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
public class Q10743_StrangeFungus extends Quest
{
	// NPCs
	private static final int LEIRA = 33952;
	private static final int MILONE = 33953;
	private static final int GROWLER = 23455;
	private static final int ROBUST_GROWLER = 23486;
	private static final int EVOLVED_GROWLER = 23456;
	// Items
	private static final int PECULIAR_MUSHROOM_SPORE = 39530;
	private static final ItemHolder LEATHER_SHOES = new ItemHolder(37, 1);
	// Misc
	private static final int MIN_LEVEL = 13;
	private static final int MAX_LEVEL = 20;
	private static final String KILL_VAR = "KillCount";
	
	public Q10743_StrangeFungus()
	{
		super(10743, Q10743_StrangeFungus.class.getSimpleName(), "Strange Fungus");
		addStartNpc(LEIRA);
		addTalkId(LEIRA, MILONE);
		addKillId(GROWLER, ROBUST_GROWLER, EVOLVED_GROWLER);
		registerQuestItems(PECULIAR_MUSHROOM_SPORE);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "fixme.htm");
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
			case "33952-02.htm":
			case "33953-02.htm":
			{
				htmltext = event;
				break;
			}
			case "33952-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33953-03.htm":
			{
				if (qs.isCond(2))
				{
					giveAdena(player, 62000, true);
					addExpAndSp(player, 62876, 0);
					giveItems(player, LEATHER_SHOES);
					showOnScreenMsg(player, NpcStringId.CHECK_YOUR_EQUIPMENT_IN_YOUR_INVENTORY, ExShowScreenMessage.TOP_CENTER, 4500);
					qs.exitQuest(false, true);
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
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		switch (npc.getId())
		{
			case LEIRA:
			{
				if (qs.isCreated())
				{
					htmltext = "33952-01.htm";
				}
				else if (qs.isStarted())
				{
					htmltext = "33952-03.htm";
				}
				break;
			}
			case MILONE:
			{
				if (qs.isCond(2))
				{
					htmltext = "33953-01.htm";
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
		
		if ((qs != null) && qs.isCond(1) && (getQuestItemsCount(killer, PECULIAR_MUSHROOM_SPORE) < 10))
		{
			switch (npc.getId())
			{
				case GROWLER:
				case ROBUST_GROWLER:
				{
					final int killCount = qs.getInt(KILL_VAR) + 1;
					if (killCount >= 3)
					{
						addAttackPlayerDesire(addSpawn(EVOLVED_GROWLER, npc.getLocation()), killer);
						qs.set(KILL_VAR, 0);
					}
					else
					{
						qs.set(KILL_VAR, killCount);
					}
					break;
				}
				case EVOLVED_GROWLER:
				{
					if (giveItemRandomly(killer, npc, PECULIAR_MUSHROOM_SPORE, 1, 10, 1.0, true))
					{
						qs.setCond(2);
					}
					break;
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
}
