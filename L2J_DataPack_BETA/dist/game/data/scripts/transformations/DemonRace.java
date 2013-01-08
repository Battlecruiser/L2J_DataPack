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
package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

/*
 * TODO: Skill levels. How do they work? Transformation is given at level 83, there are 6 levels of the skill. How are they assigned? Based on player level somehow? Based on servitor?
 */
public class DemonRace extends L2Transformation
{
	private static final int[] SKILLS =
	{
		901,
		902,
		903,
		904,
		905,
		5491,
		619
	};
	
	public DemonRace()
	{
		// id, colRadius, colHeight
		super(221, 11, 27);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 221) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		if (getPlayer().hasSummon())
		{
			getPlayer().getSummon().unSummon(getPlayer());
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Dark Strike (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(901, 4), false);
		// Bursting Flame (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(902, 4), false);
		// Stratum Explosion (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(903, 4), false);
		// Corpse Burst (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(904, 4), false);
		// Dark Detonation (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(905, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Dark Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(901, 4), false);
		// Bursting Flame
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(902, 4), false);
		// Stratum Explosion
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(903, 4), false);
		// Corpse Burst
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(904, 4), false);
		// Dark Detonation
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(905, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DemonRace());
	}
}
