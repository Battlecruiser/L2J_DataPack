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
 * @author Zoey76
 */
public class Recipes implements IItemHandler
{
	public void useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!(playable instanceof L2PcInstance))
		{
			return;
		}
		final L2PcInstance activeChar = playable.getActingPlayer();
		
		if (activeChar.isInCraftMode())
		{
			activeChar.sendPacket(SystemMessageId.CANT_ALTER_RECIPEBOOK_WHILE_CRAFTING);
			return;
		}
		
		final L2RecipeList rp = RecipeController.getInstance().getRecipeByItemId(item.getItemId());
		if (rp == null)
		{
			return;
		}
		
		SystemMessage sm;
		if (activeChar.hasRecipeList(rp.getId()))
		{
			activeChar.sendPacket(SystemMessageId.RECIPE_ALREADY_REGISTERED);
		}
		else
		{
			boolean canCraft = false;
			boolean recipeLevel = false;
			boolean recipeLimit = false;
			if (rp.isDwarvenRecipe() && (canCraft = activeChar.hasDwarvenCraft()))
			{
				if (recipeLevel = (rp.getLevel() > activeChar.getDwarvenCraft()))
				{
					recipeLimit = activeChar.getDwarvenRecipeBook().length >= activeChar.getDwarfRecipeLimit();
				}
			}
			else if (canCraft = activeChar.hasCommonCraft())
			{
				if (recipeLevel = (rp.getLevel() > activeChar.getCommonCraft()))
				{
					recipeLimit = activeChar.getCommonRecipeBook().length >= activeChar.getCommonRecipeLimit();
				}
			}
			
			if (canCraft)
			{
				if (recipeLevel)
				{
					activeChar.sendPacket(SystemMessageId.CREATE_LVL_TOO_LOW_TO_REGISTER);
				}
				else if (recipeLimit)
				{
					sm = SystemMessage.getSystemMessage(SystemMessageId.UP_TO_S1_RECIPES_CAN_REGISTER);
					sm.addNumber(activeChar.getDwarfRecipeLimit());
					activeChar.sendPacket(sm);
				}
				else
				{
					activeChar.registerDwarvenRecipeList(rp, true);
					activeChar.destroyItem("Consume", item.getObjectId(), 1, null, false);
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_ADDED);
					sm.addItemName(item);
					activeChar.sendPacket(sm);
				}
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.CANT_REGISTER_NO_ABILITY_TO_CRAFT);
			}
		}
	}
}
