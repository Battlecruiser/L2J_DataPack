package handlers.targethandlers;

import java.util.Collection;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2ArtefactInstance;
import com.l2jserver.gameserver.model.actor.instance.L2ChestInstance;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SummonInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author BiggBoss
 */
public class SpecialTarget implements ITargetListHandler 
{
	private static final SkillTargetType[] TARGET =
	{
		SkillTargetType.TARGET_UNDEAD,
		SkillTargetType.TARGET_UNLOCKABLE,
		SkillTargetType.TARGET_MULTIFACE,
		SkillTargetType.TARGET_HOLY,
		SkillTargetType.TARGET_FLAGPOLE
	};
	
	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{
		if(activeChar == null || activeChar.getTarget() == null
				|| !(activeChar instanceof L2Character))
			return _emptyTarget;
		
		FastList<L2Object> targets = new FastList<L2Object>();
		
		L2Character target = (L2Character) activeChar.getTarget();
		
		switch(sk.getTargetType())
		{
        	case TARGET_UNDEAD:
        	{
                if (target instanceof L2Npc || target instanceof L2SummonInstance)
                {
                    if (!target.isUndead() || target.isDead())
                    {
                        activeChar.sendPacket(new SystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
                        return _emptyTarget;
                    }

                    if (!onlyFirst)
                    	targets.add(target);
                    else
                    	return new L2Character[] {target};

                    return targets.toArray(new L2Object[targets.size()]);
                }
                else
                	sendIncorrectTargetMessage(activeChar);
                break;
        	}
            case TARGET_UNLOCKABLE:
            {
        		if(onlyFirst)
        		{
        			targets.add(activeChar.getTarget());
        			return (L2Object[]) targets.toArray();
        		}
                if (target instanceof L2DoorInstance && target instanceof L2ChestInstance)
                	targets.add(target);
                else
                	sendIncorrectTargetMessage(activeChar);
                break;
            }
            case TARGET_MULTIFACE:
            {
        		if(onlyFirst)
        		{
        			targets.add(activeChar.getTarget());
        			return (L2Object[]) targets.toArray();
        		}
                if (target instanceof L2Attackable && target instanceof L2PcInstance)
                	targets.add(target);
                else
                {
                	sendIncorrectTargetMessage(activeChar);
                	break;
                }
                
                final int radius = sk.getSkillRadius();

                final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharactersInRadius(radius);
                //synchronized (activeChar.getKnownList().getKnownObjects())
				{
					for (L2Character obj : objs)
					{
						if (obj instanceof L2Attackable && obj != target)
							targets.add(obj);				
					}
				}
				break;
            }
            case TARGET_HOLY:
            {
                if (activeChar instanceof L2PcInstance)
                {
                    if (target instanceof L2ArtefactInstance)
                        targets.add(target);
                    else
                    	sendIncorrectTargetMessage(activeChar);
                }
                break;
            }
            case TARGET_FLAGPOLE:
            {
            	targets.add(activeChar);
            	break;
            }
            default:
            	return _emptyTarget;
		}
		return (L2Object[]) targets.toArray();
	}

	@Override
	public SkillTargetType[] getTargetsType() 
	{
		return TARGET;
	}

	@Override
	public void sendIncorrectTargetMessage(L2Character activeChar) 
	{
		if(!(activeChar instanceof L2PcInstance))
			return;
		SystemMessage sm = new SystemMessage(SystemMessageId.INCORRECT_TARGET);
		activeChar.sendPacket(sm);
	}
}
