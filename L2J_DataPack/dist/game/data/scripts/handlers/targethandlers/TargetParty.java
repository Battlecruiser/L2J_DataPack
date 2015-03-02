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
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * @author St3eT
 */
public class TargetParty implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new ArrayList<>();
		
		// Check for null target or any other invalid target
		if ((target == null) || target.isDead() || (target == activeChar))
		{
			activeChar.sendPacket(SystemMessageId.THAT_IS_AN_INCORRECT_TARGET);
			return EMPTY_TARGET_LIST;
		}
		
		final int radius = skill.getAffectRange();
		final L2PcInstance player = (L2PcInstance) activeChar.getTarget();
		
		if (player.isInParty())
		{
			for (L2PcInstance partyMember : player.getParty().getMembers())
			{
				if ((partyMember == null))
				{
					continue;
				}
				
				if (Skill.addCharacter(player, partyMember, radius, false))
				{
					targetList.add(partyMember);
				}
				
				if (Skill.addPet(player, partyMember, radius, false))
				{
					targetList.add(partyMember.getPet());
				}
				
				partyMember.getServitors().values().forEach(s ->
				{
					if (Skill.addCharacter(activeChar, s, radius, false))
					{
						targetList.add(s);
					}
				});
			}
		}
		else
		{
			targetList.add(target);
		}
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.TARGET_PARTY;
	}
}
