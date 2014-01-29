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
package quests.Q10292_SevenSignsGirlOfDoubt;

import quests.Q00198_SevenSignsEmbryo.Q00198_SevenSignsEmbryo;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.util.Util;

/**
 * Seven Signs, Girl of Doubt (10292)
 * @author Adry_85
 */
public final class Q10292_SevenSignsGirlOfDoubt extends Quest
{
	// NPCs
	private static final int HARDIN = 30832;
	private static final int WOOD = 32593;
	private static final int FRANZ = 32597;
	private static final int JAINA = 32617;
	private static final int ELCADIA = 32784;
	// Item
	private static final ItemHolder ELCADIAS_MARK = new ItemHolder(17226, 10);
	// Misc
	private static final int MIN_LEVEL = 81;
	boolean isBusy = false;
	// Monster
	private static final int CREATURE_OF_THE_DUSK1 = 27422;
	private static final int CREATURE_OF_THE_DUSK2 = 27424;
	private static final int[] MOBS =
	{
		22801, // Cruel Pincer Golem
		22802, // Cruel Pincer Golem
		22803, // Cruel Pincer Golem
		22804, // Horrifying Jackhammer Golem
		22805, // Horrifying Jackhammer Golem
		22806, // Horrifying Jackhammer Golem
	};
	
	private Q10292_SevenSignsGirlOfDoubt()
	{
		super(10292, Q10292_SevenSignsGirlOfDoubt.class.getSimpleName(), "Seven Signs, Girl of Doubt");
		addStartNpc(WOOD);
		addTalkId(WOOD, FRANZ, JAINA, ELCADIA, HARDIN);
		addKillId(MOBS);
		addKillId(CREATURE_OF_THE_DUSK1, CREATURE_OF_THE_DUSK2);
		registerQuestItems(ELCADIAS_MARK.getId());
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
			case "32593-04.htm":
			case "32593-06.htm":
			{
				htmltext = event;
				break;
			}
			case "32593-05.htm":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "32597-02.html":
			case "32597-06.html":
			{
				htmltext = event;
				break;
			}
			case "32597-07.html":
			{
				st.setCond(2, true);
				htmltext = event;
				break;
			}
			case "32784-02.html":
			{
				if (st.isCond(2))
				{
					htmltext = event;
				}
				break;
			}
			case "32784-03.html":
			{
				if (st.isCond(2))
				{
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "32784-06.html":
			{
				if (st.isCond(4) && hasItem(player, ELCADIAS_MARK))
				{
					playSound(player, QuestSound.ITEMSOUND_QUEST_MIDDLE);
					htmltext = event;
				}
				break;
			}
			case "32784-08.html":
			{
				if (st.isCond(4) && hasItem(player, ELCADIAS_MARK))
				{
					takeItem(player, ELCADIAS_MARK);
					st.setCond(5, true);
					htmltext = event;
				}
				break;
			}
			case "32784-12.html":
			case "32784-13.html":
			{
				if (st.isCond(6))
				{
					htmltext = event;
				}
				break;
			}
			case "32784-14.html":
			{
				if (st.isCond(6))
				{
					st.setCond(7, true);
					htmltext = event;
				}
				break;
			}
			case "SPAWN":
			{
				if (st.isCond(5))
				{
					isBusy = true;
					final L2Npc creature1 = addSpawn(CREATURE_OF_THE_DUSK1, 89440, -238016, -9632, 335, false, 0, false, player.getInstanceId());
					creature1.setIsNoRndWalk(true);
					final L2Npc creature2 = addSpawn(CREATURE_OF_THE_DUSK2, 89524, -238131, -9632, 56, false, 0, false, player.getInstanceId());
					creature2.setIsNoRndWalk(true);
					ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						@Override
						public void run()
						{
							creature1.deleteMe();
							creature2.deleteMe();
							st.unset("ex");
							isBusy = false;
						}
					}, 60000);
				}
				break;
			}
			case "30832-02.html":
			{
				if (st.isCond(7))
				{
					st.setCond(8, true);
					htmltext = event;
				}
				break;
			}
			case "30832-03.html":
			{
				if (st.isCond(8))
				{
					htmltext = event;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (Util.contains(MOBS, npc.getId()))
		{
			final QuestState st = getRandomPartyMemberState(player, 3, 3, npc);
			if ((st != null) && giveItemRandomly(st.getPlayer(), npc, ELCADIAS_MARK.getId(), 1, ELCADIAS_MARK.getCount(), 1.0, true))
			{
				st.setCond(4, true);
			}
		}
		else
		{
			final QuestState st = getQuestState(player, false);
			if ((st != null) && st.isCond(5))
			{
				final int value = st.getInt("ex") + 1;
				st.set("ex", value);
				if (value == 2)
				{
					st.setCond(6, true);
				}
			}
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		switch (npc.getId())
		{
			case WOOD:
			{
				if (st.isCompleted())
				{
					htmltext = "32593-02.html";
				}
				else if (st.isCreated())
				{
					st = player.getQuestState(Q00198_SevenSignsEmbryo.class.getSimpleName());
					htmltext = ((player.getLevel() >= MIN_LEVEL) && (st != null) && (st.isCompleted())) ? "32593-01.htm" : "32593-03.htm";
				}
				else if (st.isStarted())
				{
					htmltext = "32593-07.html";
				}
				break;
			}
			case FRANZ:
			{
				if (st.isCond(1))
				{
					htmltext = "32597-01.html";
				}
				else if (st.isCond(2))
				{
					htmltext = "32597-03.html";
				}
				break;
			}
			case ELCADIA:
			{
				switch (st.getCond())
				{
					case 2:
					{
						htmltext = "32784-01.html";
						break;
					}
					case 3:
					{
						htmltext = "32784-04.html";
						break;
					}
					case 4:
					{
						if (hasItem(player, ELCADIAS_MARK))
						{
							playSound(player, QuestSound.ITEMSOUND_QUEST_MIDDLE);
							htmltext = "32784-05.html";
						}
						break;
					}
					case 5:
					{
						if (isBusy)
						{
							htmltext = "32784-17.html";
						}
						else
						{
							htmltext = "32784-07.html";
						}
						break;
					}
					case 6:
					{
						htmltext = "32784-11.html";
						break;
					}
					case 7:
					{
						htmltext = "32784-15.html";
						break;
					}
					case 8:
					{
						if (player.isSubClassActive())
						{
							htmltext = "32784-18.html";
						}
						else
						{
							addExpAndSp(player, 10000000, 1000000);
							st.exitQuest(false, true);
							htmltext = "32784-16.html";
						}
						break;
					}
				}
				break;
			}
			case HARDIN:
			{
				if (st.isCond(7))
				{
					htmltext = "30832-01.html";
				}
				else if (st.isCond(8))
				{
					htmltext = "30832-04.html";
				}
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String args[])
	{
		new Q10292_SevenSignsGirlOfDoubt();
	}
}
