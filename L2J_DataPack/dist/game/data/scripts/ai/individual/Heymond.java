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
 * Heymond AI.
 * @author Gladicek
 */
public final class Heymond extends AbstractNpcAI
{
	// NPCs
	private static final int HEYMOND = 33026;
	// Misc
	private static final NpcStringId[] HEYMOND_SHOUT =
	{
		NpcStringId.VIEW_OUR_WIDE_VARIETY_OF_ACCESSORIES,
		NpcStringId.THE_BEST_WEAPON_DOESN_T_MAKE_YOU_THE_BEST,
		NpcStringId.WE_BUY_AND_SELL_COME_TAKE_A_LOOK
	};
	
	private Heymond()
	{
		super(Heymond.class.getSimpleName(), "ai/individual");
		addSpawnId(HEYMOND);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("SPAM_TEXT") && (npc != null))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, HEYMOND_SHOUT[getRandom(3)], 1000);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("SPAM_TEXT", 7000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Heymond();
	}
}