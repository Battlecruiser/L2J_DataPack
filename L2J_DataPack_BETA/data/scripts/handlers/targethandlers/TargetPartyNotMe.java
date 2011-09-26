/**
 * 
 */
package handlers.targethandlers;

import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 *
 */
public class TargetPartyNotMe implements ISkillTargetTypeHandler
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
			List<L2PcInstance> partyList = activeChar.getParty().getPartyMembers();
			
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
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_PARTY_NOTME;
	}
}
