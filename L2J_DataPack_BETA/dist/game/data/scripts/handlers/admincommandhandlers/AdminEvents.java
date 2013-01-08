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

import java.util.StringTokenizer;

import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Event;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.util.StringUtil;

public class AdminEvents implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_event_menu",
		"admin_event_start",
		"admin_event_stop",
		"admin_event_start_menu",
		"admin_event_stop_menu",
		"admin_event_bypass"
	};
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (activeChar == null)
		{
			return false;
		}
		
		String _event_name = "";
		String _event_bypass = "";
		StringTokenizer st = new StringTokenizer(command, " ");
		st.nextToken();
		if (st.hasMoreTokens())
		{
			_event_name = st.nextToken();
		}
		if (st.hasMoreTokens())
		{
			_event_bypass = st.nextToken();
		}
		
		if (command.contains("_menu"))
		{
			showMenu(activeChar);
		}
		
		if (command.startsWith("admin_event_start"))
		{
			try
			{
				if (_event_name != null)
				{
					Event _event = (Event) QuestManager.getInstance().getQuest(_event_name);
					if (_event != null)
					{
						if (_event.eventStart())
						{
							activeChar.sendMessage("Event '" + _event_name + "' started.");
							return true;
						}
						
						activeChar.sendMessage("There is problem with starting '" + _event_name + "' event.");
						return true;
					}
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //event_start <eventname>");
				e.printStackTrace();
				return false;
			}
		}
		else if (command.startsWith("admin_event_stop"))
		{
			try
			{
				if (_event_name != null)
				{
					Event _event = (Event) QuestManager.getInstance().getQuest(_event_name);
					if (_event != null)
					{
						if (_event.eventStop())
						{
							activeChar.sendMessage("Event '" + _event_name + "' stopped.");
							return true;
						}
						
						activeChar.sendMessage("There is problem with stoping '" + _event_name + "' event.");
						return true;
					}
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //event_start <eventname>");
				e.printStackTrace();
				return false;
			}
		}
		else if (command.startsWith("admin_event_bypass"))
		{
			try
			{
				if (_event_name != null)
				{
					Event _event = (Event) QuestManager.getInstance().getQuest(_event_name);
					if (_event != null)
					{
						_event.eventBypass(activeChar, _event_bypass);
					}
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //event_bypass <eventname> <bypass>");
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	private void showMenu(L2PcInstance activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/gm_events.htm");
		final StringBuilder cList = new StringBuilder(500);
		for (Quest event : QuestManager.getInstance().getAllManagedScripts())
		{
			if ((event instanceof Event) && event.getName().startsWith("eventmod"))
			{
				StringUtil.append(cList, "<font color=\"LEVEL\">" + event.getName() + ":</font><br1>", "<table width=270><tr>", "<td><button value=\"Start\" action=\"bypass -h admin_event_start_menu " + event.getName() + "\" width=80 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>", "<td><button value=\"Stop\" action=\"bypass -h admin_event_stop_menu " + event.getName() + "\" width=80 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>", "<td><button value=\"Menu\" action=\"bypass -h admin_event_bypass " + event.getName() + "\" width=80 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>", "</tr></table><br>");
			}
		}
		html.replace("%LIST%", cList.toString());
		activeChar.sendPacket(html);
	}
}