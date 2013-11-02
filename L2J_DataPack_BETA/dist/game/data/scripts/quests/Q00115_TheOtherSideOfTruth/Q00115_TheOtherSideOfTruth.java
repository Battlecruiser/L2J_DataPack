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
package quests.Q00115_TheOtherSideOfTruth;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * The Other Side of Truth (115)
 * @author Adry_85
 */
public class Q00115_TheOtherSideOfTruth extends Quest
{
	// NPCs
	private static final int MISA = 32018;
	private static final int RAFFORTY = 32020;
	private static final int ICE_SCULPTURE1 = 32021;
	private static final int KIER = 32022;
	private static final int ICE_SCULPTURE2 = 32077;
	private static final int ICE_SCULPTURE3 = 32078;
	private static final int ICE_SCULPTURE4 = 32079;
	// Items
	private static final int MISAS_LETTER = 8079;
	private static final int RAFFORTYS_LETTER = 8080;
	private static final int PIECE_OF_TABLET = 8081;
	private static final int REPORT_PIECE = 8082;
	// Misc
	private static final int MIN_LEVEL = 53;
	
	public Q00115_TheOtherSideOfTruth(int id, String name, String descr)
	{
		super(id, name, descr);
		addStartNpc(RAFFORTY);
		addTalkId(RAFFORTY, MISA, KIER, ICE_SCULPTURE1, ICE_SCULPTURE2, ICE_SCULPTURE3, ICE_SCULPTURE4);
		registerQuestItems(MISAS_LETTER, RAFFORTYS_LETTER, PIECE_OF_TABLET, REPORT_PIECE);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
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
				htmltext = event;
				break;
			}
			case "32020-07.html":
			{
				if (st.isCond(2))
				{
					takeItems(player, MISAS_LETTER, -1);
					st.setCond(3, true);
					htmltext = event;
				}
				break;
			}
			case "32020-05.html":
			{
				if (st.isCond(2))
				{
					takeItems(player, MISAS_LETTER, -1);
					st.exitQuest(true, true);
					htmltext = event;
				}
				break;
			}
			case "32020-10.html":
			{
				if (st.isCond(3))
				{
					st.setCond(4, true);
					htmltext = event;
				}
				break;
			}
			case "32020-11.html":
			{
				if (st.isCond(3))
				{
					st.setCond(4, true);
					htmltext = event;
				}
				break;
			}
			case "32020-12.html":
			{
				if (st.isCond(3))
				{
					st.exitQuest(true, true);
					htmltext = event;
				}
				break;
			}
			case "32020-08.html":
			case "32020-09.html":
			case "32020-13.html":
			case "32020-14.html":
			{
				htmltext = event;
				break;
			}
			case "32020-15.html":
			{
				if (st.isCond(4))
				{
					st.setCond(5, true);
					st.playSound(QuestSound.AMBSOUND_WINGFLAP);
					htmltext = event;
				}
				break;
			}
			case "32020-23.html":
			{
				if (st.isCond(9))
				{
					st.setCond(10, true);
					htmltext = event;
				}
				break;
			}
			case "finish":
			{
				if (st.isCond(10))
				{
					if (st.hasQuestItems(PIECE_OF_TABLET))
					{
						st.giveAdena(115673, true);
						st.addExpAndSp(493595, 40442);
						st.exitQuest(false, true);
						htmltext = "32020-25.html";
					}
					else
					{
						st.setCond(11, true);
						htmltext = "32020-26.html";
						st.playSound(QuestSound.AMBSOUND_THUNDER);
					}
				}
				break;
			}
			case "finish2":
			{
				if (st.isCond(10))
				{
					if (st.hasQuestItems(PIECE_OF_TABLET))
					{
						st.giveAdena(115673, true);
						st.addExpAndSp(493595, 40442);
						st.exitQuest(false, true);
						htmltext = "32020-27.html";
					}
					else
					{
						st.setCond(11, true);
						htmltext = "32020-28.html";
						st.playSound(QuestSound.AMBSOUND_THUNDER);
					}
				}
				break;
			}
			case "32018-05.html":
			{
				if (st.isCond(6) && (st.hasQuestItems(RAFFORTYS_LETTER)))
				{
					takeItems(player, RAFFORTYS_LETTER, -1);
					st.setCond(7, true);
					htmltext = event;
				}
				break;
			}
			case "32022-02.html":
			{
				if (st.isCond(8))
				{
					giveItems(player, REPORT_PIECE, 1);
					st.setCond(9, true);
					htmltext = event;
				}
				break;
			}
			case "32021-02.html":
			{
				switch (npc.getId())
				{
					case ICE_SCULPTURE1:
					{
						if (st.isCond(7) && ((st.getInt("ex") % 2) <= 1))
						{
							int ex = st.getInt("ex");
							if ((ex == 6) || (ex == 10) || (ex == 12))
							{
								ex++;
								st.set("ex", ex);
								giveItems(player, PIECE_OF_TABLET, 1);
								htmltext = event;
							}
						}
						break;
					}
					case ICE_SCULPTURE2:
					{
						if (st.isCond(7) && ((st.getInt("ex") % 4) <= 1))
						{
							int ex = st.getInt("ex");
							if ((ex == 5) || (ex == 9) || (ex == 12))
							{
								ex += 2;
								st.set("ex", ex);
								giveItems(player, PIECE_OF_TABLET, 1);
								htmltext = event;
							}
						}
						break;
					}
					case ICE_SCULPTURE3:
					{
						if (st.isCond(7) && ((st.getInt("ex") % 8) <= 3))
						{
							int ex = st.getInt("ex");
							if ((ex == 3) || (ex == 9) || (ex == 10))
							{
								ex += 4;
								st.set("ex", ex);
								giveItems(player, PIECE_OF_TABLET, 1);
								htmltext = event;
							}
						}
						break;
					}
					case ICE_SCULPTURE4:
					{
						if (st.isCond(7) && (st.getInt("ex") <= 7))
						{
							int ex = st.getInt("ex");
							if ((ex == 3) || (ex == 5) || (ex == 6))
							{
								ex += 8;
								st.set("ex", ex);
								giveItems(player, PIECE_OF_TABLET, 1);
								htmltext = event;
							}
						}
						break;
					}
				}
				break;
			}
			case "32021-03.html":
			{
				switch (npc.getId())
				{
					case ICE_SCULPTURE1:
					{
						if (st.isCond(7) && ((st.getInt("ex") % 2) <= 1))
						{
							int ex = st.getInt("ex");
							if ((ex == 6) || (ex == 10) || (ex == 12))
							{
								ex++;
								st.set("ex", ex);
								htmltext = event;
							}
						}
						break;
					}
					case ICE_SCULPTURE2:
					{
						if (st.isCond(7) && ((st.getInt("ex") % 4) <= 1))
						{
							int ex = st.getInt("ex");
							if ((ex == 5) || (ex == 9) || (ex == 12))
							{
								ex += 2;
								st.set("ex", ex);
								htmltext = event;
							}
						}
						break;
					}
					case ICE_SCULPTURE3:
					{
						if (st.isCond(7) && ((st.getInt("ex") % 8) <= 3))
						{
							int ex = st.getInt("ex");
							if ((ex == 3) || (ex == 9) || (ex == 12))
							{
								ex += 4;
								st.set("ex", ex);
								htmltext = event;
							}
						}
						break;
					}
					case ICE_SCULPTURE4:
					{
						if (st.isCond(7) && (st.getInt("ex") <= 7))
						{
							int ex = st.getInt("ex");
							if ((ex == 3) || (ex == 5) || (ex == 6))
							{
								ex += 8;
								st.set("ex", ex);
								htmltext = event;
							}
						}
						break;
					}
				}
				break;
			}
			case "32021-06.html":
			{
				switch (npc.getId())
				{
					case ICE_SCULPTURE1:
					{
						if (st.isCond(7) && (st.getInt("ex") == 14))
						{
							st.setCond(8);
							htmltext = event;
						}
						break;
					}
					case ICE_SCULPTURE2:
					{
						if (st.isCond(7) && (st.getInt("ex") == 13))
						{
							st.setCond(8);
							htmltext = event;
						}
						break;
					}
					case ICE_SCULPTURE3:
					{
						if (st.isCond(7) && (st.getInt("ex") == 11))
						{
							st.setCond(8);
							htmltext = event;
						}
						break;
					}
					case ICE_SCULPTURE4:
					{
						if (st.isCond(7) && (st.getInt("ex") == 7))
						{
							st.setCond(8);
							htmltext = event;
						}
						break;
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
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.COMPLETED:
			{
				if (npc.getId() == RAFFORTY)
				{
					htmltext = getAlreadyCompletedMsg(player);
				}
				break;
			}
			case State.CREATED:
			{
				htmltext = (player.getLevel() >= MIN_LEVEL) ? "32020-01.htm" : "32020-03.html";
				break;
			}
			case State.STARTED:
			{
				switch (npc.getId())
				{
					case RAFFORTY:
					{
						switch (st.getCond())
						{
							case 1:
							{
								htmltext = "32020-04.html";
								break;
							}
							case 2:
							{
								htmltext = (!st.hasQuestItems(MISAS_LETTER)) ? "32020-05.html" : "32020-06.html";
								break;
							}
							case 3:
							{
								htmltext = "32020-16.html";
								break;
							}
							case 4:
							{
								htmltext = "32020-17.html";
								break;
							}
							case 5:
							{
								giveItems(player, RAFFORTYS_LETTER, 1);
								st.setCond(6, true);
								htmltext = "32020-18.html";
								break;
							}
							case 6:
							{
								if (st.hasQuestItems(RAFFORTYS_LETTER))
								{
									htmltext = "32020-19.html";
								}
								else
								{
									giveItems(player, RAFFORTYS_LETTER, 1);
									htmltext = "32020-20.html";
								}
								break;
							}
							case 7:
							case 8:
							{
								htmltext = "32020-21.html";
								break;
							}
							case 9:
							{
								if (st.hasQuestItems(REPORT_PIECE))
								{
									htmltext = "32020-22.html";
								}
								break;
							}
							case 10:
							{
								htmltext = "32020-24.html";
								break;
							}
							case 11:
							{
								if (!st.hasQuestItems(PIECE_OF_TABLET))
								{
									htmltext = "32020-29.html";
								}
								else
								{
									st.giveAdena(115673, true);
									st.addExpAndSp(493595, 40442);
									st.exitQuest(false, true);
									htmltext = "32020-30.html";
								}
								break;
							}
						}
						break;
					}
					case MISA:
					{
						switch (st.getCond())
						{
							case 1:
							{
								giveItems(player, MISAS_LETTER, 1);
								st.setCond(2, true);
								htmltext = "32018-01.html";
								break;
							}
							case 2:
							{
								htmltext = "32018-02.html";
								break;
							}
							case 3:
							case 4:
							{
								htmltext = "32018-03.html";
								break;
							}
							case 5:
							{
								break;
							}
							case 6:
							{
								if (st.hasQuestItems(RAFFORTYS_LETTER))
								{
									htmltext = "32018-04.html";
								}
								break;
							}
							case 7:
							{
								htmltext = "32018-06.html";
								break;
							}
						}
						break;
					}
					case KIER:
					{
						switch (st.getCond())
						{
							case 8:
							{
								htmltext = "32022-01.html";
								break;
							}
							case 9:
							{
								if (st.hasQuestItems(REPORT_PIECE))
								{
									htmltext = "32022-03.html";
								}
								else
								{
									giveItems(player, REPORT_PIECE, 1);
									htmltext = "32022-04.html";
								}
								break;
							}
							case 11:
							{
								if (!st.hasQuestItems(REPORT_PIECE))
								{
									htmltext = "32022-05.html";
								}
								break;
							}
						}
						break;
					}
					case ICE_SCULPTURE1:
					{
						switch (st.getCond())
						{
							case 7:
							{
								if ((st.getInt("ex") % 2) <= 1)
								{
									int ex = st.getInt("ex");
									if ((ex == 6) || (ex == 10) || (ex == 12))
									{
										htmltext = "32021-01.html";
									}
									else if (ex == 14)
									{
										htmltext = "32021-05.html";
									}
									else
									{
										ex++;
										st.set("ex", ex);
										htmltext = "32021-07.html";
									}
								}
								else
								{
									htmltext = "32021-04.html";
								}
								break;
							}
							case 8:
							{
								htmltext = "32021-08.html";
								break;
							}
							case 11:
							{
								if (!st.hasQuestItems(PIECE_OF_TABLET))
								{
									giveItems(player, PIECE_OF_TABLET, 1);
									htmltext = "32021-09.html";
								}
								else
								{
									htmltext = "32021-10.html";
								}
								break;
							}
						}
						break;
					}
					case ICE_SCULPTURE2:
					{
						switch (st.getCond())
						{
							case 7:
							{
								if ((st.getInt("ex") % 4) <= 1)
								{
									int ex = st.getInt("ex");
									if ((ex == 5) || (ex == 9) || (ex == 12))
									{
										htmltext = "32021-01.html";
									}
									else if (ex == 13)
									{
										htmltext = "32021-05.html";
									}
									else
									{
										ex += 2;
										st.set("ex", ex);
										htmltext = "32021-07.html";
									}
								}
								else
								{
									htmltext = "32021-04.html";
								}
								break;
							}
							case 8:
							{
								htmltext = "32021-08.html";
								break;
							}
							case 11:
							{
								if (!st.hasQuestItems(PIECE_OF_TABLET))
								{
									giveItems(player, PIECE_OF_TABLET, 1);
									htmltext = "32021-09.html";
								}
								else
								{
									htmltext = "32021-10.html";
								}
								break;
							}
						}
						break;
					}
					case ICE_SCULPTURE3:
					{
						switch (st.getCond())
						{
							case 7:
							{
								if ((st.getInt("ex") % 8) <= 3)
								{
									int ex = st.getInt("ex");
									if ((ex == 3) || (ex == 9) || (ex == 10))
									{
										htmltext = "32021-01.html";
									}
									else if (ex == 11)
									{
										htmltext = "32021-05.html";
									}
									else
									{
										ex += 4;
										st.set("ex", ex);
										htmltext = "32021-07.html";
									}
								}
								else
								{
									htmltext = "32021-04.html";
								}
								break;
							}
							case 8:
							{
								htmltext = "32021-08.html";
								break;
							}
							case 11:
							{
								if (!st.hasQuestItems(PIECE_OF_TABLET))
								{
									giveItems(player, PIECE_OF_TABLET, 1);
									htmltext = "32021-09.html";
								}
								else
								{
									htmltext = "32021-10.html";
								}
								break;
							}
						}
						break;
					}
					case ICE_SCULPTURE4:
					{
						switch (st.getCond())
						{
							case 7:
							{
								if (st.getInt("ex") <= 7)
								{
									int ex = st.getInt("ex");
									if ((ex == 3) || (ex == 5) || (ex == 6))
									{
										htmltext = "32021-01.html";
									}
									else if (ex == 7)
									{
										htmltext = "32021-05.html";
									}
									else
									{
										ex += 8;
										st.set("ex", ex);
										htmltext = "32021-07.html";
									}
								}
								else
								{
									htmltext = "32021-04.html";
								}
								break;
							}
							case 8:
							{
								htmltext = "32021-08.html";
								break;
							}
							case 11:
							{
								if (!st.hasQuestItems(PIECE_OF_TABLET))
								{
									giveItems(player, PIECE_OF_TABLET, 1);
									htmltext = "32021-09.html";
								}
								else
								{
									htmltext = "32021-10.html";
								}
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
	
	public static void main(String[] args)
	{
		new Q00115_TheOtherSideOfTruth(115, Q00115_TheOtherSideOfTruth.class.getSimpleName(), "The Other Side of Truth");
	}
}
