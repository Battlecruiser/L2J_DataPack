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

import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.idfactory.IdFactory;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2TrapInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Summon Trap effect implementation.
 * @author Zoey76
 */
public class SummonTrap extends L2Effect
{
	private final int _despawnTime;
	private final int _npcId;
	
	public SummonTrap(Env env, EffectTemplate template)
	{
		super(env, template);
		_despawnTime = template.getParameters().getInteger("despawnTime", 0);
		_npcId = template.getParameters().getInteger("npcId", 0);
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
		if ((getEffected() == null) || !getEffected().isPlayer() || getEffected().isAlikeDead() || getEffected().getActingPlayer().inObserverMode())
		{
			return false;
		}
		
		if (_npcId <= 0)
		{
			_log.warning(SummonTrap.class.getSimpleName() + ": Invalid NPC Id:" + _npcId + " in skill Id: " + getSkill().getId());
			return false;
		}
		
		final L2PcInstance player = getEffected().getActingPlayer();
		if (player.inObserverMode() || player.isMounted())
		{
			return false;
		}
		// Unsummon previous trap
		if (player.getTrap() != null)
		{
			player.getTrap().unSummon();
		}
		
		final L2NpcTemplate npcTemplate = NpcTable.getInstance().getTemplate(_npcId);
		if (npcTemplate == null)
		{
			_log.warning(SummonTrap.class.getSimpleName() + ": Spawn of the non-existing Trap Id: " + _npcId + " in skill Id:" + getSkill().getId());
			return false;
		}
		
		final L2TrapInstance trap = new L2TrapInstance(IdFactory.getInstance().getNextId(), npcTemplate, player, _despawnTime);
		trap.setCurrentHp(trap.getMaxHp());
		trap.setCurrentMp(trap.getMaxMp());
		trap.setIsInvul(true);
		trap.setHeading(player.getHeading());
		trap.spawnMe(player.getX(), player.getY(), player.getZ());
		player.setTrap(trap);
		return true;
	}
}
