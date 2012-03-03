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

/**
 * Mutated Kaneus - Schuttgart (10280).<br>
 * Original Jython script by Gnacik on 2010-06-29
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
		final QuestState st = player.getQuestState(qn);
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
				else if (st.isCreated())
				{
					htmltext = (player.getLevel() >= 58) ? "31981-01.htm" : "31981-00.htm";
				}
				else if (st.hasQuestItems(TISSUE_VS) && st.hasQuestItems(TISSUE_KB))
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
					htmltext = getAlreadyCompletedMsg(player);
				}
				else if (st.hasQuestItems(TISSUE_VS) && st.hasQuestItems(TISSUE_KB))
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
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return getNoQuestMsg(player);
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
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
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
		
		final int npcId = npc.getNpcId();
		if (killer.getParty() != null)
		{
			final FastList<QuestState> PartyMembers = new FastList<>();
			for (L2PcInstance member : killer.getParty().getPartyMembers())
			{
				st = member.getQuestState(qn);
				if ((st != null) && st.isStarted() && (st.getInt("cond") == 1))
				{
					if ((npcId == VENOMOUS_STORACE) && !st.hasQuestItems(TISSUE_VS))
					{
						PartyMembers.add(st);
					}
					else if ((npcId == KEL_BILETTE) && !st.hasQuestItems(TISSUE_KB))
					{
						PartyMembers.add(st);
					}
				}
			}
			
			if (!PartyMembers.isEmpty())
			{
				rewardItem(npcId, PartyMembers.get(getRandom(PartyMembers.size())));
			}
		}
		else
		{
			rewardItem(npcId, st);
		}
		return null;
	}
	
	/**
	 * @param npcId the killed monster Id.
	 * @param st the quest state of the killer or party member.
	 */
	private final void rewardItem(int npcId, QuestState st)
	{
		if ((npcId == VENOMOUS_STORACE) && !st.hasQuestItems(TISSUE_VS))
		{
			st.giveItems(TISSUE_VS, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		else if ((npcId == KEL_BILETTE) && !st.hasQuestItems(TISSUE_KB))
		{
			st.giveItems(TISSUE_KB, 1);
			st.playSound("ItemSound.quest_itemget");
		}
	}
	
	public Q10280_MutatedKaneusSchuttgart(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(VISHOTSKY);
		addTalkId(VISHOTSKY, ATRAXIA);
		
		addKillId(VENOMOUS_STORACE, KEL_BILETTE);
		
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
