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

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.handler.IBypassHandler;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author Xaras2
 */
public class ArenaBuff implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"ArenaBuffs", 
		"HPRecovery",
		"CPRecovery"
	};
	
	private final int[][] _Buffs =
	{
		{ // Fighter Buffs
			6803, 6804, 6808, 6809, 6811, 6812
		},
		{ // Mage Buffs
			6804, 6805, 6806, 6807, 6812
		}
	};
	
	@Override
	public boolean useBypass(String command, L2PcInstance activeChar, L2Character target)
	{
		if (!(target instanceof L2Npc))
		{
			return false;
		}
		
		final L2Npc npc = (L2Npc) target;
		final StringTokenizer st = new StringTokenizer(command);
		try
		{
			String cmd = st.nextToken();
			
			if (cmd.equalsIgnoreCase(COMMANDS[0]))
			{	
				if (!activeChar.reduceAdena("ArenaBuffs", 2000, activeChar.getLastFolkNPC(), true))
				{
					return false;
				}
				
				for (int skillId : _Buffs[activeChar.isMageClass() ? 1 : 0])
				{
					L2Skill skill = SkillTable.getInstance().getInfo(skillId, 1);
					
					if (skill != null)
					{
						npc.setTarget(activeChar);
						npc.doCast(skill);
					}
				}
				return true;
			}
			else if (cmd.equalsIgnoreCase(COMMANDS[1]))
			{
				if (activeChar.isInsideZone(L2Character.ZONE_PVP)) // Cannot be used while inside the pvp zone
				{
					return false;
				}
				else if (!activeChar.reduceAdena("RestoreHP", 1000, activeChar.getLastFolkNPC(), true))
				{
					return false;
				}
				
				L2Skill skill = SkillTable.getInstance().getInfo(6817, 1);
				if (skill != null)
				{
					npc.setTarget(activeChar);
					npc.doCast(skill);
				}
				return true;
			}
			else if (cmd.equalsIgnoreCase(COMMANDS[2]))
			{
				if (activeChar.isInsideZone(L2Character.ZONE_PVP)) // Cannot be used while inside the pvp zone
				{
					return false;
				}
				else if (!activeChar.reduceAdena("RestoreCP", 1000, activeChar.getLastFolkNPC(), true))
				{
					return false;
				}
				
				L2Skill skill = SkillTable.getInstance().getInfo(4380, 1);
				if (skill != null)
				{
					npc.setTarget(activeChar);
					npc.doCast(skill);
				}
				return true;
			}
		}
		catch (Exception e)
		{
			_log.log(Level.WARNING, "Exception in " + getClass().getSimpleName(), e);
		}
		return false;
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}
