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
package quests.Q00053_LinnaeusSpecialBait;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Linnaeus Special Bait (53)<br>
 * Original Jython script by Next and DooMita
 * @author nonom
 */
public class Q00053_LinnaeusSpecialBait extends Quest
{
	
	// NPCs
	private static final int LINNAEUS = 31577;
	private static final int CRIMSON_DRAKE = 20670;
	
	// Items
	private static final int CRIMSON_DRAKE_HEART = 7624;
	private static final int FLAMING_FISHING_LURE = 7613;
	
	// Custom setting: wether or not to check for fishing skill level?
	// default False to require fishing skill level, any other value to ignore fishing
	// and evaluate char level only.
	private static final boolean ALT_IGNORE_FISHING = false;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31577-1.htm":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				break;
			case "31577-3.htm":
				if ((st.getInt("cond") == 2) && (st.getQuestItemsCount(CRIMSON_DRAKE_HEART) >= 100))
				{
					st.giveItems(FLAMING_FISHING_LURE, 4);
					st.takeItems(CRIMSON_DRAKE_HEART, -1);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(false);
				}
				else
				{
					htmltext = "31577-5.html";
				}
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
		
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				htmltext = ((player.getLevel() > 59) && (fishingLevel(player) > 19)) ? "31577-0.htm" : "31577-0a.html";
				break;
			case State.STARTED:
				htmltext = (st.getInt("cond") == 1) ? "31577-4.html" : "31577-2.html";
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
		
		final QuestState st = partyMember.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		final long count = st.getQuestItemsCount(CRIMSON_DRAKE_HEART);
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
			st.giveItems(CRIMSON_DRAKE_HEART, (int) numItems);
		}
		
		return super.onKill(npc, player, isPet);
	}
	
	private int fishingLevel(L2PcInstance player)
	{
		int level = 20;
		
		if (!ALT_IGNORE_FISHING)
		{
			level = player.getSkillLevel(1315);
			L2Effect effect = player.getFirstEffect(2274);
			if (effect != null)
			{
				level = (int) effect.getSkill().getPower();
			}
		}
		return level;
	}
	
	public Q00053_LinnaeusSpecialBait(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(LINNAEUS);
		addTalkId(LINNAEUS);
		addKillId(CRIMSON_DRAKE);
	}
	
	public static void main(String[] args)
	{
		new Q00053_LinnaeusSpecialBait(53, Q00053_LinnaeusSpecialBait.class.getSimpleName(), "Linnaeus Special Bait");
	}
}
