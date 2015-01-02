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
package hellbound.AI.NPC.Solomon;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

import hellbound.HellboundEngine;

/**
 * Solomon AI.
 * @author DS
 */
public final class Solomon extends AbstractNpcAI
{
	// NPCs
	private static final int SOLOMON = 32355;
	
	public Solomon()
	{
		super(Solomon.class.getSimpleName(), "hellbound/AI/NPC");
		addFirstTalkId(SOLOMON);
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (HellboundEngine.getInstance().getLevel() == 5)
		{
			return "32355-01.htm";
		}
		else if (HellboundEngine.getInstance().getLevel() > 5)
		{
			return "32355-01a.htm";
		}
		return super.onFirstTalk(npc, player);
	}
}