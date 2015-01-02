/*
 * Copyright (C) 2004-2015 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.effecthandlers;

import com.l2jserver.gameserver.model.Elementals;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.items.L2Weapon;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.InventoryUpdate;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * Convert Item effect implementation.
 * @author Zoey76
 */
public final class ConvertItem extends AbstractEffect
{
	public ConvertItem(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		if ((info.getEffector() == null) || (info.getEffected() == null) || info.getEffected().isAlikeDead() || !info.getEffected().isPlayer())
		{
			return;
		}
		
		final L2PcInstance player = info.getEffected().getActingPlayer();
		if (player.isEnchanting())
		{
			return;
		}
		
		final L2Weapon weaponItem = player.getActiveWeaponItem();
		if (weaponItem == null)
		{
			return;
		}
		
		L2ItemInstance wpn = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);
		if (wpn == null)
		{
			wpn = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LHAND);
		}
		
		if ((wpn == null) || wpn.isAugmented() || (weaponItem.getChangeWeaponId() == 0))
		{
			return;
		}
		
		final int newItemId = weaponItem.getChangeWeaponId();
		if (newItemId == -1)
		{
			return;
		}
		
		final int enchantLevel = wpn.getEnchantLevel();
		final Elementals elementals = wpn.getElementals() == null ? null : wpn.getElementals()[0];
		final L2ItemInstance[] unequiped = player.getInventory().unEquipItemInBodySlotAndRecord(wpn.getItem().getBodyPart());
		final InventoryUpdate iu = new InventoryUpdate();
		for (L2ItemInstance item : unequiped)
		{
			iu.addModifiedItem(item);
		}
		player.sendPacket(iu);
		
		if (unequiped.length <= 0)
		{
			return;
		}
		byte count = 0;
		for (L2ItemInstance item : unequiped)
		{
			if (!(item.getItem() instanceof L2Weapon))
			{
				count++;
				continue;
			}
			
			final SystemMessage sm;
			if (item.getEnchantLevel() > 0)
			{
				sm = SystemMessage.getSystemMessage(SystemMessageId.EQUIPMENT_S1_S2_REMOVED);
				sm.addInt(item.getEnchantLevel());
				sm.addItemName(item);
			}
			else
			{
				sm = SystemMessage.getSystemMessage(SystemMessageId.S1_DISARMED);
				sm.addItemName(item);
			}
			player.sendPacket(sm);
		}
		
		if (count == unequiped.length)
		{
			return;
		}
		
		final L2ItemInstance destroyItem = player.getInventory().destroyItem("ChangeWeapon", wpn, player, null);
		if (destroyItem == null)
		{
			return;
		}
		
		final L2ItemInstance newItem = player.getInventory().addItem("ChangeWeapon", newItemId, 1, player, destroyItem);
		if (newItem == null)
		{
			return;
		}
		
		if ((elementals != null) && (elementals.getElement() != -1) && (elementals.getValue() != -1))
		{
			newItem.setElementAttr(elementals.getElement(), elementals.getValue());
		}
		newItem.setEnchantLevel(enchantLevel);
		player.getInventory().equipItem(newItem);
		
		final SystemMessage msg;
		if (newItem.getEnchantLevel() > 0)
		{
			msg = SystemMessage.getSystemMessage(SystemMessageId.S1_S2_EQUIPPED);
			msg.addInt(newItem.getEnchantLevel());
			msg.addItemName(newItem);
		}
		else
		{
			msg = SystemMessage.getSystemMessage(SystemMessageId.S1_EQUIPPED);
			msg.addItemName(newItem);
		}
		player.sendPacket(msg);
		
		final InventoryUpdate u = new InventoryUpdate();
		u.addRemovedItem(destroyItem);
		u.addItem(newItem);
		player.sendPacket(u);
		
		player.broadcastUserInfo();
	}
}
