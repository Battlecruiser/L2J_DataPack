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

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author UnAfraid
 */
public class TargetPartyOther implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		if (target != null && target != activeChar
				&& activeChar.isInParty() && target.isInParty()
				&& activeChar.getParty().getPartyLeaderOID() == target.getParty().getPartyLeaderOID())
		{
			if (!target.isDead())
			{
				if (target instanceof L2PcInstance)
				{
					switch (skill.getId())
					{
						// FORCE BUFFS may cancel here but there should be a proper condition
						case 426:
							if (!((L2PcInstance) target).isMageClass())
								return new L2Character[] { target };
							else
								return _emptyTargetList;
						case 427:
							if (((L2PcInstance) target).isMageClass())
								return new L2Character[] { target };
							else
								return _emptyTargetList;
					}
				}
				return new L2Character[] { target };
			}
			else
				return _emptyTargetList;
		}
		else
		{
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
			return _emptyTargetList;
		}
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_PARTY_OTHER;
	}
}
