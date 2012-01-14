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
package quests.Q10279_MutatedKaneusOren;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Mutated Kaneus - Oren (10279). Original Jython script by Gnacik on 2010-06-29
 * @author nonom
 */
public class Q10279_MutatedKaneusOren extends Quest
{
	private static final String qn = "10279_MutatedKaneusOren";
	
	// NPCs
	private static final int MOUEN = 30196;
	private static final int ROVIA = 30189;
	private static final int KAIM_ABIGORE = 18566;
	private static final int KNIGHT_MONTAGNAR = 18568;
	
	// Items
	private static final int TISSUE_KA = 13836;
	private static final int TISSUE_KM = 13837;
	
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
			case MOUEN:
				if (st.isCompleted())
				{
					htmltext = "30196-06.htm";
				}
				else if (st.isCreated() && (player.getLevel() >= 48))
				{
					htmltext = "30196-01.htm";
				}
				else if (st.isCreated() && (player.getLevel() < 48))
				{
					htmltext = "30196-00.htm";
				}
				else if ((st.getQuestItemsCount(TISSUE_KA) > 0) && (st.getQuestItemsCount(TISSUE_KM) > 0))
				{
					htmltext = "30196-05.htm";
				}
				else if (st.getInt("cond") == 1)
				{
					htmltext = "30196-04.htm";
				}
				break;
			case ROVIA:
				if (st.isCompleted())
				{
					htmltext = Quest.getAlreadyCompletedMsg(player);
				}
				else if ((st.getQuestItemsCount(TISSUE_KA) > 0) && (st.getQuestItemsCount(TISSUE_KM) > 0))
				{
					htmltext = "30189-02.htm";
				}
				else
				{
					htmltext = "30189-01.htm";
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
			case "30196-03.htm":
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				break;
			case "30189-03.htm":
				st.rewardItems(57, 100000);
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
					if ((npc.getNpcId() == KAIM_ABIGORE) && (st.getQuestItemsCount(TISSUE_KA) == 0))
					{
						PartyMembers.add(st);
					}
					else if ((npc.getNpcId() == KNIGHT_MONTAGNAR) && (st.getQuestItemsCount(TISSUE_KM) == 0))
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
			
			if ((npc.getNpcId() == KAIM_ABIGORE) && (winnerst.getQuestItemsCount(TISSUE_KA) == 0))
			{
				winnerst.giveItems(TISSUE_KA, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == KNIGHT_MONTAGNAR) && (winnerst.getQuestItemsCount(TISSUE_KM) == 0))
			{
				winnerst.giveItems(TISSUE_KM, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
		}
		else
		{
			if ((npc.getNpcId() == KAIM_ABIGORE) && (st.getQuestItemsCount(TISSUE_KA) == 0))
			{
				st.giveItems(TISSUE_KA, 1);
				st.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == KNIGHT_MONTAGNAR) && (st.getQuestItemsCount(TISSUE_KM) == 0))
			{
				st.giveItems(TISSUE_KM, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public Q10279_MutatedKaneusOren(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MOUEN);
		addTalkId(MOUEN);
		addTalkId(ROVIA);
		
		addKillId(KAIM_ABIGORE);
		addKillId(KNIGHT_MONTAGNAR);
		
		questItemIds = new int[]
		{
			TISSUE_KA, TISSUE_KM
		};
	}
	
	public static void main(String[] args)
	{
		new Q10279_MutatedKaneusOren(10279, qn, "Mutated Kaneus - Oren");
	}
}
