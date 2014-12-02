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
package quests.Q00627_HeartInSearchOfPower;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.Config;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Heart in Search of Power (627)
 * @author Citizen
 */
public class Q00627_HeartInSearchOfPower extends Quest
{
	// NPCs
	private static final int MYSTERIOUS_NECROMANCER = 31518;
	private static final int ENFEUX = 31519;
	// Items
	private static final int SEAL_OF_LIGHT = 7170;
	private static final int BEAD_OF_OBEDIENCE = 7171;
	private static final int GEM_OF_SAINTS = 7172;
	// Monsters
	private static final Map<Integer, Integer> MONSTERS = new HashMap<>();
	static
	{
		MONSTERS.put(21520, 661); // Eye of Splendor
		MONSTERS.put(21523, 668); // Flash of Splendor
		MONSTERS.put(21524, 714); // Blade of Splendor
		MONSTERS.put(21525, 714); // Blade of Splendor
		MONSTERS.put(21526, 796); // Wisdom of Splendor
		MONSTERS.put(21529, 659); // Soul of Splendor
		MONSTERS.put(21530, 704); // Victory of Splendor
		MONSTERS.put(21531, 791); // Punishment of Splendor
		MONSTERS.put(21532, 820); // Shout of Splendor
		MONSTERS.put(21535, 827); // Signet of Splendor
		MONSTERS.put(21536, 798); // Crown of Splendor
		MONSTERS.put(21539, 875); // Wailing of Splendor
		MONSTERS.put(21540, 875); // Wailing of Splendor
		MONSTERS.put(21658, 791); // Punishment of Splendor
	}
	// Misc
	private static final int MIN_LEVEL_REQUIRED = 60;
	private static final int BEAD_OF_OBEDIENCE_COUNT_REQUIRED = 300;
	// Rewards ID's
	private static final int ASOFE = 4043;
	private static final int THONS = 4044;
	private static final int ENRIA = 4042;
	private static final int MOLD_HARDENER = 4041;
	
	public Q00627_HeartInSearchOfPower()
	{
		super(627, Q00627_HeartInSearchOfPower.class.getSimpleName(), "Heart in Search of Power");
		addStartNpc(MYSTERIOUS_NECROMANCER);
		addTalkId(MYSTERIOUS_NECROMANCER, ENFEUX);
		addKillId(MONSTERS.keySet());
		registerQuestItems(SEAL_OF_LIGHT, BEAD_OF_OBEDIENCE, GEM_OF_SAINTS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		String htmltext = event;
		switch (event)
		{
			case "31518-02.htm":
				st.startQuest();
				break;
			case "31518-06.html":
				if (st.getQuestItemsCount(BEAD_OF_OBEDIENCE) < BEAD_OF_OBEDIENCE_COUNT_REQUIRED)
				{
					return "31518-05.html";
				}
				st.giveItems(SEAL_OF_LIGHT, 1);
				st.takeItems(BEAD_OF_OBEDIENCE, -1);
				st.setCond(3);
				break;
			case "Adena":
			case "Asofes":
			case "Thons":
			case "Enrias":
			case "Mold_Hardener":
				if (!st.hasQuestItems(GEM_OF_SAINTS))
				{
					return "31518-11.html";
				}
				switch (event)
				{
					case "Adena":
						st.giveAdena(100000, true);
						break;
					case "Asofes":
						st.rewardItems(ASOFE, 13);
						st.giveAdena(6400, true);
						break;
					case "Thons":
						st.rewardItems(THONS, 13);
						st.giveAdena(6400, true);
						break;
					case "Enrias":
						st.rewardItems(ENRIA, 6);
						st.giveAdena(13600, true);
						break;
					case "Mold_Hardener":
						st.rewardItems(MOLD_HARDENER, 3);
						st.giveAdena(17200, true);
						break;
				}
				htmltext = "31518-10.html";
				st.exitQuest(true);
				break;
			case "31519-02.html":
				if (st.hasQuestItems(SEAL_OF_LIGHT) && st.isCond(3))
				{
					st.giveItems(GEM_OF_SAINTS, 1);
					st.takeItems(SEAL_OF_LIGHT, -1);
					st.setCond(4);
				}
				else
				{
					htmltext = getNoQuestMsg(player);
				}
				break;
			case "31518-09.html":
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final L2PcInstance partyMember = getRandomPartyMember(killer, 1);
		if (partyMember != null)
		{
			final QuestState st = getQuestState(partyMember, false);
			final float chance = (MONSTERS.get(npc.getId()) * Config.RATE_QUEST_DROP);
			if (getRandom(1000) < chance)
			{
				st.giveItems(BEAD_OF_OBEDIENCE, 1);
				if (st.getQuestItemsCount(BEAD_OF_OBEDIENCE) < BEAD_OF_OBEDIENCE_COUNT_REQUIRED)
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
				else
				{
					st.setCond(2, true);
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
		if (st == null)
		{
			return htmltext;
		}
		switch (st.getState())
		{
			case State.CREATED:
				if (npc.getId() == MYSTERIOUS_NECROMANCER)
				{
					htmltext = (player.getLevel() >= MIN_LEVEL_REQUIRED) ? "31518-01.htm" : "31518-00.htm";
				}
				break;
			case State.STARTED:
				switch (npc.getId())
				{
					case MYSTERIOUS_NECROMANCER:
						switch (st.getCond())
						{
							case 1:
								htmltext = "31518-03.html";
								break;
							case 2:
								htmltext = "31518-04.html";
								break;
							case 3:
								htmltext = "31518-07.html";
								break;
							case 4:
								htmltext = "31518-08.html";
								break;
						}
						break;
					case ENFEUX:
						switch (st.getCond())
						{
							case 3:
								htmltext = "31519-01.html";
								break;
							case 4:
								htmltext = "31519-03.html";
								break;
						}
						break;
				}
				break;
		}
		return htmltext;
	}
}