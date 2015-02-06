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
 * Holly AI.
 * @author Gladicek
 */
public final class Holly extends AbstractNpcAI
{
	// NPCs
	private static final int HOLLY = 33219;
	
	private Holly()
	{
		super(Holly.class.getSimpleName(), "ai/individual");
		addSpawnId(HOLLY);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("SPAM_TEXT") && (npc != null))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.GIRAN_SHUTTLE_DOES_NOT_COME_ANYMORE_IT_S_ALL_IN_THE_PAST, 1000);
		}
		else if (event.equals("SOCIAL_ACTION") && (npc != null))
		{
			npc.broadcastSocialAction(6);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("SPAM_TEXT", 10000, npc, null, true);
		startQuestTimer("SOCIAL_ACTION", 2000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Holly();
	}
}