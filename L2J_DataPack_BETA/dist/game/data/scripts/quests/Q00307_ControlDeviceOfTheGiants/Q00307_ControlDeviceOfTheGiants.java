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
package quests.Q00307_ControlDeviceOfTheGiants;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.RadarControl;

/**
 * Control Device of the Giants (307)
 * @author Gladicek, malyelfik
 */
public class Q00307_ControlDeviceOfTheGiants extends Quest
{
	// NPC
	private final static int DROPH = 32711;
	// RB
	private final static int GORGOLOS = 25681;
	private final static int LAST_TITAN_UTENUS = 25684;
	private final static int GIANT_MARPANAK = 25680;
	private final static int HEKATON_PRIME = 25687;
	// Items
	private final static int SUPPORT_ITEMS = 14850;
	private final static int CET_1_SHEET = 14851;
	private final static int CET_2_SHEET = 14852;
	private final static int CET_3_SHEET = 14853;
	
	private final static int respawnDelay = 3600000; // 1 hour
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = event;
		
		if (st == null)
		{
			return null;
		}
		
		if (event.equalsIgnoreCase("32711-04.htm"))
		{
			if (st.getPlayer().getLevel() >= 79)
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
				if (!st.hasQuestItems(CET_1_SHEET) || !st.hasQuestItems(CET_2_SHEET) || !st.hasQuestItems(CET_3_SHEET))
				{
					htmltext = "32711-04.htm";
				}
				else if (st.hasQuestItems(CET_1_SHEET) && st.hasQuestItems(CET_2_SHEET) && st.hasQuestItems(CET_3_SHEET))
				{
					htmltext = "32711-04a.htm";
				}
			}
		}
		else if (event.equalsIgnoreCase("32711-05a.htm"))
		{
			player.sendPacket(new RadarControl(0, 2, 186214, 61591, -4152));
		}
		else if (event.equalsIgnoreCase("32711-05b.htm"))
		{
			player.sendPacket(new RadarControl(0, 2, 187554, 60800, -4984));
		}
		else if (event.equalsIgnoreCase("32711-05c.htm"))
		{
			player.sendPacket(new RadarControl(0, 2, 193432, 53922, -4368));
		}
		// Hekaton Prime spawn
		else if (event.equalsIgnoreCase("spawn"))
		{
			String test = st.getGlobalQuestVar("Hekaton respawn");
			
			if (test.isEmpty())
			{
				st.takeItems(CET_1_SHEET, 1);
				st.takeItems(CET_2_SHEET, 1);
				st.takeItems(CET_3_SHEET, 1);
				addSpawn(HEKATON_PRIME, 191887, 56405, -7626, 1000, false, 0);
				st.set("spawned", "1");
				htmltext = "32711-09.htm";
			}
			else
			{
				long remain = Long.parseLong(test) - System.currentTimeMillis();
				
				if (remain > 0)
				{
					htmltext = "32711-09a.htm";
				}
				else
				{
					st.takeItems(CET_1_SHEET, 1);
					st.takeItems(CET_2_SHEET, 1);
					st.takeItems(CET_3_SHEET, 1);
					addSpawn(HEKATON_PRIME, 192062, 57357, -7650, 1000, false, 0);
					st.set("spawned", "1");
					htmltext = "32711-09.htm";
				}
			}
		}
		return htmltext;
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
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				if (player.getLevel() >= 79)
				{
					htmltext = "32711-01.htm";
				}
				else
				{
					htmltext = "32711-02.htm";
				}
				break;
			}
			case State.STARTED:
			{
				if (st.getInt("spawned") == 1)
				{
					htmltext = "32711-09.htm";
				}
				else if (st.getInt("cond") == 1)
				{
					if (!st.hasQuestItems(CET_1_SHEET) || !st.hasQuestItems(CET_2_SHEET) || !st.hasQuestItems(CET_3_SHEET))
					{
						htmltext = "32711-07.htm";
					}
					else
					{
						htmltext = "32711-08.htm";
					}
				}
				else if (st.getInt("cond") == 2)
				{
					st.giveItems(SUPPORT_ITEMS, 1);
					st.playSound("ItemSound.quest_finish");
					st.exitQuest(true);
					htmltext = "32711-10.htm";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, "1");
		if (partyMember == null)
		{
			return null;
		}
		final QuestState st = partyMember.getQuestState(getName());
		
		switch (npc.getNpcId())
		{
			case GORGOLOS:
			{
				st.giveItems(CET_1_SHEET, 1);
				st.playSound("ItemSound.quest_itemget");
				break;
			}
			case LAST_TITAN_UTENUS:
			{
				st.giveItems(CET_2_SHEET, 1);
				st.playSound("ItemSound.quest_itemget");
				break;
			}
			case GIANT_MARPANAK:
			{
				st.giveItems(CET_3_SHEET, 1);
				st.playSound("ItemSound.quest_itemget");
				break;
			}
			case HEKATON_PRIME:
			{
				if (player.getParty() != null)
				{
					for (L2PcInstance party : player.getParty().getMembers())
					{
						rewardPlayer(party);
					}
				}
				else
				{
					rewardPlayer(player);
				}
				break;
			}
		}
		return null;
	}
	
	private void rewardPlayer(L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		
		if ((st != null) && (st.getInt("spawned") == 1))
		{
			st.playSound("ItemSound.quest_middle");
			st.unset("spawned");
			st.set("cond", "2");
			st.saveGlobalQuestVar("Hekaton respawn", Long.toString(System.currentTimeMillis() + respawnDelay));
		}
	}
	
	public Q00307_ControlDeviceOfTheGiants(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addStartNpc(DROPH);
		addTalkId(DROPH);
		addKillId(GORGOLOS);
		addKillId(LAST_TITAN_UTENUS);
		addKillId(GIANT_MARPANAK);
		addKillId(HEKATON_PRIME);
	}
	
	public static void main(String[] args)
	{
		new Q00307_ControlDeviceOfTheGiants(307, Q00307_ControlDeviceOfTheGiants.class.getSimpleName(), "Control Device Of The Giants");
	}
}
