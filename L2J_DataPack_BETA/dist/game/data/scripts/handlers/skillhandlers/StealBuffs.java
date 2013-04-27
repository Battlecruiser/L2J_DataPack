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
package handlers.skillhandlers;

import java.util.List;
import java.util.logging.Level;

import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.ShotType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class StealBuffs implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.STEAL_BUFF
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		L2Character target;
		L2Effect effect;
		for (L2Object obj : targets)
		{
			if (!(obj instanceof L2Character))
			{
				continue;
			}
			target = (L2Character) obj;
			
			if (target.isDead())
			{
				continue;
			}
			
			if (!target.isPlayer())
			{
				continue;
			}
			
			Env env;
			final List<L2Effect> toSteal = Formulas.calcCancelStealEffects(activeChar, target, skill, skill.getPower());
			
			if (toSteal.size() == 0)
			{
				continue;
			}
			
			// stealing effects
			for (L2Effect eff : toSteal)
			{
				env = new Env();
				env.setCharacter(target);
				env.setTarget(activeChar);
				env.setSkill(eff.getSkill());
				try
				{
					effect = eff.getEffectTemplate().getStolenEffect(env, eff);
					if (effect != null)
					{
						effect.scheduleEffect();
						if (effect.isIconDisplay() && activeChar.isPlayer())
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
							sm.addSkillName(effect);
							activeChar.sendPacket(sm);
						}
					}
					// Finishing stolen effect
					eff.exit();
				}
				catch (RuntimeException e)
				{
					_log.log(Level.WARNING, "Cannot steal effect: " + eff + " Stealer: " + activeChar + " Stolen: " + target, e);
				}
			}
		}
		
		if (skill.hasSelfEffects())
		{
			// Applying self-effects
			effect = activeChar.getFirstEffect(skill.getId());
			if ((effect != null) && effect.isSelfEffect())
			{
				// Replace old effect with new one.
				effect.exit();
			}
			skill.getEffectsSelf(activeChar);
		}
		
		activeChar.setChargedShot(activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS) ? ShotType.BLESSED_SPIRITSHOTS : ShotType.SPIRITSHOTS, false);
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
