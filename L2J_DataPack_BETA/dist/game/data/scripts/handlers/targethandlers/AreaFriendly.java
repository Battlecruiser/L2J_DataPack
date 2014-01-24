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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.l2jserver.Config;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeFlagInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * @author Adry_85
 */
public class AreaFriendly implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new ArrayList<>();
		if (!checkTarget(activeChar, target) && (skill.getCastRange() >= 0))
		{
			activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
			return EMPTY_TARGET_LIST;
		}
		
		if (onlyFirst)
		{
			return new L2Character[]
			{
				target
			};
		}
		
		if (activeChar.getActingPlayer().isInOlympiadMode())
		{
			return new L2Character[]
			{
				activeChar
			};
		}
		targetList.add(target); // Add target to target list
		
		if (target != null)
		{
			int maxTargets = skill.getAffectLimit();
			final Collection<L2Character> objs = target.getKnownList().getKnownCharactersInRadius(skill.getAffectRange());
			
			// TODO: Chain Heal - The recovery amount decreases starting from the most injured person.
			Collections.sort(targetList, new CharComparator());
			
			for (L2Character obj : objs)
			{
				if (!checkTarget(activeChar, obj) || (obj == activeChar))
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
	
	private boolean checkTarget(L2Character activeChar, L2Character target)
	{
		if ((Config.GEODATA > 0) && !GeoData.getInstance().canSeeTarget(activeChar, target))
		{
			return false;
		}
		
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
			if ((target != activeChar) && activeChar.isInParty() && target.isInParty())
			{
				return (activeChar.getParty().getLeader() == target.getParty().getLeader());
			}
			
			if ((activeChar.getClanId() != 0) && (target.getClanId() != 0))
			{
				return (activeChar.getClanId() == target.getClanId());
			}
			
			if ((activeChar.getAllyId() != 0) && (target.getAllyId() != 0))
			{
				return (activeChar.getAllyId() == target.getAllyId());
			}
			
			if ((target != activeChar) && (target.getActingPlayer().getPvpFlag() > 0))
			{
				return false;
			}
		}
		return true;
	}
	
	public class CharComparator implements Comparator<L2Character>
	{
		@Override
		public int compare(L2Character char1, L2Character char2)
		{
			return Integer.compare((int) (char1.getCurrentHp() / char1.getMaxHp()), (int) (char2.getCurrentHp() / char2.getMaxHp()));
		}
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.AREA_FRIENDLY;
	}
}