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
	
	// Misc
	private final static int RESPAWN_DELAY = 3600000; // 1 hour
	private static L2Npc hekaton;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "32711-04.html":
				if (player.getLevel() >= 79)
				{
					st.startQuest();
					htmltext = (hasQuestItems(player, CET_1_SHEET, CET_2_SHEET, CET_3_SHEET)) ? "32711-04a.html" : "32711-04.html";
				}
				break;
			case "32711-05a.html":
				player.sendPacket(new RadarControl(0, 2, 186214, 61591, -4152));
				break;
			case "32711-05b.html":
				player.sendPacket(new RadarControl(0, 2, 187554, 60800, -4984));
				break;
			case "32711-05c.html":
				player.sendPacket(new RadarControl(0, 2, 193432, 53922, -4368));
				break;
			case "spawn":
				if (!hasQuestItems(player, CET_1_SHEET, CET_2_SHEET, CET_3_SHEET))
				{
					return getNoQuestMsg(player);
				}
				else if ((hekaton != null) && !hekaton.isDead())
				{
					return "32711-09.html";
				}
				String respawn = loadGlobalQuestVar("Respawn");
				long remain = (!respawn.isEmpty()) ? Long.parseLong(respawn) - System.currentTimeMillis() : 0;
				if (remain > 0)
				{
					return "32711-09a.html";
				}
				st.takeItems(CET_1_SHEET, 1);
				st.takeItems(CET_2_SHEET, 1);
				st.takeItems(CET_3_SHEET, 1);
				hekaton = addSpawn(HEKATON_PRIME, 191777, 56197, -7624, 0, false, 0);
				htmltext = "32711-09.html";
				break;
			case "32711-03.htm":
			case "32711-05.html":
			case "32711-06.html":
				break;
			default:
				htmltext = null;
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
		
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (player.getLevel() >= 79) ? "32711-01.htm" : "32711-02.htm";
				break;
			}
			case State.STARTED:
			{
				if ((hekaton != null) && !hekaton.isDead())
				{
					htmltext = "32711-09.html";
				}
				else if (st.isCond(1))
				{
					htmltext = (!hasQuestItems(player, CET_1_SHEET, CET_2_SHEET, CET_3_SHEET)) ? "32711-07.html" : "32711-08.html";
				}
				else if (st.isCond(2))
				{
					st.giveItems(SUPPORT_ITEMS, 1);
					st.exitQuest(true, true);
					htmltext = "32711-10.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, "1");
		if (partyMember == null)
		{
			return super.onKill(npc, player, isPet);
		}
		final QuestState st = partyMember.getQuestState(getName());
		
		switch (npc.getNpcId())
		{
			case GORGOLOS:
			{
				st.giveItems(CET_1_SHEET, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				break;
			}
			case LAST_TITAN_UTENUS:
			{
				st.giveItems(CET_2_SHEET, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				break;
			}
			case GIANT_MARPANAK:
			{
				st.giveItems(CET_3_SHEET, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				break;
			}
			case HEKATON_PRIME:
			{
				if (player.isInParty())
				{
					for (L2PcInstance pl : player.getParty().getMembers())
					{
						final QuestState qs = pl.getQuestState(getName());
						
						if ((qs != null) && qs.isCond(1))
						{
							qs.setCond(2, true);
						}
					}
					saveGlobalQuestVar("Respawn", Long.toString(System.currentTimeMillis() + RESPAWN_DELAY));
				}
				break;
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public Q00307_ControlDeviceOfTheGiants(int id, String name, String descr)
	{
		super(id, name, descr);
		addStartNpc(DROPH);
		addTalkId(DROPH);
		addKillId(GORGOLOS, LAST_TITAN_UTENUS, GIANT_MARPANAK, HEKATON_PRIME);
	}
	
	public static void main(String[] args)
	{
		new Q00307_ControlDeviceOfTheGiants(307, Q00307_ControlDeviceOfTheGiants.class.getSimpleName(), "Control Device of the Giants");
	}
}