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

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.serverpackets.ExRegMax;

/**
 * Heal Over Time effect implementation.
 */
public final class HealOverTime extends AbstractEffect
{
	public HealOverTime(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.HEAL_OVER_TIME;
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		if (info.getEffected().isDead() || info.getEffected().isDoor())
		{
			return false;
		}
		
		double hp = info.getEffected().getCurrentHp();
		double maxhp = info.getEffected().getMaxRecoverableHp();
		
		// Not needed to set the HP and send update packet if player is already at max HP
		if (hp >= maxhp)
		{
			return false;
		}
		
		hp += getValue() * getTicks();
		hp = Math.min(hp, maxhp);
		info.getEffected().setCurrentHp(hp);
		return info.getSkill().isToggle();
	}
	
	@Override
	public boolean onStart(BuffInfo info)
	{
		if (info.getEffected().isPlayer() && (getTicks() > 0))
		{
			info.getEffected().sendPacket(new ExRegMax(getValue(), info.getAbnormalTime(), info.getAbnormalTime() / getTicks()));
		}
		return true;
	}
}
