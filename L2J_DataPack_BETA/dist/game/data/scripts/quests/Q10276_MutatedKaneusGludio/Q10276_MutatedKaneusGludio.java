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
package quests.Q10276_MutatedKaneusGludio;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Mutated Kaneus - Gludio (10276). Original Jython script by Gnacik on 2010-06-29
 * @author nonom
 */
public class Q10276_MutatedKaneusGludio extends Quest
{
	private static final String qn = "10276_MutatedKaneusGludio";
	
	// NPCs
	private static final int BATHIS = 30332;
	private static final int ROHMER = 30344;
	private static final int TOMLAN_KAMOS = 18554;
	private static final int OL_ARIOSH = 18555;
	
	// Items
	private static final int TISSUE_TK = 13830;
	private static final int TISSUE_OA = 13831;
	
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
			case BATHIS:
				if (st.isCompleted())
				{
					htmltext = "30332-06.htm";
				}
				else if (st.isCreated() && (player.getLevel() >= 18))
				{
					htmltext = "30332-01.htm";
				}
				else if (st.isCreated() && (player.getLevel() < 18))
				{
					htmltext = "30332-00.htm";
				}
				else if ((st.getQuestItemsCount(TISSUE_TK) > 0) && (st.getQuestItemsCount(TISSUE_OA) > 0))
				{
					htmltext = "30332-05.htm";
				}
				else if (st.getInt("cond") == 1)
				{
					htmltext = "30332-04.htm";
				}
				break;
			case ROHMER:
				if (st.isCompleted())
				{
					htmltext = Quest.getAlreadyCompletedMsg(player);
				}
				else if ((st.getQuestItemsCount(TISSUE_TK) > 0) && (st.getQuestItemsCount(TISSUE_OA) > 0))
				{
					htmltext = "30344-02.htm";
				}
				else
				{
					htmltext = "30344-01.htm";
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
			case "30332-03.htm":
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				break;
			case "30344-03.htm":
				st.rewardItems(57, 8500);
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
					if ((npc.getNpcId() == TOMLAN_KAMOS) && (st.getQuestItemsCount(TISSUE_TK) == 0))
					{
						PartyMembers.add(st);
					}
					else if ((npc.getNpcId() == TISSUE_OA) && (st.getQuestItemsCount(TISSUE_OA) == 0))
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
			
			if ((npc.getNpcId() == TOMLAN_KAMOS) && (winnerst.getQuestItemsCount(TISSUE_TK) == 0))
			{
				winnerst.giveItems(TISSUE_TK, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == OL_ARIOSH) && (winnerst.getQuestItemsCount(TISSUE_OA) == 0))
			{
				winnerst.giveItems(TISSUE_OA, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
		}
		else
		{
			if ((npc.getNpcId() == TOMLAN_KAMOS) && (st.getQuestItemsCount(TISSUE_TK) == 0))
			{
				st.giveItems(TISSUE_TK, 1);
				st.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == OL_ARIOSH) && (st.getQuestItemsCount(TISSUE_OA) == 0))
			{
				st.giveItems(TISSUE_OA, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public Q10276_MutatedKaneusGludio(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(BATHIS);
		addTalkId(BATHIS);
		addTalkId(ROHMER);
		
		addKillId(TOMLAN_KAMOS);
		addKillId(OL_ARIOSH);
		
		questItemIds = new int[]
		{
			TISSUE_TK, TISSUE_OA
		};
	}
	
	public static void main(String[] args)
	{
		new Q10276_MutatedKaneusGludio(10276, qn, "Mutated Kaneus - Gludio");
	}
}
