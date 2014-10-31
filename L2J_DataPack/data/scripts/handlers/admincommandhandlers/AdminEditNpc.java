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
package handlers.admincommandhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javolution.util.FastMap;

import com.l2jserver.Config;
import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.TradeController;
import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.datatables.MerchantPriceConfigTable.MerchantPriceConfig;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2DropCategory;
import com.l2jserver.gameserver.model.L2DropData;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2TradeList;
import com.l2jserver.gameserver.model.L2TradeList.L2TradeItem;
import com.l2jserver.gameserver.model.actor.instance.L2MerchantInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.templates.StatsSet;
import com.l2jserver.gameserver.templates.chars.L2NpcTemplate;
import com.l2jserver.gameserver.templates.item.L2Item;
import com.l2jserver.util.StringUtil;

/**
 * @author terry
 * 
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
		"admin_del_skill_npc"
	};
	
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		//TODO: Tokenize and protect arguments parsing. Externalize HTML.
		if (command.startsWith("admin_showShop "))
		{
			String[] args = command.split(" ");
			if (args.length > 1)
				showShop(activeChar, Integer.parseInt(command.split(" ")[1]));
		}
		else if (command.startsWith("admin_showShopList "))
		{
			String[] args = command.split(" ");
			if (args.length > 2)
				showShopList(activeChar, Integer.parseInt(command.split(" ")[1]), Integer.parseInt(command.split(" ")[2]));
		}
		else if (command.startsWith("admin_edit_npc "))
		{
			try
			{
				String[] commandSplit = command.split(" ");
				int npcId = Integer.parseInt(commandSplit[1]);
				L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
				showNpcProperty(activeChar, npc);
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Wrong usage: //edit_npc <npcId>");
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
					page = Integer.parseInt(st.nextToken());
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
				addShopItem(activeChar, args);
		}
		else if (command.startsWith("admin_delShopItem "))
		{
			String[] args = command.split(" ");
			if (args.length > 2)
				delShopItem(activeChar, args);
		}
		else if (command.startsWith("admin_editShopItem "))
		{
			String[] args = command.split(" ");
			if (args.length > 2)
				editShopItem(activeChar, args);
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
					activeChar.sendMessage("Usage: //edit_drop <npc_id> <item_id> <category> [<min> <max> <chance>]");
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
							return true;
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
					activeChar.sendMessage("Usage: //add_drop <npc_id> [<item_id> <category> <min> <max> <chance>]");
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
					confirmed = true;
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
					page = Integer.parseInt(st.nextToken());
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
					showNpcSkillEdit(activeChar, npcId, skillId);
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
			return;
		
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
		
		final String replyMSG = StringUtil.concat("<html><title>Merchant Shop Item Edit</title><body><center><font color=\"LEVEL\">", 
				NpcTable.getInstance().getTemplate(Integer.parseInt(tradeList.getNpcId())).getName(), 
				" (", 
				tradeList.getNpcId(), 
				") -> ", 
				Integer.toString(tradeListID), 
				"</font></center><table width=\"100%\"><tr><td>Item</td><td>", 
				item.getName(), 
				" (", 
				Integer.toString(item.getItemId()), 
				")", 
				"</td></tr><tr><td>Price (", 
				String.valueOf(tradeList.getPriceForItemId(itemID)), 
				")</td><td><edit var=\"price\" width=80></td></tr></table><center><br><button value=\"Save\" action=\"bypass -h admin_editShopItem ", 
				String.valueOf(tradeListID), 
				" ", 
				String.valueOf(itemID), 
				" $price\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Shop List\" action=\"bypass -h admin_showShopList ", 
				String.valueOf(tradeListID), 
				" 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
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
			return;
		
		if (args.length > 3)
		{
			int order = findOrderTradeList(itemID, tradeList.getPriceForItemId(itemID), tradeListID);
			
			tradeList.removeItem(itemID);
			deleteTradeList(tradeListID, order);
			
			activeChar.sendMessage("Deleted " + ItemTable.getInstance().getTemplate(itemID).getName() + " from Trade List " + tradeListID);
			showShopList(activeChar, tradeListID, 1);
			return;
		}
		
		final String replyMSG = StringUtil.concat("<html><title>Merchant Shop Item Delete</title><body><br>Delete entry in trade list ", 
				String.valueOf(tradeListID), 
				"<table width=\"100%\"><tr><td>Item</td><td>", 
				ItemTable.getInstance().getTemplate(itemID).getName(), 
				" (", 
				Integer.toString(itemID), 
				")</td></tr><tr><td>Price</td><td>", 
				String.valueOf(tradeList.getPriceForItemId(itemID)), 
				"</td></tr></table><center><br><button value=\"Delete\" action=\"bypass -h admin_delShopItem ", 
				String.valueOf(tradeListID), 
				" ", 
				String.valueOf(itemID), 
				" 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Shop List\" action=\"bypass -h admin_showShopList ", 
				String.valueOf(tradeListID), 
				" 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
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
				activeChar.sendMessage("Added " + ItemTable.getInstance().getTemplate(itemID).getName() + " to Trade List " + tradeList.getListId());
			else
				activeChar.sendMessage("Could not add " + ItemTable.getInstance().getTemplate(itemID).getName() + " to Trade List " + tradeList.getListId() + "!");
			
			showShopList(activeChar, tradeListID, 1);
			return;
		}
		
		final String replyMSG = StringUtil.concat("<html><title>Merchant Shop Item Add</title><body><br>Add a new entry in merchantList.<table width=\"100%\"><tr><td>ItemID</td><td><edit var=\"itemID\" width=80></td></tr><tr><td>Price</td><td><edit var=\"price\" width=80></td></tr></table><center><br><button value=\"Add\" action=\"bypass -h admin_addShopItem ", 
				String.valueOf(tradeListID), 
				" $itemID $price\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Shop List\" action=\"bypass -h admin_showShopList ", 
				String.valueOf(tradeListID), 
				" 1\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG);
		activeChar.sendPacket(adminReply);
	}
	
	private void showShopList(L2PcInstance activeChar, int tradeListID, int page)
	{
		L2TradeList tradeList = TradeController.getInstance().getBuyList(tradeListID);
		if (page > tradeList.getItems().size() / PAGE_LIMIT + 1 || page < 1)
			return;
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(itemListHtml(tradeList, page));
		activeChar.sendPacket(adminReply);
	}
	
	private String itemListHtml(L2TradeList tradeList, int page)
	{
		final StringBuilder replyMSG = new StringBuilder();
		
		int max = tradeList.getItems().size() / PAGE_LIMIT;
		if (tradeList.getItems().size() > PAGE_LIMIT * max)
			max++;
		
		StringUtil.append(replyMSG, "<html><title>Merchant Shop List Page: ", 
				String.valueOf(page), 
				" of ", 
				Integer.toString(max), 
				"</title><body><br><center><font color=\"LEVEL\">", 
				NpcTable.getInstance().getTemplate(Integer.parseInt(tradeList.getNpcId())).getName(), 
				" (", 
				tradeList.getNpcId(), 
				") Shop ID: ", 
				Integer.toString(tradeList.getListId()), 
				"</font></center><table width=300 bgcolor=666666><tr>");
		
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
				replyMSG.append(x+1);
				replyMSG.append("\"> Page ");
				replyMSG.append(pagenr);
				replyMSG.append(" </a></td>");
			}
		}
		
		replyMSG.append("</tr></table><table width=\"100%\"><tr><td width=150>Item</td><td width=60>Price</td><td width=40>Delete</td></tr>");
		
		int start = ((page - 1) * PAGE_LIMIT);
		int end = Math.min(((page - 1) * PAGE_LIMIT) + PAGE_LIMIT, tradeList.getItems().size());
		//System.out.println("page: " + page + "; tradeList.getItems().size(): " + tradeList.getItems().size() + "; start: " + start + "; end: " + end + "; max: " + max);
		for (L2TradeItem item : tradeList.getItems(start, end))
		{
			StringUtil.append(replyMSG, "<tr><td><a action=\"bypass -h admin_editShopItem ", 
					String.valueOf(tradeList.getListId()), 
					" ", 
					String.valueOf(item.getItemId()), 
					"\">", 
					ItemTable.getInstance().getTemplate(item.getItemId()).getName(), 
					"</a></td><td>", 
					String.valueOf(item.getPrice()), 
					"</td><td><a action=\"bypass -h admin_delShopItem ", 
					String.valueOf(tradeList.getListId()), 
					" ", 
					String.valueOf(item.getItemId()), 
					"\">Delete</a></td></tr>");
		}
		StringUtil.append(replyMSG, "<tr><td><br><br></td><td> </td><td> </td></tr><tr>");
		
		StringUtil.append(replyMSG, "</tr></table><center><br><button value=\"Add Shop Item\" action=\"bypass -h admin_addShopItem ", 
				String.valueOf(tradeList.getListId()), 
				"\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Close\" action=\"bypass -h admin_close_window\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
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
			StringUtil.append(replyMSG, 
					"<br>NPC: ", 
					activeChar.getTarget().getName(), 
					" (", 
					Integer.toString(merchantID), 
					") <br>Price Config: ", 
					mpc.getName(), 
					", ", 
					Integer.toString(mpc.getBaseTax()), 
					"% / ", 
					Integer.toString(mpc.getTotalTax()), 
					"%");
		}
		
		StringUtil.append(replyMSG, "<table width=\"100%\">");
		
		for (L2TradeList tradeList : tradeLists)
		{
			if (tradeList != null)
			{
				StringUtil.append(replyMSG, "<tr><td><a action=\"bypass -h admin_showShopList ", 
						String.valueOf(tradeList.getListId()), 
						" 1\">Merchant List ID ", 
						String.valueOf(tradeList.getListId()), 
						"</a></td></tr>");
			}
		}
		
		StringUtil.append(replyMSG, "</table><center><br><button value=\"Close\" action=\"bypass -h admin_close_window\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private boolean storeTradeList(int itemID, long price, int tradeListID, int order)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
			String table = "merchant_buylists";
			if (Config.CUSTOM_MERCHANT_TABLES)
				table = "custom_merchant_buylists";
			
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
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
		return true;
	}
	
	private void updateTradeList(int itemID, long price, int tradeListID, int order)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
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
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	private void deleteTradeList(int tradeListID, int order)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
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
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	private int findOrderTradeList(int itemID, long price, int tradeListID)
	{
		Connection con = null;
		int order = -1;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement("SELECT `order` FROM `merchant_buylists` WHERE `shop_id` = ? AND `item_id` = ? AND `price` = ?");
			stmt.setInt(1, tradeListID);
			stmt.setInt(2, itemID);
			stmt.setLong(3, price);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.first())
				order = rs.getInt("order");
			
			stmt.close();
			rs.close();
		}
		catch (Exception e)
		{
			_log.warning("Could not get order for (" + itemID + ", " + price + ", " + tradeListID + "): " + e);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
		return order;
	}
	
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void showNpcProperty(L2PcInstance activeChar, L2NpcTemplate npc)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		String content = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/admin/editnpc.htm");
		
		if (content != null)
		{
			adminReply.setHtml(content);
			adminReply.replace("%npcId%", String.valueOf(npc.npcId));
			adminReply.replace("%templateId%", String.valueOf(npc.idTemplate));
			adminReply.replace("%name%", npc.name);
			adminReply.replace("%serverSideName%", npc.serverSideName == true ? "1" : "0");
			adminReply.replace("%title%", npc.title);
			adminReply.replace("%serverSideTitle%", npc.serverSideTitle == true ? "1" : "0");
			adminReply.replace("%collisionRadius%", String.valueOf(npc.fCollisionRadius));
			adminReply.replace("%collisionHeight%", String.valueOf(npc.fCollisionHeight));
			adminReply.replace("%level%", String.valueOf(npc.level));
			adminReply.replace("%sex%", String.valueOf(npc.sex));
			adminReply.replace("%type%", String.valueOf(npc.type));
			adminReply.replace("%attackRange%", String.valueOf(npc.baseAtkRange));
			adminReply.replace("%hp%", String.valueOf(npc.baseHpMax));
			adminReply.replace("%mp%", String.valueOf(npc.baseMpMax));
			adminReply.replace("%hpRegen%", String.valueOf(npc.baseHpReg));
			adminReply.replace("%mpRegen%", String.valueOf(npc.baseMpReg));
			adminReply.replace("%str%", String.valueOf(npc.baseSTR));
			adminReply.replace("%con%", String.valueOf(npc.baseCON));
			adminReply.replace("%dex%", String.valueOf(npc.baseDEX));
			adminReply.replace("%int%", String.valueOf(npc.baseINT));
			adminReply.replace("%wit%", String.valueOf(npc.baseWIT));
			adminReply.replace("%men%", String.valueOf(npc.baseMEN));
			adminReply.replace("%exp%", String.valueOf(npc.rewardExp));
			adminReply.replace("%sp%", String.valueOf(npc.rewardSp));
			adminReply.replace("%pAtk%", String.valueOf(npc.basePAtk));
			adminReply.replace("%pDef%", String.valueOf(npc.basePDef));
			adminReply.replace("%mAtk%", String.valueOf(npc.baseMAtk));
			adminReply.replace("%mDef%", String.valueOf(npc.baseMDef));
			adminReply.replace("%pAtkSpd%", String.valueOf(npc.basePAtkSpd));
			adminReply.replace("%aggro%", String.valueOf(npc.aggroRange));
			adminReply.replace("%mAtkSpd%", String.valueOf(npc.baseMAtkSpd));
			adminReply.replace("%rHand%", String.valueOf(npc.rhand));
			adminReply.replace("%lHand%", String.valueOf(npc.lhand));
			adminReply.replace("%armor%", String.valueOf(npc.armor));
			adminReply.replace("%enchant%", String.valueOf(npc.enchantEffect)); 
			adminReply.replace("%walkSpd%", String.valueOf(npc.baseWalkSpd));
			adminReply.replace("%runSpd%", String.valueOf(npc.baseRunSpd));
			adminReply.replace("%factionId%", npc.getAIDataStatic().getClan() == null ? "" : npc.getAIDataStatic().getClan());
			adminReply.replace("%factionRange%", String.valueOf(npc.getAIDataStatic().getClanRange()));
			adminReply.replace("%isUndead%", npc.isUndead ? "1" : "0");
			adminReply.replace("%absorbLevel%", String.valueOf(npc.absorbLevel));
		}
		else
			adminReply.setHtml("<html><head><body>File not found: data/html/admin/editnpc.htm</body></html>");
		activeChar.sendPacket(adminReply);
	}
	
	private void saveNpcProperty(L2PcInstance activeChar, String command)
	{
		String[] commandSplit = command.split(" ");
		
		if (commandSplit.length < 4)
			return;
		
		StatsSet newNpcData = new StatsSet();
		
		try
		{
			newNpcData.set("npcId", commandSplit[1]);
			
			String statToSet = commandSplit[2];
			String value = commandSplit[3];
			
			if (commandSplit.length > 4)
			{
				for (int i = 0; i < commandSplit.length - 3; i++)
					value += " " + commandSplit[i + 4];
			}
			
			if (statToSet.equals("templateId"))
				newNpcData.set("idTemplate", Integer.parseInt(value));
			else if (statToSet.equals("name"))
				newNpcData.set("name", value);
			else if (statToSet.equals("serverSideName"))
				newNpcData.set("serverSideName", Integer.parseInt(value));
			else if (statToSet.equals("title"))
				newNpcData.set("title", value);
			else if (statToSet.equals("serverSideTitle"))
				newNpcData.set("serverSideTitle", Integer.parseInt(value) == 1 ? 1 : 0);
			else if (statToSet.equals("collisionRadius"))
				newNpcData.set("collision_radius", Integer.parseInt(value));
			else if (statToSet.equals("collisionHeight"))
				newNpcData.set("collision_height", Integer.parseInt(value));
			else if (statToSet.equals("level"))
				newNpcData.set("level", Integer.parseInt(value));
			else if (statToSet.equals("sex"))
			{
				int intValue = Integer.parseInt(value);
				newNpcData.set("sex", intValue == 0 ? "male" : intValue == 1 ? "female" : "etc");
			}
			else if (statToSet.equals("type"))
			{
				Class.forName("com.l2jserver.gameserver.model.actor.instance." + value + "Instance");
				newNpcData.set("type", value);
			}
			else if (statToSet.equals("attackRange"))
				newNpcData.set("attackrange", Integer.parseInt(value));
			else if (statToSet.equals("hp"))
				newNpcData.set("hp", Integer.parseInt(value));
			else if (statToSet.equals("mp"))
				newNpcData.set("mp", Integer.parseInt(value));
			else if (statToSet.equals("hpRegen"))
				newNpcData.set("hpreg", Integer.parseInt(value));
			else if (statToSet.equals("mpRegen"))
				newNpcData.set("mpreg", Integer.parseInt(value));
			else if (statToSet.equals("str"))
				newNpcData.set("str", Integer.parseInt(value));
			else if (statToSet.equals("con"))
				newNpcData.set("con", Integer.parseInt(value));
			else if (statToSet.equals("dex"))
				newNpcData.set("dex", Integer.parseInt(value));
			else if (statToSet.equals("int"))
				newNpcData.set("int", Integer.parseInt(value));
			else if (statToSet.equals("wit"))
				newNpcData.set("wit", Integer.parseInt(value));
			else if (statToSet.equals("men"))
				newNpcData.set("men", Integer.parseInt(value));
			else if (statToSet.equals("exp"))
				newNpcData.set("exp", Integer.parseInt(value));
			else if (statToSet.equals("sp"))
				newNpcData.set("sp", Integer.parseInt(value));
			else if (statToSet.equals("pAtk"))
				newNpcData.set("patk", Integer.parseInt(value));
			else if (statToSet.equals("pDef"))
				newNpcData.set("pdef", Integer.parseInt(value));
			else if (statToSet.equals("mAtk"))
				newNpcData.set("matk", Integer.parseInt(value));
			else if (statToSet.equals("mDef"))
				newNpcData.set("mdef", Integer.parseInt(value));
			else if (statToSet.equals("pAtkSpd"))
				newNpcData.set("atkspd", Integer.parseInt(value));
			else if (statToSet.equals("aggro"))
				newNpcData.set("aggro", Integer.parseInt(value));
			else if (statToSet.equals("mAtkSpd"))
				newNpcData.set("matkspd", Integer.parseInt(value));
			else if (statToSet.equals("rHand"))
				newNpcData.set("rhand", Integer.parseInt(value));
			else if (statToSet.equals("lHand"))
				newNpcData.set("lhand", Integer.parseInt(value));
			else if (statToSet.equals("armor"))
				newNpcData.set("armor", Integer.parseInt(value));
			else if (statToSet.equals("enchant")) 
				newNpcData.set("enchant", Integer.parseInt(value)); 
			else if (statToSet.equals("runSpd"))
				newNpcData.set("runspd", Integer.parseInt(value));
			else if (statToSet.equals("isUndead"))
				newNpcData.set("isUndead", Integer.parseInt(value) == 1 ? 1 : 0);
			else if (statToSet.equals("absorbLevel"))
			{
				int intVal = Integer.parseInt(value);
				newNpcData.set("absorb_level", intVal < 0 ? 0 : intVal > 16 ? 0 : intVal);
			}
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not save npc property!");
			_log.warning("Error saving new npc value (" + command + "): " + e);
		}
		
		NpcTable.getInstance().saveNpc(newNpcData);
		
		int npcId = newNpcData.getInteger("npcId");
		
		NpcTable.getInstance().reloadNpc(npcId);
		showNpcProperty(activeChar, NpcTable.getInstance().getTemplate(npcId));
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
		replyMSG.append(npcData.name);
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
				replyMSG.append("<td width=120></td>");
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
			replyMSG.append(npcData.name);
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
		final String replyMSG = StringUtil.concat("<html><title>Add drop data</title><body><table width=\"100%\"><tr><td>Npc</td><td>", 
				npcData.name, 
				" (", 
				Integer.toString(npcData.npcId), 
				")", 
				"</td></tr><tr><td>Item Id</td><td><edit var=\"itemId\" width=80></td></tr><tr><td>Min count</td><td><edit var=\"min\" width=80></td></tr><tr><td>Max count</td><td><edit var=\"max\" width=80></td></tr><tr><td>Category (sweep=-1)</td><td><edit var=\"category\" width=80></td></tr><tr><td>Chance (0-1000000)</td><td><edit var=\"chance\" width=80></td></tr></table><center><br><button value=\"Add\" action=\"bypass -h admin_add_drop ", 
				Integer.toString(npcData.npcId), 
				" $itemId $category $min $max $chance\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Droplist\" action=\"bypass -h admin_show_droplist ", 
				Integer.toString(npcData.npcId), 
				"\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG);
		activeChar.sendPacket(adminReply);
	}
	
	private void updateDropData(L2PcInstance activeChar, int npcId, int itemId, int min, int max, int category, int chance)
	{
		Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
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
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	private void addDropData(L2PcInstance activeChar, int npcId, int itemId, int min, int max, int category, int chance)
	{
		Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
			String table = "droplist";
			if (Config.CUSTOM_DROPLIST_TABLE)
				table = "custom_droplist";
			
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
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	private void deleteDropData(L2PcInstance activeChar, int npcId, int itemId, int category, boolean confirmed)
	{
		if (!confirmed)
		{
			final String replyMSG = StringUtil.concat("<html><title>Drop Data Delete</title><body><br>Delete drop data.", 
					"<table width=\"100%\"><tr><td>NPC</td><td>", 
					NpcTable.getInstance().getTemplate(npcId).name,
					" (", 
					Integer.toString(npcId), 
					")</td></tr><tr><td>Item ID</td><td>", 
					Integer.toString(itemId), 
					"</td></tr><tr><td>Category</td><td>", 
					Integer.toString(category), 
					"</td></tr></table><center><br><button value=\"Delete\" action=\"bypass -h admin_del_drop ", 
					Integer.toString(npcId), 
					" ", 
					Integer.toString(itemId), 
					" ", 
					Integer.toString(category), 
					" 1\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back to Droplist\" action=\"bypass -h admin_show_droplist ", 
					Integer.toString(npcId), 
					"\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
			
			NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
			adminReply.setHtml(replyMSG);
			activeChar.sendPacket(adminReply);
			return;
		}
		
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
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
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	private void reloadNpcDropList(int npcId)
	{
		L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
		if (npcData == null)
			return;
		
		// reset the drop lists
		npcData.clearAllDropData();
		
		// get the drops
		Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
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
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{
			}
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
		
		Map<Integer, L2Skill> skills = new FastMap<Integer, L2Skill>();
		if (npcData.getSkills() != null)
			skills = npcData.getSkills();
		
		int _skillsize = skills.size();
		
		int MaxSkillsPerPage = PAGE_LIMIT;
		int MaxPages = _skillsize / MaxSkillsPerPage;
		if (_skillsize > MaxSkillsPerPage * MaxPages)
			MaxPages++;
		
		if (page > MaxPages)
			page = MaxPages;
		
		int SkillsStart = MaxSkillsPerPage * page;
		int SkillsEnd = _skillsize;
		if (SkillsEnd - SkillsStart > MaxSkillsPerPage)
			SkillsEnd = SkillsStart + MaxSkillsPerPage;
		
		StringBuffer replyMSG = new StringBuffer("<html><title>Show NPC Skill List</title><body><center><font color=\"LEVEL\">");
		replyMSG.append(npcData.getName());
		replyMSG.append(" (");
		replyMSG.append(npcData.npcId);
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
				replyMSG.append(npcData.npcId);
				replyMSG.append(" ");
				replyMSG.append(x);
				replyMSG.append("\"> Page ");
				replyMSG.append(pagenr);
				replyMSG.append(" </a></td>");
			}
		}
		replyMSG.append("</tr></table><table width=\"100%\" border=0><tr><td>Skill name [skill id-skill lvl]</td><td>Delete</td></tr>");
		
		Set<Integer> skillset = skills.keySet();
		Iterator<Integer> skillite = skillset.iterator();
		int skillobj = 0;
		
		for (int i = 0; i < SkillsStart; i++)
		{
			if (skillite.hasNext())
				skillite.next();
		}
		
		int cnt = SkillsStart;
		while (skillite.hasNext())
		{
			cnt++;
			if (cnt > SkillsEnd)
				break;
			
			skillobj = skillite.next();
			replyMSG.append("<tr><td width=240><a action=\"bypass -h admin_edit_skill_npc ");
			replyMSG.append(npcData.npcId);
			replyMSG.append(" ");
			replyMSG.append(skills.get(skillobj).getId());
			replyMSG.append("\">");
			replyMSG.append(skills.get(skillobj).getName());
			replyMSG.append(" [");
			replyMSG.append(skills.get(skillobj).getId());
			replyMSG.append("-");
			replyMSG.append(skills.get(skillobj).getLevel());
			replyMSG.append("]</a></td><td width=60><a action=\"bypass -h admin_del_skill_npc ");
			replyMSG.append(npcData.npcId);
			replyMSG.append(" ");
			replyMSG.append(skillobj);
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
				return;
			
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
		Connection con = null;
		try
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
			
			con = L2DatabaseFactory.getInstance().getConnection();
			int updated = 0;
			if(Config.CUSTOM_NPC_SKILLS_TABLE)
			{
				PreparedStatement statement2 = con.prepareStatement("UPDATE `custom_npcskills` SET `level`=? WHERE `npcid`=? AND `skillid`=?");
				statement2.setInt(1, level);
				statement2.setInt(2, npcId);
				statement2.setInt(3, skillId);
				
				updated = statement2.executeUpdate();
				statement2.close();
			}
			if(updated == 0)
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
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private void showNpcSkillAdd(L2PcInstance activeChar, int npcId)
	{
		L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
		
		StringBuffer replyMSG = new StringBuffer("<html><title>NPC Skill Add</title><body><table width=\"100%\"><tr><td>NPC: </td><td>");
		replyMSG.append(npcData.getName());
		replyMSG.append(" (");
		replyMSG.append(npcData.npcId);
		replyMSG.append(")</td></tr><tr><td>SkillId: </td><td><edit var=\"skillId\" width=80></td></tr><tr><td>Level: </td><td><edit var=\"level\" width=80></td></tr></table><br><center><button value=\"Add Skill\" action=\"bypass -h admin_add_skill_npc ");
		replyMSG.append(npcData.npcId);
		replyMSG.append(" $skillId $level\"  width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><br1><button value=\"Back to SkillList\" action=\"bypass -h admin_show_skilllist_npc ");
		replyMSG.append(npcData.npcId);
		replyMSG.append("\" width=100 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void addNpcSkillData(L2PcInstance activeChar, int npcId, int skillId, int level)
	{
		Connection con = null;
		try
		{
			// skill check
			L2Skill skillData = SkillTable.getInstance().getInfo(skillId, level);
			if (skillData == null)
			{
				activeChar.sendMessage("Could not add npc skill: not existing skill id with that level!");
				showNpcSkillAdd(activeChar, npcId);
				return;
			}
			
			con = L2DatabaseFactory.getInstance().getConnection();
			
			if(Config.CUSTOM_NPC_SKILLS_TABLE)
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
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private void deleteNpcSkillData(L2PcInstance activeChar, int npcId, int skillId)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
			if (npcId > 0)
			{
				int updated = 0;
				if(Config.CUSTOM_NPC_SKILLS_TABLE)
				{
					PreparedStatement statement = con.prepareStatement("DELETE FROM `custom_npcskills` WHERE `npcid`=? AND `skillid`=?");
					statement.setInt(1, npcId);
					statement.setInt(2, skillId);
					updated = statement.executeUpdate();
					statement.close();
				}
				if(updated == 0)
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
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	private void reloadNpcSkillList(int npcId)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			L2NpcTemplate npcData = NpcTable.getInstance().getTemplate(npcId);
			
			L2Skill skillData = null;
			if (npcData.getSkills() != null)
				npcData.getSkills().clear();
			
			
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
					npcData.addSkill(skillData);
			}
			skillDataList.close();
			statement.close();
			
			if(Config.CUSTOM_NPC_SKILLS_TABLE)
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
						npcData.addSkill(skillData);
				}
				skillDataList2.close();
				statement2.close();
			}
		}
		catch (Exception e)
		{
			_log.warning("Error while reloading npc skill list (" + npcId + "): " + e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
}
