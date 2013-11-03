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
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.stats.BaseStats;
import com.l2jserver.gameserver.model.stats.Formulas;

/**
 * Backstab effect implementation.
 * @author Adry_85
 */
public final class Backstab extends AbstractEffect
{
	public Backstab(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean calcSuccess(BuffInfo info)
	{
		return info.getEffector().isBehindTarget() && !Formulas.calcPhysicalSkillEvasion(info.getEffector(), info.getEffected(), info.getSkill()) && Formulas.calcBlowSuccess(info.getEffector(), info.getEffected(), info.getSkill());
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
	public void onStart(BuffInfo info)
	{
		if (info.getEffector().isAlikeDead())
		{
			return;
		}
		
		L2Character target = info.getEffected();
		L2Character activeChar = info.getEffector();
		boolean ss = info.getSkill().useSoulShot() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		byte shld = Formulas.calcShldUse(activeChar, target, info.getSkill());
		double damage = (int) Formulas.calcBackstabDamage(activeChar, target, info.getSkill(), shld, ss);
		
		// Crit rate base crit rate for skill, modified with STR bonus
		if (Formulas.calcCrit(info.getSkill().getBaseCritRate() * 10 * BaseStats.STR.calcBonus(activeChar), true, target))
		{
			damage *= 2;
		}
		
		target.reduceCurrentHp(damage, activeChar, info.getSkill());
		target.notifyDamageReceived(damage, activeChar, info.getSkill(), true);
		
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
		Formulas.calcDamageReflected(activeChar, target, info.getSkill(), true);
	}
}