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
package instances.FaeronTrainingGrounds1;

import quests.Q10736_ASpecialPower.Q10736_ASpecialPower;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * @author Sdw
 */
public class FaeronTrainingGrounds1 extends AbstractNpcAI
{
	// Locations
	private static final Location START_LOC = new Location(-74903, 240618, -3584);
	private static final Location EXIT_LOC = new Location(-82088, 249880, -3392);
	// NPC's
	private static final int AYANTHE = 33942;
	private static final int AYANTHE_2 = 33944;
	// Instance
	private static final int TEMPLATE_ID = 251;
	
	protected class FTGWorld extends InstanceWorld
	{
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = player.getQuestState(Q10736_ASpecialPower.class.getSimpleName());
		if (qs == null)
		{
			return null;
		}
		
		if (event.equals("enter_instance"))
		{
			enterInstance(player, "FaeronTrainingGrounds1.xml");
		}
		else if (event.equals("exit_instance"))
		{
			final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
			world.removeAllowed(player.getObjectId());
			teleportPlayer(player, EXIT_LOC, 0);
		}
		
		return super.onAdvEvent(event, npc, player);
	}
	
	private FaeronTrainingGrounds1()
	{
		super(FaeronTrainingGrounds1.class.getSimpleName(), "instances");
		addStartNpc(AYANTHE, AYANTHE_2);
		addTalkId(AYANTHE, AYANTHE_2);
	}
	
	private void enterInstance(L2PcInstance player, String template)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof FTGWorld)
			{
				teleportPlayer(player, START_LOC, world.getInstanceId());
				return;
			}
			player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			return;
		}
		world = new FTGWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(TEMPLATE_ID);
		world.addAllowed(player.getObjectId());
		world.setStatus(0);
		InstanceManager.getInstance().addWorld(world);
		teleportPlayer(player, START_LOC, world.getInstanceId());
		showOnScreenMsg(player, NpcStringId.TALK_TO_MAGISTER_AYANTHE, ExShowScreenMessage.TOP_CENTER, 4500);
	}
	
	public static void main(String[] args)
	{
		new FaeronTrainingGrounds1();
	}
}
