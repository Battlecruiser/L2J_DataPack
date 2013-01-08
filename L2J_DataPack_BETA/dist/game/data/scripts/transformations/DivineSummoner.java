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

public class DivineSummoner extends L2Transformation
{
	private static final int[] SKILLS =
	{
		710, 711, 712, 713, 714, 5779, 619
	};
	
	public DivineSummoner()
	{
		// id, colRadius, colHeight
		super(258, 10, 25);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 258) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		if (getPlayer().hasSummon())
		{
			getPlayer().getSummon().unSummon(getPlayer());
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Divine Summoner Summon Divine Beast
		getPlayer().addSkill(SkillTable.getInstance().getInfo(710, 1), false);
		// Divine Summoner Transfer Pain
		getPlayer().addSkill(SkillTable.getInstance().getInfo(711, 1), false);
		// Divine Summoner Final Servitor
		getPlayer().addSkill(SkillTable.getInstance().getInfo(712, 1), false);
		// Divine Summoner Servitor Hill
		getPlayer().addSkill(SkillTable.getInstance().getInfo(713, 1), false);
		// Sacrifice Summoner
		getPlayer().addSkill(SkillTable.getInstance().getInfo(714, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		if (getPlayer().hasSummon())
		{
			getPlayer().getSummon().unSummon(getPlayer());
		}
		
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Divine Summoner Summon Divine Beast
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(710, 1), false);
		// Divine Summoner Transfer Pain
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(711, 1), false);
		// Divine Summoner Final Servitor
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(712, 1), false);
		// Divine Summoner Servitor Hill
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(713, 1), false);
		// Sacrifice Summoner
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(714, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineSummoner());
	}
}
