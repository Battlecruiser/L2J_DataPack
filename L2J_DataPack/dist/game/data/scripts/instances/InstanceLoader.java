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
package instances;

import instances.CavernOfThePirateCaptain.CavernOfThePirateCaptain;
import instances.ChambersOfDelusion.ChamberOfDelusionEast;
import instances.ChambersOfDelusion.ChamberOfDelusionNorth;
import instances.ChambersOfDelusion.ChamberOfDelusionSouth;
import instances.ChambersOfDelusion.ChamberOfDelusionSquare;
import instances.ChambersOfDelusion.ChamberOfDelusionTower;
import instances.ChambersOfDelusion.ChamberOfDelusionWest;
import instances.CrystalCaverns.CrystalCaverns;
import instances.DarkCloudMansion.DarkCloudMansion;
import instances.DisciplesNecropolisPast.DisciplesNecropolisPast;
import instances.ElcadiasTent.ElcadiasTent;
import instances.FaeronTrainingGrounds1.FaeronTrainingGrounds1;
import instances.FaeronTrainingGrounds2.FaeronTrainingGrounds2;
import instances.FinalEmperialTomb.FinalEmperialTomb;
import instances.HarnakUndergroundRuins.HarnakUndergroundRuins;
import instances.HideoutOfTheDawn.HideoutOfTheDawn;
import instances.IceQueensCastle.IceQueensCastle;
import instances.IceQueensCastleNormalBattle.IceQueensCastleNormalBattle;
import instances.JiniaGuildHideout1.JiniaGuildHideout1;
import instances.JiniaGuildHideout2.JiniaGuildHideout2;
import instances.JiniaGuildHideout3.JiniaGuildHideout3;
import instances.JiniaGuildHideout4.JiniaGuildHideout4;
import instances.Kamaloka.Kamaloka;
import instances.LibraryOfSages.LibraryOfSages;
import instances.MithrilMine.MithrilMine;
import instances.MonasteryOfSilence1.MonasteryOfSilence1;
import instances.NornilsGarden.NornilsGarden;
import instances.PailakaDevilsLegacy.PailakaDevilsLegacy;
import instances.PailakaSongOfIceAndFire.PailakaSongOfIceAndFire;
import instances.SanctumOftheLordsOfDawn.SanctumOftheLordsOfDawn;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instance class-loader.
 * @author FallenAngel
 */
public final class InstanceLoader
{
	private static final Logger _log = Logger.getLogger(InstanceLoader.class.getName());
	
	private static final Class<?>[] SCRIPTS =
	{
		CavernOfThePirateCaptain.class,
		CrystalCaverns.class,
		DarkCloudMansion.class,
		DisciplesNecropolisPast.class,
		ElcadiasTent.class,
		FaeronTrainingGrounds1.class,
		FaeronTrainingGrounds2.class,
		FinalEmperialTomb.class,
		HarnakUndergroundRuins.class,
		HideoutOfTheDawn.class,
		ChamberOfDelusionEast.class,
		ChamberOfDelusionNorth.class,
		ChamberOfDelusionSouth.class,
		ChamberOfDelusionSquare.class,
		ChamberOfDelusionTower.class,
		ChamberOfDelusionWest.class,
		IceQueensCastle.class,
		IceQueensCastleNormalBattle.class,
		JiniaGuildHideout1.class,
		JiniaGuildHideout2.class,
		JiniaGuildHideout3.class,
		JiniaGuildHideout4.class,
		Kamaloka.class,
		LibraryOfSages.class,
		MithrilMine.class,
		MonasteryOfSilence1.class,
		NornilsGarden.class,
		PailakaDevilsLegacy.class,
		PailakaSongOfIceAndFire.class,
		SanctumOftheLordsOfDawn.class,
	};
	
	public static void main(String[] args)
	{
		_log.info(InstanceLoader.class.getSimpleName() + ": Loading Instances scripts.");
		for (Class<?> script : SCRIPTS)
		{
			try
			{
				script.newInstance();
			}
			catch (Exception e)
			{
				_log.log(Level.SEVERE, InstanceLoader.class.getSimpleName() + ": Failed loading " + script.getSimpleName() + ":", e);
			}
		}
	}
}
