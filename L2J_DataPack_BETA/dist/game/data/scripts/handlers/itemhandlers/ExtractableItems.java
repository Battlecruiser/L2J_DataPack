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

import java.util.List;
import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.L2ExtractableProduct;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.templates.item.L2EtcItem;
import com.l2jserver.util.Rnd;

/**
 * @author FBIagent 11/12/2006
 */
public class ExtractableItems implements IItemHandler
{
	private static Logger _log = Logger.getLogger(ItemTable.class.getName());
	
	public void useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!(playable instanceof L2PcInstance))
		{
			return;
		}
		
		final L2PcInstance activeChar = playable.getActingPlayer();
		
		final int itemID = item.getItemId();
		final L2EtcItem etcitem = (L2EtcItem) item.getItem();
		final List<L2ExtractableProduct> exitem = etcitem.getExtractableItems();
		if (exitem == null)
		{
			_log.info("No extractable data defined for " + etcitem);
			return;
		}
		
		//destroy item
		if (!activeChar.destroyItem("Extract", item.getObjectId(), 1, activeChar, true))
		{
			return;
		}
		
		boolean created = false;
		// calculate extraction
		for (L2ExtractableProduct expi : exitem)
		{
			if (Rnd.get(100000) <= expi.getChance())
			{
				int min = expi.getMin();
				int max = expi.getMax();
				
				if (((itemID >= 6411) && (itemID <= 6518)) || ((itemID >= 7726) && (itemID <= 7860)) || ((itemID >= 8403) && (itemID <= 8483)))
				{
					min *= Config.RATE_EXTR_FISH;
					max *= Config.RATE_EXTR_FISH;
				}
				
				final int createitemAmount = (max == min) ? min : (Rnd.get(max - min + 1) + min);
				activeChar.addItem("Extract", expi.getId(), createitemAmount, activeChar, true);
				created = true;
			}
		}
		
		if (!created)
		{
			activeChar.sendPacket(SystemMessageId.NOTHING_INSIDE_THAT);
		}
	}
}
