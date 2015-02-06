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
package ai.individual;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Lumi AI.
 * @author Gladicek
 */
public final class Lumi extends AbstractNpcAI
{
	// NPCs
	private static final int LUMI = 33025;
	// Misc
	private static final NpcStringId[] LUMI_SHOUT =
	{
		NpcStringId.TO_YOUR_RIGHT_THE_ADMINISTRATIVE_DISTRICT_AND_TO_THE_LEFT_IS_THE_MUSEUM,
		NpcStringId.WHEN_YOU_USE_THE_TELEPORTER_YOU_CAN_GO_TO_THE_RUINS_OF_YE_SAGIRA,
		NpcStringId.HAVE_YOU_BEEN_TO_RUINS_OF_YE_SAGIRA_YOU_HAVE_TO_GO_AT_LEAST_ONCE,
	};
	
	private Lumi()
	{
		super(Lumi.class.getSimpleName(), "ai/individual");
		addSpawnId(LUMI);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("SPAM_TEXT") && (npc != null))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, LUMI_SHOUT[getRandom(3)], 1000);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("SPAM_TEXT", 8000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Lumi();
	}
}