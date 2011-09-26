/**
 * 
 */
package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;

/**
 * @author UnAfraid
 *
 */
public class TargetFlagPole implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		return new L2Character[] { activeChar };
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_FLAGPOLE;
	}
}
