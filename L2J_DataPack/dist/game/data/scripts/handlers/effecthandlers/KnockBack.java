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

import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.serverpackets.FlyToLocation;
import com.l2jserver.gameserver.network.serverpackets.FlyToLocation.FlyType;
import com.l2jserver.gameserver.network.serverpackets.ValidateLocation;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public final class KnockBack extends AbstractEffect
{
	private int _distance = 50;
	private int _speed = 0;
	private int _delay = 0;
	private int _animationSpeed = 0;
	
	public KnockBack(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		if (params != null)
		{
			_distance = params.getInt("distance", 50);
			_speed = params.getInt("speed", 0);
			_delay = params.getInt("delay", 0);
			_animationSpeed = params.getInt("animationSpeed", 0);
		}
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		final L2Character effected = info.getEffected();
		final double radians = Math.toRadians(Util.calculateAngleFrom(info.getEffector(), info.getEffected()));
		final int x = (int) (info.getEffected().getX() + (_distance * Math.cos(radians)));
		final int y = (int) (info.getEffected().getY() + (_distance * Math.sin(radians)));
		final int z = effected.getZ();
		final Location loc = GeoData.getInstance().moveCheck(effected.getX(), effected.getY(), effected.getZ(), x, y, z, effected.getInstanceId());
		
		effected.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		effected.broadcastPacket(new FlyToLocation(effected, loc, FlyType.PUSH_HORIZONTAL, _speed, _delay, _animationSpeed));
		effected.abortAttack();
		effected.abortCast();
		effected.setXYZ(loc);
		effected.broadcastPacket(new ValidateLocation(effected));
	}
}
