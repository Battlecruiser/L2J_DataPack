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

import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2CastleWarehouseInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * Castle Warehouse - Blood Alliance
 * @author malyelfik
 */
public class BloodAlliance implements IBypassHandler
{

	private static final String[] COMMANDS = {
		"HonoraryItem",
		"Receive",
		"Exchange"
	};

	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
			return false;
		
		L2CastleWarehouseInstance npc = ((L2CastleWarehouseInstance)target);
		
		try
		{
			NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());
			StringTokenizer st = new StringTokenizer(command, " ");
			String actualCommand = st.nextToken(); // Get actual command
			
			if (actualCommand.equalsIgnoreCase(COMMANDS[0]))
			{
				if (activeChar.isClanLeader())
				{
					html.setFile(activeChar.getHtmlPrefix(), "data/html/castlewarehouse/castlewarehouse-4.htm");
					html.replace("%blood%", Integer.toString(npc.getCastle().getBloodAlliance()));
				}
				else
				{
					html.setFile(activeChar.getHtmlPrefix(), "data/html/castlewarehouse/castlewarehouse-3.htm");
				}
			}
			else if (actualCommand.equalsIgnoreCase(COMMANDS[1]))
			{
				int count = npc.getCastle().getBloodAlliance();
				if (count == 0)
				{
					html.setFile(activeChar.getHtmlPrefix(), "data/html/castlewarehouse/castlewarehouse-5.htm");
				}
				else
				{
					activeChar.addItem("BloodAlliance", 9911, count, activeChar, true);
					npc.getCastle().setBloodAlliance(0);
					html.setFile(activeChar.getHtmlPrefix(), "data/html/castlewarehouse/castlewarehouse-6.htm");
				}
			}
			else if (actualCommand.equalsIgnoreCase(COMMANDS[2]))
			{
				if (activeChar.getInventory().getInventoryItemCount(9911, -1) > 0)
				{
					activeChar.destroyItemByItemId("BloodAllianceExchange", 9911, 1, activeChar, true);
					activeChar.addItem("BloodAllianceExchange", 9910, 30, activeChar, true);
					html.setFile(activeChar.getHtmlPrefix(), "data/html/castlewarehouse/castlewarehouse-7.htm");
				}
				else
				{
					html.setFile(activeChar.getHtmlPrefix(), "data/html/castlewarehouse/castlewarehouse-8.htm");	
				}
				
			}
			html.replace("%objectId%", String.valueOf(npc.getObjectId()));
			activeChar.sendPacket(html);
			return true;
		}
		catch (Exception e)
		{
			_log.info("Exception in " + getClass().getSimpleName());
		}
		return false;
	}

	@Override
	public String[] getBypassList()
	{
		return COMMANDS ;
	}
}
