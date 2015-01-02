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
package ai.npc.Milia;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Milia AI.
 * @author St3eT
 */
public final class Milia extends AbstractNpcAI
{
	// NPCs
	private static final int MILIA = 30006;
	// Locations
	private static final Location GLUDIO_AIRSHIP = new Location(-149406, 255247, -80);
	
	private Milia()
	{
		super(Milia.class.getSimpleName(), "ai/npc/Teleports");
		addSpawnId(MILIA);
		addStartNpc(MILIA);
		addTalkId(MILIA);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("gludioAirship"))
		{
			player.teleToLocation(GLUDIO_AIRSHIP);
		}
		else if (event.equals("TEXT_SPAM") && (npc != null))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.SPEAK_WITH_ME_ABOUT_TRAVELING_AROUND_ADEN);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("TEXT_SPAM", 10000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Milia();
	}
}