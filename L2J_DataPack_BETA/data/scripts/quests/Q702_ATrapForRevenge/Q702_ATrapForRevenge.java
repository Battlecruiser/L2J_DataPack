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
package quests.Q702_ATrapForRevenge;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * A Trap for Revenge (702)
 * @author malyelfik
 */
public class Q702_ATrapForRevenge extends Quest
{
	private static final String qn = "702_ATrapForRevenge";
	// NPC
	private static final int Plenos = 32563;
	private static final int Lekon = 32557;
	private static final int Tenius = 32555;
	private static final int[] Monsters = { 22612, 22613, 25632, 22610, 22611, 25631, 25626 };
	// Items
	private static final int DrakeFlesh = 13877;
	private static final int RottenBlood = 13878;
	private static final int BaitForDrakes = 13879;
	private static final int VariantDrakeWingHorns = 13880;
	private static final int ExtractedRedStarStone = 14009;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		
		if (st == null)
			return getNoQuestMsg(player);
		
		if (event.equalsIgnoreCase("32563-04.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32563-07.html"))
		{
			if (st.getQuestItemsCount(DrakeFlesh) < 1)
				htmltext = "32563-07.html";
			else
				htmltext = "32563-08.html";
		}
		else if (event.equalsIgnoreCase("32563-09.html"))
		{
			long count = st.getQuestItemsCount(DrakeFlesh);
			st.giveItems(57, count * 100);
			st.takeItems(DrakeFlesh, count);
		}
		else if (event.equalsIgnoreCase("32563-11.html"))
		{
			if (st.getQuestItemsCount(VariantDrakeWingHorns) < 1)
				htmltext = "32563-11.html";
			else
			{
				long count = st.getQuestItemsCount(VariantDrakeWingHorns);
				st.giveItems(57, count * 200000);
				st.takeItems(VariantDrakeWingHorns, count);
				htmltext = "32563-12.html";
			}
		}
		else if (event.equalsIgnoreCase("32563-14.html"))
		{
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		else if (event.equalsIgnoreCase("32557-03.html"))
		{
			if (st.getQuestItemsCount(RottenBlood) < 1 && st.getQuestItemsCount(ExtractedRedStarStone) < 100)
				htmltext = "32557-03.html";
			else if (st.getQuestItemsCount(RottenBlood) >= 1 && st.getQuestItemsCount(ExtractedRedStarStone) < 100)
				htmltext = "32557-04.html";
			else if (st.getQuestItemsCount(RottenBlood) < 1 && st.getQuestItemsCount(ExtractedRedStarStone) >= 100)
				htmltext = "32557-05.html";
			else if (st.getQuestItemsCount(RottenBlood) >= 1 && st.getQuestItemsCount(ExtractedRedStarStone) >= 100)
			{
				st.giveItems(BaitForDrakes, 1);
				st.takeItems(RottenBlood, 1);
				st.takeItems(ExtractedRedStarStone, 100);
				htmltext = "32557-06.html";
			}
		}
		else if (event.equalsIgnoreCase("32555-03.html"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32555-05.html"))
		{
			st.exitQuest(true);
			st.playSound("ItemSound.quest_finish");
		}
		else if (event.equalsIgnoreCase("32555-06.html"))
		{
			if (st.getQuestItemsCount(DrakeFlesh) < 100)
				htmltext = "32555-06.html";
			else
				htmltext = "32555-07.html";
		}
		else if (event.equalsIgnoreCase("32555-08.html"))
		{
			st.giveItems(RottenBlood, 1);
			st.takeItems(DrakeFlesh, 100);
		}
		else if (event.equalsIgnoreCase("32555-10.html"))
		{
			if (st.getQuestItemsCount(VariantDrakeWingHorns) < 1)
				htmltext = "32555-10.html";
			else
				htmltext = "32555-11.html";
		}
		else if (event.equalsIgnoreCase("32555-15.html"))
		{
			int i0 = Rnd.get(1000);
			int i1 = Rnd.get(1000);
			
			if (i0 >= 500 && i1 >= 600)
			{
				st.giveItems(57, Rnd.get(49917) + 125000);
				if (i1 < 720)
				{
					st.giveItems(9628, Rnd.get(3) + 1);
					st.giveItems(9629, Rnd.get(3) + 1);
				}
				else if (i1 < 840)
				{
					st.giveItems(9629, Rnd.get(3) + 1);
					st.giveItems(9630, Rnd.get(3) + 1);
				}
				else if (i1 < 960)
				{
					st.giveItems(9628, Rnd.get(3) + 1);
					st.giveItems(9630, Rnd.get(3) + 1);
				}
				else if (i1 < 1000)
				{
					st.giveItems(9628, Rnd.get(3) + 1);
					st.giveItems(9629, Rnd.get(3) + 1);
					st.giveItems(9630, Rnd.get(3) + 1);
				}
				htmltext = "32555-15.html";
			}
			else if (i0 >= 500 && i1 < 600)
			{
				st.giveItems(57, Rnd.get(49917) + 125000);
				if (i1 < 210)
				{
				}
				else if (i1 < 340)
					st.giveItems(9628, Rnd.get(3) + 1);
				else if (i1 < 470)
					st.giveItems(9629, Rnd.get(3) + 1);
				else if (i1 < 600)
					st.giveItems(9630, Rnd.get(3) + 1);
				
				htmltext = "32555-16.html";
			}
			else if (i0 < 500 && i1 >= 600)
			{
				st.giveItems(57, Rnd.get(49917) + 25000);
				if (i1 < 720)
				{
					st.giveItems(9628, Rnd.get(3) + 1);
					st.giveItems(9629, Rnd.get(3) + 1);
				}
				else if (i1 < 840)
				{
					st.giveItems(9629, Rnd.get(3) + 1);
					st.giveItems(9630, Rnd.get(3) + 1);
				}
				else if (i1 < 960)
				{
					st.giveItems(9628, Rnd.get(3) + 1);
					st.giveItems(9630, Rnd.get(3) + 1);
				}
				else if (i1 < 1000)
				{
					st.giveItems(9628, Rnd.get(3) + 1);
					st.giveItems(9629, Rnd.get(3) + 1);
					st.giveItems(9630, Rnd.get(3) + 1);
				}
				htmltext = "32555-17.html";
			}
			else if (i0 < 500 && i1 < 600)
			{
				st.giveItems(57, Rnd.get(49917) + 25000);
				if (i1 < 210)
				{
				}
				else if (i1 < 340)
					st.giveItems(9628, Rnd.get(3) + 1);
				else if (i1 < 470)
					st.giveItems(9629, Rnd.get(3) + 1);
				else if (i1 < 600)
					st.giveItems(9630, Rnd.get(3) + 1);
				
				htmltext = "32555-18.html";
			}
			st.takeItems(VariantDrakeWingHorns, 1);
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		QuestState st = player.getQuestState(qn);
		QuestState prev = player.getQuestState("10273_GoodDayToFly");
		
		if (st == null)
			return htmltext;
		
		if (npc.getNpcId() == Plenos)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (prev != null && prev.getState() == State.COMPLETED && player.getLevel() >= 78)
						htmltext = "32563-01.htm";
					else
						htmltext = "32563-02.htm";
					break;
				case State.STARTED:
					if (st.getInt("cond") == 1)
						htmltext = "32563-05.html";
					else
						htmltext = "32563-06.html";
					break;
			}
		}
		if (st.getState() == State.STARTED)
		{
			if (npc.getNpcId() == Lekon)
			{
				switch (st.getInt("cond"))
				{
					case 1:
						htmltext = "32557-01.html";
						break;
					case 2:
						htmltext = "32557-02.html";
						break;
				}
			}
			else if (npc.getNpcId() == Tenius)
			{
				switch (st.getInt("cond"))
				{
					case 1:
						htmltext = "32555-01.html";
						break;
					case 2:
						htmltext = "32555-04.html";
						break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		L2PcInstance partyMember = getRandomPartyMember(player, "2");
		if (partyMember == null)
			return null;
		final QuestState st = partyMember.getQuestState(qn);
		int chance = Rnd.get(1000);
		
		switch (npc.getNpcId())
		{
			case 22612:
				if (chance < 413)
					st.giveItems(DrakeFlesh, 2);
				else
					st.giveItems(DrakeFlesh, 1);
				break;
			case 22613:
				if (chance < 440)
					st.giveItems(DrakeFlesh, 2);
				else
					st.giveItems(DrakeFlesh, 1);
				break;
			case 25632:
				if (chance < 996)
					st.giveItems(DrakeFlesh, 1);
				break;
			case 22610:
				if (chance < 485)
					st.giveItems(DrakeFlesh, 2);
				else
					st.giveItems(DrakeFlesh, 1);
				break;
			case 22611:
				if (chance < 451)
					st.giveItems(DrakeFlesh, 2);
				else
					st.giveItems(DrakeFlesh, 1);
				break;
			case 25631:
				if (chance < 485)
					st.giveItems(DrakeFlesh, 2);
				else
					st.giveItems(DrakeFlesh, 1);
				break;
			case 25626:
				if (chance < 708)
					st.giveItems(VariantDrakeWingHorns, Rnd.get(2) + 1);
				else if (chance < 978)
					st.giveItems(VariantDrakeWingHorns, Rnd.get(3) + 3);
				else if (chance < 994)
					st.giveItems(VariantDrakeWingHorns, Rnd.get(4) + 6);
				else if (chance < 998)
					st.giveItems(VariantDrakeWingHorns, Rnd.get(4) + 10);
				else if (chance < 1000)
					st.giveItems(VariantDrakeWingHorns, Rnd.get(5) + 14);
				break;
		}
		st.playSound("ItemSound.quest_itemget");
		return null;
	}
	
	public Q702_ATrapForRevenge(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(Plenos);
		addTalkId(Plenos);
		addTalkId(Lekon);
		addTalkId(Tenius);
		for (int i : Monsters)
		{
			addKillId(i);
		}
	}
	
	public static void main(String[] args)
	{
		new Q702_ATrapForRevenge(702, qn, "A Trap for Revenge");
	}
}
