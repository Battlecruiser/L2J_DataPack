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
package custom.PurchaseBracelet;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Purchase Bracelet AI.
 * @author Nyaran
 */
public class PurchaseBracelet extends Quest
{
	private static final String qn = "PurchaseBracelet";

	private static final int NPC = 30098;

	private static final int ANGEL_BRACELET = 10320;
	private static final int DEVIL_BRACELET = 10326;

	private static final int ADENA = 57;
	private static final int BIG_RED_NIBLE_FISH = 6471;
	private static final int GREAT_CODRAN = 5094;
	private static final int MEMENTO_MORI = 9814;
	private static final int EARTH_EGG = 9816;
	private static final int NONLIVING_NUCLEUS = 9817;
	private static final int DRAGON_HEART = 9815;

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;

		if (st.getQuestItemsCount(6471) >= 20 && st.getQuestItemsCount(GREAT_CODRAN) >= 50 && st.getQuestItemsCount(MEMENTO_MORI) >= 4 && st.getQuestItemsCount(EARTH_EGG) >= 5 && st.getQuestItemsCount(NONLIVING_NUCLEUS) >= 5 && st.getQuestItemsCount(DRAGON_HEART) >= 3 && st.getQuestItemsCount(ADENA) >= 7500000)
		{
			st.takeItems(BIG_RED_NIBLE_FISH, 25);
			st.takeItems(GREAT_CODRAN, 50);
			st.takeItems(MEMENTO_MORI, 4);
			st.takeItems(EARTH_EGG, 5);
			st.takeItems(NONLIVING_NUCLEUS, 5);
			st.takeItems(DRAGON_HEART, 3);
			st.takeItems(ADENA, 7500000);
			htmltext = "";
			if (event.equals("Little_Devil"))
				st.giveItems(DEVIL_BRACELET, 1);
			else if (event.equals("Little_Angel"))
				st.giveItems(ANGEL_BRACELET, 1);
		}
		else
			htmltext = "30098-no.htm";
		st.exitQuest(true);
		return htmltext;
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(qn);
		if (st == null)
			st = this.newQuestState(player);
		htmltext = "30098.htm";
		return htmltext;
	}

	public PurchaseBracelet(int id, String name, String descr)
	{
		super(id, name, descr);

		addStartNpc(NPC);
		addTalkId(NPC);
	}

	public static void main(String args[])
	{
		new PurchaseBracelet(-1, qn, "custom");
	}
}
