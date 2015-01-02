/*
 * Copyright (C) 2004-2015 L2J DataPack
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

import java.util.Map;

import javolution.util.FastMap;
import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Angel spawns...when one of the angels in the keys dies, the other angel will spawn.
 */
public final class PolymorphingAngel extends AbstractNpcAI
{
	private static final Map<Integer, Integer> ANGELSPAWNS = new FastMap<>();
	static
	{
		ANGELSPAWNS.put(20830, 20859);
		ANGELSPAWNS.put(21067, 21068);
		ANGELSPAWNS.put(21062, 21063);
		ANGELSPAWNS.put(20831, 20860);
		ANGELSPAWNS.put(21070, 21071);
	}
	
	private PolymorphingAngel()
	{
		super(PolymorphingAngel.class.getSimpleName(), "ai/group_template");
		addKillId(ANGELSPAWNS.keySet());
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon)
	{
		final L2Attackable newNpc = (L2Attackable) addSpawn(ANGELSPAWNS.get(npc.getId()), npc);
		newNpc.setRunning();
		return super.onKill(npc, killer, isSummon);
	}
	
	public static void main(String[] args)
	{
		new PolymorphingAngel();
	}
}
