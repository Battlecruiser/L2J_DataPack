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
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Effect;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.BaseStats;
import com.l2jserver.gameserver.skills.Env;
import com.l2jserver.gameserver.skills.Formulas;
import com.l2jserver.gameserver.templates.item.L2WeaponType;
import com.l2jserver.gameserver.templates.skills.L2SkillType;


/**
 * This class ...
 *
 * @version $Revision: 1.1.2.7.2.16 $ $Date: 2005/04/06 16:13:49 $
 */

public class Pdam implements ISkillHandler
{
	private static final Logger _log = Logger.getLogger(Pdam.class.getName());
	private static final Logger _logDamage = Logger.getLogger("damage");
	
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.PDAM, L2SkillType.FATAL
	};
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.ISkillHandler#useSkill(com.l2jserver.gameserver.model.actor.L2Character, com.l2jserver.gameserver.model.L2Skill, com.l2jserver.gameserver.model.L2Object[])
	 */
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (activeChar.isAlikeDead())
			return;
		
		int damage = 0;
		
		if (Config.DEBUG)
		{
			_log.fine("Begin Skill processing in Pdam.java " + skill.getSkillType());
		}
		
		L2ItemInstance weapon = activeChar.getActiveWeaponInstance();
		boolean soul = (weapon != null && weapon.getChargedSoulshot() == L2ItemInstance.CHARGED_SOULSHOT && weapon.getItemType() != L2WeaponType.DAGGER);
		
		// If there is no weapon equipped, check for an active summon.
		if (weapon == null && activeChar instanceof L2Summon)
		{
			L2Summon activeSummon = (L2Summon) activeChar;
			if (activeSummon.getChargedSoulShot() == L2ItemInstance.CHARGED_SOULSHOT)
			{
				soul = true;
				activeSummon.setChargedSoulShot(L2ItemInstance.CHARGED_NONE);
			}
		}
		
		for (L2Character target: (L2Character[]) targets)
		{
			
			if (activeChar instanceof L2PcInstance && target instanceof L2PcInstance && ((L2PcInstance)target).isFakeDeath())
			{
				target.stopFakeDeath(null);
			}
			else if (target.isDead())
				continue;
			
			final boolean dual = activeChar.isUsingDualWeapon();
			final byte shld = Formulas.calcShldUse(activeChar, target, skill);
			// PDAM critical chance not affected by buffs, only by STR. Only some skills are meant to crit.
			boolean crit = false;
			if (skill.getBaseCritRate() > 0)
				crit = Formulas.calcCrit(skill.getBaseCritRate() * 10 * BaseStats.STR.calcBonus(activeChar), target);
			
			
			if (!crit && (skill.getCondition() & L2Skill.COND_CRIT) != 0)
				damage = 0;
			else
				damage = (int) Formulas.calcPhysDam(activeChar, target, skill, shld, false, dual, soul);
			if (skill.getMaxSoulConsumeCount() > 0 && activeChar instanceof L2PcInstance)
			{
				switch (((L2PcInstance) activeChar).getSouls())
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
				if (damage > 0)
				{
					activeChar.sendDamageMessage(target, damage, false, crit, false);
				
					if (skill.hasEffects())
					{
						if ((reflect & Formulas.SKILL_REFLECT_SUCCEED) != 0)
						{
							activeChar.stopSkillEffects(skill.getId());
							skill.getEffects(target, activeChar);
							SystemMessage sm = new SystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
						else
						{
							// activate attacked effects, if any
							target.stopSkillEffects(skill.getId());
							if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, false, false, true))
							{
								skill.getEffects(activeChar, target, new Env(shld, false, false, false));
							
								SystemMessage sm = new SystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
								sm.addSkillName(skill);
								target.sendPacket(sm);
							}
							else
							{
								SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
								sm.addCharName(target);
								sm.addSkillName(skill);
								activeChar.sendPacket(sm);
							}
						}
					
						if (Config.LOG_GAME_DAMAGE
								&& activeChar instanceof L2Playable
								&& damage > Config.LOG_GAME_DAMAGE_THRESHOLD)
						{
							LogRecord record = new LogRecord(Level.INFO, "");
							record.setParameters(new Object[]{activeChar, " did damage ", damage, skill, " to ", target});
							record.setLoggerName("pdam");
							_logDamage.log(record);
						}
					}
				
					// Possibility of a lethal strike
					final boolean lethal = Formulas.calcLethalHit(activeChar, target, skill);
				
					// Make damage directly to HP
					if (!lethal && skill.getDmgDirectlyToHP())
					{
						final L2Character[] ts = {target, activeChar};
						
						/*
						 * This loop iterates over previous array but, if skill damage is not reflected
						 * it stops on first iteration (target) and misses activeChar
						 */
						for (L2Character targ : ts)
						{
							if (targ instanceof L2PcInstance)
							{
								L2PcInstance player = (L2PcInstance) targ;
								if (!player.isInvul())
								{
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
													player.getPet().getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE,null);
											}
											else
												player.doDie(activeChar);
										}
									}
									else
										player.setCurrentHp(player.getCurrentHp()- damage);
								}

								SystemMessage smsg = new SystemMessage(SystemMessageId.C1_RECEIVED_DAMAGE_OF_S3_FROM_C2);
								smsg.addPcName(player);
								smsg.addCharName(activeChar);
								smsg.addNumber(damage);
								player.sendPacket(smsg);

							}
							else
								target.reduceCurrentHp(damage, activeChar, skill);
							
							if ((reflect & Formulas.SKILL_REFLECT_VENGEANCE) == 0) // stop if no vengeance, so only target will be effected
								break;
						}
					}
					else
					{
						target.reduceCurrentHp(damage, activeChar, skill);
						
						// vengeance reflected damage
						if ((reflect & Formulas.SKILL_REFLECT_VENGEANCE) != 0)
							activeChar.reduceCurrentHp(damage, target, skill);
					}
				
				}
				else // No - damage
				{
					activeChar.sendPacket(new SystemMessage(SystemMessageId.ATTACK_FAILED));
				}
			}
			else
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
				
				// Possibility of a lethal strike despite skill is evaded
				Formulas.calcLethalHit(activeChar, target, skill);
			}
			
			if (activeChar instanceof L2PcInstance)
			{
				L2Skill soulmastery = SkillTable.getInstance().getInfo(467, ((L2PcInstance) activeChar).getSkillLevel(467));
				if (soulmastery != null)
				{
					if (((L2PcInstance) activeChar).getSouls() < soulmastery.getNumSouls())
					{
						int count = 0;
					
						if (((L2PcInstance) activeChar).getSouls() + skill.getNumSouls() <= soulmastery.getNumSouls())
							count = skill.getNumSouls();
						else
							count = soulmastery.getNumSouls() - ((L2PcInstance) activeChar).getSouls();
						((L2PcInstance) activeChar).increaseSouls(count);
					}
					else
					{
						SystemMessage sm = new SystemMessage(SystemMessageId.SOUL_CANNOT_BE_INCREASED_ANYMORE);
						((L2PcInstance) activeChar).sendPacket(sm);
					}
				}
			}
			//self Effect :]
			L2Effect effect = activeChar.getFirstEffect(skill.getId());
			if (effect != null && effect.isSelfEffect())
			{
				//Replace old effect with new one.
				effect.exit();
			}
			skill.getEffectsSelf(activeChar);
			
			if (skill.isSuicideAttack())
				activeChar.doDie(activeChar);
		}
		
		if (soul && weapon != null)
			weapon.setChargedSoulshot(L2ItemInstance.CHARGED_NONE);
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.ISkillHandler#getSkillIds()
	 */
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
