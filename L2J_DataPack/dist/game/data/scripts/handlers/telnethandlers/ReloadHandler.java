/*
 * Copyright (C) 2004-2015 L2J DataPack
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

import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.script.ScriptException;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.data.xml.impl.MultisellData;
import com.l2jserver.gameserver.data.xml.impl.NpcData;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.datatables.TeleportLocationTable;
import com.l2jserver.gameserver.handler.ITelnetHandler;
import com.l2jserver.gameserver.instancemanager.DayNightSpawnManager;
import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.scripting.L2ScriptEngineManager;

/**
 * @author UnAfraid
 */
public class ReloadHandler implements ITelnetHandler
{
	private final String[] _commands =
	{
		"reload"
	};
	
	@Override
	public boolean useCommand(String command, PrintWriter _print, Socket _cSocket, int _uptime)
	{
		if (command.startsWith("reload"))
		{
			StringTokenizer st = new StringTokenizer(command.substring(7));
			try
			{
				String type = st.nextToken();
				
				if (type.equals("multisell"))
				{
					_print.print("Reloading multisell... ");
					MultisellData.getInstance().load();
					_print.println("done");
				}
				else if (type.equals("skill"))
				{
					_print.print("Reloading skills... ");
					SkillData.getInstance().reload();
					_print.println("done");
				}
				else if (type.equals("npc"))
				{
					_print.print("Reloading npc templates... ");
					NpcData.getInstance().load();
					QuestManager.getInstance().reloadAllScripts();
					_print.println("done");
				}
				else if (type.equals("html"))
				{
					_print.print("Reloading html cache... ");
					HtmCache.getInstance().reload();
					_print.println("done");
				}
				else if (type.equals("item"))
				{
					_print.print("Reloading item templates... ");
					ItemTable.getInstance().reload();
					_print.println("done");
				}
				else if (type.equals("zone"))
				{
					_print.print("Reloading zone tables... ");
					ZoneManager.getInstance().reload();
					_print.println("done");
				}
				else if (type.equals("teleports"))
				{
					_print.print("Reloading telport location table... ");
					TeleportLocationTable.getInstance().reloadAll();
					_print.println("done");
				}
				else if (type.equals("spawns"))
				{
					_print.print("Reloading spawns... ");
					RaidBossSpawnManager.getInstance().cleanUp();
					DayNightSpawnManager.getInstance().cleanUp();
					L2World.getInstance().deleteVisibleNpcSpawns();
					NpcData.getInstance().load();
					SpawnTable.getInstance().load();
					RaidBossSpawnManager.getInstance().load();
					_print.println("done\n");
				}
				else if (type.equalsIgnoreCase("script"))
				{
					try
					{
						String questPath = st.hasMoreTokens() ? st.nextToken() : "";
						
						File file = new File(L2ScriptEngineManager.SCRIPT_FOLDER, questPath);
						if (file.isFile())
						{
							try
							{
								L2ScriptEngineManager.getInstance().executeScript(file);
								_print.println(file.getName() + " was successfully loaded!\n");
							}
							catch (ScriptException e)
							{
								_print.println("Failed loading: " + questPath);
								L2ScriptEngineManager.getInstance().reportScriptFileError(file, e);
							}
							catch (Exception e)
							{
								_print.println("Failed loading: " + questPath);
							}
						}
						else
						{
							_print.println(file.getName() + " is not a file in: " + questPath);
						}
					}
					catch (StringIndexOutOfBoundsException e)
					{
						_print.println("Please Enter Some Text!");
					}
				}
			}
			catch (Exception e)
			{
			}
		}
		return false;
	}
	
	@Override
	public String[] getCommandList()
	{
		return _commands;
	}
}
