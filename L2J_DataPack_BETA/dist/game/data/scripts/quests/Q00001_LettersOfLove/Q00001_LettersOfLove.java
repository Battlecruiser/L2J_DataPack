/*
 * Copyright (C) 2004-2012 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00001_LettersOfLove;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.NpcStringId;

/**
 * Letters of Love (1)
 * @author Zoey76
 */
public class Q00001_LettersOfLove extends Quest
{
	// NPC
	private static final int DARIN = 30048;
	private static final int ROXXY = 30006;
	private static final int BAULRO = 30033;
	// Items
	private static final int DARINS_LETTER = 687;
	private static final int ROXXYS_KERCHIEF = 688;
	private static final int DARINS_RECEIPT = 1079;
	private static final int BAULROS_POTION = 1080;
	private static final int NECKLACE_OF_KNOWLEDGE = 906;
	// Misc
	private static final int MIN_LEVEL = 2;
	
	private Q00001_LettersOfLove(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(DARIN);
		addTalkId(DARIN, ROXXY, BAULRO);
		registerQuestItems(DARINS_LETTER, ROXXYS_KERCHIEF, DARINS_RECEIPT, BAULROS_POTION);
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
			case "30048-03.html":
			case "30048-04.html":
			case "30048-05.html":
			{
				htmltext = event;
				break;
			}
			case "30048-06.htm":
			{
				if (player.getLevel() >= MIN_LEVEL)
				{
					st.startQuest();
					st.giveItems(DARINS_LETTER, 1);
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
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = getNoQuestMsg(player);
		switch (st.getState())
		{
			case State.CREATED:
			{
				htmltext = (player.getLevel() < MIN_LEVEL) ? "30048-01.html" : "30048-02.html";
				break;
			}
			case State.STARTED:
			{
				switch (st.getCond())
				{
					case 1:
					{
						switch (npc.getNpcId())
						{
							case DARIN:
							{
								htmltext = "30048-07.html";
								break;
							}
							case ROXXY:
							{
								if (st.hasQuestItems(DARINS_LETTER) && !st.hasQuestItems(ROXXYS_KERCHIEF))
								{
									st.takeItems(DARINS_LETTER, -1);
									st.giveItems(ROXXYS_KERCHIEF, 1);
									st.setCond(2, true);
									htmltext = "30006-01.html";
								}
								break;
							}
						}
						break;
					}
					case 2:
					{
						switch (npc.getNpcId())
						{
							case DARIN:
							{
								if (st.hasQuestItems(ROXXYS_KERCHIEF))
								{
									st.takeItems(ROXXYS_KERCHIEF, -1);
									st.giveItems(DARINS_RECEIPT, 1);
									st.setCond(3, true);
									htmltext = "30048-08.html";
								}
								break;
							}
							case ROXXY:
							{
								if (st.hasQuestItems(ROXXYS_KERCHIEF))
								{
									htmltext = "30006-02.html";
								}
								break;
							}
						}
						break;
					}
					case 3:
					{
						switch (npc.getNpcId())
						{
							case DARIN:
							{
								if (st.hasQuestItems(DARINS_RECEIPT) || !st.hasQuestItems(BAULROS_POTION))
								{
									htmltext = "30048-09.html";
								}
								break;
							}
							case ROXXY:
							{
								if (st.hasQuestItems(DARINS_RECEIPT) || st.hasQuestItems(BAULROS_POTION))
								{
									htmltext = "30006-03.html";
								}
								break;
							}
							case BAULRO:
							{
								if (st.hasQuestItems(DARINS_RECEIPT))
								{
									st.takeItems(DARINS_RECEIPT, -1);
									st.giveItems(BAULROS_POTION, 1);
									st.setCond(4, true);
									htmltext = "30033-01.html";
								}
								else if (st.hasQuestItems(BAULROS_POTION))
								{
									htmltext = "30033-02.html";
								}
								break;
							}
						}
						break;
					}
					case 4:
					{
						switch (npc.getNpcId())
						{
							case DARIN:
							{
								// TODO: Beside this message something should be set for the Newbie Guide.
								showOnScreenMsg(player, NpcStringId.DELIVERY_DUTY_COMPLETE_N_GO_FIND_THE_NEWBIE_GUIDE, 2, 5000);
								st.giveItems(NECKLACE_OF_KNOWLEDGE, 1);
								st.addExpAndSp(5672, 446);
								st.giveAdena(2466, false);
								st.exitQuest(false, true);
								htmltext = "30048-10.html";
								break;
							}
							case BAULRO:
							{
								if (st.hasQuestItems(BAULROS_POTION))
								{
									htmltext = "30033-02.html";
								}
								break;
							}
							case ROXXY:
							{
								if (st.hasQuestItems(BAULROS_POTION))
								{
									htmltext = "30006-03.html";
								}
								break;
							}
						}
						break;
					}
				}
				break;
			}
			case State.COMPLETED:
			{
				htmltext = getAlreadyCompletedMsg(player);
				break;
			}
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00001_LettersOfLove(1, Q00001_LettersOfLove.class.getSimpleName(), "Letters of Love");
	}
}
