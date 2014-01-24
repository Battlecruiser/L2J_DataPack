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
package quests.Q00163_LegacyOfThePoet;

import com.l2jserver.gameserver.enums.PcRace;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Legacy of the Poet (163)
 * @author xban1x
 */
public class Q00163_LegacyOfThePoet extends Quest
{
	// NPC
	private static final int STARDEN = 30220;
	// Monsters
	private static final int[] MONSTERS =
	{
		20372, // Baraq Orc Fighter
		20373, // Baraq Orc Warrior Leader
	};
	// Items
	private static final int RUMIELS_1ST_POEM = 1038;
	private static final int RUMIELS_2ND_POEM = 1039;
	private static final int RUMIELS_3RD_POEM = 1040;
	private static final int RUMIELS_4TH_POEM = 1041;
	// Misc
	private static final int MIN_LVL = 11;
	
	private Q00163_LegacyOfThePoet(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(STARDEN);
		addTalkId(STARDEN);
		addKillId(MONSTERS);
		registerQuestItems(RUMIELS_1ST_POEM, RUMIELS_2ND_POEM, RUMIELS_3RD_POEM, RUMIELS_4TH_POEM);
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
				case "30220-03.html":
				case "30220-04.html":
				{
					htmltext = event;
					break;
				}
				case "30220-05.htm":
				{
					st.startQuest();
					htmltext = event;
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
		if ((st != null) && st.isCond(1))
		{
			if ((getRandom(10) == 0) && !st.hasQuestItems(RUMIELS_1ST_POEM))
			{
				st.giveItems(RUMIELS_1ST_POEM, 1);
				if (st.hasQuestItems(RUMIELS_2ND_POEM, RUMIELS_3RD_POEM, RUMIELS_4TH_POEM))
				{
					st.setCond(2, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
			if ((getRandom(10) > 7) && !st.hasQuestItems(RUMIELS_2ND_POEM))
			{
				st.giveItems(RUMIELS_2ND_POEM, 1);
				if (st.hasQuestItems(RUMIELS_1ST_POEM, RUMIELS_3RD_POEM, RUMIELS_4TH_POEM))
				{
					st.setCond(2, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
			if ((getRandom(10) > 7) && !st.hasQuestItems(RUMIELS_3RD_POEM))
			{
				st.giveItems(RUMIELS_3RD_POEM, 1);
				if (st.hasQuestItems(RUMIELS_1ST_POEM, RUMIELS_2ND_POEM, RUMIELS_4TH_POEM))
				{
					st.setCond(2, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
			if ((getRandom(10) > 5) && !st.hasQuestItems(RUMIELS_4TH_POEM))
			{
				st.giveItems(RUMIELS_4TH_POEM, 1);
				if (st.hasQuestItems(RUMIELS_1ST_POEM, RUMIELS_2ND_POEM, RUMIELS_3RD_POEM))
				{
					st.setCond(2, true);
				}
				else
				{
					st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
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
					htmltext = (player.getRace() != PcRace.DarkElf) ? (player.getLevel() >= MIN_LVL) ? "30220-02.htm" : "30220-01.htm" : "30220-00.htm";
					break;
				}
				case State.STARTED:
				{
					if (st.hasQuestItems(RUMIELS_1ST_POEM, RUMIELS_2ND_POEM, RUMIELS_3RD_POEM, RUMIELS_4TH_POEM))
					{
						st.addExpAndSp(21643, 943);
						st.giveAdena(13890, true);
						st.exitQuest(false, true);
						htmltext = "30220-07.html";
					}
					else
					{
						htmltext = "30220-06.html";
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
		new Q00163_LegacyOfThePoet(163, Q00163_LegacyOfThePoet.class.getSimpleName(), "Legacy of the Poet");
	}
}