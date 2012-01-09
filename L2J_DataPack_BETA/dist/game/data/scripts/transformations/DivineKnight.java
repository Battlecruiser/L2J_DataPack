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

public class DivineKnight extends L2Transformation
{
	private static final int[] SKILLS =
	{
		680, 681, 682, 683, 684, 685, 795, 796, 5491, 619
	};
	
	public DivineKnight()
	{
		// id, colRadius, colHeight
		super(252, 16, 30);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 252) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Divine Knight Hate
		getPlayer().addSkill(SkillTable.getInstance().getInfo(680, 1), false);
		// Divine Knight Hate Aura
		getPlayer().addSkill(SkillTable.getInstance().getInfo(681, 1), false);
		// Divine Knight Stun Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(682, 1), false);
		// Divine Knight Thunder Storm
		getPlayer().addSkill(SkillTable.getInstance().getInfo(683, 1), false);
		// Divine Knight Ultimate Defense
		getPlayer().addSkill(SkillTable.getInstance().getInfo(684, 1), false);
		// Sacrifice Knight
		getPlayer().addSkill(SkillTable.getInstance().getInfo(685, 1), false);
		// Divine Knight Brandish
		getPlayer().addSkill(SkillTable.getInstance().getInfo(795, 1), false);
		// Divine Knight Explosion
		getPlayer().addSkill(SkillTable.getInstance().getInfo(796, 1), false);
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
		// Divine Knight Hate
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(680, 1), false);
		// Divine Knight Hate Aura
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(681, 1), false);
		// Divine Knight Stun Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(682, 1), false);
		// Divine Knight Thunder Storm
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(683, 1), false);
		// Divine Knight Ultimate Defense
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(684, 1), false, false);
		// Sacrifice Knight
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(685, 1), false, false);
		// Divine Knight Brandish
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(795, 1), false);
		// Divine Knight Explosion
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(796, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineKnight());
	}
}
