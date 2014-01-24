/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package instances.ChambersOfDelusion;

import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.Earthquake;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * Chambers of Delusion superclass.
 * @author GKR
 */
public abstract class Chamber extends Quest
{
	protected class CDWorld extends InstanceWorld
	{
		protected int currentRoom;
		protected final L2Party partyInside;
		protected final ScheduledFuture<?> _banishTask;
		protected ScheduledFuture<?> _roomChangeTask;
		
		protected CDWorld(L2Party party)
		{
			currentRoom = 0;
			partyInside = party;
			_banishTask = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new BanishTask(), 60000, 60000);
		}
		
		protected L2Party getPartyInside()
		{
			return partyInside;
		}
		
		protected void scheduleRoomChange(boolean bossRoom)
		{
			final Instance inst = InstanceManager.getInstance().getInstance(getInstanceId());
			final long nextInterval = bossRoom ? 60000L : (ROOM_CHANGE_INTERVAL + getRandom(ROOM_CHANGE_RANDOM_TIME)) * 1000L;
			
			// Schedule next room change only if remaining time is enough
			if ((inst.getInstanceEndTime() - System.currentTimeMillis()) > nextInterval)
			{
				_roomChangeTask = ThreadPoolManager.getInstance().scheduleGeneral(new ChangeRoomTask(), nextInterval - 5000);
			}
		}
		
		protected void stopBanishTask()
		{
			_banishTask.cancel(true);
		}
		
		protected void stopRoomChangeTask()
		{
			_roomChangeTask.cancel(true);
		}
		
		protected class BanishTask implements Runnable
		{
			@Override
			public void run()
			{
				final Instance inst = InstanceManager.getInstance().getInstance(getInstanceId());
				
				if ((inst == null) || ((inst.getInstanceEndTime() - System.currentTimeMillis()) < 60000))
				{
					_banishTask.cancel(false);
				}
				else
				{
					for (int objId : inst.getPlayers())
					{
						final L2PcInstance pl = L2World.getInstance().getPlayer(objId);
						if ((pl != null) && pl.isOnline())
						{
							if ((partyInside == null) || !pl.isInParty() || (partyInside != pl.getParty()))
							{
								exitInstance(pl);
							}
						}
					}
				}
			}
		}
		
		protected class ChangeRoomTask implements Runnable
		{
			@Override
			public void run()
			{
				try
				{
					earthQuake(CDWorld.this);
					Thread.sleep(5000);
					changeRoom(CDWorld.this);
				}
				catch (Exception e)
				{
					_log.log(Level.WARNING, getClass().getSimpleName() + " ChangeRoomTask exception : " + e.getMessage(), e);
				}
			}
		}
	}
	
	// Items
	private static final int ENRIA = 4042;
	private static final int ASOFE = 4043;
	private static final int THONS = 4044;
	private static final int LEONARD = 9628;
	private static final int DELUSION_MARK = 15311;
	
	// NPCs
	private final int ENTRANCE_GATEKEEPER;
	private final int ROOM_GATEKEEPER_FIRST;
	private final int ROOM_GATEKEEPER_LAST;
	private final int AENKINEL;
	private final int BOX;
	
	// Skills
	private static final SkillHolder SUCCESS_SKILL = new SkillHolder(5758, 1);
	private static final SkillHolder FAIL_SKILL = new SkillHolder(5376, 4);
	
	private static final int ROOM_CHANGE_INTERVAL = 480; // 8 min
	private static final int ROOM_CHANGE_RANDOM_TIME = 120; // 2 min
	
	// Instance restart time
	private static final int RESET_HOUR = 6;
	private static final int RESET_MIN = 30;
	
	// Following values vary between scripts
	private final int INSTANCEID;
	private final String INSTANCE_TEMPLATE;
	
	protected Location[] ROOM_ENTER_POINTS;
	
	protected Chamber(int questId, String name, String descr, int instanceId, String instanceTemplateName, int entranceGKId, int roomGKFirstId, int roomGKLastId, int aenkinelId, int boxId)
	{
		super(questId, name, descr);
		
		INSTANCEID = instanceId;
		INSTANCE_TEMPLATE = instanceTemplateName;
		ENTRANCE_GATEKEEPER = entranceGKId;
		ROOM_GATEKEEPER_FIRST = roomGKFirstId;
		ROOM_GATEKEEPER_LAST = roomGKLastId;
		AENKINEL = aenkinelId;
		BOX = boxId;
		
		addStartNpc(ENTRANCE_GATEKEEPER);
		addTalkId(ENTRANCE_GATEKEEPER);
		for (int i = ROOM_GATEKEEPER_FIRST; i <= ROOM_GATEKEEPER_LAST; i++)
		{
			addStartNpc(i);
			addTalkId(i);
		}
		addKillId(AENKINEL);
		addAttackId(BOX);
		addSpellFinishedId(BOX);
		addEventReceivedId(BOX);
	}
	
	private boolean isBigChamber()
	{
		return ((INSTANCEID == 131) || (INSTANCEID == 132));
	}
	
	private boolean isBossRoom(CDWorld world)
	{
		return (world.currentRoom == (ROOM_ENTER_POINTS.length - 1));
	}
	
	private boolean checkConditions(L2PcInstance player)
	{
		final L2Party party = player.getParty();
		if (party == null)
		{
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.NOT_IN_PARTY_CANT_ENTER));
			return false;
		}
		
		if (party.getLeader() != player)
		{
			player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER));
			return false;
		}
		
		for (L2PcInstance partyMember : party.getMembers())
		{
			if (partyMember.getLevel() < 80)
			{
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
			
			if (!Util.checkIfInRange(1000, player, partyMember, true))
			{
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
			
			if (isBigChamber())
			{
				final long reentertime = InstanceManager.getInstance().getInstanceTime(partyMember.getObjectId(), INSTANCEID);
				
				if (System.currentTimeMillis() < reentertime)
				{
					final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_MAY_NOT_REENTER_YET);
					sm.addPcName(partyMember);
					party.broadcastPacket(sm);
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void markRestriction(InstanceWorld world)
	{
		if (world instanceof CDWorld)
		{
			final Calendar reenter = Calendar.getInstance();
			final Calendar now = Calendar.getInstance();
			reenter.set(Calendar.MINUTE, RESET_MIN);
			reenter.set(Calendar.HOUR_OF_DAY, RESET_HOUR);
			if (reenter.before(now))
			{
				reenter.add(Calendar.DAY_OF_WEEK, 1);
			}
			final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.INSTANT_ZONE_S1_RESTRICTED);
			sm.addString(InstanceManager.getInstance().getInstanceIdName(world.getTemplateId()));
			// set instance reenter time for all allowed players
			for (int objectId : world.getAllowed())
			{
				final L2PcInstance player = L2World.getInstance().getPlayer(objectId);
				if ((player != null) && player.isOnline())
				{
					InstanceManager.getInstance().setInstanceTime(objectId, world.getTemplateId(), reenter.getTimeInMillis());
					player.sendPacket(sm);
				}
			}
		}
	}
	
	protected void changeRoom(CDWorld world)
	{
		final L2Party party = world.getPartyInside();
		final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
		
		if ((party == null) || (inst == null))
		{
			return;
		}
		
		int newRoom = world.currentRoom;
		
		// Do nothing, if there are raid room of Sqare or Tower Chamber
		if (isBigChamber() && isBossRoom(world))
		{
			return;
		}
		
		// Teleport to raid room 10 min or lesser before instance end time for Tower and Square Chambers
		else if (isBigChamber() && ((inst.getInstanceEndTime() - System.currentTimeMillis()) < 600000))
		{
			newRoom = ROOM_ENTER_POINTS.length - 1;
		}
		
		// 10% chance for teleport to raid room if not here already for Northern, Southern, Western and Eastern Chambers
		else if (!isBigChamber() && !isBossRoom(world) && (getRandom(100) < 10))
		{
			newRoom = ROOM_ENTER_POINTS.length - 1;
		}
		
		else
		{
			while (newRoom == world.currentRoom) // otherwise teleport to another room, except current
			{
				newRoom = getRandom(ROOM_ENTER_POINTS.length - 1);
			}
		}
		
		for (L2PcInstance partyMember : party.getMembers())
		{
			if (world.getInstanceId() == partyMember.getInstanceId())
			{
				partyMember.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
				partyMember.teleToLocation(ROOM_ENTER_POINTS[newRoom], true);
			}
		}
		
		world.currentRoom = newRoom;
		
		// Do not schedule room change for Square and Tower Chambers, if raid room is reached
		if (isBigChamber() && isBossRoom(world))
		{
			inst.setDuration((int) ((inst.getInstanceEndTime() - System.currentTimeMillis()) + 1200000)); // Add 20 min to instance time if raid room is reached
			
			for (L2Npc npc : inst.getNpcs())
			{
				if (npc.getId() == ROOM_GATEKEEPER_LAST)
				{
					npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.N21_MINUTES_ARE_ADDED_TO_THE_REMAINING_TIME_IN_THE_INSTANT_ZONE));
				}
			}
		}
		else
		{
			world.scheduleRoomChange(false);
		}
	}
	
	private void enter(CDWorld world)
	{
		final L2Party party = world.getPartyInside();
		
		if (party == null)
		{
			return;
		}
		
		for (L2PcInstance partyMember : party.getMembers())
		{
			QuestState st = partyMember.getQuestState(getName());
			if (st == null)
			{
				st = newQuestState(partyMember);
			}
			
			if (st.hasQuestItems(DELUSION_MARK))
			{
				st.takeItems(DELUSION_MARK, -1);
			}
			
			if (party.isLeader(partyMember))
			{
				st.giveItems(DELUSION_MARK, 1);
			}
			
			// Save location for teleport back into main hall
			st.set("return_point", Integer.toString(partyMember.getX()) + ";" + Integer.toString(partyMember.getY()) + ";" + Integer.toString(partyMember.getZ()));
			
			partyMember.setInstanceId(world.getInstanceId());
			world.addAllowed(partyMember.getObjectId());
		}
		
		changeRoom(world);
	}
	
	protected void earthQuake(CDWorld world)
	{
		final L2Party party = world.getPartyInside();
		
		if (party == null)
		{
			return;
		}
		
		for (L2PcInstance partyMember : party.getMembers())
		{
			if (world.getInstanceId() == partyMember.getInstanceId())
			{
				partyMember.sendPacket(new Earthquake(partyMember.getX(), partyMember.getY(), partyMember.getZ(), 20, 10));
			}
		}
	}
	
	protected int enterInstance(L2PcInstance player)
	{
		int instanceId = 0;
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof CDWorld))
			{
				player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER));
				return 0;
			}
			final CDWorld currentWorld = (CDWorld) world;
			teleportPlayer(player, ROOM_ENTER_POINTS[currentWorld.currentRoom], world.getInstanceId());
			return instanceId;
		}
		
		// New instance
		if (!checkConditions(player))
		{
			return 0;
		}
		final L2Party party = player.getParty();
		instanceId = InstanceManager.getInstance().createDynamicInstance(INSTANCE_TEMPLATE);
		world = new CDWorld(party);
		world.setInstanceId(instanceId);
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		InstanceManager.getInstance().addWorld(world);
		_log.info("Chamber Of Delusion started " + INSTANCE_TEMPLATE + " Instance: " + instanceId + " created by player: " + player.getName());
		enter((CDWorld) world);
		return instanceId;
	}
	
	protected void exitInstance(L2PcInstance player)
	{
		if ((player == null) || !player.isOnline() || (player.getInstanceId() == 0))
		{
			return;
		}
		final Instance inst = InstanceManager.getInstance().getInstance(player.getInstanceId());
		Location ret = inst.getSpawnLoc();
		final QuestState st = player.getQuestState(getName());
		
		if (st != null)
		{
			String return_point = st.get("return_point");
			if (return_point != null)
			{
				String[] coords = return_point.split(";");
				if (coords.length == 3)
				{
					try
					{
						int x = Integer.parseInt(coords[0]);
						int y = Integer.parseInt(coords[1]);
						int z = Integer.parseInt(coords[2]);
						ret.setLocation(new Location(x, y, z));
					}
					catch (Exception e)
					{
					}
				}
			}
		}
		
		teleportPlayer(player, ret, 0);
		final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			world.removeAllowed((player.getObjectId()));
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if ((player != null) && (tmpworld != null) && (tmpworld instanceof CDWorld) && (npc.getId() >= ROOM_GATEKEEPER_FIRST) && (npc.getId() <= ROOM_GATEKEEPER_LAST))
		{
			final CDWorld world = (CDWorld) tmpworld;
			
			QuestState st = player.getQuestState(getName());
			
			if (st == null)
			{
				st = newQuestState(player);
			}
			
			// Change room from dialog
			else if (event.equals("next_room"))
			{
				if (player.getParty() == null)
				{
					htmltext = getHtm(player.getHtmlPrefix(), "data/scripts/instances/ChambersOfDelusion/no_party.html");
				}
				
				else if (player.getParty().getLeaderObjectId() != player.getObjectId())
				{
					htmltext = getHtm(player.getHtmlPrefix(), "data/scripts/instances/ChambersOfDelusion/no_leader.html");
				}
				
				else if (hasQuestItems(player, DELUSION_MARK))
				{
					st.takeItems(DELUSION_MARK, 1);
					world.stopRoomChangeTask();
					changeRoom(world);
				}
				
				else
				{
					htmltext = getHtm(player.getHtmlPrefix(), "data/scripts/instances/ChambersOfDelusion/no_item.html");
				}
			}
			
			else if (event.equals("go_out"))
			{
				if (player.getParty() == null)
				{
					htmltext = getHtm(player.getHtmlPrefix(), "data/scripts/instances/ChambersOfDelusion/no_party.html");
				}
				else if (player.getParty().getLeaderObjectId() != player.getObjectId())
				{
					htmltext = getHtm(player.getHtmlPrefix(), "data/scripts/instances/ChambersOfDelusion/no_leader.html");
				}
				else
				{
					final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
					
					world.stopRoomChangeTask();
					world.stopBanishTask();
					
					for (L2PcInstance partyMember : player.getParty().getMembers())
					{
						exitInstance(partyMember);
					}
					
					inst.setEmptyDestroyTime(0);
				}
			}
			
			else if (event.equals("look_party"))
			{
				if ((player.getParty() != null) && (player.getParty() == world.getPartyInside()))
				{
					player.teleToLocation(ROOM_ENTER_POINTS[world.currentRoom], false);
				}
			}
		}
		
		return htmltext;
	}
	
	@Override
	public String onAttack(final L2Npc npc, final L2PcInstance attacker, final int damage, final boolean isPet, final L2Skill skill)
	{
		if (!npc.isBusy() && (npc.getCurrentHp() < (npc.getMaxHp() / 10)))
		{
			npc.setBusy(true);
			if (getRandom(100) < 25) // 25% chance to reward
			{
				if (getRandom(100) < 33)
				{
					npc.dropItem(attacker, ENRIA, (int) (3 * Config.RATE_DROP_ITEMS));
				}
				if (getRandom(100) < 50)
				{
					npc.dropItem(attacker, THONS, (int) (4 * Config.RATE_DROP_ITEMS));
				}
				if (getRandom(100) < 50)
				{
					npc.dropItem(attacker, ASOFE, (int) (4 * Config.RATE_DROP_ITEMS));
				}
				if (getRandom(100) < 16)
				{
					npc.dropItem(attacker, LEONARD, (int) (2 * Config.RATE_DROP_ITEMS));
				}
				
				npc.broadcastEvent("SCE_LUCKY", 2000, null);
				npc.doCast(SUCCESS_SKILL.getSkill());
			}
			else
			{
				npc.broadcastEvent("SCE_DREAM_FIRE_IN_THE_HOLE", 2000, null);
			}
		}
		
		return super.onAttack(npc, attacker, damage, isPet, skill);
	}
	
	@Override
	public String onEventReceived(String eventName, L2Npc sender, L2Npc receiver, L2Object reference)
	{
		switch (eventName)
		{
			case "SCE_LUCKY":
				receiver.setBusy(true);
				receiver.doCast(SUCCESS_SKILL.getSkill());
				break;
			case "SCE_DREAM_FIRE_IN_THE_HOLE":
				receiver.setBusy(true);
				receiver.doCast(FAIL_SKILL.getSkill());
				break;
		}
		
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
		if ((tmpworld != null) && (tmpworld instanceof CDWorld))
		{
			final CDWorld world = (CDWorld) tmpworld;
			final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
			
			if (isBigChamber())
			{
				markRestriction(world); // Set reenter restriction
				if ((inst.getInstanceEndTime() - System.currentTimeMillis()) > 300000)
				{
					inst.setDuration(300000); // Finish instance in 5 minutes
				}
			}
			else
			{
				world.stopRoomChangeTask();
				world.scheduleRoomChange(true);
			}
			
			inst.spawnGroup("boxes");
		}
		
		return super.onKill(npc, player, isPet);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if ((npc.getId() == BOX) && ((skill.getId() == 5376) || (skill.getId() == 5758)) && !npc.isDead())
		{
			npc.doDie(player);
		}
		
		return super.onSpellFinished(npc, player, skill);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		int npcId = npc.getId();
		QuestState st = player.getQuestState(getName());
		
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		if (npcId == ENTRANCE_GATEKEEPER)
		{
			enterInstance(player);
		}
		
		return "";
	}
}