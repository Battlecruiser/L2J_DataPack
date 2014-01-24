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
package quests.Q00119_LastImperialPrince;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Last Imperial Prince (119)
 * @author Adry_85
 */
public class Q00119_LastImperialPrince extends Quest
{
	// NPCs
	private static final int NAMELESS_SPIRIT = 31453;
	private static final int DEVORIN = 32009;
	// Item
	private static final int ANTIQUE_BROOCH = 7262;
	// Misc
	private static final int MIN_LEVEL = 74;
	
	public Q00119_LastImperialPrince(int id, String name, String descr)
	{
		super(id, name, descr);
		addStartNpc(NAMELESS_SPIRIT);
		addTalkId(NAMELESS_SPIRIT, DEVORIN);
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
		switch (event)
		{
			case "31453-02.htm":
			case "31453-03.htm":
			case "31453-10.html":
			{
				htmltext = event;
				break;
			}
			case "31453-04.html":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "31453-11.html":
			{
				if (st.isCond(2))
				{
					st.giveAdena(150292, true);
					st.addExpAndSp(902439, 90067);
					st.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
			case "brooch":
			{
				htmltext = (st.hasQuestItems(ANTIQUE_BROOCH)) ? "32009-02.html" : "32009-03.html";
				break;
			}
			case "32009-04.html":
			{
				if (st.isCond(1) && st.hasQuestItems(ANTIQUE_BROOCH))
				{
					st.setCond(2, true);
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
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
			{
				if (npc.getId() == NAMELESS_SPIRIT)
				{
					htmltext = "31453-06.html";
				}
				break;
			}
			case State.CREATED:
			{
				htmltext = ((player.getLevel() >= MIN_LEVEL) && st.hasQuestItems(ANTIQUE_BROOCH)) ? "31453-01.htm" : "31453-05.html";
				break;
			}
			case State.STARTED:
			{
				if (npc.getId() == NAMELESS_SPIRIT)
				{
					if (st.isCond(1))
					{
						if (st.hasQuestItems(ANTIQUE_BROOCH))
						{
							htmltext = "31453-07.html";
						}
						else
						{
							htmltext = "31453-08.html";
							st.exitQuest(true);
						}
					}
					else if (st.isCond(2))
					{
						htmltext = "31453-09.html";
					}
				}
				else if (npc.getId() == DEVORIN)
				{
					if (st.isCond(1))
					{
						htmltext = "32009-01.html";
					}
					else if (st.isCond(2))
					{
						htmltext = "32009-05.html";
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00119_LastImperialPrince(119, Q00119_LastImperialPrince.class.getSimpleName(), "Last Imperial Prince");
	}
}
