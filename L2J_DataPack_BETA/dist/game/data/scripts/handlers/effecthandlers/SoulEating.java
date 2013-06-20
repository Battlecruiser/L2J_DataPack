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

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.events.listeners.iExperienceReceivedEventListener;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExSpawnEmitter;

/**
 * Soul Eating effect implementation.
 * @author UnAfraid
 */
public final class SoulEating extends L2Effect implements iExperienceReceivedEventListener
{
	private final int _expNeeded;
	
	public SoulEating(Env env, EffectTemplate template)
	{
		super(env, template);
		_expNeeded = template.getParameters().getInteger("expNeeded");
	}
	
	public SoulEating(Env env, L2Effect effect)
	{
		super(env, effect);
		_expNeeded = effect.getEffectTemplate().getParameters().getInteger("expNeeded");
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
	
	@Override
	public boolean onExperienceReceived(L2Playable playable, long exp)
	{
		final L2PcInstance player = getEffected().isPlayer() ? getEffected().getActingPlayer() : null;
		if ((player != null) && (exp >= _expNeeded))
		{
			final int maxSouls = (int) player.calcStat(Stats.MAX_SOULS, 0, null, null);
			if (player.getChargedSouls() >= maxSouls)
			{
				playable.sendPacket(SystemMessageId.SOUL_CANNOT_BE_ABSORBED_ANYMORE);
				return true;
			}
			
			player.increaseSouls(1);
			
			if ((player.getTarget() != null) && player.getTarget().isNpc())
			{
				final L2Npc npc = (L2Npc) playable.getTarget();
				player.broadcastPacket(new ExSpawnEmitter(player, npc), 500);
			}
		}
		return true;
	}
	
	@Override
	public void onExit()
	{
		if (getEffected().isPlayer())
		{
			getEffected().getEvents().unregisterListener(this);
		}
		super.onExit();
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected().isPlayer())
		{
			getEffected().getEvents().registerListener(this);
		}
		return super.onStart();
	}
}
