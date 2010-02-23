package handlers.targethandlers;

import java.util.Collection;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2ClanMember;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.templates.skills.L2SkillType;
import com.l2jserver.gameserver.util.Util;

/**
 * @author BiggBoss
 */
public class AllyClanTarget implements ITargetListHandler 
{
	private static final SkillTargetType[] TARGET = 
	{
		SkillTargetType.TARGET_ALLY,
		SkillTargetType.TARGET_CLAN,
	};
	
	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{		
		if(activeChar == null || activeChar.getKnownList().getKnownCharacters().isEmpty())
			return _emptyTarget;
		
		if(!(activeChar.getTarget() instanceof L2Character))
			return _emptyTarget;
		
		if(onlyFirst)
			return new L2Object[] { activeChar.getTarget() };
		
		FastList<L2Object> targets = new FastList<L2Object>();
		L2PcInstance actor = null;
		int skillRange = sk.getSkillRadius();

		switch(sk.getTargetType())
		{
			case TARGET_CLAN:
			{
				if (activeChar instanceof L2Playable)
                {
                    final L2PcInstance player = activeChar.getActingPlayer();

                    if (player.isInOlympiadMode())
                    	return new L2Character[] {player};

                    final boolean isCorpseType = sk.getTargetType() == SkillTargetType.TARGET_CORPSE_CLAN;

                    if (!isCorpseType)
                        targets.add(player);

                    final int radius = sk.getSkillRadius();
                    final L2Clan clan = player.getClan();

                    if (L2Skill.addSummon(activeChar, player, radius, isCorpseType))
                		targets.add(player.getPet());

                    if (clan != null)
                    {
                    	L2PcInstance obj;
                        // Get Clan Members
                        for (L2ClanMember member : clan.getMembers())
                        {
                            obj = member.getPlayerInstance();

                            if (obj == null || obj == player)
                            	continue;

							if (player.isInDuel())
							{
								if (player.getDuelId() != obj.getDuelId())
									continue;
								if (player.isInParty() && obj.isInParty() && player.getParty().getPartyLeaderOID() != obj.getParty().getPartyLeaderOID())
									continue;
							}

							// Don't add this target if this is a Pc->Pc pvp casting and pvp condition not met
                            if (!player.checkPvpSkill(obj, sk))
                            	continue;

							if (!TvTEvent.checkForTvTSkill(player, obj, sk))
								continue;

							if (!onlyFirst && L2Skill.addSummon(activeChar, obj, radius, isCorpseType))
								targets.add(obj.getPet());

							if (!isInsideRadius(activeChar, activeChar.getTarget(), sk.getCastRange()))
								continue;

                            if (isCorpseType)
                            {
                            	if (sk.getSkillType() == L2SkillType.RESURRECT)
                            	{
                            		// check target is not in a active siege zone
                                 	if (obj.isInsideZone(L2Character.ZONE_SIEGE) && !obj.isInSiege())
                                 		continue;
                            	}
                            }
                            targets.add(obj);
                        }
                    }
                }
                else if (activeChar instanceof L2Npc)
                {
                	// for buff purposes, returns one unbuffed friendly mob nearby or mob itself?
                	final L2Npc npc = (L2Npc) activeChar;
                	final Collection<L2Object> objs = activeChar.getKnownList().getKnownObjects().values();
                	//synchronized (activeChar.getKnownList().getKnownObjects())
					{
						for (L2Object newTarget : objs)
						{
							if (newTarget instanceof L2Npc
							        && ((L2Npc) newTarget).getFactionId() == npc.getFactionId())
							{
								if (!Util.checkIfInRange(sk.getCastRange(), activeChar, newTarget, true))
									continue;
								if (((L2Npc) newTarget).getFirstEffect(sk) != null)
								{
									targets.add((L2Npc) newTarget);
									break;
								}
							}
						}
					}
                	if (targets.isEmpty())
                	{
                		targets.add(activeChar);
                	}
                }
            }			
			case TARGET_ALLY:
			{
				if(activeChar instanceof L2PcInstance)
					actor = (L2PcInstance) activeChar;
				else if(activeChar instanceof L2Summon)
					actor = ((L2Summon) activeChar).getOwner();
				
				if(actor.getAllyId() != 0)
				{
					for(L2PcInstance pc : actor.getKnownList().getKnownPlayersInRadius(skillRange))
					{
						if(pc.getAllyId() == actor.getAllyId())
						{
							targets.add(pc);
							if(pc.getPet() != null)
								targets.add(pc.getPet());
						}
					}
				}
				
				else if(actor.getClan() != null)
				{
					for(L2PcInstance pc : actor.getKnownList().getKnownPlayersInRadius(skillRange))
					{
						if(pc.getClan() != null && (pc.getClanId() == actor.getClanId()))
						{
							targets.add(pc);
							if(pc.getPet() != null)
								targets.add(pc.getPet());
						}
					}
				}
				
				else
				{
					targets.add(actor);
					if(actor.getPet() != null)
						targets.add(actor.getPet());
				}
				break;
			}
			default:
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
		if(!(activeChar instanceof L2PcInstance))
			return;
		SystemMessage sm = new SystemMessage(SystemMessageId.INCORRECT_TARGET);
		activeChar.sendPacket(sm);
	}
	
	public boolean isInsideRadius(L2Character castor, L2Object target, int range)
	{
		return castor.getKnownList().getKnownCharactersInRadius(range).contains(target);
	}
}
