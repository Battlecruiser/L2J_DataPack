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

import com.l2jserver.gameserver.model.actor.instance.L2NpcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeSummonInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.serverpackets.StartRotation;
import com.l2jserver.gameserver.network.serverpackets.StopRotation;

/**
 * Bluff effect implementation.
 * @author decad
 */
public class Bluff extends L2Effect
{
	private final int _chance;
	
	public Bluff(Env env, EffectTemplate template)
	{
		super(env, template);
		_chance = template.hasParameters() ? template.getParameters().getInteger("chance", 100) : 100;
	}
	
	@Override
	public boolean calcSuccess()
	{
		return Formulas.calcProbability(_chance, getEffector(), getEffected(), getSkill());
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onStart()
	{
		if ((getEffected() instanceof L2NpcInstance) || ((getEffected().isNpc()) && (getEffected().getId() == 35062)) || (getEffected() instanceof L2SiegeSummonInstance))
		{
			return false;
		}
		
		getEffected().broadcastPacket(new StartRotation(getEffected().getObjectId(), getEffected().getHeading(), 1, 65535));
		getEffected().broadcastPacket(new StopRotation(getEffected().getObjectId(), getEffector().getHeading(), 65535));
		getEffected().setHeading(getEffector().getHeading());
		return true;
	}
}
