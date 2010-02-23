package handlers.targethandlers;

import java.util.Collection;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ITargetListHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Skill;
import com.l2jserver.gameserver.model.L2Skill.SkillTargetType;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.L2Summon;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PetInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.taskmanager.DecayTaskManager;
import com.l2jserver.gameserver.templates.skills.L2SkillType;
import com.l2jserver.gameserver.util.Util;

/**
 * @author BiggBoss
 */
public class CorpseTarget implements ITargetListHandler 
{
	private static final SkillTargetType[] TARGET =
	{
		SkillTargetType.TARGET_AREA_CORPSE_MOB,
		SkillTargetType.TARGET_CORPSE,
		SkillTargetType.TARGET_CORPSE_ALLY,
		SkillTargetType.TARGET_CORPSE_CLAN,
		SkillTargetType.TARGET_CORPSE_MOB,
		SkillTargetType.TARGET_CORPSE_PET,
		SkillTargetType.TARGET_CORPSE_PLAYER
	};
	
	@Override
	public L2Object[] getTargets(L2Character activeChar, L2Skill sk, boolean onlyFirst) 
	{
		
		if(activeChar == null || activeChar.getTarget() == null
				|| !(activeChar.getTarget() instanceof L2Character))
		{
			sendIncorrectTargetMessage(activeChar);
			return _emptyTarget;
		}
		
		if(onlyFirst)
			return new L2Object[] { activeChar.getTarget() };
		
		L2Character target = (L2Character) activeChar.getTarget();
		if(!target.isDead())
		{
			sendIncorrectTargetMessage(activeChar);
			return _emptyTarget;
		}
		
		FastList<L2Object> targets = new FastList<L2Object>();
		int radius = sk.getCastRange();
		
		switch(sk.getTargetType())
		{
			case TARGET_CORPSE:
			{
                if (target != null && target.isDead())
                {
                    final L2PcInstance player;
                    if (activeChar instanceof L2PcInstance)
                    	player = (L2PcInstance) activeChar;
                    else
                    	player = null;
                    
                    final L2PcInstance targetPlayer;
                    if (target instanceof L2PcInstance)
                    	targetPlayer = (L2PcInstance) target;
                    else
                    	targetPlayer = null;
                    
                    final L2PetInstance targetPet;
                    if (target instanceof L2PetInstance)
                    	targetPet = (L2PetInstance) target;
                    else
                    	targetPet = null;

                    if (player != null && (targetPlayer != null || targetPet != null))
                    {
                        boolean condGood = true;

                        if (sk.getSkillType() == L2SkillType.RESURRECT)
                        {
                         	if (targetPlayer != null)
                            {
                        		// check target is not in a active siege zone
                             	if (targetPlayer.isInsideZone(L2Character.ZONE_SIEGE) && !targetPlayer.isInSiege())
                             	{
                					condGood = false;
                					activeChar.sendPacket(new SystemMessage(SystemMessageId.CANNOT_BE_RESURRECTED_DURING_SIEGE));
                             	}

                            	if (targetPlayer.isFestivalParticipant()) // Check to see if the current player target is in a festival.
            					{
            						condGood = false;
            						activeChar.sendMessage("You may not resurrect participants in a festival.");
            					}
                            	if (targetPlayer.isReviveRequested())
                            	{
                            		if (targetPlayer.isRevivingPet())
                            			player.sendPacket(new SystemMessage(SystemMessageId.MASTER_CANNOT_RES)); // While a pet is attempting to resurrect, it cannot help in resurrecting its master.
                            		else
                            			player.sendPacket(new SystemMessage(SystemMessageId.RES_HAS_ALREADY_BEEN_PROPOSED)); // Resurrection is already been proposed.
                                    condGood = false;
                            	}
                            }
                            else if (targetPet != null)
                            {
                                if (targetPet.getOwner() != player)
                                {
                                	if (targetPet.getOwner().isReviveRequested())
            						{
            							if (targetPet.getOwner().isRevivingPet())
            								player.sendPacket(new SystemMessage(SystemMessageId.RES_HAS_ALREADY_BEEN_PROPOSED)); // Resurrection is already been proposed.
            							else
            								player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_RES_PET2)); // A pet cannot be resurrected while it's owner is in the process of resurrecting.
            							condGood = false;
            						}
                                }
                            }
                        }

                        if (condGood)
                        {
                               targets.add(target);
                        }
                    }
                }
            }
			case TARGET_CORPSE_PLAYER:
			{
                if (target != null && target.isDead())
                {
                    final L2PcInstance player;
                    if (activeChar instanceof L2PcInstance)
                    	player = (L2PcInstance) activeChar;
                    else
                    	player = null;
                    
                    final L2PcInstance targetPlayer;
                    if (target instanceof L2PcInstance)
                    	targetPlayer = (L2PcInstance) target;
                    else
                    	targetPlayer = null;
                    
                    final L2PetInstance targetPet;
                    if (target instanceof L2PetInstance)
                    	targetPet = (L2PetInstance) target;
                    else
                    	targetPet = null;

                    if (player != null && (targetPlayer != null || targetPet != null))
                    {
                        boolean condGood = true;

                        if (sk.getSkillType() == L2SkillType.RESURRECT)
                        {
                         	if (targetPlayer != null)
                            {
                        		// check target is not in a active siege zone
                             	if (targetPlayer.isInsideZone(L2Character.ZONE_SIEGE) && !targetPlayer.isInSiege())
                             	{
                					condGood = false;
                					activeChar.sendPacket(new SystemMessage(SystemMessageId.CANNOT_BE_RESURRECTED_DURING_SIEGE));
                             	}

                            	if (targetPlayer.isFestivalParticipant()) // Check to see if the current player target is in a festival.
            					{
            						condGood = false;
            						activeChar.sendMessage("You may not resurrect participants in a festival.");
            					}
                            	if (targetPlayer.isReviveRequested())
                            	{
                            		if (targetPlayer.isRevivingPet())
                            			player.sendPacket(new SystemMessage(SystemMessageId.MASTER_CANNOT_RES)); // While a pet is attempting to resurrect, it cannot help in resurrecting its master.
                            		else
                            			player.sendPacket(new SystemMessage(SystemMessageId.RES_HAS_ALREADY_BEEN_PROPOSED)); // Resurrection is already been proposed.
                                    condGood = false;
                            	}
                            }
                            else if (targetPet != null)
                            {
                                if (targetPet.getOwner() != player)
                                {
                                	if (targetPet.getOwner().isReviveRequested())
            						{
            							if (targetPet.getOwner().isRevivingPet())
            								player.sendPacket(new SystemMessage(SystemMessageId.RES_HAS_ALREADY_BEEN_PROPOSED)); // Resurrection is already been proposed.
            							else
            								player.sendPacket(new SystemMessage(SystemMessageId.CANNOT_RES_PET2)); // A pet cannot be resurrected while it's owner is in the process of resurrecting.
            							condGood = false;
            						}
                                }
                            }
                        }

                        if (condGood)
                        	targets.add(target);
                     }
                }
			}
			case TARGET_CORPSE_PET:
			{
				if(activeChar instanceof L2PcInstance && target instanceof L2PetInstance)
				{
					if(((L2PetInstance)target).isDead())
						targets.add(target);
				}
				else
					sendIncorrectTargetMessage(activeChar);
				break;
			}
			case TARGET_CORPSE_ALLY:
			case TARGET_CORPSE_CLAN:
			{
				L2PcInstance effector = null;
				
				if(activeChar instanceof L2PcInstance)
					effector = (L2PcInstance) activeChar;
				else if(activeChar instanceof L2Summon)
					effector = ((L2Summon) activeChar).getOwner();

				if(target instanceof L2PcInstance)
				{
					L2PcInstance effected = (L2PcInstance) target;
					
					if(effected.getClan() == null || effector.getClan() == null)
						return _emptyTarget;
					
					if(!effected.isDead())
						return _emptyTarget;
					
					if(effector.getClanId() == effected.getClanId()
							|| (effector.getAllyId() == effected.getAllyId()
									&& effector.getAllyId() != 0))
					{
						targets.add(effected);
					}
				}
				else if(target instanceof L2Summon)
				{
					L2Summon effected = (L2Summon) target;
					
					if(effected.getOwner().getClan() == null || effector.getClan() == null)
						return _emptyTarget;
					
					if(!effected.isDead())
						return _emptyTarget;
					
					if(effector.getClanId() == effected.getOwner().getClanId()
							|| (effector.getAllyId() == effected.getOwner().getAllyId()
									&& effector.getAllyId() != 0))
					{
						targets.add(effected);
					}

				}
				else
				{
					sendIncorrectTargetMessage(activeChar);
					return _emptyTarget;
				}
					
				final Collection<L2PcInstance> objs = activeChar.getKnownList().getKnownPlayersInRadius(sk.getCastRange());
				L2PcInstance player = activeChar.getActingPlayer();
				for(L2PcInstance obj : objs)
				{
					if (obj == null)
						continue;
					
					if(player.getClan() != null && obj.getClan() != null)
					{
						if(player.getClanId() != obj.getClanId()
								|| ((obj.getAllyId() == 0 || obj.getAllyId() != player.getAllyId())
								        && (obj.getClan() == null || obj.getClanId() != player.getClanId())))
							continue;
					}

					if(player.getClan() != null && obj.getClan() != null)
					{
						if(player.getClanId() != obj.getClanId())
							continue;
					}

					if (player.isInDuel())
					{
						if (player.getDuelId() != obj.getDuelId())
							continue;
						if (player.isInParty() && obj.isInParty() && player.getParty().getPartyLeaderOID() != obj.getParty().getPartyLeaderOID())
							continue;
					}

					// Don't add this target if this is a Pc->Pc pvp
					// casting and pvp condition not met
					if (!player.checkPvpSkill(obj, sk))
						continue;
					
					if (!TvTEvent.checkForTvTSkill(player, obj, sk))
						continue;

					if (L2Skill.addSummon(activeChar, obj, sk.getCastRange(), true))
						targets.add(obj.getPet());

					if (!L2Skill.addCharacter(activeChar, obj, sk.getCastRange(), true))
						continue;

					// Siege battlefield resurrect has been made possible for participants
					if (sk.getSkillType() == L2SkillType.RESURRECT)
					{
						if (obj.isInsideZone(L2Character.ZONE_SIEGE) && !obj.isInSiege())
									continue;
					}
					targets.add(obj);
				}
				break;
			}
			case TARGET_CORPSE_MOB:
			{
                switch (sk.getSkillType())
                {
                	case DRAIN:
                	case SUMMON:
                	{
                		if (DecayTaskManager.getInstance().getTasks().containsKey(target) 
                        		&& (System.currentTimeMillis() - DecayTaskManager.getInstance().getTasks().get(target)) > DecayTaskManager.ATTACKABLE_DECAY_TIME / 2)
                        {
                        	activeChar.sendPacket(new SystemMessage(SystemMessageId.CORPSE_TOO_OLD_SKILL_NOT_USED));
                        	return _emptyTarget;
                        }
                	}
                }

				if(target instanceof L2MonsterInstance)
					targets.add(target);
				break;
			}
			case TARGET_AREA_CORPSE_MOB:
			{
                if ((!(target instanceof L2Attackable)) || !target.isDead())
                {
                    activeChar.sendPacket(new SystemMessage(SystemMessageId.TARGET_IS_INCORRECT));
                    return _emptyTarget;
                }

                if (onlyFirst)
                	return new L2Character[] {target};

            	targets.add(target);

            	final boolean srcInArena = (activeChar.isInsideZone(L2Character.ZONE_PVP) && !activeChar.isInsideZone(L2Character.ZONE_SIEGE));

                final Collection<L2Character> objs = activeChar.getKnownList().getKnownCharacters();
                //synchronized (activeChar.getKnownList().getKnownObjects())
				{
					for (L2Character obj : objs)
					{
						if (!(obj instanceof L2Attackable || obj instanceof L2Playable)
						        || !Util.checkIfInRange(radius, target, obj, true))
							continue;

						if (!L2Skill.checkForAreaOffensiveSkills(activeChar, obj, sk, srcInArena))
							continue;
						
						targets.add(obj);
					}
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
}
