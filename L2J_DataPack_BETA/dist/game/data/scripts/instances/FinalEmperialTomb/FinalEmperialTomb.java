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
package instances.FinalEmperialTomb;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilderFactory;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2jserver.Config;
import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager.InstanceWorld;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.L2CommandChannel;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Territory;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2GrandBossInstance;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.AbstractNpcInfo.NpcInfo;
import com.l2jserver.gameserver.network.serverpackets.Earthquake;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;
import com.l2jserver.gameserver.network.serverpackets.L2GameServerPacket;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillCanceld;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;
import com.l2jserver.gameserver.network.serverpackets.SpecialCamera;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.skills.SkillHolder;
import com.l2jserver.gameserver.templates.skills.L2SkillType;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/*
TODO:
- test when Frintezza song use 5008 effect skill
- maybe test more deeply halishas AI
- use correct Song names
- use proper zone spawn system
Contributing authors: Gigiikun
*/
public class FinalEmperialTomb extends Quest
{

	private class FETWorld extends InstanceWorld
	{
		public         Lock                 lock                         = new ReentrantLock();
		public         List<L2Npc>          npcList                      = new FastList<L2Npc>();
		public         int                  darkChoirPlayerCount         = 0;
		public         FrintezzaSong        OnSong                       = null;
		public         ScheduledFuture<?>   songTask                     = null;
		public         ScheduledFuture<?>   songEffectTask               = null;
		public         boolean              isVideo                      = false;
		public         L2Npc                frintezzaDummy               = null;
		public         L2Npc                overheadDummy                = null;
		public         L2Npc                portraitDummy1               = null;
		public         L2Npc                portraitDummy3               = null;
		public         L2Npc                scarletDummy                 = null;
		public         L2GrandBossInstance  frintezza                    = null;
		public         L2GrandBossInstance  activeScarlet                = null;
		public         List<L2MonsterInstance>  demons                   = new FastList<L2MonsterInstance>();
		public         Map<L2MonsterInstance, Integer>  portraits        = new FastMap<L2MonsterInstance, Integer>();
		public         int                  scarlet_x                    = 0;
		public         int                  scarlet_y                    = 0;
		public         int                  scarlet_z                    = 0;
		public         int                  scarlet_h                    = 0;
		public         int                  scarlet_a                    = 0;

		public FETWorld()
		{
		}
	}
	
	private static class FETSpawn
	{
		public boolean isZone = false;
		public boolean isNeededNextFlag = false;
		public int npcId;
		public int x = 0;
		public int y = 0;
		public int z = 0;
		public int h = 0;
		public int zone = 0;
		public int count = 0;
	}
	
	private static class FrintezzaSong
	{
		public SkillHolder skill;
		public SkillHolder effectSkill;
		public NpcStringId songName;
		public int chance;
		
		public FrintezzaSong(SkillHolder sk, SkillHolder esk, NpcStringId sn, int ch)
		{
			skill = sk;
			effectSkill = esk;
			songName = sn;
			chance = ch;
		}
	}
	
	private static final String qn = "FinalEmperialTomb";
	private static final int INSTANCEID = 136; // this is the client number
	private static final int MIN_PLAYERS = 36;
	private static final int MAX_PLAYERS = 45;
	private static final boolean debug = false;
	
	private final TIntObjectHashMap<L2Territory> _spawnZoneList = new TIntObjectHashMap<L2Territory>();
	private final TIntObjectHashMap<List<FETSpawn>> _spawnList = new TIntObjectHashMap<List<FETSpawn>>();
	private final List<Integer> _mustKillMobsId = new FastList<Integer>();
	
	// Teleports
	private static final int[] ENTER_TELEPORT = {-88015,-141153,-9168};
	
	//NPCs
	private static final int GUIDE = 32011;
	private static final int CUBE = 29061;
	
	//mobs
	private static final int SCARLET1 = 29046;
	private static final int SCARLET2 = 29047;
	private static final int FRINTEZZA = 29045;
	private static final int[] PORTRAITS = { 29048, 29049 };
	private static final int[] DEMONS = { 29050, 29051 };
	private static final int HALL_ALARM = 18328;
	private static final int DARK_CHOIR_PLAYER = 18339;
	private static final int[] AI_DISABLED_MOBS = { 18328 };
	
	private static final int FIRST_SCARLET_WEAPON = 8204;
	private static final int SECOND_SCARLET_WEAPON = 7903;
	private static final SkillHolder INTRO_SKILL = new SkillHolder(5004,1);
	private static final SkillHolder FIRST_MORPH_SKILL = new SkillHolder(5017,1);
	
	private static final FrintezzaSong[] FRINTEZZASONGLIST = 
	{
		new FrintezzaSong(new SkillHolder(5007,1), new SkillHolder(5008,1), NpcStringId.REQUIEM_OF_HATRED, 5),
        new FrintezzaSong(new SkillHolder(5007,2), new SkillHolder(5008,2), NpcStringId.RONDO_OF_SOLITUDE, 50),
        new FrintezzaSong(new SkillHolder(5007,3), new SkillHolder(5008,3), NpcStringId.FRENETIC_TOCCATA, 70),
        new FrintezzaSong(new SkillHolder(5007,4), new SkillHolder(5008,4), NpcStringId.FUGUE_OF_JUBILATION, 90),
        new FrintezzaSong(new SkillHolder(5007,5), new SkillHolder(5008,5), NpcStringId.HYPNOTIC_MAZURKA, 100),
	};
	
	// Doors/Walls/Zones
	private static final int[] FIRST_ROOM_DOORS = 
	{ 
		17130051, 17130052, 17130053, 17130054, 17130055,
		17130056, 17130057, 17130058
	};
	private static final int[] SECOND_ROOM_DOORS = 
	{
		17130061, 17130062, 17130063, 17130064, 17130065,
		17130066, 17130067, 17130068, 17130069, 17130070
	};
	
	private static final int[] FIRST_ROUTE_DOORS = { 17130042, 17130043 };
	private static final int[] SECOND_ROUTE_DOORS = { 17130045, 17130046 };
	private static final L2CharPosition MOVE_TO_CENTER = new L2CharPosition( -87904, -141296, -9168, 0 );

	// spawns
	private static final int TIME_BETWEEN_DEMON_SPAWNS = 20000;
	private static final int MAX_DEMONS = 24;
	private static final int[][] PORTRAIT_SPAWNS = 
	{
		{ 29048, -89381, -153981, -9168, 3368, -89378, -153968, -9168, 3368 },
		{ 29048, -86234, -152467, -9168, 37656, -86261, -152492, -9168, 37656 },
		{ 29049, -89342, -152479, -9168, -5152, -89311, -152491, -9168, -5152 },
		{ 29049, -86189, -153968, -9168, 29456, -86217, -153956, -9168, 29456 }
	};
	
	// Initialization at 6:30 am on Wednesday and Saturday
	private static final int RESET_HOUR = 6;
	private static final int RESET_MIN = 30;
	private static final int RESET_DAY_1 = 4;
	private static final int RESET_DAY_2 = 7;
	
	@SuppressWarnings("unused")
	private void load()
	{
		
		int spawnCount = 0;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);
			
			File file = new File(Config.DATAPACK_ROOT + "/data/scripts/instances/FinalEmperialTomb/data.xml");
			if (!file.exists())
			{
				_log.severe("[Final Emperial Tomb] Missing data.xml. The quest wont work without it!");
				return;
			}
			
			Document doc = factory.newDocumentBuilder().parse(file);
			Node first = doc.getFirstChild();
			if (first != null && "list".equalsIgnoreCase(first.getNodeName()))
			{
				for (Node n = first.getFirstChild(); n != null; n = n.getNextSibling())
				{
					if ("npc".equalsIgnoreCase(n.getNodeName()))
					{
						for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
						{
							if ("spawn".equalsIgnoreCase(d.getNodeName()))
							{
								NamedNodeMap attrs = d.getAttributes();
								Node att = attrs.getNamedItem("npcId");
								if (att == null)
								{
									_log.severe("[Final Emperial Tomb] Missing npcId in npc List, skipping");
									continue;
								}
								int npcId = Integer.parseInt(attrs.getNamedItem("npcId").getNodeValue());
								
								att = attrs.getNamedItem("flag");
								if (att == null)
								{
									_log.severe("[Final Emperial Tomb] Missing flag in npc List npcId: " + npcId + ", skipping");
									continue;
								}
								int flag = Integer.parseInt(attrs.getNamedItem("flag").getNodeValue());
								if (!_spawnList.contains(flag))
									_spawnList.put(flag, new FastList<FETSpawn>());
								
								for (Node cd = d.getFirstChild(); cd != null; cd = cd.getNextSibling())
								{
									if ("loc".equalsIgnoreCase(cd.getNodeName()))
									{
										attrs = cd.getAttributes();
										FETSpawn spw = new FETSpawn();
										spw.npcId = npcId;
										
										att = attrs.getNamedItem("x");
										if (att != null)
											spw.x = Integer.parseInt(att.getNodeValue());
										else
											continue;
										att = attrs.getNamedItem("y");
										if (att != null)
											spw.y = Integer.parseInt(att.getNodeValue());
										else
											continue;
										att = attrs.getNamedItem("z");
										if (att != null)
											spw.z = Integer.parseInt(att.getNodeValue());
										else
											continue;
										att = attrs.getNamedItem("heading");
										if (att != null)
											spw.h = Integer.parseInt(att.getNodeValue());
										else
											continue;
										att = attrs.getNamedItem("mustKill");
										if (att != null)
											spw.isNeededNextFlag = Boolean.parseBoolean(att.getNodeValue());
										if (spw.isNeededNextFlag)
											_mustKillMobsId.add(npcId);
										_spawnList.get(flag).add(spw);
										spawnCount++;
									}
									else if ("zone".equalsIgnoreCase(cd.getNodeName()))
									{
										attrs = cd.getAttributes();
										FETSpawn spw = new FETSpawn();
										spw.npcId = npcId;
										spw.isZone = true;
										
										att = attrs.getNamedItem("id");
										if (att != null)
											spw.zone = Integer.parseInt(att.getNodeValue());
										else
											continue;
										att = attrs.getNamedItem("count");
										if (att != null)
											spw.count = Integer.parseInt(att.getNodeValue());
										else
											continue;
										att = attrs.getNamedItem("mustKill");
										if (att != null)
											spw.isNeededNextFlag = Boolean.parseBoolean(att.getNodeValue());
										if (spw.isNeededNextFlag)
											_mustKillMobsId.add(npcId);
										_spawnList.get(flag).add(spw);
										spawnCount++;
									}
								}
							}
						}
					}
					else if ("spawnZones".equalsIgnoreCase(n.getNodeName()))
					{
						for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
						{
							if ("zone".equalsIgnoreCase(d.getNodeName()))
							{
								NamedNodeMap attrs = d.getAttributes();
								Node att = attrs.getNamedItem("id");
								if (att == null)
								{
									_log.severe("[Final Emperial Tomb] Missing id in spawnZones List, skipping");
									continue;
								}
								int id = Integer.parseInt(att.getNodeValue());
								att = attrs.getNamedItem("minZ");
								if (att == null)
								{
									_log.severe("[Final Emperial Tomb] Missing minZ in spawnZones List id: " + id + ", skipping");
									continue;
								}
								int minz = Integer.parseInt(att.getNodeValue());
								att = attrs.getNamedItem("maxZ");
								if (att == null)
								{
									_log.severe("[Final Emperial Tomb] Missing maxZ in spawnZones List id: " + id + ", skipping");
									continue;
								}
								int maxz = Integer.parseInt(att.getNodeValue());
								L2Territory ter = new L2Territory(id);
								
								for (Node cd = d.getFirstChild(); cd != null; cd = cd.getNextSibling())
								{
									if ("point".equalsIgnoreCase(cd.getNodeName()))
									{
										attrs = cd.getAttributes();
										int x, y;
										att = attrs.getNamedItem("x");
										if (att != null)
											x = Integer.parseInt(att.getNodeValue());
										else
											continue;
										att = attrs.getNamedItem("y");
										if (att != null)
											y = Integer.parseInt(att.getNodeValue());
										else
											continue;
										
										ter.add(x, y, minz, maxz, 0);
									}
								}
								
								_spawnZoneList.put(id, ter);
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "[Final Emperial Tomb] Could not parse data.xml file: " + e.getMessage(), e);
		}
		if (debug)
		{
			_log.info("[Final Emperial Tomb] Loaded " + _spawnZoneList.size() + " spawn zones data.");
			_log.info("[Final Emperial Tomb] Loaded " + spawnCount + " spawns data.");
		}
	}
	
	protected void openDoor(int doorId, int instanceId)
	{
		for (L2DoorInstance door : InstanceManager.getInstance().getInstance(instanceId).getDoors())
			if (door.getDoorId() == doorId)
				door.openMe();
	}
	
	protected void closeDoor(int doorId, int instanceId)
	{
		for (L2DoorInstance door : InstanceManager.getInstance().getInstance(instanceId).getDoors())
			if (door.getDoorId() == doorId)
				if (door.getOpen())
					door.closeMe();
	}
	
	private boolean checkConditions(L2PcInstance player)
	{
		if (debug || player.isGM())
			return true;
		L2Party party = player.getParty();
		if (party == null)
		{
			player.sendPacket(SystemMessageId.NOT_IN_PARTY_CANT_ENTER);
			return false;
		}
		L2CommandChannel channel = player.getParty().getCommandChannel();
		if (channel == null)
		{
			player.sendPacket(SystemMessageId.NOT_IN_COMMAND_CHANNEL_CANT_ENTER);
			return false;
		}
		else if (channel.getChannelLeader() != player)
		{
			player.sendPacket(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER);
			return false;
		}
		else if (player.getInventory().getItemByItemId(8073) == null)
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_ITEM_REQUIREMENT_NOT_SUFFICIENT);
			sm.addPcName(player);
			player.sendPacket(sm);
			return false;
		}
		else if (channel.getMemberCount() < MIN_PLAYERS || channel.getMemberCount() > MAX_PLAYERS)
		{
			player.sendPacket(SystemMessageId.PARTY_EXCEEDED_THE_LIMIT_CANT_ENTER);
			return false;
		}
		for (L2PcInstance channelMember : channel.getMembers())
		{
			if (channelMember.getLevel() < 80)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(channelMember);
				party.broadcastToPartyMembers(sm);
				return false;
			}
			if (!Util.checkIfInRange(1000, player, channelMember, true))
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED);
				sm.addPcName(channelMember);
				party.broadcastToPartyMembers(sm);
				return false;
			}
			Long reentertime = InstanceManager.getInstance().getInstanceTime(channelMember.getObjectId(), INSTANCEID);
			if (System.currentTimeMillis() < reentertime)
			{
				SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_MAY_NOT_REENTER_YET);
				sm.addPcName(channelMember);
				party.broadcastToPartyMembers(sm);
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
			if (!(world instanceof FETWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, coords, world.instanceId);
			return world.instanceId;
		}
		//New instance
		else
		{
			if (!checkConditions(player))
				return 0;
			if (!player.isGM() && !player.destroyItemByItemId("QUEST", 8073, 1, player, true))
				return 0;
			instanceId = InstanceManager.getInstance().createDynamicInstance(template);
			//Instance ins = InstanceManager.getInstance().getInstance(instanceId);
			//ins.setSpawnLoc(new int[]{player.getX(),player.getY(),player.getZ()});
			world = new FETWorld();
			world.instanceId = instanceId;
			world.status = 0;
			InstanceManager.getInstance().addWorld(world);
			controlStatus((FETWorld) world);
			_log.info("Final Emperial Tomb started " + template + " Instance: " + instanceId + " created by player: " + player.getName());
			// teleport players
			if (player.getParty() == null || player.getParty().getCommandChannel() == null)
			{
				world.allowed.add(player.getObjectId());
				teleportPlayer(player, coords, instanceId);
			}
			else
			{
				for (L2PcInstance channelMember : player.getParty().getCommandChannel().getMembers())
				{
					world.allowed.add(channelMember.getObjectId());
					teleportPlayer(channelMember, coords, instanceId);
				}
			}
			return instanceId;
		}
	}
	
	protected synchronized boolean checkKillProgress(L2Npc mob, FETWorld world)
	{
		if (world.npcList.contains(mob))
			world.npcList.remove(mob);
		return world.npcList.size() == 0;
	}
	
	private void spawnFlaggedNPCs(FETWorld world, int flag)
	{
		if (world.lock.tryLock())
		{
			try
			{
				for (FETSpawn spw : _spawnList.get(flag))
				{
					if (spw.isZone)
					{
						for (int i = 0; i < spw.count; i++)
						{
							if (_spawnZoneList.contains(spw.zone))
							{
								int[] point = _spawnZoneList.get(spw.zone).getRandomPoint();
								spawn(world, spw.npcId, point[0], point[1], GeoData.getInstance().getSpawnHeight(point[0], point[1], point[2], point[3], null), Rnd.get(65535), spw.isNeededNextFlag);
							}
							else
								_log.info("[Final Emperial Tomb] Missing zone: " + spw.zone);
						}
					}
					else
						spawn(world, spw.npcId, spw.x, spw.y, spw.z, spw.h, spw.isNeededNextFlag);
				}
			}
			finally
			{
				world.lock.unlock();
			}
		}
	}
	
	protected boolean controlStatus(FETWorld world)
	{
		if (world.lock.tryLock())
		{
			try
			{
				if (debug)
					_log.info("[Final Emperial Tomb] Starting " + world.status + ". status.");
				world.npcList.clear();
				switch (world.status)
				{
					case 0:
						spawnFlaggedNPCs(world, 0);
						break;
					case 1:
						for (int doorId : FIRST_ROUTE_DOORS)
							openDoor(doorId, world.instanceId);
						spawnFlaggedNPCs(world, world.status);
						break;
					case 2:
						for (int doorId : SECOND_ROUTE_DOORS)
							openDoor(doorId, world.instanceId);
						ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(world, 0), 600000);
						break;
					case 3: // first morph
						if (world.songEffectTask != null)
							world.songEffectTask.cancel(false);
						world.songEffectTask = null;
						world.activeScarlet.setIsInvul(true);
						if (world.activeScarlet.isCastingNow())
							world.activeScarlet.abortCast();
						setInstanceTimeRestrictions(world);
						world.activeScarlet.doCast(FIRST_MORPH_SKILL.getSkill());
						ThreadPoolManager.getInstance().scheduleGeneral(new SongTask(world, 2), 1500);
						break;
					case 4: // second morph
						world.isVideo = true;
						broadCastPacket(world, new MagicSkillCanceld(world.frintezza.getObjectId()));
						if (world.songEffectTask != null)
							world.songEffectTask.cancel(false);
						world.songEffectTask = null;
						ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(world, 23), 2000);
						ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(world, 24), 2100);
						break;
					case 5: // raid success
						world.isVideo = true;
						broadCastPacket(world, new MagicSkillCanceld(world.frintezza.getObjectId()));
						if (world.songTask != null)
							world.songTask.cancel(true);
						if (world.songEffectTask != null)
							world.songEffectTask.cancel(false);
						world.songTask = null;
						world.songEffectTask = null;
						ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(world, 33), 500);
						break;
					case 6: // open doors
						InstanceManager.getInstance().getInstance(world.instanceId).setDuration(300000);
						for (int doorId : FIRST_ROOM_DOORS)
							openDoor(doorId, world.instanceId);
						for (int doorId : FIRST_ROUTE_DOORS)
							openDoor(doorId, world.instanceId);
						for (int doorId : SECOND_ROUTE_DOORS)
							openDoor(doorId, world.instanceId);
						for (int doorId : SECOND_ROOM_DOORS)
							closeDoor(doorId, world.instanceId);
						break;
				}
				world.status++;
				return true;
			}
			finally
			{
				world.lock.unlock();
			}
		}
		return false;
	}
	
	protected void spawn(FETWorld world, int npcId, int x, int y, int z, int h, boolean addToKillTable)
	{
		L2Npc npc = addSpawn(npcId, x, y, z, h, false, 0, false, world.instanceId);
		if (addToKillTable)
			world.npcList.add(npc);
		npc.setIsNoRndWalk(true);
		if (npc.isInstanceType(InstanceType.L2Attackable))
			((L2Attackable) npc).setSeeThroughSilentMove(true);
		if (Util.contains(AI_DISABLED_MOBS, npcId))
			npc.disableCoreAI(true);
		if (npcId == DARK_CHOIR_PLAYER)
			world.darkChoirPlayerCount++;
	}
	
	private class DemonSpawnTask implements Runnable
	{
		private final FETWorld _world;
		
		DemonSpawnTask(FETWorld world)
		{
			_world = world;
		}
		
		@Override
		public void run()
		{
			if (InstanceManager.getInstance().getWorld(_world.instanceId) != _world || _world.portraits.isEmpty())
			{
				if (debug)
					_log.info("[Final Emperial Tomb] Instance is deleted or all Portraits is killed.");
				return;
			}
			for (int i : _world.portraits.values())
			{
				if (_world.demons.size() > MAX_DEMONS)
					break;
				L2MonsterInstance demon = (L2MonsterInstance) addSpawn(PORTRAIT_SPAWNS[i][0] + 2, PORTRAIT_SPAWNS[i][5], PORTRAIT_SPAWNS[i][6], PORTRAIT_SPAWNS[i][7], PORTRAIT_SPAWNS[i][8], false, 0, false, _world.instanceId);
				updateKnownList(_world, demon);
				_world.demons.add(demon);
			}
			ThreadPoolManager.getInstance().scheduleGeneral(new DemonSpawnTask(_world), TIME_BETWEEN_DEMON_SPAWNS);
		}
	}
	
	private class SongTask implements Runnable
	{
		private final FETWorld _world;
		private final int _status;
		
		SongTask(FETWorld world, int status)
		{
			_world = world;
			_status = status;
		}
		
		@Override
		public void run()
		{
			if (InstanceManager.getInstance().getWorld(_world.instanceId) != _world)
				return;
			switch (_status)
			{
				case 0: // new song play
					if (_world.isVideo)
						_world.songTask = ThreadPoolManager.getInstance().scheduleGeneral(new SongTask(_world, 0), 1000);
					else if (_world.frintezza != null && !_world.frintezza.isDead())
					{
						int rnd = Rnd.get(100);
						for (int i = 0; i < FRINTEZZASONGLIST.length; i++)
						{
							if (rnd < FRINTEZZASONGLIST[i].chance)
							{
								_world.OnSong = FRINTEZZASONGLIST[i];
								broadCastPacket(_world, new ExShowScreenMessage(FRINTEZZASONGLIST[i].songName, 2, 500));
								broadCastPacket(_world, new MagicSkillUse(_world.frintezza, _world.frintezza, FRINTEZZASONGLIST[i].skill.getSkillId(), FRINTEZZASONGLIST[i].skill.getSkillLvl(), FRINTEZZASONGLIST[i].skill.getSkill().getHitTime(), 0));
								_world.songEffectTask = ThreadPoolManager.getInstance().scheduleGeneral(new SongTask(_world, 1), FRINTEZZASONGLIST[i].skill.getSkill().getHitTime() - 10000);
								_world.songTask = ThreadPoolManager.getInstance().scheduleGeneral(new SongTask(_world, 0), FRINTEZZASONGLIST[i].skill.getSkill().getHitTime());
								break;
							}
						}
					}
					break;
				case 1: // Frintezza song effect
					_world.songEffectTask = null;
					L2Skill skill = _world.OnSong.effectSkill.getSkill();
					if (skill == null)
						return;
					
					if (_world.frintezza != null && !_world.frintezza.isDead() && _world.activeScarlet != null && !_world.activeScarlet.isDead())
					{
						List<L2Character> targetList = new FastList<L2Character>();
						if (skill.getSkillType() == L2SkillType.STUN || skill.getSkillType() == L2SkillType.DEBUFF)
						{
							for (int objId : _world.allowed)
							{
								L2PcInstance player = L2World.getInstance().getPlayer(objId);
								if (player != null && player.isOnline() && player.getInstanceId() == _world.instanceId)
								{
									if (!player.isDead())
										targetList.add(player);
									if (player.getPet() != null && !player.getPet().isDead())
										targetList.add(player.getPet());
								}
							}
						}
						else
							targetList.add(_world.activeScarlet);
						if (targetList.size() > 0)
							_world.frintezza.doCast(skill, targetList.get(0), targetList.toArray(new L2Character[targetList.size()]));
					}
					break;
				case 2: // finish morph
					_world.activeScarlet.setRHandId(SECOND_SCARLET_WEAPON);
					_world.activeScarlet.setIsInvul(false);
					break;
			}
		}
	}
	
	private class IntroTask implements Runnable
	{
		private final FETWorld _world;
		private final int _status;
		
		IntroTask(FETWorld world, int status)
		{
			_world = world;
			_status = status;
		}
		
		@Override
		public void run()
		{
			switch (_status)
			{
				case 0:
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 1), 27000);
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 2), 30000);
					broadCastPacket(_world, new Earthquake(-87784, -155083, -9087, 45, 27));
					break;
				case 1:
					for (int doorId : FIRST_ROOM_DOORS)
						closeDoor(doorId, _world.instanceId);
					for (int doorId : FIRST_ROUTE_DOORS)
						closeDoor(doorId, _world.instanceId);
					for (int doorId : SECOND_ROOM_DOORS)
						closeDoor(doorId, _world.instanceId);
					for (int doorId : SECOND_ROUTE_DOORS)
						closeDoor(doorId, _world.instanceId);
					addSpawn(29061, -87904, -141296, -9168, 0, false, 0, false, _world.instanceId);
					break;
				case 2:
					_world.frintezzaDummy = addSpawn(29052, -87784, -155083, -9087, 16048, false, 0, false, _world.instanceId);
					_world.frintezzaDummy.setIsInvul(true);
					_world.frintezzaDummy.setIsImmobilized(true);
					
					_world.overheadDummy = addSpawn(29052, -87784, -153298, -9175, 16384, false, 0, false, _world.instanceId);
					_world.overheadDummy.setIsInvul(true);
					_world.overheadDummy.setIsImmobilized(true);
					_world.overheadDummy.setCollisionHeight(600);
					broadCastPacket(_world, new NpcInfo(_world.overheadDummy, null));
					
					_world.portraitDummy1 = addSpawn(29052, -89566, -153168, -9165, 16048, false, 0, false, _world.instanceId);
					_world.portraitDummy1.setIsImmobilized(true);
					_world.portraitDummy1.setIsInvul(true);
					
					_world.portraitDummy3 = addSpawn(29052, -86004, -153168, -9165, 16048, false, 0, false, _world.instanceId);
					_world.portraitDummy3.setIsImmobilized(true);
					_world.portraitDummy3.setIsInvul(true);
					
					_world.scarletDummy = addSpawn(29053, -87784, -153298, -9175, 16384, false, 0, false, _world.instanceId);
					_world.scarletDummy.setIsInvul(true);
					_world.scarletDummy.setIsImmobilized(true);
					
					stopPc();
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 3), 1000);
					break;
				case 3:
					broadCastPacket(_world, new SpecialCamera(_world.overheadDummy.getObjectId(), 0, 75, -89, 0, 100, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.overheadDummy.getObjectId(), 0, 75, -89, 0, 100, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.overheadDummy.getObjectId(), 300, 90, -10, 6500, 7000, 0, 0, 1, 0));
					
					_world.frintezza = (L2GrandBossInstance) addSpawn(FRINTEZZA, -87784, -155083, -9087, 16048, false, 0, false, _world.instanceId);
					_world.frintezza.setIsImmobilized(true);
					_world.frintezza.setIsInvul(true);
					_world.frintezza.disableAllSkills();
					updateKnownList(_world, _world.frintezza);
					
					for (int i = 0; i < PORTRAIT_SPAWNS.length; i++)
					{
						L2MonsterInstance demon = (L2MonsterInstance) addSpawn(PORTRAIT_SPAWNS[i][0] + 2, PORTRAIT_SPAWNS[i][5], PORTRAIT_SPAWNS[i][6], PORTRAIT_SPAWNS[i][7], PORTRAIT_SPAWNS[i][8], false, 0, false, _world.instanceId);
						demon.setIsImmobilized(true);
						demon.disableAllSkills();
						updateKnownList(_world, demon);
						_world.demons.add(demon);
					}
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 4), 6500);
					break;
				case 4:
					broadCastPacket(_world, new SpecialCamera(_world.frintezzaDummy.getObjectId(), 1800, 90, 8, 6500, 7000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 5), 900);
					break;
				case 5:
					broadCastPacket(_world, new SpecialCamera(_world.frintezzaDummy.getObjectId(), 140, 90, 10, 2500, 4500, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 6), 4000);
					break;
				case 6:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 40, 75, -10, 0, 1000, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 40, 75, -10, 0, 12000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 7), 1350);
					break;
				case 7:
					broadCastPacket(_world, new SocialAction(_world.frintezza.getObjectId(), 2));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 8), 7000);
					break;
				case 8:
					_world.frintezzaDummy.deleteMe();
					_world.frintezzaDummy = null;
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 9), 1000);
					break;
				case 9:
					broadCastPacket(_world, new SocialAction(_world.demons.get(1).getObjectId(), 1));
					broadCastPacket(_world, new SocialAction(_world.demons.get(2).getObjectId(), 1));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 10), 400);
					break;
				case 10:
					broadCastPacket(_world, new SocialAction(_world.demons.get(0).getObjectId(), 1));
					broadCastPacket(_world, new SocialAction(_world.demons.get(3).getObjectId(), 1));
					sendPacketX(new SpecialCamera(_world.portraitDummy1.getObjectId(), 1000, 118, 0, 0, 1000, 0, 0, 1, 0), new SpecialCamera(_world.portraitDummy3.getObjectId(), 1000, 62, 0, 0, 1000, 0, 0, 1, 0), -87784);
					sendPacketX(new SpecialCamera(_world.portraitDummy1.getObjectId(), 1000, 118, 0, 0, 10000, 0, 0, 1, 0), new SpecialCamera(_world.portraitDummy3.getObjectId(), 1000, 62, 0, 0, 10000, 0, 0, 1, 0), -87784);
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 11), 2000);
					break;
				case 11:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 240, 90, 0, 0, 1000, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 240, 90, 25, 5500, 10000, 0, 0, 1, 0));
					broadCastPacket(_world, new SocialAction(_world.frintezza.getObjectId(), 3));
					_world.portraitDummy1.deleteMe();
					_world.portraitDummy3.deleteMe();
					_world.portraitDummy1 = null;
					_world.portraitDummy3 = null;
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 12), 4500);
					break;
				case 12:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 100, 195, 35, 0, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 13), 700);
					break;
				case 13:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 100, 195, 35, 0, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 14), 1300);
					break;
				case 14:
					broadCastPacket(_world, new ExShowScreenMessage(NpcStringId.MOURNFUL_CHORALE_PRELUDE, 2, 5000));
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 120, 180, 45, 1500, 10000, 0, 0, 1, 0));
					broadCastPacket(_world, new MagicSkillUse(_world.frintezza, _world.frintezza, 5006, 1, 34000, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 15), 1500);
					break;
				case 15:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 520, 135, 45, 8000, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 16), 7500);
					break;
				case 16:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 1500, 110, 25, 10000, 13000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 17), 9500);
					break;
				case 17:
					broadCastPacket(_world, new SpecialCamera(_world.overheadDummy.getObjectId(), 930, 160, -20, 0, 1000, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.overheadDummy.getObjectId(), 600, 180, -25, 0, 10000, 0, 0, 1, 0));
					broadCastPacket(_world, new MagicSkillUse(_world.scarletDummy, _world.overheadDummy, 5004, 1, 5800, 0));
					
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 18), 5000);
					break;
				case 18:
					_world.activeScarlet = (L2GrandBossInstance) addSpawn(29046, -87784, -153298, -9165, 16384, false, 0, false, _world.instanceId);
					_world.activeScarlet.setRHandId(FIRST_SCARLET_WEAPON);
					_world.activeScarlet.setIsInvul(true);
					_world.activeScarlet.setIsImmobilized(true);
					_world.activeScarlet.disableAllSkills();
					updateKnownList(_world, _world.activeScarlet);
					broadCastPacket(_world, new SocialAction(_world.activeScarlet.getObjectId(), 3));
					broadCastPacket(_world, new SpecialCamera(_world.scarletDummy.getObjectId(), 800, 180, 10, 1000, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 19), 2100);
					break;
				case 19:
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 300, 60, 8, 0, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 20), 2000);
					break;
				case 20:
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 500, 90, 10, 3000, 5000, 0, 0, 1, 0));
					_world.songTask = ThreadPoolManager.getInstance().scheduleGeneral(new SongTask(_world, 0), 100);
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 21), 3000);
					break;
				case 21:
					for (int i = 0; i < PORTRAIT_SPAWNS.length; i++)
					{
						L2MonsterInstance portrait = (L2MonsterInstance) addSpawn(PORTRAIT_SPAWNS[i][0], PORTRAIT_SPAWNS[i][1], PORTRAIT_SPAWNS[i][2], PORTRAIT_SPAWNS[i][3], PORTRAIT_SPAWNS[i][4], false, 0, false, _world.instanceId);
						updateKnownList(_world, portrait);
						_world.portraits.put(portrait, i);
					}
					
					_world.overheadDummy.deleteMe();
					_world.scarletDummy.deleteMe();
					_world.overheadDummy = null;
					_world.scarletDummy = null;
					
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 22), 2000);
					break;
				case 22:
					for (L2MonsterInstance demon : _world.demons)
					{
						demon.setIsImmobilized(false);
						demon.enableAllSkills();
					}
					_world.activeScarlet.setIsInvul(false);
					_world.activeScarlet.setIsImmobilized(false);
					_world.activeScarlet.enableAllSkills();
					_world.activeScarlet.setRunning();
					_world.activeScarlet.doCast(INTRO_SKILL.getSkill());
					_world.frintezza.enableAllSkills();
					_world.frintezza.disableCoreAI(true);
					_world.frintezza.setIsMortal(false);
					startPc();
					
					ThreadPoolManager.getInstance().scheduleGeneral(new DemonSpawnTask(_world), TIME_BETWEEN_DEMON_SPAWNS);
					break;
				case 23:
					broadCastPacket(_world, new SocialAction(_world.frintezza.getObjectId(), 4));
					break;
				case 24:
					stopPc();
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 250, 120, 15, 0, 1000, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 250, 120, 15, 0, 10000, 0, 0, 1, 0));
					_world.activeScarlet.abortAttack();
					_world.activeScarlet.abortCast();
					_world.activeScarlet.setIsInvul(true);
					_world.activeScarlet.setIsImmobilized(true);
					_world.activeScarlet.disableAllSkills();
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 25), 7000);
					break;
				case 25:
					broadCastPacket(_world, new MagicSkillUse(_world.frintezza, _world.frintezza, 5006, 1, 34000, 0));
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 500, 70, 15, 3000, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 26), 3000);
					break;
				case 26:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 2500, 90, 12, 6000, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 27), 3000);
					break;
				case 27:
					_world.scarlet_x = _world.activeScarlet.getX();
					_world.scarlet_y = _world.activeScarlet.getY();
					_world.scarlet_z = _world.activeScarlet.getZ();
					_world.scarlet_h = _world.activeScarlet.getHeading();
					if (_world.scarlet_h < 32768)
						_world.scarlet_a = Math.abs(180 - (int) (_world.scarlet_h / 182.044444444));
					else
						_world.scarlet_a = Math.abs(540 - (int) (_world.scarlet_h / 182.044444444));
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 250, _world.scarlet_a, 12, 0, 1000, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 250, _world.scarlet_a, 12, 0, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 28), 500);
					break;
				case 28:
					_world.activeScarlet.doDie(_world.activeScarlet);
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 450, _world.scarlet_a, 14, 8000, 8000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 29), 6250);
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 30), 7200);
					break;
				case 29:
					_world.activeScarlet.deleteMe();
					_world.activeScarlet = null;
					break;
				case 30:
					_world.activeScarlet = (L2GrandBossInstance) addSpawn(SCARLET2, _world.scarlet_x, _world.scarlet_y, _world.scarlet_z, _world.scarlet_h, false, 0, false, _world.instanceId);
					_world.activeScarlet.setIsInvul(true);
					_world.activeScarlet.setIsImmobilized(true);
					_world.activeScarlet.disableAllSkills();
					updateKnownList(_world, _world.activeScarlet);
					
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 450, _world.scarlet_a, 12, 500, 14000, 0, 0, 1, 0));
					
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 31), 8100);
					break;
				case 31:
					broadCastPacket(_world, new SocialAction(_world.activeScarlet.getObjectId(), 2));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 32), 9000);
					break;
				case 32:
					startPc();
					_world.activeScarlet.setIsInvul(false);
					_world.activeScarlet.setIsImmobilized(false);
					_world.activeScarlet.enableAllSkills();
					_world.isVideo = false;
					break;
				case 33:
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 300, _world.scarlet_a - 180, 5, 0, 7000, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.activeScarlet.getObjectId(), 200, _world.scarlet_a, 85, 4000, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 34), 7400);
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 35), 7500);
					break;
				case 34:
					_world.frintezza.doDie(_world.frintezza);
					break;
				case 35:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 100, 120, 5, 0, 7000, 0, 0, 1, 0));
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 100, 90, 5, 5000, 15000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 36), 7000);
					break;
				case 36:
					broadCastPacket(_world, new SpecialCamera(_world.frintezza.getObjectId(), 900, 90, 25, 7000, 10000, 0, 0, 1, 0));
					ThreadPoolManager.getInstance().scheduleGeneral(new IntroTask(_world, 37), 9000);
					break;
				case 37:
					controlStatus(_world);
					_world.isVideo = false;
					startPc();
					break;
			}
		}
		
		private void stopPc()
		{
			for (int objId : _world.allowed)
			{
				L2PcInstance player = L2World.getInstance().getPlayer(objId);
				if (player != null && player.isOnline() && player.getInstanceId() == _world.instanceId)
				{
					player.abortAttack();
					player.abortCast();
					player.disableAllSkills();
					player.setTarget(null);
					player.stopMove(null);
					player.setIsImmobilized(true);
					player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
				}
			}
		}
		
		private void startPc()
		{
			for (int objId : _world.allowed)
			{
				L2PcInstance player = L2World.getInstance().getPlayer(objId);
				if (player != null && player.isOnline() && player.getInstanceId() == _world.instanceId)
				{
					player.enableAllSkills();
					player.setIsImmobilized(false);
				}
			}
		}
		
		private void sendPacketX(L2GameServerPacket packet1, L2GameServerPacket packet2, int x)
		{
			for (int objId : _world.allowed)
			{
				L2PcInstance player = L2World.getInstance().getPlayer(objId);
				if (player != null && player.isOnline() && player.getInstanceId() == _world.instanceId)
				{
					if (player.getX() < x)
						player.sendPacket(packet1);
					else
						player.sendPacket(packet2);
				}
			}
		}
	}
	
	private class StatusTask implements Runnable
	{
		private final FETWorld _world;
		private final int _status;
		
		StatusTask(FETWorld world, int status)
		{
			_world = world;
			_status = status;
		}
		
		@Override
		public void run()
		{
			if (InstanceManager.getInstance().getWorld(_world.instanceId) != _world)
				return;
			switch (_status)
			{
				case 0:
					ThreadPoolManager.getInstance().scheduleGeneral(new StatusTask(_world, 1), 2000);
					for (int doorId : FIRST_ROOM_DOORS)
						openDoor(doorId, _world.instanceId);
					break;
				case 1:
					addAggroToMobs();
					break;
				case 2:
					ThreadPoolManager.getInstance().scheduleGeneral(new StatusTask(_world, 3), 100);
					for (int doorId : SECOND_ROOM_DOORS)
						openDoor(doorId, _world.instanceId);
					break;
				case 3:
					addAggroToMobs();
					break;
				case 4:
					controlStatus(_world);
					break;
			}
		}
		
		private void addAggroToMobs()
		{
			L2PcInstance target = L2World.getInstance().getPlayer(_world.allowed.get(Rnd.get(_world.allowed.size())));
			if (target == null || target.getInstanceId() != _world.instanceId || target.isDead() || target.isFakeDeath())
				for (int objId : _world.allowed)
				{
					target = L2World.getInstance().getPlayer(objId);
					if (target != null && target.getInstanceId() == _world.instanceId && !target.isDead() && !target.isFakeDeath())
						break;
					target = null;
				}
			for (L2Npc mob : _world.npcList)
			{
				mob.setRunning();
				if (target != null)
				{
					((L2MonsterInstance) mob).addDamageHate(target, 0, 500);
					mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
				}
				else
					mob.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, MOVE_TO_CENTER);
			}
		}
	}
	
	protected void setInstanceTimeRestrictions(FETWorld world)
	{
		Calendar reenter = Calendar.getInstance();
		reenter.set(Calendar.MINUTE, RESET_MIN);
		reenter.set(Calendar.HOUR_OF_DAY, RESET_HOUR);
		// if time is >= RESET_HOUR - roll to the next day
		if (reenter.getTimeInMillis() <= System.currentTimeMillis())
			reenter.add(Calendar.DAY_OF_MONTH, 1);
		if (reenter.get(Calendar.DAY_OF_WEEK) <= RESET_DAY_1)
			while (reenter.get(Calendar.DAY_OF_WEEK) != RESET_DAY_1)
				reenter.add(Calendar.DAY_OF_MONTH, 1);
		else
			while (reenter.get(Calendar.DAY_OF_WEEK) != RESET_DAY_2)
				reenter.add(Calendar.DAY_OF_MONTH, 1);
		
		SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.INSTANT_ZONE_S1_RESTRICTED);
		sm.addInstanceName(INSTANCEID);
		
		// set instance reenter time for all allowed players
		for (int objectId : world.allowed)
		{
			L2PcInstance player = L2World.getInstance().getPlayer(objectId);
			InstanceManager.getInstance().setInstanceTime(objectId, INSTANCEID, reenter.getTimeInMillis());
			if (player != null && player.isOnline())
				player.sendPacket(sm);
		}
	}
	
	private void broadCastPacket(FETWorld world, L2GameServerPacket packet)
	{
		for (int objId : world.allowed)
		{
			L2PcInstance player = L2World.getInstance().getPlayer(objId);
			if (player != null && player.isOnline() && player.getInstanceId() == world.instanceId)
				player.sendPacket(packet);
		}
	}
	
	private void updateKnownList(FETWorld world, L2Npc npc)
	{
		Map<Integer, L2PcInstance> npcKnownPlayers = npc.getKnownList().getKnownPlayers();
		for (int objId : world.allowed)
		{
			L2PcInstance player = L2World.getInstance().getPlayer(objId);
			if (player != null && player.isOnline() && player.getInstanceId() == world.instanceId)
				npcKnownPlayers.put(player.getObjectId(), player);
		}
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet, L2Skill skill)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (tmpworld instanceof FETWorld)
		{
			FETWorld world = (FETWorld) tmpworld;
			if (npc.getNpcId() == SCARLET1 && world.status == 3 && npc.getCurrentHp() < npc.getMaxHp() * 0.80)
			{
				controlStatus(world);
			}
			else if (npc.getNpcId() == SCARLET1 && world.status == 4 && npc.getCurrentHp() < npc.getMaxHp() * 0.20)
			{
				controlStatus(world);
			}
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (tmpworld instanceof FETWorld)
		{
			FETWorld world = (FETWorld) tmpworld;
			if (npc.getNpcId() == HALL_ALARM)
			{
				ThreadPoolManager.getInstance().scheduleGeneral(new StatusTask(world, 0), 2000);
				if (debug)
					_log.info("[Final Emperial Tomb] Hall alarm is disabled, doors will open!");
			}
			else if (npc.getNpcId() == DARK_CHOIR_PLAYER)
			{
				world.darkChoirPlayerCount--;
				if (world.darkChoirPlayerCount < 1)
				{
					ThreadPoolManager.getInstance().scheduleGeneral(new StatusTask(world, 2), 2000);
					if (debug)
						_log.info("[Final Emperial Tomb] All Dark Choir Players are killed, doors will open!");
				}
			}
			else if (npc.getNpcId() == SCARLET2)
			{
				controlStatus(world);
			}
			else if (world.status <= 2)
			{
				if (checkKillProgress(npc, world))
					controlStatus(world);
			}
			else if (world.demons.contains(npc))
			{
				world.demons.remove(npc);
			}
			else if (world.portraits.containsKey(npc))
			{
				world.portraits.remove(npc);
			}
		}
		return "";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		int npcId = npc.getNpcId();
		QuestState st = player.getQuestState(qn);
		if (st == null)
			st = newQuestState(player);
		if (npcId == GUIDE)
		{
			enterInstance(player, "FinalEmperialTomb.xml", ENTER_TELEPORT);
		}
		else if (npc.getNpcId() == CUBE)
		{
			int x = -87534 + Rnd.get(500);
			int y = -153048 + Rnd.get(500);
			player.teleToLocation(x, y, -9165);
			return null;
		}
		return "";
	}
	
	public FinalEmperialTomb(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		load();
		addStartNpc(GUIDE);
		addTalkId(GUIDE);
		addStartNpc(CUBE);
		addTalkId(CUBE);
		addKillId(HALL_ALARM);
		addKillId(DARK_CHOIR_PLAYER);
		addAttackId(SCARLET1);
		addKillId(SCARLET2);
		for (int mobId : PORTRAITS)
			addKillId(mobId);
		for (int mobId : DEMONS)
			addKillId(mobId);
		for (int mobId : _mustKillMobsId)
			addKillId(mobId);
	}
	
	public static void main(String[] args)
	{
		// now call the constructor (starts up the)
		new FinalEmperialTomb(-1, qn, "instances");
	}
}