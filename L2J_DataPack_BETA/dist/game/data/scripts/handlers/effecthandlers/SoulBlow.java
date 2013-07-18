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

import com.l2jserver.gameserver.model.ShotType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.BaseStats;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;

/**
 * Soul Blow effect implementation.
 * @author Adry_85
 */
public class SoulBlow extends L2Effect
{
	public SoulBlow(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	/**
	 * If is not evaded and blow lands.
	 */
	@Override
	public boolean calcSuccess()
	{
		return !Formulas.calcPhysicalSkillEvasion(getEffector(), getEffected(), getSkill()) && Formulas.calcBlowSuccess(getEffector(), getEffected(), getSkill());
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.FATAL_BLOW;
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
		
		boolean ss = getSkill().useSoulShot() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		byte shld = Formulas.calcShldUse(activeChar, target, getSkill());
		double damage = (int) Formulas.calcBlowDamage(activeChar, target, getSkill(), shld, ss);
		if ((getSkill().getMaxSoulConsumeCount() > 0) && activeChar.isPlayer())
		{
			// Souls Formula (each soul increase +4%)
			int chargedSouls = (activeChar.getActingPlayer().getChargedSouls() <= getSkill().getMaxSoulConsumeCount()) ? activeChar.getActingPlayer().getChargedSouls() : getSkill().getMaxSoulConsumeCount();
			damage *= 1 + (chargedSouls * 0.04);
		}
		
		// Crit rate base crit rate for skill, modified with STR bonus
		boolean crit = Formulas.calcCrit(getSkill().getBaseCritRate() * 10 * BaseStats.STR.calcBonus(activeChar), true, target);
		if (crit)
		{
			damage *= 2;
		}
		
		target.reduceCurrentHp(damage, activeChar, getSkill());
		target.notifyDamageReceived(damage, activeChar, getSkill(), crit);
		
		// Manage attack or cast break of the target (calculating rate, sending message...)
		if (!target.isRaid() && Formulas.calcAtkBreak(target, damage))
		{
			target.breakAttack();
			target.breakCast();
		}
		
		if (activeChar.isPlayer())
		{
			L2PcInstance activePlayer = activeChar.getActingPlayer();
			activePlayer.sendDamageMessage(target, (int) damage, false, true, false);
		}
		// Check if damage should be reflected
		Formulas.calcDamageReflected(activeChar, target, getSkill(), true);
		
		return true;
	}
}