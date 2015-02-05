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
 * Luderic AI.
 * @author Gladicek
 */
public final class Luderic extends AbstractNpcAI
{
	// NPCs
	private static final int LUDERIC = 33575;
	// Misc
	private static final NpcStringId[] LUDERIC_SHOUT =
	{
		NpcStringId.THERE_IS_A_DAY_WHERE_YOU_CAN_SEE_EVEN_THE_ADEN_CONTINENT_IF_THE_WEATHER_IS_GOOD,
		NpcStringId.IF_I_M_HERE_IT_FEELS_LIKE_TIME_HAS_STOPPED
	};
	
	private Luderic()
	{
		super(Luderic.class.getSimpleName(), "ai/individual");
		addSpawnId(LUDERIC);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("SPAM_TEXT") && (npc != null))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, LUDERIC_SHOUT[getRandom(2)]);
		}
		else if (event.equals("SOCIAL_ACTION") && (npc != null))
		{
			npc.broadcastSocialAction(1);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("SPAM_TEXT", 7000, npc, null, true);
		startQuestTimer("SOCIAL_ACTION", 3000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Luderic();
	}
}