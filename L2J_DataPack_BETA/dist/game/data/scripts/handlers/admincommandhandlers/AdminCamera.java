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

import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Camera commands.
 * @author Zoey76
 */
public class AdminCamera implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_cam",
		"admin_camex",
		"admin_cam3"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if ((activeChar.getTarget() == null) || !activeChar.getTarget().isCharacter())
		{
			activeChar.sendPacket(SystemMessageId.TARGET_CANT_FOUND);
			return false;
		}
		
		final L2Character target = (L2Character) activeChar.getTarget();
		final String[] com = command.split(" ");
		switch (com[0])
		{
			case "admin_cam":
			{
				if (com.length != 12)
				{
					activeChar.sendMessage("Usage: //cam force angle1 angle2 time range duration relYaw relPitch isWide relAngle");
					return false;
				}
				Quest.specialCamera(activeChar, target, Integer.parseInt(com[1]), Integer.parseInt(com[2]), Integer.parseInt(com[3]), Integer.parseInt(com[4]), Integer.parseInt(com[5]), Integer.parseInt(com[6]), Integer.parseInt(com[7]), Integer.parseInt(com[8]), Integer.parseInt(com[9]), Integer.parseInt(com[10]));
				break;
			}
			case "admin_camex":
			{
				if (com.length != 10)
				{
					activeChar.sendMessage("Usage: //camex force angle1 angle2 time duration relYaw relPitch isWide relAngle");
					return false;
				}
				Quest.specialCameraEx(activeChar, target, Integer.parseInt(com[1]), Integer.parseInt(com[2]), Integer.parseInt(com[3]), Integer.parseInt(com[4]), Integer.parseInt(com[5]), Integer.parseInt(com[6]), Integer.parseInt(com[7]), Integer.parseInt(com[8]), Integer.parseInt(com[9]));
				break;
			}
			case "admin_cam3":
			{
				if (com.length != 12)
				{
					activeChar.sendMessage("Usage: //cam3 force angle1 angle2 time range duration relYaw relPitch isWide relAngle unk");
					return false;
				}
				Quest.specialCamera3(activeChar, target, Integer.parseInt(com[1]), Integer.parseInt(com[2]), Integer.parseInt(com[3]), Integer.parseInt(com[4]), Integer.parseInt(com[5]), Integer.parseInt(com[6]), Integer.parseInt(com[7]), Integer.parseInt(com[8]), Integer.parseInt(com[9]), Integer.parseInt(com[10]), Integer.parseInt(com[11]));
				break;
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