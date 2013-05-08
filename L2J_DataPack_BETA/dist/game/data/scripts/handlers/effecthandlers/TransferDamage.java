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

import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Transfer Damage effect implementation.
 * @author UnAfraid
 */
public class TransferDamage extends L2Effect
{
	public TransferDamage(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	public TransferDamage(Env env, L2Effect effect)
	{
		super(env, effect);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.DAMAGE_TRANSFER;
	}
	
	@Override
	public void onExit()
	{
		if (getEffected().isPlayable() && getEffector().isPlayer())
		{
			((L2Playable) getEffected()).setTransferDamageTo(null);
		}
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected().isPlayable() && getEffector().isPlayer())
		{
			((L2Playable) getEffected()).setTransferDamageTo(getEffector().getActingPlayer());
		}
		return true;
	}
}