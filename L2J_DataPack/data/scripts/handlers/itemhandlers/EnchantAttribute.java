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

import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ExChooseInventoryAttributeItem;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class EnchantAttribute implements IItemHandler
{
	private static final int	ITEM_IDS[]	=
											{
			9546,
			9547,
			9548,
			9549,
			9550,
			9551,
			9552,
			9553,
			9554,
			9555,
			9556,
			9557,
			9558,
			9559,
			9560,
			9561,
			9562,
			9563,
			9564,
			9565,
			9566,
			9567,
			9568,
			9569							};

	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;

		L2PcInstance activeChar = (L2PcInstance) playable;
		if (activeChar.isCastingNow())
		{
			return;
		}

		activeChar.sendPacket(new SystemMessage(SystemMessageId.SELECT_ITEM_TO_ADD_ELEMENTAL_POWER));
		activeChar.setActiveEnchantAttrItem(item);
		activeChar.sendPacket(new ExChooseInventoryAttributeItem(item.getItemId()));
	}

	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}
