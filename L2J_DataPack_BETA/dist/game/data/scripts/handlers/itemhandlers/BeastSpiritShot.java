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

import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PetInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.util.Broadcast;

/**
 * Beast SpiritShot Handler
 *
 * @author Tempy
 */
public class BeastSpiritShot implements IItemHandler
{
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IItemHandler#useItem(com.l2jserver.gameserver.model.actor.L2Playable, com.l2jserver.gameserver.model.items.instance.L2ItemInstance, boolean)
	 */
	@Override
	public void useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (playable == null)
			return;
		
		L2PcInstance activeOwner = playable.getActingPlayer();
		
		if (playable instanceof L2Summon)
		{
			activeOwner.sendPacket(SystemMessageId.PET_CANNOT_USE_ITEM);
			return;
		}
		
		L2Summon activePet = activeOwner.getPet();
		
		if (activePet == null)
		{
			activeOwner.sendPacket(SystemMessageId.PETS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
			return;
		}
		
		if (activePet.isDead())
		{
			activeOwner.sendPacket(SystemMessageId.SOULSHOTS_AND_SPIRITSHOTS_ARE_NOT_AVAILABLE_FOR_A_DEAD_PET);
			return;
		}
		
		int itemId = item.getItemId();
		boolean isBlessed = (itemId == 6647 || itemId == 20334);
		short shotConsumption = activePet.getSpiritShotsPerHit();
		
		long shotCount = item.getCount();
		if (!(shotCount > shotConsumption))
		{
			// Not enough SpiritShots to use.
			if (!activeOwner.disableAutoShot(itemId))
				activeOwner.sendPacket(SystemMessageId.NOT_ENOUGH_SPIRITHOTS_FOR_PET);
			return;
		}
		
		L2ItemInstance weaponInst = null;
		
		if (activePet instanceof L2PetInstance)
			weaponInst = ((L2PetInstance) activePet).getActiveWeaponInstance();
		
		if (weaponInst == null)
		{
			if (activePet.getChargedSpiritShot() != L2ItemInstance.CHARGED_NONE)
				return;
			
			if (isBlessed)
				activePet.setChargedSpiritShot(L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT);
			else
				activePet.setChargedSpiritShot(L2ItemInstance.CHARGED_SPIRITSHOT);
		}
		else
		{
			if (weaponInst.getChargedSpiritshot() != L2ItemInstance.CHARGED_NONE)
			{
				// SpiritShots are already active.
				return;
			}
			
			if (isBlessed)
				weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT);
			else
				weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_SPIRITSHOT);
		}
		
		if (!activeOwner.destroyItemWithoutTrace("Consume", item.getObjectId(), shotConsumption, null, false))
		{
			if (!activeOwner.disableAutoShot(itemId))
				activeOwner.sendPacket(SystemMessageId.NOT_ENOUGH_SPIRITHOTS_FOR_PET);
			return;
		}
		
		// Pet uses the power of spirit.
		activeOwner.sendPacket(SystemMessageId.PET_USE_SPIRITSHOT);
		int skillId = 0;
		switch (itemId)
		{
			case 6646:
				skillId = 2008;
				break;
			case 6647:
				skillId = 2009;
				break;
			case 20333:
				skillId = 22037;
				break;
			case 20334:
				skillId = 22038;
				break;
		}
		Broadcast.toSelfAndKnownPlayersInRadius(activeOwner, new MagicSkillUse(activePet, activePet, skillId, 1, 0, 0), 360000/*600*/);
	}
}
