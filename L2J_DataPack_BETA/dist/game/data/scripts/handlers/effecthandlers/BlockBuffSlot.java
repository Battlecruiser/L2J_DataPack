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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.AbnormalType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Block Buff Slot effect implementation.
 * @author Zoey76
 */
public class BlockBuffSlot extends L2Effect
{
	private final Set<AbnormalType> _blockBuffSlots;
	
	public BlockBuffSlot(Env env, EffectTemplate template)
	{
		super(env, template);
		
		String blockBuffSlots = template.hasParameters() ? template.getParameters().getString("slot", null) : null;
		if ((blockBuffSlots != null) && !blockBuffSlots.isEmpty())
		{
			_blockBuffSlots = new HashSet<>();
			for (String slot : blockBuffSlots.split(";"))
			{
				_blockBuffSlots.add(AbnormalType.getAbnormalType(slot));
			}
		}
		else
		{
			_blockBuffSlots = Collections.<AbnormalType> emptySet();
		}
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
	
	@Override
	public void onExit()
	{
		getEffected().getEffectList().removeBlockedBuffSlots(_blockBuffSlots);
	}
	
	@Override
	public boolean onStart()
	{
		if (_blockBuffSlots.isEmpty())
		{
			return false;
		}
		
		getEffected().getEffectList().addBlockedBuffSlots(_blockBuffSlots);
		
		return true;
	}
}
