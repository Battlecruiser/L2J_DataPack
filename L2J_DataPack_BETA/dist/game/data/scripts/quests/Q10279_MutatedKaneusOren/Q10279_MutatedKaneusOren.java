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
package quests.Q10279_MutatedKaneusOren;

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Mutated Kaneus - Oren (10279)<br>
 * Original Jython script by Gnacik on 2010-06-29.
 * @author nonom
 */
public class Q10279_MutatedKaneusOren extends Quest
{
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
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case MOUEN:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() > 47) ? "30196-01.htm" : "30196-00.htm";
						break;
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_KA) && st.hasQuestItems(TISSUE_KM)) ? "30196-05.htm" : "30196-04.htm";
						break;
					case State.COMPLETED:
						htmltext = "30916-06.htm";
						break;
				}
				break;
			case ROVIA:
				switch (st.getState())
				{
					case State.STARTED:
						htmltext = (st.hasQuestItems(TISSUE_KA) && st.hasQuestItems(TISSUE_KM)) ? "30189-02.htm" : "30189-01.htm";
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
			case "30196-03.htm":
				st.startQuest();
				break;
			case "30189-03.htm":
				st.giveAdena(100000, true);
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
			return null;
		}
		
		final int npcId = npc.getNpcId();
		if (killer.getParty() != null)
		{
			final List<QuestState> PartyMembers = new ArrayList<>();
			for (L2PcInstance member : killer.getParty().getMembers())
			{
				st = member.getQuestState(getName());
				if ((st != null) && st.isStarted() && (((npcId == KAIM_ABIGORE) && !st.hasQuestItems(TISSUE_KA)) || ((npcId == KNIGHT_MONTAGNAR) && !st.hasQuestItems(TISSUE_KM))))
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
		if ((npcId == KAIM_ABIGORE) && !st.hasQuestItems(TISSUE_KA))
		{
			st.giveItems(TISSUE_KA, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		else if ((npcId == KNIGHT_MONTAGNAR) && !st.hasQuestItems(TISSUE_KM))
		{
			st.giveItems(TISSUE_KM, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
	}
	
	public Q10279_MutatedKaneusOren(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(MOUEN);
		addTalkId(MOUEN, ROVIA);
		addKillId(KAIM_ABIGORE, KNIGHT_MONTAGNAR);
		registerQuestItems(TISSUE_KA, TISSUE_KM);
	}
	
	public static void main(String[] args)
	{
		new Q10279_MutatedKaneusOren(10279, Q10279_MutatedKaneusOren.class.getSimpleName(), "Mutated Kaneus - Oren");
	}
}
