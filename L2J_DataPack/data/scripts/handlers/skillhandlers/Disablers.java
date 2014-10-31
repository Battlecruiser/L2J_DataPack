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

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;
import net.sf.l2j.gameserver.ai.CtrlEvent;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.ai.L2AttackableAI;
import net.sf.l2j.gameserver.handler.ISkillHandler;
import net.sf.l2j.gameserver.handler.SkillHandler;
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Attackable;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2SiegeSummonInstance;
import net.sf.l2j.gameserver.model.base.Experience;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.skills.Env;
import net.sf.l2j.gameserver.skills.Formulas;
import net.sf.l2j.gameserver.skills.Stats;
import net.sf.l2j.gameserver.skills.effects.EffectBuff;
import net.sf.l2j.gameserver.templates.skills.L2EffectType;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;
import net.sf.l2j.util.Rnd;

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
		L2SkillType.CANCEL,
		L2SkillType.CANCEL_DEBUFF,
		L2SkillType.PARALYZE,
		L2SkillType.ERASE,
		L2SkillType.MAGE_BANE,
		L2SkillType.WARRIOR_BANE,
		L2SkillType.BETRAY,
		L2SkillType.DISARM,
		L2SkillType.STEAL_BUFF
	};
	
	protected static final Logger _log = Logger.getLogger(L2Skill.class.getName());
	
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.ISkillHandler#useSkill(net.sf.l2j.gameserver.model.actor.L2Character, net.sf.l2j.gameserver.model.L2Skill, net.sf.l2j.gameserver.model.L2Object[])
	 */
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		L2SkillType type = skill.getSkillType();
		
		byte shld = 0;
		boolean ss = false;
		boolean sps = false;
		boolean bss = false;
		
		L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
		
		if (activeChar instanceof L2PcInstance)
		{
			if (weaponInst == null && skill.isOffensive())
			{
				activeChar.sendMessage("You must equip a weapon before casting a spell.");
				return;
			}
		}
		
		if (weaponInst != null)
		{
			if (skill.isMagic())
			{
				if (weaponInst.getChargedSpiritshot() == L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT)
				{
					bss = true;
					if (skill.getId() != 1020) // vitalize
						weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_NONE);
				}
				else if (weaponInst.getChargedSpiritshot() == L2ItemInstance.CHARGED_SPIRITSHOT)
				{
					sps = true;
					if (skill.getId() != 1020) // vitalize
						weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_NONE);
				}
			}
			else
			{
				if (weaponInst.getChargedSoulshot() == L2ItemInstance.CHARGED_SOULSHOT)
				{
					ss = true;
					if (skill.getId() != 1020) // vitalize
						weaponInst.setChargedSoulshot(L2ItemInstance.CHARGED_NONE);
				}
			}
		}
		// If there is no weapon equipped, check for an active summon.
		else if (activeChar instanceof L2Summon)
		{
			L2Summon activeSummon = (L2Summon) activeChar;
			
			if (skill.isMagic())
			{
				if (activeSummon.getChargedSpiritShot() == L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT)
				{
					bss = true;
					activeSummon.setChargedSpiritShot(L2ItemInstance.CHARGED_NONE);
				}
				else if (activeSummon.getChargedSpiritShot() == L2ItemInstance.CHARGED_SPIRITSHOT)
				{
					sps = true;
					activeSummon.setChargedSpiritShot(L2ItemInstance.CHARGED_NONE);
				}
			}
			else
			{
				if (activeSummon.getChargedSoulShot() == L2ItemInstance.CHARGED_SOULSHOT)
				{
					ss = true;
					activeSummon.setChargedSoulShot(L2ItemInstance.CHARGED_NONE);
				}
			}
		}
		else if (activeChar instanceof L2Npc)
		{
			bss = ((L2Npc) activeChar).isUsingShot(false);
			ss = ((L2Npc) activeChar).isUsingShot(true);
		}
		
		for (L2Character target: (L2Character[]) targets)
		{
			if (target == null || target.isDead() || target.isInvul()) //bypass if target is null, invul or dead
				continue;
			
			shld = Formulas.calcShldUse(activeChar, target);
			
			switch (type)
			{
				case BETRAY:
				{
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						skill.getEffects(activeChar, target);
					else
					{
						SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
						sm.addCharName(target);
						sm.addSkillName(skill);
						activeChar.sendPacket(sm);
					}
					break;
				}
				case FAKE_DEATH:
				{
					// stun/fakedeath is not mdef dependant, it depends on lvl difference, target CON and power of stun
					skill.getEffects(activeChar, target);
					break;
				}
				case ROOT:
				case DISARM:
				case STUN:
				{
					if (target.reflectSkill(skill))
						target = activeChar;
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						skill.getEffects(activeChar, target);
					else
					{
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
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
					if (target.reflectSkill(skill))
						target = activeChar;
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						skill.getEffects(activeChar, target);
					else
					{
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
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
					if (target.reflectSkill(skill))
						target = activeChar;
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						// stop same type effect if available
						L2Effect[] effects = target.getAllEffects();
						for (L2Effect e : effects)
						{
							if (e.getSkill().getSkillType() == type)
								e.exit();
						}
						skill.getEffects(activeChar, target);
					}
					else
					{
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
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
					if (target instanceof L2Attackable)
					{
						if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						{
							L2Effect[] effects = target.getAllEffects();
							for (L2Effect e : effects)
							{
								if (e.getSkill().getSkillType() == type)
									e.exit();
							}
							skill.getEffects(activeChar, target);
						}
						else
						{
							if (activeChar instanceof L2PcInstance)
							{
								SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
								sm.addCharName(target);
								sm.addSkillName(skill);
								activeChar.sendPacket(sm);
							}
						}
					}
					else
						activeChar.sendPacket(new SystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
					break;
				}
				case AGGDAMAGE:
				{
					if (target instanceof L2Attackable)
						target.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, activeChar, (int) ((150 * skill.getPower()) / (target.getLevel() + 7)));
					// TODO [Nemesiss] should this have 100% chance?
					skill.getEffects(activeChar, target);
					break;
				}
				case AGGREDUCE:
				{
					// these skills needs to be rechecked
					if (target instanceof L2Attackable)
					{
						skill.getEffects(activeChar, target);
						
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
						if (target instanceof L2Attackable)
						{
							L2Attackable targ = (L2Attackable) target;
							targ.stopHating(activeChar);
							if (targ.getMostHated() == null)
							{
								((L2AttackableAI) targ.getAI()).setGlobalAggro(-25);
								targ.clearAggroList();
								targ.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
								targ.setWalking();
							}
						}
						skill.getEffects(activeChar, target);
					}
					else
					{
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
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
					if (target instanceof L2Attackable && !target.isRaid())
					{
						if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
						{
							if (skill.getTargetType() == L2Skill.SkillTargetType.TARGET_UNDEAD)
							{
								if (target.isUndead())
									((L2Attackable) target).reduceHate(null, ((L2Attackable) target).getHating(((L2Attackable) target).getMostHated()));
							}
							else
								((L2Attackable) target).reduceHate(null, ((L2Attackable) target).getHating(((L2Attackable) target).getMostHated()));
						}
						else
						{
							if (activeChar instanceof L2PcInstance)
							{
								SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
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
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss)
					// doesn't affect siege golem or wild hog cannon
							&& !(target instanceof L2SiegeSummonInstance))
					{
						L2PcInstance summonOwner = null;
						L2Summon summonPet = null;
						summonOwner = ((L2Summon) target).getOwner();
						summonPet = summonOwner.getPet();
						if (summonPet != null)
						{
							summonPet.unSummon(summonOwner);
							SystemMessage sm = new SystemMessage(SystemMessageId.YOUR_SERVITOR_HAS_VANISHED);
							summonOwner.sendPacket(sm);
						}
					}
					else
					{
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
					}
					break;
				}
				case MAGE_BANE:
				{
					if (target.reflectSkill(skill))
						target = activeChar;
					
					if (!Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
						continue;
					}
					
					L2Effect[] effects = target.getAllEffects();
					for (L2Effect e : effects)
					{
						// TODO: Unhardcode this SkillType, maybe on its own child class
						// only Acumen and Greater Empower
						if (e.getSkill().getId() == 1085 || e.getSkill().getId() == 1059)
							e.exit();
					}
					
					break;
				}
				case WARRIOR_BANE:
				{
					if (target.reflectSkill(skill))
						target = activeChar;
					
					if (!Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
						continue;
					}
					
					L2Effect[] effects = target.getAllEffects();
					for (L2Effect e : effects)
					{
						// TODO: Unhardcode this SkillType, maybe on its own child class
						// only Wind Walk and Haste
						if (e.getSkill().getId() == 1204 || e.getSkill().getId() == 1086)
							e.exit();
					}
					break;
				}
				case CANCEL_DEBUFF:
				{
					L2Effect[] effects = target.getAllEffects();
					
					if (effects.length == 0 || effects == null)
						break;
					
					int count = (skill.getMaxNegatedEffects() > 0) ? 0 : -2;
					for (L2Effect e : effects)
					{
						if (e.getSkill().isDebuff() && count < skill.getMaxNegatedEffects() && e.getSkill().getId() != 5660)
						{
							//Do not remove raid curse skills
							if (e.getSkill().getId() != 4215 && e.getSkill().getId() != 4515 && e.getSkill().getId() != 4082)
							{
								e.exit();
								if (count > -1)
									count++;
							}
						}
					}
					
					break;
				}
				case STEAL_BUFF:
				{
					if (!(target instanceof L2Playable))
						return;
							
					L2Effect[] effects = target.getAllEffects();
						
					if (effects == null || effects.length < 1)
						return;
						
						// Reversing array
						List<L2Effect> list = Arrays.asList(effects);
						Collections.reverse(list);
						list.toArray(effects);
						
						FastList<L2Effect> toSteal = new FastList<L2Effect>();
						int count = 0;
						int lastSkill = 0;
						
						for (L2Effect e : effects)
						{
							if (e == null || (!(e instanceof EffectBuff) && e.getEffectType() != L2EffectType.TRANSFORMATION)
									|| e.getSkill().getSkillType() == L2SkillType.HEAL
									|| e.getSkill().isToggle()
									|| e.getSkill().isDebuff()
									|| e.getSkill().isHeroSkill()
									|| e.getSkill().isPotion()
									|| e.isHerbEffect())
								continue;
							
							if (e.getSkill().getId() == lastSkill)
							{
								if (count == 0) count = 1;
									toSteal.add(e);
							}
							else if (count < skill.getPower())
							{
								toSteal.add(e);
								count++;
							}
							else
								break;
						}
						if (!toSteal.isEmpty())
							stealEffects(activeChar, target, toSteal);
						
					break;
				}
				case CANCEL:
				{
					// old chance calculation:
					//int lvlmodifier = 52 + skill.getLevel() * 2;
					//if (skill.getLevel() == 12)
					//	lvlmodifier = (Experience.MAX_LEVEL - 1);
					//int landrate = 90;
					//if ((target.getLevel() - lvlmodifier) > 0)
					//	landrate = 90 - 4 * (target.getLevel() - lvlmodifier);
					
					//landrate = (int) activeChar.calcStat(Stats.CANCEL_VULN, landrate, target, null);
					
					//if (Rnd.get(100) < landrate)
					
					if (target.reflectSkill(skill))
						target = activeChar;
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						L2Effect[] effects = target.getAllEffects();
						
						double max = skill.getMaxNegatedEffects();
						if (max == 0)
							max = 24; //this is for RBcancells and stuff...
							
						if (effects.length >= max)
							effects = SortEffects(effects);
						
						//for(int i = 0; i < effects.length;i++)
						//    activeChar.sendMessage(Integer.toString(effects[i].getSkill().getMagicLevel()));
						
						double count = 1;
						
						for (L2Effect e : effects)
						{
							// do not delete signet effects!
							switch (e.getEffectType())
							{
								case SIGNET_GROUND:
								case SIGNET_EFFECT:
									continue;
							}
							
							if (e.getSkill().getId() != 4082 && e.getSkill().getId() != 4215 && e.getSkill().getId() != 4515 && e.getSkill().getId() != 5182 && e.getSkill().getId() != 110 && e.getSkill().getId() != 111
									&& e.getSkill().getId() != 1323 && e.getSkill().getId() != 1325) // Cannot cancel skills 4082, 4215, 4515, 110, 111, 1323, 1325
							{
								//if (e.getSkill().getSkillType() != L2SkillType.BUFF) //sleep, slow, surrenders etc
								//    e.exit();
								//else
								if (e.getSkill().getSkillType() == L2SkillType.BUFF || 
										e.getSkill().getSkillType() == L2SkillType.HEAL_PERCENT ||
											e.getSkill().getSkillType() == L2SkillType.REFLECT ||
												e.getSkill().getSkillType() == L2SkillType.COMBATPOINTHEAL)
								{
									double rate = 1 - (count / max);
									if (rate < 0.33)
										rate = 0.33;
									else if (rate > 0.95)
										rate = 0.95;
									if (Rnd.get(1000) < (rate * 1000))
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
						if (activeChar instanceof L2PcInstance)
						{
							SystemMessage sm = new SystemMessage(SystemMessageId.C1_RESISTED_YOUR_S2);
							sm.addCharName(target);
							sm.addSkillName(skill);
							activeChar.sendPacket(sm);
						}
					}
					
					break;
				}
				case NEGATE:
				{
					if (target.reflectSkill(skill))
						target = activeChar;
					
					if (skill.getNegateId() > 0)
					{
						L2Effect[] effects = target.getAllEffects();
						for (L2Effect e : effects)
						{
							if (e.getSkill().getId() == skill.getNegateId())
								e.exit();
						}
					}
					// fishing potion
					else if (skill.getId() == 2275)
					{
						negateEffect(target, L2SkillType.BUFF, skill.getNegateLvl(), skill.getNegateId(), -1);
					}
					// all others negate type skills
					else
					{
						int removedBuffs = (skill.getMaxNegatedEffects() > 0) ? 0 : -2;
						
						for (String stat : skill.getNegateStats())
						{
							if (removedBuffs > skill.getMaxNegatedEffects())
								break;
							
							stat = stat.toLowerCase().intern();
							
							if ("buff".equals(stat))
							{
								int lvlmodifier = 52 + skill.getMagicLevel() * 2;
								if (skill.getMagicLevel() == 12)
									lvlmodifier = (Experience.MAX_LEVEL - 1);
								int landrate = 90;
								if ((target.getLevel() - lvlmodifier) > 0)
									landrate = 90 - 4 * (target.getLevel() - lvlmodifier);
								
								landrate = (int) activeChar.calcStat(Stats.CANCEL_VULN, landrate, target, null);
								
								if (Rnd.get(100) < landrate)
									removedBuffs += negateEffect(target, L2SkillType.BUFF, -1, skill.getMaxNegatedEffects());
							}
							else if ("debuff".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.DEBUFF, -1, skill.getMaxNegatedEffects());
							else if ("weakness".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.WEAKNESS, -1, skill.getMaxNegatedEffects());
							else if ("stun".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.STUN, -1, skill.getMaxNegatedEffects());
							else if ("sleep".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.SLEEP, -1, skill.getMaxNegatedEffects());
							else if ("confusion".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.CONFUSION, -1, skill.getMaxNegatedEffects());
							else if ("mute".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.MUTE, -1, skill.getMaxNegatedEffects());
							else if ("fear".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.FEAR, -1, skill.getMaxNegatedEffects());
							else if ("poison".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.POISON, skill.getNegateLvl(), skill.getMaxNegatedEffects());
							else if ("bleed".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.BLEED, skill.getNegateLvl(), skill.getMaxNegatedEffects());
							else if ("paralyze".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.PARALYZE, -1, skill.getMaxNegatedEffects());
							else if ("root".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
								removedBuffs += negateEffect(target, L2SkillType.ROOT, -1, skill.getMaxNegatedEffects());
							else if ("heal".equals(stat) && removedBuffs < skill.getMaxNegatedEffects())
							{
								ISkillHandler Healhandler = SkillHandler.getInstance().getSkillHandler(L2SkillType.HEAL);
								if (Healhandler == null)
								{
									_log.severe("Couldn't find skill handler for HEAL.");
									continue;
								}
								L2Character tgts[] = new L2Character[]{target};
								try
								{
									Healhandler.useSkill(activeChar, skill, tgts);
								}
								catch (IOException e)
								{
									_log.log(Level.WARNING, "", e);
								}
							}
						}//end for
					}//end else
					
					if (Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss))
					{
						skill.getEffects(activeChar, target);
					}
				}// end case
			}//end switch
			
			//Possibility of a lethal strike
			Formulas.calcLethalHit(activeChar, target, skill);
			
		}//end for
		
		// self Effect :]
		L2Effect effect = activeChar.getFirstEffect(skill.getId());
		if (effect != null && effect.isSelfEffect())
		{
			//Replace old effect with new one.
			effect.exit();
		}
		skill.getEffectsSelf(activeChar);
		
	} //end void
	
	/**
	 * 
	 * @param target
	 * @param type
	 * @param power
	 * @param maxRemoved
	 * @return
	 */
	private int negateEffect(L2Character target, L2SkillType type, double power, int maxRemoved)
	{
		return negateEffect(target, type, power, 0, maxRemoved);
	}
	
	/**
	 * 
	 * @param target
	 * @param type
	 * @param power
	 * @param skillId
	 * @param maxRemoved
	 * @return
	 */
	private int negateEffect(L2Character target, L2SkillType type, double negateLvl, int skillId, int maxRemoved)
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
	
	private void stealEffects(L2Character stealer, L2Character stolen, FastList<L2Effect> stolenEffects)
	{
		if (stolen == null || stolenEffects == null || stolenEffects.isEmpty()) return;
		
		for (L2Effect eff : stolenEffects)
		{
			// if eff time is smaller than 1 sec, will not be stolen, just to save CPU,
			// avoid synchronization(?) problems and NPEs
			if (eff.getPeriod() - eff.getTime() < 1)
				continue;
			
			Env env = new Env();
			env.player = stolen;
			env.target = stealer;
			env.skill = eff.getSkill();
			L2Effect e = eff.getEffectTemplate().getStolenEffect(env, eff);
			
			// Since there is a previous check that limits allowed effects to those which come from SkillType.BUFF,
			// it is not needed another check for SkillType
			if (stealer instanceof L2PcInstance && e != null)
			{
				SystemMessage smsg = new SystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
				smsg.addSkillName(eff);
				stealer.sendPacket(smsg);
			}
			// Finishing stolen effect
				eff.exit();
		}
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
				if (initial[j].getSkill().getMagicLevel() <= min)
				{
					min = initial[j].getSkill().getMagicLevel();
					index = j;
					
				}
			pom = initial[i];
			initial[i] = initial[index];
			initial[index] = pom;
		}
		
		return initial;
	}
	    
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.ISkillHandler#getSkillIds()
	 */
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
