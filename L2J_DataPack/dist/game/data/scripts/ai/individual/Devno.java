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
package ai.individual;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.enums.ChatType;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;

/**
 * Devno AI.
 * @author Gladicek
 */
public final class Devno extends AbstractNpcAI
{
	// NPC
	private static final int DEVNO = 33241;
	// Misc
	private static final NpcStringId[] DEVNO_SHOUT =
	{
		NpcStringId.CARRY_OUT_YOUR_QUESTS_FAITHFULLY_IN_TALKING_ISLAND_AND_YOU_LL_GET_TO_THE_1ST_CLASS_TRANSFER_IN_NO_TIME,
		NpcStringId.I_SEE_THAT_ADVENTURERS_ARE_RETURNING_TO_TALKING_ISLAND_FOR_THE_AWAKENING,
		NpcStringId.YOU_CAN_SEE_VARIOUS_STATISTICS_IN_THE_MUSEUM_STATS_IN_THE_MAIN_MENU
	};
	private final static Location[] DEVNO_LOC =
	{
		new Location(-114448, 259106, -1203),
		new Location(-114565, 258686, -1203),
		new Location(-115047, 258883, -1204),
		new Location(-114904, 259038, -1203),
		new Location(-114673, 258981, -1203),
		new Location(-114595, 259277, -1203),
		new Location(-114866, 259350, -1203),
		new Location(-114601, 258926, -1203),
		new Location(-114702, 259080, -1203),
		new Location(-114973, 259306, -1203),
	};
	
	private Devno()
	{
		super(Devno.class.getSimpleName(), "ai/individual");
		addSpawnId(DEVNO);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("npc_move") && (npc != null))
		{
			if (getRandom(100) > 40)
			{
				broadcastNpcSay(npc, ChatType.NPC_GENERAL, DEVNO_SHOUT[getRandom(3)], 1000);
				addMoveToDesire(npc, DEVNO_LOC[getRandom(10)], 0);
			}
			else
			{
				broadcastNpcSay(npc, ChatType.NPC_GENERAL, DEVNO_SHOUT[getRandom(3)], 1000);
			}
		}
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("npc_move", 10000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Devno();
	}
}