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

public class Teleporter extends L2Transformation
{
	private static final int[] SKILLS =
	{
		5656,
		5657,
		5658,
		5659,
		619
	};
	
	public Teleporter()
	{
		// id, colRadius, colHeight
		super(319, 8, 25);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 319) || getPlayer().isCursedWeaponEquipped())
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
		final int level = getPlayer().getLevel();
		// Gatekeeper Aura Flare
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5656, level), false);
		// Gatekeeper Prominence
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5657, level), false);
		// Gatekeeper Flame Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5658, level), false);
		// Gatekeeper Berserker Spirit
		if ((level >= 35) & (level < 52))
		{
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(5659, 1), false);
		}
		else if (level >= 52)
		{
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(5659, 2), false);
		}
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public void transformedSkills()
	{
		updateSkills();
		
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onLevelUp()
	{
		updateSkills();
	}
	
	private void updateSkills()
	{
		final int level = getPlayer().getLevel();
		
		// Gatekeeper Aura Flare
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5656, level), false);
		// Gatekeeper Prominence
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5657, level), false);
		// Gatekeeper Flame Strike
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5658, level), false);
		// Gatekeeper Berserker Spirit
		if ((level >= 35) & (level < 52))
		{
			getPlayer().addSkill(SkillTable.getInstance().getInfo(5659, 1), false);
		}
		else if (level >= 52)
		{
			getPlayer().addSkill(SkillTable.getInstance().getInfo(5659, 2), false);
		}
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Teleporter());
	}
}
