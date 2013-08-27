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
package quests.Q00035_FindGlitteringJewelry;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Find Glittering Jewelry (35)
 * @author malyelfik
 */
public class Q00035_FindGlitteringJewelry extends Quest
{
	// NPCs
	private static final int ELLIE = 30091;
	private static final int FELTON = 30879;
	// Monster
	private static final int ALLIGATOR = 20135;
	// Items
	private static final int SILVER_NUGGET = 1873;
	private static final int ORIHARUKON = 1893;
	private static final int THONS = 4044;
	private static final int JEWEL_BOX = 7077;
	private static final int ROUGH_JEWEL = 7162;
	// Misc
	private static final int MIN_LEVEL = 60;
	private static final int JEWEL_COUNT = 10;
	private static final int ORIHARUKON_COUNT = 5;
	private static final int NUGGET_COUNT = 500;
	private static final int THONS_COUNT = 150;
	
	private Q00035_FindGlitteringJewelry(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ELLIE);
		addTalkId(ELLIE, FELTON);
		addKillId(ALLIGATOR);
		registerQuestItems(ROUGH_JEWEL);
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
			case "30091-03.htm":
				st.startQuest();
				break;
			case "30879-02.html":
				st.setCond(2, true);
				break;
			case "30091-07.html":
				if (st.getQuestItemsCount(ROUGH_JEWEL) < JEWEL_COUNT)
				{
					return "30091-08.html";
				}
				st.takeItems(ROUGH_JEWEL, -1);
				st.setCond(4, true);
				break;
			case "30091-11.html":
				if ((st.getQuestItemsCount(ORIHARUKON) >= ORIHARUKON_COUNT) && (st.getQuestItemsCount(SILVER_NUGGET) >= NUGGET_COUNT) && (st.getQuestItemsCount(THONS) >= THONS_COUNT))
				{
					st.takeItems(ORIHARUKON, ORIHARUKON_COUNT);
					st.takeItems(SILVER_NUGGET, NUGGET_COUNT);
					st.takeItems(THONS, THONS_COUNT);
					st.giveItems(JEWEL_BOX, 1);
					st.exitQuest(false, true);
				}
				else
				{
					htmltext = "30091-12.html";
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
		final L2PcInstance member = getRandomPartyMember(player, 2);
		if (member != null)
		{
			final QuestState st = member.getQuestState(getName());
			if (getRandomBoolean())
			{
				st.giveItems(ROUGH_JEWEL, 1);
				if (st.getQuestItemsCount(ROUGH_JEWEL) >= JEWEL_COUNT)
				{
					st.setCond(3, true);
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
		
		switch (npc.getId())
		{
			case ELLIE:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= MIN_LEVEL) ? "30091-01.htm" : "30091-02.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "30091-04.html";
								break;
							case 3:
								htmltext = (st.getQuestItemsCount(ROUGH_JEWEL) >= JEWEL_COUNT) ? "30091-06.html" : "30091-05.html";
								break;
							case 4:
								htmltext = ((st.getQuestItemsCount(ORIHARUKON) >= ORIHARUKON_COUNT) && (st.getQuestItemsCount(SILVER_NUGGET) >= NUGGET_COUNT) && (st.getQuestItemsCount(THONS) >= THONS_COUNT)) ? "30091-09.html" : "30091-10.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case FELTON:
				if (st.isStarted())
				{
					if (st.isCond(1))
					{
						htmltext = "30879-01.html";
					}
					else if (st.isCond(2))
					{
						htmltext = "30879-03.html";
					}
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00035_FindGlitteringJewelry(35, Q00035_FindGlitteringJewelry.class.getSimpleName(), "Find Glittering Jewelry");
	}
}