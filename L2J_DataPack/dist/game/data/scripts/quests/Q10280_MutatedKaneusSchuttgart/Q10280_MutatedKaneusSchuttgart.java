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
package quests.Q10280_MutatedKaneusSchuttgart;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Mutated Kaneus - Schuttgart (10280). Original Jython script by Gnacik on 2010-06-29
 * @author nonom
 */
public class Q10280_MutatedKaneusSchuttgart extends Quest
{
	private static final String qn = "10280_MutatedKaneusSchuttgart";
	
	// NPCs
	private static final int VISHOTSKY = 31981;
	private static final int ATRAXIA = 31972;
	private static final int VENOMOUS_STORACE = 18571;
	private static final int KEL_BILETTE = 18573;
	
	// Items
	private static final int TISSUE_VS = 13838;
	private static final int TISSUE_KB = 13839;
	
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
			case VISHOTSKY:
				if (st.isCompleted())
				{
					htmltext = "31981-06.htm";
				}
				else if (st.isCreated() && (player.getLevel() >= 58))
				{
					htmltext = "31981-01.htm";
				}
				else if (st.isCreated() && (player.getLevel() < 58))
				{
					htmltext = "31981-00.htm";
				}
				else if ((st.getQuestItemsCount(TISSUE_VS) > 0) && (st.getQuestItemsCount(TISSUE_KB) > 0))
				{
					htmltext = "31981-05.htm";
				}
				else if (st.getInt("cond") == 1)
				{
					htmltext = "31981-04.htm";
				}
				break;
			case ATRAXIA:
				if (st.isCompleted())
				{
					htmltext = Quest.getAlreadyCompletedMsg(player);
				}
				else if ((st.getQuestItemsCount(TISSUE_VS) > 0) && (st.getQuestItemsCount(TISSUE_KB) > 0))
				{
					htmltext = "31972-02.htm";
				}
				else
				{
					htmltext = "31972-01.htm";
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
			case "31981-03.htm":
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				break;
			case "31972-03.htm":
				st.rewardItems(57, 210000);
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
			FastList<QuestState> PartyMembers = new FastList<>();
			
			for (L2PcInstance member : killer.getParty().getPartyMembers())
			{
				st = member.getQuestState(qn);
				if ((st != null) && st.isStarted() && (st.getInt("cond") == 1))
				{
					if ((npc.getNpcId() == VENOMOUS_STORACE) && (st.getQuestItemsCount(TISSUE_VS) == 0))
					{
						PartyMembers.add(st);
					}
					else if ((npc.getNpcId() == KEL_BILETTE) && (st.getQuestItemsCount(TISSUE_KB) == 0))
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
			
			if ((npc.getNpcId() == VENOMOUS_STORACE) && (winnerst.getQuestItemsCount(TISSUE_VS) == 0))
			{
				winnerst.giveItems(TISSUE_VS, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == KEL_BILETTE) && (winnerst.getQuestItemsCount(TISSUE_KB) == 0))
			{
				winnerst.giveItems(TISSUE_KB, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
			
		}
		else
		{
			
			if ((npc.getNpcId() == VENOMOUS_STORACE) && (st.getQuestItemsCount(TISSUE_VS) == 0))
			{
				st.giveItems(TISSUE_VS, 1);
				st.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == KEL_BILETTE) && (st.getQuestItemsCount(TISSUE_KB) == 0))
			{
				st.giveItems(TISSUE_KB, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		
		return null;
	}
	
	public Q10280_MutatedKaneusSchuttgart(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(VISHOTSKY);
		addTalkId(VISHOTSKY);
		addTalkId(ATRAXIA);
		
		addKillId(VENOMOUS_STORACE);
		addKillId(KEL_BILETTE);
		
		questItemIds = new int[]
		{
			TISSUE_VS, TISSUE_KB
		};
	}
	
	public static void main(String[] args)
	{
		new Q10280_MutatedKaneusSchuttgart(10280, qn, "Mutated Kaneus - Schuttgart");
	}
}
