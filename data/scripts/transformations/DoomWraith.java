package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class DoomWraith extends L2Transformation
{
	public DoomWraith()
	{
		// id, colRadius, colHeight
		super(2, 13, 25);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 2 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Rolling Attack (up to 2 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(586, 2), false);
		// Earth Storm (up to 2 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(587, 2), false);
		// Curse of Darkness (up to 2 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(588, 2), false);
		// Darkness Energy Drain (up to 2 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(589, 2), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{586,587,588,589,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Rolling Attack (up to 2 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(586, 2), false);
		// Earth Storm (up to 2 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(587, 2), false);
		// Curse of Darkness (up to 2 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(588, 2), false);
		// Darkness Energy Drain (up to 2 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(589, 2), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DoomWraith());
	}
}
