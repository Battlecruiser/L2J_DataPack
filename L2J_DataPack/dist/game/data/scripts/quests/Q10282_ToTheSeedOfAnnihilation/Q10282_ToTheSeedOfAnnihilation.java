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
package quests.Q10282_ToTheSeedOfAnnihilation;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * To the Seed of Destruction (10269). Original jython script by Gnacik 2010-08-13 Based on Freya PTS
 * @author nonom
 */
public class Q10282_ToTheSeedOfAnnihilation extends Quest
{
	private static final String qn = "10282_ToTheSeedOfAnnihilation";
	
	// NPCs
	private static final int KBALDIR = 32733;
	private static final int KLEMIS = 32734;
	
	// Items
	private static final int SOA_ORDERS = 15512;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		int npcId = npc.getNpcId();
		
		if (st == null)
		{
			return htmltext;
		}
		
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.COMPLETED:
				if (npcId == KBALDIR)
				{
					htmltext = "32733-09.htm";
				}
				else if (npcId == KLEMIS)
				{
					htmltext = "32734-03.htm";
				}
				break;
			case State.CREATED:
				if (player.getLevel() < 84)
				{
					htmltext = "32733-01.htm";
				}
				else
				{
					htmltext = "32733-00.htm";
				}
				break;
			case State.STARTED:
				if (cond == 1)
				{
					if (npcId == KBALDIR)
					{
						htmltext = "32733-08.htm";
					}
					else if (npcId == KLEMIS)
					{
						htmltext = "32734-01.htm";
					}
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "32733-07.htm":
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.giveItems(SOA_ORDERS, 1);
				st.playSound("ItemSound.quest_accept");
				break;
			case "32734-02.htm":
				st.addExpAndSp(1148480, 99110);
				st.takeItems(SOA_ORDERS, -1);
				st.exitQuest(false);
				break;
		}
		return htmltext;
	}
	
	public Q10282_ToTheSeedOfAnnihilation(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(KBALDIR);
		addTalkId(KBALDIR);
		addTalkId(KLEMIS);
	}
	
	public static void main(String[] args)
	{
		new Q10282_ToTheSeedOfAnnihilation(10282, qn, "To the Seed of Annihilation");
	}
}