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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.clanhall.SiegableHall;
import com.l2jserver.gameserver.network.serverpackets.ConfirmDlg;
import com.l2jserver.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jserver.gameserver.network.serverpackets.StaticObject;
import com.l2jserver.gameserver.network.serverpackets.ValidateLocation;

public class L2DoorInstanceAction implements IActionHandler
{
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		// Check if the L2PcInstance already target the L2NpcInstance
		if (activeChar.getTarget() != target)
		{
			// Set the target of the L2PcInstance activeChar
			activeChar.setTarget(target);
			
			// Send a Server->Client packet MyTargetSelected to the L2PcInstance activeChar
			activeChar.sendPacket(new MyTargetSelected(target.getObjectId(), 0));
			
			StaticObject su;
			L2DoorInstance door = (L2DoorInstance)target;
			// send HP amount if doors are inside castle/fortress zone
			// TODO: needed to be added here doors from conquerable clanhalls
			if ((door.getCastle() != null && door.getCastle().getCastleId() > 0)
				|| (door.getFort() != null && door.getFort().getFortId() > 0)
				|| (door.getClanHall() != null && door.getClanHall().isSiegableHall())
				&& !door.getIsCommanderDoor())
				su = new StaticObject(door, true);
			else
				su = new StaticObject(door, false);
			
			activeChar.sendPacket(su);
			
			// Send a Server->Client packet ValidateLocation to correct the L2NpcInstance position and heading on the client
			activeChar.sendPacket(new ValidateLocation(door));
		}
		else if (interact)
		{
			L2DoorInstance door = (L2DoorInstance)target;
			//            MyTargetSelected my = new MyTargetSelected(getObjectId(), activeChar.getLevel());
			//            activeChar.sendPacket(my);
			if (target.isAutoAttackable(activeChar))
			{
				if (Math.abs(activeChar.getZ() - target.getZ()) < 400) // this max heigth difference might need some tweaking
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
			}
			else if (activeChar.getClan() != null
					&& door.getClanHall() != null
					&& activeChar.getClanId() == door.getClanHall().getOwnerId())
			{
				if (!door.isInsideRadius(activeChar, L2Npc.INTERACTION_DISTANCE, false, false))
				{
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, target);
				}
				else if(!door.getClanHall().isSiegableHall() ||
					!((SiegableHall)door.getClanHall()).isInSiege())
				{
					activeChar.gatesRequest(door);
					if (!door.getOpen())
						activeChar.sendPacket(new ConfirmDlg(1140));
					else
						activeChar.sendPacket(new ConfirmDlg(1141));
				}
			}
			else if (activeChar.getClan() != null
					&& ((L2DoorInstance)target).getFort() != null
					&& activeChar.getClan() == ((L2DoorInstance)target).getFort().getOwnerClan()
					&& ((L2DoorInstance)target).isUnlockable()
					&& !((L2DoorInstance)target).getFort().getSiege().getIsInProgress())
			{
				if (!((L2Character)target).isInsideRadius(activeChar, L2Npc.INTERACTION_DISTANCE, false, false))
				{
					activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, target);
				}
				else
				{
					activeChar.gatesRequest((L2DoorInstance)target);
					if (!((L2DoorInstance)target).getOpen())
						activeChar.sendPacket(new ConfirmDlg(1140));
					else
						activeChar.sendPacket(new ConfirmDlg(1141));
				}
			}
		}
		return true;
	}
	
	public InstanceType getInstanceType()
	{
		return InstanceType.L2DoorInstance;
	}
}
