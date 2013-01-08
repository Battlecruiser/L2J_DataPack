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
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Class handling the Mana damage skill
 * @author slyce
 */
public class Manadam implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.MANADAM
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (activeChar.isAlikeDead())
		{
			return;
		}
		
		boolean ss = skill.useSoulShot() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		boolean sps = skill.useSpiritShot() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
		boolean bss = skill.useSpiritShot() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
		
		for (L2Character target : (L2Character[]) targets)
		{
			if (Formulas.calcSkillReflect(target, skill) == Formulas.SKILL_REFLECT_SUCCEED)
			{
				target = activeChar;
			}
			
			boolean acted = Formulas.calcMagicAffected(activeChar, target, skill);
			if (target.isInvul() || !acted)
			{
				activeChar.sendPacket(SystemMessageId.MISSED_TARGET);
			}
			else
			{
				if (skill.hasEffects())
				{
					byte shld = Formulas.calcShldUse(activeChar, target, skill);
					target.stopSkillEffects(skill.getId());
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					}
					else
					{
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
						sm.addCharName(target);
						sm.addSkillName(skill);
						activeChar.sendPacket(sm);
					}
				}
				
				double damage = skill.isStaticDamage() ? skill.getPower() : Formulas.calcManaDam(activeChar, target, skill, ss, bss);
				
				if (!skill.isStaticDamage() && Formulas.calcMCrit(activeChar.getMCriticalHit(target, skill)))
				{
					damage *= 3.;
					activeChar.sendPacket(SystemMessageId.CRITICAL_HIT_MAGIC);
				}
				
				double mp = (damage > target.getCurrentMp() ? target.getCurrentMp() : damage);
				target.reduceCurrentMp(mp);
				if (damage > 0)
				{
					target.stopEffectsOnDamage(true);
				}
				
				if (target.isPlayer())
				{
					StatusUpdate sump = new StatusUpdate(target);
					sump.addAttribute(StatusUpdate.CUR_MP, (int) target.getCurrentMp());
					// [L2J_JP EDIT START - TSL]
					target.sendPacket(sump);
					
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S2_MP_HAS_BEEN_DRAINED_BY_C1);
					sm.addCharName(activeChar);
					sm.addNumber((int) mp);
					target.sendPacket(sm);
				}
				
				if (activeChar.isPlayer())
				{
					SystemMessage sm2 = SystemMessage.getSystemMessage(SystemMessageId.YOUR_OPPONENTS_MP_WAS_REDUCED_BY_S1);
					sm2.addNumber((int) mp);
					activeChar.sendPacket(sm2);
				}
				// [L2J_JP EDIT END - TSL]
			}
		}
		
		if (skill.hasSelfEffects())
		{
			L2Effect effect = activeChar.getFirstEffect(skill.getId());
			if (effect != null && effect.isSelfEffect())
			{
				// Replace old effect with new one.
				effect.exit();
			}
			// cast self effect if any
			skill.getEffectsSelf(activeChar);
		}
		
		activeChar.setChargedShot(bss ? ShotType.BLESSED_SPIRITSHOTS : ShotType.SPIRITSHOTS, false);
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
