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
package ai.group_template;

import java.util.Collection;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;

/**
 * Giant Scouts AI.
 * @author Gnacik
 */
public class GiantScouts extends AbstractNpcAI
{
	private GiantScouts()
	{
		super(GiantScouts.class.getSimpleName(), "ai/group_template");
		addAggroRangeEnterId(22668, 22669); // scouts
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		L2Character target = isSummon ? player.getSummon() : player;
		
		if (GeoData.getInstance().canSeeTarget(npc, target))
		{
			if (!npc.isInCombat() && (npc.getTarget() == null))
			{
				npc.broadcastPacket(new CreatureSay(npc.getObjectId(), Say2.NPC_SHOUT, npc.getName(), NpcStringId.OH_GIANTS_AN_INTRUDER_HAS_BEEN_DISCOVERED));
			}
			
			npc.setTarget(target);
			npc.setRunning();
			((L2Attackable) npc).addDamageHate(target, 0, 999);
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
			
			// Notify clan
			Collection<L2Object> objs = npc.getKnownList().getKnownObjects().values();
			for (L2Object obj : objs)
			{
				if (obj != null)
				{
					if (obj instanceof L2MonsterInstance)
					{
						L2MonsterInstance monster = (L2MonsterInstance) obj;
						if (((npc.getClan() != null) && (monster.getClan() != null)) && monster.getClan().equals(npc.getClan()) && GeoData.getInstance().canSeeTarget(npc, monster))
						{
							monster.setTarget(target);
							monster.setRunning();
							monster.addDamageHate(target, 0, 999);
							monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
						}
					}
					
				}
			}
		}
		return super.onAggroRangeEnter(npc, player, isSummon);
	}
	
	public static void main(String[] args)
	{
		new GiantScouts();
	}
}