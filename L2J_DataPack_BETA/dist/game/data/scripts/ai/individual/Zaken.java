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

import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.GameTimeController;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.datatables.DoorTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.GrandBossManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.MountType;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2GrandBossInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.zone.type.L2BossZone;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;

/**
 * Zaken AI
 */
public class Zaken extends AbstractNpcAI
{
	private int _1001 = 0; // used for first cancel of QuestTimer "1001"
	private int _ai0 = 0; // used for zaken coords updater
	private final Location _loc = new Location(0, 0, 0);
	private int _ai4 = 0; // used for spawning minions cycles
	private int _quest0 = 0; // used for teleporting progress
	private int _quest1 = 0; // used for most hated players progress
	private int _quest2 = 0; // used for zaken HP check for teleport
	private L2PcInstance c_quest0 = null; // 1st player used for area teleport
	private L2PcInstance c_quest1 = null; // 2nd player used for area teleport
	private L2PcInstance c_quest2 = null; // 3rd player used for area teleport
	private L2PcInstance c_quest3 = null; // 4th player used for area teleport
	private L2PcInstance c_quest4 = null; // 5th player used for area teleport
	private static final int ZAKEN = 29022;
	private static final int doll_blader_b = 29023;
	private static final int vale_master_b = 29024;
	private static final int pirates_zombie_captain_b = 29026;
	private static final int pirates_zombie_b = 29027;
	private static final int[] Xcoords =
	{
		53950,
		55980,
		54950,
		55970,
		53930,
		55970,
		55980,
		54960,
		53950,
		53930,
		55970,
		55980,
		54960,
		53950,
		53930
	};
	
	private static final int[] Ycoords =
	{
		219860,
		219820,
		218790,
		217770,
		217760,
		217770,
		219920,
		218790,
		219860,
		217760,
		217770,
		219920,
		218790,
		219860,
		217760
	};
	
	private static final int[] Zcoords =
	{
		-3488,
		-3488,
		-3488,
		-3488,
		-3488,
		-3216,
		-3216,
		-3216,
		-3216,
		-3216,
		-2944,
		-2944,
		-2944,
		-2944,
		-2944
	};
	
	// ZAKEN Status Tracking :
	private static final byte ALIVE = 0; // Zaken is spawned.
	private static final byte DEAD = 1; // Zaken has been killed.
	
	private static L2BossZone _Zone;
	
	private Zaken(String name, String descr)
	{
		super(name, descr);
		
		// Zaken doors handling
		ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					if (getTimeHour() == 0)
					{
						DoorTable.getInstance().getDoor(21240006).openMe();
						ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									DoorTable.getInstance().getDoor(21240006).closeMe();
								}
								catch (Throwable e)
								{
									_log.warning("Cannot close door ID: 21240006 " + e);
								}
							}
						}, 300000L);
					}
				}
				catch (Throwable e)
				{
					_log.warning("Cannot open door ID: 21240006 " + e);
				}
			}
		}, 2000L, 600000L);
		
		int[] mobs =
		{
			ZAKEN,
			doll_blader_b,
			vale_master_b,
			pirates_zombie_captain_b,
			pirates_zombie_b
		};
		
		registerMobs(mobs);
		_Zone = GrandBossManager.getInstance().getZone(55312, 219168, -3223);
		
		StatsSet info = GrandBossManager.getInstance().getStatsSet(ZAKEN);
		int status = GrandBossManager.getInstance().getBossStatus(ZAKEN);
		if (status == DEAD)
		{
			// load the unlock date and time for zaken from DB
			long temp = info.getLong("respawn_time") - System.currentTimeMillis();
			// if zaken is locked until a certain time, mark it so and start the unlock timer
			// the unlock time has not yet expired.
			if (temp > 0)
			{
				startQuestTimer("zaken_unlock", temp, null, null);
			}
			else
			{
				// the time has already expired while the server was offline. Immediately spawn zaken.
				L2GrandBossInstance zaken = (L2GrandBossInstance) addSpawn(ZAKEN, 55312, 219168, -3223, 0, false, 0);
				GrandBossManager.getInstance().setBossStatus(ZAKEN, ALIVE);
				spawnBoss(zaken);
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
			L2GrandBossInstance zaken = (L2GrandBossInstance) addSpawn(ZAKEN, loc_x, loc_y, loc_z, heading, false, 0);
			zaken.setCurrentHpMp(hp, mp);
			spawnBoss(zaken);
		}
	}
	
	public void spawnBoss(L2GrandBossInstance npc)
	{
		if (npc == null)
		{
			_log.warning("Zaken AI failed to load, missing Zaken in grandboss_data.sql");
			return;
		}
		GrandBossManager.getInstance().addBoss(npc);
		
		npc.broadcastPacket(new PlaySound(1, "BS01_A", 1, npc.getObjectId(), npc.getX(), npc.getY(), npc.getZ()));
		_ai0 = 0;
		_loc.setLocation(npc.getLocation());
		_quest0 = 0;
		_quest1 = 0;
		_quest2 = 3;
		if (_Zone == null)
		{
			_log.warning("Zaken AI failed to load, missing zone for Zaken");
			return;
		}
		if (_Zone.isInsideZone(npc))
		{
			_ai4 = 1;
			startQuestTimer("1003", 1700, null, null, true);
		}
		_1001 = 1;
		startQuestTimer("1001", 1000, npc, null, true); // buffs,random teleports
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		int status = GrandBossManager.getInstance().getBossStatus(ZAKEN);
		if ((status == DEAD) && !event.equalsIgnoreCase("zaken_unlock"))
		{
			return super.onAdvEvent(event, npc, player);
		}
		
		if (event.equalsIgnoreCase("1001"))
		{
			if (_1001 == 1)
			{
				_1001 = 0;
				cancelQuestTimer("1001", npc, null);
			}
			int sk_4223 = 0;
			int sk_4227 = 0;
			for (L2Effect e : npc.getAllEffects())
			{
				if (e.getSkill().getId() == 4227)
				{
					sk_4227 = 1;
				}
				if (e.getSkill().getId() == 4223)
				{
					sk_4223 = 1;
				}
			}
			if (getTimeHour() < 5)
			{
				if (sk_4223 == 1) // use night face if zaken have day face
				{
					npc.setTarget(npc);
					npc.doCast(SkillTable.getInstance().getInfo(4224, 1));
					_loc.setLocation(npc.getLocation());
				}
				if (sk_4227 == 0) // use zaken regeneration
				{
					npc.setTarget(npc);
					npc.doCast(SkillTable.getInstance().getInfo(4227, 1));
				}
				if ((npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ATTACK) && (_ai0 == 0))
				{
					int i0 = 0;
					int i1 = 1;
					if (((L2Attackable) npc).getMostHated() != null)
					{
						if ((((((L2Attackable) npc).getMostHated().getX() - _loc.getX()) * (((L2Attackable) npc).getMostHated().getX() - _loc.getX())) + ((((L2Attackable) npc).getMostHated().getY() - _loc.getY()) * (((L2Attackable) npc).getMostHated().getY() - _loc.getY()))) > (1500 * 1500))
						{
							i0 = 1;
						}
						else
						{
							i0 = 0;
						}
						if (i0 == 0)
						{
							i1 = 0;
						}
						if (_quest0 > 0)
						{
							if (c_quest0 == null)
							{
								i0 = 0;
							}
							else if ((((c_quest0.getX() - _loc.getX()) * (c_quest0.getX() - _loc.getX())) + ((c_quest0.getY() - _loc.getY()) * (c_quest0.getY() - _loc.getY()))) > (1500 * 1500))
							{
								i0 = 1;
							}
							else
							{
								i0 = 0;
							}
							if (i0 == 0)
							{
								i1 = 0;
							}
						}
						if (_quest0 > 1)
						{
							if (c_quest1 == null)
							{
								i0 = 0;
							}
							else if ((((c_quest1.getX() - _loc.getX()) * (c_quest1.getX() - _loc.getX())) + ((c_quest1.getY() - _loc.getY()) * (c_quest1.getY() - _loc.getY()))) > (1500 * 1500))
							{
								i0 = 1;
							}
							else
							{
								i0 = 0;
							}
							if (i0 == 0)
							{
								i1 = 0;
							}
						}
						if (_quest0 > 2)
						{
							if (c_quest2 == null)
							{
								i0 = 0;
							}
							else if ((((c_quest2.getX() - _loc.getX()) * (c_quest2.getX() - _loc.getX())) + ((c_quest2.getY() - _loc.getY()) * (c_quest2.getY() - _loc.getY()))) > (1500 * 1500))
							{
								i0 = 1;
							}
							else
							{
								i0 = 0;
							}
							if (i0 == 0)
							{
								i1 = 0;
							}
						}
						if (_quest0 > 3)
						{
							if (c_quest3 == null)
							{
								i0 = 0;
							}
							else if ((((c_quest3.getX() - _loc.getX()) * (c_quest3.getX() - _loc.getX())) + ((c_quest3.getY() - _loc.getY()) * (c_quest3.getY() - _loc.getY()))) > (1500 * 1500))
							{
								i0 = 1;
							}
							else
							{
								i0 = 0;
							}
							if (i0 == 0)
							{
								i1 = 0;
							}
						}
						if (_quest0 > 4)
						{
							if (c_quest4 == null)
							{
								i0 = 0;
							}
							else if ((((c_quest4.getX() - _loc.getX()) * (c_quest4.getX() - _loc.getX())) + ((c_quest4.getY() - _loc.getY()) * (c_quest4.getY() - _loc.getY()))) > (1500 * 1500))
							{
								i0 = 1;
							}
							else
							{
								i0 = 0;
							}
							if (i0 == 0)
							{
								i1 = 0;
							}
						}
						if (i1 == 1)
						{
							_quest0 = 0;
							int i2 = getRandom(15);
							_loc.setX(Xcoords[i2] + getRandom(650));
							_loc.setY(Ycoords[i2] + getRandom(650));
							_loc.setZ(Zcoords[i2]);
							npc.setTarget(npc);
							npc.doCast(SkillTable.getInstance().getInfo(4222, 1));
						}
					}
				}
				if ((getRandom(20) < 1) && (_ai0 == 0))
				{
					_loc.setLocation(npc.getLocation());
				}
				L2Character c_ai0 = null;
				if ((npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ATTACK) && (_quest1 == 0))
				{
					if (((L2Attackable) npc).getMostHated() != null)
					{
						c_ai0 = ((L2Attackable) npc).getMostHated();
						_quest1 = 1;
					}
				}
				else if ((npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ATTACK) && (_quest1 != 0))
				{
					if (((L2Attackable) npc).getMostHated() != null)
					{
						if (c_ai0 == ((L2Attackable) npc).getMostHated())
						{
							_quest1 = (_quest1 + 1);
						}
						else
						{
							_quest1 = 1;
							c_ai0 = ((L2Attackable) npc).getMostHated();
						}
					}
				}
				if (npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_IDLE)
				{
					_quest1 = 0;
				}
				if (_quest1 > 5)
				{
					((L2Attackable) npc).stopHating(c_ai0);
					L2Character nextTarget = ((L2Attackable) npc).getMostHated();
					if (nextTarget != null)
					{
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, nextTarget);
					}
					_quest1 = 0;
				}
			}
			else if (sk_4223 == 0) // use day face if not night time
			{
				npc.setTarget(npc);
				npc.doCast(SkillTable.getInstance().getInfo(4223, 1));
				_quest2 = 3;
			}
			if (sk_4227 == 1) // when switching to day time, cancel zaken night regen
			{
				npc.setTarget(npc);
				npc.doCast(SkillTable.getInstance().getInfo(4242, 1));
			}
			if (getRandom(40) < 1)
			{
				int i2 = getRandom(15);
				_loc.setX(Xcoords[i2] + getRandom(650));
				_loc.setY(Ycoords[i2] + getRandom(650));
				_loc.setZ(Zcoords[i2]);
				npc.setTarget(npc);
				npc.doCast(SkillTable.getInstance().getInfo(4222, 1));
			}
			startQuestTimer("1001", 30000, npc, null, true);
		}
		if (event.equalsIgnoreCase("1002"))
		{
			_quest0 = 0;
			npc.doCast(SkillTable.getInstance().getInfo(4222, 1));
			_ai0 = 0;
		}
		if (event.equalsIgnoreCase("1003"))
		{
			if (_ai4 == 1)
			{
				int rr = getRandom(15);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, Xcoords[rr] + getRandom(650), Ycoords[rr] + getRandom(650), Zcoords[rr], getRandom(65536), false, 0)).setIsRaidMinion(true);
				_ai4 = 2;
			}
			else if (_ai4 == 2)
			{
				int rr = getRandom(15);
				((L2Attackable) addSpawn(doll_blader_b, Xcoords[rr] + getRandom(650), Ycoords[rr] + getRandom(650), Zcoords[rr], getRandom(65536), false, 0)).setIsRaidMinion(true);
				_ai4 = 3;
			}
			else if (_ai4 == 3)
			{
				((L2Attackable) addSpawn(vale_master_b, Xcoords[getRandom(15)] + getRandom(650), Ycoords[getRandom(15)] + getRandom(650), Zcoords[getRandom(15)], getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, Xcoords[getRandom(15)] + getRandom(650), Ycoords[getRandom(15)] + getRandom(650), Zcoords[getRandom(15)], getRandom(65536), false, 0)).setIsRaidMinion(true);
				_ai4 = 4;
			}
			else if (_ai4 == 4)
			{
				((L2Attackable) addSpawn(pirates_zombie_b, Xcoords[getRandom(15)] + getRandom(650), Ycoords[getRandom(15)] + getRandom(650), Zcoords[getRandom(15)], getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, Xcoords[getRandom(15)] + getRandom(650), Ycoords[getRandom(15)] + getRandom(650), Zcoords[getRandom(15)], getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, Xcoords[getRandom(15)] + getRandom(650), Ycoords[getRandom(15)] + getRandom(650), Zcoords[getRandom(15)], getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, Xcoords[getRandom(15)] + getRandom(650), Ycoords[getRandom(15)] + getRandom(650), Zcoords[getRandom(15)], getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, Xcoords[getRandom(15)] + getRandom(650), Ycoords[getRandom(15)] + getRandom(650), Zcoords[getRandom(15)], getRandom(65536), false, 0)).setIsRaidMinion(true);
				_ai4 = 5;
			}
			else if (_ai4 == 5)
			{
				((L2Attackable) addSpawn(doll_blader_b, 52675, 219371, -3290, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 52687, 219596, -3368, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 52672, 219740, -3418, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 52857, 219992, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 52959, 219997, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 53381, 220151, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 54236, 220948, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54885, 220144, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55264, 219860, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 55399, 220263, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55679, 220129, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 56276, 220783, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 57173, 220234, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 56267, 218826, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 56294, 219482, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 56094, 219113, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 56364, 218967, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 57113, 218079, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 56186, 217153, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55440, 218081, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 55202, 217940, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55225, 218236, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54973, 218075, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 53412, 218077, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54226, 218797, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54394, 219067, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54139, 219253, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 54262, 219480, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				_ai4 = 6;
			}
			else if (_ai4 == 6)
			{
				((L2Attackable) addSpawn(pirates_zombie_b, 53412, 218077, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54413, 217132, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 54841, 217132, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 55372, 217128, -3343, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 55893, 217122, -3488, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 56282, 217237, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 56963, 218080, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 56267, 218826, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 56294, 219482, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 56094, 219113, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 56364, 218967, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 56276, 220783, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 57173, 220234, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54885, 220144, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55264, 219860, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 55399, 220263, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55679, 220129, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 54236, 220948, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 54464, 219095, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54226, 218797, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54394, 219067, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54139, 219253, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 54262, 219480, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 53412, 218077, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55440, 218081, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 55202, 217940, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55225, 218236, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54973, 218075, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				_ai4 = 7;
			}
			else if (_ai4 == 7)
			{
				((L2Attackable) addSpawn(pirates_zombie_b, 54228, 217504, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54181, 217168, -3216, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 54714, 217123, -3168, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 55298, 217127, -3073, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 55787, 217130, -2993, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 56284, 217216, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 56963, 218080, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 56267, 218826, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 56294, 219482, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 56094, 219113, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 56364, 218967, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 56276, 220783, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 57173, 220234, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54885, 220144, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55264, 219860, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 55399, 220263, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55679, 220129, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 54236, 220948, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 54464, 219095, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54226, 218797, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(vale_master_b, 54394, 219067, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54139, 219253, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(doll_blader_b, 54262, 219480, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 53412, 218077, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 54280, 217200, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55440, 218081, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_captain_b, 55202, 217940, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 55225, 218236, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				((L2Attackable) addSpawn(pirates_zombie_b, 54973, 218075, -2944, getRandom(65536), false, 0)).setIsRaidMinion(true);
				_ai4 = 8;
				cancelQuestTimer("1003", null, null);
			}
		}
		
		else if (event.equalsIgnoreCase("zaken_unlock"))
		{
			L2GrandBossInstance zaken = (L2GrandBossInstance) addSpawn(ZAKEN, 55312, 219168, -3223, 0, false, 0);
			GrandBossManager.getInstance().setBossStatus(ZAKEN, ALIVE);
			spawnBoss(zaken);
		}
		else if (event.equalsIgnoreCase("CreateOnePrivateEx"))
		{
			((L2Attackable) addSpawn(npc.getId(), npc.getX(), npc.getY(), npc.getZ(), 0, false, 0)).setIsRaidMinion(true);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFactionCall(L2Npc npc, L2Npc caller, L2PcInstance attacker, boolean isSummon)
	{
		if ((caller == null) || (npc == null))
		{
			return super.onFactionCall(npc, caller, attacker, isSummon);
		}
		int npcId = npc.getId();
		int callerId = caller.getId();
		
		if ((getTimeHour() < 5) && (callerId != ZAKEN) && (npcId == ZAKEN))
		{
			int damage = 0; // well damage required :x
			if ((npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_IDLE) && (_ai0 == 0) && (damage < 10) && (getRandom((30 * 15)) < 1))// todo - damage missing
			{
				_ai0 = 1;
				_loc.setLocation(caller.getLocation());
				startQuestTimer("1002", 300, caller, null);
			}
		}
		return super.onFactionCall(npc, caller, attacker, isSummon);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (npc.getId() == ZAKEN)
		{
			int skillId = skill.getId();
			if (skillId == 4222)
			{
				npc.teleToLocation(_loc);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
			}
			else if (skillId == 4216)
			{
				int i1 = getRandom(15);
				player.teleToLocation(new Location(Xcoords[i1] + getRandom(650), Ycoords[i1] + getRandom(650), Zcoords[i1]));
				((L2Attackable) npc).stopHating(player);
				L2Character nextTarget = ((L2Attackable) npc).getMostHated();
				if (nextTarget != null)
				{
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, nextTarget);
				}
				
			}
			else if (skillId == 4217)
			{
				int i0 = 0;
				int i1 = getRandom(15);
				player.teleToLocation(new Location(Xcoords[i1] + getRandom(650), Ycoords[i1] + getRandom(650), Zcoords[i1]));
				((L2Attackable) npc).stopHating(player);
				
				if ((c_quest0 != null) && (_quest0 > 0) && (c_quest0 != player) && (c_quest0.getZ() > (player.getZ() - 100)) && (c_quest0.getZ() < (player.getZ() + 100)))
				{
					if ((((c_quest0.getX() - player.getX()) * (c_quest0.getX() - player.getX())) + ((c_quest0.getY() - player.getY()) * (c_quest0.getY() - player.getY()))) > (250 * 250))
					{
						i0 = 1;
					}
					else
					{
						i0 = 0;
					}
					if (i0 == 0)
					{
						i1 = getRandom(15);
						c_quest0.teleToLocation(new Location(Xcoords[i1] + getRandom(650), Ycoords[i1] + getRandom(650), Zcoords[i1]));
						((L2Attackable) npc).stopHating(c_quest0);
					}
				}
				if ((c_quest1 != null) && (_quest0 > 1) && (c_quest1 != player) && (c_quest1.getZ() > (player.getZ() - 100)) && (c_quest1.getZ() < (player.getZ() + 100)))
				{
					if ((((c_quest1.getX() - player.getX()) * (c_quest1.getX() - player.getX())) + ((c_quest1.getY() - player.getY()) * (c_quest1.getY() - player.getY()))) > (250 * 250))
					{
						i0 = 1;
					}
					else
					{
						i0 = 0;
					}
					if (i0 == 0)
					{
						i1 = getRandom(15);
						c_quest1.teleToLocation(new Location(Xcoords[i1] + getRandom(650), Ycoords[i1] + getRandom(650), Zcoords[i1]));
						((L2Attackable) npc).stopHating(c_quest1);
					}
				}
				if ((c_quest2 != null) && (_quest0 > 2) && (c_quest2 != player) && (c_quest2.getZ() > (player.getZ() - 100)) && (c_quest2.getZ() < (player.getZ() + 100)))
				{
					if ((((c_quest2.getX() - player.getX()) * (c_quest2.getX() - player.getX())) + ((c_quest2.getY() - player.getY()) * (c_quest2.getY() - player.getY()))) > (250 * 250))
					{
						i0 = 1;
					}
					else
					{
						i0 = 0;
					}
					if (i0 == 0)
					{
						i1 = getRandom(15);
						c_quest2.teleToLocation(new Location(Xcoords[i1] + getRandom(650), Ycoords[i1] + getRandom(650), Zcoords[i1]));
						((L2Attackable) npc).stopHating(c_quest2);
					}
				}
				if ((c_quest3 != null) && (_quest0 > 3) && (c_quest3 != player) && (c_quest3.getZ() > (player.getZ() - 100)) && (c_quest3.getZ() < (player.getZ() + 100)))
				{
					if ((((c_quest3.getX() - player.getX()) * (c_quest3.getX() - player.getX())) + ((c_quest3.getY() - player.getY()) * (c_quest3.getY() - player.getY()))) > (250 * 250))
					{
						i0 = 1;
					}
					else
					{
						i0 = 0;
					}
					if (i0 == 0)
					{
						i1 = getRandom(15);
						c_quest3.teleToLocation(new Location(Xcoords[i1] + getRandom(650), Ycoords[i1] + getRandom(650), Zcoords[i1]));
						((L2Attackable) npc).stopHating(c_quest3);
					}
				}
				if ((c_quest4 != null) && (_quest0 > 4) && (c_quest4 != player) && (c_quest4.getZ() > (player.getZ() - 100)) && (c_quest4.getZ() < (player.getZ() + 100)))
				{
					if ((((c_quest4.getX() - player.getX()) * (c_quest4.getX() - player.getX())) + ((c_quest4.getY() - player.getY()) * (c_quest4.getY() - player.getY()))) > (250 * 250))
					{
						i0 = 1;
					}
					else
					{
						i0 = 0;
					}
					if (i0 == 0)
					{
						i1 = getRandom(15);
						c_quest4.teleToLocation(new Location(Xcoords[i1] + getRandom(650), Ycoords[i1] + getRandom(650), Zcoords[i1]));
						((L2Attackable) npc).stopHating(c_quest4);
					}
				}
				L2Character nextTarget = ((L2Attackable) npc).getMostHated();
				if (nextTarget != null)
				{
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, nextTarget);
				}
			}
		}
		return super.onSpellFinished(npc, player, skill);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		int npcId = npc.getId();
		if (npcId == ZAKEN)
		{
			if (attacker.getMountType() == MountType.STRIDER)
			{
				int sk_4258 = 0;
				for (L2Effect e : attacker.getAllEffects())
				{
					if (e.getSkill().getId() == 4258)
					{
						sk_4258 = 1;
					}
				}
				if (sk_4258 == 0)
				{
					npc.setTarget(attacker);
					npc.doCast(SkillTable.getInstance().getInfo(4258, 1));
				}
			}
			L2Character originalAttacker = isSummon ? attacker.getSummon() : attacker;
			int hate = (int) (((damage / npc.getMaxHp()) / 0.05) * 20000);
			((L2Attackable) npc).addDamageHate(originalAttacker, 0, hate);
			if (getRandom(10) < 1)
			{
				int i0 = getRandom((15 * 15));
				if (i0 < 1)
				{
					npc.setTarget(attacker);
					npc.doCast(SkillTable.getInstance().getInfo(4216, 1));
				}
				else if (i0 < 2)
				{
					npc.setTarget(attacker);
					npc.doCast(SkillTable.getInstance().getInfo(4217, 1));
				}
				else if (i0 < 4)
				{
					npc.setTarget(attacker);
					npc.doCast(SkillTable.getInstance().getInfo(4219, 1));
				}
				else if (i0 < 8)
				{
					npc.setTarget(attacker);
					npc.doCast(SkillTable.getInstance().getInfo(4218, 1));
				}
				else if (i0 < 15)
				{
					for (L2Character character : npc.getKnownList().getKnownCharactersInRadius(100))
					{
						if (character != attacker)
						{
							continue;
						}
						if (attacker != ((L2Attackable) npc).getMostHated())
						{
							npc.setTarget(attacker);
							npc.doCast(SkillTable.getInstance().getInfo(4221, 1));
						}
					}
				}
				if (getRandom(2) < 1)
				{
					if (attacker == ((L2Attackable) npc).getMostHated())
					{
						npc.setTarget(attacker);
						npc.doCast(SkillTable.getInstance().getInfo(4220, 1));
					}
				}
			}
			if (getTimeHour() < 5)
			{
			}
			else if (npc.getCurrentHp() < ((npc.getMaxHp() * _quest2) / 4.0))
			{
				_quest2 = (_quest2 - 1);
				int i2 = getRandom(15);
				_loc.setX(Xcoords[i2] + getRandom(650));
				_loc.setY(Ycoords[i2] + getRandom(650));
				_loc.setZ(Zcoords[i2]);
				npc.setTarget(npc);
				npc.doCast(SkillTable.getInstance().getInfo(4222, 1));
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		int npcId = npc.getId();
		if (npcId == ZAKEN)
		{
			npc.broadcastPacket(new PlaySound(1, "BS02_D", 1, npc.getObjectId(), npc.getX(), npc.getY(), npc.getZ()));
			GrandBossManager.getInstance().setBossStatus(ZAKEN, DEAD);
			// Calculate Min and Max respawn times randomly.
			long respawnTime = Config.ZAKEN_SPAWN_INTERVAL + getRandom(-Config.ZAKEN_SPAWN_RANDOM, Config.ZAKEN_SPAWN_RANDOM);
			respawnTime *= 3600000;
			startQuestTimer("zaken_unlock", respawnTime, null, null);
			cancelQuestTimer("1001", npc, null);
			cancelQuestTimer("1003", npc, null);
			// also save the respawn time so that the info is maintained past reboots
			StatsSet info = GrandBossManager.getInstance().getStatsSet(ZAKEN);
			info.set("respawn_time", System.currentTimeMillis() + respawnTime);
			GrandBossManager.getInstance().setStatsSet(ZAKEN, info);
		}
		else if (GrandBossManager.getInstance().getBossStatus(ZAKEN) == ALIVE)
		{
			if (npcId != ZAKEN)
			{
				startQuestTimer("CreateOnePrivateEx", ((30 + getRandom(60)) * 1000), npc, null);
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isSummon)
	{
		int npcId = npc.getId();
		if (npcId == ZAKEN)
		{
			if (skill.getEffectPoint() > 0)
			{
				((L2Attackable) npc).addDamageHate(caster, 0, (((skill.getEffectPoint() / npc.getMaxHp()) * 10) * 150));
			}
			if (getRandom(12) < 1)
			{
				int i0 = getRandom((15 * 15));
				if (i0 < 1)
				{
					npc.setTarget(caster);
					npc.doCast(SkillTable.getInstance().getInfo(4216, 1));
				}
				else if (i0 < 2)
				{
					npc.setTarget(caster);
					npc.doCast(SkillTable.getInstance().getInfo(4217, 1));
				}
				else if (i0 < 4)
				{
					npc.setTarget(caster);
					npc.doCast(SkillTable.getInstance().getInfo(4219, 1));
				}
				else if (i0 < 8)
				{
					npc.setTarget(caster);
					npc.doCast(SkillTable.getInstance().getInfo(4218, 1));
				}
				else if (i0 < 15)
				{
					for (L2Character character : npc.getKnownList().getKnownCharactersInRadius(100))
					{
						if (character != caster)
						{
							continue;
						}
						if (caster != ((L2Attackable) npc).getMostHated())
						{
							npc.setTarget(caster);
							npc.doCast(SkillTable.getInstance().getInfo(4221, 1));
						}
					}
				}
				if (getRandom(2) < 1)
				{
					if (caster == ((L2Attackable) npc).getMostHated())
					{
						npc.setTarget(caster);
						npc.doCast(SkillTable.getInstance().getInfo(4220, 1));
					}
				}
			}
		}
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		int npcId = npc.getId();
		if (npcId == ZAKEN)
		{
			if (_Zone.isInsideZone(npc))
			{
				L2Character target = isSummon ? player.getSummon() : player;
				((L2Attackable) npc).addDamageHate(target, 1, 200);
			}
			if ((player.getZ() > (npc.getZ() - 100)) && (player.getZ() < (npc.getZ() + 100)))
			{
				if ((_quest0 < 5) && (getRandom(3) < 1))
				{
					if (_quest0 == 0)
					{
						c_quest0 = player;
					}
					else if (_quest0 == 1)
					{
						c_quest1 = player;
					}
					else if (_quest0 == 2)
					{
						c_quest2 = player;
					}
					else if (_quest0 == 3)
					{
						c_quest3 = player;
					}
					else if (_quest0 == 4)
					{
						c_quest4 = player;
					}
					_quest0++;
				}
				if (getRandom(15) < 1)
				{
					int i0 = getRandom((15 * 15));
					if (i0 < 1)
					{
						npc.setTarget(player);
						npc.doCast(SkillTable.getInstance().getInfo(4216, 1));
					}
					else if (i0 < 2)
					{
						npc.setTarget(player);
						npc.doCast(SkillTable.getInstance().getInfo(4217, 1));
					}
					else if (i0 < 4)
					{
						npc.setTarget(player);
						npc.doCast(SkillTable.getInstance().getInfo(4219, 1));
					}
					else if (i0 < 8)
					{
						npc.setTarget(player);
						npc.doCast(SkillTable.getInstance().getInfo(4218, 1));
					}
					else if (i0 < 15)
					{
						for (L2Character character : npc.getKnownList().getKnownCharactersInRadius(100))
						{
							if (character != player)
							{
								continue;
							}
							if (player != ((L2Attackable) npc).getMostHated())
							{
								npc.setTarget(player);
								npc.doCast(SkillTable.getInstance().getInfo(4221, 1));
							}
						}
					}
					if (getRandom(2) < 1)
					{
						if (player == ((L2Attackable) npc).getMostHated())
						{
							npc.setTarget(player);
							npc.doCast(SkillTable.getInstance().getInfo(4220, 1));
						}
					}
				}
			}
		}
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	public int getTimeHour()
	{
		return (GameTimeController.getInstance().getGameTime() / 60) % 24;
	}
	
	public static void main(String[] args)
	{
		new Zaken(Zaken.class.getSimpleName(), "ai");
	}
}
