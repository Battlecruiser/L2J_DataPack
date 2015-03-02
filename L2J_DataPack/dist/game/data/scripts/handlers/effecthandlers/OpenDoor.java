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

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.util.Rnd;

/**
 * Open Door effect implementation.
 * @author Adry_85
 */
public final class OpenDoor extends AbstractEffect
{
	private final int _chance;
	private final boolean _isItem;
	
	public OpenDoor(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_chance = params.getInt("chance", 0);
		_isItem = params.getBoolean("isItem", false);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if (!info.getEffected().isDoor())
		{
			return;
		}
		
		final L2Character effector = info.getEffector();
		L2DoorInstance door = (L2DoorInstance) info.getEffected();
		// Check if door in the different instance
		if (effector.getInstanceId() != door.getInstanceId())
		{
			// Search for the instance
			final Instance inst = InstanceManager.getInstance().getInstance(effector.getInstanceId());
			if (inst == null)
			{
				// Instance not found
				return;
			}
			final L2DoorInstance instanceDoor = inst.getDoor(door.getId());
			if (instanceDoor != null)
			{
				// Door found
				door = instanceDoor;
			}
			
			// Checking instance again
			if (effector.getInstanceId() != door.getInstanceId())
			{
				return;
			}
		}
		
		if ((!door.isOpenableBySkill() && !_isItem) || (door.getFort() != null))
		{
			effector.sendPacket(SystemMessageId.THIS_DOOR_CANNOT_BE_UNLOCKED);
			return;
		}
		
		if ((Rnd.get(100) < _chance) && !door.getOpen())
		{
			door.openMe();
		}
		else
		{
			effector.sendPacket(SystemMessageId.YOU_HAVE_FAILED_TO_UNLOCK_THE_DOOR);
		}
	}
}
