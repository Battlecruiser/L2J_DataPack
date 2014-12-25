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
package handlers.admincommandhandlers;

import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;

public class AdminDebug implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_debug"
	};
	
	@Override
	public final boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		String[] commandSplit = command.split(" ");
		if (ADMIN_COMMANDS[0].equalsIgnoreCase(commandSplit[0]))
		{
			L2Object target;
			if (commandSplit.length > 1)
			{
				target = L2World.getInstance().getPlayer(commandSplit[1].trim());
				if (target == null)
				{
					activeChar.sendPacket(SystemMessageId.THAT_PLAYER_IS_NOT_ONLINE);
					return true;
				}
			}
			else
			{
				target = activeChar.getTarget();
			}
			
			if (target instanceof L2Character)
			{
				setDebug(activeChar, (L2Character) target);
			}
			else
			{
				setDebug(activeChar, activeChar);
			}
		}
		return true;
	}
	
	@Override
	public final String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private final void setDebug(L2PcInstance activeChar, L2Character target)
	{
		if (target.isDebug())
		{
			target.setDebug(null);
			activeChar.sendMessage("Stop debugging " + target.getName());
		}
		else
		{
			target.setDebug(activeChar);
			activeChar.sendMessage("Start debugging " + target.getName());
		}
	}
}
