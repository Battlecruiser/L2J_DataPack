/*
 * Copyright (C) 2004-2013 L2J DataPack
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
package custom.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jserver.gameserver.datatables.CharNameTable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.instance.L2ItemInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.scripting.scriptengine.events.AttackEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.AugmentEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanCreationEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanJoinEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanLeaderChangeEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanLeaveEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanLevelUpEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanWarEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanWarehouseAddItemEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanWarehouseDeleteItemEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ClanWarehouseTransferEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.DlgAnswerEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.FortSiegeEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.HennaEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ItemCreateEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ItemDropEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.ItemPickupEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.PlayerEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.RequestBypassToServerEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.SiegeEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.SkillUseEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.TransformEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.TvtKillEvent;
import com.l2jserver.gameserver.scripting.scriptengine.events.impl.L2Event;
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
		addLoginLogoutNotify();
		addClanCreationLevelUpNotify();
		addFortSiegeNotify();
		addSiegeNotify();
		addTvTNotify();
		addItemAugmentNotify();
		addItemDropPickupNotify();
		addHennaNotify();
		addDlgAnswerNotify(SystemMessageId.RESSURECTION_REQUEST_BY_C1_FOR_S2_XP.getId());
		addRequestBypassToServerNotify();
		addPlayerNotify();
	}
	
	/**
	 * Fired when a player logs in
	 * @param player
	 */
	@Override
	public void onPlayerLogin(L2PcInstance player)
	{
		_log.log(Level.INFO, "Player " + player.getName() + " just logged in!");
		List<Integer> items = new ArrayList<>();
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
		removeDlgAnswerNotify();
		removeRequestBypassToServerNotify();
		removePlayerNotify();
	}
	
	/**
	 * Fired when a clan is created Register the listener using addClanCreationLevelUpNotify()
	 * @param event
	 */
	@Override
	public void onClanCreated(ClanCreationEvent event)
	{
		_log.log(Level.INFO, "Clan " + event.getClan().getName() + " has been created by " + event.getClan().getLeaderName() + "!");
	}
	
	/**
	 * Fired when a clan levels up<br>
	 * Register the listener using addClanCreationLevelUpListener()
	 * @param event
	 */
	@Override
	public boolean onClanLeveledUp(ClanLevelUpEvent event)
	{
		_log.log(Level.INFO, "Clan " + event.getClan().getName() + " has leveled up!");
		return true;
	}
	
	/**
	 * Fired when a player joins a clan<br>
	 * Register the listener with addClanJoinLeaveNotify()<br>
	 * @param event
	 */
	@Override
	public boolean onClanJoin(ClanJoinEvent event)
	{
		_log.log(Level.INFO, "Player " + event.getPlayer().getName() + " has joined clan: " + event.getPlayer().getName() + "!");
		return true;
	}
	
	/**
	 * Fired when a player leaves a clan<br>
	 * Register the listener with addClanJoinLeaveNotify()<br>
	 * @param event
	 */
	@Override
	public boolean onClanLeave(ClanLeaveEvent event)
	{
		String name = CharNameTable.getInstance().getNameById(event.getPlayerId());
		_log.log(Level.INFO, "Player " + name + " has leaved clan: " + event.getClan().getName() + "!");
		return true;
	}
	
	/**
	 * Fired when a clan leader is changed for another<br>
	 * Register the listener with addClanJoinLeaveNotify()<br>
	 */
	@Override
	public boolean onClanLeaderChange(ClanLeaderChangeEvent event)
	{
		_log.log(Level.INFO, "Player " + event.getNewLeader().getName() + " become the new leader of clan: " + event.getClan().getName() + "!");
		return true;
	}
	
	/**
	 * Fired when an item is added to a clan warehouse<br>
	 * Register the listener with addClanWarehouseNotify(L2Clan)
	 * @param event
	 */
	@Override
	public boolean onClanWarehouseAddItem(ClanWarehouseAddItemEvent event)
	{
		_log.log(Level.INFO, "Player " + event.getActor().getName() + " added an item (" + event.getItem() + ") to clan warehouse (" + event.getProcess() + ")!");
		return true;
	}
	
	/**
	 * Fired when an item is deleted from a clan warehouse<br>
	 * Register the listener with addClanWarehouseNotify(L2Clan)
	 * @param event
	 */
	@Override
	public boolean onClanWarehouseDeleteItem(ClanWarehouseDeleteItemEvent event)
	{
		_log.log(Level.INFO, "Player " + event.getActor().getName() + " removed an item (" + event.getItem() + ") from clan warehouse (" + event.getProcess() + ")!");
		return true;
	}
	
	/**
	 * Fired when an item is transfered from/to a clan warehouse<br>
	 * Register the listener with addClanWarehouseNotify(L2Clan)
	 * @param event
	 */
	@Override
	public boolean onClanWarehouseTransferItem(ClanWarehouseTransferEvent event)
	{
		_log.log(Level.INFO, "Player " + event.getActor().getName() + " transfered an item (" + event.getItem() + ") from clan warehouse to " + event.getTarget() + " (" + event.getProcess() + ")!");
		return true;
	}
	
	/**
	 * Fired when a clan war starts or ends<br>
	 * Register the listener witn addClanWarNotify()
	 * @param event
	 */
	@Override
	public boolean onClanWarEvent(ClanWarEvent event)
	{
		_log.log(Level.INFO, "Clan " + event.getClan1().getName() + " challanges " + event.getClan2().getName() + " stage: " + event.getStage().toString() + "!");
		return true;
	}
	
	/**
	 * Fired when a fort siege starts or ends<br>
	 * Register using addFortSiegeNotify()
	 * @param event
	 */
	@Override
	public boolean onFortSiegeEvent(FortSiegeEvent event)
	{
		_log.log(Level.INFO, "FortSiege event: " + event.getSiege().getFort().getName() + " " + event.getSiege() + " " + event.getStage().toString() + "!");
		return true;
	}
	
	/**
	 * Fired when a castle siege starts or ends<br>
	 * Register using addSiegeNotify()
	 * @param event
	 */
	@Override
	public boolean onSiegeEvent(SiegeEvent event)
	{
		_log.log(Level.INFO, "Siege event: " + event.getSiege().getCastle().getName() + " " + event.getSiege() + " " + event.getStage().toString() + "!");
		return true;
	}
	
	/**
	 * Fired when the control of a castle changes during a siege<br>
	 * Register using addSiegeNotify()
	 * @param event
	 */
	@Override
	public void onCastleControlChange(SiegeEvent event)
	{
		_log.log(Level.INFO, "Castle control change: " + event.getSiege().getCastle().getName() + " " + event.getSiege() + "!");
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
	 * @param event
	 */
	@Override
	public void onTvtKill(TvtKillEvent event)
	{
		_log.log(Level.INFO, "TvT event killed " + event.getVictim().getName() + " killer " + event.getKiller().getName() + " killer team: " + event.getKillerTeam().getName() + "!");
	}
	
	/**
	 * triggered when an item is augmented or when the augmentation is removed<br>
	 * Register using addItemAugmentNotify()
	 * @param event
	 */
	@Override
	public boolean onItemAugment(AugmentEvent event)
	{
		_log.log(Level.INFO, "Item (" + event.getItem().getName() + " has been augumented added = " + event.getAugmentation() + "!");
		return true;
	}
	
	/**
	 * Fired when an item is dropped by a player<br>
	 * Register using addItemDropPickupNotify()
	 * @param event
	 */
	@Override
	public boolean onItemDrop(ItemDropEvent event)
	{
		_log.log(Level.INFO, "Item (" + event.getItem().getName() + " has been dropped by (" + event.getDropper().getName() + " ) at: " + event.getLocation() + "!");
		return true;
	}
	
	/**
	 * Fired when an item is picked up by a player<br>
	 * Register using addItemDropPickupNotify()
	 * @param event
	 */
	@Override
	public boolean onItemPickup(ItemPickupEvent event)
	{
		_log.log(Level.INFO, "Item (" + event.getItem().getName() + " has been pickup by (" + event.getPicker().getName() + " ) from: " + event.getLocation() + "!");
		return true;
	}
	
	/**
	 * Fired when a player's henna changes (add/remove)<br>
	 * Register using addHennaNotify()
	 * @param event
	 */
	@Override
	public boolean onHennaModify(HennaEvent event)
	{
		_log.log(Level.INFO, "Henna Modify: player: " + event.getPlayer().getName() + " henna: " + event.getHenna().getDyeName() + " added: " + event.isAdd());
		return true;
	}
	
	/**
	 * Fired when an item on the item tracker list has an event<br>
	 * Register using addItemTracker(itemIds)
	 * @param event
	 */
	@Override
	public void onItemTrackerEvent(L2Event event)
	{
		//_log.log(Level.INFO, "ItemTrackerEvent: " + event.getName() + " has been " + event + " owner: " + player + " target: " + target);
		// TODO: Fix it?
	}
	
	/**
	 * Fired when an item is created<br>
	 * Register using addNewItemNotify(itemIds)
	 * @param event
	 */
	@Override
	public boolean onItemCreate(ItemCreateEvent event)
	{
		_log.log(Level.INFO, "ItemTrackerEvent: " + event.getItemId() + " has been created owner: " + event.getPlayer().getName());
		return true;
	}
	
	/**
	 * Fired when a player transforms/untransforms<br>
	 * Register using addTransformNotify(player)
	 * @param event
	 */
	@Override
	public boolean onPlayerTransform(TransformEvent event)
	{
		_log.log(Level.INFO, "Player (" + event.getTransformation().getPlayer() + ") has been transformed to " + event.getTransformation().toString() + " transform: " + event.isTransforming());
		return true;
	}
	
	/**
	 * Fired when a L2Character registered with addAttackNotify is either attacked or attacks another L2Character
	 * @param event
	 */
	@Override
	public boolean onAttack(AttackEvent event)
	{
		_log.log(Level.INFO, event.getTarget() + " has been attacked by " + event.getAttacker());
		return true;
	}
	
	/**
	 * Fired when a SKillUseListener gets triggered.<br>
	 * Register using addSkillUseNotify()
	 * @param event
	 */
	@Override
	public boolean onUseSkill(SkillUseEvent event)
	{
		_log.log(Level.INFO, event.getSkill() + " has been used by " + event.getCaster());
		return true;
	}
	
	/**
	 * Fired when client answer on dialog request<br>
	 * Register using addDlgAnswerNotify()
	 * @param event
	 */
	@Override
	public void onDlgAnswer(DlgAnswerEvent event)
	{
		_log.log(Level.INFO, event.getActiveChar() + " has been answered on " + event.getMessageId() + " with " + event.getAnswer() + " requester: " + event.getRequesterId());
	}
	
	/**
	 * Fired when client answer on dialog request<br>
	 * Register using addDlgAnswerNotify()
	 * @param event
	 */
	@Override
	protected void onRequestBypassToServer(RequestBypassToServerEvent event)
	{
		_log.log(Level.INFO, event.getActiveChar() + " has sent command to server: " + event.getCommand());
	}
	
	/**
	 * Fired when client select a player<br>
	 * Register using addPlayerNotify()
	 * @param event
	 */
	@Override
	protected void onCharSelect(PlayerEvent event)
	{
		_log.log(Level.INFO, event.getClient() + " has selected char: " + event.getName());
	}
	
	/**
	 * Fired when client create a character<br>
	 * Register using addPlayerNotify()
	 * @param event
	 */
	@Override
	protected void onCharCreate(PlayerEvent event)
	{
		_log.log(Level.INFO, event.getClient() + " has created char: " + event.getName());
	}
	
	/**
	 * Fired when client select a character for delete<br>
	 * Register using addPlayerNotify()
	 * @param event
	 */
	@Override
	protected void onCharDelete(PlayerEvent event)
	{
		_log.log(Level.INFO, event.getClient() + " has deleted char: " + event.getName());
	}
	
	/**
	 * Fired when client select a character for restore<br>
	 * Register using addPlayerNotify()
	 * @param event
	 */
	@Override
	protected void onCharRestore(PlayerEvent event)
	{
		_log.log(Level.INFO, event.getClient() + " has restored char: " + event.getName());
	}
	
	public static void main(String[] args)
	{
		new Listeners(Listeners.class.getSimpleName(), "custom");
	}
}