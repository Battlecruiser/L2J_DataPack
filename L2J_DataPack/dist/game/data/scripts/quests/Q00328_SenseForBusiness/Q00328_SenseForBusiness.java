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
package quests.Q00328_SenseForBusiness;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Sense for Business (328)
 * @author xban1x
 */
public class Q00328_SenseForBusiness extends Quest
{
	// NPCs
	private static final int SARIEN = 30436;
	private static final Map<Integer, int[]> MONSTER_EYES = new HashMap<>();
	private static final Map<Integer, Integer> MONSTER_BASILISKS = new HashMap<>();
	// @formatter:off
	static
	{
		MONSTER_EYES.put(20055, new int[] { 61, 62 });
		MONSTER_EYES.put(20059, new int[] { 61, 62 });
		MONSTER_EYES.put(20067, new int[] { 72, 74 });
		MONSTER_EYES.put(20068, new int[] { 78, 79 });
		MONSTER_BASILISKS.put(20070, 60);
		MONSTER_BASILISKS.put(20072, 63);
	}
	// @formatter:on
	// Items
	private static final int MONSTER_EYE_CARCASS = 1347;
	private static final int MONSTER_EYE_LENS = 1366;
	private static final int BASILISK_GIZZARD = 1348;
	// Misc
	private static final int MONSTER_EYE_CARCASS_ADENA = 25;
	private static final int MONSTER_EYE_LENS_ADENA = 1000;
	private static final int BASILISK_GIZZARD_ADENA = 60;
	private static final int BONUS = 618;
	private static final int BONUS_COUNT = 10;
	private static final int MIN_LVL = 21;
	
	public Q00328_SenseForBusiness()
	{
		super(328, Q00328_SenseForBusiness.class.getSimpleName(), "Sense for Business");
		addStartNpc(SARIEN);
		addTalkId(SARIEN);
		addKillId(MONSTER_EYES.keySet());
		addKillId(MONSTER_BASILISKS.keySet());
		registerQuestItems(MONSTER_EYE_CARCASS, MONSTER_EYE_LENS, BASILISK_GIZZARD);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st != null)
		{
			switch (event)
			{
				case "30436-03.htm":
				{
					st.startQuest();
					htmltext = event;
					break;
				}
				case "30436-06.html":
				{
					st.exitQuest(true, true);
					htmltext = event;
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st != null)
		{
			switch (st.getState())
			{
				case State.CREATED:
				{
					htmltext = player.getLevel() < MIN_LVL ? "30436-01.htm" : "30436-02.htm";
					break;
				}
				case State.STARTED:
				{
					final long carcass = st.getQuestItemsCount(MONSTER_EYE_CARCASS);
					final long lens = st.getQuestItemsCount(MONSTER_EYE_LENS);
					final long gizzards = st.getQuestItemsCount(BASILISK_GIZZARD);
					if ((carcass + lens + gizzards) > 0)
					{
						st.giveAdena(((carcass * MONSTER_EYE_CARCASS_ADENA) + (lens * MONSTER_EYE_LENS_ADENA) + (gizzards * BASILISK_GIZZARD_ADENA) + ((carcass + lens + gizzards) >= BONUS_COUNT ? BONUS : 0)), true);
						takeItems(player, -1, MONSTER_EYE_CARCASS, MONSTER_EYE_LENS, BASILISK_GIZZARD);
						htmltext = "30436-05.html";
					}
					else
					{
						htmltext = "30436-04.html";
					}
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isStarted())
		{
			final int chance = getRandom(100);
			if (MONSTER_EYES.containsKey(npc.getId()))
			{
				if (chance < MONSTER_EYES.get(npc.getId())[0])
				{
					st.giveItems(MONSTER_EYE_CARCASS, 1);
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
				else if (chance < MONSTER_EYES.get(npc.getId())[1])
				{
					st.giveItems(MONSTER_EYE_LENS, 1);
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
			else if (MONSTER_BASILISKS.containsKey(npc.getId()))
			{
				if (chance < MONSTER_BASILISKS.get(npc.getId()))
				{
					st.giveItems(BASILISK_GIZZARD, 1);
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}
}
