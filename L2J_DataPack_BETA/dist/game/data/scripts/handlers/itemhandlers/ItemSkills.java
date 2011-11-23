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

import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.item.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Item skills not allowed on olympiad
 */
public class ItemSkills extends ItemSkillsTemplate
{
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance, boolean)
	 */
	@Override
	public void useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		final L2PcInstance activeChar = playable.getActingPlayer();
		if (activeChar != null && activeChar.isInOlympiadMode())
		{
			activeChar.sendPacket(SystemMessageId.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT);
			return;
		}
		
		super.useItem(playable, item, forceUse);
	}
}
