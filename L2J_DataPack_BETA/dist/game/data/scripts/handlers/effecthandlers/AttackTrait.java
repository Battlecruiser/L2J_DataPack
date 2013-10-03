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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.l2jserver.gameserver.model.actor.stat.CharStat;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.TraitType;

/**
 * Attack Trait effect implementation
 * @author Nos
 */
public class AttackTrait extends L2Effect
{
	private static final Logger _log = Logger.getLogger(AttackTrait.class.getName());
	
	private final Map<TraitType, Float> _attackTraits = new HashMap<>();
	
	/**
	 * @param env
	 * @param template
	 */
	public AttackTrait(Env env, EffectTemplate template)
	{
		super(env, template);
		if (template.hasParameters())
		{
			for (Entry<String, Object> param : template.getParameters().getSet().entrySet())
			{
				try
				{
					final TraitType traitType = TraitType.valueOf(param.getKey());
					final float value = (Float.parseFloat((String) param.getValue()) + 100) / 100;
					_attackTraits.put(traitType, value);
				}
				catch (NumberFormatException e)
				{
					_log.warning(getClass().getSimpleName() + ": value of " + param.getKey() + " must be float value " + param.getValue() + " found.");
				}
				catch (Exception e)
				{
					_log.warning(getClass().getSimpleName() + ": value of L2TraitType enum required but found: " + param.getValue());
				}
			}
		}
		else
		{
			_log.warning(getClass().getSimpleName() + ": must have parameters.");
		}
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
	
	@Override
	public boolean onStart()
	{
		final CharStat charStat = getEffected().getStat();
		synchronized (charStat.getAttackTraits())
		{
			for (Entry<TraitType, Float> trait : _attackTraits.entrySet())
			{
				charStat.getAttackTraits()[trait.getKey().getId()] *= trait.getValue();
				charStat.getAttackTraitsCount()[trait.getKey().getId()]++;
			}
		}
		return true;
	}
	
	@Override
	public void onExit()
	{
		final CharStat charStat = getEffected().getStat();
		synchronized (charStat.getAttackTraits())
		{
			for (Entry<TraitType, Float> trait : _attackTraits.entrySet())
			{
				charStat.getAttackTraits()[trait.getKey().getId()] /= trait.getValue();
				charStat.getAttackTraitsCount()[trait.getKey().getId()]--;
			}
		}
	}
}
