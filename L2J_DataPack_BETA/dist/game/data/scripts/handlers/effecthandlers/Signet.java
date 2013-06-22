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

import java.util.ArrayList;
import java.util.List;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.l2skills.L2SkillSignet;
import com.l2jserver.gameserver.model.skills.l2skills.L2SkillSignetCasttime;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.zone.ZoneId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;

/**
 * Signet effect implementation.
 * @author Forsaiken, Sami
 */
public class Signet extends L2Effect
{
	private L2Skill _skill;
	private boolean _srcInArena;
	
	public Signet(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.SIGNET_EFFECT;
	}
	
	@Override
	public boolean onActionTime()
	{
		if (_skill == null)
		{
			return false;
		}
		
		int mpConsume = _skill.getMpConsume();
		if (mpConsume > getEffector().getCurrentMp())
		{
			getEffector().sendPacket(SystemMessageId.SKILL_REMOVED_DUE_LACK_MP);
			return false;
		}
		getEffector().reduceCurrentMp(mpConsume);
		
		List<L2Character> targets = new ArrayList<>();
		for (L2Character cha : getEffected().getKnownList().getKnownCharactersInRadius(getSkill().getAffectRange()))
		{
			if (cha == null)
			{
				continue;
			}
			
			if (_skill.isBad() && !L2Skill.checkForAreaOffensiveSkills(getEffector(), cha, _skill, _srcInArena))
			{
				continue;
			}
			
			getEffected().broadcastPacket(new MagicSkillUse(getEffected(), cha, _skill.getId(), _skill.getLevel(), 0, 0));
			targets.add(cha);
		}
		
		if (!targets.isEmpty())
		{
			getEffector().callSkill(_skill, targets.toArray(new L2Character[targets.size()]));
		}
		return false;
	}
	
	@Override
	public void onExit()
	{
		if (getEffected() != null)
		{
			getEffected().deleteMe();
		}
	}
	
	@Override
	public boolean onStart()
	{
		if (getSkill() instanceof L2SkillSignet)
		{
			_skill = SkillTable.getInstance().getInfo(getSkill().getEffectId(), getSkill().getLevel());
		}
		else if (getSkill() instanceof L2SkillSignetCasttime)
		{
			_skill = SkillTable.getInstance().getInfo(getSkill().getEffectId(), getSkill().getLevel());
		}
		_srcInArena = (getEffector().isInsideZone(ZoneId.PVP) && !getEffector().isInsideZone(ZoneId.SIEGE));
		return true;
	}
}
