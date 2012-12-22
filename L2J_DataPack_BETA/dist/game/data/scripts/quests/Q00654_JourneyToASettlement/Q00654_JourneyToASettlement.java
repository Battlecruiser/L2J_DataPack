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
package quests.Q00654_JourneyToASettlement;

import java.util.HashMap;
import java.util.Map;

import quests.Q00119_LastImperialPrince.Q00119_LastImperialPrince;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Journey to a Settlement (654)
 * @author Adry_85
 */
public class Q00654_JourneyToASettlement extends Quest
{
	// NPC
	private static final int NAMELESS_SPIRIT = 31453;
	// Items
	private static final int ANTELOPE_SKIN = 8072;
	private static final int FRINTEZZAS_SCROLL = 8073;
	// Misc
	private static final int MIN_LEVEL = 74;
	
	private static final Map<Integer, Integer> MOBS_SKIN = new HashMap<>();
	
	static
	{
		MOBS_SKIN.put(21294, 840); // Canyon Antelope
		MOBS_SKIN.put(21295, 893); // Canyon Antelope Slave
	}
	
	public Q00654_JourneyToASettlement(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(NAMELESS_SPIRIT);
		addTalkId(NAMELESS_SPIRIT);
		addKillId(MOBS_SKIN.keySet());
		registerQuestItems(ANTELOPE_SKIN);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "31453-02.html":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "31453-03.html":
			{
				if (st.isCond(1))
				{
					st.setCond(2, true);
					htmltext = event;
				}
			}
			case "31453-07.html":
			{
				if (st.isCond(3) && st.hasQuestItems(ANTELOPE_SKIN))
				{
					st.giveItems(FRINTEZZAS_SCROLL, 1);
					st.exitQuest(true, true);
					htmltext = event;
				}
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
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				st = player.getQuestState(Q00119_LastImperialPrince.class.getSimpleName());
				htmltext = ((player.getLevel() >= MIN_LEVEL) && (st != null) && (st.isCompleted())) ? "31453-01.htm" : "31453-04.html";
				break;
			}
			case State.STARTED:
			{
				if (st.isCond(2))
				{
					htmltext = "31453-05.html";
				}
				else if (st.isCond(3))
				{
					htmltext = "31453-06.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, "2");
		if (partyMember == null)
		{
			return super.onKill(npc, player, isPet);
		}
		
		final QuestState st = partyMember.getQuestState(getName());
		int npcId = npc.getNpcId();
		float chance = (MOBS_SKIN.get(npcId) * Config.RATE_QUEST_DROP);
		if (getRandom(1000) < chance)
		{
			st.rewardItems(ANTELOPE_SKIN, 1);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			st.setCond(3, true);
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q00654_JourneyToASettlement(654, Q00654_JourneyToASettlement.class.getSimpleName(), "Journey to a Settlement");
	}
}
