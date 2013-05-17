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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.ai.CtrlEvent;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectFlag;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.util.Rnd;

/**
 * Confusion effect implementation.
 * @author littlecrow
 */
public class Confusion extends L2Effect
{
	public Confusion(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public int getEffectFlags()
	{
		return EffectFlag.CONFUSED.getMask();
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CONFUSION;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		final List<L2Character> targetList = new ArrayList<>();
		// Getting the possible targets
		for (L2Object obj : getEffected().getKnownList().getKnownObjects().values())
		{
			if (((getEffected().isMonster() && obj.isL2Attackable()) || (obj instanceof L2Character)) && (obj != getEffected()))
			{
				targetList.add((L2Character) obj);
			}
		}
		// if there is no target, exit function
		if (!targetList.isEmpty())
		{
			// Choosing randomly a new target
			final L2Character target = targetList.get(Rnd.nextInt(targetList.size()));
			// Attacking the target
			getEffected().setTarget(target);
			getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
			return true;
		}
		return false;
	}
	
	@Override
	public void onExit()
	{
		if (!getEffected().isPlayer())
		{
			getEffected().getAI().notifyEvent(CtrlEvent.EVT_THINK);
		}
	}
	
	@Override
	public boolean onStart()
	{
		getEffected().getAI().notifyEvent(CtrlEvent.EVT_CONFUSED);
		return true;
	}
}
