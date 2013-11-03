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

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeSummonInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.serverpackets.StartRotation;
import com.l2jserver.gameserver.network.serverpackets.StopRotation;

/**
 * Bluff effect implementation.
 * @author decad
 */
public final class Bluff extends AbstractEffect
{
	private final int _chance;
	
	public Bluff(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_chance = hasParameters() ? getParameters().getInt("chance", 100) : 100;
	}
	
	@Override
	public boolean calcSuccess(BuffInfo info)
	{
		return Formulas.calcProbability(_chance, info.getEffector(), info.getEffected(), info.getSkill());
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if ((info.getEffected() instanceof L2NpcInstance) || (info.getEffected().isNpc() && (info.getEffected().getId() == 35062)) || (info.getEffected() instanceof L2SiegeSummonInstance))
		{
			return;
		}
		
		info.getEffected().broadcastPacket(new StartRotation(info.getEffected().getObjectId(), info.getEffected().getHeading(), 1, 65535));
		info.getEffected().broadcastPacket(new StopRotation(info.getEffected().getObjectId(), info.getEffector().getHeading(), 65535));
		info.getEffected().setHeading(info.getEffector().getHeading());
	}
}
