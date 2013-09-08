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

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.taskmanager.DecayTaskManager;

/**
 * Resurrection effect implementation.
 * @author Adry_85
 */
public class Resurrection extends L2Effect
{
	private final int _power;
	
	public Resurrection(Env env, EffectTemplate template)
	{
		super(env, template);
		_power = template.getParameters().getInt("power", 0);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.RESURRECTION;
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character target = getEffected();
		L2Character activeChar = getEffector();
		
		if (activeChar.isPlayer())
		{
			if (target.getActingPlayer() != null)
			{
				target.getActingPlayer().reviveRequest(activeChar.getActingPlayer(), getSkill(), target.isPet(), _power);
			}
		}
		else
		{
			DecayTaskManager.getInstance().cancelDecayTask(target);
			target.doRevive(Formulas.calculateSkillResurrectRestorePercent(_power, activeChar));
		}
		return true;
	}
}