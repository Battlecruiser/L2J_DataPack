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

import com.l2jserver.gameserver.handler.ITelnetHandler;

/**
 * @author UnAfraid
 */
public class HelpHandler implements ITelnetHandler
{
	private final String[] _commands =
	{
		"help"
	};
	
	@Override
	public boolean useCommand(String command, PrintWriter _print, Socket _cSocket, int _uptime)
	{
		if (command.equals("help"))
		{
			_print.println("The following is a list of all available commands: ");
			_print.println("help                  - shows this help.");
			_print.println("status                - displays basic server statistics.");
			_print.println("gamestat privatestore - displays info about stores");
			_print.println("performance           - shows server performance statistics.");
			_print.println("forcegc               - forced garbage collection.");
			_print.println("purge                 - removes finished threads from thread pools.");
			_print.println("memusage              - displays memory amounts in JVM.");
			_print.println("announce <text>       - announces <text> in game.");
			_print.println("msg <nick> <text>     - Sends a whisper to char <nick> with <text>.");
			_print.println("gmchat <text>         - Sends a message to all GMs with <text>.");
			_print.println("gmlist                - lists all gms online.");
			_print.println("kick                  - kick player <name> from server.");
			_print.println("shutdown <time>       - shuts down server in <time> seconds.");
			_print.println("restart <time>        - restarts down server in <time> seconds.");
			_print.println("abort                 - aborts shutdown/restart.");
			_print.println("give <player> <itemid> <amount>");
			_print.println("enchant <player> <itemType> <enchant> (itemType: 1 - Helmet, 2 - Chest, 3 - Gloves, 4 - Feet, 5 - Legs, 6 - Right Hand, 7 - Left Hand, 8 - Left Ear, 9 - Right Ear , 10 - Left Finger, 11 - Right Finger, 12- Necklace, 13 - Underwear, 14 - Back, 15 - Belt, 0 - No Enchant)");
			_print.println("debug <cmd>           - executes the debug command (see 'help debug').");
			_print.println("reload <type>         - reload data");
			_print.println("jail <player> [time]");
			_print.println("unjail <player>");
			_print.println("quit                  - closes telnet session.");
		}
		else if (command.equals("help debug"))
		{
			_print.println("The following is a list of all available debug commands: ");
			_print.println("full                - Dumps complete debug information to an file (recommended)");
			_print.println("decay               - prints info about the DecayManager");
			_print.println("packetsend          - Send packet data to a player");
			_print.println("PacketTP            - prints info about the General Packet ThreadPool");
			_print.println("IOPacketTP          - prints info about the I/O Packet ThreadPool");
			_print.println("GeneralTP           - prints info about the General ThreadPool");
		}
		return false;
	}
	
	@Override
	public String[] getCommandList()
	{
		return _commands;
	}
}
