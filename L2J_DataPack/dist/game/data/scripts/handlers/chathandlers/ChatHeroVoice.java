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
import com.l2jserver.gameserver.enums.ChatType;
import com.l2jserver.gameserver.handler.IChatHandler;
import com.l2jserver.gameserver.model.BlockList;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.PcCondOverride;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;

/**
 * Hero chat handler.
 * @author durgus
 */
public final class ChatHeroVoice implements IChatHandler
{
	private static final ChatType[] CHAT_TYPES =
	{
		ChatType.HERO_VOICE,
	};
	
	@Override
	public void handleChat(ChatType type, L2PcInstance activeChar, String target, String text)
	{
		if (!activeChar.isHero() && !activeChar.canOverrideCond(PcCondOverride.CHAT_CONDITIONS))
		{
			activeChar.sendPacket(SystemMessageId.ONLY_HEROES_CAN_ENTER_THE_HERO_CHANNEL);
			return;
		}
		
		if (activeChar.isChatBanned() && Config.BAN_CHAT_CHANNELS.contains(type))
		{
			activeChar.sendPacket(SystemMessageId.CHATTING_IS_CURRENTLY_PROHIBITED_IF_YOU_TRY_TO_CHAT_BEFORE_THE_PROHIBITION_IS_REMOVED_THE_PROHIBITION_TIME_WILL_INCREASE_EVEN_FURTHER);
			return;
		}
		
		if (!activeChar.getFloodProtectors().getHeroVoice().tryPerformAction("hero voice"))
		{
			activeChar.sendMessage("Action failed. Heroes are only able to speak in the global channel once every 10 seconds.");
			return;
		}
		
		final CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
		for (L2PcInstance player : L2World.getInstance().getPlayers())
		{
			if ((player != null) && !BlockList.isBlocked(player, activeChar))
			{
				player.sendPacket(cs);
			}
		}
	}
	
	@Override
	public ChatType[] getChatTypeList()
	{
		return CHAT_TYPES;
	}
}