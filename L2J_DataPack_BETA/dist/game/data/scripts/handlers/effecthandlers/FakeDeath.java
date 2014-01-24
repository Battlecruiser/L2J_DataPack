/*
 * Copyright (C) 2004-2014 L2J DataPack
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
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ChangeWaitType;
import com.l2jserver.gameserver.network.serverpackets.Revive;

/**
 * Fake Death effect implementation.
 * @author mkizub
 */
public final class FakeDeath extends AbstractEffect
{
	public FakeDeath(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.FAKE_DEATH;
	}
	
	@Override
	public boolean onActionTime(BuffInfo info)
	{
		if (info.getEffected().isDead())
		{
			return false;
		}
		
		final double manaDam = getValue() * getTicksMultiplier();
		if (manaDam > info.getEffected().getCurrentMp())
		{
			if (info.getSkill().isToggle())
			{
				info.getEffected().sendPacket(SystemMessageId.SKILL_REMOVED_DUE_LACK_MP);
				return false;
			}
		}
		
		info.getEffected().reduceCurrentMp(manaDam);
		
		return info.getSkill().isToggle();
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		if (info.getEffected().isPlayer())
		{
			info.getEffected().getActingPlayer().setIsFakeDeath(false);
			info.getEffected().getActingPlayer().setRecentFakeDeath(true);
		}
		
		info.getEffected().broadcastPacket(new ChangeWaitType(info.getEffected(), ChangeWaitType.WT_STOP_FAKEDEATH));
		info.getEffected().broadcastPacket(new Revive(info.getEffected()));
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		info.getEffected().startFakeDeath();
	}
}
