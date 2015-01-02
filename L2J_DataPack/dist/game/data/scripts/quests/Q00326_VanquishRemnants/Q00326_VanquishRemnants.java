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
package quests.Q00326_VanquishRemnants;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Vanquish Remnants (326)
 * @author xban1x
 */
public final class Q00326_VanquishRemnants extends Quest
{
	// NPC
	private static final int LEOPOLD = 30435;
	// Items
	private static final int RED_CROSS_BADGE = 1359;
	private static final int BLUE_CROSS_BADGE = 1360;
	private static final int BLACK_CROSS_BADGE = 1361;
	private static final int BLACK_LION_MARK = 1369;
	// Monsters
	private static final Map<Integer, int[]> MONSTERS = new HashMap<>();
	//@formatter:off
	static
	{
		MONSTERS.put(20053, new int[] {61, RED_CROSS_BADGE}); // Ol Mahum Patrol
		MONSTERS.put(20058, new int[] {61, RED_CROSS_BADGE}); // Ol Mahum Guard
		MONSTERS.put(20061, new int[] {57, BLUE_CROSS_BADGE}); // Ol Mahum Remnants
		MONSTERS.put(20063, new int[] {63, BLUE_CROSS_BADGE}); // Ol Mahum Shooter
		MONSTERS.put(20066, new int[] {59, BLACK_CROSS_BADGE}); // Ol Mahum Captain
		MONSTERS.put(20436, new int[] {55, BLUE_CROSS_BADGE}); // Ol Mahum Supplier
		MONSTERS.put(20437, new int[] {59, RED_CROSS_BADGE}); // Ol Mahum Recruit
		MONSTERS.put(20438, new int[] {60, BLACK_CROSS_BADGE}); // Ol Mahum General
		MONSTERS.put(20439, new int[] {62, BLUE_CROSS_BADGE}); // Ol Mahum Officer
	}
	//@formatter:on
	// Misc
	private static final int MIN_LVL = 21;
	
	public Q00326_VanquishRemnants()
	{
		super(326, Q00326_VanquishRemnants.class.getSimpleName(), "Vanquish Remnants");
		addStartNpc(LEOPOLD);
		addTalkId(LEOPOLD);
		addKillId(MONSTERS.keySet());
		registerQuestItems(RED_CROSS_BADGE, BLUE_CROSS_BADGE, BLACK_CROSS_BADGE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		String htmltext = null;
		if (st != null)
		{
			switch (event)
			{
				case "30435-03.htm":
				{
					st.startQuest();
					htmltext = event;
					break;
				}
				case "30435-07.html":
				{
					st.exitQuest(true, true);
					htmltext = event;
					break;
				}
				case "30435-08.html":
				{
					htmltext = event;
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = getQuestState(killer, false);
		if ((st != null) && st.isStarted() && (getRandom(100) < MONSTERS.get(npc.getId())[0]))
		{
			st.giveItems(MONSTERS.get(npc.getId())[1], 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, true);
		String htmltext = null;
		if (st != null)
		{
			switch (st.getState())
			{
				case State.CREATED:
				{
					htmltext = (player.getLevel() >= MIN_LVL) ? "30435-02.htm" : "30435-01.htm";
					break;
				}
				case State.STARTED:
				{
					final long red_badges = st.getQuestItemsCount(RED_CROSS_BADGE);
					final long blue_badges = st.getQuestItemsCount(BLUE_CROSS_BADGE);
					final long black_badges = st.getQuestItemsCount(BLACK_CROSS_BADGE);
					final long sum = red_badges + blue_badges + black_badges;
					if (sum > 0)
					{
						if ((sum >= 100) && !st.hasQuestItems(BLACK_LION_MARK))
						{
							st.giveItems(BLACK_LION_MARK, 1);
						}
						st.giveAdena(((red_badges * 46) + (blue_badges * 52) + (black_badges * 58) + ((sum >= 10) ? 4320 : 0)), true);
						takeItems(player, -1, RED_CROSS_BADGE, BLUE_CROSS_BADGE, BLACK_CROSS_BADGE);
						htmltext = (sum >= 100) ? (st.hasQuestItems(BLACK_LION_MARK)) ? "30435-09.html" : "30435-06.html" : "30435-05.html";
					}
					else
					{
						htmltext = "30435-04.html";
					}
					break;
				}
			}
		}
		return htmltext;
	}
}