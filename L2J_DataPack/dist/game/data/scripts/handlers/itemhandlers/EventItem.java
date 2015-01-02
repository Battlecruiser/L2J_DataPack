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

import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.instancemanager.HandysBlockCheckerManager;
import com.l2jserver.gameserver.model.ArenaParticipantsHolder;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2BlockInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class EventItem implements IItemHandler
{
	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!playable.isPlayer())
		{
			playable.sendPacket(SystemMessageId.YOUR_PET_CANNOT_CARRY_THIS_ITEM);
			return false;
		}
		
		boolean used = false;
		
		final L2PcInstance activeChar = playable.getActingPlayer();
		
		final int itemId = item.getId();
		switch (itemId)
		{
			case 13787: // Handy's Block Checker Bond
				used = useBlockCheckerItem(activeChar, item);
				break;
			case 13788: // Handy's Block Checker Land Mine
				used = useBlockCheckerItem(activeChar, item);
				break;
			default:
				_log.warning("EventItemHandler: Item with id: " + itemId + " is not handled");
		}
		return used;
	}
	
	private final boolean useBlockCheckerItem(final L2PcInstance castor, L2ItemInstance item)
	{
		final int blockCheckerArena = castor.getBlockCheckerArena();
		if (blockCheckerArena == -1)
		{
			SystemMessage msg = SystemMessage.getSystemMessage(SystemMessageId.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS);
			msg.addItemName(item);
			castor.sendPacket(msg);
			return false;
		}
		
		final Skill sk = item.getEtcItem().getSkills()[0].getSkill();
		if (sk == null)
		{
			return false;
		}
		
		if (!castor.destroyItem("Consume", item, 1, castor, true))
		{
			return false;
		}
		
		final L2BlockInstance block = (L2BlockInstance) castor.getTarget();
		
		final ArenaParticipantsHolder holder = HandysBlockCheckerManager.getInstance().getHolder(blockCheckerArena);
		if (holder != null)
		{
			final int team = holder.getPlayerTeam(castor);
			for (final L2PcInstance pc : block.getKnownList().getKnownPlayersInRadius(sk.getEffectRange()))
			{
				final int enemyTeam = holder.getPlayerTeam(pc);
				if ((enemyTeam != -1) && (enemyTeam != team))
				{
					sk.applyEffects(castor, pc);
				}
			}
			return true;
		}
		_log.warning("Char: " + castor.getName() + "[" + castor.getObjectId() + "] has unknown block checker arena");
		return false;
	}
}
