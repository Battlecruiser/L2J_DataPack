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
package quests.Q00028_ChestCaughtWithABaitOfIcyAir;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Chest Caught With A Bait Of Icy Air (28)<br>
 * Original Jython script by Skeleton
 * @author nonom
 */
public class Q00028_ChestCaughtWithABaitOfIcyAir extends Quest
{
	private static final String qn = "28_ChestCaughtWithABaitOfIcyAir";
	
	// NPCs
	private static final int OFULLE = 31572;
	private static final int KIKI = 31442;
	
	// Items
	private static final int YELLOW_TREASURE_BOX = 6503;
	private static final int KIKIS_LETTER = 7626;
	private static final int ELVEN_RING = 881;
	
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
			case "31572-04.htm":
				st.set("cond", "1");
				st.setState(State.STARTED);
				st.playSound("ItemSound.quest_accept");
				break;
			case "31572-08.htm":
				if ((st.getInt("cond") == 1) && (st.hasQuestItems(YELLOW_TREASURE_BOX)))
				{
					htmltext = "31572-07.htm";
					st.set("cond", "2");
					st.giveItems(KIKIS_LETTER, 1);
					st.takeItems(YELLOW_TREASURE_BOX, -1);
					st.playSound("ItemSound.quest_middle");
				}
				break;
			case "31442-03.htm":
				if ((st.getInt("cond") == 2) && (st.hasQuestItems(KIKIS_LETTER)))
				{
					htmltext = "31442-02.htm";
					st.giveItems(ELVEN_RING, 1);
					st.takeItems(KIKIS_LETTER, -1);
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
				final QuestState qs = player.getQuestState("51_OFullesSpecialBait");
				if (npcId == OFULLE)
				{
					htmltext = "31572-02.htm";
					if (qs != null)
					{
						htmltext = ((player.getLevel() >= 36) && qs.isCompleted()) ? "31572-01.htm" : htmltext;
					}
				}
				break;
			case State.STARTED:
				final int cond = st.getInt("cond");
				switch (npcId)
				{
					case OFULLE:
						switch (cond)
						{
							case 1:
								htmltext = "31572-06.htm";
								if (st.hasQuestItems(YELLOW_TREASURE_BOX))
								{
									htmltext = "31572-05.htm";
								}
								break;
							case 2:
								htmltext = "31572-09.htm";
								break;
						}
						break;
					case KIKI:
						if (cond == 2)
						{
							htmltext = "31442-01.htm";
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	public Q00028_ChestCaughtWithABaitOfIcyAir(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(OFULLE);
		addTalkId(OFULLE, KIKI);
	}
	
	public static void main(String[] args)
	{
		new Q00028_ChestCaughtWithABaitOfIcyAir(28, qn, "Chest Caught With A Bait Of Icy Air");
	}
}
