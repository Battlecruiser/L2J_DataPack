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
package hellbound.Bernarde;

import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * @author DS
 */
public class Bernarde extends Quest
{
	private static final int BERNARDE = 32300;
	private static final int NATIVE_TRANSFORM = 101;
	private static final int HOLY_WATER = 9673;
	private static final int DARION_BADGE = 9674;
	private static final int TREASURE = 9684;
	
	private static final boolean isTransformed(L2PcInstance player)
	{
		return player.isTransformed() && (player.getTransformation().getId() == NATIVE_TRANSFORM);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ("HolyWater".equalsIgnoreCase(event))
		{
			if (HellboundManager.getInstance().getLevel() == 2)
			{
				if (player.getInventory().getInventoryItemCount(DARION_BADGE, -1, false) >= 5)
				{
					if (player.exchangeItemsById("Quest", npc, DARION_BADGE, 5, HOLY_WATER, 1, true))
					{
						return "32300-02b.htm";
					}
				}
			}
			event = "32300-02c.htm";
		}
		else if ("Treasure".equalsIgnoreCase(event))
		{
			if (HellboundManager.getInstance().getLevel() == 3)
			{
				if (player.getInventory().getInventoryItemCount(TREASURE, -1, false) > 0)
				{
					if (player.destroyItemByItemId("Quest", TREASURE, player.getInventory().getInventoryItemCount(TREASURE, -1, false), npc, true))
					{
						HellboundManager.getInstance().updateTrust((int) (player.getInventory().getInventoryItemCount(TREASURE, -1, false) * 1000), true);
						return "32300-02d.htm";
					}
				}
			}
			event = "32300-02e.htm";
		}
		else if ("rumors".equalsIgnoreCase(event))
		{
			event = "32300-" + HellboundManager.getInstance().getLevel() + "r.htm";
		}
		return event;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		
		switch (HellboundManager.getInstance().getLevel())
		{
			case 0:
			case 1:
				return isTransformed(player) ? "32300-01a.htm" : "32300-01.htm";
			case 2:
				return isTransformed(player) ? "32300-02.htm" : "32300-03.htm";
			case 3:
				return isTransformed(player) ? "32300-01c.htm" : "32300-03.htm";
			case 4:
				return isTransformed(player) ? "32300-01d.htm" : "32300-03.htm";
			default:
				return isTransformed(player) ? "32300-01f.htm" : "32300-03.htm";
		}
	}
	
	public Bernarde(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(BERNARDE);
		addStartNpc(BERNARDE);
		addTalkId(BERNARDE);
	}
	
	public static void main(String[] args)
	{
		new Bernarde(-1, Bernarde.class.getSimpleName(), "hellbound");
	}
}
