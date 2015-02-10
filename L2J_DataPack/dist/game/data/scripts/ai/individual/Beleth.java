/*
 * Copyright (C) 2004-2015 L2J DataPack
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

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.data.xml.impl.DoorData;
import com.l2jserver.gameserver.instancemanager.GrandBossManager;
import com.l2jserver.gameserver.instancemanager.ZoneManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;
import com.l2jserver.gameserver.network.serverpackets.DoorStatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;
import com.l2jserver.gameserver.network.serverpackets.SpecialCamera;
import com.l2jserver.gameserver.network.serverpackets.StaticObject;
import com.l2jserver.gameserver.util.Util;

/**
 * Beleth's AI.
 * @author Treat
 */
public final class Beleth extends AbstractNpcAI
{
	protected static L2Npc CAMERA;
	protected static L2Npc CAMERA2;
	protected static L2Npc CAMERA3;
	protected static L2Npc CAMERA4;
	protected static L2Npc BELETH;
	protected static L2Npc PRIEST;
	protected static L2ZoneType ZONE = null;
	private static L2PcInstance BELETH_KILLER;
	private static boolean DEBUG = false;
	protected static boolean MOVIE = false;
	private static boolean ATTACKED = false;
	private static int ALLOW_OBJECT_ID = 0;
	private static int KILLED = 0;
	protected static ScheduledFuture<?> SPAWN_TIMER = null;
	protected static ArrayList<L2Npc> MINIONS = new ArrayList<>();
	private static SkillHolder BLEED = new SkillHolder(5495, 1);
	private static SkillHolder FIREBALL = new SkillHolder(5496, 1);
	private static SkillHolder HORN_OF_RISING = new SkillHolder(5497, 1);
	private static SkillHolder LIGHTENING = new SkillHolder(5499, 1);
	
	protected static final Location BELETH_SPAWN = new Location(16323, 213059, -9357, 49152);
	
	private Beleth()
	{
		super(Beleth.class.getSimpleName(), "ai/individual");
		ZONE = ZoneManager.getInstance().getZoneById(12018);
		addEnterZoneId(12018);
		registerMobs(29118, 29119);
		addStartNpc(32470);
		addTalkId(32470);
		addFirstTalkId(29128);
		StatsSet info = GrandBossManager.getInstance().getStatsSet(29118);
		int status = GrandBossManager.getInstance().getBossStatus(29118);
		if (status == 3)
		{
			long temp = (info.getLong("respawn_time") - System.currentTimeMillis());
			if (temp > 0)
			{
				ThreadPoolManager.getInstance().scheduleGeneral(new unlock(), temp);
			}
			else
			{
				GrandBossManager.getInstance().setBossStatus(29118, 0);
			}
		}
		else if (status != 0)
		{
			GrandBossManager.getInstance().setBossStatus(29118, 0);
		}
		DoorData.getInstance().getDoor(20240001).openMe();
	}
	
	protected static L2Npc spawn(int npcId, Location loc)
	{
		try
		{
			final L2Spawn spawn = new L2Spawn(npcId);
			spawn.setInstanceId(loc.getInstanceId());
			spawn.setLocation(loc);
			spawn.setAmount(spawn.getAmount() + 1);
			return spawn.doSpawn();
		}
		catch (Exception ignored)
		{
		}
		return null;
	}
	
	public static void startSpawnTask()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(1), DEBUG ? 10000 : 300000);
	}
	
	protected static class unlock implements Runnable
	{
		@Override
		public void run()
		{
			GrandBossManager.getInstance().setBossStatus(29118, 0);
			DoorData.getInstance().getDoor(20240001).openMe();
		}
	}
	
	private static class Cast implements Runnable
	{
		SkillHolder _skill;
		L2Npc _npc;
		
		public Cast(SkillHolder skill, L2Npc npc)
		{
			_skill = skill;
			_npc = npc;
		}
		
		@Override
		public void run()
		{
			if ((_npc != null) && !_npc.isDead() && !_npc.isCastingNow())
			{
				_npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
				_npc.doCast(_skill.getSkill());
			}
		}
	}
	
	private static class Spawn implements Runnable
	{
		private int _taskId = 0;
		
		public Spawn(int taskId)
		{
			_taskId = taskId;
		}
		
		@Override
		public void run()
		{
			try
			{
				int instanceId = 0;
				switch (_taskId)
				{
					case 1:
						MOVIE = true;
						for (L2Character npc : ZONE.getCharactersInside())
						{
							if (npc.isNpc())
							{
								npc.deleteMe();
							}
						}
						CAMERA = spawn(29120, new Location(16323, 213142, -9357, 0, instanceId));
						CAMERA2 = spawn(29121, new Location(16323, 210741, -9357, 0, instanceId));
						CAMERA3 = spawn(29122, new Location(16323, 213170, -9357, 0, instanceId));
						CAMERA4 = spawn(29123, new Location(16323, 214917, -9356, 0, instanceId));
						ZONE.broadcastPacket(new PlaySound(1, "BS07_A", 1, CAMERA.getObjectId(), CAMERA.getX(), CAMERA.getY(), CAMERA.getZ()));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 400, 75, -25, 0, 2500, 0, 0, 1, 0, 0));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 400, 75, -25, 0, 2500, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(2), 300);
						break;
					case 2:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 1800, -45, -45, 5000, 5000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(3), 4900);
						break;
					case 3:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 2500, -120, -45, 5000, 5000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(4), 4900);
						break;
					case 4:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA2, 2200, 130, 0, 0, 1500, -20, 15, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(5), 1400);
						break;
					case 5:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA2, 2300, 100, 0, 2000, 4500, 0, 10, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(6), 2500);
						break;
					case 6:
						L2DoorInstance door = DoorData.getInstance().getDoor(20240001);
						door.closeMe();
						ZONE.broadcastPacket(new StaticObject(door, false));
						ZONE.broadcastPacket(new DoorStatusUpdate(door));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(7), 1700);
						break;
					case 7:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA4, 1500, 210, 0, 0, 1500, 0, 0, 1, 0, 0));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA4, 900, 255, 0, 5000, 6500, 0, 10, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(8), 6000);
						break;
					case 8:
						spawn(29125, new Location(16323, 214917, -9356, 0, instanceId));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA4, 900, 255, 0, 0, 1500, 0, 10, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(9), 1000);
						break;
					case 9:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA4, 1000, 255, 0, 7000, 17000, 0, 25, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(10), 3000);
						break;
					case 10:
						BELETH = spawn(29118, new Location(16321, 214211, -9352, 49369, instanceId));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(11), 200);
						break;
					case 11:
						ZONE.broadcastPacket(new SocialAction(BELETH.getObjectId(), 1));
						for (int i = 0; i < 6; i++)
						{
							int x = (int) ((150 * Math.cos(i * 1.046666667)) + 16323);
							int y = (int) ((150 * Math.sin(i * 1.046666667)) + 213059);
							L2Npc minion = spawn(29119, new Location(x, y, -9357, 49152, BELETH.getInstanceId()));
							minion.setShowSummonAnimation(true);
							minion.decayMe();
							MINIONS.add(minion);
						}
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(12), 6800);
						break;
					case 12:
						ZONE.broadcastPacket(new SpecialCamera(BELETH, 0, 270, -5, 0, 4000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(13), 3500);
						break;
					case 13:
						ZONE.broadcastPacket(new SpecialCamera(BELETH, 800, 270, 10, 3000, 6000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(14), 5000);
						break;
					case 14:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA3, 100, 270, 15, 0, 5000, 0, 0, 1, 0, 0));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA3, 100, 270, 15, 0, 5000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(15), 100);
						break;
					case 15:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA3, 100, 270, 15, 3000, 6000, 0, 5, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(16), 1400);
						break;
					case 16:
						BELETH.teleToLocation(BELETH_SPAWN);
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(17), 200);
						break;
					case 17:
						ZONE.broadcastPacket(new MagicSkillUse(BELETH, BELETH, 5532, 1, 2000, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(18), 2000);
						break;
					case 18:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA3, 700, 270, 20, 1500, 8000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(19), 6900);
						break;
					case 19:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA3, 40, 260, 0, 0, 4000, 0, 0, 1, 0, 0));
						for (L2Npc blth : MINIONS)
						{
							blth.spawnMe();
						}
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(20), 3000);
						break;
					case 20:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA3, 40, 280, 0, 0, 4000, 5, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(21), 3000);
						break;
					case 21:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA3, 5, 250, 5, 0, 13000, 20, 15, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(22), 1000);
						break;
					case 22:
						ZONE.broadcastPacket(new SocialAction(BELETH.getObjectId(), 3));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(23), 4000);
						break;
					case 23:
						ZONE.broadcastPacket(new MagicSkillUse(BELETH, BELETH, 5533, 1, 2000, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(24), 6800);
						break;
					case 24:
						BELETH.deleteMe();
						for (L2Npc bel : MINIONS)
						{
							bel.deleteMe();
						}
						MINIONS.clear();
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(25), 1000);
						break;
					case 25:
						CAMERA.deleteMe();
						CAMERA2.deleteMe();
						CAMERA3.deleteMe();
						CAMERA4.deleteMe();
						MOVIE = false;
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(26), 60000);
						break;
					case 26:
						if (SPAWN_TIMER != null)
						{
							SPAWN_TIMER.cancel(false);
							setSpawnTimer(0);
						}
						SpawnBeleths();
						break;
					case 27:
						BELETH.doDie(null);
						CAMERA = spawn(29122, new Location(16323, 213170, -9357, 0, instanceId));
						CAMERA.broadcastPacket(new PlaySound(1, "BS07_D", 1, CAMERA.getObjectId(), CAMERA.getX(), CAMERA.getY(), CAMERA.getZ()));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 400, 290, 25, 0, 10000, 0, 0, 1, 0, 0));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 400, 290, 25, 0, 10000, 0, 0, 1, 0, 0));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 400, 110, 25, 4000, 10000, 0, 0, 1, 0, 0));
						ZONE.broadcastPacket(new SocialAction(BELETH.getObjectId(), 5));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(28), 4000);
						break;
					case 28:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 400, 295, 25, 4000, 5000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(29), 4500);
						break;
					case 29:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 400, 295, 10, 4000, 11000, 0, 25, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(30), 9000);
						break;
					case 30:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 250, 90, 25, 0, 1000, 0, 0, 1, 0, 0));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA, 250, 90, 25, 0, 10000, 0, 0, 1, 0, 0));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(31), 2000);
						break;
					case 31:
						PRIEST.spawnMe();
						BELETH.deleteMe();
						CAMERA2 = spawn(29121, new Location(14056, 213170, -9357, 0, instanceId));
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(32), 3500);
						break;
					case 32:
						ZONE.broadcastPacket(new SpecialCamera(CAMERA2, 800, 180, 0, 0, 4000, 0, 10, 1, 0, 0));
						ZONE.broadcastPacket(new SpecialCamera(CAMERA2, 800, 180, 0, 0, 4000, 0, 10, 1, 0, 0));
						L2DoorInstance door2 = DoorData.getInstance().getDoor(20240002);
						door2.openMe();
						ZONE.broadcastPacket(new StaticObject(door2, false));
						ZONE.broadcastPacket(new DoorStatusUpdate(door2));
						DoorData.getInstance().getDoor(20240003).openMe();
						ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(33), 4000);
						break;
					case 33:
						CAMERA.deleteMe();
						CAMERA2.deleteMe();
						MOVIE = false;
						break;
					case 333:
						BELETH = spawn(29118, new Location(16323, 213170, -9357, 49152));
						break;
				
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String onEnterZone(L2Character character, L2ZoneType zone)
	{
		if (((character.isPlayer()) && (GrandBossManager.getInstance().getBossStatus(29118) == 1)) || (DEBUG && (GrandBossManager.getInstance().getBossStatus(29118) != 2) && (character.isPlayer())))
		{
			startSpawnTask();
			GrandBossManager.getInstance().setBossStatus(29118, 2);
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (npc == null)
		{
			return super.onKill(npc, killer, isSummon);
		}
		
		if ((npc.getId() == 29118) && (killer != null))
		{
			setBelethKiller(1, killer);
			GrandBossManager.getInstance().setBossStatus(29118, 3);
			// Calculate Min and Max respawn times randomly.
			long respawnTime = Config.BELETH_SPAWN_INTERVAL + getRandom(-Config.BELETH_SPAWN_RANDOM, Config.BELETH_SPAWN_RANDOM);
			respawnTime *= 3600000;
			
			StatsSet info = GrandBossManager.getInstance().getStatsSet(29118);
			info.set("respawn_time", System.currentTimeMillis() + respawnTime);
			GrandBossManager.getInstance().setStatsSet(29118, info);
			ThreadPoolManager.getInstance().scheduleGeneral(new unlock(), respawnTime);
			deleteAll();
			npc.deleteMe();
			MOVIE = true;
			BELETH = spawn(29118, new Location(16323, 213170, -9357, 49152));
			BELETH.setIsInvul(true);
			BELETH.setIsImmobilized(true);
			BELETH.disableAllSkills();
			PRIEST = spawn(29128, new Location(BELETH));
			PRIEST.setShowSummonAnimation(true);
			PRIEST.decayMe();
			spawn(32470, new Location(12470, 215607, -9381, 49152));
			ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(27), 1000);
		}
		else if (npc.getId() == 29119)
		{
			if (npc.getObjectId() == ALLOW_OBJECT_ID)
			{
				MINIONS.remove(npc);
				KILLED++;
				if (KILLED >= 5)
				{
					deleteAll();
					setSpawnTimer(1);
				}
				else
				{
					ALLOW_OBJECT_ID = MINIONS.get(getRandom(MINIONS.size())).getObjectId();
					ATTACKED = false;
				}
			}
			else if (SPAWN_TIMER == null)
			{
				deleteAll();
				setSpawnTimer(2);
				KILLED = 0;
			}
			npc.abortCast();
			npc.setTarget(null);
			npc.deleteMe();
		}
		return null;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance player, Skill skill, L2Object[] targets, boolean isSummon)
	{
		if ((npc != null) && !npc.isDead() && ((npc.getId() == 29118) || (npc.getId() == 29119)) && !npc.isCastingNow() && skill.hasEffectType(L2EffectType.HEAL) && (getRandom(100) < 80))
		{
			npc.setTarget(player);
			npc.doCast(HORN_OF_RISING.getSkill());
		}
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (npc == null)
		{
			return super.onAttack(npc, attacker, damage, isSummon);
		}
		
		if ((npc.getId() == 29118) || (npc.getId() == 29119))
		{
			if ((npc.getObjectId() == ALLOW_OBJECT_ID) && !ATTACKED)
			{
				ATTACKED = true;
				L2Npc fakeBeleth = MINIONS.get(getRandom(MINIONS.size()));
				while (fakeBeleth.getObjectId() == ALLOW_OBJECT_ID)
				{
					fakeBeleth = MINIONS.get(getRandom(MINIONS.size()));
				}
				ZONE.broadcastPacket(new CreatureSay(fakeBeleth.getObjectId(), 0, fakeBeleth.getName(), "Miss text."));
			}
			if (getRandom(100) < 40)
			{
				return null;
			}
			final double distance = npc.calculateDistance(attacker, false, false);
			if ((distance > 500) || (getRandom(100) < 80))
			{
				for (L2Npc beleth : MINIONS)
				{
					if ((beleth != null) && !beleth.isDead() && Util.checkIfInRange(900, beleth, attacker, false) && !beleth.isCastingNow())
					{
						beleth.setTarget(attacker);
						beleth.doCast(FIREBALL.getSkill());
					}
				}
				if ((BELETH != null) && !BELETH.isDead() && Util.checkIfInRange(900, BELETH, attacker, false) && !BELETH.isCastingNow())
				{
					BELETH.setTarget(attacker);
					BELETH.doCast(FIREBALL.getSkill());
				}
			}
			else if (!npc.isDead() && !npc.isCastingNow())
			{
				if (!npc.getKnownList().getKnownPlayersInRadius(200).isEmpty())
				{
					npc.doCast(LIGHTENING.getSkill());
					return null;
				}
				((L2Attackable) npc).clearAggroList();
			}
		}
		return null;
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, Skill skill)
	{
		if ((npc != null) && !npc.isDead() && ((npc.getId() == 29118) || (npc.getId() == 29119)) && !npc.isCastingNow())
		{
			if ((player != null) && !player.isDead())
			{
				final double distance2 = npc.calculateDistance(player, false, false);
				if ((distance2 > 890) && !npc.isMovementDisabled())
				{
					npc.setTarget(player);
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
					double speed = npc.isRunning() ? npc.getRunSpeed() : npc.getWalkSpeed();
					int time = (int) (((distance2 - 890) / speed) * 1000);
					ThreadPoolManager.getInstance().scheduleGeneral(new Cast(FIREBALL, npc), time);
					
				}
				else if (distance2 < 890)
				{
					npc.setTarget(player);
					npc.doCast(FIREBALL.getSkill());
				}
				return null;
			}
			if (getRandom(100) < 40)
			{
				if (!npc.getKnownList().getKnownPlayersInRadius(200).isEmpty())
				{
					npc.doCast(LIGHTENING.getSkill());
					return null;
				}
			}
			for (L2PcInstance plr : npc.getKnownList().getKnownPlayersInRadius(950))
			{
				npc.setTarget(plr);
				npc.doCast(FIREBALL.getSkill());
				return null;
			}
			((L2Attackable) npc).clearAggroList();
		}
		return null;
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		if ((npc != null) && !npc.isDead() && ((npc.getId() == 29118) || (npc.getId() == 29119)) && !npc.isCastingNow() && !MOVIE)
		{
			if (getRandom(100) < 40)
			{
				if (!npc.getKnownList().getKnownPlayersInRadius(200).isEmpty())
				{
					npc.doCast(BLEED.getSkill());
					return null;
				}
			}
			npc.setTarget(player);
			npc.doCast(FIREBALL.getSkill());
		}
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if ((npc.getId() == 29118) || (npc.getId() == 29119))
		{
			npc.setRunning();
			if (!MOVIE && !npc.getKnownList().getKnownPlayersInRadius(300).isEmpty() && (getRandom(100) < 60))
			{
				npc.doCast(BLEED.getSkill());
			}
			if (npc.getId() == 29118)
			{
				npc.getSpawn().setRespawnDelay(0);// setOnKillDelay
			}
		}
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		final String html;
		if ((BELETH_KILLER != null) && (player.getObjectId() == BELETH_KILLER.getObjectId()))
		{
			player.addItem("Kill Beleth", 10314, 1, null, true);// giveItems(10314, 1, 0)
			setBelethKiller(0, player);
			html = "32470a.htm";
		}
		else
		{
			html = "32470b.htm";
		}
		return HtmCache.getInstance().getHtm(player.getHtmlPrefix(), "data/html/default/" + html);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return null;
	}
	
	private static void setBelethKiller(int event, L2PcInstance killer)
	{
		if (event == 0)
		{
			BELETH_KILLER = null;
		}
		else if (event == 1)
		{
			if (killer.getParty() != null)
			{
				if (killer.getParty().getCommandChannel() != null)
				{
					BELETH_KILLER = killer.getParty().getCommandChannel().getLeader();
				}
				else
				{
					BELETH_KILLER = killer.getParty().getLeader();
				}
			}
			else
			{
				BELETH_KILLER = killer;
			}
		}
	}
	
	protected static void setSpawnTimer(int event)
	{
		switch (event)
		{
			case 0:
				SPAWN_TIMER = null;
				break;
			case 1:
				SPAWN_TIMER = ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(333), 60000);
				break;
			case 2:
				SPAWN_TIMER = ThreadPoolManager.getInstance().scheduleGeneral(new Spawn(26), 60000);
				break;
			default:
				break;
		}
	}
	
	private static void deleteAll()
	{
		if ((MINIONS != null) && !MINIONS.isEmpty())
		{
			for (L2Npc npc : MINIONS)
			{
				if ((npc == null) || npc.isDead())
				{
					continue;
				}
				npc.abortCast();
				npc.setTarget(null);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
				npc.deleteMe();
			}
			MINIONS.clear();
		}
		ALLOW_OBJECT_ID = 0;
		ATTACKED = false;
	}
	
	protected static void SpawnBeleths()
	{
		int a = 0;
		L2Npc npc;
		for (int i = 0; i < 16; i++)
		{
			a++;
			int x = (int) ((650 * Math.cos(i * 0.39)) + 16323);
			int y = (int) ((650 * Math.sin(i * 0.39)) + 213170);
			npc = spawn(29119, new Location(x, y, -9357, 49152));
			MINIONS.add(npc);
			if (a >= 2)
			{
				npc.setIsOverloaded(true);
				a = 0;
			}
		}
		int[] xm = new int[16];
		int[] ym = new int[16];
		for (int i = 0; i < 4; i++)
		{
			xm[i] = (int) ((1700 * Math.cos((i * 1.57) + 0.78)) + 16323);
			ym[i] = (int) ((1700 * Math.sin((i * 1.57) + 0.78)) + 213170);
			npc = spawn(29119, new Location(xm[i], ym[i], -9357, 49152));
			npc.setIsOverloaded(true);
			MINIONS.add(npc);
		}
		xm[4] = (xm[0] + xm[1]) / 2;
		ym[4] = (ym[0] + ym[1]) / 2;
		npc = spawn(29119, new Location(xm[4], ym[4], -9357, 49152));
		npc.setIsOverloaded(true);
		MINIONS.add(npc);
		xm[5] = (xm[1] + xm[2]) / 2;
		ym[5] = (ym[1] + ym[2]) / 2;
		npc = spawn(29119, new Location(xm[5], ym[5], -9357, 49152));
		npc.setIsOverloaded(true);
		MINIONS.add(npc);
		xm[6] = (xm[2] + xm[3]) / 2;
		ym[6] = (ym[2] + ym[3]) / 2;
		npc = spawn(29119, new Location(xm[6], ym[6], -9357, 49152));
		npc.setIsOverloaded(true);
		MINIONS.add(npc);
		xm[7] = (xm[3] + xm[0]) / 2;
		ym[7] = (ym[3] + ym[0]) / 2;
		npc = spawn(29119, new Location(xm[7], ym[7], -9357, 49152));
		npc.setIsOverloaded(true);
		MINIONS.add(npc);
		xm[8] = (xm[0] + xm[4]) / 2;
		ym[8] = (ym[0] + ym[4]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[8], ym[8], -9357, 49152)));
		xm[9] = (xm[4] + xm[1]) / 2;
		ym[9] = (ym[4] + ym[1]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[9], ym[9], -9357, 49152)));
		xm[10] = (xm[1] + xm[5]) / 2;
		ym[10] = (ym[1] + ym[5]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[10], ym[10], -9357, 49152)));
		xm[11] = (xm[5] + xm[2]) / 2;
		ym[11] = (ym[5] + ym[2]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[11], ym[11], -9357, 49152)));
		xm[12] = (xm[2] + xm[6]) / 2;
		ym[12] = (ym[2] + ym[6]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[12], ym[12], -9357, 49152)));
		xm[13] = (xm[6] + xm[3]) / 2;
		ym[13] = (ym[6] + ym[3]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[13], ym[13], -9357, 49152)));
		xm[14] = (xm[3] + xm[7]) / 2;
		ym[14] = (ym[3] + ym[7]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[14], ym[14], -9357, 49152)));
		xm[15] = (xm[7] + xm[0]) / 2;
		ym[15] = (ym[7] + ym[0]) / 2;
		MINIONS.add(spawn(29119, new Location(xm[15], ym[15], -9357, 49152)));
		ALLOW_OBJECT_ID = MINIONS.get(getRandom(MINIONS.size())).getObjectId();
		ATTACKED = false;
	}
	
	public static void main(String[] args)
	{
		new Beleth();
	}
}
