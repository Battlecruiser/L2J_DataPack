/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10277_MutatedKaneusDion;

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Mutated Kaneus - Dion (10277)<br>
 * Original Jython script by Gnacik on 2010-06-29.
 * @author nonom
 */
public class Q10277_MutatedKaneusDion extends Quest
{
	// NPCs
	private static final int LUKAS = 30071;
	private static final int MIRIEN = 30461;
	private static final int CRIMSON_HATU = 18558;
	private static final int SEER_FLOUROS = 18559;
	// Items
	private static final int TISSUE_CH = 13832;
	private static final int TISSUE_SF = 13833;
	
	public Q10277_MutatedKaneusDion(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(LUKAS);
		addTalkId(LUKAS, MIRIEN);
		addKillId(CRIMSON_HATU, SEER_FLOUROS);
		registerQuestItems(TISSUE_CH, TISSUE_SF);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case LUKAS:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() > 27) ? "30071-01.htm" : "30071-00.html";
						break;
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_CH) && st.hasQuestItems(TISSUE_SF)) ? "30071-05.html" : "30071-04.html";
						break;
					case State.COMPLETED:
						htmltext = "30071-06.html";
						break;
				}
				break;
			case MIRIEN:
				switch (st.getState())
				{
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_CH) && st.hasQuestItems(TISSUE_SF)) ? "30461-02.html" : "30461-01.html";
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
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		switch (event)
		{
			case "30071-03.html":
				st.startQuest();
				break;
			case "30461-03.html":
				st.giveAdena(20000, true);
				st.exitQuest(false, true);
				break;
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		QuestState st = killer.getQuestState(getName());
		if (st == null)
		{
			return super.onKill(npc, killer, isPet);
		}
		
		final int npcId = npc.getNpcId();
		if (killer.getParty() != null)
		{
			final List<QuestState> PartyMembers = new ArrayList<>();
			for (L2PcInstance member : killer.getParty().getMembers())
			{
				st = member.getQuestState(getName());
				if ((st != null) && st.isStarted() && (((npcId == CRIMSON_HATU) && !st.hasQuestItems(TISSUE_CH)) || ((npcId == SEER_FLOUROS) && !st.hasQuestItems(TISSUE_SF))))
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
		return super.onKill(npc, killer, isPet);
	}
	
	/**
	 * @param npcId the ID of the killed monster
	 * @param st the quest state of the killer or party member
	 */
	private final void rewardItem(int npcId, QuestState st)
	{
		if ((npcId == CRIMSON_HATU) && !st.hasQuestItems(TISSUE_CH))
		{
			st.giveItems(TISSUE_CH, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		else if ((npcId == SEER_FLOUROS) && !st.hasQuestItems(TISSUE_SF))
		{
			st.giveItems(TISSUE_SF, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
	}
	
	public static void main(String[] args)
	{
		new Q10277_MutatedKaneusDion(10277, Q10277_MutatedKaneusDion.class.getSimpleName(), "Mutated Kaneus - Dion");
	}
}
