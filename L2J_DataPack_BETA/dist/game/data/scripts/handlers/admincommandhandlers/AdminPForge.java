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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.StringTokenizer;

import org.mmocore.network.NioNetStringBuffer;

import com.l2jserver.gameserver.GameServer;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.clientpackets.L2GameClientPacket;
import com.l2jserver.gameserver.network.serverpackets.AdminForgePacket;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.util.StringUtil;


/**
 * This class handles commands for gm to forge packets
 *
 * @author Maktakien
 *
 */
public class AdminPForge implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_forge",
		"admin_forge2",
		"admin_forge3"
	};
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.equals("admin_forge"))
		{
			showMainPage(activeChar);
		}
		else if (command.startsWith("admin_forge2"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command);
				st.nextToken();
				String format = st.nextToken();
				showPage2(activeChar, format);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				activeChar.sendMessage("Usage: //forge2 format");
			}
		}
		else if (command.startsWith("admin_forge3"))
		{
			try
			{
				StringTokenizer st = new StringTokenizer(command);
				st.nextToken();
				String format = st.nextToken();
				boolean broadcast = false;
				boolean client = false;
				ByteBuffer buf = null;
				if (format.toLowerCase().equals("broadcast"))
				{
					format = st.nextToken();
					broadcast = true;
				}
				else if (format.toLowerCase().equals("client"))
				{
					format = st.nextToken();
					client = true;
					buf = ByteBuffer.allocate(65533);
				}
				AdminForgePacket sp = new AdminForgePacket();
				for (int i = 0; i < format.length(); i++)
				{
					String val = st.nextToken();
					if (val.toLowerCase().equals("$objid"))
					{
						val = String.valueOf(activeChar.getObjectId());
					}
					else if (val.toLowerCase().equals("$tobjid"))
					{
						val = String.valueOf(activeChar.getTarget().getObjectId());
					}
					else if (val.toLowerCase().equals("$bobjid"))
					{
						if (activeChar.getBoat() != null)
						{
							val = String.valueOf(activeChar.getBoat().getObjectId());
						}
					}
					else if (val.toLowerCase().equals("$clanid"))
					{
						val = String.valueOf(activeChar.getClanId());
					}
					else if (val.toLowerCase().equals("$allyid"))
					{
						val = String.valueOf(activeChar.getAllyId());
					}
					else if (val.toLowerCase().equals("$tclanid"))
					{
						val = String.valueOf(((L2PcInstance) activeChar.getTarget()).getClanId());
					}
					else if (val.toLowerCase().equals("$tallyid"))
					{
						val = String.valueOf(((L2PcInstance) activeChar.getTarget()).getAllyId());
					}
					else if (val.toLowerCase().equals("$x"))
					{
						val = String.valueOf(activeChar.getX());
					}
					else if (val.toLowerCase().equals("$y"))
					{
						val = String.valueOf(activeChar.getY());
					}
					else if (val.toLowerCase().equals("$z"))
					{
						val = String.valueOf(activeChar.getZ());
					}
					else if (val.toLowerCase().equals("$heading"))
					{
						val = String.valueOf(activeChar.getHeading());
					}
					else if (val.toLowerCase().equals("$tx"))
					{
						val = String.valueOf(activeChar.getTarget().getX());
					}
					else if (val.toLowerCase().equals("$ty"))
					{
						val = String.valueOf(activeChar.getTarget().getY());
					}
					else if (val.toLowerCase().equals("$tz"))
					{
						val = String.valueOf(activeChar.getTarget().getZ());
					}
					else if (val.toLowerCase().equals("$theading"))
					{
						val = String.valueOf(((L2PcInstance) activeChar.getTarget()).getHeading());
					}
					
					if (!client)
						sp.addPart(format.getBytes()[i], val);
					else
						write(format.getBytes()[i], val, buf);
				}
				if (broadcast)
				{
					activeChar.broadcastPacket(sp);
				}
				else if (client)
				{
					if (buf != null)
					{
						buf.flip();
						L2GameClientPacket p = (L2GameClientPacket) GameServer.gameServer.getL2GamePacketHandler().handlePacket(buf, activeChar.getClient());
						if (p != null)
						{
							p.setBuffers(buf, activeChar.getClient(), new NioNetStringBuffer(2000));
							if (p.read())
								ThreadPoolManager.getInstance().executePacket(p);
						}
					}
				}
				else
				{
					activeChar.sendPacket(sp);
				}
				showPage3(activeChar, format, command);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return true;
	}
	
	private void showMainPage(L2PcInstance activeChar)
	{
		AdminHelpPage.showHelpPage(activeChar, "pforge1.htm");
	}
	
	private void showPage2(L2PcInstance activeChar, String format)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/pforge2.htm");
		adminReply.replace("%format%", format);
		
		final StringBuilder replyMSG =
			new StringBuilder(format.length() * 40);
		
		for (int i = 0; i < format.length(); i++)
			StringUtil.append(replyMSG,
					String.valueOf(format.charAt(i)),
					" : <edit var=\"v",
					String.valueOf(i),
			"\" width=100><br1>");
		adminReply.replace("%valueditors%", replyMSG.toString());
		replyMSG.setLength(0);
		
		for (int i = 0; i < format.length(); i++)
			replyMSG.append(" $v" + i);
		adminReply.replace("%send%", replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
	
	private void showPage3(L2PcInstance activeChar, String format, String command)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setFile(activeChar.getHtmlPrefix(), "data/html/admin/pforge3.htm");
		adminReply.replace("%format%", format);
		adminReply.replace("%command%", command);
		activeChar.sendPacket(adminReply);
	}
	
	
	private boolean write(byte b, String string, ByteBuffer buf)
	{
		if((b == 'C')||(b == 'c'))
		{
			buf.put(Byte.decode(string));
			return true;
		}
		else if((b == 'D')||(b == 'd'))
		{
			buf.putInt(Integer.decode(string));
			return true;
		}
		else if((b == 'H')||(b == 'h'))
		{
			buf.putShort(Short.decode(string));
			return true;
		}
		else if((b == 'F')||(b == 'f'))
		{
			buf.putDouble(Double.parseDouble(string));
			return true;
		}
		else if((b == 'S')||(b == 's'))
		{
			final int len = string.length();
			for (int i = 0; i < len; i++)
			{
				buf.putChar(string.charAt(i));
			}
			buf.putChar('\000');
			return true;
		}
		else if((b == 'B')||(b == 'b')||(b == 'X')||(b == 'x'))
		{
			buf.put(new BigInteger(string).toByteArray());
			return true;
		}
		else if ((b == 'Q') || (b == 'q'))
		{
			buf.putLong(Long.decode(string));
			return true;
		}
		return false;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
