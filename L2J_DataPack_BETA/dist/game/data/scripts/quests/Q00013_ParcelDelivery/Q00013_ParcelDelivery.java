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
package quests.Q00013_ParcelDelivery;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Parcel Delivery (13)<br>
 * Original Jython script by Emperorc.
 * @author nonom
 */
public class Q00013_ParcelDelivery extends Quest
{
	// NPCs
	private static final int FUNDIN = 31274;
	private static final int VULCAN = 31539;
	// Item
	private static final int PACKAGE = 7263;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (event)
		{
			case "31274-02.html":
				st.startQuest();
				st.giveItems(PACKAGE, 1);
				break;
			case "31539-01.html":
				if (st.isCond(1) && st.hasQuestItems(PACKAGE))
				{
					st.giveAdena(157834, true);
					st.addExpAndSp(589092, 58794);
					st.exitQuest(false, true);
				}
				else
				{
					htmltext = "31539-02.html";
				}
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
		
		final int npcId = npc.getNpcId();
		switch (st.getState())
		{
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
			case State.CREATED:
				if (npcId == FUNDIN)
				{
					htmltext = (player.getLevel() >= 74) ? "31274-00.htm" : "31274-01.html";
				}
				break;
			case State.STARTED:
				if (st.isCond(1))
				{
					switch (npcId)
					{
						case FUNDIN:
							htmltext = "31274-02.html";
							break;
						case VULCAN:
							htmltext = "31539-00.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
	
	public Q00013_ParcelDelivery(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(FUNDIN);
		addTalkId(FUNDIN, VULCAN);
		registerQuestItems(PACKAGE);
	}
	
	public static void main(String[] args)
	{
		new Q00013_ParcelDelivery(13, Q00013_ParcelDelivery.class.getSimpleName(), "Parcel Delivery");
	}
}
