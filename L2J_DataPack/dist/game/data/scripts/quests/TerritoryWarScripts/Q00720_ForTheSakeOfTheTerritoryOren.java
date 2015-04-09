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
package quests.TerritoryWarScripts;

import com.l2jserver.gameserver.network.NpcStringId;

/**
 * For the Sake of the Territory - Oren (720)
 * @author Gigiikun
 */
public final class Q00720_ForTheSakeOfTheTerritoryOren extends TerritoryWarSuperClass
{
	public Q00720_ForTheSakeOfTheTerritoryOren()
	{
		super(720, Q00720_ForTheSakeOfTheTerritoryOren.class.getSimpleName(), "For the Sake of the Territory - Oren");
		CATAPULT_ID = 36502;
		TERRITORY_ID = 84;
		LEADER_IDS = new int[]
		{
			36526,
			36528,
			36531,
			36594
		};
		GUARD_IDS = new int[]
		{
			36527,
			36529,
			36530
		};
		npcString = new NpcStringId[]
		{
			NpcStringId.THE_CATAPULT_OF_OREN_HAS_BEEN_DESTROYED
		};
		registerKillIds();
	}
}
