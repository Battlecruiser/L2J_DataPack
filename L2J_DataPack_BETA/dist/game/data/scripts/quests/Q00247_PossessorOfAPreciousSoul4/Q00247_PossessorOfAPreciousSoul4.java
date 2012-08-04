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
package quests.Q00247_PossessorOfAPreciousSoul4;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Possessor Of A PreciousSoul part 4 (247)<br>
 * Original Jython script by disKret
 * @author nonom
 */
public class Q00247_PossessorOfAPreciousSoul4 extends Quest
{
	
	// NPCs
	private static final int CARADINE = 31740;
	private static final int LADY_OF_LAKE = 31745;
	
	// Items
	private static final int CARADINE_LETTER_LAST = 7679;
	private static final int NOBLESS_TIARA = 7694;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		if (!player.isSubClassActive())
		{
			return "no_sub.html";
		}
		
		switch (event)
		{
			case "31740-3.html":
				st.startQuest();
				break;
			case "31740-5.html":
				if (st.hasQuestItems(CARADINE_LETTER_LAST))
				{
					st.setCond(2, true);
					st.takeItems(CARADINE_LETTER_LAST, -1);
					player.teleToLocation(new Location(143209, 43968, -3038), 0);
				}
				break;
			case "31745-5.html":
				if (st.isCond(2))
				{
					player.setNoble(true);
					st.addExpAndSp(93836, 0);
					st.giveItems(NOBLESS_TIARA, 1);
					st.exitQuest(false, true);
				}
				break;
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
		if (st.isStarted() && !player.isSubClassActive())
		{
			return "no_sub.html";
		}
		
		switch (npc.getNpcId())
		{
			case CARADINE:
			{
				htmltext = ((player.getLevel() >= 75) ? "31740-1.htm" : "31740-2.html");
				if (st.getState() == State.COMPLETED)
				{
					htmltext = getAlreadyCompletedMsg(player);
				}
				break;
			}
			case LADY_OF_LAKE:
			{
				if (st.isCond(2))
				{
					htmltext = "31745-1.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public Q00247_PossessorOfAPreciousSoul4(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(CARADINE);
		addTalkId(CARADINE, LADY_OF_LAKE);
	}
	
	public static void main(String[] args)
	{
		new Q00247_PossessorOfAPreciousSoul4(247, Q00247_PossessorOfAPreciousSoul4.class.getSimpleName(), "Possessor Of A Precious Soul 4");
	}
}
