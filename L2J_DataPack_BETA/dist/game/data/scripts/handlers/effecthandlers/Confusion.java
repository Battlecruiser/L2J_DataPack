/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.effecthandlers;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.CharEffectList;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.util.Rnd;

/**
 * Implementation of the Confusion Effect.<br>
 * Zoey76: TODO: Review onActionTime() method.
 * @author littlecrow
 */
public class Confusion extends L2Effect
{
	
	public Confusion(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CONFUSION;
	}
	
	@Override
	public boolean onStart()
	{
		getEffected().startConfused();
		onActionTime();
		return true;
	}
	
	@Override
	public void onExit()
	{
		getEffected().stopConfused(this);
	}
	
	@Override
	public boolean onActionTime()
	{
		List<L2Character> targetList = new FastList<>();
		
		// Getting the possible targets
		
		Collection<L2Object> objs = getEffected().getKnownList().getKnownObjects().values();
		// synchronized (getEffected().getKnownList().getKnownObjects())
		{
			for (L2Object obj : objs)
			{
				if ((obj instanceof L2Character) && (obj != getEffected()))
					targetList.add((L2Character) obj);
			}
		}
		// if there is no target, exit function
		if (targetList.isEmpty())
			return true;
		
		// Choosing randomly a new target
		int nextTargetIdx = Rnd.nextInt(targetList.size());
		L2Object target = targetList.get(nextTargetIdx);
		
		// Attacking the target
		getEffected().setTarget(target);
		getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
		
		return true;
	}
	
	@Override
	public int getEffectFlags()
	{
		return CharEffectList.EFFECT_FLAG_CONFUSED;
	}
}
