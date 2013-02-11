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
package quests.Q10505_JewelOfValakas;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Jewel of Valakas (10505)
 * @author Zoey76
 */
public class Q10505_JewelOfValakas extends Quest
{
	// NPC
	private static final int KLEIN = 31540;
	// Monster
	private static final int VALAKAS = 29028;
	// Items
	private static final int EMPTY_CRYSTAL = 21906;
	private static final int FILLED_CRYSTAL_VALAKAS_ENERGY = 21908;
	private static final int JEWEL_OF_VALAKAS = 21898;
	private static final int VACUALITE_FLOATING_STONE = 7267;
	// Misc
	private static final int MIN_LEVEL = 83;
	
	private Q10505_JewelOfValakas(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KLEIN);
		addTalkId(KLEIN);
		addKillId(VALAKAS);
		registerQuestItems(EMPTY_CRYSTAL, FILLED_CRYSTAL_VALAKAS_ENERGY);
	}
	
	@Override
	public void actionForEachPlayer(L2PcInstance player, L2Npc npc, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && st.isCond(1) && Util.checkIfInRange(1500, npc, player, false))
		{
			st.takeItems(EMPTY_CRYSTAL, -1);
			st.giveItems(FILLED_CRYSTAL_VALAKAS_ENERGY, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			st.setCond(2, true);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		if ((player.getLevel() >= MIN_LEVEL) && st.hasQuestItems(VACUALITE_FLOATING_STONE))
		{
			switch (event)
			{
				case "31540-05.htm":
				case "31540-06.htm":
				{
					htmltext = event;
					break;
				}
				case "31540-07.html":
				{
					st.startQuest();
					st.giveItems(EMPTY_CRYSTAL, 1);
					htmltext = event;
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		executeForEachPlayer(killer, npc, isSummon, true, true);
		return super.onKill(npc, killer, isSummon);
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
				if (player.getLevel() < MIN_LEVEL)
				{
					htmltext = "31540-02.html";
				}
				else if (!st.hasQuestItems(VACUALITE_FLOATING_STONE))
				{
					htmltext = "31540-04.html";
				}
				else
				{
					htmltext = "31540-01.htm";
				}
				break;
			}
			case State.STARTED:
			{
				switch (st.getCond())
				{
					case 1:
					{
						if (st.hasQuestItems(EMPTY_CRYSTAL))
						{
							htmltext = "31540-08.html";
						}
						else
						{
							st.giveItems(EMPTY_CRYSTAL, 1);
							htmltext = "31540-09.html";
						}
						break;
					}
					case 2:
					{
						st.giveItems(JEWEL_OF_VALAKAS, 1);
						st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
						st.exitQuest(false, true);
						htmltext = "31540-10.html";
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = "31540-03.html";
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q10505_JewelOfValakas(10505, Q10505_JewelOfValakas.class.getSimpleName(), "Jewel of Valakas");
	}
}
