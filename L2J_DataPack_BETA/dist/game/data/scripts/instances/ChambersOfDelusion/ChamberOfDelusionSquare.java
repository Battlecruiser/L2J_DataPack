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
package instances.ChambersOfDelusion;

import com.l2jserver.gameserver.model.Location;

/**
 * Chamber of Delusion Square.
 * @author GKR
 */
public class ChamberOfDelusionSquare extends Chamber
{
	// NPC's
	private static final int ENTRANCE_GATEKEEPER = 32662;
	private static final int ROOM_GATEKEEPER_FIRST = 32684;
	private static final int ROOM_GATEKEEPER_LAST = 32692;
	private static final int AENKINEL = 25694;
	private static final int BOX = 18820;
	
	// Misc
	private static final int INSTANCEID = 131;
	private static final String INSTANCE_TEMPLATE = "ChamberOfDelusionSquare.xml";
	
	public ChamberOfDelusionSquare(int questId, String name, String descr)
	{
		super(questId, name, descr, INSTANCEID, INSTANCE_TEMPLATE, ENTRANCE_GATEKEEPER, ROOM_GATEKEEPER_FIRST, ROOM_GATEKEEPER_LAST, AENKINEL, BOX);
		
		ROOM_ENTER_POINTS = new Location[]
		{
			new Location(-122368, -153388, -6688),
			new Location(-122368, -152524, -6688),
			new Location(-120480, -155116, -6688),
			new Location(-120480, -154236, -6688),
			new Location(-121440, -151212, -6688),
			new Location(-120464, -152908, -6688),
			new Location(-122368, -154700, -6688),
			new Location(-121440, -152908, -6688),
			new Location(-121440, -154572, -6688)
		// Raid room
		};
	}
	
	public static void main(String[] args)
	{
		new ChamberOfDelusionSquare(-1, ChamberOfDelusionSquare.class.getSimpleName(), "instances");
	}
}