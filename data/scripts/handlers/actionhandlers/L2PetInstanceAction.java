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
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PetInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jserver.gameserver.network.serverpackets.PetStatusShow;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.network.serverpackets.ValidateLocation;

public class L2PetInstanceAction implements IActionHandler
{
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		// Aggression target lock effect
		if (activeChar.isLockedTarget() && activeChar.getLockedTarget() != target)
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.FAILED_CHANGE_TARGET));
			return false;
		}

		boolean isOwner = activeChar.getObjectId() == ((L2PetInstance)target).getOwner().getObjectId();

		activeChar.sendPacket(new ValidateLocation((L2Character)target));
		if(isOwner && activeChar != ((L2PetInstance)target).getOwner())
			((L2PetInstance)target).updateRefOwner(activeChar);
		if (activeChar.getTarget() != target)
		{
			if (Config.DEBUG)
				_log.fine("new target selected:" + target.getObjectId());
			
			// Set the target of the L2PcInstance activeChar
			activeChar.setTarget(target);
			
			activeChar.sendPacket(new MyTargetSelected(target.getObjectId(), activeChar.getLevel() - ((L2Character)target).getLevel()));
			
			// Send a Server->Client packet StatusUpdate of the L2PetInstance to the L2PcInstance to update its HP bar
			StatusUpdate su = new StatusUpdate(target);
			su.addAttribute(StatusUpdate.CUR_HP, (int) ((L2Character)target).getCurrentHp());
			su.addAttribute(StatusUpdate.MAX_HP, ((L2Character)target).getMaxHp());
			activeChar.sendPacket(su);
		}
		else if (interact)
		{
			// Check if the pet is attackable (without a forced attack) and isn't dead
			if (target.isAutoAttackable(activeChar) && !isOwner)
			{
				if (Config.GEODATA > 0)
				{
					if (GeoData.getInstance().canSeeTarget(activeChar, target))
					{
						// Set the L2PcInstance Intention to AI_INTENTION_ATTACK
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
			else if (!((L2Character)target).isInsideRadius(activeChar, 150, false, false))
			{
				if (Config.GEODATA > 0)
				{
					if (GeoData.getInstance().canSeeTarget(activeChar, target))
					{
						activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, target);
						activeChar.onActionRequest();
					}
				}
				else
				{
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, target);
					activeChar.onActionRequest();
				}
			}
			else 
			{
				if (isOwner)
					activeChar.sendPacket(new PetStatusShow((L2PetInstance)target));
			}
		}
		return true;
	}

	public InstanceType getInstanceType()
	{
		return InstanceType.L2PetInstance;
	}
}