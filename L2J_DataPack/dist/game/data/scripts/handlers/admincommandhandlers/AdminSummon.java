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
package handlers.admincommandhandlers;

import java.util.logging.Logger;

import com.l2jserver.gameserver.datatables.AdminTable;
import com.l2jserver.gameserver.handler.AdminCommandHandler;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author poltomb
 */
public class AdminSummon implements IAdminCommandHandler
{
	private static final Logger _log = Logger.getLogger(AdminSummon.class.getName());
	
	public static final String[] ADMIN_COMMANDS =
	{
		"admin_summon"
	};
	
	@Override
	public String[] getAdminCommandList()
	{
		
		return ADMIN_COMMANDS;
	}
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		int id;
		int count = 1;
		String[] data = command.split(" ");
		try
		{
			id = Integer.parseInt(data[1]);
			if (data.length > 2)
			{
				count = Integer.parseInt(data[2]);
			}
		}
		catch (NumberFormatException nfe)
		{
			activeChar.sendMessage("Incorrect format for command 'summon'");
			return false;
		}
		
		String subCommand;
		if (id < 1000000)
		{
			subCommand = "admin_create_item";
			if (!AdminTable.getInstance().hasAccess(subCommand, activeChar.getAccessLevel()))
			{
				activeChar.sendMessage("You don't have the access right to use this command!");
				_log.warning("Character " + activeChar.getName() + " tryed to use admin command " + subCommand + ", but have no access to it!");
				return false;
			}
			IAdminCommandHandler ach = AdminCommandHandler.getInstance().getHandler(subCommand);
			ach.useAdminCommand(subCommand + " " + id + " " + count, activeChar);
		}
		else
		{
			subCommand = "admin_spawn_once";
			if (!AdminTable.getInstance().hasAccess(subCommand, activeChar.getAccessLevel()))
			{
				activeChar.sendMessage("You don't have the access right to use this command!");
				_log.warning("Character " + activeChar.getName() + " tryed to use admin command " + subCommand + ", but have no access to it!");
				return false;
			}
			IAdminCommandHandler ach = AdminCommandHandler.getInstance().getHandler(subCommand);
			
			activeChar.sendMessage("This is only a temporary spawn.  The mob(s) will NOT respawn.");
			id -= 1000000;
			ach.useAdminCommand(subCommand + " " + id + " " + count, activeChar);
		}
		return true;
	}
}