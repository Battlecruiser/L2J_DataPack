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
package ai.npc.Teleports.ToIVortex;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;

/**
 * Tower of Insolence Vortex teleport AI.<br>
 * Updated to High Five by pmq.
 * @author Plim
 */
public class ToIVortex extends AbstractNpcAI
{
	// NPCs
	private static final int KEPLON = 30949;
	private static final int EUCLIE = 30950;
	private static final int PITHGON = 30951;
	private static final int DIMENSION_VORTEX_1 = 30952;
	private static final int DIMENSION_VORTEX_2 = 30953;
	private static final int DIMENSION_VORTEX_3 = 30954;
	// Items
	private static final int GREEN_DIMENSION_STONE = 4401;
	private static final int BLUE_DIMENSION_STONE = 4402;
	private static final int RED_DIMENSION_STONE = 4403;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		int npcId = npc.getId();
		
		switch (event)
		{
			case "1":
			{
				// 1st Floor
				if (hasQuestItems(player, GREEN_DIMENSION_STONE))
				{
					takeItems(player, GREEN_DIMENSION_STONE, 1);
					player.teleToLocation(114356, 13423, -5096, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "2":
			{
				// 2nd Floor
				if (hasQuestItems(player, GREEN_DIMENSION_STONE))
				{
					takeItems(player, GREEN_DIMENSION_STONE, 1);
					player.teleToLocation(114666, 13380, -3608, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "3":
			{
				// 3rd Floor
				if (hasQuestItems(player, GREEN_DIMENSION_STONE))
				{
					takeItems(player, GREEN_DIMENSION_STONE, 1);
					player.teleToLocation(111982, 16028, -2120, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "4":
			{
				// 4th Floor
				if (hasQuestItems(player, BLUE_DIMENSION_STONE))
				{
					takeItems(player, BLUE_DIMENSION_STONE, 1);
					player.teleToLocation(114636, 13413, -640, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "5":
			{
				// 5th Floor
				if (hasQuestItems(player, BLUE_DIMENSION_STONE))
				{
					takeItems(player, BLUE_DIMENSION_STONE, 1);
					player.teleToLocation(114152, 19902, 928, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "6":
			{
				// 6th Floor
				if (hasQuestItems(player, BLUE_DIMENSION_STONE))
				{
					takeItems(player, BLUE_DIMENSION_STONE, 1);
					player.teleToLocation(117131, 16044, 1944, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "7":
			{
				// 7th Floor
				if (hasQuestItems(player, RED_DIMENSION_STONE))
				{
					takeItems(player, RED_DIMENSION_STONE, 1);
					player.teleToLocation(113026, 17687, 2952, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "8":
			{
				// 8th Floor
				if (hasQuestItems(player, RED_DIMENSION_STONE))
				{
					takeItems(player, RED_DIMENSION_STONE, 1);
					player.teleToLocation(115571, 13723, 3960, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "9":
			{
				// 9th Floor
				if (hasQuestItems(player, RED_DIMENSION_STONE))
				{
					takeItems(player, RED_DIMENSION_STONE, 1);
					player.teleToLocation(114649, 14144, 4976, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "10":
			{
				// 10 Floor
				if (hasQuestItems(player, RED_DIMENSION_STONE))
				{
					takeItems(player, RED_DIMENSION_STONE, 1);
					player.teleToLocation(118507, 16605, 5984, true);
				}
				else
				{
					return "no-stones.htm";
				}
				break;
			}
			case "GREEN":
			{
				if (player.getAdena() >= 10000)
				{
					takeItems(player, PcInventory.ADENA_ID, 10000);
					giveItems(player, GREEN_DIMENSION_STONE, 1);
				}
				else
				{
					return npcId + "no-adena.htm";
				}
				break;
			}
			case "BLUE":
			{
				if (player.getAdena() >= 10000)
				{
					takeItems(player, PcInventory.ADENA_ID, 10000);
					giveItems(player, BLUE_DIMENSION_STONE, 1);
				}
				else
				{
					return npcId + "no-adena.htm";
				}
				break;
			}
			case "RED":
			{
				if (player.getAdena() >= 10000)
				{
					takeItems(player, PcInventory.ADENA_ID, 10000);
					giveItems(player, RED_DIMENSION_STONE, 1);
				}
				else
				{
					return npcId + "no-adena.htm";
				}
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	private ToIVortex(String name, String descr)
	{
		super(name, descr);
		addStartNpc(KEPLON, EUCLIE, PITHGON, DIMENSION_VORTEX_1, DIMENSION_VORTEX_2, DIMENSION_VORTEX_3);
		addTalkId(KEPLON, EUCLIE, PITHGON, DIMENSION_VORTEX_1, DIMENSION_VORTEX_2, DIMENSION_VORTEX_3);
	}
	
	public static void main(String[] args)
	{
		new ToIVortex(ToIVortex.class.getSimpleName(), "ai/npc/Teleports");
	}
}
