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
package ai.npc.Mammons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.util.Broadcast;

/**
 * Mammons AI.
 * @author St3eT
 */
public final class Mammons extends AbstractNpcAI
{
	// NPCs
	private static final int MAMMONS[] =
	{
		31126, // Blacksmith of Mammon
		33739, // Priest of Mammon
		33511, // Merchant of Mammon
	};
	// Locations
	private static final Location[] BLACKSMITH_LOC =
	{
		new Location(146873, 29448, -2264, 0), // Aden
		new Location(81266, 150091, -3528, 891), // Giran
		new Location(42825, -41337, -2184), // Rune
	};
	private static Location[] MERCHANT_LOC =
	{
		new Location(146872, 29569, -2264, 0), // Aden
		new Location(81272, 150041, -3528, 891), // Giran
		new Location(42803, -41283, -2184, 37972), // Rune
	};
	private static Location[] PRIEST_LOC =
	{
		new Location(146882, 29665, -2264, 0), // Aden
		new Location(81284, 150155, -3528, 891), // Giran
		new Location(42784, -41236, -2192, 37972), // Rune
	};
	// Misc
	private static String[] TOWN_NAME =
	{
		"Town of Aden",
		"Town of Giran",
		"Town of Rune",
	};
	private static final int TELEPORT_DELAY = 1800000; // 30min
	private static final List<L2Npc> _mammons = new ArrayList<>();
	
	private Mammons()
	{
		super(Mammons.class.getSimpleName(), "ai/npc");
		addStartNpc(MAMMONS);
		addTalkId(MAMMONS);
		addFirstTalkId(MAMMONS);
		
		onAdvEvent("RESPAWN_MAMMONS", null, null);
		startQuestTimer("RESPAWN_MAMMONS", TELEPORT_DELAY, null, null, true);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		
		switch (event)
		{
			case "31126.html":
			case "31126-01.html":
			case "31126-02.html":
			case "31126-03.html":
			case "31126-04.html":
			case "31126-05.html":
			case "31126-06.html":
			case "33739-01.html":
			{
				htmltext = event;
				break;
			}
			case "RESPAWN_MAMMONS":
			{
				if (!_mammons.isEmpty())
				{
					_mammons.stream().filter(Objects::nonNull).forEach(L2Npc::deleteMe);
					_mammons.clear();
				}
				final int town = getRandom(3);
				final L2Npc blacksmith = addSpawn(MAMMONS[0], BLACKSMITH_LOC[town]);
				final L2Npc merchant = addSpawn(MAMMONS[1], MERCHANT_LOC[town]);
				final L2Npc priest = addSpawn(MAMMONS[2], PRIEST_LOC[town]);
				_mammons.addAll(Arrays.asList(blacksmith, merchant, priest));
				
				if (blacksmith != null)
				{
					broadcastNpcSay(blacksmith, Say2.NPC_ALL, NpcStringId.I_HAVE_SOME_EXCELLENT_WEAPONS_TO_SHOW_YOU);
				}
				
				if (Config.ANNOUNCE_MAMMON_SPAWN)
				{
					Broadcast.toAllOnlinePlayers("Mammon's has been spawned in " + TOWN_NAME[town] + ".", false);
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Mammons();
	}
}
