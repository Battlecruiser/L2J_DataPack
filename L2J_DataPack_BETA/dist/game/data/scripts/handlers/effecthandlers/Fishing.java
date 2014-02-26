/*
 * Copyright (C) 2004-2014 L2J DataPack
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

import com.l2jserver.Config;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.PcCondOverride;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.conditions.Condition;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.items.L2Weapon;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.items.type.L2EtcItemType;
import com.l2jserver.gameserver.model.items.type.L2WeaponType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.model.zone.ZoneId;
import com.l2jserver.gameserver.model.zone.type.L2FishingZone;
import com.l2jserver.gameserver.model.zone.type.L2WaterZone;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * @author UnAfraid
 */
public class Fishing extends AbstractEffect
{
	private static final int MIN_BAIT_DISTANCE = 90;
	private static final int MAX_BAIT_DISTANCE = 250;
	
	public Fishing(Condition attachCond, Condition applyCond, StatsSet set, StatsSet params)
	{
		super(attachCond, applyCond, set, params);
	}
	
	@Override
	public boolean isInstant()
	{
		return true;
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.FISHING_START;
	}
	
	@Override
	public void onStart(BuffInfo info)
	{
		super.onStart(info);
		final L2Character activeChar = info.getEffector();
		if (!activeChar.isPlayer())
		{
			return;
		}
		
		final L2PcInstance player = activeChar.getActingPlayer();
		
		if (!Config.ALLOWFISHING && !player.canOverrideCond(PcCondOverride.SKILL_CONDITIONS))
		{
			player.sendMessage("Fishing is disabled!");
			return;
		}
		
		if (player.isFishing())
		{
			if (player.getFishCombat() != null)
			{
				player.getFishCombat().doDie(false);
			}
			else
			{
				player.endFishing(false);
			}
			
			player.sendPacket(SystemMessageId.FISHING_ATTEMPT_CANCELLED);
			return;
		}
		
		// check for equiped fishing rod
		L2Weapon equipedWeapon = player.getActiveWeaponItem();
		if (((equipedWeapon == null) || (equipedWeapon.getItemType() != L2WeaponType.FISHINGROD)))
		{
			player.sendPacket(SystemMessageId.FISHING_POLE_NOT_EQUIPPED);
			return;
		}
		
		// check for equiped lure
		L2ItemInstance equipedLeftHand = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LHAND);
		if ((equipedLeftHand == null) || (equipedLeftHand.getItemType() != L2EtcItemType.LURE))
		{
			player.sendPacket(SystemMessageId.BAIT_ON_HOOK_BEFORE_FISHING);
			return;
		}
		
		if (!player.isGM())
		{
			if (player.isInBoat())
			{
				player.sendPacket(SystemMessageId.CANNOT_FISH_ON_BOAT);
				return;
			}
			
			if (player.isInCraftMode() || player.isInStoreMode())
			{
				player.sendPacket(SystemMessageId.CANNOT_FISH_WHILE_USING_RECIPE_BOOK);
				return;
			}
			
			if (player.isInsideZone(ZoneId.WATER))
			{
				player.sendPacket(SystemMessageId.CANNOT_FISH_UNDER_WATER);
				return;
			}
		}
		
		// calculate a position in front of the player with a random distance
		int distance = Rnd.get(MIN_BAIT_DISTANCE, MAX_BAIT_DISTANCE);
		final double angle = Util.convertHeadingToDegree(player.getHeading());
		final double radian = Math.toRadians(angle);
		final double sin = Math.sin(radian);
		final double cos = Math.cos(radian);
		int baitX = (int) (player.getX() + (cos * distance));
		int baitY = (int) (player.getY() + (sin * distance));
		
		// search for fishing and water zone
		L2FishingZone fishingZone = null;
		L2WaterZone waterZone = null;
		for (final L2ZoneType zone : ZoneManager.getInstance().getZones(baitX, baitY))
		{
			if (zone instanceof L2FishingZone)
			{
				fishingZone = (L2FishingZone) zone;
			}
			else if (zone instanceof L2WaterZone)
			{
				waterZone = (L2WaterZone) zone;
			}
			
			if ((fishingZone != null) && (waterZone != null))
			{
				break;
			}
		}
		
		int baitZ = computeBaitZ(player, baitX, baitY, fishingZone, waterZone);
		if (baitZ == Integer.MIN_VALUE)
		{
			for (distance = MAX_BAIT_DISTANCE; distance >= MIN_BAIT_DISTANCE; --distance)
			{
				baitX = (int) (player.getX() + (cos * distance));
				baitY = (int) (player.getY() + (sin * distance));
				
				// search for fishing and water zone again
				fishingZone = null;
				waterZone = null;
				for (final L2ZoneType zone : ZoneManager.getInstance().getZones(baitX, baitY))
				{
					if (zone instanceof L2FishingZone)
					{
						fishingZone = (L2FishingZone) zone;
					}
					else if (zone instanceof L2WaterZone)
					{
						waterZone = (L2WaterZone) zone;
					}
					
					if ((fishingZone != null) && (waterZone != null))
					{
						break;
					}
				}
				
				baitZ = computeBaitZ(player, baitX, baitY, fishingZone, waterZone);
				if (baitZ != Integer.MIN_VALUE)
				{
					break;
				}
			}
			
			if (baitZ == Integer.MIN_VALUE)
			{
				if (player.isGM())
				{
					baitZ = player.getZ();
				}
				else
				{
					player.sendPacket(SystemMessageId.CANNOT_FISH_HERE);
					return;
				}
			}
		}
		
		if (!player.destroyItem("FishingSkillHandler", equipedLeftHand, 1, null, false))
		{
			player.sendPacket(SystemMessageId.NOT_ENOUGH_BAIT);
			return;
		}
		
		player.setLure(equipedLeftHand);
		player.startFishing(baitX, baitY, baitZ);
	}
	
	/**
	 * Computes the Z of the bait.
	 * @param player the player
	 * @param baitX the bait x
	 * @param baitY the bait y
	 * @param fishingZone the fishing zone
	 * @param waterZone the water zone
	 * @return the bait z or {@link Integer#MIN_VALUE} when you cannot fish here
	 */
	private int computeBaitZ(final L2PcInstance player, final int baitX, final int baitY, final L2FishingZone fishingZone, final L2WaterZone waterZone)
	{
		if ((fishingZone == null))
		{
			return Integer.MIN_VALUE;
		}
		
		if ((waterZone == null))
		{
			return Integer.MIN_VALUE;
		}
		
		// always use water zone, fishing zone high z is high in the air...
		int baitZ = waterZone.getWaterZ();
		
		if (!GeoData.getInstance().canSeeTarget(player.getX(), player.getY(), player.getZ(), baitX, baitY, baitZ))
		{
			return Integer.MIN_VALUE;
		}
		
		if (GeoData.getInstance().hasGeo(baitX, baitY))
		{
			if (GeoData.getInstance().getHeight(baitX, baitY, baitZ) > baitZ)
			{
				return Integer.MIN_VALUE;
			}
			
			if (GeoData.getInstance().getHeight(baitX, baitY, player.getZ()) > baitZ)
			{
				return Integer.MIN_VALUE;
			}
		}
		
		return baitZ;
	}
}
