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
package quests.Q290_ThreatRemoval;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

public class Q290_ThreatRemoval extends Quest
{
	public static final int PINAPS		= 30201;
	public static final int TAGS		= 15714;

	public static final String qn = "290_ThreatRemoval";
	
	private static final int[] MOBS1 =
	{
		22780, 22782, 22784
	};
	private static final int[] MOBS2 =
	{
		22781, 22783, 22785
	};
	private static final int[] MOBS3 =
	{
		22776, 22775, 22777, 22778
	};

	private static final int[][][] REWARD =
	{
		{{959, 1}}, 
		{{960, 1}, {960, 2}, {960, 3}}, 
		{{9552, 1}, {9552, 2}}
	};
	
	public Q290_ThreatRemoval(int id, String name, String descr)
	{
		super(id,name,descr);
		
		addStartNpc(PINAPS);
		addTalkId(PINAPS);

		for (int i : MOBS1)
			addKillId(i);	
		for (int i : MOBS2)
			addKillId(i);
		for (int i : MOBS3)
			addKillId(i);
	}
	
	@Override
	public String onAdvEvent (String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		int[][] i = REWARD[(int) Rnd.get(REWARD.length)];
		int b = Rnd.get(i.length);

		if (npc.getNpcId() == PINAPS)
		{
			if (event.equalsIgnoreCase("30201-02.htm"))
			{
				st.set("cond","1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("30201-05.htm"))
			{
				if (st.getQuestItemsCount(TAGS) >= 400)
				{
					st.giveItems(i[b][0],i[b][1]);
					st.takeItems(TAGS, 400);
					st.playSound("ItemSound.quest_finish");
					htmltext = "30201-05.htm";
				}
				else
					htmltext = "30201-03.htm";
			}
			else if (event.equalsIgnoreCase("30201-08.htm"))
			{
				st.unset("cond");
				st.exitQuest(true);
				st.playSound("ItemSound.quest_finish");
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc,L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		QuestState _prev = player.getQuestState("251_NoSecrets");
		if (st == null)
			return htmltext;
		
		if(npc.getNpcId() == PINAPS)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82 && (_prev != null) && _prev.isCompleted())
						htmltext = "30201-01.htm";
					else
						htmltext = "30201-00.htm";
				break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
					{
						if (st.getQuestItemsCount(TAGS) >= 400)
							htmltext = "30201-04.htm";
						else
							htmltext = "30201-03.htm";
					}
				break;
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
		if (Util.contains(MOBS1, npcId))
		{
			int chance = (int) (25 * Config.RATE_QUEST_DROP);
			int numItems = (int) (chance / 100);
			chance = chance % 100;
			if (st.getRandom(100) < chance)
				numItems++;
			if (numItems > 0)
			{
				st.playSound("ItemSound.quest_itemget");
				st.giveItems(TAGS, numItems);
			}
		}
		else if (Util.contains(MOBS2, npcId))
		{
			int chance = (int) (30 * Config.RATE_QUEST_DROP);
			int numItems = (int) (chance / 100);
			chance = chance % 100;
			if (st.getRandom(100) < chance)
				numItems++;
			if (numItems > 0)
			{
				st.playSound("ItemSound.quest_itemget");
				st.giveItems(TAGS, numItems);
			}
		}
		else if (Util.contains(MOBS3, npcId))
		{
			int chance = (int) (50 * Config.RATE_QUEST_DROP);
			int numItems = (int) (chance / 100);
			chance = chance % 100;
			if (st.getRandom(100) < chance)
				numItems++;
			if (numItems > 0)
			{
				st.playSound("ItemSound.quest_itemget");
				st.giveItems(TAGS, numItems);
			}
		}
		return super.onKill(npc, player, isPet);
	}

	public static void main(String[] args)
	{
		new Q290_ThreatRemoval(290, qn, "Threat Removal");
	}
}
