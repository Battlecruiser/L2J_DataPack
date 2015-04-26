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

import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.InstanceType;
import com.l2jserver.gameserver.enums.PrivateStoreType;
import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;

public class L2PcInstanceAction implements IActionHandler
{
	/**
	 * Manage actions when a player click on this L2PcInstance.<BR>
	 * <BR>
	 * <B><U> Actions on first click on the L2PcInstance (Select it)</U> :</B><BR>
	 * <BR>
	 * <li>Set the target of the player</li> <li>Send a Server->Client packet MyTargetSelected to the player (display the select window)</li><BR>
	 * <BR>
	 * <B><U> Actions on second click on the L2PcInstance (Follow it/Attack it/Intercat with it)</U> :</B><BR>
	 * <BR>
	 * <li>Send a Server->Client packet MyTargetSelected to the player (display the select window)</li> <li>If target L2PcInstance has a Private Store, notify the player AI with AI_INTENTION_INTERACT</li> <li>If target L2PcInstance is autoAttackable, notify the player AI with AI_INTENTION_ATTACK</li>
	 * <BR>
	 * <BR>
	 * <li>If target L2PcInstance is NOT autoAttackable, notify the player AI with AI_INTENTION_FOLLOW</li><BR>
	 * <BR>
	 * <B><U> Example of use </U> :</B><BR>
	 * <BR>
	 * <li>Client packet : Action, AttackRequest</li><BR>
	 * <BR>
	 * @param activeChar The player that start an action on target L2PcInstance
	 */
	@Override
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		// See description in TvTEvent.java
		if (!TvTEvent.onAction(activeChar, target.getObjectId()))
		{
			return false;
		}
		
		// Check if the L2PcInstance is confused
		if (activeChar.isOutOfControl())
		{
			return false;
		}
		
		// Aggression target lock effect
		if (activeChar.isLockedTarget() && (activeChar.getLockedTarget() != target))
		{
			activeChar.sendPacket(SystemMessageId.FAILED_CHANGE_TARGET);
			return false;
		}
		
		// Check if the activeChar already target this L2PcInstance
		if (activeChar.getTarget() != target)
		{
			// Set the target of the activeChar
			activeChar.setTarget(target);
		}
		else if (interact)
		{
			// Check if this L2PcInstance has a Private Store
			if (((L2PcInstance) target).getPrivateStoreType() != PrivateStoreType.NONE)
			{
				activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, target);
			}
			else
			{
				// Check if this L2PcInstance is autoAttackable
				if (target.isAutoAttackable(activeChar))
				{
					// activeChar with lvl < 21 can't attack a cursed weapon holder
					// And a cursed weapon holder can't attack activeChars with lvl < 21
					if ((((L2PcInstance) target).isCursedWeaponEquipped() && (activeChar.getLevel() < 21)) || (activeChar.isCursedWeaponEquipped() && (((L2Character) target).getLevel() < 21)))
					{
						activeChar.sendPacket(ActionFailed.STATIC_PACKET);
					}
					else
					{
						if (GeoData.getInstance().canSeeTarget(activeChar, target))
						{
							activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
							activeChar.onActionRequest();
						}
						else
						{
							final Location destination = GeoData.getInstance().moveCheck(activeChar.getX(), activeChar.getY(), activeChar.getZ(), target.getX(), target.getY(), target.getZ(), activeChar.getInstanceId());
							activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, destination);
							activeChar.onActionRequest();
						}
					}
				}
				else
				{
					// This Action Failed packet avoids activeChar getting stuck when clicking three or more times
					activeChar.sendPacket(ActionFailed.STATIC_PACKET);
					if (GeoData.getInstance().canSeeTarget(activeChar, target))
					{
						activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, target);
					}
					else
					{
						final Location destination = GeoData.getInstance().moveCheck(activeChar.getX(), activeChar.getY(), activeChar.getZ(), target.getX(), target.getY(), target.getZ(), activeChar.getInstanceId());
						activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, destination);
						activeChar.onActionRequest();
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public InstanceType getInstanceType()
	{
		return InstanceType.L2PcInstance;
	}
}
