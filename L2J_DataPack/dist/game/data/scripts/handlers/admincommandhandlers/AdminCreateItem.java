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

import java.util.Collection;
import java.util.StringTokenizer;

import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.templates.item.L2Item;


/**
 * This class handles following admin commands:
 * - itemcreate = show menu
 * - create_item <id> [num] = creates num items with respective id, if num is not specified, assumes 1.
 *
 * @version $Revision: 1.2.2.2.2.3 $ $Date: 2005/04/11 10:06:06 $
 */
public class AdminCreateItem implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_itemcreate",
		"admin_create_item",
		"admin_create_coin",
		"admin_give_item_target",
		"admin_give_item_to_all"
	};
	
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_itemcreate"))
		{
			AdminHelpPage.showHelpPage(activeChar, "itemcreation.htm");
		}
		else if (command.startsWith("admin_create_item"))
		{
			try
			{
				String val = command.substring(17);
				StringTokenizer st = new StringTokenizer(val);
				if (st.countTokens() == 2)
				{
					String id = st.nextToken();
					int idval = Integer.parseInt(id);
					String num = st.nextToken();
					long numval = Long.parseLong(num);
					createItem(activeChar, activeChar, idval, numval);
				}
				else if (st.countTokens() == 1)
				{
					String id = st.nextToken();
					int idval = Integer.parseInt(id);
					createItem(activeChar, activeChar, idval, 1);
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //create_item <itemId> [amount]");
			}
			catch (NumberFormatException nfe)
			{
				activeChar.sendMessage("Specify a valid number.");
			}
			AdminHelpPage.showHelpPage(activeChar, "itemcreation.htm");
		}
		else if (command.startsWith("admin_create_coin"))
		{
			try
			{
				String val = command.substring(17);
				StringTokenizer st = new StringTokenizer(val);
				if (st.countTokens() == 2)
				{
					String name = st.nextToken();
					int idval = getCoinId(name);
					if(idval > 0)
					{
						String num = st.nextToken();
						long numval = Long.parseLong(num);
						createItem(activeChar, activeChar, idval, numval);
					}
				}
				else if (st.countTokens() == 1)
				{
					String name = st.nextToken();
					int idval = getCoinId(name);
					createItem(activeChar, activeChar, idval, 1);
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //create_coin <name> [amount]");
			}
			catch (NumberFormatException nfe)
			{
				activeChar.sendMessage("Specify a valid number.");
			}
			AdminHelpPage.showHelpPage(activeChar, "itemcreation.htm");
		}
		else if (command.startsWith("admin_give_item_target"))
		{
			try
			{
				L2PcInstance target;
				if (activeChar.getTarget() instanceof L2PcInstance)
					target = (L2PcInstance) activeChar.getTarget();
				else
				{
					activeChar.sendMessage("Invalid target.");
					return false;
				}
				
				String val = command.substring(22);
				StringTokenizer st = new StringTokenizer(val);
				if (st.countTokens() == 2)
				{
					String id = st.nextToken();
					int idval = Integer.parseInt(id);
					String num = st.nextToken();
					long numval = Long.parseLong(num);
					createItem(activeChar, target, idval, numval);
				}
				else if (st.countTokens() == 1)
				{
					String id = st.nextToken();
					int idval = Integer.parseInt(id);
					createItem(activeChar, target, idval, 1);
				}
			}
			catch (StringIndexOutOfBoundsException e)
			{
				activeChar.sendMessage("Usage: //give_item_target <itemId> [amount]");
			}
			catch (NumberFormatException nfe)
			{
				activeChar.sendMessage("Specify a valid number.");
			}
			AdminHelpPage.showHelpPage(activeChar, "itemcreation.htm");
		}
		else if (command.startsWith("admin_give_item_to_all"))
		{
			String val = command.substring(22);
			StringTokenizer st = new StringTokenizer(val);
			int idval = 0;
			long numval = 0;
			if (st.countTokens() == 2)
			{
				String id = st.nextToken();
				idval = Integer.parseInt(id);
				String num = st.nextToken();
				numval = Long.parseLong(num);
			}
			else if (st.countTokens() == 1)
			{
				String id = st.nextToken();
				idval = Integer.parseInt(id);
				numval = 1;
			}
			int counter = 0;
			L2Item template = ItemTable.getInstance().getTemplate(idval);
			if (template == null)
			{
				activeChar.sendMessage("This item doesn't exist.");
				return false;
			}
			if (numval > 10 && !template.isStackable())
			{
				activeChar.sendMessage("This item does not stack - Creation aborted.");
				return false;
			}
			Collection<L2PcInstance> pls = L2World.getInstance().getAllPlayers().values();
			{
				for (L2PcInstance onlinePlayer : pls)
				{
					if (activeChar != onlinePlayer && onlinePlayer.isOnline() && (onlinePlayer.getClient() != null && !onlinePlayer.getClient().isDetached()))
					{
						onlinePlayer.getInventory().addItem("Admin", idval, numval, onlinePlayer, activeChar);
						onlinePlayer.sendMessage("Admin spawned "+numval+" "+template.getName()+" in your inventory.");
						counter++;
					}
				}
			}
			activeChar.sendMessage(counter +" players rewarded with " + template.getName());
		}
		return true;
	}
	
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void createItem(L2PcInstance activeChar, L2PcInstance target, int id, long num)
	{
		L2Item template = ItemTable.getInstance().getTemplate(id);
		if (template == null)
		{
			activeChar.sendMessage("This item doesn't exist.");
			return;
		}
		if (num > 10 && !template.isStackable())
		{
			activeChar.sendMessage("This item does not stack - Creation aborted.");
			return;
		}
		
		target.getInventory().addItem("Admin", id, num, activeChar, null);
		
		if (activeChar != target)
			target.sendMessage("Admin spawned " + num + " "+template.getName()+" in your inventory.");
		activeChar.sendMessage("You have spawned " + num + " "+template.getName()+"(" + id + ") in "+target.getName()+" inventory.");
	}
	
	private int getCoinId(String name)
	{
		int id;
		if (name.equalsIgnoreCase("adena"))
			id = 57;
		else if (name.equalsIgnoreCase("ancientadena"))
			id = 5575;
		else if (name.equalsIgnoreCase("festivaladena"))
			id = 6673;
		else if (name.equalsIgnoreCase("blueeva"))
			id = 4355;
		else if (name.equalsIgnoreCase("goldeinhasad"))
			id = 4356;
		else if (name.equalsIgnoreCase("silvershilen"))
			id = 4357;
		else if (name.equalsIgnoreCase("bloodypaagrio"))
			id = 4358;
		else if (name.equalsIgnoreCase("fantasyislecoin"))
			id = 13067;
		else id = 0;
		
		return id;
	}
}
