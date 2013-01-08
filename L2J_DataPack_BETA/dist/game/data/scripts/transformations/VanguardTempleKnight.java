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

public class VanguardTempleKnight extends L2Transformation
{
	public VanguardTempleKnight()
	{
		// id
		super(314);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 314) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		int lvl = 1;
		if (getPlayer().getLevel() > 42)
		{
			lvl = (getPlayer().getLevel() - 42);
		}
		
		// Two handed mastery
		getPlayer().addSkill(SkillTable.getInstance().getInfo(293, lvl), false);
		// Full Swing
		getPlayer().addSkill(SkillTable.getInstance().getInfo(814, lvl), false);
		// Power Divide
		getPlayer().addSkill(SkillTable.getInstance().getInfo(816, lvl), false);
		// Boost Morale
		getPlayer().addSkill(SkillTable.getInstance().getInfo(956, lvl), false);
		// Guillotine Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(957, lvl), false);
		// Switch Stance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
		// Set allowed skills
		getPlayer().setTransformAllowedSkills(new int[]
		{
			10,
			18,
			28,
			67,
			197,
			293,
			400,
			449,
			814,
			816,
			838,
			956,
			957
		});
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		int lvl = 1;
		if (getPlayer().getLevel() > 42)
		{
			lvl = (getPlayer().getLevel() - 42);
		}
		
		// Two handed mastery
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(293, lvl), false);
		// Full Swing
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(814, lvl), false);
		// Power Divide
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(816, lvl), false);
		// Boost Morale
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(956, lvl), false, false);
		// Guillotine Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(957, lvl), false);
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new VanguardTempleKnight());
	}
}
