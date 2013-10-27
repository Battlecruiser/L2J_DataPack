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
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.EffectFlag;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.util.Rnd;

/**
 * Confuse effect implementation.
 * @author littlecrow
 */
public final class Confuse extends AbstractEffect
{
	private final int _chance;
	
	public Confuse(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		_chance = hasParameters() ? getParameters().getInt("chance", 100) : 100;
	}
	
	@Override
	public boolean calcSuccess(BuffInfo info)
	{
		return Formulas.calcProbability(_chance, info.getEffector(), info.getEffected(), info.getSkill());
	}
	
	@Override
	public int getEffectFlags()
	{
		return EffectFlag.CONFUSED.getMask();
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		if (!info.getEffected().isPlayer())
		{
			info.getEffected().getAI().notifyEvent(CtrlEvent.EVT_THINK);
		}
	}
	
	@Override
	public boolean onStart(BuffInfo info)
	{
		info.getEffected().getAI().notifyEvent(CtrlEvent.EVT_CONFUSED);
		
		final List<L2Character> targetList = new ArrayList<>();
		// Getting the possible targets
		for (L2Object obj : info.getEffected().getKnownList().getKnownObjects().values())
		{
			if (((info.getEffected().isMonster() && obj.isL2Attackable()) || (obj instanceof L2Character)) && (obj != info.getEffected()))
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
			info.getEffected().setTarget(target);
			info.getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
		}
		return true;
	}
}
