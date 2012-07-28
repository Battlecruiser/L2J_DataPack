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
package quests.Q00052_WilliesSpecialBait;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Willie's Special Bait (52)<br>
 * Original Jython script by Kilkenny
 * @author nonom
 */
public class Q00052_WilliesSpecialBait extends Quest
{
	private static final String qn = "52_WilliesSpecialBait";
	
	// NPCs
	private static final int WILLIE = 31574;
	private static final int TARLK_BASILISK = 20573;
	
	// Items
	private static final int TARLK_EYE = 7623;
	private static final int EARTH_FISHING_LURE = 7612;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31574-03.htm":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				break;
			case "31574-07.html":
				if ((st.getInt("cond") == 2) && (st.getQuestItemsCount(TARLK_EYE) >= 100))
				{
					htmltext = "31574-06.htm";
					st.giveItems(EARTH_FISHING_LURE, 4);
					st.takeItems(TARLK_EYE, -1);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(false);
				}
				break;
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
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				htmltext = (player.getLevel() >= 48) ? "31574-01.htm" : "31574-02.html";
				break;
			case State.STARTED:
				htmltext = (st.getInt("cond") == 1) ? "31574-04.html" : "31574-05.html";
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, "1");
		if (partyMember == null)
		{
			return null;
		}
		
		final QuestState st = partyMember.getQuestState(qn);
		if (st == null)
		{
			return null;
		}
		
		final long count = st.getQuestItemsCount(TARLK_EYE);
		if ((st.getInt("cond") == 1) && (count < 100))
		{
			float chance = 33 * Config.RATE_QUEST_DROP;
			float numItems = chance / 100;
			chance = chance % 100;
			
			if (getRandom(100) < chance)
			{
				numItems += 1;
			}
			if (numItems > 0)
			{
				if ((count + numItems) >= 100)
				{
					numItems = 100 - count;
				}
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
			else
			{
				st.playSound("ItemSound.quest_itemget");
			}
			st.giveItems(TARLK_EYE, (int) numItems);
		}
		
		return super.onKill(npc, player, isPet);
	}
	
	public Q00052_WilliesSpecialBait(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(WILLIE);
		addTalkId(WILLIE);
		addKillId(TARLK_BASILISK);
	}
	
	public static void main(String[] args)
	{
		new Q00052_WilliesSpecialBait(52, qn, "Willie's Special Bait");
	}
}
