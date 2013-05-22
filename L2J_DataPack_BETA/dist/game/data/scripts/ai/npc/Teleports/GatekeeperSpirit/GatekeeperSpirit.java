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
package ai.npc.Teleports.GatekeeperSpirit;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.SevenSigns;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Gatekeeper Spirit AI.
 * @author Charus, lion
 */
public class GatekeeperSpirit extends AbstractNpcAI
{
	// NPCs
	private static final int GATEKEEPER_SPIRIT_ENTER = 31111;
	private static final int GATEKEEPER_SPIRIT_EXIT = 31112;
	private static final int LILITH = 25283;
	private static final int ANAKIM = 25286;
	// Exit gatekeeper spawn locations
	private static final Location SPAWN_LILITH_GATEKEEPER = new Location(184410, -10111, -5488);
	private static final Location SPAWN_ANAKIM_GATEKEEPER = new Location(184410, -13102, -5488);
	
	private GatekeeperSpirit()
	{
		super(GatekeeperSpirit.class.getSimpleName(), "ai/npc/Teleports");
		addStartNpc(GATEKEEPER_SPIRIT_ENTER);
		addFirstTalkId(GATEKEEPER_SPIRIT_ENTER);
		addKillId(LILITH, ANAKIM);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		int playerCabal = SevenSigns.getInstance().getPlayerCabal(player.getObjectId());
		int sealOfAvariceOwner = SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_AVARICE);
		int compWinner = SevenSigns.getInstance().getCabalHighestScore();
		
		if ((playerCabal == sealOfAvariceOwner) && (playerCabal == compWinner) && (sealOfAvariceOwner != SevenSigns.CABAL_NULL))
		{
			return (sealOfAvariceOwner == SevenSigns.CABAL_DUSK) ? "dusk.htm" : "dawn.htm";
		}
		
		npc.showChatWindow(player);
		return super.onFirstTalk(npc, player);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		startQuestTimer(Integer.toString(npc.getNpcId()), 10000, npc, killer);
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		Location loc = (event.equals(Integer.toString(LILITH)) ? SPAWN_LILITH_GATEKEEPER : SPAWN_ANAKIM_GATEKEEPER);
		addSpawn(GATEKEEPER_SPIRIT_EXIT, loc, false, 900000);
		return super.onAdvEvent(event, npc, player);
	}
	
	public static void main(String[] args)
	{
		new GatekeeperSpirit();
	}
}