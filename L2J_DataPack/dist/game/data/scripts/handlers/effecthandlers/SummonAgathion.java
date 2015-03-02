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
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.serverpackets.CharInfo;
import com.l2jserver.gameserver.network.serverpackets.ExUserInfoCubic;

/**
 * Summon Agathion effect implementation.
 * @author Zoey76
 */
public final class SummonAgathion extends AbstractEffect
{
	private final int _npcId;
	
	public SummonAgathion(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		if (params.isEmpty())
		{
			_log.warning(getClass().getSimpleName() + ": must have parameters.");
		}
		
		_npcId = params.getInt("npcId", 0);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if ((info.getEffected() == null) || !info.getEffected().isPlayer())
		{
			return;
		}
		
		final L2PcInstance player = info.getEffected().getActingPlayer();
		
		player.setAgathionId(_npcId);
		player.sendPacket(new ExUserInfoCubic(player));
		player.broadcastPacket(new CharInfo(player));
	}
}
