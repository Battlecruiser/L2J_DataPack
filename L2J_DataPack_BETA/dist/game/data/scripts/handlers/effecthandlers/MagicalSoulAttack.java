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
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.util.Rnd;

/**
 * Magical Soul Attack effect implementation.
 * @author Adry_85
 */
public class MagicalSoulAttack extends L2Effect
{
	public MagicalSoulAttack(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public boolean calcSuccess()
	{
		return true;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.MAGICAL_ATTACK;
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
		
		if (target.isPlayer() && target.getActingPlayer().isFakeDeath())
		{
			target.stopFakeDeath(true);
		}
		
		boolean sps = getSkill().useSpiritShot() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
		boolean bss = getSkill().useSpiritShot() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
		final boolean mcrit = Formulas.calcMCrit(activeChar.getMCriticalHit(target, getSkill()));
		final byte shld = Formulas.calcShldUse(activeChar, target, getSkill());
		int damage = (int) Formulas.calcMagicDam(activeChar, target, getSkill(), shld, sps, bss, mcrit);
		
		// Curse of Divinity Formula (each buff increase +30%)
		if (getSkill().getDependOnTargetBuff())
		{
			damage *= (((target.getBuffCount() * 0.3) + 1.3) / 4);
		}
		
		if ((getSkill().getMaxSoulConsumeCount() > 0) && activeChar.isPlayer())
		{
			// Souls Formula (each soul increase +4%)
			int chargedSouls = (activeChar.getActingPlayer().getChargedSouls() <= getSkill().getMaxSoulConsumeCount()) ? activeChar.getActingPlayer().getChargedSouls() : getSkill().getMaxSoulConsumeCount();
			damage *= 1 + (chargedSouls * 0.04);
		}
		
		if (damage > 0)
		{
			// Manage attack or cast break of the target (calculating rate, sending message...)
			if (!target.isRaid() && Formulas.calcAtkBreak(target, damage))
			{
				target.breakAttack();
				target.breakCast();
			}
			
			// Shield Deflect Magic: Reflect all damage on caster.
			if (target.getStat().calcStat(Stats.VENGEANCE_SKILL_MAGIC_DAMAGE, 0, target, getSkill()) > Rnd.get(100))
			{
				activeChar.reduceCurrentHp(damage, target, getSkill());
				activeChar.notifyDamageReceived(damage, target, getSkill(), mcrit);
			}
			else
			{
				target.reduceCurrentHp(damage, activeChar, getSkill());
				target.notifyDamageReceived(damage, activeChar, getSkill(), mcrit);
				activeChar.sendDamageMessage(target, damage, mcrit, false, false);
			}
		}
		return true;
	}
}