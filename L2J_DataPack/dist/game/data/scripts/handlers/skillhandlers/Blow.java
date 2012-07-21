/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.skillhandlers;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.stats.BaseStats;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author  Steuf
 */
public class Blow implements ISkillHandler
{
	private static final Logger _logDamage = Logger.getLogger("damage");
	
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.BLOW
	};

	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (activeChar.isAlikeDead())
			return;
		
		for (L2Character target: (L2Character[]) targets)
		{
			if (target.isAlikeDead())
				continue;
			
			// Check firstly if target dodges skill
			final boolean skillIsEvaded = Formulas.calcPhysicalSkillEvasion(target, skill);
			
			if (!skillIsEvaded && Formulas.calcBlowSuccess(activeChar, target, skill))
			{
				final byte reflect = Formulas.calcSkillReflect(target, skill);
				
				if (skill.hasEffects())
				{
					if (reflect == Formulas.SKILL_REFLECT_SUCCEED)
					{
						activeChar.stopSkillEffects(skill.getId());
						skill.getEffects(target, activeChar);
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
						sm.addSkillName(skill);
						activeChar.sendPacket(sm);
					}
					else
					{
						final byte shld = Formulas.calcShldUse(activeChar, target, skill);
						target.stopSkillEffects(skill.getId());
						if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, false, false, true))
						{
							skill.getEffects(activeChar, target, new Env(shld, false, false, false));
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
							sm.addSkillName(skill);
							target.sendPacket(sm);
						}
						else
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
					}
				}
				
				boolean soul = activeChar.isSoulshotCharged(skill);
				byte shld = Formulas.calcShldUse(activeChar, target, skill);
				
				double damage = skill.isStaticDamage() ? skill.getPower() : (int) Formulas.calcBlowDamage(activeChar, target, skill, shld, soul);
				if (!skill.isStaticDamage() && skill.getMaxSoulConsumeCount() > 0 && activeChar.isPlayer())
				{
					switch (activeChar.getActingPlayer().getSouls())
					{
						case 0:
							break;
						case 1:
							damage *= 1.10;
							break;
						case 2:
							damage *= 1.12;
							break;
						case 3:
							damage *= 1.15;
							break;
						case 4:
							damage *= 1.18;
							break;
						default:
							damage *= 1.20;
							break;
					}
				}
				
				// Crit rate base crit rate for skill, modified with STR bonus
				if (!skill.isStaticDamage() && Formulas.calcCrit(skill.getBaseCritRate() * 10 * BaseStats.STR.calcBonus(activeChar), true, target))
					damage *= 2;
				
				if (Config.LOG_GAME_DAMAGE
						&& activeChar.isPlayable()
						&& damage > Config.LOG_GAME_DAMAGE_THRESHOLD)
				{
					LogRecord record = new LogRecord(Level.INFO, "");
					record.setParameters(new Object[]{activeChar, " did damage ", (int)damage, skill, " to ", target});
					record.setLoggerName("pdam");
					_logDamage.log(record);
				}
				
				target.reduceCurrentHp(damage, activeChar, skill);
				
				// vengeance reflected damage
				if ((reflect & Formulas.SKILL_REFLECT_VENGEANCE) != 0)
				{
					if (target.isPlayer())
					{
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.COUNTERED_C1_ATTACK);
						sm.addCharName(activeChar);
						target.sendPacket(sm);
					}
					if (activeChar.isPlayer())
					{
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_PERFORMING_COUNTERATTACK);
						sm.addCharName(target);
						activeChar.sendPacket(sm);
					}
					// Formula from Diego Vargas post: http://www.l2guru.com/forum/showthread.php?p=3122630
					// 1189 x Your PATK / PDEF of target
					double vegdamage = (1189 * target.getPAtk(activeChar) / activeChar.getPDef(target));
					activeChar.reduceCurrentHp(vegdamage, target, skill);
				}
				
				// Manage attack or cast break of the target (calculating rate, sending message...)
				if (!target.isRaid() && Formulas.calcAtkBreak(target, damage))
				{
					target.breakAttack();
					target.breakCast();
				}

				if(activeChar.isPlayer())
				{
					L2PcInstance activePlayer = activeChar.getActingPlayer();
					
					activePlayer.sendDamageMessage(target, (int)damage, false, true, false);
				}
			}
			
			// Sending system messages
			if (skillIsEvaded)
			{
				if (activeChar.isPlayer())
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_DODGES_ATTACK);
					sm.addString(target.getName());
					activeChar.getActingPlayer().sendPacket(sm);
				}
				if (target.isPlayer())
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.AVOIDED_C1_ATTACK);
					sm.addString(activeChar.getName());
					target.getActingPlayer().sendPacket(sm);
				}
			}
			
			//Possibility of a lethal strike
			Formulas.calcLethalHit(activeChar, target, skill);
			
			//Self Effect
			if (skill.hasSelfEffects())
			{
				final L2Effect effect = activeChar.getFirstEffect(skill.getId());
				if (effect != null && effect.isSelfEffect())
					effect.exit();
				skill.getEffectsSelf(activeChar);
			}
			
			activeChar.ssUncharge(skill);
		}
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
