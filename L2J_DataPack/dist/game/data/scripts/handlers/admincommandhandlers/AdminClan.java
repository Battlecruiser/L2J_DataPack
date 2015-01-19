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

import java.util.StringTokenizer;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.data.sql.impl.ClanTable;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.CastleManager;
import com.l2jserver.gameserver.instancemanager.ClanHallManager;
import com.l2jserver.gameserver.instancemanager.FortManager;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2ClanMember;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid, Zoey76
 */
public class AdminClan implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_clan_info",
		"admin_clan_changeleader",
		"admin_clan_show_pending",
		"admin_clan_force_pending"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		final StringTokenizer st = new StringTokenizer(command);
		final String cmd = st.nextToken();
		switch (cmd)
		{
			case "admin_clan_info":
			{
				final L2PcInstance player = getPlayer(activeChar, st);
				if (player == null)
				{
					break;
				}
				
				final L2Clan clan = player.getClan();
				if (clan == null)
				{
					activeChar.sendPacket(SystemMessageId.TARGET_MUST_BE_IN_CLAN);
					return false;
				}
				
				final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
				html.setHtml(HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/admin/claninfo.htm"));
				html.replace("%clan_name%", clan.getName());
				html.replace("%clan_leader%", clan.getLeaderName());
				html.replace("%clan_level%", String.valueOf(clan.getLevel()));
				html.replace("%clan_has_castle%", clan.getCastleId() > 0 ? CastleManager.getInstance().getCastleById(clan.getCastleId()).getName() : "No");
				html.replace("%clan_has_clanhall%", clan.getHideoutId() > 0 ? ClanHallManager.getInstance().getClanHallById(clan.getHideoutId()).getName() : "No");
				html.replace("%clan_has_fortress%", clan.getFortId() > 0 ? FortManager.getInstance().getFortById(clan.getFortId()).getName() : "No");
				html.replace("%clan_points%", String.valueOf(clan.getReputationScore()));
				html.replace("%clan_players_count%", String.valueOf(clan.getMembersCount()));
				html.replace("%clan_ally%", clan.getAllyId() > 0 ? clan.getAllyName() : "Not in ally");
				html.replace("%current_player_objectId%", String.valueOf(player.getObjectId()));
				html.replace("%current_player_name%", player.getName());
				activeChar.sendPacket(html);
				break;
			}
			case "admin_clan_changeleader":
			{
				final L2PcInstance player = getPlayer(activeChar, st);
				if (player == null)
				{
					break;
				}
				
				final L2Clan clan = player.getClan();
				if (clan == null)
				{
					activeChar.sendPacket(SystemMessageId.TARGET_MUST_BE_IN_CLAN);
					return false;
				}
				
				final L2ClanMember member = clan.getClanMember(player.getObjectId());
				if (member != null)
				{
					if (player.isAcademyMember())
					{
						player.sendPacket(SystemMessageId.RIGHT_CANT_TRANSFERRED_TO_ACADEMY_MEMBER);
					}
					else
					{
						clan.setNewLeader(member);
					}
				}
				break;
			}
			case "admin_clan_show_pending":
			{
				final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
				html.setHtml(HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/admin/clanchanges.htm"));
				StringBuilder sb = new StringBuilder();
				for (L2Clan clan : ClanTable.getInstance().getClans())
				{
					if (clan.getNewLeaderId() != 0)
					{
						sb.append("<tr>");
						sb.append("<td>" + clan.getName() + "</td>");
						sb.append("<td>" + clan.getNewLeaderName() + "</td>");
						sb.append("<td><a action=\"bypass -h admin_clan_force_pending " + clan.getId() + "\">Force</a></td>");
						sb.append("</tr>");
					}
				}
				html.replace("%data%", sb.toString());
				activeChar.sendPacket(html);
				break;
			}
			case "admin_clan_force_pending":
			{
				if (st.hasMoreElements())
				{
					String token = st.nextToken();
					if (!Util.isDigit(token))
					{
						break;
					}
					int clanId = Integer.parseInt(token);
					
					final L2Clan clan = ClanTable.getInstance().getClan(clanId);
					if (clan == null)
					{
						break;
					}
					
					final L2ClanMember member = clan.getClanMember(clan.getNewLeaderId());
					if (member == null)
					{
						break;
					}
					
					clan.setNewLeader(member);
					activeChar.sendMessage("Task have been forcely executed.");
					break;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param activeChar
	 * @param st
	 * @return
	 */
	private L2PcInstance getPlayer(L2PcInstance activeChar, StringTokenizer st)
	{
		String val;
		L2PcInstance player = null;
		if (st.hasMoreTokens())
		{
			val = st.nextToken();
			// From the HTML we receive player's object Id.
			if (Util.isDigit(val))
			{
				player = L2World.getInstance().getPlayer(Integer.parseInt(val));
				if (player == null)
				{
					activeChar.sendPacket(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
					return null;
				}
			}
			else
			{
				player = L2World.getInstance().getPlayer(val);
				if (player == null)
				{
					activeChar.sendPacket(SystemMessageId.INCORRECT_NAME_TRY_AGAIN);
					return null;
				}
			}
		}
		else
		{
			L2Object targetObj = activeChar.getTarget();
			if (targetObj instanceof L2PcInstance)
			{
				player = targetObj.getActingPlayer();
			}
			else
			{
				activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				return null;
			}
		}
		return player;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
