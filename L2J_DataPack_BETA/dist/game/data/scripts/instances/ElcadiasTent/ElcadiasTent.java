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
package instances.ElcadiasTent;

import quests.Q10292_SevenSignsGirlOfDoubt.Q10292_SevenSignsGirlOfDoubt;
import quests.Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Elcadia's Tent instance zone.
 * @author Adry_85
 */
public final class ElcadiasTent extends Quest
{
	protected class ETWorld extends InstanceWorld
	{
		protected long storeTime = 0;
	}
	
	private static final int INSTANCEID = 158;
	// NPCs
	private static final int ELCADIA = 32784;
	private static final int GRUFF_LOOKING_MAN = 32862;
	// Locations
	private static final Location START_LOC = new Location(89706, -238074, -9632, 0, 0);
	private static final Location EXIT_LOC = new Location(43316, -87986, -2832, 0, 0);
	
	private ElcadiasTent()
	{
		super(-1, ElcadiasTent.class.getSimpleName(), "instances");
		addFirstTalkId(GRUFF_LOOKING_MAN, ELCADIA);
		addStartNpc(GRUFF_LOOKING_MAN, ELCADIA);
		addTalkId(GRUFF_LOOKING_MAN, ELCADIA);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (npc.getId() == ELCADIA)
		{
			htmltext = "32784.html";
		}
		else
		{
			htmltext = "32862.html";
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		if (npc.getId() == GRUFF_LOOKING_MAN)
		{
			if (((talker.getQuestState(Q10292_SevenSignsGirlOfDoubt.class.getSimpleName()) != null) && talker.getQuestState(Q10292_SevenSignsGirlOfDoubt.class.getSimpleName()).isStarted()) //
				|| ((talker.getQuestState(Q10292_SevenSignsGirlOfDoubt.class.getSimpleName()) != null) && talker.getQuestState(Q10292_SevenSignsGirlOfDoubt.class.getSimpleName()).isCompleted() && (talker.getQuestState(Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.class.getSimpleName()) == null)) //
				|| ((talker.getQuestState(Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.class.getSimpleName()) != null) && talker.getQuestState(Q10293_SevenSignsForbiddenBookOfTheElmoreAdenKingdom.class.getSimpleName()).isStarted()))
			{
				enterInstance(talker, "ElcadiasTent.xml", START_LOC);
			}
			else
			{
				return "32862-01.html";
			}
		}
		else
		{
			final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(talker);
			world.removeAllowed(talker.getObjectId());
			talker.setInstanceId(0);
			talker.teleToLocation(EXIT_LOC);
		}
		return super.onTalk(npc, talker);
	}
	
	protected int enterInstance(L2PcInstance player, String template, Location loc)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof ETWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, loc, world.getInstanceId(), false);
			return 0;
		}
		// New instance
		world = new ETWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((ETWorld) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("Elcadia's Tent started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, loc, world.getInstanceId(), false);
		world.addAllowed(player.getObjectId());
		return world.getInstanceId();
	}
	
	public static void main(String[] args)
	{
		new ElcadiasTent();
	}
}
