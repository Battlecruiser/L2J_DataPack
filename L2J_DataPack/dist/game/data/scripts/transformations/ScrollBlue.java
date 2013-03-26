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

/**
 * TODO: Buffs disappear once you get transformed, but reappear after the transformed state wears off. Skills involved in the minigame but are not assigned directly to players: Flip Nearby Blocks - 5847 - For Flip Block, there are two skills, one for each side (makes sense). For this, there is only
 * one skill. Thus it is probably not assigned to the transformation. Block Trigger Slow - 5848 - This may be assigned to players, unsure. Decrease Speed - 5849 - This is possibly assigned to all players to set all players to the same running speed for the duration of the game. Block Trigger Stun -
 * 5849 - From L2Vault: "The squares gives drops of "bond" and "landmine
 * ". I wasn't able to figure out what the bond did as it wasn't anything that seemed to go into your inventory. However, Landmine did appear in your inventory which allows you to use it before flipping a square which will give the other team a state of stun when they attempt to flip the same square (from what I can gather, it all happens so quickly ;) "
 * Shock - 5851 - Stun effect from 5849 More Info: http://l2vault.ign.com/wiki/index.php/Handy%E2%80%99s_Block_Checker
 */
public class ScrollBlue extends L2Transformation
{
	private static final int[] SKILLS =
	{
		5852,
		5491,
		619
	};
	
	public ScrollBlue()
	{
		// id, colRadius, colHeight
		super(122, 9, 28.3);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 122) || getPlayer().isCursedWeaponEquipped())
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
		// Flip Block
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5852, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		// getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public void transformedSkills()
	{
		// Flip Block
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5852, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		// getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new ScrollBlue());
	}
}
