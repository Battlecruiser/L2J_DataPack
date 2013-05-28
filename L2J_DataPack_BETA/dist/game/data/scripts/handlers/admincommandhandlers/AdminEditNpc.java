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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.TradeController;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.datatables.MerchantPriceConfigTable.MerchantPriceConfig;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.Elementals;
import com.l2jserver.gameserver.model.L2DropCategory;
import com.l2jserver.gameserver.model.L2DropData;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2TradeList;
import com.l2jserver.gameserver.model.L2TradeList.L2TradeItem;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MerchantInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.items.L2Item;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.stats.MoveType;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.util.StringUtil;

/**
 * con.close() change by Zoey76 24/02/2011
 * @author terry
 */
public class AdminEditNpc implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminEditNpc.class.getName());
	private final static int PAGE_LIMIT = 20;
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_edit_npc",
		"admin_save_npc",
		"admin_show_droplist",
		"admin_edit_drop",
		"admin_add_drop",
		"admin_del_drop",
		"admin_showShop",
		"admin_showShopList",
		"admin_addShopItem",
		"admin_delShopItem",
		"admin_editShopItem",
		"admin_close_window",
		"admin_show_skilllist_npc",
		"admin_add_skill_npc",
		"admin_edit_skill_npc",
		"admin_del_skill_npc",
		"admin_log_npc_spawn"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		// TODO: Tokenize and protect arguments parsing. Externalize HTML.
		if (command.startsWith("admin_showShop "))
		{
			String[] args = command.split(" ");
			if (args.length > 1)
			{
				showShop(activeChar, Integer.parseInt(command.split(" ")[1]));
			}
		}
		else if (command.startsWith("admin_log_npc_spawn"))
		{
			L2Object target = activeChar.getTarget();
			if (target instanceof L2Npc)
			{
				L2Npc npc = (L2Npc) target;
				_log.info("('',1," + npc.getNpcId() + "," + npc.getX() + "," + npc.getY() + "," + npc.getZ() + ",0,0," + npc.getHeading() + ",60,0,0),");
			}
		}
		else if (command.startsWith("admin_showShopList "))
		{
			String[] args = command.split(" ");
			if (args.length > 2)
			{
				showShopList(activeChar, Integer.parseInt(command.split(" ")[1]), Integer.parseInt(command.split(" ")[2]));
			}
		}
		else if (command.startsWith("admin_edit_npc "))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			
			if (st.countTokens() < 2)
			{
				activeChar.sendMessage("Wrong usage: //edit_npc <stats|ai|elementals|visuals> <npc_id>");
				return true;
			}
			
			String category = st.nextToken();
			try
			{
				int npcId = Integer.parseInt(st.nextToken());
				L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
				if (npc != null)
				{
					showNpcProperty(activeChar, npc, category);
				}
				else
				{
					activeChar.sendMessage("NPC does not exist or not loaded.");
				}
			}
			catch (NumberFormatException e)
			{
				activeChar.sendMessage("npc_id should be a number");
			}
		}
		else if (command.startsWith("admin_show_droplist "))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			try
			{
				int npcId = Integer.parseInt(st.nextToken());
				int page = 1;
				if (st.hasMoreTokens())
				{
					page = Integer.parseInt(st.nextToken());
				}
				showNpcDropList(activeChar, npcId, page);
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //show_droplist <npc_id> [<page>]");
			}
		}
		else if (command.startsWith("admin_addShopItem "))
		{
			String[] args = command.split(" ");
			if (args.length > 1)
			{
				addShopItem(activeChar, args);
			}
		}
		else if (command.startsWith("admin_delShopItem "))
		{
			String[] args = command.split(" ");
			if (args.length > 2)
			{
				delShopItem(activeChar, args);
			}
		}
		else if (command.startsWith("admin_editShopItem "))
		{
			String[] args = command.split(" ");
			if (args.length > 2)
			{
				editShopItem(activeChar, args);
			}
		}
		else if (command.startsWith("admin_save_npc "))
		{
			try
			{
				saveNpcProperty(activeChar, command);
			}
			catch (StringIndexOutOfBoundsException e)
			{
			}
		}
		else if (command.startsWith("admin_edit_drop "))
		{
			int npcId = -1, itemId = 0, category = -1000;
			try
			{
				StringTokenizer st = new StringTokenizer(command.substring(16).trim());
				if (st.countTokens() == 3)
				{
					try
					{
						npcId = Integer.parseInt(st.nextToken());
						itemId = Integer.parseInt(st.nextToken());
						category = Integer.parseInt(st.nextToken());
						showEditDropData(activeChar, npcId, itemId, category);
					}
					catch (Exception e)
					{
						_log.log(Level.WARNING, "", e);
					}
				}
				else if (st.countTokens() == 6)
				{
					try
					{
						npcId = Integer.parseInt(st.nextToken());
						itemId = Integer.parseInt(st.nextToken());
						category = Integer.parseInt(st.nextToken());
						int min = Integer.parseInt(st.nextToken());
						int max = Integer.parseInt(st.nextToken());
						int chance = Integer.parseInt(st.nextToken());
						
						updateDropData(activeChar, npcId, itemId, min, max, category, chance);
					}
					catch (Exception e)
					{
						_log.fine("admin_edit_drop parameters error: " + command);
					}
				}
				else
				{
					activeChar.sendMessage("Usage: //edit_drop <npc_id> <item_id> <category> [<min> <max> <chance>]");
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //edit_drop <npc_id> <item_id> <category> [<min> <max> <chance>]");
			}
		}
		else if (command.startsWith("admin_add_drop "))
		{
			int npcId = -1;
			try
			{
				StringTokenizer st = new StringTokenizer(command.substring(15).trim());
				if (st.countTokens() == 1)
				{
					try
					{
						String[] input = command.substring(15).split(" ");
						if (input.length < 1)
						{
							return true;
						}
						npcId = Integer.parseInt(input[0]);
					}
					catch (Exception e)
					{
					}
					
					if (npcId > 0)
					{
						L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
						showAddDropData(activeChar, npcData);
					}
				}
				else if (st.countTokens() == 6)
				{
					try
					{
						npcId = Integer.parseInt(st.nextToken());
						int itemId = Integer.parseInt(st.nextToken());
						int category = Integer.parseInt(st.nextToken());
						int min = Integer.parseInt(st.nextToken());
						int max = Integer.parseInt(st.nextToken());
						int chance = Integer.parseInt(st.nextToken());
						
						addDropData(activeChar, npcId, itemId, min, max, category, chance);
					}
					catch (Exception e)
					{
						_log.fine("admin_add_drop parameters error: " + command);
					}
				}
				else
				{
					activeChar.sendMessage("Usage: //add_drop <npc_id> [<item_id> <category> <min> <max> <chance>]");
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //add_drop <npc_id> [<item_id> <category> <min> <max> <chance>]");
			}
		}
		else if (command.startsWith("admin_del_drop "))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			try
			{
				int npcId = Integer.parseInt(st.nextToken());
				int itemId = Integer.parseInt(st.nextToken());
				int category = Integer.parseInt(st.nextToken());
				boolean confirmed = false;
				if (st.hasMoreTokens())
				{
					confirmed = true;
				}
				deleteDropData(activeChar, npcId, itemId, category, confirmed);
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //del_drop <npc_id> <item_id> <category>");
			}
		}
		else if (command.startsWith("admin_show_skilllist_npc "))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			try
			{
				int npcId = Integer.parseInt(st.nextToken());
				int page = 0;
				if (st.hasMoreTokens())
				{
					page = Integer.parseInt(st.nextToken());
				}
				showNpcSkillList(activeChar, npcId, page);
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //show_skilllist_npc <npc_id> <page>");
			}
		}
		else if (command.startsWith("admin_edit_skill_npc "))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command, " ");
				st.nextToken();
				int npcId = Integer.parseInt(st.nextToken());
				int skillId = Integer.parseInt(st.nextToken());
				if (!st.hasMoreTokens())
				{
					showNpcSkillEdit(activeChar, npcId, skillId);
				}
				else
				{
					int level = Integer.parseInt(st.nextToken());
					updateNpcSkillData(activeChar, npcId, skillId, level);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //edit_skill_npc <npc_id> <item_id> [<level>]");
			}
		}
		else if (command.startsWith("admin_add_skill_npc "))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command, " ");
				st.nextToken();
				int npcId = Integer.parseInt(st.nextToken());
				if (!st.hasMoreTokens())
				{
					showNpcSkillAdd(activeChar, npcId);
				}
				else
				{
					int skillId = Integer.parseInt(st.nextToken());
					int level = Integer.parseInt(st.nextToken());
					addNpcSkillData(activeChar, npcId, skillId, level);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //add_skill_npc <npc_id> [<skill_id> <level>]");
			}
		}
		else if (command.startsWith("admin_del_skill_npc "))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command, " ");
				st.nextToken();
				int npcId = Integer.parseInt(st.nextToken());
				int skillId = Integer.parseInt(st.nextToken());
				deleteNpcSkillData(activeChar, npcId, skillId);
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage: //del_skill_npc <npc_id> <skill_id>");
			}
		}
		
		return true;
	}
	
	private void editShopItem(L2PcInstance activeChar, String[] args)
	{
		int tradeListID = Integer.parseInt(args[1]);
		int itemID = Integer.parseInt(args[2]);
		L2TradeList tradeList = TradeController.getInstance().getBuyList(tradeListID);
		
		L2Item item = ItemTable.getInstance().getTemplate(itemID);
		if (tradeList.getPriceForItemId(itemID) < 0)
		{
			return;
		}
		
		if (args.length > 3)
		{
			long price = Long.parseLong(args[3]);
			int order = findOrderTradeList(itemID, tradeList.getPriceForItemId(itemID), tradeListID);
			
			tradeList.replaceItem(itemID, Long.parseLong(args[3]));
			updateTradeList(itemID, price, tradeListID, order);
			
			activeChar.sendMessage("Updated price for " + item.getName() + " in Trade List " + tradeListID);
			showShopList(activeChar, tradeListID, 1);
			return;
		}
		
		final String replyMSG = StringUtil.concat("<html><title>Merchant Shop Item Edit</title><body><center><font color=\"LEVEL\">", NpcTable.getInstance().getTemplate(Integer.parseInt(tradeList.getNpcId())).getName(), " (", tradeList.getNpcId(), ") -> ", Integer.toString(tradeListID), "</font></center><table width=\"100%\"><tr><td>Item</td><td>", item.getName(), " (", Integer.toString(item.getItemId()), ")", "</td></tr><tr><td>Price (", String.valueOf(tradeList.getPriceForItemId(itemID)), ")</td><td><edit var=\"price\" width=80></td></tr></table><center><br><button value=\"Save\" action=\"bypass -h admin_editShopItem ", String.valueOf(tradeListID), " ", String.valueOf(itemID), " $price\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Shop List\" action=\"bypass -h admin_showShopList ", String.valueOf(tradeListID), " 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG);
		activeChar.sendPacket(adminReply);
	}
	
	private void delShopItem(L2PcInstance activeChar, String[] args)
	{
		int tradeListID = Integer.parseInt(args[1]);
		int itemID = Integer.parseInt(args[2]);
		L2TradeList tradeList = TradeController.getInstance().getBuyList(tradeListID);
		
		if (tradeList.getPriceForItemId(itemID) < 0)
		{
			return;
		}
		
		if (args.length > 3)
		{
			int order = findOrderTradeList(itemID, tradeList.getPriceForItemId(itemID), tradeListID);
			
			tradeList.removeItem(itemID);
			deleteTradeList(tradeListID, order);
			
			activeChar.sendMessage("Deleted " + ItemTable.getInstance().getTemplate(itemID).getName() + " from Trade List " + tradeListID);
			showShopList(activeChar, tradeListID, 1);
			return;
		}
		
		final String replyMSG = StringUtil.concat("<html><title>Merchant Shop Item Delete</title><body><br>Delete entry in trade list ", String.valueOf(tradeListID), "<table width=\"100%\"><tr><td>Item</td><td>", ItemTable.getInstance().getTemplate(itemID).getName(), " (", Integer.toString(itemID), ")</td></tr><tr><td>Price</td><td>", String.valueOf(tradeList.getPriceForItemId(itemID)), "</td></tr></table><center><br><button value=\"Delete\" action=\"bypass -h admin_delShopItem ", String.valueOf(tradeListID), " ", String.valueOf(itemID), " 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Shop List\" action=\"bypass -h admin_showShopList ", String.valueOf(tradeListID), " 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG);
		activeChar.sendPacket(adminReply);
	}
	
	private void addShopItem(L2PcInstance activeChar, String[] args)
	{
		int tradeListID = Integer.parseInt(args[1]);
		
		L2TradeList tradeList = TradeController.getInstance().getBuyList(tradeListID);
		if (tradeList == null)
		{
			activeChar.sendMessage("TradeList not found!");
			return;
		}
		
		if (args.length > 3)
		{
			int order = tradeList.getItems().size() + 1; // last item order + 1
			int itemID = Integer.parseInt(args[2]);
			long price = Long.parseLong(args[3]);
			
			L2TradeItem newItem = new L2TradeItem(tradeListID, itemID);
			newItem.setPrice(price);
			newItem.setMaxCount(-1);
			tradeList.addItem(newItem);
			boolean stored = storeTradeList(itemID, price, tradeListID, order);
			
			if (stored)
			{
				activeChar.sendMessage("Added " + ItemTable.getInstance().getTemplate(itemID).getName() + " to Trade List " + tradeList.getListId());
			}
			else
			{
				activeChar.sendMessage("Could not add " + ItemTable.getInstance().getTemplate(itemID).getName() + " to Trade List " + tradeList.getListId() + "!");
			}
			
			showShopList(activeChar, tradeListID, 1);
			return;
		}
		
		final String replyMSG = StringUtil.concat("<html><title>Merchant Shop Item Add</title><body><br>Add a new entry in merchantList.<table width=\"100%\"><tr><td>ItemID</td><td><edit var=\"itemID\" width=80></td></tr><tr><td>Price</td><td><edit var=\"price\" width=80></td></tr></table><center><br><button value=\"Add\" action=\"bypass -h admin_addShopItem ", String.valueOf(tradeListID), " $itemID $price\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Shop List\" action=\"bypass -h admin_showShopList ", String.valueOf(tradeListID), " 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG);
		activeChar.sendPacket(adminReply);
	}
	
	private void showShopList(L2PcInstance activeChar, int tradeListID, int page)
	{
		L2TradeList tradeList = TradeController.getInstance().getBuyList(tradeListID);
		if ((page > ((tradeList.getItems().size() / PAGE_LIMIT) + 1)) || (page < 1))
		{
			return;
		}
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(itemListHtml(tradeList, page));
		activeChar.sendPacket(adminReply);
	}
	
	private String itemListHtml(L2TradeList tradeList, int page)
	{
		final StringBuilder replyMSG = new StringBuilder();
		
		int max = tradeList.getItems().size() / PAGE_LIMIT;
		if (tradeList.getItems().size() > (PAGE_LIMIT * max))
		{
			max++;
		}
		
		StringUtil.append(replyMSG, "<html><title>Merchant Shop List Page: ", String.valueOf(page), " of ", Integer.toString(max), "</title><body><br><center><font color=\"LEVEL\">", NpcTable.getInstance().getTemplate(Integer.parseInt(tradeList.getNpcId())).getName(), " (", tradeList.getNpcId(), ") Shop ID: ", Integer.toString(tradeList.getListId()), "</font></center><table width=300 bgcolor=666666><tr>");
		
		for (int x = 0; x < max; x++)
		{
			int pagenr = x + 1;
			if (page == pagenr)
			{
				replyMSG.append("<td>Page ");
				replyMSG.append(pagenr);
				replyMSG.append("</td>");
			}
			else
			{
				replyMSG.append("<td><a action=\"bypass -h admin_showShopList ");
				replyMSG.append(tradeList.getListId());
				replyMSG.append(" ");
				replyMSG.append(x + 1);
				replyMSG.append("\"> Page ");
				replyMSG.append(pagenr);
				replyMSG.append(" </a></td>");
			}
		}
		
		replyMSG.append("</tr></table><table width=\"100%\"><tr><td width=150>Item</td><td width=60>Price</td><td width=40>Delete</td></tr>");
		
		int start = ((page - 1) * PAGE_LIMIT);
		int end = Math.min(((page - 1) * PAGE_LIMIT) + PAGE_LIMIT, tradeList.getItems().size());
		for (L2TradeItem item : tradeList.getItems(start, end))
		{
			StringUtil.append(replyMSG, "<tr><td><a action=\"bypass -h admin_editShopItem ", String.valueOf(tradeList.getListId()), " ", String.valueOf(item.getItemId()), "\">", ItemTable.getInstance().getTemplate(item.getItemId()).getName(), "</a></td><td>", String.valueOf(item.getPrice()), "</td><td><a action=\"bypass -h admin_delShopItem ", String.valueOf(tradeList.getListId()), " ", String.valueOf(item.getItemId()), "\">Delete</a></td></tr>");
		}
		StringUtil.append(replyMSG, "<tr><td><br><br></td><td> </td><td> </td></tr><tr>");
		
		StringUtil.append(replyMSG, "</tr></table><center><br><button value=\"Add Shop Item\" action=\"bypass -h admin_addShopItem ", String.valueOf(tradeList.getListId()), "\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Close\" action=\"bypass -h admin_close_window\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		return replyMSG.toString();
	}
	
	private void showShop(L2PcInstance activeChar, int merchantID)
	{
		List<L2TradeList> tradeLists = TradeController.getInstance().getBuyListByNpcId(merchantID);
		if (tradeLists == null)
		{
			activeChar.sendMessage("Unknown npc template Id: " + merchantID);
			return;
		}
		
		final StringBuilder replyMSG = new StringBuilder();
		StringUtil.append(replyMSG, "<html><title>Merchant Shop Lists</title><body>");
		
		if (activeChar.getTarget() instanceof L2MerchantInstance)
		{
			MerchantPriceConfig mpc = ((L2MerchantInstance) activeChar.getTarget()).getMpc();
			StringUtil.append(replyMSG, "<br>NPC: ", activeChar.getTarget().getName(), " (", Integer.toString(merchantID), ") <br>Price Config: ", mpc.getName(), ", ", Integer.toString(mpc.getBaseTax()), "% / ", Integer.toString(mpc.getTotalTax()), "%");
		}
		
		StringUtil.append(replyMSG, "<table width=\"100%\">");
		
		for (L2TradeList tradeList : tradeLists)
		{
			if (tradeList != null)
			{
				StringUtil.append(replyMSG, "<tr><td><a action=\"bypass -h admin_showShopList ", String.valueOf(tradeList.getListId()), " 1\">Merchant List ID ", String.valueOf(tradeList.getListId()), "</a></td></tr>");
			}
		}
		
		StringUtil.append(replyMSG, "</table><center><br><button value=\"Close\" action=\"bypass -h admin_close_window\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private boolean storeTradeList(int itemID, long price, int tradeListID, int order)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String table = "merchant_buylists";
			if (Config.CUSTOM_MERCHANT_TABLES)
			{
				table = "custom_merchant_buylists";
			}
			
			PreparedStatement stmt = con.prepareStatement("INSERT INTO `" + table + "`(`item_id`,`price`,`shop_id`,`order`) VALUES (?,?,?,?)");
			stmt.setInt(1, itemID);
			stmt.setLong(2, price);
			stmt.setInt(3, tradeListID);
			stmt.setInt(4, order);
			stmt.execute();
			stmt.close();
		}
		catch (Exception e)
		{
			_log.warning("Could not store trade list (" + itemID + ", " + price + ", " + tradeListID + ", " + order + "): " + e);
			return false;
		}
		return true;
	}
	
	private void updateTradeList(int itemID, long price, int tradeListID, int order)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			int updated = 0;
			if (Config.CUSTOM_MERCHANT_TABLES)
			{
				PreparedStatement stmt = con.prepareStatement("UPDATE `custom_merchant_buylists` SET `price` = ? WHERE `shop_id` = ? AND `order` = ?");
				stmt.setLong(1, price);
				stmt.setInt(2, tradeListID);
				stmt.setInt(3, order);
				updated = stmt.executeUpdate();
				stmt.close();
			}
			if (updated == 0)
			{
				PreparedStatement stmt = con.prepareStatement("UPDATE `merchant_buylists` SET `price` = ? WHERE `shop_id` = ? AND `order` = ?");
				stmt.setLong(1, price);
				stmt.setInt(2, tradeListID);
				stmt.setInt(3, order);
				updated = stmt.executeUpdate();
				stmt.close();
			}
		}
		catch (Exception e)
		{
			_log.warning("Could not update trade list (" + itemID + ", " + price + ", " + tradeListID + ", " + order + "): " + e);
		}
	}
	
	private void deleteTradeList(int tradeListID, int order)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			int updated = 0;
			if (Config.CUSTOM_MERCHANT_TABLES)
			{
				PreparedStatement stmt = con.prepareStatement("DELETE FROM `custom_merchant_buylists` WHERE `shop_id` = ? AND `order` = ?");
				stmt.setInt(1, tradeListID);
				stmt.setInt(2, order);
				updated = stmt.executeUpdate();
				stmt.close();
			}
			if (updated == 0)
			{
				PreparedStatement stmt = con.prepareStatement("DELETE FROM `merchant_buylists` WHERE `shop_id` = ? AND `order` = ?");
				stmt.setInt(1, tradeListID);
				stmt.setInt(2, order);
				updated = stmt.executeUpdate();
				stmt.close();
			}
		}
		catch (Exception e)
		{
			_log.warning("Could not delete trade list (" + tradeListID + ", " + order + "): " + e);
		}
	}
	
	private int findOrderTradeList(int itemID, long price, int tradeListID)
	{
		int order = -1;
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			PreparedStatement stmt = con.prepareStatement("SELECT `order` FROM `merchant_buylists` WHERE `shop_id` = ? AND `item_id` = ? AND `price` = ?");
			stmt.setInt(1, tradeListID);
			stmt.setInt(2, itemID);
			stmt.setLong(3, price);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.first())
			{
				order = rs.getInt("order");
			}
			
			stmt.close();
			rs.close();
		}
		catch (Exception e)
		{
			_log.warning("Could not get order for (" + itemID + ", " + price + ", " + tradeListID + "): " + e);
		}
		return order;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void showNpcProperty(L2PcInstance activeChar, L2NpcTemplate npc, String category)
	{
		if (category.equalsIgnoreCase("stats") || category.equalsIgnoreCase("ai") || category.equalsIgnoreCase("elementals") || category.equalsIgnoreCase("visuals"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(5, 1);
			html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/editnpc-" + category.toLowerCase() + ".htm");
			
			html.replace("%npcId%", String.valueOf(npc.getNpcId()));
			html.replace("%title_npc_id%", String.valueOf(npc.getNpcId()));
			html.replace("%title_npc_name%", String.valueOf(npc.getName()));
			switch (category.toLowerCase())
			{
				case "stats":
				{
					html.replace("%level%", String.valueOf(npc.getLevel()));
					html.replace("%exp%", String.valueOf(npc.getRewardExp()));
					html.replace("%sp%", String.valueOf(npc.getRewardSp()));
					html.replace("%hp%", String.valueOf(npc.getBaseHpMax()));
					html.replace("%mp%", String.valueOf(npc.getBaseMpMax()));
					html.replace("%hpreg%", String.valueOf(npc.getBaseHpReg()));
					html.replace("%mpreg%", String.valueOf(npc.getBaseMpReg()));
					html.replace("%patk%", String.valueOf(npc.getBasePAtk()));
					html.replace("%pdef%", String.valueOf(npc.getBasePDef()));
					html.replace("%matk%", String.valueOf(npc.getBaseMAtk()));
					html.replace("%mdef%", String.valueOf(npc.getBaseMDef()));
					html.replace("%atkspd%", String.valueOf(npc.getBasePAtkSpd()));
					html.replace("%matkspd%", String.valueOf(npc.getBaseMAtkSpd()));
					html.replace("%str%", String.valueOf(npc.getBaseSTR()));
					html.replace("%con%", String.valueOf(npc.getBaseCON()));
					html.replace("%dex%", String.valueOf(npc.getBaseDEX()));
					html.replace("%int%", String.valueOf(npc.getBaseINT()));
					html.replace("%wit%", String.valueOf(npc.getBaseWIT()));
					html.replace("%men%", String.valueOf(npc.getBaseMEN()));
					html.replace("%critical%", String.valueOf(npc.getBaseCritRate()));
					html.replace("%attackrange%", String.valueOf(npc.getBaseAtkRange()));
					html.replace("%walkspd%", String.valueOf(npc.getBaseMoveSpd(MoveType.WALK)));
					html.replace("%runspd%", String.valueOf(npc.getBaseMoveSpd(MoveType.RUN)));
					break;
				}
				case "ai":
				{
					html.replace("%aggro%", String.valueOf(npc.getAIDataStatic().getAggroRange()));
					html.replace("%clan%", String.valueOf(npc.getAIDataStatic().getClan()));
					html.replace("%clanRange%", String.valueOf(npc.getAIDataStatic().getClanRange()));
					html.replace("%enemyClan%", String.valueOf(npc.getAIDataStatic().getEnemyClan()));
					html.replace("%enemyRange%", String.valueOf(npc.getAIDataStatic().getEnemyRange()));
					html.replace("%dodge%", String.valueOf(npc.getAIDataStatic().getDodge()));
					html.replace("%canMove%", String.valueOf(npc.getAIDataStatic().getCanMove()));
					html.replace("%primarySkillId%", String.valueOf(npc.getAIDataStatic().getPrimarySkillId()));
					html.replace("%minSkillChance%", String.valueOf(npc.getAIDataStatic().getMinSkillChance()));
					html.replace("%maxSkillChance%", String.valueOf(npc.getAIDataStatic().getMaxSkillChance()));
					html.replace("%minRangeSkill%", String.valueOf(npc.getAIDataStatic().getShortRangeSkill()));
					html.replace("%minRangeChance%", String.valueOf(npc.getAIDataStatic().getShortRangeChance()));
					html.replace("%maxRangeSkill%", String.valueOf(npc.getAIDataStatic().getLongRangeSkill()));
					html.replace("%maxRangeChance%", String.valueOf(npc.getAIDataStatic().getLongRangeChance()));
					html.replace("%soulShot%", String.valueOf(npc.getAIDataStatic().getSoulShot()));
					html.replace("%ssChance%", String.valueOf(npc.getAIDataStatic().getSoulShotChance()));
					html.replace("%spiritShot%", String.valueOf(npc.getAIDataStatic().getSpiritShot()));
					html.replace("%spsChance%", String.valueOf(npc.getAIDataStatic().getSpiritShotChance()));
					html.replace("%isChaos%", String.valueOf(npc.getAIDataStatic().getIsChaos()));
					html.replace("%aiType%", String.valueOf(npc.getAIDataStatic().getAiType()));
					break;
				}
				case "elementals":
				{
					int elements[] =
					{
						npc.getBaseFire(),
						npc.getBaseWater(),
						npc.getBaseWind(),
						npc.getBaseEarth(),
						npc.getBaseHoly(),
						npc.getBaseDark()
					};
					byte attackAttribute = -1;
					int max_element = 0;
					for (byte i = 0; i < 6; i++)
					{
						if (elements[i] > max_element)
						{
							attackAttribute = i;
							max_element = elements[i];
						}
					}
					html.replace("%elemAtkType%", Elementals.getElementName(attackAttribute));
					html.replace("%elemAtkValue%", String.valueOf(attackAttribute != -1 ? elements[attackAttribute] : 0));
					html.replace("%fireDefValue%", String.valueOf(npc.getBaseFireRes()));
					html.replace("%waterDefValue%", String.valueOf(npc.getBaseWaterRes()));
					html.replace("%windDefValue%", String.valueOf(npc.getBaseWindRes()));
					html.replace("%earthDefValue%", String.valueOf(npc.getBaseEarthRes()));
					html.replace("%holyDefValue%", String.valueOf(npc.getBaseHolyRes()));
					html.replace("%darkDefValue%", String.valueOf(npc.getBaseDarkRes()));
					break;
				}
				case "visuals":
				{
					html.replace("%idTemplate%", String.valueOf(npc.getIdTemplate()));
					html.replace("%type%", String.valueOf(npc.getType()));
					html.replace("%showName%", String.valueOf(npc.getAIDataStatic().showName() ? 1 : 0));
					html.replace("%name%", npc.getName());
					html.replace("%serverSideName%", String.valueOf(npc.isServerSideName() ? 1 : 0));
					html.replace("%title%", npc.getTitle());
					html.replace("%serverSideTitle%", String.valueOf(npc.isServerSideTitle() ? 1 : 0));
					html.replace("%targetable%", String.valueOf(npc.getAIDataStatic().isTargetable() ? 1 : 0));
					html.replace("%rhand%", String.valueOf(npc.getRightHand()));
					html.replace("%lhand%", String.valueOf(npc.getLeftHand()));
					html.replace("%enchant%", String.valueOf(npc.getEnchantEffect()));
					html.replace("%collision_radius%", String.valueOf(npc.getCollisionRadius()));
					html.replace("%collision_height%", String.valueOf(npc.getCollisionHeight()));
					html.replace("%sex%", String.valueOf(npc.getSex()));
					html.replace("%dropHerbGroup%", String.valueOf(npc.getDropHerbGroup()));
					break;
				}
			}
			activeChar.sendPacket(html);
		}
		else
		{
			_log.warning(activeChar.getName() + " tried to see invalid category for showNpcProperty.");
		}
	}
	
	private void saveNpcProperty(L2PcInstance activeChar, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		st.nextToken();
		
		if (st.countTokens() < 3)
		{
			return;
		}
		
		StatsSet newNpcData = new StatsSet();
		try
		{
			String category = st.nextToken();
			newNpcData.set("npcId", st.nextToken());
			
			String statToSet = st.nextToken();
			String value = st.hasMoreTokens() ? st.nextToken() : "";
			
			while (st.hasMoreTokens())
			{
				value += " " + st.nextToken();
			}
			
			int npcId = newNpcData.getInteger("npcId");
			L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
			if (npc != null)
			{
				switch (statToSet)
				{
					case "serverSideName":
					case "serverSideTitle":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue == 0) || (intValue == 1))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0 or 1.");
							return;
						}
						break;
					}
					case "sex":
					{
						if (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female") || value.equalsIgnoreCase("etc"))
						{
							newNpcData.set(statToSet, value.toLowerCase());
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be male, female or etc.");
							return;
						}
						break;
					}
					case "enchant":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 50))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-50.");
							return;
						}
						break;
					}
					case "level":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 1) && (intValue <= 87))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 1-87.");
							return;
						}
						break;
					}
					case "str":
					case "con":
					case "dex":
					case "int":
					case "wit":
					case "men":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 1) && (intValue <= 99))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 1-99.");
							return;
						}
						break;
					}
					case "critical":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 1) && (intValue <= 127))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 1-127.");
							return;
						}
						break;
					}
					case "dropHerbGroup":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 127))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-127.");
							return;
						}
						break;
					}
					case "atkspd":
					case "matkspd":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 1) && (intValue <= 1000))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 1-1000.");
							return;
						}
						break;
					}
					case "attackrange":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 1) && (intValue <= 2000))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 1-2000.");
							return;
						}
						break;
					}
					case "rhand":
					case "lhand":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 65535))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-65535.");
							return;
						}
						break;
					}
					case "idTemplate":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 1) && (intValue <= 65535))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 1-65535.");
							return;
						}
						break;
					}
					case "exp":
					case "sp":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 2147483647))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-2147483647.");
							return;
						}
						break;
					}
					case "collision_radius":
					case "collision_height":
					{
						double doubleValue = Double.parseDouble(value);
						if ((doubleValue >= -9999.99) && (doubleValue <= 9999.99))
						{
							newNpcData.set(statToSet, doubleValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be -9999.99-9999.99.");
							return;
						}
						break;
					}
					case "walkspd":
					case "runspd":
					{
						double doubleValue = Double.parseDouble(value);
						if ((doubleValue >= 0) && (doubleValue <= 99999.99999))
						{
							newNpcData.set(statToSet, doubleValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-99999.99999.");
							return;
						}
						break;
					}
					case "patk":
					case "pdef":
					case "matk":
					case "mdef":
					{
						double doubleValue = Double.parseDouble(value);
						if ((doubleValue >= 0) && (doubleValue <= 9999999.99999))
						{
							newNpcData.set(statToSet, doubleValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-9999999.99999.");
							return;
						}
						break;
					}
					case "hp":
					case "mp":
					case "hpreg":
					case "mpreg":
					{
						double doubleValue = Double.parseDouble(value);
						if ((doubleValue >= 0) && (doubleValue <= 999999999999999.999999999999999))
						{
							newNpcData.set(statToSet, doubleValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-999999999999999.999999999999999.");
							return;
						}
						break;
					}
					case "type":
					{
						if ((value.length() >= 1) && (value.length() <= 22))
						{
							Class.forName("com.l2jserver.gameserver.model.actor.instance." + value + "Instance");
							newNpcData.set(statToSet, value);
						}
						else
						{
							activeChar.sendMessage("Length of " + statToSet + " must be 1-22.");
							return;
						}
						break;
					}
					case "title":
					{
						if ((value.length() >= 0) && (value.length() <= 45))
						{
							newNpcData.set(statToSet, value);
						}
						else
						{
							activeChar.sendMessage("Length of " + statToSet + " must be 1-45.");
							return;
						}
						break;
					}
					case "name":
					{
						if ((value.length() >= 0) && (value.length() <= 200))
						{
							newNpcData.set(statToSet, value);
						}
						else
						{
							activeChar.sendMessage("Length of " + statToSet + " must be 1-200.");
							return;
						}
						break;
					}
					case "canMove":
					case "targetable":
					case "showName":
					case "isChaos":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue == 0) || (intValue == 1))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0 or 1.");
							return;
						}
						break;
					}
					case "dodge":
					case "minSkillChance":
					case "maxSkillChance":
					case "minRangeChance":
					case "maxRangeChance":
					case "ssChance":
					case "spsChance":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 100))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-100.");
							return;
						}
						break;
					}
					case "aggro":
					case "clanRange":
					case "enemyRange":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 3000))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-3000.");
							return;
						}
						break;
					}
					case "primarySkillId":
					case "minRangeSkill":
					case "maxRangeSkill":
					case "soulShot":
					case "spiritShot":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 65535))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-65535.");
							return;
						}
						break;
					}
					case "clan":
					case "enemyClan":
					{
						if (value.isEmpty())
						{
							newNpcData.set(statToSet, "null");
						}
						else if ((value.length() >= 1) && (value.length() <= 40))
						{
							
							newNpcData.set(statToSet, value);
						}
						else
						{
							activeChar.sendMessage("Length of " + statToSet + " must be 1-40 or empty for null.");
							return;
						}
						break;
					}
					case "aiType":
					{
						switch (value)
						{
							case "fighter":
							case "archer":
							case "mage":
							case "healer":
							case "balanced":
							case "corpse":
							{
								newNpcData.set(statToSet, value);
								break;
							}
							default:
							{
								activeChar.sendMessage("Value of " + statToSet + " must be fighter, archer, mage, healer, balanced, or corpse.");
								return;
							}
						}
						break;
					}
					case "elemAtkType":
					{
						switch (value)
						{
							case "fire":
							{
								newNpcData.set(statToSet, Elementals.FIRE);
								break;
							}
							case "water":
							{
								newNpcData.set(statToSet, Elementals.WATER);
								break;
							}
							case "earth":
							{
								newNpcData.set(statToSet, Elementals.EARTH);
								break;
							}
							case "wind":
							{
								newNpcData.set(statToSet, Elementals.WIND);
								break;
							}
							case "holy":
							{
								newNpcData.set(statToSet, Elementals.HOLY);
								break;
							}
							case "dark":
							{
								newNpcData.set(statToSet, Elementals.DARK);
								break;
							}
							default:
							{
								activeChar.sendMessage("Value of " + statToSet + " must be fire, water, earth, wind, holy or dark.");
								return;
							}
						}
						break;
					}
					case "elemAtkValue":
					case "fireDefValue":
					case "waterDefValue":
					case "windDefValue":
					case "earthDefValue":
					case "holyDefValue":
					case "darkDefValue":
					{
						int intValue = Integer.parseInt(value);
						if ((intValue >= 0) && (intValue <= 3000))
						{
							newNpcData.set(statToSet, intValue);
						}
						else
						{
							activeChar.sendMessage("Value of " + statToSet + " must be 0-3000.");
							return;
						}
						break;
					}
					default:
					{
						
						activeChar.sendMessage("Unknown stat " + statToSet + " can't set.");
						return;
					}
				}
				NpcTable.getInstance().saveNpc(newNpcData);
				NpcTable.getInstance().reloadNpc(npcId, true, true, true, false, false, false);
				npc = NpcTable.getInstance().getTemplate(npcId);
				showNpcProperty(activeChar, npc, category);
			}
			else
			{
				activeChar.sendMessage("NPC does not exist or not loaded.");
			}
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not save npc property!");
			_log.warning("Error saving new npc value (" + command + "): " + e);
		}
	}
	
	private void showNpcDropList(L2PcInstance activeChar, int npcId, int page)
	{
		L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
		if (npcData == null)
		{
			activeChar.sendMessage("Unknown npc template id " + npcId);
			return;
		}
		
		final StringBuilder replyMSG = new StringBuilder(2900);
		replyMSG.append("<html><title>Show droplist page ");
		replyMSG.append(page);
		replyMSG.append("</title><body><br1><center><font color=\"LEVEL\">");
		replyMSG.append(npcData.getName());
		replyMSG.append(" (");
		replyMSG.append(npcId);
		replyMSG.append(")</font></center><br1><table width=\"100%\" border=0><tr><td width=35>cat.</td><td width=210>item</td><td width=30>type</td><td width=25>del</td></tr>");
		
		int myPage = 1;
		int i = 0;
		int shown = 0;
		boolean hasMore = false;
		if (npcData.getDropData() != null)
		{
			for (L2DropCategory cat : npcData.getDropData())
			{
				if (shown == PAGE_LIMIT)
				{
					hasMore = true;
					break;
				}
				for (L2DropData drop : cat.getAllDrops())
				{
					if (myPage != page)
					{
						i++;
						if (i == PAGE_LIMIT)
						{
							myPage++;
							i = 0;
						}
						continue;
					}
					if (shown == PAGE_LIMIT)
					{
						hasMore = true;
						break;
					}
					
					replyMSG.append("<tr><td>");
					replyMSG.append(cat.getCategoryType());
					replyMSG.append("</td><td><a action=\"bypass -h admin_edit_drop ");
					replyMSG.append(npcId);
					replyMSG.append(" ");
					replyMSG.append(drop.getItemId());
					replyMSG.append(" ");
					replyMSG.append(cat.getCategoryType());
					replyMSG.append("\">");
					replyMSG.append(ItemTable.getInstance().getTemplate(drop.getItemId()).getName());
					replyMSG.append(" (");
					replyMSG.append(drop.getItemId());
					replyMSG.append(")</a></td><td>");
					replyMSG.append((drop.isQuestDrop() ? "Q" : (cat.isSweep() ? "S" : "D")));
					replyMSG.append("</td><td><a action=\"bypass -h admin_del_drop ");
					replyMSG.append(npcId);
					replyMSG.append(" ");
					replyMSG.append(drop.getItemId());
					replyMSG.append(" ");
					replyMSG.append(cat.getCategoryType());
					replyMSG.append("\">del</a></td></tr>");
					shown++;
				}
			}
		}
		
		replyMSG.append("</table><table width=300 bgcolor=666666 border=0><tr>");
		
		if (page > 1)
		{
			replyMSG.append("<td width=120><a action=\"bypass -h admin_show_droplist ");
			replyMSG.append(npcId);
			replyMSG.append(" ");
			replyMSG.append(page - 1);
			replyMSG.append("\">Prev Page</a></td>");
			if (!hasMore)
			{
				replyMSG.append("<td width=100>Page ");
				replyMSG.append(page);
				replyMSG.append("</td><td width=70></td></tr>");
			}
		}
		if (hasMore)
		{
			if (page <= 1)
			{
				replyMSG.append("<td width=120></td>");
			}
			replyMSG.append("<td width=100>Page ");
			replyMSG.append(page);
			replyMSG.append("</td><td width=70><a action=\"bypass -h admin_show_droplist ");
			replyMSG.append(npcId);
			replyMSG.append(" ");
			replyMSG.append(page + 1);
			replyMSG.append("\">Next Page</a></td></tr>");
		}
		
		replyMSG.append("</table><center><br><button value=\"Add Drop Data\" action=\"bypass -h admin_add_drop ");
		replyMSG.append(npcId);
		replyMSG.append("\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Close\" action=\"bypass -h admin_close_window\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void showEditDropData(L2PcInstance activeChar, int npcId, int itemId, int category)
	{
		L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
		if (npcData == null)
		{
			activeChar.sendMessage("Unknown npc template id " + npcId);
			return;
		}
		
		L2Item itemData = ItemTable.getInstance().getTemplate(itemId);
		if (itemData == null)
		{
			activeChar.sendMessage("Unknown item template id " + itemId);
			return;
		}
		
		final StringBuilder replyMSG = new StringBuilder();
		replyMSG.append("<html><title>Edit drop data</title><body>");
		
		List<L2DropData> dropDatas = null;
		if (npcData.getDropData() != null)
		{
			for (L2DropCategory dropCat : npcData.getDropData())
			{
				if (dropCat.getCategoryType() == category)
				{
					dropDatas = dropCat.getAllDrops();
					break;
				}
			}
		}
		
		L2DropData dropData = null;
		if (dropDatas != null)
		{
			for (L2DropData drop : dropDatas)
			{
				if (drop.getItemId() == itemId)
				{
					dropData = drop;
					break;
				}
			}
		}
		
		if (dropData != null)
		{
			replyMSG.append("<table width=\"100%\"><tr><td>Npc</td><td>");
			replyMSG.append(npcData.getName());
			replyMSG.append(" (");
			replyMSG.append(npcId);
			replyMSG.append(")</td></tr><tr><td>Item</td><td>");
			replyMSG.append(itemData.getName());
			replyMSG.append(" (");
			replyMSG.append(itemId);
			replyMSG.append(")</td></tr><tr><td>Category</td><td>");
			replyMSG.append(((category == -1) ? "-1 (sweep)" : Integer.toString(category)));
			replyMSG.append("</td></tr>");
			replyMSG.append("<tr><td>Min count (");
			replyMSG.append(dropData.getMinDrop());
			replyMSG.append(")</td><td><edit var=\"min\" width=80></td></tr><tr><td>Max count (");
			replyMSG.append(dropData.getMaxDrop());
			replyMSG.append(")</td><td><edit var=\"max\" width=80></td></tr><tr><td>Chance (");
			replyMSG.append(dropData.getChance());
			replyMSG.append(")</td><td><edit var=\"chance\" width=80></td></tr></table><br>");
			
			replyMSG.append("<center><br><button value=\"Save\" action=\"bypass -h admin_edit_drop ");
			replyMSG.append(npcId);
			replyMSG.append(" ");
			replyMSG.append(itemId);
			replyMSG.append(" ");
			replyMSG.append(category);
			replyMSG.append(" $min $max $chance\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
		}
		else
		{
			replyMSG.append("No drop data detail found.<center><br>");
		}
		replyMSG.append("<button value=\"Back to Droplist\" action=\"bypass -h admin_show_droplist ");
		replyMSG.append(npcId);
		replyMSG.append("\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center>");
		replyMSG.append("</body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void showAddDropData(L2PcInstance activeChar, L2NpcTemplate npcData)
	{
		final String replyMSG = StringUtil.concat("<html><title>Add drop data</title><body><table width=\"100%\"><tr><td>Npc</td><td>", npcData.getName(), " (", Integer.toString(npcData.getNpcId()), ")", "</td></tr><tr><td>Item Id</td><td><edit var=\"itemId\" width=80></td></tr><tr><td>Min count</td><td><edit var=\"min\" width=80></td></tr><tr><td>Max count</td><td><edit var=\"max\" width=80></td></tr><tr><td>Category (sweep=-1)</td><td><edit var=\"category\" width=80></td></tr><tr><td>Chance (0-1000000)</td><td><edit var=\"chance\" width=80></td></tr></table><center><br><button value=\"Add\" action=\"bypass -h admin_add_drop ", Integer.toString(npcData.getNpcId()), " $itemId $category $min $max $chance\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Droplist\" action=\"bypass -h admin_show_droplist ", Integer.toString(npcData.getNpcId()), "\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG);
		activeChar.sendPacket(adminReply);
	}
	
	private void updateDropData(L2PcInstance activeChar, int npcId, int itemId, int min, int max, int category, int chance)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			int updated = 0;
			if (Config.CUSTOM_DROPLIST_TABLE)
			{
				PreparedStatement statement = con.prepareStatement("UPDATE `custom_droplist` SET `min`=?, `max`=?, `chance`=? WHERE `mobId`=? AND `itemId`=? AND `category`=?");
				statement.setInt(1, min);
				statement.setInt(2, max);
				statement.setInt(3, chance);
				statement.setInt(4, npcId);
				statement.setInt(5, itemId);
				statement.setInt(6, category);
				
				updated = statement.executeUpdate();
				statement.close();
			}
			if (updated == 0)
			{
				PreparedStatement statement = con.prepareStatement("UPDATE `droplist` SET `min`=?, `max`=?, `chance`=? WHERE `mobId`=? AND `itemId`=? AND `category`=?");
				statement.setInt(1, min);
				statement.setInt(2, max);
				statement.setInt(3, chance);
				statement.setInt(4, npcId);
				statement.setInt(5, itemId);
				statement.setInt(6, category);
				
				updated = statement.executeUpdate();
				statement.close();
			}
			
			reloadNpcDropList(npcId);
			
			showNpcDropList(activeChar, npcId, 1);
			activeChar.sendMessage("Updated drop data for npc id " + npcId + " and item id " + itemId + " in category " + category + ".");
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not update drop data!");
			_log.warning("Error while updating drop data (" + npcId + ", " + itemId + ", " + min + ", " + max + ", " + category + ", " + chance + "): " + e);
		}
	}
	
	private void addDropData(L2PcInstance activeChar, int npcId, int itemId, int min, int max, int category, int chance)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String table = "droplist";
			if (Config.CUSTOM_DROPLIST_TABLE)
			{
				table = "custom_droplist";
			}
			
			PreparedStatement statement = con.prepareStatement("INSERT INTO `" + table + "`(`mobId`, `itemId`, `min`, `max`, `category`, `chance`) VALUES(?,?,?,?,?,?)");
			statement.setInt(1, npcId);
			statement.setInt(2, itemId);
			statement.setInt(3, min);
			statement.setInt(4, max);
			statement.setInt(5, category);
			statement.setInt(6, chance);
			statement.execute();
			statement.close();
			
			reloadNpcDropList(npcId);
			
			showNpcDropList(activeChar, npcId, 1);
			activeChar.sendMessage("Added drop data for npc id " + npcId + " with item id " + itemId + " in category " + category + ".");
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not add drop data!");
			_log.warning("Error while adding drop data (" + npcId + ", " + itemId + ", " + min + ", " + max + ", " + category + ", " + chance + "): " + e);
		}
	}
	
	private void deleteDropData(L2PcInstance activeChar, int npcId, int itemId, int category, boolean confirmed)
	{
		if (!confirmed)
		{
			final String replyMSG = StringUtil.concat("<html><title>Drop Data Delete</title><body><br>Delete drop data.", "<table width=\"100%\"><tr><td>NPC</td><td>", NpcTable.getInstance().getTemplate(npcId).getName(), " (", Integer.toString(npcId), ")</td></tr><tr><td>Item ID</td><td>", Integer.toString(itemId), "</td></tr><tr><td>Category</td><td>", Integer.toString(category), "</td></tr></table><center><br><button value=\"Delete\" action=\"bypass -h admin_del_drop ", Integer.toString(npcId), " ", Integer.toString(itemId), " ", Integer.toString(category), " 1\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Droplist\" action=\"bypass -h admin_show_droplist ", Integer.toString(npcId), "\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
			
			NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
			adminReply.setHtml(replyMSG);
			activeChar.sendPacket(adminReply);
			return;
		}
		
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			int updated = 0;
			if (Config.CUSTOM_DROPLIST_TABLE)
			{
				PreparedStatement statement = con.prepareStatement("DELETE FROM `custom_droplist` WHERE `mobId`=? AND `itemId`=? AND `category`=?");
				statement.setInt(1, npcId);
				statement.setInt(2, itemId);
				statement.setInt(3, category);
				updated = statement.executeUpdate();
				statement.close();
			}
			if (updated == 0)
			{
				PreparedStatement statement = con.prepareStatement("DELETE FROM `droplist` WHERE `mobId`=? AND `itemId`=? AND `category`=?");
				statement.setInt(1, npcId);
				statement.setInt(2, itemId);
				statement.setInt(3, category);
				updated = statement.executeUpdate();
				statement.close();
			}
			
			reloadNpcDropList(npcId);
			
			showNpcDropList(activeChar, npcId, 1);
			activeChar.sendMessage("Deleted drop data for npc id " + npcId + " and item id " + itemId + " in category " + category + ".");
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not delete drop data!");
			_log.warning("Error while deleting drop data (" + npcId + ", " + itemId + ", " + category + "): " + e);
		}
	}
	
	private void reloadNpcDropList(int npcId)
	{
		L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
		if (npcData == null)
		{
			return;
		}
		
		// reset the drop lists
		npcData.clearAllDropData();
		
		// get the drops
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			L2DropData dropData = null;
			
			PreparedStatement statement = con.prepareStatement("SELECT `mobId`, `itemId`, `min`, `max`, `category`, `chance` FROM `droplist` WHERE `mobId`=?");
			statement.setInt(1, npcId);
			ResultSet dropDataList = statement.executeQuery();
			
			while (dropDataList.next())
			{
				dropData = new L2DropData();
				
				dropData.setItemId(dropDataList.getInt("itemId"));
				dropData.setMinDrop(dropDataList.getInt("min"));
				dropData.setMaxDrop(dropDataList.getInt("max"));
				dropData.setChance(dropDataList.getInt("chance"));
				
				int category = dropDataList.getInt("category");
				npcData.addDropData(dropData, category);
			}
			dropDataList.close();
			statement.close();
			
			if (Config.CUSTOM_DROPLIST_TABLE)
			{
				PreparedStatement statement2 = con.prepareStatement("SELECT `mobId`, `itemId`, `min`, `max`, `category`, `chance` FROM `custom_droplist` WHERE `mobId`=?");
				statement2.setInt(1, npcId);
				ResultSet dropDataList2 = statement2.executeQuery();
				
				while (dropDataList2.next())
				{
					dropData = new L2DropData();
					
					dropData.setItemId(dropDataList2.getInt("itemId"));
					dropData.setMinDrop(dropDataList2.getInt("min"));
					dropData.setMaxDrop(dropDataList2.getInt("max"));
					dropData.setChance(dropDataList2.getInt("chance"));
					
					int category = dropDataList2.getInt("category");
					npcData.addDropData(dropData, category);
				}
				dropDataList2.close();
				statement2.close();
			}
		}
		catch (Exception e)
		{
			_log.warning("Error while reloading npc droplist (" + npcId + "): " + e);
		}
	}
	
	private void showNpcSkillList(L2PcInstance activeChar, int npcId, int page)
	{
		L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
		if (npcData == null)
		{
			activeChar.sendMessage("Template id unknown: " + npcId);
			return;
		}
		
		Map<Integer, L2Skill> skills = new HashMap<>(npcData.getSkills());
		int _skillsize = skills.size();
		
		int MaxSkillsPerPage = PAGE_LIMIT;
		int MaxPages = _skillsize / MaxSkillsPerPage;
		if (_skillsize > (MaxSkillsPerPage * MaxPages))
		{
			MaxPages++;
		}
		
		if (page > MaxPages)
		{
			page = MaxPages;
		}
		
		int SkillsStart = MaxSkillsPerPage * page;
		int SkillsEnd = _skillsize;
		if ((SkillsEnd - SkillsStart) > MaxSkillsPerPage)
		{
			SkillsEnd = SkillsStart + MaxSkillsPerPage;
		}
		
		StringBuffer replyMSG = new StringBuffer("<html><title>Show NPC Skill List</title><body><center><font color=\"LEVEL\">");
		replyMSG.append(npcData.getName());
		replyMSG.append(" (");
		replyMSG.append(npcData.getNpcId());
		replyMSG.append("): ");
		replyMSG.append(_skillsize);
		replyMSG.append(" skills</font></center><table width=300 bgcolor=666666><tr>");
		
		for (int x = 0; x < MaxPages; x++)
		{
			int pagenr = x + 1;
			if (page == x)
			{
				replyMSG.append("<td>Page ");
				replyMSG.append(pagenr);
				replyMSG.append("</td>");
			}
			else
			{
				replyMSG.append("<td><a action=\"bypass -h admin_show_skilllist_npc ");
				replyMSG.append(npcData.getNpcId());
				replyMSG.append(" ");
				replyMSG.append(x);
				replyMSG.append("\"> Page ");
				replyMSG.append(pagenr);
				replyMSG.append(" </a></td>");
			}
		}
		replyMSG.append("</tr></table><table width=\"100%\" border=0><tr><td>Skill name [skill id-skill lvl]</td><td>Delete</td></tr>");
		Iterator<L2Skill> skillite = skills.values().iterator();
		
		for (int i = 0; i < SkillsStart; i++)
		{
			if (skillite.hasNext())
			{
				skillite.next();
			}
		}
		
		int cnt = SkillsStart;
		L2Skill sk;
		while (skillite.hasNext())
		{
			cnt++;
			if (cnt > SkillsEnd)
			{
				break;
			}
			
			sk = skillite.next();
			replyMSG.append("<tr><td width=240><a action=\"bypass -h admin_edit_skill_npc ");
			replyMSG.append(npcData.getNpcId());
			replyMSG.append(" ");
			replyMSG.append(sk.getId());
			replyMSG.append("\">");
			if (sk.getSkillType() == L2SkillType.NOTDONE)
			{
				replyMSG.append("<font color=\"777777\">" + sk.getName() + "</font>");
			}
			else
			{
				replyMSG.append(sk.getName());
			}
			replyMSG.append(" [");
			replyMSG.append(sk.getId());
			replyMSG.append("-");
			replyMSG.append(sk.getLevel());
			replyMSG.append("]</a></td><td width=60><a action=\"bypass -h admin_del_skill_npc ");
			replyMSG.append(npcData.getNpcId());
			replyMSG.append(" ");
			replyMSG.append(sk.getId());
			replyMSG.append("\">Delete</a></td></tr>");
		}
		replyMSG.append("</table><br><center><button value=\"Add Skill\" action=\"bypass -h admin_add_skill_npc ");
		replyMSG.append(npcId);
		replyMSG.append("\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Close\" action=\"bypass -h admin_close_window\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void showNpcSkillEdit(L2PcInstance activeChar, int npcId, int skillId)
	{
		try
		{
			StringBuffer replyMSG = new StringBuffer("<html><title>NPC Skill Edit</title><body>");
			
			L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
			if (npcData == null)
			{
				activeChar.sendMessage("Template id unknown: " + npcId);
				return;
			}
			if (npcData.getSkills() == null)
			{
				return;
			}
			
			L2Skill npcSkill = npcData.getSkills().get(skillId);
			
			if (npcSkill != null)
			{
				replyMSG.append("<table width=\"100%\"><tr><td>NPC: </td><td>");
				replyMSG.append(NpcTable.getInstance().getTemplate(npcId).getName());
				replyMSG.append(" (");
				replyMSG.append(npcId);
				replyMSG.append(")</td></tr><tr><td>Skill: </td><td>");
				replyMSG.append(npcSkill.getName());
				replyMSG.append(" (");
				replyMSG.append(skillId);
				replyMSG.append(")</td></tr><tr><td>Skill Lvl: (");
				replyMSG.append(npcSkill.getLevel());
				replyMSG.append(") </td><td><edit var=\"level\" width=50></td></tr></table><br><center><button value=\"Save\" action=\"bypass -h admin_edit_skill_npc ");
				replyMSG.append(npcId);
				replyMSG.append(" ");
				replyMSG.append(skillId);
				replyMSG.append(" $level\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><br1><button value=\"Back to SkillList\" action=\"bypass -h admin_show_skilllist_npc ");
				replyMSG.append(npcId);
				replyMSG.append("\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center>");
			}
			
			replyMSG.append("</body></html>");
			
			NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
			adminReply.setHtml(replyMSG.toString());
			activeChar.sendPacket(adminReply);
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not edit npc skills!");
			_log.warning("Error while editing npc skills (" + npcId + ", " + skillId + "): " + e);
		}
	}
	
	private void updateNpcSkillData(L2PcInstance activeChar, int npcId, int skillId, int level)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			L2Skill skillData = SkillTable.getInstance().getInfo(skillId, level);
			if (skillData == null)
			{
				activeChar.sendMessage("Could not update npc skill: not existing skill id with that level!");
				showNpcSkillEdit(activeChar, npcId, skillId);
				return;
			}
			
			if (skillData.getLevel() != level)
			{
				activeChar.sendMessage("Skill id with requested level doesn't exist! Skill level not changed.");
				showNpcSkillEdit(activeChar, npcId, skillId);
				return;
			}
			
			int updated = 0;
			if (Config.CUSTOM_NPC_SKILLS_TABLE)
			{
				PreparedStatement statement2 = con.prepareStatement("UPDATE `custom_npcskills` SET `level`=? WHERE `npcid`=? AND `skillid`=?");
				statement2.setInt(1, level);
				statement2.setInt(2, npcId);
				statement2.setInt(3, skillId);
				
				updated = statement2.executeUpdate();
				statement2.close();
			}
			if (updated == 0)
			{
				PreparedStatement statement = con.prepareStatement("UPDATE `npcskills` SET `level`=? WHERE `npcid`=? AND `skillid`=?");
				statement.setInt(1, level);
				statement.setInt(2, npcId);
				statement.setInt(3, skillId);
				
				statement.execute();
				statement.close();
			}
			reloadNpcSkillList(npcId);
			
			showNpcSkillList(activeChar, npcId, 0);
			activeChar.sendMessage("Updated skill id " + skillId + " for npc id " + npcId + " to level " + level + ".");
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not update npc skill!");
			_log.warning("Error while updating npc skill (" + npcId + ", " + skillId + ", " + level + "): " + e);
		}
	}
	
	private void showNpcSkillAdd(L2PcInstance activeChar, int npcId)
	{
		L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
		
		StringBuffer replyMSG = new StringBuffer("<html><title>NPC Skill Add</title><body><table width=\"100%\"><tr><td>NPC: </td><td>");
		replyMSG.append(npcData.getName());
		replyMSG.append(" (");
		replyMSG.append(npcData.getNpcId());
		replyMSG.append(")</td></tr><tr><td>SkillId: </td><td><edit var=\"skillId\" width=80></td></tr><tr><td>Level: </td><td><edit var=\"level\" width=80></td></tr></table><br><center><button value=\"Add Skill\" action=\"bypass -h admin_add_skill_npc ");
		replyMSG.append(npcData.getNpcId());
		replyMSG.append(" $skillId $level\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><br1><button value=\"Back to SkillList\" action=\"bypass -h admin_show_skilllist_npc ");
		replyMSG.append(npcData.getNpcId());
		replyMSG.append("\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void addNpcSkillData(L2PcInstance activeChar, int npcId, int skillId, int level)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			// skill check
			L2Skill skillData = SkillTable.getInstance().getInfo(skillId, level);
			if (skillData == null)
			{
				activeChar.sendMessage("Could not add npc skill: not existing skill id with that level!");
				showNpcSkillAdd(activeChar, npcId);
				return;
			}
			
			if (Config.CUSTOM_NPC_SKILLS_TABLE)
			{
				PreparedStatement statement = con.prepareStatement("INSERT INTO `custom_npcskills`(`npcid`, `skillid`, `level`) VALUES(?,?,?)");
				statement.setInt(1, npcId);
				statement.setInt(2, skillId);
				statement.setInt(3, level);
				statement.execute();
				statement.close();
			}
			else
			{
				PreparedStatement statement = con.prepareStatement("INSERT INTO `npcskills`(`npcid`, `skillid`, `level`) VALUES(?,?,?)");
				statement.setInt(1, npcId);
				statement.setInt(2, skillId);
				statement.setInt(3, level);
				statement.execute();
				statement.close();
			}
			
			reloadNpcSkillList(npcId);
			
			showNpcSkillList(activeChar, npcId, 0);
			activeChar.sendMessage("Added skill " + skillId + "-" + level + " to npc id " + npcId + ".");
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not add npc skill!");
			_log.warning("Error while adding a npc skill (" + npcId + ", " + skillId + ", " + level + "): " + e);
		}
	}
	
	private void deleteNpcSkillData(L2PcInstance activeChar, int npcId, int skillId)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			if (npcId > 0)
			{
				int updated = 0;
				if (Config.CUSTOM_NPC_SKILLS_TABLE)
				{
					PreparedStatement statement = con.prepareStatement("DELETE FROM `custom_npcskills` WHERE `npcid`=? AND `skillid`=?");
					statement.setInt(1, npcId);
					statement.setInt(2, skillId);
					updated = statement.executeUpdate();
					statement.close();
				}
				if (updated == 0)
				{
					PreparedStatement statement2 = con.prepareStatement("DELETE FROM `npcskills` WHERE `npcid`=? AND `skillid`=?");
					statement2.setInt(1, npcId);
					statement2.setInt(2, skillId);
					statement2.execute();
					statement2.close();
				}
				
				reloadNpcSkillList(npcId);
				
				showNpcSkillList(activeChar, npcId, 0);
				activeChar.sendMessage("Deleted skill id " + skillId + " from npc id " + npcId + ".");
			}
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not delete npc skill!");
			_log.warning("Error while deleting npc skill (" + npcId + ", " + skillId + "): " + e);
		}
	}
	
	private void reloadNpcSkillList(int npcId)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
			
			L2Skill skillData = null;
			if (npcData.getSkills() != null)
			{
				npcData.getSkills().clear();
			}
			
			// without race
			PreparedStatement statement = con.prepareStatement("SELECT `skillid`, `level` FROM `npcskills` WHERE `npcid`=? AND `skillid` <> 4416");
			statement.setInt(1, npcId);
			ResultSet skillDataList = statement.executeQuery();
			
			while (skillDataList.next())
			{
				int idval = skillDataList.getInt("skillid");
				int levelval = skillDataList.getInt("level");
				skillData = SkillTable.getInstance().getInfo(idval, levelval);
				if (skillData != null)
				{
					npcData.addSkill(skillData);
				}
			}
			skillDataList.close();
			statement.close();
			
			if (Config.CUSTOM_NPC_SKILLS_TABLE)
			{
				PreparedStatement statement2 = con.prepareStatement("SELECT `skillid`, `level` FROM `npcskills` WHERE `npcid`=? AND `skillid` <> 4416");
				statement2.setInt(1, npcId);
				ResultSet skillDataList2 = statement2.executeQuery();
				
				while (skillDataList2.next())
				{
					int idval = skillDataList2.getInt("skillid");
					int levelval = skillDataList2.getInt("level");
					skillData = SkillTable.getInstance().getInfo(idval, levelval);
					if (skillData != null)
					{
						npcData.addSkill(skillData);
					}
				}
				skillDataList2.close();
				statement2.close();
			}
		}
		catch (Exception e)
		{
			_log.warning("Error while reloading npc skill list (" + npcId + "): " + e);
		}
	}
}
