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
package ai.group_template;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Evas Gift Boxes AI.
 * @author Gigiikun
 */
public class EvasGiftBoxes extends AbstractNpcAI
{
	// Monster
	private static final int GIFTBOX = 32342;
	// Skill
	private static final int KISSOFEVA = 1073;
	// @formatter:off
	private static final int[][] CHANCES =
	{
		// chance,itemId,...
		{ 2, 9692, 1, 9693 }, // without kiss of eva
		{ 100, 9692, 50, 9693 } // with kiss of eva
	};
	// @formatter:on
	private EvasGiftBoxes()
	{
		super(EvasGiftBoxes.class.getSimpleName(), "ai/group_template");
		addKillId(GIFTBOX);
		addSpawnId(GIFTBOX);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setIsNoRndWalk(true);
		return super.onSpawn(npc);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (npc.getId() == GIFTBOX)
		{
			int isKissOfEvaBuffed = 0;
			if (killer.isAffectedBySkill(KISSOFEVA))
			{
				isKissOfEvaBuffed = 1;
			}
			for (int i = 0; i < CHANCES[isKissOfEvaBuffed].length; i += 2)
			{
				if (getRandom(100) < CHANCES[isKissOfEvaBuffed][i])
				{
					giveItems(killer, CHANCES[isKissOfEvaBuffed][i + 1], 1);
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new EvasGiftBoxes();
	}
}