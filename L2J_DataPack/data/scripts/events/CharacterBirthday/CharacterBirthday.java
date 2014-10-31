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
package events.CharacterBirthday;

import java.util.Calendar;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;

/**
 * @author Gnacik
 * 
 */

public class CharacterBirthday extends Quest
{
	private static final int _npc = 32600;
	private static boolean is_spawned = false;
	
	private final static int[] _gk =
	{
		30006,30059,30080,30134,30146,30177,30233,30256,30320,30540,
		30576,30836,30848,30878,30899,31275,31320,31964,32163
	};

	public CharacterBirthday(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(_npc);
		addFirstTalkId(_npc);
		addTalkId(_npc);
		for (int id : _gk)
		{
			addStartNpc(id);
			addTalkId(id);
		}
	}

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		htmltext = event;

		if (event.equalsIgnoreCase("despawn_npc"))
		{
			npc.doDie(player);
			is_spawned = false;

			htmltext = null;
		}
		if (event.equalsIgnoreCase("receive_reward"))
		{
			// Give Adventurer Hat (Event)
			st.giveItems(10250, 1);

			// Give Buff			
			L2Skill skill;
			skill = SkillTable.getInstance().getInfo(5950, 1);
			if (skill != null)
				skill.getEffects(npc, player);
			npc.setTarget(player);
			npc.broadcastPacket(new MagicSkillUse(player, 5950, 1, 1000, 0));

			// Despawn npc
			npc.doDie(player);
			is_spawned = false;

			// Update for next year
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(System.currentTimeMillis());
			st.set("Birthday", String.valueOf(now.get(Calendar.YEAR)+1));

			htmltext = "32600-ok.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		if(is_spawned)
			return null;

		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			Quest q = QuestManager.getInstance().getQuest(getName());
			st = q.newQuestState(player);
		}
		if (st != null && player.checkBirthDay() == 0)
		{
			player.sendPacket(new PlaySound(1, "HB01", 0, 0, 0, 0, 0));
			L2Npc spawned = st.addSpawn(32600, player.getX()+10, player.getY()+10, player.getZ()+10, 0, false, 0, true);
			st.setState(State.STARTED);
			st.startQuestTimer("despawn_npc", 60000, spawned);
			is_spawned = true;
		}
		else
			return "32600-no.htm";

		return null;
	}

	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			Quest q = QuestManager.getInstance().getQuest(getName());
			st = q.newQuestState(player);
		}
		if (player.checkBirthDay() == 0)
			htmltext = "32600.htm";
		else
			htmltext = "32600-no.htm";
		return htmltext;
	}

	public static void main(String[] args)
	{
		new CharacterBirthday(-1, "CharacterBirthday", "events");
	}
}
