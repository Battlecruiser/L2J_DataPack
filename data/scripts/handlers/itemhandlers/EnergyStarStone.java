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

import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2AirShipInstance;
import com.l2jserver.gameserver.model.actor.instance.L2ControllableAirShipInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

public class EnergyStarStone extends ItemSkills
{
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	@Override
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;

		final L2AirShipInstance ship = ((L2PcInstance)playable).getAirShip();
		if (ship == null
				|| !(ship instanceof L2ControllableAirShipInstance)
				|| ship.getFuel() >= ship.getMaxFuel())
		{
			playable.sendPacket(new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED).addItemName(item));
			playable.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		super.useItem(playable, item);
	}
}