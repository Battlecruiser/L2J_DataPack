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
package handlers.skillhandlers;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.GeoData;
import net.sf.l2j.gameserver.handler.ISkillHandler;
import net.sf.l2j.gameserver.instancemanager.ZoneManager;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.itemcontainer.Inventory;
import net.sf.l2j.gameserver.model.zone.type.L2FishingZone;
import net.sf.l2j.gameserver.model.zone.type.L2WaterZone;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.InventoryUpdate;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.templates.item.L2Weapon;
import net.sf.l2j.gameserver.templates.item.L2WeaponType;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;
import net.sf.l2j.gameserver.util.Util;
import net.sf.l2j.util.Rnd;

public class Fishing implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.FISHING
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.ISkillHandler#useSkill(net.sf.l2j.gameserver.model.actor.L2Character, net.sf.l2j.gameserver.model.L2Skill, net.sf.l2j.gameserver.model.L2Object[])
	 */
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!(activeChar instanceof L2PcInstance))
			return;
		
		L2PcInstance player = (L2PcInstance) activeChar;
		
		/*
		 * If fishing is disabled, there isn't much point in doing anything
		 * else, unless you are GM. so this got moved up here, before anything
		 * else.
		 */
		if (!Config.ALLOWFISHING && !player.isGM())
		{
			player.sendMessage("Fishing server is currently offline");
			return;
		}
		if (player.isFishing())
		{
			if (player.getFishCombat() != null)
				player.getFishCombat().doDie(false);
			else
				player.endFishing(false);
			// Cancels fishing
			player.sendPacket(new SystemMessage(SystemMessageId.FISHING_ATTEMPT_CANCELLED));
			return;
		}
		L2Weapon weaponItem = player.getActiveWeaponItem();
		if ((weaponItem == null || weaponItem.getItemType() != L2WeaponType.ROD))
		{
			// Fishing poles are not installed
			player.sendPacket(new SystemMessage(SystemMessageId.FISHING_POLE_NOT_EQUIPPED));
			return;
		}
		L2ItemInstance lure = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LHAND);
		if (lure == null)
		{
			// Bait not equiped.
			player.sendPacket(new SystemMessage(SystemMessageId.BAIT_ON_HOOK_BEFORE_FISHING));
			return;
		}
		player.setLure(lure);
		L2ItemInstance lure2 = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LHAND);
		
		if (lure2 == null || lure2.getCount() < 1) // Not enough bait.
		{
			player.sendPacket(new SystemMessage(SystemMessageId.NOT_ENOUGH_BAIT));
			return;
		}
		if (player.isInBoat())
		{
			// You can't fish while you are on boat
			player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_FISH_ON_BOAT));
			if (!player.isGM())
				return;
		}
		if (player.isInCraftMode() || player.isInStoreMode())
		{
			player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_FISH_WHILE_USING_RECIPE_BOOK));
			if (!player.isGM())
				return;
		}
		/*
		 * If fishing is enabled, here is the code that was striped from
		 * startFishing() in L2PcInstance. Decide now where will the hook be
		 * cast...
		 */
		int rnd = Rnd.get(200) + 200;
		double angle = Util.convertHeadingToDegree(player.getHeading());
		double radian = Math.toRadians(angle);
		double sin = Math.sin(radian);
		double cos = Math.cos(radian);
		int x1 = (int) (cos * rnd);
		int y1 = (int) (sin * rnd);
		int x = player.getX() + x1;
		int y = player.getY() + y1;
		int z = player.getZ() - 30;
		/*
		 * ...and if the spot is in a fishing zone. If it is, it will then
		 * position the hook on the water surface. If not, you have to be GM to
		 * proceed past here... in that case, the hook will be positioned using
		 * the old Z lookup method.
		 */
		L2FishingZone aimingTo = ZoneManager.getInstance().getFishingZone(x, y, z);
		L2WaterZone water = ZoneManager.getInstance().getWaterZone(x, y, z);
		if (aimingTo != null && water != null && (GeoData.getInstance().canSeeTarget(player.getX(), player.getY(), player.getZ() + 50, x, y, water.getWaterZ() - 50)))
		{
			z = water.getWaterZ() + 10;
			// player.sendMessage("Hook x,y: " + x + "," + y + " - Water Z,
			// Player Z:" + z + ", " + player.getZ()); //debug line, shows hook
			// landing related coordinates. Uncoment if needed.
		}
		else if (aimingTo != null && GeoData.getInstance().canSeeTarget(player.getX(), player.getY(), player.getZ() + 50, x, y, aimingTo.getWaterZ() - 50))
			z = aimingTo.getWaterZ() + 10;
		else
		{
			// You can't fish here
			player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_FISH_HERE));
			if (!player.isGM())
			{
				return;
			}
		}
		/*
		 * Of course since you can define fishing water volumes of any height,
		 * the function needs to be changed to cope with that. Still, this is
		 * assuming that fishing zones water surfaces, are always above "sea
		 * level".
		 */
		if (player.getZ() <= -3800 || player.getZ() < (z - 32))
		{
			// You can't fish in water
			player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_FISH_UNDER_WATER));
			if (!player.isGM())
				return;
		}
		// Has enough bait, consume 1 and update inventory. Start fishing
		// follows.
		lure2 = player.getInventory().destroyItem("Consume", player.getInventory().getPaperdollObjectId(Inventory.PAPERDOLL_LHAND), 1, player, null);
		InventoryUpdate iu = new InventoryUpdate();
		iu.addModifiedItem(lure2);
		player.sendPacket(iu);
		// If everything else checks out, actually cast the hook and start
		// fishing... :P
		player.startFishing(x, y, z);
	}
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.ISkillHandler#getSkillIds()
	 */
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}