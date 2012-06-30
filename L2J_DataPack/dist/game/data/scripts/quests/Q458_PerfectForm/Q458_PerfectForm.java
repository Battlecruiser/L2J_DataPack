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
package quests.Q458_PerfectForm;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.QuestState.QuestType;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.util.Util;

/**
 * @author centrio, Zoey76
 */
public class Q458_PerfectForm extends Quest
{
	private static final String qn = "458_PerfectForm";
	
	// NPCs
	private static final int _Kelleyia = 32768;
	
	// Mobs
	private static final int[] _mobs1 =
	{
		18878,
		18879
	};
	private static final int[] _mobs2 =
	{
		18885,
		18886
	};
	private static final int[] _mobs3 =
	{
		18892,
		18893
	};
	private static final int[] _mobs4 =
	{
		18899,
		18900
	};
	
	private final int[][] _mobs =
	{
		_mobs1,
		_mobs2,
		_mobs3,
		_mobs4
	};
	
	private static final ItemHolder[] _rewards1 =
	{
		new ItemHolder(10397, 2),
		new ItemHolder(10398, 2),
		new ItemHolder(10399, 2),
		new ItemHolder(10400, 2),
		new ItemHolder(10401, 2),
		new ItemHolder(10402, 2),
		new ItemHolder(10403, 2),
		new ItemHolder(10404, 2),
		new ItemHolder(10405, 2)
	};
	
	private static final ItemHolder[] _rewards2 =
	{
		new ItemHolder(10397, 5),
		new ItemHolder(10398, 5),
		new ItemHolder(10399, 5),
		new ItemHolder(10400, 5),
		new ItemHolder(10401, 5),
		new ItemHolder(10402, 5),
		new ItemHolder(10403, 5),
		new ItemHolder(10404, 5),
		new ItemHolder(10405, 5)
	};
	
	private static final ItemHolder[] _rewards3 =
	{
		new ItemHolder(10373, 1),
		new ItemHolder(10374, 1),
		new ItemHolder(10375, 1),
		new ItemHolder(10376, 1),
		new ItemHolder(10377, 1),
		new ItemHolder(10378, 1),
		new ItemHolder(10379, 1),
		new ItemHolder(10380, 1),
		new ItemHolder(10381, 1)
	};
	
	public Q458_PerfectForm(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(_Kelleyia);
		addTalkId(_Kelleyia);
		
		addKillId(_mobs1);
		addKillId(_mobs2);
		addKillId(_mobs3);
		addKillId(_mobs4);
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
		
		if (npc.getNpcId() == _Kelleyia)
		{
			final int overHits = st.getInt("overHits");
			if (event.equalsIgnoreCase("32768-12.html"))
			{
				st.setState(State.STARTED);
				st.set("cond", "1");
				st.playSound("ItemSound.quest_accept");
			}
			else if (event.equalsIgnoreCase("32768-16.html"))
			{
				if ((overHits >= 0) && (overHits <= 6))
				{
					htmltext = getHtm(st.getPlayer().getHtmlPrefix(), "32768-16c.html");
					htmltext = htmltext.replace("%overhits%", "" + overHits);
				}
				else if ((overHits >= 7) && (overHits <= 19))
				{
					htmltext = getHtm(st.getPlayer().getHtmlPrefix(), "32768-16b.html");
					htmltext = htmltext.replace("%overhits%", "" + overHits);
				}
				else if (overHits >= 20)
				{
					htmltext = getHtm(st.getPlayer().getHtmlPrefix(), "32768-16a.html");
					htmltext = htmltext.replace("%overhits%", "" + overHits);
				}
			}
			else if (event.equalsIgnoreCase("32768-17.html"))
			{
				if ((overHits >= 0) && (overHits <= 6))
				{
					final int rnd = getRandom(_rewards1.length);
					st.giveItems(_rewards1[rnd].getId(), _rewards1[rnd].getCount() * (long) Config.RATE_QUEST_REWARD);
				}
				else if ((overHits >= 7) && (overHits <= 19))
				{
					final int rnd = getRandom(_rewards2.length);
					st.giveItems(_rewards2[rnd].getId(), _rewards2[rnd].getCount() * (long) Config.RATE_QUEST_REWARD);
				}
				else if (overHits >= 20)
				{
					final int rnd = getRandom(_rewards3.length);
					st.giveItems(_rewards3[rnd].getId(), _rewards3[rnd].getCount() * (long) Config.RATE_QUEST_REWARD);
				}
				
				st.giveItems(15482, (int) Config.RATE_QUEST_REWARD);
				st.giveItems(15483, (int) Config.RATE_QUEST_REWARD);
				st.playSound("ItemSound.quest_finish");
				st.exitQuest(QuestType.DAILY);
			}
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return htmltext;
		}
		
		if (npc.getNpcId() == _Kelleyia)
		{
			switch (st.getState())
			{
				case State.CREATED:
					htmltext = (player.getLevel() >= 82) ? "32768-01.htm" : "32768-03.html";
					break;
				case State.STARTED:
					final int cond = st.getInt("cond");
					if (cond == 1)
					{
						if ((st.getInt("mobs1") == 0) && (st.getInt("mobs2") == 0) && (st.getInt("mobs3") == 0) && (st.getInt("mobs4") == 0))
						{
							htmltext = "32768-13.html";
						}
						else
						{
							htmltext = "32768-14.html";
						}
					}
					else if (cond == 2)
					{
						htmltext = "32768-15.html";
					}
					break;
				case State.COMPLETED:
					if (st.isNowAvailable())
					{
						st.setState(State.CREATED); // Not required, but it'll set the proper state.
						htmltext = (player.getLevel() >= 82) ? "32768-01.htm" : "32768-03.html";
					}
					else
					{
						htmltext = "32768-02.html";
					}
					break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			return null;
		}
		
		if (st.getInt("cond") == 1)
		{
			final int npcId = npc.getNpcId();
			for (int m = 0; m < _mobs.length; m++)
			{
				if (Util.contains(_mobs[m], npcId))
				{
					st.set("mobs" + (m + 1), String.valueOf(st.getInt("mobs" + (m + 1)) + 1));
					if (((L2Attackable) npc).isOverhit())
					{
						st.set("overHits", String.valueOf(st.getInt("overHits") + 1));
					}
					
					if ((st.getInt("mobs1") >= 10) && (st.getInt("mobs2") >= 10) && (st.getInt("mobs3") >= 10) && (st.getInt("mobs4") >= 10))
					{
						st.set("cond", "2");
					}
					break;
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new Q458_PerfectForm(458, qn, "Perfect Form");
	}
}
