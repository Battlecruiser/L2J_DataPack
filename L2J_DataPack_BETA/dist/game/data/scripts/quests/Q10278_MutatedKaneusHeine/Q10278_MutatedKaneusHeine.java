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
package quests.Q10278_MutatedKaneusHeine;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Mutated Kaneus - Heine (10278). Original Jython script by Gnacik on 2010-06-29
 * @author nonom
 */
public class Q10278_MutatedKaneusHeine extends Quest
{
	private static final String qn = "10278_MutatedKaneusHeine";
	
	// NPCs
	private static final int GOSTA = 30916;
	private static final int MINEVIA = 30907;
	private static final int BLADE_OTIS = 18562;
	private static final int WEIRD_BUNEI = 18564;
	
	// Items
	private static final int TISSUE_BO = 13834;
	private static final int TISSUE_WB = 13835;
	
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
			case GOSTA:
				if (st.isCompleted())
				{
					htmltext = "30916-06.htm";
				}
				else if (st.isCreated() && (player.getLevel() >= 38))
				{
					htmltext = "30916-01.htm";
				}
				else if (st.isCreated() && (player.getLevel() < 38))
				{
					htmltext = "30916-00.htm";
				}
				else if ((st.getQuestItemsCount(TISSUE_BO) > 0) && (st.getQuestItemsCount(TISSUE_WB) > 0))
				{
					htmltext = "30916-05.htm";
				}
				else if (st.getInt("cond") == 1)
				{
					htmltext = "30916-04.htm";
				}
				break;
			case MINEVIA:
				if (st.isCompleted())
				{
					htmltext = Quest.getAlreadyCompletedMsg(player);
				}
				else if ((st.getQuestItemsCount(TISSUE_BO) > 0) && (st.getQuestItemsCount(TISSUE_WB) > 0))
				{
					htmltext = "30907-02.htm";
				}
				else
				{
					htmltext = "30907-01.htm";
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
			case "30916-03.htm":
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				break;
			case "30907-03.htm":
				st.rewardItems(57, 50000);
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
					if ((npc.getNpcId() == BLADE_OTIS) && (st.getQuestItemsCount(TISSUE_BO) == 0))
					{
						PartyMembers.add(st);
					}
					else if ((npc.getNpcId() == WEIRD_BUNEI) && (st.getQuestItemsCount(TISSUE_WB) == 0))
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
			
			if ((npc.getNpcId() == BLADE_OTIS) && (winnerst.getQuestItemsCount(TISSUE_BO) == 0))
			{
				winnerst.giveItems(TISSUE_BO, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == WEIRD_BUNEI) && (winnerst.getQuestItemsCount(TISSUE_WB) == 0))
			{
				winnerst.giveItems(TISSUE_WB, 1);
				winnerst.playSound("ItemSound.quest_itemget");
			}
		}
		else
		{
			if ((npc.getNpcId() == BLADE_OTIS) && (st.getQuestItemsCount(TISSUE_BO) == 0))
			{
				st.giveItems(TISSUE_BO, 1);
				st.playSound("ItemSound.quest_itemget");
			}
			else if ((npc.getNpcId() == WEIRD_BUNEI) && (st.getQuestItemsCount(TISSUE_WB) == 0))
			{
				st.giveItems(TISSUE_WB, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public Q10278_MutatedKaneusHeine(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GOSTA);
		addTalkId(MINEVIA);
		addTalkId(MINEVIA);
		
		addKillId(BLADE_OTIS);
		addKillId(WEIRD_BUNEI);
		
		questItemIds = new int[]
		{
			TISSUE_BO, TISSUE_WB
		};
	}
	
	public static void main(String[] args)
	{
		new Q10278_MutatedKaneusHeine(10278, qn, "Mutated Kaneus - Heine");
	}
}
