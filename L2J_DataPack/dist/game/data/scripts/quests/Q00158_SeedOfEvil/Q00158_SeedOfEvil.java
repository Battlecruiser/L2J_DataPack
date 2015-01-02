/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package quests.Q00158_SeedOfEvil;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Seed of Evil (158)
 * @author malyelfik
 */
public class Q00158_SeedOfEvil extends Quest
{
	// NPC
	private static final int BIOTIN = 30031;
	// Monster
	private static final int NERKAS = 27016;
	// Items
	private static final int ENCHANT_ARMOR_D = 956;
	private static final int CLAY_TABLET = 1025;
	// Misc
	private static final int MIN_LEVEL = 21;
	
	public Q00158_SeedOfEvil()
	{
		super(158, Q00158_SeedOfEvil.class.getSimpleName(), "Seed of Evil");
		addStartNpc(BIOTIN);
		addTalkId(BIOTIN);
		addAttackId(NERKAS);
		addKillId(NERKAS);
		registerQuestItems(CLAY_TABLET);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if ((st != null) && event.equalsIgnoreCase("30031-03.htm"))
		{
			st.startQuest();
			return event;
		}
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (npc.isScriptValue(0))
		{
			npc.broadcastPacket(new NpcSay(npc, Say2.NPC_ALL, NpcStringId._HOW_DARE_YOU_CHALLENGE_ME));
			npc.setScriptValue(1);
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = getQuestState(killer, false);
		if ((st != null) && !st.hasQuestItems(CLAY_TABLET))
		{
			st.giveItems(CLAY_TABLET, 1);
			st.setCond(2, true);
		}
		npc.broadcastPacket(new NpcSay(npc, Say2.NPC_ALL, NpcStringId.THE_POWER_OF_LORD_BELETH_RULES_THE_WHOLE_WORLD));
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = getQuestState(player, true);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (player.getLevel() >= MIN_LEVEL) ? "30031-02.htm" : "30031-01.html";
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					htmltext = "30031-04.html";
				}
				else if (st.isCond(2) && st.hasQuestItems(CLAY_TABLET))
				{
					st.giveItems(ENCHANT_ARMOR_D, 1);
					st.addExpAndSp(17818, 927);
					st.giveAdena(1495, true);
					st.exitQuest(false, true);
					htmltext = "30031-05.html";
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
}