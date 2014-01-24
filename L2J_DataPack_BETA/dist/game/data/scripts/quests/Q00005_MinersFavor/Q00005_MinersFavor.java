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
package quests.Q00005_MinersFavor;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;

/**
 * Miner's Favor (5)
 * @author malyelfik
 */
public class Q00005_MinersFavor extends Quest
{
	// NPCs
	private static final int BOLTER = 30554;
	private static final int SHARI = 30517;
	private static final int GARITA = 30518;
	private static final int REED = 30520;
	private static final int BRUNON = 30526;
	// Items
	private static final int BOLTERS_LIST = 1547;
	private static final int MINING_BOOTS = 1548;
	private static final int MINERS_PICK = 1549;
	private static final int BOOMBOOM_POWDER = 1550;
	private static final int REDSTONE_BEER = 1551;
	private static final int BOLTERS_SMELLY_SOCKS = 1552;
	private static final int NECKLACE = 906;
	// Misc
	private static final int MIN_LEVEL = 2;
	
	private Q00005_MinersFavor(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(BOLTER);
		addTalkId(BOLTER, SHARI, GARITA, REED, BRUNON);
		registerQuestItems(BOLTERS_LIST, MINING_BOOTS, MINERS_PICK, BOOMBOOM_POWDER, REDSTONE_BEER, BOLTERS_SMELLY_SOCKS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30554-03.htm":
				st.startQuest();
				st.giveItems(BOLTERS_LIST, 1);
				st.giveItems(BOLTERS_SMELLY_SOCKS, 1);
				break;
			case "30526-02.html":
				if (!st.hasQuestItems(BOLTERS_SMELLY_SOCKS))
				{
					return "30526-04.html";
				}
				st.takeItems(BOLTERS_SMELLY_SOCKS, -1);
				st.giveItems(MINERS_PICK, 1);
				checkProgress(st);
				break;
			case "30554-05.html":
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
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
		
		switch (npc.getId())
		{
			case BOLTER:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= MIN_LEVEL) ? "30554-02.htm" : "30554-01.html";
						break;
					case State.STARTED:
						if (st.isCond(1))
						{
							htmltext = "30554-04.html";
						}
						else
						{
							st.giveAdena(2466, true);
							st.addExpAndSp(5672, 446);
							st.giveItems(NECKLACE, 1);
							st.exitQuest(false, true);
							// Newbie Guide
							showOnScreenMsg(player, NpcStringId.DELIVERY_DUTY_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000);
							htmltext = "30554-06.html";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case BRUNON:
				if (st.isStarted())
				{
					htmltext = (st.hasQuestItems(MINERS_PICK)) ? "30526-03.html" : "30526-01.html";
				}
				break;
			case REED:
				htmltext = giveItem(st, npc.getId(), REDSTONE_BEER);
				break;
			case SHARI:
				htmltext = giveItem(st, npc.getId(), BOOMBOOM_POWDER);
				break;
			case GARITA:
				htmltext = giveItem(st, npc.getId(), MINING_BOOTS);
				break;
		}
		return htmltext;
	}
	
	private static void checkProgress(QuestState st)
	{
		if (st.hasQuestItems(BOLTERS_LIST, MINING_BOOTS, MINERS_PICK, BOOMBOOM_POWDER, REDSTONE_BEER))
		{
			st.setCond(2, true);
		}
	}
	
	private static String giveItem(QuestState st, int npcId, int itemId)
	{
		if (!st.isStarted())
		{
			return getNoQuestMsg(st.getPlayer());
		}
		else if (st.hasQuestItems(itemId))
		{
			return npcId + "-02.html";
		}
		st.giveItems(itemId, 1);
		st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		checkProgress(st);
		return npcId + "-01.html";
	}
	
	public static void main(String[] args)
	{
		new Q00005_MinersFavor(5, Q00005_MinersFavor.class.getSimpleName(), "Miner's Favor");
	}
}