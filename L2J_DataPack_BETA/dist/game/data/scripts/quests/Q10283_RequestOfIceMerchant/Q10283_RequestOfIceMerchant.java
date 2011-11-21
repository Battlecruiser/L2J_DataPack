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
package quests.Q10283_RequestOfIceMerchant;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 ** @author Gnacik
 **
 ** 2010-08-07 Based on Freya PTS
 */
public class Q10283_RequestOfIceMerchant extends Quest
{
	private static final String qn = "10283_RequestOfIceMerchant";
	// NPC's
	private static final int _rafforty = 32020;
	private static final int _kier = 32022;
	private static final int _jinia = 32760;
	
	public Q10283_RequestOfIceMerchant(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_rafforty);
		addTalkId(_rafforty);
		addTalkId(_kier);
		addFirstTalkId(_jinia);
		addTalkId(_jinia);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == _rafforty)
		{
			if (event.equalsIgnoreCase("32020-03.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("32020-07.htm"))
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
		}
		else if (npc.getNpcId() == _kier && event.equalsIgnoreCase("spawn"))
		{
			addSpawn(_jinia, 104322, -107669, -3680, 44954, false, 60000);
			return null;
		}
		else if (npc.getNpcId() == _jinia && event.equalsIgnoreCase("32760-04.html"))
		{
			st.giveItems(57, 190000);
			st.addExpAndSp(627000, 50300);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
			npc.deleteMe();
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == _rafforty)
		{
			switch (st.getState())
			{
				case State.CREATED:
					QuestState _prev = player.getQuestState("115_TheOtherSideOfTruth");
					if ((_prev != null) && _prev.isCompleted() && (player.getLevel() >= 82))
						htmltext = "32020-01.htm";
					else
						htmltext = "32020-00.htm";
					break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
						htmltext = "32020-04.htm";
					else if (st.getInt("cond") == 2)
						htmltext = "32020-08.htm";
					break;
				case State.COMPLETED:
					htmltext = "32020-09.htm";
					break;
			}
		}
		else if (npc.getNpcId() == _kier && st.getInt("cond") == 2)
		{
			htmltext = "32022-01.html";
		}
		else if (npc.getNpcId() == _jinia && st.getInt("cond") == 2)
		{
			htmltext = "32760-02.html";
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);

		if (npc.getInstanceId() > 0)
			return "32760-10.html";
		if (npc.getNpcId() == _jinia && st != null && st.getInt("cond") == 2)
			return "32760-01.html";
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q10283_RequestOfIceMerchant(10283, qn, "Request of Ice Merchant");
	}
}
