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
package handlers.admincommandhandlers;

import com.l2jserver.gameserver.SevenSigns;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.AutoSpawnHandler;
import com.l2jserver.gameserver.model.AutoSpawnHandler.AutoSpawnInstance;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Admin Command Handler for Mammon NPCs
 * @author Tempy, Zoey76
 */
public class AdminMammon implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_mammon_find",
		"admin_mammon_respawn",
	};
	
	private final boolean _isSealValidation = SevenSigns.getInstance().isSealValidationPeriod();
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		int teleportIndex = -1;
		final AutoSpawnInstance blackSpawnInst = AutoSpawnHandler.getInstance().getAutoSpawnInstance(SevenSigns.MAMMON_BLACKSMITH_ID, false);
		final AutoSpawnInstance merchSpawnInst = AutoSpawnHandler.getInstance().getAutoSpawnInstance(SevenSigns.MAMMON_MERCHANT_ID, false);
		
		if (command.startsWith("admin_mammon_find"))
		{
			try
			{
				if (command.length() > 17)
				{
					teleportIndex = Integer.parseInt(command.substring(18));
				}
			}
			catch (Exception NumberFormatException)
			{
				activeChar.sendMessage("Usage: //mammon_find [teleportIndex] (where 1 = Blacksmith, 2 = Merchant)");
				return false;
			}
			
			if (!_isSealValidation)
			{
				activeChar.sendPacket(SystemMessageId.SSQ_COMPETITION_UNDERWAY);
				return false;
			}
			
			if (blackSpawnInst != null)
			{
				final L2Npc[] blackInst = blackSpawnInst.getNPCInstanceList();
				if (blackInst.length > 0)
				{
					final int x1 = blackInst[0].getX(), y1 = blackInst[0].getY(), z1 = blackInst[0].getZ();
					activeChar.sendMessage("Blacksmith of Mammon: " + x1 + " " + y1 + " " + z1);
					if (teleportIndex == 1)
					{
						activeChar.teleToLocation(blackInst[0].getLocation(), true);
					}
				}
			}
			else
			{
				activeChar.sendMessage("Blacksmith of Mammon isn't registered for spawn.");
			}
			
			if (merchSpawnInst != null)
			{
				final L2Npc[] merchInst = merchSpawnInst.getNPCInstanceList();
				if (merchInst.length > 0)
				{
					final int x2 = merchInst[0].getX(), y2 = merchInst[0].getY(), z2 = merchInst[0].getZ();
					activeChar.sendMessage("Merchant of Mammon: " + x2 + " " + y2 + " " + z2);
					if (teleportIndex == 2)
					{
						activeChar.teleToLocation(merchInst[0].getLocation(), true);
					}
				}
			}
			else
			{
				activeChar.sendMessage("Merchant of Mammon isn't registered for spawn.");
			}
		}
		else if (command.startsWith("admin_mammon_respawn"))
		{
			if (!_isSealValidation)
			{
				activeChar.sendPacket(SystemMessageId.SSQ_COMPETITION_UNDERWAY);
				return true;
			}
			
			if (merchSpawnInst != null)
			{
				long merchRespawn = AutoSpawnHandler.getInstance().getTimeToNextSpawn(merchSpawnInst);
				activeChar.sendMessage("The Merchant of Mammon will respawn in " + (merchRespawn / 60000) + " minute(s).");
			}
			else
			{
				activeChar.sendMessage("Merchant of Mammon isn't registered for spawn.");
			}
			
			if (blackSpawnInst != null)
			{
				long blackRespawn = AutoSpawnHandler.getInstance().getTimeToNextSpawn(blackSpawnInst);
				activeChar.sendMessage("The Blacksmith of Mammon will respawn in " + (blackRespawn / 60000) + " minute(s).");
			}
			else
			{
				activeChar.sendMessage("Blacksmith of Mammon isn't registered for spawn.");
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
