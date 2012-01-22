/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10288_SecretMission;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * 2010-08-07 Based on Freya PTS
 * @author Gnacik
 */
public class Q10288_SecretMission extends Quest
{
	private static final String qn = "10288_SecretMission";
	// NPC's
	private static final int _dominic = 31350;
	private static final int _aquilani = 32780;
	private static final int _greymore = 32757;
	// Items
	private static final int _letter = 15529;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == _dominic)
		{
			if (event.equalsIgnoreCase("31350-05.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.giveItems(_letter, 1);
				st.playSound("ItemSound.quest_accept");
			}
		}
		else if ((npc.getNpcId() == _greymore) && event.equalsIgnoreCase("32757-03.htm"))
		{
			st.unset("cond");
			st.takeItems(_letter, -1);
			st.giveItems(57, 106583);
			st.addExpAndSp(417788, 46320);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		else if (npc.getNpcId() == _aquilani)
		{
			if (st.isStarted())
			{
				if (event.equalsIgnoreCase("32780-05.html"))
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
			}
			else if (st.isCompleted() && event.equalsIgnoreCase("teleport"))
			{
				player.teleToLocation(118833, -80589, -2688);
				return null;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		final int npcId = npc.getNpcId();
		final int cond = st.getInt("cond");
		switch (npcId)
		{
			case _dominic:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= 82) ? "31350-01.htm" : "31350-00.htm";
						break;
					case State.STARTED:
						if (cond == 1)
						{
							htmltext = "31350-06.htm";
						}
						else if (cond == 2)
						{
							htmltext = "31350-07.htm";
						}
						break;
					case State.COMPLETED:
						htmltext = "31350-08.htm";
						break;
				}
				break;
			case _aquilani:
				if (cond == 1)
				{
					htmltext = "32780-03.html";
				}
				else if (cond == 2)
				{
					htmltext = "32780-06.html";
				}
				break;
			case _greymore:
				if (cond == 2)
				{
					return "32757-01.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		if (npc.getNpcId() == _aquilani)
		{
			return st.isCompleted() ? "32780-01.html" : "32780-00.html";
		}
		return null;
	}
	
	public Q10288_SecretMission(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_dominic, _aquilani);
		addTalkId(_dominic, _greymore, _aquilani);
		addFirstTalkId(_aquilani);
	}
	
	public static void main(String[] args)
	{
		new Q10288_SecretMission(10288, qn, "Secret Mission");
	}
}
