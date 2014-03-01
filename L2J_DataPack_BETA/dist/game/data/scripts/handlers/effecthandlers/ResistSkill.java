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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Resist Skill effect implementaion.
 * @author UnAfraid
 */
public final class ResistSkill extends AbstractEffect
{
	private final List<SkillHolder> _skills = new ArrayList<>();
	
	public ResistSkill(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
		
		if (params != null)
		{
			for (int i = 1;; i++)
			{
				int skillId = params.getInt("skillId" + i, 0);
				int skillLvl = params.getInt("skillLvl" + i, 0);
				if (skillId == 0)
				{
					break;
				}
				_skills.add(new SkillHolder(skillId, skillLvl));
			}
		}
		if (_skills.isEmpty())
		{
			throw new IllegalArgumentException(getClass().getSimpleName() + ": Without parameters!");
		}
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		final L2Character effected = info.getEffected();
		for (SkillHolder holder : _skills)
		{
			effected.addInvulAgainst(holder);
			effected.sendDebugMessage("Applying invul against " + holder.getSkill());
		}
	}
	
	@Override
	public void onExit(BuffInfo info)
	{
		final L2Character effected = info.getEffected();
		for (SkillHolder holder : _skills)
		{
			info.getEffected().removeInvulAgainst(holder);
			effected.sendDebugMessage("Removing invul against " + holder.getSkill());
		}
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.BUFF;
	}
}
