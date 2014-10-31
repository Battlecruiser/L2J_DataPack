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
package handlers.actionhandlers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jserver.gameserver.network.serverpackets.PetStatusShow;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.network.serverpackets.ValidateLocation;

public class L2SummonAction implements IActionHandler
{
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		// Aggression target lock effect
		if (activeChar.isLockedTarget() && activeChar.getLockedTarget() != target)
		{
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.FAILED_CHANGE_TARGET));
			return false;
		}
		
		
		if (activeChar == ((L2Summon)target).getOwner() && activeChar.getTarget() == target)
		{
			activeChar.sendPacket(new PetStatusShow((L2Summon)target));
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else if (activeChar.getTarget() != target)
		{
			if (Config.DEBUG)
				_log.fine("new target selected:"+target.getObjectId());
			
			activeChar.setTarget(target);
			activeChar.sendPacket(new ValidateLocation((L2Character)target));
			MyTargetSelected my = new MyTargetSelected(target.getObjectId(), activeChar.getLevel() - ((L2Character)target).getLevel());
			activeChar.sendPacket(my);
			
			//sends HP/MP status of the summon to other characters
			StatusUpdate su = new StatusUpdate(target);
			su.addAttribute(StatusUpdate.CUR_HP, (int) ((L2Character)target).getCurrentHp());
			su.addAttribute(StatusUpdate.MAX_HP, ((L2Character)target).getMaxHp());
			activeChar.sendPacket(su);
		}
		else if (interact)
		{
			activeChar.sendPacket(new ValidateLocation((L2Character)target));
			if (target.isAutoAttackable(activeChar))
			{
				if (Config.GEODATA > 0)
				{
					if (GeoData.getInstance().canSeeTarget(activeChar, target))
					{
						activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
						activeChar.onActionRequest();
					}
				}
				else
				{
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
					activeChar.onActionRequest();
				}
			}
			else
			{
				// This Action Failed packet avoids activeChar getting stuck when clicking three or more times
				activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				if (Config.GEODATA > 0)
				{
					if (GeoData.getInstance().canSeeTarget(activeChar, target))
						activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, target);
				}
				else
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, target);
			}
		}
		return true;
	}
	
	public InstanceType getInstanceType()
	{
		return InstanceType.L2Summon;
	}
}
