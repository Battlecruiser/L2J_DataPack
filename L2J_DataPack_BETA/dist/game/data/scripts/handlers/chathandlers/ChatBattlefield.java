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
package handlers.chathandlers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.IChatHandler;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;
import com.l2jserver.gameserver.util.Util;

/**
 * A chat handler
 * @author Gigiikun
 */
public class ChatBattlefield implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		20
	};
	
	/**
	 * Handle chat type 'battlefield'
	 */
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (TerritoryWarManager.getInstance().isTWChannelOpen() && (activeChar.getSiegeSide() > 0))
		{
			if (activeChar.isChatBanned() && Util.contains(Config.BAN_CHAT_CHANNELS, type))
			{
				activeChar.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED);
				return;
			}
			
			CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
			for (L2PcInstance player : L2World.getInstance().getAllPlayersArray())
			{
				if (player.getSiegeSide() == activeChar.getSiegeSide())
				{
					player.sendPacket(cs);
				}
			}
		}
	}
	
	/**
	 * Returns the chat types registered to this handler.
	 */
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}
