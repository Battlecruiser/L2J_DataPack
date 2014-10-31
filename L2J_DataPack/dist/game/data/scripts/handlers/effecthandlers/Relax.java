/*
 * Copyright (C) 2004-2014 L2J DataPack
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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.EffectFlag;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Relax effect implementation.
 */
public final class Relax extends AbstractEffect
{
	private final double _power;
	
	public Relax(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_power = params.getDouble("power", 0);
	}
	
	@Override
	public int getEffectFlags()
	{
		return EffectFlag.RELAXING.getMask();
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.RELAXING;
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		if (info.getEffected().isDead())
		{
			return false;
		}
		
		if (info.getEffected().isPlayer())
		{
			if (!info.getEffected().getActingPlayer().isSitting())
			{
				return false;
			}
		}
		
		if ((info.getEffected().getCurrentHp() + 1) > info.getEffected().getMaxRecoverableHp())
		{
			if (info.getSkill().isToggle())
			{
				info.getEffected().sendPacket(SystemMessageId.SKILL_DEACTIVATED_HP_FULL);
				return false;
			}
		}
		
		final double manaDam = _power * getTicksMultiplier();
		if (manaDam > info.getEffected().getCurrentMp())
		{
			if (info.getSkill().isToggle())
			{
				info.getEffected().sendPacket(SystemMessageId.SKILL_REMOVED_DUE_LACK_MP);
				return false;
			}
		}
		
		info.getEffected().reduceCurrentMp(manaDam);
		
		return info.getSkill().isToggle();
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if (info.getEffected().isPlayer())
		{
			info.getEffected().getActingPlayer().sitDown(false);
		}
		else
		{
			info.getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_REST);
		}
	}
}
