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
package quests.Q636_TruthBeyond;

import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.model.zone.L2ZoneType;

/**
 * 
 * @author moved to java by DS, jython script by Polo, BiTi and DrLecter
 *
 */
public final class Q636_TruthBeyond extends Quest
{
	private static final String QN = "636_TruthBeyond";
	
	private static final int ELIAH = 31329;
	private static final int FLAURON = 32010;
	private static final int ZONE = 30100;
	private static final int VISITOR_MARK = 8064;
	private static final int FADED_MARK = 8065;
	private static final int MARK = 8067;
	
	public Q636_TruthBeyond(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ELIAH);
		addTalkId(ELIAH);
		addTalkId(FLAURON);
		addEnterZoneId(ZONE);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(QN);
		if (st == null)
			return null;
		
		if ("31329-04.htm".equalsIgnoreCase(event))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if ("32010-02.htm".equalsIgnoreCase(event))
		{
			st.giveItems(VISITOR_MARK, 1);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(true);
		}
		return event;
	}
	
	@Override
	public final String onTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(QN);
		if (st == null)
			return getNoQuestMsg(player);
		
		if (npc.getNpcId() == ELIAH)
		{
			if (st.hasQuestItems(VISITOR_MARK) || st.hasQuestItems(FADED_MARK) || st.hasQuestItems(MARK))
			{
				st.exitQuest(true);
				return "31329-mark.htm";
			}
			if (st.getState() == State.CREATED)
			{
				if (player.getLevel() > 72)
					return "31329-02.htm";
				
				st.exitQuest(true);
				return "31329-01.htm";
			}
			else if (st.getState() == State.STARTED)
				return "31329-05.htm";
		}
		else if (st.getState() == State.STARTED) // Flauron only
		{
			if (st.getInt("cond") == 1)
			{
				return "32010-01.htm";
			}
			st.exitQuest(true);
			return "32010-03.htm";
		}
		return getNoQuestMsg(player);
	}
	
	@Override
	public final String onEnterZone(L2Character character, L2ZoneType zone)
	{
		// QuestState already null on enter because quest is finished
		if (character instanceof L2PcInstance)
		{
			if (((L2PcInstance)character).destroyItemByItemId("Mark", VISITOR_MARK, 1, character, false))
				((L2PcInstance)character).addItem("Mark", FADED_MARK, 1, character, true);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q636_TruthBeyond(636, QN, "The Truth Beyond the Gate");
	}
}
