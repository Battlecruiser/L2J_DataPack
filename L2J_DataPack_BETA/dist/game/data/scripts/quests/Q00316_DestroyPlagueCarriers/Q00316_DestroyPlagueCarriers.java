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
import com.l2jserver.gameserver.model.quest.State;
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
	
	private Q00316_DestroyPlagueCarriers(int questId, String name, String descr)
	{
		super(questId, name, descr);
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
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		String htmltext = null;
		switch (event)
		{
			case "30155-04.htm":
			{
				if (st.isCreated())
				{
					st.startQuest();
					htmltext = event;
				}
				break;
			}
			case "30155-08.html":
			{
				st.exitQuest(true, true);
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
			giveItemRandomly(killer, npc, item.getId(), 1, limit, 10.0 / item.getCount(), true);
		}
		return super.onKill(npc, killer, isSummon);
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
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (player.getRace() == PcRace.Elf) ? (player.getLevel() >= MIN_LEVEL) ? "30155-03.htm" : "30155-02.htm" : "30155-00.htm";
				break;
			}
			case State.STARTED:
			{
				if (hasAtLeastOneQuestItem(player, getRegisteredItemIds()))
				{
					final long wererars = st.getQuestItemsCount(WERERAT_FANG);
					final long foulclaws = st.getQuestItemsCount(VAROOL_FOULCLAW_FANG);
					st.giveAdena(((wererars * 30) + (foulclaws * 10000) + ((wererars + foulclaws) >= 10 ? 5000 : 0)), true);
					takeItems(player, -1, getRegisteredItemIds());
					htmltext = "30155-07.html";
				}
				else
				{
					htmltext = "30155-05.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00316_DestroyPlagueCarriers(316, Q00316_DestroyPlagueCarriers.class.getSimpleName(), "Destroy Plague Carriers");
	}
}