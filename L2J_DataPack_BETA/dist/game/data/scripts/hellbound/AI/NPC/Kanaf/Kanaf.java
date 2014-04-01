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
package hellbound.AI.NPC.Kanaf;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * Kanaf AI.
 * @author GKR
 */
public final class Kanaf extends Quest
{
	private static final int KANAF = 32346;
	
	public Kanaf()
	{
		super(-1, Kanaf.class.getSimpleName(), "hellbound/AI/NPC");
		addStartNpc(KANAF);
		addTalkId(KANAF);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("info"))
		{
			return "32346-0" + getRandom(1, 3) + ".htm";
		}
		
		return super.onAdvEvent(event, npc, player);
	}
}
