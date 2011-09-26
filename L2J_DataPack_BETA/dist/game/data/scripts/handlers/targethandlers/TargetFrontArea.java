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
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 *
 */
public class TargetFrontArea implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		if (((target == null || target == activeChar || target.isAlikeDead()) && skill.getCastRange() >= 0) || (!(target instanceof L2Attackable || target instanceof L2Playable)))
		{
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
			return _emptyTargetList;
		}
		
		final L2Character origin;
		final boolean srcInArena = (activeChar.isInsideZone(L2Character.ZONE_PVP) && !activeChar.isInsideZone(L2Character.ZONE_SIEGE));
		final int radius = skill.getSkillRadius();
		
		if (skill.getCastRange() >= 0)
		{
			if (!L2Skill.checkForAreaOffensiveSkills(activeChar, target, skill, srcInArena))
				return _emptyTargetList;
			
			if (onlyFirst)
				return new L2Character[] { target };
			
			origin = target;
			targetList.add(origin); // Add target to target list
		}
		else
			origin = activeChar;
		
		final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharacters();
		for (L2Character obj : objs)
		{
			if (!(obj instanceof L2Attackable || obj instanceof L2Playable))
				continue;
			
			if (obj == origin)
				continue;
			
			if (Util.checkIfInRange(radius, origin, obj, true))
			{
				if (!obj.isInFrontOf(activeChar))
					continue;
				
				if (!L2Skill.checkForAreaOffensiveSkills(activeChar, obj, skill, srcInArena))
					continue;
				
				if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
					break;
				
				targetList.add(obj);
			}
		}
		
		if (targetList.isEmpty())
			return _emptyTargetList;
		
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_FRONT_AREA;
	}
}
