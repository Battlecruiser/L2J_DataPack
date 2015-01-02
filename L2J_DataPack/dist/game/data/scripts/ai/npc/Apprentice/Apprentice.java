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
package ai.npc.Apprentice;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Apprentice AI.
 * @author St3eT
 */
public final class Apprentice extends AbstractNpcAI
{
	// NPCs
	private static final int APPRENTICE = 33124;
	// Skill
	private static final SkillHolder KUKURU = new SkillHolder(9204, 1); // Kukuru
	
	private Apprentice()
	{
		super(Apprentice.class.getSimpleName(), "ai/npc");
		addSpawnId(APPRENTICE);
		addStartNpc(APPRENTICE);
		addTalkId(APPRENTICE);
		addFirstTalkId(APPRENTICE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("rideKukuru"))
		{
			if (!player.isTransformed())
			{
				KUKURU.getSkill().applyEffects(npc, player);
			}
			else
			{
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.YOU_CAN_T_RIDE_A_KUKURI_NOW);
			}
		}
		else if (event.equals("SPAM_TEXT") && (npc != null))
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.TRY_RIDING_A_KUKURI);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("SPAM_TEXT", 12000, npc, null, true);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Apprentice();
	}
}
