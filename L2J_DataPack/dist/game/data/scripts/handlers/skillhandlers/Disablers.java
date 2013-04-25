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

import java.util.logging.Logger;

import com.l2jserver.gameserver.ai.CtrlEvent;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.ai.L2AttackableAI;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.ShotType;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeSummonInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * This Handles Disabler skills
 * @author _drunk_
 */
public class Disablers implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.STUN,
		L2SkillType.ROOT,
		L2SkillType.SLEEP,
		L2SkillType.CONFUSION,
		L2SkillType.AGGDAMAGE,
		L2SkillType.AGGREDUCE,
		L2SkillType.AGGREDUCE_CHAR,
		L2SkillType.AGGREMOVE,
		L2SkillType.MUTE,
		L2SkillType.CONFUSE_MOB_ONLY,
		L2SkillType.PARALYZE,
		L2SkillType.ERASE,
		L2SkillType.BETRAY,
		L2SkillType.DISARM
	};
	
	protected static final Logger _log = Logger.getLogger(L2Skill.class.getName());
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		L2SkillType type = skill.getSkillType();
		
		byte shld = 0;
		boolean ss = skill.useSoulShot() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		boolean sps = skill.useSpiritShot() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
		boolean bss = skill.useSpiritShot() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
		
		for (L2Object obj : targets)
		{
			if (!(obj instanceof L2Character))
			{
				continue;
			}
			L2Character target = (L2Character) obj;
			if (target.isDead() || (target.isInvul() && !target.isParalyzed()))
			{
				continue;
			}
			
			shld = Formulas.calcShldUse(activeChar, target, skill);
			
			switch (type)
			{
				case BETRAY:
				{
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
					break;
				}
				case ROOT:
				case DISARM:
				case STUN:
				case SLEEP:
				case PARALYZE:
				{
					if (Formulas.calcSkillReflect(target, skill) == Formulas.SKILL_REFLECT_SUCCEED)
					{
						target = activeChar;
					}
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					}
					else
					{
						if (activeChar.isPlayer())
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
					}
					break;
				}
				case CONFUSION:
				case MUTE:
				{
					if (Formulas.calcSkillReflect(target, skill) == Formulas.SKILL_REFLECT_SUCCEED)
					{
						target = activeChar;
					}
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						// stop same type effect if available
						L2Effect[] effects = target.getAllEffects();
						for (L2Effect e : effects)
						{
							if ((e != null) && (e.getSkill() != null) && (e.getSkill().getSkillType() == type))
							{
								e.exit();
							}
						}
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					}
					else
					{
						if (activeChar.isPlayer())
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
					}
					break;
				}
				case CONFUSE_MOB_ONLY:
				{
					// do nothing if not on mob
					if (target.isL2Attackable())
					{
						if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						{
							L2Effect[] effects = target.getAllEffects();
							for (L2Effect e : effects)
							{
								if (e.getSkill().getSkillType() == type)
								{
									e.exit();
								}
							}
							skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
						}
						else
						{
							if (activeChar.isPlayer())
							{
								SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
								sm.addCharName(target);
								sm.addSkillName(skill);
								activeChar.sendPacket(sm);
							}
						}
					}
					else
					{
						activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
					}
					break;
				}
				case AGGDAMAGE:
				{
					if (target.isL2Attackable())
					{
						target.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, activeChar, (int) ((150 * skill.getPower()) / (target.getLevel() + 7)));
					}
					skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					// TODO: Remove this when lethal effect is done.
					Formulas.calcLethalHit(activeChar, target, skill);
					break;
				}
				case AGGREDUCE:
				{
					// these skills needs to be rechecked
					if (target.isL2Attackable())
					{
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
						
						double aggdiff = ((L2Attackable) target).getHating(activeChar) - target.calcStat(Stats.AGGRESSION, ((L2Attackable) target).getHating(activeChar), target, skill);
						
						if (skill.getPower() > 0)
						{
							((L2Attackable) target).reduceHate(null, (int) skill.getPower());
						}
						else if (aggdiff > 0)
						{
							((L2Attackable) target).reduceHate(null, (int) aggdiff);
						}
					}
					// when fail, target.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, activeChar);
					break;
				}
				case AGGREDUCE_CHAR:
				{
					// these skills needs to be rechecked
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						if (target.isL2Attackable())
						{
							L2Attackable targ = (L2Attackable) target;
							targ.stopHating(activeChar);
							if ((targ.getMostHated() == null) && targ.hasAI() && (targ.getAI() instanceof L2AttackableAI))
							{
								((L2AttackableAI) targ.getAI()).setGlobalAggro(-25);
								targ.clearAggroList();
								targ.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
								targ.setWalking();
							}
						}
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					}
					else
					{
						if (activeChar.isPlayer())
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
						target.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, activeChar);
					}
					break;
				}
				case AGGREMOVE:
				{
					// these skills needs to be rechecked
					if (target.isL2Attackable() && !target.isRaid())
					{
						if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						{
							if (skill.getTargetType() == L2TargetType.UNDEAD)
							{
								if (target.isUndead())
								{
									((L2Attackable) target).reduceHate(null, ((L2Attackable) target).getHating(((L2Attackable) target).getMostHated()));
								}
							}
							else
							{
								((L2Attackable) target).reduceHate(null, ((L2Attackable) target).getHating(((L2Attackable) target).getMostHated()));
							}
						}
						else
						{
							if (activeChar.isPlayer())
							{
								SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
								sm.addCharName(target);
								sm.addSkillName(skill);
								activeChar.sendPacket(sm);
							}
							target.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, activeChar);
						}
					}
					else
					{
						target.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, activeChar);
					}
					break;
				}
				case ERASE:
				{
					// Doesn't affect siege golem or wild hog cannon
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss) && !(target instanceof L2SiegeSummonInstance))
					{
						final L2PcInstance summonOwner = ((L2Summon) target).getOwner();
						final L2Summon summon = summonOwner.getSummon();
						if (summon != null)
						{
							// TODO: Retail confirmation for Soul of the Phoenix required.
							if (summon.isPhoenixBlessed())
							{
								if (summon.isNoblesseBlessed())
								{
									summon.stopNoblesseBlessing(null);
								}
							}
							else if (summon.isNoblesseBlessed())
							{
								summon.stopNoblesseBlessing(null);
							}
							else
							{
								summon.stopAllEffectsExceptThoseThatLastThroughDeath();
							}
							summon.abortAttack();
							summon.abortCast();
							summon.unSummon(summonOwner);
							summonOwner.sendPacket(SystemMessageId.YOUR_SERVITOR_HAS_VANISHED);
						}
					}
					else
					{
						if (activeChar.isPlayer())
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
					}
					break;
				}
			}
		}
		
		// self Effect :]
		if (skill.hasSelfEffects())
		{
			final L2Effect effect = activeChar.getFirstEffect(skill.getId());
			if ((effect != null) && effect.isSelfEffect())
			{
				// Replace old effect with new one.
				effect.exit();
			}
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
