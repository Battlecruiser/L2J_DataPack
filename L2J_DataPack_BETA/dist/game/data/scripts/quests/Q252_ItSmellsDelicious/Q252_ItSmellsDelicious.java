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

import javolution.util.FastList;

import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * @author Dumpster Updated by corbin12 Thanks VLight for help.
 */
public class Q252_ItSmellsDelicious extends Quest
{
	public static final int STAN = 30200;
	public static final int MAHUM_DIARY = 15500;
	public static final int MAHUM_COOKBOOK = 15501;
	
	public static final String qn = "252_ItSmellsDelicious";
	
	private static final int[] MOBS =
	{
		22786,
		22787,
		22788
	};
	
	private static final int CHEF = 18908;
	
	public Q252_ItSmellsDelicious(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(STAN);
		addTalkId(STAN);
		addKillId(CHEF);
		for (final int i : MOBS)
		{
			addKillId(i);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == STAN)
		{
			if (event.equalsIgnoreCase("30200-05.htm"))
			{
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("30200-08.htm"))
			{
				st.takeItems(MAHUM_DIARY, -1);
				st.takeItems(MAHUM_COOKBOOK, -1);
				st.giveItems(57, 147656);
				st.addExpAndSp(716238, 78324);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
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
		
		if (npc.getNpcId() == STAN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() >= 82)
					{
						htmltext = "30200-01.htm";
					}
					else
					{
						htmltext = "30200-02.htm";
					}
					break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
					{
						htmltext = "30200-06.htm";
					}
					else if (st.getInt("cond") == 2)
					{
						if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOK) >= 5))
						{
							htmltext = "30200-07.htm";
						}
					}
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
		final int npcId = npc.getNpcId();
		QuestState st;
		if (Util.contains(MOBS, npcId) && (getRandom(1000) < 599))
		{
			st = getRandomPartyMemberQuestState(player);
			if (st != null)
			{
				st.giveItems(MAHUM_DIARY, 1);
				st.playSound("ItemSound.quest_itemget");
				
				if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOK) >= 5))
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
			}
		}
		else if (npcId == CHEF)
		{
			st = player.getQuestState(qn);
			if ((st != null) && st.isStarted() && (st.getInt("cond") == 1) && (st.getQuestItemsCount(MAHUM_COOKBOOK) < 5) && (getRandom(1000) < 360))
			{
				st.giveItems(MAHUM_COOKBOOK, 1);
				st.playSound("ItemSound.quest_itemget");
				
				if ((st.getQuestItemsCount(MAHUM_DIARY) >= 10) && (st.getQuestItemsCount(MAHUM_COOKBOOK) >= 5))
				{
					st.set("cond", "2");
					st.playSound("ItemSound.quest_middle");
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	private QuestState getRandomPartyMemberQuestState(L2PcInstance player)
	{
		if (player == null)
		{
			return null;
		}
		
		final L2Party party = player.getParty();
		QuestState st;
		
		if ((party == null) || party.getMembers().isEmpty())
		{
			st = player.getQuestState(qn);
			if ((st == null) || st.isStarted() || (st.getInt("cond") != 1) || (st.getQuestItemsCount(MAHUM_DIARY) >= 10))
			{
				return null;
			}
			return st;
		}
		
		final FastList<QuestState> candidates = new FastList<QuestState>();
		// get the target for enforcing distance limitations.
		L2Object target = player.getTarget();
		
		if (target == null)
		{
			target = player;
		}
		
		for (final L2PcInstance partyMember : party.getMembers())
		{
			if (partyMember.isDead() || !partyMember.isInsideRadius(target, 1500, true, false))
			{
				continue;
			}
			
			st = partyMember.getQuestState(qn);
			if ((st == null) || (st.getState() != State.STARTED) || (st.getInt("cond") != 1) || (st.getQuestItemsCount(MAHUM_DIARY) >= 10))
			{
				continue;
			}
			candidates.add(st);
		}
		return candidates.isEmpty() ? null : candidates.get(getRandom(candidates.size()));
	}
	
	public static void main(String[] args)
	{
		new Q252_ItSmellsDelicious(252, qn, "It Smells Delicious!");
	}
}
