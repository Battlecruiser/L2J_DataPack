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
package ai.npc.Teleports.MithrilMines;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Mithril Mines teleport AI.
 * @author Charus
 */
public class MithrilMines extends AbstractNpcAI
{
	// Location
	private static final Location[] LOCS =
	{
		new Location(171946, -173352, 3440),
		new Location(175499, -181586, -904),
		new Location(173462, -174011, 3480),
		new Location(179299, -182831, -224),
		new Location(178591, -184615, 360),
		new Location(175499, -181586, -904)
	};
	// NPC
	private final static int TELEPORT_CRYSTAL = 32652;
	
	private MithrilMines(String name, String descr)
	{
		super(name, descr);
		addStartNpc(TELEPORT_CRYSTAL);
		addFirstTalkId(TELEPORT_CRYSTAL);
		addTalkId(TELEPORT_CRYSTAL);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		int index = Integer.parseInt(event) - 1;
		if (LOCS.length > index)
		{
			Location loc = LOCS[index];
			player.teleToLocation(loc, false);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.isInsideRadius(173147, -173762, L2Npc.INTERACTION_DISTANCE, true))
		{
			return "32652-01.htm";
		}
		else if (npc.isInsideRadius(181941, -174614, L2Npc.INTERACTION_DISTANCE, true))
		{
			return "32652-02.htm";
		}
		else if (npc.isInsideRadius(179560, -182956, L2Npc.INTERACTION_DISTANCE, true))
		{
			return "32652-03.htm";
		}
		return super.onFirstTalk(npc, player);
	}
	
	public static void main(String[] args)
	{
		new MithrilMines(MithrilMines.class.getSimpleName(), "ai/npc/Teleports/");
	}
}