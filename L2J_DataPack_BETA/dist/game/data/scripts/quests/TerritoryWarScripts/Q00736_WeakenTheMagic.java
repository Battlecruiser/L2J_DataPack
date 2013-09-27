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
 * Weaken the magic! (736)
 * @author Gigiikun
 */
public final class Q00736_WeakenTheMagic extends TerritoryWarSuperClass
{
	public Q00736_WeakenTheMagic()
	{
		super(736, Q00736_WeakenTheMagic.class.getSimpleName(), "Weaken the magic");
		CLASS_IDS = new int[]
		{
			40,
			110,
			27,
			103,
			13,
			95,
			12,
			94,
			41,
			111,
			28,
			104,
			14,
			96
		};
		RANDOM_MIN = 10;
		RANDOM_MAX = 15;
		npcString = new NpcStringId[]
		{
			NpcStringId.YOU_HAVE_DEFEATED_S2_OF_S1_ENEMIES,
			NpcStringId.YOU_WEAKENED_THE_ENEMYS_MAGIC
		};
	}
}
