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
package quests.Q00239_WontYouJoinUs;

import quests.Q00237_WindsOfChange.Q00237_WindsOfChange;
import quests.Q00238_SuccessFailureOfBusiness.Q00238_SuccessFailureOfBusiness;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Won't You Join Us (239)<br>
 * Original Jython script by Bloodshed.
 * @author Joxit
 */
public class Q00239_WontYouJoinUs extends Quest
{
	// NPC
	private static final int ATHENIA = 32643;
	// Mobs
	private static final int WASTE_LANDFILL_MACHINE = 18805;
	private static final int SUPPRESSOR = 22656;
	private static final int EXTERMINATOR = 22657;
	// Items
	private static final int SUPPORT_CERTIFICATE = 14866;
	private static final int DESTROYED_MACHINE_PIECE = 14869;
	private static final int ENCHANTED_GOLEM_FRAGMENT = 14870;
	// Misc
	private static final int ENCHANTED_GOLEM_FRAGMENT_NEEDED = 20;
	private static final int DESTROYED_MACHINE_PIECE_NEEDED = 10;
	private static final int CHANCE_FOR_FRAGMENT = 80;
	private static final int MIN_LEVEL = 82;
	
	public Q00239_WontYouJoinUs()
	{
		super(239, Q00239_WontYouJoinUs.class.getSimpleName(), "Won't You Join Us?");
		addStartNpc(ATHENIA);
		addTalkId(ATHENIA);
		addKillId(WASTE_LANDFILL_MACHINE, SUPPRESSOR, EXTERMINATOR);
		registerQuestItems(DESTROYED_MACHINE_PIECE, ENCHANTED_GOLEM_FRAGMENT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "32643-02.htm":
				htmltext = event;
				break;
			case "32643-03.html":
				st.startQuest();
				htmltext = event;
				break;
			case "32643-07.html":
				if (st.isCond(2))
				{
					st.setCond(3, true);
					htmltext = event;
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (npc.getId() == WASTE_LANDFILL_MACHINE)
		{
			final L2PcInstance partyMember = getRandomPartyMember(killer, 1);
			if (partyMember != null)
			{
				final QuestState st = getQuestState(partyMember, false);
				if (st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) < DESTROYED_MACHINE_PIECE_NEEDED)
				{
					st.giveItems(DESTROYED_MACHINE_PIECE, 1);
				}
				if (st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) == DESTROYED_MACHINE_PIECE_NEEDED)
				{
					st.setCond(2, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
		}
		else
		{
			final L2PcInstance partyMember = getRandomPartyMember(killer, 3);
			if ((partyMember != null) && (getRandom(100) < CHANCE_FOR_FRAGMENT))
			{
				final QuestState st = getQuestState(partyMember, false);
				if (st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) < ENCHANTED_GOLEM_FRAGMENT_NEEDED)
				{
					st.giveItems(ENCHANTED_GOLEM_FRAGMENT, 1);
				}
				if (st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) == ENCHANTED_GOLEM_FRAGMENT_NEEDED)
				{
					st.setCond(4, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		String htmltext = getNoQuestMsg(talker);
		final QuestState st = getQuestState(talker, true);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = "32643-11.html";
				break;
			case State.CREATED:
				final QuestState q237 = st.getPlayer().getQuestState(Q00237_WindsOfChange.class.getSimpleName());
				final QuestState q238 = st.getPlayer().getQuestState(Q00238_SuccessFailureOfBusiness.class.getSimpleName());
				if ((q238 != null) && q238.isCompleted())
				{
					htmltext = "32643-12.html";
				}
				else if ((q237 != null) && q237.isCompleted() && (talker.getLevel() >= MIN_LEVEL) && st.hasQuestItems(SUPPORT_CERTIFICATE))
				{
					htmltext = "32643-01.htm";
				}
				else
				{
					htmltext = "32643-00.html";
				}
				break;
			case State.STARTED:
				switch (st.getCond())
				{
					case 1:
						htmltext = (st.hasQuestItems(DESTROYED_MACHINE_PIECE)) ? "32643-05.html" : "32643-04.html";
						break;
					case 2:
						if (st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) == DESTROYED_MACHINE_PIECE_NEEDED)
						{
							htmltext = "32643-06.html";
							st.takeItems(DESTROYED_MACHINE_PIECE, -1);
						}
						break;
					case 3:
						htmltext = (st.hasQuestItems(ENCHANTED_GOLEM_FRAGMENT)) ? "32643-08.html" : "32643-09.html";
						break;
					case 4:
						if (st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) == ENCHANTED_GOLEM_FRAGMENT_NEEDED)
						{
							htmltext = "32643-10.html";
							st.giveAdena(283346, true);
							st.takeItems(SUPPORT_CERTIFICATE, 1);
							st.addExpAndSp(1319736, 103553);
							st.exitQuest(false, true);
						}
						break;
				}
				break;
		}
		return htmltext;
	}
}