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
package quests.Q551_OlympiadStarter;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.olympiad.CompetitionType;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 ** @author Gnacik 2011-02-04 Based on official H5 PTS server
 */
public class Q551_OlympiadStarter extends Quest
{
	// Name
	private static final String QUEST_NAME = "551_OlympiadStarter";
	// NPC
	private static final int MANAGER = 31688;
	// Items
	private static final int CERT_3 = 17238;
	private static final int CERT_5 = 17239;
	private static final int CERT_10 = 17240;
	
	private static final int OLY_CHEST = 17169;
	private static final int MEDAL_OF_GLORY = 21874;
	
	public Q551_OlympiadStarter(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MANAGER);
		addTalkId(MANAGER);
		setOlympiadUse(true);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == MANAGER)
		{
			if (event.equalsIgnoreCase("31688-ok.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("exchange_3"))
			{
				st.takeItems(CERT_3, 1);
				st.giveItems(OLY_CHEST, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				
				htmltext = "31688-exchange.htm";
			}
			else if (event.equalsIgnoreCase("exchange_5"))
			{
				st.takeItems(CERT_3, 1);
				st.giveItems(OLY_CHEST, 2);
				
				st.takeItems(CERT_5, 1);
				st.giveItems(MEDAL_OF_GLORY, 3);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				
				htmltext = "31688-exchange.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == MANAGER)
		{
			if (player.getLevel() < 75 || !player.isNoble())
				htmltext = "31688-00.htm";
			else if (st.getState() == State.CREATED)
				htmltext = "31688-01.htm";
			else if (st.getState() == State.COMPLETED)
				htmltext = "31688-done.htm";
			else if (st.getState() == State.STARTED && st.hasQuestItems(CERT_10))
			{
				st.takeItems(CERT_3, 1);
				st.takeItems(CERT_5, 1);
				st.takeItems(CERT_10, 1);
				st.giveItems(OLY_CHEST, 4);
				st.giveItems(MEDAL_OF_GLORY, 5);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				
				htmltext = "31688-03.htm";
			}
			else if (st.getState() == State.STARTED && st.hasQuestItems(CERT_5))
				htmltext = "31688-f3.htm";
			else if (st.getState() == State.STARTED && st.hasQuestItems(CERT_3))
				htmltext = "31688-f5.htm";
			else
				htmltext = "31688-no.htm";
		}
		return htmltext;
	}
	
	@Override
	public void onOlympiadWin(L2PcInstance winner, CompetitionType type)
	{
		QuestState st = null;
		if (winner != null)
		{
			st = winner.getQuestState(getName());
			if (st != null && st.getState() == State.STARTED)
			{
				int matches = st.getInt("matches") + 1;
				switch (matches)
				{
					case 3:
						st.giveItems(CERT_3, 1);
						break;
					case 5:
						st.giveItems(CERT_5, 1);
						break;
					case 10:
						st.giveItems(CERT_10, 1);
						break;
				}
				
				st.set("matches", String.valueOf(matches));
			}
		}
	}
	
	@Override
	public void onOlympiadLoose(L2PcInstance looser, CompetitionType type)
	{
		QuestState st = null;
		if (looser != null)
		{
			st = looser.getQuestState(getName());
			if (st != null && st.getState() == State.STARTED)
			{
				int matches = st.getInt("matches") + 1;
				switch (matches)
				{
					case 3:
						st.giveItems(CERT_3, 1);
						break;
					case 5:
						st.giveItems(CERT_5, 1);
						break;
					case 10:
						st.giveItems(CERT_10, 1);
						break;
				}
				
				st.set("matches", String.valueOf(matches));
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Q551_OlympiadStarter(551, QUEST_NAME, "Olympiad Starter");
	}
}