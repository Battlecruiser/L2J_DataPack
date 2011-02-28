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
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Zoey76
 */
public class AdminMessages implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_msg"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_msg"))
		{
			try
			{
				activeChar.sendPacket(SystemMessage.getSystemMessage(Integer.parseInt(command.substring(10).trim())));
				return true;
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Command format: //msg <SYSTEM_MSG_ID>");
			}
		}
		return false;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}