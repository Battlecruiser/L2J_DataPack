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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.model.zone.ZoneId;

/**
 * @author St3eT
 */
public class Ground implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		final List<L2Character> targetList = new ArrayList<>();
		final L2PcInstance player = (L2PcInstance) activeChar;
		final int maxTargets = skill.getAffectLimit();
		final boolean srcInArena = (activeChar.isInsideZone(ZoneId.PVP) && !activeChar.isInsideZone(ZoneId.SIEGE));
		
		for (L2Character character : activeChar.getKnownList().getKnownCharacters())
		{
			if ((character != null) && character.isInsideRadius(player.getCurrentSkillWorldPosition(), skill.getAffectRange(), false, false))
			{
				if (!Skill.checkForAreaOffensiveSkills(activeChar, character, skill, srcInArena))
				{
					continue;
				}
				
				if (character.isDoor())
				{
					continue;
				}
				
				if ((maxTargets > 0) && (targetList.size() >= maxTargets))
				{
					break;
				}
				targetList.add(character);
			}
		}
		
		if (targetList.isEmpty())
		{
			if (skill.hasEffectType(L2EffectType.SUMMON_NPC))
			{
				targetList.add(activeChar);
			}
		}
		return targetList.isEmpty() ? EMPTY_TARGET_LIST : targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.GROUND;
	}
}