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

import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.instancemanager.TerritoryWarManager;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.item.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * This class provides handling for items that should display a map
 * when double clicked.
 * 
 * @version $Revision: 1.1.4.3 $ $Date: 2005/03/27 15:30:07 $
 */

public class Disguise implements IItemHandler
{
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance, boolean)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		
		int regId = TerritoryWarManager.getInstance().getRegisteredTerritoryId(activeChar);
		if (regId > 0 && regId == (item.getItemId() - 13596))
		{
			if (activeChar.getClan() != null && activeChar.getClan().getHasCastle() > 0)
			{
				activeChar.sendPacket(SystemMessageId.TERRITORY_OWNING_CLAN_CANNOT_USE_DISGUISE_SCROLL);
				return;
			}
			TerritoryWarManager.getInstance().addDisguisedPlayer(activeChar.getObjectId());
			activeChar.broadcastUserInfo();
			playable.destroyItem("Consume", item.getObjectId(), 1, null, false);
		}
		else if (regId > 0)
		{
			activeChar.sendPacket(SystemMessageId.THE_DISGUISE_SCROLL_MEANT_FOR_DIFFERENT_TERRITORY);
			return;
		}
		else
		{
			activeChar.sendPacket(SystemMessageId.TERRITORY_WAR_SCROLL_CAN_NOT_USED_NOW);
			return;
		}
	}
}
