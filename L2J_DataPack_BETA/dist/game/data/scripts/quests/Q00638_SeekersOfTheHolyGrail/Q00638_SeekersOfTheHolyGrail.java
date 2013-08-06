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
package quests.Q00638_SeekersOfTheHolyGrail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Seekers Of The Holy Grail (638)
 * @author netvirus
 */
public final class Q00638_SeekersOfTheHolyGrail extends Quest
{
	private static class DropInfo
	{
		private final int _itemId;
		private final int _chance;
		private final int _keyId;
		private final int _keyChance;
		private final int _keyCount;
		
		public DropInfo(int itemId, int chance)
		{
			this(itemId, chance, 0, 0, 0);
		}
		
		public DropInfo(int itemId, int chance, int keyId, int keyChance, int count)
		{
			_itemId = itemId;
			_chance = chance;
			_keyId = keyId;
			_keyChance = keyChance;
			_keyCount = count;
		}
		
		public int getItemId()
		{
			return _itemId;
		}
		
		public int getChance()
		{
			return _chance;
		}
		
		public int getKeyId()
		{
			return _keyId;
		}
		
		public int getKeyChance()
		{
			return _keyChance;
		}
		
		public int getKeyCount()
		{
			return _keyCount;
		}
	}
	
	// Npc
	private static final int INNOCENTIN = 31328;
	// Items
	private static final int TOTEM = 8068;
	private static final int ANTEROOM_KEY = 8273;
	private static final int CHAPEL_KEY = 8274;
	private static final int KEY_OF_DARKNESS = 8275;
	// Miscs
	private static final int MIN_LVL = 73;
	private static final int TOTEMS_REQUIRED_COUNT = 2000;
	// Rewards
	private static final int SCROLL_ENCHANT_W_S = 959;
	private static final int SCROLL_ENCHANT_A_S = 960;
	// Mobs
	private static final Map<Integer, DropInfo> MOBS_DROP_CHANCES = new HashMap<>();
	
	static
	{
		MOBS_DROP_CHANCES.put(22136, new DropInfo(TOTEM, 55)); // Gatekeeper Zombie
		MOBS_DROP_CHANCES.put(22137, new DropInfo(TOTEM, 6)); // Penance Guard
		MOBS_DROP_CHANCES.put(22138, new DropInfo(TOTEM, 6)); // Chapel Guard
		MOBS_DROP_CHANCES.put(22139, new DropInfo(TOTEM, 54)); // Old Aristocrat's Soldier
		MOBS_DROP_CHANCES.put(22140, new DropInfo(TOTEM, 54)); // Zombie Worker
		MOBS_DROP_CHANCES.put(22141, new DropInfo(TOTEM, 55)); // Forgotten Victim
		MOBS_DROP_CHANCES.put(22142, new DropInfo(TOTEM, 54)); // Triol's Layperson
		MOBS_DROP_CHANCES.put(22143, new DropInfo(TOTEM, 62, CHAPEL_KEY, 100, 1)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22144, new DropInfo(TOTEM, 54)); // Resurrected Temple Knight
		MOBS_DROP_CHANCES.put(22145, new DropInfo(TOTEM, 53)); // Ritual Sacrifice
		MOBS_DROP_CHANCES.put(22146, new DropInfo(TOTEM, 54, KEY_OF_DARKNESS, 10, 1)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22147, new DropInfo(TOTEM, 55)); // Ritual Offering
		MOBS_DROP_CHANCES.put(22148, new DropInfo(TOTEM, 45)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22149, new DropInfo(TOTEM, 54, ANTEROOM_KEY, 100, 6)); // Ritual Offering
		MOBS_DROP_CHANCES.put(22150, new DropInfo(TOTEM, 46)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22151, new DropInfo(TOTEM, 62, KEY_OF_DARKNESS, 10, 1)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22152, new DropInfo(TOTEM, 55)); // Temple Guard
		MOBS_DROP_CHANCES.put(22153, new DropInfo(TOTEM, 54)); // Temple Guard Captain
		MOBS_DROP_CHANCES.put(22154, new DropInfo(TOTEM, 53)); // Ritual Sacrifice
		MOBS_DROP_CHANCES.put(22155, new DropInfo(TOTEM, 75)); // Triol's High Priest
		MOBS_DROP_CHANCES.put(22156, new DropInfo(TOTEM, 67)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22157, new DropInfo(TOTEM, 66)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22158, new DropInfo(TOTEM, 67)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22159, new DropInfo(TOTEM, 75)); // Triol's High Priest
		MOBS_DROP_CHANCES.put(22160, new DropInfo(TOTEM, 67)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22161, new DropInfo(TOTEM, 78)); // Ritual Sacrifice
		MOBS_DROP_CHANCES.put(22162, new DropInfo(TOTEM, 67)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22163, new DropInfo(TOTEM, 87)); // Triol's High Priest
		MOBS_DROP_CHANCES.put(22164, new DropInfo(TOTEM, 67)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22165, new DropInfo(TOTEM, 66)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22166, new DropInfo(TOTEM, 66)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22167, new DropInfo(TOTEM, 75)); // Triol's High Priest
		MOBS_DROP_CHANCES.put(22168, new DropInfo(TOTEM, 66)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22169, new DropInfo(TOTEM, 78)); // Ritual Sacrifice
		MOBS_DROP_CHANCES.put(22170, new DropInfo(TOTEM, 67)); // Triol's Believer
		MOBS_DROP_CHANCES.put(22171, new DropInfo(TOTEM, 87)); // Triol's High Priest
		MOBS_DROP_CHANCES.put(22172, new DropInfo(TOTEM, 78)); // Ritual Sacrifice
		MOBS_DROP_CHANCES.put(22173, new DropInfo(TOTEM, 66)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22174, new DropInfo(TOTEM, 67)); // Triol's Priest
		MOBS_DROP_CHANCES.put(22175, new DropInfo(TOTEM, 3)); // Andreas' Captain of the Royal Guard
		MOBS_DROP_CHANCES.put(22176, new DropInfo(TOTEM, 3)); // Andreas' Royal Guards
		MOBS_DROP_CHANCES.put(22188, new DropInfo(TOTEM, 3)); // Andreas' Captain of the Royal Guard
		MOBS_DROP_CHANCES.put(22189, new DropInfo(TOTEM, 3)); // Andreas' Royal Guards
		MOBS_DROP_CHANCES.put(22190, new DropInfo(TOTEM, 3)); // Ritual Sacrifice
		MOBS_DROP_CHANCES.put(22191, new DropInfo(TOTEM, 3)); // Andreas' Captain of the Royal Guard
		MOBS_DROP_CHANCES.put(22192, new DropInfo(TOTEM, 3)); // Andreas' Royal Guards
		MOBS_DROP_CHANCES.put(22193, new DropInfo(TOTEM, 3)); // Andreas' Royal Guards
		MOBS_DROP_CHANCES.put(22194, new DropInfo(TOTEM, 3)); // Penance Guard
		MOBS_DROP_CHANCES.put(22194, new DropInfo(TOTEM, 3)); // Ritual Sacrifice
	}
	
	private Q00638_SeekersOfTheHolyGrail(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(INNOCENTIN);
		addTalkId(INNOCENTIN);
		addKillId(MOBS_DROP_CHANCES.keySet());
		registerQuestItems(TOTEM);
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
			case "31328-03.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "31328-06.html":
			{
				if (st.isStarted())
				{
					htmltext = event;
				}
				break;
			}
			case "reward":
			{
				if (st.isStarted() && (st.getQuestItemsCount(TOTEM) >= TOTEMS_REQUIRED_COUNT))
				{
					if (getRandom(100) < 80)
					{
						if (getRandomBoolean())
						{
							st.rewardItems(SCROLL_ENCHANT_A_S, 1);
						}
						else
						{
							st.rewardItems(SCROLL_ENCHANT_W_S, 1);
						}
						htmltext = "31328-07.html";
					}
					else
					{
						st.giveAdena(3576000, true);
						htmltext = "31328-08.html";
					}
					st.takeItems(TOTEM, 2000);
				}
				break;
			}
			case "31328-09.html":
			{
				if (st.isStarted())
				{
					st.exitQuest(true, true);
					htmltext = "31328-09.html";
				}
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
			final DropInfo info = MOBS_DROP_CHANCES.get(npc.getId());
			final QuestState st3 = randomList.get(getRandom(randomList.size()));
			if ((getRandom(100) < info.getChance()) && Util.checkIfInRange(1500, npc, st3.getPlayer(), true))
			{
				final int rate = (npc.isChampion()) ? (int) Config.L2JMOD_CHAMPION_REWARDS : (int) Config.RATE_QUEST_DROP;
				st3.giveItems(info.getItemId(), 1 * rate);
				st3.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				if ((info.getKeyId() > 0) && (getRandom(100) < info.getKeyChance()))
				{
					((L2MonsterInstance) npc).dropItem(killer, info.getKeyId(), info.getKeyCount());
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
				htmltext = (player.getLevel() >= MIN_LVL) ? "31328-01.htm" : "31328-02.htm";
				break;
			}
			case State.STARTED:
			{
				htmltext = (st.getQuestItemsCount(TOTEM) >= TOTEMS_REQUIRED_COUNT) ? "31328-04.html" : "31328-05.html";
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00638_SeekersOfTheHolyGrail(638, Q00638_SeekersOfTheHolyGrail.class.getSimpleName(), "Seekers Of The Holy Grail");
	}
	
}
