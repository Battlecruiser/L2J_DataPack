/*
 * Copyright (C) 2004-2013 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.npc.NpcBuffers;

import com.l2jserver.Config;
import com.l2jserver.gameserver.ThreadPoolManager;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid
 */
public class NpcBufferAI implements Runnable
{
	private final L2Npc _npc;
	private final NpcBufferSkillData _skillData;
	
	protected NpcBufferAI(L2Npc npc, NpcBufferSkillData skill)
	{
		_npc = npc;
		_skillData = skill;
	}
	
	@Override
	public void run()
	{
		if ((_npc == null) || !_npc.isVisible() || _npc.isDecayed() || _npc.isDead() || (_skillData == null) || (_skillData.getSkill() == null))
		{
			return;
		}
		
		final L2Skill skill = _skillData.getSkill();
		if ((_npc.getSummoner() == null) || !_npc.getSummoner().isPlayer())
		{
			return;
		}
		final L2PcInstance player = _npc.getSummoner().getActingPlayer();
		switch (_skillData.getAffectScope())
		{
			case PARTY:
			{
				if (!player.isInParty())
				{
					if (Util.checkIfInRange(Config.ALT_PARTY_RANGE, _npc, player, true))
					{
						skill.applyEffects(player, player);
					}
				}
				else
				{
					for (L2PcInstance member : player.getParty().getMembers())
					{
						if (Util.checkIfInRange(Config.ALT_PARTY_RANGE, _npc, member, true))
						{
							skill.applyEffects(player, member);
						}
					}
				}
				break;
			}
			case RANGE:
			{
				for (L2Character target : _npc.getKnownList().getKnownCharactersInRadius(skill.getAffectRange()))
				{
					switch (_skillData.getAffectObject())
					{
						case FRIEND:
						{
							if (isFriendly(player, target))
							{
								skill.applyEffects(target, target);
							}
							break;
						}
						case NOT_FRIEND:
						{
							if (!isFriendly(player, target))
							{
								skill.applyEffects(target, target);
							}
							break;
						}
					}
				}
				break;
			}
		}
		ThreadPoolManager.getInstance().scheduleGeneral(this, _skillData.getDelay());
	}
	
	private boolean isFriendly(L2PcInstance player, L2Character target)
	{
		if (target.isPlayable())
		{
			final L2PcInstance targetPlayer = target.getActingPlayer();
			if (targetPlayer == null)
			{
				return false;
			}
			
			if (player.isInParty())
			{
				final L2Party party = player.getParty();
				
				// Same party.
				if (party.containsPlayer(targetPlayer))
				{
					return true;
				}
				
				// Same command channel.
				if (party.isInCommandChannel() && party.getCommandChannel().containsPlayer(targetPlayer))
				{
					return true;
				}
			}
			
			// Same clan.
			if ((player.getClanId() > 0) && (player.getClanId() == target.getClanId()))
			{
				return true;
			}
			
			// Same ally.
			if ((player.getAllyId() > 0) && (player.getAllyId() == target.getAllyId()))
			{
				return true;
			}
		}
		return false;
	}
}