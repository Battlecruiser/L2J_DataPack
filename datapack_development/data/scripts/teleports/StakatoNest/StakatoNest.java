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
package teleports.StakatoNest;

import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

public class StakatoNest extends Quest
{
	private static Map<String, Object[]> data = new FastMap<String, Object[]>();

	private final static int npcId = 32640;

	public StakatoNest(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(npcId);
		addTalkId(npcId);

		loadData();
	}

	private void loadData()
	{
		data.put("1", new Object[]{80456, -52322, -5640});
		data.put("2", new Object[]{88718, -46214, -4640});
		data.put("3", new Object[]{87464, -54221, -5120});
		data.put("4", new Object[]{80848, -49426, -5128});
		data.put("5", new Object[]{87682, -43291, -4128});
	}

	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		if (st == null)
			st = newQuestState(player);

		if (data.containsKey(event))
		{
			int x = (Integer) data.get(event)[0];
			int y = (Integer) data.get(event)[1];
			int z = (Integer) data.get(event)[2];

			if (player.getParty() != null)
			{
				for (L2PcInstance partyMember : player.getParty().getPartyMembers())
				{
					if (partyMember.isInsideRadius(player, 1000, true, true))
						partyMember.teleToLocation(x, y, z);
				}
			}
			player.teleToLocation(x, y, z);
			st.exitQuest(true);
		}

		return htmltext;
	}

	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState accessQuest = player.getQuestState("240_ImTheOnlyOneYouCanTrust");
		if (accessQuest != null && accessQuest.getState() == State.COMPLETED)
			htmltext = "32640.htm";
		else
			htmltext = "32640-no.htm";

		return htmltext;
	}

	public static void main(String[] args)
	{
		new StakatoNest(-1, "StakatoNest", "teleports");
	}
}