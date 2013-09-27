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
package quests.TerritoryWarScripts;

import com.l2jserver.gameserver.network.NpcStringId;

/**
 * For the Sake of the Territory - Schuttgart (725)
 * @author Gigiikun
 */
public final class Q00725_ForTheSakeOfTheTerritorySchuttgart extends TerritoryWarSuperClass
{
	public Q00725_ForTheSakeOfTheTerritorySchuttgart()
	{
		super(725, Q00725_ForTheSakeOfTheTerritorySchuttgart.class.getSimpleName(), "For the Sake of the Territory - Schuttgart");
		CATAPULT_ID = 36507;
		TERRITORY_ID = 89;
		LEADER_IDS = new int[]
		{
			36556,
			36558,
			36561,
			36599
		};
		GUARD_IDS = new int[]
		{
			36557,
			36559,
			36560
		};
		npcString = new NpcStringId[]
		{
			NpcStringId.THE_CATAPULT_OF_SCHUTTGART_HAS_BEEN_DESTROYED
		};
		registerKillIds();
	}
}
