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
package ai.npc.RemembranceTower;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.OnEventTrigger;

/**
 * Remembrance Tower AI.
 * @author St3eT
 */
public final class RemembranceTower extends AbstractNpcAI
{
	// NPCs
	private static final int REMEMBRANCE_TOWER = 33989;
	// Misc
	private static final int EMMITER_ID = 17250700;
	
	private RemembranceTower()
	{
		super(RemembranceTower.class.getSimpleName(), "ai/npc");
		addStartNpc(REMEMBRANCE_TOWER);
		addTalkId(REMEMBRANCE_TOWER);
		addFirstTalkId(REMEMBRANCE_TOWER);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("action") && npc.isScriptValue(0))
		{
			npc.broadcastPacket(new OnEventTrigger(EMMITER_ID, true));
			npc.setScriptValue(1);
			startQuestTimer("TRIGGER", 3000, npc, null);
		}
		else if (event.equals("TRIGGER"))
		{
			npc.setScriptValue(0);
			npc.broadcastPacket(new OnEventTrigger(EMMITER_ID, false));
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	public static void main(String[] args)
	{
		new RemembranceTower();
	}
}