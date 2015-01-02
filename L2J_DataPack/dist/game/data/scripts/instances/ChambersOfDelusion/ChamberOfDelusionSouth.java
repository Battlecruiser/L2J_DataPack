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
package instances.ChambersOfDelusion;

import com.l2jserver.gameserver.model.Location;

/**
 * Chamber of Delusion South.
 * @author GKR
 */
public final class ChamberOfDelusionSouth extends Chamber
{
	// NPC's
	private static final int ENTRANCE_GATEKEEPER = 32660;
	private static final int ROOM_GATEKEEPER_FIRST = 32674;
	private static final int ROOM_GATEKEEPER_LAST = 32678;
	private static final int AENKINEL = 25692;
	private static final int BOX = 18838;
	
	// Misc
	private static final Location[] ENTER_POINTS = new Location[]
	{
		new Location(-122368, -207820, -6720),
		new Location(-122368, -206940, -6720),
		new Location(-122368, -209116, -6720),
		new Location(-121456, -207356, -6720),
		new Location(-121440, -209004, -6720), // Raid room
	};
	private static final int INSTANCEID = 129; // this is the client number
	private static final String INSTANCE_TEMPLATE = "ChamberOfDelusionSouth.xml";
	
	private ChamberOfDelusionSouth()
	{
		super(-1, ChamberOfDelusionSouth.class.getSimpleName(), "instances", INSTANCEID, INSTANCE_TEMPLATE, ENTRANCE_GATEKEEPER, ROOM_GATEKEEPER_FIRST, ROOM_GATEKEEPER_LAST, AENKINEL, BOX);
		ROOM_ENTER_POINTS = ENTER_POINTS;
	}
	
	public static void main(String[] args)
	{
		new ChamberOfDelusionSouth();
	}
}