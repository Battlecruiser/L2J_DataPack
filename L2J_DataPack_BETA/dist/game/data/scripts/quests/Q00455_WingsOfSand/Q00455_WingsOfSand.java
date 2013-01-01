/*
 * Copyright (C) 2004-2012 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00455_WingsOfSand;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Wings of Sand (455)
 * @author Zoey76
 */
public class Q00455_WingsOfSand extends Quest
{
	// NPC
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
	private static final int EMERALD_HORN = 25718;
	private static final int DUST_RIDER = 25719;
	private static final int BLEEDING_FLY = 25720;
	private static final int BLACK_DAGGER_WING = 25721;
	private static final int SHADOW_SUMMONER = 25722;
	private static final int SPIKE_SLASHER = 25723;
	private static final int MUSCLE_BOMBER = 25724;
	// Items
	private static final int LARGE_BABY_DRAGON = 17250;
	// Misc
	private static final int MIN_LEVEL = 80;
	private static final int CHANCE = 350;
	
	private Q00455_WingsOfSand(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(SEPARATED_SOULS);
		addTalkId(SEPARATED_SOULS);
		addKillId(EMERALD_HORN, DUST_RIDER, BLEEDING_FLY, BLACK_DAGGER_WING, SHADOW_SUMMONER, SPIKE_SLASHER, MUSCLE_BOMBER);
		registerQuestItems(LARGE_BABY_DRAGON);
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
				if (player.getLevel() >= MIN_LEVEL)
				{
					htmltext = "32864-01.htm";
				}
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
						reward(st);
						htmltext = "32864-07.html";
						break;
					}
					case 3:
					{
						reward(st);
						htmltext = "32864-07.html";
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				if (!st.isNowAvailable())
				{
					htmltext = "32864-08.html";
				}
				else
				{
					st.setState(State.CREATED);
					if (player.getLevel() >= MIN_LEVEL)
					{
						htmltext = "32864-01.htm";
					}
				}
				break;
			}
		}
		return htmltext;
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
				case "32864-02.htm":
				case "32864-03.htm":
				case "32864-04.htm":
				{
					htmltext = event;
					break;
				}
				case "32864-05.htm":
				{
					if (player.getLevel() >= MIN_LEVEL)
					{
						st.startQuest();
						htmltext = event;
					}
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		QuestState st;
		if (killer.isInParty())
		{
			for (L2PcInstance player : killer.getParty().getMembers())
			{
				st = player.getQuestState(getName());
				if ((st != null) && Util.checkIfInRange(1500, npc, player, false) && (getRandom(1000) < CHANCE))
				{
					st.giveItems(LARGE_BABY_DRAGON, 1);
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
					if (st.getQuestItemsCount(LARGE_BABY_DRAGON) == 1)
					{
						st.setCond(2, true);
					}
					else if (st.getQuestItemsCount(LARGE_BABY_DRAGON) == 2)
					{
						st.setCond(3, true);
					}
				}
			}
		}
		else
		{
			st = killer.getQuestState(getName());
			if ((st != null) && Util.checkIfInRange(1500, npc, killer, false) && (getRandom(1000) < CHANCE))
			{
				st.giveItems(LARGE_BABY_DRAGON, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				if (st.getQuestItemsCount(LARGE_BABY_DRAGON) == 1)
				{
					st.setCond(2, true);
				}
				else if (st.getQuestItemsCount(LARGE_BABY_DRAGON) == 2)
				{
					st.setCond(3, true);
				}
			}
		}
		return null;
	}
	
	/**
	 * Reward the player.
	 * @param st the quest state of the player to reward
	 */
	private static final void reward(QuestState st)
	{
		int chance;
		for (int i = 1; i <= st.getCond() - 1; i++)
		{
			chance = getRandom(1000);
			if (chance <= 250)
			{
				st.giveItems(getRandom(15660, 15691), 1); // Armor Parts
			}
			else if ((chance > 250) && (chance <= 500))
			{
				st.giveItems(getRandom(15634, 15644), 1); // Weapon Parts
			}
			else if ((chance > 550) && (chance <= 750))
			{
				st.giveItems(getRandom(15769, 15771), 1); // Jewelry Parts
			}
			else if ((chance > 750) && (chance <= 900))
			{
				st.giveItems(getRandom(9552, 9557), 1); // Crystals
			}
			else if ((chance > 900) && (chance <= 970))
			{
				st.giveItems(6578, 1); // Blessed Scroll: Enchant Armor (S-Grade)
			}
			else if (chance > 970)
			{
				st.giveItems(6577, 1); // Blessed Scroll: Enchant Weapon (S-Grade)
			}
		}
		st.exitQuest(QuestType.DAILY, true);
	}
	
	public static void main(String[] args)
	{
		new Q00455_WingsOfSand(455, Q00455_WingsOfSand.class.getSimpleName(), "Wings of Sand");
	}
}
