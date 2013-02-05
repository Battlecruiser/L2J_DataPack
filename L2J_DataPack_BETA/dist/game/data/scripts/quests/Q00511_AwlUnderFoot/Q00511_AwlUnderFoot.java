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
package quests.Q00511_AwlUnderFoot;

import java.util.HashMap;
import java.util.Map;

import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2RaidBossInstance;
import com.l2jserver.gameserver.model.entity.Fort;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.util.Util;

/**
 * Awl Under Foot (511)
 * @author Gigiikun
 */
public final class Q00511_AwlUnderFoot extends Quest
{
	protected class FAUWorld extends InstanceWorld
	{
		
	}
	
	public static class FortDungeon
	{
		private final int INSTANCEID;
		private long _reEnterTime = 0;
		
		public FortDungeon(int iId)
		{
			INSTANCEID = iId;
		}
		
		public int getInstanceId()
		{
			return INSTANCEID;
		}
		
		public long getReEnterTime()
		{
			return _reEnterTime;
		}
		
		public void setReEnterTime(long time)
		{
			_reEnterTime = time;
		}
	}
	
	private class spawnRaid implements Runnable
	{
		private final FAUWorld _world;
		
		public spawnRaid(FAUWorld world)
		{
			_world = world;
		}
		
		@Override
		public void run()
		{
			try
			{
				int spawnId;
				if (_world.getStatus() == 0)
				{
					spawnId = RAIDS1[getRandom(RAIDS1.length)];
				}
				else if (_world.getStatus() == 1)
				{
					spawnId = RAIDS2[getRandom(RAIDS2.length)];
				}
				else
				{
					spawnId = RAIDS3[getRandom(RAIDS3.length)];
				}
				L2Npc raid = addSpawn(spawnId, 53319, 245814, -6576, 0, false, 0, false, _world.getInstanceId());
				if (raid instanceof L2RaidBossInstance)
				{
					((L2RaidBossInstance) raid).setUseRaidCurse(false);
				}
			}
			catch (Exception e)
			{
				_log.warning("Fortress AwlUnderFoot Raid Spawn error: " + e);
			}
		}
	}
	
	private static final boolean debug = false;
	private static final long REENTERTIME = 14400000;
	
	private static final long RAID_SPAWN_DELAY = 120000;
	
	private final Map<Integer, FortDungeon> _fortDungeons = new HashMap<>(21);
	// QUEST ITEMS
	private static final int DL_MARK = 9797;
	// REWARDS
	private static final int KNIGHT_EPALUETTE = 9912;
	// MONSTER TO KILL -- Only last 3 Raids (lvl ordered) give DL_MARK
	protected static final int[] RAIDS1 =
	{
		25572,
		25575,
		25578
	};
	protected static final int[] RAIDS2 =
	{
		25579,
		25582,
		25585,
		25588
	};
	protected static final int[] RAIDS3 =
	{
		25589,
		25592,
		25593
	};
	
	// Skill
	private static final SkillHolder RAID_CURSE = new SkillHolder(5456, 1);
	
	public Q00511_AwlUnderFoot(int questId, String name, String descr)
	{
		super(questId, name, descr);
		_fortDungeons.put(35666, new FortDungeon(22));
		_fortDungeons.put(35698, new FortDungeon(23));
		_fortDungeons.put(35735, new FortDungeon(24));
		_fortDungeons.put(35767, new FortDungeon(25));
		_fortDungeons.put(35804, new FortDungeon(26));
		_fortDungeons.put(35835, new FortDungeon(27));
		_fortDungeons.put(35867, new FortDungeon(28));
		_fortDungeons.put(35904, new FortDungeon(29));
		_fortDungeons.put(35936, new FortDungeon(30));
		_fortDungeons.put(35974, new FortDungeon(31));
		_fortDungeons.put(36011, new FortDungeon(32));
		_fortDungeons.put(36043, new FortDungeon(33));
		_fortDungeons.put(36081, new FortDungeon(34));
		_fortDungeons.put(36118, new FortDungeon(35));
		_fortDungeons.put(36149, new FortDungeon(36));
		_fortDungeons.put(36181, new FortDungeon(37));
		_fortDungeons.put(36219, new FortDungeon(38));
		_fortDungeons.put(36257, new FortDungeon(39));
		_fortDungeons.put(36294, new FortDungeon(40));
		_fortDungeons.put(36326, new FortDungeon(41));
		_fortDungeons.put(36364, new FortDungeon(42));
		
		for (int i : _fortDungeons.keySet())
		{
			addStartNpc(i);
			addTalkId(i);
		}
		
		addKillId(RAIDS1);
		addKillId(RAIDS2);
		addKillId(RAIDS3);
		
		for (int i = 25572; i <= 25595; i++)
		{
			addAttackId(i);
		}
	}
	
	private String checkConditions(L2PcInstance player)
	{
		if (debug)
		{
			return null;
		}
		L2Party party = player.getParty();
		if (party == null)
		{
			return "FortressWarden-03.htm";
		}
		if (party.getLeader() != player)
		{
			return getHtm(player.getHtmlPrefix(), "FortressWarden-04.htm").replace("%leader%", party.getLeader().getName());
		}
		for (L2PcInstance partyMember : party.getMembers())
		{
			QuestState st = partyMember.getQuestState(getName());
			if ((st == null) || (st.getInt("cond") < 1))
			{
				return getHtm(player.getHtmlPrefix(), "FortressWarden-05.htm").replace("%player%", partyMember.getName());
			}
			if (!Util.checkIfInRange(1000, player, partyMember, true))
			{
				return getHtm(player.getHtmlPrefix(), "FortressWarden-06.htm").replace("%player%", partyMember.getName());
			}
		}
		return null;
	}
	
	private String checkFortCondition(L2PcInstance player, L2Npc npc, boolean isEnter)
	{
		Fort fortress = npc.getFort();
		FortDungeon dungeon = _fortDungeons.get(npc.getNpcId());
		if ((player == null) || (fortress == null) || (dungeon == null))
		{
			return "FortressWarden-01.htm";
		}
		if ((player.getClan() == null) || (player.getClan().getFortId() != fortress.getFortId()))
		{
			return "FortressWarden-01.htm";
		}
		else if (fortress.getFortState() == 0)
		{
			return "FortressWarden-02a.htm";
		}
		else if (fortress.getFortState() == 2)
		{
			return "FortressWarden-02b.htm";
		}
		else if (isEnter && (dungeon.getReEnterTime() > System.currentTimeMillis()))
		{
			return "FortressWarden-07.htm";
		}
		
		L2Party party = player.getParty();
		if (party == null)
		{
			return "FortressWarden-03.htm";
		}
		for (L2PcInstance partyMember : party.getMembers())
		{
			if ((partyMember.getClan() == null) || (partyMember.getClan().getFortId() == 0) || (partyMember.getClan().getFortId() != fortress.getFortId()))
			{
				return getHtm(player.getHtmlPrefix(), "FortressWarden-05.htm").replace("%player%", partyMember.getName());
			}
		}
		return null;
	}
	
	protected String enterInstance(L2PcInstance player, String template, int[] coords, FortDungeon dungeon, String ret)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof FAUWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return "";
			}
			teleportPlayer(player, coords, world.getInstanceId());
			return "";
		}
		// New instance
		if (ret != null)
		{
			return ret;
		}
		ret = checkConditions(player);
		if (ret != null)
		{
			return ret;
		}
		L2Party party = player.getParty();
		int instanceId = InstanceManager.getInstance().createDynamicInstance(template);
		Instance ins = InstanceManager.getInstance().getInstance(instanceId);
		ins.setSpawnLoc(new Location(player));
		world = new FAUWorld();
		world.setInstanceId(instanceId);
		world.setTemplateId(dungeon.getInstanceId());
		world.setStatus(0);
		dungeon.setReEnterTime(System.currentTimeMillis() + REENTERTIME);
		InstanceManager.getInstance().addWorld(world);
		_log.info("Fortress AwlUnderFoot started " + template + " Instance: " + instanceId + " created by player: " + player.getName());
		ThreadPoolManager.getInstance().scheduleGeneral(new spawnRaid((FAUWorld) world), RAID_SPAWN_DELAY);
		
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
				if (partyMember.getQuestState(getName()) == null)
				{
					newQuestState(partyMember);
				}
			}
		}
		return getHtm(player.getHtmlPrefix(), "FortressWarden-08.htm").replace("%clan%", player.getClan().getName());
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		if (event.equalsIgnoreCase("enter"))
		{
			int[] tele = new int[3];
			tele[0] = 53322;
			tele[1] = 246380;
			tele[2] = -6580;
			return enterInstance(player, "fortdungeon.xml", tele, _fortDungeons.get(npc.getNpcId()), checkFortCondition(player, npc, true));
		}
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		if (event.equalsIgnoreCase("FortressWarden-10.htm"))
		{
			if (st.isCond(0))
			{
				st.startQuest();
			}
		}
		else if (event.equalsIgnoreCase("FortressWarden-15.htm"))
		{
			st.exitQuest(true, true);
		}
		return htmltext;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
	{
		L2Playable attacker = (isPet ? player.getSummon() : player);
		if ((attacker.getLevel() - npc.getLevel()) >= 9)
		{
			if ((attacker.getBuffCount() > 0) || (attacker.getDanceCount() > 0))
			{
				npc.setTarget(attacker);
				npc.doSimultaneousCast(RAID_CURSE.getSkill());
			}
			else if (player.getParty() != null)
			{
				for (L2PcInstance pmember : player.getParty().getMembers())
				{
					if ((pmember.getBuffCount() > 0) || (pmember.getDanceCount() > 0))
					{
						npc.setTarget(pmember);
						npc.doSimultaneousCast(RAID_CURSE.getSkill());
					}
				}
			}
		}
		return super.onAttack(npc, player, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		if (tmpworld instanceof FAUWorld)
		{
			FAUWorld world = (FAUWorld) tmpworld;
			if (Util.contains(RAIDS3, npc.getNpcId()))
			{
				if (player.getParty() != null)
				{
					for (L2PcInstance pl : player.getParty().getMembers())
					{
						rewardPlayer(pl);
					}
				}
				else
				{
					rewardPlayer(player);
				}
				
				Instance instanceObj = InstanceManager.getInstance().getInstance(world.getInstanceId());
				instanceObj.setDuration(360000);
				instanceObj.removeNpcs();
			}
			else
			{
				world.incStatus();
				ThreadPoolManager.getInstance().scheduleGeneral(new spawnRaid(world), RAID_SPAWN_DELAY);
			}
		}
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		String ret = checkFortCondition(player, npc, false);
		if (ret != null)
		{
			return ret;
		}
		else if (st != null)
		{
			int npcId = npc.getNpcId();
			int cond = 0;
			if (st.getState() == State.CREATED)
			{
				st.set("cond", "0");
			}
			else
			{
				cond = st.getInt("cond");
			}
			if (_fortDungeons.containsKey(npcId) && (cond == 0))
			{
				if (player.getLevel() >= 60)
				{
					htmltext = "FortressWarden-09.htm";
				}
				else
				{
					htmltext = "FortressWarden-00.htm";
					st.exitQuest(true);
				}
			}
			else if (_fortDungeons.containsKey(npcId) && (cond > 0) && (st.getState() == State.STARTED))
			{
				long count = st.getQuestItemsCount(DL_MARK);
				if ((cond == 1) && (count > 0))
				{
					htmltext = "FortressWarden-14.htm";
					st.takeItems(DL_MARK, -1);
					st.rewardItems(KNIGHT_EPALUETTE, count);
				}
				else if ((cond == 1) && (count == 0))
				{
					htmltext = "FortressWarden-10.htm";
				}
			}
		}
		return htmltext;
	}
	
	private void rewardPlayer(L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st.isCond(1))
		{
			st.giveItems(DL_MARK, 140);
			st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
		}
	}
	
	private void teleportPlayer(L2PcInstance player, int[] coords, int instanceId)
	{
		player.setInstanceId(instanceId);
		player.teleToLocation(coords[0], coords[1], coords[2]);
	}
	
	public static void main(String[] args)
	{
		new Q00511_AwlUnderFoot(511, Q00511_AwlUnderFoot.class.getSimpleName(), "instances");
	}
}
