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

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * Sandstorms AI.
 * @author Ectis
 */
public class Sandstorms extends AbstractNpcAI
{
	private static final int SANDSTORM = 32350;
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		npc.setTarget(player);
		npc.doCast(SkillTable.getInstance().getInfo(5435, 1));
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	public Sandstorms(String name, String descr)
	{
		super(name, descr);
		addAggroRangeEnterId(SANDSTORM);
	}
	
	public static void main(String[] args)
	{
		new Sandstorms(Sandstorms.class.getSimpleName(), "ai");
	}
}
