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
package quests.Q00132_MatrasCuriosity;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Matras' Curiosity (132)
 * @author GKR, Gladicek
 */
public final class Q00132_MatrasCuriosity extends Quest
{
	
	// NPCs
	private static final int MATRAS = 32245;
	private static final int DEMON_PRINCE = 25540;
	private static final int RANKU = 25542;
	
	// Items
	private static final int FIRE = 10521;
	private static final int WATER = 10522;
	private static final int EARTH = 10523;
	private static final int WIND = 10524;
	private static final int DARKNESS = 10525;
	private static final int DIVINITY = 10526;
	private static final int BLUEPRINT_RANKU = 9800;
	private static final int BLUEPRINT_PRINCE = 9801;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = event;
		
		if (event.equalsIgnoreCase("32245-03.htm") && (player.getLevel() >= 76) && !st.isCompleted())
		{
			if (st.isCreated())
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.set("rewarded_prince", "1");
				st.set("rewarded_ranku", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else
			{
				htmltext = "32245-03a.htm";
			}
		}
		else if (event.equalsIgnoreCase("32245-07.htm") && (st.getInt("cond") == 3) && !st.isCompleted())
		{
			st.giveAdena(65884, true);
			st.addExpAndSp(50541, 5094);
			st.giveItems(FIRE, 1);
			st.giveItems(WATER, 1);
			st.giveItems(EARTH, 1);
			st.giveItems(WIND, 1);
			st.giveItems(DARKNESS, 1);
			st.giveItems(DIVINITY, 1);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (st.isCreated())
		{
			htmltext = (player.getLevel() >= 76) ? "32245-01.htm" : "32245-02.htm";
		}
		else if (st.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		else if (st.isStarted())
		{
			switch (st.getInt("cond"))
			{
				case 1:
				case 2:
					if (st.hasQuestItems(BLUEPRINT_RANKU) && st.hasQuestItems(BLUEPRINT_PRINCE))
					{
						st.takeItems(BLUEPRINT_RANKU, -1);
						st.takeItems(BLUEPRINT_PRINCE, -1);
						st.set("cond", "3");
						st.playSound("ItemSound.quest_middle");
						htmltext = "32245-05.htm";
					}
					else
					{
						htmltext = "32245-04.htm";
					}
					
					break;
				case 3:
					htmltext = "32245-06.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance pl = null;
		switch (npc.getNpcId())
		{
			case DEMON_PRINCE:
				pl = getRandomPartyMember(player, "rewarded_prince", "1");
				if (pl != null)
				{
					final QuestState st = pl.getQuestState(getName());
					st.giveItems(BLUEPRINT_PRINCE, 1);
					st.set("rewarded_prince", "2");
					
					if (st.hasQuestItems(BLUEPRINT_RANKU))
					{
						st.set("cond", "2");
						st.playSound("ItemSound.quest_middle");
					}
					else
					{
						st.playSound("ItemSound.quest_itemget");
					}
				}
				break;
			case RANKU:
				pl = getRandomPartyMember(player, "rewarded_ranku", "1");
				if (pl != null)
				{
					final QuestState st = pl.getQuestState(getName());
					st.giveItems(BLUEPRINT_RANKU, 1);
					st.set("rewarded_ranku", "2");
					
					if (st.hasQuestItems(BLUEPRINT_PRINCE))
					{
						st.set("cond", "2");
						st.playSound("ItemSound.quest_middle");
					}
					else
					{
						st.playSound("ItemSound.quest_itemget");
					}
				}
				break;
		}
		return null;
	}
	
	public Q00132_MatrasCuriosity(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MATRAS);
		addTalkId(MATRAS);
		
		addKillId(RANKU, DEMON_PRINCE);
	}
	
	public static void main(String[] args)
	{
		new Q00132_MatrasCuriosity(132, Q00132_MatrasCuriosity.class.getSimpleName(), "Matras' Curiosity");
	}
}
