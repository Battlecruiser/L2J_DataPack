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

public class Treykan extends L2Transformation
{
	private static final int[] SKILLS = new int[]
	{
		619,
		967,
		968,
		969,
		5437
	};
	
	public Treykan()
	{
		// id, colRadius, colHeight
		super(126, 25, 27.00);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 126) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Cursed Body
		getPlayer().addSkill(SkillTable.getInstance().getInfo(967, 1), false);
		// Treykan Claw
		getPlayer().addSkill(SkillTable.getInstance().getInfo(968, 1), false);
		// Treykan Dash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(969, 1), false);
		// Dissonance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Cursed Body
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(967, 1), false);
		// Treykan Claw
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(968, 1), false);
		// Treykan Dash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(969, 1), false);
		// Dissonance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Treykan());
	}
}
