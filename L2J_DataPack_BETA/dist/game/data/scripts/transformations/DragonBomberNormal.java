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

public class DragonBomberNormal extends L2Transformation
{
	private static final int[] SKILLS =
	{
		580,
		581,
		582,
		583,
		5491,
		619
	};
	
	public DragonBomberNormal()
	{
		// id, colRadius, colHeight
		super(217, 16, 24);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 217) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Death Blow (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(580, 3), false);
		// Sand Cloud (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(581, 3), false);
		// Scope Bleed (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(582, 3), false);
		// Assimilation (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(583, 3), false);
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
		// Death Blow (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(580, 3), false);
		// Sand Cloud (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(581, 3), false);
		// Scope Bleed (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(582, 3), false);
		// Assimilation (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(583, 3), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DragonBomberNormal());
	}
}
