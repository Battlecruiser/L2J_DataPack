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
package quests.Q00905_RefinedDragonBlood;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Refined Dragon Blood (905)
 * @author Zoey76
 */
public class Q00905_RefinedDragonBlood extends Quest
{
	// NPCs
	private static final int[] SEPARATED_SOULS =
	{
		32864,
		32865,
		32866,
		32867,
		32868,
		32869,
		32870,
		32891
	};
	// Monsters
	private static final int DRAGON_KNIGHT1 = 22844; // Blue
	private static final int DRAGON_KNIGHT2 = 22845; // Blue
	private static final int ELITE_DRAGON_KNIGHT = 22846; // Blue
	private static final int DRAGON_KNIGHT_WARRIOR = 22847; // Red
	private static final int DRAKE_LEADER = 22848; // Red
	private static final int DRAKE_SCOUT = 22850; // Red
	private static final int DRAKE_MAGE = 22851; // Red
	private static final int DRAGON_GUARD = 22852; // Blue
	private static final int DRAGON_MAGE = 22853; // Blue
	// Items
	private static final int UNREFINED_RED_DRAGON_BLOOD = 21913;
	private static final int UNREFINED_BLUE_DRAGON_BLOOD = 21914;
	private static final int REFINED_RED_DRAGON_BLOOD = 21903;
	private static final int REFINED_BLUE_DRAGON_BLOOD = 21904;
	// Misc
	private static final int MIN_LEVEL = 83;
	private static final int DRAGON_BLOOD_COUNT = 10;
	
	private boolean _wait = true;
	
	private Q00905_RefinedDragonBlood(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(SEPARATED_SOULS);
		addTalkId(SEPARATED_SOULS);
		addKillId(DRAGON_KNIGHT1, DRAGON_KNIGHT2, ELITE_DRAGON_KNIGHT, DRAGON_KNIGHT_WARRIOR, DRAKE_LEADER, DRAKE_SCOUT, DRAKE_MAGE, DRAGON_GUARD, DRAGON_GUARD, DRAGON_MAGE);
		registerQuestItems(UNREFINED_RED_DRAGON_BLOOD, UNREFINED_BLUE_DRAGON_BLOOD);
	}
	
	@Override
	public void actionForEachPlayer(L2PcInstance player, L2Npc npc, boolean isPet)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(1) && Util.checkIfInRange(1500, npc, player, false))
		{
			if ((npc.getNpcId() < DRAGON_KNIGHT_WARRIOR) || (npc.getNpcId() > DRAKE_MAGE))
			{
				st.giveItems(UNREFINED_BLUE_DRAGON_BLOOD, 1);
			}
			else
			{
				st.giveItems(UNREFINED_RED_DRAGON_BLOOD, 1);
			}
			if ((st.getQuestItemsCount(UNREFINED_RED_DRAGON_BLOOD) >= DRAGON_BLOOD_COUNT) && (st.getQuestItemsCount(UNREFINED_BLUE_DRAGON_BLOOD) >= DRAGON_BLOOD_COUNT))
			{
				st.setCond(2, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
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
		if (player.getLevel() >= MIN_LEVEL)
		{
			switch (event)
			{
				case "32864-04.htm":
				case "32864-09.html":
				case "32864-10.html":
				{
					htmltext = event;
					break;
				}
				case "32864-05.htm":
				{
					st.startQuest();
					htmltext = event;
					break;
				}
				case "32864-11.html":
				{
					st.giveItems(REFINED_RED_DRAGON_BLOOD, 1);
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
					st.exitQuest(QuestType.DAILY, true);
					htmltext = event;
				}
				case "32864-12.html":
				{
					st.giveItems(REFINED_BLUE_DRAGON_BLOOD, 1);
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
					st.exitQuest(QuestType.DAILY, true);
					htmltext = event;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		executeForEachPlayer(killer, npc, isPet, true, false);
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = getNoQuestMsg(player);
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (player.getLevel() < MIN_LEVEL) ? "32864-02.html" : "32864-01.htm";
				break;
			}
			case State.STARTED:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "32864-06.html";
						break;
					}
					case 2:
					{
						if (_wait)
						{
							htmltext = "32864-07.html";
							_wait = false;
						}
						else
						{
							htmltext = "32864-08.html";
						}
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				if (!st.isNowAvailable())
				{
					htmltext = "32864-03.html";
				}
				else
				{
					st.setState(State.CREATED);
					htmltext = (player.getLevel() < MIN_LEVEL) ? "32864-02.html" : "32864-01.htm";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00905_RefinedDragonBlood(905, Q00905_RefinedDragonBlood.class.getSimpleName(), "Refined Dragon Blood");
	}
}
