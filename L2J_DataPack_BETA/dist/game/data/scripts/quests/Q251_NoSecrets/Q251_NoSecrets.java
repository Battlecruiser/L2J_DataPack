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
package quests.Q251_NoSecrets;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

public class Q251_NoSecrets extends Quest
{
	public static final int PINAPS		= 30201;
	public static final int DIARY		= 15508; 
	public static final int TABLE		= 15509;

	public static final String qn = "251_NoSecrets";
	
	private static final int[] MOBS =
	{22783, 22785, 22780, 22782, 22784};

	private static final int[] MOBS2 =
	{22775, 22776, 22778};
	
	public Q251_NoSecrets(int id, String name, String descr)
	{
		super(id,name,descr);
		
		addStartNpc(PINAPS);
		addTalkId(PINAPS);

		for (int i : MOBS)
			addKillId(i);
		for (int i : MOBS2)
			addKillId(i);	
	}
	
	@Override
	public String onAdvEvent (String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;

		if (npc.getNpcId() == PINAPS)
		{
			if (event.equalsIgnoreCase("30201-03.htm"))
			{
				st.set("cond","1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
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
		
		if(npc.getNpcId() == PINAPS)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82)
						htmltext = "30201-01.htm";
					else
						htmltext = "30201-00.htm";
				break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
					{
						htmltext = "30201-05.htm";
					}
					else if (st.getInt("cond") == 2)
					{
						if ((st.getQuestItemsCount(DIARY) >= 10) && (st.getQuestItemsCount(TABLE) >= 5))
						{
							htmltext = "30201-04.htm";
							st.takeItems(DIARY, -1);
							st.takeItems(TABLE, -1);
							st.giveAdena(313355, true);
							st.addExpAndSp(56787, 160578);
							st.playSound("ItemSound.quest_finish");
							st.exitQuest(false);
							st.setState(State.COMPLETED);
						}
					}
				break;
				case State.COMPLETED:
					htmltext = "30201-06.htm";
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
			if ((Util.contains(MOBS, npcId)) && (Rnd.get(100) < 10) && (st.getQuestItemsCount(DIARY) < 10))
			{
				st.giveItems(DIARY, 1);
				st.playSound("ItemSound.quest_itemget");
				if ((st.getQuestItemsCount(DIARY) >= 10) && (st.getQuestItemsCount(TABLE) >= 5))
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_itemget");
				}
			}
			else if ((Util.contains(MOBS2, npcId)) && (Rnd.get(100) < 5) &&(st.getQuestItemsCount(TABLE) < 5))
			{
				st.giveItems(TABLE, 1);
				st.playSound("ItemSound.quest_itemget");
				if ((st.getQuestItemsCount(DIARY) >= 10) && (st.getQuestItemsCount(TABLE) >= 5))
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
		new Q251_NoSecrets(251, qn, "No Secrets");
	}
}