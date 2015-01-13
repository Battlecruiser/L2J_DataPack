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
package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeFlagInstance;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * @author St3eT
 */
public final class OneFriendly implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		// Check for null target or any other invalid target
		if ((target == null) || target.isDead())
		{
			activeChar.sendPacket(SystemMessageId.THAT_IS_AN_INCORRECT_TARGET);
			return EMPTY_TARGET_LIST;
		}
		
		if (!checkTarget(activeChar, target))
		{
			activeChar.sendPacket(SystemMessageId.THAT_IS_AN_INCORRECT_TARGET);
			return EMPTY_TARGET_LIST;
		}
		
		// If a target is found, return it in a table else send a system message TARGET_IS_INCORRECT
		return new L2Character[]
		{
			target
		};
	}
	
	private boolean checkTarget(L2Character activeChar, L2Character target)
	{
		if ((target == null) || target.isAlikeDead() || target.isDoor() || (target instanceof L2SiegeFlagInstance) || target.isMonster())
		{
			return false;
		}
		
		if ((target.getActingPlayer() != null) && (target.getActingPlayer() != activeChar) && (target.getActingPlayer().inObserverMode() || target.getActingPlayer().isInOlympiadMode()))
		{
			return false;
		}
		
		if (target.isPlayable())
		{
			boolean friendly = false;
			
			if ((activeChar.getAllyId() > 0) && (activeChar.getAllyId() == target.getActingPlayer().getAllyId()))
			{
				friendly = true;
			}
			else if ((activeChar.getClanId() > 0) && (activeChar.getClanId() == target.getActingPlayer().getClanId()))
			{
				friendly = true;
			}
			else if (activeChar.isInParty() && activeChar.getParty().containsPlayer(target.getActingPlayer()))
			{
				friendly = true;
			}
			else if ((target != activeChar) && (target.getActingPlayer().getPvpFlag() == 0))
			{
				friendly = true;
			}
			return friendly;
		}
		return true;
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.ONE_FRIENDLY;
	}
}