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

import com.l2jserver.gameserver.datatables.FishingRodsData;
import com.l2jserver.gameserver.enums.ShotType;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.fishing.L2Fishing;
import com.l2jserver.gameserver.model.fishing.L2FishingRod;
import com.l2jserver.gameserver.model.items.L2Weapon;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;

/**
 * Reeling effect implementation.
 * @author UnAfraid
 */
public final class Reeling extends AbstractEffect
{
	private final double _power;
	
	public Reeling(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		if (params.getString("power", null) == null)
		{
			throw new IllegalArgumentException(getClass().getSimpleName() + ": effect without power!");
		}
		_power = params.getDouble("power");
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.FISHING;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		final L2Character activeChar = info.getEffector();
		if (!activeChar.isPlayer())
		{
			return;
		}
		
		final L2PcInstance player = activeChar.getActingPlayer();
		final L2Fishing fish = player.getFishCombat();
		if (fish == null)
		{
			// Reeling skill is available only while fishing
			player.sendPacket(SystemMessageId.CAN_USE_REELING_ONLY_WHILE_FISHING);
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		final L2Weapon weaponItem = player.getActiveWeaponItem();
		final L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
		if ((weaponInst == null) || (weaponItem == null))
		{
			return;
		}
		int SS = 1;
		int pen = 0;
		if (activeChar.isChargedShot(ShotType.FISH_SOULSHOTS))
		{
			SS = 2;
		}
		final L2FishingRod fishingRod = FishingRodsData.getInstance().getFishingRod(weaponItem.getId());
		final double gradeBonus = fishingRod.getFishingRodLevel() * 0.1; // TODO: Check this formula (is guessed)
		int dmg = (int) ((fishingRod.getFishingRodDamage() + player.calcStat(Stats.FISHING_EXPERTISE, 1, null, null) + _power) * gradeBonus * SS);
		// Penalty 5% less damage dealt
		if (player.getSkillLevel(1315) <= (info.getSkill().getLevel() - 2)) // 1315 - Fish Expertise
		{
			player.sendPacket(SystemMessageId.REELING_PUMPING_3_LEVELS_HIGHER_THAN_FISHING_PENALTY);
			pen = (int) (dmg * 0.05);
			dmg = dmg - pen;
		}
		if (SS > 1)
		{
			weaponInst.setChargedShot(ShotType.FISH_SOULSHOTS, false);
		}
		
		fish.useReeling(dmg, pen);
	}
}
