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
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.templates.skills.L2SkillType;

/**
 * @author UnAfraid
 *
 */
public class TargetBehindAura implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		final boolean srcInArena = (activeChar.isInsideZone(L2Character.ZONE_PVP) && !activeChar.isInsideZone(L2Character.ZONE_SIEGE));
		
		final L2PcInstance sourcePlayer = activeChar.getActingPlayer();
		
		final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharactersInRadius(skill.getSkillRadius());
		
		if (skill.getSkillType() == L2SkillType.DUMMY)
		{
			if (onlyFirst)
				return new L2Character[] { activeChar };
			
			targetList.add(activeChar);
			for (L2Character obj : objs)
			{
				if (!(obj == activeChar || obj == sourcePlayer || obj instanceof L2Npc || obj instanceof L2Attackable))
					continue;
				
				if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
					break;
				targetList.add(obj);
			}
		}
		else
		{
			for (L2Character obj : objs)
			{
				if (obj instanceof L2Attackable || obj instanceof L2Playable)
				{
					
					if (!obj.isBehind(activeChar))
						continue;
					
					if (!L2Skill.checkForAreaOffensiveSkills(activeChar, obj, skill, srcInArena))
						continue;
					
					if (onlyFirst)
						return new L2Character[] { obj };
					
					if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
						break;
					targetList.add(obj);
				}
			}
		}
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_BEHIND_AURA;
	}
}
