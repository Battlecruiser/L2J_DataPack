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
package gracia.vehicles.SoIController;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.VehiclePathPoint;

import gracia.vehicles.AirShipController;

public final class SoIController extends AirShipController
{
	private static final int DOCK_ZONE = 50600;
	private static final int LOCATION = 101;
	private static final int CONTROLLER_ID = 32604;
	
	private static final VehiclePathPoint[] ARRIVAL =
	{
		new VehiclePathPoint(-214422, 211396, 5000, 280, 2000),
		new VehiclePathPoint(-214422, 211396, 4422, 280, 2000)
	};
	
	private static final VehiclePathPoint[] DEPART =
	{
		new VehiclePathPoint(-214422, 211396, 5000, 280, 2000),
		new VehiclePathPoint(-215877, 209709, 5000, 280, 2000)
	};
	
	private static final VehiclePathPoint[][] TELEPORTS =
	{
		{
			new VehiclePathPoint(-214422, 211396, 5000, 280, 2000),
			new VehiclePathPoint(-215877, 209709, 5000, 280, 2000),
			new VehiclePathPoint(-206692, 220997, 3000, 0, 0)
		},
		{
			new VehiclePathPoint(-214422, 211396, 5000, 280, 2000),
			new VehiclePathPoint(-215877, 209709, 5000, 280, 2000),
			new VehiclePathPoint(-195357, 233430, 2500, 0, 0)
		}
	};
	
	private static final int[] FUEL =
	{
		0,
		50
	};
	
	public SoIController()
	{
		super(-1, SoIController.class.getSimpleName(), "gracia/vehicles");
		addStartNpc(CONTROLLER_ID);
		addFirstTalkId(CONTROLLER_ID);
		addTalkId(CONTROLLER_ID);
		
		_dockZone = DOCK_ZONE;
		addEnterZoneId(DOCK_ZONE);
		addExitZoneId(DOCK_ZONE);
		
		_shipSpawnX = -212719;
		_shipSpawnY = 213348;
		_shipSpawnZ = 5000;
		
		_oustLoc = new Location(-213401, 210401, 4408);
		
		_locationId = LOCATION;
		_arrivalPath = ARRIVAL;
		_departPath = DEPART;
		_teleportsTable = TELEPORTS;
		_fuelTable = FUEL;
		
		_movieId = 1002;
		
		validityCheck();
	}
}