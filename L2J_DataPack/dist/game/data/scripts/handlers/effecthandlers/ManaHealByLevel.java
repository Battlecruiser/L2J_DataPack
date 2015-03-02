/*
 * Copyright (C) 2004-2015 L2J DataPack
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
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Mana Heal By Level effect implementation.
 * @author UnAfraid
 */
public final class ManaHealByLevel extends AbstractEffect
{
	private final double _power;
	
	public ManaHealByLevel(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_power = params.getDouble("power", 0);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.MANAHEAL_BY_LEVEL;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		L2Character target = info.getEffected();
		if ((target == null) || target.isDead() || target.isDoor() || target.isInvul())
		{
			return;
		}
		
		double amount = _power;
		
		// recharged mp influenced by difference between target level and skill level
		// if target is within 5 levels or lower then skill level there's no penalty.
		amount = target.calcStat(Stats.MANA_CHARGE, amount, null, null);
		if (target.getLevel() > info.getSkill().getMagicLevel())
		{
			int lvlDiff = target.getLevel() - info.getSkill().getMagicLevel();
			// if target is too high compared to skill level, the amount of recharged mp gradually decreases.
			if (lvlDiff == 6)
			{
				amount *= 0.9; // only 90% effective
			}
			else if (lvlDiff == 7)
			{
				amount *= 0.8; // 80%
			}
			else if (lvlDiff == 8)
			{
				amount *= 0.7; // 70%
			}
			else if (lvlDiff == 9)
			{
				amount *= 0.6; // 60%
			}
			else if (lvlDiff == 10)
			{
				amount *= 0.5; // 50%
			}
			else if (lvlDiff == 11)
			{
				amount *= 0.4; // 40%
			}
			else if (lvlDiff == 12)
			{
				amount *= 0.3; // 30%
			}
			else if (lvlDiff == 13)
			{
				amount *= 0.2; // 20%
			}
			else if (lvlDiff == 14)
			{
				amount *= 0.1; // 10%
			}
			else if (lvlDiff >= 15)
			{
				amount = 0; // 0mp recharged
			}
		}
		
		// Prevents overheal and negative amount
		amount = Math.max(Math.min(amount, target.getMaxRecoverableMp() - target.getCurrentMp()), 0);
		if (amount != 0)
		{
			target.setCurrentMp(amount + target.getCurrentMp());
		}
		
		final SystemMessage sm = SystemMessage.getSystemMessage(info.getEffector().getObjectId() != target.getObjectId() ? SystemMessageId.S2_MP_HAS_BEEN_RESTORED_BY_C1 : SystemMessageId.S1_MP_HAS_BEEN_RESTORED);
		if (info.getEffector().getObjectId() != target.getObjectId())
		{
			sm.addCharName(info.getEffector());
		}
		sm.addInt((int) amount);
		target.sendPacket(sm);
	}
}
