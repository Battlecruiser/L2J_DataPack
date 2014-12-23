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
package quests.Q10322_SearchingForTheMysteriousPower;

import quests.Q10321_QualificationsOfTheSeeker.Q10321_QualificationsOfTheSeeker;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.TutorialShowHtml;
import com.l2jserver.gameserver.util.Util;

/**
 * Searching For The Mysterious Power (10322)
 * @author ivantotov
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
	// Message
	// Misc
	private static final int MAX_LEVEL = 20;
	private static final ExShowScreenMessage MESSAGE = new ExShowScreenMessage(NpcStringId.WEAPONS_HAVE_BEEN_ADDED_TO_YOUR_INVENTORY, 2, 5000);
	// Buffs
	private static final SkillHolder[] FIGHTER_BUFFS =
	{
		new SkillHolder(5182, 1), // Blessing of Protection
		new SkillHolder(15642, 1), // Horn Melody
		new SkillHolder(15643, 1), // Drum Melody
		new SkillHolder(15644, 1), // Pipe Organ Melody
		new SkillHolder(15645, 1), // Guitar Melody
		new SkillHolder(15646, 1), // Harp Melody
		new SkillHolder(15647, 1), // Lute Melody
		new SkillHolder(15651, 1), // Prevailing Sonata
		new SkillHolder(15652, 1), // Daring Sonata
		new SkillHolder(15653, 1), // Refreshing Sonata
		new SkillHolder(15649, 1), // Warrior Harmony
	};
	private static final SkillHolder[] MAGE_BUFFS =
	{
		new SkillHolder(5182, 1), // Blessing of Protection
		new SkillHolder(15642, 1), // Horn Melody
		new SkillHolder(15643, 1), // Drum Melody
		new SkillHolder(15644, 1), // Pipe Organ Melody
		new SkillHolder(15645, 1), // Guitar Melody
		new SkillHolder(15646, 1), // Harp Melody
		new SkillHolder(15647, 1), // Lute Melody
		new SkillHolder(15651, 1), // Prevailing Sonata
		new SkillHolder(15652, 1), // Daring Sonata
		new SkillHolder(15653, 1), // Refreshing Sonata
		new SkillHolder(15650, 1), // Wizard Harmony
	};
	
	public Q10322_SearchingForTheMysteriousPower()
	{
		super(10322, Q10322_SearchingForTheMysteriousPower.class.getSimpleName(), "Searching For The Mysterious Power");
		addStartNpc(SHANNON);
		addTalkId(SHANNON, ADVENTURERS_GUIDE, EVAIN);
		addKillId(SCARECROW);
		addCondMaxLevel(MAX_LEVEL, "32974-01a.htm");
		addCondNotRace(Race.ERTHEIA, "32974-01b.htm");
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
			case "32981-02.html":
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
		String htmltext = getNoQuestMsg(player);
		if (qs.isCreated())
		{
			if (npc.getId() == SHANNON)
			{
				htmltext = "32974-01.htm";
			}
		}
		else if (qs.isStarted())
		{
			if (npc.getId() == SHANNON)
			{
				if (qs.isCond(1))
				{
					htmltext = "32974-04.html";
				}
			}
			else if (npc.getId() == ADVENTURERS_GUIDE)
			{
				if (qs.isCond(4))
				{
					htmltext = "32981-01.html";
				}
				else if (qs.isCond(5))
				{
					htmltext = "32981-03.html";
				}
			}
			else if (npc.getId() == EVAIN)
			{
				switch (qs.getCond())
				{
					case 1:
					{
						qs.setCond(2, true);
						htmltext = "33464-01.html";
						break;
					}
					case 2:
					{
						htmltext = "33464-02.html";
						break;
					}
					case 3:
					{
						qs.setCond(4, true);
						htmltext = "33464-03.html";
						break;
					}
					case 4:
					{
						htmltext = "33464-04.html";
						break;
					}
					case 5:
					{
						htmltext = "33464-05.html";
						break;
					}
					case 6:
					{
						htmltext = "33464-06.html";
						player.sendPacket(MESSAGE);
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
						qs.exitQuest(false, true);
						break;
					}
				}
			}
		}
		else if (qs.isCompleted())
		{
			if (npc.getId() == SHANNON)
			{
				htmltext = "32974-05.html";
			}
		}
		return htmltext;
	}
}