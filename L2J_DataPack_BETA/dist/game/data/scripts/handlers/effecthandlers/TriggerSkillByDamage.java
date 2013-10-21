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

import java.util.logging.Logger;

import com.l2jserver.gameserver.enums.InstanceType;
import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.handler.TargetHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.events.listeners.IDamageReceivedEventListener;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.util.Rnd;

/**
 * Trigger Skill By Damage effect implementation.
 * @author UnAfraid
 */
public class TriggerSkillByDamage extends L2Effect implements IDamageReceivedEventListener
{
	private static final Logger _log = Logger.getLogger(TriggerSkillByDamage.class.getName());
	
	private final int _minAttackerLevel;
	private final int _maxAttackerLevel;
	private final int _minDamage;
	private final int _chance;
	private final SkillHolder _skill;
	private final L2TargetType _targetType;
	private final InstanceType _attackerType;
	
	public TriggerSkillByDamage(Env env, EffectTemplate template)
	{
		super(env, template);
		_minAttackerLevel = template.getParameters().getInt("minAttackerLevel", 1);
		_maxAttackerLevel = template.getParameters().getInt("maxAttackerLevel", 100);
		_minDamage = template.getParameters().getInt("minDamage", 1);
		_chance = template.getParameters().getInt("chance", 100);
		_skill = new SkillHolder(template.getParameters().getInt("skillId"), template.getParameters().getInt("skillLevel", 1));
		_targetType = template.getParameters().getEnum("targetType", L2TargetType.class, L2TargetType.SELF);
		_attackerType = template.getParameters().getEnum("attackerType", InstanceType.class, InstanceType.L2Character);
	}
	
	public TriggerSkillByDamage(Env env, L2Effect effect)
	{
		super(env, effect);
		_minAttackerLevel = effect.getEffectTemplate().getParameters().getInt("minAttackerLevel", 1);
		_maxAttackerLevel = effect.getEffectTemplate().getParameters().getInt("maxAttackerLevel", 100);
		_minDamage = effect.getEffectTemplate().getParameters().getInt("minDamage", 1);
		_chance = effect.getEffectTemplate().getParameters().getInt("chance", 100);
		_skill = new SkillHolder(effect.getEffectTemplate().getParameters().getInt("skillId"), effect.getEffectTemplate().getParameters().getInt("skillLevel", 1));
		_targetType = effect.getEffectTemplate().getParameters().getEnum("targetType", L2TargetType.class, L2TargetType.SELF);
		_attackerType = effect.getEffectTemplate().getParameters().getEnum("attackerType", InstanceType.class, InstanceType.L2Character);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NONE;
	}
	
	@Override
	public void onDamageReceivedEvent(L2Character attacker, L2Character target, double damage, L2Skill skill, boolean crit)
	{
		final ITargetTypeHandler targetHandler = TargetHandler.getInstance().getHandler(_targetType);
		if (targetHandler == null)
		{
			_log.warning("Handler for target type: " + _targetType + " does not exist.");
			return;
		}
		
		if (attacker == target)
		{
			return;
		}
		
		if ((attacker.getLevel() < _minAttackerLevel) || (attacker.getLevel() > _maxAttackerLevel))
		{
			return;
		}
		
		if ((damage < _minDamage) || (Rnd.get(100) > _chance) || !attacker.getInstanceType().isType(_attackerType))
		{
			return;
		}
		
		final L2Skill triggerSkill = _skill.getSkill();
		final L2Object[] targets = targetHandler.getTargetList(triggerSkill, target, false, attacker);
		for (L2Object triggerTarget : targets)
		{
			if ((triggerTarget == null) || !triggerTarget.isCharacter())
			{
				continue;
			}
			
			final L2Character targetChar = (L2Character) triggerTarget;
			if (!targetChar.isInvul())
			{
				target.makeTriggerCast(triggerSkill, targetChar);
			}
		}
	}
	
	@Override
	public void onExit()
	{
		if ((_chance == 0) || (_skill.getSkillLvl() == 0))
		{
			return;
		}
		
		getEffected().getEvents().unregisterListener(this);
		super.onExit();
	}
	
	@Override
	public boolean onStart()
	{
		if ((_chance == 0) || (_skill.getSkillLvl() == 0))
		{
			return false;
		}
		
		getEffected().getEvents().registerListener(this);
		return super.onStart();
	}
}
