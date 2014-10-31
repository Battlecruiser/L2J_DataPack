package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class GolemGuardianNormal extends L2Transformation
{
	private static final int[] SKILLS = {572,573,574,575,5491,619};
	public GolemGuardianNormal()
	{
		// id, colRadius, colHeight
		super(211, 13, 25);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 211 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Double Slasher (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(572, 3), false);
		// Earthquake (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(573, 3), false);
		// Bomb Installation (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(574, 3), false);
		// Steel Cutter (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(575, 3), false);
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
		// Double Slasher (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(572, 3), false);
		// Earthquake (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(573, 3), false);
		// Bomb Installation (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(574, 3), false);
		// Steel Cutter (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(575, 3), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new GolemGuardianNormal());
	}
}
