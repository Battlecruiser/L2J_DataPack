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
package teleports.NoblesseTeleport;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @author Plim
 * Original python script by Ham Wong
 */
public class NoblesseTeleport extends Quest
{
	private static final int[] NPCs = { 30006, 30059, 30080, 30134, 30146, 30177, 30233, 30256, 30320, 30540, 30576, 30836, 30848, 30878, 30899, 31275, 31320, 31964, 32163 };
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return null;
		
		if (event.equalsIgnoreCase("teleportWithToken"))
		{
			if (st.getQuestItemsCount(13722) > 0)
				npc.showChatWindow(player, 3);
			else
				return"noble-nopass.htm";
		}
		
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		
		if (st == null)
			return htmltext;
		
		if (player.isNoble())
			htmltext = "nobleteleporter.htm";
		else
			htmltext = "nobleteleporter-no.htm";
		
		return htmltext;
	}
	
	public NoblesseTeleport(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		for (int npcId : NPCs)
		{
			addStartNpc(npcId);
			addTalkId(npcId);
		}
	}
	
	public static void main(String[] args)
	{
		new NoblesseTeleport(-1, NoblesseTeleport.class.getSimpleName(), "teleports");
	}
}
