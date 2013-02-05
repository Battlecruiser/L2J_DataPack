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
		5491,
		8248
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
		/*
		 * Commented out until we figure out how to remove the skills properly. What happens if a player transforms at level 40, gets the level 40 version of the skill, then somehow levels up? Then when we untransform, the script will look for the level 41 version of the skill, right? Or will it
		 * still remove the level 40 skill? Needs to be tested. // Gatekeeper Aura Flare getPlayer().removeSkill(SkillTable.getInstance().getInfo(5656, getPlayer().getLevel()), false); // Gatekeeper Prominence getPlayer().removeSkill(SkillTable.getInstance().getInfo(5657, getPlayer().getLevel()),
		 * false); // Gatekeeper Flame Strike getPlayer().removeSkill(SkillTable.getInstance().getInfo(5658, getPlayer().getLevel()), false); // Gatekeeper Berserker Spirit (there are two levels, when do players get access to level 2?) getPlayer().removeSkill(SkillTable.getInstance().getInfo(5659,
		 * 1), false);
		 */
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Cancel Gatekeeper Transformation
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(8248, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public void transformedSkills()
	{
		/*
		 * Commented out until we figure out how to remove the skills properly. What happens if a player transforms at level 40, gets the level 40 version of the skill, then somehow levels up? Then when we untransform, the script will look for the level 41 version of the skill, right? Or will it
		 * still remove the level 40 skill? Needs to be tested. // Gatekeeper Aura Flare getPlayer().addSkill(SkillTable.getInstance().getInfo(5656, getPlayer().getLevel()), false); // Gatekeeper Prominence getPlayer().addSkill(SkillTable.getInstance().getInfo(5657, getPlayer().getLevel()), false);
		 * // Gatekeeper Flame Strike getPlayer().addSkill(SkillTable.getInstance().getInfo(5658, getPlayer().getLevel()), false); // Gatekeeper Berserker Spirit (there are two levels, when do players get access to level 2?) getPlayer().addSkill(SkillTable.getInstance().getInfo(5659, 1), false);
		 */
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Cancel Gatekeeper Transformation
		getPlayer().addSkill(SkillTable.getInstance().getInfo(8248, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Teleporter());
	}
}
