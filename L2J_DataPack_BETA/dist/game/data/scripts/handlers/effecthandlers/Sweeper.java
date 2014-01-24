/*
 * Copyright (C) 2004-2014 L2J DataPack
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

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Sweeper effect implementation.
 * @author Zoey76
 */
public final class Sweeper extends AbstractEffect
{
	private static final int MAX_SWEEPER_TIME = 15000;
	
	public Sweeper(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if ((info.getEffector() == null) || (info.getEffected() == null) || !info.getEffector().isPlayer() || !info.getEffected().isL2Attackable())
		{
			return;
		}
		
		final L2PcInstance player = info.getEffector().getActingPlayer();
		final L2Attackable monster = (L2Attackable) info.getEffected();
		if (!monster.checkSpoilOwner(player, false))
		{
			return;
		}
		
		if (monster.isOldCorpse(player, MAX_SWEEPER_TIME, false))
		{
			return;
		}
		
		if (!player.getInventory().checkInventorySlotsAndWeight(monster.getSpoilLootItems(), false, false))
		{
			return;
		}
		
		final ItemHolder[] items = monster.takeSweep();
		if ((items == null) || (items.length == 0))
		{
			return;
		}
		
		for (ItemHolder item : items)
		{
			if (player.isInParty())
			{
				player.getParty().distributeItem(player, item, true, monster);
			}
			else
			{
				player.addItem("Sweeper", item, info.getEffected(), true);
			}
		}
	}
}
