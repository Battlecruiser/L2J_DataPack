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
import com.l2jserver.gameserver.model.L2Multisell;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2TransformManagerInstance;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

public class Transform implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"transformskilllist",
		"buytransform"
	};

	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
			return false;

		if (command.toLowerCase().startsWith(COMMANDS[0])) // skills list
		{
			if (canTransform(activeChar))
			{
				activeChar.setSkillLearningClassId(activeChar.getClassId());
				L2TransformManagerInstance.showTransformSkillList(activeChar);
				return true;
			}
			else
			{
				NpcHtmlMessage html = new NpcHtmlMessage(((L2Npc)target).getObjectId());
				html.setFile(activeChar.getHtmlPrefix(), "data/html/default/" + ((L2Npc)target).getNpcId() + "-cantlearn.htm");
				activeChar.sendPacket(html);
			}
		}
		else if (command.toLowerCase().startsWith(COMMANDS[1])) 
		{
			if (canTransform(activeChar))
			{				
				L2Multisell.getInstance().separateAndSend(32323001, activeChar, ((L2Npc)target).getNpcId(), false, ((L2Npc)target).getCastle().getTaxRate());
				return true;
			}
			else
			{
				NpcHtmlMessage html = new NpcHtmlMessage(((L2Npc)target).getObjectId());
				html.setFile(activeChar.getHtmlPrefix(), "data/html/default/" + ((L2Npc)target).getNpcId() + "-cantbuy.htm");
				activeChar.sendPacket(html);
			}
		}
		return false;
	}

	private static boolean canTransform(L2PcInstance player)
	{
		QuestState st = player.getQuestState("136_MoreThanMeetsTheEye");
		if (st != null && st.getState() == State.COMPLETED)
			return true;

		return false;
	}

	public String[] getBypassList()
	{
		return COMMANDS;
	}
}