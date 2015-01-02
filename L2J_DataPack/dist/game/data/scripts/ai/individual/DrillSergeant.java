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

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Drill Sergeant AI.
 * @author St3eT
 */
public final class DrillSergeant extends AbstractNpcAI
{
	// NPCs
	private static final int SERGANT = 33007; // Drill Sergant
	private static final int GUARD = 33018;
	// Misc
	//@formatter:off
	private final int[] SOCIAL_ACTIONS = {9, 10, 11, 1 };
	//@formatter:on
	
	private DrillSergeant()
	{
		super(DrillSergeant.class.getSimpleName(), "ai/individual");
		addSpawnId(SERGANT);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("SOCIAL_SHOW"))
		{
			final int socialActionId = SOCIAL_ACTIONS[getRandom(SOCIAL_ACTIONS.length)];
			npc.broadcastSocialAction(socialActionId);
			
			for (L2Character chars : npc.getKnownList().getKnownCharactersInRadius(500))
			{
				if (chars.isNpc() && (chars.getId() == GUARD))
				{
					final L2Npc guard = (L2Npc) chars;
					guard.getVariables().set("SOCIAL_ACTION_ID", socialActionId);
					startQuestTimer("SOCIAL_ACTION", getRandom(500, 1500), guard, null);
					
				}
			}
		}
		else if (event.equals("SOCIAL_ACTION"))
		{
			final int socialActionId = npc.getVariables().getInt("SOCIAL_ACTION_ID", 0);
			if (socialActionId > 0)
			{
				npc.broadcastSocialAction(socialActionId);
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc.getId() == SERGANT)
		{
			startQuestTimer("SOCIAL_SHOW", 10000, npc, null, true);
		}
		npc.setRandomAnimationEnabled(false);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new DrillSergeant();
	}
}