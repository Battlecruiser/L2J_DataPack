/**
 * 
 */
package handlers.targethandlers;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.Config;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2SummonInstance;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 *
 */
public class TargetAreaUndead implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		final L2Character cha;
		final int radius = skill.getSkillRadius();
		if (skill.getCastRange() >= 0 && (target instanceof L2Npc || target instanceof L2SummonInstance) && target.isUndead() && !target.isAlikeDead())
		{
			cha = target;
			
			if (!onlyFirst)
				targetList.add(cha);
			else
				return new L2Character[] { cha };
		}
		else
			cha = activeChar;
		
		final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharacters();
		for (L2Character obj : objs)
		{
			if (!Util.checkIfInRange(radius, cha, obj, true))
				continue;
			else if (obj instanceof L2Npc)
				target = obj;
			else if (obj instanceof L2SummonInstance)
				target = obj;
			else
				continue;
			
			if (!target.isAlikeDead())
			{
				if (!target.isUndead())
					continue;
				else if (Config.GEODATA > 0 && !GeoData.getInstance().canSeeTarget(activeChar, target))
					continue;
				else if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
					break;
				else if (!onlyFirst)
					targetList.add(obj);
				else
					return new L2Character[] { obj };
			}
		}
		
		if (targetList.isEmpty())
			return _emptyTargetList;
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_AREA_UNDEAD;
	}
}
