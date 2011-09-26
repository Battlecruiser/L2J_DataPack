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
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2SummonInstance;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 *
 */
public class TargetAreaSummon implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		target = activeChar.getPet();
		if (target == null || !(target instanceof L2SummonInstance) || target.isDead())
			return _emptyTargetList;
		
		if (onlyFirst)
			return new L2Character[] { target };
		
		final boolean srcInArena = (activeChar.isInsideZone(L2Character.ZONE_PVP) && !activeChar.isInsideZone(L2Character.ZONE_SIEGE));
		final Collection<L2Character> objs = target.getKnownList().getKnownCharacters();
		final int radius = skill.getSkillRadius();
		
		for (L2Character obj : objs)
		{
			if (obj == null || obj == target || obj == activeChar)
				continue;
			
			if (!Util.checkIfInRange(radius, target, obj, true))
				continue;
			
			if (!(obj instanceof L2Attackable || obj instanceof L2Playable))
				continue;
			
			if (!L2Skill.checkForAreaOffensiveSkills(activeChar, obj, skill, srcInArena))
				continue;
			
			if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
				break;
			
			targetList.add(obj);
		}
		
		if (targetList.isEmpty())
			return _emptyTargetList;
		
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_AREA_SUMMON;
	}
}
