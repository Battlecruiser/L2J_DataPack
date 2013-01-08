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
package ai.npc.ForgeOfTheGods;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.L2Skill;

/**
 * Tar Beetle AI
 * @author nonom, malyelfik
 */
public class TarBeetle extends AbstractNpcAI
{
	// NPC
	private static final int TAR_BEETLE = 18804;
	
	// Skills
	private static final int SKILL_ID = 6142;
	private static SkillHolder[] SKILLS =
	{
		new SkillHolder(SKILL_ID, 1),
		new SkillHolder(SKILL_ID, 2),
		new SkillHolder(SKILL_ID, 3)
	};
	
	private static final TarBeetleSpawn spawn = new TarBeetleSpawn();
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		if ((spawn.getBeetle(npc).getScriptValue() > 0) && canCastSkill(npc))
		{
			int level = 0;
			final L2Effect effect = player.getFirstEffect(SKILL_ID);
			if (effect != null)
			{
				level = effect.getSkill().getAbnormalLvl();
			}
			if (level < 3)
			{
				
				npc.setTarget(player);
				npc.doCast(SKILLS[level].getSkill());
			}
		}
		return super.onAggroRangeEnter(npc, player, isPet);
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if ((skill != null) && (skill.getId() == SKILL_ID))
		{
			int val = spawn.getBeetle(npc).getScriptValue() - 1;
			if ((val <= 0) || (SKILLS[0].getSkill().getMpConsume() > npc.getCurrentMp()))
			{
				spawn.removeBeetle(npc);
			}
			else
			{
				spawn.getBeetle(npc).isScriptValue(val);
			}
		}
		return super.onSpellFinished(npc, player, skill);
	}
	
	private boolean canCastSkill(L2Npc npc)
	{
		for (SkillHolder holder : SKILLS)
		{
			if (npc.isSkillDisabled(holder.getSkill()))
			{
				return false;
			}
		}
		return true;
	}
	
	public TarBeetle(String name, String descr)
	{
		super(name, descr);
		addAggroRangeEnterId(TAR_BEETLE);
		addSpellFinishedId(TAR_BEETLE);
		
		spawn.startTasks();
	}
	
	public static void main(String[] args)
	{
		new TarBeetle(TarBeetle.class.getSimpleName(), "ai/npc/");
	}
}