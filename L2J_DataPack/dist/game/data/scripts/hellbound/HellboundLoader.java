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
package hellbound;

import handlers.admincommandhandlers.AdminHellbound;
import handlers.voicedcommandhandlers.Hellbound;
import hellbound.AI.Amaskari;
import hellbound.AI.Chimeras;
import hellbound.AI.DemonPrince;
import hellbound.AI.HellboundCore;
import hellbound.AI.Keltas;
import hellbound.AI.NaiaLock;
import hellbound.AI.OutpostCaptain;
import hellbound.AI.Ranku;
import hellbound.AI.Slaves;
import hellbound.AI.Typhoon;
import hellbound.AI.NPC.Bernarde.Bernarde;
import hellbound.AI.NPC.Budenka.Budenka;
import hellbound.AI.NPC.Buron.Buron;
import hellbound.AI.NPC.Deltuva.Deltuva;
import hellbound.AI.NPC.Falk.Falk;
import hellbound.AI.NPC.Hude.Hude;
import hellbound.AI.NPC.Jude.Jude;
import hellbound.AI.NPC.Kanaf.Kanaf;
import hellbound.AI.NPC.Kief.Kief;
import hellbound.AI.NPC.Natives.Natives;
import hellbound.AI.NPC.Quarry.Quarry;
import hellbound.AI.NPC.Shadai.Shadai;
import hellbound.AI.NPC.Solomon.Solomon;
import hellbound.AI.NPC.Warpgate.Warpgate;
import hellbound.AI.Zones.AnomicFoundry.AnomicFoundry;
import hellbound.AI.Zones.BaseTower.BaseTower;
import hellbound.AI.Zones.TowerOfInfinitum.TowerOfInfinitum;
import hellbound.AI.Zones.TowerOfNaia.TowerOfNaia;
import hellbound.AI.Zones.TullyWorkshop.TullyWorkshop;
import hellbound.Instances.DemonPrinceFloor.DemonPrinceFloor;
import hellbound.Instances.RankuFloor.RankuFloor;
import hellbound.Instances.UrbanArea.UrbanArea;

import java.util.logging.Logger;

import quests.Q00130_PathToHellbound.Q00130_PathToHellbound;
import quests.Q00133_ThatsBloodyHot.Q00133_ThatsBloodyHot;

import com.l2jserver.Config;
import com.l2jserver.gameserver.handler.AdminCommandHandler;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.handler.VoicedCommandHandler;

/**
 * Hellbound class-loader.
 * @author Zoey76
 */
public final class HellboundLoader
{
	private static final Logger _log = Logger.getLogger(HellboundLoader.class.getName());
	
	private static final Class<?>[] SCRIPTS =
	{
		// Commands
		AdminHellbound.class,
		Hellbound.class,
		// AIs
		Amaskari.class,
		Chimeras.class,
		DemonPrince.class,
		HellboundCore.class,
		Keltas.class,
		NaiaLock.class,
		OutpostCaptain.class,
		Ranku.class,
		Slaves.class,
		Typhoon.class,
		// NPCs
		Bernarde.class,
		Budenka.class,
		Buron.class,
		Deltuva.class,
		Falk.class,
		Hude.class,
		Jude.class,
		Kanaf.class,
		Kief.class,
		Natives.class,
		Quarry.class,
		Shadai.class,
		Solomon.class,
		Warpgate.class,
		// Zones
		AnomicFoundry.class,
		BaseTower.class,
		TowerOfInfinitum.class,
		TowerOfNaia.class,
		TullyWorkshop.class,
		// Instances
		DemonPrinceFloor.class,
		UrbanArea.class,
		RankuFloor.class,
		// Quests
		Q00130_PathToHellbound.class,
		Q00133_ThatsBloodyHot.class,
	};
	
	public static void main(String[] args)
	{
		_log.info(HellboundLoader.class.getSimpleName() + ": Loading Hellbound related scripts:");
		// Data
		HellboundPointData.getInstance();
		HellboundSpawns.getInstance();
		// Engine
		HellboundEngine.getInstance();
		for (Class<?> script : SCRIPTS)
		{
			try
			{
				final Object instance = script.newInstance();
				if (instance instanceof IAdminCommandHandler)
				{
					AdminCommandHandler.getInstance().registerHandler((IAdminCommandHandler) instance);
				}
				else if (Config.L2JMOD_HELLBOUND_STATUS && (instance instanceof IVoicedCommandHandler))
				{
					VoicedCommandHandler.getInstance().registerHandler((IVoicedCommandHandler) instance);
				}
			}
			catch (Exception e)
			{
				_log.severe(HellboundLoader.class.getSimpleName() + ": Failed loading " + script.getSimpleName() + ":" + e.getMessage());
			}
		}
	}
}
