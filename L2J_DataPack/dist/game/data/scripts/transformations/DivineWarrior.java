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

public class DivineWarrior extends L2Transformation
{
	private static final int[] SKILLS =
	{
		675,
		676,
		677,
		678,
		679,
		798,
		5491,
		619
	};
	
	public DivineWarrior()
	{
		// id, colRadius, colHeight
		super(253, 14.5, 29);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 253) || getPlayer().isCursedWeaponEquipped())
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
		// Cross Slash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(675, 1), false);
		// Sonic Blaster
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(676, 1), false);
		// Transfixition of Earth
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(677, 1), false);
		// Divine Warrior War Cry
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(678, 1), false, false);
		// Sacrifice Warrior
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(679, 1), false, false);
		// Divine Warrior Assault Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(798, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public void transformedSkills()
	{
		// Cross Slash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(675, 1), false);
		// Sonic Blaster
		getPlayer().addSkill(SkillTable.getInstance().getInfo(676, 1), false);
		// Transfixition of Earth
		getPlayer().addSkill(SkillTable.getInstance().getInfo(677, 1), false);
		// Divine Warrior War Cry
		getPlayer().addSkill(SkillTable.getInstance().getInfo(678, 1), false);
		// Sacrifice Warrior
		getPlayer().addSkill(SkillTable.getInstance().getInfo(679, 1), false);
		// Divine Warrior Assault Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(798, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineWarrior());
	}
}
