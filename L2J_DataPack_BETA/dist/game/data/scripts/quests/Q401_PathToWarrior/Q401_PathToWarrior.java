/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q401_PathToWarrior;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;

/**
 * Path of the Warrior (401)
 * @author jython script by ElgarL, Mr.
 * @version 2010-12-29 (Freya)
 */
public class Q401_PathToWarrior extends Quest
{
	
	private static final String qn = "401_PathToWarrior";
	
	// Item
	private static final int AuronsLetter = 1138;
	private static final int WarriorGuildMark = 1139;
	private static final int RustedBronzeSword1 = 1140;
	private static final int RustedBronzeSword2 = 1141;
	private static final int RustedBronzeSword3 = 1142;
	private static final int SimplonsLetter = 1143;
	private static final int PoisonSpiderLeg = 1144;
	private static final int MedallionOfWarrior = 1145;
	
	// Npc
	private static final int Auron = 30010;
	private static final int Simplon = 30253;
	private static final int[] Monsters = { 20035, 20038, 20042, 20043 };
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("401_1"))
		{
			if (player.getClassId().getId() == 0x00)
			{
				if (player.getLevel() >= 18)
				{
					if (st.getQuestItemsCount(MedallionOfWarrior) == 1)
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
			}
			else if (player.getClassId().getId() == 0x01)
			{
				htmltext = "30010-03.htm";
			}
			else
			{
				htmltext = "30010-02b.htm";
			}
		}
		else if (event.equalsIgnoreCase("401_accept"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.giveItems(AuronsLetter, 1);
			st.playSound("ItemSound.quest_accept");
			htmltext = "30010-06.htm";
		}
		else if (event.equalsIgnoreCase("30253_1"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
			st.takeItems(AuronsLetter, 1);
			st.giveItems(WarriorGuildMark, 1);
			htmltext = "30253-02.html";
		}
		else if (event.equalsIgnoreCase("401_2"))
		{
			htmltext = "30010-10.html";
		}
		else if (event.equalsIgnoreCase("401_3"))
		{
			st.set("cond", "5");
			st.takeItems(RustedBronzeSword2, 1);
			st.giveItems(RustedBronzeSword3, 1);
			st.takeItems(SimplonsLetter, 1);
			st.playSound("ItemSound.quest_middle");
			htmltext = "30010-11.html";
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == Auron)
		{
			if (st.getInt("cond") == 0)
			{
				htmltext = "30010-01.htm";
			}
			else if (st.getInt("cond") == 1)
			{
				htmltext = "30010-07.html";
			}
			else if ((st.getInt("cond") == 2) || (st.getInt("cond") == 3))
			{
				htmltext = "30010-08.html";
			}
			else if (st.getInt("cond") == 4)
			{
				htmltext = "30010-09.html";
			}
			else if (st.getInt("cond") == 5)
			{
				htmltext = "30010-12.html";
			}
			else if (st.getInt("cond") == 6)
			{
				st.takeItems(RustedBronzeSword3, 1);
				st.takeItems(PoisonSpiderLeg, -1);
				st.giveItems(MedallionOfWarrior, 1);
				if (player.getLevel() >= 20)
					st.addExpAndSp(320534, 21012);
				else if (player.getLevel() == 19)
					st.addExpAndSp(456128, 27710);
				else
					st.addExpAndSp(160267, 34408);
				st.giveItems(57, 163800);
				st.playSound("ItemSound.quest_finish");
				player.sendPacket(new SocialAction(player.getObjectId(), 3));
				st.exitQuest(false);
				st.saveGlobalQuestVar("1ClassQuestFinished", "1");
				htmltext = "30010-13.html";
			}
		}
		else if (npc.getNpcId() == Simplon)
		{
			if (st.getInt("cond") == 1)
			{
				htmltext = "30253-01.html";
			}
			else if (st.getInt("cond") == 2)
			{
				htmltext = "30253-03.html";
			}
			else if (st.getInt("cond") == 3)
			{
				st.set("cond", "4");
				st.playSound("ItemSound.quest_middle");
				st.takeItems(WarriorGuildMark, 1);
				st.takeItems(RustedBronzeSword1, 10);
				st.giveItems(RustedBronzeSword2, 1);
				st.giveItems(SimplonsLetter, 1);
				htmltext = "30253-04.html";
			}
			else if (st.getInt("cond") == 4)
			{
				htmltext = "30253-05.html";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return null;
		}
		
		if (st.getInt("cond") == 2)
		{
			if ((npc.getNpcId() == Monsters[0]) || (npc.getNpcId() == Monsters[2]))
			{
				if (st.getQuestItemsCount(RustedBronzeSword1) < 10)
				{
					if (st.getRandom(10) < 4)
					{
						st.giveItems(RustedBronzeSword1, 1);
						st.playSound("ItemSound.quest_itemget");
					}
				}
				if (st.getQuestItemsCount(RustedBronzeSword1) == 10)
				{
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "3");
				}
			}
		}
		else if ((st.getInt("cond") == 5) && (st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == RustedBronzeSword3))
		{
			if ((npc.getNpcId() == Monsters[1]) || (npc.getNpcId() == Monsters[3]))
			{
				if (st.getQuestItemsCount(PoisonSpiderLeg) < 20)
				{
					st.giveItems(PoisonSpiderLeg, 1);
					st.playSound("ItemSound.quest_itemget");
				}
				if (st.getQuestItemsCount(PoisonSpiderLeg) == 20)
				{
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "6");
				}
			}
		}
		return null;
	}
	
	public Q401_PathToWarrior(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(Auron);
		addTalkId(Auron);
		addTalkId(Simplon);
		for (int i : Monsters)
		{
			addKillId(i);
		}
		questItemIds = new int[] { AuronsLetter, WarriorGuildMark, RustedBronzeSword1, RustedBronzeSword2, RustedBronzeSword3, SimplonsLetter, PoisonSpiderLeg };
	}
	
	public static void main(String[] args)
	{
		new Q401_PathToWarrior(401, qn, "Path of the Warrior");
	}
}
