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
package quests.Q10735_ASpecialPower;

import quests.Q10734_DoOrDie.Q10734_DoOrDie;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.ClassId;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.TutorialShowHtml;

/**
 * @author Sdw
 */
public class Q10735_ASpecialPower extends Quest
{
	// NPC's
	private static final int AYANTHE = 33942;
	private static final int AYANTHE_2 = 33944;
	private static final int FLOATO = 27526;
	private static final int RATEL = 27527;
	// Misc
	private static final int MIN_LEVEL = 4;
	private static final int MAX_LEVEL = 20;
	// Items
	private static final ItemHolder SPIRITSHOTS_TRAINING = new ItemHolder(2509, 150);
	private static final ItemHolder SPIRITSHOTS_REWARD = new ItemHolder(2509, 500);
	// Locations
	private static final Location MOB_1 = new Location(-75112, 240760, -3615);
	private static final Location MOB_2 = new Location(-75016, 240456, -3628);
	
	private static final int KILL_COUNT_ID = 0;
	
	public Q10735_ASpecialPower()
	{
		super(10735, Q10735_ASpecialPower.class.getSimpleName(), "A Special Power");
		addStartNpc(AYANTHE);
		addTalkId(AYANTHE, AYANTHE_2);
		addKillId(FLOATO, RATEL);
		addCondLevel(MIN_LEVEL, MAX_LEVEL, "fixme.htm");
		addCondRace(Race.ERTHEIA, "fixme.htm");
		addCondClassId(ClassId.ERTHEIA_WIZARD, "fixme.html");
		addCondCompletedQuest(Q10734_DoOrDie.class.getSimpleName(), "fixme.htm");
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
			case "33942-02.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "33944-03.htm":
			{
				htmltext = event;
				showOnScreenMsg(player, NpcStringId.ATTACK_THE_MONSTER, ExShowScreenMessage.TOP_CENTER, 4500);
				break;
			}
			case "showscreen_1":
			{
				showOnScreenMsg(player, NpcStringId.AUTOMATE_SPIRITSHOT_AS_SHOWN_IN_THE_TUTORIAL, ExShowScreenMessage.TOP_CENTER, 4500);
				break;
			}
			case "33944-07.htm":
			{
				if (qs.isCond(5))
				{
					qs.setCond(6);
					showOnScreenMsg(player, NpcStringId.FIGHT_USING_SKILLS, ExShowScreenMessage.TOP_CENTER, 4500);
					addSpawn(RATEL, MOB_1, false, 0, false, player.getInstanceId());
					addSpawn(RATEL, MOB_2, false, 0, false, player.getInstanceId());
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
		QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		switch (npc.getId())
		{
			case AYANTHE:
			{
				switch (qs.getState())
				{
					case State.CREATED:
					{
						htmltext = "33942-01.htm";
						break;
					}
					case State.STARTED:
					{
						switch (qs.getCond())
						{
							case 1:
							{
								htmltext = "33942-02.htm";
								break;
							}
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							{
								htmltext = "33942-04.htm";
								break;
							}
							case 7:
							{
								htmltext = "33942-03.htm";
								giveAdena(player, 900, true);
								rewardItems(player, SPIRITSHOTS_REWARD);
								addExpAndSp(player, 3154, 0);
								qs.exitQuest(false, true);
								break;
							}
						}
						break;
					}
				}
				break;
			}
			case AYANTHE_2:
			{
				switch (qs.getCond())
				{
					case 1:
					{
						showOnScreenMsg(player, NpcStringId.ATTACK_THE_MONSTER, ExShowScreenMessage.TOP_CENTER, 4500);
						qs.setCond(2, true);
						addSpawn(FLOATO, MOB_1, false, 0, false, player.getInstanceId());
						addSpawn(FLOATO, MOB_2, false, 0, false, player.getInstanceId());
						htmltext = "33944-01.htm";
						break;
					}
					case 2:
					case 4:
					case 6:
					{
						htmltext = "33944-02.htm";
						break;
					}
					case 3:
					{
						if (qs.getInt("ss") == 1)
						{
							addSpawn(FLOATO, MOB_1, false, 0, false, player.getInstanceId());
							addSpawn(FLOATO, MOB_2, false, 0, false, player.getInstanceId());
							showOnScreenMsg(player, NpcStringId.ATTACK_THE_MONSTER, ExShowScreenMessage.TOP_CENTER, 4500);
							htmltext = "33944-05.htm";
							qs.setCond(4, true);
						}
						else
						{
							giveItems(player, SPIRITSHOTS_TRAINING);
							showOnScreenMsg(player, NpcStringId.SPIRITSHOT_HAVE_BEEN_ADDED_TO_YOUR_INVENTORY, ExShowScreenMessage.TOP_CENTER, 4500);
							startQuestTimer("showscreen_1", 4500, npc, player);
							player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_003_bullet_01.htm", TutorialShowHtml.LARGE_WINDOW));
							htmltext = "33944-04.htm";
							qs.set("ss", 1);
						}
						break;
					}
					case 5:
					{
						htmltext = "33944-06.htm";
						player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_004_skill_01.htm", TutorialShowHtml.LARGE_WINDOW));
						break;
					}
					case 7:
					{
						htmltext = "33944-08.htm";
						break;
					}
				}
				break;
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		
		if (qs != null)
		{
			final int cond = qs.getCond();
			if (npc.getId() == FLOATO)
			{
				if ((cond == 2) || (cond == 4))
				{
					final int value = qs.getMemoStateEx(KILL_COUNT_ID) + 1;
					playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
					if (value >= 2)
					{
						qs.setCond(cond + 1, true);
						qs.setMemoStateEx(KILL_COUNT_ID, 0);
					}
					else
					{
						qs.setMemoStateEx(KILL_COUNT_ID, value);
					}
				}
			}
			else if (npc.getId() == RATEL)
			{
				if (cond == 6)
				{
					final int value = qs.getMemoStateEx(KILL_COUNT_ID) + 1;
					playSound(killer, QuestSound.ITEMSOUND_QUEST_ITEMGET);
					if (value >= 2)
					{
						qs.setCond(cond + 1, true);
						qs.setMemoStateEx(KILL_COUNT_ID, 0);
						showOnScreenMsg(killer, NpcStringId.TALK_TO_AYANTHE_TO_LEAVE_THE_TRAINING_GROUNDS, ExShowScreenMessage.TOP_CENTER, 4500);
					}
					else
					{
						qs.setMemoStateEx(KILL_COUNT_ID, value);
					}
				}
			}
		}
		
		return super.onKill(npc, killer, isSummon);
	}
}
