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
package ai.group_template;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Lair of Antharas AI.
 * @author St3eT, UnAfraid
 */
public final class LairOfAntharas extends AbstractNpcAI
{
	// NPC
	final private static int KNORIKS = 22857;
	final private static int DRAGON_KNIGHT = 22844;
	final private static int DRAGON_KNIGHT2 = 22845;
	final private static int ELITE_DRAGON_KNIGHT = 22846;
	
	// Misc
	final private static int KNIGHT_CHANCE = 30;
	final private static int KNORIKS_CHANCE = 60;
	final private static int KNORIKS_CHANCE2 = 50;
	
	private LairOfAntharas()
	{
		super(LairOfAntharas.class.getSimpleName(), "ai/group_template");
		addKillId(DRAGON_KNIGHT, DRAGON_KNIGHT2);
		addSpawnId(DRAGON_KNIGHT, DRAGON_KNIGHT2);
		addAggroRangeEnterId(KNORIKS);
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(DRAGON_KNIGHT))
		{
			onSpawn(spawn.getLastSpawn());
		}
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(DRAGON_KNIGHT2))
		{
			onSpawn(spawn.getLastSpawn());
		}
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (npc.isScriptValue(0) && (getRandom(100) < KNORIKS_CHANCE))
		{
			if (getRandom(100) < KNORIKS_CHANCE2)
			{
				npc.setScriptValue(1);
			}
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_SHOUT, npc.getId(), NpcStringId.WHOS_THERE_IF_YOU_DISTURB_THE_TEMPER_OF_THE_GREAT_LAND_DRAGON_ANTHARAS_I_WILL_NEVER_FORGIVE_YOU), 1000);
		}
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		switch (npc.getId())
		{
			case DRAGON_KNIGHT:
			{
				if (getRandom(100) > KNIGHT_CHANCE)
				{
					final L2Attackable newKnight = (L2Attackable) addSpawn(DRAGON_KNIGHT2, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), false, 0, true);
					npc.deleteMe();
					newKnight.broadcastPacket(new NpcSay(newKnight.getObjectId(), Say2.NPC_SHOUT, DRAGON_KNIGHT2, NpcStringId.THOSE_WHO_SET_FOOT_IN_THIS_PLACE_SHALL_NOT_LEAVE_ALIVE));
					attackPlayer(newKnight, killer);
				}
				break;
			}
			case DRAGON_KNIGHT2:
			{
				if (getRandom(100) > KNIGHT_CHANCE)
				{
					final L2Attackable eliteKnight = (L2Attackable) addSpawn(ELITE_DRAGON_KNIGHT, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), false, 0, true);
					npc.deleteMe();
					eliteKnight.broadcastPacket(new NpcSay(eliteKnight.getObjectId(), Say2.NPC_SHOUT, DRAGON_KNIGHT2, NpcStringId.IF_YOU_WISH_TO_SEE_HELL_I_WILL_GRANT_YOU_YOUR_WISH));
					attackPlayer(eliteKnight, killer);
				}
				break;
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		((L2Attackable) npc).setOnKillDelay(0);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new LairOfAntharas();
	}
}