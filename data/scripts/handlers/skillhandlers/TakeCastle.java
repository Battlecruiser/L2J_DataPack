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
import net.sf.l2j.gameserver.instancemanager.CastleManager;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2ArtefactInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.entity.Castle;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;
import net.sf.l2j.gameserver.util.Util;

/**
 * @author _drunk_
 *
 */
public class TakeCastle implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.TAKECASTLE
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
		
		if (player.getClan() == null || player.getClan().getLeaderId() != player.getObjectId())
			return;
		
		Castle castle = CastleManager.getInstance().getCastle(player);
		if (castle == null || !checkIfOkToCastSealOfRule(player, castle))
			return;
		
		try
		{
			if (targets[0] instanceof L2ArtefactInstance)
				castle.Engrave(player.getClan(), targets[0].getObjectId());
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
	 * 
	 * @param activeChar
	 * @param castle
	 * @param isCheckOnly
	 * @return
	 */
	public static boolean checkIfOkToCastSealOfRule(L2Character activeChar, Castle castle)
	{
		if (!(activeChar instanceof L2PcInstance))
			return false;
		
		String text = "";
		L2PcInstance player = (L2PcInstance) activeChar;
		
		if (castle == null || castle.getCastleId() <= 0)
			text = "You must be on castle ground to use this skill";
		else if (!(player.getTarget() instanceof L2ArtefactInstance))
			text = "You can only use this skill on an artifact";
		else if (!castle.getSiege().getIsInProgress())
			text = "You can only use this skill during a siege.";
		else if (!Util.checkIfInRange(200, player, player.getTarget(), true))
			text = "You are not in range of the artifact.";
		else if (castle.getSiege().getAttackerClan(player.getClan()) == null)
			text = "You must be an attacker to use this skill";
		else
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.OPPONENT_STARTED_ENGRAVING);
			castle.getSiege().announceToPlayer(sm, false);
			return true;
		}
		
		player.sendMessage(text);
		return false;
	}
}
