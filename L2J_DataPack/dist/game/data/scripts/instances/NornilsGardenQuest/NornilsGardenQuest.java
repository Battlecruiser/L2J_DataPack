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
package instances.NornilsGardenQuest;

import instances.AbstractInstance;
import quests.Q00236_SeedsOfChaos.Q00236_SeedsOfChaos;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Nornil's Garden Quest instant zone.
 * @author Zoey76
 */
public final class NornilsGardenQuest extends AbstractInstance
{
	protected static final class NornilsGardenQuestWorld extends InstanceWorld
	{
		protected Location ORIGIN_LOC;
	}
	
	// NPCs
	private static final int RODENPICULA = 32237;
	private static final int MOTHER_NORNIL = 32239;
	// Location
	private static final Location ENTER_LOC = new Location(-119538, 87177, -12592);
	// Misc
	private static final int TEMPLATE_ID = 12;
	
	public NornilsGardenQuest()
	{
		super(NornilsGardenQuest.class.getSimpleName());
		
		addStartNpc(RODENPICULA, MOTHER_NORNIL);
		addTalkId(RODENPICULA, MOTHER_NORNIL);
		addFirstTalkId(RODENPICULA, MOTHER_NORNIL);
	}
	
	@Override
	protected boolean checkConditions(L2PcInstance player)
	{
		final QuestState qs = player.getQuestState(Q00236_SeedsOfChaos.class.getSimpleName());
		return (qs != null) && (qs.getMemoState() >= 40) && (qs.getMemoState() <= 45);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		final QuestState q236 = player.getQuestState(Q00236_SeedsOfChaos.class.getSimpleName());
		switch (event)
		{
			case "enter":
			{
				if (checkConditions(player))
				{
					final NornilsGardenQuestWorld world = new NornilsGardenQuestWorld();
					world.ORIGIN_LOC = player.getLocation();
					enterInstance(player, world, "NornilsGardenQuest.xml", TEMPLATE_ID);
					q236.setCond(16, true);
					htmltext = "32190-02.html";
				}
				else
				{
					htmltext = "32190-03.html";
				}
				break;
			}
			case "exit":
			{
				if ((q236 != null) && q236.isCompleted())
				{
					final NornilsGardenQuestWorld world = (NornilsGardenQuestWorld) InstanceManager.getInstance().getPlayerWorld(player);
					world.removeAllowed(player.getObjectId());
					finishInstance(world, 5000);
					
					player.setInstanceId(0);
					player.teleToLocation(world.ORIGIN_LOC);
					htmltext = "32239-03.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	protected void onEnterInstance(L2PcInstance player, InstanceWorld world, boolean firstEntrance)
	{
		if (firstEntrance)
		{
			world.addAllowed(player.getObjectId());
		}
		teleportPlayer(player, ENTER_LOC, world.getInstanceId(), false);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		final QuestState q236 = player.getQuestState(Q00236_SeedsOfChaos.class.getSimpleName());
		switch (npc.getId())
		{
			case RODENPICULA:
			{
				htmltext = (q236 != null) && (q236.isCompleted()) ? "32237-02.html" : "32237-01.html";
				break;
			}
			case MOTHER_NORNIL:
			{
				htmltext = (q236 != null) && (q236.isCompleted()) ? "32239-02.html" : "32239-01.html";
				break;
			}
		}
		return htmltext;
	}
}
