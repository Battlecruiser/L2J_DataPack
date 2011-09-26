/**
 * 
 */
package handlers.targethandlers;

import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2ChestInstance;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;

/**
 * @author UnAfraid
 *
 */
public class TargetUnlockable implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		if (!(target instanceof L2DoorInstance) && !(target instanceof L2ChestInstance))
		{
			return _emptyTargetList;
		}
		
		if (!onlyFirst)
		{
			targetList.add(target);
			return targetList.toArray(new L2Object[targetList.size()]);
		}
		else
			return new L2Character[] { target };
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_UNLOCKABLE;
	}
}
