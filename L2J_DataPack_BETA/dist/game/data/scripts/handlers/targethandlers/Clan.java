/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.targethandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2ClanMember;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class Clan implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new ArrayList<>();
		
		if (activeChar.isPlayable())
		{
			final L2PcInstance player = activeChar.getActingPlayer();
			
			if (player == null)
			{
				return EMPTY_TARGET_LIST;
			}
			
			if (player.isInOlympiadMode())
			{
				return new L2Character[]
				{
					player
				};
			}
			
			if (onlyFirst)
			{
				return new L2Character[]
				{
					player
				};
			}
			
			targetList.add(player);
			
			final int radius = skill.getAffectRange();
			final L2Clan clan = player.getClan();
			
			if (L2Skill.addSummon(activeChar, player, radius, false))
			{
				targetList.add(player.getSummon());
			}
			
			if (clan != null)
			{
				L2PcInstance obj;
				for (L2ClanMember member : clan.getMembers())
				{
					obj = member.getPlayerInstance();
					
					if ((obj == null) || (obj == player))
					{
						continue;
					}
					
					if (player.isInDuel())
					{
						if (player.getDuelId() != obj.getDuelId())
						{
							continue;
						}
						if (player.isInParty() && obj.isInParty() && (player.getParty().getLeaderObjectId() != obj.getParty().getLeaderObjectId()))
						{
							continue;
						}
					}
					
					// Don't add this target if this is a Pc->Pc pvp casting and pvp condition not met
					if (!player.checkPvpSkill(obj, skill))
					{
						continue;
					}
					
					if (!TvTEvent.checkForTvTSkill(player, obj, skill))
					{
						continue;
					}
					
					if (!onlyFirst && L2Skill.addSummon(activeChar, obj, radius, false))
					{
						targetList.add(obj.getSummon());
					}
					
					if (!L2Skill.addCharacter(activeChar, obj, radius, false))
					{
						continue;
					}
					
					if (onlyFirst)
					{
						return new L2Character[]
						{
							obj
						};
					}
					
					targetList.add(obj);
				}
			}
		}
		else if (activeChar.isNpc())
		{
			// for buff purposes, returns friendly mobs nearby and mob itself
			final L2Npc npc = (L2Npc) activeChar;
			if ((npc.getFactionId() == null) || npc.getFactionId().isEmpty())
			{
				return new L2Character[]
				{
					activeChar
				};
			}
			
			targetList.add(activeChar);
			
			final Collection<L2Object> objs = activeChar.getKnownList().getKnownObjects().values();
			int maxTargets = skill.getAffectLimit();
			for (L2Object newTarget : objs)
			{
				if (newTarget.isNpc() && npc.getFactionId().equals(((L2Npc) newTarget).getFactionId()))
				{
					if (!Util.checkIfInRange(skill.getCastRange(), activeChar, newTarget, true))
					{
						continue;
					}
					
					if (targetList.size() >= maxTargets)
					{
						break;
					}
					
					targetList.add((L2Npc) newTarget);
				}
			}
		}
		
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.CLAN;
	}
}
