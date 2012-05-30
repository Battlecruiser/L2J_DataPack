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
import ai.group_template.L2AttackableAIScript;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.GrandBossManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2GrandBossInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.zone.type.L2BossZone;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;

/**
 * Orfen AI
 * @author Emperorc
 */
public class Orfen extends L2AttackableAIScript
{
	//@formatter:off
	private static final Location[] Pos =
	{
		new Location(43728, 17220, -4342), 
		new Location(55024, 17368, -5412),
		new Location(53504, 21248, -5486), 
		new Location(53248, 24576, -5262)
	};
	
	private static final NpcStringId[] Text =
	{
		NpcStringId.S1_STOP_KIDDING_YOURSELF_ABOUT_YOUR_OWN_POWERLESSNESS,
		NpcStringId.S1_ILL_MAKE_YOU_FEEL_WHAT_TRUE_FEAR_IS, 
		NpcStringId.YOURE_REALLY_STUPID_TO_HAVE_CHALLENGED_ME_S1_GET_READY, 
		NpcStringId.S1_DO_YOU_THINK_THATS_GOING_TO_WORK
	};
	//@formatter:on
	
	private static final int ORFEN = 29014;
	// private static final int RAIKEL = 29015;
	private static final int RAIKEL_LEOS = 29016;
	// private static final int RIBA = 29017;
	private static final int RIBA_IREN = 29018;
	
	private static boolean _IsTeleported;
	private static List<L2Attackable> _Minions = new FastList<>();
	private static L2BossZone _Zone;
	
	private static final byte ALIVE = 0;
	private static final byte DEAD = 1;
	
	public Orfen(int id, String name, String descr)
	{
		super(id, name, descr);
		int[] mobs =
		{
			ORFEN, RAIKEL_LEOS, RIBA_IREN
		};
		registerMobs(mobs);
		_IsTeleported = false;
		_Zone = GrandBossManager.getInstance().getZone(Pos[0]);
		StatsSet info = GrandBossManager.getInstance().getStatsSet(ORFEN);
		int status = GrandBossManager.getInstance().getBossStatus(ORFEN);
		if (status == DEAD)
		{
			// load the unlock date and time for Orfen from DB
			long temp = info.getLong("respawn_time") - System.currentTimeMillis();
			// if Orfen is locked until a certain time, mark it so and start the unlock timer
			// the unlock time has not yet expired.
			if (temp > 0)
				startQuestTimer("orfen_unlock", temp, null, null);
			else
			{
				// the time has already expired while the server was offline. Immediately spawn Orfen.
				int i = getRandom(10);
				Location loc;
				if (i < 4)
				{
					loc = Pos[1];
				}
				else if (i < 7)
				{
					loc = Pos[2];
				}
				else
				{
					loc = Pos[3];
				}
				L2GrandBossInstance orfen = (L2GrandBossInstance) addSpawn(ORFEN, loc, false, 0);
				GrandBossManager.getInstance().setBossStatus(ORFEN, ALIVE);
				spawnBoss(orfen);
			}
		}
		else
		{
			int loc_x = info.getInteger("loc_x");
			int loc_y = info.getInteger("loc_y");
			int loc_z = info.getInteger("loc_z");
			int heading = info.getInteger("heading");
			int hp = info.getInteger("currentHP");
			int mp = info.getInteger("currentMP");
			L2GrandBossInstance orfen = (L2GrandBossInstance) addSpawn(ORFEN, loc_x, loc_y, loc_z, heading, false, 0);
			orfen.setCurrentHpMp(hp, mp);
			spawnBoss(orfen);
		}
	}
	
	public void setSpawnPoint(L2Npc npc, int index)
	{
		((L2Attackable) npc).clearAggroList();
		npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
		L2Spawn spawn = npc.getSpawn();
		spawn.setLocation(Pos[index]);
		npc.teleToLocation(Pos[index], false);
	}
	
	public void spawnBoss(L2GrandBossInstance npc)
	{
		GrandBossManager.getInstance().addBoss(npc);
		npc.broadcastPacket(new PlaySound(1, "BS01_A", 1, npc.getObjectId(), npc.getX(), npc.getY(), npc.getZ()));
		startQuestTimer("check_orfen_pos", 10000, npc, null, true);
		// Spawn minions
		int x = npc.getX();
		int y = npc.getY();
		L2Attackable mob;
		mob = (L2Attackable) addSpawn(RAIKEL_LEOS, x + 100, y + 100, npc.getZ(), 0, false, 0);
		mob.setIsRaidMinion(true);
		_Minions.add(mob);
		mob = (L2Attackable) addSpawn(RAIKEL_LEOS, x + 100, y - 100, npc.getZ(), 0, false, 0);
		mob.setIsRaidMinion(true);
		_Minions.add(mob);
		mob = (L2Attackable) addSpawn(RAIKEL_LEOS, x - 100, y + 100, npc.getZ(), 0, false, 0);
		mob.setIsRaidMinion(true);
		_Minions.add(mob);
		mob = (L2Attackable) addSpawn(RAIKEL_LEOS, x - 100, y - 100, npc.getZ(), 0, false, 0);
		mob.setIsRaidMinion(true);
		_Minions.add(mob);
		startQuestTimer("check_minion_loc", 10000, npc, null, true);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("orfen_unlock"))
		{
			int i = getRandom(10);
			Location loc;
			if (i < 4)
			{
				loc = Pos[1];
			}
			else if (i < 7)
			{
				loc = Pos[2];
			}
			else
			{
				loc = Pos[3];
			}
			L2GrandBossInstance orfen = (L2GrandBossInstance) addSpawn(ORFEN, loc, false, 0);
			GrandBossManager.getInstance().setBossStatus(ORFEN, ALIVE);
			spawnBoss(orfen);
		}
		else if (event.equalsIgnoreCase("check_orfen_pos"))
		{
			if ((_IsTeleported && npc.getCurrentHp() > npc.getMaxHp() * 0.95) || (!_Zone.isInsideZone(npc) && !_IsTeleported))
			{
				setSpawnPoint(npc, getRandom(3) + 1);
				_IsTeleported = false;
			}
			else if (_IsTeleported && !_Zone.isInsideZone(npc))
				setSpawnPoint(npc, 0);
		}
		else if (event.equalsIgnoreCase("check_minion_loc"))
		{
			for (int i = 0; i < _Minions.size(); i++)
			{
				L2Attackable mob = _Minions.get(i);
				if (!npc.isInsideRadius(mob, 3000, false, false))
				{
					mob.teleToLocation(npc.getX(), npc.getY(), npc.getZ());
					((L2Attackable) npc).clearAggroList();
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
				}
			}
		}
		else if (event.equalsIgnoreCase("despawn_minions"))
		{
			for (int i = 0; i < _Minions.size(); i++)
			{
				L2Attackable mob = _Minions.get(i);
				if (mob != null)
					mob.decayMe();
			}
			_Minions.clear();
		}
		else if (event.equalsIgnoreCase("spawn_minion"))
		{
			L2Attackable mob = (L2Attackable) addSpawn(RAIKEL_LEOS, npc.getX(), npc.getY(), npc.getZ(), 0, false, 0);
			mob.setIsRaidMinion(true);
			_Minions.add(mob);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		if (npc.getNpcId() == ORFEN)
		{
			L2Character originalCaster = isPet ? caster.getPet() : caster;
			if (skill.getAggroPoints() > 0 && getRandom(5) == 0 && npc.isInsideRadius(originalCaster, 1000, false, false))
			{
				NpcSay packet = new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), Text[getRandom(4)]);
				packet.addStringParameter(caster.getName().toString());
				npc.broadcastPacket(packet);
				originalCaster.teleToLocation(npc.getX(), npc.getY(), npc.getZ());
				npc.setTarget(originalCaster);
				npc.doCast(SkillTable.getInstance().getInfo(4064, 1));
			}
		}
		return super.onSkillSee(npc, caster, skill, targets, isPet);
	}
	
	@Override
	public String onFactionCall(L2Npc npc, L2Npc caller, L2PcInstance attacker, boolean isPet)
	{
		if (caller == null || npc == null || npc.isCastingNow())
			return super.onFactionCall(npc, caller, attacker, isPet);
		int npcId = npc.getNpcId();
		int callerId = caller.getNpcId();
		if (npcId == RAIKEL_LEOS && getRandom(20) == 0)
		{
			npc.setTarget(attacker);
			npc.doCast(SkillTable.getInstance().getInfo(4067, 4));
		}
		else if (npcId == RIBA_IREN)
		{
			int chance = 1;
			if (callerId == ORFEN)
				chance = 9;
			if (callerId != RIBA_IREN && caller.getCurrentHp() < (caller.getMaxHp() / 2.0) && getRandom(10) < chance)
			{
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
				npc.setTarget(caller);
				npc.doCast(SkillTable.getInstance().getInfo(4516, 1));
			}
		}
		return super.onFactionCall(npc, caller, attacker, isPet);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		int npcId = npc.getNpcId();
		if (npcId == ORFEN)
		{
			if (!_IsTeleported && (npc.getCurrentHp() - damage) < (npc.getMaxHp() / 2))
			{
				_IsTeleported = true;
				setSpawnPoint(npc, 0);
			}
			else if (npc.isInsideRadius(attacker, 1000, false, false) && !npc.isInsideRadius(attacker, 300, false, false) && getRandom(10) == 0)
			{
				NpcSay packet = new NpcSay(npc.getObjectId(), 0, npcId, Text[getRandom(3)]);
				packet.addStringParameter(attacker.getName().toString());
				npc.broadcastPacket(packet);
				attacker.teleToLocation(npc.getX(), npc.getY(), npc.getZ());
				npc.setTarget(attacker);
				npc.doCast(SkillTable.getInstance().getInfo(4064, 1));
			}
		}
		else if (npcId == RIBA_IREN)
		{
			if (!npc.isCastingNow() && (npc.getCurrentHp() - damage) < (npc.getMaxHp() / 2.0))
			{
				npc.setTarget(attacker);
				npc.doCast(SkillTable.getInstance().getInfo(4516, 1));
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		if (npc.getNpcId() == ORFEN)
		{
			npc.broadcastPacket(new PlaySound(1, "BS02_D", 1, npc.getObjectId(), npc.getX(), npc.getY(), npc.getZ()));
			GrandBossManager.getInstance().setBossStatus(ORFEN, DEAD);
			// time is 48hour +/- 20hour
			long respawnTime = (long) Config.Interval_Of_Orfen_Spawn + getRandom(Config.Random_Of_Orfen_Spawn);
			startQuestTimer("orfen_unlock", respawnTime, null, null);
			// also save the respawn time so that the info is maintained past reboots
			StatsSet info = GrandBossManager.getInstance().getStatsSet(ORFEN);
			info.set("respawn_time", System.currentTimeMillis() + respawnTime);
			GrandBossManager.getInstance().setStatsSet(ORFEN, info);
			cancelQuestTimer("check_minion_loc", npc, null);
			cancelQuestTimer("check_orfen_pos", npc, null);
			startQuestTimer("despawn_minions", 20000, null, null);
			cancelQuestTimers("spawn_minion");
		}
		else if (GrandBossManager.getInstance().getBossStatus(ORFEN) == ALIVE && npc.getNpcId() == RAIKEL_LEOS)
		{
			_Minions.remove(npc);
			startQuestTimer("spawn_minion", 360000, npc, null);
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		// Quest class and state definition
		new Orfen(-1, "orfen", "ai");
	}
}
