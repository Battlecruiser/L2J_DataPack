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
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Hp By Level effect implementation..
 * @author Zoey76
 */
public class HpByLevel extends L2Effect
{
	public HpByLevel(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public boolean calcSuccess()
	{
		return true;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.BUFF;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffector() == null)
		{
			return false;
		}
		// Calculation
		final int abs = (int) calc();
		final double absorb = ((getEffector().getCurrentHp() + abs) > getEffector().getMaxHp() ? getEffector().getMaxHp() : (getEffector().getCurrentHp() + abs));
		final int restored = (int) (absorb - getEffector().getCurrentHp());
		getEffector().setCurrentHp(absorb);
		// System message
		final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HP_RESTORED);
		sm.addNumber(restored);
		getEffector().sendPacket(sm);
		return true;
	}
}
