package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author BiggBoss
 */
public class SingleHumanTarget implements ITargetListHandler
{
	private static final SkillTargetType[] TARGETS = 
	{ 
		SkillTargetType.TARGET_SELF,
		SkillTargetType.TARGET_ONE,
		SkillTargetType.TARGET_OWNER_PET,
		SkillTargetType.TARGET_GROUND
	};

	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{
		// Avoid null pointers
		if(activeChar == null || activeChar.getTarget() == null) 
			return _emptyTarget;
		
		// If no objects in knowlist, no targets possibles
		if(activeChar.getKnownList().getKnownObjects().values().isEmpty())
			return _emptyTarget;
				
		L2Object[] target = new L2Object[1];
		L2Object targ = activeChar.getTarget();
		
		if(targ instanceof L2Character)
		{
			if(((L2Character)targ).isDead())
				return _emptyTarget;
		}
		
		// Switch the skill target type
		switch(sk.getTargetType())
		{
			case TARGET_GROUND:
			case TARGET_SELF:
			{
				target[0] = activeChar;
				break;
			}
			case TARGET_ONE:
			{
				boolean canTargetSelf = false;
				
                switch (sk.getSkillType())
				{
					case BUFF:
					case HEAL:
					case HOT:
					case HEAL_PERCENT:
					case MANARECHARGE:
					case MANAHEAL:
					case NEGATE:
					case CANCEL:
					case CANCEL_DEBUFF:
					case REFLECT:
					case COMBATPOINTHEAL:
					case MAGE_BANE:
					case WARRIOR_BANE:
					case BETRAY:
					case BALANCE_LIFE:
						canTargetSelf = true;
						break;
				}

				if(targ == activeChar && canTargetSelf)
					target[0] = activeChar;
				else if(targ != activeChar)
					target[0] = targ;
				else
					sendIncorrectTargetMessage(activeChar);
				break;
			}
			case TARGET_OWNER_PET:
			{
				L2Object t = activeChar.getTarget();
				if(t instanceof L2Summon)
				{
					if(((L2Summon) t).getOwner() != null)
						target[0] = ((L2Summon) t).getOwner();
				}
				else
					sendIncorrectTargetMessage(activeChar);
				break;
			}
			// Shouldnt reach this
			default:
			{
				return _emptyTarget;
			}
		}
		return target;
	}

	@Override
	public SkillTargetType[] getTargetsType() 
	{
		return TARGETS;
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
