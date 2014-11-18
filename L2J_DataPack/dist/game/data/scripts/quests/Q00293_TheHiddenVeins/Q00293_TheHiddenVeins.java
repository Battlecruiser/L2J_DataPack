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
package quests.Q00293_TheHiddenVeins;

import quests.Q00281_HeadForTheHills.Q00281_HeadForTheHills;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * The Hidden Veins (293)
 * @author xban1x
 */
public final class Q00293_TheHiddenVeins extends Quest
{
	// NPCs
	private static final int FILAUR = 30535;
	private static final int CHICHIRIN = 30539;
	// Items
	private static final int CHRYSOLITE_ORE = 1488;
	private static final int TORN_MAP_FRAGMENT = 1489;
	private static final int HIDDEN_ORE_MAP = 1490;
	// Monsters
	private static final int[] MONSTERS = new int[]
	{
		20446,
		20447,
		20448,
	};
	// Misc
	private static final int MIN_LVL = 6;
	private static final int REQUIRED_TORN_MAP_FRAGMENT = 4;
	
	public Q00293_TheHiddenVeins()
	{
		super(293, Q00293_TheHiddenVeins.class.getSimpleName(), "The Hidden Veins");
		addStartNpc(FILAUR);
		addTalkId(FILAUR, CHICHIRIN);
		addKillId(MONSTERS);
		registerQuestItems(CHRYSOLITE_ORE, TORN_MAP_FRAGMENT, HIDDEN_ORE_MAP);
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
			case "30535-04.htm":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "30535-07.html":
			{
				st.exitQuest(true, true);
				htmltext = event;
				break;
			}
			case "30535-08.html":
			{
				htmltext = event;
				break;
			}
			case "30539-03.html":
			{
				if (st.getQuestItemsCount(TORN_MAP_FRAGMENT) >= REQUIRED_TORN_MAP_FRAGMENT)
				{
					st.giveItems(HIDDEN_ORE_MAP, 1);
					st.takeItems(TORN_MAP_FRAGMENT, REQUIRED_TORN_MAP_FRAGMENT);
					htmltext = event;
				}
				else
				{
					htmltext = "30539-02.html";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = killer.getQuestState(getName());
		if (st != null)
		{
			final int chance = getRandom(100);
			if (chance > 50)
			{
				st.giveItems(CHRYSOLITE_ORE, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
			else if (chance < 5)
			{
				st.giveItems(TORN_MAP_FRAGMENT, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case FILAUR:
			{
				switch (st.getState())
				{
					case State.CREATED:
					{
						htmltext = (player.getRace() == Race.DWARF) ? (player.getLevel() >= MIN_LVL) ? "30535-03.htm" : "30535-02.htm" : "30535-01.htm";
						break;
					}
					case State.STARTED:
					{
						if (hasAtLeastOneQuestItem(player, CHRYSOLITE_ORE, HIDDEN_ORE_MAP))
						{
							final long ores = st.getQuestItemsCount(CHRYSOLITE_ORE);
							final long maps = st.getQuestItemsCount(HIDDEN_ORE_MAP);
							st.giveAdena((ores * 5) + (maps * 500) + (((ores + maps) >= 10) ? 2000 : 0), true);
							takeItems(player, -1, CHRYSOLITE_ORE, HIDDEN_ORE_MAP);
							Q00281_HeadForTheHills.giveNewbieReward(player);
							htmltext = (ores > 0) ? (maps > 0) ? "30535-10.html" : "30535-06.html" : "30535-09.html";
						}
						else
						{
							htmltext = "30535-05.html";
						}
						break;
					}
				}
				break;
			}
			case CHICHIRIN:
			{
				htmltext = "30539-01.html";
				break;
			}
		}
		return htmltext;
	}
}
