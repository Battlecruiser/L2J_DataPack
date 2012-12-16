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
package quests.Q00432_BirthdayPartySong;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Birthday Party Song (432)<br>
 * Original Jython script by CubicVirtuoso.
 * @author malyelfik
 */
public class Q00432_BirthdayPartySong extends Quest
{
	// NPC
	private static final int OCTAVIA = 31043;
	
	// Monster
	private static final int GOLEM = 21103;
	
	// Item
	private static final int RED_CRYSTAL = 7541;
	
	// Reward
	private static final int ECHO_CRYSTAL = 7061;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "31043-02.htm":
				st.startQuest();
				break;
			case "31043-05.html":
				if (st.getQuestItemsCount(RED_CRYSTAL) < 50)
				{
					return "31043-06.html";
				}
				
				st.giveItems(ECHO_CRYSTAL, 25);
				st.exitQuest(true, true);
				break;
			default:
				htmltext = null;
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
			case State.CREATED:
				htmltext = (player.getLevel() >= 31) ? "31043-01.htm" : "31043-00.htm";
				break;
			case State.STARTED:
				htmltext = (st.isCond(1)) ? "31043-03.html" : "31043-04.html";
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(getName());
		
		if ((st != null) && st.isCond(1) && (Rnd.nextBoolean()))
		{
			st.giveItems(RED_CRYSTAL, 1);
			if (st.getQuestItemsCount(RED_CRYSTAL) == 50)
			{
				st.setCond(2, true);
			}
			else
			{
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public Q00432_BirthdayPartySong(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(OCTAVIA);
		addTalkId(OCTAVIA);
		addKillId(GOLEM);
		
		registerQuestItems(RED_CRYSTAL);
	}
	
	public static void main(String[] args)
	{
		new Q00432_BirthdayPartySong(432, Q00432_BirthdayPartySong.class.getSimpleName(), "Birthday Party Song");
	}
}