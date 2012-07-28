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
package quests.Q00019_GoToThePastureland;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Go to the Pastureland (19)
 * @author disKret, malyelfik
 */
public class Q00019_GoToThePastureland extends Quest
{
	private static final String qn = "19_GoToThePastureland";
	
	// NPC
	private static final int Vladimir = 31302;
	private static final int Tunatun = 31537;
	// Items
	private static final int Veal = 15532;
	private static final int YoungWildBeastMeat = 7547;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equalsIgnoreCase("31302-02.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
			st.giveItems(Veal, 1);
		}
		else if (event.equalsIgnoreCase("31537-02.html"))
		{
			if (st.hasQuestItems(YoungWildBeastMeat))
			{
				st.takeItems(YoungWildBeastMeat, -1);
				st.giveAdena(50000, true);
				st.addExpAndSp(136766, 12688);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				htmltext = "31537-02.html";
			}
			else if (st.hasQuestItems(Veal))
			{
				st.takeItems(Veal, -1);
				st.giveAdena(147200, true);
				st.addExpAndSp(385040, 75250);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				htmltext = "31537-02.html";
			}
			else
			{
				htmltext = "31537-03.html";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == Vladimir)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82)
					{
						htmltext = "31302-01.htm";
					}
					else
					{
						htmltext = "31302-03.html";
					}
					break;
				case State.STARTED:
					htmltext = "31302-04.html";
					break;
				case State.COMPLETED:
					htmltext = getAlreadyCompletedMsg(player);
					break;
			}
		}
		else if ((npc.getNpcId() == Tunatun) && (st.getInt("cond") == 1))
		{
			htmltext = "31537-01.html";
		}
		return htmltext;
	}
	
	public Q00019_GoToThePastureland(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Vladimir);
		addTalkId(Vladimir, Tunatun);
		
		questItemIds = new int[]
		{
			Veal,
			YoungWildBeastMeat
		};
	}
	
	public static void main(String[] args)
	{
		new Q00019_GoToThePastureland(19, qn, "Go to the Pastureland");
	}
}
