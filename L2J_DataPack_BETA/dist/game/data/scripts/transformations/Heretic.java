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

public class Heretic extends L2Transformation
{
	private static final int[] SKILLS =
	{
		738,
		739,
		740,
		741,
		5491,
		619
	};
	
	public Heretic()
	{
		// id, colRadius, colHeight
		super(3, 7.7, 28.4);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 3) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
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
			// Heretic Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(738, 3), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(739, 3), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(740, 3), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(741, 3), false, false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(738, 2), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(739, 2), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(740, 2), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(741, 2), false, false);
		}
		else
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(738, 1), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(739, 1), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(740, 1), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(741, 1), false, false);
		}
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public void transformedSkills()
	{
		if (getPlayer().getLevel() >= 76)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(738, 3), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(739, 3), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(740, 3), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(741, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(738, 2), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(739, 2), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(740, 2), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(741, 2), false);
		}
		else if (getPlayer().getLevel() >= 70)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(738, 1), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(739, 1), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(740, 1), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(741, 1), false);
		}
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Heretic());
	}
}
