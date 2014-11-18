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
package quests.Q00031_SecretBuriedInTheSwamp;

import java.util.Arrays;
import java.util.List;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Secret Buried in the Swamp (31)
 * @author janiko
 */
public final class Q00031_SecretBuriedInTheSwamp extends Quest
{
	// NPCs
	private static final int ABERCROMBIE = 31555;
	private static final int FORGOTTEN_MONUMENT_1 = 31661;
	private static final int FORGOTTEN_MONUMENT_2 = 31662;
	private static final int FORGOTTEN_MONUMENT_3 = 31663;
	private static final int FORGOTTEN_MONUMENT_4 = 31664;
	private static final int CORPSE_OF_DWARF = 31665;
	// Items
	private static final int KRORINS_JOURNAL = 7252;
	// Misc
	private static final int MIN_LVL = 66;
	// Monuments
	private static final List<Integer> MONUMENTS = Arrays.asList(FORGOTTEN_MONUMENT_1, FORGOTTEN_MONUMENT_2, FORGOTTEN_MONUMENT_3, FORGOTTEN_MONUMENT_4);
	
	public Q00031_SecretBuriedInTheSwamp()
	{
		super(31, Q00031_SecretBuriedInTheSwamp.class.getSimpleName(), "Secret Buried in the Swamp");
		addStartNpc(ABERCROMBIE);
		addTalkId(ABERCROMBIE, CORPSE_OF_DWARF);
		addTalkId(MONUMENTS);
		registerQuestItems(KRORINS_JOURNAL);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31555-02.html":
			{
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "31665-02.html":
			{
				if (st.isCond(1))
				{
					st.setCond(2, true);
					st.giveItems(KRORINS_JOURNAL, 1);
					htmltext = event;
				}
				break;
			}
			case "31555-05.html":
			{
				if (st.isCond(2) && st.hasQuestItems(KRORINS_JOURNAL))
				{
					st.takeItems(KRORINS_JOURNAL, -1);
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "31661-02.html":
			case "31662-02.html":
			case "31663-02.html":
			case "31664-02.html":
			{
				if (MONUMENTS.contains(npc.getId()) && st.isCond(MONUMENTS.indexOf(npc.getId()) + 3))
				{
					st.setCond(st.getCond() + 1, true);
					htmltext = event;
				}
				break;
			}
			case "31555-08.html":
			{
				if (st.isCond(7))
				{
					st.addExpAndSp(490000, 45880);
					st.giveAdena(120000, true);
					st.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
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
		switch (npc.getId())
		{
			case ABERCROMBIE:
			{
				switch (st.getState())
				{
					case State.CREATED:
					{
						htmltext = (player.getLevel() >= MIN_LVL) ? "31555-01.htm" : "31555-03.htm";
						break;
					}
					case State.STARTED:
					{
						switch (st.getCond())
						{
							case 1:
							{
								htmltext = "31555-02.html";
								break;
							}
							case 2:
							{
								if (st.hasQuestItems(KRORINS_JOURNAL))
								{
									htmltext = "31555-04.html";
								}
								break;
							}
							case 3:
							{
								htmltext = "31555-06.html";
								break;
							}
							case 7:
							{
								htmltext = "31555-07.html";
								break;
							}
						}
						break;
					}
					case State.COMPLETED:
					{
						htmltext = getAlreadyCompletedMsg(player);
						break;
					}
				}
				break;
			}
			case CORPSE_OF_DWARF:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "31665-01.html";
						break;
					}
					case 2:
					{
						htmltext = "31665-03.html";
						break;
					}
				}
				break;
			}
			case FORGOTTEN_MONUMENT_1:
			case FORGOTTEN_MONUMENT_2:
			case FORGOTTEN_MONUMENT_3:
			case FORGOTTEN_MONUMENT_4:
			{
				final int loc = MONUMENTS.indexOf(npc.getId()) + 3;
				if (st.isCond(loc))
				{
					htmltext = npc.getId() + "-01.html";
				}
				else if (st.isCond(loc + 1))
				{
					htmltext = npc.getId() + "-03.html";
				}
				break;
			}
		}
		return htmltext;
	}
}
