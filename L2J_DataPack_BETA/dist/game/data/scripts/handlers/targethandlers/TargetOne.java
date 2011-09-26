/**
 * 
 */
package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author UnAfraid
 *
 */
public class TargetOne implements ISkillTargetTypeHandler
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
				canTargetSelf = true;
				break;
		}
		
		// Check for null target or any other invalid target
		if (target == null || target.isDead() || (target == activeChar && !canTargetSelf))
		{
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
			return _emptyTargetList;
		}
		
		// If a target is found, return it in a table else send a system message TARGET_IS_INCORRECT
		return new L2Character[] { target };
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_ONE;
	}
}
