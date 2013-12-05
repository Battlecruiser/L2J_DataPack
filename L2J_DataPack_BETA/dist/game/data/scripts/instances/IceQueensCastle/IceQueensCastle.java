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
package instances.IceQueensCastle;

import quests.Q10285_MeetingSirra.Q10285_MeetingSirra;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Ice Queen's Castle instance zone.
 * @author Adry_85
 */
public final class IceQueensCastle extends Quest
{
	protected class IQCWorld extends InstanceWorld
	{
		long storeTime = 0;
	}
	
	private static final int INSTANCEID = 137;
	// NPCs
	private static final int FREYA = 18847;
	private static final int BATTALION_LEADER = 18848;
	private static final int LEGIONNAIRE = 18849;
	private static final int INVISIBLE_NPC = 18919;
	private static final int MERCENARY_ARCHER = 18926;
	private static final int ARCHERY_KNIGHT = 22767;
	private static final int JINIA = 32781;
	// Locations
	private static final Location START_LOC = new Location(114000, -112357, -11200, 0, 0);
	private static final Location EXIT_LOC = new Location(113883, -108777, -848, 0, 0);
	private static final Location FREYA_LOC = new Location(114730, -114805, -11200, 50, 0);
	// Skill
	private static SkillHolder ETHERNAL_BLIZZARD = new SkillHolder(6276, 1);
	// Door
	private static final int ICE_QUEEN_DOOR = 23140101;
	
	private IceQueensCastle()
	{
		super(-1, IceQueensCastle.class.getSimpleName(), "instances");
		addStartNpc(JINIA);
		addTalkId(JINIA);
		addSeeCreatureId(BATTALION_LEADER, LEGIONNAIRE, MERCENARY_ARCHER);
		addSpawnId(FREYA);
		addSpellFinishedId(FREYA);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "ATTACK_KNIGHT":
			{
				for (L2Character character : npc.getKnownList().getKnownCharacters())
				{
					if ((character.getId() == ARCHERY_KNIGHT) && !character.isDead() && !((L2Attackable) character).isDecayed())
					{
						npc.setIsRunning(true);
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, character);
						((L2Attackable) npc).addDamageHate(character, 0, 999999);
					}
				}
				startQuestTimer("ATTACK_KNIGHT", 3000, npc, null);
				break;
			}
			case "TIMER_MOVING":
			{
				if (npc != null)
				{
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, FREYA_LOC);
				}
				break;
			}
			case "TIMER_BLIZZARD":
			{
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.I_CAN_NO_LONGER_STAND_BY));
				npc.stopMove(null);
				npc.setTarget(player);
				npc.doCast(ETHERNAL_BLIZZARD.getSkill());
				break;
			}
			case "TIMER_SCENE_21":
			{
				if (npc != null)
				{
					player.showQuestMovie(21);
					npc.deleteMe();
					startQuestTimer("TIMER_PC_LEAVE", 24000, npc, player);
				}
				break;
			}
			case "TIMER_PC_LEAVE":
			{
				final QuestState qs = player.getQuestState(Q10285_MeetingSirra.class.getSimpleName());
				if ((qs != null))
				{
					qs.setMemoState(3);
					qs.setCond(10, true);
					final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
					world.removeAllowed(player.getObjectId());
					player.setInstanceId(0);
					player.teleToLocation(EXIT_LOC, 0);
				}
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onSeeCreature(L2Npc npc, L2Character creature, boolean isSummon)
	{
		if (creature.isPlayer() && npc.isScriptValue(0))
		{
			for (L2Character character : npc.getKnownList().getKnownCharacters())
			{
				if ((character.getId() == ARCHERY_KNIGHT) && !character.isDead() && !((L2Attackable) character).isDecayed())
				{
					npc.setIsRunning(true);
					npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, character);
					((L2Attackable) npc).addDamageHate(character, 0, 999999);
					npc.setScriptValue(1);
					startQuestTimer("ATTACK_KNIGHT", 5000, npc, null);
				}
			}
			NpcSay ns = new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getId(), NpcStringId.S1_MAY_THE_PROTECTION_OF_THE_GODS_BE_UPON_YOU);
			ns.addStringParameter(creature.getName());
			npc.broadcastPacket(ns);
		}
		return super.onSeeCreature(npc, creature, isSummon);
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		startQuestTimer("TIMER_MOVING", 60000, npc, null);
		startQuestTimer("TIMER_BLIZZARD", 180000, npc, null);
		return super.onSpawn(npc);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (skill == ETHERNAL_BLIZZARD.getSkill())
		{
			startQuestTimer("TIMER_SCENE_21", 1000, npc, player);
		}
		return super.onSpellFinished(npc, player, skill);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		enterInstance(talker, "IceQueensCastle.xml", START_LOC);
		return super.onTalk(npc, talker);
	}
	
	protected int enterInstance(L2PcInstance player, String template, Location loc)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof IQCWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, loc, world.getInstanceId(), false);
			return 0;
		}
		// New instance
		world = new IQCWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((IQCWorld) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("Ice Queen's Castle started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, loc, world.getInstanceId(), false);
		world.addAllowed(player.getObjectId());
		openDoor(ICE_QUEEN_DOOR, world.getInstanceId());
		addSpawn(INVISIBLE_NPC, 114394, -112383, -11200, 0, false, 0, false, INSTANCEID);
		return world.getInstanceId();
	}
	
	public static void main(String[] args)
	{
		new IceQueensCastle();
	}
}