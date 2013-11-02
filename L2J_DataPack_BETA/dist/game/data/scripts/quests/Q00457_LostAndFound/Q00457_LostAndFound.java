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
package quests.Q00457_LostAndFound;

import java.util.Set;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.enums.QuestType;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;

/**
 * Lost and Found (457)
 * @author nonom
 */
public final class Q00457_LostAndFound extends Quest
{
	private static final int GUMIEL = 32759;
	private static final int ESCORT_CHECKER = 32764;
	private static final int[] SOLINA_CLAN =
	{
		22789, // Guide Solina
		22790, // Seeker Solina
		22791, // Savior Solina
		22793, // Ascetic Solina
	};
	
	private static final int PACKAGED_BOOK = 15716;
	
	private static final int CHANCE_SPAWN = 1; // 1%
	
	private static int _count = 0;
	private static Set<L2Spawn> _escortCheckers;
	private static L2Npc _gumiel = null;
	
	private Q00457_LostAndFound(int id, String name, String descr)
	{
		super(id, name, descr);
		addStartNpc(GUMIEL);
		addFirstTalkId(GUMIEL);
		addTalkId(GUMIEL);
		addSpawnId(GUMIEL);
		addKillId(SOLINA_CLAN);
		
		_escortCheckers = SpawnTable.getInstance().getSpawns(ESCORT_CHECKER);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = null;
		switch (event)
		{
			case "32759-06.html":
				_count = 0;
				st.startQuest();
				npc.setTarget(player);
				npc.setWalking();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
				startQuestTimer("check", 1000, npc, player, true);
				startQuestTimer("time_limit", 600000, npc, player);
				startQuestTimer("talk_time", 120000, npc, player);
				startQuestTimer("talk_time2", 30000, npc, player);
				break;
			case "talk_time":
				npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.AH_I_THINK_I_REMEMBER_THIS_PLACE));
				break;
			case "talk_time2":
				npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.WHAT_WERE_YOU_DOING_HERE));
				startQuestTimer("talk_time3", 10 * 1000, npc, player);
				break;
			case "talk_time3":
				npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.I_GUESS_YOURE_THE_SILENT_TYPE_THEN_ARE_YOU_LOOKING_FOR_TREASURE_LIKE_ME));
				break;
			case "time_limit":
				startQuestTimer("stop", 2000, npc, player);
				st.exitQuest(QuestType.DAILY);
				break;
			case "check":
				final double distance = npc.calculateDistance(player, false, false);
				if (distance > 1000)
				{
					if (distance > 5000)
					{
						startQuestTimer("stop", 2000, npc, player);
						st.exitQuest(QuestType.DAILY);
					}
					else if (_count == 0)
					{
						npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.HEY_DONT_GO_SO_FAST));
						_count = 1;
					}
					else if (_count == 1)
					{
						npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.ITS_HARD_TO_FOLLOW));
						_count = 2;
					}
					else if (_count == 2)
					{
						startQuestTimer("stop", 2000, npc, player);
						st.exitQuest(QuestType.DAILY);
					}
				}
				for (L2Spawn escortSpawn : _escortCheckers)
				{
					final L2Npc escort = escortSpawn.getLastSpawn();
					if ((escort != null) && npc.isInsideRadius(escort, 1000, false, false))
					{
						startQuestTimer("stop", 1000, npc, player);
						startQuestTimer("bye", 3000, npc, player);
						cancelQuestTimer("check", npc, player);
						npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.AH_FRESH_AIR));
						giveItems(player, PACKAGED_BOOK, 1);
						st.exitQuest(QuestType.DAILY, true);
						break;
					}
				}
				break;
			case "stop":
				npc.setTarget(null);
				npc.getAI().stopFollow();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
				cancelQuestTimer("check", npc, player);
				cancelQuestTimer("time_limit", npc, player);
				cancelQuestTimer("talk_time", npc, player);
				cancelQuestTimer("talk_time2", npc, player);
				_gumiel = null;
				break;
			case "bye":
				npc.deleteMe();
				break;
			default:
			{
				htmltext = event;
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.getTarget() != null)
		{
			if (npc.getTarget().equals(player))
			{
				return "32759-08.html";
			}
			else if (_gumiel != null)
			{
				return "32759-01a.html";
			}
		}
		return "32759.html";
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if ((_gumiel == null) && (getRandom(100) < CHANCE_SPAWN))
		{
			addSpawn(GUMIEL, npc);
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		_gumiel = npc;
		_gumiel.getSpawn().stopRespawn();
		return super.onSpawn(npc);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				htmltext = (player.getLevel() > 81) ? "32759-01.htm" : "32759-03.html";
				break;
			case State.COMPLETED:
				if (st.isNowAvailable())
				{
					st.setState(State.CREATED);
					htmltext = (player.getLevel() > 81) ? "32759-01.htm" : "32759-03.html";
				}
				else
				{
					htmltext = "32759-02.html";
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00457_LostAndFound(457, Q00457_LostAndFound.class.getSimpleName(), "Lost and Found");
	}
}
