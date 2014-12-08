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
package quests.Q10275_ContainingTheAttributePower;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.Elementals;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Containing the Attribute Power (10275)<br>
 * Original Jython script by Kerberos v1.0 on 2009/05/03.
 * @author nonom
 */
public class Q10275_ContainingTheAttributePower extends Quest
{
	// NPCs
	private static final int HOLLY = 30839;
	private static final int WEBER = 31307;
	private static final int YIN = 32325;
	private static final int YANG = 32326;
	private static final int WATER = 27380;
	private static final int AIR = 27381;
	// Items
	private static final int YINSWORD = 13845;
	private static final int YANGSWORD = 13881;
	private static final int SOULPIECEWATER = 13861;
	private static final int SOULPIECEAIR = 13862;
	// Skills
	private static final SkillHolder BLESSING_OF_FIRE = new SkillHolder(2635, 1);
	private static final SkillHolder BLESSING_OF_EARTH = new SkillHolder(2636, 1);
	
	public Q10275_ContainingTheAttributePower()
	{
		super(10275, Q10275_ContainingTheAttributePower.class.getSimpleName(), "Containing the Attribute Power");
		addStartNpc(HOLLY, WEBER);
		addTalkId(HOLLY, WEBER, YIN, YANG);
		addKillId(AIR, WATER);
		registerQuestItems(YINSWORD, YANGSWORD, SOULPIECEWATER, SOULPIECEAIR);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "30839-02.html":
			case "31307-02.html":
				st.startQuest();
				break;
			case "30839-05.html":
				st.setCond(2, true);
				break;
			case "31307-05.html":
				st.setCond(7, true);
				break;
			case "32325-03.html":
				st.setCond(3, true);
				st.giveItems(YINSWORD, 1, Elementals.FIRE, 10);
				break;
			case "32326-03.html":
				st.setCond(8, true);
				st.giveItems(YANGSWORD, 1, Elementals.EARTH, 10);
				break;
			case "32325-06.html":
				if (st.hasQuestItems(YINSWORD))
				{
					st.takeItems(YINSWORD, 1);
					htmltext = "32325-07.html";
				}
				st.giveItems(YINSWORD, 1, Elementals.FIRE, 10);
				break;
			case "32326-06.html":
				if (st.hasQuestItems(YANGSWORD))
				{
					st.takeItems(YANGSWORD, 1);
					htmltext = "32326-07.html";
				}
				st.giveItems(YANGSWORD, 1, Elementals.EARTH, 10);
				break;
			case "32325-09.html":
				st.setCond(5, true);
				BLESSING_OF_FIRE.getSkill().applyEffects(player, player);
				st.giveItems(YINSWORD, 1, Elementals.FIRE, 10);
				break;
			case "32326-09.html":
				st.setCond(10, true);
				BLESSING_OF_EARTH.getSkill().applyEffects(player, player);
				st.giveItems(YANGSWORD, 1, Elementals.EARTH, 10);
				break;
		}
		
		if (Util.isDigit(event))
		{
			htmltext = Integer.toString(npc.getId()) + "-1" + event + ".html";
			st.giveItems(10520 + Integer.valueOf(event), 2);
			st.addExpAndSp(202160, 20375);
			st.exitQuest(false, true);
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		switch (npc.getId())
		{
			case AIR:
				if ((st.isCond(8) || st.isCond(10)) && (st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == YANGSWORD) && (st.getQuestItemsCount(SOULPIECEAIR) < 6) && (getRandom(100) < 30))
				{
					st.giveItems(SOULPIECEAIR, 1);
					if (st.getQuestItemsCount(SOULPIECEAIR) >= 6)
					{
						st.setCond(st.getCond() + 1, true);
					}
					else
					{
						st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
					}
				}
				break;
			case WATER:
				if (((st.getCond() >= 3) || (st.getCond() <= 5)) && (st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == YINSWORD) && (st.getQuestItemsCount(SOULPIECEWATER) < 6) && (getRandom(100) < 30))
				{
					st.giveItems(SOULPIECEWATER, 1);
					if (st.getQuestItemsCount(SOULPIECEWATER) >= 6)
					{
						st.setCond(st.getCond() + 1, true);
					}
					else
					{
						st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
					}
				}
				break;
		}
		return null;
		
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
		
		switch (npc.getId())
		{
			case HOLLY:
			{
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() > 75) ? "30839-01.htm" : "30839-00.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "30839-03.html";
								break;
							case 2:
								htmltext = "30839-05.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = "30839-0a.html";
						break;
				}
				break;
			}
			case WEBER:
			{
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() > 75) ? "31307-01.htm" : "31307-00.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "31307-03.html";
								break;
							case 7:
								htmltext = "31307-05.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = "31307-0a.html";
						break;
				}
				break;
			}
			case YIN:
			{
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 2:
							htmltext = "32325-01.html";
							break;
						case 3:
						case 5:
							htmltext = "32325-04.html";
							break;
						case 4:
							htmltext = "32325-08.html";
							st.takeItems(YINSWORD, 1);
							st.takeItems(SOULPIECEWATER, -1);
							break;
						case 6:
							htmltext = "32325-10.html";
							break;
					}
				}
				break;
			}
			case YANG:
			{
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 7:
							htmltext = "32326-01.html";
							break;
						case 8:
						case 10:
							htmltext = "32326-04.html";
							break;
						case 9:
							htmltext = "32326-08.html";
							st.takeItems(YANGSWORD, 1);
							st.takeItems(SOULPIECEAIR, -1);
							break;
						case 11:
							htmltext = "32326-10.html";
							break;
					}
				}
				break;
			}
		}
		return htmltext;
	}
}
