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
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Magical Attack MP effect.
 * @author Adry_85
 */
public class MagicalAttackMp extends L2Effect
{
	public MagicalAttackMp(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.MAGICAL_ATTACK_MP;
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
		
		boolean acted = Formulas.calcMagicAffected(activeChar, target, getSkill());
		if (target.isInvul() || !acted)
		{
			activeChar.sendPacket(SystemMessageId.MISSED_TARGET);
		}
		else
		{
			boolean sps = getSkill().useSpiritShot() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
			boolean bss = getSkill().useSpiritShot() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
			final byte shld = Formulas.calcShldUse(activeChar, target, getSkill());
			final boolean mcrit = Formulas.calcMCrit(activeChar.getMCriticalHit(target, getSkill()));
			double damage = Formulas.calcManaDam(activeChar, target, getSkill(), shld, sps, bss, mcrit);
			double mp = (damage > target.getCurrentMp() ? target.getCurrentMp() : damage);
			
			if (damage > 0)
			{
				target.stopEffectsOnDamage(true);
				target.setCurrentMp(target.getCurrentMp() - mp);
			}
			
			if (target.isPlayer())
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S2_MP_HAS_BEEN_DRAINED_BY_C1);
				sm.addCharName(activeChar);
				sm.addNumber((int) mp);
				target.sendPacket(sm);
			}
			
			if (activeChar.isPlayer())
			{
				SystemMessage sm2 = SystemMessage.getSystemMessage(SystemMessageId.YOUR_OPPONENTS_MP_WAS_REDUCED_BY_S1);
				sm2.addNumber((int) mp);
				activeChar.sendPacket(sm2);
			}
		}
		return true;
	}
}