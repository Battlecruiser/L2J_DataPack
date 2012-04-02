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
package handlers.targethandlers;

import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class TargetPartyNotMe implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		if (onlyFirst)
			return new L2Character[] { activeChar };
		
		L2PcInstance player = null;
		
		if (activeChar instanceof L2Summon)
		{
			player = ((L2Summon) activeChar).getOwner();
			targetList.add(player);
		}
		else if (activeChar instanceof L2PcInstance)
		{
			player = (L2PcInstance) activeChar;
			if (activeChar.getPet() != null)
				targetList.add(activeChar.getPet());
		}
		
		if (activeChar.getParty() != null)
		{
			List<L2PcInstance> partyList = activeChar.getParty().getMembers();
			
			for (L2PcInstance partyMember : partyList)
			{
				if (partyMember == null)
					continue;
				else if (partyMember == player)
					continue;
				else if (!partyMember.isDead() && Util.checkIfInRange(skill.getSkillRadius(), activeChar, partyMember, true))
				{
					if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
						break;
					
					targetList.add(partyMember);
					
					if (partyMember.getPet() != null && !partyMember.getPet().isDead())
					{
						targetList.add(partyMember.getPet());
					}
				}
			}
		}
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.TARGET_PARTY_NOTME;
	}
}
