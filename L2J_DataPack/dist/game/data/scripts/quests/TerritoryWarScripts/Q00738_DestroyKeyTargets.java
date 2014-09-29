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
package quests.TerritoryWarScripts;

import com.l2jserver.gameserver.network.NpcStringId;

/**
 * Destroy Key Targets (738)
 * @author Gigiikun
 */
public final class Q00738_DestroyKeyTargets extends TerritoryWarSuperClass
{
	public Q00738_DestroyKeyTargets()
	{
		super(738, Q00738_DestroyKeyTargets.class.getSimpleName(), "Destroy Key Targets");
		CLASS_IDS = new int[]
		{
			51,
			115,
			57,
			118
		};
		RANDOM_MIN = 3;
		RANDOM_MAX = 8;
		npcString = new NpcStringId[]
		{
			NpcStringId.YOU_HAVE_DEFEATED_S2_OF_S1_WARSMITHS_AND_OVERLORDS,
			NpcStringId.YOU_DESTROYED_THE_ENEMYS_PROFESSIONALS
		};
	}
}
