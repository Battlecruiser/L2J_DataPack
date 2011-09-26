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

/**
 * @author UnAfraid
 *
 */
public class TargetParty implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		if (onlyFirst)
			return new L2Character[] { activeChar };
		
		targetList.add(activeChar);
		
		final int radius = skill.getSkillRadius();
		
		L2PcInstance player = activeChar.getActingPlayer();
		if (activeChar instanceof L2Summon)
		{
			if (L2Skill.addCharacter(activeChar, player, radius, false))
				targetList.add(player);
		}
		else if (activeChar instanceof L2PcInstance)
		{
			if (L2Skill.addSummon(activeChar, player, radius, false))
				targetList.add(player.getPet());
		}
		
		if (activeChar.isInParty())
		{
			// Get a list of Party Members
			for (L2PcInstance partyMember : activeChar.getParty().getPartyMembers())
			{
				if (partyMember == null || partyMember == player)
					continue;
				
				if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
					break;
				
				if (L2Skill.addCharacter(activeChar, partyMember, radius, false))
					targetList.add(partyMember);
				
				if (L2Skill.addSummon(activeChar, partyMember, radius, false))
					targetList.add(partyMember.getPet());
			}
		}
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_PARTY;
	}
}
