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
import com.l2jserver.gameserver.model.holders.QuestItemHolder;

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
	private static final QuestItemHolder[] LITTLE_DEVILS = new QuestItemHolder[]
	{
		new AdditionalQuestItemHolder(10321, 600, 1, 10408),
		new QuestItemHolder(10322, 10),
		new QuestItemHolder(10323, 10),
		new QuestItemHolder(10324, 5),
		new QuestItemHolder(10325, 5),
		new QuestItemHolder(10326, 370),
	};
	private static final QuestItemHolder[] LITTLE_ANGELS = new QuestItemHolder[]
	{
		new AdditionalQuestItemHolder(10315, 600, 1, 10408),
		new QuestItemHolder(10316, 10),
		new QuestItemHolder(10317, 10),
		new QuestItemHolder(10318, 5),
		new QuestItemHolder(10319, 5),
		new QuestItemHolder(10320, 370),
	};
	private static final Map<String, List<QuestItemHolder>> AGATHIONS = new HashMap<>();
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
			for (QuestItemHolder agathion : AGATHIONS.get(event))
			{
				chance3 += agathion.getChance();
				if ((chance >= chance2) && (chance2 < chance3))
				{
					if (takeAllItems(player, REQUIRED_ITEMS))
					{
						giveItems(player, agathion);
						htmltext = "30098-03.html";
						
						if (agathion instanceof AdditionalQuestItemHolder)
						{
							giveItems(player, ((AdditionalQuestItemHolder) agathion).getAdditionalId(), 1);
							htmltext = "30098-03a.html";
						}
					}
					else
					{
						htmltext = "30098-04.html";
					}
					break;
				}
				chance2 += agathion.getChance();
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Alexandria();
	}
	
	public static class AdditionalQuestItemHolder extends QuestItemHolder
	{
		private final int _additionalId;
		
		public AdditionalQuestItemHolder(int id, int chance, long count, int additionalId)
		{
			super(id, chance, count);
			_additionalId = additionalId;
		}
		
		public int getAdditionalId()
		{
			return _additionalId;
		}
	}
}
