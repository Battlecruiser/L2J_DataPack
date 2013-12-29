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
package instances.MithrilMine;

import quests.Q10284_AcquisitionOfDivineSword.Q10284_AcquisitionOfDivineSword;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Mithril Mine instance zone.
 * @author Adry_85
 */
public final class MithrilMine extends AbstractNpcAI
{
	protected class MMWorld extends InstanceWorld
	{
		long storeTime = 0;
		int _count = 0;
	}
	
	private static final int INSTANCEID = 138;
	// NPCs
	private static final int KEGOR = 18846;
	private static final int MITHRIL_MILLIPEDE = 22766;
	private static final int KRUN = 32653;
	private static final int TARUN = 32654;
	// Item
	private static final int COLD_RESISTANCE_POTION = 15514;
	// Skill
	private static SkillHolder BLESS_OF_SWORD = new SkillHolder(6286, 1);
	// Location
	private static final Location START_LOC = new Location(186852, -173492, -3763, 0, 0);
	private static final Location EXIT_LOC = new Location(178823, -184303, -347, 0, 0);
	private static final Location[] MOB_SPAWNS = new Location[]
	{
		new Location(185216, -184112, -3308, -15396),
		new Location(185456, -184240, -3308, -19668),
		new Location(185712, -184384, -3308, -26696),
		new Location(185920, -184544, -3308, -32544),
		new Location(185664, -184720, -3308, 27892)
	};
	
	private MithrilMine()
	{
		super(MithrilMine.class.getSimpleName(), "instances");
		addFirstTalkId(KEGOR);
		addKillId(KEGOR, MITHRIL_MILLIPEDE);
		addStartNpc(TARUN, KRUN);
		addTalkId(TARUN, KRUN, KEGOR);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final InstanceWorld world = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		switch (event)
		{
			case "BUFF":
			{
				if ((player != null) && npc.isInsideRadius(player, 1000, true, false) && npc.isScriptValue(1) && !player.isDead())
				{
					npc.setTarget(player);
					npc.doCast(BLESS_OF_SWORD.getSkill());
				}
				startQuestTimer("BUFF", 30000, npc, player);
				break;
			}
			case "TIMER":
			{
				if (world instanceof MMWorld)
				{
					for (Location loc : MOB_SPAWNS)
					{
						final L2Attackable spawnedMob = (L2Attackable) addSpawn(MITHRIL_MILLIPEDE, loc, false, 0, false, world.getInstanceId());
						spawnedMob.setScriptValue(1);
						spawnedMob.setIsRunning(true);
						spawnedMob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, npc);
						spawnedMob.addDamageHate(npc, 0, 999999);
					}
				}
				break;
			}
			case "FINISH":
			{
				for (L2Character knownChar : npc.getKnownList().getKnownCharacters())
				{
					if ((knownChar != null) && (knownChar.getId() == KEGOR))
					{
						final L2Npc kegor = (L2Npc) knownChar;
						kegor.setScriptValue(2);
						kegor.setWalking();
						kegor.setTarget(player);
						kegor.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player);
						broadcastNpcSay(kegor, Say2.NPC_ALL, NpcStringId.I_CAN_FINALLY_TAKE_A_BREATHER_BY_THE_WAY_WHO_ARE_YOU_HMM_I_THINK_I_KNOW_WHO_SENT_YOU);
					}
				}
				InstanceManager.getInstance().getInstance(world.getInstanceId()).setDuration(3000);
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		final QuestState qs = player.getQuestState(Q10284_AcquisitionOfDivineSword.class.getSimpleName());
		if ((qs != null))
		{
			if (qs.isMemoState(2))
			{
				return npc.isScriptValue(0) ? "18846.html" : "18846-01.html";
			}
			else if (qs.isMemoState(3))
			{
				final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
				world.removeAllowed(player.getObjectId());
				player.setInstanceId(0);
				player.teleToLocation(EXIT_LOC, 0);
				qs.giveAdena(296425, true);
				qs.addExpAndSp(921805, 82230);
				qs.exitQuest(false, true);
				return "18846-03.html";
			}
		}
		return super.onFirstTalk(npc, player);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final InstanceWorld world = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		final MMWorld _world = ((MMWorld) world);
		
		if (npc.getId() == KEGOR)
		{
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.HOW_COULD_I_FALL_IN_A_PLACE_LIKE_THIS);
			InstanceManager.getInstance().getInstance(world.getInstanceId()).setDuration(1000);
		}
		else
		{
			if (npc.isScriptValue(1))
			{
				_world._count++;
			}
			
			if (_world._count >= 5)
			{
				final QuestState qs = player.getQuestState(Q10284_AcquisitionOfDivineSword.class.getSimpleName());
				if ((qs != null) && qs.isMemoState(2))
				{
					cancelQuestTimer("BUFF", npc, player);
					qs.setMemoState(3);
					qs.setCond(6, true);
					startQuestTimer("FINISH", 3000, npc, player);
				}
			}
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		switch (npc.getId())
		{
			case TARUN:
			case KRUN:
			{
				final QuestState qs = talker.getQuestState(Q10284_AcquisitionOfDivineSword.class.getSimpleName());
				if ((qs != null) && qs.isMemoState(2))
				{
					if (!qs.hasQuestItems(COLD_RESISTANCE_POTION))
					{
						qs.giveItems(COLD_RESISTANCE_POTION, 1);
					}
					qs.setCond(4, true);
					enterInstance(talker, "MithrilMine.xml", START_LOC);
				}
				break;
			}
			case KEGOR:
			{
				final QuestState qs = talker.getQuestState(Q10284_AcquisitionOfDivineSword.class.getSimpleName());
				if ((qs != null) && qs.isMemoState(2) && qs.hasQuestItems(COLD_RESISTANCE_POTION) && npc.isScriptValue(0))
				{
					qs.takeItems(COLD_RESISTANCE_POTION, -1);
					qs.setCond(5, true);
					npc.setScriptValue(1);
					startQuestTimer("TIMER", 3000, npc, talker);
					startQuestTimer("BUFF", 3500, npc, talker);
					return "18846-02.html";
				}
				break;
			}
		}
		return super.onTalk(npc, talker);
	}
	
	protected int enterInstance(L2PcInstance player, String template, Location loc)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof MMWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, loc, world.getInstanceId(), false);
			return 0;
		}
		// New instance
		world = new MMWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((MMWorld) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("Mithril Mine started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, loc, world.getInstanceId(), false);
		world.addAllowed(player.getObjectId());
		return world.getInstanceId();
	}
	
	public static void main(String[] args)
	{
		new MithrilMine();
	}
}