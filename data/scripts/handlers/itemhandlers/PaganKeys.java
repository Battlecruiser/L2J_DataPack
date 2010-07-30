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
package handlers.itemhandlers;

import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author  chris
 */
public class PaganKeys implements IItemHandler
{
	public static final int INTERACTION_DISTANCE = 100;
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		int itemId = item.getItemId();
		if (!(playable instanceof L2PcInstance))
			return;
		L2PcInstance activeChar = (L2PcInstance) playable;
		L2Object target = activeChar.getTarget();
		
		if (!(target instanceof L2DoorInstance))
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.INCORRECT_TARGET));
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		L2DoorInstance door = (L2DoorInstance) target;
		
		if (!(activeChar.isInsideRadius(door, INTERACTION_DISTANCE, false, false)))
		{
			activeChar.sendMessage("Too far.");
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		if (activeChar.getAbnormalEffect() > 0 || activeChar.isInCombat())
		{
			activeChar.sendMessage("You cannot use the key now.");
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		
		if (!playable.destroyItem("Consume", item.getObjectId(), 1, null, false))
			return;
		
		switch (itemId)
		{
			case 9698:
				if (door.getDoorId() == 24220020)
				{
					if (activeChar.getInstanceId() != door.getInstanceId())
					{
						for (L2DoorInstance instanceDoor : InstanceManager.getInstance().getInstance(activeChar.getInstanceId()).getDoors())
							if (instanceDoor.getDoorId() == door.getDoorId())
							{
								instanceDoor.openMe();
							}
					}
					else
					{
						door.openMe();
					}
				}
				else
				{
					activeChar.sendMessage("Incorrect Door.");
				}
				break;
			case 9699:
				if (door.getDoorId() == 24220022)
				{
					if (activeChar.getInstanceId() != door.getInstanceId())
					{
						for (L2DoorInstance instanceDoor : InstanceManager.getInstance().getInstance(activeChar.getInstanceId()).getDoors())
							if (instanceDoor.getDoorId() == door.getDoorId())
							{
								instanceDoor.openMe();
							}
					}
					else
					{
						door.openMe();
					}
				}
				else
				{
					activeChar.sendMessage("Incorrect Door.");
				}
				break;
			case 8056:
				if (door.getDoorId() == 23150004||door.getDoorId() == 23150003)
				{
					DoorTable.getInstance().getDoor(23150003).openMe();
					DoorTable.getInstance().getDoor(23150003).onOpen();
					DoorTable.getInstance().getDoor(23150004).openMe();
					DoorTable.getInstance().getDoor(23150004).onOpen();
				}
				else
				{
					activeChar.sendMessage("Incorrect Door.");
				}
				break;
		}
	}
}
