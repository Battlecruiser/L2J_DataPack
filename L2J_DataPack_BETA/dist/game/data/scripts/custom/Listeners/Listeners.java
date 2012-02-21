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
package custom.Listeners;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastList;

import com.l2jserver.gameserver.datatables.CharNameTable;
import com.l2jserver.gameserver.model.L2Augmentation;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Transformation;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.FortSiege;
import com.l2jserver.gameserver.model.entity.Siege;
import com.l2jserver.gameserver.model.entity.TvTEventTeam;
import com.l2jserver.gameserver.model.itemcontainer.ItemContainer;
import com.l2jserver.gameserver.model.items.instance.L2HennaInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.scripting.scriptengine.impl.L2Script;

/**
 * An example class of using Listeners.
 * @author UnAfraid
 */
public class Listeners extends L2Script
{
	private static final Logger _log = Logger.getLogger(Listeners.class.getName());
	
	public Listeners(String name, String descr)
	{
		super(name, descr);
	}
	
	@Override
	public void init()
	{
		addLoginLogoutNotify();
		addClanCreationLevelUpNotify();
		addFortSiegeNotify();
		addSiegeNotify();
		addTvTNotify();
		addItemAugmentNotify();
		addItemDropPickupNotify();
		addHennaNotify();
	}
	
	/**
	 * Fired when a player logs in
	 * @param player
	 */
	@Override
	public void onPlayerLogin(L2PcInstance player)
	{
		_log.log(Level.INFO, "Player " + player.getName() + " just logged in!");
		List<Integer> items = new FastList<Integer>();
		for (L2ItemInstance item : player.getInventory().getItems())
		{
			items.add(item.getItemId());
		}
		addItemTracker(items);
		addTransformNotify(player);
		addSkillUseNotify(player, -1);
		addAttackNotify(player);
	}
	
	/**
	 * Fired when a player logs out
	 * @param player
	 */
	@Override
	public void onPlayerLogout(L2PcInstance player)
	{
		_log.log(Level.INFO, "Player " + player.getName() + " just logged out!");
		removeTransformNotify(player);
		removeSkillUseNotify(player);
		removeAttackNotify(player);
	}
	
	/**
	 * Fired when a clan is created Register the listener using addClanCreationLevelUpNotify()
	 * @param clan
	 */
	@Override
	public void onClanCreated(L2Clan clan)
	{
		_log.log(Level.INFO, "Clan " + clan.getName() + " has been created by " + clan.getLeaderName() + "!");
	}
	
	/**
	 * Fired when a clan levels up<br>
	 * Register the listener using addClanCreationLevelUpListener()
	 * @param clan
	 */
	@Override
	public boolean onClanLeveledUp(L2Clan clan, int oldLevel)
	{
		_log.log(Level.INFO, "Clan " + clan.getName() + " has leveled up!");
		return true;
	}
	
	/**
	 * Fired when a player joins a clan<br>
	 * Register the listener with addClanJoinLeaveNotify()<br>
	 * @param player
	 * @param clan
	 */
	@Override
	public boolean onClanJoin(L2PcInstance player, L2Clan clan)
	{
		_log.log(Level.INFO, "Player " + player.getName() + " has joined clan: " + clan.getName() + "!");
		return true;
	}
	
	/**
	 * Fired when a player leaves a clan<br>
	 * Register the listener with addClanJoinLeaveNotify()<br>
	 * @param clan
	 */
	@Override
	public boolean onClanLeave(int playerObjId, L2Clan clan)
	{
		String name = CharNameTable.getInstance().getNameById(playerObjId);
		_log.log(Level.INFO, "Player " + name + " has leaved clan: " + clan.getName() + "!");
		return true;
	}
	
	/**
	 * Fired when a clan leader is changed for another<br>
	 * Register the listener with addClanJoinLeaveNotify()<br>
	 * @param player
	 * @param clan
	 */
	@Override
	public boolean onClanLeaderChange(L2PcInstance player, L2Clan clan)
	{
		_log.log(Level.INFO, "Player " + player.getName() + " become the new leader of clan: " + clan.getName() + "!");
		return true;
	}
	
	/**
	 * Fired when an item is added to a clan warehouse<br>
	 * Register the listener with addClanWarehouseNotify(L2Clan)
	 * @param process
	 * @param item
	 * @param actor
	 */
	@Override
	public boolean onClanWarehouseAddItem(String process, L2ItemInstance item, L2PcInstance actor)
	{
		_log.log(Level.INFO, "Player " + actor.getName() + " added an item (" + item + ") to clan warehouse (" + process + ")!");
		return true;
	}
	
	/**
	 * Fired when an item is deleted from a clan warehouse<br>
	 * Register the listener with addClanWarehouseNotify(L2Clan)
	 * @param process
	 * @param item
	 * @param count
	 * @param actor
	 */
	@Override
	public boolean onClanWarehouseDeleteItem(String process, L2ItemInstance item, long count, L2PcInstance actor)
	{
		_log.log(Level.INFO, "Player " + actor.getName() + " removed an item (" + item + ") from clan warehouse (" + process + ")!");
		return true;
	}
	
	/**
	 * Fired when an item is transfered from/to a clan warehouse<br>
	 * Register the listener with addClanWarehouseNotify(L2Clan)
	 * @param process
	 * @param item
	 * @param count
	 * @param target
	 * @param actor
	 */
	@Override
	public boolean onClanWarehouseTransferItem(String process, L2ItemInstance item, long count, ItemContainer target, L2PcInstance actor)
	{
		_log.log(Level.INFO, "Player " + actor.getName() + " transfered an item (" + item + ") from clan warehouse to " + target + " (" + process + ")!");
		return true;
	}
	
	/**
	 * Fired when a clan war starts or ends<br>
	 * Register the listener witn addClanWarNotify()
	 * @param clan1
	 * @param clan2
	 * @param stage
	 */
	@Override
	public boolean onClanWarEvent(L2Clan clan1, L2Clan clan2, EventStage stage)
	{
		_log.log(Level.INFO, "Clan " + clan1.getName() + " challanges " + clan2.getName() + " stage: " + stage.toString() + "!");
		return true;
	}
	
	/**
	 * Fired when a fort siege starts or ends<br>
	 * Register using addFortSiegeNotify()
	 * @param fortSiege
	 * @param stage
	 */
	@Override
	public boolean onFortSiegeEvent(FortSiege fortSiege, EventStage stage)
	{
		_log.log(Level.INFO, "FortSiege event: " + fortSiege.getFort().getName() + " " + fortSiege + " " + stage.toString() + "!");
		return true;
	}
	
	/**
	 * Fired when a castle siege starts or ends<br>
	 * Register using addSiegeNotify()
	 * @param siege
	 * @param stage
	 */
	@Override
	public boolean onSiegeEvent(Siege siege, EventStage stage)
	{
		_log.log(Level.INFO, "Siege event: " + siege.getCastle().getName() + " " + siege + " " + stage.toString() + "!");
		return true;
	}
	
	/**
	 * Fired when the control of a castle changes during a siege<br>
	 * Register using addSiegeNotify()
	 * @param siege
	 */
	@Override
	public void onCastleControlChange(Siege siege)
	{
		_log.log(Level.INFO, "Castle control change: " + siege.getCastle().getName() + " " + siege + "!");
	}
	
	/**
	 * Notifies of TvT events<br>
	 * Register using addTvtNotify()
	 * @param stage
	 */
	@Override
	public void onTvtEvent(EventStage stage)
	{
		_log.log(Level.INFO, "TvT event: " + stage.toString() + "!");
	}
	
	/**
	 * Notifies that a player was killed during TvT<br>
	 * Register using addTvtNotify()
	 * @param killed
	 * @param killer
	 * @param killerTeam
	 */
	@Override
	public void onTvtKill(L2PcInstance killed, L2PcInstance killer, TvTEventTeam killerTeam)
	{
		_log.log(Level.INFO, "TvT event killed " + killed.getName() + " killer " + killer.getName() + " killer team: " + killerTeam.getName() + "!");
	}
	
	/**
	 * triggered when an item is augmented or when the augmentation is removed<br>
	 * Register using addItemAugmentNotify()
	 * @param item
	 * @param augmentation
	 * @param augment -> false = remove augment
	 */
	@Override
	public boolean onItemAugment(L2ItemInstance item, L2Augmentation augmentation, boolean augment)
	{
		_log.log(Level.INFO, "Item (" + item.getName() + " has been augumented added = " + augment + "!");
		return true;
	}
	
	/**
	 * Fired when an item is dropped by a player<br>
	 * Register using addItemDropPickupNotify()
	 * @param item
	 * @param dropper
	 * @param x
	 * @param y
	 * @param z
	 */
	@Override
	public boolean onItemDrop(L2ItemInstance item, L2PcInstance dropper, int x, int y, int z)
	{
		_log.log(Level.INFO, "Item (" + item.getName() + " has been dropped by (" + dropper.getName() + " ) at X: " + x + " Y: " + y + " Z: " + z + "!");
		return true;
	}
	
	/**
	 * Fired when an item is picked up by a player<br>
	 * Register using addItemDropPickupNotify()
	 * @param item
	 * @param dropper
	 * @param x
	 * @param y
	 * @param z
	 */
	@Override
	public boolean onItemPickup(L2ItemInstance item, L2PcInstance dropper, int x, int y, int z)
	{
		_log.log(Level.INFO, "Item (" + item.getName() + " has been pickup by (" + dropper.getName() + " ) from X: " + x + " Y: " + y + " Z: " + z + "!");
		return true;
	}
	
	/**
	 * Fired when a player's henna changes (add/remove)<br>
	 * Register using addHennaNotify()
	 * @param player
	 * @param henna
	 * @param add -> false = remove
	 */
	@Override
	public boolean onHennaModify(L2PcInstance player, L2HennaInstance henna, boolean add)
	{
		_log.log(Level.INFO, "Henna Modify: player: " + player.getName() + " henna: " + henna.getName() + " added: " + add);
		return true;
	}
	
	/**
	 * Fired when an item on the item tracker list has an event<br>
	 * Register using addItemTracker(itemIds)
	 * @param item
	 * @param player
	 * @param target
	 * @param event
	 */
	@Override
	public void onItemTrackerEvent(L2ItemInstance item, L2PcInstance player, ItemContainer target, ItemTrackerEvent event)
	{
		_log.log(Level.INFO, "ItemTrackerEvent: " + item.getName() + " has been " + event + " owner: " + player + " target: " + target);
	}
	
	/**
	 * Fired when an item is created<br>
	 * Register using addNewItemNotify(itemIds)
	 */
	@Override
	public boolean onItemCreate(int itemId, L2PcInstance player)
	{
		_log.log(Level.INFO, "ItemTrackerEvent: " + itemId + " has been created owner: " + player.getName());
		return true;
	}
	
	/**
	 * Fired when a player transforms/untransforms<br>
	 * Register using addTransformNotify(player)
	 * @param player
	 * @param transformation
	 * @param transform -> false = untransform
	 */
	@Override
	public boolean onPlayerTransform(L2PcInstance player, L2Transformation transformation, boolean transform)
	{
		_log.log(Level.INFO, "Player (" + player + ") has been transformed to " + transformation.toString() + " transform: " + transform);
		return true;
	}
	
	/**
	 * Fired when a L2Character registered with addAttackNotify is either attacked or attacks another L2Character
	 * @param target
	 * @param attacker
	 */
	@Override
	public boolean onAttack(L2Character target, L2Character attacker)
	{
		_log.log(Level.INFO, target + " has been attacked by " + attacker);
		return true;
	}
	
	/**
	 * Fired when a SKillUseListener gets triggered.<br>
	 * Register using addSkillUseNotify()
	 * @param skill
	 * @param caster
	 * @param targets
	 */
	@Override
	public boolean onUseSkill(L2Skill skill, L2Character caster, L2Object[] targets)
	{
		_log.log(Level.INFO, skill + " has been used by " + caster);
		return true;
	}
	
	public static void main(String[] args)
	{
		new Listeners(Listeners.class.getSimpleName(), "custom");
	}
}