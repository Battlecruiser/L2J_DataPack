/*
 * Copyright (C) 2004-2014 L2J DataPack
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.model.zone.ZoneId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class Area implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new ArrayList<>();
		if ((target == null) || (((target == activeChar) || target.isAlikeDead()) && (skill.getCastRange() >= 0)) || (!(target.isAttackable() || target.isPlayable())))
		{
			activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
			return EMPTY_TARGET_LIST;
		}
		
		final L2Character origin;
		final boolean srcInArena = (activeChar.isInsideZone(ZoneId.PVP) && !activeChar.isInsideZone(ZoneId.SIEGE));
		
		if (skill.getCastRange() >= 0)
		{
			if (!Skill.checkForAreaOffensiveSkills(activeChar, target, skill, srcInArena))
			{
				return EMPTY_TARGET_LIST;
			}
			
			if (onlyFirst)
			{
				return new L2Character[]
				{
					target
				};
			}
			
			origin = target;
			targetList.add(origin); // Add target to target list
		}
		else
		{
			origin = activeChar;
		}
		
		int maxTargets = skill.getAffectLimit();
		final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharacters();
		for (L2Character obj : objs)
		{
			if (!(obj.isAttackable() || obj.isPlayable()))
			{
				continue;
			}
			
			if (obj == origin)
			{
				continue;
			}
			
			if (Util.checkIfInRange(skill.getAffectRange(), origin, obj, true))
			{
				if (!Skill.checkForAreaOffensiveSkills(activeChar, obj, skill, srcInArena))
				{
					continue;
				}
				
				if ((maxTargets > 0) && (targetList.size() >= maxTargets))
				{
					break;
				}
				
				targetList.add(obj);
			}
		}
		
		if (targetList.isEmpty())
		{
			return EMPTY_TARGET_LIST;
		}
		
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.AREA;
	}
}
