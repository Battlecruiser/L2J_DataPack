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

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.PetDataTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PetInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author  Kerberos
 */
public class PetFood implements IItemHandler
{
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		int itemId = item.getItemId();
		switch (itemId)
		{
			case 2515: // Wolf's food
				useFood(playable, 2048, item);
				break;
			case 4038: // Hatchling's food
				useFood(playable, 2063, item);
				break;
			case 5168: // Strider's food
				useFood(playable, 2101, item);
				break;
			case 5169: // ClanHall / Castle Strider's food
				useFood(playable, 2102, item);
				break;
			case 6316: // Wyvern's food
				useFood(playable, 2180, item);
				break;
			case 7582: // Baby Pet's food
				useFood(playable, 2048, item);
				break;
			case 9668: // Great Wolf's food
				useFood(playable, 2361, item);
				break;
			case 10425: // Improved Baby Pet's food
				useFood(playable, 2361, item);
				break;
		}
	}
	
	public boolean useFood(L2Playable activeChar, int magicId, L2ItemInstance item)
	{
		L2Skill skill = SkillTable.getInstance().getInfo(magicId, 1);
		if (skill != null)
		{
			if (activeChar instanceof L2PetInstance)
			{
				if (((L2PetInstance)activeChar).destroyItem("Consume", item.getObjectId(), 1, null, false))
				{
					activeChar.broadcastPacket(new MagicSkillUse(activeChar, activeChar, magicId, 1, 0, 0));
					((L2PetInstance)activeChar).setCurrentFed(((L2PetInstance)activeChar).getCurrentFed() + (skill.getFeed() * Config.PET_FOOD_RATE));
					((L2PetInstance)activeChar).broadcastStatusUpdate();
					if (((L2PetInstance)activeChar).getCurrentFed() < (0.55 * ((L2PetInstance)activeChar).getPetData().getPetMaxFeed()))
						((L2PetInstance)activeChar).getOwner().sendPacket(new SystemMessage(SystemMessageId.YOUR_PET_ATE_A_LITTLE_BUT_IS_STILL_HUNGRY));
					return true;
				}
			}
			else if (activeChar instanceof L2PcInstance)
			{
				L2PcInstance player = ((L2PcInstance)activeChar);
				int itemId = item.getItemId();
				boolean canUse = false;
				if (player.isMounted())
				{
					int petId = player.getMountNpcId();
					if (PetDataTable.isWolf(petId) && PetDataTable.isWolfFood(itemId))
						canUse = true;
					else if (PetDataTable.isEvolvedWolf(petId) && PetDataTable.isEvolvedWolfFood(itemId))
						canUse = true;
					else if (PetDataTable.isSinEater(petId) && PetDataTable.isSinEaterFood(itemId))
						canUse = true;
					else if (PetDataTable.isHatchling(petId) && PetDataTable.isHatchlingFood(itemId))
						canUse = true;
					else if (PetDataTable.isStrider(petId) && PetDataTable.isStriderFood(itemId))
						canUse = true;
					else if (PetDataTable.isWyvern(petId) && PetDataTable.isWyvernFood(itemId))
						canUse = true;
					else if (PetDataTable.isBaby(petId) && PetDataTable.isBabyFood(itemId))
						canUse = true;
					else if (PetDataTable.isImprovedBaby(petId) && PetDataTable.isImprovedBabyFood(itemId))
						canUse = true;

					if (canUse)
					{
						if (player.destroyItem("Consume", item.getObjectId(), 1, null, false))
						{
							player.broadcastPacket(new MagicSkillUse(activeChar, activeChar, magicId, 1, 0, 0));
							player.setCurrentFeed(player.getCurrentFeed() + skill.getFeed());
						}
						return true;
					}
					else
					{
						SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
						sm.addItemName(item);
						activeChar.sendPacket(sm);
						return false;
					}
				}
				else
				{
					SystemMessage sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
					sm.addItemName(item);
					activeChar.sendPacket(sm);
				}
				return false;
			}
		}
		return false;
	}
}
