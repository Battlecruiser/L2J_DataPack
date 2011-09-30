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

import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Manages Darion's Enforcer's and Darion's Executioner spawn/despawn
 * @author GKR
 */
public class Keltas extends Quest
{
	private static final int KELTAS = 22341;
	private static final int ENFORCER = 22342;
	private static final int EXECUTIONER = 22343;
	
	private L2MonsterInstance spawnedKeltas = null;
	
	private final List<L2Spawn> spawnedMonsters;
	
	private static final int[][] ENFORCER_SPAWN_POINTS =
	{
		{
			-24540, 251404, -3320
		},
		{
			-24100, 252578, -3060
		},
		{
			-24607, 252443, -3074
		},
		{
			-23962, 252041, -3275
		},
		{
			-24381, 252132, -3090
		},
		{
			-23652, 251838, -3370
		},
		{
			-23838, 252603, -3095
		},
		{
			-23257, 251671, -3360
		},
		{
			-27127, 251106, -3523
		},
		{
			-27118, 251203, -3523
		},
		{
			-27052, 251205, -3523
		},
		{
			-26999, 250818, -3523
		},
		{
			-29613, 252888, -3523
		},
		{
			-29765, 253009, -3523
		},
		{
			-29594, 252570, -3523
		},
		{
			-29770, 252658, -3523
		},
		{
			-27816, 252008, -3527
		},
		{
			-27930, 252011, -3523
		},
		{
			-28702, 251986, -3523
		},
		{
			-27357, 251987, -3527
		},
		{
			-28859, 251081, -3527
		},
		{
			-28607, 250397, -3523
		},
		{
			-28801, 250462, -3523
		},
		{
			-29123, 250387, -3472
		},
		{
			-25376, 252368, -3257
		},
		{
			-25376, 252208, -3257
		}
	};
	
	private static final int[][] EXECUTIONER_SPAWN_POINTS =
	{
		{
			-24419, 251395, -3340
		},
		{
			-24912, 252160, -3310
		},
		{
			-25027, 251941, -3300
		},
		{
			-24127, 252657, -3058
		},
		{
			-25120, 252372, -3270
		},
		{
			-24456, 252651, -3060
		},
		{
			-24844, 251614, -3295
		},
		{
			-28675, 252008, -3523
		},
		{
			-27943, 251238, -3523
		},
		{
			-27827, 251984, -3523
		},
		{
			-27276, 251995, -3523
		},
		{
			-28769, 251955, -3523
		},
		{
			-27969, 251073, -3523
		},
		{
			-27233, 250938, -3523
		},
		{
			-26835, 250914, -3523
		},
		{
			-26802, 251276, -3523
		},
		{
			-29671, 252781, -3527
		},
		{
			-29536, 252831, -3523
		},
		{
			-29419, 253214, -3523
		},
		{
			-27923, 251965, -3523
		},
		{
			-28499, 251882, -3527
		},
		{
			-28194, 251915, -3523
		},
		{
			-28358, 251078, -3527
		},
		{
			-28580, 251071, -3527
		},
		{
			-28492, 250704, -3523
		}
	};
	
	private void spawnMinions()
	{
		for (int[] pos : ENFORCER_SPAWN_POINTS)
		{
			L2MonsterInstance minion = (L2MonsterInstance) addSpawn(ENFORCER, pos[0], pos[1], pos[2], 0, false, 0, false);
			minion.getSpawn().setRespawnDelay(60);
			minion.getSpawn().setAmount(1);
			minion.getSpawn().startRespawn();
			spawnedMonsters.add(minion.getSpawn());
		}
		
		for (int[] pos : EXECUTIONER_SPAWN_POINTS)
		{
			L2MonsterInstance minion = (L2MonsterInstance) addSpawn(EXECUTIONER, pos[0], pos[1], pos[2], 0, false, 0, false);
			minion.getSpawn().setRespawnDelay(80);
			minion.getSpawn().setAmount(1);
			minion.getSpawn().startRespawn();
			spawnedMonsters.add(minion.getSpawn());
		}
	}
	
	private void despawnMinions()
	{
		if ((spawnedMonsters == null) || spawnedMonsters.isEmpty())
		{
			return;
		}
		
		for (L2Spawn spawn : spawnedMonsters)
		{
			spawn.stopRespawn();
			L2Npc minion = spawn.getLastSpawn();
			if ((minion != null) && !minion.isDead())
			{
				minion.deleteMe();
			}
		}
		spawnedMonsters.clear();
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("despawn"))
		{
			if ((spawnedKeltas != null) && !spawnedKeltas.isDead())
			{
				spawnedKeltas.broadcastPacket(new NpcSay(spawnedKeltas.getObjectId(), Say2.SHOUT, spawnedKeltas.getNpcId(), NpcStringId.THAT_IS_IT_FOR_TODAYLETS_RETREAT_EVERYONE_PULL_BACK));
				spawnedKeltas.deleteMe();
				spawnedKeltas.getSpawn().decreaseCount(spawnedKeltas);
				despawnMinions();
			}
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		cancelQuestTimers("despawn");
		despawnMinions();
		
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		if (!npc.isTeleporting())
		{
			spawnedKeltas = (L2MonsterInstance) npc;
			npc.broadcastPacket(new NpcSay(spawnedKeltas.getObjectId(), Say2.SHOUT, spawnedKeltas.getNpcId(), NpcStringId.GUYS_SHOW_THEM_OUR_POWER));
			spawnMinions();
			startQuestTimer("despawn", 1800000, null, null);
		}
		return super.onSpawn(npc);
	}
	
	public Keltas(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addKillId(KELTAS);
		addSpawnId(KELTAS);
		
		spawnedMonsters = new FastList<L2Spawn>();
	}
	
	public static void main(String[] args)
	{
		new Keltas(-1, "keltas", "ai");
	}
}
