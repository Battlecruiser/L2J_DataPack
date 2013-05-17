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
package quests.SagasScripts;

import com.l2jserver.gameserver.model.Location;

/**
 * Saga of the Hell Knight (95)
 * @author Emperorc
 */
public class SagaOfTheHellKnight extends SagasSuperClass
{
	public SagaOfTheHellKnight()
	{
		super(95, "95_SagaOfTheHellKnight", "Saga of the Hell Knight");
		NPC = new int[]
		{
			31582,
			31623,
			31297,
			31297,
			31599,
			31646,
			31647,
			31653,
			31654,
			31655,
			31656,
			31297
		};
		Items = new int[]
		{
			7080,
			7532,
			7081,
			7510,
			7293,
			7324,
			7355,
			7386,
			7417,
			7448,
			7086,
			0
		};
		Mob = new int[]
		{
			27258,
			27244,
			27263
		};
		classid = new int[]
		{
			91
		};
		prevclass = new int[]
		{
			0x06
		};
		npcSpawnLocations = new Location[]
		{
			new Location(164650, -74121, -2871),
			new Location(47391, -56929, -2370),
			new Location(47429, -56923, -2383)
		};
		Text = new String[]
		{
			"PLAYERNAME! Pursued to here! However, I jumped out of the Banshouren boundaries! You look at the giant as the sign of power!",
			"... Oh ... good! So it was ... let's begin!",
			"I do not have the patience ..! I have been a giant force ...! Cough chatter ah ah ah!",
			"Paying homage to those who disrupt the orderly will be PLAYERNAME's death!",
			"Now, my soul freed from the shackles of the millennium, Halixia, to the back side I come ...",
			"Why do you interfere others' battles?",
			"This is a waste of time.. Say goodbye...!",
			"...That is the enemy",
			"...Goodness! PLAYERNAME you are still looking?",
			"PLAYERNAME ... Not just to whom the victory. Only personnel involved in the fighting are eligible to share in the victory.",
			"Your sword is not an ornament. Don't you think, PLAYERNAME?",
			"Goodness! I no longer sense a battle there now.",
			"let...",
			"Only engaged in the battle to bar their choice. Perhaps you should regret.",
			"The human nation was foolish to try and fight a giant's strength.",
			"Must...Retreat... Too...Strong.",
			"PLAYERNAME. Defeat...by...retaining...and...Mo...Hacker",
			"....! Fight...Defeat...It...Fight...Defeat...It..."
		};
		registerNPCs();
	}
}
