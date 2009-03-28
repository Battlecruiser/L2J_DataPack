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
package handlers.ItemHandlers;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.handler.ItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PlayableInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Charus
 */
public class WondrousCubic implements IItemHandler
{
	private static final int[] ITEM_IDS = {	10632 };

	/**
	 * @see net.l2emuproject.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.instance.L2PlayableInstance, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2PlayableInstance playable, L2ItemInstance item)
	{
		L2PcInstance activeChar = (L2PcInstance) playable;

		if (activeChar.isSubClassActive())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.MAIN_CLASS_SKILL_ONLY));
			activeChar.sendPacket(new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED).addSkillName(2510));
			return;
		}
		if (activeChar.getWeightPenalty() >= 3 || activeChar.getInventoryLimit() - 10 < activeChar.getInventory().getSize())
		{
			activeChar.sendPacket(new SystemMessage(SystemMessageId.SLOTS_FULL));
			activeChar.sendPacket(new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED).addSkillName(2510));
			return;
		}

		if (item.getItemId() == 10632)
			activeChar.useMagic(SkillTable.getInstance().getInfo(2510, 1), true, false);
	}

	/**
	 * @see net.l2emuproject.gameserver.handler.IItemHandler#getItemIds()
	 */
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
	
    public static void main(String[] args)
    {
    	ItemHandler.getInstance().registerItemHandler(new WondrousCubic());
    }
}