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
package handlers.admincommandhandlers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.l2jserver.Config;
import com.l2jserver.gameserver.SevenSigns;
import com.l2jserver.gameserver.datatables.AdminTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.DayNightSpawnManager;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.instancemanager.QuestManager;
import com.l2jserver.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jserver.gameserver.model.AutoSpawnHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.entity.Instance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Broadcast;
import com.l2jserver.util.StringUtil;

/**
 * This class handles following admin commands: - show_spawns = shows menu - spawn_index lvl = shows menu for monsters with respective level - spawn_monster id = spawns monster id on target
 * @version $Revision: 1.2.2.5.2.5 $ $Date: 2005/04/11 10:06:06 $
 */
public class AdminSpawn implements IAdminCommandHandler
{
	private static final Logger _log = Logger.getLogger(AdminSpawn.class.getName());
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_show_spawns",
		"admin_spawn",
		"admin_spawn_monster",
		"admin_spawn_index",
		"admin_unspawnall",
		"admin_respawnall",
		"admin_spawn_reload",
		"admin_npc_index",
		"admin_spawn_once",
		"admin_show_npcs",
		"admin_spawnnight",
		"admin_spawnday",
		"admin_instance_spawns",
		"admin_list_spawns",
		"admin_list_positions",
		"admin_spawn_debug_menu",
		"admin_spawn_debug_print",
		"admin_spawn_debug_print_menu"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_show_spawns"))
		{
			AdminHtml.showAdminHtml(activeChar, "spawns.htm");
		}
		else if (command.equalsIgnoreCase("admin_spawn_debug_menu"))
		{
			AdminHtml.showAdminHtml(activeChar, "spawns_debug.htm");
		}
		else if (command.startsWith("admin_spawn_debug_print"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			L2Object target = activeChar.getTarget();
			if (target instanceof L2Npc)
			{
				try
				{
					st.nextToken();
					int type = Integer.parseInt(st.nextToken());
					printSpawn((L2Npc) target, type);
					if (command.contains("_menu"))
					{
						AdminHtml.showAdminHtml(activeChar, "spawns_debug.htm");
					}
				}
				catch (Exception e)
				{
				}
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
			}
		}
		else if (command.startsWith("admin_spawn_index"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				st.nextToken();
				int level = Integer.parseInt(st.nextToken());
				int from = 0;
				try
				{
					from = Integer.parseInt(st.nextToken());
				}
				catch (NoSuchElementException nsee)
				{
				}
				showMonsters(activeChar, level, from);
			}
			catch (Exception e)
			{
				AdminHtml.showAdminHtml(activeChar, "spawns.htm");
			}
		}
		else if (command.equals("admin_show_npcs"))
		{
			AdminHtml.showAdminHtml(activeChar, "npcs.htm");
		}
		else if (command.startsWith("admin_npc_index"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				st.nextToken();
				String letter = st.nextToken();
				int from = 0;
				try
				{
					from = Integer.parseInt(st.nextToken());
				}
				catch (NoSuchElementException nsee)
				{
				}
				showNpcs(activeChar, letter, from);
			}
			catch (Exception e)
			{
				AdminHtml.showAdminHtml(activeChar, "npcs.htm");
			}
		}
		else if (command.startsWith("admin_instance_spawns"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				st.nextToken();
				int instance = Integer.parseInt(st.nextToken());
				if (instance >= 300000)
				{
					final StringBuilder html = StringUtil.startAppend(500 + 1000, "<html><table width=\"100%\"><tr><td width=45><button value=\"Main\" action=\"bypass -h admin_admin\" width=45 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td width=180><center>", "<font color=\"LEVEL\">Spawns for " + String.valueOf(instance) + "</font>", "</td><td width=45><button value=\"Back\" action=\"bypass -h admin_current_player\" width=45 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td></tr></table><br>", "<table width=\"100%\"><tr><td width=200>NpcName</td><td width=70>Action</td></tr>");
					int counter = 0;
					int skiped = 0;
					Instance inst = InstanceManager.getInstance().getInstance(instance);
					if (inst != null)
					{
						for (L2Npc npc : inst.getNpcs())
						{
							if (!npc.isDead())
							{
								// Only 50 because of client html limitation
								if (counter < 50)
								{
									StringUtil.append(html, "<tr><td>" + npc.getName() + "</td><td>", "<a action=\"bypass -h admin_move_to " + npc.getX() + " " + npc.getY() + " " + npc.getZ() + "\">Go</a>", "</td></tr>");
									counter++;
								}
								else
								{
									skiped++;
								}
							}
						}
						StringUtil.append(html, "<tr><td>Skipped:</td><td>" + String.valueOf(skiped) + "</td></tr></table></body></html>");
						NpcHtmlMessage ms = new NpcHtmlMessage(1);
						ms.setHtml(html.toString());
						activeChar.sendPacket(ms);
					}
					else
					{
						activeChar.sendMessage("Cannot find instance " + instance);
					}
				}
				else
				{
					activeChar.sendMessage("Invalid instance number.");
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Usage //instance_spawns <instance_number>");
			}
		}
		else if (command.startsWith("admin_unspawnall"))
		{
			Broadcast.toAllOnlinePlayers(SystemMessage.getSystemMessage(SystemMessageId.NPC_SERVER_NOT_OPERATING));
			RaidBossSpawnManager.getInstance().cleanUp();
			DayNightSpawnManager.getInstance().cleanUp();
			L2World.getInstance().deleteVisibleNpcSpawns();
			AdminTable.getInstance().broadcastMessageToGMs("NPC Unspawn completed!");
		}
		else if (command.startsWith("admin_spawnday"))
		{
			DayNightSpawnManager.getInstance().spawnDayCreatures();
		}
		else if (command.startsWith("admin_spawnnight"))
		{
			DayNightSpawnManager.getInstance().spawnNightCreatures();
		}
		else if (command.startsWith("admin_respawnall") || command.startsWith("admin_spawn_reload"))
		{
			// make sure all spawns are deleted
			RaidBossSpawnManager.getInstance().cleanUp();
			DayNightSpawnManager.getInstance().cleanUp();
			L2World.getInstance().deleteVisibleNpcSpawns();
			// now respawn all
			NpcTable.getInstance().reloadAllNpc();
			SpawnTable.getInstance().load();
			RaidBossSpawnManager.getInstance().load();
			AutoSpawnHandler.getInstance().reload();
			SevenSigns.getInstance().spawnSevenSignsNPC();
			QuestManager.getInstance().reloadAllQuests();
			AdminTable.getInstance().broadcastMessageToGMs("NPC Respawn completed!");
		}
		else if (command.startsWith("admin_spawn_monster") || command.startsWith("admin_spawn"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			try
			{
				String cmd = st.nextToken();
				String id = st.nextToken();
				int respawnTime = 0;
				int mobCount = 1;
				if (st.hasMoreTokens())
				{
					mobCount = Integer.parseInt(st.nextToken());
				}
				if (st.hasMoreTokens())
				{
					respawnTime = Integer.parseInt(st.nextToken());
				}
				if (cmd.equalsIgnoreCase("admin_spawn_once"))
				{
					spawnMonster(activeChar, id, respawnTime, mobCount, false);
				}
				else
				{
					spawnMonster(activeChar, id, respawnTime, mobCount, true);
				}
			}
			catch (Exception e)
			{ // Case of wrong or missing monster data
				AdminHtml.showAdminHtml(activeChar, "spawns.htm");
			}
		}
		else if (command.startsWith("admin_list_spawns") || command.startsWith("admin_list_positions"))
		{
			int npcId = 0;
			int teleportIndex = -1;
			try
			{ // admin_list_spawns x[xxxx] x[xx]
				String[] params = command.split(" ");
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher regexp = pattern.matcher(params[1]);
				if (regexp.matches())
				{
					npcId = Integer.parseInt(params[1]);
				}
				else
				{
					params[1] = params[1].replace('_', ' ');
					npcId = NpcTable.getInstance().getTemplateByName(params[1]).getId();
				}
				if (params.length > 2)
				{
					teleportIndex = Integer.parseInt(params[2]);
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Command format is //list_spawns <npcId|npc_name> [tele_index]");
			}
			if (command.startsWith("admin_list_positions"))
			{
				findNPCInstances(activeChar, npcId, teleportIndex, true);
			}
			else
			{
				findNPCInstances(activeChar, npcId, teleportIndex, false);
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	/**
	 * Get all the spawn of a NPC.
	 * @param activeChar
	 * @param npcId
	 * @param teleportIndex
	 * @param showposition
	 */
	private void findNPCInstances(L2PcInstance activeChar, int npcId, int teleportIndex, boolean showposition)
	{
		int index = 0;
		for (L2Spawn spawn : SpawnTable.getInstance().getSpawns(npcId))
		{
			index++;
			L2Npc npc = spawn.getLastSpawn();
			if (teleportIndex > -1)
			{
				if (teleportIndex == index)
				{
					if (showposition && (npc != null))
					{
						activeChar.teleToLocation(npc.getX(), npc.getY(), npc.getZ(), true);
					}
					else
					{
						activeChar.teleToLocation(spawn.getLocation(), true);
					}
				}
			}
			else
			{
				if (showposition && (npc != null))
				{
					activeChar.sendMessage(index + " - " + spawn.getTemplate().getName() + " (" + spawn + "): " + npc.getX() + " " + npc.getY() + " " + npc.getZ());
				}
				else
				{
					activeChar.sendMessage(index + " - " + spawn.getTemplate().getName() + " (" + spawn + "): " + spawn.getX() + " " + spawn.getY() + " " + spawn.getZ());
				}
			}
		}
		
		if (index == 0)
		{
			activeChar.sendMessage(getClass().getSimpleName() + ": No current spawns found.");
		}
	}
	
	private void printSpawn(L2Npc target, int type)
	{
		int i = target.getId();
		int x = target.getSpawn().getX();
		int y = target.getSpawn().getY();
		int z = target.getSpawn().getZ();
		int h = target.getSpawn().getHeading();
		switch (type)
		{
			default:
			case 0:
				_log.info("('',1," + i + "," + x + "," + y + "," + z + ",0,0," + h + ",60,0,0),");
				break;
			case 1:
				_log.info("<spawn npcId=\"" + i + "\" x=\"" + x + "\" y=\"" + y + "\" z=\"" + z + "\" heading=\"" + h + "\" respawn=\"0\" />");
				break;
			case 2:
				_log.info("{ " + i + ", " + x + ", " + y + ", " + z + ", " + h + " },");
				break;
		}
	}
	
	private void spawnMonster(L2PcInstance activeChar, String monsterId, int respawnTime, int mobCount, boolean permanent)
	{
		L2Object target = activeChar.getTarget();
		if (target == null)
		{
			target = activeChar;
		}
		
		L2NpcTemplate template1;
		if (monsterId.matches("[0-9]*"))
		{
			// First parameter was an ID number
			int monsterTemplate = Integer.parseInt(monsterId);
			template1 = NpcTable.getInstance().getTemplate(monsterTemplate);
		}
		else
		{
			// First parameter wasn't just numbers so go by name not ID
			monsterId = monsterId.replace('_', ' ');
			template1 = NpcTable.getInstance().getTemplateByName(monsterId);
		}
		
		try
		{
			L2Spawn spawn = new L2Spawn(template1);
			if (Config.SAVE_GMSPAWN_ON_CUSTOM)
			{
				spawn.setCustom(true);
			}
			spawn.setX(target.getX());
			spawn.setY(target.getY());
			spawn.setZ(target.getZ());
			spawn.setAmount(mobCount);
			spawn.setHeading(activeChar.getHeading());
			spawn.setRespawnDelay(respawnTime);
			if (activeChar.getInstanceId() > 0)
			{
				spawn.setInstanceId(activeChar.getInstanceId());
				permanent = false;
			}
			else
			{
				spawn.setInstanceId(0);
			}
			// TODO add checks for GrandBossSpawnManager
			if (RaidBossSpawnManager.getInstance().isDefined(spawn.getId()))
			{
				activeChar.sendMessage("You cannot spawn another instance of " + template1.getName() + ".");
			}
			else
			{
				if (RaidBossSpawnManager.getInstance().getValidTemplate(spawn.getId()) != null)
				{
					spawn.setRespawnMinDelay(43200);
					spawn.setRespawnMaxDelay(129600);
					RaidBossSpawnManager.getInstance().addNewSpawn(spawn, 0, template1.getBaseHpMax(), template1.getBaseMpMax(), permanent);
				}
				else
				{
					SpawnTable.getInstance().addNewSpawn(spawn, permanent);
					spawn.init();
				}
				if (!permanent)
				{
					spawn.stopRespawn();
				}
				activeChar.sendMessage("Created " + template1.getName() + " on " + target.getObjectId());
			}
		}
		catch (Exception e)
		{
			activeChar.sendPacket(SystemMessageId.TARGET_CANT_FOUND);
		}
	}
	
	private void showMonsters(L2PcInstance activeChar, int level, int from)
	{
		final List<L2NpcTemplate> mobs = NpcTable.getInstance().getAllMonstersOfLevel(level);
		final int mobsCount = mobs.size();
		final StringBuilder tb = StringUtil.startAppend(500 + (mobsCount * 80), "<html><title>Spawn Monster:</title><body><p> Level : ", Integer.toString(level), "<br>Total Npc's : ", Integer.toString(mobsCount), "<br>");
		
		// Loop
		int i = from;
		for (int j = 0; (i < mobsCount) && (j < 50); i++, j++)
		{
			StringUtil.append(tb, "<a action=\"bypass -h admin_spawn_monster ", Integer.toString(mobs.get(i).getId()), "\">", mobs.get(i).getName(), "</a><br1>");
		}
		
		if (i == mobsCount)
		{
			tb.append("<br><center><button value=\"Back\" action=\"bypass -h admin_show_spawns\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		}
		else
		{
			StringUtil.append(tb, "<br><center><button value=\"Next\" action=\"bypass -h admin_spawn_index ", Integer.toString(level), " ", Integer.toString(i), "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back\" action=\"bypass -h admin_show_spawns\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		}
		
		activeChar.sendPacket(new NpcHtmlMessage(5, tb.toString()));
	}
	
	private void showNpcs(L2PcInstance activeChar, String starting, int from)
	{
		final List<L2NpcTemplate> mobs = NpcTable.getInstance().getAllNpcStartingWith(starting);
		final int mobsCount = mobs.size();
		final StringBuilder tb = StringUtil.startAppend(500 + (mobsCount * 80), "<html><title>Spawn Monster:</title><body><p> There are ", Integer.toString(mobsCount), " Npcs whose name starts with ", starting, ":<br>");
		
		// Loop
		int i = from;
		for (int j = 0; (i < mobsCount) && (j < 50); i++, j++)
		{
			StringUtil.append(tb, "<a action=\"bypass -h admin_spawn_monster ", Integer.toString(mobs.get(i).getId()), "\">", mobs.get(i).getName(), "</a><br1>");
		}
		
		if (i == mobsCount)
		{
			tb.append("<br><center><button value=\"Back\" action=\"bypass -h admin_show_npcs\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		}
		else
		{
			StringUtil.append(tb, "<br><center><button value=\"Next\" action=\"bypass -h admin_npc_index ", starting, " ", Integer.toString(i), "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><button value=\"Back\" action=\"bypass -h admin_show_npcs\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
		}
		
		activeChar.sendPacket(new NpcHtmlMessage(5, tb.toString()));
	}
}
