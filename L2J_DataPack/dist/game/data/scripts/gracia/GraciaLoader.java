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
package gracia;

import java.util.logging.Level;
import java.util.logging.Logger;

import gracia.AI.EnergySeeds;
import gracia.AI.Lindvior;
import gracia.AI.Maguen;
import gracia.AI.StarStones;
import gracia.AI.NPC.FortuneTelling.FortuneTelling;
import gracia.AI.NPC.GeneralDilios.GeneralDilios;
import gracia.AI.NPC.Lekon.Lekon;
import gracia.AI.NPC.Nemo.Nemo;
import gracia.AI.NPC.Nottingale.Nottingale;
import gracia.AI.NPC.Seyo.Seyo;
import gracia.AI.NPC.ZealotOfShilen.ZealotOfShilen;
import gracia.AI.SeedOfAnnihilation.SeedOfAnnihilation;
import gracia.instances.SecretArea.SecretArea;
import gracia.instances.SeedOfDestruction.Stage1;
import gracia.instances.SeedOfInfinity.HallOfSuffering;
import gracia.vehicles.AirShipGludioGracia.AirShipGludioGracia;
import gracia.vehicles.KeucereusNorthController.KeucereusNorthController;
import gracia.vehicles.KeucereusSouthController.KeucereusSouthController;
import gracia.vehicles.SoDController.SoDController;
import gracia.vehicles.SoIController.SoIController;

/**
 * Gracia class-loader.
 * @author Pandragon
 */
public final class GraciaLoader
{
	private static final Logger _log = Logger.getLogger(GraciaLoader.class.getName());
	
	private static final Class<?>[] SCRIPTS =
	{
		// AIs
		EnergySeeds.class,
		Lindvior.class,
		Maguen.class,
		StarStones.class,
		// NPCs
		FortuneTelling.class,
		GeneralDilios.class,
		Lekon.class,
		Nemo.class,
		Nottingale.class,
		Seyo.class,
		ZealotOfShilen.class,
		// Seed of Annihilation
		SeedOfAnnihilation.class,
		// Instances
		SecretArea.class,
		Stage1.class, // Seed of Destruction
		HallOfSuffering.class, // Seed of Infinity
		// Vehicles
		AirShipGludioGracia.class,
		KeucereusNorthController.class,
		KeucereusSouthController.class,
		SoIController.class,
		SoDController.class,
	};
	
	public static void main(String[] args)
	{
		_log.info(GraciaLoader.class.getSimpleName() + ": Loading Gracia related scripts.");
		for (Class<?> script : SCRIPTS)
		{
			try
			{
				script.newInstance();
			}
			catch (Exception e)
			{
				_log.log(Level.SEVERE, GraciaLoader.class.getSimpleName() + ": Failed loading " + script.getSimpleName() + ":", e);
			}
		}
	}
}
