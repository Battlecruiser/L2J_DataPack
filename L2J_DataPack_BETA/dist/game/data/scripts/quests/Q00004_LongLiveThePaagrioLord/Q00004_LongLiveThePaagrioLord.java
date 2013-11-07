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
package quests.Q00004_LongLiveThePaagrioLord;

import com.l2jserver.gameserver.enums.PcRace;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;

/**
 * Long Live the Pa'agrio Lord (4)
 * @author malyelfik
 */
public class Q00004_LongLiveThePaagrioLord extends Quest
{
	// NPCs
	private static final int KUNAI = 30559;
	private static final int USKA = 30560;
	private static final int GROOKIN = 30562;
	private static final int VARKEES = 30566;
	private static final int NAKUSIN = 30578;
	private static final int HESTUI = 30585;
	private static final int URUTU = 30587;
	// Items
	private static final int CLUB = 4;
	private static final int HONEY_KHANDAR = 1541;
	private static final int BEAR_FUR_CLOAK = 1542;
	private static final int BLOODY_AXE = 1543;
	private static final int ANCESTOR_SKULL = 1544;
	private static final int SPIDER_DUST = 1545;
	private static final int DEEP_SEA_ORB = 1546;
	// Misc
	private static final int MIN_LEVEL = 2;
	
	private Q00004_LongLiveThePaagrioLord(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(NAKUSIN);
		addTalkId(NAKUSIN, VARKEES, URUTU, HESTUI, KUNAI, USKA, GROOKIN);
		registerQuestItems(HONEY_KHANDAR, BEAR_FUR_CLOAK, BLOODY_AXE, ANCESTOR_SKULL, SPIDER_DUST, DEEP_SEA_ORB);
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
			case "30578-03.htm":
				st.startQuest();
				break;
			case "30578-05.html":
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
			case NAKUSIN:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getRace() != PcRace.Orc) ? "30578-00.htm" : (player.getLevel() >= MIN_LEVEL) ? "30578-02.htm" : "30578-01.htm";
						break;
					case State.STARTED:
						if (st.isCond(1))
						{
							htmltext = "30578-04.html";
						}
						else
						{
							st.giveItems(CLUB, 1);
							// Newbie Guide
							showOnScreenMsg(player, NpcStringId.DELIVERY_DUTY_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000);
							st.addExpAndSp(4254, 335);
							st.giveAdena(1850, true);
							st.exitQuest(false, true);
							htmltext = "30578-06.html";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case VARKEES:
				htmltext = giveItem(st, npc.getId(), HONEY_KHANDAR, getRegisteredItemIds());
				break;
			case URUTU:
				htmltext = giveItem(st, npc.getId(), DEEP_SEA_ORB, getRegisteredItemIds());
				break;
			case HESTUI:
				htmltext = giveItem(st, npc.getId(), BEAR_FUR_CLOAK, getRegisteredItemIds());
				break;
			case KUNAI:
				htmltext = giveItem(st, npc.getId(), SPIDER_DUST, getRegisteredItemIds());
				break;
			case USKA:
				htmltext = giveItem(st, npc.getId(), ANCESTOR_SKULL, getRegisteredItemIds());
				break;
			case GROOKIN:
				htmltext = giveItem(st, npc.getId(), BLOODY_AXE, getRegisteredItemIds());
				break;
		}
		return htmltext;
	}
	
	private static String giveItem(QuestState st, int npcId, int itemId, int... items)
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
		if (st.hasQuestItems(items))
		{
			st.setCond(2, true);
		}
		return npcId + "-01.html";
	}
	
	public static void main(String[] args)
	{
		new Q00004_LongLiveThePaagrioLord(4, Q00004_LongLiveThePaagrioLord.class.getSimpleName(), "Long Live the Pa'agrio Lord");
	}
}