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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jserver.Config;
import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.datatables.CharNameTable;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.PunishmentManager;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.punishment.PunishmentAffect;
import com.l2jserver.gameserver.model.punishment.PunishmentTask;
import com.l2jserver.gameserver.model.punishment.PunishmentType;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.util.GMAudit;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class AdminPunishment implements IAdminCommandHandler
{
	private static final Logger _log = Logger.getLogger(AdminPunishment.class.getName());
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_punishment",
		"admin_punishment_add",
		"admin_punishment_remove",
		"admin_ban_acc",
		"admin_unban_acc",
		"admin_ban_chat",
		"admin_unban_chat",
		"admin_ban_char",
		"admin_unban_char",
		"admin_jail",
		"admin_unjail"
	};
	
	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		final StringTokenizer st = new StringTokenizer(command, " ");
		if (!st.hasMoreTokens())
		{
			return false;
		}
		final String cmd = st.nextToken();
		switch (cmd)
		{
			case "admin_punishment":
			{
				if (!st.hasMoreTokens())
				{
					String content = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/admin/punishment.htm");
					if (content != null)
					{
						content = content.replaceAll("%punishments%", Util.implode(PunishmentType.values(), ";"));
						content = content.replaceAll("%affects%", Util.implode(PunishmentAffect.values(), ";"));
						activeChar.sendPacket(new NpcHtmlMessage(0, 1, content));
					}
					else
					{
						_log.log(Level.WARNING, getClass().getSimpleName() + ": data/html/admin/punishment.htm is missing");
					}
				}
				else
				{
					final String subcmd = st.nextToken();
					switch (subcmd)
					{
						case "info":
						{
							String key = st.hasMoreTokens() ? st.nextToken() : null;
							String af = st.hasMoreTokens() ? st.nextToken() : null;
							String name = key;
							
							if ((key == null) || (af == null))
							{
								activeChar.sendMessage("Not enough data specified!");
								break;
							}
							final PunishmentAffect affect = PunishmentAffect.getByName(af);
							if (affect == null)
							{
								activeChar.sendMessage("Incorrect value specified for affect type!");
								break;
							}
							
							// Swap the name of the character with it's id.
							if (affect == PunishmentAffect.CHARACTER)
							{
								key = findCharId(key);
							}
							
							String content = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/admin/punishment-info.htm");
							if (content != null)
							{
								StringBuilder sb = new StringBuilder();
								for (PunishmentType type : PunishmentType.values())
								{
									if (PunishmentManager.getInstance().hasPunishment(key, affect, type))
									{
										long expiration = PunishmentManager.getInstance().getPunishmentExpiration(key, affect, type);
										String expire = "never";
										
										if (expiration > 0)
										{
											// Synchronize date formatter since its not thread safe.
											synchronized (DATE_FORMATTER)
											{
												expire = DATE_FORMATTER.format(new Date(expiration));
											}
										}
										sb.append("<tr><td><font color=\"LEVEL\">" + type + "</font></td><td>" + expire + "</td><td><a action=\"bypass -h admin_punishment_remove " + name + " " + affect + " " + type + "\">Remove</a></td></tr>");
									}
								}
								
								content = content.replaceAll("%player_name%", name);
								content = content.replaceAll("%punishments%", sb.toString());
								content = content.replaceAll("%affects%", Util.implode(PunishmentAffect.values(), ";"));
								content = content.replaceAll("%affect_type%", affect.name());
								activeChar.sendPacket(new NpcHtmlMessage(0, 1, content));
							}
							else
							{
								_log.log(Level.WARNING, getClass().getSimpleName() + ": data/html/admin/punishment-info.htm is missing");
							}
							break;
						}
						case "player":
						{
							L2PcInstance target = null;
							if (st.hasMoreTokens())
							{
								final String playerName = st.nextToken();
								if (playerName.isEmpty() && ((activeChar.getTarget() == null) || !activeChar.getTarget().isPlayer()))
								{
									return useAdminCommand("admin_punishment", activeChar);
								}
								target = L2World.getInstance().getPlayer(playerName);
							}
							if ((target == null) && ((activeChar.getTarget() == null) || !activeChar.getTarget().isPlayer()))
							{
								activeChar.sendMessage("You must target player!");
								break;
							}
							if (target == null)
							{
								target = activeChar.getTarget().getActingPlayer();
							}
							String content = HtmCache.getInstance().getHtm(activeChar.getHtmlPrefix(), "data/html/admin/punishment-player.htm");
							if (content != null)
							{
								content = content.replaceAll("%player_name%", target.getName());
								content = content.replaceAll("%punishments%", Util.implode(PunishmentType.values(), ";"));
								content = content.replaceAll("%acc%", target.getAccountName());
								content = content.replaceAll("%char%", target.getName());
								content = content.replaceAll("%ip%", target.getIPAddress());
								activeChar.sendPacket(new NpcHtmlMessage(0, 1, content));
							}
							else
							{
								_log.log(Level.WARNING, getClass().getSimpleName() + ": data/html/admin/punishment-player.htm is missing");
							}
							break;
						}
					}
				}
				break;
			}
			case "admin_punishment_add":
			{
				// Add new punishment
				String key = st.hasMoreTokens() ? st.nextToken() : null;
				String af = st.hasMoreTokens() ? st.nextToken() : null;
				String t = st.hasMoreTokens() ? st.nextToken() : null;
				String exp = st.hasMoreTokens() ? st.nextToken() : null;
				String reason = st.hasMoreTokens() ? st.nextToken() : null;
				
				// Let's grab the other part of the reason if there is..
				if (reason != null)
				{
					while (st.hasMoreTokens())
					{
						reason += " " + st.nextToken();
					}
					if (!reason.isEmpty())
					{
						reason = reason.replaceAll("\\$", "\\\\\\$");
						reason = reason.replaceAll("\r\n", "<br1>");
						reason = reason.replace("<", "&lt;");
						reason = reason.replace(">", "&gt;");
					}
				}
				
				String name = key;
				
				if ((key == null) || (af == null) || (t == null) || (exp == null) || (reason == null))
				{
					activeChar.sendMessage("Please fill all the fields!");
					break;
				}
				if (!Util.isDigit(exp) && !exp.equals("-1"))
				{
					activeChar.sendMessage("Incorrect value specified for expiration time!");
					break;
				}
				
				long expirationTime = Integer.parseInt(exp);
				if (expirationTime > 0)
				{
					expirationTime = System.currentTimeMillis() + (expirationTime * 60 * 1000);
				}
				
				final PunishmentAffect affect = PunishmentAffect.getByName(af);
				final PunishmentType type = PunishmentType.getByName(t);
				if ((affect == null) || (type == null))
				{
					activeChar.sendMessage("Incorrect value specified for affect/punishment type!");
					break;
				}
				
				// Swap the name of the character with it's id.
				if (affect == PunishmentAffect.CHARACTER)
				{
					key = findCharId(key);
				}
				else if (affect == PunishmentAffect.IP)
				{
					try
					{
						InetAddress addr = InetAddress.getByName(key);
						if (addr.isLoopbackAddress())
						{
							throw new UnknownHostException("You cannot ban any local address!");
						}
						else if (Config.GAME_SERVER_HOSTS.contains(addr.getHostAddress()))
						{
							throw new UnknownHostException("You cannot ban your gameserver's address!");
						}
					}
					catch (UnknownHostException e)
					{
						activeChar.sendMessage("You've entered an incorrect IP address!");
						activeChar.sendMessage(e.getMessage());
						break;
					}
				}
				
				// Check if we already put the same punishment on that guy ^^
				if (PunishmentManager.getInstance().hasPunishment(key, affect, type))
				{
					activeChar.sendMessage("Target is already affected by that punishment.");
					break;
				}
				
				// Punish him!
				PunishmentManager.getInstance().startPunishment(new PunishmentTask(key, affect, type, expirationTime, reason, activeChar.getName()));
				activeChar.sendMessage("Punishment " + type.name() + " have been applied to: " + affect + " " + name + "!");
				GMAudit.auditGMAction(activeChar.getName() + " [" + activeChar.getObjectId() + "]", cmd, affect.name(), name);
				return useAdminCommand("admin_punishment info " + name + " " + affect.name(), activeChar);
			}
			case "admin_punishment_remove":
			{
				// Remove punishment.
				String key = st.hasMoreTokens() ? st.nextToken() : null;
				String af = st.hasMoreTokens() ? st.nextToken() : null;
				String t = st.hasMoreTokens() ? st.nextToken() : null;
				String name = key;
				
				if ((key == null) || (af == null) || (t == null))
				{
					activeChar.sendMessage("Not enough data specified!");
					break;
				}
				
				final PunishmentAffect affect = PunishmentAffect.getByName(af);
				final PunishmentType type = PunishmentType.getByName(t);
				if ((affect == null) || (type == null))
				{
					activeChar.sendMessage("Incorrect value specified for affect/punishment type!");
					break;
				}
				
				// Swap the name of the character with it's id.
				if (affect == PunishmentAffect.CHARACTER)
				{
					key = findCharId(key);
				}
				
				if (!PunishmentManager.getInstance().hasPunishment(key, affect, type))
				{
					activeChar.sendMessage("Target is not affected by that punishment!");
					break;
				}
				
				PunishmentManager.getInstance().stopPunishment(key, affect, type);
				activeChar.sendMessage("Punishment " + type.name() + " have been stopped to: " + affect + " " + name + "!");
				GMAudit.auditGMAction(activeChar.getName() + " [" + activeChar.getObjectId() + "]", cmd, affect.name(), name);
				return useAdminCommand("admin_punishment info " + name + " " + affect.name(), activeChar);
			}
			case "admin_ban_char":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_add %s %s %s %s %s", st.nextToken(), PunishmentAffect.CHARACTER, PunishmentType.BAN, 0, "Banned by admin"), activeChar);
				}
			}
			case "admin_unban_char":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_remove %s %s %s", st.nextToken(), PunishmentAffect.CHARACTER, PunishmentType.BAN), activeChar);
				}
			}
			case "admin_ban_acc":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_add %s %s %s %s %s", st.nextToken(), PunishmentAffect.ACCOUNT, PunishmentType.BAN, 0, "Banned by admin"), activeChar);
				}
			}
			case "admin_unban_acc":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_remove %s %s %s", st.nextToken(), PunishmentAffect.ACCOUNT, PunishmentType.BAN), activeChar);
				}
			}
			case "admin_ban_chat":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_add %s %s %s %s %s", st.nextToken(), PunishmentAffect.CHARACTER, PunishmentType.CHAT_BAN, 0, "Chat banned by admin"), activeChar);
				}
			}
			case "admin_unban_chat":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_remove %s %s %s", st.nextToken(), PunishmentAffect.CHARACTER, PunishmentType.CHAT_BAN), activeChar);
				}
			}
			case "admin_jail":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_add %s %s %s %s %s", st.nextToken(), PunishmentAffect.CHARACTER, PunishmentType.JAIL, 0, "Jailed by admin"), activeChar);
				}
			}
			case "admin_unjail":
			{
				if (st.hasMoreTokens())
				{
					return useAdminCommand(String.format("admin_punishment_remove %s %s %s", st.nextToken(), PunishmentAffect.CHARACTER, PunishmentType.JAIL), activeChar);
				}
			}
		}
		return true;
	}
	
	private static final String findCharId(String key)
	{
		int charId = CharNameTable.getInstance().getIdByName(key);
		if (charId > 0) // Yeah its a char name!
		{
			return Integer.toString(charId);
		}
		return key;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}