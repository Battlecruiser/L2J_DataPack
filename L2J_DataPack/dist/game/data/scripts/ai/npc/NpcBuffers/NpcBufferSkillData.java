/*
 * Copyright (C) 2004-2014 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.npc.NpcBuffers;

import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.skills.targets.AffectObject;
import com.l2jserver.gameserver.model.skills.targets.AffectScope;

/**
 * @author UnAfraid
 */
public class NpcBufferSkillData
{
	private final SkillHolder _skill;
	private final int _initialDelay;
	private final int _delay;
	private final AffectScope _affectScope;
	private final AffectObject _affectObject;
	
	public NpcBufferSkillData(StatsSet set)
	{
		_skill = new SkillHolder(set.getInt("id"), set.getInt("level"));
		_initialDelay = set.getInt("skillInitDelay", 0) * 1000;
		_delay = set.getInt("delay") * 1000;
		_affectScope = set.getEnum("affectScope", AffectScope.class);
		_affectObject = set.getEnum("affectObject", AffectObject.class);
	}
	
	public Skill getSkill()
	{
		return _skill.getSkill();
	}
	
	public int getInitialDelay()
	{
		return _initialDelay;
	}
	
	public int getDelay()
	{
		return _delay;
	}
	
	public AffectScope getAffectScope()
	{
		return _affectScope;
	}
	
	public AffectObject getAffectObject()
	{
		return _affectObject;
	}
}
