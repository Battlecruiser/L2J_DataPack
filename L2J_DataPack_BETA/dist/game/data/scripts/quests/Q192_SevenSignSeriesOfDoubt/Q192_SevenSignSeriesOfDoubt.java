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
package quests.Q192_SevenSignSeriesOfDoubt;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Seven Signs, Series of Doubt (192)<br>
 * Original Jython script by Bloodshed.
 * @author Tavo22
 */
public class Q192_SevenSignSeriesOfDoubt extends Quest
{
	private static final String qn = "192_SevenSignSeriesOfDoubt";
	// NPC
	private static final int CROOP = 30676;
	private static final int HECTOR = 30197;
	private static final int STAN = 30200;
	private static final int CORPSE = 32568;
	private static final int HOLLINT = 30191;
	// ITEMS
	private static final int CROOP_INTRO = 13813;
	private static final int JACOB_NECK = 13814;
	private static final int CROOP_LETTER = 13815;
	
	public Q192_SevenSignSeriesOfDoubt(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(CROOP);
		addTalkId(CROOP, HECTOR, STAN, CORPSE, HOLLINT);
		
		questItemIds = new int[]
		{
			CROOP_INTRO,
			JACOB_NECK,
			CROOP_LETTER
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		switch (event)
		{
			case "30676-03.htm":
			{
				st.startQuest();
				break;
			}
			case "showmovie":
			{
				st.setCond(2, true);
				player.showQuestMovie(8);
				startQuestTimer("teleportback", 32000, npc, player);
				return "";
			}
			case "teleportback":
			{
				player.teleToLocation(81654, 54851, -1513);
				return "";
			}
			case "30197-03.html":
			{
				if (st.hasQuestItems(CROOP_INTRO))
				{
					st.setCond(4, true);
					st.takeItems(CROOP_INTRO, -1);
				}
				break;
			}
			case "30200-04.html":
			{
				st.setCond(5, true);
				break;
			}
			case "32568-02.html":
			{
				st.setCond(6, true);
				st.giveItems(JACOB_NECK, 1);
				break;
			}
			case "30676-12.html":
			{
				if (st.hasQuestItems(JACOB_NECK))
				{
					st.setCond(7, true);
					st.takeItems(JACOB_NECK, -1);
					st.giveItems(CROOP_LETTER, 1);
				}
				break;
			}
			case "30191-03.html":
			{
				if (player.getLevel() < 79)
				{
					return "30676-00.htm";
				}
				
				if (st.hasQuestItems(CROOP_LETTER))
				{
					st.takeItems(CROOP_LETTER, -1);
					st.addExpAndSp(25000000, 2500000);
					st.exitQuest(false, true);
				}
				break;
			}
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case CROOP:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = player.getLevel() < 79 ? "30676-00.htm" : "30676-01.htm";
						break;
					case State.STARTED:
						switch (st.getInt("cond"))
						{
							case 1:
								htmltext = "30676-04.html";
								break;
							case 2:
								st.setCond(3, true);
								htmltext = "30676-05.html";
								st.giveItems(CROOP_INTRO, 1);
								break;
							case 3:
							case 4:
							case 5:
								htmltext = "30676-06.html";
								break;
							case 6:
								htmltext = "30676-07.html";
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = "30676-13.html";
						break;
				}
				break;
			case HECTOR:
			{
				switch (st.getInt("cond"))
				{
					case 3:
						htmltext = "30197-01.html";
						break;
					case 4:
					case 5:
					case 6:
					case 7:
						htmltext = "30197-04.html";
						break;
				}
				break;
			}
			case STAN:
			{
				switch (st.getInt("cond"))
				{
					case 4:
						htmltext = "30200-01.html";
						break;
					case 5:
					case 6:
					case 7:
						htmltext = "30200-05.html";
						break;
				}
				break;
			}
			case CORPSE:
			{
				if (st.isCond(5))
				{
					htmltext = "32568-01.html";
				}
				break;
			}
			case HOLLINT:
			{
				if (st.isCond(7))
				{
					htmltext = "30191-01.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String args[])
	{
		new Q192_SevenSignSeriesOfDoubt(192, qn, "Seven Sign Series Of Doubt");
	}
}