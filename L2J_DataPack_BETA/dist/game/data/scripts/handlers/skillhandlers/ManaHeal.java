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
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.ShotType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @version $Revision: 1.1.2.2.2.1 $ $Date: 2005/03/02 15:38:36 $
 */
public class ManaHeal implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.MANAHEAL,
		L2SkillType.MANARECHARGE,
		L2SkillType.MANA_BY_LEVEL
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		for (L2Character target: (L2Character[]) targets)
		{
			// if skill power is "0 or less" don't show heal system message.
			if (skill.getPower() <= 0)
			{
				continue;
			}
			
			if (target.isInvul())
				continue;
			
			double mp = skill.getPower();
			
			switch (skill.getSkillType())
			{
				case MANARECHARGE:
					mp = target.calcStat(Stats.RECHARGE_MP_RATE, mp, null, null);
					break;
				case MANA_BY_LEVEL:	
				//recharged mp influenced by difference between target level and skill level
				//if target is within 5 levels or lower then skill level there's no penalty.
					mp = target.calcStat(Stats.RECHARGE_MP_RATE, mp, null, null);  
					if (target.getLevel() > skill.getMagicLevel())
					{
						int lvlDiff = target.getLevel() - skill.getMagicLevel();
						//if target is too high compared to skill level, the amount of recharged mp gradually decreases.
						if (lvlDiff == 6)		//6 levels difference:
							mp *= 0.9;			//only 90% effective
						else if (lvlDiff == 7)
							mp *= 0.8;			//80%
						else if (lvlDiff == 8)
							mp *= 0.7;			//70%
						else if (lvlDiff == 9)
							mp *= 0.6;			//60%
						else if (lvlDiff == 10)
							mp *= 0.5;			//50%
						else if (lvlDiff == 11)
							mp *= 0.4;			//40%
						else if (lvlDiff == 12)
							mp *= 0.3;			//30%
						else if (lvlDiff == 13)
							mp *= 0.2;			//20%
						else if (lvlDiff == 14)
							mp *= 0.1;			//10%
						
						else if (lvlDiff >= 15)	//15 levels or more:
							mp = 0;				//0mp recharged
					}
					
			}
			
			//from CT2 u will receive exact MP, u can't go over it, if u have full MP and u get MP buff, u will receive 0MP restored message
			mp = Math.min(mp, target.getMaxRecoverableMp() - target.getCurrentMp());
			
			// Prevent negative amounts
			if (mp < 0)
				mp = 0;
			
			target.setCurrentMp(mp + target.getCurrentMp());
			StatusUpdate sump = new StatusUpdate(target);
			sump.addAttribute(StatusUpdate.CUR_MP, (int) target.getCurrentMp());
			target.sendPacket(sump);
			
			SystemMessage sm;
			// if skill power is "0 or less" don't show heal system message.
			if (skill.getPower() > 0)
			{
				if (activeChar.isPlayer() && activeChar != target)
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.S2_MP_RESTORED_BY_C1);
					sm.addString(activeChar.getName());
					sm.addNumber((int) mp);
					target.sendPacket(sm);
				}
				else
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_MP_RESTORED);
					sm.addNumber((int) mp);
					target.sendPacket(sm);
				}
			}
			
			if (skill.hasEffects())
			{
				target.stopSkillEffects(skill.getId());
				skill.getEffects(activeChar, target);
				sm = SystemMessage.getSystemMessage(SystemMessageId.YOU_FEEL_S1_EFFECT);
				sm.addSkillName(skill);
				target.sendPacket(sm);
			}
		}
		
		if (skill.hasSelfEffects())
		{
			L2Effect effect = activeChar.getFirstEffect(skill.getId());
			if (effect != null && effect.isSelfEffect())
			{
				//Replace old effect with new one.
				effect.exit();
			}
			// cast self effect if any
			skill.getEffectsSelf(activeChar);
		}
		
		activeChar.setChargedShot(activeChar.isChargedShot(ShotType.BLESSED_SPIRITSHOTS) ? ShotType.BLESSED_SPIRITSHOTS : ShotType.SPIRITSHOTS, false);
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
