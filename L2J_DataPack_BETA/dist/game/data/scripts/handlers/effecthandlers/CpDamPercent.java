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
import com.l2jserver.gameserver.model.stats.Formulas;

/**
 * CP Damage Percent effect implementation.
 * @author Zoey76, Adry_85
 */
public class CpDamPercent extends L2Effect
{
	public CpDamPercent(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CPDAMPERCENT;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected().isPlayer())
		{
			if (getEffected().isPlayer() && getEffected().getActingPlayer().isFakeDeath())
			{
				getEffected().stopFakeDeath(true);
			}
			
			int damage = (int) ((getEffected().getCurrentCp() * calc()) / 100);
			// Manage attack or cast break of the target (calculating rate, sending message)
			if (!getEffected().isRaid() && Formulas.calcAtkBreak(getEffected(), damage))
			{
				getEffected().breakAttack();
				getEffected().breakCast();
			}
			
			if (damage > 0)
			{
				getEffected().setCurrentCp(getEffected().getCurrentCp() - damage);
				if (getEffected() != getEffector())
				{
					getEffector().sendDamageMessage(getEffected(), damage, false, false, false);
					getEffected().notifyDamageReceivedToEffects(damage, getEffector(), getSkill(), false);
				}
			}
			// Check if damage should be reflected
			Formulas.calcDamageReflected(getEffector(), getEffected(), getSkill(), false);
			return true;
		}
		return false;
	}
}