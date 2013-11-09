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
package quests.Q00316_DestroyPlagueCarriers;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.enums.PcRace;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Destroy Plague Carriers (316)
 * @author ivantotov
 */
public final class Q00316_DestroyPlagueCarriers extends Quest
{
	// NPC
	private static final int ELLENIA = 30155;
	// Items
	private static final int WERERAT_FANG = 1042;
	private static final int VAROOL_FOULCLAW_FANG = 1043;
	// Misc
	private static final int MIN_LEVEL = 18;
	// Monsters
	private static final int VAROOL_FOULCLAW = 27020;
	private static final Map<Integer, ItemHolder> MONSTER_DROPS = new HashMap<>();
	static
	{
		MONSTER_DROPS.put(20040, new ItemHolder(WERERAT_FANG, 5)); // Sukar Wererat
		MONSTER_DROPS.put(20047, new ItemHolder(WERERAT_FANG, 5)); // Sukar Wererat Leader
		MONSTER_DROPS.put(VAROOL_FOULCLAW, new ItemHolder(VAROOL_FOULCLAW_FANG, 7)); // Varool Foulclaw
	}
	
	private Q00316_DestroyPlagueCarriers()
	{
		super(316, Q00316_DestroyPlagueCarriers.class.getSimpleName(), "Destroy Plague Carriers");
		addStartNpc(ELLENIA);
		addTalkId(ELLENIA);
		addAttackId(VAROOL_FOULCLAW);
		addKillId(MONSTER_DROPS.keySet());
		registerQuestItems(WERERAT_FANG, VAROOL_FOULCLAW_FANG);
	}
	
	@Override
	public boolean checkPartyMember(QuestState qs, L2Npc npc)
	{
		return ((npc.getId() != VAROOL_FOULCLAW) || !qs.hasQuestItems(VAROOL_FOULCLAW_FANG));
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		String htmltext = null;
		if (qs == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "30155-04.htm":
			{
				if (qs.isCreated())
				{
					qs.startQuest();
					htmltext = event;
				}
				break;
			}
			case "30155-08.html":
			{
				qs.exitQuest(true, true);
				htmltext = event;
				break;
			}
			case "30155-09.html":
			{
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (npc.isScriptValue(0))
		{
			npc.broadcastPacket(new NpcSay(npc, Say2.NPC_ALL, NpcStringId.WHY_DO_YOU_OPPRESS_US_SO));
			npc.setScriptValue(1);
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getRandomPartyMemberState(killer, -1, 3, npc);
		if (qs != null)
		{
			final ItemHolder item = MONSTER_DROPS.get(npc.getId());
			final int limit = (npc.getId() == VAROOL_FOULCLAW ? 1 : 0);
			giveItemRandomly(qs.getPlayer(), npc, item.getId(), 1, limit, 10.0 / item.getCount(), true);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (qs == null)
		{
			return htmltext;
		}
		
		if (qs.isCreated())
		{
			if (player.getRace() != PcRace.Elf)
			{
				htmltext = "30155-00.htm";
			}
			else if (player.getLevel() < MIN_LEVEL)
			{
				htmltext = "30155-02.htm";
			}
			else
			{
				htmltext = "30155-03.htm";
			}
		}
		else if (qs.isStarted())
		{
			if (hasAtLeastOneQuestItem(player, getRegisteredItemIds()))
			{
				final long wererars = getQuestItemsCount(player, WERERAT_FANG);
				final long foulclaws = getQuestItemsCount(player, VAROOL_FOULCLAW_FANG);
				giveAdena(player, ((wererars * 30) + (foulclaws * 10000) + ((wererars + foulclaws) >= 10 ? 5000 : 0)), true);
				takeItems(player, -1, getRegisteredItemIds());
				htmltext = "30155-07.html";
			}
			else
			{
				htmltext = "30155-05.html";
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00316_DestroyPlagueCarriers();
	}
}