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
package quests.Q10276_MutatedKaneusGludio;

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Mutated Kaneus - Gludio (10276)<br>
 * Original Jython script by Gnacik on 2010-06-29.
 * @author nonom
 */
public class Q10276_MutatedKaneusGludio extends Quest
{
	// NPCs
	private static final int BATHIS = 30332;
	private static final int ROHMER = 30344;
	private static final int TOMLAN_KAMOS = 18554;
	private static final int OL_ARIOSH = 18555;
	// Items
	private static final int TISSUE_TK = 13830;
	private static final int TISSUE_OA = 13831;
	
	public Q10276_MutatedKaneusGludio(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(BATHIS);
		addTalkId(BATHIS, ROHMER);
		addKillId(TOMLAN_KAMOS, OL_ARIOSH);
		registerQuestItems(TISSUE_TK, TISSUE_OA);
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
			case "30332-03.htm":
				st.startQuest();
				break;
			case "30344-03.htm":
				st.giveAdena(8500, true);
				st.exitQuest(false, true);
				break;
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		QuestState st = killer.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		final int npcId = npc.getId();
		if (killer.getParty() != null)
		{
			final List<QuestState> PartyMembers = new ArrayList<>();
			for (L2PcInstance member : killer.getParty().getMembers())
			{
				st = member.getQuestState(getName());
				if ((st != null) && st.isStarted() && (((npcId == TOMLAN_KAMOS) && !st.hasQuestItems(TISSUE_TK)) || ((npcId == OL_ARIOSH) && !st.hasQuestItems(TISSUE_OA))))
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
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case BATHIS:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() > 17) ? "30332-01.htm" : "30332-00.htm";
						break;
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_TK) && st.hasQuestItems(TISSUE_OA)) ? "30332-05.htm" : "30332-04.htm";
						break;
					case State.COMPLETED:
						htmltext = "30332-06.htm";
						break;
				}
				break;
			case ROHMER:
				switch (st.getState())
				{
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_TK) && st.hasQuestItems(TISSUE_OA)) ? "30344-02.htm" : "30344-01.htm";
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
	
	/**
	 * @param npcId the ID of the killed monster
	 * @param st the quest state of the killer or party member
	 */
	private final void rewardItem(int npcId, QuestState st)
	{
		if ((npcId == TOMLAN_KAMOS) && !st.hasQuestItems(TISSUE_TK))
		{
			giveItems(st.getPlayer(), TISSUE_TK, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		else if ((npcId == OL_ARIOSH) && !st.hasQuestItems(TISSUE_OA))
		{
			giveItems(st.getPlayer(), TISSUE_OA, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
	}
	
	public static void main(String[] args)
	{
		new Q10276_MutatedKaneusGludio(10276, Q10276_MutatedKaneusGludio.class.getSimpleName(), "Mutated Kaneus - Gludio");
	}
}
