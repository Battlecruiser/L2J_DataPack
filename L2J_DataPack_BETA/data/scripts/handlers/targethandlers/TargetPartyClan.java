/**
 * 
 */
package handlers.targethandlers;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ISkillTargetTypeHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;

/**
 * @author UnAfraid
 *
 */
public class TargetPartyClan implements ISkillTargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<L2Character>();
		if (onlyFirst)
			return new L2Character[] { activeChar };
		
		final L2PcInstance player = activeChar.getActingPlayer();
		
		if (player == null)
			return _emptyTargetList;
		
		targetList.add(player);
		
		final int radius = skill.getSkillRadius();
		final boolean hasClan = player.getClan() != null;
		final boolean hasParty = player.isInParty();
		
		if (L2Skill.addSummon(activeChar, player, radius, false))
			targetList.add(player.getPet());
		
		// if player in clan and not in party
		if (!(hasClan || hasParty))
			return targetList.toArray(new L2Character[targetList.size()]);
		
		// Get all visible objects in a spherical area near the L2Character
		final Collection<L2PcInstance> objs = activeChar.getKnownList().getKnownPlayersInRadius(radius);
		for (L2PcInstance obj : objs)
		{
			if (obj == null)
				continue;
			
			// olympiad mode - adding only own side
			if (player.isInOlympiadMode())
			{
				if (!obj.isInOlympiadMode())
					continue;
				if (player.getOlympiadGameId() != obj.getOlympiadGameId())
					continue;
				if (player.getOlympiadSide() != obj.getOlympiadSide())
					continue;
			}
			
			if (player.isInDuel())
			{
				if (player.getDuelId() != obj.getDuelId())
					continue;
				
				if (hasParty && obj.isInParty() && player.getParty().getPartyLeaderOID() != obj.getParty().getPartyLeaderOID())
					continue;
			}
			
			if (!((hasClan && obj.getClanId() == player.getClanId()) || (hasParty && obj.isInParty() && player.getParty().getPartyLeaderOID() == obj.getParty().getPartyLeaderOID())))
				continue;
			
			// Don't add this target if this is a Pc->Pc pvp
			// casting and pvp condition not met
			if (!player.checkPvpSkill(obj, skill))
				continue;
			
			if (!TvTEvent.checkForTvTSkill(player, obj, skill))
				continue;
			
			if (!onlyFirst && L2Skill.addSummon(activeChar, obj, radius, false))
				targetList.add(obj.getPet());
			
			if (!L2Skill.addCharacter(activeChar, obj, radius, false))
				continue;
			
			if (onlyFirst)
				return new L2Character[] { obj };
			
			if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
				break;
			
			targetList.add(obj);
		}
		
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<SkillTargetType> getTargetType()
	{
		return SkillTargetType.TARGET_PARTY_CLAN;
	}
}
