/**
 * 
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
 *
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
