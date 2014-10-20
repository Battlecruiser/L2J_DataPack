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
package ai.individual;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.holders.MinionHolder;

/**
 * Grove Robber's AI.<br>
 * <ul>
 * <li>Grove Robber Summoner</li>
 * <li>Grove Robber Megician</li>
 * </ul>
 * @author Zealar
 */
public final class GraveRobbers extends AbstractNpcAI
{
	private static final int GRAVE_ROBBER_SUMMONER = 22678;
	private static final int GRAVE_ROBBER_MEGICIAN = 22679;
	
	private GraveRobbers()
	{
		super(GraveRobbers.class.getSimpleName(), "ai/individual");
		addSpawnId(GRAVE_ROBBER_SUMMONER, GRAVE_ROBBER_MEGICIAN);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (getRandom(2) == 0)
		{
			spawnMinions(npc, "Privates1");
		}
		else
		{
			spawnMinions(npc, "Privates2");
		}
		return super.onSpawn(npc);
	}
	
	private void spawnMinions(final L2Npc npc, final String spawnName)
	{
		for (MinionHolder is : npc.getTemplate().getParameters().getMinionList(spawnName))
		{
			addMinion((L2MonsterInstance) npc, is.getId());
		}
	}
	
	public static void main(String[] args)
	{
		new GraveRobbers();
	}
}
