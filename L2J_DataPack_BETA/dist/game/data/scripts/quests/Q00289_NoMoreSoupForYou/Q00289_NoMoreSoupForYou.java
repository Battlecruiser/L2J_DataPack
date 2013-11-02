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
package quests.Q00289_NoMoreSoupForYou;

import quests.Q00252_ItSmellsDelicious.Q00252_ItSmellsDelicious;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * No More Soup For You (289)
 * @author kostantinos
 */
public class Q00289_NoMoreSoupForYou extends Quest
{
	// NPC
	public static final int STAN = 30200;
	// Item
	public static final int SOUP = 15712;
	// Misc
	public static final int RATE = 5;
	
	private static final int[] MOBS =
	{
		18908,
		22779,
		22786,
		22787,
		22788
	};
	
	private static final int[][] WEAPONS =
	{
		{
			10377,
			1
		},
		{
			10401,
			1
		},
		{
			10401,
			2
		},
		{
			10401,
			3
		},
		{
			10401,
			4
		},
		{
			10401,
			5
		},
		{
			10401,
			6
		}
	};
	
	private static final int[][] ARMORS =
	{
		{
			15812,
			1
		},
		{
			15813,
			1
		},
		{
			15814,
			1
		},
		{
			15791,
			1
		},
		{
			15787,
			1
		},
		{
			15784,
			1
		},
		{
			15781,
			1
		},
		{
			15778,
			1
		},
		{
			15775,
			1
		},
		{
			15774,
			5
		},
		{
			15773,
			5
		},
		{
			15772,
			5
		},
		{
			15693,
			5
		},
		{
			15657,
			5
		},
		{
			15654,
			5
		},
		{
			15651,
			5
		},
		{
			15648,
			5
		},
		{
			15645,
			5
		}
	};
	
	public Q00289_NoMoreSoupForYou(int id, String name, String descr)
	{
		super(id, name, descr);
		addStartNpc(STAN);
		addTalkId(STAN);
		addKillId(MOBS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		int b = getRandom(18);
		int c = getRandom(7);
		
		if (npc.getId() == STAN)
		{
			if (event.equalsIgnoreCase("30200-03.htm"))
			{
				st.startQuest();
			}
			else if (event.equalsIgnoreCase("30200-05.htm"))
			{
				if (getQuestItemsCount(player, SOUP) >= 500)
				{
					giveItems(player, WEAPONS[c][0], WEAPONS[c][1]);
					takeItems(player, SOUP, 500);
					st.playSound(QuestSound.ITEMSOUND_QUEST_MIDDLE);
					htmltext = "30200-04.htm";
				}
				else
				{
					htmltext = "30200-07.htm";
				}
			}
			else if (event.equalsIgnoreCase("30200-06.htm"))
			{
				if (getQuestItemsCount(player, SOUP) >= 100)
				{
					giveItems(player, ARMORS[b][0], ARMORS[b][1]);
					takeItems(player, SOUP, 100);
					st.playSound(QuestSound.ITEMSOUND_QUEST_MIDDLE);
					htmltext = "30200-04.htm";
				}
				else
				{
					htmltext = "30200-07.htm";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		QuestState st = player.getQuestState(getName());
		int npcId = npc.getId();
		if ((st == null) || (st.getState() != State.STARTED))
		{
			return null;
		}
		if (Util.contains(MOBS, npcId))
		{
			giveItems(player, SOUP, 1 * RATE);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
		return super.onKill(npc, player, isSummon);
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
		
		if (npc.getId() == STAN)
		{
			switch (st.getState())
			{
				case State.CREATED:
					QuestState _prev = player.getQuestState(Q00252_ItSmellsDelicious.class.getSimpleName());
					htmltext = ((_prev != null) && _prev.isCompleted() && (player.getLevel() >= 82)) ? "30200-01.htm" : "30200-00.htm";
					break;
				case State.STARTED:
					if (st.isCond(1))
					{
						htmltext = (getQuestItemsCount(player, SOUP) >= 100) ? "30200-04.htm" : "30200-03.htm";
					}
					break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00289_NoMoreSoupForYou(289, Q00289_NoMoreSoupForYou.class.getSimpleName(), "No More Soup For You");
	}
}
