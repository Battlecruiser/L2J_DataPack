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
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.BaseStats;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Physical Soul Attack effect implementation.
 * @author Adry_85
 */
public class PhysicalSoulAttack extends L2Effect
{
	public PhysicalSoulAttack(Env env, EffectTemplate template)
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
		return L2EffectType.PHYSICAL_ATTACK;
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
		
		if (((getSkill().getFlyRadius() > 0) || (getSkill().getFlyType() != null)) && activeChar.isMovementDisabled())
		{
			final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addSkillName(getSkill());
			activeChar.sendPacket(sm);
			return false;
		}
		
		if (target.isPlayer() && target.getActingPlayer().isFakeDeath())
		{
			target.stopFakeDeath(true);
		}
		
		int damage = 0;
		boolean ss = getSkill().isPhysical() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		final byte shld = Formulas.calcShldUse(activeChar, target, getSkill());
		// Physical damage critical rate is only affected by STR.
		boolean crit = false;
		if (getSkill().getBaseCritRate() > 0)
		{
			crit = Formulas.calcCrit(getSkill().getBaseCritRate() * 10 * BaseStats.STR.calcBonus(activeChar), true, target);
		}
		
		damage = (int) Formulas.calcPhysDam(activeChar, target, getSkill(), shld, false, ss);
		
		if ((getSkill().getMaxSoulConsumeCount() > 0) && activeChar.isPlayer())
		{
			// Souls Formula (each soul increase +4%)
			int chargedSouls = (activeChar.getActingPlayer().getChargedSouls() <= getSkill().getMaxSoulConsumeCount()) ? activeChar.getActingPlayer().getChargedSouls() : getSkill().getMaxSoulConsumeCount();
			damage *= 1 + (chargedSouls * 0.04);
		}
		if (crit)
		{
			damage *= 2;
		}
		
		if (damage > 0)
		{
			activeChar.sendDamageMessage(target, damage, false, crit, false);
			target.reduceCurrentHp(damage, activeChar, getSkill());
			// Check if damage should be reflected
			Formulas.calcDamageReflected(activeChar, target, getSkill(), crit);
		}
		else
		{
			activeChar.sendPacket(SystemMessageId.ATTACK_FAILED);
		}
		
		if (getSkill().isSuicideAttack())
		{
			activeChar.doDie(activeChar);
		}
		return true;
	}
}