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
package ai.group_template;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Isle of Prayer AI.
 * @author Zoey76
 */
public final class IsleOfPrayer extends AbstractNpcAI
{
	// Items
	private static final int YELLOW_SEED_OF_EVIL_SHARD = 9593;
	private static final int GREEN_SEED_OF_EVIL_SHARD = 9594;
	private static final int BLUE_SEED_OF_EVIL_SHARD = 9595;
	private static final int RED_SEED_OF_EVIL_SHARD = 9596;
	// Monsters
	private static final int ISLAND_GUARDIAN = 22257;
	private static final int WHITE_SAND_MIRAGE = 22258;
	private static final int MUDDY_CORAL = 22259;
	private static final int KLEOPORA = 22260;
	private static final int SEYCHELLES = 22261;
	private static final int NAIAD = 22262;
	private static final int SONNERATIA = 22263;
	private static final int CASTALIA = 22264;
	private static final int CHRYSOCOLLA = 22265;
	private static final int PYTHIA = 22266;
	private static final int DARK_WATER_DRAGON = 22267;
	private static final int SHADE1 = 22268;
	private static final int SHADE2 = 22269;
	private static final int WATER_DRAGON_DETRACTOR1 = 22270;
	private static final int WATER_DRAGON_DETRACTOR2 = 22271;
	
	private IsleOfPrayer()
	{
		super(IsleOfPrayer.class.getSimpleName(), "ai/group_template");
		addKillId(ISLAND_GUARDIAN, WHITE_SAND_MIRAGE, MUDDY_CORAL, KLEOPORA, SEYCHELLES, NAIAD, SONNERATIA, CASTALIA, CHRYSOCOLLA, PYTHIA, DARK_WATER_DRAGON, SHADE1, SHADE2, WATER_DRAGON_DETRACTOR1, WATER_DRAGON_DETRACTOR2);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		switch (npc.getId())
		{
			case ISLAND_GUARDIAN:
			{
				doDrop(killer, npc, YELLOW_SEED_OF_EVIL_SHARD, 2087);
				break;
			}
			case WHITE_SAND_MIRAGE:
			{
				doDrop(killer, npc, YELLOW_SEED_OF_EVIL_SHARD, 2147);
				break;
			}
			case MUDDY_CORAL:
			{
				doDrop(killer, npc, YELLOW_SEED_OF_EVIL_SHARD, 2642);
				break;
			}
			case KLEOPORA:
			{
				doDrop(killer, npc, YELLOW_SEED_OF_EVIL_SHARD, 2292);
				break;
			}
			case SEYCHELLES:
			{
				doDrop(killer, npc, GREEN_SEED_OF_EVIL_SHARD, 1171);
				break;
			}
			case NAIAD:
			{
				doDrop(killer, npc, GREEN_SEED_OF_EVIL_SHARD, 1173);
				break;
			}
			case SONNERATIA:
			{
				doDrop(killer, npc, GREEN_SEED_OF_EVIL_SHARD, 1403);
				break;
			}
			case CASTALIA:
			{
				doDrop(killer, npc, GREEN_SEED_OF_EVIL_SHARD, 1207);
				break;
			}
			case CHRYSOCOLLA:
			{
				doDrop(killer, npc, RED_SEED_OF_EVIL_SHARD, 575);
				break;
			}
			case PYTHIA:
			{
				doDrop(killer, npc, RED_SEED_OF_EVIL_SHARD, 493);
				break;
			}
			case DARK_WATER_DRAGON:
			{
				doDrop(killer, npc, RED_SEED_OF_EVIL_SHARD, 770);
				break;
			}
			case SHADE1:
			{
				doDrop(killer, npc, BLUE_SEED_OF_EVIL_SHARD, 987);
				break;
			}
			case SHADE2:
			{
				doDrop(killer, npc, BLUE_SEED_OF_EVIL_SHARD, 995);
				break;
			}
			case WATER_DRAGON_DETRACTOR1:
			case WATER_DRAGON_DETRACTOR2:
			{
				doDrop(killer, npc, BLUE_SEED_OF_EVIL_SHARD, 1008);
				break;
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	/**
	 * @param killer the player that kills the NPC
	 * @param npc the killed NPC that will drop
	 * @param itemId the item Id to drop
	 * @param chance the chance of this NPC to drop the item
	 */
	private static final void doDrop(L2PcInstance killer, L2Npc npc, int itemId, int chance)
	{
		if (getRandom(1000) <= chance)
		{
			npc.dropItem(killer, itemId, 1);
		}
	}
	
	public static void main(String[] args)
	{
		new IsleOfPrayer();
	}
}
