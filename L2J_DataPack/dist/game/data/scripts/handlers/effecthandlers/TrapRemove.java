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

import com.l2jserver.gameserver.enums.TrapAction;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2TrapInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.events.EventDispatcher;
import com.l2jserver.gameserver.model.events.impl.character.trap.OnTrapAction;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Trap Remove effect implementation.
 * @author UnAfraid
 */
public final class TrapRemove extends AbstractEffect
{
	private final int _power;
	
	public TrapRemove(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		if (params.isEmpty())
		{
			throw new IllegalArgumentException(getClass().getSimpleName() + ": effect without power!");
		}
		
		_power = params.getInt("power");
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		final L2Character target = info.getEffected();
		if (!target.isTrap())
		{
			return;
		}
		
		if (target.isAlikeDead())
		{
			return;
		}
		
		final L2TrapInstance trap = (L2TrapInstance) target;
		if (!trap.canBeSeen(info.getEffector()))
		{
			if (info.getEffector().isPlayer())
			{
				info.getEffector().sendPacket(SystemMessageId.INVALID_TARGET);
			}
			return;
		}
		
		if (trap.getLevel() > _power)
		{
			return;
		}
		
		// Notify to scripts
		EventDispatcher.getInstance().notifyEventAsync(new OnTrapAction(trap, info.getEffector(), TrapAction.TRAP_DISARMED), trap);
		
		trap.unSummon();
		if (info.getEffector().isPlayer())
		{
			info.getEffector().sendPacket(SystemMessageId.THE_TRAP_DEVICE_HAS_BEEN_STOPPED);
		}
	}
}
