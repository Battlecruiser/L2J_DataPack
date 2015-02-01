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
package instances.HideoutOfTheDawn;

import instances.AbstractInstance;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;

/**
 * Hideout of the Dawn instance zone.
 * @author Adry_85
 */
public final class HideoutOfTheDawn extends AbstractInstance
{
	protected class HotDWorld extends InstanceWorld
	{
		
	}
	
	// NPCs
	private static final int WOOD = 32593;
	private static final int JAINA = 32617;
	// Location
	private static final Location WOOD_LOC = new Location(-23758, -8959, -5384, 0, 0);
	private static final Location JAINA_LOC = new Location(147072, 23743, -1984, 0);
	// Misc
	private static final int TEMPLATE_ID = 113;
	
	public HideoutOfTheDawn()
	{
		super(HideoutOfTheDawn.class.getSimpleName());
		addStartNpc(WOOD);
		addTalkId(WOOD, JAINA);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		switch (npc.getId())
		{
			case WOOD:
			{
				enterInstance(talker, new HotDWorld(), "HideoutOfTheDawn.xml", TEMPLATE_ID);
				return "32593-01.htm";
			}
			case JAINA:
			{
				final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(talker);
				world.removeAllowed(talker.getObjectId());
				talker.setInstanceId(0);
				talker.teleToLocation(JAINA_LOC);
				return "32617-01.htm";
			}
		}
		return super.onTalk(npc, talker);
	}
	
	@Override
	public void onEnterInstance(L2PcInstance player, InstanceWorld world, boolean firstEntrance)
	{
		if (firstEntrance)
		{
			world.addAllowed(player.getObjectId());
		}
		teleportPlayer(player, WOOD_LOC, world.getInstanceId(), false);
	}
}