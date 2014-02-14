/*
 * Copyright (C) 2004-2014 L2J DataPack
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

import java.util.List;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.QuestEventType;
import com.l2jserver.gameserver.instancemanager.HellboundManager;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.taskmanager.DecayTaskManager;

/**
 * Hellbound Slaves AI.
 * @author DS
 */
public class Slaves extends AbstractNpcAI
{
	private static final int[] MASTERS =
	{
		22320,
		22321
	};
	private static final Location MOVE_TO = new Location(-25451, 252291, -3252, 3500);
	private static final int TRUST_REWARD = 10;
	
	private Slaves()
	{
		super(Slaves.class.getSimpleName(), "ai/group_template");
		registerMobs(MASTERS, QuestEventType.ON_SPAWN, QuestEventType.ON_KILL);
	}
	
	@Override
	public final String onSpawn(L2Npc npc)
	{
		((L2MonsterInstance) npc).enableMinions(HellboundManager.getInstance().getLevel() < 5);
		((L2MonsterInstance) npc).setOnKillDelay(1000);
		
		return super.onSpawn(npc);
	}
	
	// Let's count trust points for killing in Engine
	@Override
	public final String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		if (((L2MonsterInstance) npc).getMinionList() != null)
		{
			final List<L2MonsterInstance> slaves = ((L2MonsterInstance) npc).getMinionList().getSpawnedMinions();
			if ((slaves != null) && !slaves.isEmpty())
			{
				for (L2MonsterInstance slave : slaves)
				{
					if ((slave == null) || slave.isDead())
					{
						continue;
					}
					
					slave.clearAggroList();
					slave.abortAttack();
					slave.abortCast();
					slave.broadcastPacket(new NpcSay(slave.getObjectId(), Say2.NPC_ALL, slave.getId(), NpcStringId.THANK_YOU_FOR_SAVING_ME_FROM_THE_CLUTCHES_OF_EVIL));
					
					if ((HellboundManager.getInstance().getLevel() >= 1) && (HellboundManager.getInstance().getLevel() <= 2))
					{
						HellboundManager.getInstance().updateTrust(TRUST_REWARD, false);
					}
					
					slave.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, MOVE_TO);
					DecayTaskManager.getInstance().add(slave);
				}
			}
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new Slaves();
	}
}
