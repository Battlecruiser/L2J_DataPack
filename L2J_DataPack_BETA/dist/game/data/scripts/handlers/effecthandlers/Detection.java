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
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.AbnormalType;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * @author UnAfraid
 */
public class Detection extends AbstractEffect
{
	public Detection(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		super.onStart(info);
		if (!info.getEffector().isPlayer() || !info.getEffected().isPlayer())
		{
			return;
		}
		final L2PcInstance player = info.getEffector().getActingPlayer();
		final L2PcInstance target = info.getEffected().getActingPlayer();
		final boolean hasParty = player.isInParty();
		final boolean hasClan = player.getClanId() > 0;
		final boolean hasAlly = player.getAllyId() > 0;
		
		if (target.getAppearance().getInvisible())
		{
			if (hasParty && (target.isInParty()) && (player.getParty().getLeaderObjectId() == target.getParty().getLeaderObjectId()))
			{
				return;
			}
			else if (hasClan && (player.getClanId() == target.getClanId()))
			{
				return;
			}
			else if (hasAlly && (player.getAllyId() == target.getAllyId()))
			{
				return;
			}
			
			// Remove Hide.
			target.getEffectList().stopSkillEffects(true, AbnormalType.HIDE);
		}
	}
}
