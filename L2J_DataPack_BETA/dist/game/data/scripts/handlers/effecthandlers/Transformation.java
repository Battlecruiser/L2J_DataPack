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

import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Transformation effect implementation.
 * @author nBd
 */
public class Transformation extends L2Effect
{
	public Transformation(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	public Transformation(Env env, L2Effect effect)
	{
		super(env, effect);
	}
	
	@Override
	public boolean canBeStolen()
	{
		return false;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.TRANSFORMATION;
	}
	
	@Override
	public void onExit()
	{
		getEffected().stopTransformation(false);
	}
	
	@Override
	public boolean onStart()
	{
		if (!getEffected().isPlayer())
		{
			return false;
		}
		
		L2PcInstance trg = getEffected().getActingPlayer();
		if (trg == null)
		{
			return false;
		}
		
		if (trg.isAlikeDead() || trg.isCursedWeaponEquipped())
		{
			return false;
		}
		
		if (trg.isSitting())
		{
			trg.sendPacket(SystemMessageId.CANNOT_TRANSFORM_WHILE_SITTING);
			return false;
		}
		
		if (trg.isTransformed() || trg.isInStance())
		{
			trg.sendPacket(SystemMessageId.YOU_ALREADY_POLYMORPHED_AND_CANNOT_POLYMORPH_AGAIN);
			return false;
		}
		
		if (trg.isInWater())
		{
			trg.sendPacket(SystemMessageId.YOU_CANNOT_POLYMORPH_INTO_THE_DESIRED_FORM_IN_WATER);
			return false;
		}
		
		if (trg.isFlyingMounted() || trg.isMounted() || trg.isRidingStrider())
		{
			trg.sendPacket(SystemMessageId.YOU_CANNOT_POLYMORPH_WHILE_RIDING_A_PET);
			return false;
		}
		
		TransformationManager.getInstance().transformPlayer((int) calc(), trg);
		return true;
	}
}
