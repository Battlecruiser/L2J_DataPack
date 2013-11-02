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
package quests.Q00288_HandleWithCare;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Handle With Care (288)
 * @author Zoey76
 */
public class Q00288_HandleWithCare extends Quest
{
	// NPC
	private static final int ANKUMI = 32741;
	// Monster
	private static final int SEER_UGOROS = 18863;
	// Items
	private static final int HIGH_GRADE_LIZARD_SCALE = 15497;
	private static final int MIDDLE_GRADE_LIZARD_SCALE = 15498;
	private static final int SCROLL_ENCHANT_WEAPON_S_GRADE = 959;
	private static final int SCROLL_ENCHANT_ARMOR_S_GRADE = 960;
	private static final int HOLY_CRYSTAL = 9557;
	private static final ItemHolder[] REWARDS =
	{
		new ItemHolder(SCROLL_ENCHANT_WEAPON_S_GRADE, 1),
		new ItemHolder(SCROLL_ENCHANT_ARMOR_S_GRADE, 1),
		new ItemHolder(SCROLL_ENCHANT_ARMOR_S_GRADE, 2),
		new ItemHolder(SCROLL_ENCHANT_ARMOR_S_GRADE, 3),
		new ItemHolder(HOLY_CRYSTAL, 1),
		new ItemHolder(HOLY_CRYSTAL, 2)
	};
	// Misc
	private static final int MIN_LEVEL = 82;
	
	public Q00288_HandleWithCare(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ANKUMI);
		addTalkId(ANKUMI);
		addKillId(SEER_UGOROS);
		registerQuestItems(HIGH_GRADE_LIZARD_SCALE, MIDDLE_GRADE_LIZARD_SCALE);
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
			case "32741-03.htm":
			{
				if (player.getLevel() >= MIN_LEVEL)
				{
					htmltext = event;
				}
				break;
			}
			case "32741-04.html":
			{
				if (player.getLevel() >= MIN_LEVEL)
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "32741-08.html":
			{
				if (st.isCond(2) || st.isCond(3))
				{
					ItemHolder reward = null;
					if (st.hasQuestItems(MIDDLE_GRADE_LIZARD_SCALE))
					{
						takeItems(player, MIDDLE_GRADE_LIZARD_SCALE, 1);
						final int rnd = getRandom(10);
						if (rnd == 0)
						{
							reward = REWARDS[0];
						}
						else if (rnd < 4)
						{
							reward = REWARDS[1];
						}
						else if (rnd < 6)
						{
							reward = REWARDS[2];
						}
						else if (rnd < 7)
						{
							reward = REWARDS[3];
						}
						else if (rnd < 9)
						{
							reward = REWARDS[4];
						}
						else
						{
							reward = REWARDS[5];
						}
					}
					else if (st.hasQuestItems(HIGH_GRADE_LIZARD_SCALE))
					{
						takeItems(player, HIGH_GRADE_LIZARD_SCALE, 1);
						final int rnd = getRandom(10);
						if (rnd == 0)
						{
							reward = REWARDS[0];
						}
						else if (rnd < 5)
						{
							reward = REWARDS[1];
						}
						else if (rnd < 8)
						{
							reward = REWARDS[2];
						}
						else
						{
							reward = REWARDS[3];
						}
						giveItems(player, REWARDS[4]);
					}
					if (reward != null)
					{
						giveItems(player, reward);
					}
					st.exitQuest(true, true);
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
		final QuestState st = killer.getQuestState(getName());
		if ((st != null) && st.isCond(1) && Util.checkIfInRange(1500, npc, killer, false))
		{
			if (!st.hasQuestItems(MIDDLE_GRADE_LIZARD_SCALE))
			{
				giveItems(killer, MIDDLE_GRADE_LIZARD_SCALE, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				st.setCond(2, true);
			}
			else if (!st.hasQuestItems(HIGH_GRADE_LIZARD_SCALE))
			{
				giveItems(killer, HIGH_GRADE_LIZARD_SCALE, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				st.setCond(3, true);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (player.getLevel() < MIN_LEVEL) ? "32741-01.html" : "32741-02.htm";
				break;
			case State.STARTED:
				if (st.isCond(1) && !st.hasQuestItems(HIGH_GRADE_LIZARD_SCALE) && !st.hasQuestItems(MIDDLE_GRADE_LIZARD_SCALE))
				{
					htmltext = "32741-05.html";
				}
				else if (st.isCond(2) && st.hasQuestItems(MIDDLE_GRADE_LIZARD_SCALE))
				{
					htmltext = "32741-06.html";
				}
				
				if (st.isCond(2) && st.hasQuestItems(HIGH_GRADE_LIZARD_SCALE))
				{
					htmltext = "32741-07.html";
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00288_HandleWithCare(288, Q00288_HandleWithCare.class.getSimpleName(), "Handle With Care");
	}
}
