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

public class DragonBomberStrong extends L2Transformation
{
	private static final int[] SKILLS =
	{
		580, 581, 582, 583, 5491, 619
	};
	
	public DragonBomberStrong()
	{
		// id, colRadius, colHeight
		super(216, 16, 24);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 216) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Death Blow (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(580, 4), false);
		// Sand Cloud (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(581, 4), false);
		// Scope Bleed (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(582, 4), false);
		// Assimilation (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(583, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Death Blow (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(580, 4), false);
		// Sand Cloud (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(581, 4), false);
		// Scope Bleed (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(582, 4), false);
		// Assimilation (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(583, 4), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DragonBomberStrong());
	}
}
