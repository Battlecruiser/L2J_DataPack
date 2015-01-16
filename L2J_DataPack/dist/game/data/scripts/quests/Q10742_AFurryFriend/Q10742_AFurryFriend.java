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
package quests.Q10742_AFurryFriend;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExSendUIEvent;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * @author Sdw
 */
public class Q10742_AFurryFriend extends Quest
{
	// NPC's
	private static final int LEIRA = 33952;
	private static final int KIKU_S_CAVE = 33995;
	private static final int RICKY = 19552;
	private static final int KIKU = 23453;
	// Misc
	private static final int MIN_LEVEL = 11;
	private static final int MAX_LEVEL = 20;
	// Location
	private static final Location RICKY_SPAWN = new Location(-78138, 237328, -3548);
	// Waypoints
	protected static Location[] WAYPOINTS =
	{
		new Location(-78152, 237352, -3569),
		new Location(-79176, 236792, -3440),
		new Location(-80072, 237064, -3311),
		new Location(-80440, 237320, -3313)
	};
	
	public Q10742_AFurryFriend()
	{
		super(10742, Q10742_AFurryFriend.class.getSimpleName(), "A Furry Friend");
		addStartNpc(LEIRA);
		addTalkId(LEIRA, KIKU_S_CAVE);
		addMoveFinishedId(RICKY);
		addCondRace(Race.ERTHEIA, "fixme.html");
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "fixme.html");
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
			case "33952-02.htm":
			case "33952-05.htm":
			{
				htmltext = event;
				break;
			}
			case "33952-03.htm":
			{
				qs.startQuest();
				final L2Npc ricky = addSpawn(RICKY, RICKY_SPAWN);
				ricky.setIsRunning(true);
				ricky.setSummoner(player);
				startQuestTimer("start_move_ricky", 1000, ricky, player);
				showOnScreenMsg(player, NpcStringId.FOLLOW_RICKY, ExShowScreenMessage.TOP_CENTER, 4500);
				htmltext = event;
				break;
			}
			case "start_move_ricky":
			{
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, WAYPOINTS[0]);
				npc.setScriptValue(0);
				break;
			}
			case "33995-03.htm":
			{
				if (qs.isStarted())
				{
					if (!player.getKnownList().getKnownCharactersInRadius(500).stream().anyMatch(n -> (n.getId() == RICKY) && (n.getSummoner() == player)))
					{
						showOnScreenMsg(player, NpcStringId.TAKE_RICKY_TO_LEIRA_IN_UNDER_2_MINUTES, ExShowScreenMessage.MIDDLE_CENTER, 4500);
						final L2Npc ricky = addSpawn(RICKY, player.getLocation());
						ricky.setSummoner(player);
						ricky.setTitle(player.getAppearance().getVisibleName());
						ricky.setIsRunning(true);
						ricky.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
						startQuestTimer("check_ricky_distance", 1000, ricky, player, true);
						startQuestTimer("unspawn_ricky_failed", 120000, ricky, player);
						player.sendPacket(new ExSendUIEvent(player, false, false, 0, 120, NpcStringId.REMAINING_TIME));
					}
				}
				break;
			}
			case "check_ricky_distance":
			{
				if (player == null)
				{
					startQuestTimer("unspawn_ricky", 2000, npc, null);
				}
				else
				{
					// Follow was breaking sometimes, making sure it doesn't happen.
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
					
					final double distanceToRicky = player.calculateDistance(npc, false, true);
					
					if ((distanceToRicky > 200) && (distanceToRicky < 500))
					{
						showOnScreenMsg(player, NpcStringId.YOU_ARE_FAR_FROM_RICKY, ExShowScreenMessage.TOP_CENTER, 4500);
					}
					else if (distanceToRicky > 500)
					{
						startQuestTimer("unspawn_ricky_failed", 120000, npc, player);
					}
					else
					{
						final L2Npc leira = (L2Npc) npc.getKnownList().getKnownCharactersInRadius(100).stream().filter(n -> (n.getId() == LEIRA)).findFirst().orElse(null);
						if (leira != null)
						{
							qs.setCond(2, true);
							showOnScreenMsg(player, NpcStringId.RICKY_HAS_FOUND_LEIRA, ExShowScreenMessage.TOP_CENTER, 4500);
							player.sendPacket(new ExSendUIEvent(player, false, false, 0, 0, NpcStringId.REMAINING_TIME));
							startQuestTimer("unspawn_ricky", 2000, npc, player);
							cancelQuestTimer("check_ricky_distance", npc, player);
						}
					}
				}
				break;
			}
			case "unspawn_ricky":
			{
				npc.deleteMe();
				break;
			}
			case "unspawn_ricky_failed":
			{
				showOnScreenMsg(player, NpcStringId.BRING_BACK_RICKY, ExShowScreenMessage.TOP_CENTER, 4500);
				npc.deleteMe();
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		switch (npc.getId())
		{
			case LEIRA:
			{
				if (qs.isCreated())
				{
					htmltext = "33952-01.htm";
				}
				else if (qs.isCond(2))
				{
					giveAdena(player, 2500, true);
					addExpAndSp(player, 52516, 5);
					qs.exitQuest(false, true);
					htmltext = "33952-04.htm";
				}
				break;
			}
			case KIKU_S_CAVE:
			{
				if (qs.isStarted())
				{
					if (getRandomBoolean())
					{
						htmltext = "33995-01.htm";
					}
					else
					{
						addAttackPlayerDesire(addSpawn(KIKU, player.getLocation(), true, 120000), player);
						showOnScreenMsg(player, NpcStringId.RICKY_IS_NOT_HERE_NTRY_SEARCHING_ANOTHER_KIKU_S_CAVE, ExShowScreenMessage.TOP_CENTER, 4500);
						htmltext = "33995-02.htm";
					}
				}
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public void onMoveFinished(L2Npc npc)
	{
		final int currentWaypoint = npc.getScriptValue();
		
		switch (currentWaypoint)
		{
			case 0:
			case 1:
			case 2:
			{
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, WAYPOINTS[currentWaypoint + 1]);
				npc.setScriptValue(currentWaypoint + 1);
				break;
			}
			case 3:
			{
				showOnScreenMsg(npc.getSummoner().getActingPlayer(), NpcStringId.RICKY_IS_ENTERING_KIKU_S_CAVE, ExShowScreenMessage.TOP_CENTER, 4500);
				npc.deleteMe();
				break;
			}
		}
	}
}
