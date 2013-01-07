/*
 * Copyright (C) 2004-2013 L2J Server
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
package quests.Q10503_FrintezzaEmbroideredSoulCloak;

import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Frintezza Embroidered Soul Cloak (10503)
 * @author Zoey76
 */
public class Q10503_FrintezzaEmbroideredSoulCloak extends Quest
{
	// NPC
	private static final int OLF_ADAMS = 32612;
	// Monster
	private static final int FRINTEZZA = 29045;
	// Items
	private static final int FRINTEZZAS_SOUL_FRAGMENT = 21724;
	private static final int SOUL_CLOAK_OF_FRINTEZZA = 21721;
	// Misc
	private static final int MIN_LEVEL = 80;
	private static final int FRAGMENT_COUNT = 20;
	
	private Q10503_FrintezzaEmbroideredSoulCloak(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(OLF_ADAMS);
		addTalkId(OLF_ADAMS);
		addKillId(FRINTEZZA);
		registerQuestItems(FRINTEZZAS_SOUL_FRAGMENT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && (player.getLevel() >= MIN_LEVEL) && event.equals("32612-04.html"))
		{
			st.startQuest();
			return event;
		}
		return null;
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
				htmltext = (player.getLevel() < MIN_LEVEL) ? "32612-02.html" : "32612-01.htm";
				break;
			}
			case State.STARTED:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "32612-05.html";
						break;
					}
					case 2:
					{
						if (st.getQuestItemsCount(FRINTEZZAS_SOUL_FRAGMENT) >= FRAGMENT_COUNT)
						{
							st.giveItems(SOUL_CLOAK_OF_FRINTEZZA, 1);
							st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
							st.exitQuest(false, true);
							htmltext = "32612-06.html";
						}
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = "32612-03.html";
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (killer.isInParty())
		{
			if (killer.getParty().isInCommandChannel())
			{
				for (L2Party party : killer.getParty().getCommandChannel().getPartys())
				{
					for (L2PcInstance player : party.getMembers())
					{
						rewardPlayer(player, npc);
					}
				}
			}
			else
			{
				for (L2PcInstance player : killer.getParty().getMembers())
				{
					rewardPlayer(player, npc);
				}
			}
		}
		else
		{
			rewardPlayer(killer, npc);
		}
		return super.onKill(npc, killer, isPet);
	}
	
	private final void rewardPlayer(L2PcInstance player, L2Npc npc)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(1) && Util.checkIfInRange(1500, npc, player, false))
		{
			st.giveItems(FRINTEZZAS_SOUL_FRAGMENT, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			
			if (st.getQuestItemsCount(FRINTEZZAS_SOUL_FRAGMENT) >= FRAGMENT_COUNT)
			{
				st.setCond(2, true);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Q10503_FrintezzaEmbroideredSoulCloak(10503, Q10503_FrintezzaEmbroideredSoulCloak.class.getSimpleName(), "Frintezza Embroidered Soul Cloak");
	}
}
