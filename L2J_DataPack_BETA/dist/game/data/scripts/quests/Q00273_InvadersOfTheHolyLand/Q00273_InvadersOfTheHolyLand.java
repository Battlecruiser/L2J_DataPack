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
package quests.Q00273_InvadersOfTheHolyLand;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.model.PlayerVariables;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * Invaders of the Holy Land (273)
 * @author xban1x
 */
public final class Q00273_InvadersOfTheHolyLand extends Quest
{
	// NPC
	private static final int VARKEES = 30566;
	// Items
	private static final int BLACK_SOULSTONE = 1475;
	private static final int RED_SOULSTONE = 1476;
	// Rewards
	private static final ItemHolder SOULSHOTS_NO_GRADE_FOR_ROOKIES = new ItemHolder(5789, 6000);
	// Monsters
	private static final Map<Integer, Integer> MONSTERS = new HashMap<>();
	static
	{
		MONSTERS.put(20311, 90); // Rakeclaw Imp
		MONSTERS.put(20312, 87); // Rakeclaw Imp Hunter
		MONSTERS.put(20313, 77); // Rakeclaw Imp Chieftain
	}
	// Misc
	private static final int MIN_LVL = 6;
	
	private Q00273_InvadersOfTheHolyLand(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(VARKEES);
		addTalkId(VARKEES);
		addKillId(MONSTERS.keySet());
		registerQuestItems(BLACK_SOULSTONE, RED_SOULSTONE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st != null)
		{
			switch (event)
			{
				case "30566-04.htm":
				{
					st.startQuest();
					htmltext = event;
					break;
				}
				case "30566-08.html":
				{
					st.exitQuest(true, true);
					htmltext = event;
					break;
				}
				case "30566-09.html":
				{
					htmltext = event;
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = killer.getQuestState(getName());
		if (st != null)
		{
			if (getRandom(100) <= MONSTERS.get(npc.getNpcId()))
			{
				st.giveItems(BLACK_SOULSTONE, 1);
			}
			else
			{
				st.giveItems(RED_SOULSTONE, 1);
			}
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = null;
		if (st != null)
		{
			switch (st.getState())
			{
				case State.CREATED:
				{
					htmltext = (player.getRace() == Race.Orc) ? (player.getLevel() >= MIN_LVL) ? "30566-03.htm" : "30566-02.htm" : "30566-01.htm";
					break;
				}
				case State.STARTED:
				{
					if (hasAtLeastOneQuestItem(player, BLACK_SOULSTONE, RED_SOULSTONE))
					{
						final long black = st.getQuestItemsCount(BLACK_SOULSTONE);
						final long red = st.getQuestItemsCount(RED_SOULSTONE);
						st.giveAdena((red * 10) + (black * 3) + ((red > 0) ? (((red + black) >= 10) ? 1800 : 0) : ((black >= 10) ? 1500 : 0)), true);
						takeItems(player, -1, BLACK_SOULSTONE, RED_SOULSTONE);
						final PlayerVariables vars = player.getVariables();
						if ((player.getLevel() < 25) && !vars.getBool("NEWBIE_SHOTS", false))
						{
							st.giveItems(SOULSHOTS_NO_GRADE_FOR_ROOKIES);
							vars.set("NEWBIE_SHOTS", true);
							st.playTutorialVoice("tutorial_voice_26");
						}
						if (vars.getString("GUIDE_MISSION", null) == null)
						{
							vars.set("GUIDE_MISSION", 1000);
							player.sendPacket(new ExShowScreenMessage(NpcStringId.ACQUISITION_OF_SOULSHOT_FOR_BEGINNERS_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000));
						}
						else if (((vars.getInteger("GUIDE_MISSION") % 10000) / 1000) != 1)
						{
							vars.set("GUIDE_MISSION", vars.getInteger("GUIDE_MISSION") + 1000);
							player.sendPacket(new ExShowScreenMessage(NpcStringId.ACQUISITION_OF_SOULSHOT_FOR_BEGINNERS_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000));
						}
						htmltext = (red > 0) ? "30566-07.html" : "30566-06.html";
					}
					else
					{
						htmltext = "30566-05.html";
					}
					break;
				}
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00273_InvadersOfTheHolyLand(273, Q00273_InvadersOfTheHolyLand.class.getSimpleName(), "Invaders of the Holy Land");
	}
}
