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
package ai.group_template;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlEvent;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Warrior Fishing Block AI.
 * @author Zoey76
 */
public class WarriorFishingBlock extends AbstractNpcAI
{
	// Monsters
	private static final int[] MONSTERS =
	{
		18319, // Caught Frog
		18320, // Caught Undine
		18321, // Caught Rakul
		18322, // Caught Sea Giant
		18323, // Caught Sea Horse Soldier
		18324, // Caught Homunculus
		18325, // Caught Flava
		18326, // Caught Gigantic Eye
	};
	// NPC Strings
	private static final NpcStringId[] NPC_STRINGS_ON_SPAWN =
	{
		NpcStringId.CROAK_CROAK_FOOD_LIKE_S1_IN_THIS_PLACE,
		NpcStringId.S1_HOW_LUCKY_I_AM,
		NpcStringId.PRAY_THAT_YOU_CAUGHT_A_WRONG_FISH_S1
	};
	private static final NpcStringId[] NPC_STRINGS_ON_ATTACK =
	{
		NpcStringId.DO_YOU_KNOW_WHAT_A_FROG_TASTES_LIKE,
		NpcStringId.I_WILL_SHOW_YOU_THE_POWER_OF_A_FROG,
		NpcStringId.I_WILL_SWALLOW_AT_A_MOUTHFUL
	};
	private static final NpcStringId[] NPC_STRINGS_ON_KILL =
	{
		NpcStringId.UGH_NO_CHANCE_HOW_COULD_THIS_ELDER_PASS_AWAY_LIKE_THIS,
		NpcStringId.CROAK_CROAK_A_FROG_IS_DYING,
		NpcStringId.A_FROG_TASTES_BAD_YUCK
	};
	// Misc
	private static final int CHANCE_TO_SHOUT_ON_ATTACK = 33;
	private static final int DESPAWN_TIME = 50000; // 50 seconds
	
	public WarriorFishingBlock()
	{
		super(WarriorFishingBlock.class.getSimpleName(), "ai/group_template");
		addAttackId(MONSTERS);
		addKillId(MONSTERS);
		addSpawnId(MONSTERS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if ((npc != null) && event.equals("DESPAWN"))
		{
			npc.deleteMe();
		}
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (getRandom(100) < CHANCE_TO_SHOUT_ON_ATTACK)
		{
			npc.broadcastPacket(new NpcSay(npc, Say2.NPC_ALL, NPC_STRINGS_ON_ATTACK[getRandom(NPC_STRINGS_ON_ATTACK.length)]));
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		npc.broadcastPacket(new NpcSay(npc, Say2.NPC_ALL, NPC_STRINGS_ON_KILL[getRandom(NPC_STRINGS_ON_KILL.length)]));
		cancelQuestTimer("DESPAWN", npc, killer);
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if ((npc == null) || !npc.isAttackable())
		{
			return null;
		}
		
		final L2Object target = npc.getTarget();
		if ((target == null) || !target.isPlayer())
		{
			npc.deleteMe();
			return null;
		}
		
		final L2PcInstance player = target.getActingPlayer();
		final NpcSay say = new NpcSay(npc, Say2.NPC_ALL, NPC_STRINGS_ON_SPAWN[getRandom(NPC_STRINGS_ON_SPAWN.length)]);
		say.addStringParameter(player.getName());
		npc.broadcastPacket(say);
		
		((L2Attackable) npc).addDamageHate(player, 0, 2000);
		npc.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, player);
		npc.addAttackerToAttackByList(player);
		
		startQuestTimer("DESPAWN", DESPAWN_TIME, npc, player);
		return null;
	}
	
	public static void main(String[] args)
	{
		new WarriorFishingBlock();
	}
}
