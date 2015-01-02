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
package quests.Q00003_WillTheSealBeBroken;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Will the Seal be Broken? (3)
 * @author malyelfik
 */
public class Q00003_WillTheSealBeBroken extends Quest
{
	// NPC
	private static final int TALLOTH = 30141;
	// Monsters
	private static final int OMEN_BEAST = 20031;
	private static final int TAINTED_ZOMBIE = 20041;
	private static final int STINK_ZOMBIE = 20046;
	private static final int LESSER_SUCCUBUS = 20048;
	private static final int LESSER_SUCCUBUS_TUREN = 20052;
	private static final int LESSER_SUCCUBUS_TILFO = 20057;
	// Items
	private static final int OMEN_BEAST_EYE = 1081;
	private static final int TAINT_STONE = 1082;
	private static final int SUCCUBUS_BLOOD = 1083;
	private static final int ENCHANT = 956;
	// Misc
	private static final int MIN_LEVEL = 16;
	
	public Q00003_WillTheSealBeBroken()
	{
		super(3, Q00003_WillTheSealBeBroken.class.getSimpleName(), "Will the Seal be Broken?");
		addStartNpc(TALLOTH);
		addTalkId(TALLOTH);
		addKillId(OMEN_BEAST, TAINTED_ZOMBIE, STINK_ZOMBIE, LESSER_SUCCUBUS, LESSER_SUCCUBUS_TILFO, LESSER_SUCCUBUS_TUREN);
		registerQuestItems(OMEN_BEAST_EYE, TAINT_STONE, SUCCUBUS_BLOOD);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30141-03.htm":
				st.startQuest();
				break;
			case "30141-05.html":
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final L2PcInstance member = getRandomPartyMember(player, 1);
		if (member == null)
		{
			return super.onKill(npc, player, isSummon);
		}
		final QuestState st = getQuestState(member, false);
		switch (npc.getId())
		{
			case OMEN_BEAST:
				giveItem(st, OMEN_BEAST_EYE, getRegisteredItemIds());
				break;
			case STINK_ZOMBIE:
			case TAINTED_ZOMBIE:
				giveItem(st, TAINT_STONE, getRegisteredItemIds());
				break;
			case LESSER_SUCCUBUS:
			case LESSER_SUCCUBUS_TILFO:
			case LESSER_SUCCUBUS_TUREN:
				giveItem(st, SUCCUBUS_BLOOD, getRegisteredItemIds());
				break;
		}
		return super.onKill(npc, player, isSummon);
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
				htmltext = (player.getRace() != Race.DARK_ELF) ? "30141-00.htm" : (player.getLevel() >= MIN_LEVEL) ? "30141-02.htm" : "30141-01.html";
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					htmltext = "30141-04.html";
				}
				else
				{
					st.giveItems(ENCHANT, 1);
					st.exitQuest(false, true);
					htmltext = "30141-06.html";
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	private static void giveItem(QuestState st, int item, int... items)
	{
		if (!st.hasQuestItems(item))
		{
			st.giveItems(item, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			if (st.hasQuestItems(items))
			{
				st.setCond(2, true);
			}
		}
	}
}