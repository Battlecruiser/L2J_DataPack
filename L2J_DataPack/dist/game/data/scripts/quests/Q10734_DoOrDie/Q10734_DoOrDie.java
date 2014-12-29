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
package quests.Q10734_DoOrDie;

import quests.Q10733_TheTestForSurvival.Q10733_TheTestForSurvival;

import com.l2jserver.gameserver.enums.Race;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.TutorialShowHtml;

/**
 * @author Sdw
 */
public class Q10734_DoOrDie extends Quest
{
	// NPC's
	private static final int KATALIN = 33943;
	private static final int AYANTHE = 33942;
	private static final int ADVENTURER_S_GUIDE_APPRENTICE = 33950;
	private static final int TRAINING_DUMMY = 19546;
	// Misc
	private static final int MAX_LEVEL = 20;
	// Skills
	private final static SkillHolder[] COMMON_BUFFS =
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
	};
	private static final SkillHolder WARRIOR_HARMONY = new SkillHolder(15649, 1);
	private static final SkillHolder WIZARD_HARMONY = new SkillHolder(15650, 1);
	
	public Q10734_DoOrDie()
	{
		super(10734, Q10734_DoOrDie.class.getSimpleName(), "Do or Die");
		addStartNpc(KATALIN, AYANTHE);
		addTalkId(KATALIN, AYANTHE, ADVENTURER_S_GUIDE_APPRENTICE);
		addKillId(TRAINING_DUMMY);
		addCondMaxLevel(MAX_LEVEL, "33942-08.htm");
		addCondRace(Race.ERTHEIA, "33942-08.htm");
		addCondCompletedQuest(Q10733_TheTestForSurvival.class.getSimpleName(), "33942-08.htm");
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
			case "33943-03.htm":
			case "33942-03.htm":
			{
				qs.startQuest();
				showOnScreenMsg(player, NpcStringId.ATTACK_THE_TRAINING_DUMMY, ExShowScreenMessage.TOP_CENTER, 4500);
				htmltext = event;
				break;
			}
			case "other_buffs":
			{
				if (player.isMageClass())
				{
					htmltext = "33950-03.htm";
				}
				else
				{
					htmltext = "33950-05.htm";
				}
				
				player.sendPacket(new TutorialShowHtml(npc.getObjectId(), "..\\L2Text\\QT_002_Guide_01.htm", TutorialShowHtml.LARGE_WINDOW));
				break;
			}
			case "buffs":
			{
				if (qs.isCond(4) || qs.isCond(5))
				{
					showOnScreenMsg(player, NpcStringId.ATTACK_THE_TRAINING_DUMMY, ExShowScreenMessage.TOP_CENTER, 4500);
					qs.setCond(6, true);
					
					for (SkillHolder skillHolder : COMMON_BUFFS)
					{
						npc.setTarget(player);
						npc.doCast(skillHolder.getSkill());
					}
					if (player.isMageClass())
					{
						htmltext = "33950-06.htm";
						npc.setTarget(player);
						npc.doCast(WIZARD_HARMONY.getSkill());
					}
					else
					{
						htmltext = "33950-04.htm";
						npc.setTarget(player);
						npc.doCast(WARRIOR_HARMONY.getSkill());
					}
				}
				break;
			}
			case "33943-02.htm":
			case "33942-02.htm":
			case "33950-02.htm":
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
		QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		
		if (qs.isCompleted())
		{
			htmltext = getAlreadyCompletedMsg(player);
		}
		
		switch (npc.getId())
		{
			case KATALIN:
			{
				switch (qs.getCond())
				{
					case 0:
					{
						if (!player.isMageClass())
						{
							htmltext = "33943-01.htm";
						}
						else
						{
							htmltext = "33943-08.htm";
						}
						break;
					}
					case 1:
					{
						if (!player.isMageClass())
						{
							htmltext = "33943-04.htm";
						}
						break;
					}
					case 3:
					{
						showOnScreenMsg(player, NpcStringId.TALK_TO_THE_APPRENTICE_ADVENTURER_S_GUIDE, ExShowScreenMessage.TOP_CENTER, 4500);
						qs.setCond(5, true);
						htmltext = "33943-05.htm";
						break;
					}
					case 5:
					{
						htmltext = "33943-06.htm";
						break;
					}
					case 8:
					{
						giveAdena(player, 7000, true);
						addExpAndSp(player, 805, 2);
						qs.exitQuest(false, true);
						htmltext = "33943-07.htm";
						break;
					}
				}
				break;
			}
			case AYANTHE:
			{
				switch (qs.getCond())
				{
					case 0:
					{
						if (player.isMageClass())
						{
							htmltext = "33942-01.htm";
						}
						else
						{
							htmltext = "33942-08.htm";
						}
						break;
					}
					case 1:
					{
						if (player.isMageClass())
						{
							htmltext = "33942-04.htm";
						}
						break;
					}
					case 2:
					{
						showOnScreenMsg(player, NpcStringId.TALK_TO_THE_APPRENTICE_ADVENTURER_S_GUIDE, ExShowScreenMessage.TOP_CENTER, 4500);
						qs.setCond(4, true);
						htmltext = "33942-05.htm";
						break;
					}
					case 4:
					{
						htmltext = "33942-06.htm";
						break;
					}
					case 7:
					{
						giveAdena(player, 7000, true);
						addExpAndSp(player, 805, 2);
						qs.exitQuest(false, true);
						htmltext = "33942-07.htm";
						break;
					}
				}
				break;
			}
			case ADVENTURER_S_GUIDE_APPRENTICE:
			{
				switch (qs.getCond())
				{
					case 4:
					case 5:
					{
						htmltext = "33950-01.htm";
						break;
					}
					case 6:
					{
						if (player.isMageClass())
						{
							htmltext = "33950-06.htm";
						}
						else
						{
							htmltext = "33950-04.htm";
						}
						break;
					}
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState qs = getQuestState(killer, false);
		if (qs == null)
		{
			return super.onKill(npc, killer, isSummon);
		}
		if (npc.getId() == TRAINING_DUMMY)
		{
			if (qs.isCond(1))
			{
				if (killer.isMageClass())
				{
					qs.setCond(2, true);
				}
				else
				{
					qs.setCond(3, true);
				}
			}
			else if (qs.isCond(6))
			{
				if (killer.isMageClass())
				{
					qs.setCond(7, true);
				}
				else
				{
					qs.setCond(8, true);
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
}
