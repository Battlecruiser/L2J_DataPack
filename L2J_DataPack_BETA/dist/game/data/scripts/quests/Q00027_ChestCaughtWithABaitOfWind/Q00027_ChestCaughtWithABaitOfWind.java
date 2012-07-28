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
package quests.Q00027_ChestCaughtWithABaitOfWind;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Chest Caught With A Bait Of Wind (27)<br>
 * Original Jython script by DooMIta
 * @author nonom
 */
public class Q00027_ChestCaughtWithABaitOfWind extends Quest
{
	private static final String qn = "27_ChestCaughtWithABaitOfWind";
	
	// NPCs
	private static final int LANOSCO = 31570;
	private static final int SHALING = 31434;
	
	// Items
	private static final int BLUE_TREASURE_BOX = 6500;
	private static final int STRANGE_BLUESPRINT = 7625;
	private static final int BLACK_PEARL_RING = 880;
	
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
			case "31570-03.htm":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				break;
			case "31570-05.htm":
				if ((st.getInt("cond") == 1) && (st.hasQuestItems(BLUE_TREASURE_BOX)))
				{
					htmltext = "31570-06.htm";
					st.set("cond", "2");
					st.giveItems(STRANGE_BLUESPRINT, 1);
					st.takeItems(BLUE_TREASURE_BOX, -1);
					st.playSound("ItemSound.quest_middle");
				}
				break;
			case "31434-02.htm":
				if ((st.getInt("cond") == 2) && (st.hasQuestItems(STRANGE_BLUESPRINT)))
				{
					htmltext = "31434-01.htm";
					st.giveItems(BLACK_PEARL_RING, 1);
					st.takeItems(STRANGE_BLUESPRINT, -1);
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
		
		final int npcId = npc.getNpcId();
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				final QuestState qs = player.getQuestState("50_LanoscosSpecialBait");
				if (npcId == LANOSCO)
				{
					htmltext = "31570-02.htm";
					if (qs != null)
					{
						htmltext = ((player.getLevel() >= 27) && qs.isCompleted()) ? "31570-01.htm" : htmltext;
					}
				}
				break;
			case State.STARTED:
				final int cond = st.getInt("cond");
				switch (npcId)
				{
					case LANOSCO:
						if (cond == 1)
						{
							if (st.hasQuestItems(BLUE_TREASURE_BOX))
							{
								htmltext = "31570-04.htm";
							}
							else
							{
								htmltext = "31570-05.htm";
							}
						}
						else
						{
							htmltext = "31570-07.htm";
						}
						break;
					case SHALING:
						if (cond == 2)
						{
							htmltext = "31434-00.htm";
						}
						break;
				}
		}
		return htmltext;
	}
	
	public Q00027_ChestCaughtWithABaitOfWind(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(LANOSCO);
		addTalkId(LANOSCO, SHALING);
	}
	
	public static void main(String[] args)
	{
		new Q00027_ChestCaughtWithABaitOfWind(27, qn, "Chest Caught With A Bait Of Wind");
	}
}
