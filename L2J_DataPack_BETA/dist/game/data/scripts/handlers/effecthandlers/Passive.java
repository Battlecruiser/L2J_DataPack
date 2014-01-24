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

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Passive effect implementation.
 * @author Adry_85
 */
public final class Passive extends AbstractEffect
{
	public Passive(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		info.getEffected().enableAllSkills();
		info.getEffected().setIsImmobilized(false);
	}
	
	@Override
	public boolean canStart(BuffInfo info)
	{
		return info.getEffected().isL2Attackable();
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		L2Attackable target = (L2Attackable) info.getEffected();
		target.abortAttack();
		target.abortCast();
		target.disableAllSkills();
		target.setIsImmobilized(true);
	}
}
