package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DemonPrince extends L2Transformation
{
	private static final int[] SKILLS = {735,736,737,5491,619};
	public DemonPrince()
	{
		// id, colRadius, colHeight
		super(311, 33, 49);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 311 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Devil Spinning Weapon
		getPlayer().addSkill(SkillTable.getInstance().getInfo(735, 1), false);
		// Devil Seed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(736, 1), false);
		// Devil Ultimate Defense
		getPlayer().addSkill(SkillTable.getInstance().getInfo(737, 1), false);
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
		// Devil Spinning Weapon
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(735, 1), false);
		// Devil Seed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(736, 1), false);
		// Devil Ultimate Defense
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(737, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DemonPrince());
	}
}
