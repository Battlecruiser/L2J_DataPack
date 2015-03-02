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
package quests.Q10323_TrainLikeItsReal;

import quests.Q10322_SearchingForTheMysteriousPower.Q10322_SearchingForTheMysteriousPower;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExQuestNpcLogList;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.TutorialShowHtml;

/**
 * @author Sdw, Gladicek
 */
public class Q10323_TrainLikeItsReal extends Quest
{
	// NPC's
	private static final int EVAIN = 33464;
	private static final int HOLDEN = 33194;
	private static final int SHANNON = 32974;
	// Mobs
	private static final int TRAINING_GOLEM = 27532;
	// Misc
	private static final int MAX_LEVEL = 20;
	// Items
	private static final ItemHolder SPIRITSHOTS = new ItemHolder(2509, 500);
	private static final ItemHolder SOULSHOTS = new ItemHolder(1835, 500);
	
	public Q10323_TrainLikeItsReal()
	{
		super(10323, Q10323_TrainLikeItsReal.class.getSimpleName(), "Train Like It's Real");
		addStartNpc(EVAIN);
		addTalkId(HOLDEN, EVAIN, SHANNON);
		addKillId(TRAINING_GOLEM);
		addCondMaxLevel(MAX_LEVEL, "33464-05.htm");
		addCondCompletedQuest(Q10322_SearchingForTheMysteriousPower.class.getSimpleName(), "33464-05.htm");
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
			case "33464-02.htm":
			case "33194-02.htm":
			{
				htmltext = event;
				break;
			}
			case "showscreen_spiritshot":
			{
				showOnScreenMsg(player, NpcStringId.AUTOMATE_SPIRITSHOT_AS_SHOWN_IN_THE_TUTORIAL, ExShowScreenMessage.TOP_CENTER, 4500);
				break;
			}
			case "showscreen_soulshot":
			{
				showOnScreenMsg(player, NpcStringId.AUTOMATE_SOULSHOT_AS_SHOWN_IN_THE_TUTORIAL, ExShowScreenMessage.TOP_CENTER, 4500);
				break;
			}
			case "33464-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33194-03.htm":
			{
				if (qs.isCond(1))
				{
					qs.setCond(2, true);
					final ExQuestNpcLogList packet = new ExQuestNpcLogList(getId());
					packet.addNpc(TRAINING_GOLEM, 0);
					player.sendPacket(packet);
					htmltext = event;
				}
				break;
			}
			case "33194-05.htm":
			{
				if (qs.isCond(3))
				{
					qs.setMemoState(0);
					if (player.isMageClass())
					{
						giveItems(player, SPIRITSHOTS);
						showOnScreenMsg(player, NpcStringId.SPIRITSHOT_HAVE_BEEN_ADDED_TO_YOUR_INVENTORY, ExShowScreenMessage.TOP_CENTER, 4500);
						startQuestTimer("showscreen_spiritshot", 4500, npc, player);
						player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_003_bullet_01.htm", TutorialShowHtml.LARGE_WINDOW));
						qs.setCond(5, true);
					}
					else
					{
						giveItems(player, SOULSHOTS);
						showOnScreenMsg(player, NpcStringId.SOULSHOT_HAVE_BEEN_ADDED_TO_YOUR_INVENTORY, ExShowScreenMessage.TOP_CENTER, 4500);
						startQuestTimer("showscreen_soulshot", 4500, npc, player);
						player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_003_bullet_01.htm", TutorialShowHtml.LARGE_WINDOW));
						qs.setCond(4, true);
					}
					htmltext = event;
				}
				break;
			}
			case "33194-08.htm":
			{
				if (qs.isCond(8))
				{
					qs.setCond(9, true);
					htmltext = event;
				}
				break;
			}
			case "32974-02.htm":
			{
				if (qs.isCond(9))
				{
					addExpAndSp(player, 1700, 5);
					giveAdena(player, 90, true);
					qs.exitQuest(false, true);
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
		final QuestState qs = getQuestState(player, true);
		String htmltext = null;
		
		switch (qs.getState())
		{
			case State.CREATED:
			{
				if (npc.getId() == EVAIN)
				{
					htmltext = "33464-01.htm";
					break;
				}
				else if (npc.getId() == SHANNON)
				{
					htmltext = "32974-03.htm";
					break;
				}
			}
			case State.STARTED:
			{
				if (npc.getId() == EVAIN)
				{
					htmltext = "33464-03.htm";
					break;
				}
				else if (npc.getId() == HOLDEN)
				{
					switch (qs.getCond())
					{
						case 1:
						{
							htmltext = "33194-01.htm";
							break;
						}
						case 3:
						{
							htmltext = "33194-04.htm";
							break;
						}
						case 4:
						{
							htmltext = "33194-06.htm";
							qs.setCond(6, true);
							break;
						}
						case 5:
						{
							htmltext = "33194-06.htm";
							qs.setCond(7, true);
							break;
						}
						case 8:
						{
							htmltext = "33194-07.htm";
							break;
						}
					}
					break;
				}
				else if (npc.getId() == SHANNON)
				{
					if (qs.isCond(9))
					{
						htmltext = "32974-01.htm";
						break;
					}
				}
			}
			case State.COMPLETED:
			{
				if (npc.getId() == EVAIN)
				{
					htmltext = "33464-04.htm";
					break;
				}
				else if (npc.getId() == SHANNON)
				{
					htmltext = "32974-05.htm";
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		
		if ((qs != null) && qs.isStarted())
		{
			int killedGolem = qs.getMemoState();
			
			if (qs.isCond(2))
			{
				killedGolem++;
				final ExQuestNpcLogList packet = new ExQuestNpcLogList(getId());
				packet.addNpcString(NpcStringId.ELIMINATE_THE_TRAINING_GOLEM, killedGolem);
				killer.sendPacket(packet);
				if (killedGolem >= 4)
				{
					qs.setCond(3, true);
				}
				else
				{
					qs.setMemoState(killedGolem);
					playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
			else if (qs.isCond(6) || qs.isCond(7))
			{
				killedGolem++;
				final ExQuestNpcLogList packet = new ExQuestNpcLogList(getId());
				packet.addNpcString(NpcStringId.ELIMINATE_THE_TRAINING_GOLEM2, killedGolem);
				killer.sendPacket(packet);
				if (killedGolem >= 4)
				{
					qs.setCond(8, true);
				}
				else
				{
					qs.setMemoState(killedGolem);
					playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
				}
			}
		}
		
		return super.onKill(npc, killer, isSummon);
	}
}
