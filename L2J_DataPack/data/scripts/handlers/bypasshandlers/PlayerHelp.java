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

import java.util.StringTokenizer;
import java.util.logging.Level;

import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

public class PlayerHelp implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"player_help"
	};

	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		try
		{
			if (command.length() < 13)
				return false;

			final String path = command.substring(12);
			if (path.indexOf("..") != -1)
				return false;

			final StringTokenizer st = new StringTokenizer(path);
			final String[] cmd = st.nextToken().split("#");
			
			NpcHtmlMessage html;
			if (cmd.length > 1)
			{
				final int itemId = Integer.parseInt(cmd[1]);
				html = new NpcHtmlMessage(1,itemId);
			}
			else
				html = new NpcHtmlMessage(1);

			html.setFile(activeChar.getHtmlPrefix(), "data/html/help/"+cmd[0]);
			html.disableValidation();
			activeChar.sendPacket(html);
		}
		catch (Exception e)
		{
			_log.log(Level.INFO, "Exception in " + e.getMessage(), e);
		}
		return true;
	}

	public String[] getBypassList()
	{
		return COMMANDS;
	}
}