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
package quests.Q00194_SevenSignsMammonsContract;

import quests.Q00193_SevenSignsDyingMessage.Q00193_SevenSignsDyingMessage;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Seven Signs, Mammon's Contract (194)
 * @author Adry_85
 */
public final class Q00194_SevenSignsMammonsContract extends Quest
{
	// NPCs
	private static final int SIR_GUSTAV_ATHEBALDT = 30760;
	private static final int CLAUDIA_ATHEBALDT = 31001;
	private static final int COLIN = 32571;
	private static final int FROG = 32572;
	private static final int TESS = 32573;
	private static final int KUTA = 32574;
	// Items
	private static final int ATHEBALDTS_INTRODUCTION = 13818;
	private static final int NATIVES_GLOVE = 13819;
	private static final int FROG_KINGS_BEAD = 13820;
	private static final int GRANDA_TESS_CANDY_POUCH = 13821;
	// Misc
	private static final int MIN_LEVEL = 79;
	// Skills
	private static SkillHolder TRANSFORMATION_FROG = new SkillHolder(6201, 1);
	private static SkillHolder TRANSFORMATION_KID = new SkillHolder(6202, 1);
	private static SkillHolder TRANSFORMATION_NATIVE = new SkillHolder(6203, 1);
	
	private Q00194_SevenSignsMammonsContract()
	{
		super(194, Q00194_SevenSignsMammonsContract.class.getSimpleName(), "Seven Signs, Mammon's Contract");
		addStartNpc(SIR_GUSTAV_ATHEBALDT);
		addTalkId(SIR_GUSTAV_ATHEBALDT, COLIN, FROG, TESS, KUTA, CLAUDIA_ATHEBALDT);
		registerQuestItems(ATHEBALDTS_INTRODUCTION, NATIVES_GLOVE, FROG_KINGS_BEAD, GRANDA_TESS_CANDY_POUCH);
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
			case "30760-02.html":
			{
				st.startQuest();
				htmltext = event;
				break;
			}
			case "30760-03.html":
			{
				if (st.isCond(1))
				{
					htmltext = event;
				}
				break;
			}
			case "30760-04.html":
			{
				if (st.isCond(1))
				{
					htmltext = event;
					st.playSound(QuestSound.ITEMSOUND_QUEST_MIDDLE);
				}
				break;
			}
			case "showmovie":
			{
				if (st.isCond(1))
				{
					st.setCond(2, true);
					player.showQuestMovie(10);
					return "";
				}
				break;
			}
			case "30760-07.html":
			{
				if (st.isCond(2))
				{
					st.giveItems(ATHEBALDTS_INTRODUCTION, 1);
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "32571-03.html":
			case "32571-04.html":
			{
				if (st.isCond(3) && st.hasQuestItems(ATHEBALDTS_INTRODUCTION))
				{
					htmltext = event;
				}
				break;
			}
			case "32571-05.html":
			{
				if (st.isCond(3) && st.hasQuestItems(ATHEBALDTS_INTRODUCTION))
				{
					st.takeItems(ATHEBALDTS_INTRODUCTION, -1);
					npc.setTarget(player);
					npc.doCast(TRANSFORMATION_FROG.getSkill());
					st.setCond(4, true);
					htmltext = event;
				}
				break;
			}
			case "32571-07.html":
			{
				if (st.isCond(4) && (player.getTransformationId() != 111) && !st.hasQuestItems(FROG_KINGS_BEAD))
				{
					npc.setTarget(player);
					npc.doCast(TRANSFORMATION_FROG.getSkill());
					htmltext = event;
				}
				break;
			}
			case "32571-09.html":
			{
				if (st.isCond(4) && (player.getTransformationId() == 111) && !st.hasQuestItems(FROG_KINGS_BEAD))
				{
					player.stopAllEffects();
					htmltext = event;
				}
				break;
			}
			case "32571-11.html":
			{
				if (st.isCond(5) && st.hasQuestItems(FROG_KINGS_BEAD))
				{
					st.takeItems(FROG_KINGS_BEAD, -1);
					st.setCond(6, true);
					htmltext = event;
					if (player.getTransformationId() == 111)
					{
						player.stopAllEffects();
					}
				}
				break;
			}
			case "32571-13.html":
			{
				if (st.isCond(6))
				{
					npc.setTarget(player);
					npc.doCast(TRANSFORMATION_KID.getSkill());
					st.setCond(7, true);
					htmltext = event;
				}
				break;
			}
			case "32571-15.html":
			{
				if (st.isCond(7) && (player.getTransformationId() != 112) && !st.hasQuestItems(GRANDA_TESS_CANDY_POUCH))
				{
					npc.setTarget(player);
					npc.doCast(TRANSFORMATION_KID.getSkill());
					htmltext = event;
				}
				break;
			}
			case "32571-17.html":
			{
				if (st.isCond(7) && (player.getTransformationId() == 112) && !st.hasQuestItems(GRANDA_TESS_CANDY_POUCH))
				{
					player.stopAllEffects();
					htmltext = event;
				}
				break;
			}
			case "32571-19.html":
			{
				if (st.isCond(8) && st.hasQuestItems(GRANDA_TESS_CANDY_POUCH))
				{
					st.takeItems(GRANDA_TESS_CANDY_POUCH, -1);
					st.setCond(9, true);
					htmltext = event;
					if (player.getTransformationId() == 112)
					{
						player.stopAllEffects();
					}
				}
				break;
			}
			case "32571-21.html":
			{
				if (st.isCond(9))
				{
					npc.setTarget(player);
					npc.doCast(TRANSFORMATION_NATIVE.getSkill());
					st.setCond(10, true);
					htmltext = event;
				}
				break;
			}
			case "32571-23.html":
			{
				if (st.isCond(10) && (player.getTransformationId() != 124) && !st.hasQuestItems(NATIVES_GLOVE))
				{
					npc.setTarget(player);
					npc.doCast(TRANSFORMATION_NATIVE.getSkill());
					htmltext = event;
				}
				break;
			}
			case "32571-25.html":
			{
				if (st.isCond(10) && (player.getTransformationId() == 124) && !st.hasQuestItems(NATIVES_GLOVE))
				{
					player.stopAllEffects();
					htmltext = event;
				}
				break;
			}
			case "32571-27.html":
			{
				if (st.isCond(11) && st.hasQuestItems(NATIVES_GLOVE))
				{
					st.takeItems(NATIVES_GLOVE, -1);
					st.setCond(12, true);
					htmltext = event;
					if (player.getTransformationId() == 124)
					{
						player.stopAllEffects();
					}
				}
				break;
			}
			case "32572-03.html":
			case "32572-04.html":
			{
				if (st.isCond(4))
				{
					htmltext = event;
				}
				break;
			}
			case "32572-05.html":
			{
				if (st.isCond(4))
				{
					st.giveItems(FROG_KINGS_BEAD, 1);
					st.setCond(5, true);
					htmltext = event;
				}
				break;
			}
			case "32573-03.html":
			{
				if (st.isCond(7))
				{
					htmltext = event;
				}
				break;
			}
			case "32573-04.html":
			{
				if (st.isCond(7))
				{
					st.giveItems(GRANDA_TESS_CANDY_POUCH, 1);
					st.setCond(8, true);
					htmltext = event;
				}
				break;
			}
			case "32574-03.html":
			case "32574-04.html":
			{
				if (st.isCond(10))
				{
					htmltext = event;
				}
				break;
			}
			case "32574-05.html":
			{
				if (st.isCond(10))
				{
					st.giveItems(NATIVES_GLOVE, 1);
					st.setCond(11, true);
					htmltext = event;
				}
				break;
			}
			case "31001-02.html":
			{
				if (st.isCond(12))
				{
					htmltext = event;
				}
				break;
			}
			case "31001-03.html":
			{
				if (st.isCond(12))
				{
					if (player.getLevel() >= MIN_LEVEL)
					{
						st.addExpAndSp(52518015, 5817677);
						st.exitQuest(false, true);
						htmltext = event;
					}
					else
					{
						htmltext = "level_check.html";
					}
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
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
			case State.CREATED:
			{
				if (npc.getId() == SIR_GUSTAV_ATHEBALDT)
				{
					st = player.getQuestState(Q00193_SevenSignsDyingMessage.class.getSimpleName());
					htmltext = ((player.getLevel() >= MIN_LEVEL) && (st != null) && st.isCompleted()) ? "30760-01.htm" : "30760-05.html";
				}
				break;
			}
			case State.STARTED:
			{
				switch (npc.getId())
				{
					case SIR_GUSTAV_ATHEBALDT:
					{
						if (st.isCond(1))
						{
							htmltext = "30760-02.html";
						}
						else if (st.isCond(2))
						{
							htmltext = "30760-06.html";
						}
						else if (st.isCond(3) && st.hasQuestItems(ATHEBALDTS_INTRODUCTION))
						{
							htmltext = "30760-08.html";
						}
						break;
					}
					case COLIN:
					{
						switch (st.getCond())
						{
							case 1:
							case 2:
							{
								htmltext = "32571-01.html";
								break;
							}
							case 3:
							{
								if (st.hasQuestItems(ATHEBALDTS_INTRODUCTION))
								{
									htmltext = "32571-02.html";
								}
								break;
							}
							case 4:
							{
								if (!st.hasQuestItems(FROG_KINGS_BEAD))
								{
									htmltext = (player.getTransformationId() != 111) ? "32571-06.html" : "32571-08.html";
								}
								break;
							}
							case 5:
							{
								if (st.hasQuestItems(FROG_KINGS_BEAD))
								{
									htmltext = "32571-10.html";
								}
								break;
							}
							case 6:
							{
								htmltext = "32571-12.html";
								break;
							}
							case 7:
							{
								if (!st.hasQuestItems(GRANDA_TESS_CANDY_POUCH))
								{
									htmltext = (player.getTransformationId() != 112) ? "32571-14.html" : "32571-16.html";
								}
								break;
							}
							case 8:
							{
								if (st.hasQuestItems(GRANDA_TESS_CANDY_POUCH))
								{
									htmltext = "32571-18.html";
								}
								break;
							}
							case 9:
							{
								htmltext = "32571-20.html";
								break;
							}
							case 10:
							{
								if (!st.hasQuestItems(NATIVES_GLOVE))
								{
									htmltext = (player.getTransformationId() != 124) ? "32571-22.html" : "32571-24.html";
								}
								break;
							}
							case 11:
							{
								if (st.hasQuestItems(NATIVES_GLOVE))
								{
									htmltext = "32571-26.html";
								}
								break;
							}
							case 12:
							{
								htmltext = "32571-28.html";
								break;
							}
						}
						break;
					}
					case FROG:
					{
						switch (st.getCond())
						{
							case 1:
							case 2:
							case 3:
							{
								htmltext = "32572-01.html";
								break;
							}
							case 4:
							{
								htmltext = (player.getTransformationId() == 111) ? "32572-02.html" : "32572-06.html";
								break;
							}
							case 5:
							{
								if (st.hasQuestItems(FROG_KINGS_BEAD) && (player.getTransformationId() == 111))
								{
									htmltext = "32572-07.html";
								}
								break;
							}
						}
						break;
					}
					case TESS:
					{
						switch (st.getCond())
						{
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							{
								htmltext = "32573-01.html";
								break;
							}
							case 7:
							{
								htmltext = (player.getTransformationId() == 112) ? "32573-02.html" : "32573-05.html";
								break;
							}
							case 8:
							{
								if (st.hasQuestItems(GRANDA_TESS_CANDY_POUCH) && (player.getTransformationId() == 112))
								{
									htmltext = "32573-06.html";
								}
								break;
							}
						}
						break;
					}
					case KUTA:
					{
						switch (st.getCond())
						{
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 7:
							case 8:
							case 9:
							{
								htmltext = "32574-01.html";
								break;
							}
							case 10:
							{
								htmltext = (player.getTransformationId() == 124) ? "32574-02.html" : "32574-06.html";
								break;
							}
							case 11:
							{
								if (st.hasQuestItems(NATIVES_GLOVE) && (player.getTransformationId() == 124))
								{
									htmltext = "32574-07.html";
								}
								break;
							}
						}
						break;
					}
					case CLAUDIA_ATHEBALDT:
					{
						if (st.isCond(12))
						{
							htmltext = "31001-01.html";
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
		new Q00194_SevenSignsMammonsContract();
	}
}
