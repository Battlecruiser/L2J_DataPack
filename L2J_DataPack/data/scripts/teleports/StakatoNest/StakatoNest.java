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

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

public class StakatoNest extends Quest
{
	private final static int[][] data =
	{
		{80456, -52322, -5640},
		{88718, -46214, -4640},
		{87464, -54221, -5120},
		{80848, -49426, -5128},
		{87682, -43291, -4128}
	};

	private final static int npcId = 32640;

	public StakatoNest(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(npcId);
		addTalkId(npcId);
	}

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		if (st == null)
			st = newQuestState(player);

		int loc = Integer.parseInt(event) - 1;
		
		if (data.length > loc)
		{
			int x = data[loc][0];
			int y = data[loc][1];
			int z = data[loc][2];

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

	@Override
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