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
 * Chamber of Delusion East.
 * @author GKR
 */
public class ChamberOfDelusionEast extends Chamber
{
	// NPCs
	private static final int ENTRANCE_GATEKEEPER = 32658;
	private static final int ROOM_GATEKEEPER_FIRST = 32664;
	private static final int ROOM_GATEKEEPER_LAST = 32668;
	private static final int AENKINEL = 25690;
	private static final int BOX = 18838;
	
	// Misc
	private static final int INSTANCEID = 127;
	private static final String INSTANCE_TEMPLATE = "ChamberOfDelusionEast.xml";
	
	public ChamberOfDelusionEast(int questId, String name, String descr)
	{
		super(questId, name, descr, INSTANCEID, INSTANCE_TEMPLATE, ENTRANCE_GATEKEEPER, ROOM_GATEKEEPER_FIRST, ROOM_GATEKEEPER_LAST, AENKINEL, BOX);
		
		ROOM_ENTER_POINTS = new Location[]
		{
			new Location(-122368, -218972, -6720),
			new Location(-122352, -218044, -6720),
			new Location(-122368, -220220, -6720),
			new Location(-121440, -218444, -6720),
			new Location(-121424, -220124, -6720)
		// Raid room
		};
	}
	
	public static void main(String[] args)
	{
		new ChamberOfDelusionEast(-1, ChamberOfDelusionEast.class.getSimpleName(), "instances");
	}
}