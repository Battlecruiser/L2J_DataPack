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

import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.handler.SkillHandler;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeFlagInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.Formulas;
import com.l2jserver.gameserver.skills.Stats;
import com.l2jserver.gameserver.templates.item.L2Item;
import com.l2jserver.gameserver.templates.skills.L2SkillType;

/**
 * This class ...
 *
 * @version $Revision: 1.1.2.2.2.4 $ $Date: 2005/04/06 16:13:48 $
 */

public class Heal implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.HEAL,
		L2SkillType.HEAL_STATIC
	};
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.ISkillHandler#useSkill(com.l2jserver.gameserver.model.actor.L2Character, com.l2jserver.gameserver.model.L2Skill, com.l2jserver.gameserver.model.L2Object[])
	 */
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		//check for other effects
		ISkillHandler handler = SkillHandler.getInstance().getSkillHandler(L2SkillType.BUFF);
		
		if (handler != null)
			handler.useSkill(activeChar, skill, targets);
		
		double power = skill.getPower();
		
		switch (skill.getSkillType())
		{
			case HEAL_STATIC:
				break;
			default:
				final L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
				double staticShotBonus = 0;
				int mAtkMul = 1; // mAtk multiplier
				if (weaponInst != null
						&& weaponInst.getChargedSpiritshot() != L2ItemInstance.CHARGED_NONE)
				{
					if (activeChar instanceof L2PcInstance && ((L2PcInstance)activeChar).isMageClass())
					{
						staticShotBonus = skill.getMpConsume(); // static bonus for spiritshots
						
						if (weaponInst.getChargedSpiritshot() == L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT)
						{
							mAtkMul = 4;
							staticShotBonus *= 2.4; // static bonus for blessed spiritshots
						}
						else
							mAtkMul = 2;
					}
					else
					{
						// no static bonus
						// grade dynamic bonus
						switch (weaponInst.getItem().getItemGrade())
						{
							case L2Item.CRYSTAL_S84:
								mAtkMul = 4;
								break;
							case L2Item.CRYSTAL_S80:
								mAtkMul = 2;
								break;
						}
						// shot dynamic bonus
						if (weaponInst.getChargedSpiritshot() == L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT)
							mAtkMul *= 4; // 16x/8x/4x s84/s80/other
						else
							mAtkMul += 1; // 5x/3x/1x s84/s80/other
					}
					
					weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_NONE);
				}
				// If there is no weapon equipped, check for an active summon.
				else if (activeChar instanceof L2Summon
						&& ((L2Summon)activeChar).getChargedSpiritShot() != L2ItemInstance.CHARGED_NONE)
				{
					staticShotBonus = skill.getMpConsume(); // static bonus for spiritshots
					
					if (((L2Summon)activeChar).getChargedSpiritShot() == L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT)
					{
						staticShotBonus *= 2.4; // static bonus for blessed spiritshots
						mAtkMul = 4;
					}
					else
						mAtkMul = 2;
					
					((L2Summon)activeChar).setChargedSpiritShot(L2ItemInstance.CHARGED_NONE);
				}
				else if (activeChar instanceof L2Npc && ((L2Npc)activeChar)._spiritshotcharged)
				{
					staticShotBonus = 2.4 * skill.getMpConsume(); // always blessed spiritshots
					mAtkMul = 4;
					
					((L2Npc)activeChar)._spiritshotcharged = false;
				}
				
				power += staticShotBonus + Math.sqrt(mAtkMul * activeChar.getMAtk(activeChar, null));
		}
		
		double hp;
		for (L2Character target: (L2Character[]) targets)
		{
			// We should not heal if char is dead/invul
			if (target == null || target.isDead() || target.isInvul())
				continue;
			
			if (target instanceof L2DoorInstance || target instanceof L2SiegeFlagInstance)
				continue;
			
			// Player holding a cursed weapon can't be healed and can't heal
			if (target != activeChar)
			{
				if (target instanceof L2PcInstance && ((L2PcInstance) target).isCursedWeaponEquipped())
					continue;
				else if (activeChar instanceof L2PcInstance && ((L2PcInstance)activeChar).isCursedWeaponEquipped())
					continue;
			}
			
			switch (skill.getSkillType())
			{
				case HEAL_PERCENT:
					hp = target.getMaxHp() * power / 100.0;
					break;
				default:
					hp = power;
					hp *= target.calcStat(Stats.HEAL_EFFECTIVNESS, 100, null, null) / 100;
			}
			
			// Healer proficiency (since CT1)
			hp *= activeChar.calcStat(Stats.HEAL_PROFICIENCY, 100, null, null) / 100;
			// Extra bonus (since CT1.5)
			if (!skill.isPotion())
				hp += target.calcStat(Stats.HEAL_STATIC_BONUS, 0, null, null);
			
			// Heal critic, since CT2.3 Gracia Final
			if(skill.getSkillType() == L2SkillType.HEAL && !skill.isPotion()
					&& Formulas.calcMCrit(activeChar.getMCriticalHit(target, skill)))
				hp *= 3;
			
			//from CT2 u will receive exact HP, u can't go over it, if u have full HP and u get HP buff, u will receive 0HP restored message
			hp = Math.min(hp, target.getMaxRecoverableHp() - target.getCurrentHp());
			
			if (hp < 0)
				hp = 0;
			
			target.setCurrentHp(hp + target.getCurrentHp());
			StatusUpdate su = new StatusUpdate(target);
			su.addAttribute(StatusUpdate.CUR_HP, (int) target.getCurrentHp());
			target.sendPacket(su);
			
			if (target instanceof L2PcInstance)
			{
				if (skill.getId() == 4051)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.REJUVENATING_HP);
					target.sendPacket(sm);
				}
				else
				{
					if (activeChar instanceof L2PcInstance && activeChar != target)
					{
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S2_HP_RESTORED_BY_C1);
						sm.addString(activeChar.getName());
						sm.addNumber((int) hp);
						target.sendPacket(sm);
					}
					else
					{
						SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HP_RESTORED);
						sm.addNumber((int) hp);
						target.sendPacket(sm);
					}
				}
			}
		}
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
