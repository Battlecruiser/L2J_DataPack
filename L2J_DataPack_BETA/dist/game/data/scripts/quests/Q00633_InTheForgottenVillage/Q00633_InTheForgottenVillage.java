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
package quests.Q00633_InTheForgottenVillage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * In The Forgotten Village (633)
 * @author netvirus
 */
public final class Q00633_InTheForgottenVillage extends Quest
{
	// NPC
	private static final int MINA = 31388;
	// Items
	private static final int RIB_BONE_OF_A_BLACK_MAGUS = 7544;
	private static final int ZOMBIES_LIVER = 7545;
	// Miscs
	private static final int MIN_LVL = 65;
	private static final int RIB_BONE_REQUIRED_COUNT = 200;
	// Mobs
	private static final Map<Integer, ItemHolder> MOBS_DROP_CHANCES = new HashMap<>();
	
	static
	{
		MOBS_DROP_CHANCES.put(21553, new ItemHolder(ZOMBIES_LIVER, 417)); // Trampled Man
		MOBS_DROP_CHANCES.put(21554, new ItemHolder(ZOMBIES_LIVER, 417)); // Trampled Man
		MOBS_DROP_CHANCES.put(21557, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 394)); // Bone Snatcher
		MOBS_DROP_CHANCES.put(21558, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 394)); // Bone Snatcher
		MOBS_DROP_CHANCES.put(21559, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 436)); // Bone Maker
		MOBS_DROP_CHANCES.put(21560, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 430)); // Bone Shaper
		MOBS_DROP_CHANCES.put(21561, new ItemHolder(ZOMBIES_LIVER, 538)); // Sacrificed Man
		MOBS_DROP_CHANCES.put(21563, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 436)); // Bone Collector
		MOBS_DROP_CHANCES.put(21564, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 414)); // Skull Collector
		MOBS_DROP_CHANCES.put(21565, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 420)); // Bone Animator
		MOBS_DROP_CHANCES.put(21566, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 460)); // Skull Animator
		MOBS_DROP_CHANCES.put(21567, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 549)); // Bone Slayer
		MOBS_DROP_CHANCES.put(21570, new ItemHolder(ZOMBIES_LIVER, 508)); // Ghost of Betrayer
		MOBS_DROP_CHANCES.put(21572, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 465)); // Bone Sweeper
		MOBS_DROP_CHANCES.put(21574, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 586)); // Bone Grinder
		MOBS_DROP_CHANCES.put(21575, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 329)); // Bone Grinder
		MOBS_DROP_CHANCES.put(21578, new ItemHolder(ZOMBIES_LIVER, 649)); // Behemoth Zombie
		MOBS_DROP_CHANCES.put(21580, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 462)); // Bone Caster
		MOBS_DROP_CHANCES.put(21581, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 505)); // Bone Puppeteer
		MOBS_DROP_CHANCES.put(21583, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 475)); // Bone Scavenger
		MOBS_DROP_CHANCES.put(21584, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 475)); // Bone Scavenger
		MOBS_DROP_CHANCES.put(21596, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 543)); // Requiem Lord
		MOBS_DROP_CHANCES.put(21597, new ItemHolder(ZOMBIES_LIVER, 510)); // Requiem Behemoth
		MOBS_DROP_CHANCES.put(21598, new ItemHolder(ZOMBIES_LIVER, 572)); // Requiem Behemoth
		MOBS_DROP_CHANCES.put(21599, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 580)); // Requiem Priest
		MOBS_DROP_CHANCES.put(21600, new ItemHolder(ZOMBIES_LIVER, 561)); // Requiem Behemoth
		MOBS_DROP_CHANCES.put(21601, new ItemHolder(RIB_BONE_OF_A_BLACK_MAGUS, 677)); // Requiem Behemoth
	}
	
	private Q00633_InTheForgottenVillage(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(MINA);
		addTalkId(MINA);
		addKillId(MOBS_DROP_CHANCES.keySet());
		registerQuestItems(RIB_BONE_OF_A_BLACK_MAGUS, ZOMBIES_LIVER);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31388-03.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "31388-04.html":
			case "31388-05.html":
			case "31388-06.html":
			{
				if (st.isStarted())
				{
					htmltext = event;
				}
				break;
			}
			case "31388-07.html":
			{
				if (st.isCond(2))
				{
					if (st.getQuestItemsCount(RIB_BONE_OF_A_BLACK_MAGUS) >= RIB_BONE_REQUIRED_COUNT)
					{
						st.giveAdena(25000, true);
						st.addExpAndSp(305235, 0);
						st.takeItems(RIB_BONE_OF_A_BLACK_MAGUS, -1);
						st.setCond(1, true);
						htmltext = event;
					}
					else
					{
						htmltext = "31388-08.html";
					}
				}
				break;
			}
			case "31388-09.html":
			{
				if (st.isStarted())
				{
					st.exitQuest(true, true);
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final List<QuestState> randomList = new ArrayList<>();
		final QuestState st = killer.getQuestState(getName());
		if ((st != null) && st.isStarted())
		{
			randomList.add(st);
			randomList.add(st);
		}
		
		if (killer.isInParty())
		{
			for (L2PcInstance member : killer.getParty().getMembers())
			{
				final QuestState st2 = member.getQuestState(getName());
				if ((st2 != null) && st2.isStarted())
				{
					randomList.add(st2);
				}
			}
		}
		
		if (!randomList.isEmpty())
		{
			final QuestState st3 = randomList.get(getRandom(randomList.size()));
			final ItemHolder info = MOBS_DROP_CHANCES.get(npc.getId());
			if ((getRandom(1000) < info.getCount()) && Util.checkIfInRange(1500, npc, killer, false))
			{
				switch (info.getId())
				{
					case RIB_BONE_OF_A_BLACK_MAGUS:
					{
						if (st3.isCond(1))
						{
							st3.giveItems(RIB_BONE_OF_A_BLACK_MAGUS, 1);
							if (st3.getQuestItemsCount(RIB_BONE_OF_A_BLACK_MAGUS) == RIB_BONE_REQUIRED_COUNT)
							{
								st3.setCond(2, true);
							}
							else
							{
								st3.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
							}
						}
						break;
					}
					case ZOMBIES_LIVER:
					{
						st3.giveItems(ZOMBIES_LIVER, 1);
						st3.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
						break;
					}
				}
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
			{
				htmltext = (player.getLevel() >= MIN_LVL) ? "31388-01.htm" : "31388-02.htm";
				break;
			}
			case State.STARTED:
			{
				htmltext = (st.getQuestItemsCount(RIB_BONE_OF_A_BLACK_MAGUS) >= RIB_BONE_REQUIRED_COUNT) ? "31388-04.html" : "31388-05.html";
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00633_InTheForgottenVillage(633, Q00633_InTheForgottenVillage.class.getSimpleName(), "In The Forgotten Village");
	}
}
