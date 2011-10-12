/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.admincommandhandlers;

import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author
 */
public class AdminRide implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_ride_horse",
		"admin_ride_bike",
		"admin_ride_wyvern",
		"admin_ride_strider",
		"admin_unride_wyvern",
		"admin_unride_strider",
		"admin_unride",
		"admin_ride_wolf",
		"admin_unride_wolf",
	};
	private int _petRideId;
	
	private static final int PURPLE_MANED_HORSE_TRANSFORMATION_ID = 106;
	
	private static final int JET_BIKE_TRANSFORMATION_ID = 20001;
	
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		L2PcInstance player = getRideTarget(activeChar);
		if(player == null)
			return false;
		
		if (command.startsWith("admin_ride"))
		{		
			if (player.isMounted() || player.getPet() != null)
			{
				activeChar.sendMessage("Target already have a pet.");
				return false;
			}
			if (command.startsWith("admin_ride_wyvern"))
			{
				_petRideId = 12621;
			}
			else if (command.startsWith("admin_ride_strider"))
			{
				_petRideId = 12526;
			}
			else if (command.startsWith("admin_ride_wolf"))
			{
				_petRideId = 16041;
			}
			else if (command.startsWith("admin_ride_horse")) // handled using transformation
			{
				if (player.isTransformed() || player.isInStance())
					//FIXME: Wrong Message
					activeChar.sendMessage("Player cannot mount a steed while transformed.");
				else
					TransformationManager.getInstance().transformPlayer(PURPLE_MANED_HORSE_TRANSFORMATION_ID, player);
				
				return true;
			}
			else if (command.startsWith("admin_ride_bike")) // handled using transformation
			{
				if (player.isTransformed() || player.isInStance())
					//FIXME: Wrong Message
					activeChar.sendMessage("Player cannot mount a steed while transformed.");
				else
					TransformationManager.getInstance().transformPlayer(JET_BIKE_TRANSFORMATION_ID, player);
				
				return true;
			}
			else
			{
				activeChar.sendMessage("Command '" + command + "' not recognized");
				return false;
			}
			
			player.mount(_petRideId, 0, false);
			
			return false;
		}
		else if (command.startsWith("admin_unride"))
		{
			if (player.getTransformationId() == PURPLE_MANED_HORSE_TRANSFORMATION_ID)
				player.untransform();
			
			if (player.getTransformationId() == JET_BIKE_TRANSFORMATION_ID)
				player.untransform();
			else
				player.dismount();
		}
		return true;
	}
	
	private L2PcInstance getRideTarget(L2PcInstance activeChar)
	{
		L2PcInstance player = null;
		
		if(activeChar.getTarget() == null
			|| activeChar.getTarget().getObjectId() == activeChar.getObjectId()
			|| !(activeChar.getTarget() instanceof L2PcInstance))
			player = activeChar;
		else
			player = (L2PcInstance)activeChar.getTarget();
		
		return player;
	}
	
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
}
