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
package teleports.ElrokiTeleporters;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Plim
 * Original python script by kerberos_20
 */
public class ElrokiTeleporters extends Quest
{
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return null;
		
		switch (npc.getNpcId())
		{
			case 32111:
				if (player.isInCombat())
					return "32111-no.htm";
				else
					player.teleToLocation(4990,-1879,-3178);
				break;
			case 32112:
				player.teleToLocation(7557,-5513,-3221);
				break;
		}
		
		return htmltext;
	}
	
	public ElrokiTeleporters(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(32111);
		addTalkId(32111);
		addStartNpc(32112);
		addTalkId(32112);
	}
	
	public static void main(String[] args)
	{
		new ElrokiTeleporters(-1, ElrokiTeleporters.class.getSimpleName(), "teleports");
	}
}
