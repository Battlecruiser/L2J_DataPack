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
package instances.ChambersOfDelusion;

import com.l2jserver.gameserver.model.Location;

/**
 * Chamber of Delusion West.
 * @author GKR
 */
public final class ChamberOfDelusionWest extends Chamber
{
	// NPC's
	private static final int ENTRANCE_GATEKEEPER = 32659;
	private static final int ROOM_GATEKEEPER_FIRST = 32669;
	private static final int ROOM_GATEKEEPER_LAST = 32673;
	private static final int AENKINEL = 25691;
	private static final int BOX = 18838;
	
	// Misc
	private static final Location[] ENTER_POINTS = new Location[]
	{
		new Location(-108960, -218892, -6720),
		new Location(-108976, -218028, -6720),
		new Location(-108960, -220204, -6720),
		new Location(-108032, -218428, -6720),
		new Location(-108032, -220140, -6720), // Raid room
	};
	private static final int INSTANCEID = 128; // this is the client number
	private static final String INSTANCE_TEMPLATE = "ChamberOfDelusionWest.xml";
	
	private ChamberOfDelusionWest()
	{
		super(-1, ChamberOfDelusionWest.class.getSimpleName(), "instances", INSTANCEID, INSTANCE_TEMPLATE, ENTRANCE_GATEKEEPER, ROOM_GATEKEEPER_FIRST, ROOM_GATEKEEPER_LAST, AENKINEL, BOX);
		ROOM_ENTER_POINTS = ENTER_POINTS;
	}
	
	public static void main(String[] args)
	{
		new ChamberOfDelusionWest();
	}
}