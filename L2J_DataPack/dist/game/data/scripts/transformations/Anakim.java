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

public class Anakim extends L2Transformation
{
	private static final int[] SKILLS = new int[]
	{
		720,
		721,
		722,
		723,
		724,
		5491,
		619
	};
	
	public Anakim()
	{
		// id, colRadius, colHeight
		super(306, 15.5, 29);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 306) || getPlayer().isCursedWeaponEquipped())
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
		// Anakim Holy Light Burst (up to 2 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(720, 2), false);
		// Anakim Energy Attack (up to 2 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(721, 2), false);
		// Anakim Holy Beam (up to 2 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(722, 2), false);
		// Anakim Sunshine
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(723, 1), false);
		// Anakim Cleanse
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(724, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public void transformedSkills()
	{
		// Anakim Holy Light Burst (up to 2 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(720, 2), false);
		// Anakim Energy Attack (up to 2 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(721, 2), false);
		// Anakim Holy Beam (up to 2 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(722, 2), false);
		// Anakim Sunshine
		getPlayer().addSkill(SkillTable.getInstance().getInfo(723, 1), false);
		// Anakim Cleanse
		getPlayer().addSkill(SkillTable.getInstance().getInfo(724, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Anakim());
	}
}
