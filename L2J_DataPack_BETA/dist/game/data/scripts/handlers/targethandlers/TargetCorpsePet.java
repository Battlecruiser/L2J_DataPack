/**
 * 
 */
package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author UnAfraid
 *
 */
public class TargetCorpsePet implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		if (activeChar instanceof L2PcInstance)
		{
			target = activeChar.getPet();
			if (target != null && target.isDead())
				return new L2Character[] { target };
		}
		
		return _emptyTargetList;
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_CORPSE_PET;
	}
}
