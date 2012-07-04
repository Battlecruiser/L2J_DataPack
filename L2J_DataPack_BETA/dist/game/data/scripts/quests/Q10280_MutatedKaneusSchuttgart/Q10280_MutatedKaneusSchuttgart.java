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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Mutated Kaneus - Schuttgart (10280)
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
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() > 57) ? "31981-01.htm" : "31981-00.htm";
						break;
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_VS) && st.hasQuestItems(TISSUE_KB)) ? "31981-05.htm" : "31981-04.htm";
						break;
					case State.COMPLETED:
						htmltext = "31981-06.htm";
						break;
				}
				break;
			case ATRAXIA:
				switch (st.getState())
				{
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_VS) && st.hasQuestItems(TISSUE_KB)) ? "31972-02.htm" : "31972-01.htm";
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
					default:
						break;
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "31981-03.htm":
				st.startQuest();
				break;
			case "31972-03.htm":
				st.giveAdena(210000, true);
				st.exitQuest(false, true);
				break;
		}
		return event;
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
			final List<QuestState> PartyMembers = new ArrayList<>();
			for (L2PcInstance member : killer.getParty().getMembers())
			{
				st = member.getQuestState(qn);
				if ((st != null) && st.isStarted() && (((npcId == VENOMOUS_STORACE) && !st.hasQuestItems(TISSUE_VS)) || ((npcId == KEL_BILETTE) && !st.hasQuestItems(TISSUE_KB))))
				{
					PartyMembers.add(st);
				}
			}
			
			if (!PartyMembers.isEmpty())
			{
				rewardItem(npcId, PartyMembers.get(getRandom(PartyMembers.size())));
			}
		}
		else if (st.isStarted())
		{
			rewardItem(npcId, st);
		}
		return null;
	}
	
	/**
	 * @param npcId the ID of the killed monster
	 * @param st the quest state of the killer or party member
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
			TISSUE_VS,
			TISSUE_KB
		};
	}
	
	public static void main(String[] args)
	{
		new Q10280_MutatedKaneusSchuttgart(10280, qn, "Mutated Kaneus - Schuttgart");
	}
}
