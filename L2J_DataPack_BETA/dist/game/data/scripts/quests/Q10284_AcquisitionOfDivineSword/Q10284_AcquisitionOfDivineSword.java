/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package quests.Q10284_AcquisitionOfDivineSword;

import quests.Q10283_RequestOfIceMerchant.Q10283_RequestOfIceMerchant;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Acquisition of Divine Sword (10284)
 * @author Adry_85
 */
public final class Q10284_AcquisitionOfDivineSword extends Quest
{
	// NPCs
	private static final int RAFFORTY = 32020;
	private static final int KRUN = 32653;
	private static final int TARUN = 32654;
	private static final int JINIA = 32760;
	// Misc
	private static final int MIN_LEVEL = 82;
	// Item
	private static final int COLD_RESISTANCE_POTION = 15514;
	// Location
	private static final Location EXIT_LOC = new Location(113793, -109342, -845, 0);
	
	private Q10284_AcquisitionOfDivineSword()
	{
		super(10284, Q10284_AcquisitionOfDivineSword.class.getSimpleName(), "Acquisition of Divine Sword");
		addStartNpc(RAFFORTY);
		addTalkId(RAFFORTY, JINIA, TARUN, KRUN);
		registerQuestItems(COLD_RESISTANCE_POTION);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = getQuestState(player, false);
		if (st == null)
		{
			return null;
		}
		
		String htmltext = null;
		switch (event)
		{
			case "32020-02.html":
			{
				st.startQuest();
				st.setMemoState(1);
				htmltext = event;
				break;
			}
			case "32020-03.html":
			case "32760-02a.html":
			case "32760-02b.html":
			case "32760-03a.html":
			case "32760-03b.html":
			case "32760-04a.html":
			case "32760-04b.html":
			{
				if (st.isMemoState(1))
				{
					htmltext = event;
				}
				break;
			}
			case "32760-02c.html":
			{
				if (st.isMemoState(1))
				{
					st.set("ex1", 1);
					htmltext = event;
				}
				break;
			}
			case "another_story":
			{
				if (st.isMemoState(1))
				{
					if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 0) && (st.getInt("ex3") == 0))
					{
						htmltext = "32760-05a.html";
					}
					else if ((st.getInt("ex1") == 0) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 0))
					{
						htmltext = "32760-05b.html";
					}
					else if ((st.getInt("ex1") == 0) && (st.getInt("ex2") == 0) && (st.getInt("ex3") == 1))
					{
						htmltext = "32760-05c.html";
					}
					else if ((st.getInt("ex1") == 0) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 1))
					{
						htmltext = "32760-05d.html";
					}
					else if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 0) && (st.getInt("ex3") == 1))
					{
						htmltext = "32760-05e.html";
					}
					else if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 0))
					{
						htmltext = "32760-05f.html";
					}
					else if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 1))
					{
						htmltext = "32760-05g.html";
					}
				}
				break;
			}
			case "32760-03c.html":
			{
				if (st.isMemoState(1))
				{
					st.set("ex2", 1);
					htmltext = event;
				}
				break;
			}
			case "32760-04c.html":
			{
				if (st.isMemoState(1))
				{
					st.set("ex3", 1);
					htmltext = event;
				}
				break;
			}
			case "32760-06.html":
			{
				if (st.isMemoState(1) && (st.getInt("ex1") == 1) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 1))
				{
					htmltext = event;
				}
				break;
			}
			case "32760-07.html":
			{
				if (st.isMemoState(1) && (st.getInt("ex1") == 1) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 1))
				{
					st.unset("ex1");
					st.unset("ex2");
					st.unset("ex3");
					st.setCond(3, true);
					st.setMemoState(2);
					final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
					world.removeAllowed(player.getObjectId());
					player.setInstanceId(0);
					htmltext = event;
				}
				break;
			}
			case "exit_instance":
			{
				if (st.isMemoState(2))
				{
					player.teleToLocation(EXIT_LOC, 0);
				}
				break;
			}
			case "32654-02.html":
			case "32654-03.html":
			case "32653-02.html":
			case "32653-03.html":
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
		QuestState st = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
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
					st = player.getQuestState(Q10283_RequestOfIceMerchant.class.getSimpleName());
					htmltext = ((player.getLevel() >= MIN_LEVEL) && (st != null) && (st.isCompleted())) ? "32020-01.htm" : "32020-04.html";
				}
				break;
			}
			case State.STARTED:
			{
				switch (npc.getId())
				{
					case RAFFORTY:
					{
						switch (st.getMemoState())
						{
							case 1:
							{
								htmltext = (player.getLevel() >= MIN_LEVEL) ? "32020-06.html" : "32020-08.html";
								break;
							}
							case 2:
							{
								htmltext = "32020-07.html";
								break;
							}
						}
						break;
					}
					case JINIA:
					{
						if (st.isMemoState(1))
						{
							if ((st.getInt("ex1") == 0) && (st.getInt("ex2") == 0) && (st.getInt("ex3") == 0))
							{
								htmltext = "32760-01.html";
							}
							else if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 0) && (st.getInt("ex3") == 0))
							{
								htmltext = "32760-01a.html";
							}
							else if ((st.getInt("ex1") == 0) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 0))
							{
								htmltext = "32760-01b.html";
							}
							else if ((st.getInt("ex1") == 0) && (st.getInt("ex2") == 0) && (st.getInt("ex3") == 1))
							{
								htmltext = "32760-01c.html";
							}
							else if ((st.getInt("ex1") == 0) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 1))
							{
								htmltext = "32760-01d.html";
							}
							else if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 0) && (st.getInt("ex3") == 1))
							{
								htmltext = "32760-01e.html";
							}
							else if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 0))
							{
								htmltext = "32760-01f.html";
							}
							else if ((st.getInt("ex1") == 1) && (st.getInt("ex2") == 1) && (st.getInt("ex3") == 1))
							{
								htmltext = "32760-01g.html";
							}
						}
						break;
					}
					case TARUN:
					{
						switch (st.getMemoState())
						{
							case 2:
							{
								htmltext = (player.getLevel() >= MIN_LEVEL) ? "32654-01.html" : "32654-05.html";
								break;
							}
							case 3:
							{
								st.giveAdena(296425, true);
								st.addExpAndSp(921805, 82230);
								st.exitQuest(false, true);
								htmltext = "32654-04.html";
								break;
							}
						}
						break;
					}
					case KRUN:
					{
						switch (st.getMemoState())
						{
							case 2:
							{
								htmltext = (player.getLevel() >= MIN_LEVEL) ? "32653-01.html" : "32653-05.html";
								break;
							}
							case 3:
							{
								st.giveAdena(296425, true);
								st.addExpAndSp(921805, 82230);
								st.exitQuest(false, true);
								htmltext = "32653-04.html";
								break;
							}
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
		new Q10284_AcquisitionOfDivineSword();
	}
}
