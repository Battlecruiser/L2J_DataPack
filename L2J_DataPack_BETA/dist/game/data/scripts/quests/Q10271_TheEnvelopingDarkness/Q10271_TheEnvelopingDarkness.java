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
package quests.Q10271_TheEnvelopingDarkness;

import quests.Q10269_ToTheSeedOfDestruction.Q10269_ToTheSeedOfDestruction;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * The Enveloping Darkness (10271)
 * @author Gladicek
 */
public class Q10271_TheEnvelopingDarkness extends Quest
{
	private static final int ORBYU = 32560;
	private static final int EL = 32556;
	private static final int MEDIBAL_CORPSE = 32528;
	private static final int MEDIBAL_DOCUMENT = 13852;
	
	public Q10271_TheEnvelopingDarkness(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ORBYU);
		addTalkId(ORBYU, EL, MEDIBAL_CORPSE);
		registerQuestItems(MEDIBAL_DOCUMENT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "32560-05.html":
				st.startQuest();
				break;
			case "32556-06.html":
				st.setCond(2, true);
				break;
			case "32556-09.html":
				if (st.hasQuestItems(MEDIBAL_DOCUMENT))
				{
					st.takeItems(MEDIBAL_DOCUMENT, -1);
					st.setCond(4, true);
				}
				break;
			default:
				break;
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case ORBYU:
				switch (st.getState())
				{
					case State.CREATED:
						st = player.getQuestState(Q10269_ToTheSeedOfDestruction.class.getSimpleName());
						htmltext = ((player.getLevel() >= 75) && (st != null) && st.isCompleted()) ? "32560-01.htm" : "32560-02.html";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "32560-05.html"; // TODO this html should most probably be different
								break;
							case 2:
								htmltext = "32560-06.html";
								break;
							case 3:
								htmltext = "32560-07.html";
								break;
							case 4:
								htmltext = "32560-08.html";
								st.giveAdena(62516, true);
								st.addExpAndSp(377403, 37867);
								st.exitQuest(false, true);
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = "32560-03.html";
						break;
				}
				
				break;
			case EL:
				if (st.isCompleted())
				{
					htmltext = "32556-02.html";
				}
				else if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "32556-01.html";
							break;
						case 2:
							htmltext = "32556-07.html";
							break;
						case 3:
							htmltext = "32556-08.html";
							break;
						case 4:
							htmltext = "32556-09.html";
							break;
					}
				}
				break;
			case MEDIBAL_CORPSE:
				if (st.isCompleted())
				{
					htmltext = "32528-02.html";
				}
				else if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 2:
							htmltext = "32528-01.html";
							st.setCond(3, true);
							st.giveItems(MEDIBAL_DOCUMENT, 1);
							break;
						case 3:
						case 4:
							htmltext = "32528-03.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q10271_TheEnvelopingDarkness(10271, Q10271_TheEnvelopingDarkness.class.getSimpleName(), "The Enveloping Darkness");
	}
}
