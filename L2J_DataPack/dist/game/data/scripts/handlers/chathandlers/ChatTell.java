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
package handlers.chathandlers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.IChatHandler;
import com.l2jserver.gameserver.model.BlockList;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.PcCondOverride;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;
import com.l2jserver.gameserver.util.Util;

/**
 * Tell chat handler.
 * @author durgus
 */
public class ChatTell implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		2
	};
	
	/**
	 * Handle chat type 'tell'
	 */
	@Override
	public void handleChat(int type, L2PcInstance activeChar, String target, String text)
	{
		if (activeChar.isChatBanned() && Util.contains(Config.BAN_CHAT_CHANNELS, type))
		{
			activeChar.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED_IF_YOU_TRY_TO_CHAT_BEFORE_THE_PROHIBITION_IS_REMOVED_THE_PROHIBITION_TIME_WILL_INCREASE_EVEN_FURTHER);
			return;
		}
		
		if (Config.JAIL_DISABLE_CHAT && activeChar.isJailed() && !activeChar.canOverrideCond(PcCondOverride.CHAT_CONDITIONS))
		{
			activeChar.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED);
			return;
		}
		
		// Return if no target is set
		if (target == null)
		{
			return;
		}
		
		L2PcInstance receiver = null;
		
		receiver = L2World.getInstance().getPlayer(target);
		
		if ((receiver != null) && !receiver.isSilenceMode(activeChar.getObjectId()))
		{
			if (Config.JAIL_DISABLE_CHAT && receiver.isJailed() && !activeChar.canOverrideCond(PcCondOverride.CHAT_CONDITIONS))
			{
				activeChar.sendMessage("Player is in jail.");
				return;
			}
			if (receiver.isChatBanned())
			{
				activeChar.sendPacket(SystemMessageId.THAT_PERSON_IS_IN_MESSAGE_REFUSAL_MODE);
				return;
			}
			if ((receiver.getClient() == null) || receiver.getClient().isDetached())
			{
				activeChar.sendMessage("Player is in offline mode.");
				return;
			}
			if (!BlockList.isBlocked(receiver, activeChar))
			{
				// Allow reciever to send PMs to this char, which is in silence mode.
				if (Config.SILENCE_MODE_EXCLUDE && activeChar.isSilenceMode())
				{
					activeChar.addSilenceModeExcluded(receiver.getObjectId());
				}
				
				receiver.sendPacket(new CreatureSay(activeChar, receiver, activeChar.getName(), type, text));
				activeChar.sendPacket(new CreatureSay(activeChar, receiver, "->" + receiver.getName(), type, text));
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.THAT_PERSON_IS_IN_MESSAGE_REFUSAL_MODE);
			}
		}
		else
		{
			activeChar.sendPacket(SystemMessageId.THAT_PLAYER_IS_NOT_ONLINE);
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
