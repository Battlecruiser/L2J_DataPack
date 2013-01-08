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

import com.l2jserver.gameserver.ai.CtrlEvent;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * This is the Effect support for spoil.<br>
 * This was originally done by _drunk_
 * @author Ahmed
 */
public class Spoil extends L2Effect
{
	public Spoil(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.SPOIL;
	}
	
	@Override
	public boolean onStart()
	{
		
		if (!getEffector().isPlayer())
			return false;
		
		if (!getEffected().isMonster())
			return false;
		
		L2MonsterInstance target = (L2MonsterInstance) getEffected();
		
		if (target == null)
			return false;
		
		if (target.isSpoil())
		{
			getEffector().sendPacket(SystemMessageId.ALREADY_SPOILED);
			return false;
		}
		
		boolean spoil = false;
		if (target.isDead() == false)
		{
			spoil = Formulas.calcMagicSuccess(getEffector(), target, getSkill());
			
			if (spoil)
			{
				target.setSpoil(true);
				target.setIsSpoiledBy(getEffector().getObjectId());
				getEffector().sendPacket(SystemMessageId.SPOIL_SUCCESS);
			}
			target.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, getEffector());
		}
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
