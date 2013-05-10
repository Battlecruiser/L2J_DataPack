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
import com.l2jserver.gameserver.network.serverpackets.PetItemList;

/**
 * Restoration effect implementation.
 * @author Zoey76
 */
public class Restoration extends L2Effect
{
	private final int _itemId;
	private final int _itemCount;
	
	public Restoration(Env env, EffectTemplate template)
	{
		super(env, template);
		_itemId = template.getParameters().getInteger("itemId", 0);
		_itemCount = template.getParameters().getInteger("itemCount", 0);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
	
	@Override
	public boolean onStart()
	{
		if ((getEffected() == null) || !getEffected().isPlayable())
		{
			return false;
		}
		
		if ((_itemId <= 0) || (_itemCount <= 0))
		{
			getEffected().sendPacket(SystemMessageId.NOTHING_INSIDE_THAT);
			_log.warning(Restoration.class.getSimpleName() + " effect with wrong item Id/count: " + _itemId + "/" + _itemCount + "!");
			return false;
		}
		
		if (getEffected().isPlayer())
		{
			getEffected().getActingPlayer().addItem("Skill", _itemId, _itemCount, getEffector(), true);
		}
		else if (getEffected().isPet())
		{
			getEffected().getInventory().addItem("Skill", _itemId, _itemCount, getEffected().getActingPlayer(), getEffector());
			getEffected().getActingPlayer().sendPacket(new PetItemList(getEffected().getInventory().getItems()));
		}
		return true;
	}
}
