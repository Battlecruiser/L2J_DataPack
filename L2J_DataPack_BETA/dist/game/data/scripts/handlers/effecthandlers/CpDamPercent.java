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

import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;

/**
 * CP Damage Percent effect implementation.
 * @author Zoey76
 */
public class CpDamPercent extends L2Effect
{
	public CpDamPercent(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.CPDAMPERCENT;
	}
	
	@Override
	public boolean onActionTime()
	{
		if (!getEffected().isDead())
		{
			double cp = (getEffected().getCurrentCp() * (100 - getEffectPower())) / 100;
			getEffected().setCurrentCp(cp);
			StatusUpdate sucp = new StatusUpdate(getEffected());
			sucp.addAttribute(StatusUpdate.CUR_CP, (int) cp);
			getEffected().sendPacket(sucp);
		}
		return false;
	}
}