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

import com.l2jserver.gameserver.datatables.NpcData;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.L2Playable;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.actor.templates.L2NpcTemplate;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.util.Util;

/**
 * Range Guard AI.
 * @author St3eT.
 */
public final class RangeGuard extends AbstractNpcAI
{
	// Skill
	private static SkillHolder ULTIMATE_DEFENSE = new SkillHolder(5044, 3); // NPC Ultimate Defense
	
	// Misc
	private static final int MIN_DISTANCE = 150;
	
	// Not allowed skills
	//@formatter:off
	private static final int[] NOT_ALLOWED_SKILLS =
	{
		15, 28, // Charm / Aggression
		51, 65, // Lure / Horror
		106, 115, // Veil / Power Break
		122, 127, // Hex / Hamstring
		254, 352, // Spoil / Shield Bash
		353, 358, // Shield Slam / Bluff
		402, 403, // Arrest / Shackle
		412, 485, // Sand Bomb / Disarm
		501, 511, // Violent Temper / Temptation
		522, 531, // Real Target / Critical Wound
		680, 695, // Divine Knight Hate / Divine Wizard Divine Cloud
		696, 716, // Divine Wizard Surrender to Divine / Zaken Hold
		775, 792, // Weapon Blockade / Betrayal Mark
		1042, 1049, // Hold Undead / Requiem
		1069, 1071, // Sleep / Surrender To Water
		1072, 1074, // Sleeping Cloud / Surrender To Wind
		1083, 1097, // Surrender To Fire / Dreaming Spirit
		1092, 1064, // Fear / Silence
		1160, 1164, // Slow / Curse Weakness
		1169, 1170, // Curse Fear / Anchor
		1201, 1206, // Dryad Root / Wind Shackle
		1222, 1223, // Curse Chaos / Surrender To Earth
		1224, 1263, // Surrender To Poison / Curse Gloom
		1269, 1336, // Curse Disease / Curse of Doom
		1337, 1338, // Curse of Abyss / Arcane Chaos
		1358, 1359, // Block Shield / Block Wind Walk
		1386, 1394, // Arcane Disruption / Trance
		1396, 1445, // Magical BackFire / Surrender to Dark
		1446, 1447, // Shadow Bind / Voice Bind
		1481, 1482, // Oblivion / Weak Constitution
		1483, 1484, // Thin Skin / Enervation
		1485, 1486, // Spite / Mental Impoverish
		1511, 1524, // Curse of Life Flow / Surrender to the Divine
		1529, // Soul Web
	};
	//@formatter:on
	
	private RangeGuard()
	{
		super(RangeGuard.class.getSimpleName(), "ai/group_template");
		
		final List<L2NpcTemplate> monsters = NpcData.getInstance().getAllNpcOfClassType("L2Monster");
		for (L2NpcTemplate template : monsters)
		{
			if (template.getParameters().getInt("LongRangeGuardRate", -1) > 0)
			{
				addAttackId(template.getId());
			}
		}
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon, Skill skill)
	{
		final L2Playable playable = (isSummon) ? attacker.getSummon() : attacker;
		final int longRangeGuardRate = npc.getTemplate().getParameters().getInt("LongRangeGuardRate");
		final double distance = Util.calculateDistance(npc, playable, true, false);
		
		if (npc.isAffectedBySkill(ULTIMATE_DEFENSE.getSkillId()) && (distance <= MIN_DISTANCE))
		{
			npc.stopSkillEffects(true, ULTIMATE_DEFENSE.getSkillId());
		}
		else if ((distance > MIN_DISTANCE) && !npc.isSkillDisabled(ULTIMATE_DEFENSE.getSkillId()) && !((skill != null) && Util.contains(NOT_ALLOWED_SKILLS, skill.getId())) && (getRandom(100) < longRangeGuardRate))
		{
			npc.setTarget(npc);
			npc.doCast(ULTIMATE_DEFENSE.getSkill());
		}
		return super.onAttack(npc, attacker, damage, isSummon, skill);
	}
	
	public static void main(String[] args)
	{
		new RangeGuard();
	}
}