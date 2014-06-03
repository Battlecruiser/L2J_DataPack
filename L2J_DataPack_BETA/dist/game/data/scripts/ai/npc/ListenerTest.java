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
package ai.npc;

import java.util.logging.Level;

import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.annotations.Npc;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.impl.character.OnCreatureKill;
import com.l2jserver.gameserver.model.events.impl.character.npc.attackable.OnAttackableAttack;

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
		
		// An set function which is a Consumer it has one parameter and doesn't returns anything!
		setAttackableAttackId(this::onAttackableAttack, ELPIES);
		
		// An Add function which is Function it has one parameter and returns either null or a class that extends {@link com.l2jserver.gameserver.model.events.returns.AbstractEventReturn}
		addCreatureKillId(event ->
		{
			System.out.println("on " + event.getClass().getSimpleName() + " executed");
			return null;
		}, ELPIES);
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
	 * This method will be invoked as soon as L2Attackable (Rabbits 20432 and 22228) are being killed by L2PcInstance (a player)
	 * @param event
	 */
	@RegisterEvent(EventType.ON_CREATURE_KILL)
	@Npc(20432)
	@Npc(22228)
	public void onCreatureKill(OnCreatureKill event)
	{
		_log.log(Level.INFO, getClass().getSimpleName() + ": " + event.getClass().getSimpleName() + " invoked attacker: " + event.getAttacker() + " target: " + event.getTarget());
	}
	
	public static void main(String[] args)
	{
		new ListenerTest();
	}
}
