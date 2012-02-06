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
package handlers.voicedcommandhandlers;

import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.util.Util;

/**
 * Fixed by Zoey76.
 */
public class set implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS =
	{
		"set name",
		"set home",
		"set group"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String params)
	{
		if (command.startsWith("set privileges"))
		{
			final String val = command.substring(15);
			final L2Object target = activeChar.getTarget();
			if (!Util.isDigit(val) || (target == null) || !target.isPlayer())
			{
				return false;
			}
			
			final int n = Integer.parseInt(val);
			final L2PcInstance player = target.getActingPlayer();
			if ((activeChar.getClan() == null) || (player.getClan() == null) || (activeChar.getClan().getClanId() != player.getClan().getClanId()) || !((activeChar.getClanPrivileges() > n) || activeChar.isClanLeader()))
			{
				return false;
			}
			
			player.setClanPrivileges(n);
			activeChar.sendMessage("Your clan privileges have been set to " + n + " by " + activeChar.getName() + ".");
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}
