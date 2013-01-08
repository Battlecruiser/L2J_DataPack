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

import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.handler.SkillHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @version $Revision: 1.1.2.2.2.1 $ $Date: 2005/03/02 15:38:36 $
 */
public class CombatPointHeal implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.COMBATPOINTHEAL
	};
	
	@Override
	public void useSkill(L2Character actChar, L2Skill skill, L2Object[] targets)
	{
		// check for other effects
		ISkillHandler handler = SkillHandler.getInstance().getHandler(L2SkillType.BUFF);
		
		if (handler != null)
		{
			handler.useSkill(actChar, skill, targets);
		}
		
		for (L2Character target : (L2Character[]) targets)
		{
			if (target.isInvul())
			{
				continue;
			}
			
			double cp = skill.getPower();
			
			cp = Math.min(cp, target.getMaxRecoverableCp() - target.getCurrentCp());
			
			// Prevent negative amounts
			if (cp < 0)
			{
				cp = 0;
			}
			
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_CP_WILL_BE_RESTORED);
			sm.addNumber((int) cp);
			target.sendPacket(sm);
			target.setCurrentCp(cp + target.getCurrentCp());
			StatusUpdate sump = new StatusUpdate(target);
			sump.addAttribute(StatusUpdate.CUR_CP, (int) target.getCurrentCp());
			target.sendPacket(sump);
		}
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}