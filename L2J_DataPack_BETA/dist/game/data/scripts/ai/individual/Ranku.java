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
package ai.individual;

import java.util.HashSet;
import java.util.Set;

import ai.group_template.L2AttackableAIScript;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.MinionList;

/**
 * Ranku's AI.
 * @author GKR
 */
public class Ranku extends L2AttackableAIScript
{
	private static final int RANKU = 25542;
	private static final int MINION = 32305;
	private static final int MINION_2 = 25543;
	
	private static Set<Integer> myTrackingSet = new HashSet<>();
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("checkup") && (npc.getNpcId() == RANKU) && !npc.isDead())
		{
			for (L2MonsterInstance minion : ((L2MonsterInstance) npc).getMinionList().getSpawnedMinions())
			{
				if ((minion != null) && !minion.isDead() && myTrackingSet.contains(minion.getObjectId()))
				{
					L2PcInstance[] players = minion.getKnownList().getKnownPlayers().values().toArray(new L2PcInstance[minion.getKnownList().getKnownPlayers().size()]);
					L2PcInstance killer = players[getRandom(players.length)];
					minion.reduceCurrentHp(minion.getMaxHp() / 100, killer, null);
				}
			}
			startQuestTimer("checkup", 1000, npc, null);
		}
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet, L2Skill skill)
	{
		if (npc.getNpcId() == RANKU)
		{
			for (L2MonsterInstance minion : ((L2MonsterInstance) npc).getMinionList().getSpawnedMinions())
			{
				if ((minion != null) && !minion.isDead() && !myTrackingSet.contains(minion.getObjectId()))
				{
					minion.broadcastPacket(new NpcSay(minion.getObjectId(), Say2.NPC_ALL, minion.getNpcId(), NpcStringId.DONT_KILL_ME_PLEASE_SOMETHINGS_STRANGLING_ME));
					startQuestTimer("checkup", 1000, npc, null);
					synchronized (myTrackingSet)
					{
						myTrackingSet.add(minion.getObjectId());
					}
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isPet, skill);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == MINION)
		{
			if (myTrackingSet.contains(npc.getObjectId()))
			{
				synchronized (myTrackingSet)
				{
					myTrackingSet.remove(npc.getObjectId());
				}
			}
			
			final L2MonsterInstance master = ((L2MonsterInstance) npc).getLeader();
			if ((master != null) && !master.isDead())
			{
				L2MonsterInstance minion2 = MinionList.spawnMinion(master, MINION_2);
				minion2.teleToLocation(npc.getX(), npc.getY(), npc.getZ());
			}
		}
		else if (npc.getNpcId() == RANKU)
		{
			for (L2MonsterInstance minion : ((L2MonsterInstance) npc).getMinionList().getSpawnedMinions())
			{
				if (myTrackingSet.contains(minion.getObjectId()))
				{
					synchronized (myTrackingSet)
					{
						myTrackingSet.remove(minion.getObjectId());
					}
				}
			}
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public Ranku(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addAttackId(RANKU);
		addKillId(RANKU);
		addKillId(MINION);
	}
	
	public static void main(String[] args)
	{
		new Ranku(-1, "Ranku", "ai");
	}
}
