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
package quests.Q10272_LightFragment;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Light Fragment (10272)
 * @author Gladicek Updated 28-10-2011
 */
public class Q10272_LightFragment extends Quest
{
	private static final String qn = "10272_LightFragment";
	
	private static final int ORBYU = 32560;
	private static final int ARTIUS = 32559;
	private static final int GINBY = 32566;
	private static final int LELRIKIA = 32567;
	private static final int LEKON = 32557;
	private static final int[] Monsters =
	{
		22536, 22537, 22538, 22539, 22540, 22541, 22542, 22543, 22544, 22547, 22550, 22551, 22552, 22596
	};
	private static final int FRAGMENT_POWDER = 13853;
	private static final int LIGHT_FRAGMENT_POWDER = 13854;
	private static final int LIGHT_FRAGMENT = 13855;
	private static final double DROP_CHANCE = 60;
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		final int npcId = npc.getNpcId();
		final int cond = st.getInt("cond");
		if (npcId == ORBYU)
		{
			switch (st.getState())
			{
				case State.CREATED:
					if (player.getLevel() < 75)
					{
						htmltext = "32560-03.htm";
					}
					else
					{
						final QuestState _prev = player.getQuestState("10271_TheEnvelopingDarkness");
						if ((_prev != null) && _prev.isCompleted())
						{
							htmltext = "32560-01.htm";
						}
						else
						{
							htmltext = "32560-02.htm";
						}
					}
					break;
				case State.STARTED:
					htmltext = "32560-06.htm";
					break;
				case State.COMPLETED:
					htmltext = "32560-04.htm";
					break;
			}
			
			if (st.getInt("cond") == 2)
			{
				htmltext = "32560-06.htm";
			}
		}
		else if (npcId == ARTIUS)
		{
			if (st.isCompleted())
			{
				htmltext = "32559-19.htm";
			}
			else
			{
				switch (cond)
				{
					case 1:
						htmltext = "32559-01.htm";
						break;
					case 2:
						htmltext = "32559-04.htm";
						break;
					case 3:
						htmltext = "32559-08.htm";
						break;
					case 4:
						htmltext = "32559-10.htm";
						break;
					case 5:
						if (st.getQuestItemsCount(FRAGMENT_POWDER) >= 100)
						{
							htmltext = "32559-15.htm";
							st.set("cond", "6");
						}
						else
						{
							htmltext = st.hasQuestItems(FRAGMENT_POWDER) ? "32559-14.htm" : "32559-13.htm";
						}
						break;
					case 6:
						if (st.getQuestItemsCount(LIGHT_FRAGMENT_POWDER) < 100)
						{
							htmltext = "32559-16.htm";
						}
						else
						{
							st.set("cond", "7");
							st.playSound("ItemSound.quest_middle");
							htmltext = "32559-17.htm";
						}
						break;
					case 7:
						// TODO Nothing here?
						break;
					case 8:
						st.giveAdena(556980, false);
						st.addExpAndSp(1009016, 91363);
						st.playSound("ItemSound.quest_finish");
						st.exitQuest(false);
						htmltext = "32559-18.htm";
						break;
				}
			}
		}
		else if (npcId == GINBY)
		{
			switch (cond)
			{
				case 1:
				case 2:
					htmltext = "32566-02.htm";
					break;
				case 3:
					htmltext = "32566-01.htm";
					break;
				case 4:
					htmltext = "32566-09.htm";
					break;
				case 5:
					htmltext = "32566-10.htm";
					break;
				case 6:
					htmltext = "32566-10.htm";
					break;
			}
		}
		else if (npcId == LELRIKIA)
		{
			switch (cond)
			{
				case 3:
					htmltext = "32567-01.htm";
					break;
				case 4:
					htmltext = "32567-05.htm";
					break;
			}
		}
		else if (npcId == LEKON)
		{
			switch (cond)
			{
				case 7:
					if (st.getInt("wait") == 1)
					{
						st.giveItems(LIGHT_FRAGMENT, 1);
						st.set("cond", "8");
						st.unset("wait");
						st.playSound("ItemSound.quest_middle");
						htmltext = "32557-05.htm";
					}
					else
					{
						htmltext = "32557-01.htm";
					}
					break;
				case 8:
					htmltext = "32557-06.htm";
					break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("32560-06.htm"))
		{
			st.setState(State.STARTED);
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32559-03.htm"))
		{
			st.set("cond", "2");
		}
		else if (event.equalsIgnoreCase("32559-07.htm"))
		{
			st.set("cond", "3");
		}
		else if (event.equalsIgnoreCase("pay"))
		{
			if (st.getQuestItemsCount(PcInventory.ADENA_ID) >= 10000)
			{
				st.takeItems(PcInventory.ADENA_ID, 10000);
				htmltext = "32566-05.htm";
			}
			else
			{
				htmltext = "32566-04a.htm";
			}
		}
		else if (event.equalsIgnoreCase("32567-04.htm"))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("32559-12.htm"))
		{
			st.set("cond", "5");
		}
		else if (event.equalsIgnoreCase("32557-03.htm"))
		{
			if (st.getQuestItemsCount(LIGHT_FRAGMENT_POWDER) >= 100)
			{
				st.takeItems(LIGHT_FRAGMENT_POWDER, 100);
				st.set("wait", "1");
			}
			else
			{
				htmltext = "32557-04.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(qn);
		if ((st != null) && st.getInt("cond") == 5)
		{
			final long count = st.getQuestItemsCount(FRAGMENT_POWDER);
			if (count < 100)
			{
				int chance = (int) (Config.RATE_QUEST_DROP * DROP_CHANCE);
				int numItems = chance / 100;
				chance = chance % 100;
				if (getRandom(100) < chance)
				{
					numItems++;
				}
				if (numItems > 0)
				{
					if ((count + numItems) >= 100)
					{
						numItems = 100 - (int) count;
					}
					else
					{
						st.playSound("ItemSound.quest_itemget");
					}
					st.giveItems(FRAGMENT_POWDER, numItems);
				}
			}
		}
		return null;
	}
	
	public Q10272_LightFragment(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ORBYU);
		addTalkId(ORBYU, ARTIUS, GINBY, LELRIKIA, LEKON);
		addKillId(Monsters);
		
		questItemIds = new int[]
		{
			FRAGMENT_POWDER, LIGHT_FRAGMENT_POWDER,
		};
	}
	
	public static void main(String[] args)
	{
		new Q10272_LightFragment(10272, qn, "Light Fragment");
	}
}
