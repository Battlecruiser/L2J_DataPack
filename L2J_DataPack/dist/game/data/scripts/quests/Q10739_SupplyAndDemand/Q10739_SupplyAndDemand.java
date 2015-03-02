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
package quests.Q10739_SupplyAndDemand;

import quests.Q10738_AnInnerBeauty.Q10738_AnInnerBeauty;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * @author Sdw
 */
public class Q10739_SupplyAndDemand extends Quest
{
	// NPC's
	private static final int EVNA = 33935;
	private static final int DENYA = 33934;
	private static final int PELU = 33936;
	private static final int CERI = 33937;
	private static final int SIVANTHE = 33951;
	// Items
	private static final ItemHolder WEAPON_SUPPLY_BOX = new ItemHolder(39522, 1);
	private static final ItemHolder ARMOR_SUPPLY_BOX = new ItemHolder(39523, 1);
	private static final ItemHolder GROCERY_SUPPLY_BOX = new ItemHolder(39524, 1);
	private static final ItemHolder ACCESSORY_SUPPLY_BOX = new ItemHolder(39525, 1);
	private static final ItemHolder LEATHER_SHIRT = new ItemHolder(21, 1);
	private static final ItemHolder LEATHER_PANTS = new ItemHolder(29, 1);
	private static final ItemHolder APPRENTICE_EARRING = new ItemHolder(112, 2);
	private static final ItemHolder NECKLACE_OF_KNOWNLEDGE = new ItemHolder(906, 1);
	// Misc
	private static final int MIN_LEVEL = 6;
	private static final int MAX_LEVEL = 20;
	
	public Q10739_SupplyAndDemand()
	{
		super(10739, Q10739_SupplyAndDemand.class.getSimpleName(), "Supply And Demand");
		addStartNpc(EVNA);
		addTalkId(EVNA, DENYA, PELU, CERI, SIVANTHE);
		registerQuestItems(WEAPON_SUPPLY_BOX.getId(), ARMOR_SUPPLY_BOX.getId(), GROCERY_SUPPLY_BOX.getId(), ACCESSORY_SUPPLY_BOX.getId());
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "33935-05.htm");
		addCondRace(Race.ERTHEIA, "33935-05.htm");
		addCondCompletedQuest(Q10738_AnInnerBeauty.class.getSimpleName(), "33935-05.htm");
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "33935-03.htm":
			{
				qs.startQuest();
				giveItems(player, WEAPON_SUPPLY_BOX);
				htmltext = event;
				break;
			}
			case "33934-02.htm":
			{
				if (qs.isCond(1))
				{
					qs.setCond(2, true);
					giveItems(player, ARMOR_SUPPLY_BOX);
					htmltext = event;
				}
				break;
			}
			case "33936-02.htm":
			{
				if (qs.isCond(2))
				{
					qs.setCond(3, true);
					giveItems(player, GROCERY_SUPPLY_BOX);
					htmltext = event;
				}
				break;
			}
			case "33937-02.htm":
			{
				if (qs.isCond(3))
				{
					qs.setCond(4, true);
					giveItems(player, ACCESSORY_SUPPLY_BOX);
					htmltext = event;
				}
				break;
			}
			case "33935-02.htm":
			{
				htmltext = event;
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		switch (npc.getId())
		{
			case EVNA:
				if (qs.isCreated())
				{
					htmltext = "33935-01.htm";
				}
				else if (qs.isStarted())
				{
					htmltext = "33935-04.htm";
				}
				break;
			
			case DENYA:
				if (qs.isCond(1))
				{
					htmltext = "33934-01.htm";
				}
				else if (qs.isCond(2))
				{
					htmltext = "33934-03.htm";
				}
				break;
			
			case PELU:
				if (qs.isCond(2))
				{
					htmltext = "33936-01.htm";
				}
				else if (qs.isCond(3))
				{
					htmltext = "33936-03.htm";
				}
				break;
			
			case CERI:
				if (qs.isCond(3))
				{
					htmltext = "33937-01.htm";
				}
				else if (qs.isCond(4))
				{
					htmltext = "33937-03.htm";
				}
				break;
			
			case SIVANTHE:
				if (qs.isCond(4))
				{
					giveItems(player, LEATHER_SHIRT);
					giveItems(player, LEATHER_PANTS);
					giveItems(player, APPRENTICE_EARRING);
					giveItems(player, NECKLACE_OF_KNOWNLEDGE);
					giveAdena(player, 1400, true);
					addExpAndSp(player, 8136, 0);
					qs.exitQuest(false, true);
					showOnScreenMsg(player, NpcStringId.CHECK_YOUR_EQUIPMENT_IN_YOUR_INVENTORY, ExShowScreenMessage.TOP_CENTER, 4500);
					htmltext = "33951-01.htm";
				}
				break;
		}
		
		return htmltext;
	}
}
