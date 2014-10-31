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
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2SiegeFlagInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.skills.L2SkillType;

public class HealPercent implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.HEAL_PERCENT,
		L2SkillType.MANAHEAL_PERCENT,
		L2SkillType.CPHEAL_PERCENT,
		L2SkillType.HPMPHEAL_PERCENT,
		L2SkillType.HPMPCPHEAL_PERCENT,
		L2SkillType.HPCPHEAL_PERCENT
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
		
		boolean cp = false;
		boolean hp = false;
		boolean mp = false;
		switch (skill.getSkillType())
		{
			case CPHEAL_PERCENT:
				cp = true;
				break;
			case HEAL_PERCENT:
				hp = true;
				break;
			case MANAHEAL_PERCENT:
				mp = true;
				break;
			case HPMPHEAL_PERCENT:
				mp = true;
				hp = true;
				break;
			case HPMPCPHEAL_PERCENT:
				cp = true;
				hp = true;
				mp = true;
				break;
			case HPCPHEAL_PERCENT:
				hp = true;
				cp = true;
		}
		
		StatusUpdate su = null;
		SystemMessage sm;
		double amount = 0;
		boolean full = skill.getPower() == 100.0;
		boolean targetPlayer = false;
		
		for (L2Character target: (L2Character[]) targets)
		{
			//1505 - sublime self sacrifice
			if ((target == null || target.isDead() || target.isInvul()) && skill.getId() != 1505)
				continue;
			
			targetPlayer = target instanceof L2PcInstance;
			
			// Cursed weapon owner can't heal or be healed
			if (target != activeChar)
			{
				if (activeChar instanceof L2PcInstance && ((L2PcInstance)activeChar).isCursedWeaponEquipped())
					continue;
				if (targetPlayer && ((L2PcInstance)target).isCursedWeaponEquipped())
					continue;
			}
			
			// Doors and flags can't be healed in any way
			if (hp && (target instanceof L2DoorInstance || target instanceof L2SiegeFlagInstance))
				continue;
			
			if (targetPlayer)
				su = new StatusUpdate(target);
			
			// Only players have CP
			if (cp && targetPlayer)
			{
				if (full)
					amount = target.getMaxCp();
				else
					amount = target.getMaxCp() * skill.getPower() / 100.0;
				
				amount = Math.min(amount, target.getMaxCp() - target.getCurrentCp());
				target.setCurrentCp(amount + target.getCurrentCp());
				
				sm = SystemMessage.getSystemMessage(SystemMessageId.S1_CP_WILL_BE_RESTORED);
				sm.addNumber((int)amount);
				target.sendPacket(sm);
				su.addAttribute(StatusUpdate.CUR_CP, (int) target.getCurrentCp());
			}
			
			if (hp)
			{
				if (full)
					amount = target.getMaxHp();
				else
					amount = target.getMaxHp() * skill.getPower() / 100.0;
				
				amount = Math.min(amount, target.getMaxHp() - target.getCurrentHp());
				target.setCurrentHp(amount + target.getCurrentHp());
				
				if (targetPlayer)
				{
					if (activeChar != target)
					{
						sm = SystemMessage.getSystemMessage(SystemMessageId.S2_HP_RESTORED_BY_C1);
						sm.addCharName(activeChar);
					}
					else
						sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HP_RESTORED);
					sm.addNumber((int)amount);
					target.sendPacket(sm);
					su.addAttribute(StatusUpdate.CUR_HP, (int) target.getCurrentHp());
				}
			}
			
			if (mp)
			{
				if (full)
					amount = target.getMaxMp();
				else
					amount = target.getMaxMp() * skill.getPower() / 100.0;
				
				amount = Math.min(amount, target.getMaxMp() - target.getCurrentMp());
				target.setCurrentMp(amount + target.getCurrentMp());
				
				if (targetPlayer)
				{
					if (activeChar != target)
					{
						sm = SystemMessage.getSystemMessage(SystemMessageId.S2_MP_RESTORED_BY_C1);
						sm.addCharName(activeChar);
					}
					else
						sm = SystemMessage.getSystemMessage(SystemMessageId.S1_MP_RESTORED);
					sm.addNumber((int)amount);
					target.sendPacket(sm);
					su.addAttribute(StatusUpdate.CUR_MP, (int) target.getCurrentMp());
				}
			}
			
			if (targetPlayer)
				target.sendPacket(su);
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