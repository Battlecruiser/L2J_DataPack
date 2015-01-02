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
package handlers.admincommandhandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.SkillTreesData;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.AbstractEffect;
import com.l2jserver.gameserver.model.skills.AbnormalType;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SkillCoolTime;
import com.l2jserver.gameserver.util.GMAudit;
import com.l2jserver.util.StringUtil;

public class AdminBuffs implements IAdminCommandHandler
{
	private final static int PAGE_LIMIT = 20;
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_getbuffs",
		"admin_getbuffs_ps",
		"admin_stopbuff",
		"admin_stopallbuffs",
		"admin_areacancel",
		"admin_removereuse",
		"admin_switch_gm_buffs"
	};
	// Misc
	private static final String FONT_RED1 = "<font color=\"FF0000\">";
	private static final String FONT_RED2 = "</font>";
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_getbuffs"))
		{
			final StringTokenizer st = new StringTokenizer(command, " ");
			command = st.nextToken();
			if (st.hasMoreTokens())
			{
				final String playername = st.nextToken();
				L2PcInstance player = L2World.getInstance().getPlayer(playername);
				if (player != null)
				{
					int page = 1;
					if (st.hasMoreTokens())
					{
						page = Integer.parseInt(st.nextToken());
					}
					showBuffs(activeChar, player, page, command.endsWith("_ps"));
					return true;
				}
				activeChar.sendMessage("The player " + playername + " is not online.");
				return false;
			}
			else if ((activeChar.getTarget() != null) && activeChar.getTarget().isCharacter())
			{
				showBuffs(activeChar, (L2Character) activeChar.getTarget(), 1, command.endsWith("_ps"));
				return true;
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
				return false;
			}
		}
		else if (command.startsWith("admin_stopbuff"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command, " ");
				
				st.nextToken();
				int objectId = Integer.parseInt(st.nextToken());
				int skillId = Integer.parseInt(st.nextToken());
				
				removeBuff(activeChar, objectId, skillId);
				return true;
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Failed removing effect: " + e.getMessage());
				activeChar.sendMessage("Usage: //stopbuff <objectId> <skillId>");
				return false;
			}
		}
		else if (command.startsWith("admin_stopallbuffs"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command, " ");
				st.nextToken();
				int objectId = Integer.parseInt(st.nextToken());
				removeAllBuffs(activeChar, objectId);
				return true;
			}
			catch (Exception e)
			{
				activeChar.sendMessage("Failed removing all effects: " + e.getMessage());
				activeChar.sendMessage("Usage: //stopallbuffs <objectId>");
				return false;
			}
		}
		else if (command.startsWith("admin_areacancel"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken();
			String val = st.nextToken();
			try
			{
				int radius = Integer.parseInt(val);
				
				for (L2Character knownChar : activeChar.getKnownList().getKnownCharactersInRadius(radius))
				{
					if (knownChar.isPlayer() && !knownChar.equals(activeChar))
					{
						knownChar.stopAllEffects();
					}
				}
				
				activeChar.sendMessage("All effects canceled within radius " + radius);
				return true;
			}
			catch (NumberFormatException e)
			{
				activeChar.sendMessage("Usage: //areacancel <radius>");
				return false;
			}
		}
		else if (command.startsWith("admin_removereuse"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			command = st.nextToken();
			
			L2PcInstance player = null;
			if (st.hasMoreTokens())
			{
				String playername = st.nextToken();
				
				try
				{
					player = L2World.getInstance().getPlayer(playername);
				}
				catch (Exception e)
				{
				}
				
				if (player == null)
				{
					activeChar.sendMessage("The player " + playername + " is not online.");
					return false;
				}
			}
			else if ((activeChar.getTarget() != null) && activeChar.getTarget().isPlayer())
			{
				player = activeChar.getTarget().getActingPlayer();
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT);
				return false;
			}
			
			try
			{
				player.resetTimeStamps();
				player.resetDisabledSkills();
				player.sendPacket(new SkillCoolTime(player));
				activeChar.sendMessage("Skill reuse was removed from " + player.getName() + ".");
				return true;
			}
			catch (NullPointerException e)
			{
				return false;
			}
		}
		else if (command.startsWith("admin_switch_gm_buffs"))
		{
			if (Config.GM_GIVE_SPECIAL_SKILLS != Config.GM_GIVE_SPECIAL_AURA_SKILLS)
			{
				final boolean toAuraSkills = activeChar.getKnownSkill(7041) != null;
				switchSkills(activeChar, toAuraSkills);
				activeChar.sendSkillList();
				activeChar.sendMessage("You have succefully changed to target " + (toAuraSkills ? "aura" : "one") + " special skills.");
				return true;
			}
			activeChar.sendMessage("There is nothing to switch.");
			return false;
		}
		return true;
	}
	
	/**
	 * @param gmchar the player to switch the Game Master skills.
	 * @param toAuraSkills if {@code true} it will remove "GM Aura" skills and add "GM regular" skills, vice versa if {@code false}.
	 */
	public static void switchSkills(L2PcInstance gmchar, boolean toAuraSkills)
	{
		final Collection<Skill> skills = toAuraSkills ? SkillTreesData.getInstance().getGMSkillTree().values() : SkillTreesData.getInstance().getGMAuraSkillTree().values();
		for (Skill skill : skills)
		{
			gmchar.removeSkill(skill, false); // Don't Save GM skills to database
		}
		SkillTreesData.getInstance().addSkills(gmchar, toAuraSkills);
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	public static void showBuffs(L2PcInstance activeChar, L2Character target, int page, boolean passive)
	{
		final List<BuffInfo> effects = new ArrayList<>();
		if (!passive)
		{
			effects.addAll(target.getEffectList().getEffects());
		}
		else
		{
			effects.addAll(target.getEffectList().getPassives().values());
		}
		
		if ((page > ((effects.size() / PAGE_LIMIT) + 1)) || (page < 1))
		{
			return;
		}
		
		int max = effects.size() / PAGE_LIMIT;
		if (effects.size() > (PAGE_LIMIT * max))
		{
			max++;
		}
		
		final StringBuilder html = StringUtil.startAppend(500 + (effects.size() * 200), "<html><table width=\"100%\"><tr><td width=45><button value=\"Main\" action=\"bypass -h admin_admin\" width=45 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td><td width=180><center><font color=\"LEVEL\">Effects of ", target.getName(), "</font></td><td width=45><button value=\"Back\" action=\"bypass -h admin_current_player\" width=45 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td></tr></table><br><table width=\"100%\"><tr><td width=200>Skill</td><td width=30>Rem. Time</td><td width=70>Action</td></tr>");
		int start = ((page - 1) * PAGE_LIMIT);
		int end = Math.min(((page - 1) * PAGE_LIMIT) + PAGE_LIMIT, effects.size());
		int count = 0;
		for (BuffInfo info : effects)
		{
			if ((count >= start) && (count < end))
			{
				final Skill skill = info.getSkill();
				for (AbstractEffect effect : info.getEffects())
				{
					StringUtil.append(html, "<tr><td>", (!info.isInUse() ? FONT_RED1 : "") + skill.getName(), " Lv ", String.valueOf(skill.getLevel()), " (", effect.getClass().getSimpleName(), ")" + (!info.isInUse() ? FONT_RED2 : ""), "</td><td>", skill.isToggle() ? "T (" + info.getTickCount(effect) + ")" : skill.isPassive() ? "P" : info.getTime() + "s", "</td><td><button value=\"X\" action=\"bypass -h admin_stopbuff ", Integer.toString(target.getObjectId()), " ", String.valueOf(skill.getId()), "\" width=30 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td></tr>");
				}
			}
			count++;
		}
		
		html.append("</table><table width=300 bgcolor=444444><tr>");
		for (int x = 0; x < max; x++)
		{
			int pagenr = x + 1;
			if (page == pagenr)
			{
				html.append("<td>Page ");
				html.append(pagenr);
				html.append("</td>");
			}
			else
			{
				html.append("<td><a action=\"bypass -h admin_getbuffs" + (passive ? "_ps " : " "));
				html.append(target.getName());
				html.append(" ");
				html.append(x + 1);
				html.append("\"> Page ");
				html.append(pagenr);
				html.append(" </a></td>");
			}
		}
		
		html.append("</tr></table>");
		
		// Buttons
		StringUtil.append(html, "<br><center><button value=\"Refresh\" action=\"bypass -h admin_getbuffs", (passive ? "_ps " : " "), target.getName(), "\" width=80 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
		StringUtil.append(html, "<button value=\"Remove All\" action=\"bypass -h admin_stopallbuffs ", Integer.toString(target.getObjectId()), "\" width=80 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"><br>");
		// Legend
		if (!passive)
		{
			StringUtil.append(html, FONT_RED1, "Inactive buffs: ", String.valueOf(target.getEffectList().getHiddenBuffsCount()), FONT_RED2, "<br>");
		}
		StringUtil.append(html, "Total", passive ? " passive" : "", " buff count: ", String.valueOf(effects.size()));
		if ((target.getEffectList().getAllBlockedBuffSlots() != null) && !target.getEffectList().getAllBlockedBuffSlots().isEmpty())
		{
			StringUtil.append(html, "<br>Blocked buff slots: ");
			String slots = "";
			for (AbnormalType slot : target.getEffectList().getAllBlockedBuffSlots())
			{
				slots += slot.toString() + ", ";
			}
			
			if (!slots.isEmpty() && (slots.length() > 3))
			{
				StringUtil.append(html, slots.substring(0, slots.length() - 2));
			}
		}
		StringUtil.append(html, "</html>");
		// Send the packet
		activeChar.sendPacket(new NpcHtmlMessage(html.toString()));
		
		if (Config.GMAUDIT)
		{
			GMAudit.auditGMAction(activeChar.getName() + " [" + activeChar.getObjectId() + "]", "getbuffs", target.getName() + " (" + Integer.toString(target.getObjectId()) + ")", "");
		}
	}
	
	private static void removeBuff(L2PcInstance activeChar, int objId, int skillId)
	{
		L2Character target = null;
		try
		{
			target = (L2Character) L2World.getInstance().findObject(objId);
		}
		catch (Exception e)
		{
		}
		
		if ((target != null) && (skillId > 0))
		{
			if (target.isAffectedBySkill(skillId))
			{
				target.stopSkillEffects(true, skillId);
				activeChar.sendMessage("Removed skill ID: " + skillId + " effects from " + target.getName() + " (" + objId + ").");
			}
			
			showBuffs(activeChar, target, 1, false);
			if (Config.GMAUDIT)
			{
				GMAudit.auditGMAction(activeChar.getName() + " [" + activeChar.getObjectId() + "]", "stopbuff", target.getName() + " (" + objId + ")", Integer.toString(skillId));
			}
		}
	}
	
	private static void removeAllBuffs(L2PcInstance activeChar, int objId)
	{
		L2Character target = null;
		try
		{
			target = (L2Character) L2World.getInstance().findObject(objId);
		}
		catch (Exception e)
		{
		}
		
		if (target != null)
		{
			target.stopAllEffects();
			activeChar.sendMessage("Removed all effects from " + target.getName() + " (" + objId + ")");
			showBuffs(activeChar, target, 1, false);
			if (Config.GMAUDIT)
			{
				GMAudit.auditGMAction(activeChar.getName() + " [" + activeChar.getObjectId() + "]", "stopallbuffs", target.getName() + " (" + objId + ")", "");
			}
		}
	}
}