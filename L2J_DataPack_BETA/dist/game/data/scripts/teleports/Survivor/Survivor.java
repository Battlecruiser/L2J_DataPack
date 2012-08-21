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
package teleports.Survivor;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Gracia Survivor teleport AI.<br>
 * Original Jython script by Kerberos.
 * @author Plim
 */
public class Survivor extends Quest
{
	private static final int SURVIVOR = 32632;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
			st = newQuestState(player);
		
		if (!event.isEmpty())
		{
			if (player.getLevel() < 75)
				event = "32632-3.htm";
			else if (st.getQuestItemsCount(57) >= 150000)
			{
				st.takeItems(57, 150000);
				player.teleToLocation(-149406, 255247, -80);
			}
		}
		
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return null;
		
		return "32632-1.htm";
	}
	
	public Survivor(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(SURVIVOR);
		addTalkId(SURVIVOR);
	}
	
	public static void main(String[] args)
	{
		new Survivor(-1, Survivor.class.getSimpleName(), "teleports");
	}
}
