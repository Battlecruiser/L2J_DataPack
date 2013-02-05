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
package quests.Q00278_HomeSecurity;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Home Security (278)
 * @author malyelfik
 */
public class Q00278_HomeSecurity extends Quest
{
	// NPC
	private static final int TUNATUN = 31537;
	private static final int[] MONSTER =
	{
		18905,
		18906,
		18907
	};
	// Item
	private static final int SEL_MAHUM_MANE = 15531;
	
	public Q00278_HomeSecurity(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(TUNATUN);
		addTalkId(TUNATUN);
		addKillId(MONSTER);
		registerQuestItems(SEL_MAHUM_MANE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("31537-02.htm"))
		{
			htmltext = (player.getLevel() >= 82) ? "31537-02.htm" : "31537-03.html";
		}
		else if (event.equalsIgnoreCase("31537-04.htm"))
		{
			st.startQuest();
		}
		else if (event.equalsIgnoreCase("31537-07.html"))
		{
			int i0 = getRandom(100);
			
			if (i0 < 10)
			{
				st.giveItems(960, 1);
			}
			else if (i0 < 19)
			{
				st.giveItems(960, 2);
			}
			else if (i0 < 27)
			{
				st.giveItems(960, 3);
			}
			else if (i0 < 34)
			{
				st.giveItems(960, 4);
			}
			else if (i0 < 40)
			{
				st.giveItems(960, 5);
			}
			else if (i0 < 45)
			{
				st.giveItems(960, 6);
			}
			else if (i0 < 49)
			{
				st.giveItems(960, 7);
			}
			else if (i0 < 52)
			{
				st.giveItems(960, 8);
			}
			else if (i0 < 54)
			{
				st.giveItems(960, 9);
			}
			else if (i0 < 55)
			{
				st.giveItems(960, 10);
			}
			else if (i0 < 75)
			{
				st.giveItems(9553, 1);
			}
			else if (i0 < 90)
			{
				st.giveItems(9553, 2);
			}
			else
			{
				st.giveItems(959, 1);
			}
			
			st.exitQuest(true, true);
			htmltext = "31537-07.html";
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, 1);
		if (partyMember == null)
		{
			return null;
		}
		final QuestState st = partyMember.getQuestState(getName());
		
		int chance, i1;
		if (st.isCond(1))
		{
			switch (npc.getNpcId())
			{
				case 18907: // Beast Devourer
				case 18906: // Farm Bandit
					chance = getRandom(1000);
					if (chance < 85)
					{
						st.giveItems(SEL_MAHUM_MANE, 1);
						if (st.getQuestItemsCount(SEL_MAHUM_MANE) >= 300)
						{
							st.setCond(2, true);
						}
						else
						{
							st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
						}
					}
					break;
				case 18905: // Farm Ravager (Crazy)
					chance = getRandom(1000);
					if (chance < 486)
					{
						i1 = getRandom(6) + 1;
						if ((i1 + st.getQuestItemsCount(SEL_MAHUM_MANE)) >= 300)
						{
							st.giveItems(SEL_MAHUM_MANE, (300 - st.getQuestItemsCount(SEL_MAHUM_MANE)));
							st.setCond(2, true);
						}
						else
						{
							st.giveItems(SEL_MAHUM_MANE, i1);
							st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
						}
					}
					else
					{
						i1 = (getRandom(5) + 1);
						if ((i1 + st.getQuestItemsCount(SEL_MAHUM_MANE)) >= 300)
						{
							st.giveItems(SEL_MAHUM_MANE, (300 - st.getQuestItemsCount(SEL_MAHUM_MANE)));
							st.setCond(2, true);
						}
						else
						{
							st.giveItems(SEL_MAHUM_MANE, i1);
							st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
						}
					}
					break;
			}
		}
		return null;
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
				htmltext = "31537-01.htm";
				break;
			case State.STARTED:
				if (st.isCond(1) || (st.getQuestItemsCount(SEL_MAHUM_MANE) < 300))
				{
					htmltext = "31537-06.html";
				}
				else if (st.isCond(2) && (st.getQuestItemsCount(SEL_MAHUM_MANE) >= 300))
				{
					htmltext = "31537-05.html";
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00278_HomeSecurity(278, Q00278_HomeSecurity.class.getSimpleName(), "Home Security");
	}
}
