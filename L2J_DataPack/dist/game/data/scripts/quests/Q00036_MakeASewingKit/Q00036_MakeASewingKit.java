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
package quests.Q00036_MakeASewingKit;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Make a Sewing Kit (36)
 * @author malyelfik
 */
public class Q00036_MakeASewingKit extends Quest
{
	// NPC
	private static final int FERRIS = 30847;
	// Monster
	private static final int ENCHANTED_IRON_GOLEM = 20566;
	// Items
	private static final int ARTISANS_FRAME = 1891;
	private static final int ORIHARUKON = 1893;
	private static final int SEWING_KIT = 7078;
	private static final int ENCHANTED_IRON = 7163;
	// Misc
	private static final int MIN_LEVEL = 60;
	private static final int IRON_COUNT = 5;
	private static final int COUNT = 10;
	
	private Q00036_MakeASewingKit(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(FERRIS);
		addTalkId(FERRIS);
		addKillId(ENCHANTED_IRON_GOLEM);
		registerQuestItems(ENCHANTED_IRON);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30847-03.htm":
				st.startQuest();
				break;
			case "30847-06.html":
				if (st.getQuestItemsCount(ENCHANTED_IRON) < IRON_COUNT)
				{
					return getNoQuestMsg(player);
				}
				st.takeItems(ENCHANTED_IRON, -1);
				st.setCond(3, true);
				break;
			case "30847-09.html":
				if ((st.getQuestItemsCount(ARTISANS_FRAME) >= COUNT) && (st.getQuestItemsCount(ORIHARUKON) >= COUNT))
				{
					st.takeItems(ARTISANS_FRAME, 10);
					st.takeItems(ORIHARUKON, 10);
					st.giveItems(SEWING_KIT, 1);
					st.exitQuest(false, true);
				}
				else
				{
					htmltext = "30847-10.html";
				}
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
		final L2PcInstance member = getRandomPartyMember(player, 1);
		if (member != null)
		{
			final QuestState st = member.getQuestState(getName());
			if (getRandomBoolean())
			{
				st.giveItems(ENCHANTED_IRON, 1);
				if (st.getQuestItemsCount(ENCHANTED_IRON) >= IRON_COUNT)
				{
					st.setCond(2, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (player.getLevel() >= MIN_LEVEL) ? "30847-01.htm" : "30847-02.html";
				break;
			case State.STARTED:
				switch (st.getCond())
				{
					case 1:
						htmltext = "30847-04.html";
						break;
					case 2:
						htmltext = "30847-05.html";
						break;
					case 3:
						htmltext = ((st.getQuestItemsCount(ARTISANS_FRAME) >= COUNT) && (st.getQuestItemsCount(ORIHARUKON) >= COUNT)) ? "30847-07.html" : "30847-08.html";
						break;
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00036_MakeASewingKit(36, Q00036_MakeASewingKit.class.getSimpleName(), "Make a Sewing Kit");
	}
}