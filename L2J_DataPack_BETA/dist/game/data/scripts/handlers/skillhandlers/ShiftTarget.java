/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.skillhandlers;

import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;

public class ShiftTarget implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.SHIFT_TARGET
	};
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.ISkillHandler#useSkill(com.l2jserver.gameserver.model.actor.L2Character, com.l2jserver.gameserver.model.skills.L2Skill, com.l2jserver.gameserver.model.L2Object[])
	 */
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (targets == null)
			return;
		L2Character target = (L2Character) targets[0];
		
		if (activeChar.isAlikeDead() || target == null)
			return;
		
		for (L2Character obj : activeChar.getKnownList().getKnownCharactersInRadius(skill.getSkillRadius()))
		{
			if (!(obj instanceof L2Attackable) || obj.isDead())
				continue;
			L2Attackable hater = ((L2Attackable) obj);
			if (hater.getHating(activeChar) == 0)
				continue;
			hater.addDamageHate(target, 0, hater.getHating(activeChar));
			
		}
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.ISkillHandler#getSkillIds()
	 */
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
