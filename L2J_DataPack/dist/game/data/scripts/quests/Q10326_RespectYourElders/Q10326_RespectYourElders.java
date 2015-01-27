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
package quests.Q10326_RespectYourElders;

import quests.Q10325_SearchingForNewPower.Q10325_SearchingForNewPower;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.ExRotation;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.Util;

/**
 * Respect Your Elders (10326)
 * @author Gladicek, St3eT, Neanrakyr
 */
public class Q10326_RespectYourElders extends Quest
{
	// Npcs
	private static final int GALLINT = 32980;
	private static final int PANTHEON = 32972;
	private static final int HANDERMONKEY = 32971;
	// Misc
	private static final int MAX_LEVEL = 20;
	private static final Location HANDERMONKEY_SPAWN = new Location(-116617, 255497, -1432);
	private final static Location[] HANDERMONKEY_LOC =
	{
		new Location(-116560, 255951, -1457),
		new Location(-116688, 256597, -1472),
		new Location(-116518, 257309, -1512),
		new Location(-116418, 257746, -1512),
		new Location(-115907, 257780, -1312),
		new Location(-115449, 257782, -1136),
		new Location(-114946, 257760, -1316),
		new Location(-114637, 257349, -1142),
		new Location(-114414, 257318, -1136),
	};
	
	public Q10326_RespectYourElders()
	{
		super(10326, Q10326_RespectYourElders.class.getSimpleName(), "Respect Your Elders");
		addStartNpc(GALLINT);
		addTalkId(GALLINT, PANTHEON);
		addSpawnId(HANDERMONKEY);
		addMoveFinishedId(HANDERMONKEY);
		addCondMaxLevel(MAX_LEVEL, "32980-04.htm");
		addCondCompletedQuest(Q10325_SearchingForNewPower.class.getSimpleName(), "32980-5.htm");
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "32980-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				final L2Npc handerMonkey = addSpawn(HANDERMONKEY, HANDERMONKEY_SPAWN, false, 300000);
				startQuestTimer("MOVE_DELAY", 500, handerMonkey, player);
				break;
			}
			case "32972-02.htm":
			{
				giveAdena(player, 140, true);
				addExpAndSp(player, 6700, 5);
				qs.exitQuest(false, true);
				htmltext = event;
				break;
			}
			case "32980-02.htm":
			{
				htmltext = event;
				break;
			}
			case "CHECK_PLAYER":
			{
				final L2PcInstance owner = npc.getVariables().getObject("OWNER", L2PcInstance.class);
				if (owner != null)
				{
					if (npc.calculateDistance(owner, false, false) < 100)
					{
						npc.getVariables().set("FAIL_COUNT", 0);
						final int loc_index = npc.getVariables().getInt("MOVE_INDEX", -1) + 1;
						if (loc_index > 0)
						{
							if (loc_index == 9)
							{
								npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getTemplate().getDisplayId(), NpcStringId.GO_GO_GO_CREEK));
								startQuestTimer("DELETE_NPC", 2000, npc, null);
								break;
							}
							npc.getVariables().set("MOVE_INDEX", loc_index);
							addMoveToDesire(npc, HANDERMONKEY_LOC[loc_index], 0);
						}
					}
					else
					{
						final int failCount = npc.getVariables().getInt("FAIL_COUNT", 0);
						npc.getVariables().set("FAIL_COUNT", failCount + 1);
						
						if (failCount >= 30)
						{
							npc.deleteMe();
							break;
						}
						
						startQuestTimer("CHECK_PLAYER", 2000, npc, owner);
						
						if (getRandom(100) < 10)
						{
							npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getTemplate().getDisplayId(), NpcStringId.COME_ON_CREEK));
						}
					}
				}
				else
				{
					npc.deleteMe();
				}
				break;
			}
			case "MOVE_DELAY":
			{
				npc.getVariables().set("OWNER", player);
				npc.setTitle(player.getName());
				npc.setIsRunning(true);
				npc.broadcastInfo();
				addMoveToDesire(npc, HANDERMONKEY_LOC[0], 0);
				npc.getVariables().set("MOVE_INDEX", 0);
				break;
			}
			case "DELETE_NPC":
			{
				npc.deleteMe();
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public void onMoveFinished(L2Npc npc)
	{
		npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getTemplate().getDisplayId(), NpcStringId.COME_ON_CREEK));
		final L2PcInstance owner = npc.getVariables().getObject("OWNER", L2PcInstance.class);
		
		if (owner != null)
		{
			startQuestTimer("CHECK_PLAYER", 2000, npc, owner);
			npc.setHeading(Util.calculateHeadingFrom(npc, owner));
			npc.broadcastPacket(new ExRotation(npc.getObjectId(), npc.getHeading()));
		}
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = null;
		
		switch (qs.getState())
		{
			case State.CREATED:
			{
				htmltext = npc.getId() == GALLINT ? "32980-01.htm" : "32972-01a.htm";
				break;
			}
			case State.STARTED:
			{
				htmltext = npc.getId() == GALLINT ? "32980-03.htm" : "32972-01.htm";
				break;
			}
			case State.COMPLETED:
			{
				htmltext = npc.getId() == GALLINT ? "32980-04.htm" : "32972-03.htm";
				break;
			}
		}
		return htmltext;
	}
}