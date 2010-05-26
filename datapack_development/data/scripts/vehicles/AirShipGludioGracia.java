/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package vehicles;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.instancemanager.AirShipManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.VehiclePathPoint;
import com.l2jserver.gameserver.model.actor.instance.L2AirShipControllerInstance;
import com.l2jserver.gameserver.model.actor.instance.L2AirShipInstance;

/**
 * 
 * @author DS
 *
 */
public class AirShipGludioGracia implements Runnable
{
	private static final Location OUST_GLUDIO = new Location(-149379, 255246, -80);
	private static final Location OUST_GRACIA = new Location(-186563, 243590, 2608);

	private static final VehiclePathPoint[] GLUDIO_TO_WARPGATE =
	{
		new VehiclePathPoint(-151202, 252556, 231),
		new VehiclePathPoint(-160403, 256144, 222)
	};

	private static final VehiclePathPoint[] WARPGATE_TO_GRACIA =
	{
		new VehiclePathPoint(-169763, 254815, 282),
		new VehiclePathPoint(-171822, 250061, 425),
		new VehiclePathPoint(-172595, 247737, 398),
		new VehiclePathPoint(-174538, 246185, 39),
		new VehiclePathPoint(-179440, 243651, 1337),
		new VehiclePathPoint(-182601, 243957, 2739),
		new VehiclePathPoint(-184952, 245122, 2694),
		new VehiclePathPoint(-186936, 244563, 2617)
	};

	private static final VehiclePathPoint[] GRACIA_TO_WARPGATE =
	{
		new VehiclePathPoint(-187801, 244997, 2672),
		new VehiclePathPoint(-188520, 245932, 2465),
		new VehiclePathPoint(-189932, 245243, 1682),
		new VehiclePathPoint(-191192, 242969, 1523),
		new VehiclePathPoint(-190408, 239088, 1706),
		new VehiclePathPoint(-187475, 237113, 2768),
		new VehiclePathPoint(-184673, 238433, 2802),
		new VehiclePathPoint(-184524, 241119, 2816),
		new VehiclePathPoint(-182129, 243385, 2733),
		new VehiclePathPoint(-179440, 243651, 1337),
		new VehiclePathPoint(-174538, 246185, 39),
		new VehiclePathPoint(-172595, 247737, 398),
		new VehiclePathPoint(-171822, 250061, 425),
		new VehiclePathPoint(-169763, 254815, 282),
		new VehiclePathPoint(-168067, 256626, 343)
	};

	private static final VehiclePathPoint[] WARPGATE_TO_GLUDIO =
	{
		new VehiclePathPoint(-153414, 255385, 221),
		new VehiclePathPoint(-149548, 258172, 221),
		new VehiclePathPoint(-146884, 257097, 221),
		new VehiclePathPoint(-146672, 254239, 221),
		new VehiclePathPoint(-147855, 252712, 206),
		new VehiclePathPoint(-149378, 252552, 198)
	};

	private final L2AirShipInstance _ship;
	private L2AirShipControllerInstance _atc;
	private int _cycle = 0;

	public AirShipGludioGracia(L2AirShipInstance ship)
	{
		_ship = ship;
		_atc = AirShipManager.getInstance().getNearestATC(_ship, 600);
		_ship.setOustLoc(OUST_GLUDIO);
	}

	public void run()
	{
		try
		{
			switch (_cycle)
			{
				case 0:
					if (_atc != null)
					{
						_atc.dockShip(null);
						_atc.broadcastMessage("The regurarly scheduled airship that flies to the Gracia continent has departed.");
					}

					_ship.executePath(GLUDIO_TO_WARPGATE);
					break;
				case 1:
					_ship.teleToLocation(-167874, 256731, -509, 41035, false);
					_ship.setOustLoc(OUST_GRACIA);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 5000);
					break;
				case 2:
					_ship.executePath(WARPGATE_TO_GRACIA);
					break;
				case 3:
					_atc = AirShipManager.getInstance().getNearestATC(_ship, 600);
					if (_atc != null)
					{
						_atc.dockShip(_ship);
						_atc.broadcastMessage("The regurarly scheduled airship has arrived. It will depart for the Aden continent in 1 minute.");
					}

					_ship.oustPlayers();
					ThreadPoolManager.getInstance().scheduleGeneral(this, 60000);
					break;
				case 4:
					if (_atc != null)
					{
						_atc.dockShip(null);
						_atc.broadcastMessage("The regurarly scheduled airship that flies to the Aden continent has departed.");
					}

					_ship.executePath(GRACIA_TO_WARPGATE);
					break;
				case 5:
					_ship.teleToLocation(-157261, 255664, 221, 64781, false);
					_ship.setOustLoc(OUST_GLUDIO);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 5000);
					break;
				case 6:
					_ship.executePath(WARPGATE_TO_GLUDIO);
					break;
				case 7:
					_atc = AirShipManager.getInstance().getNearestATC(_ship, 600);
					if (_atc != null)
					{
						_atc.dockShip(_ship);
						_atc.broadcastMessage("The regurarly scheduled airship has arrived. It will depart for the Gracia continent in 1 minute.");
					}

					_ship.oustPlayers();
					ThreadPoolManager.getInstance().scheduleGeneral(this, 60000);
					break;
			}
			_cycle++;
			if (_cycle > 7)
				_cycle = 0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		final L2AirShipInstance ship = AirShipManager.getInstance().getNewAirShip(-149378, 252552, 198, 33837);
		ship.registerEngine(new AirShipGludioGracia(ship));
		ship.runEngine(60000);
	}
}
