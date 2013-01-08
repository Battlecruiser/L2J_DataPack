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

public class SaberToothTiger extends L2Transformation
{
	private static final int[] SKILLS =
	{
		746, 747, 748, 5491, 619
	};
	
	public SaberToothTiger()
	{
		// id, colRadius, colHeight
		super(5, 34, 28);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 5) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		if (getPlayer().getLevel() >= 76)
		{
			// Saber Tooth Tiger Bite (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(746, 3), false);
			// Saber Tooth Tiger Fear (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(747, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Saber Tooth Tiger Bite (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(746, 2), false);
			// Saber Tooth Tiger Fear (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(747, 2), false);
		}
		else if (getPlayer().getLevel() >= 70)
		{
			// Saber Tooth Tiger Bite (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(746, 1), false);
			// Saber Tooth Tiger Fear (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(747, 1), false);
		}
		// Saber Tooth Tiger Sprint
		getPlayer().addSkill(SkillTable.getInstance().getInfo(748, 1), false);
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
		if (getPlayer().getLevel() >= 76)
		{
			// Saber Tooth Tiger Bite (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(746, 3), false);
			// Saber Tooth Tiger Fear (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(747, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Saber Tooth Tiger Bite (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(746, 2), false);
			// Saber Tooth Tiger Fear (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(747, 2), false);
		}
		else
		{
			// Saber Tooth Tiger Bite (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(746, 1), false);
			// Saber Tooth Tiger Fear (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(747, 1), false);
		}
		// Saber Tooth Tiger Sprint
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(748, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new SaberToothTiger());
	}
}
