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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.handler.ISkillHandler;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2ChestInstance;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.util.Rnd;

public class Unlock implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.UNLOCK,
		L2SkillType.UNLOCK_SPECIAL
	};
	
	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		L2Object[] targetList = skill.getTargetList(activeChar);
		
		if (targetList == null)
			return;
		
		for (L2Object target: targets)
		{
			if (target instanceof L2DoorInstance)
			{
				L2DoorInstance door = (L2DoorInstance) target;
				// Check if door in the different instance
				if (activeChar.getInstanceId() != door.getInstanceId())
				{
					// Search for the instance
					final Instance inst = InstanceManager.getInstance().getInstance(activeChar.getInstanceId());
					if (inst == null)
					{
						// Instance not found
						activeChar.sendPacket(ActionFailed.STATIC_PACKET);
						return;
					}
					for (L2DoorInstance instanceDoor : inst.getDoors())
					{
						if (instanceDoor.getDoorId() == door.getDoorId())
						{
							// Door found
							door = instanceDoor;
							break;
						}
					}
					// Checking instance again
					if (activeChar.getInstanceId() != door.getInstanceId())
					{
						activeChar.sendPacket(ActionFailed.STATIC_PACKET);
						return;
					}
				}
				
				if ((!door.isOpenableBySkill() && skill.getSkillType() != L2SkillType.UNLOCK_SPECIAL)
						|| door.getFort() != null)
				{
					activeChar.sendPacket(SystemMessageId.UNABLE_TO_UNLOCK_DOOR);
					activeChar.sendPacket(ActionFailed.STATIC_PACKET);
					return;
				}
				
				if (doorUnlock(skill) && (!door.getOpen()))
					door.openMe();
				else
					activeChar.sendPacket(SystemMessageId.FAILED_TO_UNLOCK_DOOR);
			}
			else if (target instanceof L2ChestInstance)
			{
				L2ChestInstance chest = (L2ChestInstance) target;
				if ((chest.getCurrentHp() <= 0) || chest.isInteracted() || activeChar.getInstanceId() != chest.getInstanceId())
				{
					activeChar.sendPacket(ActionFailed.STATIC_PACKET);
					return;
				}
				
				chest.setInteracted();
				if (chestUnlock(skill, chest))
				{
					activeChar.broadcastSocialAction(3);
					chest.setSpecialDrop();
					chest.setMustRewardExpSp(false);
					chest.reduceCurrentHp(99999999, activeChar, skill);
				}
				else
				{
					activeChar.broadcastSocialAction(13);
					chest.addDamageHate(activeChar, 0, 1);
					chest.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, activeChar);
					if (chestTrap(chest))
						chest.chestTrap(activeChar);
				}
			}
		}
	}
	
	private static final boolean doorUnlock(L2Skill skill)
	{
		if (skill.getSkillType() == L2SkillType.UNLOCK_SPECIAL)
			return Rnd.get(100) < skill.getPower();
		
		switch (skill.getLevel())
		{
			case 0:
				return false;
			case 1:
				return Rnd.get(120) < 30;
			case 2:
				return Rnd.get(120) < 50;
			case 3:
				return Rnd.get(120) < 75;
			default:
				return Rnd.get(120) < 100;
		}
	}
	
	private static final boolean chestUnlock(L2Skill skill, L2Character chest)
	{
		int chance = 0;
		if (chest.getLevel() > 60)
		{
			if (skill.getLevel() < 10)
				return false;
			
			chance = (skill.getLevel() - 10) * 5 + 30;
		}
		else if (chest.getLevel() > 40)
		{
			if (skill.getLevel() < 6)
				return false;
			
			chance = (skill.getLevel() - 6) * 5 + 10;
		}
		else if (chest.getLevel() > 30)
		{
			if (skill.getLevel() < 3)
				return false;
			if (skill.getLevel() > 12)
				return true;
			
			chance = (skill.getLevel() - 3) * 5 + 30;
		}
		else
		{
			if (skill.getLevel() > 10)
				return true;
			
			chance = skill.getLevel() * 5 + 35;
		}
		
		chance = Math.min(chance, 50);
		return Rnd.get(100) < chance;
	}
	
	private static final boolean chestTrap(L2Character chest)
	{
		if (chest.getLevel() > 60)
			return Rnd.get(100) < 80;
		if (chest.getLevel() > 40)
			return Rnd.get(100) < 50;
		if (chest.getLevel() > 30)
			return Rnd.get(100) < 30;
		return Rnd.get(100) < 10;
	}
	
	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
