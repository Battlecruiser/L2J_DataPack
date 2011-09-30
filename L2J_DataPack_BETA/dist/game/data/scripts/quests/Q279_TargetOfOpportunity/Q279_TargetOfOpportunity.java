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
package quests.Q279_TargetOfOpportunity;

import java.util.Arrays;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * @author GKR
 */
public final class Q279_TargetOfOpportunity extends Quest
{
	private static final String qn = "279_TargetOfOpportunity";
	
	// NPC's
	private static final int JERIAN = 32302;
	private static final int[] MONSTERS =
	{
		22373, 22374, 22375, 22376
	};
	
	// Items
	private static final int[] SEAL_COMPONENTS =
	{
		15517, 15518, 15519, 15520
	};
	private static final int[] SEAL_BREAKERS =
	{
		15515, 15516
	};
	
	public Q279_TargetOfOpportunity(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(JERIAN);
		addTalkId(JERIAN);
		
		for (int monster : MONSTERS)
		{
			addKillId(monster);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		
		if ((st == null) || (player.getLevel() < 82))
		{
			return getNoQuestMsg(player);
		}
		
		if (event.equalsIgnoreCase("32302-05.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.set("progress", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32302-08.htm") && (st.getInt("progress") == 1) && st.hasQuestItems(SEAL_COMPONENTS[0]) && st.hasQuestItems(SEAL_COMPONENTS[1]) && st.hasQuestItems(SEAL_COMPONENTS[2]) && st.hasQuestItems(SEAL_COMPONENTS[3]))
		{
			st.takeItems(SEAL_COMPONENTS[0], -1);
			st.takeItems(SEAL_COMPONENTS[1], -1);
			st.takeItems(SEAL_COMPONENTS[2], -1);
			st.takeItems(SEAL_COMPONENTS[3], -1);
			st.giveItems(SEAL_BREAKERS[0], 1);
			st.giveItems(SEAL_BREAKERS[1], 1);
			st.playSound("IItemSound.quest_finish");
			st.exitQuest(true);
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st != null)
		{
			if (st.getState() == State.CREATED)
			{
				if (player.getLevel() >= 82)
				{
					htmltext = "32302-01.htm";
				}
				else
				{
					htmltext = "32302-02.htm";
				}
			}
			else if (st.getState() == State.STARTED)
			{
				if (st.getInt("progress") == 1)
				{
					if (st.hasQuestItems(SEAL_COMPONENTS[0]) && st.hasQuestItems(SEAL_COMPONENTS[1]) && st.hasQuestItems(SEAL_COMPONENTS[2]) && st.hasQuestItems(SEAL_COMPONENTS[3]))
					{
						htmltext = "32302-07.htm";
					}
					else
					{
						htmltext = "32302-06.htm";
					}
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance pl = getRandomPartyMember(player, "progress", "1");
		final int idx = Arrays.binarySearch(MONSTERS, npc.getNpcId());
		if ((pl == null) || (idx < 0))
		{
			return null;
		}
		
		final QuestState st = pl.getQuestState(qn);
		if (Rnd.get(1000) < (int) (311 * Config.RATE_QUEST_DROP))
		{
			if (st.getQuestItemsCount(SEAL_COMPONENTS[idx]) < 1)
			{
				st.giveItems(SEAL_COMPONENTS[idx], 1);
				if (haveAllExceptThis(st, idx))
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
				else
				{
					st.playSound("ItemSound.quest_itemget");
				}
			}
		}
		return null;
	}
	
	private static final boolean haveAllExceptThis(QuestState st, int idx)
	{
		for (int i = 0; i < SEAL_COMPONENTS.length; i++)
		{
			if (i == idx)
			{
				continue;
			}
			
			if (st.getQuestItemsCount(SEAL_COMPONENTS[i]) < 1)
			{
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		new Q279_TargetOfOpportunity(279, qn, "Target of Opportunity");
	}
}
