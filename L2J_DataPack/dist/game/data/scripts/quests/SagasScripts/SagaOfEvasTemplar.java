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
package quests.SagasScripts;

import com.l2jserver.gameserver.model.Location;

/**
 * Saga of Eva's Templar (71)
 * @author Emperorc
 */
public class SagaOfEvasTemplar extends SagasSuperClass
{
	public SagaOfEvasTemplar()
	{
		super(71, "Q00071_SagaOfEvasTemplar", "Saga of Eva's Templar");
		NPC = new int[]
		{
			30852,
			31624,
			31278,
			30852,
			31638,
			31646,
			31648,
			31651,
			31654,
			31655,
			31658,
			31281
		};
		Items = new int[]
		{
			7080,
			7535,
			7081,
			7486,
			7269,
			7300,
			7331,
			7362,
			7393,
			7424,
			7094,
			6482
		};
		Mob = new int[]
		{
			27287,
			27220,
			27279
		};
		classid = new int[]
		{
			99
		};
		prevclass = new int[]
		{
			0x14
		};
		npcSpawnLocations = new Location[]
		{
			new Location(119518, -28658, -3811),
			new Location(181215, 36676, -4812),
			new Location(181227, 36703, -4816)
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
