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
package quests.Q00287_FiguringItOut;

import java.util.HashMap;
import java.util.Map;

import quests.Q00250_WatchWhatYouEat.Q00250_WatchWhatYouEat;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Figuring It Out! (287)
 * @author malyelfik
 */
public class Q00287_FiguringItOut extends Quest
{
	// NPCs
	private static final int LAKI = 32742;
	private static final Map<Integer, Integer> MONSTERS = new HashMap<>();
	
	static
	{
		MONSTERS.put(22768, 509); // Tanta Lizardman Scout
		MONSTERS.put(22769, 689); // Tanta Lizardman Warrior
		MONSTERS.put(22770, 123); // Tanta Lizardman Soldier
		MONSTERS.put(22771, 159); // Tanta Lizardman Berserker
		MONSTERS.put(22772, 739); // Tanta Lizardman Archer
		MONSTERS.put(22773, 737); // Tanta Lizardman Magician
		MONSTERS.put(22774, 261); // Tanta Lizardman Summoner
	}
	
	// Items
	private static final int VIAL_OF_TANTA_BLOOD = 15499;
	// Rewards
	private static final ItemHolder[] MOIRAI =
	{
		new ItemHolder(15776, 1),
		new ItemHolder(15779, 1),
		new ItemHolder(15782, 1),
		new ItemHolder(15785, 1),
		new ItemHolder(15788, 1),
		new ItemHolder(15812, 1),
		new ItemHolder(15813, 1),
		new ItemHolder(15814, 1),
		new ItemHolder(15646, 5),
		new ItemHolder(15649, 5),
		new ItemHolder(15652, 5),
		new ItemHolder(15655, 5),
		new ItemHolder(15658, 5),
		new ItemHolder(15772, 1),
		new ItemHolder(15773, 1),
		new ItemHolder(15774, 1)
	};
	
	private static final ItemHolder[] ICARUS =
	{
		new ItemHolder(10381, 1),
		new ItemHolder(10405, 1),
		new ItemHolder(10405, 4),
		new ItemHolder(10405, 4),
		new ItemHolder(10405, 6),
	};
	
	// Misc
	private static final int MIN_LEVEL = 82;
	
	public Q00287_FiguringItOut()
	{
		super(287, Q00287_FiguringItOut.class.getSimpleName(), "Figuring It Out!");
		addStartNpc(LAKI);
		addTalkId(LAKI);
		addKillId(MONSTERS.keySet());
		registerQuestItems(VIAL_OF_TANTA_BLOOD);
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
			case "32742-03.htm":
				st.startQuest();
				break;
			case "Icarus":
				if (st.getQuestItemsCount(VIAL_OF_TANTA_BLOOD) >= 500)
				{
					final ItemHolder holder = ICARUS[getRandom(ICARUS.length)];
					st.giveItems(holder);
					st.takeItems(VIAL_OF_TANTA_BLOOD, 500);
					st.playSound(QuestSound.ITEMSOUND_QUEST_FINISH);
					htmltext = "32742-06.html";
				}
				else
				{
					htmltext = "32742-07.html";
				}
				break;
			case "Moirai":
				if (st.getQuestItemsCount(VIAL_OF_TANTA_BLOOD) >= 100)
				{
					final ItemHolder holder = MOIRAI[getRandom(MOIRAI.length)];
					st.giveItems(holder);
					st.takeItems(VIAL_OF_TANTA_BLOOD, 100);
					st.playSound(QuestSound.ITEMSOUND_QUEST_FINISH);
					htmltext = "32742-08.html";
				}
				else
				{
					htmltext = "32742-09.html";
				}
				break;
			case "32742-11.html":
				if (!st.hasQuestItems(VIAL_OF_TANTA_BLOOD))
				{
					st.exitQuest(true, true);
					htmltext = "32742-12.html";
				}
				break;
			case "32742-13.html":
				st.exitQuest(true, true);
				break;
			case "32742-02.htm":
			case "32742-10.html":
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, 1);
		if (partyMember == null)
		{
			return super.onKill(npc, player, isSummon);
		}
		final QuestState st = getQuestState(partyMember, false);
		
		if (getRandom(1000) < MONSTERS.get(npc.getId()))
		{
			st.giveItems(VIAL_OF_TANTA_BLOOD, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
		final QuestState prev = player.getQuestState(Q00250_WatchWhatYouEat.class.getSimpleName());
		
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = ((player.getLevel() >= MIN_LEVEL) && (prev != null) && prev.isCompleted()) ? "32742-01.htm" : "32742-14.htm";
				break;
			case State.STARTED:
				htmltext = (st.getQuestItemsCount(VIAL_OF_TANTA_BLOOD) < 100) ? "32742-04.html" : "32742-05.html";
				break;
		}
		return htmltext;
	}
}