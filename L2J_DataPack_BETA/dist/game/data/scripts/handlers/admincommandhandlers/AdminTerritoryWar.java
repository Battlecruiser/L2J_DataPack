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

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager;
import com.l2jserver.gameserver.model.TerritoryWard;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * Admin comand handler for Territory War System
 * This class handles following admin commands:
 *
 * @author Gigiikun
 */
public class AdminTerritoryWar implements IAdminCommandHandler
{
	private static final String[] _adminCommands =
	{
		"admin_territory_war",
		"admin_territory_war_time",
		"admin_territory_war_start",
		"admin_territory_war_end",
		"admin_territory_wards_list"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		StringTokenizer st = new StringTokenizer(command);
		command = st.nextToken();
		
		if (command.equals("admin_territory_war"))
		{
			showMainPage(activeChar);
		}
		else if (command.equalsIgnoreCase("admin_territory_war_time"))
		{
			String val = "";
			if (st.hasMoreTokens())
			{
				val = st.nextToken();
				Calendar newAdminTWDate = Calendar.getInstance();
				newAdminTWDate.setTimeInMillis(TerritoryWarManager.getInstance().getTWStartTimeInMillis());
				if (val.equalsIgnoreCase("day"))
					newAdminTWDate.set(Calendar.DAY_OF_WEEK, Integer.parseInt(st.nextToken()));
				else if (val.equalsIgnoreCase("hour"))
					newAdminTWDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(st.nextToken()));
				else if (val.equalsIgnoreCase("min"))
					newAdminTWDate.set(Calendar.MINUTE, Integer.parseInt(st.nextToken()));
				
				if (newAdminTWDate.getTimeInMillis() < Calendar.getInstance().getTimeInMillis())
				{
					activeChar.sendMessage("Unable to change TW Date!");
				}
				else if (newAdminTWDate.getTimeInMillis() != TerritoryWarManager.getInstance().getTWStartTimeInMillis())
				{
					Quest twQuest = QuestManager.getInstance().getQuest(TerritoryWarManager.qn);
					if (twQuest != null)
						twQuest.onAdvEvent("setTWDate " + newAdminTWDate.getTimeInMillis(), null, null);
					else
						activeChar.sendMessage("Missing Territory War Quest!");
				}
			}
			showSiegeTimePage(activeChar);
		}
		else if (command.equalsIgnoreCase("admin_territory_war_start"))
		{
			Quest twQuest = QuestManager.getInstance().getQuest(TerritoryWarManager.qn);
			if (twQuest != null)
				twQuest.onAdvEvent("setTWDate " + Calendar.getInstance().getTimeInMillis(), null, null);
			else
				activeChar.sendMessage("Missing Territory War Quest!");
		}
		else if (command.equalsIgnoreCase("admin_territory_war_end"))
		{
			Quest twQuest = QuestManager.getInstance().getQuest(TerritoryWarManager.qn);
			if (twQuest != null)
				twQuest.onAdvEvent("setTWDate " + (Calendar.getInstance().getTimeInMillis() - TerritoryWarManager.WARLENGTH), null, null);
			else
				activeChar.sendMessage("Missing Territory War Quest!");
		}
		else if (command.equalsIgnoreCase("admin_territory_wards_list"))
		{
			//build beginning of html page
			NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(1, 1);
			StringBuilder sb = new StringBuilder();
			sb.append("<html><title>Territory War</title><body><br><center><font color=\"LEVEL\">Active Wards List:</font></center>");

			//get,build & send current Wards list
			if (TerritoryWarManager.getInstance().isTWInProgress())
			{
				List<TerritoryWard> territoryWardList = TerritoryWarManager.getInstance().getAllTerritoryWards();
				for(TerritoryWard ward : territoryWardList)
				{
					if (ward.getNpc() != null)
					{
						sb.append("<table width=270><tr>");
						sb.append("<td width=135 ALIGN=\"LEFT\">" + ward.getNpc().getName() + "</td>");
						sb.append("<td width=135 ALIGN=\"RIGHT\"><button value=\"TeleTo\" action=\"bypass -h admin_move_to " + ward.getNpc().getX() + " " + ward.getNpc().getY() +  " " + ward.getNpc().getZ() + "\" width=50 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></td>");
						sb.append("</tr></table>");
					}
					else if (ward.getPlayer() != null)
					{
						sb.append("<table width=270><tr>");
						sb.append("<td width=135 ALIGN=\"LEFT\">" + ward.getPlayer().getActiveWeaponInstance().getItemName() + " - " + ward.getPlayer().getName() + "</td>");
						sb.append("<td width=135 ALIGN=\"RIGHT\"><button value=\"TeleTo\" action=\"bypass -h admin_move_to " + ward.getPlayer().getX() + " " + ward.getPlayer().getY() +  " " + ward.getPlayer().getZ() + "\" width=50 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></td>");
						sb.append("</tr></table>");
					}
				}
		    	sb.append("<br><center><button value=\"Back\" action=\"bypass -h admin_territory_war\" width=50 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
				npcHtmlMessage.setHtml(sb.toString());
		    	activeChar.sendPacket(npcHtmlMessage);
			}
			else
			{
				sb.append("<br><br><center>The Ward List is empty!<br>TW has probably NOT started!");
				sb.append("<br><button value=\"Back\" action=\"bypass -h admin_territory_war\" width=50 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		    	npcHtmlMessage.setHtml(sb.toString());
		    	activeChar.sendPacket(npcHtmlMessage);
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return _adminCommands;
	}
	
	private void showSiegeTimePage(L2PcInstance activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/territorywartime.htm");
		adminReply.replace("%time%", TerritoryWarManager.getInstance().getTWStart().getTime().toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void showMainPage(L2PcInstance activeChar)
	{
		AdminHelpPage.showHelpPage(activeChar, "territorywar.htm");
	}
}
