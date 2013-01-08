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
package handlers.telnethandlers;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import com.l2jserver.gameserver.Announcements;
import com.l2jserver.gameserver.datatables.AdminTable;
import com.l2jserver.gameserver.handler.ITelnetHandler;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;

/**
 * @author UnAfraid
 */
public class ChatsHandler implements ITelnetHandler
{
	private final String[] _commands =
	{
		"announce",
		"msg",
		"gmchat"
	};
	
	@Override
	public boolean useCommand(String command, PrintWriter _print, Socket _cSocket, int _uptime)
	{
		if (command.startsWith("announce"))
		{
			try
			{
				command = command.substring(9);
				Announcements.getInstance().announceToAll(command);
				_print.println("Announcement Sent!");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				_print.println("Please Enter Some Text To Announce!");
			}
		}
		else if (command.startsWith("msg"))
		{
			try
			{
				String val = command.substring(4);
				StringTokenizer st = new StringTokenizer(val);
				String name = st.nextToken();
				String message = val.substring(name.length() + 1);
				L2PcInstance reciever = L2World.getInstance().getPlayer(name);
				CreatureSay cs = new CreatureSay(0, Say2.TELL, "Telnet Priv", message);
				if (reciever != null)
				{
					reciever.sendPacket(cs);
					_print.println("Telnet Priv->" + name + ": " + message);
					_print.println("Message Sent!");
				}
				else
				{
					_print.println("Unable To Find Username: " + name);
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				_print.println("Please Enter Some Text!");
			}
		}
		else if (command.startsWith("gmchat"))
		{
			try
			{
				command = command.substring(7);
				CreatureSay cs = new CreatureSay(0, Say2.ALLIANCE, "Telnet GM Broadcast from " + _cSocket.getInetAddress().getHostAddress(), command);
				AdminTable.getInstance().broadcastToGMs(cs);
				_print.println("Your Message Has Been Sent To " + getOnlineGMS() + " GM(s).");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				_print.println("Please Enter Some Text To Announce!");
			}
		}
		return false;
	}
	
	private int getOnlineGMS()
	{
		return AdminTable.getInstance().getAllGms(true).size();
	}
	
	@Override
	public String[] getCommandList()
	{
		return _commands;
	}
}
