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

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Handermonkey AI.
 * @author Gladicek
 */
public final class Handermonkey extends AbstractNpcAI
{
	// NPC
	private static final int HANDERMONKEY = 33203;
	
	private Handermonkey()
	{
		super(Handermonkey.class.getSimpleName(), "ai/npc");
		addSpawnId(HANDERMONKEY);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("npc_move"))
		{
			if (getRandom(100) > 30)
			{
				final int locX = (npc.getSpawn().getX() - 70) + getRandom(100);
				final int locY = (npc.getSpawn().getY() - 70) + getRandom(100);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location(locX, locY, npc.getZ(), 0));
			}
			else
			{
				npc.broadcastSocialAction(9);
			}
		}
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setRunning();
		startQuestTimer("npc_move", 5000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Handermonkey();
	}
}