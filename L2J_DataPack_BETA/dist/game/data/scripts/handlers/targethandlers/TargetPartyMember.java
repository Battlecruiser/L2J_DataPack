/**
 * 
 */
package handlers.targethandlers;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author UnAfraid
 *
 */
public class TargetPartyMember implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		if ((target != null
				&& target == activeChar)
				|| (target != null
						&& activeChar.isInParty()
						&& target.isInParty()
						&& activeChar.getParty().getPartyLeaderOID() == target.getParty().getPartyLeaderOID())
						|| (target != null
								&& activeChar instanceof L2PcInstance
								&& target instanceof L2Summon
								&& activeChar.getPet() == target)
								|| (target != null
										&& activeChar instanceof L2Summon
										&& target instanceof L2PcInstance
										&& activeChar == target.getPet()))
		{
			if (!target.isDead())
			{
				// If a target is found, return it in a table else send a system message TARGET_IS_INCORRECT
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
		return SkillTargetType.TARGET_PARTY_MEMBER;
	}
}
