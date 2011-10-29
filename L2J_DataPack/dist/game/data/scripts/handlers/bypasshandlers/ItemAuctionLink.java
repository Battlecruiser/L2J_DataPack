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
package handlers.bypasshandlers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.instancemanager.ItemAuctionManager;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemauction.ItemAuction;
import com.l2jserver.gameserver.model.itemauction.ItemAuctionInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExItemAuctionInfoPacket;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class ItemAuctionLink implements IBypassHandler
{
	private static final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
	
	private static final String[] COMMANDS =
	{
		"ItemAuction"
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
		{
			return false;
		}
		
		if (!Config.ALT_ITEM_AUCTION_ENABLED)
		{
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NO_AUCTION_PERIOD));
			return true;
		}
		
		final ItemAuctionInstance au = ItemAuctionManager.getInstance().getManagerInstance(((L2Npc) target).getNpcId());
		if (au == null)
		{
			return false;
		}
		
		try
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken(); // bypass "ItemAuction"
			if (!st.hasMoreTokens())
			{
				return false;
			}
			
			String cmd = st.nextToken();
			if ("show".equalsIgnoreCase(cmd))
			{
				if (!activeChar.getFloodProtectors().getItemAuction().tryPerformAction("RequestInfoItemAuction"))
				{
					return false;
				}
				
				if (activeChar.isItemAuctionPolling())
				{
					return false;
				}
				
				final ItemAuction currentAuction = au.getCurrentAuction();
				final ItemAuction nextAuction = au.getNextAuction();
				
				if (currentAuction == null)
				{
					activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NO_AUCTION_PERIOD));
					
					if (nextAuction != null)
					{
						activeChar.sendMessage("The next auction will begin on the " + fmt.format(new Date(nextAuction.getStartingTime())) + ".");
					}
					return true;
				}
				
				activeChar.sendPacket(new ExItemAuctionInfoPacket(false, currentAuction, nextAuction));
			}
			else if ("cancel".equalsIgnoreCase(cmd))
			{
				final ItemAuction[] auctions = au.getAuctionsByBidder(activeChar.getObjectId());
				boolean returned = false;
				for (final ItemAuction auction : auctions)
				{
					if (auction.cancelBid(activeChar))
					{
						returned = true;
					}
				}
				if (!returned)
				{
					activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NO_OFFERINGS_OWN_OR_MADE_BID_FOR));
				}
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Exception in " + getClass().getSimpleName(), e);
		}
		
		return true;
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}
