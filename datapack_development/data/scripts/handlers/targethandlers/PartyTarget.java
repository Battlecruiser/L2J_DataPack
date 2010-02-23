package handlers.targethandlers;

import java.util.Collection;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;

/**
 * @author BiggBoss
 */
public class PartyTarget implements ITargetListHandler
{
	private static final SkillTargetType[] TARGET =
	{
		SkillTargetType.TARGET_PARTY,
		SkillTargetType.TARGET_PARTY_CLAN,
		SkillTargetType.TARGET_PARTY_MEMBER,
		SkillTargetType.TARGET_PARTY_OTHER
	};
	
	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{
		if(activeChar == null || activeChar.getParty() == null)
			return _emptyTarget;
		if(activeChar.getTarget() == null && sk.getTargetType() != SkillTargetType.TARGET_PARTY)
			return _emptyTarget;
		if(!(activeChar.getTarget() instanceof L2Character)
				&& sk.getTargetType() != SkillTargetType.TARGET_PARTY)
			return _emptyTarget;
		
		FastList<L2Object> targets = new FastList<L2Object>();
		if(onlyFirst)
		{
			targets.add(activeChar.getTarget());
			return (L2Object[]) targets.toArray();
		}
		int radius = sk.getSkillRadius();
		L2Party pt = activeChar.getParty();
		L2Character target = (L2Character) activeChar.getTarget();
		
		switch(sk.getTargetType())
		{
			case TARGET_PARTY:
			{
				for(L2PcInstance pc : pt.getPartyMembers())
				{
					if(pc != null && isInsideRadious(activeChar, pc, radius))
					{
						targets.add(pc);
						if(pc.getPet() != null 
								&& isInsideRadious(activeChar, pc.getPet(), radius))
							targets.add(pc.getPet());
					}
				}
				break;
			}
			case TARGET_PARTY_MEMBER:
			{
				if (target == activeChar
					|| ( activeChar.isInParty()
							&& target.isInParty()
							&& activeChar.getParty().getPartyLeaderOID() == target.getParty().getPartyLeaderOID())
					|| ( activeChar instanceof L2PcInstance
							&& target instanceof L2Summon
							&& activeChar.getPet() == target)
					|| ( activeChar instanceof L2Summon
							&& target instanceof L2PcInstance
							&& activeChar == target.getPet()))
				{
					if (!target.isDead())
						return new L2Character[]{target};
					else
						return _emptyTarget;
				}
				else
					sendIncorrectTargetMessage(activeChar);
				break;
			}
			case TARGET_PARTY_CLAN:
            {
				final L2PcInstance player = activeChar.getActingPlayer();

                targets.add(player);

                final boolean hasClan = player.getClan() != null;
                final boolean hasParty = player.isInParty();

                if (L2Skill.addSummon(activeChar, player, radius, false))
                	targets.add(player.getPet());

                // if player in olympiad mode or not in clan and not in party
                if (player.isInOlympiadMode() || !(hasClan || hasParty))
                    break;

                // Get all visible objects in a spherical area near the L2Character
            	final Collection<L2PcInstance> objs = activeChar.getKnownList().getKnownPlayersInRadius(radius);
            	//synchronized (activeChar.getKnownList().getKnownObjects())
				{
					for (L2PcInstance obj : objs)
					{
						if (obj == null)
							continue;

						if (player.isInDuel())
						{
							if (player.getDuelId() != obj.getDuelId())
								continue;

							if (hasParty && obj.isInParty() && player.getParty().getPartyLeaderOID() != obj.getParty().getPartyLeaderOID())
								continue;
						}

						if (!((hasClan && obj.getClanId() == player.getClanId())
								|| (hasParty && obj.isInParty() && player.getParty().getPartyLeaderOID() == obj.getParty().getPartyLeaderOID())))
							continue;

						// Don't add this target if this is a Pc->Pc pvp
						// casting and pvp condition not met
						if (!player.checkPvpSkill(obj, sk))
							continue;
						
						if (!TvTEvent.checkForTvTSkill(player, obj, sk))
							continue;

						if (!onlyFirst && L2Skill.addSummon(activeChar, obj, radius, false))
							targets.add(obj.getPet());

						if (!L2Skill.addCharacter(activeChar, obj, radius, false))
							continue;

						if (onlyFirst)
							return new L2Character[] { obj };
						
						targets.add(obj);
					}
				}
				break;
            }
			case TARGET_PARTY_OTHER:
            {
                if (target != null && target != activeChar
                        && activeChar.isInParty() && target.isInParty()
                        && activeChar.getParty().getPartyLeaderOID() == target.getParty().getPartyLeaderOID())
                {
                    if (!target.isDead())
                    {
                        if (target instanceof L2PcInstance)
                        {
                            switch (sk.getId())
                            {
                            	// FORCE BUFFS may cancel here but there should be a proper condition
                            	case 426: 
                                    if (!((L2PcInstance)target).isMageClass())
                                        return new L2Character[]{target};
                                    else
                                        return _emptyTarget;
                                case 427:
                                    if (((L2PcInstance)target).isMageClass())
                                        return new L2Character[]{target};
                                    else
                                        return _emptyTarget;
                            }
                        }
                        return new L2Character[]{target};
                    }
                }
                else
                    sendIncorrectTargetMessage(activeChar);
                break;
            }
            default:
            	return _emptyTarget;
		}
		return (L2Object[]) targets.toArray();
	}

	@Override
	public SkillTargetType[] getTargetsType() 
	{
		return TARGET;
	}

	@Override
	public void sendIncorrectTargetMessage(L2Character activeChar) 
	{
		
	}

	public boolean isInsideRadious(L2Character castor, L2Object effected, int radius) 
	{
		return castor.getKnownList().getKnownCharactersInRadius((long) radius).contains(effected);
	}
}
