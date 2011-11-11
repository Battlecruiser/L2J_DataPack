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
package quests.Q252_ItSmellsDelicious;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

public class Q252_ItSmellsDelicious extends Quest
{
	public static final int STAN		= 30200;
	public static final int MAHUM_DIARY		= 15500; 
	public static final int MAHUM_COOKBOOKP		= 15501; 

	public static final String qn = "252_ItSmellsDelicious";
	
	private static final int[] MOBS =
	{22786,22787,22788};

	private static final int CHIEF = 18908;
	
	public Q252_ItSmellsDelicious(int id, String name, String descr)
	{
		super(id,name,descr);
		
		addStartNpc(STAN);
		addTalkId(STAN);
		addKillId(CHIEF);
		for (int i : MOBS)
			addKillId(i);
	}
	
	@Override
	public String onAdvEvent (String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;

		if (npc.getNpcId() == STAN)
		{
			if (event.equalsIgnoreCase("30200-05.htm"))
			{
				st.set("cond","1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("30200-08.htm"))
			{
				st.takeItems(MAHUM_DIARY, -1);
				st.takeItems(MAHUM_COOKBOOKP, -1);
				st.giveAdena(313355, true);
				st.addExpAndSp(56787, 160578);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				st.setState(State.COMPLETED);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc,L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if(npc.getNpcId() == STAN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82)
						htmltext = "30200-01.htm";
					else
						htmltext = "30200-02.htm";
				break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
						htmltext = "30200-06.htm";
					else if (st.getInt("cond") == 2)
						if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOKP) >= 5))
							htmltext = "30200-07.htm";
				break;
				case State.COMPLETED:
					htmltext = "30200-03.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet) 
	{
		QuestState st = player.getQuestState(getName());
		int npcId = npc.getNpcId();
		if (st == null || st.getState() != State.STARTED)
			return null;
		if (st.getInt("cond") == 1)
		{
			if ((Util.contains(MOBS, npcId)) && (Rnd.get(100) < 10) && (st.getQuestItemsCount(MAHUM_DIARY) < 10))
			{
					st.giveItems(MAHUM_DIARY, 1);
					st.playSound("ItemSound.quest_itemget");
					if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOKP) >= 5))
					{
						st.set("cond", "2");
						st.playSound("ItemSound.quest_itemget");
					}
			}
			else if ((npcId == CHIEF) && (Rnd.get(100) < 5) && (st.getQuestItemsCount(MAHUM_COOKBOOKP) < 5))
			{
				st.giveItems(MAHUM_COOKBOOKP, 1);
				st.playSound("ItemSound.quest_itemget");
				if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOKP) >= 5))
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_itemget");
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}

	public static void main(String[] args)
	{
		new Q252_ItSmellsDelicious(252, qn, "It Smells Delicious");
	}
}