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
public class Unicorniun extends L2Transformation
{
	private static final int[] SKILLS =
	{
		906, 907, 908, 909, 910, 5491, 619
	};
	
	public Unicorniun()
	{
		// id, colRadius, colHeight
		super(220, 8, 30);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 220) || getPlayer().isCursedWeaponEquipped())
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
		// Lance Step (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(906, 4), false);
		// Aqua Blast (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(907, 4), false);
		// Spin Slash (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(908, 4), false);
		// Ice Focus (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(909, 4), false);
		// Water Jet (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(910, 4), false);
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
		// Lance Step (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(906, 4), false);
		// Aqua Blast (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(907, 4), false);
		// Spin Slash (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(908, 4), false);
		// Ice Focus (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(909, 4), false);
		// Water Jet (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(910, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Unicorniun());
	}
}
