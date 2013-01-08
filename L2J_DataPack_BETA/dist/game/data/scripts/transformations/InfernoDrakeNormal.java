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

public class InfernoDrakeNormal extends L2Transformation
{
	private static final int[] SKILLS =
	{
		619, 5491, 576, 577, 578, 579
	};
	
	public InfernoDrakeNormal()
	{
		// id, colRadius, colHeight
		super(214, 15, 24);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 214) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Paw Strike (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(576, 3), false);
		// Fire Breath (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(577, 3), false);
		// Blaze Quake (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(578, 3), false);
		// Fire Armor (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(579, 3), false);
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
		// Paw Strike (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(576, 3), false);
		// Fire Breath (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(577, 3), false);
		// Blaze Quake (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(578, 3), false);
		// Fire Armor (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(579, 3), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new InfernoDrakeNormal());
	}
}
