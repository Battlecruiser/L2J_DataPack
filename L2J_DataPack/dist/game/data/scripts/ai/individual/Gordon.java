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

import java.util.Collection;

import ai.group_template.L2AttackableAIScript;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Gordon AI
 * @author TOFIZ
 */
public class Gordon extends L2AttackableAIScript
{
	private static final int GORDON = 29095;
	private static int _npcMoveX = 0;
	private static int _npcMoveY = 0;
	private static int _isWalkTo = 0;
	private static int _npcBlock = 0;
	private static final L2CharPosition[] WALKS = 
	{
		new L2CharPosition(141569, -45908, -2387, 0),
		new L2CharPosition(142494, -45456, -2397, 0),
		new L2CharPosition(142922, -44561, -2395, 0),
		new L2CharPosition(143672, -44130, -2398, 0),
		new L2CharPosition(144557, -43378, -2325, 0),
		new L2CharPosition(145839, -43267, -2301, 0),
		new L2CharPosition(147044, -43601, -2307, 0),
		new L2CharPosition(148140, -43206, -2303, 0),
		new L2CharPosition(148815, -43434, -2328, 0),
		new L2CharPosition(149862, -44151, -2558, 0),
		new L2CharPosition(151037, -44197, -2708, 0),
		new L2CharPosition(152555, -42756, -2836, 0),
		new L2CharPosition(154808, -39546, -3236, 0),
		new L2CharPosition(155333, -39962, -3272, 0),
		new L2CharPosition(156531, -41240, -3470, 0),
		new L2CharPosition(156863, -43232, -3707, 0),
		new L2CharPosition(156783, -44198, -3764, 0),
		new L2CharPosition(158169, -45163, -3541, 0),
		new L2CharPosition(158952, -45479, -3473, 0),
		new L2CharPosition(160039, -46514, -3634, 0),
		new L2CharPosition(160244, -47429, -3656, 0),
		new L2CharPosition(159155, -48109, -3665, 0),
		new L2CharPosition(159558, -51027, -3523, 0),
		new L2CharPosition(159396, -53362, -3244, 0),
		new L2CharPosition(160872, -56556, -2789, 0),
		new L2CharPosition(160857, -59072, -2613, 0),
		new L2CharPosition(160410, -59888, -2647, 0),
		new L2CharPosition(158770, -60173, -2673, 0),
		new L2CharPosition(156368, -59557, -2638, 0),
		new L2CharPosition(155188, -59868, -2642, 0),
		new L2CharPosition(154118, -60591, -2731, 0),
		new L2CharPosition(153571, -61567, -2821, 0),
		new L2CharPosition(153457, -62819, -2886, 0),
		new L2CharPosition(152939, -63778, -3003, 0),
		new L2CharPosition(151816, -64209, -3120, 0),
		new L2CharPosition(147655, -64826, -3433, 0),
		new L2CharPosition(145422, -64576, -3369, 0),
		new L2CharPosition(144097, -64320, -3404, 0),
		new L2CharPosition(140780, -61618, -3096, 0),
		new L2CharPosition(139688, -61450, -3062, 0),
		new L2CharPosition(138267, -61743, -3056, 0),
		new L2CharPosition(138613, -58491, -3465, 0),
		new L2CharPosition(138139, -57252, -3517, 0),
		new L2CharPosition(139555, -56044, -3310, 0),
		new L2CharPosition(139107, -54537, -3240, 0),
		new L2CharPosition(139279, -53781, -3091, 0),
		new L2CharPosition(139810, -52687, -2866, 0),
		new L2CharPosition(139657, -52041, -2793, 0),
		new L2CharPosition(139215, -51355, -2698, 0),
		new L2CharPosition(139334, -50514, -2594, 0),
		new L2CharPosition(139817, -49715, -2449, 0),
		new L2CharPosition(139824, -48976, -2263, 0),
		new L2CharPosition(140130, -47578, -2213, 0),
		new L2CharPosition(140483, -46339, -2382, 0),
		new L2CharPosition(141569, -45908, -2387, 0)
	};
	
	private static boolean _isAttacked = false;
	private static boolean _isSpawned = false;
	
	public Gordon(int id, String name, String descr)
	{
		super(id, name, descr);
		int[] mobs =
		{
			GORDON
		};
		registerMobs(mobs, QuestEventType.ON_ATTACK, QuestEventType.ON_KILL, QuestEventType.ON_SPAWN);
		// wait 2 minutes after Start AI
		startQuestTimer("check_ai", 120000, null, null, true);
		
		_isSpawned = false;
		_isAttacked = false;
		_isWalkTo = 1;
		_npcMoveX = 0;
		_npcMoveY = 0;
		_npcBlock = 0;
	}
	
	public L2Npc findTemplate(int npcId)
	{
		L2Npc npc = null;
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawnTable())
		{
			if (spawn != null && spawn.getNpcid() == npcId)
			{
				npc = spawn.getLastSpawn();
				break;
			}
		}
		return npc;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		L2CharPosition loc = WALKS[_isWalkTo - 1];
		if (event.equalsIgnoreCase("time_isAttacked"))
		{
			_isAttacked = false;
			if (npc.getNpcId() == GORDON)
			{
				npc.setWalking();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
			}
		}
		else if (event.equalsIgnoreCase("check_ai"))
		{
			cancelQuestTimer("check_ai", null, null);
			if (_isSpawned == false)
			{
				L2Npc gordon_ai = findTemplate(GORDON);
				if (gordon_ai != null)
				{
					_isSpawned = true;
					startQuestTimer("Start", 1000, gordon_ai, null, true);
					return super.onAdvEvent(event, npc, player);
				}
			}
		}
		else if (event.equalsIgnoreCase("Start"))
		{
			if (npc != null && _isSpawned == true)
			{
				// check if player have Cursed Weapon and in radius
				if (npc.getNpcId() == GORDON)
				{
					Collection<L2PcInstance> chars = npc.getKnownList().getKnownPlayers().values();
					if (chars != null && chars.size() > 0)
					{
						for (L2PcInstance pc : chars)
						{
							if (pc.isCursedWeaponEquipped() && pc.isInsideRadius(npc, 5000, false, false))
							{
								npc.setRunning();
								((L2Attackable) npc).addDamageHate(pc, 0, 9999);
								npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, pc);
								_isAttacked = true;
								cancelQuestTimer("time_isAttacked", null, null);
								startQuestTimer("time_isAttacked", 180000, npc, null);
								return super.onAdvEvent(event, npc, player);
							}
						}
					}
				}
				// end check
				if (_isAttacked == true)
					return super.onAdvEvent(event, npc, player);
				if (npc.getNpcId() == GORDON && (npc.getX() - 50) <= loc.x && (npc.getX() + 50) >= loc.y && (npc.getY() - 50) <= loc.y && (npc.getY() + 50) >= loc.y)
				{
					_isWalkTo++;
					if (_isWalkTo > 55)
						_isWalkTo = 1;
					loc = WALKS[_isWalkTo - 1];
					npc.setWalking();
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
				}
				
				// Test for unblock Npc
				if (npc.getX() != _npcMoveX && npc.getY() != _npcMoveY)
				{
					_npcMoveX = npc.getX();
					_npcMoveY = npc.getY();
					_npcBlock = 0;
				}
				else if (npc.getNpcId() == GORDON)
				{
					_npcBlock++;
					if (_npcBlock > 2)
					{
						npc.teleToLocation(loc.x, loc.y, loc.z);
						return super.onAdvEvent(event, npc, player);
					}
					if (_npcBlock > 0)
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
				}
				// End Test unblock Npc
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc.getNpcId() == GORDON && _npcBlock == 0)
		{
			_isSpawned = true;
			_isWalkTo = 1;
			startQuestTimer("Start", 1000, npc, null, true);
		}
		return super.onSpawn(npc);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		if (npc.getNpcId() == GORDON)
		{
			_isAttacked = true;
			cancelQuestTimer("time_isAttacked", null, null);
			startQuestTimer("time_isAttacked", 180000, npc, null);
			if (player != null)
			{
				npc.setRunning();
				((L2Attackable) npc).addDamageHate(player, 0, 100);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
			}
		}
		return super.onAttack(npc, player, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == GORDON)
		{
			cancelQuestTimer("Start", null, null);
			cancelQuestTimer("time_isAttacked", null, null);
			_isSpawned = false;
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new Gordon(-1, Gordon.class.getSimpleName(), "ai");
	}
}