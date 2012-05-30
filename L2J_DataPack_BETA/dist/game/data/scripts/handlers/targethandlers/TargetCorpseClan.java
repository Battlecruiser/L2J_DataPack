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

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.l2jserver.gameserver.handler.ITargetTypeHandler;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.L2ClanMember;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.entity.TvTEvent;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.skills.L2SkillType;
import com.l2jserver.gameserver.model.skills.targets.L2TargetType;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class TargetCorpseClan implements ITargetTypeHandler
{
	@Override
	public L2Object[] getTargetList(L2Skill skill, L2Character activeChar, boolean onlyFirst, L2Character target)
	{
		List<L2Character> targetList = new FastList<>();
		if (activeChar instanceof L2Playable)
		{
			final L2PcInstance player = activeChar.getActingPlayer();
			
			if (player == null)
				return _emptyTargetList;
			
			if (player.isInOlympiadMode())
				return new L2Character[] { player };
			
			final int radius = skill.getSkillRadius();
			final L2Clan clan = player.getClan();
			
			if (L2Skill.addSummon(activeChar, player, radius, true))
				targetList.add(player.getPet());
			
			if (clan != null)
			{
				L2PcInstance obj;
				for (L2ClanMember member : clan.getMembers())
				{
					obj = member.getPlayerInstance();
					
					if (obj == null || obj == player)
						continue;
					
					if (player.isInDuel())
					{
						if (player.getDuelId() != obj.getDuelId())
							continue;
						if (player.isInParty() && obj.isInParty() && player.getParty().getLeaderObjectId() != obj.getParty().getLeaderObjectId())
							continue;
					}
					
					// Don't add this target if this is a Pc->Pc pvp casting and pvp condition not met
					if (!player.checkPvpSkill(obj, skill))
						continue;
					
					if (!TvTEvent.checkForTvTSkill(player, obj, skill))
						continue;
					
					if (!onlyFirst && L2Skill.addSummon(activeChar, obj, radius, true))
						targetList.add(obj.getPet());
					
					if (!L2Skill.addCharacter(activeChar, obj, radius, true))
						continue;
					
					if (skill.getSkillType() == L2SkillType.RESURRECT)
					{
						// check target is not in a active siege zone
						if (obj.isInsideZone(L2Character.ZONE_SIEGE) && !obj.isInSiege())
							continue;
					}
					
					if (onlyFirst)
						return new L2Character[] { obj };
					
					if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
						break;
					
					targetList.add(obj);
				}
			}
		}
		else if (activeChar instanceof L2Npc)
		{
			// for buff purposes, returns friendly mobs nearby and mob itself
			final L2Npc npc = (L2Npc) activeChar;
			if (npc.getFactionId() == null || npc.getFactionId().isEmpty())
			{
				return new L2Character[] { activeChar };
			}
			
			targetList.add(activeChar);
			
			final Collection<L2Object> objs = activeChar.getKnownList().getKnownObjects().values();
			
			for (L2Object newTarget : objs)
			{
				if (newTarget instanceof L2Npc && npc.getFactionId().equals(((L2Npc) newTarget).getFactionId()))
				{
					if (!Util.checkIfInRange(skill.getCastRange(), activeChar, newTarget, true))
						continue;
					
					if (skill.getMaxTargets() > -1 && targetList.size() >= skill.getMaxTargets())
						break;
					
					targetList.add((L2Npc) newTarget);
				}
			}
		}
		
		return targetList.toArray(new L2Character[targetList.size()]);
	}
	
	@Override
	public Enum<L2TargetType> getTargetType()
	{
		return L2TargetType.TARGET_CORPSE_CLAN;
	}
}
