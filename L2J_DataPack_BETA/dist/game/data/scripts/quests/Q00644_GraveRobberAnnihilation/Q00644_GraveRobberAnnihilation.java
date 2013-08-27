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
package quests.Q00644_GraveRobberAnnihilation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.l2jserver.Config;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Grave Robber Annihilation (644)
 * @author netvirus
 */
public final class Q00644_GraveRobberAnnihilation extends Quest
{
	// Npc
	private static final int KARUDA = 32017;
	// Item
	private static final int ORC_GOODS = 8088;
	// Miscs
	private static final int MIN_LVL = 20;
	private static final int ORC_GOODS_REQUIRED_COUNT = 120;
	// Monsters
	private static final Map<Integer, Integer> MONSTER_DROP_CHANCES = new HashMap<>();
	// Rewards
	private static final Map<String, ItemHolder> REWARDS = new HashMap<>();
	
	static
	{
		MONSTER_DROP_CHANCES.put(22003, 714); // Grave Robber Scout
		MONSTER_DROP_CHANCES.put(22004, 841); // Grave Robber Lookout
		MONSTER_DROP_CHANCES.put(22005, 778); // Grave Robber Ranger
		MONSTER_DROP_CHANCES.put(22006, 746); // Grave Robber Guard
		MONSTER_DROP_CHANCES.put(22008, 810); // Grave Robber Fighter
		
		REWARDS.put("varnish", new ItemHolder(1865, 30)); // Varnish
		REWARDS.put("animalskin", new ItemHolder(1867, 40)); // Animal Skin
		REWARDS.put("animalbone", new ItemHolder(1872, 40)); // Animal Bone
		REWARDS.put("charcoal", new ItemHolder(1871, 30)); // Charcoal
		REWARDS.put("coal", new ItemHolder(1870, 30)); // Coal
		REWARDS.put("ironore", new ItemHolder(1869, 30)); // Iron Ore
	}
	
	private Q00644_GraveRobberAnnihilation(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KARUDA);
		addTalkId(KARUDA);
		addKillId(MONSTER_DROP_CHANCES.keySet());
		registerQuestItems(ORC_GOODS);
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
			case "32017-03.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "32017-06.html":
			{
				if (st.isCond(2) && (st.getQuestItemsCount(ORC_GOODS) >= ORC_GOODS_REQUIRED_COUNT))
				{
					htmltext = event;
				}
				break;
			}
			case "varnish":
			case "animalskin":
			case "animalbone":
			case "charcoal":
			case "coal":
			case "ironore":
			{
				if (st.isCond(2))
				{
					final ItemHolder reward = REWARDS.get(event);
					st.rewardItems(reward.getId(), reward.getCount());
					st.exitQuest(true, true);
					htmltext = "32017-07.html";
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
		if ((st != null) && st.isCond(1))
		{
			randomList.add(st);
			randomList.add(st);
		}
		
		if (killer.isInParty())
		{
			for (L2PcInstance member : killer.getParty().getMembers())
			{
				final QuestState st2 = member.getQuestState(getName());
				if ((st2 != null) && st2.isCond(1))
				{
					randomList.add(st2);
				}
			}
		}
		
		if (!randomList.isEmpty())
		{
			final QuestState st3 = randomList.get(getRandom(randomList.size()));
			final long count = st3.getQuestItemsCount(ORC_GOODS);
			if ((count < ORC_GOODS_REQUIRED_COUNT) && Util.checkIfInRange(1500, npc, st3.getPlayer(), true))
			{
				int chance = (int) ((npc.isChampion() ? Config.L2JMOD_CHAMPION_REWARDS : Config.RATE_QUEST_DROP) * MONSTER_DROP_CHANCES.get(npc.getId()));
				int numItems = chance / 1000;
				chance = chance % 1000;
				if (getRandom(1000) < chance)
				{
					numItems++;
				}
				if (numItems > 0)
				{
					if ((count + numItems) >= ORC_GOODS_REQUIRED_COUNT)
					{
						numItems = ORC_GOODS_REQUIRED_COUNT - (int) count;
						st3.setCond(2, true);
					}
					else
					{
						st3.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
					}
					st3.giveItems(ORC_GOODS, numItems);
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
				htmltext = (player.getLevel() >= MIN_LVL) ? "32017-01.htm" : "32017-02.htm";
				break;
			}
			case State.STARTED:
			{
				htmltext = (st.isCond(2) && (st.getQuestItemsCount(ORC_GOODS) >= ORC_GOODS_REQUIRED_COUNT)) ? "32017-04.html" : "32017-05.html";
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00644_GraveRobberAnnihilation(644, Q00644_GraveRobberAnnihilation.class.getSimpleName(), "Grave Robber Annihilation");
	}
}