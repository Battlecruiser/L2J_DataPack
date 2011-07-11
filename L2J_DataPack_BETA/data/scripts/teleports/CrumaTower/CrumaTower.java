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
package teleports.CrumaTower;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Plim
 */
public class CrumaTower extends Quest
{
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return getNoQuestMsg(player);
		
		if (player.getLevel() > 55)
			htmltext = "30483.htm";
		else
			player.teleToLocation(17724,114004,-11672);
		
		return htmltext;
	}
	
	public CrumaTower(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(30483);
		addTalkId(30483);
	}
	
	public static void main(String[] args)
	{
		new CrumaTower(-1, CrumaTower.class.getSimpleName(), "teleports");
	}
}
