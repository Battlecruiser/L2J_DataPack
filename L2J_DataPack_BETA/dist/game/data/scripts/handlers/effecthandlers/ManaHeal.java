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

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author UnAfraid
 */
public class ManaHeal extends L2Effect
{
	public ManaHeal(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.MANAHEAL;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character target = getEffected();
		if ((target == null) || target.isDead() || target.isDoor())
		{
			return false;
		}
		
		StatusUpdate su = new StatusUpdate(target);
		
		double amount = calc();
		
		if (!getSkill().isStatic())
		{
			amount = target.calcStat(Stats.RECHARGE_MP_RATE, amount, null, null);
		}
		
		// Prevents overheal and negative amount
		amount = Math.max(Math.min(amount, target.getMaxRecoverableMp() - target.getCurrentMp()), 0);
		
		target.setCurrentMp(amount + target.getCurrentMp());
		
		SystemMessage sm;
		if (getEffector().getObjectId() != target.getObjectId())
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.S2_MP_RESTORED_BY_C1);
			sm.addCharName(getEffector());
		}
		else
		{
			sm = SystemMessage.getSystemMessage(SystemMessageId.S1_MP_RESTORED);
		}
		sm.addNumber((int) amount);
		target.sendPacket(sm);
		su.addAttribute(StatusUpdate.CUR_MP, (int) target.getCurrentMp());
		target.sendPacket(su);
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
