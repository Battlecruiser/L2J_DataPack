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
package custom.KetraOrcSupport;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.WareHouseWithdrawalList;
import com.l2jserver.gameserver.util.Util;

/**
 * Ketra Orc Support AI.<br>
 * Original Jython script by Emperorc and Kerberos_20.
 * @authors Nyaran
 */
public class KetraOrcSupport extends Quest
{
	private static final String qn = "KetraOrcSupport";

	private static final int KADUN = 31370; // Hierarch
	private static final int WAHKAN = 31371; // Messenger
	private static final int ASEFA = 31372; // Soul Guide
	private static final int ATAN = 31373; // Grocer
	private static final int JAFF = 31374; // Warehouse Keeper
	private static final int JUMARA = 31375; // Trader
	private static final int KURFA = 31376; // Gate Keeper
	private static final int[] NPCS =
	{
		KADUN, WAHKAN, ASEFA, ATAN, JAFF, JUMARA, KURFA
	};

	private static final int HORN = 7186;

	private static final Map<Integer, BuffsData> BUFF = new HashMap<>();

	private class BuffsData
	{
		private int _skill;
		private int _cost;

		public BuffsData(int skill, int cost)
		{
			super();
			_skill = skill;
			_cost = cost;
		}

		public L2Skill getSkill()
		{
			return SkillTable.getInstance().getInfo(_skill, 1);
		}

		public int getCost()
		{
			return _cost;
		}
	}

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;

		int Alevel = player.getAllianceWithVarkaKetra();
		if (Util.isDigit(event) && BUFF.containsKey(Integer.parseInt(event)))
		{
			BuffsData buff = BUFF.get(Integer.parseInt(event));
			if (st.getQuestItemsCount(HORN) >= buff.getCost())
			{
				st.takeItems(HORN, buff.getCost());
				npc.setTarget(player);
				npc.doCast(buff.getSkill());
				npc.setCurrentHpMp(npc.getMaxHp(), npc.getMaxMp());
				htmltext = "31372-4.htm";
			}
		}
		else if (event.equals("Withdraw"))
		{
			if (player.getWarehouse().getSize() == 0)
				htmltext = "31374-0.htm";
			else
			{
				player.sendPacket(ActionFailed.STATIC_PACKET);
				player.setActiveWarehouse(player.getWarehouse());
				player.sendPacket(new WareHouseWithdrawalList(player, 1));
			}
		}
		else if (event.equals("Teleport"))
		{
			if (Alevel == 4)
				htmltext = "31376-4.htm";
			else if (Alevel == 5)
				htmltext = "31376-5.htm";
		}
		return htmltext;
	}

	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
			st = this.newQuestState(player);
		int npcId = npc.getNpcId();
		int Alevel = player.getAllianceWithVarkaKetra();
		if (npcId == KADUN)
		{
			if (Alevel > 0)
				htmltext = "31370-friend.htm";
			else
				htmltext = "31370-no.htm";
		}
		else if (npcId == WAHKAN)
		{
			if (Alevel > 0)
				htmltext = "31371-friend.htm";
			else
				htmltext = "31371-no.htm";
		}
		else if (npcId == ASEFA)
		{
			st.setState(State.STARTED);
			if (Alevel < 1)
				htmltext = "31372-3.htm";
			else if (Alevel < 3 && Alevel > 0)
				htmltext = "31372-1.htm";
			else if (Alevel > 2)
				if (st.hasQuestItems(HORN))
					htmltext = "31372-4.htm";
				else
					htmltext = "31372-2.htm";
		}
		else if (npcId == ATAN)
		{
			if (player.getKarma() >= 1)
				htmltext = "31373-pk.htm";
			else if (Alevel <= 0)
				htmltext = "31373-no.htm";
			else if (Alevel == 1 || Alevel == 2)
				htmltext = "31373-1.htm";
			else
				htmltext = "31373-2.htm";
		}
		else if (npcId == JAFF)
		{
			if (Alevel <= 0)
				htmltext = "31374-no.htm";
			else if (Alevel == 1)
				htmltext = "31374-1.htm";
			else if (player.getWarehouse().getSize() == 0)
				htmltext = "31374-3.htm";
			else if (Alevel == 2 || Alevel == 3)
				htmltext = "31374-2.htm";
			else
				htmltext = "31374-4.htm";
		}
		else if (npcId == JUMARA)
		{
			if (Alevel == 2)
				htmltext = "31375-1.htm";
			else if (Alevel == 3 || Alevel == 4)
				htmltext = "31375-2.htm";
			else if (Alevel == 5)
				htmltext = "31375-3.htm";
			else
				htmltext = "31375-no.htm";
		}
		else if (npcId == KURFA)
		{
			if (Alevel <= 0)
				htmltext = "31376-no.htm";
			else if (Alevel > 0 && Alevel < 4)
				htmltext = "31376-1.htm";
			else if (Alevel == 4)
				htmltext = "31376-2.htm";
			else
				htmltext = "31376-3.htm";
		}
		return htmltext;
	}

	public KetraOrcSupport(int id, String name, String descr)
	{
		super(id, name, descr);

		for (int i : NPCS)
			addFirstTalkId(i);
		addTalkId(ASEFA);
		addTalkId(KURFA);
		addTalkId(JAFF);
		addStartNpc(KURFA);
		addStartNpc(JAFF);

		BUFF.put(1, new BuffsData(4359, 2)); // Focus: Requires 2 Buffalo Horns
		BUFF.put(2, new BuffsData(4360, 2)); // Death Whisper: Requires 2 Buffalo Horns
		BUFF.put(3, new BuffsData(4345, 3)); // Might: Requires 3 Buffalo Horns
		BUFF.put(4, new BuffsData(4355, 3)); // Acumen: Requires 3 Buffalo Horns
		BUFF.put(5, new BuffsData(4352, 3)); // Berserker: Requires 3 Buffalo Horns
		BUFF.put(6, new BuffsData(4354, 3)); // Vampiric Rage: Requires 3 Buffalo Horns
		BUFF.put(7, new BuffsData(4356, 6)); // Empower: Requires 6 Buffalo Horns
		BUFF.put(8, new BuffsData(4357, 6)); // Haste: Requires 6 Buffalo Horns
	}

	public static void main(String args[])
	{
		new KetraOrcSupport(-1, qn, "custom");
	}
}
