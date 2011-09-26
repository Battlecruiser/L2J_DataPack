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
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 *
 */
public class TargetClanMember implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		if (activeChar instanceof L2Npc)
		{
			// for buff purposes, returns friendly mobs nearby and mob itself
			final L2Npc npc = (L2Npc) activeChar;
			if (npc.getFactionId() == null || npc.getFactionId().isEmpty())
			{
				return new L2Character[] { activeChar };
			}
			final Collection<L2Object> objs = activeChar.getKnownList().getKnownObjects().values();
			for (L2Object newTarget : objs)
			{
				if (newTarget instanceof L2Npc && npc.getFactionId().equals(((L2Npc) newTarget).getFactionId()))
				{
					if (!Util.checkIfInRange(skill.getCastRange(), activeChar, newTarget, true))
						continue;
					if (((L2Npc) newTarget).getFirstEffect(skill) != null)
						continue;
					targetList.add((L2Npc) newTarget);
					break;
				}
			}
			if (targetList.isEmpty())
				targetList.add(npc);
		}
		else
			return _emptyTargetList;
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_CLAN_MEMBER;
	}
}
