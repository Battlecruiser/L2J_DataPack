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
package quests.Q179_IntoTheLargeCavern;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * 2010-10-15 Based on official server Naia
 * @author Gnacik
 */
public class Q179_IntoTheLargeCavern extends Quest
{
	private static final String qn = "179_IntoTheLargeCavern";
	
	// NPC's
	private static final int _kekropus = 32138;
	private static final int _nornil = 32258;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == _kekropus)
		{
			if (event.equalsIgnoreCase("32138-03.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
		}
		else if (npc.getNpcId() == _nornil)
		{
			if (event.equalsIgnoreCase("32258-08.htm"))
			{
				st.giveItems(391, 1);
				st.giveItems(413, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
			}
			else if (event.equalsIgnoreCase("32258-09.htm"))
			{
				st.giveItems(847, 2);
				st.giveItems(890, 2);
				st.giveItems(910, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(false);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		QuestState _prev = player.getQuestState("178_IconicTrinity");
		if ((_prev != null) && _prev.isCompleted() && (player.getLevel() >= 17) && (player.getRace() == Race.Kamael) && (player.getClassId().level() == 0))
		{
			if (npc.getNpcId() == _kekropus)
			{
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = "32138-01.htm";
						break;
					case State.STARTED:
						if (st.getInt("cond") == 1)
						{
							htmltext = "32138-03.htm";
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
			}
			else if ((npc.getNpcId() == _nornil) && (st.getState() == State.STARTED))
			{
				htmltext = "32258-01.htm";
			}
		}
		else
		{
			htmltext = "32138-00.htm";
		}
		
		return htmltext;
	}
	
	public Q179_IntoTheLargeCavern(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_kekropus);
		addTalkId(_kekropus, _nornil);
	}
	
	public static void main(String[] args)
	{
		new Q179_IntoTheLargeCavern(179, qn, "Into The Large Cavern");
	}
}
