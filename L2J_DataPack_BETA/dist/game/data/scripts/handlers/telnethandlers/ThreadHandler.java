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
package handlers.telnethandlers;

import java.io.PrintWriter;
import java.net.Socket;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.handler.ITelnetHandler;

/**
 * @author UnAfraid
 */
public class ThreadHandler implements ITelnetHandler
{
	private final String[] _commands =
	{
		"purge", 
		"performance"
	};
	
	@Override
	public boolean useCommand(String command, PrintWriter _print, Socket _cSocket, int _uptime)
	{
		if (command.equals("performance"))
		{
			for (String line : ThreadPoolManager.getInstance().getStats())
			{
				_print.println(line);
			}
			_print.flush();
		}
		else if (command.equals("purge"))
		{
			ThreadPoolManager.getInstance().purge();
			_print.println("STATUS OF THREAD POOLS AFTER PURGE COMMAND:");
			_print.println("");
			for (String line : ThreadPoolManager.getInstance().getStats())
			{
				_print.println(line);
			}
			_print.flush();
		}
		return false;
	}
	
	@Override
	public String[] getCommandList()
	{
		return _commands;
	}
}
