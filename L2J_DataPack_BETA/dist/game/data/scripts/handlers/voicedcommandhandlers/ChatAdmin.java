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
package handlers.voicedcommandhandlers;

import java.util.StringTokenizer;

import com.l2jserver.gameserver.datatables.AdminCommandAccessRights;
import com.l2jserver.gameserver.datatables.CharNameTable;
import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class ChatAdmin implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"banchat",
		"unbanchat"
	};
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IVoicedCommandHandler#useVoicedCommand(java.lang.String, com.l2jserver.gameserver.model.actor.instance.L2PcInstance, java.lang.String)
	 */
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		if (!AdminCommandAccessRights.getInstance().hasAccess(command, activeChar.getAccessLevel()))
			return false;
		
		if (command.equalsIgnoreCase(VOICED_COMMANDS[0])) // banchat
		{
			if (params == null)
			{
				activeChar.sendMessage("Usage: .banchat name [minutes]");
				return true;
			}
			StringTokenizer st = new StringTokenizer(params);
			if (st.hasMoreTokens())
			{
				String name = st.nextToken();
				int length = 0;
				if (st.hasMoreTokens())
				{
					try
					{
						length = Integer.parseInt(st.nextToken());
					}
					catch (NumberFormatException e)
					{
						activeChar.sendMessage("Wrong ban length !");
						return false;
					}
				}
				if (length < 0)
					length = 0;
				
				int objId = CharNameTable.getInstance().getIdByName(name);
				if (objId > 0)
				{
					L2PcInstance player = L2World.getInstance().getPlayer(objId);
					if (player == null || !player.isOnline())
					{
						activeChar.sendMessage("Player not online !");
						return false;
					}
					if (player.getPunishLevel() != L2PcInstance.PunishLevel.NONE)
					{
						activeChar.sendMessage("Player is already punished !");
						return false;
					}
					if (player == activeChar)
					{
						activeChar.sendMessage("You can't ban yourself !");
						return false;
					}
					if (player.isGM())
					{
						activeChar.sendMessage("You can't ban GM !");
						return false;
					}
					if (AdminCommandAccessRights.getInstance().hasAccess(command, player.getAccessLevel()))
					{
						activeChar.sendMessage("You can't ban moderator !");
						return false;
					}
					
					player.setPunishLevel(L2PcInstance.PunishLevel.CHAT, length);
					player.sendMessage("Chat banned by moderator " + activeChar.getName());
					
					if (length > 0)
						activeChar.sendMessage("Player " + player.getName() + " chat banned for " + length + " minutes.");
					else
						activeChar.sendMessage("Player " + player.getName() + " chat banned forever.");
				}
				else
				{
					activeChar.sendMessage("Player not found !");
					return false;
				}
			}
		}
		else if (command.equalsIgnoreCase(VOICED_COMMANDS[1])) //unbanchat
		{
			if (params == null)
			{
				activeChar.sendMessage("Usage: .unbanchat name");
				return true;
			}
			StringTokenizer st = new StringTokenizer(params);
			if (st.hasMoreTokens())
			{
				String name = st.nextToken();
				
				int objId = CharNameTable.getInstance().getIdByName(name);
				if (objId > 0)
				{
					L2PcInstance player = L2World.getInstance().getPlayer(objId);
					if (player == null || !player.isOnline())
					{
						activeChar.sendMessage("Player not online !");
						return false;
					}
					if (player.getPunishLevel() != L2PcInstance.PunishLevel.CHAT)
					{
						activeChar.sendMessage("Player is not chat banned !");
						return false;
					}
					
					player.setPunishLevel(L2PcInstance.PunishLevel.NONE, 0);
					
					activeChar.sendMessage("Player " + player.getName() + " chat unbanned.");
					player.sendMessage("Chat unbanned by moderator " + activeChar.getName());
				}
				else
				{
					activeChar.sendMessage("Player not found !");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IVoicedCommandHandler#getVoicedCommandList()
	 */
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}
