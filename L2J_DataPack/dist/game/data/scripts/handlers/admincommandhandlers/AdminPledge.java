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

import com.l2jserver.gameserver.datatables.ClanTable;
import com.l2jserver.gameserver.enums.UserInfoType;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.GMViewPledgeInfo;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * <B>Pledge Manipulation:</B><BR>
 * <LI>With target in a character without clan:<BR>
 * //pledge create clanname <LI>With target in a clan leader:<BR>
 * //pledge info<BR>
 * //pledge dismiss<BR>
 * //pledge setlevel level<BR>
 * //pledge rep reputation_points<BR>
 */
public class AdminPledge implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_pledge"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		final StringTokenizer st = new StringTokenizer(command);
		final String cmd = st.nextToken();
		final L2Object target = activeChar.getTarget();
		final L2PcInstance targetPlayer = target instanceof L2PcInstance ? (L2PcInstance) target : null;
		L2Clan clan = targetPlayer != null ? targetPlayer.getClan() : null;
		if (targetPlayer == null)
		{
			activeChar.sendPacket(SystemMessageId.INVALID_TARGET);
			showMainPage(activeChar);
			return false;
		}
		switch (cmd)
		{
			case "admin_pledge":
			{
				if (!st.hasMoreTokens())
				{
					activeChar.sendMessage("Missing parameters!");
					break;
				}
				final String action = st.nextToken();
				if (!st.hasMoreTokens())
				{
					activeChar.sendMessage("Missing parameters!");
					break;
				}
				final String param = st.nextToken();
				
				switch (action)
				{
					case "create":
					{
						if (clan != null)
						{
							activeChar.sendMessage("Target player has clan!");
							break;
						}
						
						final long penalty = targetPlayer.getClanCreateExpiryTime();
						targetPlayer.setClanCreateExpiryTime(0);
						clan = ClanTable.getInstance().createClan(targetPlayer, param);
						if (clan != null)
						{
							activeChar.sendMessage("Clan " + param + " created. Leader: " + targetPlayer.getName());
						}
						else
						{
							targetPlayer.setClanCreateExpiryTime(penalty);
							activeChar.sendMessage("There was a problem while creating the clan.");
						}
						break;
					}
					case "dismiss":
					{
						if (clan == null)
						{
							activeChar.sendMessage("Target player has no clan!");
							break;
						}
						
						if (!targetPlayer.isClanLeader())
						{
							SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_IS_NOT_A_CLAN_LEADER);
							sm.addCharName(targetPlayer);
							activeChar.sendPacket(sm);
							showMainPage(activeChar);
							return false;
						}
						
						ClanTable.getInstance().destroyClan(targetPlayer.getClanId());
						clan = targetPlayer.getClan();
						if (clan == null)
						{
							activeChar.sendMessage("Clan disbanded.");
						}
						else
						{
							activeChar.sendMessage("There was a problem while destroying the clan.");
						}
						break;
					}
					case "info":
					{
						if (clan == null)
						{
							activeChar.sendMessage("Target player has no clan!");
							break;
						}
						
						activeChar.sendPacket(new GMViewPledgeInfo(clan, targetPlayer));
						break;
					}
					case "setlevel":
					{
						if (clan == null)
						{
							activeChar.sendMessage("Target player has no clan!");
							break;
						}
						else if (param == null)
						{
							activeChar.sendMessage("Usage: //pledge <setlevel|rep> <number>");
							break;
						}
						
						int level = Integer.parseInt(param);
						if ((level >= 0) && (level < 12))
						{
							clan.changeLevel(level);
							for (L2PcInstance member : clan.getOnlineMembers(0))
							{
								member.broadcastUserInfo(UserInfoType.RELATION, UserInfoType.CLAN);
							}
							activeChar.sendMessage("You set level " + level + " for clan " + clan.getName());
						}
						else
						{
							activeChar.sendMessage("Level incorrect.");
						}
						break;
					}
					case "rep":
					{
						if (clan == null)
						{
							activeChar.sendMessage("Target player has no clan!");
							break;
						}
						else if (clan.getLevel() < 5)
						{
							activeChar.sendMessage("Only clans of level 5 or above may receive reputation points.");
							showMainPage(activeChar);
							return false;
						}
						
						try
						{
							final int points = Integer.parseInt(param);
							clan.addReputationScore(points, true);
							activeChar.sendMessage("You " + (points > 0 ? "add " : "remove ") + Math.abs(points) + " points " + (points > 0 ? "to " : "from ") + clan.getName() + "'s reputation. Their current score is " + clan.getReputationScore());
						}
						catch (Exception e)
						{
							activeChar.sendMessage("Usage: //pledge <rep> <number>");
						}
						break;
					}
				}
				break;
			}
		}
		showMainPage(activeChar);
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void showMainPage(L2PcInstance activeChar)
	{
		AdminHtml.showAdminHtml(activeChar, "game_menu.htm");
	}
	
}
