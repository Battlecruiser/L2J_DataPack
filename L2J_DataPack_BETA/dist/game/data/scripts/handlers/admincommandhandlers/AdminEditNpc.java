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
package handlers.admincommandhandlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.datatables.NpcTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.Elementals;
import com.l2jserver.gameserver.model.L2DropCategory;
import com.l2jserver.gameserver.model.L2DropData;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.items.L2Item;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.stats.MoveType;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Nos
 */
public class AdminEditNpc implements IAdminCommandHandler
{
	private static Logger _log = Logger.getLogger(AdminEditNpc.class.getName());
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_edit_npc",
		"admin_save_npc",
		"admin_show_skill_list_npc",
		"admin_add_skill_npc",
		"admin_del_skill_npc",
		"admin_show_drop_list",
		"admin_add_drop",
		"admin_del_drop",
		"admin_log_npc_spawn"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		final StringTokenizer st = new StringTokenizer(command, " ");
		final String actualCommand = st.nextToken();
		switch (actualCommand.toLowerCase())
		{
			case "admin_edit_npc":
			{
				if (st.countTokens() < 2)
				{
					activeChar.sendMessage("Usage: //edit_npc <stats|ai|elementals|visuals> <npc_id>");
					return false;
				}
				
				String category = st.nextToken();
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						showNpcProperty(activeChar, npc, category);
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. npc_id:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id must be a number.");
				}
				break;
			}
			case "admin_save_npc":
			{
				if (st.countTokens() < 3)
				{
					activeChar.sendMessage("Usage: //save_npc <stats|ai|elementals|visuals> <npc_id> <statToSet> <value>");
					return false;
				}
				
				String category = st.nextToken();
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					String statToSet = st.nextToken();
					String value = st.hasMoreTokens() ? st.nextToken() : "";
					
					while (st.hasMoreTokens())
					{
						value += " " + st.nextToken();
					}
					
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						saveNpcProperty(activeChar, npc, category, statToSet, value);
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. npc_id:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id must be a number.");
				}
				break;
			}
			case "admin_show_skill_list_npc":
			{
				if (st.countTokens() < 1)
				{
					activeChar.sendMessage("Usage: //show_skill_list_npc <npc_id> <page>");
					return false;
				}
				
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					int page = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : 0;
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						showNpcSkillList(activeChar, npc, page);
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. npc_id:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id must be a number.");
				}
				break;
			}
			case "admin_add_skill_npc":
			{
				if (st.countTokens() < 3)
				{
					activeChar.sendMessage("Usage: //add_skill_npc <npc_id> <skill_id> <skill_lvl>");
					return false;
				}
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					int skillId = Integer.parseInt(st.nextToken());
					int skillLvl = Integer.parseInt(st.nextToken());
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						L2Skill skill = SkillTable.getInstance().getInfo(skillId, skillLvl);
						if (skill != null)
						{
							addNpcSkill(activeChar, npc, skill);
						}
						else
						{
							activeChar.sendMessage("Skill does not exist or not loaded. skill_id:" + skillId + " skill_lvl:" + skillLvl);
						}
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. npc_id:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id, skill_id and skill_lvl must all be numbers.");
				}
				break;
			}
			case "admin_del_skill_npc":
			{
				if (st.countTokens() < 2)
				{
					activeChar.sendMessage("Usage: //del_skill_npc <npc_id> <skill_id> <page>");
					return false;
				}
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					int skillId = Integer.parseInt(st.nextToken());
					int page = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : 0;
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						delNpcSkill(activeChar, npc, skillId, page);
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. ID:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id, skill_id and skill_lvl must all be numbers.");
				}
				break;
			}
			case "admin_show_drop_list":
			{
				if (st.countTokens() < 1)
				{
					activeChar.sendMessage("Usage: //show_drop_list <npc_id> <page>");
					return false;
				}
				
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					int page = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : 0;
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						showNpcDropList(activeChar, npc, page);
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. npc_id:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id must be a number.");
				}
				break;
			}
			case "admin_add_drop":
			{
				if (st.countTokens() < 6)
				{
					activeChar.sendMessage("Usage: //add_drop <npc_id> <item_id> <category> <min> <max> <chance>");
					return false;
				}
				
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					int itemId = Integer.parseInt(st.nextToken());
					int category = Integer.parseInt(st.nextToken());
					int min = Integer.parseInt(st.nextToken());
					int max = Integer.parseInt(st.nextToken());
					int chance = Integer.parseInt(st.nextToken());
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						addNpcDrop(activeChar, npc, itemId, category, min, max, chance);
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. npc_id:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id must be a number.");
				}
				break;
			}
			case "admin_del_drop":
			{
				if (st.countTokens() < 3)
				{
					activeChar.sendMessage("Usage: //add_drop <npc_id> <item_id> <category> <min> <max> <chance>");
					return false;
				}
				
				try
				{
					int npcId = Integer.parseInt(st.nextToken());
					int category = Integer.parseInt(st.nextToken());
					int itemId = Integer.parseInt(st.nextToken());
					int page = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : 0;
					final L2NpcTemplate npc = NpcTable.getInstance().getTemplate(npcId);
					if (npc != null)
					{
						delNpcDrop(activeChar, npc, category, itemId, page);
					}
					else
					{
						activeChar.sendMessage("NPC does not exist or not loaded. npc_id:" + npcId);
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("npc_id must be a number.");
				}
				break;
			}
			case "admin_log_npc_spawn":
			{
				L2Object target = activeChar.getTarget();
				if (target instanceof L2Npc)
				{
					L2Npc npc = (L2Npc) target;
					_log.info("('', 1, " + npc.getId() + ", " + npc.getX() + ", " + npc.getY() + ", " + npc.getZ() + ", 0, 0, " + npc.getHeading() + ", 60, 0, 0),");
				}
				break;
			}
		}
		
		return true;
	}
	
	private void showNpcProperty(L2PcInstance activeChar, L2NpcTemplate npc, String category)
	{
		if (category.equalsIgnoreCase("stats") || category.equalsIgnoreCase("ai") || category.equalsIgnoreCase("elementals") || category.equalsIgnoreCase("visuals"))
		{
			final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
			html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/editnpc-" + category.toLowerCase() + ".htm");
			
			html.replace("%npcId%", String.valueOf(npc.getId()));
			html.replace("%title_npc_id%", String.valueOf(npc.getId()));
			html.replace("%title_npc_name%", String.valueOf(npc.getName()));
			switch (category.toLowerCase())
			{
				case "stats":
				{
					html.replace("%level%", String.valueOf(npc.getLevel()));
					html.replace("%exp%", String.valueOf(npc.getRewardExp()));
					html.replace("%sp%", String.valueOf(npc.getRewardSp()));
					html.replace("%hp%", String.valueOf(npc.getBaseHpMax()));
					html.replace("%mp%", String.valueOf(npc.getBaseMpMax()));
					html.replace("%hpreg%", String.valueOf(npc.getBaseHpReg()));
					html.replace("%mpreg%", String.valueOf(npc.getBaseMpReg()));
					html.replace("%patk%", String.valueOf(npc.getBasePAtk()));
					html.replace("%pdef%", String.valueOf(npc.getBasePDef()));
					html.replace("%matk%", String.valueOf(npc.getBaseMAtk()));
					html.replace("%mdef%", String.valueOf(npc.getBaseMDef()));
					html.replace("%atkspd%", String.valueOf(npc.getBasePAtkSpd()));
					html.replace("%matkspd%", String.valueOf(npc.getBaseMAtkSpd()));
					html.replace("%str%", String.valueOf(npc.getBaseSTR()));
					html.replace("%con%", String.valueOf(npc.getBaseCON()));
					html.replace("%dex%", String.valueOf(npc.getBaseDEX()));
					html.replace("%int%", String.valueOf(npc.getBaseINT()));
					html.replace("%wit%", String.valueOf(npc.getBaseWIT()));
					html.replace("%men%", String.valueOf(npc.getBaseMEN()));
					html.replace("%critical%", String.valueOf(npc.getBaseCritRate()));
					html.replace("%attackrange%", String.valueOf(npc.getBaseAttackRange()));
					html.replace("%walkspd%", String.valueOf(npc.getBaseMoveSpeed(MoveType.WALK)));
					html.replace("%runspd%", String.valueOf(npc.getBaseMoveSpeed(MoveType.RUN)));
					break;
				}
				case "ai":
				{
					html.replace("%aggro%", String.valueOf(npc.getAIDataStatic().getAggroRange()));
					html.replace("%clan%", String.valueOf(npc.getAIDataStatic().getClan()));
					html.replace("%clanRange%", String.valueOf(npc.getAIDataStatic().getClanRange()));
					html.replace("%enemyClan%", String.valueOf(npc.getAIDataStatic().getEnemyClan()));
					html.replace("%enemyRange%", String.valueOf(npc.getAIDataStatic().getEnemyRange()));
					html.replace("%dodge%", String.valueOf(npc.getAIDataStatic().getDodge()));
					html.replace("%canMove%", String.valueOf(npc.getAIDataStatic().getCanMove()));
					html.replace("%primarySkillId%", String.valueOf(npc.getAIDataStatic().getPrimarySkillId()));
					html.replace("%minSkillChance%", String.valueOf(npc.getAIDataStatic().getMinSkillChance()));
					html.replace("%maxSkillChance%", String.valueOf(npc.getAIDataStatic().getMaxSkillChance()));
					html.replace("%minRangeSkill%", String.valueOf(npc.getAIDataStatic().getShortRangeSkill()));
					html.replace("%minRangeChance%", String.valueOf(npc.getAIDataStatic().getShortRangeChance()));
					html.replace("%maxRangeSkill%", String.valueOf(npc.getAIDataStatic().getLongRangeSkill()));
					html.replace("%maxRangeChance%", String.valueOf(npc.getAIDataStatic().getLongRangeChance()));
					html.replace("%soulShot%", String.valueOf(npc.getAIDataStatic().getSoulShot()));
					html.replace("%ssChance%", String.valueOf(npc.getAIDataStatic().getSoulShotChance()));
					html.replace("%spiritShot%", String.valueOf(npc.getAIDataStatic().getSpiritShot()));
					html.replace("%spsChance%", String.valueOf(npc.getAIDataStatic().getSpiritShotChance()));
					html.replace("%isChaos%", String.valueOf(npc.getAIDataStatic().getIsChaos()));
					html.replace("%aiType%", String.valueOf(npc.getAIDataStatic().getAiType()));
					break;
				}
				case "elementals":
				{
					int elements[] =
					{
						npc.getBaseFire(),
						npc.getBaseWater(),
						npc.getBaseWind(),
						npc.getBaseEarth(),
						npc.getBaseHoly(),
						npc.getBaseDark()
					};
					byte attackAttribute = -1;
					int max_element = 0;
					for (byte i = 0; i < 6; i++)
					{
						if (elements[i] > max_element)
						{
							attackAttribute = i;
							max_element = elements[i];
						}
					}
					html.replace("%elemAtkType%", Elementals.getElementName(attackAttribute));
					html.replace("%elemAtkValue%", String.valueOf(attackAttribute != -1 ? elements[attackAttribute] : 0));
					html.replace("%fireDefValue%", String.valueOf(npc.getBaseFireRes()));
					html.replace("%waterDefValue%", String.valueOf(npc.getBaseWaterRes()));
					html.replace("%windDefValue%", String.valueOf(npc.getBaseWindRes()));
					html.replace("%earthDefValue%", String.valueOf(npc.getBaseEarthRes()));
					html.replace("%holyDefValue%", String.valueOf(npc.getBaseHolyRes()));
					html.replace("%darkDefValue%", String.valueOf(npc.getBaseDarkRes()));
					break;
				}
				case "visuals":
				{
					html.replace("%idTemplate%", String.valueOf(npc.getIdTemplate()));
					html.replace("%type%", String.valueOf(npc.getType()));
					html.replace("%showName%", String.valueOf(npc.getAIDataStatic().showName() ? 1 : 0));
					html.replace("%name%", npc.getName());
					html.replace("%serverSideName%", String.valueOf(npc.isServerSideName() ? 1 : 0));
					html.replace("%title%", npc.getTitle());
					html.replace("%serverSideTitle%", String.valueOf(npc.isServerSideTitle() ? 1 : 0));
					html.replace("%targetable%", String.valueOf(npc.getAIDataStatic().isTargetable() ? 1 : 0));
					html.replace("%rhand%", String.valueOf(npc.getRightHand()));
					html.replace("%lhand%", String.valueOf(npc.getLeftHand()));
					html.replace("%enchant%", String.valueOf(npc.getEnchantEffect()));
					html.replace("%collision_radius%", String.valueOf(npc.getCollisionRadius()));
					html.replace("%collision_height%", String.valueOf(npc.getCollisionHeight()));
					html.replace("%sex%", String.valueOf(npc.getSex()));
					html.replace("%dropHerbGroup%", String.valueOf(npc.getDropHerbGroup()));
					break;
				}
			}
			activeChar.sendPacket(html);
		}
		else
		{
			activeChar.sendMessage("category can only be stats, ai, elementals or visuals.");
		}
	}
	
	private void saveNpcProperty(L2PcInstance activeChar, L2NpcTemplate npc, String category, String statToSet, String value)
	{
		StatsSet newNpcData = new StatsSet();
		try
		{
			newNpcData.set("npcId", npc.getId());
			switch (statToSet)
			{
				case "serverSideName":
				case "serverSideTitle":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue == 0) || (intValue == 1))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0 or 1.");
						return;
					}
					break;
				}
				case "sex":
				{
					if (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female") || value.equalsIgnoreCase("etc"))
					{
						newNpcData.set(statToSet, value.toLowerCase());
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be male, female or etc.");
						return;
					}
					break;
				}
				case "enchant":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 50))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-50.");
						return;
					}
					break;
				}
				case "level":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 1) && (intValue <= 87))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 1-87.");
						return;
					}
					break;
				}
				case "str":
				case "con":
				case "dex":
				case "int":
				case "wit":
				case "men":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 1) && (intValue <= 99))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 1-99.");
						return;
					}
					break;
				}
				case "critical":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 1) && (intValue <= 127))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 1-127.");
						return;
					}
					break;
				}
				case "dropHerbGroup":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 127))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-127.");
						return;
					}
					break;
				}
				case "atkspd":
				case "matkspd":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 1) && (intValue <= 1000))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 1-1000.");
						return;
					}
					break;
				}
				case "attackrange":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 1) && (intValue <= 2000))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 1-2000.");
						return;
					}
					break;
				}
				case "rhand":
				case "lhand":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 65535))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-65535.");
						return;
					}
					break;
				}
				case "idTemplate":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 1) && (intValue <= 65535))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 1-65535.");
						return;
					}
					break;
				}
				case "exp":
				case "sp":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 2147483647))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-2147483647.");
						return;
					}
					break;
				}
				case "collision_radius":
				case "collision_height":
				{
					double doubleValue = Double.parseDouble(value);
					if ((doubleValue >= -9999.99) && (doubleValue <= 9999.99))
					{
						newNpcData.set(statToSet, doubleValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be -9999.99-9999.99.");
						return;
					}
					break;
				}
				case "walkspd":
				case "runspd":
				{
					double doubleValue = Double.parseDouble(value);
					if ((doubleValue >= 0) && (doubleValue <= 99999.99999))
					{
						newNpcData.set(statToSet, doubleValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-99999.99999.");
						return;
					}
					break;
				}
				case "patk":
				case "pdef":
				case "matk":
				case "mdef":
				{
					double doubleValue = Double.parseDouble(value);
					if ((doubleValue >= 0) && (doubleValue <= 9999999.99999))
					{
						newNpcData.set(statToSet, doubleValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-9999999.99999.");
						return;
					}
					break;
				}
				case "hp":
				case "mp":
				case "hpreg":
				case "mpreg":
				{
					double doubleValue = Double.parseDouble(value);
					if ((doubleValue >= 0) && (doubleValue <= 999999999999999.999999999999999))
					{
						newNpcData.set(statToSet, doubleValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-999999999999999.999999999999999.");
						return;
					}
					break;
				}
				case "type":
				{
					if ((value.length() >= 1) && (value.length() <= 22))
					{
						Class.forName("com.l2jserver.gameserver.model.actor.instance." + value + "Instance");
						newNpcData.set(statToSet, value);
					}
					else
					{
						activeChar.sendMessage("Length of " + statToSet + " must be 1-22.");
						return;
					}
					break;
				}
				case "title":
				{
					if ((value.length() >= 0) && (value.length() <= 45))
					{
						newNpcData.set(statToSet, value);
					}
					else
					{
						activeChar.sendMessage("Length of " + statToSet + " must be 1-45.");
						return;
					}
					break;
				}
				case "name":
				{
					if ((value.length() >= 0) && (value.length() <= 200))
					{
						newNpcData.set(statToSet, value);
					}
					else
					{
						activeChar.sendMessage("Length of " + statToSet + " must be 1-200.");
						return;
					}
					break;
				}
				case "canMove":
				case "targetable":
				case "showName":
				case "isChaos":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue == 0) || (intValue == 1))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0 or 1.");
						return;
					}
					break;
				}
				case "dodge":
				case "minSkillChance":
				case "maxSkillChance":
				case "minRangeChance":
				case "maxRangeChance":
				case "ssChance":
				case "spsChance":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 100))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-100.");
						return;
					}
					break;
				}
				case "aggro":
				case "clanRange":
				case "enemyRange":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 3000))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-3000.");
						return;
					}
					break;
				}
				case "primarySkillId":
				case "minRangeSkill":
				case "maxRangeSkill":
				case "soulShot":
				case "spiritShot":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 65535))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-65535.");
						return;
					}
					break;
				}
				case "clan":
				case "enemyClan":
				{
					if (value.isEmpty())
					{
						newNpcData.set(statToSet, "null");
					}
					else if ((value.length() >= 1) && (value.length() <= 40))
					{
						
						newNpcData.set(statToSet, value);
					}
					else
					{
						activeChar.sendMessage("Length of " + statToSet + " must be 1-40 or empty for null.");
						return;
					}
					break;
				}
				case "aiType":
				{
					switch (value)
					{
						case "fighter":
						case "archer":
						case "mage":
						case "healer":
						case "balanced":
						case "corpse":
						{
							newNpcData.set(statToSet, value);
							break;
						}
						default:
						{
							activeChar.sendMessage("Value of " + statToSet + " must be fighter, archer, mage, healer, balanced, or corpse.");
							return;
						}
					}
					break;
				}
				case "elemAtkType":
				{
					switch (value)
					{
						case "fire":
						{
							newNpcData.set(statToSet, Elementals.FIRE);
							break;
						}
						case "water":
						{
							newNpcData.set(statToSet, Elementals.WATER);
							break;
						}
						case "earth":
						{
							newNpcData.set(statToSet, Elementals.EARTH);
							break;
						}
						case "wind":
						{
							newNpcData.set(statToSet, Elementals.WIND);
							break;
						}
						case "holy":
						{
							newNpcData.set(statToSet, Elementals.HOLY);
							break;
						}
						case "dark":
						{
							newNpcData.set(statToSet, Elementals.DARK);
							break;
						}
						default:
						{
							activeChar.sendMessage("Value of " + statToSet + " must be fire, water, earth, wind, holy or dark.");
							return;
						}
					}
					break;
				}
				case "elemAtkValue":
				case "fireDefValue":
				case "waterDefValue":
				case "windDefValue":
				case "earthDefValue":
				case "holyDefValue":
				case "darkDefValue":
				{
					int intValue = Integer.parseInt(value);
					if ((intValue >= 0) && (intValue <= 3000))
					{
						newNpcData.set(statToSet, intValue);
					}
					else
					{
						activeChar.sendMessage("Value of " + statToSet + " must be 0-3000.");
						return;
					}
					break;
				}
				default:
				{
					
					activeChar.sendMessage("Unknown stat " + statToSet + " can't set.");
					return;
				}
			}
			NpcTable.getInstance().saveNpc(newNpcData);
			showNpcProperty(activeChar, npc, category);
		}
		catch (Exception e)
		{
			activeChar.sendMessage("Could not set npc property. npc_id:" + npc.getId() + " stat:" + statToSet + " value:" + value);
		}
	}
	
	private void showNpcSkillList(L2PcInstance activeChar, L2NpcTemplate npc, int page)
	{
		final int PAGE_SIZE = 20;
		
		final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
		html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/editnpc-skills.htm");
		
		html.replace("%npcId%", String.valueOf(npc.getId()));
		html.replace("%title_npc_name%", String.valueOf(npc.getName()));
		html.replace("%page%", String.valueOf(page + 1));
		
		final Map<Integer, L2Skill> skills = npc.getSkills();
		
		int pages = skills.size() / PAGE_SIZE;
		if ((PAGE_SIZE * pages) < skills.size())
		{
			pages++;
		}
		
		if (pages > 1)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("<table width=280 cellspacing=0><tr>");
			for (int i = 0; i < pages; i++)
			{
				sb.append("<td align=center><button action=\"bypass admin_show_skill_list_npc " + npc.getId() + " " + i + "\" value=\"" + (i + 1) + "\" width=30 height=22 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
			}
			sb.append("</tr></table>");
			html.replace("%pages%", sb.toString());
		}
		else
		{
			html.replace("%pages%", "");
		}
		
		if (page >= pages)
		{
			page = pages - 1;
		}
		
		int start = 0;
		if (page > 0)
		{
			start = PAGE_SIZE * page;
		}
		
		int i = 0;
		StringBuilder sb = new StringBuilder(Math.min(PAGE_SIZE, skills.size()) * 550);
		for (L2Skill skill : skills.values())
		{
			if (i < start)
			{
				i++;
				continue;
			}
			
			sb.append("<table width=277 height=32 cellspacing=0 background=\"L2UI_CT1.Windows.Windows_DF_TooltipBG\">");
			sb.append("<tr><td fixwidth=32 background=\"" + skill.getIcon() + "\"></td>");
			sb.append("<td fixwidth=110>");
			sb.append(skill.getName());
			sb.append("</td>");
			sb.append("<td fixwidth=45 align=center>");
			sb.append(skill.getId());
			sb.append("</td>");
			sb.append("<td fixwidth=35 align=center>");
			sb.append(skill.getLevel());
			sb.append("</td>");
			sb.append("<td fixwidth=45>");
			sb.append("<button action=\"bypass admin_del_skill_npc " + npc.getId() + " " + skill.getId() + " " + page + "\" value=\"Delete\" width=45 height=30 back=\"L2UI_CT1.Button_DF_Calculator_Down\" fore=\"L2UI_CT1.Button_DF_Calculator\">");
			sb.append("</td></tr></table>");
			
			i++;
			if (i >= (PAGE_SIZE + start))
			{
				break;
			}
		}
		html.replace("%skills%", sb.toString());
		activeChar.sendPacket(html);
	}
	
	private void addNpcSkill(L2PcInstance activeChar, L2NpcTemplate npc, L2Skill skill)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String table_name = npc.isCustom() ? "custom_npcskills" : "npcskills";
			try (PreparedStatement ps = con.prepareStatement("INSERT INTO `" + table_name + "`(`npcid`, `skillid`, `level`) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE `level` = ?"))
			{
				ps.setInt(1, npc.getId());
				ps.setInt(2, skill.getId());
				ps.setInt(3, skill.getLevel());
				ps.setInt(4, skill.getLevel());
				ps.executeUpdate();
			}
			NpcTable.getInstance().reloadNpc(npc.getId(), false, false, false, true, false, false);
			showNpcSkillList(activeChar, npc, 0);
		}
		catch (SQLException e)
		{
			String message = "Could not store new skill to database. npc_id: " + npc.getId() + " skill_id: " + skill.getId() + " skill_lvl:" + skill.getLevel();
			activeChar.sendMessage(message);
			_log.log(Level.WARNING, message, e);
		}
	}
	
	private void delNpcSkill(L2PcInstance activeChar, L2NpcTemplate npc, int skillId, int page)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String table_name = npc.isCustom() ? "custom_npcskills" : "npcskills";
			try (PreparedStatement ps = con.prepareStatement("DELETE FROM `" + table_name + "` WHERE `npcid` = ? AND `skillid` = ?"))
			{
				ps.setInt(1, npc.getId());
				ps.setInt(2, skillId);
				ps.executeUpdate();
			}
			NpcTable.getInstance().reloadNpc(npc.getId(), false, false, false, true, false, false);
			showNpcSkillList(activeChar, npc, page);
		}
		catch (SQLException e)
		{
			String message = "Could not delete skill from database. npc_id: " + npc.getId() + " skill_id: " + skillId;
			activeChar.sendMessage(message);
			_log.log(Level.WARNING, message, e);
		}
	}
	
	private void showNpcDropList(L2PcInstance activeChar, L2NpcTemplate npc, int page)
	{
		final int PAGE_SIZE = 20;
		
		final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
		html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/editnpc-drops.htm");
		
		html.replace("%npcId%", String.valueOf(npc.getId()));
		html.replace("%title_npc_name%", String.valueOf(npc.getName()));
		html.replace("%page%", String.valueOf(page + 1));
		
		int itemCount = 0;
		
		final List<L2DropCategory> dropCategories = npc.getDropData();
		for (L2DropCategory dropCategory : dropCategories)
		{
			itemCount += dropCategory.getAllDrops().size();
		}
		
		int pages = itemCount / PAGE_SIZE;
		if ((PAGE_SIZE * pages) < itemCount)
		{
			pages++;
		}
		
		if (pages > 1)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("<table width=280 cellspacing=0><tr>");
			for (int i = 0; i < pages; i++)
			{
				sb.append("<td align=center><button action=\"bypass admin_show_drop_list " + npc.getId() + " " + i + "\" value=\"" + (i + 1) + "\" width=30 height=22 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
			}
			sb.append("</tr></table>");
			html.replace("%pages%", sb.toString());
		}
		else
		{
			html.replace("%pages%", "");
		}
		
		if (page >= pages)
		{
			page = pages - 1;
		}
		
		int start = 0;
		if (page > 0)
		{
			start = PAGE_SIZE * page;
		}
		
		int i = 0;
		StringBuilder sb = new StringBuilder(Math.min(PAGE_SIZE, itemCount) * 650);
		for (L2DropCategory dropCategory : dropCategories)
		{
			for (L2DropData dropData : dropCategory.getAllDrops())
			{
				if (i < start)
				{
					i++;
					continue;
				}
				
				final L2Item item = ItemTable.getInstance().getTemplate(dropData.getItemId());
				if (item == null)
				{
					continue;
				}
				
				sb.append("Name: <font color=\"LEVEL\">[" + item.getName() + "]</font> ID: <font color=\"LEVEL\">[" + item.getId() + "]</font> Category: <font color=\"LEVEL\">[" + dropCategory.getCategoryType() + "]</font>");
				sb.append("<table width=277 height=32 cellspacing=0 background=\"L2UI_CT1.Windows.Windows_DF_TooltipBG\">");
				sb.append("<tr><td fixwidth=32 background=\"" + item.getIcon() + "\"></td>");
				sb.append("<td fixwidth=64 align=center>");
				sb.append(dropData.getMinDrop());
				sb.append("</td>");
				sb.append("<td fixwidth=64 align=center>");
				sb.append(dropData.getMaxDrop());
				sb.append("</td>");
				sb.append("<td fixwidth=60 align=center>");
				sb.append(dropData.getChance());
				sb.append("</td>");
				sb.append("<td fixwidth=45>");
				sb.append("<button action=\"bypass admin_del_drop " + npc.getId() + " " + dropCategory.getCategoryType() + " " + item.getId() + " " + page + "\" value=\"Delete\" width=45 height=30 back=\"L2UI_CT1.Button_DF_Calculator_Down\" fore=\"L2UI_CT1.Button_DF_Calculator\">");
				sb.append("</td></tr></table>");
				
				i++;
				if (i >= (PAGE_SIZE + start))
				{
					break;
				}
			}
			
			if (i >= (PAGE_SIZE + start))
			{
				break;
			}
		}
		html.replace("%drops%", sb.toString());
		activeChar.sendPacket(html);
	}
	
	private void addNpcDrop(L2PcInstance activeChar, L2NpcTemplate npc, int itemId, int category, int min, int max, int chance)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String table_name = npc.isCustom() ? "custom_droplist" : "droplist";
			try (PreparedStatement ps = con.prepareStatement("INSERT INTO `" + table_name + "`(`mobId`, `itemId`, `min`, `max`, `category`, `chance`) VALUES(?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `min` = ?, `max` = ?, `chance` = ?"))
			{
				ps.setInt(1, npc.getId());
				ps.setInt(2, itemId);
				ps.setInt(3, min);
				ps.setInt(4, max);
				ps.setInt(5, category);
				ps.setInt(6, chance);
				ps.setInt(7, min);
				ps.setInt(8, max);
				ps.setInt(9, chance);
				ps.executeUpdate();
			}
			NpcTable.getInstance().reloadNpc(npc.getId(), false, false, false, false, true, false);
			showNpcDropList(activeChar, npc, 0);
		}
		catch (SQLException e)
		{
			String message = "Could not store new drop to database. npc_id: " + npc.getId() + " item_id: " + itemId;
			activeChar.sendMessage(message);
			_log.log(Level.WARNING, message, e);
		}
	}
	
	private void delNpcDrop(L2PcInstance activeChar, L2NpcTemplate npc, int category, int itemId, int page)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection())
		{
			String table_name = npc.isCustom() ? "custom_droplist" : "droplist";
			try (PreparedStatement ps = con.prepareStatement("DELETE FROM `" + table_name + "` WHERE `mobId` = ? AND `category` = ? AND `itemId` = ?"))
			{
				ps.setInt(1, npc.getId());
				ps.setInt(2, category);
				ps.setInt(3, itemId);
				ps.executeUpdate();
			}
			NpcTable.getInstance().reloadNpc(npc.getId(), false, false, false, false, true, false);
			showNpcDropList(activeChar, npc, page);
		}
		catch (SQLException e)
		{
			String message = "Could not delete drop from database. npc_id: " + npc.getId() + " category: " + category + " item_id:" + itemId;
			activeChar.sendMessage(message);
			_log.log(Level.WARNING, message, e);
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
