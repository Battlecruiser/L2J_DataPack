/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00377_ExplorationOfTheGiantsCavePart2;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.RadarControl;
import com.l2jserver.gameserver.util.Util;

/**
 * Exploration of the Giants' Cave Part 2 (377)<br>
 * 2010-02-17 based on official Franz server.<br>
 * Original Jython script by Gnacik.
 * @author nonom
 */
public class Q00377_ExplorationOfTheGiantsCavePart2 extends Quest
{
	private static final String qn = "377_ExplorationOfTheGiantsCavePart2";
	
	// NPC's
	private static final int SOBLING = 31147;
	
	// Items
	private static final int TITAN_ANCIENT_BOOK = 14847;
	private static final int BOOK1 = 14842;
	private static final int BOOK2 = 14843;
	private static final int BOOK3 = 14844;
	private static final int BOOK4 = 14845;
	private static final int BOOK5 = 14846;
	
	// Drop Chance
	private static final int DROP_CHANCE = 50;
	
	// Mobs
	private static final int[] _mobs =
	{
		22661,
		22662,
		22663,
		22664,
		22665,
		22666,
		22667,
		22668,
		22669
	};
	
	// Rewards
	private static final int OBLIVION = 9625;
	private static final int DISCIPLINE = 9626;
	private static final int LEONARD = 9628;
	private static final int ADAMANTINE = 9629;
	private static final int ORICHALCUM = 9630;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("31147-02.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
			player.sendPacket(new RadarControl(0, 2, -113360, -244676, -15536));
		}
		else if (event.equalsIgnoreCase("31147-quit.html"))
		{
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		else if (Util.isDigit(event))
		{
			final int val = Integer.parseInt(event);
			switch (val)
			{
				case OBLIVION:
					htmltext = onExchangeRequest(st, val, 1, 5); // Giant's Codex - Oblivion
					break;
				case DISCIPLINE:
					htmltext = onExchangeRequest(st, val, 1, 5); // Giant's Codex - Discipline
					break;
				case LEONARD:
					htmltext = onExchangeRequest(st, val, 6, 1); // Leonard
					break;
				case ADAMANTINE:
					htmltext = onExchangeRequest(st, val, 3, 1); // Adamantine
					break;
				case ORICHALCUM:
					htmltext = onExchangeRequest(st, val, 4, 1); // Orichalcum
					break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == SOBLING)
		{
			switch (st.getState())
			{
				case State.CREATED:
					htmltext = (player.getLevel() >= 79) ? "31147-01.htm" : "31147-00.html";
					break;
				case State.STARTED:
					htmltext = (st.hasQuestItems(BOOK1) && st.hasQuestItems(BOOK2) && st.hasQuestItems(BOOK3) && st.hasQuestItems(BOOK4) && st.hasQuestItems(BOOK5)) ? "31147-03.html" : "31147-02a.html";
					break;
			}
			
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return null;
		}
		
		if ((st.getInt("cond") == 1) && (getRandom(100) < DROP_CHANCE))
		{
			st.giveItems(TITAN_ANCIENT_BOOK, 1);
			st.playSound("ItemSound.quest_itemget");
		}
		return super.onKill(npc, player, isPet);
	}
	
	private String onExchangeRequest(QuestState st, int giveid, int qty, int rem)
	{
		if ((st.getQuestItemsCount(BOOK1) >= rem) && (st.getQuestItemsCount(BOOK2) >= rem) && (st.getQuestItemsCount(BOOK3) >= rem) && (st.getQuestItemsCount(BOOK4) >= rem) && (st.getQuestItemsCount(BOOK5) >= rem))
		{
			st.takeItems(BOOK1, rem);
			st.takeItems(BOOK2, rem);
			st.takeItems(BOOK3, rem);
			st.takeItems(BOOK4, rem);
			st.takeItems(BOOK5, rem);
			st.giveItems(giveid, qty);
			st.playSound("ItemSound.quest_finish");
			return "31147-ok.html";
		}
		return "31147-no.html";
	}
	
	public Q00377_ExplorationOfTheGiantsCavePart2(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(SOBLING);
		addTalkId(SOBLING);
		addKillId(_mobs);
		
		questItemIds = new int[]
		{
			TITAN_ANCIENT_BOOK
		};
	}
	
	public static void main(String[] args)
	{
		new Q00377_ExplorationOfTheGiantsCavePart2(377, qn, "Exploration of the Giants' Cave - Part 2");
	}
}
