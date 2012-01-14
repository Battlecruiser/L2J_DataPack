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
package quests.Q10277_MutatedKaneusDion;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Mutated Kaneus - Dion (10277). Original Jython script by Gnacik on 2010-06-29
 * @author nonom
 */
public class Q10277_MutatedKaneusDion extends Quest
{
	private static final String qn = "10277_MutatedKaneusDion";
	
	// NPCs
	private static final int LUKAS = 30071;
	private static final int MIRIEN = 30461;
	private static final int CRIMSON_HATU = 18558;
	private static final int SEER_FLOUROS = 18559;
	
	// Items
	private static final int TISSUE_CH = 13832;
	private static final int TISSUE_SF = 13833;
	
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
			case LUKAS:
				if (st.isCompleted())
				{
					htmltext = "30071-06.htm";
				}
				else if (st.isCreated() && (player.getLevel() >= 28))
				{
					htmltext = "30071-01.htm";
				}
				else if (st.isCreated() && (player.getLevel() < 28))
				{
					htmltext = "30371-00.htm";
				}
				else if ((st.getQuestItemsCount(TISSUE_CH) > 0) && (st.getQuestItemsCount(TISSUE_SF) > 0))
				{
					htmltext = "30371-05.htm";
				}
				else if (st.getInt("cond") == 1)
				{
					htmltext = "30371-04.htm";
				}
				break;
			case MIRIEN:
				if (st.isCompleted())
				{
					htmltext = Quest.getAlreadyCompletedMsg(player);
				}
				else if ((st.getQuestItemsCount(TISSUE_CH) > 0) && (st.getQuestItemsCount(TISSUE_SF) > 0))
				{
					htmltext = "30461-02.htm";
				}
				else
				{
					htmltext = "30461-01.htm";
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
			case "30071-03.htm":
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				break;
			case "30461-03.htm":
				st.rewardItems(57, 20000);
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
					if ((npc.getNpcId() == CRIMSON_HATU) && (st.getQuestItemsCount(TISSUE_CH) == 0))
					{
						PartyMembers.add(st);
					}
					else if ((npc.getNpcId() == SEER_FLOUROS) && (st.getQuestItemsCount(TISSUE_SF) == 0))
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
			
			if ((npc.getNpcId() == CRIMSON_HATU) && (winnerst.getQuestItemsCount(TISSUE_CH) == 0))
			{
				winnerst.giveItems(TISSUE_CH, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == SEER_FLOUROS) && (winnerst.getQuestItemsCount(TISSUE_SF) == 0))
			{
				winnerst.giveItems(TISSUE_SF, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
		}
		else
		{
			if ((npc.getNpcId() == CRIMSON_HATU) && (st.getQuestItemsCount(TISSUE_CH) == 0))
			{
				st.giveItems(TISSUE_CH, 1);
				st.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == SEER_FLOUROS) && (st.getQuestItemsCount(TISSUE_SF) == 0))
			{
				st.giveItems(TISSUE_SF, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public Q10277_MutatedKaneusDion(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(LUKAS);
		addTalkId(LUKAS);
		addTalkId(MIRIEN);
		
		addKillId(CRIMSON_HATU);
		addKillId(SEER_FLOUROS);
		
		questItemIds = new int[]
		{
			TISSUE_CH, TISSUE_SF
		};
	}
	
	public static void main(String[] args)
	{
		new Q10277_MutatedKaneusDion(10277, qn, "Mutated Kaneus - Dion");
	}
}
