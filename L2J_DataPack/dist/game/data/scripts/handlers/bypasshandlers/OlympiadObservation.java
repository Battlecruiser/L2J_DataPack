/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.bypasshandlers;

import java.util.logging.Level;

import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2OlympiadManagerInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.model.olympiad.Olympiad;
import com.l2jserver.gameserver.model.olympiad.OlympiadGameManager;
import com.l2jserver.gameserver.model.olympiad.OlympiadGameTask;
import com.l2jserver.gameserver.model.olympiad.OlympiadManager;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.util.StringUtil;

/**
 * 
 * @author DS
 *
 */
public class OlympiadObservation implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"arenalist",
		"arenachange"
	};

	public final boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		try
		{
			final boolean isManager = target instanceof L2OlympiadManagerInstance;
			if (!isManager)
			{
				// without npc command can be used only in observer mode on arena
				if (!activeChar.inObserverMode()
						|| activeChar.isInOlympiadMode()
						|| activeChar.getOlympiadGameId() < 0)
					return false;
			}

			if (command.startsWith(COMMANDS[0])) //list
			{
				NpcHtmlMessage message = new NpcHtmlMessage(0);
				if (command.length() < 10)
				{
					if (isManager)
					{
						message.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "olympiad_observe.htm");
						message.replace("%objectId%", String.valueOf(target.getObjectId()));
					}
					else
						message.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "olympiad_arena_observe.htm");

					activeChar.sendPacket(message);
					return true;
				}

				final int firstArena, lastArena;
				switch (Integer.parseInt(command.substring(10).trim()))
				{
					case 1:
						firstArena = 0;
						lastArena = 21;
						break;
					case 2:
						firstArena = 22;
						lastArena = 43;
						break;
					case 3:
						firstArena = 44;
						lastArena = 65;
						break;
					case 4:
						firstArena = 66;
						lastArena = 87;
						break;
					default:
						return false;
				}

				StringBuilder list = new StringBuilder(3500);
				OlympiadGameTask task;

				if (isManager)
				{
					message.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "olympiad_observe_list.htm");
					for (int i = firstArena; i <= lastArena; i++)
					{
						task = OlympiadGameManager.getInstance().getOlympiadTask(i);
						if (task != null)
						{
							StringUtil.append(list,
									"<a action=\"bypass -h npc_%objectId%_arenachange ",
									String.valueOf(i),
									"\">Arena ",
									String.valueOf(i + 1),
									"&nbsp;&nbsp;&nbsp;");

							if (task.isGameStarted())
							{
								if (task.isBattleStarted())
									StringUtil.append(list, "&$1719;"); // Playing
								else
									StringUtil.append(list, "&$1718;"); // Standby

								StringUtil.append(list,
										"&nbsp;&nbsp;&nbsp;",
										task.getGame().getPlayerNames()[0],
										"&nbsp; : &nbsp;",
										task.getGame().getPlayerNames()[1]);
							}
							else
							{
								StringUtil.append(list, "&$906;", // Initial State
										"</td><td>&nbsp;");
							}

							StringUtil.append(list, "</a><br>");
						}
					}
					message.replace("%list%", list.toString());
					message.replace("%objectId%", String.valueOf(target.getObjectId()));
				}
				else
				{
					message.setFile(activeChar.getHtmlPrefix(), Olympiad.OLYMPIAD_HTML_PATH + "olympiad_arena_observe_list.htm");					
					for (int i = firstArena; i <= lastArena; i++)
					{
						task = OlympiadGameManager.getInstance().getOlympiadTask(i);
						if (task != null)
						{
							StringUtil.append(list,
									"<tr><td fixwidth=30><a action=\"bypass arenachange ",
									String.valueOf(i),
									"\">",
									String.valueOf(i + 1),
									"</a></td><td fixwidth=60>");

							if (task.isGameStarted())
							{
								if (task.isBattleStarted())
									StringUtil.append(list, "&$1719;"); // Playing
								else
									StringUtil.append(list, "&$1718;"); // Standby

								StringUtil.append(list,
										"</td><td>",
										task.getGame().getPlayerNames()[0],
										"&nbsp;",
										task.getGame().getPlayerNames()[1]);
							}
							else
							{
								StringUtil.append(list, "&$906;", // Initial State
										"</td><td>&nbsp;");
							}

							StringUtil.append(list, "</td><td><font color=\"aaccff\"></font></td></tr>");
						}
					}
				}

				for (int i = firstArena; i <= lastArena; i++)
				{
					task = OlympiadGameManager.getInstance().getOlympiadTask(i);
					if (task != null)
					{
						StringUtil.append(list,
								"<tr><td fixwidth=30><a action=\"bypass arenachange ",
								String.valueOf(i),
								"\">",
								String.valueOf(i + 1),
								"</a></td><td fixwidth=60>");

						if (task.isGameStarted())
						{
							if (task.isBattleStarted())
								StringUtil.append(list, "&$1719;"); // Playing
							else
								StringUtil.append(list, "&$1718;"); // Standby

							StringUtil.append(list,
									"</td><td>",
									task.getGame().getPlayerNames()[0],
									"&nbsp;",
									task.getGame().getPlayerNames()[1]);
						}
						else
						{
							StringUtil.append(list, "&$906;", // Initial State
									"</td><td>&nbsp;");
						}

						StringUtil.append(list,
								"</td><td><font color=\"aaccff\"></font></td></tr>");
					}
					message.replace("%list%", list.toString());
				}

				activeChar.sendPacket(message);
				return true;
			}
			else //change
			{
				if (isManager)
				{
					if (OlympiadManager.getInstance().isRegisteredInComp(activeChar))
					{
						activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.WHILE_YOU_ARE_ON_THE_WAITING_LIST_YOU_ARE_NOT_ALLOWED_TO_WATCH_THE_GAME));
						return false;
					}
					if (!TvTEvent.isInactive() && TvTEvent.isPlayerParticipant(activeChar.getObjectId()))
					{
						activeChar.sendMessage("You can not observe games while registered for TvT");
						return false;
					}
				}

				final int arenaId = Integer.parseInt(command.substring(12).trim());
				final OlympiadGameTask nextArena = OlympiadGameManager.getInstance().getOlympiadTask(arenaId);
				if (nextArena != null)
				{
					activeChar.enterOlympiadObserverMode(nextArena.getZone().getSpawns().get(0), arenaId);
					activeChar.setInstanceId(OlympiadGameManager.getInstance().getOlympiadTask(arenaId).getZone().getInstanceId());
					return true;
				}
			}
		}
		catch (Exception e)
		{
			_log.log(Level.INFO, "Exception in " + e.getMessage(), e);
		}
		return false;
	}

	public final String[] getBypassList()
	{
		return COMMANDS;
	}
}