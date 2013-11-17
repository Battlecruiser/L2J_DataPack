/*
 * Copyright (C) 2004-2013 L2J DataPack
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

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;

/**
 * Sprigant AI.
 * @author St3eT
 */
public final class Sprigant extends AbstractNpcAI
{
	// NPC
	private static final int ANESTHESIA_SPRIGNANT = 18345;
	private static final int DEADLY_SPRIGNANT = 18346;
	
	// Skills
	private static SkillHolder ANESTHESIA = new SkillHolder(5085, 1);
	private static SkillHolder DEADLY_POISON = new SkillHolder(5086, 1);
	
	private Sprigant()
	{
		super(Sprigant.class.getSimpleName(), "ai/individual");
		addSpawnId(ANESTHESIA_SPRIGNANT, DEADLY_SPRIGNANT);
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(ANESTHESIA_SPRIGNANT))
		{
			onSpawn(spawn.getLastSpawn());
		}
		
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(DEADLY_SPRIGNANT))
		{
			onSpawn(spawn.getLastSpawn());
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("useSkill") && (npc != null) && !npc.isDead())
		{
			npc.doCast((npc.getId() == ANESTHESIA_SPRIGNANT ? ANESTHESIA.getSkill() : DEADLY_POISON.getSkill()));
			startQuestTimer("useSkill", 15000, npc, null);
		}
		else
		{
			cancelQuestTimer("useSkill", npc, null);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		startQuestTimer("useSkill", 15000, npc, null);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new Sprigant();
	}
}