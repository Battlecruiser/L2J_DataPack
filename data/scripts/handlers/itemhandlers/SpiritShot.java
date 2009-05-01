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
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ExAutoSoulShot;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.templates.item.L2Item;
import net.sf.l2j.gameserver.templates.item.L2Weapon;
import net.sf.l2j.gameserver.util.Broadcast;

/**
 * This class ...
 *
 * @version $Revision: 1.1.2.1.2.5 $ $Date: 2005/03/27 15:30:07 $
 */

public class SpiritShot implements IItemHandler
{
	// All the item IDs that this handler knows.
	private static final int[] ITEM_IDS =
	{
		5790, 2509, 2510, 2511, 2512, 2513, 2514
	};
	private static final int[] SKILL_IDS =
	{
		2061, 2155, 2156, 2157, 2158, 2159, 2159
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.IItemHandler#useItem(net.sf.l2j.gameserver.model.actor.L2Playable, net.sf.l2j.gameserver.model.L2ItemInstance)
	 */
	public synchronized void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		
		L2PcInstance activeChar = (L2PcInstance) playable;
		L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
		L2Weapon weaponItem = activeChar.getActiveWeaponItem();
		int itemId = item.getItemId();
		
		// Check if Spirit shot can be used
		if (weaponInst == null || weaponItem.getSpiritShotCount() == 0)
		{
			if (!activeChar.getAutoSoulShot().containsKey(itemId))
				activeChar.sendPacket(new SystemMessage(SystemMessageId.CANNOT_USE_SPIRITSHOTS));
			return;
		}
		
		// Check if Spirit shot is already active
		if (weaponInst.getChargedSpiritshot() != L2ItemInstance.CHARGED_NONE)
			return;
		
		// Check for correct grade
		int weaponGrade = weaponItem.getCrystalType();
		if ((weaponGrade == L2Item.CRYSTAL_NONE && itemId != 5790 && itemId != 2509) || (weaponGrade == L2Item.CRYSTAL_D && itemId != 2510) || (weaponGrade == L2Item.CRYSTAL_C && itemId != 2511)
				|| (weaponGrade == L2Item.CRYSTAL_B && itemId != 2512) || (weaponGrade == L2Item.CRYSTAL_A && itemId != 2513) || (weaponGrade == L2Item.CRYSTAL_S && itemId != 2514) || (weaponGrade == L2Item.CRYSTAL_S80 && itemId != 2514))
		{
			if (!activeChar.getAutoSoulShot().containsKey(itemId))
				activeChar.sendPacket(new SystemMessage(SystemMessageId.SPIRITSHOTS_GRADE_MISMATCH));
			return;
		}
		
		// Consume Spirit shot if player has enough of them
		if (!activeChar.destroyItemWithoutTrace("Consume", item.getObjectId(), weaponItem.getSpiritShotCount(), null, false))
		{
			if (activeChar.getAutoSoulShot().containsKey(itemId))
			{
				activeChar.removeAutoSoulShot(itemId);
				activeChar.sendPacket(new ExAutoSoulShot(itemId, 0));
				
				SystemMessage sm = new SystemMessage(SystemMessageId.AUTO_USE_OF_S1_CANCELLED);
				sm.addString(item.getItem().getName());
				activeChar.sendPacket(sm);
			}
			else
				activeChar.sendPacket(new SystemMessage(SystemMessageId.NOT_ENOUGH_SPIRITSHOTS));
			return;
		}
		
		// Charge Spirit shot
		weaponInst.setChargedSpiritshot(L2ItemInstance.CHARGED_SPIRITSHOT);
		
		// Send message to client
		activeChar.sendPacket(new SystemMessage(SystemMessageId.ENABLED_SPIRITSHOT));
		Broadcast.toSelfAndKnownPlayersInRadius(activeChar, new MagicSkillUse(activeChar, activeChar, SKILL_IDS[weaponGrade], 1, 0, 0), 360000);
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
