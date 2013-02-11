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
package ai.individual;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Naia Lock AI.<br>
 * Removes minions after master's death.
 * @author GKR
 */
public class NaiaLock extends AbstractNpcAI
{
	private static final int LOCK = 18491;
	
	public NaiaLock(String name, String descr)
	{
		super(name, descr);
		addKillId(LOCK);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		((L2MonsterInstance) npc).getMinionList().onMasterDie(true);
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new NaiaLock(NaiaLock.class.getSimpleName(), "ai");
	}
}
