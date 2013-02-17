/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.individual;

import java.util.Collection;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Gordon AI
 * @author TOFIZ
 */
public class Gordon extends AbstractNpcAI
{
	private static final int GORDON = 29095;
	
	private static int NPC_MOVE_X = 0;
	private static int NPC_MOVE_Y = 0;
	private static int IS_WALK_TO = 0;
	private static int NPC_BLOCK = 0;
	
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
	
	private static boolean IS_ATTACKED = false;
	private static boolean IS_SPAWNED = false;
	
	private Gordon(String name, String descr)
	{
		super(name, descr);
		addAttackId(GORDON);
		addKillId(GORDON);
		addSpawnId(GORDON);
		
		// wait 2 minutes after Start AI
		startQuestTimer("check_ai", 120000, null, null, true);
		IS_SPAWNED = false;
		IS_ATTACKED = false;
		IS_WALK_TO = 1;
		NPC_MOVE_X = 0;
		NPC_MOVE_Y = 0;
		NPC_BLOCK = 0;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		L2CharPosition loc = WALKS[IS_WALK_TO - 1];
		if (event.equalsIgnoreCase("time_isAttacked"))
		{
			IS_ATTACKED = false;
			if (npc.getNpcId() == GORDON)
			{
				npc.setWalking();
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
			}
		}
		else if (event.equalsIgnoreCase("check_ai"))
		{
			cancelQuestTimer("check_ai", null, null);
			if (IS_SPAWNED == false)
			{
				final L2Npc gordon = SpawnTable.getInstance().getFirstSpawn(GORDON).getLastSpawn();
				if (gordon != null)
				{
					IS_SPAWNED = true;
					((L2Attackable) gordon).setCanReturnToSpawnPoint(false);
					startQuestTimer("Start", 1000, gordon, null, true);
					return super.onAdvEvent(event, npc, player);
				}
			}
		}
		else if (event.equalsIgnoreCase("Start"))
		{
			if ((npc != null) && (IS_SPAWNED == true))
			{
				// check if player have Cursed Weapon and in radius
				if (npc.getNpcId() == GORDON)
				{
					Collection<L2PcInstance> chars = npc.getKnownList().getKnownPlayers().values();
					if ((chars != null) && (chars.size() > 0))
					{
						for (L2PcInstance pc : chars)
						{
							if (pc.isCursedWeaponEquipped() && pc.isInsideRadius(npc, 5000, false, false))
							{
								npc.setRunning();
								((L2Attackable) npc).addDamageHate(pc, 0, 9999);
								npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, pc);
								IS_ATTACKED = true;
								cancelQuestTimer("time_isAttacked", null, null);
								startQuestTimer("time_isAttacked", 180000, npc, null);
								return super.onAdvEvent(event, npc, player);
							}
						}
					}
				}
				// end check
				if (IS_ATTACKED == true)
				{
					return super.onAdvEvent(event, npc, player);
				}
				if ((npc.getNpcId() == GORDON) && ((npc.getX() - 50) <= loc.x) && ((npc.getX() + 50) >= loc.y) && ((npc.getY() - 50) <= loc.y) && ((npc.getY() + 50) >= loc.y))
				{
					IS_WALK_TO++;
					if (IS_WALK_TO > 55)
					{
						IS_WALK_TO = 1;
					}
					loc = WALKS[IS_WALK_TO - 1];
					npc.setWalking();
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
				}
				
				// Test for unblock Npc
				if ((npc.getX() != NPC_MOVE_X) && (npc.getY() != NPC_MOVE_Y))
				{
					NPC_MOVE_X = npc.getX();
					NPC_MOVE_Y = npc.getY();
					NPC_BLOCK = 0;
				}
				else if (npc.getNpcId() == GORDON)
				{
					NPC_BLOCK++;
					if (NPC_BLOCK > 2)
					{
						npc.teleToLocation(loc.x, loc.y, loc.z);
						return super.onAdvEvent(event, npc, player);
					}
					if (NPC_BLOCK > 0)
					{
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, loc);
					}
				}
				// End Test unblock Npc
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if ((npc.getNpcId() == GORDON) && (NPC_BLOCK == 0))
		{
			IS_SPAWNED = true;
			IS_WALK_TO = 1;
			((L2Attackable) npc).setCanReturnToSpawnPoint(false);
			startQuestTimer("Start", 1000, npc, null, true);
		}
		return super.onSpawn(npc);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isSummon)
	{
		if (npc.getNpcId() == GORDON)
		{
			IS_ATTACKED = true;
			cancelQuestTimer("time_isAttacked", null, null);
			startQuestTimer("time_isAttacked", 180000, npc, null);
			if (player != null)
			{
				npc.setRunning();
				((L2Attackable) npc).addDamageHate(player, 0, 100);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
			}
		}
		return super.onAttack(npc, player, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (npc.getNpcId() == GORDON)
		{
			cancelQuestTimer("Start", null, null);
			cancelQuestTimer("time_isAttacked", null, null);
			IS_SPAWNED = false;
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new Gordon(Gordon.class.getSimpleName(), "ai");
	}
}