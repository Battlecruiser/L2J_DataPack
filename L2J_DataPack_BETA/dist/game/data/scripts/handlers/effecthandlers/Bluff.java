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
import com.l2jserver.gameserver.model.actor.L2Character;
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
		
		_chance = params != null ? params.getInt("chance", 100) : 100;
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
		final L2Character effected = info.getEffected();
		// Headquarters NPC should not rotate
		if ((effected.getId() == 35062) || effected.isRaid() || effected.isRaidMinion())
		{
			return;
		}
		
		final L2Character effector = info.getEffector();
		effected.broadcastPacket(new StartRotation(effected.getObjectId(), effected.getHeading(), 1, 65535));
		effected.broadcastPacket(new StopRotation(effected.getObjectId(), effector.getHeading(), 65535));
		effected.setHeading(effector.getHeading());
	}
}
