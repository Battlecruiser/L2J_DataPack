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

import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.util.Rnd;

/**
 * Unsummon effect implementation.
 * @author Adry_85
 */
public class Unsummon extends L2Effect
{
	private final int _chance;
	
	public Unsummon(Env env, EffectTemplate template)
	{
		super(env, template);
		_chance = template.hasParameters() ? template.getParameters().getInt("chance", 100) : 100;
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
	public boolean calcSuccess()
	{
		int magicLevel = getSkill().getMagicLevel();
		if ((magicLevel <= 0) || ((getEffected().getLevel() - 9) <= magicLevel))
		{
			double chance = _chance * Formulas.calcAttributeBonus(getEffector(), getEffected(), getSkill()) * Formulas.calcGeneralTraitBonus(getEffector(), getEffected(), getSkill().getTraitType(), false);
			if (chance > (Rnd.nextDouble() * 100))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean onStart()
	{
		if (!getEffected().isSummon())
		{
			return false;
		}
		
		final L2PcInstance summonOwner = getEffected().getActingPlayer();
		final L2Summon summon = getEffected().getSummon();
		if (summon != null)
		{
			if (summon.isPhoenixBlessed() || summon.isNoblesseBlessed())
			{
				summon.stopEffects(L2EffectType.NOBLESSE_BLESSING);
			}
			else
			{
				summon.stopAllEffectsExceptThoseThatLastThroughDeath();
			}
			
			summon.abortAttack();
			summon.abortCast();
			summon.unSummon(summonOwner);
			summonOwner.sendPacket(SystemMessageId.YOUR_SERVITOR_HAS_VANISHED);
			return true;
		}
		return false;
	}
}
