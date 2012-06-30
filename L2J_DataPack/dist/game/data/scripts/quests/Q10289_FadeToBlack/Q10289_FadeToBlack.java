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
package quests.Q10289_FadeToBlack;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * @author Plim
 */
public class Q10289_FadeToBlack extends Quest
{
	private static final String qn = "10289_FadeToBlack";
	
	// NPCs
	private static final int GREYMORE = 32757;
	
	// Items
	private static final int MARK_OF_DARKNESS = 15528;
	private static final int MARK_OF_SPLENDOR = 15527;
	
	// MOBs
	private static final int ANAYS = 25701;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == GREYMORE)
		{
			if (event.equalsIgnoreCase("32757-04.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (Util.isDigit(event) && st.hasQuestItems(MARK_OF_SPLENDOR))
			{
				int itemId = Integer.parseInt(event);
				st.takeItems(MARK_OF_SPLENDOR, 1);
				st.giveItems(itemId, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
				htmltext = "32757-08.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		QuestState secretMission = player.getQuestState("10288_SecretMission");
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == GREYMORE)
		{
			final int cond = st.getInt("cond");
			switch (st.getState())
			{
				case State.CREATED:
					if ((player.getLevel() >= 82) && (secretMission != null) && secretMission.isCompleted())
					{
						htmltext = "32757-02.htm";
					}
					else if (player.getLevel() < 82)
					{
						htmltext = "32757-00.htm";
					}
					else
					{
						htmltext = "32757-01.htm";
					}
					break;
				case State.STARTED:
					if (cond == 1)
					{
						htmltext = "32757-04b.htm";
					}
					if ((cond == 2) && st.hasQuestItems(MARK_OF_DARKNESS))
					{
						htmltext = "32757-05.htm";
						st.takeItems(MARK_OF_DARKNESS, 1);
						player.addExpAndSp(55983, 136500);
						st.set("cond", "1");
						st.playSound("ItemSound.quest_middle");
					}
					else if (cond == 3)
					{
						htmltext = "32757-06.htm";
					}
					break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final L2PcInstance randomPartyMember = getRandomPartyMember(player, "1");
		if (randomPartyMember == null)
		{
			return super.onKill(npc, player, isPet);
		}
		
		final QuestState st = randomPartyMember.getQuestState(qn);
		if (st != null)
		{
			st.giveItems(MARK_OF_SPLENDOR, 1);
			st.playSound("ItemSound.quest_itemget");
			st.set("cond", "3");
		}
		
		if (player.getParty() != null)
		{
			QuestState st2;
			for (L2PcInstance partyMember : player.getParty().getMembers())
			{
				st2 = partyMember.getQuestState(qn);
				if ((st2 != null) && (st2.getInt("cond") == 1) && (partyMember.getObjectId() != randomPartyMember.getObjectId()))
				{
					st2.giveItems(MARK_OF_DARKNESS, 1);
					st2.playSound("ItemSound.quest_itemget");
					st2.set("cond", "2");
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public Q10289_FadeToBlack(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(GREYMORE);
		addTalkId(GREYMORE);
		addKillId(ANAYS);
	}
	
	public static void main(String[] args)
	{
		new Q10289_FadeToBlack(10289, qn, "Fade to Black");
	}
}
