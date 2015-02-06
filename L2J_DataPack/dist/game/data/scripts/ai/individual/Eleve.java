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

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Eleve AI.
 * @author Gladicek
 */
public final class Eleve extends AbstractNpcAI
{
	// NPC
	private static final int ELEVE = 33246;
	// Misc
	private static final NpcStringId[] ELEVE_SHOUT =
	{
		NpcStringId.DON_T_KNOW_WHAT_TO_DO_LOOK_AT_THE_MAP,
		NpcStringId.DO_YOU_SEE_A_SCROLL_ICON_GO_THAT_LOCATION
	};
	private final static Location[] ELEVE_LOC =
	{
		new Location(-114936, 259918, -1203),
		new Location(-114687, 259872, -1203),
		new Location(-114552, 259699, -1203),
		new Location(-114689, 259453, -1203),
		new Location(-114990, 259335, -1203),
		new Location(-115142, 259523, -1203),
		new Location(-114894, 259137, -1203),
		new Location(-114832, 259363, -1203),
		new Location(-114809, 259260, -1203),
		new Location(-115036, 260006, -1203),
	};
	
	private Eleve()
	{
		super(Eleve.class.getSimpleName(), "ai/individual");
		addSpawnId(ELEVE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("npc_move") && (npc != null))
		{
			if (getRandom(100) > 40)
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, ELEVE_SHOUT[getRandom(2)], 1000);
				addMoveToDesire(npc, ELEVE_LOC[getRandom(10)], 0);
			}
			else
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, ELEVE_SHOUT[getRandom(2)], 1000);
			}
		}
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("npc_move", 6000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Eleve();
	}
}