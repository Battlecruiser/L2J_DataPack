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
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.ShotType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeFlagInstance;
import com.l2jserver.gameserver.model.items.L2Item;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class Heal implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.HEAL,
		L2SkillType.HEAL_STATIC
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		//check for other effects
		ISkillHandler handler = SkillHandler.getInstance().getHandler(L2SkillType.BUFF);
		
		if (handler != null)
			handler.useSkill(activeChar, skill, targets);
		
		double power = skill.getPower();
		boolean sps = skill.isMagic() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
		boolean bss = skill.isMagic() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
		
		switch (skill.getSkillType())
		{
			case HEAL_STATIC:
				break;
			default:
				double staticShotBonus = 0;
				int mAtkMul = 1; // mAtk multiplier
				if ((sps || bss) && (activeChar.isPlayer() && activeChar.getActingPlayer().isMageClass()) || activeChar.isSummon())
				{
					staticShotBonus = skill.getMpConsume(); // static bonus for spiritshots
					
					if (bss)
					{
						mAtkMul = 4;
						staticShotBonus *= 2.4; // static bonus for blessed spiritshots
					}
					else
						mAtkMul = 2;
				}
				else if ((sps || bss) && activeChar.isNpc())
				{
					staticShotBonus = 2.4 * skill.getMpConsume(); // always blessed spiritshots
					mAtkMul = 4;
				}
				else
				{
					// no static bonus
					// grade dynamic bonus
					final L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
					if (weaponInst != null)
					{
						switch (weaponInst.getItem().getItemGrade())
						{
							case L2Item.CRYSTAL_S84:
								mAtkMul = 4;
								break;
							case L2Item.CRYSTAL_S80:
								mAtkMul = 2;
								break;
						}
					}
					// shot dynamic bonus
					if (bss)
						mAtkMul *= 4; // 16x/8x/4x s84/s80/other
					else
						mAtkMul += 1; // 5x/3x/1x s84/s80/other
				}
				
				power += staticShotBonus + Math.sqrt(mAtkMul * activeChar.getMAtk(activeChar, null));
				activeChar.setChargedShot(bss ? ShotType.BLESSED_SPIRITSHOTS : ShotType.SPIRITSHOTS, false);
		}
		
		double hp;
		for (L2Character target: (L2Character[]) targets)
		{
			// if skill power is "0 or less" don't show heal system message.
			if (skill.getPower() <= 0)
			{
				continue;
			}
			
			// We should not heal if char is dead/invul
			if (target == null || target.isDead() || target.isInvul())
				continue;
			
			if (target.isDoor() || target instanceof L2SiegeFlagInstance)
				continue;
			
			// Player holding a cursed weapon can't be healed and can't heal
			if (target != activeChar)
			{
				if (target.isPlayer() && target.getActingPlayer().isCursedWeaponEquipped())
					continue;
				else if (activeChar.isPlayer() && activeChar.getActingPlayer().isCursedWeaponEquipped())
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
			if (!skill.isStatic())
				hp += target.calcStat(Stats.HEAL_STATIC_BONUS, 0, null, null);
			
			// Heal critic, since CT2.3 Gracia Final
			if(skill.getSkillType() == L2SkillType.HEAL && !skill.isStatic()
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
			
			if (target.isPlayer())
			{
				if (skill.getId() == 4051)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.REJUVENATING_HP);
					target.sendPacket(sm);
				}
				else
				{
					if (activeChar.isPlayer() && activeChar != target)
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
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
