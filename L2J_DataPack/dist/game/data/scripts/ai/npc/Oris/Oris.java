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
package ai.npc.Oris;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Oris AI.
 * @author St3eT
 */
public final class Oris extends AbstractNpcAI
{
	// NPC
	private static final int ORIS = 33116;
	
	private Oris()
	{
		super(Oris.class.getSimpleName(), "ai/npc");
		addSpawnId(ORIS);
		addStartNpc(ORIS);
		addTalkId(ORIS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("SOCIAL_ACTION_1"))
		{
			npc.broadcastSocialAction(6);
			startQuestTimer("SOCIAL_ACTION_2", 2500, npc, null);
		}
		else if (event.equals("SOCIAL_ACTION_2"))
		{
			npc.broadcastSocialAction(7);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setRandomAnimationEnabled(false);
		startQuestTimer("SOCIAL_ACTION_1", 6500, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Oris();
	}
}