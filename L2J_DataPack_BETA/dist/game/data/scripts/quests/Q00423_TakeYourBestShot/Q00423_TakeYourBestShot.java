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
package quests.Q00423_TakeYourBestShot;

import quests.Q00249_PoisonedPlainsOfTheLizardmen.Q00249_PoisonedPlainsOfTheLizardmen;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * Take Your Best Shot (423)
 * @author Gnacik
 * @version 2010-06-26 Based on official server Franz
 */
public class Q00423_TakeYourBestShot extends Quest
{
	// NPC
	private static final int BATRACOS = 32740;
	private static final int JOHNNY = 32744;
	// Item
	private static final int SEER_UGOROS_PASS = 15496;
	// Spawn chance x/1000
	private static final int SPAWN_CHANCE = 2;
	// Guard
	private static final int TANTA_LIZARDMAN_GUARD = 18862;
	// Mobs
	private static final int[] MOBS =
	{
		22768,
		22769,
		22770,
		22771,
		22772,
		22773,
		22774
	};
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == JOHNNY)
		{
			if (event.equalsIgnoreCase("32744-04.htm"))
			{
				st.startQuest();
			}
			else if (event.equalsIgnoreCase("32744-quit.htm"))
			{
				st.exitQuest(true);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == JOHNNY)
		{
			switch (st.getState())
			{
				case State.CREATED:
					QuestState _prev = player.getQuestState(Q00249_PoisonedPlainsOfTheLizardmen.class.getSimpleName());
					if ((_prev != null) && _prev.isCompleted() && (player.getLevel() >= 82))
					{
						htmltext = (st.hasQuestItems(SEER_UGOROS_PASS)) ? "32744-07.htm" : "32744-01.htm";
					}
					else
					{
						htmltext = "32744-00.htm";
					}
					break;
				case State.STARTED:
					if (st.isCond(1))
					{
						htmltext = "32744-05.htm";
					}
					else if (st.isCond(2))
					{
						htmltext = "32744-06.htm";
					}
					break;
			}
		}
		else if (npc.getNpcId() == BATRACOS)
		{
			if (st.getState() == State.CREATED)
			{
				htmltext = (st.hasQuestItems(SEER_UGOROS_PASS)) ? "32740-05.htm" : "32740-00.htm";
			}
			else if ((st.getState() == State.STARTED) && (st.isCond(1)))
			{
				htmltext = "32740-02.htm";
			}
			else if ((st.getState() == State.STARTED) && (st.isCond(2)))
			{
				st.giveItems(SEER_UGOROS_PASS, 1);
				st.exitQuest(true, true);
				htmltext = "32740-04.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		if (npc.isInsideRadius(96782, 85918, 100, true))
		{
			return "32740-ugoros.htm";
		}
		return "32740.htm";
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (Util.contains(MOBS, npc.getNpcId()) && (getRandom(1000) <= SPAWN_CHANCE))
		{
			L2Npc guard = addSpawn(TANTA_LIZARDMAN_GUARD, npc, false);
			attackPlayer((L2Attackable) guard, player);
		}
		else if ((npc.getNpcId() == TANTA_LIZARDMAN_GUARD) && (st.isCond(1)))
		{
			st.setCond(2, true);
		}
		return null;
	}
	
	private void attackPlayer(L2Attackable npc, L2PcInstance player)
	{
		npc.setIsRunning(true);
		npc.addDamageHate(player, 0, 999);
		npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
	}
	
	public Q00423_TakeYourBestShot(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(JOHNNY, BATRACOS);
		addTalkId(JOHNNY, BATRACOS);
		addFirstTalkId(BATRACOS);
		addKillId(TANTA_LIZARDMAN_GUARD);
		addKillId(MOBS);
	}
	
	public static void main(String[] args)
	{
		new Q00423_TakeYourBestShot(423, Q00423_TakeYourBestShot.class.getSimpleName(), "Take Your Best Shot!");
	}
}
