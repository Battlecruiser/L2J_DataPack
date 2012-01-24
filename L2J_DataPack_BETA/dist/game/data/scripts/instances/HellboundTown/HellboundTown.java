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
package instances.HellboundTown;

import java.util.concurrent.ScheduledFuture;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager.InstanceWorld;
import com.l2jserver.gameserver.instancemanager.WalkingManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2QuestGuardInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * @author GKR
 */
public class HellboundTown extends Quest
{
	private class TownWorld extends InstanceWorld
	{
		protected L2MonsterInstance spawnedAmaskari;
		protected ScheduledFuture<?> activeAmaskariCall = null;
		public boolean isAmaskariDead = false;
		
		public TownWorld()
		{
			super();
		}
	}
	
	private static final String qn = "HellboundTown";
	private static final int INSTANCEID = 2;
	
	private static final NpcStringId[] NPCSTRING_ID =
	{
		NpcStringId.INVADER, NpcStringId.YOU_HAVE_DONE_WELL_IN_FINDING_ME_BUT_I_CANNOT_JUST_HAND_YOU_THE_KEY
	};
	
	private static final NpcStringId[] NATIVES_NPCSTRING_ID =
	{
		NpcStringId.THANK_YOU_FOR_SAVING_ME, NpcStringId.GUARDS_ARE_COMING_RUN, NpcStringId.NOW_I_CAN_ESCAPE_ON_MY_OWN
	};
	
	private static final int[][] ROUTE_DATA =
	{
		{
			14840, 251949
		},
		{
			16082, 251790
		},
		{
			16524, 255244
		},
		{
			17670, 252256
		}
	};
	
	private static final int TOMBSTONE = 32343;
	private static final int KANAF = 32346;
	private static final int KEYMASTER = 22361;
	private static final int AMASKARI = 22449;
	private static final int DOWNTOWN_NATIVE = 32358;
	private static final int TOWN_GUARD = 22359;
	private static final int TOWN_PATROL = 22360;
	
	private static final int[] AMASKARI_SPAWN_POINT =
	{
		19424, 253360, -2032, 16860
	};
	
	private static final int[] ENTRY_POINT =
	{
		14117, 255434, -2016
	};
	private static final int[] EXIT_POINT =
	{
		16262, 283651, -9700
	};
	
	private static final SkillHolder STONE = new SkillHolder(4616, 1);
	
	private static final int KEY = 9714;
	
	public HellboundTown(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
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
		if (npc.getFirstEffect(STONE.getSkill()) == null)
		{
			return "32358-02.htm";
		}
		return "32358-01.htm";
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = null;
		if (npc.getNpcId() == KANAF)
		{
			htmltext = checkConditions(player);
			
			if (htmltext == null)
			{
				enterInstance(player, "HellboundTown.xml");
			}
		}
		else if (npc.getNpcId() == TOMBSTONE)
		{
			InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
			if ((tmpworld != null) && (tmpworld instanceof TownWorld))
			{
				TownWorld world = (TownWorld) tmpworld;
				
				L2Party party = player.getParty();
				
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
					for (L2PcInstance partyMember : party.getPartyMembers())
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
						Instance inst = InstanceManager.getInstance().getInstance(world.instanceId);
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
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof TownWorld))
		{
			TownWorld world = (TownWorld) tmpworld;
			
			if (npc.getNpcId() == DOWNTOWN_NATIVE)
			{
				if (event.equalsIgnoreCase("rebuff") && !world.isAmaskariDead)
				{
					STONE.getSkill().getEffects(npc, npc);
				}
				else if (event.equalsIgnoreCase("break_chains"))
				{
					if ((npc.getFirstEffect(STONE.getSkill()) == null) || world.isAmaskariDead)
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NATIVES_NPCSTRING_ID[0]));
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NATIVES_NPCSTRING_ID[2]));
					}
					else
					{
						cancelQuestTimer("rebuff", npc, null);
						for (L2Effect e : npc.getAllEffects())
						{
							if (e.getSkill() == STONE.getSkill())
							{
								e.exit();
							}
						}
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NATIVES_NPCSTRING_ID[0]));
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NATIVES_NPCSTRING_ID[1]));
						HellboundManager.getInstance().updateTrust(10, true);
						npc.scheduleDespawn(3000);
						// Try to call Amaskari
						if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead() && (Rnd.get(1000) < 25) && Util.checkIfInRange(5000, npc, world.spawnedAmaskari, false))
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
		if (npc.getNpcId() == DOWNTOWN_NATIVE)
		{
			((L2QuestGuardInstance) npc).setPassive(true);
			((L2QuestGuardInstance) npc).setAutoAttackable(false);
			STONE.getSkill().getEffects(npc, npc);
			startQuestTimer("rebuff", 357000, npc, null);
		}
		else if ((npc.getNpcId() == TOWN_GUARD) || (npc.getNpcId() == KEYMASTER))
		{
			npc.setBusy(false);
			npc.setBusyMessage("");
		}
		else if ((npc.getNpcId() == TOWN_PATROL) && (getRoute(npc) > 0))
		{
			WalkingManager.getInstance().startMoving(npc, getRoute(npc));
		}
		return super.onSpawn(npc);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof TownWorld))
		{
			TownWorld world = (TownWorld) tmpworld;
			
			if (!npc.isBusy())
			{
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NPCSTRING_ID[0]));
				npc.setBusy(true);
				
				if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead() && (Rnd.get(1000) < 25) && Util.checkIfInRange(1000, npc, world.spawnedAmaskari, false))
				{
					if (world.activeAmaskariCall != null)
					{
						world.activeAmaskariCall.cancel(true);
					}
					
					world.activeAmaskariCall = ThreadPoolManager.getInstance().scheduleGeneral(new CallAmaskari(npc), 25000);
				}
			}
		}
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet, L2Skill skill)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof TownWorld))
		{
			TownWorld world = (TownWorld) tmpworld;
			
			if (!world.isAmaskariDead && !(npc.getBusyMessage().equalsIgnoreCase("atk") || npc.isBusy()))
			{
				int msgId;
				int range;
				switch (npc.getNpcId())
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
					npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.ALL, npc.getNpcId(), NPCSTRING_ID[msgId]));
				}
				npc.setBusy(true);
				npc.setBusyMessage("atk");
				
				if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead() && (Rnd.get(1000) < 25) && Util.checkIfInRange(range, npc, world.spawnedAmaskari, false))
				{
					if (world.activeAmaskariCall != null)
					{
						world.activeAmaskariCall.cancel(true);
					}
					
					world.activeAmaskariCall = ThreadPoolManager.getInstance().scheduleGeneral(new CallAmaskari(npc), 25000);
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isPet, skill);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if ((tmpworld != null) && (tmpworld instanceof TownWorld))
		{
			TownWorld world = (TownWorld) tmpworld;
			world.isAmaskariDead = true;
		}
		return super.onKill(npc, killer, isPet);
	}
	
	private String checkConditions(L2PcInstance player)
	{
		if (HellboundManager.getInstance().getLevel() < 10)
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
		L2Party party = player.getParty();
		
		if (party == null)
		{
			return false;
		}
		
		if (player.getObjectId() != party.getPartyLeaderOID())
		{
			player.sendPacket(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER);
			return false;
		}
		
		for (L2PcInstance partyMember : party.getPartyMembers())
		{
			if (partyMember.getLevel() < 78)
			{
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT);
				sm.addPcName(partyMember);
				party.broadcastToPartyMembers(sm);
				return false;
			}
			
			if (!Util.checkIfInRange(1000, player, partyMember, true))
			{
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED);
				sm.addPcName(partyMember);
				party.broadcastToPartyMembers(sm);
				return false;
			}
			
			if (InstanceManager.getInstance().getPlayerWorld(player) != null)
			{
				final SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				sm.addPcName(partyMember);
				party.broadcastToPartyMembers(sm);
				return false;
			}
		}
		return true;
	}
	
	private void teleportPlayer(L2PcInstance player, int[] tele, int instanceId)
	{
		player.setInstanceId(instanceId);
		player.teleToLocation((tele[0] - 50) + Rnd.get(100), (tele[1] - 50) + Rnd.get(100), tele[2]);
	}
	
	private int enterInstance(L2PcInstance player, String template)
	{
		int instanceId = 0;
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof TownWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, ENTRY_POINT, world.instanceId);
			return world.instanceId;
		}
		
		if (!checkTeleport(player))
		{
			return 0;
		}
		
		instanceId = InstanceManager.getInstance().createDynamicInstance(template);
		world = new TownWorld();
		world.instanceId = instanceId;
		world.templateId = INSTANCEID;
		world.status = 0;
		InstanceManager.getInstance().addWorld(world);
		_log.info("Hellbound Town started " + template + " Instance: " + instanceId + " created by player: " + player.getName());
		
		for (L2PcInstance partyMember : player.getParty().getPartyMembers())
		{
			teleportPlayer(partyMember, ENTRY_POINT, instanceId);
			world.allowed.add(partyMember.getObjectId());
		}
		
		((TownWorld) world).spawnedAmaskari = (L2MonsterInstance) addSpawn(AMASKARI, AMASKARI_SPAWN_POINT[0], AMASKARI_SPAWN_POINT[1], AMASKARI_SPAWN_POINT[2], AMASKARI_SPAWN_POINT[3], false, 0, false, instanceId);
		return instanceId;
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
				if ((tmpworld != null) && (tmpworld instanceof TownWorld))
				{
					TownWorld world = (TownWorld) tmpworld;
					
					if ((world.spawnedAmaskari != null) && !world.spawnedAmaskari.isDead())
					{
						world.spawnedAmaskari.teleToLocation(_caller.getX(), _caller.getY(), _caller.getZ());
						world.spawnedAmaskari.broadcastPacket(new NpcSay(world.spawnedAmaskari.getObjectId(), Say2.ALL, world.spawnedAmaskari.getNpcId(), NpcStringId.ILL_MAKE_YOU_FEEL_SUFFERING_LIKE_A_FLAME_THAT_IS_NEVER_EXTINGUISHED));
					}
				}
			}
		}
	}
	
	private static int getRoute(L2Npc npc)
	{
		int ret = -1;
		int[] coords = new int[2];
		coords[0] = npc.getSpawn().getLocx();
		coords[1] = npc.getSpawn().getLocy();
		
		for (int i = 0; i < 4; i++)
		{
			if ((ROUTE_DATA[i][0] == coords[0]) && (ROUTE_DATA[i][1] == coords[1]))
			{
				ret = i;
				break;
			}
		}
		
		return ret >= 0 ? ret + 2 : -1;
	}
	
	private static class ExitInstance implements Runnable
	{
		private final L2Party _party;
		private final TownWorld _world;
		
		public ExitInstance(L2Party party, TownWorld world)
		{
			_party = party;
			_world = world;
		}
		
		@Override
		public void run()
		{
			if ((_party != null) && (_world != null))
			{
				for (L2PcInstance partyMember : _party.getPartyMembers())
				{
					if ((partyMember != null) && !partyMember.isDead())
					{
						_world.allowed.remove(_world.allowed.indexOf(partyMember.getObjectId()));
						partyMember.setInstanceId(0);
						partyMember.teleToLocation((EXIT_POINT[0] - 100) + Rnd.get(50), (EXIT_POINT[1] - 100) + Rnd.get(50), EXIT_POINT[2]);
					}
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		new HellboundTown(-1, qn, "instances");
	}
}
