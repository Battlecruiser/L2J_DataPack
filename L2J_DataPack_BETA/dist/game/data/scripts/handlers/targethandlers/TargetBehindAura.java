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
package handlers.targethandlers;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.model.zone.ZoneId;

/**
 * @author UnAfraid
 */
public class TargetBehindAura implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<>();
		final boolean srcInArena = (activeChar.isInsideZone(ZoneId.PVP) && !activeChar.isInsideZone(ZoneId.SIEGE));
		
		final L2PcInstance sourcePlayer = activeChar.getActingPlayer();
		
		final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharactersInRadius(skill.getSkillRadius());
		
		if (skill.getSkillType() == L2SkillType.DUMMY)
		{
			if (onlyFirst)
			{
				return new L2Character[]
				{
					activeChar
				};
			}
			
			targetList.add(activeChar);
			for (L2Character obj : objs)
			{
				if (!((obj == activeChar) || (obj == sourcePlayer) || (obj.isNpc()) || (obj.isL2Attackable())))
				{
					continue;
				}
				
				if ((skill.getMaxTargets() > -1) && (targetList.size() >= skill.getMaxTargets()))
				{
					break;
				}
				targetList.add(obj);
			}
		}
		else
		{
			for (L2Character obj : objs)
			{
				if (obj.isL2Attackable() || obj.isPlayable())
				{
					
					if (!obj.isBehind(activeChar))
					{
						continue;
					}
					
					if (!L2Skill.checkForAreaOffensiveSkills(activeChar, obj, skill, srcInArena))
					{
						continue;
					}
					
					if (onlyFirst)
					{
						return new L2Character[]
						{
							obj
						};
					}
					
					if ((skill.getMaxTargets() > -1) && (targetList.size() >= skill.getMaxTargets()))
					{
						break;
					}
					targetList.add(obj);
				}
			}
		}
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.TARGET_BEHIND_AURA;
	}
}
