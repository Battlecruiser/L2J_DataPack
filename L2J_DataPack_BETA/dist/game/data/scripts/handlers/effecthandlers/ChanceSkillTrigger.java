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
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Chance Skill Trigger effect implementation.
 */
public final class ChanceSkillTrigger extends AbstractEffect
{
	public ChanceSkillTrigger(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		info.getEffected().onActionTimeChanceEffect(info.getSkill().getElement());
		return info.getSkill().isPassive();
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		// trigger only if effect in use and successfully ticked to the end
		if (info.getTickCount(this) >= getTicks())
		{
			info.getEffected().onExitChanceEffect(info.getSkill().getElement());
		}
		info.getEffected().removeChanceEffect(this);
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		info.getEffected().addChanceTrigger(this);
		info.getEffected().onStartChanceEffect(info.getSkill().getElement());
	}
}