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
package handlers.skillhandlers;

import net.sf.l2j.gameserver.handler.ISkillHandler;
import net.sf.l2j.gameserver.instancemanager.FortManager;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.Fort;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;
import net.sf.l2j.gameserver.util.Util;

/**
 * @author _drunk_
 *
 */
public class TakeFort implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.TAKEFORT
	};
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.ISkillHandler#useSkill(net.sf.l2j.gameserver.model.actor.L2Character, net.sf.l2j.gameserver.model.L2Skill, net.sf.l2j.gameserver.model.L2Object[])
	 */
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		if (!(activeChar instanceof L2PcInstance))
			return;
		
		L2PcInstance player = (L2PcInstance) activeChar;
		L2Object target = player.getTarget();
		if (player.getClan() == null)
			return;
		
		Fort fort = FortManager.getInstance().getFort(player);
		if (fort == null || !checkIfOkToCastFlagDisplay(player, fort, true, skill, target))
			return;
		
		try
		{
			fort.EndOfSiege(player.getClan());
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * 
	 * @see net.sf.l2j.gameserver.handler.ISkillHandler#getSkillIds()
	 */
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
	
	/**
	 * Return true if character clan place a flag<BR><BR>
	 *
	 * @param activeChar The L2Character of the character placing the flag
	 *
	 */
	public static boolean checkIfOkToCastFlagDisplay(L2Character activeChar, boolean isCheckOnly, L2Skill skill, L2Object target)
	{
		return checkIfOkToCastFlagDisplay(activeChar, FortManager.getInstance().getFort(activeChar), isCheckOnly, skill, target);
	}
	
	/**
	 * 
	 * @param activeChar
	 * @param fort
	 * @param isCheckOnly
	 * @return
	 */
	public static boolean checkIfOkToCastFlagDisplay(L2Character activeChar, Fort fort, boolean isCheckOnly,L2Skill skill, L2Object target)
	{
		if (!(activeChar instanceof L2PcInstance))
			return false;
		
		SystemMessage sm;
		L2PcInstance player = (L2PcInstance) activeChar;
		
		if (fort == null || fort.getFortId() <= 0)
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addSkillName(skill);
		}
		else if (!fort.getSiege().getIsInProgress())
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addSkillName(skill);
		}
		else if (!Util.checkIfInRange(200, player, player.getTarget(), true))
		{
			sm = new SystemMessage(SystemMessageId.DIST_TOO_FAR_CASTING_STOPPED);
		}
		else if (fort.getSiege().getAttackerClan(player.getClan()) == null)
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED);
			sm.addSkillName(skill);
		}
		else
		{
			if (!isCheckOnly)
				fort.getSiege().announceToPlayer(new SystemMessage(SystemMessageId.S1_TRYING_RAISE_FLAG),player.getClan().getName());
			return true;
		}
		
		if (!isCheckOnly)
			player.sendPacket(sm);
		return false;
	}
}
