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

import java.util.logging.Logger;

import com.l2jserver.gameserver.ai.CtrlEvent;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.ai.L2AttackableAI;
import com.l2jserver.gameserver.datatables.ExperienceTable;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.handler.SkillHandler;
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
import com.l2jserver.util.Rnd;

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
		L2SkillType.FAKE_DEATH,
		L2SkillType.CONFUSE_MOB_ONLY,
		L2SkillType.NEGATE,
		L2SkillType.CANCEL_DEBUFF,
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
		boolean ss = skill.isPhysical() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		boolean sps = skill.isMagic() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
		boolean bss = skill.isMagic() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
		
		for (L2Object obj: targets)
		{
			if (!(obj instanceof L2Character))
				continue;
			L2Character target = (L2Character) obj;
			if (target.isDead() || ((target.isInvul() && type != L2SkillType.NEGATE) && !target.isParalyzed())) // bypass if target is null, dead or invul (excluding invul from Petrification)
				continue;
			
			shld = Formulas.calcShldUse(activeChar, target, skill);
			
			switch (type)
			{
				case BETRAY:
				{
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					else
					{
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
						sm.addCharName(target);
						sm.addSkillName(skill);
						activeChar.sendPacket(sm);
					}
					break;
				}
				case FAKE_DEATH:
				{
					// stun/fakedeath is not mdef dependant, it depends on lvl difference, target CON and power of stun
					skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					break;
				}
				case ROOT:
				case DISARM:
				case STUN:
				{
					if (Formulas.calcSkillReflect(target, skill) == Formulas.SKILL_REFLECT_SUCCEED)
						target = activeChar;
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
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
				case SLEEP:
				case PARALYZE: //use same as root for now
				{
					if (Formulas.calcSkillReflect(target, skill) == Formulas.SKILL_REFLECT_SUCCEED)
						target = activeChar;
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
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
						target = activeChar;
					
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
									e.exit();
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
						activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
					break;
				}
				case AGGDAMAGE:
				{
					if (target.isL2Attackable())
						target.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, activeChar, (int) ((150 * skill.getPower()) / (target.getLevel() + 7)));
					// TODO [Nemesiss] should this have 100% chance?
					skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
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
							((L2Attackable) target).reduceHate(null, (int) skill.getPower());
						else if (aggdiff > 0)
							((L2Attackable) target).reduceHate(null, (int) aggdiff);
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
							if (targ.getMostHated() == null && targ.hasAI() && targ.getAI() instanceof L2AttackableAI)
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
							if (skill.getTargetType() == L2TargetType.TARGET_UNDEAD)
							{
								if (target.isUndead())
									((L2Attackable) target).reduceHate(null, ((L2Attackable) target).getHating(((L2Attackable) target).getMostHated()));
							}
							else
								((L2Attackable) target).reduceHate(null, ((L2Attackable) target).getHating(((L2Attackable) target).getMostHated()));
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
						target.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, activeChar);
					break;
				}
				case ERASE:
				{
					// Doesn't affect siege golem or wild hog cannon
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss) && !(target instanceof L2SiegeSummonInstance))
					{
						final L2PcInstance summonOwner = ((L2Summon) target).getOwner();
						final L2Summon summon = summonOwner.getPet();
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
				case CANCEL_DEBUFF:
				{
					L2Effect[] effects = target.getAllEffects();
					
					if (effects == null || effects.length == 0)
						break;
					
					int count = (skill.getMaxNegatedEffects() > 0) ? 0 : -2;
					for (L2Effect e : effects)
					{
						if (e == null
								|| !e.getSkill().isDebuff()
								|| !e.getSkill().canBeDispeled())
							continue;
						
						e.exit();
						
						if (count > -1)
						{
							count++;
							if (count >= skill.getMaxNegatedEffects())
								break;
						}
					}
					
					break;
				}
				case CANCEL_STATS: // same than CANCEL but
				{
					if (Formulas.calcSkillReflect(target, skill) == Formulas.SKILL_REFLECT_SUCCEED)
						target = activeChar;
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						L2Effect[] effects = target.getAllEffects();
						
						int max = skill.getMaxNegatedEffects();
						if (max == 0)
							max = Integer.MAX_VALUE; //this is for RBcancells and stuff...
						
						if (effects.length >= max)
							effects = SortEffects(effects);
						
						//for(int i = 0; i < effects.length;i++)
						//    activeChar.sendMessage(Integer.toString(effects[i].getSkill().getMagicLevel()));
						
						int count = 1;
						
						for (L2Effect e : effects)
						{
							// do not delete signet effects!
							switch (e.getEffectType())
							{
								case SIGNET_GROUND:
								case SIGNET_EFFECT:
									continue;
							}
							
							switch(e.getSkill().getId())
							{
								case 4082:
								case 4215:
								case 4515:
								case 5182:
								case 110:
								case 111:
								case 1323:
								case 1325:
									continue;
							}
							
							switch (e.getSkill().getSkillType())
							{
								case BUFF:
								case HEAL_PERCENT:
								case COMBATPOINTHEAL:
									break;
								default:
									continue;
							}
							
							double rate = 1 - (count / max);
							if (rate < 0.33)
								rate = 0.33;
							else if (rate > 0.95)
								rate = 0.95;
							if (Rnd.get(1000) < (rate * 1000))
							{
								boolean exit = false;
								for (L2SkillType skillType : skill.getNegateStats())
								{
									if (skillType == e.getSkillType())
									{
										exit = true;
										break;
									}
								}
								
								if (exit)
								{
									e.exit();
									if (count == max)
										break;
									
									count++;
								}
							}
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
				case NEGATE:
				{
					if (Formulas.calcSkillReflect(target, skill) == Formulas.SKILL_REFLECT_SUCCEED)
						target = activeChar;
					
					if (skill.getNegateId().length != 0)
					{
						for (int i = 0; i < skill.getNegateId().length; i++)
						{
							if (skill.getNegateId()[i] != 0)
								target.stopSkillEffects(skill.getNegateId()[i]);
						}
					}
					else if (skill.getNegateAbnormals() != null)
					{
						for (L2Effect effect : target.getAllEffects())
						{
							if (effect == null)
								continue;
							
							for (String negateAbnormalType : skill.getNegateAbnormals().keySet())
							{
								if (negateAbnormalType.equalsIgnoreCase(effect.getAbnormalType()) && skill.getNegateAbnormals().get(negateAbnormalType) >= effect.getAbnormalLvl())
									effect.exit();
							}
						}
					}
					else // all others negate type skills
					{
						int removedBuffs = (skill.getMaxNegatedEffects() > 0) ? 0 : -2;
						
						for (L2SkillType skillType : skill.getNegateStats())
						{
							if (removedBuffs > skill.getMaxNegatedEffects())
								break;
							
							switch(skillType)
							{
								case BUFF:
									int lvlmodifier = 52 + skill.getMagicLevel() * 2;
									if (skill.getMagicLevel() == 12)
										lvlmodifier = (ExperienceTable.getInstance().getMaxLevel()-1);
									int landrate = 90;
									if ((target.getLevel() - lvlmodifier) > 0)
										landrate = 90 - 4 * (target.getLevel() - lvlmodifier);
									
									landrate = (int) activeChar.calcStat(Stats.CANCEL_VULN, landrate, target, null);
									
									if (Rnd.get(100) < landrate)
										removedBuffs += negateEffect(target, L2SkillType.BUFF, -1, skill.getMaxNegatedEffects());
									break;
								case HEAL:
									ISkillHandler Healhandler = SkillHandler.getInstance().getHandler(L2SkillType.HEAL);
									if (Healhandler == null)
									{
										_log.severe("Couldn't find skill handler for HEAL.");
										continue;
									}
									L2Character tgts[] = new L2Character[]{target};
									Healhandler.useSkill(activeChar, skill, tgts);
									break;
								default:
									removedBuffs += negateEffect(target, skillType, skill.getNegateLvl(), skill.getMaxNegatedEffects());
									break;
							}//end switch
						}//end for
					}//end else
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						skill.getEffects(activeChar, target, new Env(shld, ss, sps, bss));
					}
				}// end case
			}//end switch
			
			//Possibility of a lethal strike
			Formulas.calcLethalHit(activeChar, target, skill);
			
		}//end for
		
		// self Effect :]
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
		
		activeChar.setChargedShot(bss ? ShotType.BLESSED_SPIRITSHOTS : ShotType.SPIRITSHOTS, false);
	}
	
	/**
	 * 
	 * @param target
	 * @param type
	 * @param negateLvl 
	 * @param maxRemoved
	 * @return
	 */
	private int negateEffect(L2Character target, L2SkillType type, int negateLvl, int maxRemoved)
	{
		return negateEffect(target, type, negateLvl, 0, maxRemoved);
	}
	
	/**
	 * 
	 * @param target
	 * @param type
	 * @param negateLvl 
	 * @param skillId
	 * @param maxRemoved
	 * @return
	 */
	private int negateEffect(L2Character target, L2SkillType type, int negateLvl, int skillId, int maxRemoved)
	{
		L2Effect[] effects = target.getAllEffects();
		int count = (maxRemoved <= 0) ? -2 : 0;
		for (L2Effect e : effects)
		{
			if (negateLvl == -1) // if power is -1 the effect is always removed without power/lvl check ^^
			{
				if (e.getSkill().getSkillType() == type || (e.getSkill().getEffectType() != null && e.getSkill().getEffectType() == type))
				{
					if (skillId != 0)
					{
						if (skillId == e.getSkill().getId() && count < maxRemoved)
						{
							e.exit();
							if (count > -1)
								count++;
						}
					}
					else if (count < maxRemoved)
					{
						e.exit();
						if (count > -1)
							count++;
					}
				}
			}
			else
			{
				boolean cancel = false;
				if (e.getSkill().getEffectType() != null && e.getSkill().getEffectAbnormalLvl() >= 0)
				{
					if (e.getSkill().getEffectType() == type && e.getSkill().getEffectAbnormalLvl() <= negateLvl)
						cancel = true;
				}
				else if (e.getSkill().getSkillType() == type && e.getSkill().getAbnormalLvl() <= negateLvl)
					cancel = true;
				
				if (cancel)
				{
					if (skillId != 0)
					{
						if (skillId == e.getSkill().getId() && count < maxRemoved)
						{
							e.exit();
							if (count > -1)
								count++;
						}
					}
					else if (count < maxRemoved)
					{
						e.exit();
						if (count > -1)
							count++;
					}
				}
			}
		}
		
		return (maxRemoved <= 0) ? count + 2 : count;
	}
	
	private L2Effect[] SortEffects(L2Effect[] initial)
	{
		//this is just classic insert sort
		//If u can find better sort for max 20-30 units, rewrite this... :)
		int min, index = 0;
		L2Effect pom;
		for (int i = 0; i < initial.length; i++)
		{
			min = initial[i].getSkill().getMagicLevel();
			for (int j = i; j < initial.length; j++)
			{
				if (initial[j].getSkill().getMagicLevel() <= min)
				{
					min = initial[j].getSkill().getMagicLevel();
					index = j;
				}
			}
			pom = initial[i];
			initial[i] = initial[index];
			initial[index] = pom;
		}
		
		return initial;
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
