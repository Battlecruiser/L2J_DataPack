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

import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.serverpackets.ExRegMax;

/**
 * Heal Over Time effect implementation.
 */
public class HealOverTime extends L2Effect
{
	public HealOverTime(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	public HealOverTime(Env env, L2Effect effect)
	{
		super(env, effect);
	}
	
	@Override
	public boolean calcSuccess()
	{
		return true;
	}
	
	@Override
	public boolean canBeStolen()
	{
		return true;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.HEAL_OVER_TIME;
	}
	
	@Override
	public boolean onActionTime()
	{
		if (getEffected().isDead() || getEffected().isDoor())
		{
			return false;
		}
		
		double hp = getEffected().getCurrentHp();
		double maxhp = getEffected().getMaxRecoverableHp();
		
		// Not needed to set the HP and send update packet if player is already at max HP
		if (hp >= maxhp)
		{
			return false;
		}
		
		hp += calc() * getEffectTemplate().getTotalTickCount();
		hp = Math.min(hp, maxhp);
		getEffected().setCurrentHp(hp);
		return false;
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected().isPlayer())
		{
			getEffected().sendPacket(new ExRegMax(calc(), getEffectTemplate().getTotalTickCount() * getAbnormalTime(), getAbnormalTime()));
		}
		return true;
	}
}
