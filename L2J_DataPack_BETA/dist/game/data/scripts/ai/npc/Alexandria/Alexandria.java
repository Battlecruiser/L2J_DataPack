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
package ai.npc.Alexandria;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;

/**
 * Alexandria (Armor Merchant) AI.
 * @author xban1x
 */
public final class Alexandria extends AbstractNpcAI
{
	// NPC
	private static final int ALEXANDRIA = 30098;
	// Items
	private static final ItemHolder[] REQUIRED_ITEMS = new ItemHolder[]
	{
		new ItemHolder(57, 3550000),
		new ItemHolder(5094, 400),
		new ItemHolder(6471, 200),
		new ItemHolder(9814, 40),
		new ItemHolder(9815, 30),
		new ItemHolder(9816, 50),
		new ItemHolder(9817, 50),
	};
	// Agathions
	private static final ItemHolder[] LITTLE_DEVILS = new ItemHolder[]
	{
		new ItemHolder(10321, 10408, 600),
		new ItemHolder(10322, 0, 10),
		new ItemHolder(10323, 0, 10),
		new ItemHolder(10324, 0, 5),
		new ItemHolder(10325, 0, 5),
		new ItemHolder(10326, 0, 370),
	};
	private static final ItemHolder[] LITTLE_ANGELS = new ItemHolder[]
	{
		new ItemHolder(10315, 10408, 600),
		new ItemHolder(10316, 0, 10),
		new ItemHolder(10317, 0, 10),
		new ItemHolder(10318, 0, 5),
		new ItemHolder(10319, 0, 5),
		new ItemHolder(10320, 0, 370),
	};
	private static final Map<String, List<ItemHolder>> AGATHIONS = new HashMap<>();
	static
	{
		AGATHIONS.put("littleAngel", Arrays.asList(LITTLE_ANGELS));
		AGATHIONS.put("littleDevil", Arrays.asList(LITTLE_DEVILS));
	}
	
	private Alexandria()
	{
		super(Alexandria.class.getSimpleName(), "ai/npc");
		addStartNpc(ALEXANDRIA);
		addTalkId(ALEXANDRIA);
		addFirstTalkId(ALEXANDRIA);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (event.equals("30098-02.html"))
		{
			htmltext = event;
		}
		else if (AGATHIONS.containsKey(event))
		{
			final int chance = getRandom(1000);
			int chance2 = 0;
			int chance3 = 0;
			for (ItemHolder agathion : AGATHIONS.get(event))
			{
				chance3 += agathion.getCount();
				if ((chance >= chance2) && (chance2 < chance3))
				{
					boolean hasAllItems = true;
					for (ItemHolder item : REQUIRED_ITEMS)
					{
						if (getQuestItemsCount(player, item.getId()) < item.getCount())
						{
							hasAllItems = false;
							break;
						}
					}
					if (hasAllItems)
					{
						for (ItemHolder item : REQUIRED_ITEMS)
						{
							takeItems(player, item);
						}
						giveItems(player, agathion.getId(), 1);
						if (agathion.getObjectId() == 0)
						{
							htmltext = "30098-03.html";
						}
						else
						{
							giveItems(player, agathion.getObjectId(), 1);
							htmltext = "30098-03a.html";
						}
					}
					else
					{
						htmltext = "30098-04.html";
					}
					break;
				}
				chance2 += agathion.getCount();
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Alexandria();
	}
}
