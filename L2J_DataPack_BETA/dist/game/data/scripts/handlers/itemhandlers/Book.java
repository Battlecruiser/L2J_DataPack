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
package handlers.itemhandlers;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

public class Book implements IItemHandler
{
	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!playable.isPlayer())
		{
			playable.sendPacket(SystemMessageId.ITEM_NOT_FOR_PETS);
			return false;
		}
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		final int itemId = item.getItemId();
		
		String filename = "data/html/help/" + itemId + ".htm";
		String content = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), filename);
		
		if (content == null)
		{
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			html.setHtml("<html><body>My Text is missing:<br>" + filename + "</body></html>");
			activeChar.sendPacket(html);
		}
		else
		{
			NpcHtmlMessage itemReply = new NpcHtmlMessage(5, itemId);
			itemReply.setHtml(content);
			itemReply.disableValidation();
			activeChar.sendPacket(itemReply);
		}
		
		activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		return true;
	}
}
