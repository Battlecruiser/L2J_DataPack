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
package quests.Q00610_MagicalPowerOfWaterPart2;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.Util;

/**
 * Magical Power of Water - Part 2 (610)
 * @author Joxit
 */
public class Q00610_MagicalPowerOfWaterPart2 extends Quest
{
	// NPCs
	private static final int ASEFA = 31372;
	private static final int VARKA_TOTEM = 31560;
	// Monster
	private static final int ASHUTAR = 25316;
	// Items
	private static final int GREEN_TOTEM = 7238;
	private static final int ASHUTAR_HEART = 7239;
	// Misc
	private static final int MIN_LEVEL = 75;
	
	private Q00610_MagicalPowerOfWaterPart2(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ASEFA);
		addTalkId(ASEFA, VARKA_TOTEM);
		addKillId(ASHUTAR);
		registerQuestItems(GREEN_TOTEM, ASHUTAR_HEART);
		
		final String test = loadGlobalQuestVar("Q00610_respawn");
		final long remain = (!test.isEmpty()) ? (Long.parseLong(test) - System.currentTimeMillis()) : 0;
		if (remain > 0)
		{
			startQuestTimer("spawn_npc", remain, null, null);
		}
		else
		{
			addSpawn(VARKA_TOTEM, 105452, -36775, -1050, 34000, false, 0, true);
		}
	}
	
	@Override
	public void actionForEachPlayer(L2PcInstance player, L2Npc npc, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st != null) && Util.checkIfInRange(1500, npc, player, false))
		{
			if (npc.getNpcId() == ASHUTAR)
			{
				switch (st.getCond())
				{
					case 1: // take the item and give the heart
						st.takeItems(GREEN_TOTEM, 1);
					case 2:
						if (!st.hasQuestItems(ASHUTAR_HEART))
						{
							st.giveItems(ASHUTAR_HEART, 1);
						}
						st.setCond(3, true);
						break;
				}
			}
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (player != null)
		{
			final QuestState st = player.getQuestState(getName());
			if (st == null)
			{
				return null;
			}
			
			switch (event)
			{
				case "31372-02.html":
					st.startQuest();
					htmltext = event;
					break;
				case "give_heart":
					if (st.hasQuestItems(ASHUTAR_HEART))
					{
						st.addExpAndSp(10000, 0);
						st.exitQuest(true, true);
						htmltext = "31372-06.html";
					}
					else
					{
						htmltext = "31372-07.html";
					}
					break;
				case "spawn_totem":
					htmltext = (st.hasQuestItems(GREEN_TOTEM)) ? spawnAshutar(npc, st) : "31560-04.html";
					break;
			}
		}
		else
		{
			if (event.equals("despawn_ashutar"))
			{
				npc.broadcastPacket(new NpcSay(npc, Say2.NPC_ALL, NpcStringId.THE_POWER_OF_CONSTRAINT_IS_GETTING_WEAKER_YOUR_RITUAL_HAS_FAILED));
				npc.deleteMe();
				addSpawn(VARKA_TOTEM, 105452, -36775, -1050, 34000, false, 0, true);
			}
			else if (event.equals("spawn_npc"))
			{
				addSpawn(VARKA_TOTEM, 105452, -36775, -1050, 34000, false, 0, true);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final int respawnMinDelay = (int) (43200000 * Config.RAID_MIN_RESPAWN_MULTIPLIER);
		final int respawnMaxDelay = (int) (129600000 * Config.RAID_MAX_RESPAWN_MULTIPLIER);
		final int respawnDelay = getRandom(respawnMinDelay, respawnMaxDelay);
		cancelQuestTimer("despawn_ashutar", npc, null);
		saveGlobalQuestVar("Q00610_respawn", String.valueOf(System.currentTimeMillis() + respawnDelay));
		startQuestTimer("spawn_npc", respawnDelay, null, null);
		executeForEachPlayer(killer, npc, isSummon, true, false);
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case ASEFA:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= MIN_LEVEL) ? (st.hasQuestItems(GREEN_TOTEM)) ? "31372-01.htm" : "31372-00a.html" : "31372-00b.html";
						break;
					case State.STARTED:
						htmltext = (st.isCond(1)) ? "31372-03.html" : (st.hasQuestItems(ASHUTAR_HEART)) ? "31372-04.html" : "31372-05.html";
						break;
				}
				break;
			case VARKA_TOTEM:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "31560-01.html";
							break;
						case 2:
							htmltext = spawnAshutar(npc, st);
							break;
						case 3:
							htmltext = "31560-05.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
	
	private String spawnAshutar(L2Npc npc, QuestState st)
	{
		if (getQuestTimer("spawn_npc", null, null) != null)
		{
			return "31560-03.html";
		}
		if (st.isCond(1))
		{
			st.takeItems(GREEN_TOTEM, 1);
			st.setCond(2, true);
		}
		npc.deleteMe();
		final L2Npc ashutar = addSpawn(ASHUTAR, 104825, -36926, -1136, 0, false, 0);
		ashutar.broadcastPacket(new NpcSay(ashutar, Say2.NPC_ALL, NpcStringId.THE_MAGICAL_POWER_OF_WATER_COMES_FROM_THE_POWER_OF_STORM_AND_HAIL_IF_YOU_DARE_TO_CONFRONT_IT_ONLY_DEATH_WILL_AWAIT_YOU));
		startQuestTimer("despawn_ashutar", 1200000, ashutar, null);
		return "31560-02.html";
	}
	
	public static void main(String[] args)
	{
		new Q00610_MagicalPowerOfWaterPart2(610, Q00610_MagicalPowerOfWaterPart2.class.getSimpleName(), "Magical Power of Water - Part 2");
	}
}