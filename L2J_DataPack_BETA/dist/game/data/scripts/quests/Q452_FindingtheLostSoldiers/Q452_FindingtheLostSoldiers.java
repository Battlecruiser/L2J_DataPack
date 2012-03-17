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
package quests.Q452_FindingtheLostSoldiers;

import java.util.Calendar;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 ** @author Gigiikun 2010-08-17 Based on Freya PTS
 */
public class Q452_FindingtheLostSoldiers extends Quest
{
	private static final String qn = "452_FindingtheLostSoldiers";
	private static final int JAKAN = 32773;
	private static final int TAG_ID = 15513;
	private static final int[] SOLDIER_CORPSES =
	{
		32769,
		32770,
		32771,
		32772
	};
	
	/*
	 * Reset time for Quest Default: 6:30AM on server time
	 */
	private static final int RESET_HOUR = 6;
	private static final int RESET_MIN = 30;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == JAKAN)
		{
			if (event.equalsIgnoreCase("32773-3.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
		}
		else if (Util.contains(SOLDIER_CORPSES, npc.getNpcId()))
		{
			if (st.getInt("cond") == 1)
			{
				st.giveItems(TAG_ID, 1);
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
				npc.deleteMe();
			}
			else
			{
				htmltext = getNoQuestMsg(player);
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
		
		if (npc.getNpcId() == JAKAN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 84)
					{
						htmltext = "32773-1.htm";
					}
					else
					{
						htmltext = "32773-0.htm";
					}
					break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
					{
						htmltext = "32773-4.htm";
					}
					else if (st.getInt("cond") == 2)
					{
						htmltext = "32773-5.htm";
						st.unset("cond");
						st.takeItems(TAG_ID, 1);
						st.giveItems(57, 95200);
						st.addExpAndSp(435024, 50366);
						st.playSound("ItemSound.quest_finish");
						
						Calendar reDo = Calendar.getInstance();
						reDo.set(Calendar.MINUTE, RESET_MIN);
						if (reDo.get(Calendar.HOUR_OF_DAY) >= RESET_HOUR)
						{
							reDo.add(Calendar.DATE, 1);
						}
						reDo.set(Calendar.HOUR_OF_DAY, RESET_HOUR);
						st.set("reDoTime", String.valueOf(reDo.getTimeInMillis()));
						st.exitQuest(false);
					}
					break;
				case State.COMPLETED:
					Long reDoTime = Long.parseLong(st.get("reDoTime"));
					if (reDoTime > System.currentTimeMillis())
					{
						htmltext = "32773-6.htm";
					}
					else
					{
						st.setState(State.CREATED);
						if (player.getLevel() >= 84)
						{
							htmltext = "32773-1.htm";
						}
						else
						{
							htmltext = "32773-0.htm";
						}
					}
					break;
			}
		}
		else if (Util.contains(SOLDIER_CORPSES, npc.getNpcId()))
		{
			if (st.getInt("cond") == 1)
			{
				htmltext = "corpse-1.htm";
			}
		}
		return htmltext;
	}
	
	public Q452_FindingtheLostSoldiers(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		questItemIds = new int[]
		{
			TAG_ID
		};
		addStartNpc(JAKAN);
		addTalkId(JAKAN);
		for (int i : SOLDIER_CORPSES)
		{
			addTalkId(i);
		}
	}
	
	public static void main(String[] args)
	{
		new Q452_FindingtheLostSoldiers(452, qn, "Finding the Lost Soldiers");
	}
}