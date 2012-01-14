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
package quests.Q10273_GoodDayToFly;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Good Day to Fly (10273). Original Jython script by Kerberos v1.0 on 2009/04/25
 * @author nonom
 */
public class Q10273_GoodDayToFly extends Quest
{
	private static final String qn = "10273_GoodDayToFly";
	
	// NPCs
	private static final int LEKON = 32557;
	
	private static final int[] MOBS =
	{
		22614, 22615
	};
	
	// Items
	private static final int MARK = 13856;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		int transform = st.getInt("transform");
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = "32557-0a.htm";
				break;
			case State.CREATED:
				if (player.getLevel() < 75)
				{
					htmltext = "32557-00.htm";
				}
				else
				{
					htmltext = "32557-01.htm";
				}
				break;
			default:
				if (st.getQuestItemsCount(MARK) >= 5)
				{
					htmltext = "32557-14.htm";
					if (transform == 1)
					{
						st.giveItems(13553, 1);
					}
					else if (transform == 2)
					{
						st.giveItems(13554, 1);
					}
					st.giveItems(13857, 1);
					st.addExpAndSp(25160, 2525);
					st.exitQuest(false);
					st.playSound("ItemSound.quest_finish");
				}
				else if (transform == 0)
				{
					htmltext = "32557-07.htm";
				}
				else
				{
					htmltext = "32557-11.htm";
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
			case "32557-06.htm":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				break;
			case "32557-09.htm":
				st.set("transform", "1");
				SkillTable.getInstance().getInfo(5982, 1).getEffects(player, player);
				break;
			case "32557-10.htm":
				st.set("transform", "2");
				SkillTable.getInstance().getInfo(5983, 1).getEffects(player, player);
				break;
			case "32557-13.htm":
				if (st.getInt("transform") == 1)
				{
					SkillTable.getInstance().getInfo(5982, 1).getEffects(player, player);
				}
				else if (st.getInt("transform") == 2)
				{
					SkillTable.getInstance().getInfo(5983, 1).getEffects(player, player);
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		QuestState st = killer.getQuestState(qn);
		
		if (st == null)
		{
			return null;
		}
		
		if (!st.isStarted())
		{
			return null;
		}
		
		final long count = st.getQuestItemsCount(MARK);
		
		if ((st.getInt("cond") == 1) && (count < 5))
		{
			st.giveItems(MARK, 1);
			if (count == 4)
			{
				st.playSound("ItemSound.quest_middle");
				st.set("cond", "2");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public Q10273_GoodDayToFly(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(LEKON);
		addTalkId(LEKON);
		for (int i : MOBS)
		{
			addKillId(i);
		}
		questItemIds = new int[]
		{
			MARK
		};
	}
	
	public static void main(String[] args)
	{
		new Q10273_GoodDayToFly(10273, qn, "Good Day to Fly");
	}
}
