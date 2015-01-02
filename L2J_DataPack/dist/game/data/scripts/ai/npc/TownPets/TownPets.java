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
package ai.npc.TownPets;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Town Pets AI
 * @author malyelfik
 */
public final class TownPets extends AbstractNpcAI
{
	// Pet IDs
	private static final int[] PETS =
	{
		31202, // Maximus
		31203, // Moon Dancer
		31204, // Georgio
		31205, // Katz
		31206, // Ten Ten
		31207, // Sardinia
		31208, // La Grange
		31209, // Misty Rain
		31266, // Kaiser
		31593, // Dorothy
		31758, // Rafi
		31955, // Ruby
	};
	
	private TownPets()
	{
		super(TownPets.class.getSimpleName(), "ai/npc");
		
		if (Config.ALLOW_PET_WALKERS)
		{
			addSpawnId(PETS);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("move"))
		{
			final int locX = (npc.getSpawn().getX() - 50) + getRandom(100);
			final int locY = (npc.getSpawn().getY() - 50) + getRandom(100);
			npc.setRunning();
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, new Location(locX, locY, npc.getZ(), 0));
			startQuestTimer("move", 5000, npc, null);
		}
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("move", 3000, npc, null);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new TownPets();
	}
}