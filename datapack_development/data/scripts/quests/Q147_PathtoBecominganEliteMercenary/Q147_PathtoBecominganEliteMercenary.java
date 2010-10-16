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
package quests.Q147_PathtoBecominganEliteMercenary;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 ** @author Gnacik
 **
 ** 2010-09-30 Based on official server Franz
 */
public class Q147_PathtoBecominganEliteMercenary extends Quest
{
	private static final String qn = "147_PathtoBecominganEliteMercenary";
	// NPCs
	private static final int[] _merc = { 36481, 36482, 36483, 36484, 36485, 36486, 36487, 36488, 36489 };
	// Items
	private static final int _cert_ordinary = 13766;
	private static final int _cert_elite = 13767;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return htmltext;
		
		if (Util.contains(_merc, npc.getNpcId()))
		{
			if (event.equalsIgnoreCase("elite-02.htm"))
			{
				if (st.hasQuestItems(_cert_ordinary))
					return "elite-02a.htm";
				st.giveItems(_cert_ordinary, 1);
			}
			else if (event.equalsIgnoreCase("elite-04.htm"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
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
			return htmltext;
		
		if (Util.contains(_merc, npc.getNpcId()))
		{
			switch(st.getState())
			{
				case State.CREATED :
					if (player.getClan() != null && player.getClan().getHasCastle() > 0)
						htmltext = "castle.htm";
					else
						htmltext = "elite-01.htm";
					break;
				case State.STARTED :
					if (st.getInt("cond") < 4)
					{
						htmltext = "elite-05.htm";
					}
					else if (st.getInt("cond") == 4)
					{
						st.unset("cond");
						st.unset("kills");
						st.takeItems(_cert_ordinary, -1);
						st.giveItems(_cert_elite, 1);
						st.exitQuest(false);
						htmltext = "elite-06.htm";
					}
					break;
				case State.COMPLETED :
					htmltext = getAlreadyCompletedMsg(player);
					break;
			}
		}
		return htmltext;
	}
	
	public Q147_PathtoBecominganEliteMercenary(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		for(int _npc : _merc)
		{
			addStartNpc(_npc);
			addTalkId(_npc);
		}
	}
	
	public static void main(String[] args)
	{
		new Q147_PathtoBecominganEliteMercenary(147, qn, "Path to Becoming an Elite Mercenary");
	}
}