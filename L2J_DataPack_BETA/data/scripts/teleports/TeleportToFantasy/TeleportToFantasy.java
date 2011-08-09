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
package teleports.TeleportToFantasy;

import java.util.Map;
import javolution.util.FastMap;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * @author Plim
 * Original python script by Kerberos
 */
public class TeleportToFantasy extends Quest
{
	private static final int PADDIES = 32378;
	private static final Map<Integer, Integer> TELEPORTERS = new FastMap<Integer, Integer>();
	
	private static final int[][] RETURN_LOCS = 
	{
		{-80826,149775,-3043},
		{-12672,122776,-3116},
		{15670,142983,-2705},
		{83400,147943,-3404},
		{111409,219364,-3545},
		{82956,53162,-1495},
		{146331,25762,-2018},
		{116819,76994,-2714},
		{43835,-47749,-792},
		{147930,-55281,-2728},
		{87386,-143246,-1293},
		{12882,181053,-3560}
	};
	
	private static final int[][] ISLE_LOCS = 
	{
		{-58752,-56898,-2032},
		{-59716,-57868,-2032},
		{-60691,-56893,-2032},
		{-59720,-55921,-2032}
	};
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
			return null;
		
		if (TELEPORTERS.containsKey(npc.getNpcId()))
		{
			int random_id = st.getRandom(ISLE_LOCS.length);
			
			st.getPlayer().teleToLocation(ISLE_LOCS[random_id][0], ISLE_LOCS[random_id][1], ISLE_LOCS[random_id][2]);
			st.setState(State.STARTED);
			st.set("id", String.valueOf(TELEPORTERS.get(npc.getNpcId())));
		}
		
		else if (npc.getNpcId() == PADDIES)
		{
			if (st.getState() == State.STARTED && st.getInt("id") > 0)
			{
				int return_id = st.getInt("id") - 1;
		        st.getPlayer().teleToLocation(RETURN_LOCS[return_id][0],RETURN_LOCS[return_id][1],RETURN_LOCS[return_id][2]);
		        st.unset("id");
			}
			
			else
			{
				player.sendPacket(new NpcSay(npc.getObjectId(),0,npc.getNpcId(),1600019));
		        st.getPlayer().teleToLocation(43835,-47749,-792);
			}
			
			st.exitQuest(true);
		}
		
		return htmltext;
	}
	
	public TeleportToFantasy(int questId, String name, String descr)
	{
		super(questId, name, descr);

		TELEPORTERS.put(30059, 3); //TRISHA
		TELEPORTERS.put(30080, 4); //CLARISSA
		TELEPORTERS.put(30177, 6); //VALENTIA
		TELEPORTERS.put(30233, 8); //ESMERALDA
		TELEPORTERS.put(30256, 2); //BELLA
		TELEPORTERS.put(30320, 1); //RICHLIN
		TELEPORTERS.put(30848, 7); //ELISA
		TELEPORTERS.put(30899, 5); //FLAUEN
		TELEPORTERS.put(31320, 9); //ILYANA
		TELEPORTERS.put(31275, 10); //TATIANA
		TELEPORTERS.put(31964, 11); //BILIA
		
		for (int npcId : TELEPORTERS.keySet())
		{
			addStartNpc(npcId);
			addTalkId(npcId);
		}
		
		addStartNpc(PADDIES);
		addTalkId(PADDIES);
	}
	
	public static void main(String[] args)
	{
		new TeleportToFantasy(-1, TeleportToFantasy.class.getSimpleName(), "teleports");
	}
}
