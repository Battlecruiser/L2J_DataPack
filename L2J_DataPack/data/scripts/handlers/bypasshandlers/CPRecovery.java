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

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;

public class CPRecovery implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"CPRecovery"
	};

	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
			return false;

		final L2Npc npc = (L2Npc) target; 

		if (npc.getNpcId() != 31225 && npc.getNpcId() != 31226)
			return false;

		if (activeChar.isCursedWeaponEquipped())
		{
			activeChar.sendMessage("Go away, you're not welcome here.");
			activeChar.sendPacket(ActionFailed.STATIC_PACKET);
			return true;
		}

		if (!activeChar.reduceAdena("RestoreCP", 100, activeChar.getLastFolkNPC(), true))
			return false;

		L2Skill skill = SkillTable.getInstance().getInfo(4380, 1);
		if (skill != null)
		{
			npc.setTarget(activeChar);
			npc.doCast(skill);
		}
		activeChar.sendPacket(ActionFailed.STATIC_PACKET);
		return true;
	}

	public String[] getBypassList()
	{
		return COMMANDS;
	}
}