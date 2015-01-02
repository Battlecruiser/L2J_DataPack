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
package handlers.itemhandlers;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author JIV
 */
public class Bypass implements IItemHandler
{
	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!(playable instanceof L2PcInstance))
		{
			return false;
		}
		L2PcInstance activeChar = (L2PcInstance) playable;
		final int itemId = item.getId();
		
		String filename = "data/html/item/" + itemId + ".htm";
		String content = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), filename);
		final NpcHtmlMessage html = new NpcHtmlMessage(0, item.getId());
		if (content == null)
		{
			html.setHtml("<html><body>My Text is missing:<br>" + filename + "</body></html>");
			activeChar.sendPacket(html);
		}
		else
		{
			html.setHtml(content);
			html.replace("%itemId%", String.valueOf(item.getObjectId()));
			activeChar.sendPacket(html);
		}
		return true;
	}
	
}
