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
package quests.Q00401_PathToWarrior;

import com.l2jserver.gameserver.enums.QuestSound;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;

/**
 * Path of the Warrior (401)<br>
 * Original Jython script by ElgarL, Mr
 * @author malyelfik
 * @version 2010-12-29 (Freya)
 */
public class Q00401_PathToWarrior extends Quest
{
	// Items
	private static final int AURONSLETTER = 1138;
	private static final int WARRIORGUILDMARK = 1139;
	private static final int RUSTEDBRONZESWORD1 = 1140;
	private static final int RUSTEDBRONZESWORD2 = 1141;
	private static final int RUSTEDBRONZESWORD3 = 1142;
	private static final int SIMPLONSLETTER = 1143;
	private static final int POISONSPIDERLEG = 1144;
	private static final int MEDALLIONOFWARRIOR = 1145;
	// NPCs
	private static final int AURON = 30010;
	private static final int SIMPLON = 30253;
	private static final int[] MONSTERS =
	{
		20035,
		20038,
		20042,
		20043
	};
	
	public Q00401_PathToWarrior(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(AURON);
		addTalkId(AURON, SIMPLON);
		addKillId(MONSTERS);
		registerQuestItems(AURONSLETTER, WARRIORGUILDMARK, RUSTEDBRONZESWORD1, RUSTEDBRONZESWORD2, RUSTEDBRONZESWORD3, SIMPLONSLETTER, POISONSPIDERLEG);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("401_1"))
		{
			switch (player.getClassId())
			{
				case fighter:
				{
					if (player.getLevel() >= 18)
					{
						if (st.getQuestItemsCount(MEDALLIONOFWARRIOR) == 1)
						{
							htmltext = "30010-04.htm";
						}
						else
						{
							htmltext = "30010-05.htm";
						}
					}
					else
					{
						htmltext = "30010-02.htm";
					}
					break;
				}
				case warrior:
				{
					htmltext = "30010-03.htm";
					break;
				}
				default:
				{
					htmltext = "30010-02b.htm";
				}
			}
		}
		else if (event.equalsIgnoreCase("401_accept"))
		{
			st.startQuest();
			st.giveItems(AURONSLETTER, 1);
			htmltext = "30010-06.htm";
		}
		else if (event.equalsIgnoreCase("30253_1"))
		{
			st.setCond(2, true);
			st.takeItems(AURONSLETTER, 1);
			st.giveItems(WARRIORGUILDMARK, 1);
			htmltext = "30253-02.html";
		}
		else if (event.equalsIgnoreCase("401_2"))
		{
			htmltext = "30010-10.html";
		}
		else if (event.equalsIgnoreCase("401_3"))
		{
			st.setCond(5, true);
			st.takeItems(RUSTEDBRONZESWORD2, 1);
			st.giveItems(RUSTEDBRONZESWORD3, 1);
			st.takeItems(SIMPLONSLETTER, 1);
			htmltext = "30010-11.html";
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		QuestState st = killer.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		switch (st.getCond())
		{
			case 2:
			{
				if ((npc.getId() == MONSTERS[0]) || (npc.getId() == MONSTERS[2]))
				{
					if (st.getQuestItemsCount(RUSTEDBRONZESWORD1) < 10)
					{
						if (getRandom(10) < 4)
						{
							st.giveItems(RUSTEDBRONZESWORD1, 1);
							st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
						}
					}
					if (st.getQuestItemsCount(RUSTEDBRONZESWORD1) == 10)
					{
						st.setCond(3, true);
					}
				}
				break;
			}
			case 5:
			{
				if (((st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == RUSTEDBRONZESWORD3) && (npc.getId() == MONSTERS[1])) || (npc.getId() == MONSTERS[3]))
				{
					if (st.getQuestItemsCount(POISONSPIDERLEG) < 20)
					{
						st.giveItems(POISONSPIDERLEG, 1);
						st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
					}
					
					if (st.getQuestItemsCount(POISONSPIDERLEG) == 20)
					{
						st.setCond(6, true);
					}
				}
			}
		}
		
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (npc.getId())
		{
			case AURON:
			{
				switch (st.getCond())
				{
					case 0:
					{
						htmltext = "30010-01.htm";
						break;
					}
					case 1:
					{
						htmltext = "30010-07.html";
						break;
					}
					case 2:
					case 3:
					{
						htmltext = "30010-08.html";
						break;
					}
					case 4:
					{
						htmltext = "30010-09.html";
						break;
					}
					case 5:
					{
						htmltext = "30010-12.html";
						break;
					}
					case 6:
					{
						st.takeItems(RUSTEDBRONZESWORD3, 1);
						st.takeItems(POISONSPIDERLEG, -1);
						
						if (player.getLevel() >= 20)
						{
							st.addExpAndSp(320534, 21012);
						}
						else if (player.getLevel() == 19)
						{
							st.addExpAndSp(456128, 27710);
						}
						else
						{
							st.addExpAndSp(160267, 34408);
						}
						
						st.giveAdena(163800, true);
						st.giveItems(MEDALLIONOFWARRIOR, 1);
						player.sendPacket(new SocialAction(player.getObjectId(), 3));
						st.saveGlobalQuestVar("1ClassQuestFinished", "1");
						st.exitQuest(false, true);
						htmltext = "30010-13.html";
						break;
					}
				}
				break;
			}
			case SIMPLON:
			{
				switch (st.getCond())
				{
					case 1:
					{
						htmltext = "30253-01.html";
						break;
					}
					case 2:
					{
						htmltext = "30253-03.html";
						break;
					}
					case 3:
					{
						st.setCond(4, true);
						st.takeItems(WARRIORGUILDMARK, 1);
						st.takeItems(RUSTEDBRONZESWORD1, 10);
						st.giveItems(RUSTEDBRONZESWORD2, 1);
						st.giveItems(SIMPLONSLETTER, 1);
						htmltext = "30253-04.html";
						break;
					}
					case 4:
					{
						htmltext = "30253-05.html";
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
		new Q00401_PathToWarrior(401, Q00401_PathToWarrior.class.getSimpleName(), "Path of the Warrior");
	}
}
