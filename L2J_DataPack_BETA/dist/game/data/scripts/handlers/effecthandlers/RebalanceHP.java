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

import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.util.Util;

/**
 * Rebalance HP effect.
 * @author Adry_85, earendil
 */
public class RebalanceHP extends L2Effect
{
	public RebalanceHP(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.REBALANCE_HP;
	}
	
	@Override
	public boolean onStart()
	{
		if (!getEffector().isPlayer() || !getEffector().isInParty())
		{
			return false;
		}
		
		double fullHP = 0;
		double currentHPs = 0;
		final L2Party party = getEffector().getParty();
		for (L2PcInstance member : party.getMembers())
		{
			if (member.isDead() || !Util.checkIfInRange(getSkill().getSkillRadius(), getEffector(), member, true))
			{
				continue;
			}
			
			fullHP += member.getMaxHp();
			currentHPs += member.getCurrentHp();
		}
		
		double percentHP = currentHPs / fullHP;
		for (L2PcInstance member : party.getMembers())
		{
			if (member.isDead() || !Util.checkIfInRange(getSkill().getSkillRadius(), getEffector(), member, true))
			{
				continue;
			}
			
			double newHP = member.getMaxHp() * percentHP;
			if (newHP > member.getCurrentHp()) // The target gets healed
			{
				// The heal will be blocked if the current hp passes the limit
				if (member.getCurrentHp() > member.getMaxRecoverableHp())
				{
					newHP = member.getCurrentHp();
				}
				else if (newHP > member.getMaxRecoverableHp())
				{
					newHP = member.getMaxRecoverableHp();
				}
			}
			
			member.setCurrentHp(newHP);
			StatusUpdate su = new StatusUpdate(member);
			su.addAttribute(StatusUpdate.CUR_HP, (int) member.getCurrentHp());
			member.sendPacket(su);
		}
		return true;
	}
}
