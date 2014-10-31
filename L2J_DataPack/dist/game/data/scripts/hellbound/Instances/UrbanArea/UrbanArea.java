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
package hellbound.Instances.UrbanArea;

import java.util.concurrent.ScheduledFuture;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2QuestGuardInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;

import hellbound.HellboundEngine;

/**
 * Urban Area instance zone.
 * @author GKR
 */
public final class UrbanArea extends AbstractNpcAI
{
	protected class UrbanAreaWorld extends InstanceWorld
	{
		protected L2MonsterInstance spawnedAmaskari;
		protected ScheduledFuture<?> activeAmaskariCall = null;
		public boolean isAmaskariDead = false;
	}
	
	private static final int TEMPLATE_ID = 2;
	
	private static final NpcStringId[] NPCSTRING_ID =
	{
		NpcStringId.INVADER,
		NpcStringId.YOU_HAVE_DONE_WELL_IN_FINDING_ME_BUT_I_CANNOT_JUST_HAND_YOU_THE_KEY
	};
	
	private static final NpcStringId[] NATIVES_NPCSTRING_ID =
	{
		NpcStringId.THANK_YOU_FOR_SAVING_ME,
		NpcStringId.GUARDS_ARE_COMING_RUN,
		NpcStringId.NOW_I_CAN_ESCAPE_ON_MY_OWN
	};
	
	private static final int TOMBSTONE = 32343;
	private static final int KANAF = 32346;
	private static final int KEYMASTER = 22361;
	private static final int AMASKARI = 22449;
	private static final int DOWNTOWN_NATIVE = 32358;
	private static final int TOWN_GUARD = 22359;
	private static final int TOWN_PATROL = 22360;
	private static final Location AMASKARI_SPAWN_POINT = new Location(19424, 253360, -2032, 16860);
	private static final Location ENTRY_POINT = new Location(14117, 255434, -2016);
	protected static final Location EXIT_POINT = new Location(16262, 283651, -9700);
	private static final SkillHolder STONE = new SkillHolder(4616, 1);
	private static final int KEY = 9714;
	
	public UrbanArea()
	{
		super(UrbanArea.class.getSimpleName(), "hellbound/Instances");
		addFirstTalkId(DOWNTOWN_NATIVE);
		addStartNpc(KANAF);
		addStartNpc(DOWNTOWN_NATIVE);
		addTalkId(KANAF);
		addTalkId(DOWNTOWN_NATIVE);
		addAttackId(TOWN_GUARD);
		addAttackId(KEYMASTER);
		addAggroRangeEnterId(TOWN_GUARD);
		addKillId(AMASKARI);
		addSpawnId(DOWNTOWN_NATIVE);
		addSpawnId(TOWN_GUARD);
		addSpawnId(TOWN_PATROL);
		addSpawnId(KEYMASTER);
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (!npc.isAffectedBySkill(STONE.getSkillId()))
		{
			return "32358-02.htm";
		}
		return "32358-01.htm";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (npc.getId() == KANAF)
		{
			htmltext = checkConditions(player);
			
			if (htmltext == null)
			{
				enterInstance(player, "UrbanArea.xml");
			}
		}
		else if (npc.getId() == TOMBSTONE)
		{
			InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
			if ((tmpworld != null) && (tmpworld instanceof UrbanAreaWorld))
			{
				final UrbanAreaWorld world = (UrbanAreaWorld) tmpworld;
				
				final L2Party party = player.getParty();
				
				if (party == null)
				{
					htmltext = "32343-02.htm";
				}
				else if (npc.isBusy())
				{
					htmltext = "32343-02c.htm";
				}
				else if (player.getInventory().getInventoryItemCount(KEY, -1, false) >= 1)
				{
					for (L2PcInstance partyMember : party.getMembers())
					{
						if (!Util.checkIfInRange(300, npc, partyMember, true))
						{
							return "32343-02b.htm";
						}
					}
					
					if (player.destroyItemByItemId("Quest", KEY, 1, npc, true))
					{
						npc.setBusy(true);
						// destroy instance after 5 min
						Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
						inst.setDuration(5 * 60000);
						inst.setEmptyDestroyTime(0);
						ThreadPoolManager.getInstance().scheduleGeneral(new ExitInstance(party, world), 285000);
						htmltext = "32343-02d.htm";
					}
				}
				else
				{
					htmltext = "32343-02a.htm";
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof UrbanAreaWorld))
		{
			UrbanAreaWorld world = (UrbanAreaWorld) tmpworld;
			
			if (npc.getId() == DOWNTOWN_NATIVE)
			{
				if (event.equalsIgnoreCase("rebuff") && !world.isAmaskariDead)
				{
					STONE.getSkill().applyEffects(npc, npc);
				}
				else if (event.equalsIgnoreCase("break_chains"))
				{
					if (!npc.isAffectedBySkill(STONE.getSkillId()) || world.isAmaskariDead)
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NATIVES_NPCSTRING_ID[0]));
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NATIVES_NPCSTRING_ID[2]));
					}
					else
					{
						cancelQuestTimer("rebuff", npc, null);
						if (npc.isAffectedBySkill(STONE.getSkillId()))
						{
							npc.stopSkillEffects(false, STONE.getSkillId());
						}
						
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NATIVES_NPCSTRING_ID[0]));
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NATIVES_NPCSTRING_ID[1]));
						HellboundEngine.getInstance().updateTrust(10, true);
						npc.scheduleDespawn(3000);
						// Try to call Amaskari
						if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead() && (getRandom(1000) < 25) && Util.checkIfInRange(5000, npc, world.spawnedAmaskari, false))
						{
							if (world.activeAmaskariCall != null)
							{
								world.activeAmaskariCall.cancel(true);
							}
							
							world.activeAmaskariCall = ThreadPoolManager.getInstance().scheduleGeneral(new CallAmaskari(npc), 25000);
						}
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		if (npc.getId() == DOWNTOWN_NATIVE)
		{
			((L2QuestGuardInstance) npc).setPassive(true);
			((L2QuestGuardInstance) npc).setAutoAttackable(false);
			STONE.getSkill().applyEffects(npc, npc);
			startQuestTimer("rebuff", 357000, npc, null);
		}
		else if ((npc.getId() == TOWN_GUARD) || (npc.getId() == KEYMASTER))
		{
			npc.setBusy(false);
			npc.setBusyMessage("");
		}
		
		return super.onSpawn(npc);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof UrbanAreaWorld))
		{
			UrbanAreaWorld world = (UrbanAreaWorld) tmpworld;
			
			if (!npc.isBusy())
			{
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NPCSTRING_ID[0]));
				npc.setBusy(true);
				
				if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead() && (getRandom(1000) < 25) && Util.checkIfInRange(1000, npc, world.spawnedAmaskari, false))
				{
					if (world.activeAmaskariCall != null)
					{
						world.activeAmaskariCall.cancel(true);
					}
					
					world.activeAmaskariCall = ThreadPoolManager.getInstance().scheduleGeneral(new CallAmaskari(npc), 25000);
				}
			}
		}
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon, Skill skill)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof UrbanAreaWorld))
		{
			UrbanAreaWorld world = (UrbanAreaWorld) tmpworld;
			
			if (!world.isAmaskariDead && !(npc.getBusyMessage().equalsIgnoreCase("atk") || npc.isBusy()))
			{
				int msgId;
				int range;
				switch (npc.getId())
				{
					case TOWN_GUARD:
						msgId = 0;
						range = 1000;
						break;
					case KEYMASTER:
						msgId = 1;
						range = 5000;
						break;
					default:
						msgId = -1;
						range = 0;
				}
				if (msgId >= 0)
				{
					npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NPCSTRING_ID[msgId]));
				}
				npc.setBusy(true);
				npc.setBusyMessage("atk");
				
				if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead() && (getRandom(1000) < 25) && Util.checkIfInRange(range, npc, world.spawnedAmaskari, false))
				{
					if (world.activeAmaskariCall != null)
					{
						world.activeAmaskariCall.cancel(true);
					}
					
					world.activeAmaskariCall = ThreadPoolManager.getInstance().scheduleGeneral(new CallAmaskari(npc), 25000);
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon, skill);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof UrbanAreaWorld))
		{
			UrbanAreaWorld world = (UrbanAreaWorld) tmpworld;
			world.isAmaskariDead = true;
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	private String checkConditions(L2PcInstance player)
	{
		if (HellboundEngine.getInstance().getLevel() < 10)
		{
			return "32346-lvl.htm";
		}
		
		if (player.getParty() == null)
		{
			return "32346-party.htm";
		}
		return null;
	}
	
	private boolean checkTeleport(L2PcInstance player)
	{
		final L2Party party = player.getParty();
		
		if (party == null)
		{
			return false;
		}
		
		if (!party.isLeader(player))
		{
			player.sendPacket(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER);
			return false;
		}
		
		for (L2PcInstance partyMember : party.getMembers())
		{
			if (partyMember.getLevel() < 78)
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
			
			if (InstanceManager.getInstance().getPlayerWorld(player) != null)
			{
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				sm.addPcName(partyMember);
				party.broadcastPacket(sm);
				return false;
			}
		}
		return true;
	}
	
	private void enterInstance(L2PcInstance player, String template)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof UrbanAreaWorld)
			{
				teleportPlayer(player, ENTRY_POINT, world.getInstanceId());
				return;
			}
			player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
			return;
		}
		
		if (!checkTeleport(player))
		{
			return;
		}
		
		world = new UrbanAreaWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(TEMPLATE_ID);
		world.addAllowed(player.getObjectId());
		world.setStatus(0);
		InstanceManager.getInstance().addWorld(world);
		teleportPlayer(player, ENTRY_POINT, world.getInstanceId());
		
		_log.info("Hellbound Town started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		
		for (L2PcInstance partyMember : player.getParty().getMembers())
		{
			teleportPlayer(partyMember, ENTRY_POINT, world.getInstanceId());
			world.addAllowed(partyMember.getObjectId());
		}
		
		((UrbanAreaWorld) world).spawnedAmaskari = (L2MonsterInstance) addSpawn(AMASKARI, AMASKARI_SPAWN_POINT, false, 0, false, world.getInstanceId());
	}
	
	private static class CallAmaskari implements Runnable
	{
		private final L2Npc _caller;
		
		public CallAmaskari(L2Npc caller)
		{
			_caller = caller;
		}
		
		@Override
		public void run()
		{
			if ((_caller != null) && !_caller.isDead())
			{
				InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(_caller.getInstanceId());
				if ((tmpworld != null) && (tmpworld instanceof UrbanAreaWorld))
				{
					UrbanAreaWorld world = (UrbanAreaWorld) tmpworld;
					
					if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead())
					{
						world.spawnedAmaskari.teleToLocation(_caller.getLocation());
						world.spawnedAmaskari.broadcastPacket(new NpcSay(world.spawnedAmaskari.getObjectId(), Say2.NPC_ALL, world.spawnedAmaskari.getId(), NpcStringId.ILL_MAKE_YOU_FEEL_SUFFERING_LIKE_A_FLAME_THAT_IS_NEVER_EXTINGUISHED));
					}
				}
			}
		}
	}
	
	private class ExitInstance implements Runnable
	{
		private final L2Party _party;
		private final UrbanAreaWorld _world;
		
		public ExitInstance(L2Party party, UrbanAreaWorld world)
		{
			_party = party;
			_world = world;
		}
		
		@Override
		public void run()
		{
			if ((_party != null) && (_world != null))
			{
				for (L2PcInstance partyMember : _party.getMembers())
				{
					if ((partyMember != null) && !partyMember.isDead())
					{
						_world.removeAllowed(partyMember.getObjectId());
						teleportPlayer(partyMember, EXIT_POINT, 0);
					}
				}
			}
		}
	}
}