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
package quests.Q00136_MoreThanMeetsTheEye;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * More Than Meets the Eye (136)
 * @author malyelfik
 */
public class Q00136_MoreThanMeetsTheEye extends Quest
{
	// NPCs
	private static final int HARDIN = 30832;
	private static final int ERRICKIN = 30701;
	private static final int CLAYTON = 30464;
	
	// Monsters
	private static final int GLASS_JAGUAR = 20250;
	private static final int GHOST1 = 20636;
	private static final int GHOST2 = 20637;
	private static final int GHOST3 = 20638;
	private static final int MIRROR = 20639;
	
	// Items
	private static final int ECTOPLASM = 9787;
	private static final int STABILIZED_ECTOPLASM = 9786;
	private static final int ORDER = 9788;
	private static final int GLASS_JAGUAR_CRYSTAL = 9789;
	private static final int BOOK_OF_SEAL = 9790;
	private static final int TRANSFORM_BOOK = 9648;
	
	// Misc
	private static final int MIN_LEVEL = 50;
	private static final int ECTOPLASM_COUNT = 35;
	private static final int CRYSTAL_COUNT = 5;
	private static final int[] CHANCES =
	{
		0,
		40,
		90,
		290
	};
	
	public Q00136_MoreThanMeetsTheEye(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(HARDIN);
		addTalkId(HARDIN, ERRICKIN, CLAYTON);
		addKillId(GHOST1, GHOST2, GHOST3, GLASS_JAGUAR, MIRROR);
		
		registerQuestItems(ECTOPLASM, STABILIZED_ECTOPLASM, ORDER, GLASS_JAGUAR_CRYSTAL, BOOK_OF_SEAL);
	}
	
	private void giveItem(QuestState st, int itemId, int count, int maxCount, int cond)
	{
		st.giveItems(itemId, count);
		if (st.getQuestItemsCount(itemId) >= maxCount)
		{
			st.setCond(cond, true);
		}
		else
		{
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		String htmltext = event;
		switch (event)
		{
			case "30832-05.html":
			case "30832-06.html":
			case "30832-12.html":
			case "30832-13.html":
			case "30832-18.html":
				break;
			case "30832-03.htm":
				st.startQuest();
				break;
			case "30832-07.html":
				st.setCond(2, true);
				break;
			case "30832-11.html":
				st.set("talked", "2");
				break;
			case "30832-14.html":
				st.unset("talked");
				st.giveItems(ORDER, 1);
				st.setCond(6, true);
				break;
			case "30832-17.html":
				st.set("talked", "2");
				break;
			case "30832-19.html":
				st.giveItems(TRANSFORM_BOOK, 1);
				st.giveAdena(67550, true);
				st.exitQuest(false, true);
				break;
			case "30701-03.html":
				st.setCond(3, true);
				break;
			case "30464-03.html":
				st.takeItems(ORDER, -1);
				st.setCond(7, true);
				break;
			default:
				htmltext = null;
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final QuestState st = killer.getQuestState(getName());
		if (st == null)
		{
			return super.onKill(npc, killer, isSummon);
		}
		
		final int npcId = npc.getNpcId();
		if ((npcId != GLASS_JAGUAR) && st.isCond(3))
		{
			final int count = ((npcId == MIRROR) && ((st.getQuestItemsCount(ECTOPLASM) + 2) < ECTOPLASM_COUNT)) ? 2 : 1;
			final int index = npcId - GHOST1;
			
			if ((getRandom(1000) < CHANCES[index]) && ((st.getQuestItemsCount(ECTOPLASM) + count) < ECTOPLASM_COUNT))
			{
				st.giveItems(ECTOPLASM, 1);
			}
			giveItem(st, ECTOPLASM, count, ECTOPLASM_COUNT, 4);
		}
		else if ((npcId == GLASS_JAGUAR) && st.isCond(7))
		{
			giveItem(st, GLASS_JAGUAR_CRYSTAL, 1, CRYSTAL_COUNT, 8);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getNpcId())
		{
			case HARDIN:
				switch (st.getState())
				{
					case State.CREATED:
						htmltext = (player.getLevel() >= MIN_LEVEL) ? "30832-01.htm" : "30832-02.htm";
						break;
					case State.STARTED:
						switch (st.getCond())
						{
							case 1:
								htmltext = "30832-04.html";
								break;
							case 2:
							case 3:
							case 4:
								htmltext = "30832-08.html";
								break;
							case 5:
								if (st.getInt("talked") == 1)
								{
									htmltext = "30832-10.html";
								}
								else if (st.getInt("talked") == 2)
								{
									htmltext = "30832-12.html";
								}
								else if (st.hasQuestItems(STABILIZED_ECTOPLASM))
								{
									st.takeItems(STABILIZED_ECTOPLASM, -1);
									st.set("talked", "1");
									htmltext = "30832-09.html";
								}
								else
								{
									htmltext = "30832-08.html";
								}
								break;
							case 6:
							case 7:
							case 8:
								htmltext = "30832-15.html";
								break;
							case 9:
								if (st.getInt("talked") == 1)
								{
									st.set("talked", "2");
									htmltext = "30832-17.html";
								}
								else if (st.getInt("talked") == 2)
								{
									htmltext = "30832-18.html";
								}
								else
								{
									st.takeItems(BOOK_OF_SEAL, -1);
									st.set("talked", "1");
									htmltext = "30832-16.html";
								}
								break;
						}
						break;
					case State.COMPLETED:
						htmltext = getAlreadyCompletedMsg(player);
						break;
				}
				break;
			case ERRICKIN:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
							htmltext = "30701-01.html";
							break;
						case 2:
							htmltext = "30701-02.html";
							break;
						case 3:
							htmltext = "30701-04.html";
							break;
						case 4:
							if (st.getQuestItemsCount(ECTOPLASM) < ECTOPLASM_COUNT)
							{
								st.giveItems(STABILIZED_ECTOPLASM, 1);
								st.setCond(5, true);
								htmltext = "30701-06.html";
							}
							else
							{
								st.takeItems(ECTOPLASM, -1);
								htmltext = "30701-05.html";
							}
							break;
						default:
							htmltext = "30701-07.html";
							break;
					}
				}
				break;
			case CLAYTON:
				if (st.isStarted())
				{
					switch (st.getCond())
					{
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
							htmltext = "30464-01.html";
							break;
						case 6:
							htmltext = "30464-02.html";
							break;
						case 7:
							htmltext = "30464-04.html";
							break;
						case 8:
							st.giveItems(BOOK_OF_SEAL, 1);
							st.takeItems(GLASS_JAGUAR_CRYSTAL, -1);
							st.setCond(9, true);
							htmltext = "30464-05.html";
							break;
						default:
							htmltext = "30464-06.html";
							break;
					}
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00136_MoreThanMeetsTheEye(136, Q00136_MoreThanMeetsTheEye.class.getSimpleName(), "More Than Meets the Eye");
	}
}