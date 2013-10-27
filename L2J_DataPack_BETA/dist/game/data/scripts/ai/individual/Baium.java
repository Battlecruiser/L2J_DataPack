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

import static com.l2jserver.gameserver.ai.CtrlIntention.AI_INTENTION_FOLLOW;
import static com.l2jserver.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import javolution.util.FastList;
import ai.npc.AbstractNpcAI;

import com.l2jserver.Config;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.enums.MountType;
import com.l2jserver.gameserver.instancemanager.GrandBossManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DecoyInstance;
import com.l2jserver.gameserver.model.actor.instance.L2GrandBossInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.QuestTimer;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.zone.type.L2BossZone;
import com.l2jserver.gameserver.network.serverpackets.Earthquake;
import com.l2jserver.gameserver.network.serverpackets.MoveToPawn;
import com.l2jserver.gameserver.network.serverpackets.PlaySound;
import com.l2jserver.gameserver.util.Util;

/**
 * Baium's AI.
 * @author Fulminus
 */
public class Baium extends AbstractNpcAI
{
	// NPCs
	private static final int STONE_BAIUM = 29025;
	private static final int ANGELIC_VORTEX = 31862;
	private static final int LIVE_BAIUM = 29020;
	private static final int ARCHANGEL = 29021;
	private static final int TELEPORT_CUBIC = 31842;
	// Item
	private static final int BLOODED_FABRIC = 4295;
	// Baium status tracking
	private static final byte ASLEEP = 0; // baium is in the stone version, waiting to be woken up. Entry is unlocked
	private static final byte AWAKE = 1; // baium is awake and fighting. Entry is locked.
	private static final byte DEAD = 2; // baium has been killed and has not yet spawned. Entry is locked
	// Fixed archangel spawn location
	private static final Location[] ANGEL_LOCATION =
	{
		new Location(114239, 17168, 10080, 63544),
		new Location(115780, 15564, 10080, 13620),
		new Location(114880, 16236, 10080, 5400),
		new Location(115168, 17200, 10080, 0),
		new Location(115792, 16608, 10080, 0)
	};
	private static final Location[] TELEPORT_CUBIC_LOCATION = new Location[]
	{
		new Location(108784, 16000, -4928),
		new Location(113824, 10448, -5164),
		new Location(115488, 22096, -5168),
	};
	private static final Location BAIUM_DESPAWN = new Location(116033, 17447, 10104);
	private static final Location BAIUM_ENTER = new Location(113100, 14500, 10077);
	// Skills
	private static final SkillHolder GENERAL_ATTACK = new SkillHolder(4127, 1);
	private static final SkillHolder WIND_OF_FORCE = new SkillHolder(4128, 1);
	private static final SkillHolder EARTHQUAKE = new SkillHolder(4129, 1);
	private static final SkillHolder STRIKING_OF_THUNDERBOLT = new SkillHolder(4130, 1);
	private static final SkillHolder STUN = new SkillHolder(4131, 1);
	private static final SkillHolder BAIUM_HEAL = new SkillHolder(4135, 1);
	private static final SkillHolder HINDER_STRIDER = new SkillHolder(4258, 1);
	// private static final SkillHolder PRESENT_FROM_BAIUM = new SkillHolder(4136, 1);
	
	private long _LastAttackVsBaiumTime = 0;
	protected final List<L2Npc> _Minions = new ArrayList<>(5);
	private L2BossZone _Zone;
	
	private L2Character _target;
	private SkillHolder _skill;
	
	private Baium(String name, String descr)
	{
		super(name, descr);
		registerMobs(LIVE_BAIUM);
		// Quest NPC starter initialization
		addStartNpc(STONE_BAIUM, ANGELIC_VORTEX, TELEPORT_CUBIC);
		addTalkId(STONE_BAIUM, ANGELIC_VORTEX, TELEPORT_CUBIC);
		
		_Zone = GrandBossManager.getInstance().getZone(113100, 14500, 10077);
		StatsSet info = GrandBossManager.getInstance().getStatsSet(LIVE_BAIUM);
		int status = GrandBossManager.getInstance().getBossStatus(LIVE_BAIUM);
		if (status == DEAD)
		{
			// load the unlock date and time for baium from DB
			long temp = (info.getLong("respawn_time") - System.currentTimeMillis());
			if (temp > 0)
			{
				// the unlock time has not yet expired. Mark Baium as currently locked (dead). Setup a timer
				// to fire at the correct time (calculate the time between now and the unlock time,
				// setup a timer to fire after that many msec)
				startQuestTimer("baium_unlock", temp, null, null);
			}
			else
			{
				// the time has already expired while the server was offline. Delete the saved time and
				// immediately spawn the stone-baium. Also the state need not be changed from ASLEEP
				addSpawn(STONE_BAIUM, 116033, 17447, 10107, -25348, false, 0);
				GrandBossManager.getInstance().setBossStatus(LIVE_BAIUM, ASLEEP);
			}
		}
		else if (status == AWAKE)
		{
			int loc_x = info.getInt("loc_x");
			int loc_y = info.getInt("loc_y");
			int loc_z = info.getInt("loc_z");
			int heading = info.getInt("heading");
			final int hp = info.getInt("currentHP");
			final int mp = info.getInt("currentMP");
			L2GrandBossInstance baium = (L2GrandBossInstance) addSpawn(LIVE_BAIUM, loc_x, loc_y, loc_z, heading, false, 0);
			GrandBossManager.getInstance().addBoss(baium);
			final L2Npc _baium = baium;
			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						_baium.setCurrentHpMp(hp, mp);
						_baium.setIsInvul(true);
						_baium.setIsImmobilized(true);
						_baium.setRunning();
						_baium.broadcastSocialAction(2);
						startQuestTimer("baium_wakeup", 15000, _baium, null);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}, 100L);
		}
		else
		{
			addSpawn(STONE_BAIUM, 116033, 17447, 10107, -25348, false, 0);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "baium_unlock":
			{
				GrandBossManager.getInstance().setBossStatus(LIVE_BAIUM, ASLEEP);
				addSpawn(STONE_BAIUM, 116033, 17447, 10107, -25348, false, 0);
				break;
			}
			case "skill_range":
			{
				if (npc != null)
				{
					callSkillAI(npc);
				}
				break;
			}
			case "clean_player":
			{
				_target = getRandomTarget(npc);
				break;
			}
			case "baium_wakeup":
			{
				if ((npc != null) && (npc.getId() == LIVE_BAIUM))
				{
					npc.broadcastSocialAction(1);
					npc.broadcastPacket(new Earthquake(npc.getX(), npc.getY(), npc.getZ(), 40, 5));
					npc.broadcastPacket(new PlaySound(1, "BS02_A", 1, npc.getObjectId(), npc.getX(), npc.getY(), npc.getZ()));
					// start monitoring baium's inactivity
					_LastAttackVsBaiumTime = System.currentTimeMillis();
					startQuestTimer("baium_despawn", 60000, npc, null, true);
					startQuestTimer("skill_range", 500, npc, null, true);
					final L2Npc baium = npc;
					ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								baium.setIsInvul(false);
								baium.setIsImmobilized(false);
								for (L2Npc minion : _Minions)
								{
									minion.setShowSummonAnimation(false);
								}
							}
							catch (Exception e)
							{
								_log.log(Level.WARNING, "", e);
							}
						}
					}, 11100L);
					
					// TODO: Player that wake up Baium take damage.
					
					for (Location loc : ANGEL_LOCATION)
					{
						L2Npc angel = addSpawn(ARCHANGEL, loc, false, 0, true);
						angel.setIsInvul(true);
						_Minions.add(angel);
					}
				}
				// despawn the live baium after 30 minutes of inactivity
				// also check if the players are cheating, having pulled Baium outside his zone...
				break;
			}
			case "baium_despawn":
			{
				if ((npc != null) && (npc.getId() == LIVE_BAIUM))
				{
					// just in case the zone reference has been lost (somehow...), restore the reference
					if (_Zone == null)
					{
						_Zone = GrandBossManager.getInstance().getZone(113100, 14500, 10077);
					}
					if ((_LastAttackVsBaiumTime + 1800000) < System.currentTimeMillis())
					{
						npc.deleteMe(); // despawn the live-baium
						for (L2Npc minion : _Minions)
						{
							if (minion != null)
							{
								minion.getSpawn().stopRespawn();
								minion.deleteMe();
							}
						}
						_Minions.clear();
						addSpawn(STONE_BAIUM, 116033, 17447, 10107, -25348, false, 0); // spawn stone-baium
						GrandBossManager.getInstance().setBossStatus(LIVE_BAIUM, ASLEEP); // mark that Baium is not awake any more
						_Zone.oustAllPlayers();
						cancelQuestTimer("baium_despawn", npc, null);
					}
					else if (((_LastAttackVsBaiumTime + 300000) < System.currentTimeMillis()) && (npc.getCurrentHp() < ((npc.getMaxHp() * 3) / 4.0)))
					{
						npc.setIsCastingNow(false); // just in case
						npc.setTarget(npc);
						if (npc.isPhysicalMuted())
						{
							return super.onAdvEvent(event, npc, player);
						}
						npc.doCast(BAIUM_HEAL.getSkill());
						npc.setIsCastingNow(true);
					}
					else if (!_Zone.isInsideZone(npc))
					{
						npc.teleToLocation(BAIUM_DESPAWN);
					}
				}
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		if (_Zone == null)
		{
			_Zone = GrandBossManager.getInstance().getZone(113100, 14500, 10077);
		}
		if (_Zone == null)
		{
			return "<html><body>Angelic Vortex:<br>You may not enter while admin disabled this zone</body></html>";
		}
		
		switch (npc.getId())
		{
			case STONE_BAIUM:
			{
				if (GrandBossManager.getInstance().getBossStatus(LIVE_BAIUM) == ASLEEP)
				{
					if (_Zone.isPlayerAllowed(player))
					{
						// once Baium is awaken, no more people may enter until he dies, the server reboots, or
						// 30 minutes pass with no attacks made against Baium.
						GrandBossManager.getInstance().setBossStatus(LIVE_BAIUM, AWAKE);
						npc.deleteMe();
						L2GrandBossInstance baium = (L2GrandBossInstance) addSpawn(LIVE_BAIUM, npc, true);
						GrandBossManager.getInstance().addBoss(baium);
						final L2Npc _baium = baium;
						ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
						{
							@Override
							public void run()
							{
								try
								{
									_baium.setIsInvul(true);
									_baium.setRunning();
									_baium.broadcastSocialAction(2);
									startQuestTimer("baium_wakeup", 15000, _baium, null);
									_baium.setShowSummonAnimation(false);
								}
								catch (Throwable e)
								{
									_log.log(Level.WARNING, "", e);
								}
							}
						}, 100L);
					}
					else
					{
						htmltext = "Conditions are not right to wake up Baium";
					}
				}
				break;
			}
			case ANGELIC_VORTEX:
			{
				if (player.isFlying())
				{
					// print "Player "+player.getName()+" attempted to enter Baium's lair while flying!";
					return "<html><body>Angelic Vortex:<br>You may not enter while flying a wyvern</body></html>";
				}
				
				if ((GrandBossManager.getInstance().getBossStatus(LIVE_BAIUM) == ASLEEP) && hasQuestItems(player, BLOODED_FABRIC))
				{
					takeItems(player, BLOODED_FABRIC, 1);
					// allow entry for the player for the next 30 secs (more than enough time for the TP to happen)
					// Note: this just means 30secs to get in, no limits on how long it takes before we get out.
					_Zone.allowPlayerEntry(player, 30);
					player.teleToLocation(BAIUM_ENTER);
				}
				else
				{
					npc.showChatWindow(player, 1);
				}
				break;
			}
			case TELEPORT_CUBIC:
			{
				final Location loc = TELEPORT_CUBIC_LOCATION[getRandom(TELEPORT_CUBIC_LOCATION.length)];
				player.teleToLocation(loc.getX() + getRandom(100), loc.getY() + getRandom(100), loc.getZ());
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (npc.isInvul())
		{
			npc.getAI().setIntention(AI_INTENTION_IDLE);
			return null;
		}
		callSkillAI(npc);
		return super.onSpellFinished(npc, player, skill);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		npc.disableCoreAI(true);
		return super.onSpawn(npc);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (!_Zone.isInsideZone(attacker))
		{
			attacker.reduceCurrentHp(attacker.getCurrentHp(), attacker, false, false, null);
			return super.onAttack(npc, attacker, damage, isSummon);
		}
		if (npc.isInvul())
		{
			npc.getAI().setIntention(AI_INTENTION_IDLE);
			return super.onAttack(npc, attacker, damage, isSummon);
		}
		
		if (attacker.getMountType() == MountType.STRIDER)
		{
			if (!attacker.isAffectedBySkill(HINDER_STRIDER.getSkillId()))
			{
				npc.setTarget(attacker);
				if (npc.isMuted())
				{
					return super.onAttack(npc, attacker, damage, isSummon);
				}
				npc.doCast(HINDER_STRIDER.getSkill());
			}
		}
		// update a variable with the last action against baium
		_LastAttackVsBaiumTime = System.currentTimeMillis();
		callSkillAI(npc);
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		cancelQuestTimer("baium_despawn", npc, null);
		npc.broadcastPacket(new PlaySound(1, "BS01_D", 1, npc.getObjectId(), npc.getX(), npc.getY(), npc.getZ()));
		// spawn the "Teleportation Cubic" for 15 minutes (to allow players to exit the lair)
		addSpawn(TELEPORT_CUBIC, 115017, 15549, 10090, 0, false, 900000);
		// Calculate Min and Max respawn times randomly.
		long respawnTime = Config.BAIUM_SPAWN_INTERVAL + getRandom(-Config.BAIUM_SPAWN_RANDOM, Config.BAIUM_SPAWN_RANDOM);
		respawnTime *= 3600000;
		
		GrandBossManager.getInstance().setBossStatus(LIVE_BAIUM, DEAD);
		startQuestTimer("baium_unlock", respawnTime, null, null);
		// also save the respawn time so that the info is maintained past reboots
		StatsSet info = GrandBossManager.getInstance().getStatsSet(LIVE_BAIUM);
		info.set("respawn_time", (System.currentTimeMillis()) + respawnTime);
		GrandBossManager.getInstance().setStatsSet(LIVE_BAIUM, info);
		for (L2Npc minion : _Minions)
		{
			if (minion != null)
			{
				minion.getSpawn().stopRespawn();
				minion.deleteMe();
			}
		}
		_Minions.clear();
		final QuestTimer timer = getQuestTimer("skill_range", npc, null);
		if (timer != null)
		{
			timer.cancelAndRemove();
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	public L2Character getRandomTarget(L2Npc npc)
	{
		FastList<L2Character> result = FastList.newInstance();
		Collection<L2Object> objs = npc.getKnownList().getKnownObjects().values();
		{
			for (L2Object obj : objs)
			{
				if (obj.isPlayable() || (obj instanceof L2DecoyInstance))
				{
					if (obj.isPlayer())
					{
						if (obj.getActingPlayer().getAppearance().getInvisible())
						{
							continue;
						}
					}
					
					if (((((L2Character) obj).getZ() < (npc.getZ() - 100)) && (((L2Character) obj).getZ() > (npc.getZ() + 100))) || !(GeoData.getInstance().canSeeTarget(((L2Character) obj).getX(), ((L2Character) obj).getY(), ((L2Character) obj).getZ(), npc.getX(), npc.getY(), npc.getZ())))
					{
						continue;
					}
				}
				if (obj.isPlayable() || (obj instanceof L2DecoyInstance))
				{
					if (Util.checkIfInRange(9000, npc, obj, true) && !((L2Character) obj).isDead())
					{
						result.add((L2Character) obj);
					}
				}
			}
		}
		if (result.isEmpty())
		{
			for (L2Npc minion : _Minions)
			{
				if (minion != null)
				{
					result.add(minion);
				}
			}
		}
		
		if (result.isEmpty())
		{
			FastList.recycle(result);
			return null;
		}
		
		Object[] characters = result.toArray();
		QuestTimer timer = getQuestTimer("clean_player", npc, null);
		if (timer != null)
		{
			timer.cancelAndRemove();
		}
		startQuestTimer("clean_player", 20000, npc, null);
		L2Character target = (L2Character) characters[getRandom(characters.length)];
		FastList.recycle(result);
		return target;
		
	}
	
	public synchronized void callSkillAI(L2Npc npc)
	{
		if (npc.isInvul() || npc.isCastingNow())
		{
			return;
		}
		
		if ((_target == null) || _target.isDead() || !(_Zone.isInsideZone(_target)))
		{
			_target = getRandomTarget(npc);
			if (_target != null)
			{
				_skill = getRandomSkill(npc);
			}
		}
		
		L2Character target = _target;
		SkillHolder skill = _skill;
		if (skill == null)
		{
			skill = (getRandomSkill(npc));
		}
		
		if (npc.isPhysicalMuted())
		{
			return;
		}
		
		if ((target == null) || target.isDead() || !(_Zone.isInsideZone(target)))
		{
			npc.setIsCastingNow(false);
			return;
		}
		
		if (Util.checkIfInRange(skill.getSkill().getCastRange(), npc, target, true))
		{
			npc.getAI().setIntention(AI_INTENTION_IDLE);
			npc.setTarget(target);
			npc.setIsCastingNow(true);
			_target = null;
			_skill = null;
			if (getDist(skill.getSkill().getCastRange()) > 0)
			{
				npc.broadcastPacket(new MoveToPawn(npc, target, getDist(skill.getSkill().getCastRange())));
			}
			try
			{
				Thread.sleep(1000);
				npc.stopMove(null);
				npc.doCast(skill.getSkill());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			npc.getAI().setIntention(AI_INTENTION_FOLLOW, target, null);
			npc.setIsCastingNow(false);
		}
	}
	
	public SkillHolder getRandomSkill(L2Npc npc)
	{
		SkillHolder skill;
		if (npc.getCurrentHp() > ((npc.getMaxHp() * 3) / 4.0))
		{
			if (getRandom(100) < 10)
			{
				skill = WIND_OF_FORCE;
			}
			else if (getRandom(100) < 10)
			{
				skill = EARTHQUAKE;
			}
			else
			{
				skill = GENERAL_ATTACK;
			}
		}
		else if (npc.getCurrentHp() > ((npc.getMaxHp() * 2) / 4.0))
		{
			if (getRandom(100) < 10)
			{
				skill = STUN;
			}
			else if (getRandom(100) < 10)
			{
				skill = WIND_OF_FORCE;
			}
			else if (getRandom(100) < 10)
			{
				skill = EARTHQUAKE;
			}
			else
			{
				skill = GENERAL_ATTACK;
			}
		}
		else if (npc.getCurrentHp() > (npc.getMaxHp() / 4.0))
		{
			if (getRandom(100) < 10)
			{
				skill = STRIKING_OF_THUNDERBOLT;
			}
			else if (getRandom(100) < 10)
			{
				skill = STUN;
			}
			else if (getRandom(100) < 10)
			{
				skill = WIND_OF_FORCE;
			}
			else if (getRandom(100) < 10)
			{
				skill = EARTHQUAKE;
			}
			else
			{
				skill = GENERAL_ATTACK;
			}
		}
		else if (getRandom(100) < 10)
		{
			skill = STRIKING_OF_THUNDERBOLT;
		}
		else if (getRandom(100) < 10)
		{
			skill = STUN;
		}
		else if (getRandom(100) < 10)
		{
			skill = WIND_OF_FORCE;
		}
		else if (getRandom(100) < 10)
		{
			skill = EARTHQUAKE;
		}
		else
		{
			skill = GENERAL_ATTACK;
		}
		return skill;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isSummon)
	{
		if (npc.isInvul())
		{
			npc.getAI().setIntention(AI_INTENTION_IDLE);
			return null;
		}
		npc.setTarget(caster);
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	public int getDist(int range)
	{
		int dist = 0;
		switch (range)
		{
			case -1:
				break;
			case 100:
				dist = 85;
				break;
			default:
				dist = range - 85;
				break;
		}
		return dist;
	}
	
	public static void main(String[] args)
	{
		new Baium(Baium.class.getSimpleName(), "ai");
	}
}
