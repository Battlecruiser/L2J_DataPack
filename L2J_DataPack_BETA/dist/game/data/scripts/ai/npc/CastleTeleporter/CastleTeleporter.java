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
package ai.npc.CastleTeleporter;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.MapRegionManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Siege;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Castle Teleporter AI.
 * @author malyelfik
 */
public final class CastleTeleporter extends AbstractNpcAI
{
	// Teleporter IDs
	private static final int[] NPCS =
	{
		35095, // Mass Gatekeeper (Gludio)
		35137, // Mass Gatekeeper (Dion)
		35179, // Mass Gatekeeper (Giran)
		35221, // Mass Gatekeeper (Oren)
		35266, // Mass Gatekeeper (Aden)
		35311, // Mass Gatekeeper (Innadril)
		35355, // Mass Gatekeeper (Goddard)
		35502, // Mass Gatekeeper (Rune)
		35547, // Mass Gatekeeper (Schuttgart)
	};
	
	private CastleTeleporter()
	{
		super(CastleTeleporter.class.getSimpleName(), "ai/npc");
		addStartNpc(NPCS);
		addTalkId(NPCS);
		addFirstTalkId(NPCS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("teleporter-03.html"))
		{
			if (npc.isScriptValue(0))
			{
				final Siege siege = npc.getCastle().getSiege();
				final int time = (siege.getIsInProgress() && (siege.getControlTowerCount() == 0)) ? 480000 : 30000;
				startQuestTimer("teleport", time, npc, null);
				npc.setScriptValue(1);
			}
			return event;
		}
		else if (event.equalsIgnoreCase("teleport"))
		{
			final int region = MapRegionManager.getInstance().getMapRegionLocId(npc.getX(), npc.getY());
			final NpcSay msg = new NpcSay(npc, Say2.NPC_SHOUT, NpcStringId.THE_DEFENDERS_OF_S1_CASTLE_WILL_BE_TELEPORTED_TO_THE_INNER_CASTLE);
			msg.addStringParameter(npc.getCastle().getName());
			npc.getCastle().oustAllPlayers();
			npc.setScriptValue(0);
			// TODO: Is it possible to get all the players for that region, instead of all players?
			for (L2PcInstance pl : L2World.getInstance().getPlayers())
			{
				if (region == MapRegionManager.getInstance().getMapRegionLocId(pl))
				{
					pl.sendPacket(msg);
				}
			}
		}
		return null;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		final Siege siege = npc.getCastle().getSiege();
		return (npc.isScriptValue(0)) ? (siege.getIsInProgress() && (siege.getControlTowerCount() == 0)) ? "teleporter-02.html" : "teleporter-01.html" : "teleporter-03.html";
	}
	
	public static void main(String[] args)
	{
		new CastleTeleporter();
	}
}