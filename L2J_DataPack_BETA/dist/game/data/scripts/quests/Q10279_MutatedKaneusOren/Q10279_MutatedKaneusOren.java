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

/**
 * Mutated Kaneus - Oren (10279).<br>
 * Original Jython script by Gnacik on 2010-06-29
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
		final QuestState st = player.getQuestState(qn);
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
				else if (st.isCreated())
				{
					htmltext = (player.getLevel() >= 48) ? "30196-01.htm" : "30196-00.htm";
				}
				else if (st.hasQuestItems(TISSUE_KA) && st.hasQuestItems(TISSUE_KM))
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
					htmltext = getAlreadyCompletedMsg(player);
				}
				else if (st.hasQuestItems(TISSUE_KA) && st.hasQuestItems(TISSUE_KM))
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
		final QuestState st = player.getQuestState(qn);
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
			final FastList<QuestState> PartyMembers = new FastList<QuestState>();
			for (L2PcInstance member : killer.getParty().getPartyMembers())
			{
				st = member.getQuestState(qn);
				if ((st != null) && st.isStarted() && (st.getInt("cond") == 1))
				{
					if ((npcId == KAIM_ABIGORE) && !st.hasQuestItems(TISSUE_KA))
					{
						PartyMembers.add(st);
					}
					else if ((npcId == KNIGHT_MONTAGNAR) && !st.hasQuestItems(TISSUE_KM))
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
		if ((npcId == KAIM_ABIGORE) && !st.hasQuestItems(TISSUE_KA))
		{
			st.giveItems(TISSUE_KA, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		else if ((npcId == KNIGHT_MONTAGNAR) && !st.hasQuestItems(TISSUE_KM))
		{
			st.giveItems(TISSUE_KM, 1);
			st.playSound("ItemSound.quest_itemget");
		}
	}
	
	public Q10279_MutatedKaneusOren(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MOUEN);
		addTalkId(MOUEN, ROVIA);
		
		addKillId(KAIM_ABIGORE, KNIGHT_MONTAGNAR);
		
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
