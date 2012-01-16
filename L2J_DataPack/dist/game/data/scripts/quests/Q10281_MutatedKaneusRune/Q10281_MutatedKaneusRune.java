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
package quests.Q10281_MutatedKaneusRune;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Mutated Kaneus - Rune (10281). Original Jython script by Gnacik on 2010-06-29
 * @author nonom
 */
public class Q10281_MutatedKaneusRune extends Quest
{
	private static final String qn = "10281_MutatedKaneusRune";
	
	// NPCs
	private static final int MATHIAS = 31340;
	private static final int KAYAN = 31335;
	private static final int WHITE_ALLOSCE = 18577;
	
	// Items
	private static final int TISSUE_WA = 13840;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case MATHIAS:
				if (st.isCompleted())
				{
					htmltext = "31340-06.htm";
				}
				else if (st.isCreated() && (player.getLevel() >= 68))
				{
					htmltext = "31340-01.htm";
				}
				else if (st.isCreated() && (player.getLevel() < 68))
				{
					htmltext = "31340-00.htm";
				}
				else if (st.getQuestItemsCount(TISSUE_WA) > 0)
				{
					htmltext = "31340-05.htm";
				}
				else if (st.getInt("cond") == 1)
				{
					htmltext = "31340-04.htm";
				}
				break;
			case KAYAN:
				if (st.isCompleted())
				{
					htmltext = Quest.getAlreadyCompletedMsg(player);
				}
				else if (st.getQuestItemsCount(TISSUE_WA) > 0)
				{
					htmltext = "31335-02.htm";
				}
				else
				{
					htmltext = "31335-01.htm";
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
			case "31340-03.htm":
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				break;
			case "31335-03.htm":
				st.rewardItems(57, 360000);
				st.exitQuest(false);
				st.playSound("ItemSound.quest_finish");
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
		
		if (killer.getParty() != null)
		{
			FastList<QuestState> PartyMembers = new FastList<QuestState>();
			
			for (L2PcInstance member : killer.getParty().getPartyMembers())
			{
				st = member.getQuestState(qn);
				if ((st != null) && st.isStarted() && (st.getInt("cond") == 1))
				{
					if ((npc.getNpcId() == WHITE_ALLOSCE) && (st.getQuestItemsCount(TISSUE_WA) == 0))
					{
						PartyMembers.add(st);
					}
				}
			}
			
			if (PartyMembers.isEmpty())
			{
				return null;
			}
			
			QuestState winnerst = PartyMembers.get(Rnd.get(PartyMembers.size()));
			
			if ((npc.getNpcId() == WHITE_ALLOSCE) && (winnerst.getQuestItemsCount(TISSUE_WA) == 0))
			{
				winnerst.giveItems(TISSUE_WA, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
		}
		else
		{
			if ((npc.getNpcId() == WHITE_ALLOSCE) && (st.getQuestItemsCount(TISSUE_WA) == 0))
			{
				st.giveItems(TISSUE_WA, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public Q10281_MutatedKaneusRune(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MATHIAS);
		addTalkId(MATHIAS);
		addTalkId(KAYAN);
		
		addKillId(WHITE_ALLOSCE);
		
		questItemIds = new int[]
		{
			TISSUE_WA
		};
	}
	
	public static void main(String[] args)
	{
		new Q10281_MutatedKaneusRune(10281, qn, "Mutated Kaneus - Rune");
	}
}