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

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.actor.L2Npc;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class SpiritLake implements IItemHandler
{
    // Spirit of the Lake
    private static final int[] ITEM_IDS =
    {
        9689
    };
    
    /**
     * 
     * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
     */
    public void useItem(L2Playable playable, L2ItemInstance item)
    {
        if (!(playable instanceof L2PcInstance) || (playable.getTarget()==null) || !(playable.getTarget() instanceof L2Npc))
            return;
        
        L2PcInstance activeChar = (L2PcInstance) playable;
        L2Npc target = (L2Npc) activeChar.getTarget();

        if (target.getNpcId() != 18482)
        {
            activeChar.sendPacket(new SystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
            return;
        }

        if (item.getItemId() == 9689)
        { // Spirit of the Lake
            activeChar.useMagic(SkillTable.getInstance().getInfo(2368, 1), true, false);
        }
    }
    
    /**
     * 
     * @see net.sf.l2j.gameserver.handler.IItemHandler#getItemIds()
     */
    public int[] getItemIds()
    {
        return ITEM_IDS;
    }
}