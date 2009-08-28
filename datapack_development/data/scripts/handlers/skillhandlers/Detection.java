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
import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.templates.skills.L2EffectType;
import net.sf.l2j.gameserver.templates.skills.L2SkillType;

/**
 * @author ZaKax
 */

public class Detection implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS =
	{
		L2SkillType.DETECTION
	};
	
	public void useSkill(L2Character activeChar, L2Skill skill, L2Object[] targets)
	{
		for (L2Character inKnownlist : activeChar.getKnownList().getKnownPlayersInRadius(skill.getSkillRadius()))
		{
			if (inKnownlist != null)
			{
				L2Effect eHide = inKnownlist.getFirstEffect(L2EffectType.HIDE);
				
				if (eHide == null)
					continue;
				
				if (activeChar instanceof L2PcInstance && inKnownlist instanceof L2PcInstance)
				{
					L2PcInstance player = ((L2PcInstance) activeChar);
					L2PcInstance target = ((L2PcInstance) inKnownlist);
					if (player.getParty() != null && target.getParty() != null && player.getParty().getPartyLeaderOID() == target.getParty().getPartyLeaderOID())
						continue;
				
					if (player.getClan() != null && target.getClan() != null && player.getClan().getClanId() == target.getClan().getClanId())
						continue;
				
					if (player.getAllyId() > 0 && target.getAllyId() > 0 && player.getClan().getAllyId() == target.getClan().getAllyId())
						continue;
				}
				
				if (eHide != null)
					eHide.exit();
			}
		}
	}
	
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}