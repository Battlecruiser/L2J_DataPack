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

import com.l2jserver.gameserver.enums.ShotType;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.instancemanager.DuelManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2ClanHallManagerInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.model.stats.Formulas;
import com.l2jserver.gameserver.network.SystemMessageId;

public class Continuous implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.BUFF,
		L2SkillType.DEBUFF,
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		boolean acted = true;
		L2PcInstance player = null;
		if (activeChar.isPlayer())
		{
			player = activeChar.getActingPlayer();
		}
		
		boolean ss = skill.useSoulShot() && activeChar.isChargedShot(ShotType.SOULSHOTS);
		boolean sps = skill.useSpiritShot() && activeChar.isChargedShot(ShotType.SPIRITSHOTS);
		boolean bss = skill.useSpiritShot() && activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS);
		
		for (L2Character target : (L2Character[]) targets)
		{
			byte shld = 0;
			
			if (Formulas.calcBuffDebuffReflection(target, skill))
			{
				target = activeChar;
			}
			
			// Player holding a cursed weapon can't be buffed and can't buff
			if ((skill.getSkillType() == L2SkillType.BUFF) && !(activeChar instanceof L2ClanHallManagerInstance))
			{
				if (target != activeChar)
				{
					if (target.isPlayer())
					{
						L2PcInstance trg = target.getActingPlayer();
						if (trg.isCursedWeaponEquipped())
						{
							continue;
						}
						else if (trg.getBlockCheckerArena() != -1)
						{
							continue;
						}
					}
					else if ((player != null) && player.isCursedWeaponEquipped())
					{
						continue;
					}
				}
			}
			
			if (skill.isBad())
			{
				shld = Formulas.calcShldUse(activeChar, target, skill);
				acted = Formulas.calcSkillSuccess(activeChar, target, skill, shld, ss, sps, bss);
			}
			
			if (acted)
			{
				if (skill.isToggle())
				{
					target.stopSkillEffects(true, skill.getId());
				}
				
				// Apply effects
				final Env env = new Env(shld, ss, sps, bss);
				skill.applyEffects(activeChar, null, target, env, false, false);
				
				// If this is a bad skill notify the duel manager, so it can be removed after the duel (player & target must be in the same duel).
				if (target.isPlayer() && target.getActingPlayer().isInDuel() && (player != null) && (player.getDuelId() == target.getActingPlayer().getDuelId()))
				{
					DuelManager.getInstance().onBuff(target.getActingPlayer(), skill);
				}
				else if (target.hasServitor() && (target.getSummon() != activeChar))
				{
					if (skill.isHeroSkill() || skill.isStatic()) // TODO: Can be stolen.
					{
						skill.applyEffects(activeChar, null, target.getSummon(), env, false, false);
					}
				}
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.ATTACK_FAILED);
			}
		}
		
		skill.applyEffects(activeChar, null, activeChar, null, true, false);
		
		activeChar.setChargedShot(bss ? ShotType.BLESSED_SPIRITSHOTS : ShotType.SPIRITSHOTS, false);
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
