/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q10286_ReunionWithSirra;

import quests.Q10285_MeetingSirra.Q10285_MeetingSirra;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Reunion with Sirra (10286)
 * @author Adry_85
 */
public class Q10286_ReunionWithSirra extends Quest
{
	// NPCs
	private static final int RAFFORTY = 32020;
	private static final int JINIA = 32760;
	private static final int SIRRA = 32762;
	private static final int JINIA2 = 32781;
	// Item
	private static final int BLACK_FROZEN_CORE = 15470;
	// Misc
	private static final int MIN_LEVEL = 82;
	// Location
	private static final Location EXIT_LOC = new Location(113793, -109342, -845, 0);
	
	private Q10286_ReunionWithSirra()
	{
		super(10286, Q10286_ReunionWithSirra.class.getSimpleName(), "Reunion with Sirra");
		addStartNpc(RAFFORTY);
		addTalkId(RAFFORTY, JINIA, SIRRA, JINIA2);
		registerQuestItems(BLACK_FROZEN_CORE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "32020-02.htm":
			{
				st.startQuest();
				st.setMemoState(1);
				htmltext = event;
				break;
			}
			case "32020-03.html":
			case "32760-02.html":
			case "32760-03.html":
			case "32760-04.html":
			{
				if (st.isMemoState(1))
				{
					htmltext = event;
				}
				break;
			}
			case "32760-05.html":
			{
				if (st.isMemoState(1))
				{
					L2Npc sirra = addSpawn(SIRRA, -23905, -8790, -5384, 56238, false, 0, false, npc.getInstanceId());
					sirra.broadcastPacket(new NpcSay(sirra.getObjectId(), Say2.NPC_ALL, sirra.getId(), NpcStringId.YOU_ADVANCED_BRAVELY_BUT_GOT_SUCH_A_TINY_RESULT_HOHOHO));
					st.set("ex", 1);
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "32760-07.html":
			{
				if (st.isMemoState(1) && (st.getInt("ex") == 2))
				{
					st.unset("ex");
					st.setMemoState(2);
					final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
					world.removeAllowed(player.getObjectId());
					player.setInstanceId(0);
					htmltext = event;
				}
				break;
			}
			case "32760-08.html":
			{
				if (st.isMemoState(2))
				{
					st.setCond(5, true);
					player.teleToLocation(EXIT_LOC, 0);
					htmltext = event; // TODO: missing "jinia_npc_q10286_10.htm"
				}
				break;
			}
			case "32762-02.html":
			case "32762-03.html":
			{
				if (st.isMemoState(1) && (st.getInt("ex") == 1))
				{
					htmltext = event;
				}
				break;
			}
			case "32762-04.html":
			{
				if (st.isMemoState(1) && (st.getInt("ex") == 1))
				{
					if (!st.hasQuestItems(BLACK_FROZEN_CORE))
					{
						st.giveItems(BLACK_FROZEN_CORE, 5);
					}
					st.set("ex", 2);
					st.setCond(4, true);
					htmltext = event;
				}
				break;
			}
			case "32781-02.html":
			case "32781-03.html":
			{
				if (st.isMemoState(2))
				{
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
			{
				if (npc.getId() == RAFFORTY)
				{
					htmltext = "32020-05.html";
				}
				break;
			}
			case State.CREATED:
			{
				if (npc.getId() == RAFFORTY)
				{
					st = player.getQuestState(Q10285_MeetingSirra.class.getSimpleName());
					htmltext = ((player.getLevel() >= MIN_LEVEL) && (st != null) && (st.isCompleted())) ? "32020-01.htm" : "32020-04.htm";
				}
				break;
			}
			case State.STARTED:
			{
				switch (npc.getId())
				{
					case RAFFORTY:
					{
						if (st.isMemoState(1))
						{
							htmltext = (player.getLevel() >= MIN_LEVEL) ? "32020-06.html" : "32020-08.html";
						}
						else if (st.isMemoState(2))
						{
							htmltext = "32020-07.html";
						}
						break;
					}
					case JINIA:
					{
						if (st.isMemoState(1))
						{
							final int state = st.getInt("ex");
							switch (state)
							{
								case 0:
								{
									htmltext = "32760-01.html";
									break;
								}
								case 1:
								{
									htmltext = "32760-05.html";
									break;
								}
								case 2:
								{
									htmltext = "32760-06.html";
									break;
								}
							}
						}
						break;
					}
					case SIRRA:
					{
						if (st.isMemoState(1))
						{
							final int state = st.getInt("ex");
							if (state == 1)
							{
								htmltext = "32762-01.html";
							}
							else if (state == 2)
							{
								htmltext = "32762-05.html";
							}
						}
						break;
					}
					case JINIA2:
					{
						if (st.isMemoState(2))
						{
							htmltext = "32781-01.html";
						}
						else if (st.isMemoState(10))
						{
							st.addExpAndSp(2152200, 181070);
							st.exitQuest(false, true);
							htmltext = "32781-04.html";
						}
						break;
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String args[])
	{
		new Q10286_ReunionWithSirra();
	}
}
