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
package ai.npc.Teleports.DelusionTeleport;

import java.util.HashMap;
import java.util.Map;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.TownManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.zone.type.L2TownZone;

/**
 * Chambers of Delusion teleport AI.
 * @author GKR
 */
public class DelusionTeleport extends AbstractNpcAI
{
	// NPCs
	private final static int REWARDER_ONE = 32658;
	private final static int REWARDER_SIX = 32663;
	private final static int PATHFINDER = 32484;
	
	// Misc
	private final static Location[] HALL_LOCATION =
	{
		new Location(-114597, -152501, -6750),
		new Location(-114589, -154162, -6750)
	};
	
	private final static Map<Integer, Location> RETURN_LOCATION = new HashMap<>();
	
	static
	{
		RETURN_LOCATION.put(0, new Location(43835, -47749, -792)); // Undefined origin, return to Rune
		RETURN_LOCATION.put(7, new Location(-14023, 123677, -3112)); // Gludio
		RETURN_LOCATION.put(8, new Location(18101, 145936, -3088)); // Dion
		RETURN_LOCATION.put(10, new Location(80905, 56361, -1552)); // Oren
		RETURN_LOCATION.put(14, new Location(42772, -48062, -792)); // Rune
		RETURN_LOCATION.put(15, new Location(108469, 221690, -3592)); // Heine
		RETURN_LOCATION.put(17, new Location(85991, -142234, -1336)); // Schuttgart
	}
	
	public DelusionTeleport(String name, String descr)
	{
		super(name, descr);
		addStartNpc(PATHFINDER);
		addTalkId(PATHFINDER);
		
		for (int i = REWARDER_ONE; i <= REWARDER_SIX; i++)
		{
			addStartNpc(i);
			addTalkId(i);
		}
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		int npcId = npc.getNpcId();
		
		if (npcId == PATHFINDER)
		{
			int townId = 0;
			L2TownZone town = TownManager.getTown(npc.getX(), npc.getY(), npc.getZ());
			
			if (town != null)
			{
				townId = town.getTownId();
			}
			
			st.set("return_loc", Integer.toString(townId));
			player.teleToLocation(HALL_LOCATION[getRandom(2)], false);
		}
		
		else if ((npcId >= REWARDER_ONE) && (npcId <= REWARDER_SIX))
		{
			int townId = st.getInt("return_loc");
			Location pos = RETURN_LOCATION.containsKey(townId) ? RETURN_LOCATION.get(townId) : RETURN_LOCATION.get(0);
			player.teleToLocation(pos, true);
			st.exitQuest(true);
		}
		
		return "";
	}
	
	public static void main(String[] args)
	{
		new DelusionTeleport(DelusionTeleport.class.getSimpleName(), "ai/npc/Teleports/");
	}
}