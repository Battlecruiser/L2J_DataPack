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
package handlers.actionhandlers;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.InstanceType;
import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.instancemanager.MercTicketManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class L2ItemInstanceAction implements IActionHandler
{
	@Override
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		// this causes the validate position handler to do the pickup if the location is reached.
		// mercenary tickets can only be picked up by the castle owner.
		final int castleId = MercTicketManager.getInstance().getTicketCastleId(target.getId());
		
		if ((castleId > 0) && (!activeChar.isCastleLord(castleId) || activeChar.isInParty()))
		{
			if (activeChar.isInParty())
			{
				activeChar.sendMessage("You cannot pickup mercenaries while in a party.");
			}
			else
			{
				activeChar.sendMessage("Only the castle lord can pickup mercenaries.");
			}
			
			activeChar.setTarget(target);
			activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		}
		else if (!activeChar.isFlying())
		{
			activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_PICK_UP, target);
		}
		
		return true;
	}
	
	@Override
	public InstanceType getInstanceType()
	{
		return InstanceType.L2ItemInstance;
	}
}