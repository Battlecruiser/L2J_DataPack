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

import com.l2jserver.gameserver.Shutdown;
import com.l2jserver.gameserver.handler.ITelnetHandler;

/**
 * @author UnAfraid
 */
public class ServerHandler implements ITelnetHandler
{
	private final String[] _commands =
	{
		"shutdown", 
		"restart", 
		"abort"
	};
	
	@Override
	public boolean useCommand(String command, PrintWriter _print, Socket _cSocket, int _uptime)
	{
		if (command.startsWith("shutdown"))
		{
			try
			{
				int val = Integer.parseInt(command.substring(9));
				Shutdown.getInstance().startTelnetShutdown(_cSocket.getInetAddress().getHostAddress(), val, false);
				_print.println("Server Will Shutdown In " + val + " Seconds!");
				_print.println("Type \"abort\" To Abort Shutdown!");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				_print.println("Please Enter * amount of seconds to shutdown!");
			}
			catch (Exception NumberFormatException)
			{
				_print.println("Numbers Only!");
			}
		}
		else if (command.startsWith("restart"))
		{
			try
			{
				int val = Integer.parseInt(command.substring(8));
				Shutdown.getInstance().startTelnetShutdown(_cSocket.getInetAddress().getHostAddress(), val, true);
				_print.println("Server Will Restart In " + val + " Seconds!");
				_print.println("Type \"abort\" To Abort Restart!");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				_print.println("Please Enter * amount of seconds to restart!");
			}
			catch (Exception NumberFormatException)
			{
				_print.println("Numbers Only!");
			}
		}
		else if (command.startsWith("abort"))
		{
			Shutdown.getInstance().telnetAbort(_cSocket.getInetAddress().getHostAddress());
			_print.println("OK! - Shutdown/Restart Aborted.");
		}
		return false;
	}
	
	@Override
	public String[] getCommandList()
	{
		return _commands;
	}
}
