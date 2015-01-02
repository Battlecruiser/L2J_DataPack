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
package instances.IceQueensCastle;

import quests.Q10285_MeetingSirra.Q10285_MeetingSirra;
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
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.clientpackets.Say2;

/**
 * Ice Queen's Castle instance zone.
 * @author Adry_85
 */
public final class IceQueensCastle extends AbstractNpcAI
{
	protected class IQCWorld extends InstanceWorld
	{
		L2PcInstance player = null;
	}
	
	// NPCs
	private static final int FREYA = 18847;
	private static final int BATTALION_LEADER = 18848;
	private static final int LEGIONNAIRE = 18849;
	private static final int MERCENARY_ARCHER = 18926;
	private static final int ARCHERY_KNIGHT = 22767;
	private static final int JINIA = 32781;
	// Locations
	private static final Location START_LOC = new Location(114000, -112357, -11200, 0, 0);
	private static final Location EXIT_LOC = new Location(113883, -108777, -848, 0, 0);
	private static final Location FREYA_LOC = new Location(114730, -114805, -11200, 50, 0);
	// Skill
	private static SkillHolder ETHERNAL_BLIZZARD = new SkillHolder(6276, 1);
	// Misc
	private static final int TEMPLATE_ID = 137;
	private static final int ICE_QUEEN_DOOR = 23140101;
	private static final int MIN_LV = 82;
	
	private IceQueensCastle()
	{
		super(IceQueensCastle.class.getSimpleName(), "instances");
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
				broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.I_CAN_NO_LONGER_STAND_BY);
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
			broadcastNpcSay(npc, Say2.NPC_ALL, NpcStringId.S1_MAY_THE_PROTECTION_OF_THE_GODS_BE_UPON_YOU, creature.getName());
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
	public String onSpellFinished(L2Npc npc, L2PcInstance player, Skill skill)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if ((tmpworld != null) && (tmpworld instanceof IQCWorld))
		{
			final IQCWorld world = (IQCWorld) tmpworld;
			
			if ((skill == ETHERNAL_BLIZZARD.getSkill()) && (world.player != null))
			{
				startQuestTimer("TIMER_SCENE_21", 1000, npc, world.player);
			}
		}
		return super.onSpellFinished(npc, player, skill);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		enterInstance(talker, "IceQueensCastle.xml");
		return super.onTalk(npc, talker);
	}
	
	private void enterInstance(L2PcInstance player, String template)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof IQCWorld)
			{
				teleportPlayer(player, START_LOC, world.getInstanceId(), false);
				return;
			}
			player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			return;
		}
		
		if (checkConditions(player))
		{
			world = new IQCWorld();
			world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
			world.setTemplateId(TEMPLATE_ID);
			world.setStatus(0);
			InstanceManager.getInstance().addWorld(world);
			_log.info("Ice Queen's Castle started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
			teleportPlayer(player, START_LOC, world.getInstanceId(), false);
			world.addAllowed(player.getObjectId());
			((IQCWorld) world).player = player;
			openDoor(ICE_QUEEN_DOOR, world.getInstanceId());
		}
	}
	
	private boolean checkConditions(L2PcInstance player)
	{
		if (player.getLevel() < MIN_LV)
		{
			player.sendPacket(SystemMessageId.C1_S_LEVEL_DOES_NOT_CORRESPOND_TO_THE_REQUIREMENTS_FOR_ENTRY);
			return false;
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		new IceQueensCastle();
	}
}
