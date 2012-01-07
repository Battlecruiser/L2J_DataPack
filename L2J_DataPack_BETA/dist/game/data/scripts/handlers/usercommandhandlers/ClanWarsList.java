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
package handlers.usercommandhandlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.handler.IUserCommandHandler;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;


/**
 * Support for /clanwarlist command
 * @author Tempy
 */
public class ClanWarsList implements IUserCommandHandler
{
	private static final Logger _log = Logger.getLogger(ClanWarsList.class.getName());
	private static final int[] COMMAND_IDS =
	{
		88, 89, 90
	};
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IUserCommandHandler#useUserCommand(int, com.l2jserver.gameserver.model.actor.instance.L2PcInstance)
	 */
	@Override
	public boolean useUserCommand(int id, L2PcInstance activeChar)
	{
		if (id != COMMAND_IDS[0] && id != COMMAND_IDS[1] && id != COMMAND_IDS[2])
			return false;
		
		L2Clan clan = activeChar.getClan();
		
		if (clan == null)
		{
			activeChar.sendMessage("You are not in a clan.");
			return false;
		}
		
		SystemMessage sm;
		java.sql.Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement;
			
			if (id == 88)
			{
				// Attack List
				activeChar.sendPacket(SystemMessageId.CLANS_YOU_DECLARED_WAR_ON);
				statement = con.prepareStatement("select clan_name,clan_id,ally_id,ally_name from clan_data,clan_wars where clan1=? and clan_id=clan2 and clan2 not in (select clan1 from clan_wars where clan2=?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, clan.getClanId());
			}
			else if (id == 89)
			{
				// Under Attack List
				activeChar.sendPacket(SystemMessageId.CLANS_THAT_HAVE_DECLARED_WAR_ON_YOU);
				statement = con.prepareStatement("select clan_name,clan_id,ally_id,ally_name from clan_data,clan_wars where clan2=? and clan_id=clan1 and clan1 not in (select clan2 from clan_wars where clan1=?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, clan.getClanId());
			}
			else
				// ID = 90
			{
				// War List
				activeChar.sendPacket(SystemMessageId.WAR_LIST);
				statement = con.prepareStatement("select clan_name,clan_id,ally_id,ally_name from clan_data,clan_wars where clan1=? and clan_id=clan2 and clan2 in (select clan1 from clan_wars where clan2=?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, clan.getClanId());
			}
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				String clanName = rset.getString("clan_name");
				int ally_id = rset.getInt("ally_id");
				
				if (ally_id > 0)
				{
					// Target With Ally
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_S2_ALLIANCE);
					sm.addString(clanName);
					sm.addString(rset.getString("ally_name"));
				}
				else
				{
					// Target Without Ally
					sm = SystemMessage.getSystemMessage(SystemMessageId.S1_NO_ALLI_EXISTS);
					sm.addString(clanName);
				}
				
				activeChar.sendPacket(sm);
			}
			
			activeChar.sendPacket(SystemMessageId.FRIEND_LIST_FOOTER);
			
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
		
		return true;
	}
	
	/**
	 * 
	 * @see com.l2jserver.gameserver.handler.IUserCommandHandler#getUserCommandList()
	 */
	@Override
	public int[] getUserCommandList()
	{
		return COMMAND_IDS;
	}
}
