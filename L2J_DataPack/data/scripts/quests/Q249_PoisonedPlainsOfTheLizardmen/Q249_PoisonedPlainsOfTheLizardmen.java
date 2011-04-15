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
package quests.Q249_PoisonedPlainsOfTheLizardmen;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 ** @author Gnacik
 **
 ** 2010-08-04 Based on Freya PTS
 */
public class Q249_PoisonedPlainsOfTheLizardmen extends Quest
{
	private static final String qn = "249_PoisonedPlainsOfTheLizardmen";
	private static final int _mouen = 30196;
	private static final int _johnny = 32744;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == _mouen)
		{
			if (event.equalsIgnoreCase("30196-03.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
		}
		else if (npc.getNpcId() == _johnny && event.equalsIgnoreCase("32744-03.htm"))
		{
			st.unset("cond");
			st.giveItems(57, 83056);
			st.addExpAndSp(477496, 58743);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
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
		
		if (npc.getNpcId() == _mouen)
		{
			switch(st.getState())
			{
				case State.CREATED :
					if (player.getLevel() >= 82)
						htmltext = "30196-01.htm";
					else
						htmltext = "30196-00.htm";
					break;
				case State.STARTED :
					if (st.getInt("cond") == 1)
						htmltext = "30196-04.htm";
					break;
				case State.COMPLETED :
					htmltext = "30196-05.htm";
					break;
			}
		}
		else if (npc.getNpcId() == _johnny)
		{
			if (st.getInt("cond") == 1)
				htmltext = "32744-01.htm";
			else if (st.getState() == State.COMPLETED)
				htmltext = "32744-04.htm";
		}
		return htmltext;
	}
	
	public Q249_PoisonedPlainsOfTheLizardmen(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_mouen);
		addTalkId(_mouen);
		addTalkId(_johnny);
	}
	
	public static void main(String[] args)
	{
		new Q249_PoisonedPlainsOfTheLizardmen(249, qn, "Poisoned Plains of the Lizardmen");
	}
}