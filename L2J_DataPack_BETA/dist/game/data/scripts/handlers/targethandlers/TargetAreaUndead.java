/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.targethandlers;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.Config;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.templates.skills.L2TargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2SummonInstance;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class TargetAreaUndead implements ITargetTypeHandler
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
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.TARGET_AREA_UNDEAD;
	}
}
