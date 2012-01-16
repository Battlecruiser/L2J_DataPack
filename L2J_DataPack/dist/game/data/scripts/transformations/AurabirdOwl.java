/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class AurabirdOwl extends L2Transformation
{
	private static final int[] SKILLS = new int[]
	{
		884, 885, 887, 889, 892, 893, 895, 911, 932, 619
	};
	
	public AurabirdOwl()
	{
		// id, colRadius, colHeight
		super(9, 40, 18.57);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 9) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		getPlayer().setIsFlyingMounted(true);
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Air Blink
		if (getPlayer().getLevel() >= 75)
		{
			getPlayer().addSkill(SkillTable.getInstance().getInfo(885, 1), false);
		}
		
		// Exhilarate
		if (getPlayer().getLevel() >= 83)
		{
			getPlayer().addSkill(SkillTable.getInstance().getInfo(895, 1), false);
		}
		
		int lvl = getPlayer().getLevel() - 74;
		
		if (lvl > 0)
		{
			// Air Assault (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(884, lvl), false);
			// Sky Clutch (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(887, lvl), false);
			// Energy Storm (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(889, lvl), false);
			// Energy Shot (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(892, lvl), false);
			// Concentrated Energy Shot (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(893, lvl), false);
			// Energy Burst (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(911, lvl), false);
		}
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		getPlayer().setIsFlyingMounted(false);
		
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Air Blink
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(885, 1), false);
		
		// Exhilarate
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(895, 1), false);
		
		int lvl = getPlayer().getLevel() - 74;
		
		if (lvl > 0)
		{
			// Air Assault (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(884, lvl), false);
			// Sky Clutch (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(887, lvl), false);
			// Energy Storm (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(889, lvl), false);
			// Energy Shot (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(892, lvl), false);
			// Concentrated Energy Shot (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(893, lvl), false);
			// Energy Burst (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(911, lvl), false);
		}
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new AurabirdOwl());
	}
}
