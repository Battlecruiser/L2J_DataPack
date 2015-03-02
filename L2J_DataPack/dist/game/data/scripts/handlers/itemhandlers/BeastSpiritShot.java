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
package handlers.itemhandlers;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.l2jserver.gameserver.enums.ShotType;
import com.l2jserver.gameserver.handler.IItemHandler;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.util.Broadcast;

/**
 * Beast SpiritShot Handler
 * @author Tempy
 */
public class BeastSpiritShot implements IItemHandler
{
	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item, boolean forceUse)
	{
		if (!playable.isPlayer())
		{
			playable.sendPacket(SystemMessageId.YOUR_PET_CANNOT_CARRY_THIS_ITEM);
			return false;
		}
		
		final L2PcInstance activeOwner = playable.getActingPlayer();
		if (!activeOwner.hasSummon())
		{
			activeOwner.sendPacket(SystemMessageId.PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
			return false;
		}
		
		final L2Summon pet = playable.getPet();
		if ((pet != null) && pet.isDead())
		{
			activeOwner.sendPacket(SystemMessageId.SOULSHOTS_AND_SPIRITSHOTS_ARE_NOT_AVAILABLE_FOR_A_DEAD_PET_OR_SERVITOR_SAD_ISN_T_IT);
			return false;
		}
		
		final List<L2Summon> aliveServitor = playable.getServitors().values().stream().filter(s -> !s.isDead()).collect(Collectors.toList());
		if (aliveServitor.isEmpty())
		{
			activeOwner.sendPacket(SystemMessageId.SOULSHOTS_AND_SPIRITSHOTS_ARE_NOT_AVAILABLE_FOR_A_DEAD_PET_OR_SERVITOR_SAD_ISN_T_IT);
			return false;
		}
		
		final int itemId = item.getId();
		final boolean isBlessed = ((itemId == 6647) || (itemId == 20334)); // TODO: Unhardcode these!
		final SkillHolder[] skills = item.getItem().getSkills();
		
		final ShotType shotType = isBlessed ? ShotType.BLESSED_SPIRITSHOTS : ShotType.SPIRITSHOTS;
		
		short shotConsumption = 0;
		if (pet != null)
		{
			if (!pet.isChargedShot(shotType))
			{
				shotConsumption += pet.getSpiritShotsPerHit();
			}
		}
		
		for (L2Summon servitors : aliveServitor)
		{
			if (!servitors.isChargedShot(shotType))
			{
				shotConsumption += servitors.getSpiritShotsPerHit();
			}
		}
		
		if (skills == null)
		{
			_log.log(Level.WARNING, getClass().getSimpleName() + ": is missing skills!");
			return false;
		}
		
		long shotCount = item.getCount();
		if (shotCount < shotConsumption)
		{
			// Not enough SpiritShots to use.
			if (!activeOwner.disableAutoShot(itemId))
			{
				activeOwner.sendPacket(SystemMessageId.YOU_DON_T_HAVE_ENOUGH_SPIRITSHOTS_NEEDED_FOR_A_PET_SERVITOR);
			}
			return false;
		}
		
		if (!activeOwner.destroyItemWithoutTrace("Consume", item.getObjectId(), shotConsumption, null, false))
		{
			if (!activeOwner.disableAutoShot(itemId))
			{
				activeOwner.sendPacket(SystemMessageId.YOU_DON_T_HAVE_ENOUGH_SPIRITSHOTS_NEEDED_FOR_A_PET_SERVITOR);
			}
			return false;
		}
		
		// Pet uses the power of spirit.
		activeOwner.sendPacket(SystemMessageId.YOUR_PET_USES_SPIRITSHOT);
		if (pet != null)
		{
			if (!pet.isChargedShot(shotType))
			{
				pet.setChargedShot(shotType, true);
				Broadcast.toSelfAndKnownPlayersInRadius(activeOwner, new MagicSkillUse(pet, pet, skills[0].getSkillId(), skills[0].getSkillLvl(), 0, 0), 600);
			}
		}
		
		aliveServitor.forEach(s ->
		{
			if (!s.isChargedShot(shotType))
			{
				s.setChargedShot(shotType, true);
				Broadcast.toSelfAndKnownPlayersInRadius(activeOwner, new MagicSkillUse(s, s, skills[0].getSkillId(), skills[0].getSkillLvl(), 0, 0), 600);
			}
		});
		return true;
	}
}
