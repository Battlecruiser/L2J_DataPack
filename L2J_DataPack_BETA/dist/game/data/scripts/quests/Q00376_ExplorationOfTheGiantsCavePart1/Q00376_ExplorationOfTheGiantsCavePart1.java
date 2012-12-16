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
package quests.Q00376_ExplorationOfTheGiantsCavePart1;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.RadarControl;
import com.l2jserver.gameserver.util.Util;

/**
 * Exploration of the Giants' Cave Part 1 (376)<br>
 * Original Jython script by Gnacik.
 * @author nonom
 * @version 2010-02-17 based on official Franz server
 */
public class Q00376_ExplorationOfTheGiantsCavePart1 extends Quest
{
	// NPC's
	private static final int SOBLING = 31147;
	// Items
	private static final int ANCIENT_PARCHMENT = 14841;
	private static final int BOOK1 = 14836;
	private static final int BOOK2 = 14837;
	private static final int BOOK3 = 14838;
	private static final int BOOK4 = 14839;
	private static final int BOOK5 = 14840;
	// Drop Chance
	private static final int DROP_CHANCE = 20;
	// Mobs
	private static final int[] MOBS =
	{
		22670,
		22671,
		22672,
		22673,
		22674,
		22675,
		22676,
		22677
	};
	// Rewards
	private static final int RECIPE_DYNASTY_SWORD_60 = 9967;
	private static final int RECIPE_DYNASTY_BLADE_60 = 9968;
	private static final int RECIPE_DYNASTY_PHANTOM_60 = 9969;
	private static final int RECIPE_DYNASTY_BOW_60 = 9970;
	private static final int RECIPE_DYNASTY_KNIFE_60 = 9971;
	private static final int RECIPE_DYNASTY_HALBERD_60 = 9972;
	private static final int RECIPE_DYNASTY_CUDGEL_60 = 9973;
	private static final int RECIPE_DYNASTY_MACE_60 = 9974;
	private static final int RECIPE_DYNASTY_BAGHNAKH_60 = 9975;
	private static final int LEONARD = 9628;
	private static final int ADAMANTINE = 9629;
	private static final int ORICHALCUM = 9630;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("31147-02.htm"))
		{
			st.startQuest();
			player.sendPacket(new RadarControl(0, 2, 185712, 47414, -4350));
		}
		else if (event.equalsIgnoreCase("31147-quit.html"))
		{
			st.exitQuest(true, true);
		}
		else if (Util.isDigit(event))
		{
			final int val = Integer.parseInt(event);
			switch (val)
			{
				case RECIPE_DYNASTY_SWORD_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Sword (60%)
					break;
				case RECIPE_DYNASTY_BLADE_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Blade (60%)
					break;
				case RECIPE_DYNASTY_PHANTOM_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Phantom (60%)
					break;
				case RECIPE_DYNASTY_BOW_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Bow (60%)
					break;
				case RECIPE_DYNASTY_KNIFE_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Knife (60%)
					break;
				case RECIPE_DYNASTY_HALBERD_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Halberd (60%)
					break;
				case RECIPE_DYNASTY_CUDGEL_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Cudgel (60%)
					break;
				case RECIPE_DYNASTY_MACE_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Mace (60%)
					break;
				case RECIPE_DYNASTY_BAGHNAKH_60:
					htmltext = onExchangeRequest(st, val, 1, 10); // Recipe Dynasty Bagh-Nakh (60%)
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
		final QuestState st = player.getQuestState(getName());
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
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (st.isCond(1) && (getRandom(100) < DROP_CHANCE))
		{
			st.giveItems(ANCIENT_PARCHMENT, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
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
			st.playSound(QuestSound.ITEMSOUND_QUEST_FINISH);
			return "31147-ok.html";
		}
		return "31147-no.html";
	}
	
	public Q00376_ExplorationOfTheGiantsCavePart1(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(SOBLING);
		addTalkId(SOBLING);
		addKillId(MOBS);
		registerQuestItems(ANCIENT_PARCHMENT);
	}
	
	public static void main(String[] args)
	{
		new Q00376_ExplorationOfTheGiantsCavePart1(376, Q00376_ExplorationOfTheGiantsCavePart1.class.getSimpleName(), "Exploration of the Giants' Cave - Part 1");
	}
}
