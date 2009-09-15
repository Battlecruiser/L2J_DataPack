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

import net.sf.l2j.gameserver.handler.IItemHandler;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PetInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ExAutoSoulShot;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.util.Broadcast;

/**
 * Beast SpiritShot Handler
 *
 * @author Tempy
 */
public class BeastSpiritShot implements IItemHandler
{
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (playable == null)
			return;
		
		L2PcInstance activeOwner = playable.getActingPlayer();
		
		if (playable instanceof L2Summon)
		{
			activeOwner.sendPacket(new SystemMessage(SystemMessageId.PET_CANNOT_USE_ITEM));
			return;
		}
		
		L2Summon activePet = activeOwner.getPet();
		
		if (activePet == null)
		{
			activeOwner.sendPacket(new SystemMessage(SystemMessageId.PETS_ARE_NOT_AVAILABLE_AT_THIS_TIME));
			return;
		}
		
		if (activePet.isDead())
		{
			activeOwner.sendPacket(new SystemMessage(SystemMessageId.SOULSHOTS_AND_SPIRITSHOTS_ARE_NOT_AVAILABLE_FOR_A_DEAD_PET));
			return;
		}
		
		int itemId = item.getItemId();
		boolean isBlessed = (itemId == 6647 || itemId == 20334);
		int shotConsumption = 1; // TODO: this should be readed from npc.sql(summons)/pets_stats.sql tables
		
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
			long shotCount = item.getCount();
			if (shotConsumption == 0)
			{
				activeOwner.sendPacket(new SystemMessage(SystemMessageId.CANNOT_USE_SPIRITSHOTS));
				return;
			}
			if (!(shotCount > shotConsumption))
			{
				// Not enough SpiritShots to use.
				activeOwner.sendPacket(new SystemMessage(SystemMessageId.NOT_ENOUGH_SPIRITHOTS_FOR_PET));
				return;
			}
			
			if (isBlessed)
				weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT);
			else
				weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_SPIRITSHOT);
		}
		
		if (!activeOwner.destroyItemWithoutTrace("Consume", item.getObjectId(), shotConsumption, null, false))
		{
			if (activeOwner.getAutoSoulShot().containsKey(itemId))
			{
				activeOwner.removeAutoSoulShot(itemId);
				activeOwner.sendPacket(new ExAutoSoulShot(itemId, 0));
				
				SystemMessage sm = new SystemMessage(SystemMessageId.AUTO_USE_OF_S1_CANCELLED);
				sm.addString(item.getItem().getName());
				activeOwner.sendPacket(sm);
				return;
			}
			
			activeOwner.sendPacket(new SystemMessage(SystemMessageId.NOT_ENOUGH_SPIRITSHOTS));
			return;
		}
		
		// Pet uses the power of spirit.
		activeOwner.sendPacket(new SystemMessage(SystemMessageId.PET_USE_THE_POWER_OF_SPIRIT));
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
