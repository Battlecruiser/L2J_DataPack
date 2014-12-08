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
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Lost and Found (457)
 * @author nonom
 */
public final class Q00457_LostAndFound extends Quest
{
	// NPCs
	private static final int GUMIEL = 32759;
	private static final int ESCORT_CHECKER = 32764;
	private static final int[] SOLINA_CLAN =
	{
		22789, // Guide Solina
		22790, // Seeker Solina
		22791, // Savior Solina
		22793, // Ascetic Solina
	};
	// Misc
	private static final int PACKAGED_BOOK = 15716;
	private static final int CHANCE_SPAWN = 1; // 1%
	private static final int MIN_LV = 82;
	private static Set<L2Spawn> _escortCheckers;
	
	public Q00457_LostAndFound()
	{
		super(457, Q00457_LostAndFound.class.getSimpleName(), "Lost and Found");
		addStartNpc(GUMIEL);
		addSpawnId(ESCORT_CHECKER);
		addFirstTalkId(GUMIEL);
		addTalkId(GUMIEL);
		addKillId(SOLINA_CLAN);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = null;
		switch (event)
		{
			case "32759-06.html":
			{
				npc.setScriptValue(0);
				st.startQuest();
				npc.setTarget(player);
				npc.setWalking();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
				startQuestTimer("CHECK", 1000, npc, player, true);
				startQuestTimer("TIME_LIMIT", 600000, npc, player);
				startQuestTimer("TALK_TIME", 120000, npc, player);
				startQuestTimer("TALK_TIME2", 30000, npc, player);
				break;
			}
			case "TALK_TIME":
			{
				broadcastNpcSay(npc, player, NpcStringId.AH_I_THINK_I_REMEMBER_THIS_PLACE, false);
				break;
			}
			case "TALK_TIME2":
			{
				broadcastNpcSay(npc, player, NpcStringId.WHAT_WERE_YOU_DOING_HERE, false);
				startQuestTimer("TALK_TIME3", 10 * 1000, npc, player);
				break;
			}
			case "TALK_TIME3":
			{
				broadcastNpcSay(npc, player, NpcStringId.I_GUESS_YOURE_THE_SILENT_TYPE_THEN_ARE_YOU_LOOKING_FOR_TREASURE_LIKE_ME, false);
				break;
			}
			case "TIME_LIMIT":
			{
				startQuestTimer("STOP", 2000, npc, player);
				st.exitQuest(QuestType.DAILY);
				break;
			}
			case "CHECK":
			{
				final double distance = npc.calculateDistance(player, true, false);
				if (distance > 1000)
				{
					if (distance > 5000)
					{
						startQuestTimer("STOP", 2000, npc, player);
						st.exitQuest(QuestType.DAILY);
					}
					else if (npc.isScriptValue(0))
					{
						broadcastNpcSay(npc, player, NpcStringId.HEY_DONT_GO_SO_FAST, true);
						npc.setScriptValue(1);
					}
					else if (npc.isScriptValue(1))
					{
						broadcastNpcSay(npc, player, NpcStringId.ITS_HARD_TO_FOLLOW, true);
						npc.setScriptValue(2);
					}
					else if (npc.isScriptValue(2))
					{
						startQuestTimer("STOP", 2000, npc, player);
						st.exitQuest(QuestType.DAILY);
					}
				}
				for (L2Spawn escortSpawn : _escortCheckers)
				{
					final L2Npc escort = escortSpawn.getLastSpawn();
					if ((escort != null) && npc.isInsideRadius(escort, 1000, false, false))
					{
						startQuestTimer("STOP", 1000, npc, player);
						startQuestTimer("BYE", 3000, npc, player);
						cancelQuestTimer("CHECK", npc, player);
						npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_ALL, npc.getName(), NpcStringId.AH_FRESH_AIR));
						broadcastNpcSay(npc, player, NpcStringId.AH_FRESH_AIR, false);
						st.giveItems(PACKAGED_BOOK, 1);
						st.exitQuest(QuestType.DAILY, true);
						break;
					}
				}
				break;
			}
			case "STOP":
			{
				npc.setTarget(null);
				npc.getAI().stopFollow();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
				cancelQuestTimer("CHECK", npc, player);
				cancelQuestTimer("TIME_LIMIT", npc, player);
				cancelQuestTimer("TALK_TIME", npc, player);
				cancelQuestTimer("TALK_TIME2", npc, player);
				break;
			}
			case "BYE":
			{
				npc.deleteMe();
				break;
			}
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
			return npc.getTarget().equals(player) ? "32759-08.html" : "32759-01a.html";
		}
		return "32759.html";
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = getQuestState(player, true);
		
		if ((getRandom(100) < CHANCE_SPAWN) && st.isNowAvailable() && (player.getLevel() >= MIN_LV))
		{
			addSpawn(GUMIEL, npc);
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = getQuestState(player, true);
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (player.getLevel() >= MIN_LV) ? "32759-01.htm" : "32759-03.html";
				break;
			}
			case State.COMPLETED:
			{
				if (st.isNowAvailable())
				{
					st.setState(State.CREATED);
					htmltext = (player.getLevel() >= MIN_LV) ? "32759-01.htm" : "32759-03.html";
				}
				else
				{
					htmltext = "32759-02.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		_escortCheckers = SpawnTable.getInstance().getSpawns(ESCORT_CHECKER);
		return super.onSpawn(npc);
	}
	
	public void broadcastNpcSay(L2Npc npc, L2PcInstance player, NpcStringId stringId, boolean whisper)
	{
		((whisper) ? player : npc).sendPacket(new NpcSay(npc.getObjectId(), ((whisper) ? Say2.TELL : Say2.ALL), npc.getId(), stringId));
	}
}
