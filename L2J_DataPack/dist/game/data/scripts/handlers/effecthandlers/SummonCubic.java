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
import com.l2jserver.gameserver.model.actor.instance.L2CubicInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.serverpackets.CharInfo;
import com.l2jserver.gameserver.network.serverpackets.ExUserInfoCubic;
import com.l2jserver.util.Rnd;

/**
 * Summon Cubic effect implementation.
 * @author Zoey76
 */
public final class SummonCubic extends AbstractEffect
{
	/** Cubic ID. */
	private final int _cubicId;
	/** Cubic power. */
	private final int _cubicPower;
	/** Cubic duration. */
	private final int _cubicDuration;
	/** Cubic activation delay. */
	private final int _cubicDelay;
	/** Cubic maximum casts before going idle. */
	private final int _cubicMaxCount;
	/** Cubic activation chance. */
	private final int _cubicSkillChance;
	
	public SummonCubic(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_cubicId = params.getInt("cubicId", -1);
		// Custom AI data.
		_cubicPower = params.getInt("cubicPower", 0);
		_cubicDuration = params.getInt("cubicDuration", 0);
		_cubicDelay = params.getInt("cubicDelay", 0);
		_cubicMaxCount = params.getInt("cubicMaxCount", -1);
		_cubicSkillChance = params.getInt("cubicSkillChance", 0);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if ((info.getEffected() == null) || !info.getEffected().isPlayer() || info.getEffected().isAlikeDead() || info.getEffected().getActingPlayer().inObserverMode())
		{
			return;
		}
		
		if (_cubicId < 0)
		{
			_log.warning(SummonCubic.class.getSimpleName() + ": Invalid Cubic ID:" + _cubicId + " in skill ID: " + info.getSkill().getId());
			return;
		}
		
		final L2PcInstance player = info.getEffected().getActingPlayer();
		if (player.inObserverMode() || player.isMounted())
		{
			return;
		}
		
		// Gnacik: TODO: Make better method of calculation.
		// If skill is enchanted calculate cubic skill level based on enchant
		// 8 at 101 (+1 Power)
		// 12 at 130 (+30 Power)
		// Because 12 is max 5115-5117 skills
		int _cubicSkillLevel = info.getSkill().getLevel();
		if (_cubicSkillLevel > 100)
		{
			_cubicSkillLevel = ((info.getSkill().getLevel() - 100) / 7) + 8;
		}
		
		// If cubic is already present, it's replaced.
		final L2CubicInstance cubic = player.getCubicById(_cubicId);
		if (cubic != null)
		{
			cubic.stopAction();
			cubic.cancelDisappear();
			player.getCubics().remove(_cubicId);
		}
		else
		{
			// If maximum amount is reached, random cubic is removed.
			// Players with no mastery can have only one cubic.
			final int allowedCubicCount = info.getEffected().getActingPlayer().getStat().getMaxCubicCount();
			final int currentCubicCount = player.getCubics().size();
			// Extra cubics are removed, one by one, randomly.
			for (int i = 0; i <= (currentCubicCount - allowedCubicCount); i++)
			{
				final int removedCubicId = (int) player.getCubics().keySet().toArray()[Rnd.get(currentCubicCount)];
				final L2CubicInstance removedCubic = player.getCubicById(removedCubicId);
				removedCubic.stopAction();
				removedCubic.cancelDisappear();
				player.getCubics().remove(removedCubic.getId());
			}
		}
		// Adding a new cubic.
		player.addCubic(_cubicId, _cubicSkillLevel, _cubicPower, _cubicDelay, _cubicSkillChance, _cubicMaxCount, _cubicDuration, info.getEffected() != info.getEffector());
		player.sendPacket(new ExUserInfoCubic(player));
		player.broadcastPacket(new CharInfo(player));
	}
}
