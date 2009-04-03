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

import net.sf.l2j.gameserver.datatables.ExtractableItemsData;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ExtractableItem;
import net.sf.l2j.gameserver.model.L2ExtractableProductItem;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

/**
 *
 * @author FBIagent 11/12/2006
 *
 */

public class ExtractableItems implements IItemHandler
{
	//private static Logger _log = Logger.getLogger(ItemTable.class.getName());
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		
		int itemID = item.getItemId();
		L2Skill skill = null;
		L2ExtractableItem exitem = ExtractableItemsData.getInstance().getExtractableItem(itemID);
		for (L2ExtractableProductItem expi : exitem.getProductItemsArray())
		{
			skill = expi.getSkill();
			if (skill != null)
				activeChar.useMagic(skill,false,false);
			return;
		}
	}
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#getItemIds()
	 */
	public int[] getItemIds()
	{
		return ExtractableItemsData.getInstance().itemIDs();
	}
}
