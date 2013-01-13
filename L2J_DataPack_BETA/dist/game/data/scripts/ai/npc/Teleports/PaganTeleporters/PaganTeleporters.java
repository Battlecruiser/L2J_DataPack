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
package ai.npc.Teleports.PaganTeleporters;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Pagan Temple teleport AI.<br>
 * Original Jython script by BiTi.
 * @author Plim
 */
public class PaganTeleporters extends AbstractNpcAI
{
	// NPCs
	private static final int TRIOLS_MIRROR_1 = 32039;
	private static final int TRIOLS_MIRROR_2 = 32040;
	
	private static final int[] NPCS =
	{
		32034,
		32035,
		32036,
		32037,
		32039,
		32040
	};
	
	// Items
	private static final int VISITORS_MARK = 8064;
	private static final int FADED_VISITORS_MARK = 8065;
	private static final int PAGANS_MARK = 8067;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("Close_Door1"))
		{
			DoorTable.getInstance().getDoor(19160001).closeMe();
		}
		else if (event.equalsIgnoreCase("Close_Door2"))
		{
			DoorTable.getInstance().getDoor(19160010).closeMe();
			DoorTable.getInstance().getDoor(19160011).closeMe();
		}
		return "";
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.getNpcId() == TRIOLS_MIRROR_1)
		{
			player.teleToLocation(-12766, -35840, -10856);
		}
		else if (npc.getNpcId() == TRIOLS_MIRROR_2)
		{
			player.teleToLocation(36640, -51218, 718);
		}
		return "";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		switch (npc.getNpcId())
		{
			case 32034:
			{
				if (!hasQuestItems(player, VISITORS_MARK) && !hasQuestItems(player, FADED_VISITORS_MARK) && !hasQuestItems(player, PAGANS_MARK))
				{
					return "noItem.htm";
				}
				DoorTable.getInstance().getDoor(19160001).openMe();
				startQuestTimer("Close_Door1", 10000, null, null);
				return "FadedMark.htm";
			}
			case 32035:
			{
				DoorTable.getInstance().getDoor(19160001).openMe();
				startQuestTimer("Close_Door1", 10000, null, null);
				return "FadedMark.htm";
			}
			case 32036:
			{
				if (!hasQuestItems(player, PAGANS_MARK))
				{
					return "noMark.htm";
				}
				startQuestTimer("Close_Door2", 10000, null, null);
				DoorTable.getInstance().getDoor(19160010).openMe();
				DoorTable.getInstance().getDoor(19160011).openMe();
				return "openDoor.htm";
			}
			case 32037:
			{
				DoorTable.getInstance().getDoor(19160010).openMe();
				DoorTable.getInstance().getDoor(19160011).openMe();
				startQuestTimer("Close_Door2", 10000, null, null);
				return "FadedMark.htm";
			}
		}
		return super.onTalk(npc, player);
	}
	
	private PaganTeleporters(String name, String descr)
	{
		super(name, descr);
		addStartNpc(NPCS);
		addTalkId(NPCS);
		addFirstTalkId(TRIOLS_MIRROR_1, TRIOLS_MIRROR_2);
	}
	
	public static void main(String[] args)
	{
		new PaganTeleporters(PaganTeleporters.class.getSimpleName(), "ai/npc/Teleports/");
	}
}
