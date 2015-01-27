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
package quests.Q10322_SearchingForTheMysteriousPower;

import quests.Q10321_QualificationsOfTheSeeker.Q10321_QualificationsOfTheSeeker;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.TutorialShowHtml;
import com.l2jserver.gameserver.util.Broadcast;
import com.l2jserver.gameserver.util.Util;

/**
 * Searching For The Mysterious Power (10322)
 * @author ivantotov, Gladicek
 */
public final class Q10322_SearchingForTheMysteriousPower extends Quest
{
	// NPCs
	private static final int SHANNON = 32974;
	private static final int ADVENTURERS_GUIDE = 32981;
	private static final int EVAIN = 33464;
	// Monster
	private static final int SCARECROW = 27457;
	// Reward
	private static final int WOODEN_ARROW = 17;
	private static final int ADENA = 57;
	private static final int HEALING_POTION = 1060;
	private static final int APPRENTICE_ADVENTURERS_STAFF = 7816;
	private static final int APPRENTICE_ADVENTURERS_BONE_CLUB = 7817;
	private static final int APPRENTICE_ADVENTURERS_KNIFE = 7818;
	private static final int APPRENTICE_ADVENTURERS_CESTUS = 7819;
	private static final int APPRENTICE_ADVENTURERS_BOW = 7820;
	private static final int APPRENTICE_ADVENTURERS_LONG_SWORD = 7821;
	// Misc
	private static final int MAX_LEVEL = 20;
	// Buffs
	private static final SkillHolder[] FIGHTER_BUFFS =
	{
		new SkillHolder(4322, 1), // Wind Walk
		new SkillHolder(4323, 1), // Shield
		new SkillHolder(5637, 1), // Magic Barrier
		new SkillHolder(4324, 1), // Bless the Body
		new SkillHolder(4325, 1), // Vampiric Rage
		new SkillHolder(4326, 1), // Regeneration
	};
	private static final SkillHolder[] MAGE_BUFFS =
	{
		new SkillHolder(4322, 1), // Wind Walk
		new SkillHolder(4323, 1), // Shield
		new SkillHolder(5637, 1), // Magic Barrier
		new SkillHolder(4328, 1), // Bless the Soul
		new SkillHolder(4329, 1), // Acumen
		new SkillHolder(4330, 1), // Concentration
		new SkillHolder(4331, 1), // Empower
	};
	
	public Q10322_SearchingForTheMysteriousPower()
	{
		super(10322, Q10322_SearchingForTheMysteriousPower.class.getSimpleName(), "Searching For The Mysterious Power");
		addStartNpc(SHANNON);
		addTalkId(SHANNON, ADVENTURERS_GUIDE, EVAIN);
		addKillId(SCARECROW);
		addCondMaxLevel(MAX_LEVEL, "32974-01a.htm");
		addCondCompletedQuest(Q10321_QualificationsOfTheSeeker.class.getSimpleName(), "32974-01a.htm");
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
			case "32974-03.htm":
			{
				qs.startQuest();
				htmltext = event;
				break;
			}
			case "32974-02.htm":
			{
				htmltext = event;
				break;
			}
			case "32981-02.htm":
			{
				if (qs.isCond(4))
				{
					final SkillHolder[] buffs = player.isMageClass() ? MAGE_BUFFS : FIGHTER_BUFFS;
					if (buffs != null)
					{
						npc.setTarget(player);
						for (SkillHolder holder : buffs)
						{
							npc.doCast(holder.getSkill());
						}
					}
					player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_002_Guide_01.htm", TutorialShowHtml.LARGE_WINDOW));
					qs.setCond(5, true);
					htmltext = event;
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
		if ((qs != null) && qs.isStarted() && Util.checkIfInRange(1500, npc, killer, true))
		{
			if (qs.isCond(2))
			{
				qs.setCond(3, true);
			}
			else if (qs.isCond(5))
			{
				qs.setCond(6, true);
			}
		}
		return super.onKill(npc, killer, isSummon);
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
				if (npc.getId() == SHANNON)
				{
					htmltext = "32974-01.htm";
					break;
				}
				else if (npc.getId() == EVAIN)
				{
					htmltext = "33464-07.htm";
					break;
				}
				else if (npc.getId() == ADVENTURERS_GUIDE)
				{
					htmltext = "32981-04.htm";
					break;
				}
			}
			case State.STARTED:
			{
				if (npc.getId() == SHANNON)
				{
					if (qs.isCond(1))
					{
						htmltext = "32974-04.htm";
						break;
					}
				}
				else if (npc.getId() == ADVENTURERS_GUIDE)
				{
					if (qs.isCond(4))
					{
						htmltext = "32981-01.htm";
						break;
					}
					else if (qs.isCond(5))
					{
						htmltext = "32981-03.htm";
						break;
					}
				}
				else if (npc.getId() == EVAIN)
				{
					switch (qs.getCond())
					{
						case 1:
						{
							qs.setCond(2, true);
							htmltext = "33464-01.htm";
							break;
						}
						case 2:
						{
							htmltext = "33464-02.htm";
							break;
						}
						case 3:
						{
							qs.setCond(4, true);
							htmltext = "33464-03.htm";
							break;
						}
						case 4:
						{
							htmltext = "33464-04.htm";
							break;
						}
						case 5:
						{
							htmltext = "33464-05.htm";
							break;
						}
						case 6:
						{
							htmltext = "33464-06.html";
							showOnScreenMsg(player, NpcStringId.WEAPONS_HAVE_BEEN_ADDED_TO_YOUR_INVENTORY, ExShowScreenMessage.TOP_CENTER, 4500);
							giveItems(player, WOODEN_ARROW, 500);
							giveItems(player, ADENA, 70);
							giveItems(player, HEALING_POTION, 50);
							giveItems(player, APPRENTICE_ADVENTURERS_STAFF, 1);
							giveItems(player, APPRENTICE_ADVENTURERS_BONE_CLUB, 1);
							giveItems(player, APPRENTICE_ADVENTURERS_KNIFE, 1);
							giveItems(player, APPRENTICE_ADVENTURERS_CESTUS, 1);
							giveItems(player, APPRENTICE_ADVENTURERS_BOW, 1);
							giveItems(player, APPRENTICE_ADVENTURERS_LONG_SWORD, 1);
							addExpAndSp(player, 300, 5);
							Broadcast.toKnownPlayers(npc, new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getTemplate().getDisplayId(), NpcStringId.THERE_S_THE_NEXT_TRAINING_STEP));
							qs.exitQuest(false, true);
							break;
						}
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				if (npc.getId() == SHANNON)
				{
					htmltext = "32974-05.htm";
					break;
				}
				else if (npc.getId() == EVAIN)
				{
					htmltext = "33464-08.htm";
					break;
				}
				// Official is using same html for created/completed
				else if (npc.getId() == ADVENTURERS_GUIDE)
				{
					htmltext = "32981-04.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}