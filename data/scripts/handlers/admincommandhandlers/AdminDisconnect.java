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
package handlers.admincommandhandlers;

import net.sf.l2j.gameserver.communitybbs.Manager.RegionBBSManager;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.network.serverpackets.LeaveWorld;

/**
 * This class handles following admin commands:
 * - character_disconnect = disconnects target player
 *
 * @version $Revision: 1.2.4.4 $ $Date: 2005/04/11 10:06:00 $
 */
public class AdminDisconnect implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_character_disconnect"
	};
	
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_character_disconnect"))
		{
			disconnectCharacter(activeChar);
		}
		return true;
	}
	
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private void disconnectCharacter(L2PcInstance activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		if (target instanceof L2PcInstance)
			player = (L2PcInstance) target;
		else
			return;
		
		if (player == activeChar)
		{
			activeChar.sendMessage("You cannot logout your own character.");
		}
		else
		{
			activeChar.sendMessage("Character " + player.getName() + " disconnected from server.");
			
			//Logout Character
			LeaveWorld ql = new LeaveWorld();
			player.sendPacket(ql);
			
			RegionBBSManager.getInstance().changeCommunityBoard();
			
			player.closeNetConnection();
		}
	}
}
