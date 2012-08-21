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
package quests.Q00020_BringUpWithLove;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Bring Up With Love (20)
 * @author Gnacik
 * @version 2010-09-29 Based on official server Franz
 */
public class Q00020_BringUpWithLove extends Quest
{
	
	// Npc
	private static final int _tunatun = 31537;
	// Item
	private static final int _beast_whip = 15473;
	private static final int _crystal = 9553;
	private static final int _jewel = 7185;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == _tunatun)
		{
			if (event.equalsIgnoreCase("31537-12.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("31537-03.htm"))
			{
				if (st.hasQuestItems(_beast_whip))
				{
					return "31537-03a.htm";
				}
				st.giveItems(_beast_whip, 1);
			}
			else if (event.equalsIgnoreCase("31537-15.htm"))
			{
				st.takeItems(_jewel, -1);
				st.giveItems(_crystal, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
			}
			else if (event.equalsIgnoreCase("31537-21.html"))
			{
				if (player.getLevel() < 82)
				{
					return "31537-23.html";
				}
				if (st.hasQuestItems(_beast_whip))
				{
					return "31537-22.html";
				}
				st.giveItems(_beast_whip, 1);
			}
		}
		return htmltext;
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
		
		if (npc.getNpcId() == _tunatun)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82)
					{
						htmltext = "31537-01.htm";
					}
					else
					{
						htmltext = "31537-00.htm";
					}
					break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
					{
						htmltext = "31537-13.htm";
					}
					else if (st.getInt("cond") == 2)
					{
						htmltext = "31537-14.htm";
					}
					break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			newQuestState(player);
		}
		return "31537-20.html";
	}
	
	public Q00020_BringUpWithLove(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_tunatun);
		addTalkId(_tunatun);
		addFirstTalkId(_tunatun);
	}
	
	public static void main(String[] args)
	{
		new Q00020_BringUpWithLove(20, Q00020_BringUpWithLove.class.getSimpleName(), "Bring Up With Love");
	}
}
