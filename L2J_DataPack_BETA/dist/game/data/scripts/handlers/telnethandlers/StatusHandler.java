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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.l2jserver.gameserver.GameTimeController;
import com.l2jserver.gameserver.LoginServerThread;
import com.l2jserver.gameserver.datatables.AdminTable;
import com.l2jserver.gameserver.enums.ItemLocation;
import com.l2jserver.gameserver.handler.ITelnetHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;

/**
 * @author UnAfraid
 */
public class StatusHandler implements ITelnetHandler
{
	private final String[] _commands =
	{
		"status",
		"forcegc",
		"memusage",
		"gmlist"
	};
	
	private int uptime;
	
	@Override
	public boolean useCommand(String command, PrintWriter _print, Socket _cSocket, int _uptime)
	{
		if (command.equals("status"))
		{
			uptime = _uptime;
			_print.print(getServerStatus());
			_print.flush();
		}
		else if (command.equals("forcegc"))
		{
			System.gc();
			StringBuilder sb = new StringBuilder();
			sb.append("RAM Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576)); // 1024 * 1024 = 1048576
			_print.println(sb.toString());
		}
		else if (command.startsWith("memusage"))
		{
			double max = Runtime.getRuntime().maxMemory() / 1024; // maxMemory is the upper
			// limit the jvm can use
			double allocated = Runtime.getRuntime().totalMemory() / 1024; // totalMemory the
			// size of the
			// current
			// allocation pool
			double nonAllocated = max - allocated; // non allocated memory till jvm limit
			double cached = Runtime.getRuntime().freeMemory() / 1024; // freeMemory the
			// unused memory in
			// the allocation pool
			double used = allocated - cached; // really used memory
			double useable = max - used; // allocated, but non-used and non-allocated memory
			
			DecimalFormat df = new DecimalFormat(" (0.0000'%')");
			DecimalFormat df2 = new DecimalFormat(" # 'KB'");
			
			_print.println("+----");// ...
			_print.println("| Allowed Memory:" + df2.format(max));
			_print.println("|    |= Allocated Memory:" + df2.format(allocated) + df.format((allocated / max) * 100));
			_print.println("|    |= Non-Allocated Memory:" + df2.format(nonAllocated) + df.format((nonAllocated / max) * 100));
			_print.println("| Allocated Memory:" + df2.format(allocated));
			_print.println("|    |= Used Memory:" + df2.format(used) + df.format((used / max) * 100));
			_print.println("|    |= Unused (cached) Memory:" + df2.format(cached) + df.format((cached / max) * 100));
			_print.println("| Useable Memory:" + df2.format(useable) + df.format((useable / max) * 100)); // ...
			_print.println("+----");
		}
		else if (command.equals("gmlist"))
		{
			int igm = 0;
			String gmList = "";
			
			for (String player : AdminTable.getInstance().getAllGmNames(true))
			{
				gmList = gmList + ", " + player;
				igm++;
			}
			_print.println("There are currently " + igm + " GM(s) online...");
			if (!gmList.isEmpty())
			{
				_print.println(gmList);
			}
		}
		return false;
	}
	
	public String getServerStatus()
	{
		int playerCount = 0, objectCount = 0;
		int max = LoginServerThread.getInstance().getMaxPlayer();
		
		playerCount = L2World.getInstance().getAllPlayersCount();
		objectCount = L2World.getInstance().getVisibleObjectsCount();
		
		int itemCount = 0;
		int itemVoidCount = 0;
		int monsterCount = 0;
		int minionCount = 0;
		int minionsGroupCount = 0;
		int npcCount = 0;
		int charCount = 0;
		int pcCount = 0;
		int detachedCount = 0;
		int doorCount = 0;
		int summonCount = 0;
		int AICount = 0;
		
		for (L2Object obj : L2World.getInstance().getVisibleObjects())
		{
			if (obj == null)
			{
				continue;
			}
			if (obj instanceof L2Character)
			{
				if (((L2Character) obj).hasAI())
				{
					AICount++;
				}
			}
			if (obj instanceof L2ItemInstance)
			{
				if (((L2ItemInstance) obj).getItemLocation() == ItemLocation.VOID)
				{
					itemVoidCount++;
				}
				else
				{
					itemCount++;
				}
			}
			else if (obj instanceof L2MonsterInstance)
			{
				monsterCount++;
				if (((L2MonsterInstance) obj).hasMinions())
				{
					minionCount += ((L2MonsterInstance) obj).getMinionList().countSpawnedMinions();
					minionsGroupCount += ((L2MonsterInstance) obj).getMinionList().lazyCountSpawnedMinionsGroups();
				}
			}
			else if (obj instanceof L2Npc)
			{
				npcCount++;
			}
			else if (obj instanceof L2PcInstance)
			{
				pcCount++;
				if ((((L2PcInstance) obj).getClient() != null) && ((L2PcInstance) obj).getClient().isDetached())
				{
					detachedCount++;
				}
			}
			else if (obj instanceof L2Summon)
			{
				summonCount++;
			}
			else if (obj instanceof L2DoorInstance)
			{
				doorCount++;
			}
			else if (obj instanceof L2Character)
			{
				charCount++;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Server Status: ");
		sb.append("\r\n  --->  Player Count: " + playerCount + "/" + max);
		sb.append("\r\n  ---> Offline Count: " + detachedCount + "/" + playerCount);
		sb.append("\r\n  +-->  Object Count: " + objectCount);
		sb.append("\r\n  +-->      AI Count: " + AICount);
		sb.append("\r\n  +.... L2Item(Void): " + itemVoidCount);
		sb.append("\r\n  +.......... L2Item: " + itemCount);
		sb.append("\r\n  +....... L2Monster: " + monsterCount);
		sb.append("\r\n  +......... Minions: " + minionCount);
		sb.append("\r\n  +.. Minions Groups: " + minionsGroupCount);
		sb.append("\r\n  +........... L2Npc: " + npcCount);
		sb.append("\r\n  +............ L2Pc: " + pcCount);
		sb.append("\r\n  +........ L2Summon: " + summonCount);
		sb.append("\r\n  +.......... L2Door: " + doorCount);
		sb.append("\r\n  +.......... L2Char: " + charCount);
		sb.append("\r\n  --->   Ingame Time: " + gameTime());
		sb.append("\r\n  ---> Server Uptime: " + getUptime(uptime));
		sb.append("\r\n  --->      GM Count: " + getOnlineGMS());
		sb.append("\r\n  --->       Threads: " + Thread.activeCount());
		sb.append("\r\n  RAM Used: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576)); // 1024 * 1024 = 1048576
		sb.append("\r\n");
		
		return sb.toString();
	}
	
	private int getOnlineGMS()
	{
		return AdminTable.getInstance().getAllGms(true).size();
	}
	
	private String getUptime(int time)
	{
		int uptime = (int) System.currentTimeMillis() - time;
		uptime = uptime / 1000;
		int h = uptime / 3600;
		int m = (uptime - (h * 3600)) / 60;
		int s = ((uptime - (h * 3600)) - (m * 60));
		return h + "hrs " + m + "mins " + s + "secs";
	}
	
	private String gameTime()
	{
		int t = GameTimeController.getInstance().getGameTime();
		int h = t / 60;
		int m = t % 60;
		SimpleDateFormat format = new SimpleDateFormat("H:mm");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, h);
		cal.set(Calendar.MINUTE, m);
		return format.format(cal.getTime());
	}
	
	@Override
	public String[] getCommandList()
	{
		return _commands;
	}
}
