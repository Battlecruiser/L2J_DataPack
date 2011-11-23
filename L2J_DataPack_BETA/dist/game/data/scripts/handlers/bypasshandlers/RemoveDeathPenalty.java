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

import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.EtcStatusUpdate;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.util.StringUtil;

public class RemoveDeathPenalty implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"remove_dp"
	};
	
	private static final int[] pen_clear_price =
	{
		3600, 8640, 25200, 50400, 86400, 144000, 144000, 144000
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
		{
			return false;
		}
		
		try
		{
			final int cmdChoice = Integer.parseInt(command.substring(10, 11).trim());
			final L2Npc npc = (L2Npc) target;
			switch (cmdChoice)
			{
				case 1:
					String filename = "data/html/default/30981-1.htm";
					NpcHtmlMessage html = new NpcHtmlMessage(npc.getObjectId());
					html.setFile(activeChar.getHtmlPrefix(), filename);
					html.replace("%objectId%", String.valueOf(npc.getObjectId()));
					html.replace("%dp_price%", String.valueOf(pen_clear_price[activeChar.getExpertiseLevel()]));
					activeChar.sendPacket(html);
					break;
				case 2:
					NpcHtmlMessage Reply = new NpcHtmlMessage(npc.getObjectId());
					final StringBuilder replyMSG = StringUtil.startAppend(400, "<html><body>Black Judge:<br>");
					
					if (activeChar.getDeathPenaltyBuffLevel() > 0)
					{
						if (activeChar.getAdena() >= pen_clear_price[activeChar.getExpertiseLevel()])
						{
							if (!activeChar.reduceAdena("DeathPenality", pen_clear_price[activeChar.getExpertiseLevel()], npc, true))
							{
								return false;
							}
							activeChar.setDeathPenaltyBuffLevel(activeChar.getDeathPenaltyBuffLevel() - 1);
							activeChar.sendPacket(SystemMessageId.DEATH_PENALTY_LIFTED);
							activeChar.sendPacket(new EtcStatusUpdate(activeChar));
							return true;
						}
						else
						{
							replyMSG.append("The wound you have received from death's touch is too deep to be healed for the money you have to give me. Find more money if you wish death's mark to be fully removed from you.");
						}
					}
					else
					{
						replyMSG.append("You have no more death wounds that require healing.<br>" + "Go forth and fight, both for this world and your own glory.");
					}
					
					replyMSG.append("</body></html>");
					Reply.setHtml(replyMSG.toString());
					activeChar.sendPacket(Reply);
					break;
			}
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
		return COMMANDS;
	}
}
