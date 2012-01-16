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

import java.util.Map;

import javolution.util.FastMap;
import ai.group_template.L2AttackableAIScript;

import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.skills.SkillHolder;
import com.l2jserver.util.Rnd;

/**
 * @author GKR
 */
public class DemonPrince extends L2AttackableAIScript
{
	private static final int DEMON_PRINCE = 25540;
	private static final int FIEND = 25541;
	
	private static final SkillHolder UD = new SkillHolder(5044, 2);
	private static final SkillHolder[] AOE =
	{
		new SkillHolder(5376, 4), new SkillHolder(5376, 5), new SkillHolder(5376, 6)
	};
	
	private static final Map<Integer, Boolean> _attackState = new FastMap<Integer, Boolean>();
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("cast") && (npc != null) && (npc.getNpcId() == FIEND) && !npc.isDead())
		{
			npc.doCast(AOE[Rnd.get(3)].getSkill());
		}
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet, L2Skill skill)
	{
		if ((npc.getNpcId() == DEMON_PRINCE) && !npc.isDead())
		{
			if (!_attackState.containsKey(npc.getObjectId()) && (npc.getCurrentHp() < (npc.getMaxHp() * 0.5)))
			{
				npc.doCast(UD.getSkill());
				spawnMinions(npc);
				_attackState.put(npc.getObjectId(), false);
			}
			else if ((npc.getCurrentHp() < (npc.getMaxHp() * 0.1)) && _attackState.containsKey(npc.getObjectId()) && (_attackState.get(npc.getObjectId()) == false))
			{
				npc.doCast(UD.getSkill());
				spawnMinions(npc);
				_attackState.put(npc.getObjectId(), true);
			}
			
			if (Rnd.get(1000) < 10)
			{
				spawnMinions(npc);
			}
		}
		return super.onAttack(npc, attacker, damage, isPet, skill);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == DEMON_PRINCE)
		{
			_attackState.remove(npc.getObjectId());
		}
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		if (npc.getNpcId() == FIEND)
		{
			startQuestTimer("cast", 15000, npc, null);
		}
		return super.onSpawn(npc);
	}
	
	private void spawnMinions(L2Npc master)
	{
		if ((master != null) && !master.isDead())
		{
			int instanceId = master.getInstanceId();
			int x = master.getX();
			int y = master.getY();
			int z = master.getZ();
			addSpawn(FIEND, x + 200, y, z, 0, false, 0, false, instanceId);
			addSpawn(FIEND, x - 200, y, z, 0, false, 0, false, instanceId);
			addSpawn(FIEND, x - 100, y - 140, z, 0, false, 0, false, instanceId);
			addSpawn(FIEND, x - 100, y + 140, z, 0, false, 0, false, instanceId);
			addSpawn(FIEND, x + 100, y - 140, z, 0, false, 0, false, instanceId);
			addSpawn(FIEND, x + 100, y + 140, z, 0, false, 0, false, instanceId);
		}
	}
	
	public DemonPrince(int id, String name, String descr)
	{
		super(id, name, descr);
		
		addAttackId(DEMON_PRINCE);
		addKillId(DEMON_PRINCE);
		addSpawnId(FIEND);
	}
	
	public static void main(String[] args)
	{
		new DemonPrince(-1, "DemonPrince", "ai");
	}
}
