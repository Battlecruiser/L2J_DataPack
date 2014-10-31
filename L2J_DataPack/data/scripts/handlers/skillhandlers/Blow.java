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

import net.sf.l2j.gameserver.model.olympiad.Olympiad;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.handler.ISkillHandler;
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2SummonInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Formulas;
import net.sf.l2j.gameserver.skills.Stats;
import net.sf.l2j.gameserver.skills.funcs.Func;
import net.sf.l2j.gameserver.templates.item.L2WeaponType;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;
import net.sf.l2j.gameserver.util.Util;

/**
 *
 * @author  Steuf
 */
public class Blow implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.BLOW
	};
	
	public final static byte FRONT = 50;
	public final static byte SIDE = 60;
	public final static byte BEHIND = 70;
	
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (activeChar.isAlikeDead())
			return;
		for (L2Character target: (L2Character[]) targets)
		{
			if (target.isAlikeDead())
				continue;
			
			// Check firstly if target dodges skill
			boolean skillIsEvaded = Formulas.calcPhysicalSkillEvasion(target, skill);
			
			byte _successChance = SIDE;
			
			if (activeChar.isBehindTarget())
				_successChance = BEHIND;
			else if (activeChar.isInFrontOfTarget())
				_successChance = FRONT;
			
			
			//If skill requires Crit or skill requires behind,
			//calculate chance based on DEX, Position and on self BUFF
			boolean success = true;
			if ((skill.getCondition() & L2Skill.COND_BEHIND) != 0)
				success = (_successChance == BEHIND);
			if ((skill.getCondition() & L2Skill.COND_CRIT) != 0)
				success = (success && Formulas.calcBlow(activeChar, target, _successChance));
			if (!skillIsEvaded && success)
			{
				if (skill.hasEffects())
				{
					if (target.reflectSkill(skill))
					{
						activeChar.stopSkillEffects(skill.getId());
						skill.getEffects(target, activeChar);
						SystemMessage sm = new SystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
						sm.addSkillName(skill);
						activeChar.sendPacket(sm);
					}
				}
				L2ItemInstance weapon = activeChar.getActiveWeaponInstance();
				boolean soul = (weapon != null && weapon.getChargedSoulshot() == L2ItemInstance.CHARGED_SOULSHOT && weapon.getItemType() == L2WeaponType.DAGGER);
				byte shld = Formulas.calcShldUse(activeChar, target);
				
				// Crit rate base crit rate for skill, modified with STR bonus
				boolean crit = false;
				if (Formulas.calcCrit(skill.getBaseCritRate() * 10 * Formulas.getSTRBonus(activeChar)))
					crit = true;
				double damage = (int) Formulas.calcBlowDamage(activeChar, target, skill, shld, soul);
				if (crit)
				{
					damage *= 2;
					// Vicious Stance is special after C5, and only for BLOW skills
					// Adds directly to damage
					L2Effect vicious = activeChar.getFirstEffect(312);
					if (vicious != null && damage > 1)
					{
						for (Func func : vicious.getStatFuncs())
						{
							Env env = new Env();
							env.player = activeChar;
							env.target = target;
							env.skill = skill;
							env.value = damage;
							func.calc(env);
							damage = (int) env.value;
						}
					}
				}
				
				if (soul)
					weapon.setChargedSoulshot(L2ItemInstance.CHARGED_NONE);
				if (skill.getDmgDirectlyToHP() && target instanceof L2PcInstance)
				{
					L2PcInstance player = (L2PcInstance) target;
					if (!player.isInvul())
					{   
						// Check and calculate transfered damage
			            L2Summon summon = player.getPet();
			            if (summon != null && summon instanceof L2SummonInstance && Util.checkIfInRange(900, player, summon, true))
			            {
			                int tDmg = (int)damage * (int)player.getStat().calcStat(Stats.TRANSFER_DAMAGE_PERCENT, 0, null, null) /100;

			                // Only transfer dmg up to current HP, it should not be killed
			                if (summon.getCurrentHp() < tDmg) tDmg = (int)summon.getCurrentHp() - 1;
			                if (tDmg > 0)
			                {
			                    summon.reduceCurrentHp(tDmg, activeChar ,skill);
			                    damage -= tDmg;
			                }
			            }
						if (damage >= player.getCurrentHp())
						{
							if (player.isInDuel())
								player.setCurrentHp(1);
							else
							{
								player.setCurrentHp(0);
								if (player.isInOlympiadMode())
								{
									player.abortAttack();
									player.abortCast();
									player.getStatus().stopHpMpRegeneration();
									player.setIsDead(true);
									player.setIsPendingRevive(true);
									if (player.getPet() != null)
										player.getPet().getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null);
				                }
								else
									player.doDie(activeChar);
							}
						}
						else
							player.setCurrentHp(player.getCurrentHp() - damage);
					}
	        		SystemMessage smsg = new SystemMessage(SystemMessageId.C1_RECEIVED_DAMAGE_OF_S3_FROM_C2);
	        		smsg.addPcName(player);
	        		smsg.addCharName(activeChar);
	        		smsg.addNumber((int)damage);
	        		player.sendPacket(smsg);
	        	}
	        	else
	        		target.reduceCurrentHp(damage, activeChar, skill);
				// Manage attack or cast break of the target (calculating rate, sending message...)
                if (!target.isRaid() && Formulas.calcAtkBreak(target, damage))
                {
                	target.breakAttack();
                	target.breakCast();
                }
				if(activeChar instanceof L2PcInstance)
				{
					L2PcInstance activePlayer = (L2PcInstance) activeChar; 
					activePlayer.sendPacket(new SystemMessage(SystemMessageId.C1_HAD_CRITICAL_HIT).addPcName(activePlayer));
					SystemMessage sm = new SystemMessage(SystemMessageId.YOU_DID_S1_DMG);
					sm.addNumber((int) damage);
					activePlayer.sendPacket(sm);
					if (activePlayer.isInOlympiadMode() &&
			        		target instanceof L2PcInstance &&
			        		((L2PcInstance)target).isInOlympiadMode() &&
			        		((L2PcInstance)target).getOlympiadGameId() == activePlayer.getOlympiadGameId())
			        {
			        	Olympiad.getInstance().notifyCompetitorDamage(activePlayer, (int) damage, activePlayer.getOlympiadGameId());
			        }
				}
			}
			
			// Sending system messages
			if (skillIsEvaded)
			{
				if (activeChar instanceof L2PcInstance)
				{
					SystemMessage sm = new SystemMessage(SystemMessageId.C1_DODGES_ATTACK);
					sm.addString(target.getName());
					((L2PcInstance) activeChar).sendPacket(sm);
				}
				if (target instanceof L2PcInstance)
				{
					SystemMessage sm = new SystemMessage(SystemMessageId.AVOIDED_C1_ATTACK);
					sm.addString(activeChar.getName());
					((L2PcInstance) target).sendPacket(sm);
				}
			}
			
			//Possibility of a lethal strike
			Formulas.calcLethalHit(activeChar, target, skill);
			
			L2Effect effect = activeChar.getFirstEffect(skill.getId());
			//Self Effect
			if (effect != null && effect.isSelfEffect())
				effect.exit();
			skill.getEffectsSelf(activeChar);
		}
	}
	
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
