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
package quests.Q00298_LizardmensConspiracy;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemChanceHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Lizardmen's Conspiracy (298)
 * @author xban1x
 */
public final class Q00298_LizardmensConspiracy extends Quest
{
	// NPCs
	private static final int GUARD_PRAGA = 30333;
	private static final int MAGISTER_ROHMER = 30344;
	// Items
	private static final int PATROLS_REPORT = 7182;
	private static final int SHINING_GEM = 7183;
	private static final int SHINING_RED_GEM = 7184;
	// Monsters
	private static final Map<Integer, ItemChanceHolder> MONSTERS = new HashMap<>();
	static
	{
		MONSTERS.put(20922, new ItemChanceHolder(SHINING_GEM, 0.49, 1));
		MONSTERS.put(20924, new ItemChanceHolder(SHINING_GEM, 0.75, 1));
		MONSTERS.put(20926, new ItemChanceHolder(SHINING_RED_GEM, 0.54, 1));
		MONSTERS.put(20927, new ItemChanceHolder(SHINING_RED_GEM, 0.54, 1));
		MONSTERS.put(20922, new ItemChanceHolder(SHINING_GEM, 0.70, 1));
	}
	// Misc
	private static final int MIN_LVL = 25;
	
	public Q00298_LizardmensConspiracy()
	{
		super(298, Q00298_LizardmensConspiracy.class.getSimpleName(), "Lizardmen's Conspiracy");
		addStartNpc(GUARD_PRAGA);
		addTalkId(GUARD_PRAGA, MAGISTER_ROHMER);
		addKillId(MONSTERS.keySet());
		registerQuestItems(PATROLS_REPORT, SHINING_GEM, SHINING_RED_GEM);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		String html = null;
		if (qs == null)
		{
			return html;
		}
		
		switch (event)
		{
			case "30333-03.htm":
			{
				if (qs.isCreated())
				{
					qs.startQuest();
					giveItems(player, PATROLS_REPORT, 1);
					html = event;
				}
				break;
			}
			case "30344-04.html":
			{
				if (qs.isCond(1) && hasQuestItems(player, PATROLS_REPORT))
				{
					takeItems(player, PATROLS_REPORT, -1);
					qs.setCond(2, true);
					html = event;
				}
				break;
			}
			case "30344-06.html":
			{
				if (qs.isStarted())
				{
					if (qs.isCond(3))
					{
						addExpAndSp(player, 0, 42000);
						qs.exitQuest(true, true);
						html = event;
					}
					else
					{
						html = "30344-07.html";
					}
				}
				break;
			}
		}
		return html;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getRandomPartyMemberState(killer, 2, 3, npc);
		if (qs != null)
		{
			final ItemChanceHolder item = MONSTERS.get(npc.getId());
			if (giveItemRandomly(qs.getPlayer(), npc, item.getId(), item.getCount(), 50, item.getChance(), true) //
				&& (getQuestItemsCount(qs.getPlayer(), SHINING_GEM) >= 50) //
				&& (getQuestItemsCount(qs.getPlayer(), SHINING_RED_GEM) >= 50))
			{
				qs.setCond(3, true);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		final QuestState qs = getQuestState(talker, true);
		String html = getNoQuestMsg(talker);
		if (qs.isCreated() && (npc.getId() == GUARD_PRAGA))
		{
			html = (talker.getLevel() >= MIN_LVL) ? "30333-01.htm" : "30333-02.htm";
		}
		else if (qs.isStarted())
		{
			if ((npc.getId() == GUARD_PRAGA) && hasQuestItems(talker, PATROLS_REPORT))
			{
				html = "30333-04.html";
			}
			else if (npc.getId() == MAGISTER_ROHMER)
			{
				switch (qs.getCond())
				{
					case 1:
					{
						html = "30344-01.html";
						break;
					}
					case 2:
					{
						html = "30344-02.html";
						break;
					}
					case 3:
					{
						html = "30344-03.html";
						break;
					}
				}
			}
		}
		return html;
	}
}
