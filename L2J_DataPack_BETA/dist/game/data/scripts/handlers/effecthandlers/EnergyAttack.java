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

import com.l2jserver.gameserver.enums.ShotType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.BaseStats;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Energy Attack effect implementation.
 * @author Adry_85
 */
public class EnergyAttack extends L2Effect
{
	public EnergyAttack(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public boolean calcSuccess()
	{
		return !Formulas.calcPhysicalSkillEvasion(getEffector(), getEffected(), getSkill());
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.ENERGY_ATTACK;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character target = getEffected();
		L2Character activeChar = getEffector();
		if (activeChar.isAlikeDead())
		{
			return false;
		}
		
		boolean ss = getSkill().isPhysical() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		byte shld = Formulas.calcShldUse(activeChar, target, getSkill());
		boolean crit = false;
		if (getSkill().getBaseCritRate() > 0)
		{
			crit = Formulas.calcCrit(getSkill().getBaseCritRate() * 10 * BaseStats.STR.calcBonus(activeChar), true, target);
		}
		// damage calculation
		double damage = Formulas.calcPhysDam(activeChar, target, getSkill(), shld, false, ss);
		
		double modifier = 0;
		if (activeChar.isPlayer())
		{
			// Charges Formula (each charge increase +25%)
			modifier = ((activeChar.getActingPlayer().getCharges() * 0.25) + 1);
		}
		if (crit)
		{
			damage *= 2;
		}
		
		if (damage > 0)
		{
			double finalDamage = damage * modifier;
			target.reduceCurrentHp(finalDamage, activeChar, getSkill());
			target.notifyDamageReceived(damage, activeChar, getSkill(), crit);
			activeChar.sendDamageMessage(target, (int) finalDamage, false, crit, false);
			// Check if damage should be reflected
			Formulas.calcDamageReflected(activeChar, target, getSkill(), crit);
		}
		else
		{
			activeChar.sendPacket(SystemMessageId.ATTACK_FAILED);
		}
		return true;
	}
}