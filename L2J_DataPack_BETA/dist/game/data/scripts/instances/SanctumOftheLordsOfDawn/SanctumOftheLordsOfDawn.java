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
package instances.SanctumOftheLordsOfDawn;

import quests.Q00195_SevenSignsSecretRitualOfThePriests.Q00195_SevenSignsSecretRitualOfThePriests;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2CharPosition;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2DoorInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
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
public class SanctumOftheLordsOfDawn extends Quest
{
	protected class HSWorld extends InstanceWorld
	{
		protected long storeTime = 0;
		protected int doorst = 0;
		protected L2Npc w_npc_1, w_npc_2, w_npc_3, w_npc_4, w_npc_5, w_npc_6, w_npc_7, w_npc_8, w_npc_9, w_npc_10, w_npc_11, w_npc_12, w_npc_13, w_npc_14, w_npc_15, w_npc_16, w_npc_17, w_npc_18, w_npc_19, w_npc_20, w_npc_21, w_npc_22, w_npc_23, w_npc_24, w_npc_25, w_npc_26, w_npc_27, w_npc_28,
			w_npc_29, w_npc_30, w_npc_31, w_npc_32, w_npc_33, w_npc_34, w_npc_35, w_npc_36, w_npc_37, w_npc_38, w_npc_39, w_npc_40, w_npc_41, w_npc_42, w_npc_43, w_npc_44, w_npc_45, w_npc_46, w_npc_47, w_npc_48, w_npc_49, w_npc_50, w_npc_51, w_npc_52, w_npc_53, w_npc_54, w_npc_55, w_npc_56,
			w_npc_57, w_npc_58, w_npc_59, w_npc_60;
	}
	
	// Instance
	private static final int INSTANCEID = 111;
	// NPCs
	private static final int HIGH_PRIEST_OF_DAWN = 18828;
	private static final int GUARDS_OF_THE_DAWN = 18834;
	private static final int GUARDS_OF_THE_DAWN_2 = 18835;
	private static final int GUARDS_OF_THE_DAWN_3 = 27351;
	private static final int LIGHT_OF_DAWN = 32575;
	private static final int IDENTITY_CONFIRM_DEVICE = 32578;
	private static final int PASSWORD_ENTRY_DEVICE = 32577;
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
	// Routes
	private static final L2CharPosition iz111_zone01_01 = new L2CharPosition(-75373, 212107, -7312, 0);
	private static final L2CharPosition iz111_zone01_01_r = new L2CharPosition(-75650, 212107, -7312, 0);
	private static final L2CharPosition iz111_zone01_02 = new L2CharPosition(-75046, 212107, -7312, 0);
	private static final L2CharPosition iz111_zone01_02_r = new L2CharPosition(-74854, 212107, -7312, 0);
	private static final L2CharPosition iz111_zone01_03 = new L2CharPosition(-74255, 212108, -7312, 0);
	private static final L2CharPosition iz111_zone01_03_r = new L2CharPosition(-74532, 212108, -7312, 0);
	private static final L2CharPosition iz111_zone01_04 = new L2CharPosition(-75200, 211465, -7312, 0);
	private static final L2CharPosition iz111_zone01_04_r = new L2CharPosition(-75200, 211178, -7312, 0);
	private static final L2CharPosition iz111_zone01_05 = new L2CharPosition(-74701, 211172, -7312, 0);
	private static final L2CharPosition iz111_zone01_05_r = new L2CharPosition(-74701, 211460, -7312, 0);
	private static final L2CharPosition iz111_zone02_01 = new L2CharPosition(-74750, 210174, -7408, 0);
	private static final L2CharPosition iz111_zone02_01_r = new L2CharPosition(-75190, 210175, -7408, 0);
	private static final L2CharPosition iz111_zone02_02 = new L2CharPosition(-75168, 209820, -7408, 0);
	private static final L2CharPosition iz111_zone02_02_r = new L2CharPosition(-74743, 209820, -7408, 0);
	private static final L2CharPosition iz111_zone03_01 = new L2CharPosition(-75559, 208813, -7504, 0);
	private static final L2CharPosition iz111_zone03_01_r = new L2CharPosition(-75559, 207860, -7504, 0);
	private static final L2CharPosition iz111_zone03_02 = new L2CharPosition(-74508, 208290, -7504, 0);
	private static final L2CharPosition iz111_zone03_02_r = new L2CharPosition(-74227, 208290, -7504, 0);
	private static final L2CharPosition iz111_zone03_03 = new L2CharPosition(-74206, 207064, -7504, 0);
	private static final L2CharPosition iz111_zone03_03_r = new L2CharPosition(-74522, 207063, -7504, 0);
	private static final L2CharPosition iz111_zone03_04 = new L2CharPosition(-74956, 206686, -7504, 0);
	private static final L2CharPosition iz111_zone03_04_r = new L2CharPosition(-74956, 206348, -7504, 0);
	private static final L2CharPosition iz111_zone03_05 = new L2CharPosition(-75693, 206939, -7504, 0);
	private static final L2CharPosition iz111_zone03_05_r = new L2CharPosition(-75402, 206939, -7504, 0);
	private static final L2CharPosition iz111_zone03_06 = new L2CharPosition(-75668, 206515, -7504, 0);
	private static final L2CharPosition iz111_zone03_06_r = new L2CharPosition(-74246, 206515, -7504, 0);
	private static final L2CharPosition iz111_zone04_01 = new L2CharPosition(-76628, 207852, -7600, 0);
	private static final L2CharPosition iz111_zone04_01_r = new L2CharPosition(-76378, 207852, -7600, 0);
	private static final L2CharPosition iz111_zone04_02 = new L2CharPosition(-76367, 208151, -7600, 0);
	private static final L2CharPosition iz111_zone04_02_r = new L2CharPosition(-76628, 208151, -7600, 0);
	private static final L2CharPosition iz111_zone04_03 = new L2CharPosition(-76632, 208848, -7600, 0);
	private static final L2CharPosition iz111_zone04_03_r = new L2CharPosition(-76374, 208848, -7600, 0);
	private static final L2CharPosition iz111_zone04_04 = new L2CharPosition(-76928, 209446, -7600, 0);
	private static final L2CharPosition iz111_zone04_04_r = new L2CharPosition(-76928, 209189, -7600, 0);
	private static final L2CharPosition iz111_zone04_05 = new L2CharPosition(-77183, 209188, -7600, 0);
	private static final L2CharPosition iz111_zone04_05_r = new L2CharPosition(-77183, 209443, -7600, 0);
	private static final L2CharPosition iz111_zone05_01 = new L2CharPosition(-77361, 208464, -7696, 0);
	private static final L2CharPosition iz111_zone05_01_r = new L2CharPosition(-78054, 208464, -7696, 0);
	private static final L2CharPosition iz111_zone05_02 = new L2CharPosition(-78065, 208036, -7696, 0);
	private static final L2CharPosition iz111_zone05_02_r = new L2CharPosition(-78521, 208035, -7696, 0);
	private static final L2CharPosition iz111_zone05_03 = new L2CharPosition(-77340, 208036, -7696, 0);
	private static final L2CharPosition iz111_zone05_03_r = new L2CharPosition(-76881, 208037, -7696, 0);
	private static final L2CharPosition iz111_zone05_04 = new L2CharPosition(-77060, 207793, -7696, 0);
	private static final L2CharPosition iz111_zone05_04_r = new L2CharPosition(-78335, 207793, -7696, 0);
	private static final L2CharPosition iz111_zone05_05 = new L2CharPosition(-77702, 208184, -7696, 0);
	private static final L2CharPosition iz111_zone05_05_r = new L2CharPosition(-77702, 207414, -7696, 0);
	private static final L2CharPosition iz111_zone05_06 = new L2CharPosition(-77013, 207105, -7696, 0);
	private static final L2CharPosition iz111_zone05_06_r = new L2CharPosition(-77336, 207428, -7696, 0);
	private static final L2CharPosition iz111_zone06_01 = new L2CharPosition(-78710, 206306, -7872, 0);
	private static final L2CharPosition iz111_zone06_01_r = new L2CharPosition(-78930, 206101, -7888, 0);
	private static final L2CharPosition iz111_zone06_02 = new L2CharPosition(-79066, 206237, -7888, 0);
	private static final L2CharPosition iz111_zone06_02_r = new L2CharPosition(-78855, 206443, -7888, 0);
	private static final L2CharPosition iz111_zone06_03 = new L2CharPosition(-79357, 206330, -7888, 0);
	private static final L2CharPosition iz111_zone06_03_r = new L2CharPosition(-79357, 206713, -7888, 0);
	private static final L2CharPosition iz111_zone06_04 = new L2CharPosition(-79858, 206454, -7872, 0);
	private static final L2CharPosition iz111_zone06_04_r = new L2CharPosition(-79656, 206246, -7888, 0);
	private static final L2CharPosition iz111_zone06_05 = new L2CharPosition(-79787, 206104, -7888, 0);
	private static final L2CharPosition iz111_zone06_05_r = new L2CharPosition(-79999, 206302, -7888, 0);
	private static final L2CharPosition iz111_zone06_06 = new L2CharPosition(-79991, 205392, -7888, 0);
	private static final L2CharPosition iz111_zone06_06_r = new L2CharPosition(-79781, 205602, -7888, 0);
	private static final L2CharPosition iz111_zone06_07 = new L2CharPosition(-79641, 205466, -7888, 0);
	private static final L2CharPosition iz111_zone06_07_r = new L2CharPosition(-79849, 205260, -7888, 0);
	private static final L2CharPosition iz111_zone06_08 = new L2CharPosition(-79364, 204964, -7888, 0);
	private static final L2CharPosition iz111_zone06_08_r = new L2CharPosition(-79363, 205379, -7888, 0);
	private static final L2CharPosition iz111_zone06_09 = new L2CharPosition(-79085, 205454, -7888, 0);
	private static final L2CharPosition iz111_zone06_09_r = new L2CharPosition(-78870, 205253, -7888, 0);
	private static final L2CharPosition iz111_zone06_10 = new L2CharPosition(-78731, 205386, -7872, 0);
	private static final L2CharPosition iz111_zone06_10_r = new L2CharPosition(-78942, 205599, -7888, 0);
	private static final L2CharPosition iz111_zone07_01 = new L2CharPosition(-81125, 205855, -7984, 0);
	private static final L2CharPosition iz111_zone07_01_r = new L2CharPosition(-81938, 205856, -7984, 0);
	// Locations
	private static final Location ENTER = new Location(-76161, 213401, -7120, 0, 0);
	private static final Location EXIT = new Location(-12585, 122305, -2989, 0, 0);
	
	public SanctumOftheLordsOfDawn(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(LIGHT_OF_DAWN);
		addTalkId(LIGHT_OF_DAWN, IDENTITY_CONFIRM_DEVICE, PASSWORD_ENTRY_DEVICE, DARKNESS_OF_DAWN, SHELF);
		addAggroRangeEnterId(GUARDS_OF_THE_DAWN, GUARDS_OF_THE_DAWN_2, GUARDS_OF_THE_DAWN_3);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		switch (event)
		{
			case "Part1":
			{
				InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
				if (tmpworld instanceof HSWorld)
				{
					HSWorld world = (HSWorld) tmpworld;
					world.w_npc_1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone07_01_r);
					world.w_npc_6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_01_r);
					world.w_npc_7.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_02_r);
					world.w_npc_8.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_03_r);
					world.w_npc_9.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_04_r);
					world.w_npc_10.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_05_r);
					world.w_npc_11.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone02_01_r);
					world.w_npc_12.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone02_02_r);
					world.w_npc_16.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_01_r);
					world.w_npc_17.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_02_r);
					world.w_npc_18.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_03_r);
					world.w_npc_19.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_04_r);
					world.w_npc_20.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_06_r);
					world.w_npc_23.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_05_r);
					world.w_npc_27.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_01_r);
					world.w_npc_28.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_02_r);
					world.w_npc_29.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_03_r);
					world.w_npc_30.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_04_r);
					world.w_npc_31.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_05_r);
					world.w_npc_32.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_02_r);
					world.w_npc_35.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_03_r);
					world.w_npc_39.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_01_r);
					world.w_npc_40.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_04_r);
					world.w_npc_41.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_05_r);
					world.w_npc_45.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_06_r);
					world.w_npc_47.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_01_r);
					world.w_npc_48.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_02_r);
					world.w_npc_49.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_03_r);
					world.w_npc_50.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_04_r);
					world.w_npc_51.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_05_r);
					world.w_npc_52.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_06_r);
					world.w_npc_53.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_07_r);
					world.w_npc_54.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_08_r);
					world.w_npc_55.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_09_r);
					world.w_npc_56.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_10_r);
					startQuestTimer("Part2", 3000, world.w_npc_7, null);
				}
				break;
			}
			case "Part2":
			{
				InstanceWorld tmpworld = InstanceManager.getInstance().getWorld(npc.getInstanceId());
				if (tmpworld instanceof HSWorld)
				{
					HSWorld world = (HSWorld) tmpworld;
					world.w_npc_1.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone07_01);
					world.w_npc_6.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_01);
					world.w_npc_7.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_02);
					world.w_npc_8.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_03);
					world.w_npc_9.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_04);
					world.w_npc_10.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone01_05);
					world.w_npc_11.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone02_01);
					world.w_npc_12.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone02_02);
					world.w_npc_16.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_01);
					world.w_npc_17.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_02);
					world.w_npc_18.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_03);
					world.w_npc_19.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_04);
					world.w_npc_20.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_06);
					world.w_npc_23.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone03_05);
					world.w_npc_27.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_01);
					world.w_npc_28.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_02);
					world.w_npc_29.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_03);
					world.w_npc_30.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_04);
					world.w_npc_31.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone04_05);
					world.w_npc_32.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_02);
					world.w_npc_35.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_03);
					world.w_npc_39.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_01);
					world.w_npc_40.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_04);
					world.w_npc_41.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_05);
					world.w_npc_45.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone05_06);
					world.w_npc_47.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_01);
					world.w_npc_48.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_02);
					world.w_npc_49.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_03);
					world.w_npc_50.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_04);
					world.w_npc_51.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_05);
					world.w_npc_52.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_06);
					world.w_npc_53.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_07);
					world.w_npc_54.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_08);
					world.w_npc_55.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_09);
					world.w_npc_56.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, iz111_zone06_10);
					startQuestTimer("Part1", 3000, world.w_npc_7, null);
				}
				break;
			}
			case "Part3":
			{
				player.teleToLocation(-78383, 205845, -7889);
				player.sendPacket(SystemMessageId.SNEAK_INTO_DAWNS_DOCUMENT_STORAGE);
				break;
			}
			case "teleportPlayer":
			{
				switch (npc.getNpcId())
				{
					case GUARDS_OF_THE_DAWN:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), NpcStringId.INTRUDER_PROTECT_THE_PRIESTS_OF_DAWN));
						player.teleToLocation(-75987, 213470, -7123);
						break;
					}
					case GUARDS_OF_THE_DAWN_2:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), NpcStringId.HOW_DARE_YOU_INTRUDE_WITH_THAT_TRANSFORMATION_GET_LOST));
						player.teleToLocation(-75987, 213470, -7123);
						break;
					}
					case GUARDS_OF_THE_DAWN_3:
					{
						npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), NpcStringId.WHO_ARE_YOU_A_NEW_FACE_LIKE_YOU_CANT_APPROACH_THIS_PLACE));
						player.teleToLocation(-75987, 213470, -7123);
						break;
					}
				}
				break;
			}
		}
		return "";
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
			loc.setInstanceId(world.getInstanceId());
			teleportPlayer(player, loc);
			return world.getInstanceId();
		}
		// New instance
		world = new HSWorld();
		world.setInstanceId(InstanceManager.getInstance().createDynamicInstance(template));
		world.setTemplateId(INSTANCEID);
		world.setStatus(0);
		((HSWorld) world).storeTime = System.currentTimeMillis();
		InstanceManager.getInstance().addWorld(world);
		spawnState((HSWorld) world);
		_log.info("SevenSign started " + template + " Instance: " + world.getInstanceId() + " created by player: " + player.getName());
		// teleport players
		loc.setInstanceId(world.getInstanceId());
		teleportPlayer(player, loc);
		world.addAllowed(player.getObjectId());
		return world.getInstanceId();
	}
	
	protected void spawnState(HSWorld world)
	{
		// Static Npcs
		L2Npc s_npc_1 = addSpawn(DARKNESS_OF_DAWN, -75988, 213411, -7124, 0, false, 0, false, world.getInstanceId());
		s_npc_1.setIsNoRndWalk(true);
		L2Npc s_npc_2 = addSpawn(IDENTITY_CONFIRM_DEVICE, -75695, 213537, -7128, 0, false, 0, false, world.getInstanceId());
		s_npc_2.setIsNoRndWalk(true);
		L2Npc s_npc_3 = addSpawn(PASSWORD_ENTRY_DEVICE, -80152, 205740, -7904, 0, false, 0, false, world.getInstanceId());
		s_npc_3.setIsNoRndWalk(true);
		L2Npc s_npc_4 = addSpawn(IDENTITY_CONFIRM_DEVICE, -78289, 205749, -7884, 0, false, 0, false, world.getInstanceId());
		s_npc_4.setIsNoRndWalk(true);
		L2Npc s_npc_5 = addSpawn(SHELF, -81393, 205565, -7960, 0, false, 0, false, world.getInstanceId());
		s_npc_5.setIsNoRndWalk(true);
		addSpawn(HIGH_PRIEST_OF_DAWN, -79229, 205782, -7896, 28672, false, 0, false, world.getInstanceId());
		addSpawn(HIGH_PRIEST_OF_DAWN, -79362, 205706, -7896, 16383, false, 0, false, world.getInstanceId());
		addSpawn(HIGH_PRIEST_OF_DAWN, -79495, 205774, -7896, 4096, false, 0, false, world.getInstanceId());
		addSpawn(HIGH_PRIEST_OF_DAWN, -79493, 205930, -7896, 61440, false, 0, false, world.getInstanceId());
		addSpawn(HIGH_PRIEST_OF_DAWN, -79362, 206012, -7896, 49152, false, 0, false, world.getInstanceId());
		addSpawn(HIGH_PRIEST_OF_DAWN, -79230, 205935, -7896, 36864, false, 0, false, world.getInstanceId());
		// Guard Npcs
		world.w_npc_1 = addSpawn(GUARDS_OF_THE_DAWN_3, -81938, 205856, -8000, 0, false, 0, false, world.getInstanceId());
		world.w_npc_1.setIsNoRndWalk(true);
		world.w_npc_2 = addSpawn(GUARDS_OF_THE_DAWN_3, -81535, 205503, -8000, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_2.setIsNoRndWalk(true);
		world.w_npc_3 = addSpawn(GUARDS_OF_THE_DAWN_3, -81536, 206223, -8000, 49151, false, 0, false, world.getInstanceId());
		world.w_npc_3.setIsNoRndWalk(true);
		world.w_npc_4 = addSpawn(GUARDS_OF_THE_DAWN, -74934, 213446, -7232, 33334, false, 0, false, world.getInstanceId());
		world.w_npc_4.setIsNoRndWalk(true);
		world.w_npc_5 = addSpawn(GUARDS_OF_THE_DAWN_2, -74951, 211629, -7321, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_5.setIsNoRndWalk(true);
		world.w_npc_6 = addSpawn(GUARDS_OF_THE_DAWN_2, -75650, 212107, -7322, 0, false, 0, false, world.getInstanceId());
		world.w_npc_6.setIsNoRndWalk(true);
		world.w_npc_7 = addSpawn(GUARDS_OF_THE_DAWN_2, -74854, 212107, -7322, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_7.setIsNoRndWalk(true);
		world.w_npc_8 = addSpawn(GUARDS_OF_THE_DAWN_2, -74534, 212108, -7321, 0, false, 0, false, world.getInstanceId());
		world.w_npc_8.setIsNoRndWalk(true);
		world.w_npc_9 = addSpawn(GUARDS_OF_THE_DAWN_2, -75200, 211178, -7322, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_9.setIsNoRndWalk(true);
		world.w_npc_10 = addSpawn(GUARDS_OF_THE_DAWN_2, -74701, 211460, -7321, 49151, false, 0, false, world.getInstanceId());
		world.w_npc_10.setIsNoRndWalk(true);
		world.w_npc_11 = addSpawn(GUARDS_OF_THE_DAWN, -75190, 210176, -7418, 0, false, 0, false, world.getInstanceId());
		world.w_npc_11.setIsNoRndWalk(true);
		world.w_npc_12 = addSpawn(GUARDS_OF_THE_DAWN, -74743, 209820, -7418, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_12.setIsNoRndWalk(true);
		world.w_npc_13 = addSpawn(GUARDS_OF_THE_DAWN_3, -74619, 209981, -7418, 30212, false, 0, false, world.getInstanceId());
		world.w_npc_13.setIsNoRndWalk(true);
		world.w_npc_14 = addSpawn(GUARDS_OF_THE_DAWN_3, -75301, 209980, -7418, 1722, false, 0, false, world.getInstanceId());
		world.w_npc_14.setIsNoRndWalk(true);
		world.w_npc_15 = addSpawn(GUARDS_OF_THE_DAWN_3, -74282, 208784, -7520, 40959, false, 0, false, world.getInstanceId());
		world.w_npc_15.setIsNoRndWalk(true);
		world.w_npc_16 = addSpawn(GUARDS_OF_THE_DAWN, -75559, 207860, -7515, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_16.setIsNoRndWalk(true);
		world.w_npc_17 = addSpawn(GUARDS_OF_THE_DAWN, -74227, 208290, -7520, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_17.setIsNoRndWalk(true);
		world.w_npc_18 = addSpawn(GUARDS_OF_THE_DAWN, -74522, 207063, -7520, 0, false, 0, false, world.getInstanceId());
		world.w_npc_18.setIsNoRndWalk(true);
		world.w_npc_19 = addSpawn(GUARDS_OF_THE_DAWN, -74956, 206348, -7520, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_19.setIsNoRndWalk(true);
		world.w_npc_20 = addSpawn(GUARDS_OF_THE_DAWN, -74246, 206515, -7520, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_20.setIsNoRndWalk(true);
		world.w_npc_21 = addSpawn(GUARDS_OF_THE_DAWN_3, -74558, 206625, -7520, 65102, false, 0, false, world.getInstanceId());
		world.w_npc_21.setIsNoRndWalk(true);
		world.w_npc_22 = addSpawn(GUARDS_OF_THE_DAWN_3, -75454, 206740, -7520, 34645, false, 0, false, world.getInstanceId());
		world.w_npc_22.setIsNoRndWalk(true);
		world.w_npc_23 = addSpawn(GUARDS_OF_THE_DAWN, -75402, 206939, -7520, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_23.setIsNoRndWalk(true);
		world.w_npc_24 = addSpawn(GUARDS_OF_THE_DAWN, -74955, 207611, -7520, 0, false, 0, false, world.getInstanceId());
		world.w_npc_24.setIsNoRndWalk(true);
		world.w_npc_25 = addSpawn(GUARDS_OF_THE_DAWN, -75654, 208112, -7520, 2718, false, 0, false, world.getInstanceId());
		world.w_npc_25.setIsNoRndWalk(true);
		world.w_npc_26 = addSpawn(GUARDS_OF_THE_DAWN, -75428, 208115, -7520, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_26.setIsNoRndWalk(true);
		world.w_npc_27 = addSpawn(GUARDS_OF_THE_DAWN, -76378, 207852, -7616, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_27.setIsNoRndWalk(true);
		world.w_npc_28 = addSpawn(GUARDS_OF_THE_DAWN, -76628, 208151, -7616, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_28.setIsNoRndWalk(true);
		world.w_npc_29 = addSpawn(GUARDS_OF_THE_DAWN, -76374, 208848, -7616, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_29.setIsNoRndWalk(true);
		world.w_npc_30 = addSpawn(GUARDS_OF_THE_DAWN, -76928, 209189, -7616, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_30.setIsNoRndWalk(true);
		world.w_npc_31 = addSpawn(GUARDS_OF_THE_DAWN, -77183, 209443, -7616, 49151, false, 0, false, world.getInstanceId());
		world.w_npc_31.setIsNoRndWalk(true);
		world.w_npc_32 = addSpawn(GUARDS_OF_THE_DAWN_2, -78521, 208035, -7712, 0, false, 0, false, world.getInstanceId());
		world.w_npc_32.setIsNoRndWalk(true);
		world.w_npc_33 = addSpawn(GUARDS_OF_THE_DAWN_2, -77718, 207512, -7712, 24550, false, 0, false, world.getInstanceId());
		world.w_npc_33.setIsNoRndWalk(true);
		world.w_npc_34 = addSpawn(GUARDS_OF_THE_DAWN_3, -76962, 207802, -7712, 35928, false, 0, false, world.getInstanceId());
		world.w_npc_34.setIsNoRndWalk(true);
		world.w_npc_35 = addSpawn(GUARDS_OF_THE_DAWN_2, -76881, 208037, -7712, 32768, false, 0, false, world.getInstanceId());
		world.w_npc_35.setIsNoRndWalk(true);
		world.w_npc_36 = addSpawn(GUARDS_OF_THE_DAWN_2, -77216, 208297, -7712, 35486, false, 0, false, world.getInstanceId());
		world.w_npc_36.setIsNoRndWalk(true);
		world.w_npc_37 = addSpawn(GUARDS_OF_THE_DAWN_3, -77703, 208320, -7712, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_37.setIsNoRndWalk(true);
		world.w_npc_38 = addSpawn(GUARDS_OF_THE_DAWN_3, -77703, 207275, -7712, 49151, false, 0, false, world.getInstanceId());
		world.w_npc_38.setIsNoRndWalk(true);
		world.w_npc_39 = addSpawn(GUARDS_OF_THE_DAWN_2, -78054, 208464, -7712, 0, false, 0, false, world.getInstanceId());
		world.w_npc_39.setIsNoRndWalk(true);
		world.w_npc_40 = addSpawn(GUARDS_OF_THE_DAWN_2, -78335, 207793, -7712, 0, false, 0, false, world.getInstanceId());
		world.w_npc_40.setIsNoRndWalk(true);
		world.w_npc_41 = addSpawn(GUARDS_OF_THE_DAWN_2, -77702, 207414, -7712, 16384, false, 0, false, world.getInstanceId());
		world.w_npc_41.setIsNoRndWalk(true);
		world.w_npc_42 = addSpawn(GUARDS_OF_THE_DAWN_2, -77558, 207138, -7712, 17906, false, 0, false, world.getInstanceId());
		world.w_npc_42.setIsNoRndWalk(true);
		world.w_npc_43 = addSpawn(GUARDS_OF_THE_DAWN_2, -78113, 207384, -7705, 41575, false, 0, false, world.getInstanceId());
		world.w_npc_43.setIsNoRndWalk(true);
		world.w_npc_44 = addSpawn(GUARDS_OF_THE_DAWN_2, -78346, 207146, -7706, 8680, false, 0, false, world.getInstanceId());
		world.w_npc_44.setIsNoRndWalk(true);
		world.w_npc_45 = addSpawn(GUARDS_OF_THE_DAWN_2, -77337, 207428, -7712, 57279, false, 0, false, world.getInstanceId());
		world.w_npc_45.setIsNoRndWalk(true);
		world.w_npc_46 = addSpawn(GUARDS_OF_THE_DAWN_2, -77159, 207642, -7712, 32460, false, 0, false, world.getInstanceId());
		world.w_npc_46.setIsNoRndWalk(true);
		world.w_npc_47 = addSpawn(GUARDS_OF_THE_DAWN, -78921, 206110, -7904, 7872, false, 0, false, world.getInstanceId());
		world.w_npc_47.setIsNoRndWalk(true);
		world.w_npc_48 = addSpawn(GUARDS_OF_THE_DAWN, -78855, 206443, -7896, 24784, false, 0, false, world.getInstanceId());
		world.w_npc_48.setIsNoRndWalk(true);
		world.w_npc_49 = addSpawn(GUARDS_OF_THE_DAWN, -79357, 206713, -7904, 49151, false, 0, false, world.getInstanceId());
		world.w_npc_49.setIsNoRndWalk(true);
		world.w_npc_50 = addSpawn(GUARDS_OF_THE_DAWN, -79665, 206257, -7896, 24528, false, 0, false, world.getInstanceId());
		world.w_npc_50.setIsNoRndWalk(true);
		world.w_npc_51 = addSpawn(GUARDS_OF_THE_DAWN, -79999, 206302, -7904, -8016, false, 0, false, world.getInstanceId());
		world.w_npc_51.setIsNoRndWalk(true);
		world.w_npc_52 = addSpawn(GUARDS_OF_THE_DAWN, -79781, 205602, -7904, -24728, false, 0, false, world.getInstanceId());
		world.w_npc_52.setIsNoRndWalk(true);
		world.w_npc_53 = addSpawn(GUARDS_OF_THE_DAWN, -79849, 205260, -7904, 8032, false, 0, false, world.getInstanceId());
		world.w_npc_53.setIsNoRndWalk(true);
		world.w_npc_54 = addSpawn(GUARDS_OF_THE_DAWN, -79363, 205379, -7904, 49151, false, 0, false, world.getInstanceId());
		world.w_npc_54.setIsNoRndWalk(true);
		world.w_npc_55 = addSpawn(GUARDS_OF_THE_DAWN, -78870, 205253, -7904, 24792, false, 0, false, world.getInstanceId());
		world.w_npc_55.setIsNoRndWalk(true);
		world.w_npc_56 = addSpawn(GUARDS_OF_THE_DAWN, -78928, 205585, -7904, -8184, false, 0, false, world.getInstanceId());
		world.w_npc_56.setIsNoRndWalk(true);
		world.w_npc_57 = addSpawn(GUARDS_OF_THE_DAWN_3, -78926, 205432, -7904, 23278, false, 0, false, world.getInstanceId());
		world.w_npc_57.setIsNoRndWalk(true);
		world.w_npc_58 = addSpawn(GUARDS_OF_THE_DAWN_3, -79813, 205426, -7904, 9231, false, 0, false, world.getInstanceId());
		world.w_npc_58.setIsNoRndWalk(true);
		world.w_npc_59 = addSpawn(GUARDS_OF_THE_DAWN_3, -79814, 206277, -7904, 59013, false, 0, false, world.getInstanceId());
		world.w_npc_59.setIsNoRndWalk(true);
		world.w_npc_60 = addSpawn(GUARDS_OF_THE_DAWN_3, -78891, 206272, -7904, 59013, false, 0, false, world.getInstanceId());
		world.w_npc_60.setIsNoRndWalk(true);
		startQuestTimer("Part2", 3000, world.w_npc_7, null);
	}
	
	protected void openDoor(int doorId, int instanceId)
	{
		for (L2DoorInstance door : InstanceManager.getInstance().getInstance(instanceId).getDoors())
		{
			if (door.getDoorId() == doorId)
			{
				door.openMe();
			}
		}
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		switch (npc.getNpcId())
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
									startQuestTimer("Part3", 30000, null, talker);
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
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		npc.broadcastPacket(new MagicSkillUse(npc, player, GUARD_SKILL.getSkillId(), 1, 2000, 1));
		startQuestTimer("teleportPlayer", 3000, npc, player);
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	private void teleportPlayer(L2PcInstance player, Location loc)
	{
		player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
		player.teleToLocation(loc, 0);
	}
	
	public static void main(String[] args)
	{
		new SanctumOftheLordsOfDawn(-1, SanctumOftheLordsOfDawn.class.getSimpleName(), "instances");
	}
}
