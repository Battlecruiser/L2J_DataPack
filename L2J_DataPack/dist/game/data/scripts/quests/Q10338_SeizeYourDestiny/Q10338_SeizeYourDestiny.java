/*
 * Copyright (C) 2004-2015 L2J DataPack
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
package quests.Q10338_SeizeYourDestiny;

import com.l2jserver.gameserver.enums.CategoryType;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * @author Sdw
 */
public class Q10338_SeizeYourDestiny extends Quest
{
	// NPCs
	private static final int CELLPHINE = 33477;
	private static final int HADEL = 33344;
	private static final int HERMUNCUS = 33340;
	// Monsters
	private static final int HARNAKS_WRAITH = 27445;
	// Items
	private static final ItemHolder SCROLL_OF_AFTERLIFE = new ItemHolder(17600, 1);
	private static final ItemHolder STEEL_DOOR_GUILD_COIN = new ItemHolder(37045, 400);
	// Teleport
	private static final Location RELIQUARY_OF_THE_GIANT = new Location(-114962, 226564, -2864);
	// Movie
	private static final int RELIQUARY_OF_THE_GIANT_SCENE = 55;
	
	public Q10338_SeizeYourDestiny()
	{
		super(10338, Q10338_SeizeYourDestiny.class.getSimpleName(), "Seize Your Destiny");
		addStartNpc(CELLPHINE);
		addTalkId(CELLPHINE, HADEL, HERMUNCUS);
		addKillId(HARNAKS_WRAITH);
		addCondNotClassId(ClassId.JUDICATOR, "33477-04.htm");
		addCondIsNotSubClassActive("33477-04.htm");
		addCondMinLevel(85, "33477-04.htm");
		addCondInCategory(CategoryType.FOURTH_CLASS_GROUP, "33477-04.htm");
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, false);
		if (qs == null)
		{
			return null;
		}
		
		String htmltext = null;
		
		switch (event)
		{
			case "TELEPORT":
			{
				if (player.isSubClassActive() && !player.isDualClassActive())
				{
					htmltext = "";
					break;
				}
				teleportPlayer(player, RELIQUARY_OF_THE_GIANT, 0);
				player.showQuestMovie(RELIQUARY_OF_THE_GIANT_SCENE);
				break;
			}
			case "33477-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33344-05.htm":
			{
				if (qs.isCond(1))
				{
					qs.setCond(2, true);
					htmltext = event;
				}
				break;
			}
			case "33340-02.htm":
			{
				if (qs.isCond(3))
				{
					showOnScreenMsg(player, NpcStringId.YOU_MAY_USE_SCROLL_OF_AFTERLIFE_FROM_HERMUNCUS_TO_AWAKEN, ExShowScreenMessage.TOP_CENTER, 10000);
					giveItems(player, SCROLL_OF_AFTERLIFE);
					rewardItems(player, STEEL_DOOR_GUILD_COIN);
					qs.exitQuest(false, true);
					htmltext = event;
				}
				break;
			}
			case "33344-02.htm":
			case "33344-03.htm":
			case "33344-04.htm":
			case "33477-02.htm":
			{
				htmltext = event;
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		switch (npc.getId())
		{
			case CELLPHINE:
			{
				if (qs.isStarted())
				{
					htmltext = "33477-06.htm";
				}
				else if (qs.hasQuestItems(SCROLL_OF_AFTERLIFE.getId()) || qs.isCompleted())
				{
					htmltext = "33477-05.htm";
				}
				else if (qs.isCreated())
				{
					htmltext = "33477-01.htm";
				}
				break;
			}
			case HADEL:
			{
				if (qs.isCompleted() || player.isInCategory(CategoryType.AWAKEN_GROUP) || qs.hasQuestItems(SCROLL_OF_AFTERLIFE.getId()))
				{
					htmltext = "33344-07.htm";
				}
				else if (player.getLevel() < 85)
				{
					htmltext = "33344-06.htm";
				}
				else if (player.isSubClassActive() && !player.isDualClassActive())
				{
					htmltext = "33344-09.htm";
				}
				else
				{
					switch (qs.getCond())
					{
						case 1:
						{
							htmltext = "33344-01.htm";
							break;
						}
						case 2:
						{
							htmltext = "33344-08.htm";
							break;
						}
						case 3:
						{
							htmltext = "33344-07.htm";
							break;
						}
					}
				}
				break;
			}
			case HERMUNCUS:
			{
				if (player.isSubClassActive() && !player.isDualClassActive())
				{
					htmltext = "33340-04.htm";
					break;
				}
				else if (qs.isCond(3))
				{
					htmltext = "33340-01.htm";
					break;
				}
				else if (qs.hasQuestItems(SCROLL_OF_AFTERLIFE.getId()))
				{
					htmltext = "33340-03.htm";
					break;
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if (npc.getId() == HARNAKS_WRAITH)
		{
			final QuestState qs = getQuestState(player, false);
			if ((qs != null) && qs.isCond(2))
			{
				qs.setCond(3, true);
			}
		}
		return super.onKill(npc, player, isSummon);
	}
}