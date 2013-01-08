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
package handlers.bypasshandlers;

import java.util.StringTokenizer;
import java.util.logging.Level;

import com.l2jserver.gameserver.datatables.MultiSell;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MercenaryManagerInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.ExShowDominionRegistry;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

public class TerritoryWar implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"Territory",
		"TW_Buy_List",
		"TW_Buy",
		"TW_Buy_Elite"
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
		{
			return false;
		}
		
		try
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			String actualCommand = st.nextToken(); // Get actual command
			
			if (actualCommand.equalsIgnoreCase("Territory"))
			{
				if (st.countTokens() < 1)
				{
					return false;
				}
				
				int castleId = Integer.parseInt(st.nextToken());
				activeChar.sendPacket(new ExShowDominionRegistry(castleId, activeChar));
			}
			else if (!(target instanceof L2MercenaryManagerInstance))
			{
				return false;
			}
			
			L2MercenaryManagerInstance mercman = ((L2MercenaryManagerInstance) target);
			if (actualCommand.equalsIgnoreCase("TW_Buy_List"))
			{
				if (st.countTokens() < 1)
				{
					return false;
				}
				
				String itemId = st.nextToken();
				NpcHtmlMessage html = new NpcHtmlMessage(mercman.getObjectId());
				html.setFile(activeChar.getHtmlPrefix(), "data/html/mercmanager/" + st.nextToken());
				html.replace("%itemId%", itemId);
				html.replace("%noblessBadge%", String.valueOf(TerritoryWarManager.MINTWBADGEFORNOBLESS));
				html.replace("%striderBadge%", String.valueOf(TerritoryWarManager.MINTWBADGEFORSTRIDERS));
				html.replace("%gstriderBadge%", String.valueOf(TerritoryWarManager.MINTWBADGEFORBIGSTRIDER));
				html.replace("%objectId%", String.valueOf(mercman.getObjectId()));
				activeChar.sendPacket(html);
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			}
			else if (actualCommand.equalsIgnoreCase("TW_Buy"))
			{
				int itemId = Integer.parseInt(st.nextToken());
				int count = Integer.parseInt(st.nextToken());
				int type = Integer.parseInt(st.nextToken());
				if (activeChar.getInventory().getItemByItemId(itemId) != null)
				{
					long playerItemCount = activeChar.getInventory().getItemByItemId(itemId).getCount();
					if (count <= playerItemCount)
					{
						int boughtId = 0;
						switch (type)
						{
							case 1:
								boughtId = 4422;
								break;
							case 2:
								boughtId = 4423;
								break;
							case 3:
								boughtId = 4424;
								break;
							case 4:
								boughtId = 14819;
								break;
							default:
								_log.warning("TerritoryWar buy: not handled type: " + type);
								return false;
						}
						activeChar.exchangeItemsById("TerritoryWar", mercman, itemId, count, boughtId, 1, true);
						mercman.showChatWindow(activeChar, 7);
						return true;
					}
				}
				mercman.showChatWindow(activeChar, 6);
			}
			else if (actualCommand.equalsIgnoreCase("TW_Buy_Elite"))
			{
				if (activeChar.getInventory().getItemByItemId(13767) != null)
				{
					int _castleid = mercman.getCastle().getCastleId();
					if (_castleid > 0)
					{
						MultiSell.getInstance().separateAndSend(_castleid + 676, activeChar, mercman, false);
					}
				}
				else
				{
					NpcHtmlMessage html = new NpcHtmlMessage(mercman.getObjectId());
					html.setFile(activeChar.getHtmlPrefix(), "data/html/mercmanager/nocert.htm");
					activeChar.sendPacket(html);
					activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				}
			}
			return true;
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Exception in " + getClass().getSimpleName(), e);
		}
		return false;
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}
