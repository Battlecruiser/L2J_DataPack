package handlers.targethandlers;

import java.util.Collection;

import javolution.util.FastList;

import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SummonInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.skills.L2SkillType;
import com.l2jserver.gameserver.util.Util;

/**
 * @author BiggBoss
 */
public class AreaTarget implements ITargetListHandler 
{
	private static final SkillTargetType[] TARGET =
	{
		SkillTargetType.TARGET_AREA,
		SkillTargetType.TARGET_AURA,
		SkillTargetType.TARGET_BEHIND_AREA,
		SkillTargetType.TARGET_BEHIND_AURA,
		SkillTargetType.TARGET_FRONT_AREA,
		SkillTargetType.TARGET_FRONT_AURA,
		SkillTargetType.TARGET_AREA_UNDEAD
	};
	
	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{	
		if(activeChar == null || activeChar.getTarget() == null)
			return _emptyTarget;
		
		if(!(activeChar.getTarget() instanceof L2Character))
			return _emptyTarget;
		
		if(onlyFirst)
			return new L2Object[] { activeChar.getTarget() };
		
		FastList<L2Object> targets = new FastList<L2Object>();
		L2Character target = (L2Character)activeChar.getTarget();
		
		switch(sk.getTargetType())
		{
        	case TARGET_AREA:
        	case TARGET_FRONT_AREA:
        	case TARGET_BEHIND_AREA:
        	{
        		if ((!(target instanceof L2Attackable || target instanceof L2Playable)) ||  
        				(sk.getCastRange() >= 0 && (target == null || target == activeChar || target.isAlikeDead()))) 
        		{
        			activeChar.sendPacket(new SystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
        			return _emptyTarget;
        		}

        		L2Character origin = null;
        		boolean srcInArena = (activeChar.isInsideZone(L2Character.ZONE_PVP) && !activeChar.isInsideZone(L2Character.ZONE_SIEGE));
        		int radius = sk.getSkillRadius();

        		if (sk.getCastRange() >= 0)
        			targets.add(target); 
        		else
        			origin = activeChar;


        		final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharacters();
        		//synchronized (activeChar.getKnownList().getKnownObjects())
        		{
        			for (L2Character obj : objs)
        			{
        				if (!(obj instanceof L2Attackable
        						|| obj instanceof L2Playable))
        					continue;

        				if (obj == origin)
        					continue;

        				if (Util.checkIfInRange(radius, origin, obj, true))
        				{
        					switch (sk.getTargetType())
        					{
								case TARGET_FRONT_AREA:
									if (!obj.isInFrontOf(activeChar))
										continue;
									break;
								case TARGET_BEHIND_AREA:
									if (!obj.isBehind(activeChar))
										continue;
									break;
        					}

        					if (!L2Skill.checkForAreaOffensiveSkills(activeChar, obj, sk, srcInArena))
        						continue;
						
        					targets.add(obj);
        				}
        			}
        		}
        		break;
        	}
            case TARGET_AURA:
            case TARGET_FRONT_AURA:
            case TARGET_BEHIND_AURA:
            {
                final boolean srcInArena = (activeChar.isInsideZone(L2Character.ZONE_PVP) && !activeChar.isInsideZone(L2Character.ZONE_SIEGE));

                final L2PcInstance sourcePlayer = activeChar.getActingPlayer();

                // Go through the L2Character _knownList
                final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharactersInRadius(sk.getSkillRadius());
                //synchronized (activeChar.getKnownList().getKnownObjects())
                if (sk.getSkillType() == L2SkillType.DUMMY)
                {
					targets.add(activeChar);
                	for (L2Character obj : objs)
                	{
                		if (!(obj == activeChar
                				|| obj == sourcePlayer
                				|| obj instanceof L2Npc
                				|| obj instanceof L2Attackable))
                			continue;
                		targets.add(obj);
                	}
                }
                else
				{
					for (L2Character obj : objs)
					{
						if (obj instanceof L2Attackable || obj instanceof L2Playable)
						{
							switch (sk.getTargetType())
							{
								case TARGET_FRONT_AURA:
									if (!obj.isInFrontOf(activeChar))
										continue;
									break;
								case TARGET_BEHIND_AURA:
									if(!obj.isBehind(activeChar))
										continue;
									break;
							}

							if (!L2Skill.checkForAreaOffensiveSkills(activeChar, obj, sk, srcInArena))
								continue;

							if (onlyFirst)
								return new L2Character[] { obj };

							targets.add(obj);
						}
					}
				}
                break;
            }
            case TARGET_AREA_UNDEAD:
            {
                L2Character cha;
                int radius = sk.getSkillRadius();
                if (sk.getCastRange() >= 0 && (target instanceof L2Npc || target instanceof L2SummonInstance)
                		&& target.isUndead() && !target.isAlikeDead())
                {
                    cha = target;
                    targets.add(cha); 
                }
                else cha = activeChar;

                final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharacters();
                //synchronized (cha.getKnownList().getKnownObjects())
				{
					for (L2Character obj : objs)
					{
						if (!Util.checkIfInRange(radius, cha, obj, true))
							continue;
						if (obj instanceof L2Npc)
							target = obj;
						else if (obj instanceof L2SummonInstance)
							target = obj;
						else
							continue;
						
						if (!target.isAlikeDead())
						{
							if (!target.isUndead())
								continue;

			                if (L2Skill.geoEnabled && !GeoData.getInstance().canSeeTarget(activeChar, target))
								continue;
							
							if (!onlyFirst)
								targets.add(obj);
							else
								return new L2Character[] { obj };
						}
					}
				}
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
		
	}	
}
