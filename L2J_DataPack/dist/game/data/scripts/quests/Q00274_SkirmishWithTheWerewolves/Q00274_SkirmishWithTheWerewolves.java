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
package quests.Q00274_SkirmishWithTheWerewolves;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Skirmish with the Werewolves (274)
 * @author xban1x
 */
public final class Q00274_SkirmishWithTheWerewolves extends Quest
{
	// NPC
	private static final int BRUKURSE = 30569;
	// Monsters
	private static final int[] MONSTERS = new int[]
	{
		20363, // Maraku Werewolf
		20364, // Maraku Werewolf Chieftain
	};
	// Items
	private static final int NECKLACE_OF_COURAGE = 1506;
	private static final int NECKLACE_OF_VALOR = 1507;
	private static final int WEREWOLF_HEAD = 1477;
	private static final int WEREWOLF_TOTEM = 1501;
	// Misc
	private static final int MIN_LVL = 9;
	
	public Q00274_SkirmishWithTheWerewolves()
	{
		super(274, Q00274_SkirmishWithTheWerewolves.class.getSimpleName(), "Skirmish with the Werewolves");
		addStartNpc(BRUKURSE);
		addTalkId(BRUKURSE);
		addKillId(MONSTERS);
		registerQuestItems(WEREWOLF_HEAD, WEREWOLF_TOTEM);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && event.equalsIgnoreCase("30569-04.htm"))
		{
			st.startQuest();
			return event;
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = killer.getQuestState(getName());
		if ((st != null) && st.isCond(1))
		{
			st.giveItems(WEREWOLF_HEAD, 1);
			if (getRandom(100) <= 5)
			{
				st.giveItems(WEREWOLF_TOTEM, 1);
			}
			if (st.getQuestItemsCount(WEREWOLF_HEAD) >= 40)
			{
				st.setCond(2, true);
			}
			else
			{
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
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				if (hasAtLeastOneQuestItem(player, NECKLACE_OF_VALOR, NECKLACE_OF_COURAGE))
				{
					htmltext = (player.getRace() == Race.ORC) ? (player.getLevel() >= MIN_LVL) ? "30569-03.htm" : "30569-02.html" : "30569-01.html";
				}
				else
				{
					htmltext = "30569-08.html";
				}
				break;
			}
			case State.STARTED:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "30569-05.html";
						break;
					}
					case 2:
					{
						final long heads = st.getQuestItemsCount(WEREWOLF_HEAD);
						if (heads >= 40)
						{
							final long totems = st.getQuestItemsCount(WEREWOLF_TOTEM);
							st.giveAdena((heads * 30) + (totems * 600) + 2300, true);
							st.exitQuest(true, true);
							htmltext = (totems > 0) ? "30569-07.html" : "30569-06.html";
						}
					}
				}
				break;
			}
		}
		return htmltext;
	}
}
