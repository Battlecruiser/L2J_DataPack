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

import com.l2jserver.gameserver.RecipeController;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2RecipeList;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * This class ...
 *
 * @version $Revision: 1.1.2.5.2.5 $ $Date: 2005/04/06 16:13:51 $
 */

public class Recipes implements IItemHandler
{
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		L2PcInstance activeChar = (L2PcInstance) playable;
		L2RecipeList rp = RecipeController.getInstance().getRecipeByItemId(item.getItemId());
		if (rp == null)
			return;
		if (activeChar.hasRecipeList(rp.getId()))
			activeChar.sendPacket(new SystemMessage(SystemMessageId.RECIPE_ALREADY_REGISTERED));
		else
		{
			if (rp.isDwarvenRecipe())
			{
				if (activeChar.hasDwarvenCraft())
				{
					if (rp.getLevel() > activeChar.getDwarvenCraft())
					{
						//can't add recipe, becouse create item level too low
						activeChar.sendPacket(new SystemMessage(SystemMessageId.CREATE_LVL_TOO_LOW_TO_REGISTER));
					}
					else if (activeChar.getDwarvenRecipeBook().length >= activeChar.getDwarfRecipeLimit())
					{
						//Up to $s1 recipes can be registered.
						SystemMessage sm = new SystemMessage(SystemMessageId.UP_TO_S1_RECIPES_CAN_REGISTER);
						sm.addNumber(activeChar.getDwarfRecipeLimit());
						activeChar.sendPacket(sm);
					}
					else
					{
						activeChar.registerDwarvenRecipeList(rp, true);
						activeChar.destroyItem("Consume", item.getObjectId(), 1, null, false);
						activeChar.sendPacket(new SystemMessage(SystemMessageId.S1_ADDED).addItemName(item));
					}
				}
				else
					activeChar.sendPacket(new SystemMessage(SystemMessageId.CANT_REGISTER_NO_ABILITY_TO_CRAFT));
			}
			else
			{
				if (activeChar.hasCommonCraft())
				{
					if (rp.getLevel() > activeChar.getCommonCraft())
					{
						//can't add recipe, becouse create item level too low
						activeChar.sendPacket(new SystemMessage(SystemMessageId.CREATE_LVL_TOO_LOW_TO_REGISTER));
					}
					else if (activeChar.getCommonRecipeBook().length >= activeChar.getCommonRecipeLimit())
					{
						//Up to $s1 recipes can be registered.
						SystemMessage sm = new SystemMessage(SystemMessageId.UP_TO_S1_RECIPES_CAN_REGISTER);
						sm.addNumber(activeChar.getCommonRecipeLimit());
						activeChar.sendPacket(sm);
					}
					else
					{
						activeChar.registerCommonRecipeList(rp, true);
						activeChar.destroyItem("Consume", item.getObjectId(), 1, null, false);
						activeChar.sendPacket(new SystemMessage(SystemMessageId.S1_ADDED).addItemName(item));
					}
				}
				else
					activeChar.sendPacket(new SystemMessage(SystemMessageId.CANT_REGISTER_NO_ABILITY_TO_CRAFT));
			}
		}
	}
}
