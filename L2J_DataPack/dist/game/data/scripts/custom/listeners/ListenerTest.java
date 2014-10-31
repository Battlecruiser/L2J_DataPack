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
package custom.listeners;

import java.util.logging.Level;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.events.Containers;
import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.ListenerRegisterType;
import com.l2jserver.gameserver.model.events.annotations.Id;
import com.l2jserver.gameserver.model.events.annotations.NpcLevelRange;
import com.l2jserver.gameserver.model.events.annotations.Priority;
import com.l2jserver.gameserver.model.events.annotations.Range;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.annotations.RegisterType;
import com.l2jserver.gameserver.model.events.impl.character.OnCreatureKill;
import com.l2jserver.gameserver.model.events.impl.character.npc.attackable.OnAttackableAttack;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerDlgAnswer;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerLogin;
import com.l2jserver.gameserver.model.events.impl.item.OnItemCreate;
import com.l2jserver.gameserver.model.events.impl.sieges.castle.OnCastleSiegeStart;
import com.l2jserver.gameserver.model.events.listeners.ConsumerEventListener;
import com.l2jserver.gameserver.model.events.returns.TerminateReturn;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.util.Rnd;

/**
 * An example usage of Listeners.
 * @author UnAfraid
 */
public class ListenerTest extends AbstractNpcAI
{
	private static final int[] ELPIES =
	{
		20432,
		22228
	};
	
	private ListenerTest()
	{
		super(ListenerTest.class.getSimpleName(), "ai/npc");
		
		// Method preset listener registration
		// An set function which is a Consumer it has one parameter and doesn't returns anything!
		setAttackableAttackId(this::onAttackableAttack, ELPIES);
		
		// Manual listener registration
		Containers.Global().addListener(new ConsumerEventListener(Containers.Global(), EventType.ON_PLAYER_DLG_ANSWER, (OnPlayerDlgAnswer event) ->
		{
			_log.log(Level.INFO, ListenerTest.class.getSimpleName() + ": " + event.getActiveChar() + " OnPlayerDlgAnswer: Answer: " + event.getAnswer() + " MessageId: " + event.getMessageId());
		}, this));
	}
	
	/**
	 * This method will be invoked as soon as an L2Attackable (Rabbits 20432 and 22228) is being attacked from L2PcInstance (a player)
	 * @param event
	 */
	public void onAttackableAttack(OnAttackableAttack event)
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": " + event.getClass().getSimpleName() + " invoked attacker: " + event.getAttacker() + " target: " + event.getTarget() + " damage: " + event.getDamage() + " skill: " + event.getSkill());
	}
	
	/**
	 * This method will be invoked as soon as L2Attackable (Rabbits 20432 and 22228) are being killed by L2PcInstance (a player)<br>
	 * This listener is registered into individual npcs container.
	 * @param event
	 */
	// Annotation listener registration
	@RegisterEvent(EventType.ON_CREATURE_KILL)
	@RegisterType(ListenerRegisterType.NPC)
	@Id(20432)
	@Id(22228)
	public void onCreatureKill(OnCreatureKill event)
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": " + event.getClass().getSimpleName() + " invoked attacker: " + event.getAttacker() + " target: " + event.getTarget());
	}
	
	/**
	 * This method will be invoked as soon as Siege of castle ids 1-9 starts<br>
	 * This listener is registered into individual castle container.
	 * @param event
	 */
	@RegisterEvent(EventType.ON_CASTLE_SIEGE_START)
	@RegisterType(ListenerRegisterType.CASTLE)
	@Range(from = 1, to = 9)
	public void onSiegeStart(OnCastleSiegeStart event)
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": The siege of " + event.getSiege().getCastle().getName() + " (" + event.getSiege().getCastle().getResidenceId() + ") has started!");
	}
	
	/**
	 * This method will be invoked as soon as Ancient Adena (5575) item is created on player's inventory (As new item!).<br>
	 * This listener is registered into individual items container.
	 * @param event
	 */
	@RegisterEvent(EventType.ON_ITEM_CREATE)
	@RegisterType(ListenerRegisterType.ITEM)
	@Id(5575)
	public void onItemCreate(OnItemCreate event)
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": Item [" + event.getItem() + "] has been created actor: " + event.getActiveChar() + " process: " + event.getProcess() + " reference: " + event.getReference());
	}
	
	/**
	 * Prioritized event notification <br>
	 * This method will be invoked as soon as creature from level range between 1 and 10 dies.<br>
	 * This listener is registered into individual npcs container.
	 * @param event
	 */
	@RegisterEvent(EventType.ON_CREATURE_KILL)
	@RegisterType(ListenerRegisterType.NPC)
	@NpcLevelRange(from = 1, to = 10)
	@Priority(100)
	public void OnCreatureKill(OnCreatureKill event)
	{
		// 70% chance to drop
		if (Rnd.get(100) >= 70)
		{
			return;
		}
		
		// Make sure a player killed this monster.
		if ((event.getAttacker() != null) && event.getAttacker().isPlayable() && event.getTarget().isAttackable())
		{
			final L2Attackable monster = (L2Attackable) event.getTarget();
			monster.dropItem(event.getAttacker().getActingPlayer(), new ItemHolder(57, Rnd.get(100, 1000)));
		}
	}
	
	/**
	 * This method will be invoked as soon a a player logs into the game.<br>
	 * This listener is registered into global players container.
	 * @param event
	 */
	@RegisterEvent(EventType.ON_PLAYER_LOGIN)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void onPlayerLogin(OnPlayerLogin event)
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": Player: " + event.getActiveChar() + " has logged in!");
	}
	
	/**
	 * Prioritized event notification - Ensuring that this listener will be the first to receive notification.<br>
	 * Also this method interrupts notification to other listeners and taking over return if somehow it wasn't the first one to set.<br>
	 * This method will be invoked as soon a a creature dies.<br>
	 * This listener is registered into global players container.
	 * @param event
	 * @return termination return preventing the base code execution if needed.
	 */
	@RegisterEvent(EventType.ON_CREATURE_KILL)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	@Priority(Integer.MAX_VALUE)
	public TerminateReturn onPlayerDeath(OnCreatureKill event)
	{
		if (event.getTarget().isGM())
		{
			_log.log(Level.INFO, getClass().getSimpleName() + ": Player: " + event.getTarget() + " was prevented from dying!");
			return new TerminateReturn(true, true, true);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new ListenerTest();
	}
}