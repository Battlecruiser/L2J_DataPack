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
package quests.Q00105_SkirmishWithOrcs;

import java.util.HashMap;
import java.util.Map;

import quests.Q00281_HeadForTheHills.Q00281_HeadForTheHills;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;
import com.l2jserver.gameserver.util.Util;

/**
 * Skimirish with Orcs (105)
 * @author janiko
 */
public final class Q00105_SkirmishWithOrcs extends Quest
{
	// NPC
	private static final int KENDNELL = 30218;
	// Items
	private static final int KENDNELLS_ORDER1 = 1836;
	private static final int KENDNELLS_ORDER2 = 1837;
	private static final int KENDNELLS_ORDER3 = 1838;
	private static final int KENDNELLS_ORDER4 = 1839;
	private static final int KENDNELLS_ORDER5 = 1840;
	private static final int KENDNELLS_ORDER6 = 1841;
	private static final int KENDNELLS_ORDER7 = 1842;
	private static final int KENDNELLS_ORDER8 = 1843;
	private static final int KABOO_CHIEF_TORC1 = 1844;
	private static final int KABOO_CHIEF_TORC2 = 1845;
	// MONSTER_DROP
	private static final Map<Integer, Integer> MONSTER_DROP = new HashMap<>();
	static
	{
		MONSTER_DROP.put(27059, KENDNELLS_ORDER1); // Uoph (Kaboo Chief)
		MONSTER_DROP.put(27060, KENDNELLS_ORDER2); // Kracha (Kaboo Chief)
		MONSTER_DROP.put(27061, KENDNELLS_ORDER3); // Batoh (Kaboo Chief)
		MONSTER_DROP.put(27062, KENDNELLS_ORDER4); // Tanukia (Kaboo Chief)
		MONSTER_DROP.put(27064, KENDNELLS_ORDER5); // Turel (Kaboo Chief)
		MONSTER_DROP.put(27065, KENDNELLS_ORDER6); // Roko (Kaboo Chief)
		MONSTER_DROP.put(27067, KENDNELLS_ORDER7); // Kamut (Kaboo Chief)
		MONSTER_DROP.put(27068, KENDNELLS_ORDER8); // Murtika (Kaboo Chief)
	}
	// Orders
	private static final int[] KENDNELLS_ORDERS =
	{
		KENDNELLS_ORDER1,
		KENDNELLS_ORDER2,
		KENDNELLS_ORDER3,
		KENDNELLS_ORDER4,
		KENDNELLS_ORDER5,
		KENDNELLS_ORDER6,
		KENDNELLS_ORDER7,
		KENDNELLS_ORDER8
	};
	// Misc
	private static final int MIN_LVL = 10;
	
	private Q00105_SkirmishWithOrcs(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(KENDNELL);
		addTalkId(KENDNELL);
		addKillId(MONSTER_DROP.keySet());
		registerQuestItems(KENDNELLS_ORDERS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st == null)
		{
			return htmltext;
		}
		switch (event)
		{
			case "30218-04.html":
			{
				if (st.isCreated())
				{
					st.startQuest();
					st.giveItems(KENDNELLS_ORDERS[getRandom(0, 3)], 1);
					htmltext = event;
				}
				break;
			}
			case "30218-05.html":
			{
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		final QuestState st = talker.getQuestState(getName());
		String htmltext = getNoQuestMsg(talker);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				if (talker.getRace() == Race.Elf)
				{
					htmltext = (talker.getLevel() >= MIN_LVL) ? "30218-03.htm" : "30218-02.htm";
				}
				else
				{
					htmltext = "30218-01.htm";
				}
				break;
			}
			case State.STARTED:
			{
				if (hasAtLeastOneQuestItem(talker, KENDNELLS_ORDER1, KENDNELLS_ORDER2, KENDNELLS_ORDER3, KENDNELLS_ORDER4))
				{
					htmltext = "30218-06.html";
				}
				if (st.isCond(2) && st.hasQuestItems(KABOO_CHIEF_TORC1))
				{
					for (int i = 0; i < 4; i++)
					{
						st.takeItems(KENDNELLS_ORDERS[i], -1);
					}
					st.takeItems(KABOO_CHIEF_TORC1, 1);
					st.giveItems(KENDNELLS_ORDERS[getRandom(4, 7)], 1);
					st.setCond(3, true);
					htmltext = "30218-07.html";
				}
				if (hasAtLeastOneQuestItem(talker, KENDNELLS_ORDER5, KENDNELLS_ORDER6, KENDNELLS_ORDER7, KENDNELLS_ORDER8))
				{
					htmltext = "30218-08.html";
				}
				if (st.isCond(4) && st.hasQuestItems(KABOO_CHIEF_TORC2))
				{
					Q00281_HeadForTheHills.giveNewbieReward(talker);
					talker.sendPacket(new SocialAction(talker.getObjectId(), 3));
					st.giveAdena(17599, true);
					st.addExpAndSp(41478, 3555);
					st.exitQuest(false, true);
					htmltext = "30218-09.html";
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(talker);
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = killer.getQuestState(getName());
		if ((st != null) && Util.checkIfInRange(1500, npc, killer, true))
		{
			switch (npc.getId())
			{
				case 27059:
				case 27060:
				case 27061:
				case 27062:
				{
					if (st.isCond(1) && st.hasQuestItems(MONSTER_DROP.get(npc.getId())))
					{
						st.giveItems(KABOO_CHIEF_TORC1, 1);
						st.setCond(2, true);
					}
					break;
				}
				case 27064:
				case 27065:
				case 27067:
				case 27068:
				{
					if (st.isCond(3) && st.hasQuestItems(MONSTER_DROP.get(npc.getId())))
					{
						st.giveItems(KABOO_CHIEF_TORC2, 1);
						st.setCond(4, true);
					}
					break;
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new Q00105_SkirmishWithOrcs(105, Q00105_SkirmishWithOrcs.class.getSimpleName(), "Skirmish with Orcs");
	}
}