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
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class NornilsPower implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.NORNILS_POWER
	};

	@Override
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!activeChar.isPlayer())
			return;
		
		InstanceWorld world = null;
		final int instanceId = activeChar.getInstanceId();
		if (instanceId > 0)
			world = InstanceManager.getInstance().getPlayerWorld(activeChar.getActingPlayer());
		
		if (world != null && world.getInstanceId() == instanceId && world.getTemplateId() == 11)
		{
			if(activeChar.isInsideRadius(-107393, 83677, 100, true))
			{
				activeChar.destroyItemByItemId("NornilsPower", 9713, 1, activeChar, true);
				L2DoorInstance door = InstanceManager.getInstance().getInstance(world.getInstanceId()).getDoor(16200010);
				if (door != null)
				{
					door.setMeshIndex(1);
					door.setTargetable(true);
					door.broadcastStatusUpdate();
				}
			}
			else
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
				sm.addSkillName(skill);
				activeChar.sendPacket(sm);
			}			
		}
		else
			activeChar.sendPacket(SystemMessageId.NOTHING_HAPPENED);
	}

	@Override
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}