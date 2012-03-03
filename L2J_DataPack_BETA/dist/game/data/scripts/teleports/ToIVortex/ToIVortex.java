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
package teleports.ToIVortex;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Plim update to H5 by pmq
 */
public class ToIVortex extends Quest
{
	// NPCs
	private static final int KEPLON = 30949;
	private static final int EUCLIE = 30950;
	private static final int PITHGON = 30951;
	private static final int DIMENSION_VORTEX_1 = 30952;
	private static final int DIMENSION_VORTEX_2 = 30953;
	private static final int DIMENSION_VORTEX_3 = 30954;
	private static final int TELEPORT_CUBIC = 29055;
	
	// ITEMS
	private static final int ADENA = 57;
	private static final int BLUE_DIMENSION_STONE = 4402;
	private static final int GREEN_DIMENSION_STONE = 4401;
	private static final int RED_DIMENSION_STONE = 4403;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		int npcId = npc.getNpcId();
		// 1st Floor
		if (event.equalsIgnoreCase("1"))
		{
			if (st.hasQuestItems(GREEN_DIMENSION_STONE))
			{
				st.takeItems(GREEN_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(114356, 13423, -5096);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 2nd Floor
		else if (event.equalsIgnoreCase("2"))
		{
			if (st.hasQuestItems(GREEN_DIMENSION_STONE))
			{
				st.takeItems(GREEN_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(114666, 13380, -3608);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 3rd Floor
		else if (event.equalsIgnoreCase("3"))
		{
			if (st.hasQuestItems(GREEN_DIMENSION_STONE))
			{
				st.takeItems(GREEN_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(111982, 16028, -2120);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 4th Floor
		else if (event.equalsIgnoreCase("4"))
		{
			if (st.hasQuestItems(BLUE_DIMENSION_STONE))
			{
				st.takeItems(BLUE_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(114636, 13413, -640);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 5th Floor
		else if (event.equalsIgnoreCase("5"))
		{
			if (st.hasQuestItems(BLUE_DIMENSION_STONE))
			{
				st.takeItems(BLUE_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(114152, 19902, 928);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 6th Floor
		else if (event.equalsIgnoreCase("6"))
		{
			if (st.hasQuestItems(BLUE_DIMENSION_STONE))
			{
				st.takeItems(BLUE_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(117131, 16044, 1944);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 7th Floor
		else if (event.equalsIgnoreCase("7"))
		{
			if (st.hasQuestItems(RED_DIMENSION_STONE))
			{
				st.takeItems(RED_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(113026, 17687, 2952);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 8th Floor
		else if (event.equalsIgnoreCase("8"))
		{
			if (st.hasQuestItems(RED_DIMENSION_STONE))
			{
				st.takeItems(RED_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(115571, 13723, 3960);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 9th Floor
		else if (event.equalsIgnoreCase("9"))
		{
			if (st.hasQuestItems(RED_DIMENSION_STONE))
			{
				st.takeItems(RED_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(114649, 14144, 4976);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		// 10 Floor
		else if (event.equalsIgnoreCase("10"))
		{
			if (st.hasQuestItems(RED_DIMENSION_STONE))
			{
				st.takeItems(RED_DIMENSION_STONE, 1);
				st.getPlayer().teleToLocation(118507, 16605, 5984);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = "no-stones.htm";
			}
		}
		else if (event.equalsIgnoreCase("GREEN"))
		{
			if (st.getQuestItemsCount(ADENA) >= 10000)
			{
				st.takeItems(ADENA, 10000);
				st.giveItems(GREEN_DIMENSION_STONE, 1);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = npcId + "no-adena.htm";
			}
		}
		else if (event.equalsIgnoreCase("BLUE"))
		{
			if (st.getQuestItemsCount(ADENA) >= 10000)
			{
				st.takeItems(ADENA, 10000);
				st.giveItems(BLUE_DIMENSION_STONE, 1);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = npcId + "no-adena.htm";
			}
		}
		else if (event.equalsIgnoreCase("RED"))
		{
			if (st.getQuestItemsCount(ADENA) >= 10000)
			{
				st.takeItems(ADENA, 10000);
				st.giveItems(RED_DIMENSION_STONE, 1);
				st.exitQuest(true);
			}
			else
			{
				st.exitQuest(true);
				htmltext = npcId + "no-adena.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return null;
		
		// ToI Vortex exit
		if (npc.getNpcId() == TELEPORT_CUBIC)
		{
			int chance = getRandom(3);
			int x, y, z;
			
			switch (chance)
			{
				case 0:
					x = 108784 + getRandom(100);
					y = 16000 + getRandom(100);
					z = -4928;
					break;
				
				case 1:
					x = 113824 + getRandom(100);
					y = 10448 + getRandom(100);
					z = -5164;
					break;
				
				default:
					x = 115488 + getRandom(100);
					y = 22096 + getRandom(100);
					z = -5168;
					break;
			}
			
			player.teleToLocation(x, y, z);
			st.exitQuest(true);
		}
		
		return htmltext;
	}
	
	public ToIVortex(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(KEPLON);
		addTalkId(KEPLON);
		addStartNpc(EUCLIE);
		addTalkId(EUCLIE);
		addStartNpc(PITHGON);
		addTalkId(PITHGON);
		addStartNpc(DIMENSION_VORTEX_1);
		addTalkId(DIMENSION_VORTEX_1);
		addStartNpc(DIMENSION_VORTEX_2);
		addTalkId(DIMENSION_VORTEX_2);
		addStartNpc(DIMENSION_VORTEX_3);
		addTalkId(DIMENSION_VORTEX_3);
		addStartNpc(TELEPORT_CUBIC);
		addTalkId(TELEPORT_CUBIC);
	}
	
	public static void main(String[] args)
	{
		new ToIVortex(-1, ToIVortex.class.getSimpleName(), "teleports");
	}
}
