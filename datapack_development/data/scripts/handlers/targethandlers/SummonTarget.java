package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SummonInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author BiggBoss
 */
public class SummonTarget implements ITargetListHandler 
{
	private static final SkillTargetType[] TARGETS =
	{
		SkillTargetType.TARGET_PET,
		SkillTargetType.TARGET_SUMMON,
		SkillTargetType.TARGET_ENEMY_SUMMON
	};
	
	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{
		if(activeChar == null || activeChar.getTarget() == null
				|| !(activeChar.getTarget() instanceof L2Summon))
			return _emptyTarget;
		
		if(activeChar.getKnownList().getKnownSummons().values().isEmpty())
			return _emptyTarget;
		
		/*
		if(!isInsideRadious(activeChar, activeChar.getTarget(), sk.getSkillRadius()))
			return _emptyTarget;
		*/
		
		L2Object[] target = new L2Object[1];
		L2Object targ = activeChar.getTarget();
		
		if(targ instanceof L2Character)
		{
			if(((L2Character)targ).isDead())
				return _emptyTarget;
		}
		
		switch(sk.getTargetType())
		{
			case TARGET_PET:
			{	
				if(targ == activeChar.getPet())
					target[0] = targ;
				else
					sendIncorrectTargetMessage(activeChar);
				break;
			}
			case TARGET_SUMMON:
			{
				if(targ == activeChar.getPet() && targ instanceof L2SummonInstance)
					target[0] = targ;
				else
					sendIncorrectTargetMessage(activeChar);
				break;
			}
            case TARGET_ENEMY_SUMMON:
            {
                if(activeChar.getTarget() instanceof L2Summon)
                {
                    L2Summon targetSummon = (L2Summon)activeChar.getTarget();
                    if (activeChar instanceof L2PcInstance && activeChar.getPet() != targetSummon && !targetSummon.isDead()
                            && (targetSummon.getOwner().getPvpFlag() != 0 || targetSummon.getOwner().getKarma() > 0)
                            || (targetSummon.getOwner().isInsideZone(L2Character.ZONE_PVP) && ((L2PcInstance)activeChar).isInsideZone(L2Character.ZONE_PVP)))
                       target[0] = targetSummon;
                }
            }
            break;
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
