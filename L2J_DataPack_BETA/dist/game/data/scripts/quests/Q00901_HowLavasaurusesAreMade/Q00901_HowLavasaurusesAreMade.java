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
package quests.Q00901_HowLavasaurusesAreMade;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.model.quest.State;

/**
 * How Lavasauruses Are Made (901)
 * @author UnAfraid, nonom, malyelfik
 */
public class Q00901_HowLavasaurusesAreMade extends Quest
{
	// NPC
	private static final int ROONEY = 32049;
	// Monsters
	private static final int LAVASAURUS_NEWBORN = 18799;
	private static final int LAVASAURUS_FLEDGIING = 18800;
	private static final int LAVASAURUS_ADULT = 18801;
	private static final int LAVASAURUS_ELDERLY = 18802;
	// Items
	private static final int FRAGMENT_STONE = 21909;
	private static final int FRAGMENT_HEAD = 21910;
	private static final int FRAGMENT_BODY = 21911;
	private static final int FRAGMENT_HORN = 21912;
	// Rewards
	private static final int TOTEM_OF_BODY = 21899;
	private static final int TOTEM_OF_SPIRIT = 21900;
	private static final int TOTEM_OF_COURAGE = 21901;
	private static final int TOTEM_OF_FORTITUDE = 21902;
	
	public Q00901_HowLavasaurusesAreMade(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ROONEY);
		addTalkId(ROONEY);
		addKillId(LAVASAURUS_NEWBORN, LAVASAURUS_FLEDGIING, LAVASAURUS_ADULT, LAVASAURUS_ELDERLY);
		registerQuestItems(FRAGMENT_STONE, FRAGMENT_HORN, FRAGMENT_HEAD, FRAGMENT_BODY);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = event;
		switch (event)
		{
			case "32049-03.htm":
			case "32049-08.html":
			case "32049-09.html":
			case "32049-10.html":
			case "32049-11.html":
				break;
			case "32049-04.htm":
				st.startQuest();
				break;
			case "32049-12.html":
				st.giveItems(TOTEM_OF_BODY, 1);
				st.exitQuest(QuestType.DAILY, true);
				break;
			case "32049-13.html":
				st.giveItems(TOTEM_OF_SPIRIT, 1);
				st.exitQuest(QuestType.DAILY, true);
				break;
			case "32049-14.html":
				st.giveItems(TOTEM_OF_FORTITUDE, 1);
				st.exitQuest(QuestType.DAILY, true);
				break;
			case "32049-15.html":
				st.giveItems(TOTEM_OF_COURAGE, 1);
				st.exitQuest(QuestType.DAILY, true);
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
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
				htmltext = (st.getPlayer().getLevel() >= 76) ? "32049-01.htm" : "32049-02.htm";
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					htmltext = "32049-05.html";
				}
				else if (st.isCond(2))
				{
					if (gotAllQuestItems(st))
					{
						st.takeItems(FRAGMENT_STONE, -1);
						st.takeItems(FRAGMENT_HEAD, -1);
						st.takeItems(FRAGMENT_BODY, -1);
						st.takeItems(FRAGMENT_HORN, -1);
						htmltext = "32049-06.html";
					}
					else
					{
						htmltext = "32049-07.html";
					}
				}
				break;
			case State.COMPLETED:
				if (st.isNowAvailable())
				{
					st.setState(State.CREATED);
					htmltext = (st.getPlayer().getLevel() >= 76) ? "32049-01.htm" : "32049-02.html";
				}
				else
				{
					htmltext = "32049-16.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(1))
		{
			switch (npc.getNpcId())
			{
				case LAVASAURUS_NEWBORN:
					giveQuestItems(st, FRAGMENT_STONE);
					break;
				case LAVASAURUS_FLEDGIING:
					giveQuestItems(st, FRAGMENT_HEAD);
					break;
				case LAVASAURUS_ADULT:
					giveQuestItems(st, FRAGMENT_BODY);
					break;
				case LAVASAURUS_ELDERLY:
					giveQuestItems(st, FRAGMENT_HORN);
					break;
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public boolean gotAllQuestItems(QuestState st)
	{
		return (st.getQuestItemsCount(FRAGMENT_STONE) >= 10) && (st.getQuestItemsCount(FRAGMENT_HEAD) >= 10) && (st.getQuestItemsCount(FRAGMENT_BODY) >= 10) && (st.getQuestItemsCount(FRAGMENT_HORN) >= 10);
	}
	
	public void giveQuestItems(QuestState st, int itemId)
	{
		if (st.getQuestItemsCount(itemId) < 10)
		{
			st.giveItems(itemId, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		else if (gotAllQuestItems(st))
		{
			st.setCond(2, true);
		}
	}
	
	public static void main(String[] values)
	{
		new Q00901_HowLavasaurusesAreMade(901, Q00901_HowLavasaurusesAreMade.class.getSimpleName(), "How Lavasauruses Are Made");
	}
}