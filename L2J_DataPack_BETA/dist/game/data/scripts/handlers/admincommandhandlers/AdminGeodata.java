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

import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author -Nemesiss-
 * @author FBIagent
 */
public class AdminGeodata implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_geo_pos",
		"admin_geo_spawn_pos",
		"admin_geo_can_move",
		"admin_geo_can_see"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if ("admin_geo_pos".equals(command))
		{
			int worldX = activeChar.getX();
			int worldY = activeChar.getY();
			int worldZ = activeChar.getZ();
			int geoX = GeoData.getInstance().getGeoX(worldX);
			int geoY = GeoData.getInstance().getGeoY(worldY);
			
			if (GeoData.getInstance().hasGeoPos(geoX, geoY))
			{
				activeChar.sendMessage("WorldX: " + worldX + ", WorldY: " + worldY + ", WorldZ: " + worldZ + ", GeoX: " + geoX + ", GeoY: " + geoY + ", GeoZ: " + GeoData.getInstance().getNearestZ(geoX, geoY, worldZ));
			}
			else
			{
				activeChar.sendMessage("There is no geodata at this position.");
			}
		}
		else if ("admin_geo_spawn_pos".equals(command))
		{
			int worldX = activeChar.getX();
			int worldY = activeChar.getY();
			int worldZ = activeChar.getZ();
			int geoX = GeoData.getInstance().getGeoX(worldX);
			int geoY = GeoData.getInstance().getGeoY(worldY);
			
			if (GeoData.getInstance().hasGeoPos(geoX, geoY))
			{
				activeChar.sendMessage("WorldX: " + worldX + ", WorldY: " + worldY + ", WorldZ: " + worldZ + ", GeoX: " + geoX + ", GeoY: " + geoY + ", GeoZ: " + GeoData.getInstance().getSpawnHeight(worldX, worldY, worldZ, worldZ));
			}
			else
			{
				activeChar.sendMessage("There is no geodata at this position.");
			}
		}
		else if ("admin_geo_can_move".equals(command))
		{
			L2Object target = activeChar.getTarget();
			if (target != null)
			{
				if (GeoData.getInstance().canSeeTarget(activeChar, target))
				{
					activeChar.sendMessage("Can move beeline.");
				}
				else
				{
					activeChar.sendMessage("Can not move beeline!");
				}
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			}
		}
		else if ("admin_geo_can_see".equals(command))
		{
			L2Object target = activeChar.getTarget();
			if (target != null)
			{
				if (GeoData.getInstance().canSeeTarget(activeChar, target))
				{
					activeChar.sendMessage("Can see target.");
				}
				else
				{
					activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.CANT_SEE_TARGET));
				}
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			}
		}
		else
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
