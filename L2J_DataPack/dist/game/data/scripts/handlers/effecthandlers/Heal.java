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
package handlers.effecthandlers;

import com.l2jserver.gameserver.model.L2Effect;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.item.L2Item;
import com.l2jserver.gameserver.model.item.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.Env;
import com.l2jserver.gameserver.skills.Formulas;
import com.l2jserver.gameserver.skills.Stats;
import com.l2jserver.gameserver.templates.effects.EffectTemplate;
import com.l2jserver.gameserver.templates.skills.L2EffectType;

/**
 * @author UnAfraid
 */
public class Heal extends L2Effect
{
	public Heal(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.HEAL;
	}
	
	@Override
	public boolean onStart()
	{
		L2Character target = getEffected();
		L2Character activeChar = getEffector();
		if (target == null || target.isDead() || target instanceof L2DoorInstance)
			return false;
		
		double amount = calc();
		final L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
		double staticShotBonus = 0;
		int mAtkMul = 1;
		
		if (weaponInst != null && weaponInst.getChargedSpiritshot() != L2ItemInstance.CHARGED_NONE)
		{
			if (activeChar instanceof L2PcInstance && ((L2PcInstance) activeChar).isMageClass())
			{
				staticShotBonus = getSkill().getMpConsume(); // static bonus for spiritshots
				
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
		else if (activeChar instanceof L2Summon && ((L2Summon) activeChar).getChargedSpiritShot() != L2ItemInstance.CHARGED_NONE)
		{
			staticShotBonus = getSkill().getMpConsume(); // static bonus for spiritshots
			
			if (((L2Summon) activeChar).getChargedSpiritShot() == L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT)
			{
				staticShotBonus *= 2.4; // static bonus for blessed spiritshots
				mAtkMul = 4;
			}
			else
				mAtkMul = 2;
			
			((L2Summon) activeChar).setChargedSpiritShot(L2ItemInstance.CHARGED_NONE);
		}
		else if (activeChar instanceof L2Npc && ((L2Npc) activeChar)._spiritshotcharged)
		{
			staticShotBonus = 2.4 * getSkill().getMpConsume(); // always blessed spiritshots
			mAtkMul = 4;
			
			((L2Npc) activeChar)._spiritshotcharged = false;
		}
		
		if (!getSkill().isStaticHeal())
		{
			amount += staticShotBonus + Math.sqrt(mAtkMul * activeChar.getMAtk(activeChar, null));
			amount *= target.calcStat(Stats.HEAL_EFFECTIVNESS, 100, null, null) / 100;
			// Healer proficiency (since CT1)
			amount *= activeChar.calcStat(Stats.HEAL_PROFICIENCY, 100, null, null) / 100;
			// Extra bonus (since CT1.5)
			if (!getSkill().isPotion())
				amount += target.calcStat(Stats.HEAL_STATIC_BONUS, 0, null, null);
			
			// Heal critic, since CT2.3 Gracia Final
			if (!getSkill().isPotion() && Formulas.calcMCrit(activeChar.getMCriticalHit(target, getSkill())))
				amount *= 3;
		}
		
		amount = Math.min(amount, target.getMaxRecoverableHp() - target.getCurrentHp());
		
		// Prevent negative amounts
		if (amount < 0)
			amount = 0;
		
		target.setCurrentHp(amount + target.getCurrentHp());
		StatusUpdate su = new StatusUpdate(target);
		su.addAttribute(StatusUpdate.CUR_HP, (int) target.getCurrentHp());
		target.sendPacket(su);
		
		if (target instanceof L2PcInstance)
		{
			if (getSkill().getId() == 4051)
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
					sm.addNumber((int) amount);
					target.sendPacket(sm);
				}
				else
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_HP_RESTORED);
					sm.addNumber((int) amount);
					target.sendPacket(sm);
				}
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
