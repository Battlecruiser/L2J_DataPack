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
package instances.JiniaGuildHideout1;

import quests.Q10284_AcquisitionOfDivineSword.Q10284_AcquisitionOfDivineSword;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Jinia Guild Hideout instance zone.
 * @author Adry_85
 */
public final class JiniaGuildHideout1 extends Quest
{
	protected class JGH1World extends InstanceWorld
	{
		long storeTime = 0;
	}
	
	private static final int INSTANCEID = 140;
	// NPC
	private static final int RAFFORTY = 32020;
	// Location
	private static final Location START_LOC = new Location(-23530, -8963, -5413);
	
	private JiniaGuildHideout1()
	{
		super(-1, JiniaGuildHideout1.class.getSimpleName(), "instances");
		addStartNpc(RAFFORTY);
		addTalkId(RAFFORTY);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		final QuestState qs = talker.getQuestState(Q10284_AcquisitionOfDivineSword.class.getSimpleName());
		if ((qs != null) && qs.isCond(1))
		{
			enterInstance(talker, "JiniaGuildHideout1.xml");
			qs.setCond(2, true);
		}
		return super.onTalk(npc, talker);
	}
	
	protected void enterInstance(L2PcInstance player, String template)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof JGH1World))
			{
				player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
				return;
			}
			teleportPlayer(player, START_LOC, world.getInstanceId(), false);
			return;
		}
		// New instance
		world = new JGH1World();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((JGH1World) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("Jinia Guild Hideout started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, START_LOC, world.getInstanceId(), false);
		world.addAllowed(player.getObjectId());
	}
	
	public static void main(String[] args)
	{
		new JiniaGuildHideout1();
	}
}
