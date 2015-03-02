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
package instances.PailakaDevilsLegacy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import quests.Q00129_PailakaDevilsLegacy.Q00129_PailakaDevilsLegacy;
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
import com.l2jserver.gameserver.model.zone.L2ZoneType;
import com.l2jserver.gameserver.network.SystemMessageId;

/**
 * Pailaka Devil's Legacy Instance zone.
 * @author St3eT
 */
public final class PailakaDevilsLegacy extends AbstractNpcAI
{
	protected class DIWorld extends InstanceWorld
	{
		L2Attackable _lematanNpc = null;
		List<L2Attackable> _followerslist = new CopyOnWriteArrayList<>();
	}
	
	// NPCs
	private static final int LEMATAN = 18633; // Lematan
	private static final int SURVIVOR = 32498; // Devil's Isle Survivor
	private static final int FOLLOWERS = 18634; // Lematan's Follower
	private static final int POWDER_KEG = 18622; // Powder Keg
	private static final int TREASURE_BOX = 32495; // Treasure Chest
	private static final int ADVENTURER2 = 32511; // Dwarf Adventurer
	// Items
	private static final int ANTIDOTE_POTION = 13048; // Pailaka Antidote
	private static final int DIVINE_POTION = 13049; // Divine Soul
	private static final int PAILAKA_KEY = 13150; // Pailaka All-Purpose Key
	private static final int SHIELD = 13032; // Pailaka Instant Shield
	private static final int DEFENCE_POTION = 13059; // Long-Range Defense Increasing Potion
	private static final int HEALING_POTION = 13033; // Quick Healing Potion
	// Skills
	private static final SkillHolder ENERGY = new SkillHolder(5712, 1); // Energy Ditch
	private static final SkillHolder BOOM = new SkillHolder(5714, 1); // Boom Up
	private static final SkillHolder AV_TELEPORT = new SkillHolder(4671, 1); // AV - Teleport
	// Locations
	private static final Location TELEPORT = new Location(76427, -219045, -3780);
	private static final Location LEMATAN_SPAWN = new Location(88108, -209252, -3744, 6425);
	private static final Location LEMATAN_PORT_POINT = new Location(86116, -209117, -3774);
	private static final Location LEMATAN_PORT = new Location(85000, -208699, -3336);
	private static final Location ADVENTURER_LOC = new Location(84983, -208736, -3336, 49915);
	private static final Location[] FOLLOWERS_LOC =
	{
		new Location(85067, -208943, -3336, 20106),
		new Location(84904, -208944, -3336, 10904),
		new Location(85062, -208538, -3336, 44884),
		new Location(84897, -208542, -3336, 52973),
		new Location(84808, -208633, -3339, 65039),
		new Location(84808, -208856, -3339, 0),
		new Location(85144, -208855, -3341, 33380),
		new Location(85139, -208630, -3339, 31777),
	};
	// Misc
	private static final int TEMPLATE_ID = 44;
	private static final int ZONE = 20109;
	
	private PailakaDevilsLegacy()
	{
		super(PailakaDevilsLegacy.class.getSimpleName(), "instances");
		addTalkId(SURVIVOR);
		addAttackId(POWDER_KEG, TREASURE_BOX, LEMATAN);
		addKillId(LEMATAN);
		addSpawnId(FOLLOWERS);
		addEnterZoneId(ZONE);
		addMoveFinishedId(LEMATAN);
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		String htmltext = null;
		
		if (event.equals("enter"))
		{
			final QuestState qs = player.getQuestState(Q00129_PailakaDevilsLegacy.class.getSimpleName());
			enterInstance(player, "PailakaDevilsLegacy.xml");
			if (qs.isCond(1))
			{
				qs.setCond(2, true);
				htmltext = "32498-01.htm";
			}
			else
			{
				htmltext = "32498-02.htm";
			}
		}
		else if ((tmpworld != null) && (tmpworld instanceof DIWorld))
		{
			final DIWorld world = (DIWorld) tmpworld;
			
			switch (event)
			{
				case "FOLLOWER_CAST":
				{
					if ((world._lematanNpc != null) && !world._lematanNpc.isDead())
					{
						for (L2Attackable follower : world._followerslist)
						{
							follower.setTarget(world._lematanNpc);
							follower.doCast(ENERGY.getSkill());
						}
						startQuestTimer("FOLLOWER_CAST", 15000, world._lematanNpc, null);
					}
					break;
				}
				case "LEMATAN_TELEPORT":
				{
					((L2Attackable) npc).clearAggroList();
					npc.disableCoreAI(false);
					npc.teleToLocation(LEMATAN_PORT);
					npc.getVariables().set("ON_SHIP", 1);
					npc.getSpawn().setLocation(LEMATAN_PORT);
					for (Location loc : FOLLOWERS_LOC)
					{
						final L2Attackable follower = (L2Attackable) addSpawn(FOLLOWERS, loc, false, 0, false, world.getInstanceId());
						follower.disableCoreAI(true);
						follower.setIsImmobilized(true);
						world._followerslist.add(follower);
					}
					startQuestTimer("FOLLOWER_CAST", 4000, world._lematanNpc, null);
					break;
				}
				case "TELEPORT":
				{
					player.teleToLocation(TELEPORT);
					break;
				}
				case "DELETE":
				{
					npc.deleteMe();
					break;
				}
			}
		}
		return htmltext;
	}
	
	@Override
	public final String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if ((tmpworld != null) && (tmpworld instanceof DIWorld))
		{
			switch (npc.getId())
			{
				case POWDER_KEG:
				{
					if ((damage > 0) && npc.isScriptValue(0))
					{
						for (L2Character characters : npc.getKnownList().getKnownCharactersInRadius(600))
						{
							if ((characters != null) && characters.isMonster())
							{
								final L2Attackable monster = (L2Attackable) characters;
								monster.addDamageHate(npc, 0, 999);
								monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, npc);
								monster.reduceCurrentHp(500 + getRandom(0, 200), npc, BOOM.getSkill());
							}
						}
						npc.doCast(BOOM.getSkill());
						npc.setScriptValue(1);
						startQuestTimer("DELETE", 2000, npc, null);
					}
					break;
				}
				case LEMATAN:
				{
					if (npc.isScriptValue(0) && (npc.getCurrentHp() < (npc.getMaxHp() * 0.5)))
					{
						npc.disableCoreAI(true);
						npc.setScriptValue(1);
						npc.setIsRunning(true);
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, LEMATAN_PORT_POINT);
					}
					break;
				}
				case TREASURE_BOX:
				{
					if (npc.isScriptValue(0))
					{
						switch (getRandom(7))
						{
							case 0:
							case 1:
							{
								npc.dropItem(attacker, ANTIDOTE_POTION, getRandom(1, 10));
								break;
							}
							case 2:
							{
								npc.dropItem(attacker, DIVINE_POTION, getRandom(1, 5));
								break;
							}
							case 3:
							{
								npc.dropItem(attacker, PAILAKA_KEY, getRandom(1, 2));
								break;
							}
							case 4:
							{
								npc.dropItem(attacker, DEFENCE_POTION, getRandom(1, 7));
								break;
							}
							case 5:
							{
								npc.dropItem(attacker, SHIELD, getRandom(1, 10));
								break;
							}
							case 6:
							{
								npc.dropItem(attacker, HEALING_POTION, getRandom(1, 10));
								break;
							}
						}
						npc.setScriptValue(1);
						startQuestTimer("DELETE", 3000, npc, attacker);
					}
					break;
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
		
		if ((tmpworld != null) && (tmpworld instanceof DIWorld))
		{
			final DIWorld world = (DIWorld) tmpworld;
			
			if (world._followerslist != null)
			{
				for (L2Npc _follower : world._followerslist)
				{
					_follower.deleteMe();
				}
				world._followerslist.clear();
			}
			addSpawn(ADVENTURER2, ADVENTURER_LOC, false, 0, false, npc.getInstanceId());
		}
		return super.onKill(npc, player, isSummon);
	}
	
	@Override
	public String onEnterZone(L2Character character, L2ZoneType zone)
	{
		if ((character.isPlayer()) && !character.isDead() && !character.isTeleporting() && ((L2PcInstance) character).isOnline())
		{
			final InstanceWorld world = InstanceManager.getInstance().getWorld(character.getInstanceId());
			if ((world != null) && (world.getTemplateId() == TEMPLATE_ID))
			{
				startQuestTimer("TELEPORT", 1000, ((DIWorld) world)._lematanNpc, (L2PcInstance) character);
			}
		}
		return super.onEnterZone(character, zone);
	}
	
	@Override
	public void onMoveFinished(L2Npc npc)
	{
		if (npc.getLocation() == LEMATAN_PORT_POINT)
		{
			npc.doCast(AV_TELEPORT.getSkill());
			startQuestTimer("LEMATAN_TELEPORT", 2000, npc, null);
		}
	}
	
	private void enterInstance(L2PcInstance player, String template)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		
		if (world != null)
		{
			if (world instanceof DIWorld)
			{
				teleportPlayer(player, TELEPORT, world.getInstanceId());
				return;
			}
			player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			return;
		}
		world = new DIWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(TEMPLATE_ID);
		InstanceManager.getInstance().addWorld(world);
		world.addAllowed(player.getObjectId());
		teleportPlayer(player, TELEPORT, world.getInstanceId());
		((DIWorld) world)._lematanNpc = (L2Attackable) addSpawn(LEMATAN, LEMATAN_SPAWN, false, 0, false, world.getInstanceId());
		_log.info("Pailaka Devils Legacy" + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
	}
	
	public static void main(String[] args)
	{
		new PailakaDevilsLegacy();
	}
}
