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

/**
 * AI for handle Non-Talking NPCs.
 * @author St3eT
 */
public final class NonTalkingNpcs extends AbstractNpcAI
{
	// @formatter:off
	private static final int[] NONTALKINGNPCS =
	{
		18684, 18685, 18686, // Red Star Stone 
		18687, 18688, 18689, // Blue Star Stone 
		18690, 18691, 18692, // Green Star Stone 
		18848, 18849, 18926, // Jinia Guild
		18927, // Fire 
		18933, // Fire Feed 
		31202, 31203, 31204, 31205, 31206, 31207, 31208, 31209, 31266, 31593, 31758, 31955, // Town pets
		31557, // Mercenary Sentry 
		31606, // Alice de Catrina
		31671, 31672, 31673, 31674, // Patrol 
		32026, // Hestui Guard 
		32030, // Garden Sculpture 
		32031, // Ice Fairy Sculpture 
		32032, // Strange Machine
		32306, // Native's Corpse
		32619, 32620, 32621, // NPC's without name
		32715, 32716, 32717, // Lilith's group
		32718, 32719, 32720, 32721, // Anakim's group
		18839, // Wild Maguen
		18915, // Divine Furnace
	};
	// @formatter:on
	
	public NonTalkingNpcs()
	{
		super(NonTalkingNpcs.class.getSimpleName(), "ai/group_template");
		addSpawnId(NONTALKINGNPCS);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.setTalking(false);
		return super.onSpawn(npc);
	}
	
	public static void main(String[] args)
	{
		new NonTalkingNpcs();
	}
}