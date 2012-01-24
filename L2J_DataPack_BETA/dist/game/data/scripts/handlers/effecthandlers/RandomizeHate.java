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
package handlers.effecthandlers;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.util.Rnd;

public class RandomizeHate extends L2Effect
{
	public RandomizeHate(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.RANDOMIZE_HATE;
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected() == null || getEffected() == getEffector())
			return false;
		
		// Effect is for mobs only.
		if (!(getEffected() instanceof L2Attackable))
			return false;
		
		L2Attackable effectedMob = (L2Attackable) getEffected();
		
		List<L2Character> targetList = new FastList<L2Character>();
		
		// Getting the possible targets
		
		Collection<L2Character> chars = getEffected().getKnownList().getKnownCharacters();
		for (L2Character cha : chars)
		{
			if (cha != null && (cha != effectedMob) && (cha != getEffector()))
			{
				// Aggro cannot be transfared to a mob of the same faction.
				if (cha instanceof L2Attackable && ((L2Attackable) cha).getFactionId() != null && ((L2Attackable) cha).getFactionId().equals(effectedMob.getFactionId()))
					continue;
				
				targetList.add(cha);
			}
		}
		// if there is no target, exit function
		if (targetList.isEmpty())
			return true;
		
		// Choosing randomly a new target
		final L2Character target = targetList.get(Rnd.get(targetList.size()));
		
		final int hate = effectedMob.getHating(getEffector());
		effectedMob.stopHating(getEffector());
		effectedMob.addDamageHate(target, 0, hate);
		
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}