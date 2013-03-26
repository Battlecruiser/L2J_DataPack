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

public class FlyingFinalForm extends L2Transformation
{
	private static final int[] SKILLS =
	{
		932,
		950,
		951,
		953,
		1544,
		1545,
		619
	};
	
	public FlyingFinalForm()
	{
		// id, colRadius, colHeight
		super(260, 9, 38);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 260) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		getPlayer().setIsFlyingMounted(true);
		
		transformedSkills();
	}
	
	@Override
	public void onUntransform()
	{
		getPlayer().setIsFlyingMounted(false);
		
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Life to Soul
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(953, 1), false);
		// Soul Sucking
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1545, 1), false);
		
		int lvl = getPlayer().getLevel() - 78;
		
		if (lvl > 0)
		{
			// Nail Attack (up to 7 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(950, lvl), false);
			// Wing Assault (up to 7 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(951, lvl), false);
			// Death Beam (up to 7 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(1544, lvl), false);
		}
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public void transformedSkills()
	{
		// Life to Soul
		getPlayer().addSkill(SkillTable.getInstance().getInfo(953, 1), false);
		// Soul Sucking
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1545, 1), false);
		
		int lvl = getPlayer().getLevel() - 78;
		
		if (lvl > 0)
		{
			// Nail Attack (up to 7 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(950, lvl), false);
			// Wing Assault (up to 7 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(951, lvl), false);
			// Death Beam (up to 7 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(1544, lvl), false);
		}
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new FlyingFinalForm());
	}
}
