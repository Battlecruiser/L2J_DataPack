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
package quests.Q00237_WindsOfChange;

import quests.Q00238_SuccessFailureOfBusiness.Q00238_SuccessFailureOfBusiness;
import quests.Q00239_WontYouJoinUs.Q00239_WontYouJoinUs;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Winds of Change (237)<br>
 * Original Jython script by Bloodshed.
 * @author Joxit
 */
public class Q00237_WindsOfChange extends Quest
{
	// NPCs
	private static final int FLAUEN = 30899;
	private static final int IASON = 30969;
	private static final int ROMAN = 30897;
	private static final int MORELYN = 30925;
	private static final int HELVETICA = 32641;
	private static final int ATHENIA = 32643;
	// Items
	private static final int FLAUENS_LETTER = 14862;
	private static final int DOSKOZER_LETTER = 14863;
	private static final int ATHENIA_LETTER = 14864;
	private static final int VICINITY_OF_FOS = 14865;
	private static final int SUPPORT_CERTIFICATE = 14866;
	// Misc
	private static final int MIN_LEVEL = 82;
	
	public Q00237_WindsOfChange(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(FLAUEN);
		addTalkId(FLAUEN, IASON, ROMAN, MORELYN, HELVETICA, ATHENIA);
		registerQuestItems(FLAUENS_LETTER, DOSKOZER_LETTER, ATHENIA_LETTER);
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
			case "30899-02.htm":// FLAUEN
			case "30899-03.htm":
			case "30899-04.htm":
			case "30899-05.htm":
			case "30969-03.html":// IASON
			case "30969-03a.html":
			case "30969-03b.html":
			case "30969-04.html":
			case "30969-08.html":
			case "30969-08a.html":
			case "30969-08b.html":
			case "30969-08c.html":
			case "30897-02.html":// ROMAN
			case "30925-02.html":// MORELYN
				htmltext = event;
				break;
			case "30899-06.html":
				st.startQuest();
				giveItems(player, FLAUENS_LETTER, 1);
				htmltext = event;
				break;
			case "30969-02.html":
				takeItems(player, FLAUENS_LETTER, -1);
				htmltext = event;
				break;
			case "30969-05.html":
				if (st.isCond(1))
				{
					st.setCond(2, true);
					htmltext = event;
				}
				break;
			case "30897-03.html":
				if (st.isCond(2))
				{
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			case "30925-03.html":
				if (st.isCond(3))
				{
					st.setCond(4, true);
					htmltext = event;
				}
				break;
			case "30969-09.html":
				if (st.isCond(4))
				{
					giveItems(player, DOSKOZER_LETTER, 1);
					st.setCond(5, true);
					htmltext = event;
				}
				break;
			case "30969-10.html":
				if (st.isCond(4))
				{
					giveItems(player, ATHENIA_LETTER, 1);
					st.setCond(6, true);
					htmltext = event;
				}
				break;
			case "32641-02.html":
				st.giveAdena(213876, true);
				giveItems(player, VICINITY_OF_FOS, 1);
				st.addExpAndSp(892773, 60012);
				st.exitQuest(false, true);
				htmltext = event;
				break;
			case "32643-02.html":
				st.giveAdena(213876, true);
				giveItems(player, SUPPORT_CERTIFICATE, 1);
				st.addExpAndSp(892773, 60012);
				st.exitQuest(false, true);
				htmltext = event;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		String htmltext = getNoQuestMsg(talker);
		final QuestState st = talker.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case FLAUEN:
				switch (st.getState())
				{
					case State.COMPLETED:
						htmltext = "30899-09.html";
						break;
					case State.CREATED:
						htmltext = (talker.getLevel() >= MIN_LEVEL) ? "30899-01.htm" : "30899-00.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
							case 4:
								htmltext = "30899-07.html";
								break;
							case 2:
								htmltext = "30899-10.html";
								break;
							case 3:
								htmltext = "30899-11.html";
								break;
							case 5:
							case 6:
								htmltext = "30899-08.html";
								break;
						}
				}
				break;
			case IASON:
				if (st.isCompleted())
				{
					htmltext = Quest.getNoQuestMsg(talker);
				}
				else
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "30969-01.html";
							break;
						case 2:
							htmltext = "30969-06.html";
							break;
						case 4:
							htmltext = "30969-07.html";
							break;
						case 5:
						case 6:
							htmltext = "30969-11.html";
							break;
					}
				}
				break;
			case ROMAN:
				switch (st.getCond())
				{
					case 2:
						htmltext = "30897-01.html";
						break;
					case 3:
					case 4:
						htmltext = "30897-04.html";
						break;
				}
				break;
			case MORELYN:
				switch (st.getCond())
				{
					case 3:
						htmltext = "30925-01.html";
						break;
					case 4:
						htmltext = "30925-04.html";
						break;
				}
				break;
			case HELVETICA:
				if (st.isCompleted())
				{
					final QuestState q238 = st.getPlayer().getQuestState(Q00238_SuccessFailureOfBusiness.class.getSimpleName());
					htmltext = (st.hasQuestItems(VICINITY_OF_FOS) || ((q238 != null) && q238.isCompleted())) ? "32641-03.html" : "32641-05.html";
				}
				else if (st.isCond(5))
				{
					htmltext = "32641-01.html";
				}
				else if (st.isCond(6))
				{
					htmltext = "32641-04.html";
				}
				break;
			case ATHENIA:
				if (st.isCompleted())
				{
					final QuestState q239 = st.getPlayer().getQuestState(Q00239_WontYouJoinUs.class.getSimpleName());
					htmltext = (st.hasQuestItems(SUPPORT_CERTIFICATE) || ((q239 != null) && q239.isCompleted())) ? "32643-03.html" : "32643-05.html";
				}
				else if (st.isCond(5))
				{
					htmltext = "32643-04.html";
				}
				else if (st.isCond(6))
				{
					htmltext = "32643-01.html";
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00237_WindsOfChange(237, Q00237_WindsOfChange.class.getSimpleName(), "Winds of Change");
	}
}
