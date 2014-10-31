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
import com.l2jserver.gameserver.model.L2ItemInstance;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.Stats;
import com.l2jserver.gameserver.templates.item.L2Item;
import com.l2jserver.gameserver.templates.item.L2Weapon;
import com.l2jserver.gameserver.util.Broadcast;

/**
 * This class ...
 *
 * @version $Revision: 1.2.4.4 $ $Date: 2005/03/27 15:30:07 $
 */

public class SoulShots implements IItemHandler
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
		L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
		L2Weapon weaponItem = activeChar.getActiveWeaponItem();
		int itemId = item.getItemId();
		
		// Check if Soul shot can be used
		if (weaponInst == null || weaponItem.getSoulShotCount() == 0)
		{
			if (!activeChar.getAutoSoulShot().contains(itemId))
				activeChar.sendPacket(new SystemMessage(SystemMessageId.CANNOT_USE_SOULSHOTS));
			return;
		}
		
		final int weaponGrade = weaponItem.getCrystalType();
		
		boolean gradeCheck = true;
		
		switch (weaponGrade)
		{
			case L2Item.CRYSTAL_NONE:
				if (itemId != 5789 && itemId != 1835)
					gradeCheck = false;
				break;
			case L2Item.CRYSTAL_D:
				if (itemId != 1463 && itemId != 22082)
					gradeCheck = false;
				break;
			case L2Item.CRYSTAL_C:
				if (itemId != 1464 && itemId != 22083)
					gradeCheck = false;
				break;
			case L2Item.CRYSTAL_B:
				if (itemId != 1465 && itemId != 22084)
					gradeCheck = false;
				break;
			case L2Item.CRYSTAL_A:
				if (itemId != 1466 && itemId != 22085)
					gradeCheck = false;
				break;
			case L2Item.CRYSTAL_S:
			case L2Item.CRYSTAL_S80:
			case L2Item.CRYSTAL_S84:
				if (itemId != 1467 && itemId != 22086)
					gradeCheck = false;
				break;
		}
		
		if (!gradeCheck)
		{
			if (!activeChar.getAutoSoulShot().contains(itemId))
				activeChar.sendPacket(new SystemMessage(SystemMessageId.SOULSHOTS_GRADE_MISMATCH));
			
			return;
		}
		
		activeChar.soulShotLock.lock();
		try
		{
			// Check if Soul shot is already active
			if (weaponInst.getChargedSoulshot() != L2ItemInstance.CHARGED_NONE)
				return;
			
			// Consume Soul shots if player has enough of them
			int saSSCount = (int) activeChar.getStat().calcStat(Stats.SOULSHOT_COUNT, 0, null, null);
			int SSCount = saSSCount == 0 ? weaponItem.getSoulShotCount() : saSSCount;
			
			if (!activeChar.destroyItemWithoutTrace("Consume", item.getObjectId(), SSCount, null, false))
			{
				if (!activeChar.disableAutoShot(itemId))
					activeChar.sendPacket(new SystemMessage(SystemMessageId.NOT_ENOUGH_SOULSHOTS));
				return;
			}
			
			// Charge soul shot
			weaponInst.setChargedSoulshot(L2ItemInstance.CHARGED_SOULSHOT);
		}
		finally
		{
			activeChar.soulShotLock.unlock();
		}
		int skillId = 0;
		switch (itemId)
		{
			case 1835:
			case 5789:
				skillId=2039;
				break;
			case 1463:
				skillId=2150;
				break;
			case 1464:
				skillId=2151;
				break;
			case 1465:
				skillId=2152;
				break;
			case 1466:
				skillId=2153;
				break;
			case 1467:
				skillId=2154;
				break;
			case 22082:
				skillId=26060;
				break;
			case 22083:
				skillId=26061;
				break;
			case 22084:
				skillId=26062;
				break;
			case 22085:
				skillId=26063;
				break;
			case 22086:
				skillId=26064;
				break;
				
		}
		// Send message to client
		activeChar.sendPacket(new SystemMessage(SystemMessageId.ENABLED_SOULSHOT));
		Broadcast.toSelfAndKnownPlayersInRadius(activeChar, new MagicSkillUse(activeChar, activeChar, skillId, 1, 0, 0), 360000);
	}
}
