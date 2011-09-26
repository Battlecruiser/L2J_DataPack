/**
 * 
 */
package handlers.targethandlers;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;

/**
 * @author UnAfraid
 *
 */
public class TargetAuraCorpseMob implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		// Go through the L2Character _knownList
		final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharactersInRadius(skill.getSkillRadius());
		for (L2Character obj : objs)
		{
			if (obj instanceof L2Attackable && obj.isDead())
			{
				if (onlyFirst)
					return new L2Character[] { obj };
				
				if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
					break;
				
				targetList.add(obj);
			}
		}
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_AURA_CORPSE_MOB;
	}
}
