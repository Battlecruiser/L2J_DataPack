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
package teleports.PaganTeleporters;

import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Plim
 * Original python script by BiTi
 */
public class PaganTeleporters extends Quest
{
	private static final int[] NPCS = { 32034, 32035, 32036, 32037, 32039, 32040 };
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("Close_Door1"))
			DoorTable.getInstance().getDoor(19160001).closeMe();
		
		else if (event.equalsIgnoreCase("Close_Door2"))
		{
			DoorTable.getInstance().getDoor(19160010).closeMe();
			DoorTable.getInstance().getDoor(19160011).closeMe();
		}
		
		return "";
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (npc.getNpcId() == 32039)
			player.teleToLocation(-12766, -35840, -10856);
		
		else if (npc.getNpcId() == 32040)
			player.teleToLocation(36640, -51218, 718);
		
		return "";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return null;
		
		switch (npc.getNpcId())
		{
			case 32034:
				if (st.getQuestItemsCount(8064) == 0 && st.getQuestItemsCount(8065) == 0 && st.getQuestItemsCount(8067) == 0)
					htmltext = "noItem.htm";
				
				else
				{
					htmltext = "FadedMark.htm";
					DoorTable.getInstance().getDoor(19160001).openMe();
					startQuestTimer("Close_Door1", 10000, null, null);
				}
				break;
			
			case 32035:
				DoorTable.getInstance().getDoor(19160001).openMe();
				startQuestTimer("Close_Door1", 10000, null, null);
				htmltext = "FadedMark.htm";
				break;
			
			case 32036:
				if (st.getQuestItemsCount(8067) == 0)
					htmltext = "noMark.htm";
				
				else
				{
					htmltext = "openDoor.htm";
					startQuestTimer("Close_Door2", 10000, null, null);
					DoorTable.getInstance().getDoor(19160010).openMe();
					DoorTable.getInstance().getDoor(19160011).openMe();
				}
				break;
			
			case 32037:
				DoorTable.getInstance().getDoor(19160010).openMe();
				DoorTable.getInstance().getDoor(19160011).openMe();
				startQuestTimer("Close_Door2", 10000, null, null);
				htmltext = "FadedMark.htm";
				break;
		}
		
		st.exitQuest(true);
		
		return htmltext;
	}
	
	public PaganTeleporters(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		for (int npcId : NPCS)
		{
			addStartNpc(npcId);
			addTalkId(npcId);
		}
		
		addFirstTalkId(32039);
		addFirstTalkId(32040);
	}
	
	public static void main(String[] args)
	{
		new PaganTeleporters(-1, PaganTeleporters.class.getSimpleName(), "teleports");
	}
}
