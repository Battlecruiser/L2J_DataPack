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

import com.l2jserver.gameserver.model.CharEffectList;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Synchronizing effects on player and servitor if one of them gets removed for some reason the same will happen to another.
 * @author UnAfraid
 */
public class ServitorShare extends L2Effect
{
	public ServitorShare(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.BUFF;
	}
	
	@Override
	public void onExit()
	{
		L2Effect[] effects = null;
		if (getEffected().isPlayer())
		{
			L2Summon summon = getEffector().getSummon();
			if ((summon != null) && summon.isServitor())
			{
				effects = summon.getAllEffects();
			}
		}
		else if (getEffected().isServitor())
		{
			L2PcInstance owner = getEffected().getActingPlayer();
			if (owner != null)
			{
				effects = owner.getAllEffects();
			}
		}
		
		if (effects != null)
		{
			for (L2Effect eff : effects)
			{
				if (eff.getSkill().getId() == getSkill().getId())
				{
					eff.exit();
					break;
				}
			}
		}
		super.onExit();
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
	
	@Override
	public boolean canBeStolen()
	{
		return false;
	}
	
	@Override
	public int getEffectFlags()
	{
		return CharEffectList.EFFECT_FLAG_SERVITOR_SHARE;
	}
}
