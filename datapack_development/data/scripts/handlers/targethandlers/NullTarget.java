package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;

/**
 * @author BiggBoss
 */
public class NullTarget implements ITargetListHandler
{
	private static final SkillTargetType[] TARGET = 
	{ 
		SkillTargetType.TARGET_NONE 
	};
	
	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{
		return _emptyTarget;
	}

	@Override
	public SkillTargetType[] getTargetsType()
	{
		return TARGET;
	}

	@Override
	public void sendIncorrectTargetMessage(L2Character activeChar) 
	{
		// Unused
	}	
}
