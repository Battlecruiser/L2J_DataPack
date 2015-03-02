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
package ai.npc.Teleports.StrongholdsTeleports;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Strongholds teleport AI.<br>
 * Original Jython script by Kerberos.
 * @author Plim
 */
public final class StrongholdsTeleports extends AbstractNpcAI
{
	// NPCs
	private final static int[] NPCs =
	{
		32181,
		32184,
		32186
	};
	
	private StrongholdsTeleports()
	{
		super(StrongholdsTeleports.class.getSimpleName(), "ai/npc/Teleports");
		addFirstTalkId(NPCs);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getLevel() < 20)
		{
			return String.valueOf(npc.getId()) + ".htm";
		}
		return String.valueOf(npc.getId()) + "-no.htm";
	}
	
	public static void main(String[] args)
	{
		new StrongholdsTeleports();
	}
}
