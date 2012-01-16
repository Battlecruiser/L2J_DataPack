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
package quests.Q692_HowtoOpposeEvil;

import gnu.trove.map.hash.TIntObjectHashMap;

import com.l2jserver.Config;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * @author Gigiikun
 */
public final class Q692_HowtoOpposeEvil extends Quest
{
	private static final String QN = "692_HowtoOpposeEvil";
	private static final int DILIOS = 32549;
	private static final int LEKONS_CERTIFICATE = 13857;
	private static final int[] QUEST_ITEMS = { 13863, 13864, 13865, 13866, 13867, 15535, 15536 };
	
	private static final TIntObjectHashMap<Integer[]> _questMobs = new TIntObjectHashMap<Integer[]>();
	
	static
	{
		// Seed of Infinity
		_questMobs.put(22509, new Integer[]{13863,500});
		_questMobs.put(22510, new Integer[]{13863,500});
		_questMobs.put(22511, new Integer[]{13863,500});
		_questMobs.put(22512, new Integer[]{13863,500});
		_questMobs.put(22513, new Integer[]{13863,500});
		_questMobs.put(22514, new Integer[]{13863,500});
		_questMobs.put(22515, new Integer[]{13863,500});
		// Seed of Destruction
		_questMobs.put(22537, new Integer[]{13865,250});
		_questMobs.put(22538, new Integer[]{13865,250});
		_questMobs.put(22539, new Integer[]{13865,250});
		_questMobs.put(22540, new Integer[]{13865,250});
		_questMobs.put(22541, new Integer[]{13865,250});
		_questMobs.put(22542, new Integer[]{13865,250});
		_questMobs.put(22543, new Integer[]{13865,250});
		_questMobs.put(22544, new Integer[]{13865,250});
		_questMobs.put(22546, new Integer[]{13865,250});
		_questMobs.put(22547, new Integer[]{13865,250});
		_questMobs.put(22548, new Integer[]{13865,250});
		_questMobs.put(22549, new Integer[]{13865,250});
		_questMobs.put(22550, new Integer[]{13865,250});
		_questMobs.put(22551, new Integer[]{13865,250});
		_questMobs.put(22552, new Integer[]{13865,250});
		_questMobs.put(22593, new Integer[]{13865,250});
		_questMobs.put(22596, new Integer[]{13865,250});
		_questMobs.put(22597, new Integer[]{13865,250});
		// Seed of Annihilation
		_questMobs.put(22746, new Integer[]{15536,125});
		_questMobs.put(22747, new Integer[]{15536,125});
		_questMobs.put(22748, new Integer[]{15536,125});
		_questMobs.put(22749, new Integer[]{15536,125});
		_questMobs.put(22750, new Integer[]{15536,125});
		_questMobs.put(22751, new Integer[]{15536,125});
		_questMobs.put(22752, new Integer[]{15536,125});
		_questMobs.put(22753, new Integer[]{15536,125});
		_questMobs.put(22754, new Integer[]{15536,125});
		_questMobs.put(22755, new Integer[]{15536,125});
		_questMobs.put(22756, new Integer[]{15536,125});
		_questMobs.put(22757, new Integer[]{15536,125});
		_questMobs.put(22758, new Integer[]{15536,125});
		_questMobs.put(22759, new Integer[]{15536,125});
		_questMobs.put(22760, new Integer[]{15536,125});
		_questMobs.put(22761, new Integer[]{15536,125});
		_questMobs.put(22762, new Integer[]{15536,125});
		_questMobs.put(22763, new Integer[]{15536,125});
		_questMobs.put(22764, new Integer[]{15536,125});
		_questMobs.put(22765, new Integer[]{15536,125});
	}
	
	private final boolean giveReward(QuestState st, int itemId, int minCount, int rewardItemId, long rewardCount)
	{
		long count = st.getQuestItemsCount(itemId);
		if (count >= minCount)
		{
			count = count / minCount;
			st.takeItems(itemId, count * minCount);
			if (rewardItemId == 57)
				st.giveAdena(rewardCount * count, true);
			else
				st.giveItems(rewardItemId, rewardCount * count);
			return true;
		}
		return false;
	}

	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(QN);
		if (st == null)
			return "";
		if (event.equalsIgnoreCase("32549-03.htm"))
		{
			st.set("cond","1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32550-04.htm"))
			st.set("cond","3");
		else if (event.equalsIgnoreCase("32550-07.htm"))
		{
			if (!giveReward(st, 13863, 5, 13796, 1))
				return "32550-08.htm";
		}
		else if (event.equalsIgnoreCase("32550-09.htm"))
		{
			if (!giveReward(st, 13798, 1, 57, 5000))
				return "32550-10.htm";
		}
		else if (event.equalsIgnoreCase("32550-12.htm"))
		{
			if (!giveReward(st, 13865, 5, 13841, 1))
				return "32550-13.htm";
		}
		else if (event.equalsIgnoreCase("32550-14.htm"))
		{
			if (!giveReward(st, 13867, 1, 57, 5000))
				return "32550-15.htm";
		}
		else if (event.equalsIgnoreCase("32550-17.htm"))
		{
			if (!giveReward(st, 15536, 5, 15486, 1))
				return "32550-18.htm";
		}
		else if (event.equalsIgnoreCase("32550-19.htm"))
		{
			if (!giveReward(st, 15535, 1, 57, 5000))
				return "32550-20.htm";
		}
		return htmltext;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(QN);
		if (st == null)
			return getNoQuestMsg(player);
		
		final int cond = st.getInt("cond");
		String htmltext = "";
		if (st.isCreated())
		{
			if (player.getLevel() >= 75)
			{
				htmltext = "32549-01.htm";
			}
			else
			{
				htmltext = "32549-00.htm";
			}
		}
		else
		{
			if (npc.getNpcId() == DILIOS)
			{
				if (cond == 1 && st.hasQuestItems(LEKONS_CERTIFICATE))
				{
					st.takeItems(LEKONS_CERTIFICATE, 1);
					htmltext = "32549-04.htm";
					st.set("cond","2");
				}
				else if (cond == 2)
					htmltext = "32549-05.htm";
			}
			else
			{
				if (cond == 2)
					htmltext = "32550-01.htm";
				else if (cond == 3)
				{
					for(int i : QUEST_ITEMS)
						if (st.getQuestItemsCount(i) > 0)
							return "32550-05.htm";
					htmltext = "32550-04.htm";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final L2PcInstance partyMember = getRandomPartyMember(player, "3");
		if (partyMember == null)
			return null;
		final QuestState st = partyMember.getQuestState(QN);
		final int npcId = npc.getNpcId();
		if (st != null && _questMobs.containsKey(npcId))
		{
			int chance = (int) (_questMobs.get(npcId)[1] * Config.RATE_QUEST_DROP);
			int numItems = chance / 1000;
			chance = chance % 1000;
			if (st.getRandom(1000) < chance)
				numItems++;
			if (numItems > 0)
			{
				st.giveItems(_questMobs.get(npcId)[0],numItems);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public Q692_HowtoOpposeEvil(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for(int i : _questMobs.keys())
			addKillId(i);
		addStartNpc(DILIOS);
		addTalkId(DILIOS);
		addTalkId(32550);
	}
	
	public static void main(String[] args)
	{
		new Q692_HowtoOpposeEvil(692, QN, "How to Oppose Evil");
	}
}