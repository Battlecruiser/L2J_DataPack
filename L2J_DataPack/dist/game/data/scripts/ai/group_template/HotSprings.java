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

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.BuffInfo;
import com.l2jserver.gameserver.model.skills.Skill;

/**
 * Hot Springs AI.
 * @author Pandragon
 */
public final class HotSprings extends AbstractNpcAI
{
	// NPCs
	private static final int BANDERSNATCHLING = 21314;
	private static final int FLAVA = 21316;
	private static final int ATROXSPAWN = 21317;
	private static final int NEPENTHES = 21319;
	private static final int ATROX = 21321;
	private static final int BANDERSNATCH = 21322;
	// Skills
	private static final int RHEUMATISM = 4551;
	private static final int CHOLERA = 4552;
	private static final int FLU = 4553;
	private static final int MALARIA = 4554;
	// Misc
	private static final int DISEASE_CHANCE = 10;
	
	private HotSprings()
	{
		super(HotSprings.class.getSimpleName(), "ai/group_template");
		addAttackId(BANDERSNATCHLING, FLAVA, ATROXSPAWN, NEPENTHES, ATROX, BANDERSNATCH);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (getRandom(100) < DISEASE_CHANCE)
		{
			tryToInfect(npc, attacker, MALARIA);
		}
		
		if (getRandom(100) < DISEASE_CHANCE)
		{
			switch (npc.getId())
			{
				case BANDERSNATCHLING:
				case ATROX:
				{
					tryToInfect(npc, attacker, RHEUMATISM);
					break;
				}
				case FLAVA:
				case NEPENTHES:
				{
					tryToInfect(npc, attacker, CHOLERA);
					break;
				}
				case ATROXSPAWN:
				case BANDERSNATCH:
				{
					tryToInfect(npc, attacker, FLU);
					break;
				}
			}
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	private void tryToInfect(L2Npc npc, L2Character player, int diseaseId)
	{
		final BuffInfo info = player.getEffectList().getBuffInfoBySkillId(diseaseId);
		final int skillLevel = (info == null) ? 1 : (info.getSkill().getLevel() < 10) ? info.getSkill().getLevel() + 1 : 10;
		final Skill skill = SkillData.getInstance().getSkill(diseaseId, skillLevel);
		
		if ((skill != null) && !npc.isCastingNow() && npc.checkDoCastConditions(skill))
		{
			npc.setTarget(player);
			npc.doCast(skill);
		}
	}
	
	public static void main(String[] args)
	{
		new HotSprings();
	}
}