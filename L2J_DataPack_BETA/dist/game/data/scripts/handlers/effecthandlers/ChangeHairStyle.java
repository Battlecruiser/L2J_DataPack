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

import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Change Hair Style effect.
 * @author Zoey76
 */
public class ChangeHairStyle extends L2Effect
{
	public ChangeHairStyle(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public boolean onStart()
	{
		if ((getEffector() == null) || (getEffected() == null) || !getEffector().isPlayer() || !getEffected().isPlayer() || getEffected().isAlikeDead())
		{
			return false;
		}
		
		final L2PcInstance player = getEffector().getActingPlayer();
		player.getAppearance().setHairStyle(getSkill().getHairStyleId());
		player.broadcastUserInfo();
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.BUFF;
	}
}
