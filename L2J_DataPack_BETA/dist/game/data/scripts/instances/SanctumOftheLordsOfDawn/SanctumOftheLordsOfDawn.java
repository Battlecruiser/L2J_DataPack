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
package instances.SanctumOftheLordsOfDawn;

import quests.Q00195_SevenSignsSecretRitualOfThePriests.Q00195_SevenSignsSecretRitualOfThePriests;

import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;

/**
 * Sanctum of the Lords of Dawn instance zone.
 * @author Adry_85
 */
public final class SanctumOftheLordsOfDawn extends Quest
{
	protected class HSWorld extends InstanceWorld
	{
		protected long storeTime = 0;
		protected int doorst = 0;
	}
	
	// Instance
	private static final int INSTANCEID = 111;
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
	
	private SanctumOftheLordsOfDawn()
	{
		super(-1, SanctumOftheLordsOfDawn.class.getSimpleName(), "instances");
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
					player.sendPacket(SystemMessageId.SNEAK_INTO_DAWNS_DOCUMENT_STORAGE);
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
						player.teleToLocation(-75987, 213470, -7123);
						break;
					}
					case GUARDS_OF_THE_DAWN_2:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getId(), NpcStringId.HOW_DARE_YOU_INTRUDE_WITH_THAT_TRANSFORMATION_GET_LOST));
						player.teleToLocation(-75987, 213470, -7123);
						break;
					}
					case GUARDS_OF_THE_DAWN_3:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getId(), NpcStringId.WHO_ARE_YOU_A_NEW_FACE_LIKE_YOU_CANT_APPROACH_THIS_PLACE));
						player.teleToLocation(-75987, 213470, -7123);
						break;
					}
				}
				break;
			}
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	protected int enterInstance(L2PcInstance player, String template, Location loc)
	{
		// check for existing instances for this player
		InstanceWorld world = InstanceManager.getInstance().getPlayerWorld(player);
		// existing instance
		if (world != null)
		{
			if (!(world instanceof HSWorld))
			{
				player.sendPacket(SystemMessageId.ALREADY_ENTERED_ANOTHER_INSTANCE_CANT_ENTER);
				return 0;
			}
			teleportPlayer(player, loc, world.getInstanceId());
			return world.getInstanceId();
		}
		// New instance
		world = new HSWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((HSWorld) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		_log.info("SevenSign started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		teleportPlayer(player, loc, world.getInstanceId());
		world.addAllowed(player.getObjectId());
		return world.getInstanceId();
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
							talker.sendPacket(SystemMessageId.SNEAK_INTO_DAWNS_DOCUMENT_STORAGE);
							talker.sendPacket(SystemMessageId.MALE_GUARDS_CAN_DETECT_FEMALES_DONT);
							talker.sendPacket(SystemMessageId.FEMALE_GUARDS_NOTICE_BETTER_THAN_MALE);
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
