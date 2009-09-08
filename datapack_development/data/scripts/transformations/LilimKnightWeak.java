package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class LilimKnightWeak extends L2Transformation
{
	public LilimKnightWeak()
	{
		// id, colRadius, colHeight
		super(209, 12, 25.5);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 209 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Attack Buster (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(568, 2), false);
		// Attack Storm (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(569, 2), false);
		// Attack Rage (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(570, 2), false);
		// Poison Dust (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(571, 2), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{568,569,570,571,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Attack Buster (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(568, 2), false);
		// Attack Storm (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(569, 2), false);
		// Attack Rage (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(570, 2), false);
		// Poison Dust (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(571, 2), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new LilimKnightWeak());
	}
}
