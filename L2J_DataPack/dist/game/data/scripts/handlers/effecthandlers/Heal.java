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

import com.l2jserver.gameserver.enums.ShotType;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.items.type.CrystalType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExMagicAttackInfo;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Heal effect implementation.
 * @author UnAfraid
 */
public final class Heal extends AbstractEffect
{
	private final double _power;
	
	public Heal(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_power = params.getDouble("power", 0);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.HEAL;
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
		L2Character activeChar = info.getEffector();
		if ((target == null) || target.isDead() || target.isDoor() || target.isInvul())
		{
			return;
		}
		
		double amount = _power;
		double staticShotBonus = 0;
		int mAtkMul = 1;
		boolean sps = info.getSkill().isMagic() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
		boolean bss = info.getSkill().isMagic() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
		
		if (((sps || bss) && (activeChar.isPlayer() && activeChar.getActingPlayer().isMageClass())) || activeChar.isSummon())
		{
			staticShotBonus = info.getSkill().getMpConsume(); // static bonus for spiritshots
			mAtkMul = bss ? 4 : 2;
			staticShotBonus *= bss ? 2.4 : 1.0;
		}
		else if ((sps || bss) && activeChar.isNpc())
		{
			staticShotBonus = 2.4 * info.getSkill().getMpConsume(); // always blessed spiritshots
			mAtkMul = 4;
		}
		else
		{
			// no static bonus
			// grade dynamic bonus
			final L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
			if (weaponInst != null)
			{
				mAtkMul = weaponInst.getItem().getItemGrade() == CrystalType.S84 ? 4 : weaponInst.getItem().getItemGrade() == CrystalType.S80 ? 2 : 1;
			}
			// shot dynamic bonus
			mAtkMul = bss ? mAtkMul * 4 : mAtkMul + 1;
		}
		
		if (!info.getSkill().isStatic())
		{
			amount += staticShotBonus + Math.sqrt(mAtkMul * activeChar.getMAtk(activeChar, null));
			amount = target.calcStat(Stats.HEAL_EFFECT, amount, null, null);
			// Heal critic, since CT2.3 Gracia Final
			if (info.getSkill().isMagic() && Formulas.calcMCrit(activeChar.getMCriticalHit(target, info.getSkill())))
			{
				amount *= 3;
				activeChar.sendPacket(SystemMessageId.M_CRITICAL);
				activeChar.sendPacket(new ExMagicAttackInfo(activeChar.getObjectId(), target.getObjectId(), ExMagicAttackInfo.CRITICAL_HEAL));
				if (target.isPlayer() && (target != activeChar))
				{
					target.sendPacket(new ExMagicAttackInfo(activeChar.getObjectId(), target.getObjectId(), ExMagicAttackInfo.CRITICAL_HEAL));
				}
			}
		}
		
		// Prevents overheal and negative amount
		amount = Math.max(Math.min(amount, target.getMaxRecoverableHp() - target.getCurrentHp()), 0);
		if (amount != 0)
		{
			target.setCurrentHp(amount + target.getCurrentHp());
		}
		
		if (target.isPlayer())
		{
			if (info.getSkill().getId() == 4051)
			{
				target.sendPacket(SystemMessageId.REJUVENATING_HP);
			}
			else
			{
				if (activeChar.isPlayer() && (activeChar != target))
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S2_HP_HAS_BEEN_RESTORED_BY_C1);
					sm.addString(activeChar.getName());
					sm.addInt((int) amount);
					target.sendPacket(sm);
				}
				else
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HP_HAS_BEEN_RESTORED);
					sm.addInt((int) amount);
					target.sendPacket(sm);
				}
			}
		}
	}
}
