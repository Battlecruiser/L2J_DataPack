package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class GolemGuardianWeak extends L2Transformation
{
	public GolemGuardianWeak()
	{
		// id, colRadius, colHeight
		super(212, 13, 25);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 212 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Double Slasher (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(572, 2), false);
		// Earthquake (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(573, 2), false);
		// Bomb Installation (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(574, 2), false);
		// Steel Cutter (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(575, 2), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{572,573,574,575,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Double Slasher (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(572, 2), false);
		// Earthquake (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(573, 2), false);
		// Bomb Installation (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(574, 2), false);
		// Steel Cutter (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(575, 2), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new GolemGuardianWeak());
	}
}
