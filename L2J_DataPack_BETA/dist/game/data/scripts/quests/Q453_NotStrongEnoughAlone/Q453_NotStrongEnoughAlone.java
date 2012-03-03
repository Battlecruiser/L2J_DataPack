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
package quests.Q453_NotStrongEnoughAlone;

import java.util.Calendar;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * Not Strong Enough Alone (453)
 * @author malyelfik
 */
public class Q453_NotStrongEnoughAlone extends Quest
{
	private static final String qn = "453_NotStrongEnoughAlone";
	// NPC
	private static final int Klemis = 32734;
	private static final int[] Monsters1 =
	{
		22746, 22747, 22748, 22749, 22750, 22751, 22752, 22753
	};
	private static final int[] Monsters2 =
	{
		22754, 22755, 22756, 22757, 22758, 22759
	};
	private static final int[] Monsters3 =
	{
		22760, 22761, 22762, 22763, 22764, 22765
	};
	
	// Reward
	private static final int[][] Reward =
	{
		{
			15815, 15816, 15817, 15818, 15819, 15820, 15821, 15822, 15823, 15824, 15825
		},
		{
			15634, 15635, 15636, 15637, 15638, 15639, 15640, 15641, 15642, 15643, 15644
		}
	};
	
	// Restart Time
	private static final int ResetHour = 6;
	private static final int ResetMin = 30;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("32734-06.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32734-07.html"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32734-08.html"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32734-09.html"))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		QuestState prev = player.getQuestState("10282_ToTheSeedOfAnnihilation");
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case State.CREATED:
				if ((player.getLevel() >= 84) && (prev != null) && prev.isCompleted())
					htmltext = "32734-01.htm";
				else
					htmltext = "32734-03.html";
				break;
			case State.STARTED:
				if (st.getInt("cond") == 1)
					htmltext = "32734-10.html";
				else if (st.getInt("cond") == 2)
					htmltext = "32734-11.html";
				else if (st.getInt("cond") == 3)
					htmltext = "32734-12.html";
				else if (st.getInt("cond") == 4)
					htmltext = "32734-13.html";
				else if (st.getInt("cond") == 5)
				{
					if (Rnd.nextBoolean())
						st.giveItems(Reward[0][getRandom(Reward[0].length)], 1);
					else
						st.giveItems(Reward[1][getRandom(Reward[1].length)], 1);
					st.playSound("ItemSound.quest_finish");
					htmltext = "32734-14.html";
					
					Calendar reset = Calendar.getInstance();
					reset.set(Calendar.MINUTE, ResetMin);
					if (reset.get(Calendar.HOUR_OF_DAY) >= ResetHour)
						reset.add(Calendar.DATE, 1);
					reset.set(Calendar.HOUR_OF_DAY, ResetHour);
					st.set("reset", String.valueOf(reset.getTimeInMillis()));
					st.exitQuest(false);
				}
				break;
			case State.COMPLETED:
				if (Long.parseLong(st.get("reset")) > System.currentTimeMillis())
					htmltext = "32734-02.htm";
				else
				{
					st.setState(State.CREATED);
					if (player.getLevel() >= 84 && prev != null && prev.getState() == State.COMPLETED)
						htmltext = "32734-01.htm";
					else
						htmltext = "32734-03.html";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if (player.getParty() != null)
		{
			for (L2PcInstance member : player.getParty().getPartyMembers())
			{
				increaseKill(member, npc);
			}
		}
		else
		{
			increaseKill(player, npc);
		}
		return null;
	}
	
	private static void increaseKill(L2PcInstance player, L2Npc npc)
	{
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return;
		
		int npcId = npc.getNpcId();
		
		if (Util.checkIfInRange(1500, npc, player, false))
		{
			if (Util.contains(Monsters1, npcId) && st.getInt("cond") == 2)
			{
				if (npcId == Monsters1[4])
					npcId = Monsters1[0];
				else if (npcId == Monsters1[5])
					npcId = Monsters1[1];
				else if (npcId == Monsters1[6])
					npcId = Monsters1[2];
				else if (npcId == Monsters1[7])
					npcId = Monsters1[3];
				
				int i = st.getInt(String.valueOf(npcId));
				if (i < 15)
				{
					st.set(String.valueOf(npcId), String.valueOf(i + 1));
					st.playSound("ItemSound.quest_itemget");
				}
				
				checkProgress(st, 15, Monsters1[0], Monsters1[1], Monsters1[2], Monsters1[3]);
			}
			else if (Util.contains(Monsters2, npcId) && st.getInt("cond") == 3)
			{
				if (npcId == Monsters2[3])
					npcId = Monsters2[0];
				else if (npcId == Monsters2[4])
					npcId = Monsters2[1];
				else if (npcId == Monsters2[5])
					npcId = Monsters2[2];
				
				int i = st.getInt(String.valueOf(npcId));
				if (i < 20)
				{
					st.set(String.valueOf(npcId), String.valueOf(i + 1));
					st.playSound("ItemSound.quest_itemget");
				}
				
				checkProgress(st, 20, Monsters2[0], Monsters2[1], Monsters2[2]);
			}
			else if (Util.contains(Monsters3, npcId) && st.getInt("cond") == 4)
			{
				if (npcId == Monsters3[3])
					npcId = Monsters3[0];
				else if (npcId == Monsters3[4])
					npcId = Monsters3[1];
				else if (npcId == Monsters3[5])
					npcId = Monsters3[2];
				
				int i = st.getInt(String.valueOf(npcId));
				if (i < 20)
				{
					st.set(String.valueOf(npcId), String.valueOf(i + 1));
					st.playSound("ItemSound.quest_itemget");
				}
				
				checkProgress(st, 20, Monsters3[0], Monsters3[1], Monsters3[2]);
			}
		}
	}
	
	private static void checkProgress(QuestState st, int count, int... mobs)
	{
		for (int mob : mobs)
		{
			if (st.getInt(String.valueOf(mob)) < count)
				return;
		}
		
		st.set("cond", "5");
		st.playSound("ItemSound.quest_middle");
	}
	
	public Q453_NotStrongEnoughAlone(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(Klemis);
		addTalkId(Klemis);
		
		for (int i : Monsters1)
		{
			addKillId(i);
		}
		for (int i : Monsters2)
		{
			addKillId(i);
		}
		for (int i : Monsters3)
		{
			addKillId(i);
		}
	}
	
	public static void main(String[] args)
	{
		new Q453_NotStrongEnoughAlone(453, qn, "Not Strong Enought Alone");
	}
}
