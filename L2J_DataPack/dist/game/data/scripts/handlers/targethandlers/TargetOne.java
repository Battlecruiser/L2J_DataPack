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
package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.templates.skills.L2TargetType;

/**
 * @author UnAfraid
 */
public class TargetOne implements ITargetTypeHandler
{
	
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		boolean canTargetSelf = false;
		switch (skill.getSkillType())
		{
			case BUFF:
			case HEAL:
			case HOT:
			case HEAL_PERCENT:
			case MANARECHARGE:
			case MANA_BY_LEVEL:
			case MANAHEAL:
			case NEGATE:
			case CANCEL_DEBUFF:
			case COMBATPOINTHEAL:
			case BALANCE_LIFE:
			case HPMPCPHEAL_PERCENT:
			case HPMPHEAL_PERCENT:
			case HPCPHEAL_PERCENT:
			case DUMMY:
				canTargetSelf = true;
				break;
		}
		
		// Check for null target or any other invalid target
		if (target == null || target.isDead() || (target == activeChar && !canTargetSelf))
		{
			activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
			return _emptyTargetList;
		}
		
		// If a target is found, return it in a table else send a system message TARGET_IS_INCORRECT
		return new L2Character[] { target };
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.TARGET_ONE;
	}
}
