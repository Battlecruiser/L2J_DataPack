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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2ServitorInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * @author UnAfraid
 */
public class CorpseMob implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new ArrayList<>();
		final boolean isSummon = target.isServitor();
		if (!(isSummon || target.isL2Attackable()) || !target.isDead())
		{
			activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
			return _emptyTargetList;
		}
		
		// Corpse mob only available for half time
		switch (skill.getSkillType())
		{
			case SUMMON:
			{
				if (isSummon && (((L2ServitorInstance) target).getOwner() != null) && (((L2ServitorInstance) target).getOwner().getObjectId() == activeChar.getObjectId()))
				{
					return _emptyTargetList;
				}
				
				break;
			}
			case DRAIN:
			{
				if (((L2Attackable) target).isOldCorpse(activeChar.getActingPlayer(), (Config.NPC_DECAY_TIME / 2), true))
				{
					return _emptyTargetList;
				}
			}
		}
		
		if (!onlyFirst)
		{
			targetList.add(target);
			return targetList.toArray(new L2Object[targetList.size()]);
		}
		return new L2Character[]
		{
			target
		};
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.CORPSE_MOB;
	}
}
