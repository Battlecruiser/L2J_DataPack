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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Mutated Kaneus - Rune (10281)
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
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case MATHIAS:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() > 67) ? "31340-01.htm" : "31340-00.htm";
						break;
					case State.STARTED:
						htmltext = st.hasQuestItems(TISSUE_WA) ? "31340-05.htm" : "31340-04.htm";
						break;
					case State.COMPLETED:
						htmltext = "31340-06.htm";
						break;
				}
				break;
			case KAYAN:
				switch (st.getState())
				{
					case State.STARTED:
						htmltext = st.hasQuestItems(TISSUE_WA) ? "31335-02.htm" : "31335-01.htm";
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
			case "31340-03.htm":
				st.startQuest();
				break;
			case "31335-03.htm":
				st.giveAdena(360000, true);
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
				if ((st != null) && st.isStarted() && !st.hasQuestItems(TISSUE_WA))
				{
					PartyMembers.add(st);
				}
			}
			
			if (!PartyMembers.isEmpty())
			{
				rewardItem(npcId, PartyMembers.get(getRandom(PartyMembers.size())));
			}
		}
		else if (st.isStarted() && !st.hasQuestItems(TISSUE_WA))
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
		st.giveItems(TISSUE_WA, 1);
		st.playSound("ItemSound.quest_itemget");
	}
	
	public Q10281_MutatedKaneusRune(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(MATHIAS);
		addTalkId(MATHIAS, KAYAN);
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
