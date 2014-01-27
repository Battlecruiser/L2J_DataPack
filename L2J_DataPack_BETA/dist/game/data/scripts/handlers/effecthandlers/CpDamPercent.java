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
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.stats.Formulas;

/**
 * CP Damage Percent effect implementation.
 * @author Zoey76, Adry_85
 */
public final class CpDamPercent extends AbstractEffect
{
	public CpDamPercent(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CPDAMPERCENT;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if (info.getEffected().isPlayer())
		{
			if (info.getEffected().isPlayer() && info.getEffected().getActingPlayer().isFakeDeath())
			{
				info.getEffected().stopFakeDeath(true);
			}
			
			int damage = (int) ((info.getEffected().getCurrentCp() * getValue()) / 100);
			// Manage attack or cast break of the target (calculating rate, sending message)
			if (!info.getEffected().isRaid() && Formulas.calcAtkBreak(info.getEffected(), damage))
			{
				info.getEffected().breakAttack();
				info.getEffected().breakCast();
			}
			
			if (damage > 0)
			{
				info.getEffected().setCurrentCp(info.getEffected().getCurrentCp() - damage);
				if (info.getEffected() != info.getEffector())
				{
					info.getEffector().sendDamageMessage(info.getEffected(), damage, false, false, false);
					info.getEffected().notifyDamageReceived(damage, info.getEffector(), info.getSkill(), false, false);
				}
			}
			// Check if damage should be reflected
			Formulas.calcDamageReflected(info.getEffector(), info.getEffected(), info.getSkill(), false);
		}
	}
}