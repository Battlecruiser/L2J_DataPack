/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package quests.Q00169_OffspringOfNightmares;

import com.l2jserver.gameserver.enums.PcRace;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;

/**
 * Offspring of Nightmares (169)
 * @author xban1x
 */
public class Q00169_OffspringOfNightmares extends Quest
{
	// NPC
	private static final int VLASTY = 30145;
	// Monsters
	private static final int LESSER_DARK_HORROR = 20025;
	private static final int DARK_HORROR = 20105;
	// Items
	private static final int BONE_GAITERS = 31;
	private static final int CRACKED_SKULL = 1030;
	private static final int PERFECT_SKULL = 1031;
	// Misc
	private static final int MIN_LVL = 15;
	
	private Q00169_OffspringOfNightmares(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(VLASTY);
		addTalkId(VLASTY);
		addKillId(LESSER_DARK_HORROR, DARK_HORROR);
		registerQuestItems(CRACKED_SKULL, PERFECT_SKULL);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st != null)
		{
			switch (event)
			{
				case "30145-03.htm":
				{
					st.startQuest();
					htmltext = event;
					break;
				}
				case "30145-07.html":
				{
					if (st.isCond(2) && st.hasQuestItems(PERFECT_SKULL))
					{
						st.giveItems(BONE_GAITERS, 1);
						st.addExpAndSp(17475, 818);
						st.giveAdena(17030 + (10 * st.getQuestItemsCount(CRACKED_SKULL)), true);
						st.exitQuest(false, true);
						showOnScreenMsg(player, NpcStringId.LAST_DUTY_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000); // TODO: Newbie Guide
						htmltext = event;
					}
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = killer.getQuestState(getName());
		if ((st != null) && st.isStarted())
		{
			if ((getRandom(10) > 7) && !st.hasQuestItems(PERFECT_SKULL))
			{
				st.giveItems(PERFECT_SKULL, 1);
				st.setCond(2, true);
			}
			else if (getRandom(10) > 4)
			{
				st.giveItems(CRACKED_SKULL, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st != null)
		{
			switch (st.getState())
			{
				case State.CREATED:
				{
					htmltext = (player.getRace() == PcRace.DarkElf) ? (player.getLevel() >= MIN_LVL) ? "30145-02.htm" : "30145-01.htm" : "30145-00.htm";
					break;
				}
				case State.STARTED:
				{
					if (st.hasQuestItems(CRACKED_SKULL) && !st.hasQuestItems(PERFECT_SKULL))
					{
						htmltext = "30145-05.html";
					}
					else if (st.isCond(2) && st.hasQuestItems(PERFECT_SKULL))
					{
						htmltext = "30145-06.html";
					}
					else if (!st.hasQuestItems(CRACKED_SKULL, PERFECT_SKULL))
					{
						htmltext = "30145-04.html";
					}
					break;
				}
				case State.COMPLETED:
				{
					htmltext = getAlreadyCompletedMsg(player);
					break;
				}
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00169_OffspringOfNightmares(169, Q00169_OffspringOfNightmares.class.getSimpleName(), "Offspring of Nightmares");
	}
}