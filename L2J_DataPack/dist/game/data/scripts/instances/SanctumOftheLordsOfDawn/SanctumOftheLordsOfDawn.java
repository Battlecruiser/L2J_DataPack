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
package instances.SanctumOftheLordsOfDawn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import quests.Q00195_SevenSignsSecretRitualOfThePriests.Q00195_SevenSignsSecretRitualOfThePriests;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Sanctum of the Lords of Dawn instance zone.
 * @author Adry_85
 */
public final class SanctumOftheLordsOfDawn extends AbstractNpcAI
{
	protected static final class HSWorld extends InstanceWorld
	{
		protected long storeTime = 0;
		protected int doorst = 0;
		protected final static Map<Integer, List<L2Npc>> _save_point = new HashMap<>();
		
		public static Map<Integer, List<L2Npc>> getMonsters()
		{
			return _save_point;
		}
	}
	
	// Instance
	private static final int TEMPLATE_ID = 111;
	// NPCs
	private static final int GUARDS_OF_THE_DAWN = 18834;
	private static final int GUARDS_OF_THE_DAWN_2 = 18835;
	private static final int GUARDS_OF_THE_DAWN_3 = 27351;
	private static final int LIGHT_OF_DAWN = 32575;
	private static final int PASSWORD_ENTRY_DEVICE = 32577;
	private static final int IDENTITY_CONFIRM_DEVICE = 32578;
	private static final int DARKNESS_OF_DAWN = 32579;
	private static final int SHELF = 32580;
	// Doors
	private static int DOOR_ONE = 17240001;
	private static int DOOR_TWO = 17240003;
	private static int DOOR_THREE = 17240005;
	// Item
	private static final int IDENTITY_CARD = 13822;
	// Skill
	private static SkillHolder GUARD_SKILL = new SkillHolder(5978, 1);
	// Locations
	private static final Location ENTER = new Location(-76161, 213401, -7120, 0, 0);
	private static final Location EXIT = new Location(-12585, 122305, -2989, 0, 0);
	
	private static final Location[] SAVE_POINT = new Location[]
	{
		new Location(-75775, 213415, -7120),
		new Location(-74959, 209240, -7472),
		new Location(-77699, 208905, -7640),
		new Location(-79939, 205857, -7888),
	};
	
	private SanctumOftheLordsOfDawn()
	{
		super(SanctumOftheLordsOfDawn.class.getSimpleName(), "instances");
		addStartNpc(LIGHT_OF_DAWN);
		addTalkId(LIGHT_OF_DAWN, IDENTITY_CONFIRM_DEVICE, PASSWORD_ENTRY_DEVICE, DARKNESS_OF_DAWN, SHELF);
		addAggroRangeEnterId(GUARDS_OF_THE_DAWN, GUARDS_OF_THE_DAWN_2, GUARDS_OF_THE_DAWN_3);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "spawn":
			{
				InstanceWorld tmpworld = InstanceManager.getInstance().getPlayerWorld(player);
				if (tmpworld instanceof HSWorld)
				{
					HSWorld world = (HSWorld) tmpworld;
					Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
					inst.spawnGroup("high_priest_of_dawn");
					player.sendPacket(SystemMessageId.BY_USING_THE_INVISIBLE_SKILL_SNEAK_INTO_THE_DAWN_S_DOCUMENT_STORAGE);
				}
				break;
			}
			case "teleportPlayer":
			{
				switch (npc.getId())
				{
					case GUARDS_OF_THE_DAWN:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getId(), NpcStringId.INTRUDER_PROTECT_THE_PRIESTS_OF_DAWN));
						break;
					}
					case GUARDS_OF_THE_DAWN_2:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getId(), NpcStringId.HOW_DARE_YOU_INTRUDE_WITH_THAT_TRANSFORMATION_GET_LOST));
						break;
					}
					case GUARDS_OF_THE_DAWN_3:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getId(), NpcStringId.WHO_ARE_YOU_A_NEW_FACE_LIKE_YOU_CAN_T_APPROACH_THIS_PLACE));
						break;
					}
				}
				
				OUTTER:
				for (Entry<Integer, List<L2Npc>> entry : HSWorld._save_point.entrySet())
				{
					for (L2Npc monster : entry.getValue())
					{
						if (monster.getObjectId() == npc.getObjectId())
						{
							player.teleToLocation(SAVE_POINT[entry.getKey()]);
							break OUTTER;
						}
					}
				}
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	private void enterInstance(L2PcInstance player, String template, Location loc)
	{
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		if (world != null)
		{
			if (!(world instanceof HSWorld))
			{
				player.sendPacket(SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON);
			}
			else
			{
				teleportPlayer(player, loc, world.getInstanceId());
			}
		}
		else
		{
			// New instance,
			world = new HSWorld();
			world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
			world.setTemplateId(TEMPLATE_ID);
			world.setStatus(0);
			((HSWorld) world).storeTime = System.currentTimeMillis();
			InstanceManager.getInstance().addWorld(world);
			_log.info("Sanctum of the Lords of Dawn started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
			// Teleport players.
			teleportPlayer(player, loc, world.getInstanceId());
			world.addAllowed(player.getObjectId());
			final Map<Integer, List<L2Npc>> save_point = HSWorld.getMonsters();
			final Instance inst = InstanceManager.getInstance().getInstance(world.getInstanceId());
			save_point.put(0, inst.spawnGroup("save_point1"));
			save_point.put(1, inst.spawnGroup("save_point2"));
			save_point.put(2, inst.spawnGroup("save_point3"));
			save_point.put(3, inst.spawnGroup("save_point4"));
		}
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		switch (npc.getId())
		{
			case LIGHT_OF_DAWN:
			{
				final QuestState qs = talker.getQuestState(Q00195_SevenSignsSecretRitualOfThePriests.class.getSimpleName());
				if ((qs != null) && qs.isCond(3) && hasQuestItems(talker, IDENTITY_CARD) && (talker.getTransformationId() == 113))
				{
					enterInstance(talker, "SanctumoftheLordsofDawn.xml", ENTER);
					return "32575-01.html";
				}
				return "32575-02.html";
			}
			case IDENTITY_CONFIRM_DEVICE:
			{
				InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
				if (tmpworld instanceof HSWorld)
				{
					if (hasQuestItems(talker, IDENTITY_CARD) && (talker.getTransformationId() == 113))
					{
						HSWorld world = (HSWorld) tmpworld;
						if (world.doorst == 0)
						{
							openDoor(DOOR_ONE, world.getInstanceId());
							talker.sendPacket(SystemMessageId.BY_USING_THE_INVISIBLE_SKILL_SNEAK_INTO_THE_DAWN_S_DOCUMENT_STORAGE);
							talker.sendPacket(SystemMessageId.MALE_GUARDS_CAN_DETECT_THE_CONCEALMENT_BUT_THE_FEMALE_GUARDS_CANNOT);
							talker.sendPacket(SystemMessageId.FEMALE_GUARDS_NOTICE_THE_DISGUISES_FROM_FAR_AWAY_BETTER_THAN_THE_MALE_GUARDS_DO_SO_BEWARE);
							world.doorst++;
							npc.decayMe();
						}
						else if (world.doorst == 1)
						{
							openDoor(DOOR_TWO, world.getInstanceId());
							world.doorst++;
							npc.decayMe();
							for (int objId : world.getAllowed())
							{
								L2PcInstance pl = L2World.getInstance().getPlayer(objId);
								if (pl != null)
								{
									pl.showQuestMovie(11);
									startQuestTimer("spawn", 35000, null, talker);
								}
							}
						}
						return "32578-01.html";
					}
					return "32578-02.html";
				}
				break;
			}
			case PASSWORD_ENTRY_DEVICE:
			{
				InstanceWorld tmworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
				if (tmworld instanceof HSWorld)
				{
					HSWorld world = (HSWorld) tmworld;
					openDoor(DOOR_THREE, world.getInstanceId());
					return "32577-01.html";
				}
				break;
			}
			case DARKNESS_OF_DAWN:
			{
				final InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(talker);
				world.removeAllowed(talker.getObjectId());
				talker.teleToLocation(EXIT, 0);
				return "32579-01.html";
			}
			case SHELF:
			{
				InstanceWorld world = InstanceManager.getInstance().getWorld(npc.getInstanceId());
				InstanceManager.getInstance().getInstance(world.getInstanceId()).setDuration(300000);
				talker.teleToLocation(-75925, 213399, -7128);
				return "32580-01.html";
			}
		}
		return "";
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		npc.broadcastPacket(new MagicSkillUse(npc, player, GUARD_SKILL.getSkillId(), 1, 2000, 1));
		startQuestTimer("teleportPlayer", 2000, npc, player);
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	public static void main(String[] args)
	{
		new SanctumOftheLordsOfDawn();
	}
}
