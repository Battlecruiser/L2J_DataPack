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
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.stats.BaseStats;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class Pdam implements ISkillHandler
{
	private static final Logger _log = Logger.getLogger(Pdam.class.getName());
	private static final Logger _logDamage = Logger.getLogger("damage");
	
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.PDAM, L2SkillType.FATAL
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (activeChar.isAlikeDead())
			return;
		
		int damage = 0;
		
		if (Config.DEBUG)
		{
			_log.fine("Begin Skill processing in Pdam.java " + skill.getSkillType());
		}
		
		boolean soul = activeChar.isSoulshotCharged(skill);
		
		for (L2Character target: (L2Character[]) targets)
		{
			
			if (activeChar.isPlayer() && target.isPlayer() && target.getActingPlayer().isFakeDeath())
			{
				target.stopFakeDeath(true);
			}
			else if (target.isDead())
				continue;
			
			final boolean dual = activeChar.isUsingDualWeapon();
			final byte shld = Formulas.calcShldUse(activeChar, target, skill);
			// PDAM critical chance not affected by buffs, only by STR. Only some skills are meant to crit.
			boolean crit = false;
			if (!skill.isStaticDamage() && skill.getBaseCritRate() > 0)
				crit = Formulas.calcCrit(skill.getBaseCritRate() * 10 * BaseStats.STR.calcBonus(activeChar), true, target);
			
			
			if (!crit && (skill.getCondition() & L2Skill.COND_CRIT) != 0)
				damage = 0;
			else
				damage = skill.isStaticDamage() ? (int)skill.getPower() : (int) Formulas.calcPhysDam(activeChar, target, skill, shld, false, dual, soul);
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
			if (crit)
				damage *= 2; // PDAM Critical damage always 2x and not affected by buffs
			
			
			final boolean skillIsEvaded = Formulas.calcPhysicalSkillEvasion(target, skill);
			final byte reflect = Formulas.calcSkillReflect(target, skill);
			
			if (!skillIsEvaded)
			{
				if (skill.hasEffects())
				{
					L2Effect[] effects;
					if ((reflect & Formulas.SKILL_REFLECT_SUCCEED) != 0)
					{
						activeChar.stopSkillEffects(skill.getId());
						effects = skill.getEffects(target, activeChar);
						if (effects != null && effects.length > 0)
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
					}
					else
					{
						// activate attacked effects, if any
						target.stopSkillEffects(skill.getId());
						effects = skill.getEffects(activeChar, target, new Env(shld, false, false, false));
						if (effects != null && effects.length > 0)
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
							sm.addSkillName(skill);
							target.sendPacket(sm);
						}
					}
				}

				if (damage > 0)
				{
					activeChar.sendDamageMessage(target, damage, false, crit, false);
					
					if (Config.LOG_GAME_DAMAGE
							&& activeChar.isPlayable()
							&& damage > Config.LOG_GAME_DAMAGE_THRESHOLD)
					{
						LogRecord record = new LogRecord(Level.INFO, "");
						record.setParameters(new Object[]{activeChar, " did damage ", damage, skill, " to ", target});
						record.setLoggerName("pdam");
						_logDamage.log(record);
					}

					// Possibility of a lethal strike
					Formulas.calcLethalHit(activeChar, target, skill);

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
						// Formula from Diego post, 700 from rpg tests
						double vegdamage = (700 * target.getPAtk(activeChar) / activeChar.getPDef(target));
						activeChar.reduceCurrentHp(vegdamage, target, skill);
					}
				}
				else // No damage
					activeChar.sendPacket(SystemMessageId.ATTACK_FAILED);
			}
			else
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
				
				// Possibility of a lethal strike despite skill is evaded
				Formulas.calcLethalHit(activeChar, target, skill);
			}
			
			if (activeChar.isPlayer())
			{
				int soulMasteryLevel = activeChar.getSkillLevel(467);
				if (soulMasteryLevel > 0)
				{
					L2Skill soulmastery = SkillTable.getInstance().getInfo(467, soulMasteryLevel);
					if (soulmastery != null)
					{
						if (activeChar.getActingPlayer().getSouls() < soulmastery.getNumSouls())
						{
							int count = 0;
							
							if (activeChar.getActingPlayer().getSouls() + skill.getNumSouls() <= soulmastery.getNumSouls())
								count = skill.getNumSouls();
							else
								count = soulmastery.getNumSouls() - activeChar.getActingPlayer().getSouls();
							activeChar.getActingPlayer().increaseSouls(count);
						}
						else
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.SOUL_CANNOT_BE_INCREASED_ANYMORE);
							activeChar.getActingPlayer().sendPacket(sm);
						}
					}
				}
			}
		}
		
		//self Effect :]
		if (skill.hasSelfEffects())
		{
			final L2Effect effect = activeChar.getFirstEffect(skill.getId());
			if (effect != null && effect.isSelfEffect())
			{
				//Replace old effect with new one.
				effect.exit();
			}
			skill.getEffectsSelf(activeChar);
		}
		
		activeChar.ssUncharge(skill);
		
		if (skill.isSuicideAttack())
			activeChar.doDie(activeChar);
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
