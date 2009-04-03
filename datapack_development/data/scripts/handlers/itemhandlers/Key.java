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
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.instance.L2ChestInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public class Key implements IItemHandler
{
	public static final int INTERACTION_DISTANCE = 100;
	
	private static final int[] ITEM_IDS =
	{
		6665, 6666, 6667, 6668, 6669,
		6670, 6671, 6672, 8060
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		L2PcInstance activeChar = (L2PcInstance) playable;
		
		int itemId = item.getItemId();
		
		switch (itemId)
		{
			case 6665:
			case 6666:
			case 6667:
			case 6668:
			case 6669:
			case 6670:
			case 6671:
			case 6672:
			{
				L2Skill skill = SkillTable.getInstance().getInfo(2229, itemId - 6664); // box key skill
				L2Object target = activeChar.getTarget();
				
				if (!(target instanceof L2ChestInstance))
				{
					activeChar.sendPacket(new SystemMessage(SystemMessageId.INCORRECT_TARGET));
					activeChar.sendPacket(ActionFailed.STATIC_PACKET);
				}
				else
				{
					L2ChestInstance chest = (L2ChestInstance) target;
					if (chest.isDead() || chest.isInteracted())
					{
						activeChar.sendMessage("The chest Is empty.");
						activeChar.sendPacket(ActionFailed.STATIC_PACKET);
						return;
					}
					activeChar.useMagic(skill, false, false);
				}
				break;
			}
			case 8060:
			{
				L2Skill skill = SkillTable.getInstance().getInfo(2260, 1);
				activeChar.doSimultaneousCast(skill);
				break;
			}
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
