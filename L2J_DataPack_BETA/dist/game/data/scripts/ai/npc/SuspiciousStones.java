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
package ai.npc;

import quests.Q00114_ResurrectionOfAnOldManager.Q00114_ResurrectionOfAnOldManager;

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * Suspicious-Looking Pile of Stones AI
 * @author malyelfik
 */
public class SuspiciousStones extends AbstractNpcAI
{
	private static final int npcId = 32046;
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("check"))
		{
			for (L2PcInstance pl : npc.getKnownList().getKnownPlayers().values())
			{
				if (pl != null)
				{
					final QuestState st = pl.getQuestState(Q00114_ResurrectionOfAnOldManager.class.getSimpleName());
					if ((st != null) && st.isCond(17))
					{
						st.takeItems(8090, 1);
						st.giveItems(8091, 1);
						st.setCond(18, true);
						pl.sendPacket(new ExShowScreenMessage(NpcStringId.THE_RADIO_SIGNAL_DETECTOR_IS_RESPONDING_A_SUSPICIOUS_PILE_OF_STONES_CATCHES_YOUR_EYE, 2, 4500));
					}
				}
			}
		}
		return event;
	}
	
	public SuspiciousStones(String name, String descr)
	{
		super(name, descr);
		
		L2Npc npc = null;
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawnTable())
		{
			if ((spawn != null) && (spawn.getNpcid() == npcId))
			{
				npc = spawn.getLastSpawn();
			}
		}
		
		if (npc != null)
		{
			startQuestTimer("check", 1000, npc, null, true);
		}
		else
		{
			_log.warning("SuspiciousStones: Can't find npc!");
		}
	}
	
	public static void main(String[] args)
	{
		new SuspiciousStones(SuspiciousStones.class.getSimpleName(), "ai/npc");
	}
}