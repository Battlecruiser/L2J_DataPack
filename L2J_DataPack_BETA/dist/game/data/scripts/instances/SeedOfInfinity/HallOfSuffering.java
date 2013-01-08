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
package instances.SeedOfInfinity;

import java.util.Calendar;
import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.ai.CtrlEvent;
import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * Seed of Infinity (Hall of Suffering) instance zone.<br>
 * TODO:
 * - after 15mins mobs are despawned
 * - bound instance to quests
 * 
 * Contributing authors: Gigiikun, ZakaX, Didldak
 * Please maintain consistency between the Seed scripts.
 */
public class HallOfSuffering extends Quest
{
	protected class HSWorld extends InstanceWorld
	{
		public           Map<L2Npc,Boolean> npcList                      = new FastMap<>();
		public           L2Npc klodekus                                  = null;
		public           L2Npc klanikus                                  = null;
		public           boolean isBossesAttacked                        = false;
		public           long startTime                                  = 0;
		public           String ptLeaderName                             = "";
		public           int rewardItemId                                = -1;
		public           String rewardHtm                                = "";
		public           boolean isRewarded                              = false;
	}
	
	private static final String qn = "SeedOfInfinity";
	private static final int INSTANCEID = 115; // this is the client number
	private static final boolean debug = false;
	
	//NPCs
	private static final int MOUTHOFEKIMUS = 32537;
	private static final int TEPIOS = 32530;
	// teleports
	private static final int[] ENTER_TELEPORT = {-187567,205570,-9538};
	//mobs
	private static final int KLODEKUS = 25665;
	private static final int KLANIKUS = 25666;
	private static final int TUMOR_ALIVE = 18704;
	private static final int TUMOR_DEAD = 18705;
	private static final int[] TUMOR_MOBIDS = {22509,22510,22511,22512,22513,22514,22515};
	private static final int[] TWIN_MOBIDS = {22509,22510,22511,22512,22513};
	// Doors/Walls/Zones
	private static final int[][] ROOM_1_MOBS = {
		{22509, -186296, 208200, -9544}, {22509, -186161, 208345, -9544}, {22509, -186296, 208403, -9544},
		{22510, -186107, 208113, -9528}, {22510, -186350, 208200, -9544}
	};
	private static final int[][] ROOM_2_MOBS = {
		{22511, -184433, 210953, -9536}, {22511, -184406, 211301, -9536}, {22509, -184541, 211272, -9544},
		{22510, -184244, 211098, -9536}, {22510, -184352, 211243, -9536}, {22510, -184298, 211330, -9528}
	};
	private static final int[][] ROOM_3_MOBS = {
		{22512, -182611, 213984, -9520}, {22512, -182908, 214071, -9520}, {22512, -182962, 213868, -9512},
		{22509, -182881, 213955, -9512}, {22511, -182827, 213781, -9504}, {22511, -182530, 213984, -9528},
		{22510, -182935, 213723, -9512}, {22510, -182557, 213868, -9520}
	};
	private static final int[][] ROOM_4_MOBS = {
		{22514, -180958, 216860, -9544}, {22514, -181012, 216628, -9536}, {22514, -181120, 216715, -9536},
		{22513, -180661, 216599, -9536}, {22513, -181039, 216599, -9536}, {22511, -180715, 216599, -9536},
		{22511, -181012, 216889, -9536}, {22512, -180931, 216918, -9536}, {22512, -180742, 216628, -9536}
	};
	private static final int[][] ROOM_5_MOBS = {
		{22512, -177372, 217854, -9536}, {22512, -177237, 218140, -9536}, {22512, -177021, 217647, -9528},
		{22513, -177372, 217792, -9544}, {22513, -177372, 218053, -9536}, {22514, -177291, 217734, -9544},
		{22514, -177264, 217792, -9544}, {22514, -177264, 218053, -9536}, {22515, -177156, 217792, -9536},
		{22515, -177075, 217647, -9528}
	};
	private static final int[][] TUMOR_SPAWNS = {
		{-186327,208286,-9544},{-184429,211155,-9544},{-182811,213871,-9496},
		{-181039,216633,-9528},{-177264,217760,-9544}
	};
	private static final int[][] TWIN_SPAWNS = {{25665,-173727,218169,-9536},{25666,-173727,218049,-9536}};
	private static final int[] TEPIOS_SPAWN = {-173727,218109,-9536};
	
	//etc
	private static final int BOSS_INVUL_TIME = 30000; // in milisex
	private static final int BOSS_MINION_SPAWN_TIME = 60000; // in milisex
	private static final int BOSS_RESSURECT_TIME = 20000; // in milisex
	// Instance reenter time
	// default: 24h
	private static final int INSTANCEPENALTY = 24;
	
	private boolean checkConditions(L2PcInstance player)
	{
		if (debug)
			return true;
		L2Party party = player.getParty();
		if (party == null)
		{
			player.sendPacket(SystemMessageId.NOT_IN_PARTY_CANT_ENTER);
			return false;
		}
		if (party.getLeader() != player)
		{
			player.sendPacket(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER);
			return false;
		}
		for (L2PcInstance partyMember : party.getMembers())
		{
			if (partyMember.getLevel() < 75)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
			if (!Util.checkIfInRange(1000, player, partyMember, true))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
			Long reentertime = InstanceManager.getInstance().getInstanceTime(partyMember.getObjectId(), INSTANCEID);
			if (System.currentTimeMillis() < reentertime)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_MAY_NOT_REENTER_YET);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
		}
		return true;
	}
	
	private void teleportPlayer(L2PcInstance player, int[] coords, int instanceId)
	{
		player.setInstanceId(instanceId);
		player.teleToLocation(coords[0], coords[1], coords[2]);
	}
	
	protected int enterInstance(L2PcInstance player, String template, int[] coords)
	{
		int instanceId = 0;
		//check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		//existing instance
		if (world != null)
		{
			if (!(world instanceof HSWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, coords, world.getInstanceId());
			return world.getInstanceId();
		}
		//New instance
		if (!checkConditions(player))
			return 0;
		L2Party party = player.getParty();
		instanceId = InstanceManager.getInstance().createDynamicInstance(template);
		world = new HSWorld();
		world.setInstanceId(instanceId);
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((HSWorld)world).startTime = System.currentTimeMillis();
		((HSWorld)world).ptLeaderName = player.getName();
		InstanceManager.getInstance().addWorld(world);
		_log.info("Hall Of Suffering started " + template + " Instance: " + instanceId + " created by player: " + player.getName());
		runTumors((HSWorld)world);
		
		// teleport players
		if (player.getParty() == null)
		{
			teleportPlayer(player, coords, instanceId);
			world.addAllowed(player.getObjectId());
		}
		else
		{
			for (L2PcInstance partyMember : party.getMembers())
			{
				teleportPlayer(partyMember, coords, instanceId);
				world.addAllowed(partyMember.getObjectId());
				if (partyMember.getQuestState(qn) == null)
					newQuestState(partyMember);
			}
		}
		return instanceId;
	}
	
	protected boolean checkKillProgress(L2Npc mob, HSWorld world)
	{
		if (world.npcList.containsKey(mob))
			world.npcList.put(mob, true);
		for(boolean isDead: world.npcList.values())
			if (!isDead)
				return false;
		return true;
	}
	
	protected int[][] getRoomSpawns(int room)
	{
		switch(room)
		{
			case 0:
				return ROOM_1_MOBS;
			case 1:
				return ROOM_2_MOBS;
			case 2:
				return ROOM_3_MOBS;
			case 3:
				return ROOM_4_MOBS;
			case 4:
				return ROOM_5_MOBS;
		}
		_log.warning("");
		return new int[][]{};
	}
	
	protected void runTumors(HSWorld world)
	{
		for (int[] mob : getRoomSpawns(world.getStatus()))
		{
			L2Npc npc = addSpawn(mob[0], mob[1], mob[2], mob[3], 0, false,0,false,world.getInstanceId());
			world.npcList.put(npc, false);
		}
		L2Npc mob = addSpawn(TUMOR_ALIVE, TUMOR_SPAWNS[world.getStatus()][0], TUMOR_SPAWNS[world.getStatus()][1], TUMOR_SPAWNS[world.getStatus()][2], 0, false,0,false,world.getInstanceId());
		mob.disableCoreAI(true);
		mob.setIsImmobilized(true);
		mob.setCurrentHp(mob.getMaxHp()*0.5);
		world.npcList.put(mob, false);
		world.incStatus();
	}
	
	protected void runTwins(HSWorld world)
	{
		world.incStatus();
		world.klodekus = addSpawn(TWIN_SPAWNS[0][0], TWIN_SPAWNS[0][1], TWIN_SPAWNS[0][2], TWIN_SPAWNS[0][3], 0, false,0,false,world.getInstanceId());
		world.klanikus = addSpawn(TWIN_SPAWNS[1][0], TWIN_SPAWNS[1][1], TWIN_SPAWNS[1][2], TWIN_SPAWNS[1][3], 0, false,0,false,world.getInstanceId());
		world.klanikus.setIsMortal(false);
		world.klodekus.setIsMortal(false);
	}
	
	protected void bossSimpleDie(L2Npc boss)
	{
		// killing is only possible one time
		synchronized (this)
		{
			if (boss.isDead()) return;
			// now reset currentHp to zero
			boss.setCurrentHp(0);
			boss.setIsDead(true);
		}
		
		// Set target to null and cancel Attack or Cast
		boss.setTarget(null);
		
		// Stop movement
		boss.stopMove(null);
		
		// Stop HP/MP/CP Regeneration task
		boss.getStatus().stopHpMpRegeneration();
		
		boss.stopAllEffectsExceptThoseThatLastThroughDeath();
		
		// Send the Server->Client packet StatusUpdate with current HP and MP to all other L2PcInstance to inform
		boss.broadcastStatusUpdate();
		
		// Notify L2Character AI
		boss.getAI().notifyEvent(CtrlEvent.EVT_DEAD);
		
		if (boss.getWorldRegion() != null)
			boss.getWorldRegion().onDeath(boss);
	}
	
	private void calcRewardItemId(HSWorld world)
	{
		Long finishDiff = System.currentTimeMillis() - world.startTime;
		if (finishDiff < 1260000)
		{
			world.rewardHtm = "32530-00.htm";
			world.rewardItemId = 13777;
		}
		else if (finishDiff < 1380000)
		{
			world.rewardHtm = "32530-01.htm";
			world.rewardItemId = 13778;
		}
		else if (finishDiff < 1500000)
		{
			world.rewardHtm = "32530-02.htm";
			world.rewardItemId = 13779;
		}
		else if (finishDiff < 1620000)
		{
			world.rewardHtm = "32530-03.htm";
			world.rewardItemId = 13780;
		}
		else if (finishDiff < 1740000)
		{
			world.rewardHtm = "32530-04.htm";
			world.rewardItemId = 13781;
		}
		else if (finishDiff < 1860000)
		{
			world.rewardHtm = "32530-05.htm";
			world.rewardItemId = 13782;
		}
		else if (finishDiff < 1980000)
		{
			world.rewardHtm = "32530-06.htm";
			world.rewardItemId = 13783;
		}
		else if (finishDiff < 2100000)
		{
			world.rewardHtm = "32530-07.htm";
			world.rewardItemId = 13784;
		}
		else if (finishDiff < 2220000)
		{
			world.rewardHtm = "32530-08.htm";
			world.rewardItemId = 13785;
		}
		else
		{
			world.rewardHtm = "32530-09.htm";
			world.rewardItemId = 13786;
		}
	}
	
	private String getPtLeaderText(L2PcInstance player, HSWorld world)
	{
		String htmltext = HtmCache.getInstance().getHtm(player.getHtmlPrefix(),"/data/scripts/instances/SeedOfInfinity/32530-10.htm");
		htmltext = htmltext.replaceAll("%ptLeader%", String.valueOf(world.ptLeaderName));
		return htmltext;
	}
	
	@Override
	public String onSkillSee (L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		if (skill.getSkillType() == L2SkillType.BALANCE_LIFE || skill.getSkillType() == L2SkillType.HEAL
				|| skill.getSkillType() == L2SkillType.HEAL_PERCENT || skill.getSkillType() == L2SkillType.HEAL_STATIC)
		{
			int hate = 2 * skill.getAggroPoints();
			if (hate < 2)
				hate = 1000;
			((L2Attackable)npc).addDamageHate(caster,0,hate);
		}
		return super.onSkillSee(npc, caster, skill, targets, isPet);
	}
	
	@Override
	public String onAdvEvent (String event, L2Npc npc, L2PcInstance player)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (tmpworld instanceof HSWorld)
		{
			HSWorld world = (HSWorld) tmpworld;
			if (event.equalsIgnoreCase("spawnBossGuards"))
			{
				if (!world.klanikus.isInCombat() && !world.klodekus.isInCombat())
				{
					world.isBossesAttacked = false;
					return "";
				}
				L2Npc mob = addSpawn(TWIN_MOBIDS[getRandom(TWIN_MOBIDS.length)], TWIN_SPAWNS[0][1], TWIN_SPAWNS[0][2], TWIN_SPAWNS[0][3], 0, false,0,false,npc.getInstanceId());
				((L2Attackable)mob).addDamageHate(((L2Attackable)npc).getMostHated(),0,1);
				if (getRandom(100) < 33)
				{
					mob = addSpawn(TWIN_MOBIDS[getRandom(TWIN_MOBIDS.length)], TWIN_SPAWNS[1][1], TWIN_SPAWNS[1][2], TWIN_SPAWNS[1][3], 0, false,0,false,npc.getInstanceId());
					((L2Attackable)mob).addDamageHate(((L2Attackable)npc).getMostHated(),0,1);
				}
				startQuestTimer("spawnBossGuards", BOSS_MINION_SPAWN_TIME, npc, null);
			}
			else if (event.equalsIgnoreCase("isTwinSeparated"))
			{
				if (Util.checkIfInRange(500, world.klanikus, world.klodekus, false))
				{
					world.klanikus.setIsInvul(false);
					world.klodekus.setIsInvul(false);
				}
				else
				{
					world.klanikus.setIsInvul(true);
					world.klodekus.setIsInvul(true);
				}
				startQuestTimer("isTwinSeparated", 10000, npc, null);
			}
			else if (event.equalsIgnoreCase("ressurectTwin"))
			{
				L2Skill skill = SkillTable.getInstance().getInfo(5824, 1);
				L2Npc aliveTwin = (world.klanikus == npc ? world.klodekus : world.klanikus);
				npc.doRevive();
				npc.doCast(skill);
				npc.setCurrentHp(aliveTwin.getCurrentHp());
				
				// get most hated of other boss
				L2Character hated = ((L2MonsterInstance)aliveTwin).getMostHated();
				if(hated != null) //to prevent revived idling
					npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, hated, 1000);
				
				aliveTwin.setIsInvul(true); //make other boss invul
				startQuestTimer("uninvul", BOSS_INVUL_TIME, aliveTwin, null);
			}
			else if(event.equals("uninvul"))
				npc.setIsInvul(false);
		}
		return "";
	}
	
	@Override
	public String onAttack (L2Npc npc, L2PcInstance attacker, int damage, boolean isPet, L2Skill skill)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (tmpworld instanceof HSWorld)
		{
			final HSWorld world = (HSWorld) tmpworld;
			if (!world.isBossesAttacked)
			{
				world.isBossesAttacked = true;
				Calendar reenter = Calendar.getInstance();
				reenter.add(Calendar.HOUR, INSTANCEPENALTY);
				
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.INSTANT_ZONE_S1_RESTRICTED);
				sm.addInstanceName(tmpworld.getTemplateId());
				
				// set instance reenter time for all allowed players
				for (int objectId : tmpworld.getAllowed())
				{
					L2PcInstance player = L2World.getInstance().getPlayer(objectId);
					if (player != null && player.isOnline())
					{
						InstanceManager.getInstance().setInstanceTime(objectId, tmpworld.getTemplateId(), reenter.getTimeInMillis());
						player.sendPacket(sm);
					}
				}
				startQuestTimer("spawnBossGuards", BOSS_MINION_SPAWN_TIME, npc, null);
				startQuestTimer("isTwinSeparated", 10000, npc, null);
			}
			else if (damage >= npc.getCurrentHp())
			{
				if (world.klanikus.isDead())
				{
					world.klanikus.setIsDead(false);
					world.klanikus.doDie(attacker);
					world.klodekus.doDie(attacker);
				}
				else if (((HSWorld)tmpworld).klodekus.isDead())
				{
					world.klodekus.setIsDead(false);
					world.klodekus.doDie(attacker);
					world.klanikus.doDie(attacker);
				}
				else
				{
					bossSimpleDie(npc);
					startQuestTimer("ressurectTwin", BOSS_RESSURECT_TIME, npc, null);
				}
			}
		}
		return null;
	}
	
	@Override
	public String onKill( L2Npc npc, L2PcInstance player, boolean isPet)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (tmpworld instanceof HSWorld)
		{
			HSWorld world = (HSWorld) tmpworld;
			
			if (npc.getNpcId() == TUMOR_ALIVE)
				addSpawn(TUMOR_DEAD, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), false, 0, false, npc.getInstanceId());
			if (world.getStatus() < 5)
			{
				if (checkKillProgress(npc, world))
					runTumors(world);
			}
			else if (world.getStatus() == 5)
			{
				if (checkKillProgress(npc, world))
					runTwins(world);
			}
			else if (world.getStatus() == 6 && (npc.getNpcId() == KLODEKUS || npc.getNpcId() == KLANIKUS))
			{
				if (world.klanikus.isDead() && world.klodekus.isDead())
				{
					world.incStatus();
					// instance end
					calcRewardItemId(world);
					world.klanikus = null;
					world.klodekus = null;
					this.cancelQuestTimers("ressurectTwin");
					this.cancelQuestTimers("spawnBossGuards");
					this.cancelQuestTimers("isTwinSeparated");
					addSpawn(TEPIOS, TEPIOS_SPAWN[0], TEPIOS_SPAWN[1], TEPIOS_SPAWN[2], 0, false,0,false,world.getInstanceId());
				}
			}
		}
		return "";
	}
	
	@Override
	public String onFirstTalk (L2Npc npc, L2PcInstance player)
	{
		if (npc.getNpcId() == TEPIOS)
		{
			InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
			if (((HSWorld)world).rewardItemId == -1)
			{
				_log.warning("Hall of Suffering: " + player.getName() + "(" + player.getObjectId() + ") is try to cheat!");
				return getPtLeaderText(player, (HSWorld)world);
			}
			else if (((HSWorld)world).isRewarded)
				return "32530-11.htm";
			else if (player.getParty() != null && player.getParty().getLeaderObjectId() == player.getObjectId())
				return ((HSWorld)world).rewardHtm;
			
			return getPtLeaderText(player, (HSWorld)world);
		}
		return "";
	}
	
	@Override
	public String onTalk (L2Npc npc, L2PcInstance player)
	{
		int npcId = npc.getNpcId();
		QuestState st = player.getQuestState(qn);
		if (st == null)
			st = newQuestState(player);
		if (npcId == MOUTHOFEKIMUS)
		{
			enterInstance(player, "HallOfSuffering.xml", ENTER_TELEPORT);
			return "";
		}
		else if (npcId == TEPIOS)
		{
			InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
			if (((HSWorld)world).rewardItemId == -1)
			{
				_log.warning("Hall of Suffering: " + player.getName() + "(" + player.getObjectId() + ") is try to cheat!");
				return getPtLeaderText(player, (HSWorld)world);
			}
			else if (((HSWorld)world).isRewarded)
				return "32530-11.htm";
			else if (player.getParty() != null && player.getParty().getLeaderObjectId() == player.getObjectId())
			{
				((HSWorld)world).isRewarded = true;
				for(L2PcInstance pl : player.getParty().getMembers())
				{
					st = pl.getQuestState(qn);
					if (st != null)
					{
						st.giveItems(736, 1);
						st.giveItems(((HSWorld)world).rewardItemId, 1);
					}
				}
				return "";
			}
			
			return getPtLeaderText(player, (HSWorld)world);
		}
		return "";
	}
	
	public HallOfSuffering(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(MOUTHOFEKIMUS);
		addTalkId(MOUTHOFEKIMUS);
		addStartNpc(TEPIOS);
		addFirstTalkId(TEPIOS);
		addTalkId(TEPIOS);
		addKillId(TUMOR_ALIVE);
		addKillId(KLODEKUS);
		addKillId(KLANIKUS);
		addAttackId(KLODEKUS);
		addAttackId(KLANIKUS);
		for(int mobId : TUMOR_MOBIDS)
		{
			addSkillSeeId(mobId);
			addKillId(mobId);
		}
	}
	
	public static void main(String[] args)
	{
		// now call the constructor (starts up the)
		new HallOfSuffering(-1,qn,"instances");
	}
}