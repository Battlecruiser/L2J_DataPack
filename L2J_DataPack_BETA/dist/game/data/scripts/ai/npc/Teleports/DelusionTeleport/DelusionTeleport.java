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
public final class DelusionTeleport extends AbstractNpcAI
{
	// NPCs
	// @formatter:off
	private static final int[] NPCS =
	{
		32484, 32658, 32659, 32660, 32661, 32662, 32663
	};
	// @formatter:on
	// Misc
	private static final Location[] HALL_LOCATIONS =
	{
		new Location(-114597, -152501, -6750),
		new Location(-114589, -154162, -6750)
	};
	
	private static final Map<Integer, Location> RETURN_LOCATIONS = new HashMap<>();
	
	static
	{
		RETURN_LOCATIONS.put(0, new Location(43835, -47749, -792)); // Undefined origin, return to Rune
		RETURN_LOCATIONS.put(7, new Location(-14023, 123677, -3112)); // Gludio
		RETURN_LOCATIONS.put(8, new Location(18101, 145936, -3088)); // Dion
		RETURN_LOCATIONS.put(10, new Location(80905, 56361, -1552)); // Oren
		RETURN_LOCATIONS.put(14, new Location(42772, -48062, -792)); // Rune
		RETURN_LOCATIONS.put(15, new Location(108469, 221690, -3592)); // Heine
		RETURN_LOCATIONS.put(17, new Location(85991, -142234, -1336)); // Schuttgart
	}
	
	private DelusionTeleport()
	{
		super(DelusionTeleport.class.getSimpleName(), "ai/npc/Teleports");
		addStartNpc(NPCS);
		addTalkId(NPCS);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		
		if (npc.getId() == NPCS[0])
		{
			final L2TownZone town = TownManager.getTown(npc.getX(), npc.getY(), npc.getZ());
			final int townId = ((town == null) ? 0 : town.getTownId());
			st.set("return_loc", Integer.toString(townId));
			player.teleToLocation(HALL_LOCATIONS[getRandom(HALL_LOCATIONS.length)], false);
		}
		else
		{
			player.teleToLocation(RETURN_LOCATIONS.get(st.getInt("return_loc")), true);
			st.exitQuest(true);
		}
		
		return "";
	}
	
	public static void main(String[] args)
	{
		new DelusionTeleport();
	}
}