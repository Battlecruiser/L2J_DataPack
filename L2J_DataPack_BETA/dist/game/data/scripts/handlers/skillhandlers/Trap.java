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
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Trap;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.Quest.TrapAction;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.network.SystemMessageId;

public class Trap implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.DETECT_TRAP,
		L2SkillType.REMOVE_TRAP
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if ((activeChar == null) || (skill == null))
		{
			return;
		}
		
		switch (skill.getSkillType())
		{
			case DETECT_TRAP:
			{
				for (L2Character target : activeChar.getKnownList().getKnownCharactersInRadius(skill.getSkillRadius()))
				{
					if (!target.isTrap())
					{
						continue;
					}
					
					if (target.isAlikeDead())
					{
						continue;
					}
					
					final L2Trap trap = (L2Trap) target;
					
					if (trap.getLevel() <= skill.getPower())
					{
						trap.setDetected(activeChar);
					}
				}
				break;
			}
			case REMOVE_TRAP:
			{
				for (L2Character target : (L2Character[]) targets)
				{
					if (!target.isTrap())
					{
						continue;
					}
					
					if (target.isAlikeDead())
					{
						continue;
					}
					
					final L2Trap trap = (L2Trap) target;
					
					if (!trap.canSee(activeChar))
					{
						if (activeChar.isPlayer())
						{
							activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
						}
						continue;
					}
					
					if (trap.getLevel() > skill.getPower())
					{
						continue;
					}
					
					if (trap.getTemplate().getEventQuests(Quest.QuestEventType.ON_TRAP_ACTION) != null)
					{
						for (Quest quest : trap.getTemplate().getEventQuests(Quest.QuestEventType.ON_TRAP_ACTION))
						{
							quest.notifyTrapAction(trap, activeChar, TrapAction.TRAP_DISARMED);
						}
					}
					
					trap.unSummon();
					if (activeChar.isPlayer())
					{
						activeChar.sendPacket(SystemMessageId.A_TRAP_DEVICE_HAS_BEEN_STOPPED);
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
